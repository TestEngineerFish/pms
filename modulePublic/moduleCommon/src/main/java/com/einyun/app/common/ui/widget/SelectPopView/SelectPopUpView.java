package com.einyun.app.common.ui.widget.selectpopview;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.util.JsonUtil;
import com.einyun.app.common.R;
import com.einyun.app.common.databinding.ItemSelectPopBinding;
import com.einyun.app.common.databinding.SelectPopItemBinding;
import com.einyun.app.common.databinding.SelectPopUpBinding;
import com.einyun.app.common.model.SelectModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class SelectPopUpView extends PopupWindow implements View.OnClickListener {
    private static final String TAG = "CustomPopWindow";
    private View view;
    private Activity context;
    private AdapterView.OnItemClickListener mListener;
    private RVBindingAdapter<ItemSelectPopBinding, SelectModel> adapter;//外部适配器
    private SelectPopUpBinding selectPopUpBinding;
    private List<SelectModel> selectModelList = new ArrayList<>();
    private List<SelectModel> selectModelListOrig=new ArrayList<>();//复制原始数据
    private String listJsonString;
    private OnSelectedListener onSelectedListener;
    public SelectPopUpView(Activity context) {
        super(context);
        this.context = context;
//        this.selectModel=selectModel;
        view = LayoutInflater.from(context).inflate(R.layout.select_pop_up, null);
        initPopWindow();
        initView();
    }


    public void initView() {
        SelectModel selectModel1 = new SelectModel();
        selectModel1.setId("1");
        selectModel1.setType("条线");
        selectModel1.setContent("");
        SelectModel selectModel2 = new SelectModel();
        selectModel2.setId("2");
        selectModel2.setType("是否超时");
        selectModel2.setContent("");
        List<SelectModel> selectModels1 = new ArrayList<>();
        SelectModel selectModel3 = new SelectModel();
        selectModel3.setId("3");
        selectModel3.setType("工单类型");
        selectModel3.setContent("环境");
        List<SelectModel> selectModels2 = new ArrayList<>();
        SelectModel selectModel5 = new SelectModel();
        selectModel5.setId("5");
        selectModel5.setType("");
        selectModel5.setContent("品质工单");
        SelectModel selectModel6 = new SelectModel();
        selectModel6.setId("6");
        selectModel6.setType("");
        selectModel6.setContent("日常工单");
        selectModels2.add(selectModel5);
        selectModels2.add(selectModel6);
        selectModel3.setSelectModelList(selectModels2);
        SelectModel selectModel4 = new SelectModel();
        selectModel4.setId("4");
        selectModel4.setType("工单类型");
        selectModel4.setContent("工程");
        List<SelectModel> selectModels3 = new ArrayList<>();
        SelectModel selectModel7 = new SelectModel();
        selectModel7.setId("7");
        selectModel7.setType("");
        selectModel7.setContent("给排水类");
        SelectModel selectModel8 = new SelectModel();
        selectModel8.setId("8");
        selectModel8.setType("");
        selectModel8.setContent("供配电类");
        selectModels3.add(selectModel7);
        selectModels3.add(selectModel8);
        selectModel4.setSelectModelList(selectModels3);
        selectModel3.setSelectModelList(selectModels2);
        selectModels1.add(selectModel3);
        selectModels1.add(selectModel4);
        selectModel1.setSelectModelList(selectModels1);
        selectModelList.add(selectModel1);
        selectModelList.add(selectModel2);
        List<SelectModel> selectModels4 = new ArrayList<>();
        SelectModel selectModel9 = new SelectModel();
        selectModel9.setId("9");
        selectModel9.setType("");
        selectModel9.setContent("保洁品质工单");
        SelectModel selectModel10 = new SelectModel();
        selectModel10.setId("10");
        selectModel10.setType("");
        selectModel10.setContent("绿化品质工单");
        selectModels4.add(selectModel9);
        selectModels4.add(selectModel10);
        selectModel5.setSelectModelList(selectModels4);
         listJsonString=new Gson().toJson(selectModelList);

        for (SelectModel selectModel:selectModelList){
            try {
                selectModelListOrig.add((SelectModel) selectModel.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        //一级列表适配器
        adapter = new RVBindingAdapter<ItemSelectPopBinding, SelectModel>(context, com.einyun.app.common.BR.select) {

            @Override
            public void onBindItem(ItemSelectPopBinding binding, SelectModel model, int position) {
                if (model.getType().equals("")) {
                    binding.selectType.setVisibility(View.GONE);
                    binding.itemSelectDiv.setVisibility(View.VISIBLE);
                } else {
                    binding.selectType.setVisibility(View.VISIBLE);
                    binding.itemSelectDiv.setVisibility(View.GONE);
                }


               //一级列表适配器
                RVBindingAdapter<SelectPopItemBinding, SelectModel> adapterIn = new RVBindingAdapter<SelectPopItemBinding, SelectModel>(context, com.einyun.app.common.BR.selects) {

                    @Override
                    public void onBindItem(SelectPopItemBinding binding, SelectModel model, int position) {
                        if (model.getIsCheck()) {
                            binding.tvContent.setBackgroundResource(R.drawable.iv_pop_item_choise);
                        } else {
                            binding.tvContent.setBackgroundResource(R.drawable.shape_line);
                        }
                        if (model.getIsCheck()){
                            binding.tvContent.setTextColor(context.getResources().getColor(R.color.blueTextColor));
                        }else {
                            binding.tvContent.setTextColor(context.getResources().getColor(R.color.blackTextColor));
                        }
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.select_pop_item;
                    }
                };
                //嵌套内部recycleview
                adapterIn.setDataList(model.getSelectModelList());
                binding.selectRecIn.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
                binding.selectRecIn.setAdapter(adapterIn);
                adapterIn.setOnItemClick(new ItemClickListener<SelectModel>() {
                    @Override
                    public void onItemClicked(View veiw, SelectModel data) {
                        handleCheck(model.getSelectModelList(), data);
                        adapterIn.setDataList(model.getSelectModelList());
                        if (data.getSelectModelList().size() == 0) {


                        } else {
                            /**
                             * 移除当前item下方同级别item
                             * */
                            if (adapter.getDataList().size() > position + 1) {
                                while (adapter.getDataList().get(position + 1).getType().equals(data.getType()) || adapter.getDataList().get(position + 1).getType().equals("")) {
                                    cleanData(adapter.getDataList().get(position+1).getSelectModelList());
                                    adapter.getDataList().remove(position + 1);
                                    if (adapter.getDataList().size() == position + 1) {
                                        break;
                                    }
                                }
                            }
                            if (data.getIsCheck()){
                            adapter.getDataList().add(position + 1, data);}
                            adapter.notifyDataSetChanged();
                        }
                    }
                });


            }

            @Override
            public int getLayoutId() {
                return R.layout.item_select_pop;
            }

        };

        adapter.setDataList(selectModelList);
        selectPopUpBinding.selectOutRec.setLayoutManager(new LinearLayoutManager(context));
        selectPopUpBinding.selectOutRec.setAdapter(adapter);
        selectPopUpBinding.canclel.setOnClickListener(this);
        selectPopUpBinding.ivClose.setOnClickListener(this );
        selectPopUpBinding.confirm.setOnClickListener(this);

    }


    /**
     * 设置选中数据处理
     */
    private void handleCheck(List<SelectModel> list, SelectModel selectModel) {
        if (selectModel.getIsCheck()){
            selectModel.setIsCheck(false);
        }else {
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getId().equals(selectModel.getId())) {
                    list.get(i).setIsCheck(false);
                } else {
                    list.get(i).setIsCheck(true);
                }
            }
        }

    }

    /**
     * 清除数据
     */
    private void cleanData(List<SelectModel> selectModelList) {
        for (int i = 0; i < selectModelList.size(); i++) {
            selectModelList.get(i).setIsCheck(false);
        }
    }

    /**
     * 重置数据
     * */
    private void reCoverData(){
        selectModelList=new Gson().fromJson(listJsonString,new TypeToken<List<SelectModel>>() {}.getType());
        adapter.setDataList(selectModelList);
    }


    public void initPopWindow() {
        this.setContentView(view);
        selectPopUpBinding = DataBindingUtil.bind(view);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.canclel) {
            reCoverData();//重置
        }
        if (v.getId()==R.id.iv_close){
            this.dismiss();
        }
        if (v.getId()==R.id.confirm){
            onSelectedListener.onSelected(selectModelList);
        }

    }

    /**
     * 设置确认回调
     * */
    public interface OnSelectedListener{
        void onSelected(List<SelectModel> selectModelList);
    }

    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }
}




