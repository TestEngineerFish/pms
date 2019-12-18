package com.einyun.app.pms.customerinquiries.viewmodule;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean;
import com.einyun.app.library.resource.workorder.model.WorkOrderTypeModel;
import com.einyun.app.library.resource.workorder.repository.ResourceWorkOrderRepo;
import com.einyun.app.pms.customerinquiries.module.InquiriesItemModule;
import com.einyun.app.pms.customerinquiries.module.InquiriesRequestBean;
import com.einyun.app.pms.customerinquiries.module.InquiriesTypesBean;
import com.einyun.app.pms.customerinquiries.respository.CustomerInquiriesRepository;
import com.einyun.app.pms.customerinquiries.respository.DataSourceFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_PENDING;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP;

public class CusInquiriesFragmentViewModel extends BasePageListViewModel<InquiriesItemModule> {
    public String currentFragmentTag=FRAGMENT_TO_FOLLOW_UP;
    public List<SelectModel> listAll = new ArrayList<>();
    private MutableLiveData<List<WorkOrderTypeModel>> workOrderTypeList = new MutableLiveData<>();//条线
    private ResourceWorkOrderRepo resourceWorkOrderRepo;
    public List<ResourceTypeBean> resourceTypeBeans = new ArrayList<>();
    private MutableLiveData<List<ResourceTypeBean>> tiaoxianList = new MutableLiveData<>();//条线
    /**
     * 获取跳线 LiveData
     *
     * @return LiveData
     */
    public LiveData<List<WorkOrderTypeModel>> getOrderType() {
        showLoading();
        resourceWorkOrderService.getWorkOrderType(new CallBack<List<WorkOrderTypeModel>>() {
            @Override
            public void call(List<WorkOrderTypeModel> data) {
                hideLoading();
                workOrderTypeList.postValue(data);
                //先获取第一级别，并将其他级别按照parentid分组
                listAll = new ArrayList<>();
                for (WorkOrderTypeModel beanLoop : data) {
                    SelectModel selectModel = new SelectModel();
                    selectModel.setId(beanLoop.getId());
                    selectModel.setIsCheck(false);
                    selectModel.setContent(beanLoop.getText());
                    selectModel.setType("");
                    selectModel.setTypeId(beanLoop.getTypeId());
                    selectModel.setKey(beanLoop.getKey());
                    selectModel.setName(beanLoop.getName());
                    selectModel.setParentId(beanLoop.getParentId());
                    selectModel.setOpen(beanLoop.getOpen());
                    selectModel.setText(beanLoop.getText());
                    selectModel.setKey(beanLoop.getKey());
                    listAll.add(selectModel);
                }
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });

        return workOrderTypeList;
    }
    /**
     * 获取跳线 LiveData
     *
     * @return LiveData
     */
    public LiveData<List<ResourceTypeBean>> getTiaoXian() {
        showLoading();
        resourceWorkOrderRepo.getTiaoXian(new CallBack<List<ResourceTypeBean>>() {
            @Override
            public void call(List<ResourceTypeBean> data) {
                hideLoading();
                tiaoxianList.postValue(data);
                resourceTypeBeans = new ArrayList<>();
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });

        return tiaoxianList;
    }
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
    ResourceWorkOrderService resourceWorkOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    CustomerInquiriesRepository repository= new CustomerInquiriesRepository();
    private MutableLiveData<List<InquiriesTypesBean>> detialType=new MutableLiveData<>();
    public LiveData<List<InquiriesTypesBean>> queryAduitType(){
        showLoading();
        repository.queryType( new CallBack<List<InquiriesTypesBean>>() {
            @Override
            public void call(List<InquiriesTypesBean> data) {
                hideLoading();
                detialType.postValue(data);
                listAll = new ArrayList<>();
                for (InquiriesTypesBean beanLoop : data) {
                    SelectModel selectModel = new SelectModel();
                    selectModel.setId(beanLoop.getId());
                    selectModel.setIsCheck(false);
                    selectModel.setContent("问询类别");
                    selectModel.setType("");
                    selectModel.setTypeId(beanLoop.getId());
                    selectModel.setKey(beanLoop.getDataKey());
                    selectModel.setName(beanLoop.getDataName());
                    selectModel.setParentId("");
                    selectModel.setOpen("");
                    selectModel.setText(beanLoop.getDataName());
                    selectModel.setKey(beanLoop.getDataKey());
                    listAll.add(selectModel);
                }
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
