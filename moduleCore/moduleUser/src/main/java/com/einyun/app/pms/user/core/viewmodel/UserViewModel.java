package com.einyun.app.pms.user.core.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.BaseResponse;
import com.einyun.app.base.util.ActivityUtil;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.net.CommonHttpService;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UCService;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.library.uc.user.model.CheckNumModel;
import com.einyun.app.library.uc.user.model.ImgVerifyModel;
import com.einyun.app.library.uc.user.model.TenantModel;
import com.einyun.app.library.uc.user.model.UpdateAppModel;
import com.einyun.app.library.uc.user.model.UserInfoModel;
import com.einyun.app.library.uc.user.model.UserModel;
import com.einyun.app.library.uc.user.net.request.ChangePassRequest;
import com.einyun.app.pms.mine.model.GetUserByccountBean;
import com.einyun.app.pms.mine.repository.FeedBackRepository;
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
    private LiveData<Object> checkNumModelLiveData;
    private LiveData<String> phoneLiveData;
    private LiveData<ImgVerifyModel> imgVerifyModelLiveData;
    FeedBackRepository repository = new FeedBackRepository();

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
    public LiveData<UserModel> login(String username, String password,String code,String uuid, boolean isShowLoading) {
        //网络数据交互，显示Loading
        if (isShowLoading) {
            showLoading();
        }
        mUserModel = mUCService.login(username, password,code,uuid, new CallBack<UserModel>() {
            @Override
            public void call(UserModel data) {
                if (isShowLoading) {
                    //关闭Loading
                    hideLoading();
                }
                UserServiceManager.getInstance().saveUserModel(data);
                CommonHttpService.getInstance().authorToken(data.getToken());
                mUsersRepo.saveOrUpdateUser(new UserModel("", data.getUserId(), "", username, password));
                SPUtils.put(BasicApplication.getInstance(), Constants.SP_KEY_TOKEN, data.getToken());
                SPUtils.put(BasicApplication.getInstance(), com.einyun.app.common.Constants.SP_KEY_USER_NAME, data.getAccount());
                SPUtils.put(BasicApplication.getInstance(), com.einyun.app.common.Constants.SP_KEY_USER_ID, data.getUserId());

            }

            @Override
            public void onFaild(Throwable throwable) {
                if (isShowLoading) {
                    //关闭Loading
                    hideLoading();
                } else {
                    ARouter.getInstance().build(RouterUtils.ACTIVITY_USER_LOGIN).navigation();
                    ActivityUtil.getActivityList().get(0).finish();
                }
                ThrowableParser.onFailed(throwable);
            }
        });//数据获取
        return mUserModel;
    }

