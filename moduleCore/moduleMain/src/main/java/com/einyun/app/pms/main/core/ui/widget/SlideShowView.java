package com.einyun.app.pms.main.core.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.CustomEventTypeEnum;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.utils.UserUtil;
import com.einyun.app.pms.main.R;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;


public class SlideShowView extends FrameLayout {

    private List<String> dataList;
    private List<LinearLayout> linearLayoutList;
    private TextView redTV_SP;
    private ViewPager mViewPager;
    private int currentItem = 0;
    private ScheduledExecutorService scheduledExecutorService;
    private Context mContext;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mViewPager.setCurrentItem(currentItem);
        }
    };
    private ImageView ivOne;
    private ImageView ivTwo;

    public SlideShowView(Context context) {
        this(context, null);
    }

    public SlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initUI(context);
    }

    public void initUI(Context context) {
        mContext = context;
        linearLayoutList = new ArrayList<>();
        dataList = new ArrayList<>();
        LayoutInflater.from(context).inflate(R.layout.widget_slide_show, this, true);
        ivOne = findViewById(R.id.iv_one);
        ivTwo = findViewById(R.id.iv_two);
        mViewPager = findViewById(R.id.viewPager);
        Field field = null; // ??????ViewPager???????????????????????????????????????????????????
        try {
            field = ViewPager.class.getDeclaredField("mTouchSlop");
            field.setAccessible(true); // ??????Java??????????????????
            field.setInt(mViewPager, 10); // ???????????????????????????????????????ViewPager???????????????????????????????????????150px????????????ViewPager???????????????
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setRed_SP(int red) {
        if (redTV_SP != null) {
            if (red == 0) redTV_SP.setVisibility(GONE);
            else if (red > 99) {
                redTV_SP.setText("??????");
                redTV_SP.setVisibility(VISIBLE);
            } else {
                redTV_SP.setVisibility(VISIBLE);
                redTV_SP.setText(red + "");
            }
        }

    }

    private LinearLayout createLinearLayout(Activity activity, List<String> dataList, int page) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_slide_show_view, null);
        LinearLayout root = linearLayout.findViewById(R.id.ll_root);

        redTV_SP = linearLayout.findViewById(R.id.shenpi_tv_red);

        List<LinearLayout> items = new ArrayList<>();
        items.add(linearLayout.findViewById(R.id.ll_point_check));
        items.add(linearLayout.findViewById(R.id.ll_toll));
        items.add(linearLayout.findViewById(R.id.ll_work_gongdanliebiao));
        items.add(linearLayout.findViewById(R.id.ll_work_chuangjiangongdan));
        items.add(linearLayout.findViewById(R.id.ll_approval));
        items.add(linearLayout.findViewById(R.id.ll_work_gongzuoyulan));
        items.add(linearLayout.findViewById(R.id.ll_saoma));
        root.removeAllViews();
        int index = page * 4;
        int len = dataList.size() > (page + 1) * 4 ? (page + 1) * 4 : dataList.size();
        for (int i = index; i < len; i++) {
            for (LinearLayout item : items) {
                if (item.getTag().equals(dataList.get(i))) {
                    root.addView(item);
                    switch (dataList.get(i)) {
                        case "dj":
                            //??????
                            item.setOnClickListener(v -> ARouter.getInstance().build(RouterUtils.ACTIVITY_POINT_CHECK_ACTIVITY).navigation());
                            break;
                        case "gdlb":
                            //????????????
                            item.setOnClickListener(v -> {
                                HashMap<String, String> map = new HashMap<>();
                                map.put("user_name", UserUtil.getUserName());
                                MobclickAgent.onEvent(mContext, CustomEventTypeEnum.ORDER_LIST.getTypeName(), map);
                                ARouter.getInstance().build(RouterUtils.ACTIVITY_ORDER_LIST).navigation();
                            });
                            break;
                        case "cjgd":
                            //????????????
                            item.setOnClickListener(v -> ARouter.getInstance()
                                    .build(RouterUtils.ACTIVITY_ORDER_LIST)
                                    .withString(RouteKey.KEY_WORK_ORDER_LIST_TYPE, DataConstants.WORK_ORDER_LIST_TYPE_CREATE)
                                    .navigation());
                            break;
                        case "sp":
                            //??????
//                            item.setOnClickListener(v -> readyGo(ApprovalActivity.class));
                            item.setOnClickListener(v ->
                                    ARouter.getInstance().build(RouterUtils.ACTIVITY_APPROVAL).navigation());
                            break;
                        case "sfzl":
                            //??????
//                            item.setOnClickListener(v -> readyGo(ApprovalActivity.class));
                            item.setOnClickListener(v ->
                                    ARouter.getInstance().build(RouterUtils.ACTIVITY_TOLL_BUILD).navigation());
                            break;
                        case "gzyl":
                            //????????????
                            item.setOnClickListener(v -> {
                                HashMap<String, String> map2 = new HashMap<>();
                                map2.put("user_name", UserUtil.getUserName());
                                MobclickAgent.onEvent(mContext, CustomEventTypeEnum.PREVIEW_WORK.getTypeName(), map2);
                                ARouter.getInstance().build(RouterUtils.ACTIVITY_ORDER_PREVIEW).navigation();
                            });
                            break;
                        case "smcl":
                            //????????????
                            item.setOnClickListener(v -> {
                                HashMap<String, String> map3 = new HashMap<>();
                                map3.put("user_name", UserUtil.getUserName());
                                MobclickAgent.onEvent(mContext, CustomEventTypeEnum.SWEEP_CODE.getTypeName(), map3);
                                ARouter.getInstance()
                                        .build(RouterUtils.ACTIVITY_SCANNER)
                                        .withString(RouteKey.KEY_HOME_ENTER, RouteKey.KEY_HOME_ENTER)
                                        .navigation(activity, RouterUtils.ACTIVITY_REQUEST_SCANNER);
                            });
                            break;
                    }
                }
            }
        }

        return linearLayout;
    }

    public void setImageData(Activity activity, List<String> dataList) {
        this.dataList = dataList;
        linearLayoutList = new ArrayList<>();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5, 0, 0, 0);

        LinearLayout linearLayout1 = createLinearLayout(activity, dataList, 0);
        if (linearLayout1 != null)
            linearLayoutList.add(linearLayout1);

        LinearLayout linearLayout2 = createLinearLayout(activity, dataList, 1);
        if (linearLayout2 != null)
            linearLayoutList.add(linearLayout2);
        if (dataList.size() <= 4) {
            ivTwo.setVisibility(GONE);
        } else {
            ivTwo.setVisibility(VISIBLE);
        }

        mViewPager.setFocusable(true);
        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    private void setImageBackground(int selectItems) {
        if (selectItems == 0) {
            ivOne.setImageResource(R.drawable.shape_work_one);
            ivTwo.setImageResource(R.drawable.shape_work_two);
        } else {
            ivTwo.setImageResource(R.drawable.shape_work_one);
            ivOne.setImageResource(R.drawable.shape_work_two);

        }
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(View container, int position, Object object) {
            //((ViewPag.er)container).removeView((View)object);
            ((ViewPager) container).removeView(linearLayoutList.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(linearLayoutList.get(position));
            return linearLayoutList.get(position);
        }

        @Override
        public int getCount() {
            return linearLayoutList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

        @Override
        public void finishUpdate(View arg0) {
        }
    }


    private class MyPageChangeListener implements OnPageChangeListener {

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case 1:
                    break;
                case 0:
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int pos) {
            setImageBackground(pos);
        }
    }
}



