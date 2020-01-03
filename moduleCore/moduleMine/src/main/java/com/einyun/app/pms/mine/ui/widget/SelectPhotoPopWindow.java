package com.einyun.app.pms.mine.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.einyun.app.pms.mine.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SelectPhotoPopWindow extends PopupWindow {
    private static final String TAG = "CustomPopWindow";
    private final View view;
    private Activity context;
    private OnItemClickListener mListener;

    public SelectPhotoPopWindow(Activity context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.popwindow_select_pic_layout, null);//alt+ctrl+f
        this.context = context;
        initView();
        initPopWindow();

        this.setOnDismissListener(new OnDismissListener(){

            @Override
            public void onDismiss() {

            }
        });
}


    private void initView() {

        TextView cancel = view.findViewById(R.id.tv_cancle);
        TextView tv_take_pic = view.findViewById(R.id.tv_take_pic);
        TextView tv_photo_album = view.findViewById(R.id.tv_photo_album);
        tv_take_pic.setOnClickListener(view1 -> {
            dismiss();
            mListener.takePicClick();
        });

        tv_photo_album.setOnClickListener(view1 -> {
            mListener.photoAlbumClick();
            dismiss();
        });
        cancel.setOnClickListener(view1 -> {dismiss();});
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
        void takePicClick();
        void photoAlbumClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