/**
 * 刷新token，并且登录
 * */
    public LiveData<UserModel> updateToken(String password, boolean isShowLoading) {
        //网络数据交互，显示Loading
        if (isShowLoading) {
            showLoading();
        }
        mUserModel = mUCService.updateToken( password, new CallBack<UserModel>() {
            @Override
            public void call(UserModel data) {
                if (isShowLoading) {
                    //关闭Loading
                    hideLoading();
                }
                UserServiceManager.getInstance().saveUserModel(data);
                CommonHttpService.getInstance().authorToken(data.getToken());
                mUsersRepo.saveOrUpdateUser(new UserModel("", data.getUserId(), "", data.getAccount(), password));
                SPUtils.put(BasicApplication.getInstance(), Constants.SP_KEY_TOKEN, data.getToken());
                SPUtils.put(BasicApplication.getInstance(), com.einyun.app.common.Constants.SP_KEY_USER_NAME, data.getAccount());
                SPUtils.put(BasicApplication.getInstance(), com.einyun.app.common.Constants.SP_KEY_USER_ID, data.getUserId());
//                UserServiceManager.getInstance().saveToken(data.getToken());
//                CommonHttpService.getInstance().authorToken(data.getToken());
//                Log.d("test",UserServiceManager.getInstance().getCurrentUserModel().getAccount());
//                SPUtils.put(BasicApplication.getInstance(), Constants.SP_KEY_TOKEN, data.getToken());
//                SPUtils.put(BasicApplication.getInstance(), com.einyun.app.common.Constants.SP_KEY_USER_NAME, data.getAccount());
            }

            @Override
            public void onFaild(Throwable throwable) {
                if (isShowLoading) {
                    //关闭Loading
                    hideLoading();
                } else {
                    ARouter.getInstance().build(RouterUtils.ACTIVITY_USER_LOGIN).navigation();
                    ActivityUtil.getActivityList().get(0).finish();
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
    public LiveData<TenantModel> getTenantId(String code, boolean splash) {
        //temp code for tenantid
        return mUCService.getTenantId(code, new CallBack<TenantModel>() {
            @Override
            public void call(TenantModel data) {
                CommonHttpService.getInstance().tenantId(data.getId());
                Logger.d(TAG, "tentantId:" + data.getId());
                SPUtils.put(BasicApplication.getInstance(), Constants.SP_KEY_TENANT_CODE, code);
                SPUtils.put(BasicApplication.getInstance(), Constants.SP_KEY_TENANT_ID, data.getId());
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
                if (splash) {
                    ARouter.getInstance().build(RouterUtils.ACTIVITY_USER_LOGIN).navigation();
                }
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
//                    privacyTermView.dismiss();
//                    ActivityUtil.finishActivitys();
                    showTip(context);
                }
            });
            privacyTermView.show();
        }
    }

    /**
     * 不同意协议退出提示
     */
    private void showTip(Context context) {
        new AlertDialog(context).builder().setTitle("提示").setMsg("因您未同意服务&隐私协议，\n" +
                "请点击“确定”后退出APP！").setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.finishActivitys();
            }
        }).show();
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

    public LiveData<UpdateAppModel> updateApp() {
        return mUCService.updateApp(new CallBack<UpdateAppModel>() {
            @Override
            public void call(UpdateAppModel data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
                ARouter.getInstance().build(RouterUtils.ACTIVITY_USER_LOGIN).navigation();
            }
        });
    }

    public LiveData<GetUserByccountBean> getUserByccountBeanLiveData(String id) {
        showLoading();
        MutableLiveData<GetUserByccountBean> userBean = new MutableLiveData<>();
        repository.queryUserInfo(id, new CallBack<GetUserByccountBean>() {
            @Override
            public void call(GetUserByccountBean data) {
                hideLoading();
                userBean.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return userBean;
    }

    /**
     * 获取短信验证码
     */
    public LiveData<BaseResponse> getCheckNum(String phone) {
        showLoading();
        MutableLiveData<BaseResponse> booleanLiveData = new MutableLiveData<>();
        checkNumModelLiveData = mUCService.getCheckNum(phone, new CallBack<Object>() {
            @Override
            public void call(Object data) {
                hideLoading();

                booleanLiveData.postValue((BaseResponse) data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                booleanLiveData.postValue(null);
                ThrowableParser.onFailed(throwable);
            }
        });
        return booleanLiveData;
    }

    /**
     * 获取短信验证码
     */
    public LiveData<BaseResponse> checkNum(String phone, String code) {
        showLoading();
        MutableLiveData<BaseResponse> booleanLiveData = new MutableLiveData<>();
        checkNumModelLiveData = mUCService.checkkNum(phone, code, new CallBack<Object>() {
            @Override
            public void call(Object data) {
                hideLoading();
                booleanLiveData.postValue((BaseResponse) data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                booleanLiveData.postValue(null);
                ThrowableParser.onFailed(throwable);
            }
        });
        return booleanLiveData;
    }
    /**
     * 修改密码
     */
    public LiveData<BaseResponse> changePass(String phone, String code,String account,String newPass) {
        showLoading();
        MutableLiveData<BaseResponse> booleanLiveData = new MutableLiveData<>();
        ChangePassRequest request=new ChangePassRequest();
        request.setPhone(phone);
        request.setAccount(account);
        request.setCode(code);
        request.setNewPwd(newPass);
        checkNumModelLiveData = mUCService.changePass(request, new CallBack<Object>() {
            @Override
            public void call(Object data) {
                hideLoading();
                booleanLiveData.postValue((BaseResponse) data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                booleanLiveData.postValue(null);
                ThrowableParser.onFailed(throwable);
            }
        });
        return booleanLiveData;
    }
    /**
     * 根据账号获取手机号
     */
    public LiveData<String> getPhone(String account) {
        showLoading();
        MutableLiveData<String> liveData = new MutableLiveData<>();
        phoneLiveData = mUCService.getPhone(account, new CallBack<String>() {

            @Override
            public void call(String data) {
                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                liveData.postValue("");
                ThrowableParser.onFailed(throwable);
            }
        });
        return liveData;
    }

    /**
     * 获取图片验证码
     */
    public LiveData<ImgVerifyModel> getImgVerify() {
        showLoading();
        MutableLiveData<ImgVerifyModel> liveData = new MutableLiveData<>();
        imgVerifyModelLiveData = mUCService.getImgVerify( new CallBack<ImgVerifyModel>() {

            @Override
            public void call(ImgVerifyModel data) {
                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                liveData.postValue(null);
                ThrowableParser.onFailed(throwable);
            }
        });
        return liveData;
    }
}
