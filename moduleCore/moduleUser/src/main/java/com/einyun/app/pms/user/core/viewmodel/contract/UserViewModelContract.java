package com.einyun.app.pms.user.core.viewmodel.contract;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.einyun.app.library.uc.user.model.TenantModel;
import com.einyun.app.library.uc.user.model.UserModel;

import java.util.List;

public interface UserViewModelContract {
    LiveData<List<UserModel>> localUser();

    LiveData<UserModel> getLastUser();

    /**
     * 登陆业务逻辑处理
     *
     * @param username
     * @param password
     * @return
     */
    LiveData<UserModel> login(String username, String password, boolean isShowLoading);

    LiveData<TenantModel> getTenantId(String code, boolean splash);

    void deleteUser(String userName);

    void showPrivacy(Context context);

    LiveData<List<String>> loadAllUserName();
}
