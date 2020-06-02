package com.einyun.app.pms.user.api;


import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.pms.user.core.UserServiceManager;

import java.util.List;


/**
 * Description:UserServiceImpl
 * 用户模块对外暴露接口实现
 *
 * @Author: chumingjun
 * @Email: 15951837502@163.com
 * Time: 2019/08/27
 */
@Route(path = RouterUtils.SERVICE_USER)
public class UserServiceImpl implements IUserModuleService {

    private Context mContext;

    @Override
    public String getUserId() {
        return UserServiceManager.getInstance().getUserId();
    }

    @Override
    public String getAccount() {
        return UserServiceManager.getInstance().getCurrentUserModel().getAccount();
    }

    @Override
    public String getUserName() {
        return UserServiceManager.getInstance().getCurrentUserModel().getAccount();
    }

    @Override
    public String getRealName() {
        return UserServiceManager.getInstance().getCurrentUserModel().getUsername();
    }

    @Override
    public void saveDivideCodes(List<String> divideCodes) {
        UserServiceManager.getInstance().saveDivideCodes(divideCodes);
    }

    @Override
    public List<String> getDivideCodes() {
        return UserServiceManager.getInstance().getDivideCodes();
    }

    public String getToken() {
        return UserServiceManager.getInstance().getToken();
    }

    @Override
    public void init(Context context) {
        this.mContext = context;
    }
}
