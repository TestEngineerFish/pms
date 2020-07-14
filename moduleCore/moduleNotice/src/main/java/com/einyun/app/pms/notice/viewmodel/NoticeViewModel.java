package com.einyun.app.pms.notice.viewmodel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.databinding.LayoutListPageStateBinding;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.library.core.api.MdmService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.mdm.model.NoticeListModel;
import com.einyun.app.library.mdm.model.NoticeModel;
import com.einyun.app.library.mdm.net.request.AddReadingRequest;
import com.einyun.app.library.mdm.net.request.NoticeListPageRequest;
import com.einyun.app.library.mdm.net.request.UpdateNoticeLikeBadRequest;
import com.einyun.app.library.mdm.repository.MdmRepository;
import com.einyun.app.pms.notice.model.GetUserByccountModel;
import com.einyun.app.pms.notice.repository.DataSourceFactory;
import com.google.gson.Gson;

public class NoticeViewModel extends BasePageListViewModel<NoticeListModel> {
    MdmRepository mdmRepository;
    MdmService mdmService;
    LiveData<PagedList<NoticeModel>> liveData;
    public MutableLiveData<NoticeModel> noticeModelLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> noticeStateLiveData = new MutableLiveData<>();
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    NoticeRepository repository=new NoticeRepository();

    public NoticeViewModel() {
        this.mdmRepository = new MdmRepository();
        this.mdmService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_MDM);
        mdmService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_MDM);
    }

    public LiveData<GetUserByccountModel> getUserByccountBeanLiveData(String id){
        MutableLiveData<GetUserByccountModel> detial=new MutableLiveData<>();
        showLoading();
        repository.queryUserInfo(id, new CallBack<GetUserByccountModel>() {
            @Override
            public void call(GetUserByccountModel data) {
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


    /**
     * 获取Paging LiveData
     *
     * @return LiveData
     */
    public LiveData<PagedList<NoticeModel>> loadPagingData(NoticeListPageRequest noticeListPageRequest, LayoutListPageStateBinding pageState) {
        liveData = new LivePagedListBuilder(new DataSourceFactory(noticeListPageRequest, pageState), config)
                .build();
        return liveData;
    }

    Gson gson = new Gson();

    public void addReading(LifecycleOwner owner, String id) {
        getUserByccountBeanLiveData(userModuleService.getAccount()).observe(owner,getUserByccountBean -> {
            AddReadingRequest request = new AddReadingRequest();
            request.setCommunityAnnouncementId(id);
            request.setFullname(userModuleService.getRealName());
            request.setMemberId(userModuleService.getUserId());
            request.setNickName(userModuleService.getUserName());
            request.setPhone(getUserByccountBean.getMobile());
            mdmService.addReading(request, new CallBack<Object>() {
                @Override
                public void call(Object data) {
                }

                @Override
                public void onFaild(Throwable throwable) {
                    ThrowableParser.onFailed(throwable);
                }
            });
        });
    }

    public void updateNoticeLikeBad(String id, String thumbs) {
        UpdateNoticeLikeBadRequest request = new UpdateNoticeLikeBadRequest();
        request.setCommunityAnnouncementId(id);
        request.setThumbsUpDown(thumbs);
        request.setMemberId(userModuleService.getUserId());
        mdmService.updateNoticeLikeBad(request, new CallBack<Object>() {
            @Override
            public void call(Object data) {
                getNoticeDetail(id);
                queryUpDown(id);
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    public MutableLiveData<NoticeModel> getNoticeDetail(String id) {
        mdmService.getNoticeDetail(id, new CallBack<NoticeModel>() {
            @Override
            public void call(NoticeModel data) {
                noticeModelLiveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
        return noticeModelLiveData;
    }

    public MutableLiveData<Integer> queryUpDown(String id) {
        mdmService.queryUpDown(id, userModuleService.getUserId(), new CallBack<Integer>() {
            @Override
            public void call(Integer data) {
                noticeStateLiveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
        return noticeStateLiveData;
    }

}
