package com.einyun.app.common.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class InScrollTextView extends TextView {

    private boolean calculatedLines = false;

    public InScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!calculatedLines) {
            calculateLines();
            calculatedLines = true;
        }

        super.onDraw(canvas);
    }

    private void calculateLines() {
        int mHeight = getMeasuredHeight();
        int lHeight = getLineHeight();
        int lines = mHeight / lHeight;
        setLines(lines);
    }
}
