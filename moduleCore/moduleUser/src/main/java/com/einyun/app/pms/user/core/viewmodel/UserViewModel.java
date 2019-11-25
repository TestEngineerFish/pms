package com.einyun.app.pms.user.core.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.net.CommonHttpService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UCService;
import com.einyun.app.library.uc.user.model.TenantModel;
import com.einyun.app.library.uc.user.model.UserModel;
import com.einyun.app.pms.user.R;
import com.einyun.app.pms.user.core.Constants;
import com.einyun.app.pms.user.core.ui.widget.PrivacyTermView;
import com.orhanobut.logger.Logger;
import com.einyun.app.pms.user.core.UserServiceManager;
import com.einyun.app.pms.user.core.repository.UserRepository;


import java.util.List;

/**
 * 业务逻辑处理，UI交互
 */
public class UserViewModel extends BaseViewModel {
    private static final String TAG = UserViewModel.class.getSimpleName();
    private UserRepository mUsersRepo;
    private UCService mUCService;
    private LiveData<UserModel> mUserModel;

    public UserViewModel() {
        mUsersRepo = new UserRepository();
        mUCService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_UC);
    }

    public LiveData<List<UserModel>> localUser() {
        return mUsersRepo.loadUsers();
    }

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
    public LiveData<UserModel> login(String username, String password) {
        //网络数据交互，显示Loading
        showLoading();
        mUserModel = mUCService.login(username, password, new CallBack<UserModel>() {
            @Override
            public void call(UserModel data) {
                //关闭Loading
                hideLoading();
                UserServiceManager.getInstance().saveUserModel(data);
                CommonHttpService.getInstance().authorToken(data.getToken());
                mUsersRepo.saveOrUpdateUser(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
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
    public LiveData<TenantModel> getTenantId(String code) {
        //temp code for tenantid
        CommonHttpService.getInstance().tenantId("55614223698362369");
        return mUCService.getTenantId(code, new CallBack<TenantModel>() {
            @Override
            public void call(TenantModel data) {
                CommonHttpService.getInstance().tenantId(data.getId());
                Logger.d(TAG, "tentantId:" + data.getId());
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
    }

    /**
     * 隐私页设置
     */
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
                    BasicApplication.getInstance().exit();
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
    public void deleteUser(String userName) {
        mUsersRepo.deleteUser(userName);
    }

    public List<String> loadAllUserName() {
        return mUsersRepo.loadAllUserName().getValue();
    }
}
