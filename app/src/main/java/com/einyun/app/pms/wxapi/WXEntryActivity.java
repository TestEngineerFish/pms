package com.einyun.app.pms.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.service.RouterUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.umeng.socialize.weixin.view.WXCallbackActivity;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (CommonApplication.getInstance().getWeiXinApi().handleIntent(getIntent(), this)) {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        CommonApplication.getInstance().getWeiXinApi().handleIntent(intent, this);
    }

    @Override
    public void onResp(BaseResp baseResp) {

    }

    /**
     * 接收从小程序跳转到app的数据，并进行处理
     *
     * @param showReq
     */
    private void goToShowMsg(ShowMessageFromWX.Req showReq) {
        WXMediaMessage wxMsg = showReq.message;
       /* Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();*/
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }
}

