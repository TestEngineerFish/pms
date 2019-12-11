package com.einyun.app.pms.approval.viewmodule;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.pms.approval.module.ApprovalDetailInfoBean;
import com.einyun.app.pms.approval.module.ApprovalFormdata;
import com.einyun.app.pms.approval.module.ApprovalSumitBean;
import com.einyun.app.pms.approval.module.GetByTypeKeyInnerAuditStatusModule;
import com.einyun.app.pms.approval.module.UrlxcgdGetInstBOModule;
import com.einyun.app.pms.approval.repository.ApprovalkDetailRepository;
import com.einyun.app.pms.approval.ui.adapter.ApprovalInfoDetailAdapter;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class ApprovalDetailViewModel extends BaseViewModel {
    ApprovalkDetailRepository repository=new ApprovalkDetailRepository();
    /*
    * 获取审批详情页 基本信息数据
    * */
    private MutableLiveData<UrlxcgdGetInstBOModule> approvalBasicInfo=new MutableLiveData<>();
    private ApprovalSumitBean approvalSumitBean;
    private String url;

    public LiveData<UrlxcgdGetInstBOModule> queryApprovalBasicInfo(String id){
        showLoading();
        repository.getApprovalBasicInfo(id, new CallBack<UrlxcgdGetInstBOModule>() {
            @Override
            public void call(UrlxcgdGetInstBOModule data) {
                hideLoading();
                approvalBasicInfo.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return approvalBasicInfo;
    }

    /*
    * 获取审批详情页 审批信息列表数据
    * */
    private MutableLiveData<ApprovalDetailInfoBean> approvalDetailInfo=new MutableLiveData<>();
    public LiveData<ApprovalDetailInfoBean> queryApprovalDetialInfo(String id){
        showLoading();
        repository.getApprovalDetailInfo(id, new CallBack<ApprovalDetailInfoBean>() {
            @Override
            public void call(ApprovalDetailInfoBean data) {
                hideLoading();
                approvalDetailInfo.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return approvalDetailInfo;
    }
    /*
     * 审批
     * */
    private MutableLiveData<Boolean> approval=new MutableLiveData<>();
    public LiveData<Boolean> sumitApproval(ApprovalSumitBean sumitbean,String url){
        showLoading();
        repository.approvalSumit(sumitbean,url, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                approval.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return approval;
    }

    public HashMap<Object, Object> approval(String actionName, UrlxcgdGetInstBOModule urlxcgdGetInstBOBean,
                         String proInstId, String taskId, String comment, ApprovalFormdata formdata) {

        String middleURL = "workOrder/workOrder/workOrderInnerAudit/v1/complete";

        String type = urlxcgdGetInstBOBean.getData().getWorkorder_audit_model().getAudit_type();
        String subType = urlxcgdGetInstBOBean.getData().getWorkorder_audit_model().getAudit_sub_type();

        //
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("auditInsId", proInstId);
        jsonObject.put("auditTaskId", taskId);
//        String comment = approvalTvOpinion.getText().toString().trim();
//        String comment = approvalTvOpinion.getText().toString().trim();
//        if (comment.length() == 0) {
//            comment = approvalTvOpinion.getText().toString().trim();
//        }

        jsonObject.put("comment", comment);
        jsonObject.put("actionName", actionName);

        JSONObject sendObject = jsonObject;

        if (type.equals("INNER_AUDIT_FORCE_CLOSE")) {//强制闭单

            JSONObject itemObject = new JSONObject();
            itemObject.put("id", urlxcgdGetInstBOBean.getData().getWorkorder_audit_model().getApply_biz_id());
            itemObject.put("extensionApplicationId", formdata.getExtensionApplicationId());
            itemObject.put("applyTaskId", formdata.getApplyTaskId());
            itemObject.put("innerAuditHandleParam", jsonObject);

            sendObject = itemObject;

            if (subType.equals("FORCE_CLOSE_PATROL")) {//巡查工单
           //                resource-workorder/res-order/patrol/closeApprove
                middleURL = "resource-workorder/res-order/patrol/closeApprove";
            } else if (subType.equals("FORCE_CLOSE_PLAN")) {//计划工单
           //                resource-workorder/res-order/plan/closeApprove
                middleURL = "resource-workorder/res-order/plan/closeApprove";
            } else if (subType.equals("FORCE_CLOSE_ALLOCATE")) {//派工单
           //                resource-workorder/res-order/distribute/closeApprove
                middleURL = "resource-workorder/res-order/distribute/closeApprove";
            } else {
                sendObject = jsonObject;
            }

        } else if (type.equals("INNER_AUDIT_CREATE_PLAN") || type.equals("INNER_AUDIT_UPDATE_PLAN")) {//创建计划 或者 修改计划
            JSONObject itemObject = new JSONObject();
            itemObject.put("id", formdata.getApprovalId());
            itemObject.put("innerAuditHandleParam", jsonObject);

            sendObject = itemObject;

            if (subType.equals("CREATE_WORK_PLAN") || subType.equals("UPDATE_WORK_PLAN")) {//创建工作计划 或 修改工作计划
//                           resource/api/resource/v1/workPlan/approve
                middleURL = "resource/api/resource/v1/workPlan/approve";
            } else if (subType.equals("CREATE_PATROL_PLAN") || subType.equals("UPDATE_PATROL_PLAN")) {//创建巡查工单 或 修改巡查工单
//                           resource/api/resource/v1/inspectionWorkPlan/approval
                middleURL = "resource/api/resource/v1/inspectionWorkPlan/approval";
            } else {
                sendObject = jsonObject;
            }

        } else if (type.equals("INNER_AUDIT_POSTPONED")) {//工单延期

            JSONObject itemObject = new JSONObject();
            itemObject.put("id", urlxcgdGetInstBOBean.getData().getWorkorder_audit_model().getApply_biz_id());
            itemObject.put("extensionApplicationId", formdata.getExtensionApplicationId());
            itemObject.put("extensionDays", formdata.getExtensionDays());
            itemObject.put("innerAuditHandleParam", jsonObject);

            sendObject = itemObject;

            if (subType.equals("POSTPONED_PATROL")) {//巡查工单
//                           resource-workorder/res-order/patrol/extenApprove
                middleURL = "resource-workorder/res-order/patrol/extenApprove";
            } else if (subType.equals("POSTPONED_PLAN")) {//计划工单
//                           resource-workorder/res-order/plan/extenApprove
                middleURL = "resource-workorder/res-order/plan/extenApprove";
            } else if (subType.equals("POSTPONED_ALLOCATE")) {//派工单
//                           resource-workorder/res-order/distribute/postPoneApprove
                middleURL = "resource-workorder/res-order/distribute/postPoneApprove";
            } else {
                sendObject = jsonObject;
            }
        }

        Logger.d("Approvaldetailvm"+sendObject.toString()+"url:  "+middleURL);
        Log.e("sendObject", "approval: "+sendObject.toString() );
        approvalSumitBean = new Gson().fromJson(sendObject.toString(), ApprovalSumitBean.class);
//        sumitApproval(approvalSumitBean,middleURL).observe(this);
        url = middleURL;
        HashMap<Object, Object> map = new HashMap<>();
        map.put("url",url);
        map.put("bean",approvalSumitBean);
        return map;
    }

}
