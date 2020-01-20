package com.einyun.app.pms.operatepercent.ui;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PERCENT_GET;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PERCENT_OWE;

public class OperatePercentBind {

    @BindingAdapter("allYearBase")
    public static void allYearBase(TextView view, String tag) {
        if (tag.equals(FRAGMENT_PERCENT_GET)) {
            view.setText("全部收费项目");

        } else if (tag.equals(FRAGMENT_PERCENT_OWE)) {
            view.setText("全部清欠项目");
        }
    }
    @BindingAdapter("allYearBaseAmount")
    public static void allYearBaseAmount(TextView view, String tag) {
        if (tag.equals(FRAGMENT_PERCENT_GET)) {
            view.setText("全年收缴基数(万元)");

        } else if (tag.equals(FRAGMENT_PERCENT_OWE)) {
            view.setText("全年清欠基数(万元)");
        }
    }
    @BindingAdapter("allYearRate")
    public static void allYearRate(TextView view, String tag) {
        if (tag.equals(FRAGMENT_PERCENT_GET)) {
            view.setText("前日收缴率");

        } else if (tag.equals(FRAGMENT_PERCENT_OWE)) {
            view.setText("前日清欠率");
        }
    }

    @BindingAdapter("alllYearBaseAmount")
    public static void alllYearBaseAmount(TextView view, String tag) {
        if (tag.equals(FRAGMENT_PERCENT_GET)) {
            view.setText("全年收缴基数");
        } else if (tag.equals(FRAGMENT_PERCENT_OWE)) {
            view.setText("全年清欠基数");
        }
    }

    @BindingAdapter("nowRateAmount")
    public static void nowRateAmount(TextView view, String tag) {
        if (tag.equals(FRAGMENT_PERCENT_GET)) {
            view.setText("前日收缴率");
        } else if (tag.equals(FRAGMENT_PERCENT_OWE)) {
            view.setText("前日清欠率");
        }
    }
}
