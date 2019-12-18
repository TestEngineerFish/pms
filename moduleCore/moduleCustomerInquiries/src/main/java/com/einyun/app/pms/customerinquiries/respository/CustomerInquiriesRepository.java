package com.einyun.app.pms.customerinquiries.respository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.pms.customerinquiries.constants.URLS;
import com.einyun.app.pms.customerinquiries.module.InquiriesListModule;
import com.einyun.app.pms.customerinquiries.module.InquiriesRequestBean;
import com.einyun.app.pms.customerinquiries.module.InquiriesTypesBean;
import com.orhanobut.logger.Logger;

import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_COPY_ME;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_HAVE_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FEED_BACK;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TRANSFERRED_TO;


public class CustomerInquiriesRepository {
    CustomerInquiriesServiceApi serviceApi;

    public CustomerInquiriesRepository() {
        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi(CustomerInquiriesServiceApi.class);
    }
    public void pageQuery(InquiriesRequestBean page, String tag, CallBack<InquiriesListModule> callback) {
//        QueryBuilder queryBuilder=new QueryBuilder();
//        Query build = queryBuilder.addQueryItem("","").build();
//        PageQueryRequest request = new PageQueryRequest();
//        request.setPageBean(page);
        String url="";
        switch (tag) {
            case FRAGMENT_TO_FOLLOW_UP://待跟进
                url = URLS.URL_GET_TO_FOLLOW_UP_LIST;
                break;
            case FRAGMENT_TO_FEED_BACK://待反馈
                url = URLS.URL_GET_TO_FEED_BACK_LIST;
                break;
            case FRAGMENT_HAVE_TO_FOLLOW_UP://已跟进
                url = URLS.URL_GET_HAVE_TO_FOLLOW_UP_LIST;
                break;
            case FRAGMENT_TRANSFERRED_TO://已办结
                url = URLS.URL_GET_TRANSFERRED_TO_LIST;
                break;
            case FRAGMENT_COPY_ME://抄送我
                url = URLS.URL_GET_COPY_ME_LIST;
                break;
        }

            serviceApi.getInquiriesList(url,page).compose(RxSchedulers.inIoMain())
                    .subscribe(response -> {
//                    if(response.isState()){
//                        if (mPage1==page.getPageBean().getPage()) {
//
//                        }else {
//                            mPage1=page.getPageBean().getPage();
                            callback.call(response.getData());
                            Logger.d("ItemDataSourcce.0pageBean .."+page.getPageBean().getPage());
//                        }

//                    }
                    }, error -> {
                        callback.onFaild(error);
                        error.printStackTrace();
                    });


    }
    public void queryType(CallBack<List<InquiriesTypesBean>> callBack) {
        String url = URLS.URL_GET_QUIRIES_TYPES;
        serviceApi.getTypes(url).compose(RxSchedulers.inIoMain())
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
