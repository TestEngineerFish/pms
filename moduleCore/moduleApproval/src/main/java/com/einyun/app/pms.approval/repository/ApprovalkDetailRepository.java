package com.einyun.app.pms.approval.repository;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.library.portal.dictdata.net.URLS;
import com.einyun.app.pms.approval.module.ApprovalBean;
import com.einyun.app.pms.approval.module.ApprovalDetailInfoBean;
import com.einyun.app.pms.approval.module.ApprovalListModule;
import com.einyun.app.pms.approval.module.GetByTypeKeyForComBoModule;
import com.einyun.app.pms.approval.module.GetByTypeKeyInnerAuditStatusModule;
import com.einyun.app.pms.approval.module.UrlxcgdGetInstBOModule;
import com.einyun.app.pms.approval.net.URL;
import com.einyun.app.pms.approval.response.ApprovalServiceApi;

import java.util.List;

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
}
