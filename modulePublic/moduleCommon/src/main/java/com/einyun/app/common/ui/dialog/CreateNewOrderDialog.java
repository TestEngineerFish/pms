package com.einyun.app.common.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.einyun.app.common.R;


public class CreateNewOrderDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private Display display;
    private TextView tvSendOrder;
    private TextView tvUnOrder;
    private TextView cancel;

    public CreateNewOrderDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public CreateNewOrderDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_create_order, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = view.findViewById(R.id.lLayout_bg);
        tvSendOrder = view.findViewById(R.id.tv_send_order);
        tvUnOrder = view.findViewById(R.id.tv_un_order);
        cancel = view.findViewById(R.id.tv_cancel);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.75), LayoutParams.WRAP_CONTENT));

        return this;
    }


    public CreateNewOrderDialog setCreateSendOrder(OnClickListener listener) {
        tvSendOrder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dismiss();
            }
        });
        return this;
    }

    public CreateNewOrderDialog setCreateUnOrder(OnClickListener listener) {
        tvUnOrder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dismiss();
            }
        });
        return this;
    }

    public CreateNewOrderDialog setCancel(OnClickListener listener){
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dismiss();
            }
        });
        return this;
    }

    public boolean isShowing() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void show() {
        if (dialog != null) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.cancel();
            }
        }
    }


}
