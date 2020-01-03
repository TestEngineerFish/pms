package com.einyun.app.pms.main.api;


import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.main.IMainModuleService;
import com.einyun.app.common.service.user.IUserModuleService;


/**
 * Description:UserServiceImpl
 *  用户模块对外暴露接口实现
 * @Author: chumingjun
 * @Email: 15951837502@163.com
 * Time: 2019/08/27
 * */
@Route(path = RouterUtils.SERVICE_MAIN)
public class MainServiceImpl implements IMainModuleService {

    private Context mContext;

    @Override
    public void init(Context context) {
        this.mContext=context;
    }
}
