package com.einyun.app.pms.approval.repository;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.paging.bean.Query;
import com.einyun.app.base.paging.bean.QueryBuilder;
import com.einyun.app.library.core.net.EinyunHttpService;


import com.einyun.app.library.portal.dictdata.net.URLS;
import com.einyun.app.pms.approval.module.ApprovalBean;
import com.einyun.app.pms.approval.module.ApprovalListModule;
import com.einyun.app.pms.approval.module.GetByTypeKeyForComBoModule;
import com.einyun.app.pms.approval.module.GetByTypeKeyInnerAuditStatusModule;
import com.einyun.app.pms.approval.request.PageQueryRequest;
import com.einyun.app.pms.approval.response.ApprovalServiceApi;

import java.util.List;


/**
 * @ProjectName: pms_old
 * @Package: com.einyun.app.pms.pointcheck.repository
 * @ClassName: PointCheckListRepository
 * @Description: java类作用描述
 * @CreateDate: 2019/10/09 11:46
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/09 11:46
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ApprovalkListRepository {
    ApprovalServiceApi serviceApi;

    public ApprovalkListRepository() {
        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi(ApprovalServiceApi.class);
    }


    public void pageQuery(ApprovalBean page,int table, CallBack<ApprovalListModule> callback) {
//        QueryBuilder queryBuilder=new QueryBuilder();
//        Query build = queryBuilder.addQueryItem("","").build();
//        PageQueryRequest request = new PageQueryRequest();
//        request.setPageBean(page);
        if (table==0) {
            serviceApi.waitApproval(page).compose(RxSchedulers.inIoMain())
                    .subscribe(response -> {
//                    if(response.isState()){
                        callback.call(response.getData());
//                    }
                    }, error -> {
                        callback.onFaild(error);
                        error.printStackTrace();
                    });
        }else if (table==1){
            serviceApi.query(page).compose(RxSchedulers.inIoMain())
                    .subscribe(response -> {
//                    if(response.isState()){
                        callback.call(response.getData());
//                    }
                    }, error -> {
                        callback.onFaild(error);
                        error.printStackTrace();
                    });
        }else if (table==2){
            serviceApi.meSendApproval(page).compose(RxSchedulers.inIoMain())
                    .subscribe(response -> {
//                    if(response.isState()){
                        callback.call(response.getData());
//                    }
                    }, error -> {
                        callback.onFaild(error);
                        error.printStackTrace();
                    });
        }

    }
    public void queryState(String id, CallBack<List<GetByTypeKeyInnerAuditStatusModule>> callBack) {
        String url = URLS.URL_DATA_DICT_GET_BY_TYPE_KEY + "?typeKey=inner_audit_status";
        serviceApi.getAuditState(url).compose(RxSchedulers.inIoMain())
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
    public void queryType(CallBack<List<GetByTypeKeyForComBoModule>> callBack) {
        String url = URLS.URL_DATA_DICT_GET_BY_TYPE_KEY_FOR_COMBO + "?typeKey=inner_audit_category";
        serviceApi.getAuditType(url).compose(RxSchedulers.inIoMain())
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
