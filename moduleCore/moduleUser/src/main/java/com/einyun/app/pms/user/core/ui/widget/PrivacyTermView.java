package com.einyun.app.pms.user.core.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.pms.user.R;
import com.einyun.app.pms.user.core.Constants;


/***
 * 隐私页自定义View
 */
public class PrivacyTermView extends Dialog implements View.OnClickListener {
    TextView tvTrem;
    Button btnReject;
    Button btnAgree;
    private Context context;

    public PrivacyTermView(@NonNull Context context) {
        super(context, R.style.AlertDialogStyle);
        this.context = context;
    }

    public PrivacyTermView(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected PrivacyTermView(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_privacy_term, null);
        setContentView(view);
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        refreshView();
        //初始化界面控件的事件
        initEvent();
    }

    private void initView() {
        tvTrem = findViewById(R.id.tv_privacy_term);
        btnReject = findViewById(R.id.btn_reject);
        btnAgree = findViewById(R.id.btn_agree);
    }

    private void refreshView() {

    }

    private void initEvent() {
        tvTrem.setOnClickListener(this);
        btnReject.setOnClickListener(this);
        btnAgree.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_reject) {
            if (onClickBottomListener != null) {
                onClickBottomListener.onNegtiveClick();
            }
        } else if (v.getId() == R.id.btn_agree) {
            if (onClickBottomListener != null) {
                onClickBottomListener.onPositiveClick();
            }
        } else if (v.getId() == R.id.tv_privacy_term) {
            ARouter.getInstance()
                    .build(RouterUtils.ACTIVITY_X5_WEBVIEW)
                    .withString(RouteKey.KEY_WEB_URL, Constants.PRIVACY_DETAIL_URL)
                    .withInt(RouteKey.KEY_WEB_TITLE, R.string.privacy_title_text)
                    .navigation();
        }
    }

    /**
     * 设置确定取消按钮的回调
     */
    public OnClickBottomListener onClickBottomListener;

    public PrivacyTermView setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }

    public interface OnClickBottomListener {
        /**
         * 点击确定按钮事件
         */
        public void onPositiveClick();

        /**
         * 点击取消按钮事件
         */
        public void onNegtiveClick();
    }
}
