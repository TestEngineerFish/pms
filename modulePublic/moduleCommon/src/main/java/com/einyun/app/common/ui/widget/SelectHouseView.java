package com.einyun.app.common.ui.widget;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.util.ScreenUtils;
import com.einyun.app.common.BR;
import com.einyun.app.common.R;
import com.einyun.app.common.databinding.FragmentWorkTableSelectBinding;
import com.einyun.app.common.databinding.ItemHouseChooseBinding;
import com.einyun.app.common.databinding.ItemWorkTypeChooseBinding;
import com.einyun.app.common.ui.component.blockchoose.viewmodel.BlockChooseVMFactory;
import com.einyun.app.common.ui.component.blockchoose.viewmodel.BlockChooseViewModel;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.uc.usercenter.model.HouseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SelectHouseView extends DialogFragment implements ItemClickListener<HouseModel>, View.OnClickListener {
    FragmentWorkTableSelectBinding binding;
    BlockChooseViewModel viewModel;
    private String divideId;
    List<HouseModel> selectOrgs = new CopyOnWriteArrayList<>();
    private OnWorkTypeSelectListener onWorkTypeSelectListener;
    //设置分期选择监听

    RVBindingAdapter<ItemHouseChooseBinding, HouseModel> adapter;
    TagAdapter tagAdapter;
    List<HouseModel> houseModels = new ArrayList<>();

    public SelectHouseView(String divideId) {
        this.divideId = divideId;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_work_table_select, container, false);
        viewModel = new ViewModelProvider(this, new BlockChooseVMFactory()).get(BlockChooseViewModel.class);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding.periodViewClose.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        getDialog().setCanceledOnTouchOutside(true);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.period_view_dialog);
        initData();
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setGravity(Gravity.TOP);
        window.setDimAmount(0);
        wlp.y = ScreenUtils.getMetricsHeight(getContext()) / 2;
        wlp.windowAnimations = R.style.BottomDialogAnimation;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        getActivity().getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if (event == Lifecycle.Event.ON_STOP) {
                dismiss();
            }
        });

    }


    private void initData() {
        ViewGroup.LayoutParams layoutParams = binding.rvOrgList.getLayoutParams();
        layoutParams.height = ScreenUtils.getMetricsHeight(getContext()) / 2;
        binding.type.setText(getResources().getString(R.string.text_house));
        binding.rvOrgList.setLayoutParams(layoutParams);
        binding.periodSelectDefault.setText("默认房产");
        viewModel.getHouseByCondition(divideId, null).observe(this, houseModels -> {
            this.houseModels = houseModels;
            loadData();
        });
    }

    private void loadTags() {
        if (tagAdapter == null) {
            tagAdapter = new TagAdapter();
            binding.rvTags.setAdapter(tagAdapter);
            tagAdapter.setItemClickListener((ItemClickListener<HouseModel>) (veiw, data)
                    -> switchOrgTag(data));
        }
        tagAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(View veiw, HouseModel model) {
        if (selectOrgs.size() >= 1) {
            selectOrgs.remove(selectOrgs.size() - 1);
        }
        if (selectOrgs.size() == 3 || model.getChildren() == null || model.getChildren().size() == 0) {
            selectOrgs.add(model);
            onWorkTypeSelectListener.onWorkTypeSelectListener(selectOrgs);
            this.dismiss();
        } else {
            selectOrgs.add(model);
            binding.hintText.setText(String.format(getContext().getResources().getString(R.string.text_choose_work_order_type), selectOrgs.size() == 1 ? "二" : selectOrgs.size() == 2 ? "三" : "一"));
            HouseModel orgModel1 = new HouseModel();
            orgModel1.setName(selectOrgs.size() == 0 ? "请选择楼栋" : selectOrgs.size() == 1 ? "请选择单元" : "请选择房屋");
            selectOrgs.add(orgModel1);

            loadTags();
            adapter.setDataList(model.getChildren());
        }
    }

    @Override
    public void onClick(View v) {
        this.dismiss();

    }

    public class TagAdapter extends RecyclerView.Adapter<TagViewHolder> {
        ItemClickListener<HouseModel> itemClickListener;

        public void setItemClickListener(ItemClickListener listener) {
            this.itemClickListener = listener;
        }

        @NonNull
        @Override
        public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(getActivity()).inflate(R.layout.item_block_choose_tag, parent, false);
            TagViewHolder viewHolder = new TagViewHolder(root);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
            String tag = selectOrgs.get(position).getName();
            if (tag.equals("请选择房屋") || tag.equals("请选择单元")) {
                holder.imageView.setImageResource(R.drawable.blue_oval_stock);
            } else {
                holder.imageView.setImageResource(R.drawable.blue_oval);
            }
            holder.text1.setText(tag);
            holder.itemView.setOnClickListener(v -> {
                try {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClicked(holder.itemView, selectOrgs.get(position));
                    }
                } catch (Exception e) {

                }

            });
        }

        @Override
        public int getItemCount() {
            return selectOrgs == null ? 0 : selectOrgs.size();
        }
    }

    class TagViewHolder extends RecyclerView.ViewHolder {

        public TextView text1;
        public TextView text2;
        public ImageView imageView;

        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
            imageView = itemView.findViewById(R.id.iv_blockchoose_toptag);
        }
    }

    public void switchOrgTag(HouseModel model) {
        selectOrgs.remove(selectOrgs.get(selectOrgs.size() - 1));

        List<HouseModel> list = new ArrayList<>();
        for (HouseModel data : selectOrgs) {
            if (data.getId().equals(model.getId())) {
                break;
            }
            list.add(data);
        }
        selectOrgs = list;
        binding.hintText.setText(String.format(getContext().getResources().getString(R.string.text_choose_work_order_type), selectOrgs.size() == 1 ? "二" : selectOrgs.size() == 2 ? "三" : "一"));
        HouseModel orgModel1 = new HouseModel();
        orgModel1.setName(selectOrgs.size() == 0 ? "请选择楼栋" : selectOrgs.size() == 1 ? "请选择单元" : "请选择房屋");
        selectOrgs.add(orgModel1);
        if (selectOrgs.size() == 1) {
            adapter.setDataList(houseModels);
        } else {
            adapter.setDataList(model.getChildren());
        }
        loadTags();
    }


    private void loadData() {
        if (adapter == null) {
            adapter = new RVBindingAdapter<ItemHouseChooseBinding, HouseModel>(getActivity(), BR.houseModel) {
                @Override
                public void onBindItem(ItemHouseChooseBinding binding, HouseModel model, int pos) {

                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_house_choose;
                }
            };
        }
        adapter.setOnItemClick(this);
        binding.rvOrgList.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false));
        binding.rvOrgList.setAdapter(adapter);
//        LinearLayoutManager layoutManage = new GridLayoutManager(this, 3);
        LinearLayoutManager layoutManage = new LinearLayoutManager(getActivity());
        layoutManage.setOrientation(RecyclerView.HORIZONTAL);
        binding.rvTags.setLayoutManager(layoutManage);
        adapter.setDataList(this.houseModels);
    }

    @Override
    public int show(@NonNull FragmentTransaction transaction, @Nullable String tag) {
        return super.show(transaction, tag);
    }

    /**
     * 设置分期选择监听
     */
    public interface OnWorkTypeSelectListener {
        void onWorkTypeSelectListener(List<HouseModel> model);
    }

    public void setWorkTypeListener(OnWorkTypeSelectListener onWorkTypeSelectListener) {
        this.onWorkTypeSelectListener = onWorkTypeSelectListener;

    }
}
