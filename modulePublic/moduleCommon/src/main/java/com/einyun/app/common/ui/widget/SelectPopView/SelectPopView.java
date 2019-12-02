package com.einyun.app.common.ui.widget.SelectPopView;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.BR;
import com.einyun.app.common.R;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.databinding.ItemBlockChooseBinding;
import com.einyun.app.common.databinding.SelectPopBinding;
import com.einyun.app.common.databinding.SelectPopItemBinding;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.library.uc.usercenter.model.OrgModel;

import java.util.ArrayList;
import java.util.List;


public class SelectPopView extends PopupWindow implements ItemClickListener<SelectModel> {
    private static final String TAG = "CustomPopWindow";
    private  View view;
    private Activity context;
    private AdapterView.OnItemClickListener mListener;
    private RecyclerView gv_approval_type;
    private RecyclerView gv_approval_child_type;
    private RecyclerView gv_approval_status;
    public static int mApprovalTypePosition = -1;
    public static int mApprovalChildTypePosition = -1;
    public static int mApprovalStatusPosition = -1;
    private RVBindingAdapter<SelectPopItemBinding, SelectModel> adapter;
    private SelectPopBinding selectPopBinding;
    private List<SelectModel> selectModelList=new ArrayList<>();
    public SelectPopView(Activity context) {
        super(context);
        this.context = context;
        view=LayoutInflater.from(context).inflate(R.layout.select_pop,null);
        initPopWindow();
        initView();
    }


    public void initView() {
        selectModelList.add(new SelectModel("工单"));
        selectModelList.add(new SelectModel("工单"));
        selectModelList.add(new SelectModel("工单"));
        selectModelList.add(new SelectModel("工单"));

        //一级列表
        adapter = new RVBindingAdapter<SelectPopItemBinding, SelectModel>(context, com.einyun.app.common.BR.select) {

            @Override
            public void onBindItem(SelectPopItemBinding binding, SelectModel model, int position) {

            }

            @Override
            public int getLayoutId() {
                return R.layout.select_pop_item;
            }

        };
        adapter.setOnItemClick(this);
        adapter.setDataList(selectModelList);
        selectPopBinding.gvApprovalType.setLayoutManager(new GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false));
        selectPopBinding.gvApprovalType.setAdapter(adapter);
        selectPopBinding.gvApprovalChildType.setLayoutManager(new GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false));
        selectPopBinding.gvApprovalChildType.setAdapter(adapter);
        selectPopBinding.gvApprovalStatus.setLayoutManager(new GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false));
        selectPopBinding.gvApprovalStatus.setAdapter(adapter);
    }


    @Override
    public void onItemClicked(View veiw, SelectModel data) {

    };

    public void initPopWindow() {
//

        this.setContentView(view);
        selectPopBinding=DataBindingUtil.bind(view);
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

    }

}
/**
     * 设置添加屏幕的背景透明度(值越大,透明度越高)
     *
     * @param bgAlpha
     *//*

    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
*/




