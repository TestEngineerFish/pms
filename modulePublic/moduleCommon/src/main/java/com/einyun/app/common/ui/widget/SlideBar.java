package com.einyun.app.common.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class SlideBar extends View {

    // 触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    // 26个字母
    public ArrayList<String> A_Z;
    private int choose = -1;// 选中
    private Paint paint = new Paint();

    private TextView mTextDialog;

    /**
     * 为SideBar设置显示字母的TextView
     * @param mTextDialog
     */
    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }


    public SlideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SlideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideBar(Context context) {
        super(context);
    }

    public void UpdateLetters(ArrayList<String> A_Z){

        this.A_Z = A_Z;

        postInvalidate();
    }
    /**
     * 重写这个方法
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(A_Z == null){
            return ;
        }
        // 获取焦点改变背景颜色.
        int height = getHeight();// 获取对应高度
        int width = getWidth(); // 获取对应宽度
        int singleHeight = 40;//height / A_Z.size()-2;// 获取每一个字母的高度  (这里-2仅仅是为了好看而已)

        int totalHeight = singleHeight * A_Z.size();
        int topOffset = (height - totalHeight)/2;

        for (int i = 0; i < A_Z.size(); i++) {
            paint.setColor(Color.rgb(33, 65, 98));  //设置字体颜色
            paint.setTypeface(Typeface.DEFAULT_BOLD);  //设置字体
            paint.setAntiAlias(true);  //设置抗锯齿
            paint.setTextSize(30);  //设置字母字体大小
            // 选中的状态
            if (i == choose) {
                paint.setColor(Color.parseColor("#3399ff"));  //选中的字母改变颜色
                paint.setFakeBoldText(true);  //设置字体为粗体
            }
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(A_Z.get(i)) / 2;
            float yPos = singleHeight * i + singleHeight + topOffset;
            canvas.drawText(A_Z.get(i), xPos, yPos, paint);  //绘制所有的字母
            paint.reset();// 重置画笔
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;

        int singleHeight = 40;
        int totalHeight = singleHeight * A_Z.size();
        int topOffset = (getHeight() - totalHeight)/2;

        y -= topOffset;

        final int c = (int) (y / totalHeight * A_Z.size());// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose = -1;//
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;

            default:
//                setBackgroundResource(R.drawable.sidebar_background);
                if (oldChoose != c) {  //判断选中字母是否发生改变
                    if (c >= 0 && c < A_Z.size()) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(A_Z.get(c));
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(A_Z.get(c));
                            mTextDialog.setVisibility(View.VISIBLE);
                        }

                        choose = c;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 接口
     *
     * @author coder
     *
     */
    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(String s);
    }

}
