package com.einyun.app.pms.main.core.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.einyun.app.common.manager.BarChartManager;
import com.einyun.app.library.uc.usercenter.model.Statisfaction;
import com.einyun.app.pms.main.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.ui.widget
 * @ClassName: ChatView
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/11/4 0004 上午 10:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/4 0004 上午 10:16
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ChartView extends LinearLayout implements OnChartValueSelectedListener {

    private Context mContext;
    private BarChart chartKhmyd;
    private LinearLayout llListwsj;
    private TextView yearMon;

    public ChartView(Context context) {
        super(context);
        initView(context);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        mContext = context;

        LayoutInflater.from(mContext).inflate(R.layout.widget_chart_view, this);
        if (!isInEditMode()) {
            chartKhmyd = findViewById(R.id.chart_khmyd);
            chartKhmyd.setOnChartValueSelectedListener(this);

            llListwsj = findViewById(R.id.ll_listwsj);
            yearMon = findViewById(R.id.year_mon);
        }
    }

    /**
     * 显示单条柱状图
     */
    public void showBarChartAlong(ArrayList<Statisfaction> khmydBean, String year_mon) {
        yearMon.setText("年月：" + year_mon);
        if (khmydBean == null) {
            chartKhmyd.setVisibility(GONE);
            llListwsj.setVisibility(VISIBLE);
            return;
        }

        int index_name = 0;
        BarChartManager barChartManager = new BarChartManager(chartKhmyd);
        List<BarEntry> yVals = new ArrayList<>();
        List<String> orgName = new ArrayList<>();
        for (int i = 0; i < khmydBean.size(); i++) {
            String name = khmydBean.get(i).getOrgName();
            float overall = (float) (khmydBean.get(i).getOverallSatisfaction() * 100);
            yVals.add(new BarEntry(i, overall));
            orgName.add(khmydBean.get(i).getOrgName());

            if(name == null || name.equals("")){
                index_name ++;
            }
        }
        String label = "";
        if (orgName.size() == index_name) {
            chartKhmyd.setVisibility(GONE);
            llListwsj.setVisibility(VISIBLE);
            return;
        }
        barChartManager.showBarChart(yVals, label, orgName, Color.parseColor("#55A3EE"));
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
