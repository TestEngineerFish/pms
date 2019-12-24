package com.einyun.app.common.ui.widget;

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

import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.R;
import com.einyun.app.common.databinding.ItemSelectPopBinding;
import com.einyun.app.common.databinding.SelectPopItemBinding;
import com.einyun.app.common.databinding.SelectPopUpBinding;
import com.einyun.app.common.model.SelectModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SelectPopUpView extends PopupWindow implements View.OnClickListener {
    private static final String TAG = "CustomPopWindow";
    public static final String SELECT_LINE_TYPES = "SELECT_LINE_TYPES";//分类，环境，秩序，工程
    public static final String SELECT_LINE = "SELECT_LINE";//条线
    public static final String SELECT_ROOT = "SELECT_ROOT";//根
    public static final String SELECT_ORDER_TYPE = "SELECT_ORDER_TYPE";//工单类型1
    public static final String SELECT_ORDER_TYPE1 = "SELECT_ORDER_TYPE1";//工单类型2
    public static final String SELECT_ORDER_TYPE2 = "SELECT_ORDER_TYPE2";//工单类型2
    public static final String SELECT_ORDER_TYPE3 = "SELECT_ORDER_TYPE3";//工单类型3
    public static final String SELECT_IS_OVERDUE = "SELECT_IS_OVERDUE";//是否超期;
    public static final String SELECT_TIME_CIRCLE = "SELECT_TIME_CIRCLE";//周期;
    public static final String SELECT_GRID= "SELECT_GRID";//网格;
    public static final String SELECT_BUILDING= "SELECT_BUILDING";//楼栋;
    public static final String SELECT_UNIT= "SELECT_UNIT";//单元;
    public static final String SELECT_DATE = "SELECT_DATE";
    public static final String SELECT_AREA = "SELECT_AREA";//报修区域
    public static final String SELECT_AREA_FIR = "SELECT_AREA_FIR";//报修大类
    public static final String SELECT_AREA_SEC = "SELECT_AREA_SEC";//报修小类
    public static final String SELECT_AREA_THIR = "SELECT_THIR";//报修第三级

    private View view;
    private Activity context;
    private AdapterView.OnItemClickListener mListener;
    private RVBindingAdapter<ItemSelectPopBinding, SelectModel> adapter;//外部适配器
    private SelectPopUpBinding selectPopUpBinding;
    private List<SelectModel> selectModelList;
    private List<SelectModel> selectModelListOrig = new ArrayList<>();//复制原始数据
    private String listJsonString;
    private OnSelectedListener onSelectedListener;
    private HashMap<String, SelectModel> selectedMap = new HashMap<>();

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
                        if (model.getContent().equals("是") || model.getContent().equals("否")) {
                            binding.tvChoose.setVisibility(View.VISIBLE);
                            if (model.getIsCheck()) {
                                binding.tvChoose.setImageResource(R.drawable.blue_oval);
                                binding.tvContent.setTextColor(context.getResources().getColor(R.color.blueTextColor));
                            } else {
                                binding.tvChoose.setImageResource(R.drawable.blue_oval_stock);
                                binding.tvContent.setTextColor(context.getResources().getColor(R.color.blackTextColor));
                            }
                        } else {
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
                            }
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

    /**
     * 处理选择结果
     * @param data
     */
    protected void onSelected(SelectModel data) {
        selectedMap.put(data.getConditionType(),data);
        clearUnSelect(data);
        printSelected();
    }

    /**
     * 清空未选择项结果
     */
    private void clearUnSelect(SelectModel model) {
        List<SelectModel> grades= getItemsByCondidition(model.getConditionType(),selectModelList);
        if(grades!=null){
            for(SelectModel selectModel:grades){
                clearChildren(selectModel);
            }
        }
    }

    /**
     * 清空子项选择结果
     */
    private void clearChildren(SelectModel model){
        List<SelectModel> children=model.getSelectModelList();
        if(children!=null&&children.size()>0){
            for(SelectModel selectModel:children){
                if(selectModel==model){
                    continue;
                }
                if(selectedMap.containsKey(selectModel.getConditionType())){
                    selectedMap.remove(selectModel.getConditionType());
                }
                clearChildren(selectModel);
            }
        }
    }

    /**
     * 获取同级别model
     * @param condititon
     * @return
     */
    private List<SelectModel> getItemsByCondidition(String  condititon, List<SelectModel> models){
        List<SelectModel> list=new ArrayList<>();
        for(SelectModel model:models){
            if(model.getConditionType().equals(condititon)){
                list.add(model);
            }else{
                List<SelectModel> children=model.getSelectModelList();
                if(children!=null){
                    list.addAll(getItemsByCondidition(condititon,children));
                }
            }
        }
        return list;
    }

    /**
     * 打印选择结果
     */
    private void printSelected(){
        Logger.d("selected result->");
        StringBuffer buffer=new StringBuffer();
        for(String key:selectedMap.keySet()){
            buffer.append(key+",");
        }
        Logger.d(buffer.toString());
    }


        /**
         * 设置选中数据处理
         */
        private void handleCheck (List < SelectModel > list, SelectModel selectModel){
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
        private void cleanData (List < SelectModel > selectModelList) {
            for (int i = 0; i < selectModelList.size(); i++) {
                selectModelList.get(i).setIsCheck(false);
            }
        }

        /**
         * 重置数据
         */
        private void reCoverData () {
            selectModelList = new Gson().fromJson(listJsonString, new TypeToken<List<SelectModel>>() {
            }.getType());
            selectedMap.clear();
            adapter.setDataList(selectModelList);
        }


        public void initPopWindow () {
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
        public void onClick (View v){
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
            void onSelected(Map<String, SelectModel> selected);
        }

        public SelectPopUpView setOnSelectedListener (OnSelectedListener onSelectedListener){
            this.onSelectedListener = onSelectedListener;
            return this;
        }
    }





