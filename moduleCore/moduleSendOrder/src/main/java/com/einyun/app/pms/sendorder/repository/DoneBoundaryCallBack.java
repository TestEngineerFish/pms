package com.einyun.app.pms.sendorder.repository;

import com.einyun.app.base.db.converter.DistributeListConvert;
import com.einyun.app.base.db.entity.Distribute;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrderPage;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.google.gson.Gson;

import java.util.List;

public class DoneBoundaryCallBack extends PendingBoundaryCallBack {
    public DoneBoundaryCallBack(DistributePageRequest request) {
        super(request);
        orderType=Distribute.ORDER_TYPE_DONE;
    }

    @Override
    protected void loadData(int dataType,CallBack<Integer> callBack) {
        lock.lock();
        request.setPage(pageBean.getPage());
        service.distributeDonePage((DistributePageRequest) request, new CallBack<DistributeWorkOrderPage>() {
            @Override
            public void call(DistributeWorkOrderPage data) {
                if(data.hasNextPage()){
                    callBack.call(data.nextPage());
                }
                lock.unlock();
                DistributeListConvert convert = new DistributeListConvert();
                List<Distribute> rows = convert.stringToSomeObjectList(new Gson().toJson(data.getRows()));
                if(rows.size()>0){
                    wrapList(rows);
                    persistence(rows,dataType);
                }
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
                lock.unlock();
            }
        });
    }


}
