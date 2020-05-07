package com.einyun.app.pms.toll.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.common.utils.HanziToPinyin;
import com.einyun.app.pms.toll.BR;
import com.einyun.app.pms.toll.R;
import com.einyun.app.pms.toll.databinding.UnitCheckPopwindowBinding;
import com.einyun.app.pms.toll.databinding.UnitCheckPopwindowItemBinding;
import com.einyun.app.common.model.BuildModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UnitCheckPopWindow extends PopupWindow {
    List<BuildModel.GridRangeBean> mFeeHouseList;
    private View view;
    private Activity context;
    private OnItemClickListener mListener;
    private RVBindingAdapter<UnitCheckPopwindowItemBinding, BuildModel.GridRangeBean> adapter;//外部适配器
    private int mPosition = -1;

    public UnitCheckPopWindow(Activity context, List<BuildModel.GridRangeBean> mInquiriesTypesModule) {
        super(context);
        this.mFeeHouseList = mInquiriesTypesModule;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.unit_check_popwindow, null);//alt+ctrl+f
        this.context = context;
        this.mPosition = mPosition;
        initView();
        initPopWindow();
//        getData(1,10);
        this.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
//                reSetdata();
            }
        });
    }


    private void initView() {

        TextView cancel = view.findViewById(R.id.cancle);
        TextView ok = view.findViewById(R.id.ok);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        RecyclerView recyclerview = view.findViewById(R.id.recyclerview);

        iv_close.setOnClickListener(view1 -> {
            dismiss();
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (BuildModel.GridRangeBean gridRangeBean : mFeeHouseList) {

                    gridRangeBean.setChecked(0);
                }
                adapter.notifyDataSetChanged();

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mListener.setOnItemClick(v,getData(1,10));
//
//                if (mPosition==-1) {
//
//                    mListener.onData("");
//                }        else {
//                    mListener.onData( mInquiriesTypesModule.get(mPosition).getDataName());
//                }
                mListener.onData(mFeeHouseList);
                dismiss();
            }
        });
        //一级列表适配器
        adapter = new RVBindingAdapter<UnitCheckPopwindowItemBinding, BuildModel.GridRangeBean>(context, com.einyun.app.pms.toll.BR.check) {

            @Override
            public void onBindItem(UnitCheckPopwindowItemBinding binding, BuildModel.GridRangeBean model, int position) {

                binding.llItem.setOnClickListener(view1 -> {
                    if (model.getChecked() == 0) {
                        model.setChecked(1);
                    } else {
                        model.setChecked(0);
                    }
                    adapter.notifyDataSetChanged();
                });
                if (model.getChecked() == 1) {
                    binding.tvContent.setTextColor(context.getResources().getColor(R.color.blueTextColor));
                    binding.tvContent.setBackgroundResource(R.drawable.shape_rect_radius19_blue);
                    binding.ivCheck.setVisibility(View.VISIBLE);
                    model.setChecked(1);
                } else {
                    binding.ivCheck.setVisibility(View.GONE);
                    binding.tvContent.setTextColor(context.getResources().getColor(R.color.blackTextColor));
                    model.setChecked(0);
                    binding.tvContent.setBackgroundResource(R.drawable.shape_rect_radius19_grey);
                }
                binding.tvContent.setText(model.getName());

            }

            @Override
            public int getLayoutId() {
                return R.layout.unit_check_popwindow_item;
            }

        };
        /**
         * 默认排序
         */
        Collections.sort(mFeeHouseList, new Comparator<BuildModel.GridRangeBean>() {
            @Override
            public int compare(BuildModel.GridRangeBean o1, BuildModel.GridRangeBean o2) {

                return o1.getName().compareTo(o2.getName());//顺序
            }
        });
        adapter.setDataList(mFeeHouseList);
        recyclerview.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
        recyclerview.setAdapter(adapter);
    }

    private void initPopWindow() {
        this.setContentView(view);
        // 设置弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出窗体可点击()
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00FFFFFF);
        //设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        backgroundAlpha(context, 1f);//0.0-1.0
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(context, 1f);
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度(值越大,透明度越高)
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }


    public interface OnItemClickListener {
        void onData(List<BuildModel.GridRangeBean> mFeeHouseList);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
