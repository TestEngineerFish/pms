package com.einyun.app.pms.toll.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.einyun.app.pms.toll.R;


public class InputMoneyPopWindow extends PopupWindow {

    private  View view;
    private Activity context;
    private OnItemClickListener mListener;
    int mPosition;

    public InputMoneyPopWindow(Activity context, int position) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.input_money_popwindow, null);//alt+ctrl+f
        this.context = context;

        mPosition=position;
        initView();
        initPopWindow();
//        getData(1,10);
        this.setOnDismissListener(new OnDismissListener(){

            @Override
            public void onDismiss() {
//
            }
        });
}



    private void initView() {


        TextView ok = view.findViewById(R.id.ok);
        EditText editText = view.findViewById(R.id.et_text);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(view1 -> {dismiss();});
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onData( editText.getText().toString(),mPosition);
//                dismiss();
            }
        });
        editText.setFocusable(true);

        editText.setFocusableInTouchMode(true);

        editText.requestFocus();
        //数量监听
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String editStr = editable.toString().trim();

                int posDot = editStr.indexOf(".");
                //不允许输入3位小数,超过三位就删掉
                if (posDot < 0) {
                    return;
                }
                if (editStr.length() - posDot - 1 > 2) {
                    editable.delete(posDot + 3, posDot + 4);
                } else {
                    //TODO...在这里写逻辑
                }
            }
        });

    }


    private void initPopWindow() {
        this.setContentView(view);
        // 设置弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出窗体可点击()
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00FFFFFF);
        //设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        backgroundAlpha(context, 1f);//0.0-1.0
        setOnDismissListener(new OnDismissListener(){
            @Override
            public void onDismiss() {
               backgroundAlpha(context, 1f);
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度(值越大,透明度越高)
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }


    public interface OnItemClickListener {
        void onData(String cate, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
