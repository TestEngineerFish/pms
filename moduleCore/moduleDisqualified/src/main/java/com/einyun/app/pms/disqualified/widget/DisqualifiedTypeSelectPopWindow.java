package com.einyun.app.pms.disqualified.widget;

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
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.pms.disqualified.BR;
import com.einyun.app.pms.disqualified.R;
import com.einyun.app.pms.disqualified.databinding.DisqualifiedPopwindowItemBinding;
import com.einyun.app.pms.disqualified.model.DisqualifiedTypesBean;

import java.util.ArrayList;
import java.util.List;

public class DisqualifiedTypeSelectPopWindow extends PopupWindow {
    List<DisqualifiedTypesBean> mInquiriesTypesModule;
    List<DisqualifiedTypesBean> mInquiriesTypesModule2;
    List<DisqualifiedTypesBean> mInquiriesNewCreateTypesModule=new ArrayList<>();
    private  View view;
    private Activity context;
    private OnItemClickListener mListener;
    private RVBindingAdapter<DisqualifiedPopwindowItemBinding, DisqualifiedTypesBean> adapter;//外部适配器
    private RVBindingAdapter<DisqualifiedPopwindowItemBinding, DisqualifiedTypesBean> adapterState;//二级适配器
    private int mPosition=-1;
    private int mPositionState=-1;
    private String fragmentTag;
    public DisqualifiedTypeSelectPopWindow(Activity context, List<DisqualifiedTypesBean> mInquiriesTypesModule, List<DisqualifiedTypesBean> mInquiriesTypesModule2,int mPosition,int mPositionState,String fragmentTag) {
        super(context);
        this.mInquiriesTypesModule=mInquiriesTypesModule;
        this.mInquiriesTypesModule2=mInquiriesTypesModule2;
        this.fragmentTag=fragmentTag;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.disqualifiedtype_popwindow, null);//alt+ctrl+f
        this.context = context;
        this.mPosition=mPosition;
        this.mPositionState=mPositionState;
        initView();
        initPopWindow();
//        getData(1,10);
        this.setOnDismissListener(new OnDismissListener(){

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
        RecyclerView rvLine = view.findViewById(R.id.rv_line);
        RecyclerView rvOrderState = view.findViewById(R.id.rv_order_state);

        iv_close.setOnClickListener(view1 -> {dismiss();});
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPosition=-1;
                mPositionState=-1;
                adapter.notifyDataSetChanged();
                adapterState.notifyDataSetChanged();

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
                mListener.onData( mPosition==-1?"":mInquiriesTypesModule.get(mPosition).getKey(),
                        mPositionState==-1?"":mInquiriesTypesModule2.get(mPositionState).getKey(),mPosition,mPositionState);
                dismiss();
            }
        });
        //一级列表适配器
        adapter = new RVBindingAdapter<DisqualifiedPopwindowItemBinding, DisqualifiedTypesBean>(context, com.einyun.app.pms.disqualified.BR.org) {

            @Override
            public void onBindItem(DisqualifiedPopwindowItemBinding binding, DisqualifiedTypesBean model, int position) {

                binding.llItem.setOnClickListener(view1 -> {
                    if (mPosition==position) {
                        mPosition=-1;
                    }else {
                        mPosition = position;
                    }
                    adapter.notifyDataSetChanged();
                });
                if (position== mPosition) {
                    binding.tvContent.setTextColor(context.getResources().getColor(R.color.blueTextColor));
                    binding.tvContent.setBackgroundResource(R.drawable.iv_pop_item_choise);
                }else {
                    binding.tvContent.setTextColor(context.getResources().getColor(R.color.blackTextColor));
                    binding.tvContent.setBackgroundResource(R.drawable.shape_line);
                }
                binding.tvContent.setText(model.getName());

            }

            @Override
            public int getLayoutId() {
                return R.layout.disqualified_popwindow_item;
            }

        };
        //二级列表适配器
        adapterState = new RVBindingAdapter<DisqualifiedPopwindowItemBinding, DisqualifiedTypesBean>(context, com.einyun.app.pms.disqualified.BR.org) {

            @Override
            public void onBindItem(DisqualifiedPopwindowItemBinding binding, DisqualifiedTypesBean model, int position) {

                binding.llItem.setOnClickListener(view1 -> {
                    if (mPositionState==position) {
                        mPositionState=-1;
                    }else {
                        mPositionState = position;
                    }
                    adapterState.notifyDataSetChanged();
                });
                if (position== mPositionState) {
                    binding.tvContent.setTextColor(context.getResources().getColor(R.color.blueTextColor));
                    binding.tvContent.setBackgroundResource(R.drawable.iv_pop_item_choise);
                }else {
                    binding.tvContent.setTextColor(context.getResources().getColor(R.color.blackTextColor));
                    binding.tvContent.setBackgroundResource(R.drawable.shape_line);
                }
                if (position==0) {
                    binding.tvContent.setText("待处理");
                }else {
                    binding.tvContent.setText(model.getName());
                }

            }

            @Override
            public int getLayoutId() {
                return R.layout.disqualified_popwindow_item;
            }

        };
        adapter.setDataList(mInquiriesTypesModule);
        rvLine.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
        rvLine.setAdapter(adapter);


        if (mInquiriesTypesModule2.size()>0) {
            mInquiriesNewCreateTypesModule.addAll(mInquiriesTypesModule2);
            mInquiriesNewCreateTypesModule.remove(mInquiriesNewCreateTypesModule.size()-1);
        }
        if (fragmentTag.equals(RouteKey.FRAGMENT_DISQUALIFIED_WAIT_FOLLOW)) {

            adapterState.setDataList(mInquiriesNewCreateTypesModule);
        }else {
            adapterState.setDataList(mInquiriesTypesModule2);
        }
        rvOrderState.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
        rvOrderState.setAdapter(adapterState);
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
        setOnDismissListener(new OnDismissListener(){
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
        void onData(String line, String state,int position,int positionState);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
