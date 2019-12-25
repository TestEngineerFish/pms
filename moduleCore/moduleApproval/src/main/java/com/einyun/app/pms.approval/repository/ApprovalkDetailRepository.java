package com.einyun.app.pms.approval.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.pms.approval.model.ApprovalDetailInfoBean;
import com.einyun.app.pms.approval.model.ApprovalSumitBean;
import com.einyun.app.pms.approval.model.UrlxcgdGetInstBOModule;
import com.einyun.app.pms.approval.net.URL;
import com.einyun.app.pms.approval.response.ApprovalServiceApi;

public class ApprovalkDetailRepository {
    ApprovalServiceApi serviceApi;

    public ApprovalkDetailRepository() {
        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi(ApprovalServiceApi.class);
    }


    public void getApprovalBasicInfo(String id, CallBack<UrlxcgdGetInstBOModule> callBack) {
        String url = URL.URL_GET_APPROVAL_BASIC_INFO+id;
        serviceApi.getApprovalBasicInfo(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
                        callBack.onFaild(new Exception(response.getCode()));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }

    public void getApprovalDetailInfo(String id, CallBack<ApprovalDetailInfoBean> callBack) {
        String url = URL.URL_GET_APPROVAL_DETAIL_INFO+id;
        serviceApi.getApprovalDetailInfo(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
                        callBack.onFaild(new Exception(response.getCode()));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
    /**
     * 审批提交
     * @param request
     * @param callBack
     * @return
     */
    public LiveData<Boolean> approvalSumit(ApprovalSumitBean request,String url, CallBack<Boolean> callBack) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        serviceApi.sumitApproval(url,request).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    liveData.postValue(response.isState());
                    callBack.call(response.isState());
                }, error -> {
                    callBack.onFaild(error);
                });
        return liveData;
    }
}
