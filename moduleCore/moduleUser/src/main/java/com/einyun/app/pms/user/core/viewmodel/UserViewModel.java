package com.einyun.app.pms.user.core.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.util.ActivityUtil;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.net.CommonHttpService;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UCService;
import com.einyun.app.library.uc.user.model.TenantModel;
import com.einyun.app.library.uc.user.model.UserModel;
import com.einyun.app.pms.user.R;
import com.einyun.app.pms.user.core.Constants;
import com.einyun.app.pms.user.core.ui.widget.PrivacyTermView;
import com.einyun.app.pms.user.core.viewmodel.contract.UserViewModelContract;
import com.orhanobut.logger.Logger;
import com.einyun.app.pms.user.core.UserServiceManager;
import com.einyun.app.pms.user.core.repository.UserRepository;


import java.util.List;

/**
 * 业务逻辑处理，UI交互
 */
public class UserViewModel extends BaseViewModel implements UserViewModelContract {
    private static final String TAG = UserViewModel.class.getSimpleName();
    private UserRepository mUsersRepo;
    private UCService mUCService;
    private LiveData<UserModel> mUserModel;

    public UserViewModel() {
        mUsersRepo = new UserRepository();
        mUCService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_UC);
    }

    @Override
    public LiveData<List<UserModel>> localUser() {
        return mUsersRepo.loadUsers();
    }

    @Override
    public LiveData<UserModel> getLastUser() {
        return mUsersRepo.getLastUser();
    }

    /**
     * 登陆业务逻辑处理
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public LiveData<UserModel> login(String username, String password, boolean isShowLoading) {
        //网络数据交互，显示Loading
        if (isShowLoading) {
            showLoading();
        }
        mUserModel = mUCService.login(username, password, new CallBack<UserModel>() {
            @Override
            public void call(UserModel data) {
                if (isShowLoading) {
                    //关闭Loading
                    hideLoading();
                }
                UserServiceManager.getInstance().saveUserModel(data);
                CommonHttpService.getInstance().authorToken(data.getToken());
                mUsersRepo.saveOrUpdateUser(new UserModel("", data.getUserId(), "", username, password));
            }

            @Override
            public void onFaild(Throwable throwable) {
                if (isShowLoading) {
                    //关闭Loading
                    hideLoading();
                }
                ThrowableParser.onFailed(throwable);
            }
        });//数据获取
        return mUserModel;
    }

    /**
     * 获取TenantId
     *
     * @param code
     * @return
     */
    @Override
    public LiveData<TenantModel> getTenantId(String code) {
        //temp code for tenantid
        return mUCService.getTenantId(code, new CallBack<TenantModel>() {
            @Override
            public void call(TenantModel data) {
                CommonHttpService.getInstance().tenantId(data.getId());
                Logger.d(TAG, "tentantId:" + data.getId());
                SPUtils.put(BasicApplication.getInstance(), Constants.SP_KEY_TENANT_CODE, code);
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
                ARouter.getInstance().build(RouterUtils.ACTIVITY_USER_LOGIN).navigation();
            }
        });
    }

    /**
     * 隐私页设置
     */
    @Override
    public void showPrivacy(Context context) {
        boolean showPrivacy = (boolean) SPUtils.get(context, Constants.SP_KEY_SHOW_PRIVACY, true);
        if (showPrivacy) {
            PrivacyTermView privacyTermView = new PrivacyTermView(context, R.style.AlertDialogStyle);
            privacyTermView.setOnClickBottomListener(new PrivacyTermView.OnClickBottomListener() {
                @Override
                public void onPositiveClick() {
                    SPUtils.put(context, Constants.SP_KEY_SHOW_PRIVACY, false);
                    privacyTermView.dismiss();
                }

                @Override
                public void onNegtiveClick() {
                    privacyTermView.dismiss();
                    ActivityUtil.finishActivitys();
                }
            });
            privacyTermView.show();
        }
    }

    /**
     * 通过用户名删除数据库数据
     *
     * @param userName
     */
    @Override
    public void deleteUser(String userName) {
        mUsersRepo.deleteUser(userName);
    }

    @Override
    public LiveData<List<String>> loadAllUserName() {
        return mUsersRepo.loadAllUserName();
    }
}
