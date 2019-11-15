package com.einyun.app.common.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.ui.widget
 * @ClassName: PieChartListView
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/11/4 0004 上午 11:31
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/4 0004 上午 11:31
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PieChartListView extends ListView {
    public PieChartListView(Context context) {
        super(context);
    }

    public PieChartListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChartListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
