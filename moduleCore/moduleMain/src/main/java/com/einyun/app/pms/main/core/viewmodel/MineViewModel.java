package com.einyun.app.pms.main.core.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.library.core.api.DashBoardService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UCService;
import com.einyun.app.library.core.api.UserCenterService;
import com.einyun.app.library.uc.user.model.UserInfoModel;
import com.einyun.app.pms.main.core.model.HasReadModel;
import com.einyun.app.pms.main.core.model.UCUserDetailsBean;
import com.einyun.app.pms.main.core.model.UserStarsBean;
import com.einyun.app.pms.main.core.repository.MainRepository;
import com.einyun.app.pms.main.core.viewmodel.contract.MineViewModelContract;


public class MineViewModel extends BaseViewModel implements MineViewModelContract {
    UserCenterService userCenterService;
    UCService ucService;
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;

    public MineViewModel() {
//        mUsersRepo = new UserRepository();
        userCenterService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_USER_CENTER);
        ucService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_UC);
    }
    @Override
    public LiveData<String> getWorkState() {
        return userCenterService.getWorkStatus(getUserId(), new CallBack<String>() {
            @Override
            public void call(String data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    @Override
    public LiveData<String> updateWorkState(String status) {
        return userCenterService.updateWorkStatus(getUserId(), userModuleService.getUserName(), status, new CallBack<String>() {
            @Override
            public void call(String data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    @Override
    public LiveData<UserInfoModel> getUserInfoByUserId() {
        return ucService.userById(getUserId(), new CallBack<UserInfoModel>() {
            @Override
            public void call(UserInfoModel data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    /**
     * 获取用户Id
     *
     * @return
     */
    @Override
    public String getUserId() {
        return userModuleService.getUserId();
    }

    /**
     * 获取用户星级
     *
     * **/
    MainRepository repository=new MainRepository();
    private MutableLiveData<UCUserDetailsBean> detialStars=new MutableLiveData<>();
    public LiveData<UCUserDetailsBean> getStars(UserStarsBean bean){
        showLoading();
        repository.queryStars(bean, new CallBack<UCUserDetailsBean>() {
            @Override
            public void call(UCUserDetailsBean data) {
                hideLoading();
                detialStars.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return detialStars;
    }
    private MutableLiveData<HasReadModel> hasReadModel=new MutableLiveData<>();
    public LiveData<HasReadModel> hadRead(){
        showLoading();
        repository.hasRead(new CallBack<HasReadModel>() {
            @Override
            public void call(HasReadModel data) {
                hideLoading();
                hasReadModel.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return hasReadModel;
    }
}
