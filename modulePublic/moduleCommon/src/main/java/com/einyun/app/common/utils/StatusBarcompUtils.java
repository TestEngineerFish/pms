package com.einyun.app.common.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by smz on 2017/4/27.
 */

public class StatusBarcompUtils {


        private static final int INVALID_VAL = -1;
        private static final int COLOR_DEFAULT = Color.parseColor("#20000000");

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public static void compat(Activity activity, int statusColor)
        {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                if (statusColor != INVALID_VAL)
                {
                    activity.getWindow().setStatusBarColor(statusColor);
                }
                return;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            {
                int color = COLOR_DEFAULT;
                ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
                if (statusColor != INVALID_VAL)
                {
                    color = statusColor;
                }
                View statusBarView = new View(activity);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        getStatusBarHeight(activity));
                statusBarView.setBackgroundColor(color);
                contentView.addView(statusBarView, lp);
            }

        }

        public static void compat(Activity activity)
        {
            compat(activity, INVALID_VAL);
        }


        public static int getStatusBarHeight(Context context)
        {
            int result = 0;
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0)
            {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        }
        public  static void compatpicture(Activity context){
            if (Build.VERSION.SDK_INT >= 21) {
                View decorView = context.getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                context.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
//            ActionBar actionBar =context.getSupportActionBar();
//            actionBar.hide();
        }
    }


