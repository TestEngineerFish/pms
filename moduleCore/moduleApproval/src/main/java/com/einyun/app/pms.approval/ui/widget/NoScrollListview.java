package com.einyun.app.pms.approval.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by shenmingzhao
 */

public class NoScrollListview extends ListView {
    public NoScrollListview(Context context) {
        super(context);
    }

    public NoScrollListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
