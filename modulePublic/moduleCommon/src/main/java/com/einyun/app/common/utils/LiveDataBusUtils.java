package com.einyun.app.common.utils;

import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse;
import com.jeremyliao.liveeventbus.LiveEventBus;

public class LiveDataBusUtils {
    public static void postStopRefresh() {
        LiveEventBus.get(LiveDataBusKey.STOP_REFRESH, Boolean.class).post(false);
    }

    public static void postResendOrderUser(GetMappingByUserIdsResponse model) {
        LiveEventBus.get(LiveDataBusKey.POST_RESEND_ORDER_USER).post(model);
    }

    public static void search(String tag) {
        LiveEventBus.get(LiveDataBusKey.POST_PLAN_SEARCH).post(tag);
    }

    public static void postPatrolClosedRefresh(){
        LiveEventBus.get(LiveDataBusKey.POST_PATROL_CLOSED_REFRESH)
                .post(true);
    }
    /**
     * 通用接收liveBusData
     * 空页面使用
     */
    public static void getLiveBusData(View view, String type,LifecycleOwner owner){
        LiveEventBus.get(type,Boolean.class).observe(owner, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    view.setVisibility(View.VISIBLE);
                }else {
                    view.setVisibility(View.GONE);

                }
            }
        });
    }
    /**
     * 通用发送liveBusData
     * 页面使用
     */
    public static void postLiveBusData(String type,int total){
        if (total==0) {
            LiveEventBus.get(type,Boolean.class).post(true);
        }else {
            LiveEventBus.get(type,Boolean.class).post(false);

        }
    }

}
