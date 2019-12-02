package com.einyun.app.common.ui.widget.selectpopview;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.R;
import com.einyun.app.common.databinding.SelectPopBinding;
import com.einyun.app.common.databinding.SelectPopItemBinding;
import com.einyun.app.common.model.SelectModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
//        initView();
    }


    /*public void initView() {
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

*/
    @Override
    public void onItemClicked(View veiw, SelectModel data) {

    };

    public void initPopWindow() {
//

        this.setContentView(view);
        selectPopBinding=DataBindingUtil.bind(view);
        selectPopBinding.selectSmf.setCallback(new SMFilterView.SMFilterViewInterface() {
            @Override
            public void onFilter(Map<String, Object> items) {
//                onFilterAction(selectPopBinding.selectSmf.getItemSelected());
            }

            @Override
            public void onItemClicked(SMFilterItem item) {
                selectPopBinding.selectSmf.updateType(SMFilterView.FilterType.Type_List_PGD, "");
            }
        });
        selectPopBinding.selectSmf.updateType(SMFilterView.FilterType.Type_List_PGD, "");
        selectPopBinding.selectSmf.animationShowout();

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

   /* private void onFilterAction(Map<String, Object> items){
        this.tiaoxian = items.containsKey(SMFilterDataHelper.SMFilterDataType.FilterData_TX)?
                (String)items.get(SMFilterDataHelper.SMFilterDataType.FilterData_TX):"";
        this.fType = items.containsKey(SMFilterDataHelper.SMFilterDataType.FilterData_GDLX + 1)?
                (String)items.get(SMFilterDataHelper.SMFilterDataType.FilterData_GDLX + 1):"";
        this.fType2 = items.containsKey(SMFilterDataHelper.SMFilterDataType.FilterData_GDLX + 2)?
                (String)items.get(SMFilterDataHelper.SMFilterDataType.FilterData_GDLX + 2):"";
        this.fType3 = items.containsKey(SMFilterDataHelper.SMFilterDataType.FilterData_GDLX + 3)?
                (String)items.get(SMFilterDataHelper.SMFilterDataType.FilterData_GDLX + 3):"";
        this.fOtStatus = items.containsKey(SMFilterDataHelper.SMFilterDataType.FilterData_TIME_OUT)?
                (String)items.get(SMFilterDataHelper.SMFilterDataType.FilterData_TIME_OUT):"";
    }
*/
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




