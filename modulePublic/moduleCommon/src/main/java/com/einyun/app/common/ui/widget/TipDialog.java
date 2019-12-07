package com.einyun.app.common.ui.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.einyun.app.common.R;
import com.einyun.app.common.databinding.TipDialogBinding;


public class TipDialog {
    private Context context;
    private String tip;
    private AlertDialog.Builder alterDiaglog;
    private View view;
    TipDialogBinding binding;
    public TipDialog(Context context, String tip) {
        this.context = context;
        this.tip = tip;
        initView();
    }

    private void initView() {
        alterDiaglog = new AlertDialog.Builder(context);
        view = LayoutInflater.from(context).inflate(R.layout.tip_dialog, null);
        alterDiaglog.setView(view);
        alterDiaglog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                ((ViewGroup)view.getParent()).removeView(view);
            }
        });
         binding= DataBindingUtil.bind(view);
        binding.tipDialogContent.setText(tip);
    }

    public void show() {
        alterDiaglog.show();
    }
}
