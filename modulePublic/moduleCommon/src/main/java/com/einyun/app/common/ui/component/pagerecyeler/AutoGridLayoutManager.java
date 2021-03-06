package com.einyun.app.common.ui.component.pagerecyeler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.ui.component.PageRecyeler
 * @ClassName: AutoGridLayoutManager
 * @Description: 重写GridLayoutManager，在{@link RecyclerView#setLayoutManager(RecyclerView.LayoutManager)}使用
 *  * 此类替换{@link GridLayoutManager}，使{@link RecyclerView}能够自使用内容的高度
 * @Author: chumingjun
 * @CreateDate: 2019/11/1 0001 上午 11:24
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/1 0001 上午 11:24
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AutoGridLayoutManager extends GridLayoutManager {

    private int measuredWidth = 0;
    private int measuredHeight = 0;
    // 总页数
    private int totalPages;

    public AutoGridLayoutManager(Context context, AttributeSet attrs,
                                 int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public AutoGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public AutoGridLayoutManager(Context context, int spanCount,
                                 int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler,
                          RecyclerView.State state, int widthSpec, int heightSpec) {
        if (measuredHeight <= 0) {
            View view = recycler.getViewForPosition(0);
            if (view != null) {
                measureChild(view, widthSpec, heightSpec);
                measuredWidth = View.MeasureSpec.getSize(widthSpec);
                measuredHeight = view.getMeasuredHeight() * getSpanCount();
            }
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {

        //如果页面总数大于 1 则可以滑动
        if (totalPages > 1) {
            return super.scrollHorizontallyBy(dx, recycler, state);
        } else {
            return 0;
        }
    }
}
