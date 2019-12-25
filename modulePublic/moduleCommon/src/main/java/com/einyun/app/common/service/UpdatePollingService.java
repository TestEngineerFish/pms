package com.einyun.app.common.service;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdatePollingService extends IntentService {
    private int status = 0;
    private int times = 0;
    private boolean whileSign = false;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            //发送广播
            Intent intent = new Intent();
            intent.putExtra("status", msg.what);
            intent.setAction("com.tarena.intentwechatdemo.InternetReceiver");
            sendBroadcast(intent);
            super.handleMessage(msg);

        }
    };


    public UpdatePollingService() {
        /**
         * 这里只需要传入一个字符串就可以了
         */
        super("MyIntentService");

    }

    /**
     * 必须实现的抽象方法，我们的业务逻辑就是在这个方法里面去实现的 在这个方法里实现业务逻辑，我们就不用去关心ANR的问题
     */
    @Override
    protected void onHandleIntent(Intent intent) {


        if (times == 5) {
        } else {
            if (whileSign == false) {
                handler.sendEmptyMessage(status);
                whileSign = true;
            }

        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}