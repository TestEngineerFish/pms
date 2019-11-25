package com.einyun.app.pms.user.core.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import com.einyun.app.base.RVBindingAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.library.uc.user.model.UserModel;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.yykj.app.pms.user.R;


public class NewDropEditText extends AppCompatEditText implements ItemClickListener<UserModel>,PopupWindow.OnDismissListener {

    private Drawable mDrawable; // 显示的图
    private PopupWindow mPopupWindow; // 点击图片弹出的popWindow对象
    private RecyclerView mPopListView; // popWindow的布局
    private int mDropDrawableResId; // 下拉图标

    public NewDropEditText(Context context) {
        this(context, null);
    }

    public NewDropEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NewDropEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @SuppressLint("ResourceAsColor")
    private void init(Context context) {
        mPopListView = new RecyclerView(context);
        mDropDrawableResId = R.mipmap.arrow_down; // 设置下拉图标
        showDropDrawable(); // 默认显示下拉图标
    }

    /**
     * 我们无法直接给EditText设置点击事件，只能通过按下的位置来模拟点击事件
     * 当我们按下的位置在图标包括图标到控件右边的间距范围内均算有效
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                int start = getWidth() - getTotalPaddingRight() + getPaddingRight() - 20; // 起始位置
                int end = getWidth(); // 结束位置
                boolean available = (event.getX() > start) && (event.getX() < end);
                if (available) {
                    closeSoftInput();
                    showPopWindow();
                    return true;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mPopupWindow = new PopupWindow(mPopListView, getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE)); // 设置popWindow背景颜色
            mPopupWindow.setFocusable(true); // 让popWindow获取焦点
            mPopupWindow.setOnDismissListener(this);
//            mPopupWindow.setElevation(10f);
        }
    }

    private void showPopWindow() {
        mPopupWindow.showAsDropDown(this, 0, 0);
        showRiseDrawable();
    }

    private void showDropDrawable() {
        mDrawable = getResources().getDrawable(mDropDrawableResId);
        mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth() - 50, mDrawable.getIntrinsicHeight() - 26);
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], mDrawable, getCompoundDrawables()[3]);
    }

    private void showRiseDrawable() {
        mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth() - 50, mDrawable.getIntrinsicHeight() - 26);
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], mDrawable, getCompoundDrawables()[3]);
    }

    public void setAdapter(RVBindingAdapter adapter) {
        mPopListView.setAdapter(adapter);
        adapter.setOnItemClick(this);
    }

    private void closeSoftInput() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
    }

    @Override
    public void onDismiss() {
        showDropDrawable(); // 当popWindow消失时显示下拉图标
    }

    @Override
    public void onItemClicked(View veiw, UserModel data) {
        this.setText(data.getUsername());
        mPopupWindow.dismiss();
    }
}