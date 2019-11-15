package com.einyun.app.common.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.einyun.app.common.R;

import java.lang.ref.WeakReference;

public class CustomConfirmDialog extends Dialog implements DialogInterface.OnCancelListener {

    private CustomConfirmInterface      m_interface;

    private WeakReference<Context> mContext;
    private volatile static CustomConfirmDialog sDialog;

    private CustomConfirmDialog(Context context, CharSequence message, String cancelText, String confirmText) {
        super(context, R.style.CustomProgressDialog);

        mContext = new WeakReference<>(context);

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom_confirm, null);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
        if (!TextUtils.isEmpty(message)) {
            tvMessage.setText(message);
        }

        TextView textViewCancel = view.findViewById(R.id.cancelButton);
        textViewCancel.setText(cancelText);
        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m_interface!= null){
                    m_interface.confirmCancalButtonClick();
                }

                dismiss();
            }
        });

        Button sureButton = view.findViewById(R.id.sureButton);
        sureButton.setText(confirmText);
        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m_interface!= null){
                    m_interface.confirmSureButtonClick();
                }

                dismiss();
            }
        });

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(view, lp);

//        setOnCancelListener(this);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        // 点手机返回键等触发Dialog消失，应该取消正在进行的网络请求等
        Context context = mContext.get();
    }

    public static synchronized void showLoading(Context context, CustomConfirmInterface custInterface) {
        showLoading(context, custInterface, "loading...");
    }

    public static synchronized void showLoading(Context context, CustomConfirmInterface custInterface, CharSequence message) {
        showLoading(context, custInterface, message, "取消",  "确认",  false);
    }

    public static synchronized void showLoading(Context context, CustomConfirmInterface custInterface, CharSequence message, String cancelText, String confirmText) {
        showLoading(context, custInterface, message, cancelText, confirmText, false);
    }

    public static synchronized void showLoading(Context context, CustomConfirmInterface custInterface, CharSequence message, String cancelText, String confirmText,  boolean cancelable) {
        if (sDialog != null && sDialog.isShowing()) {
            sDialog.dismiss();
        }

        if (context == null) {
            return;
        }
        sDialog = new CustomConfirmDialog(context, message, cancelText, confirmText);
        sDialog.setCancelable(false);
        sDialog.m_interface = custInterface;

        if (sDialog != null && !sDialog.isShowing()) {
            if(context instanceof Activity && !((Activity) context).isFinishing()) {
                sDialog.show();
            }
            else{
                sDialog.show();
            }
        }
    }

    public static synchronized void stopLoading() {
        if (sDialog != null && sDialog.isShowing()) {
            sDialog.dismiss();
        }
        sDialog = null;
    }

    public interface CustomConfirmInterface{
        void confirmSureButtonClick();
        void confirmCancalButtonClick();
    }


}
