package com.einyun.app.pms.approval.viewmodule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.pms.approval.module.ApprovalDetailInfoBean;
import com.einyun.app.pms.approval.module.GetByTypeKeyInnerAuditStatusModule;
import com.einyun.app.pms.approval.module.UrlxcgdGetInstBOModule;
import com.einyun.app.pms.approval.repository.ApprovalkDetailRepository;

import java.util.List;

public class ApprovalDetailViewModel extends BaseViewModel {
    ApprovalkDetailRepository repository=new ApprovalkDetailRepository();
    /*
    * 获取审批详情页 基本信息数据
    * */
    private MutableLiveData<UrlxcgdGetInstBOModule> approvalBasicInfo=new MutableLiveData<>();
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
}
