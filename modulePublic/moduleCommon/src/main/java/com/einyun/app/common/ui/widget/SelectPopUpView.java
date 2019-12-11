package com.einyun.app.common.ui.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.R;
import com.einyun.app.common.databinding.ItemSelectPopBinding;
import com.einyun.app.common.databinding.SelectPopItemBinding;
import com.einyun.app.common.databinding.SelectPopUpBinding;
import com.einyun.app.common.model.SelectModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SelectPopUpView extends PopupWindow implements View.OnClickListener {
    private static final String TAG = "CustomPopWindow";
    public static final String SELECT_LINE = "SELECT_LINE";//条线
    public static final String SELECT_ORDER_TYPE = "SELECT_ORDER_TYPE";//工单类型1
    public static final String SELECT_ORDER_TYPE2 = "SELECT_ORDER_TYPE2";//工单类型2
    public static final String SELECT_ORDER_TYPE3 = "SELECT_ORDER_TYPE3";//工单类型3
    public static final String SELECT_IS_OVERDUE = "SELECT_IS_OVERDUE";//是否超期;
    private View view;
    private Activity context;
    private AdapterView.OnItemClickListener mListener;
    private RVBindingAdapter<ItemSelectPopBinding, SelectModel> adapter;//外部适配器
    private SelectPopUpBinding selectPopUpBinding;
    private List<SelectModel> selectModelList;
    private List<SelectModel> selectModelListOrig = new ArrayList<>();//复制原始数据
    private String listJsonString;
    private OnSelectedListener onSelectedListener;
    private HashMap<String,SelectModel> selectedMap=new HashMap<>();

    public SelectPopUpView(Activity context, List<SelectModel> selectModelList) {
        super(context);
        this.context = context;
        this.selectModelList = selectModelList;
        view = LayoutInflater.from(context).inflate(R.layout.select_pop_up, null);
        initPopWindow();
        initView();
    }


    public void initView() {
        listJsonString = new Gson().toJson(selectModelList);

        for (SelectModel selectModel : selectModelList) {
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


                //二级列表适配器
                RVBindingAdapter<SelectPopItemBinding, SelectModel> adapterIn = new RVBindingAdapter<SelectPopItemBinding, SelectModel>(context, com.einyun.app.common.BR.selects) {

                    @Override
                    public void onBindItem(SelectPopItemBinding binding, SelectModel model, int position) {
                        if (model.getContent().equals("是")||model.getContent().equals("否")){
                            binding.tvChoose.setVisibility(View.VISIBLE);
                         if (model.getIsCheck()){
                             binding.tvChoose.setImageResource(R.drawable.blue_oval);
                             binding.tvContent.setTextColor(context.getResources().getColor(R.color.blueTextColor));
                         }else {
                             binding.tvChoose.setImageResource(R.drawable.blue_oval_stock);
                             binding.tvContent.setTextColor(context.getResources().getColor(R.color.blackTextColor));
                         }
                        }else {
                            binding.tvChoose.setVisibility(View.GONE);
                        if (model.getIsCheck()) {
                            binding.tvContent.setBackgroundResource(R.drawable.iv_pop_item_choise);
                        } else {
                            binding.tvContent.setBackgroundResource(R.drawable.shape_line);
                        }
                        if (model.getIsCheck()) {
                            binding.tvContent.setTextColor(context.getResources().getColor(R.color.blueTextColor));
                        } else {
                            binding.tvContent.setTextColor(context.getResources().getColor(R.color.blackTextColor));
                        }}

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
                        onSelected(data);
                        handleCheck(model.getSelectModelList(), data);
                        adapterIn.setDataList(model.getSelectModelList());
                        if (data.getSelectModelList().size() == 0) {


                        } else {
                            /**
                             * 移除当前item下方同级别item
                             * */
                            if (adapter.getDataList().size() > position + 1) {
                                while (adapter.getDataList().get(position + 1).getType().equals(data.getType()) || adapter.getDataList().get(position + 1).getType().equals("")) {
                                    cleanData(adapter.getDataList().get(position + 1).getSelectModelList());
                                    adapter.getDataList().remove(position + 1);
                                    if (adapter.getDataList().size() == position + 1) {
                                        break;
                                    }
                                }
                            }
                            if (data.getIsCheck()) {
                                adapter.getDataList().add(position + 1, data);
                            }
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
        selectPopUpBinding.ivClose.setOnClickListener(this);
        selectPopUpBinding.confirm.setOnClickListener(this);
    }

    protected void onSelected(SelectModel data) {
        if(SELECT_IS_OVERDUE.equals(data.getType())){
            selectedMap.put(SELECT_IS_OVERDUE,data);
        } else if (!TextUtils.isEmpty(data.getTypeId()) && !TextUtils.isEmpty(data.getParentId())
                && data.getParentId().equals(data.getTypeId())){
            selectedMap.put(SELECT_LINE,data);
        }else if(data.getParentId().equals(selectedMap.get(SELECT_LINE).getId())){
            selectedMap.put(SELECT_ORDER_TYPE,data);
        }else if(data.getParentId().equals(selectedMap.get(SELECT_ORDER_TYPE).getId())){
            selectedMap.put(SELECT_ORDER_TYPE2,data);
        }else if(data.getParentId().equals(selectedMap.get(SELECT_ORDER_TYPE2).getId())){
            selectedMap.put(SELECT_ORDER_TYPE3,data);
        }
    }


    /**
     * 设置选中数据处理
     */
    private void handleCheck(List<SelectModel> list, SelectModel selectModel) {
        if (selectModel.getIsCheck()) {
            selectModel.setIsCheck(false);
        } else {
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
     */
    private void reCoverData() {
        selectModelList = new Gson().fromJson(listJsonString, new TypeToken<List<SelectModel>>() {
        }.getType());
        selectedMap.clear();
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
        ColorDrawable dw = new ColorDrawable(0x00FFFFFF);
        //设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.BottomDialogAnimation);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.canclel) {
            reCoverData();//重置
        }
        if (v.getId() == R.id.iv_close) {
            this.dismiss();
        }
        if (v.getId() == R.id.confirm) {
            onSelectedListener.onSelected(selectedMap);
            this.dismiss();
        }

    }

    /**
     * 设置确认回调
     */
    public interface OnSelectedListener {
        void onSelected(Map<String,SelectModel> selected);
    }

    public SelectPopUpView setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
        return this;
    }
}




