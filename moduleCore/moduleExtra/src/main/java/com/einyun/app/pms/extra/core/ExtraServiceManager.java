package com.einyun.app.pms.extra.core;

import android.content.Context;
import android.text.TextUtils;

import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.library.uc.user.model.UserModel;

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
public class ExtraServiceManager {
    private static ExtraServiceManager instance;
    private Context mContext;

    private ExtraServiceManager(){
        mContext= BasicApplication.getInstance();
    }

    public static ExtraServiceManager getInstance() {
        if(instance==null){
            synchronized (ExtraServiceManager.class){
                if(instance==null){
                    instance=new ExtraServiceManager();
                }
            }
        }
        return instance;
    }

}
