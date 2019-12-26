package com.einyun.app.pms.customerinquiries.viewmodule;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean;
import com.einyun.app.library.resource.workorder.model.WorkOrderTypeModel;
import com.einyun.app.library.resource.workorder.repository.ResourceWorkOrderRepo;
import com.einyun.app.pms.customerinquiries.model.InquiriesItemModule;
import com.einyun.app.pms.customerinquiries.model.InquiriesRequestBean;
import com.einyun.app.pms.customerinquiries.model.InquiriesTypesBean;
import com.einyun.app.pms.customerinquiries.respository.CustomerInquiriesRepository;
import com.einyun.app.pms.customerinquiries.respository.DataSourceFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP;

public class CusInquiriesFragmentViewModel extends BasePageListViewModel<InquiriesItemModule> {
    public String currentFragmentTag=FRAGMENT_TO_FOLLOW_UP;
    /**
     * 获取Paging LiveData
     * @return LiveData
     */
    public LiveData<PagedList<InquiriesItemModule>> loadPadingData(InquiriesRequestBean requestBean, String tag){

        pageList = new LivePagedListBuilder(new DataSourceFactory(requestBean,tag), config)
//                .setBoundaryCallback(null)
//                .setFetchExecutor(null)
                .build();

        return pageList;
    }
    CustomerInquiriesRepository repository= new CustomerInquiriesRepository();
    private MutableLiveData<List<InquiriesTypesBean>> detialType=new MutableLiveData<>();
    public LiveData<List<InquiriesTypesBean>> queryAduitType(){
        showLoading();
        repository.queryType( new CallBack<List<InquiriesTypesBean>>() {
            @Override
            public void call(List<InquiriesTypesBean> data) {
                hideLoading();
                detialType.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return detialType;
    }
    public InquiriesRequestBean getRequestBean(int page, int pageSize, String cate, String state, String divideId){

        JsonObject jsonObject = new JsonObject();
        JsonObject pageBean = new JsonObject();
        pageBean.addProperty("page", page);
        pageBean.addProperty("pageSize", pageSize);
        pageBean.addProperty("showTotal", false);

        JsonArray querys = new JsonArray();

        JsonArray sorters = new JsonArray();
        JsonObject sorter = new JsonObject();
        sorter.addProperty("property", "wx_time");
        sorter.addProperty("direction", "DESC");
        sorters.add(sorter);

        JsonObject querys1 = new JsonObject();
        querys1.addProperty("property", "wx_cate_id");
        querys1.addProperty("operation", "EQUAL");
        querys1.addProperty("value", cate);
        querys1.addProperty("relation", "AND");

        JsonObject querys2 = new JsonObject();
        querys2.addProperty("property", "state");
        querys2.addProperty("operation", "EQUAL");
        querys2.addProperty("value", state);
        querys2.addProperty("relation", "AND");

        JsonObject querys3 = new JsonObject();
        querys3.addProperty("property", "wx_dk_id");
        querys3.addProperty("operation", "EQUAL");
        querys3.addProperty("value", divideId);
        querys3.addProperty("relation", "AND");


        if (!divideId.isEmpty()) {
            querys.add(querys3);
        }

        if (!cate.isEmpty()) {
            querys.add(querys1);
        }
        if (!state.isEmpty()) {
            querys.add(querys2);
        }

        jsonObject.add("pageBean", pageBean);
        jsonObject.add("querys", querys);
        jsonObject.add("sorter", sorters);
        Log.e("客户问询", "RequestTodoList: "+jsonObject.toString() );

        return new Gson().fromJson(jsonObject.toString(),InquiriesRequestBean.class);
    }

}
