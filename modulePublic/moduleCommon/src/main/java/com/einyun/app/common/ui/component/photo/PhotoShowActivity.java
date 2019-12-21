package com.einyun.app.common.ui.component.photo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.einyun.app.common.R;
import com.einyun.app.common.constants.DataConstants;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class PhotoShowActivity extends AppCompatActivity {

    HackyViewPager hViewPager;
    ImageView back;
    LinearLayout llPoint;

    private int position;
    private ArrayList<String> mDatas;

    public static void start(Context context, int position, ArrayList<String> images){
        if(images!=null&&images.size()>0){
            Intent starter = new Intent(context, PhotoShowActivity.class);
            //传递当前点击的图片的位置、图片路径集合
            starter.putExtra(DataConstants.KEY_POSITION, position);
            starter.putStringArrayListExtra(DataConstants.KEY_IAMGES, images);
            context.startActivity(starter);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_show);
        hViewPager=findViewById(R.id.hViewPager);
        back=findViewById(R.id.back);
        llPoint=findViewById(R.id.ll_point);
        getFrontPageData();
        initViews();
    }

    private void initViews() {
        hViewPager = (HackyViewPager) findViewById(R.id.hViewPager);
        hViewPager.setAdapter(new ImageAdapter());
        hViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        //为ViewPager当前page的数字
        hViewPager.setCurrentItem(position);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void getFrontPageData() {
        //点击图片的位置
        position = getIntent().getIntExtra("position", 0);
        //获取传递过来的图片地址
        mDatas = getIntent().getStringArrayListExtra("mImages");

        if (mDatas.size() == 1) {
            // 只有一张图片 就不显示圆点
        } else {
            for (int i = 0; i < mDatas.size(); i++) {
                View point = new View(this);
                //point.setId(i);//设置Id
                point.setTag(i);//设置Tag
                //设置背景
                if(i == position){
                    point.setBackgroundResource(R.drawable.shape_work_one);
                }else {
                    point.setBackgroundResource(R.drawable.shape_work_two);
                }
                //布局参数
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
                params.rightMargin = 20;//右边距
                //把点添加到线性布局
                llPoint.addView(point, params);
            }
            View point = llPoint.getChildAt(position);
            point.setEnabled(false);
        }
    }

    public class ImageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return null == mDatas ? 0 : mDatas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //创建显示图片的控件
            PhotoView photoView = new PhotoView(container.getContext());
            //设置背景颜色
            photoView.setBackgroundColor(Color.BLACK);
            //把photoView添加到viewpager中，并设置布局参数
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            //加载当前PhtotoView要显示的数据
            String url = mDatas.get(position);

            if (!TextUtils.isEmpty(url)) {
                //使用使用Glide进行加载图片进行加载图片
                Glide.with(PhotoShowActivity.this).load(url).into(photoView);
            }

            //图片单击事件的处理
            photoView.setOnPhotoTapListener((view, x, y) -> finish());

            return photoView;
        }

    }


    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        //页面选择
        @Override
        public void onPageSelected(int index) {
            changePager(index);
        }
    }

    private void changePager(int index) {
        int childCount = llPoint.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View point = llPoint.getChildAt(i);//获取小圆点
            if (index == i) {
                //当前选择的页面下标
                point.setBackgroundResource(R.drawable.shape_work_one);
                point.setEnabled(false);
            } else {
                point.setBackgroundResource(R.drawable.shape_work_two);
                point.setEnabled(true);
            }
        }
    }
}
