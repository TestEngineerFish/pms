package com.einyun.app.common.ui.widget.selectpopview;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
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


public class SelectPopView extends PopupWindow {
    private static final String TAG = "CustomPopWindow";
    private View view;
    private Activity context;
    private AdapterView.OnItemClickListener mListener;
    private RecyclerView gv_approval_type;
    private RecyclerView gv_approval_child_type;
    private RecyclerView gv_approval_status;
    public static int mApprovalTypePosition = -1;
    public static int mApprovalChildTypePosition = -1;
    public static int mApprovalStatusPosition = -1;
    private RVBindingAdapter<SelectPopItemBinding, SelectModel> adapter;
    private RVBindingAdapter<SelectPopItemBinding, SelectModel> adapter1;//二级列表适配器
    private RVBindingAdapter<SelectPopItemBinding, SelectModel> adapter2;//三级列表适配器
    private RVBindingAdapter<SelectPopItemBinding, SelectModel> adapter3;//四级列表适配器

    private SelectPopBinding selectPopBinding;
    private SelectModel selectModel;

    public SelectPopView(Activity context) {
        super(context);
        this.context = context;
//        this.selectModel=selectModel;
        view = LayoutInflater.from(context).inflate(R.layout.select_pop, null);
        initPopWindow();
        initView();
    }


