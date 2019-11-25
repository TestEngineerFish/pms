package com.einyun.app.pms.main.core.ui.widget;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.main.ui.widget
 * @ClassName: PieChartView
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/11/4 0004 上午 11:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/4 0004 上午 11:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.einyun.app.common.ui.widget.PieChartListView;
import com.einyun.app.library.dashboard.model.WorkOrder;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.core.ui.adapter.PieChartAdapter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 饼状图
 */
public class PieChartView extends LinearLayout implements OnChartValueSelectedListener {

    private Context mContext;
    private PieChart pieChart;
    public static PieChartListView pieList;
    private LinearLayout llListwsj;

    //设置各区域颜色
    public final int[] PIE_COLORS = {Color.rgb(38, 138, 252), Color.rgb(78, 209, 225),
            Color.rgb(96, 190, 104), Color.rgb(255, 89, 108), Color.rgb(148, 68, 244),
            Color.rgb(243, 204, 75), Color.rgb(237, 189, 189), Color.rgb(199, 18, 242)
    };
    public static String[] PIE_COLORS2 = {"#268AFC", "#4ED1E1", "#60BE68", "#FF596C", "#9444F4", "#F3CC4B", "#EDBDBD", "#C712F2"
    };

    public PieChartView(Context context) {
        super(context);
        initView(context);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.widget_chart_view, this);
        if (!isInEditMode()) {
//            pieChart = findViewById(R.id.chart_gdcl);
//            pieList = findViewById(R.id.pie_list);
            llListwsj = findViewById(R.id.ll_listwsj);
            pieChart.setUsePercentValues(false); //设置值是否用显示百分数，默认为假
            pieChart.getDescription().setEnabled(false);//设置描述
            pieChart.setExtraOffsets(0, 0, 0, 0); //设置饼状图距离上下左右的偏移量

            pieChart.setDragDecelerationFrictionCoef(0.95f);//设置阻尼系数，范围在[0,1]之间，越小饼状图转动越困难
            //设置中间文件
//            pieChart.setCenterText(generateCenterSpannableText());

            pieChart.setDrawSliceText(false);//影藏饼状图文字，只显示百分比
            pieChart.setDrawHoleEnabled(false);//是否绘制饼状图中间的圆
            pieChart.setHoleColor(Color.WHITE);//饼状图中间的圆的绘制颜色
            pieChart.setTransparentCircleColor(Color.WHITE); //设置圆环的颜色
            pieChart.setTransparentCircleAlpha(110);//设置圆环的透明度[0255]
            pieChart.setHoleRadius(58f); //饼状图中间的圆的半径大小
            pieChart.setTransparentCircleRadius(61f);//设置圆环的半径值
            pieChart.setDrawCenterText(false);//是否绘制中间的文字
            pieChart.setRotationAngle(-90); //设置饼状图旋转的角度
            //触摸旋转
            pieChart.setRotationEnabled(false); //设置饼状图是否可以旋转（默认为真）
            pieChart.setHighlightPerTapEnabled(false); //设置旋转的时候点中的标签是否高亮
            //变化监听
            pieChart.setOnChartValueSelectedListener(this);
        }
    }

    /**
     * 显示饼状图
     */
    public void showPieChart(ArrayList<WorkOrder> workOrderBeans, int Total) {
        if (workOrderBeans == null || Total == 0) {
            pieChart.setVisibility(GONE);
            pieList.setVisibility(GONE);
            llListwsj.setVisibility(VISIBLE);
            return;
        }

        //模拟数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        for (int i = 0; i < workOrderBeans.size(); i++) {
            entries.add(new PieEntry(workOrderBeans.get(i).getCount(), workOrderBeans.get(i).getName()));
        }

        PieChartAdapter adapter = new PieChartAdapter(mContext, workOrderBeans);
        pieList.setAdapter(adapter);

        //设置数据
        setData(entries);

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = pieChart.getLegend();//设置成比例图
        l.setEnabled(false);//图例是否显示
//        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);

        l.setWordWrapEnabled(true);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);//设置选项卡之间ý轴方向上的空白间距值
        l.setYOffset(0f);

        // 输入标签样式
        pieChart.setDrawEntryLabels(false); //设置是否绘制标签
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(12f);

    }

//    //设置中间文字
//    private SpannableString generateCenterSpannableText() {
//        //原文：MPAndroidChart\ndeveloped by Philipp Jahoda
//        SpannableString s = new SpannableString("刘某人程序员\n我仿佛听到有人说我帅");
//        //s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
//        //s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        // s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
//        //s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
//        // s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//        // s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
//        return s;
//    }

    //设置数据
    private void setData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "");
//        dataSet.setSliceSpace(3f); //PieDataSet设置饼块间隔
//        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(PIE_COLORS);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new TestDefaultValueFormatter());
//        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
        pieChart.highlightValues(null);
        //刷新
        pieChart.invalidate();
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }


    private class TestDefaultValueFormatter implements IValueFormatter {
        private DecimalFormat mFormat;

        public TestDefaultValueFormatter() {
            mFormat = new DecimalFormat("###");//十进制格式
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return "";
        }
    }
}