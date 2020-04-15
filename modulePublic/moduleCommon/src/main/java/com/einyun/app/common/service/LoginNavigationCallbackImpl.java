package com.einyun.app.common.service;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.common.constants.RouteKey;
import com.googlecode.eyesfree.utils.LogUtils;

public class LoginNavigationCallbackImpl implements NavigationCallback {
    @Override //找到了
    public void onFound(Postcard postcard) {

    }

    @Override //找不到了
    public void onLost(Postcard postcard) {

    }

    @Override    //跳转成功了
    public void onArrival(Postcard postcard) {

    }

    @Override
    public void onInterrupt(Postcard postcard) {
        String path = postcard.getPath();
        Log.e("callback path->",path);
        Bundle bundle = postcard.getExtras();
        // 被登录拦截了下来了 
        // 需要调转到登录页面，把参数跟被登录拦截下来的路径传递给登录页面，登录成功后再进行跳转被拦截的页面
        ARouter.getInstance().build(RouterUtils.ACTIVITY_USER_LOGIN)
                .with(bundle)
                .withString(RouteKey.KEY_PATH, path)
                .navigation();
    }
}