    public void initView() {
        selectModel = new SelectModel();
        selectModel.setId("tiaoxian");
        selectModel.setType("条线");
        selectModel.setContent("");
        List<SelectModel> selectModels = new ArrayList<>();
        SelectModel selectModel1 = new SelectModel();
        selectModel1.setId("huanjing");
        selectModel1.setType("工单类型");
        selectModel1.setContent("环境");
        SelectModel selectModel3 = new SelectModel();
        selectModel3.setId("huan");
        selectModel3.setType("工单类型");
        selectModel3.setContent("环境1");
        selectModels.add(selectModel3);
        List<SelectModel> selectModels1 = new ArrayList<>();
        SelectModel selectModel2 = new SelectModel();
        selectModel2.setId("ordertype");
        selectModel2.setType("");
        selectModel2.setContent("品质工单");
        List<SelectModel> selectModels2 = new ArrayList<>();
        SelectModel selectModel4 = new SelectModel();
        selectModel4.setId("orderty");
        selectModel4.setType("");
        selectModel4.setContent("保洁品质工单");
        selectModels2.add(selectModel4);
        List<SelectModel> selectModels3 = new ArrayList<>();
        SelectModel selectModel5 = new SelectModel();
        selectModel5.setId("ordert");
        selectModel5.setType("");
        selectModel5.setContent("保洁品质工单子item");
        selectModels3.add(selectModel5);
        selectModel4.setSelectModelList(selectModels3);
        selectModel2.setSelectModelList(selectModels2);
        selectModels1.add(selectModel2);
        selectModel1.setSelectModelList(selectModels1);
        selectModels.add(selectModel1);
        selectModel.setSelectModelList(selectModels);
        /*selectModelList.add(new SelectModel("1","工单1",false));
        selectModelList.add(new SelectModel("2","工单2",false));
        selectModelList.add(new SelectModel("3","工单3",false));
        selectModelList.add(new SelectModel("4","工单4",false));
*/
        //一级列表适配器
        adapter = new RVBindingAdapter<SelectPopItemBinding, SelectModel>(context, com.einyun.app.common.BR.select) {

            @Override
            public void onBindItem(SelectPopItemBinding binding, SelectModel model, int position) {
                if (model.getIsCheck()) {
                    binding.tvContent.setBackgroundResource(R.drawable.iv_pop_item_choise);
                } else {
                    binding.tvContent.setBackgroundResource(R.drawable.shape_line);

                }
            }

            @Override
            public int getLayoutId() {
                return R.layout.select_pop_item;
            }

        };
        //二级列表适配器
        adapter1 = new RVBindingAdapter<SelectPopItemBinding, SelectModel>(context, com.einyun.app.common.BR.select) {

            @Override
            public void onBindItem(SelectPopItemBinding binding, SelectModel model, int position) {
                if (model.getIsCheck()) {
                    binding.tvContent.setBackgroundResource(R.drawable.iv_pop_item_choise);
                } else {
                    binding.tvContent.setBackgroundResource(R.drawable.shape_line);
                }
            }

            @Override
            public int getLayoutId() {
                return R.layout.select_pop_item;
            }
        };
        //三级级列表适配器
        adapter2 = new RVBindingAdapter<SelectPopItemBinding, SelectModel>(context, com.einyun.app.common.BR.select) {

            @Override
            public void onBindItem(SelectPopItemBinding binding, SelectModel model, int position) {
                if (model.getIsCheck()) {
                    binding.tvContent.setBackgroundResource(R.drawable.iv_pop_item_choise);
                } else {
                    binding.tvContent.setBackgroundResource(R.drawable.shape_line);
                }
            }

            @Override
            public int getLayoutId() {
                return R.layout.select_pop_item;
            }
        };
//四级列表适配器
        adapter3 = new RVBindingAdapter<SelectPopItemBinding, SelectModel>(context, com.einyun.app.common.BR.select) {

            @Override
            public void onBindItem(SelectPopItemBinding binding, SelectModel model, int position) {
                if (model.getIsCheck()) {
                    binding.tvContent.setBackgroundResource(R.drawable.iv_pop_item_choise);
                } else {
                    binding.tvContent.setBackgroundResource(R.drawable.shape_line);
                }
            }

            @Override
            public int getLayoutId() {
                return R.layout.select_pop_item;
            }
        };


        adapter.setOnItemClick(new ItemClickListener<SelectModel>() {
            @Override
            public void onItemClicked(View view1, SelectModel data) {
                handleCheck(adapter.getDataList(), data);
                adapter.notifyDataSetChanged();
                if (!data.getType().equals("")) {
                    selectPopBinding.selectTypeSec.setText(data.getType());

                } else {
                    selectPopBinding.selectTypeSec.setVisibility(View.GONE);
                }

                cleanData(adapter1);
                cleanData(adapter2);
                cleanData(adapter3);
                adapter1.setDataList(data.getSelectModelList());

                /*if (data.getSelectModelList().size() > 0) {
                    selectPopBinding.selectLnSec.setVisibility(View.VISIBLE);
                    adapter1.setDataList(data.getSelectModelList());

                } else selectPopBinding.selectLnSec.setVisibility(View.GONE);*/
            }
        });
        adapter1.setOnItemClick(new ItemClickListener<SelectModel>() {
            @Override
            public void onItemClicked(View veiw, SelectModel data) {
                handleCheck(adapter1.getDataList(), data);
                adapter1.notifyDataSetChanged();
                if (!data.getType().equals("")) {
                    selectPopBinding.selectTypeThir.setText(data.getType());

                } else {
                    selectPopBinding.selectTypeThir.setVisibility(View.GONE);
                }
                adapter2.setDataList(data.getSelectModelList());
                /*if (data.getSelectModelList().size() > 0) {
                    adapter1.setDataList(data.getSelectModelList());
                    selectPopBinding.selectRecThir.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
                    selectPopBinding.selectRecThir.setAdapter(adapter2);
                }*/

            }
        });
        adapter2.setOnItemClick(new ItemClickListener<SelectModel>() {
            @Override
            public void onItemClicked(View veiw, SelectModel data) {
                handleCheck(adapter2.getDataList(), data);
                adapter2.notifyDataSetChanged();
                if (!data.getType().equals("")) {
                    selectPopBinding.selectTypeThir.setText(data.getType());

                } else {
                    selectPopBinding.selectTypeThir.setVisibility(View.GONE);
                }
                adapter3.setDataList(data.getSelectModelList());
//                adapter2.setDataList(data.getSelectModelList());
                /*if (data.getSelectModelList().size() > 0) {
                    adapter1.setDataList(data.getSelectModelList());
                    selectPopBinding.selectRecThir.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
                    selectPopBinding.selectRecThir.setAdapter(adapter2);
                }*/

            }
        });
        adapter3.setOnItemClick(new ItemClickListener<SelectModel>() {
            @Override
            public void onItemClicked(View veiw, SelectModel data) {
                handleCheck(adapter2.getDataList(), data);
                adapter3.notifyDataSetChanged();
                if (!data.getType().equals("")) {
                    selectPopBinding.selectTypeThir.setText(data.getType());

                } else {
                    selectPopBinding.selectTypeThir.setVisibility(View.GONE);
                }
//                adapter2.setDataList(data.getSelectModelList());
                /*if (data.getSelectModelList().size() > 0) {
                    adapter1.setDataList(data.getSelectModelList());
                    selectPopBinding.selectRecThir.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
                    selectPopBinding.selectRecThir.setAdapter(adapter2);
                }*/

            }
        });
        adapter.setDataList(selectModel.getSelectModelList());
        selectPopBinding.selectRecFir.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
        selectPopBinding.selectRecFir.setAdapter(adapter);
        selectPopBinding.selectRecSec.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
        selectPopBinding.selectRecSec.setAdapter(adapter1);
        selectPopBinding.selectRecThir.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
        selectPopBinding.selectRecThir.setAdapter(adapter2);
        selectPopBinding.selectRecFour.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
        selectPopBinding.selectRecFour.setAdapter(adapter3);

    }


    /**
     * 设置选中数据处理
     */
    private void handleCheck(List<SelectModel> list, SelectModel selectModel) {
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals(selectModel)) {
                list.get(i).setIsCheck(false);
                Log.d("test", "zhixl");
            }
            selectModel.setIsCheck(true);
        }

    }

    /**
     * 清除数据
     * */
    private void cleanData(RVBindingAdapter adapter){
        List<SelectModel> selectModelList=new ArrayList<>();
        for (int i=0;i<adapter.getDataList().size();i++){
            SelectModel selectModel=(SelectModel)adapter.getDataList().get(i);
            selectModel.setIsCheck(false);
            selectModelList.add(selectModel);
        }
        adapter.setDataList(selectModelList);
        /*selectPopBinding.selectRecSec.removeAllViews();
        selectPopBinding.selectRecThir.removeAllViews();
        selectPopBinding.selectRecFour.removeAllViews();
*/

    }


    public void initPopWindow() {
//

        this.setContentView(view);
        selectPopBinding = DataBindingUtil.bind(view);
       /* selectPopBinding.selectSmf.setCallback(new SMFilterView.SMFilterViewInterface() {
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
        selectPopBinding.selectSmf.animationShowout();*/

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




