package com.einyun.app.pms.user.core;

import android.content.Context;
import android.text.TextUtils;

import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.common.constants.SPKey;
import com.einyun.app.library.uc.user.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.user.core
 * @ClassName: UserServiceManager
 * @Description: 用户服务管理器
 * @Author: chumingjun
 * @CreateDate: 2019/08/30 11:22
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/08/30 11:22
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class UserServiceManager {
    private static UserServiceManager instance;
    private UserModel currentUserModel;
    private String token;
    private String userId;
    private Context mContext;
    private List<String> divideCodes = new ArrayList<>();

    private UserServiceManager() {
        mContext = BasicApplication.getInstance();
    }

    public static UserServiceManager getInstance() {
        if (instance == null) {
            synchronized (UserServiceManager.class) {
                if (instance == null) {
                    instance = new UserServiceManager();
                }
            }
        }
        return instance;
    }

    public UserModel getCurrentUserModel() {
        return currentUserModel;
    }

    public void saveDivideCodes(List<String> divideCodes) {
        this.divideCodes = divideCodes;
    }

    public List<String> getDivideCodes() {
        return this.divideCodes;
    }
    public void saveUserModel(@Nonnull UserModel model) {
        if (model != null) {
            currentUserModel = model;
            saveUserId(currentUserModel.getUserId());
            saveToken(currentUserModel.getToken());
        }

    }

    /**
     * 获取本地缓存userId
     *
     * @return
     */
    public String getUserId() {
        if (TextUtils.isEmpty(this.userId)) {
            userId = SPUtils.get(mContext, Constants.SP_KEY_USERID, "").toString();
        }
        return userId;
    }

    /**
     * 获取本地缓存token
     *
     * @return
     */
    public String getToken() {
        if (TextUtils.isEmpty(token)) {
            token = SPUtils.get(getContext(), SPKey.SP_KEY_TOKEN, "").toString();
        }
        return token;
    }

    /**
     * 保存token到本地
     *
     * @param token
     */
    public void saveToken(@Nonnull String token) {
        this.token = token;
        SPUtils.put(getContext(), SPKey.SP_KEY_TOKEN, token);
    }

    /**
     * 保存UserId到本地
     *
     * @param userId
     */
    public void saveUserId(@Nonnull String userId) {
        this.userId = userId;
        SPUtils.put(getContext(), Constants.SP_KEY_USERID, userId);
    }

    public Context getContext() {
        return mContext;
    }
}
