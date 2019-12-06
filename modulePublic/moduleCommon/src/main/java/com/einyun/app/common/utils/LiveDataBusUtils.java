package com.einyun.app.common.utils;

import com.einyun.app.common.constants.LiveDataBusKey;
import com.jeremyliao.liveeventbus.LiveEventBus;

public class LiveDataBusUtils {
    public static void postStopRefresh(){
        LiveEventBus.get(LiveDataBusKey.STOP_REFRESH,Boolean.class).post(false);
    }
}
