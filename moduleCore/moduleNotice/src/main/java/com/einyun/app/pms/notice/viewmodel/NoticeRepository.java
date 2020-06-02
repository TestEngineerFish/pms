package com.einyun.app.pms.notice.viewmodel;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.pms.notice.model.GetUserByccountModel;

public class NoticeRepository {
    NoticeServiceApi serviceApi;
    public static final String URL_GET_USER_INFO_BY_ACCOUNT="uc/api/user/v1/user/getUser?account=";
    public NoticeRepository() {
        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi(NoticeServiceApi.class);
    }

    /**
     * 获取用户信息
     * */
    public void queryUserInfo(String id, CallBack<GetUserByccountModel> callBack) {
        String url = URL_GET_USER_INFO_BY_ACCOUNT + id;
        serviceApi.getUserInfo(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if (response.isState()) {
                        callBack.call(response.getData());
                    } else {
                        callBack.onFaild(new Exception(response.getCode()));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
}
