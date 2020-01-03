package com.einyun.app.common.utils;

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
}