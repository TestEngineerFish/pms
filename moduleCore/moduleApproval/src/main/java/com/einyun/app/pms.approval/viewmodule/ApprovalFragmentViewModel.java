package com.einyun.app.pms.approval.viewmodule;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.pms.approval.model.ApprovalBean;
import com.einyun.app.pms.approval.model.ApprovalItemmodule;
import com.einyun.app.pms.approval.model.GetByTypeKeyForComBoModule;
import com.einyun.app.pms.approval.model.GetByTypeKeyInnerAuditStatusModule;
import com.einyun.app.pms.approval.repository.ApprovalkListRepository;
import com.einyun.app.pms.approval.repository.DataSourceFactory;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ApprovalFragmentViewModel extends BasePageListViewModel<ApprovalItemmodule> {
    /**
     * 获取Paging LiveData
     * @return LiveData
     */
    public LiveData<PagedList<ApprovalItemmodule>> loadPadingData(ApprovalBean approvalBean,int table){

            pageList = new LivePagedListBuilder(new DataSourceFactory(approvalBean,table), config)
//                .setBoundaryCallback(null)
//                .setFetchExecutor(null)
                    .build();

        return pageList;
    }

    ApprovalkListRepository repository=new ApprovalkListRepository();

    private MutableLiveData<List<GetByTypeKeyInnerAuditStatusModule>> detial=new MutableLiveData<>();
    public LiveData<List<GetByTypeKeyInnerAuditStatusModule>> queryAduitState(){
        showLoading();
        repository.queryState(new CallBack<List<GetByTypeKeyInnerAuditStatusModule>>() {
            @Override
            public void call(List<GetByTypeKeyInnerAuditStatusModule> data) {
                hideLoading();
                detial.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return detial;
    }

    private MutableLiveData<List<GetByTypeKeyForComBoModule>> detialType=new MutableLiveData<>();
    public static boolean isFirst=true;
    public LiveData<List<GetByTypeKeyForComBoModule>> queryAduitType(){
        showLoading();
        repository.queryType( new CallBack<List<GetByTypeKeyForComBoModule>>() {
            @Override
            public void call(List<GetByTypeKeyForComBoModule> data) {
                hideLoading();
                if (isFirst) {
                    isFirst=false;
                    detialType.postValue(data);
                }else {


                }
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return detialType;
    }
    public ApprovalBean getData(int page, int pageSize) {
        JSONObject jsonObject = new JSONObject();
        JSONObject pageBean = new JSONObject();
        pageBean.put("page", page);
        pageBean.put("pageSize", pageSize);
        pageBean.put("showTotal", true);
        jsonObject.put("pageBean", pageBean);
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("querys", jsonArray);
        return new Gson().fromJson(jsonObject.toString(), ApprovalBean.class);
    }
    public ApprovalBean getData(int page, int pageSize, String divideId, String divideName, String auditType, String auditSubType, String auditStatus) {
        JSONObject jsonObject = new JSONObject();
        JSONObject pageBean = new JSONObject();
        pageBean.put("page", page);
        pageBean.put("pageSize", pageSize);
        pageBean.put("showTotal", true);
        jsonObject.put("pageBean", pageBean);
        JSONArray jsonArray = new JSONArray();
        if (!divideId.isEmpty()) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("property", "divide_id");
            jsonObject1.put("operation", "EQUAL");
            jsonObject1.put("value", divideId);
            jsonObject1.put("relation", "AND");
            jsonArray.add(jsonObject1);
        }

        if (!divideName.isEmpty()) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("property", "divide_name");
            jsonObject1.put("operation", "EQUAL");
            jsonObject1.put("value", divideName);
            jsonObject1.put("relation", "AND");
            jsonArray.add(jsonObject1);
        }

        if (!auditType.isEmpty()) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("property", "audit_type");
            jsonObject1.put("operation", "EQUAL");
            jsonObject1.put("value", auditType);
            jsonObject1.put("relation", "AND");
            jsonArray.add(jsonObject1);
        }

        if (!auditSubType.isEmpty()) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("property", "audit_sub_type");
            jsonObject1.put("operation", "EQUAL");
            jsonObject1.put("value", auditSubType);
            jsonObject1.put("relation", "AND");
            jsonArray.add(jsonObject1);
        }


        if (!auditStatus.isEmpty()) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("property", "vo.status");
            jsonObject1.put("operation", "EQUAL");
            jsonObject1.put("value", auditStatus);
            jsonObject1.put("relation", "AND");
            jsonArray.add(jsonObject1);
        }
        jsonObject.put("querys", jsonArray);
        return new Gson().fromJson(jsonObject.toString(), ApprovalBean.class);
    }
    public  List<GetByTypeKeyForComBoModule> mTypeLists=new ArrayList<>();
    public List<GetByTypeKeyForComBoModule> sortAuditTypelist(List<GetByTypeKeyForComBoModule> model) {
        for (int i = 0; i < model.size(); i++) {
            Log.e("sortAuditTypelist", "sortAuditTypelist: "+model.get(i).getKey());

        }
        mTypeLists.clear();
        mTypeLists.addAll(model);
//        mTypeLists.add(0,model.get(0));
//        mTypeLists.add(1,model.get(2));
//        mTypeLists.add(2,model.get(3));
//        mTypeLists.add(3,model.get(1));
        for (GetByTypeKeyForComBoModule data : model) {
            Log.e("sortAuditTypelist", "sortAuditTypelist: "+data.getKey());
            switch (data.getKey()) {
                case "INNER_AUDIT_UPDATE_PLAN"://修改计划
                    mTypeLists.set(0,data);
                    break;
                case "INNER_AUDIT_CREATE_PLAN"://创建计划
                    mTypeLists.set(1,data);
                    break;
                case "INNER_AUDIT_POSTPONED": //工单延期
                    mTypeLists.set(2,data);
                    break;
                case "INNER_AUDIT_FORCE_CLOSE"://强制闭单
                    mTypeLists.set(3,data);
                    break;
            }
        }
        return mTypeLists;
    }
}
