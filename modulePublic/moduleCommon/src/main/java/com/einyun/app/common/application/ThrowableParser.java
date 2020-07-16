package com.einyun.app.common.application;

import android.net.ParseException;

import com.einyun.app.base.http.BaseResponse;


import com.einyun.app.common.R;
import com.einyun.app.common.utils.ToastUtil;
import com.einyun.app.library.core.net.EinyunHttpException;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.application
 * @ClassName: ThrowableParser
 * @Description: 错误解析
 * @Author: chumingjun
 * @CreateDate: 2019/09/11 19:15
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/11 19:15
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ThrowableParser {
    private static final String TAG = "ThrowableParser";

    public static void onFailed(Throwable throwable) {
        throwable.printStackTrace();
        Logger.e(TAG,throwable.getMessage());
        if (throwable instanceof HttpException) {
            HttpException exception = (HttpException) throwable;
            try {
                String body = exception.response().errorBody().string();
                BaseResponse<Object> resp = new Gson().fromJson(body, new TypeToken<BaseResponse<Object>>() {
                }.getType());
                ToastUtil.show(CommonApplication.getInstance(),resp.getMsg());
                if (resp.getCode() == "401") {
                    //做登出操作

                    //TOBEDONE
                    return;
                }
//                onError(exception, resp.getCode(), tag);
            } catch (Exception e) {
//                onError(e, "5001", -1);
            }
        }else if(throwable instanceof EinyunHttpException){
            //API业务错误
            EinyunHttpException exception= (EinyunHttpException) throwable;
            if (exception.getResponse().getMsg().contains("userAccount")) {
                ToastUtil.show(CommonApplication.getInstance(), "沟通人员不能为自己");
            }else if (exception.getResponse().getCode().equals("34516")){
                ToastUtil.show(CommonApplication.getInstance(), "任务不存在，可能已经被处理了");
            } else {
                ToastUtil.show(CommonApplication.getInstance(), exception.getResponse().getMsg());
            }
        }
        else if (throwable instanceof JsonParseException
                || throwable instanceof JSONException
                || throwable instanceof ParseException) {
            //解析错误
        } else if (throwable instanceof ConnectException) {
            //网络错误
            ToastUtil.show(CommonApplication.getInstance(), R.string.toast_error_http);
        } else if (throwable instanceof UnknownHostException || throwable instanceof SocketTimeoutException) {
            //连接错误
            ToastUtil.show(CommonApplication.getInstance(), R.string.toast_error_net);
        } else {

        }
    }
}
