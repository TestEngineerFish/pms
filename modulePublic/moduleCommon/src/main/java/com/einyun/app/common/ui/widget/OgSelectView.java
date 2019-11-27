package com.einyun.app.common.ui.widget;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.einyun.app.common.R;

public   class OgSelectView extends LinearLayout {

    public OgSelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.og_select, this);
    }

}
