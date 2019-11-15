package com.einyun.app.base.http;


import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Description:请求参数基类(JSON格式上传)
 *
 * @Author: chumingjun
 * @Email: 15951837502@163.com
 * Time: 2019/08/27
 * */
public class BaseBodyRequest<T> {

    public RequestBody requestBody;

    public T baseBodyRequestBean;

    public BaseBodyRequest(T baseBodyRequestBean) {
        super();

        this.baseBodyRequestBean = baseBodyRequestBean;
    }

    public RequestBody getRequestBody() {
        return requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(baseBodyRequestBean));
    }
}
