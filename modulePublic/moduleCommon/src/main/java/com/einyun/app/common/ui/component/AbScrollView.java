package com.einyun.app.common.ui.component;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.ui.component
 * @ClassName: AbScrollView
 * @Description: 具有反弹效果的ScrollView，能够兼容横向的滑动
 * @Author: chumingjun
 * @CreateDate: 2019/11/1 0001 上午 10:42
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/1 0001 上午 10:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AbScrollView extends NestedScrollView {

    private float xDistance, yDistance, xLast, yLast;

    public AbScrollView(Context context) {
        super(context);
    }

    public AbScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                if (xDistance > yDistance) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }
}