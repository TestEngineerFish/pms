package com.einyun.app.common.repository;

import com.einyun.app.common.application.ThrowableParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.einyun.app.base.event.ErrorMessage;
import com.einyun.app.base.http.BaseResponse;

import retrofit2.HttpException;

public class CommonRepository implements IRepository {

    public CommonRepository() {
    }

//    protected void onFailed(Throwable throwable, int tag) {
//        ThrowableParser.onFailed(throwable,tag);
//    }


//    public void onError(Exception e, String code, int tag) {
//        ErrorMessage errorMessage = new ErrorMessage();
//        errorMessage.setTag(tag);
//        errorMessage.setCode(code);
//        errorMessage.setException(e);
//        LiveEventBus.get().with(ErrorMessage.KEY_LIVE_DATA_BUS_ERROR_MESSAGE,ErrorMessage.class).post(errorMessage);
//    }


}
