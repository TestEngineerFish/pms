package com.einyun.app.pms.main.core;

import android.content.Context;
import android.text.TextUtils;

import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.library.uc.user.model.UserModel;
import com.einyun.app.pms.main.core.model.HomeModel;

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
public class MainServiceManager {
    private static MainServiceManager instance;
    private HomeModel currentHomeModel;
    private Context mContext;

    private MainServiceManager(){
        mContext= BasicApplication.getInstance();
    }

    public static MainServiceManager getInstance() {
        if(instance==null){
            synchronized (MainServiceManager.class){
                if(instance==null){
                    instance=new MainServiceManager();
                }
            }
        }
        return instance;
    }

    public  HomeModel getCurrentHomeModel() {
        return currentHomeModel;
    }


//    public void saveUserModel(@Nonnull UserModel model){
//        if(model!=null){
//            currentUserModel=model;
//            saveUserId(currentUserModel.getUserId());
//            saveToken(currentUserModel.getToken());
//        }
//
//    }
//
//    /**
//     * 获取本地缓存userId
//     * @return
//     */
//    public String getUserId(){
//        if(TextUtils.isEmpty(this.userId)){
//            userId=SPUtils.get(mContext,Constants.SP_KEY_USERID,"").toString();
//        }
//        return userId;
//    }
//
//    /**
//     * 保存UserId到本地
//     * @param userId
//     */
//    public void saveUserId(@Nonnull String userId){
//        this.userId=userId;
//        SPUtils.put(getContext(),Constants.SP_KEY_USERID,userId);
//    }

    public Context getContext(){
        return mContext;
    }
}
