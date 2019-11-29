package com.einyun.app.common.ui.widget;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.einyun.app.common.R;

public   class OgSelectView extends PopupWindow {
    private Context context;
    private View parent;
    public OgSelectView(Context context,View parent){
        this.context=context;
        this.parent=parent;
    }

    public OgSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.og_select,null);
    }

    private void initView(){


    }
}
