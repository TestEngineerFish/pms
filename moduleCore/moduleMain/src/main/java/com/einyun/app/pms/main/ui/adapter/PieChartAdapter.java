package com.einyun.app.pms.main.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.einyun.app.base.util.DecimalFormatUtil;
import com.einyun.app.library.dashboard.model.WorkOrder;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.ui.widget.PieChartView;

import java.util.ArrayList;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.main.ui.adapter
 * @ClassName: PieChartAdapter
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/11/4 0004 上午 11:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/4 0004 上午 11:17
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PieChartAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<WorkOrder> workOrderBeans;

    public PieChartAdapter(Context context, ArrayList<WorkOrder> workOrderBeans) {
        this.mContext = context;
        this.workOrderBeans = workOrderBeans;
    }

    @Override
    public int getCount() {
        return workOrderBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return workOrderBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHandler handler = null;
        if (view == null) {
            handler = new ViewHandler();
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_piechart, null);
            handler.pieImg = view.findViewById(R.id.pie_img);
            handler.pieName = view.findViewById(R.id.pie_name);
            handler.pieNum = view.findViewById(R.id.pie_num);

            view.setTag(handler);
        } else handler = (ViewHandler) view.getTag();

        if (i <= 8) {
            int strokeWidth = 0; // 3dp 边框宽度
            int roundRadius = 2; // 8dp 圆角半径
            int strokeColor = Color.parseColor(PieChartView.PIE_COLORS2[i]);//边框颜色
            int fillColor = Color.parseColor(PieChartView.PIE_COLORS2[i]);//内部填充颜色
            GradientDrawable gd = new GradientDrawable();//创建drawable
            gd.setColor(fillColor);
            gd.setCornerRadius(roundRadius);
            gd.setStroke(strokeWidth, strokeColor);
//            handler.pieImg.setBackgroundColor(Color.parseColor(View_PieChart.PIE_COLORS2[i]));
            handler.pieImg.setBackgroundDrawable(gd);
        }
        handler.pieName.setText(workOrderBeans.get(i).getName());

        String count = DecimalFormatUtil.format(workOrderBeans.get(i).getCount());
        handler.pieNum.setText(count);

        return view;
    }


    class ViewHandler {
        TextView pieImg;
        TextView pieName;
        TextView pieNum;
    }
}
