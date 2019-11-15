package com.einyun.app.base.http;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Gson请求响应装换工厂
 * @Author: chumingjun
 * @Email: 15951837502@163.com
 * Time: 2019/08/27
 * */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;


    public GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
//        String response = value.string();
//        //先将返回的json数据解析到Response中，如果code==200，则解析到我们的实体基类中，否则抛异常
////        BaseResponse baseResponse = gson.fromJson(response, BaseResponse.class);
////        if (HttpResponseConstant.RESPONSE_OK.equals(baseResponse.getCode())){
//        //200的时候就直接解析，不可能出现解析异常。因为我们实体基类中传入的泛型，就是数据成功时候的格式
//        return gson.fromJson(response, type);

        try {
            JsonReader jsonReader = gson.newJsonReader(value.charStream());
            return adapter.read(jsonReader);
        } catch (Exception e) {
            throw new RuntimeException("服务器返回数据解析出现问题!");
        } finally {
            value.close();
        }
    }
}
