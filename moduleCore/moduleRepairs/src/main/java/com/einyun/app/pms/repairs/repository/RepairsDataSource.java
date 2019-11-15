package com.einyun.app.pms.repairs.repository;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageResult;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.portal.dictdata.repository.DictRepository;
import com.orhanobut.logger.Logger;
import com.einyun.app.base.paging.bean.PageBean;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.repairs.repository
 * @ClassName: RepairsDataSource
 * @Description: Paging数据源
 * @Author: chumingjun
 * @CreateDate: 2019/09/04 09:45
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/04 09:45
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class RepairsDataSource extends PositionalDataSource<DictDataModel> {


    //初始加载
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<DictDataModel> callback) {
        loadData(new PageBean(),callback);
    }

    //自动分页数据获取
    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<DictDataModel> callback) {
        int page=params.startPosition/params.loadSize+1;
        Logger.d("page:"+page);
        loadData(new PageBean(page,params.loadSize),callback);
    }

//    //根据页数获取数据
//    private  <T> void loadData(PageBean pageBean,@NonNull T callback){
//        RepairsServiceApi serviceApi= CommonHttpService.getInstance().getServiceApi(RepairsServiceApi.class);
//        RepairsRequest repairsRequest=new RepairsRequest();
//        repairsRequest.setPageBean(pageBean);
//        serviceApi.completedList(repairsRequest)
//                .compose(RxSchedulers.inIoMain())
//                .subscribe((RepairsResponse response) -> {
//                    if(callback instanceof LoadInitialCallback){
//                        LoadInitialCallback loadInitialCallback= (LoadInitialCallback) callback;
//                        loadInitialCallback.onResult(response.getRows(),PageBean.DEFAULT_PAGE,PageBean.DEFAULT_PAGE_SIZE);
//                    }else if(callback instanceof LoadRangeCallback){
//                        LoadRangeCallback loadInitialCallback= (LoadRangeCallback) callback;
//                        loadInitialCallback.onResult(response.getRows());
//                    }
//                },error->{
//
//                });
//    }

    //根据页数获取数据
    private  <T> void loadData(PageBean pageBean,@NonNull T callback){
        DictRepository repository=new DictRepository();
//        RepairsServiceApi serviceApi= CommonHttpService.getInstance().getServiceApi(RepairsServiceApi.class);
//        RepairsRequest repairsRequest=new RepairsRequest();
//        repairsRequest.setPageBean(pageBean);
        repository.dictDataList(pageBean, new CallBack<PageResult<DictDataModel>>() {
            @Override
            public void call(PageResult<DictDataModel> data) {
                if(callback instanceof LoadInitialCallback){
                    LoadInitialCallback loadInitialCallback= (LoadInitialCallback) callback;
                    loadInitialCallback.onResult(data.getRows(),0,PageBean.DEFAULT_PAGE_SIZE);
                }else if(callback instanceof LoadRangeCallback){
                    LoadRangeCallback loadInitialCallback= (LoadRangeCallback) callback;
                    loadInitialCallback.onResult(data.getRows());
                }
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }
}
