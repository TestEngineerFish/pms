package com.einyun.app.common.service;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.common.Constants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.constants.SPKey;
import com.einyun.app.common.net.CommonHttpService;
import com.googlecode.eyesfree.utils.LogUtils;

// 在跳转过程中处理登陆事件，这样就不需要在目标页重复做登陆检查
// 拦截器会在跳转之间执行，多个拦截器会按优先级顺序依次执行
@Interceptor(name = "login", priority = 6)
public class LoginInterceptorImpl implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String path = postcard.getPath();
        Log.e("InterceptorImpl path->", path);
        if (RouterUtils.ACTIVITY_REPAIRS_PAGING.equals(path)) {
            String token = (String) SPUtils.get(BasicApplication.getInstance(), SPKey.SP_KEY_TOKEN, "");
            if (StringUtil.isNullStr(token)) {
                CommonHttpService.getInstance().authorToken(token);
                // 如果已经登录不拦截
                callback.onContinue(postcard);
            } else {
                callback.onInterrupt(null);
            }
        }else{
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {//此方法只会走一次
        Log.e("InterceptorImpl ", "路由登录拦截器初始化成功");
    }
}