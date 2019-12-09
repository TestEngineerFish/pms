package com.einyun.app.pms.approval.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.einyun.app.pms.approval.R;


/**
 * Created by Administrator on 2017/4/24.
 */

public class DoubleChoiceDialog extends Dialog {

    TextView btnChoiceLeft;

    TextView btnChoiceRight;

    TextView tvTitle;

    TextView tvContent;

    String mTitle;
    String mContent;
    String mChoiceBtn1;
    String mChoiceBtn2;

    View.OnClickListener left;
    View.OnClickListener right;

    public DoubleChoiceDialog(@NonNull Context context, @NonNull String mTitle, @NonNull String mContent, @NonNull String mChoiceBtn1, @NonNull String mChoiceBtn2, @NonNull View.OnClickListener left, @NonNull View.OnClickListener right) {
        super(context,R.style.MyDialogStyleBottom);
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mChoiceBtn1 = mChoiceBtn1;
        this.mChoiceBtn2 = mChoiceBtn2;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_double_choice);
        btnChoiceLeft = (TextView) findViewById(R.id.btn_double_choice_left);
        btnChoiceRight = (TextView) findViewById(R.id.btn_double_choice_right);
        tvTitle = (TextView) findViewById(R.id.tv_double_choice_dialog_title);
        tvContent = (TextView) findViewById(R.id.tv_double_choice_dialog_content);
        btnChoiceLeft.setText(mChoiceBtn1);
        btnChoiceRight.setText(mChoiceBtn2);
        tvTitle.setText(mTitle);
        tvContent.setText(mContent);
        btnChoiceLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (left != null)
                    left.onClick(v);
                DoubleChoiceDialog.this.dismiss();
            }
        });
        btnChoiceRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (right != null)
                    right.onClick(v);
                DoubleChoiceDialog.this.dismiss();
            }
        });
        setCanceledOnTouchOutside(false);
        // 设置点击屏幕Dialog不消失
        //   getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        //  getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

}

