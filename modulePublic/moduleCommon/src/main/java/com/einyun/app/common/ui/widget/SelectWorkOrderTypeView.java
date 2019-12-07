package com.einyun.app.common.ui.widget;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.BR;
import com.einyun.app.common.R;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.databinding.FragmentOgselectfBinding;
import com.einyun.app.common.databinding.FragmentWorkTableSelectBinding;
import com.einyun.app.common.databinding.ItemBlockChooseBinding;
import com.einyun.app.common.databinding.ItemWorkTypeChooseBinding;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.component.blockchoose.viewmodel.BlockChooseVMFactory;
import com.einyun.app.common.ui.component.blockchoose.viewmodel.BlockChooseViewModel;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.uc.usercenter.model.OrgModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SelectWorkOrderTypeView extends DialogFragment implements ItemClickListener<DictDataModel>, View.OnClickListener {
    FragmentWorkTableSelectBinding binding;
    BlockChooseViewModel viewModel;

    List<DictDataModel> selectOrgs = new CopyOnWriteArrayList<>();
    private OnWorkTypeSelectListener onWorkTypeSelectListener;
    //设置分期选择监听

    RVBindingAdapter<ItemWorkTypeChooseBinding, DictDataModel> adapter;
    String blockId = "";
    String txId = "";
    TagAdapter tagAdapter;
    List<DictDataModel> dictDataModels = new ArrayList<>();

    public SelectWorkOrderTypeView(List<DictDataModel> dictDataModels, String txId) {
        this.dictDataModels = dictDataModels;
        this.txId = txId;
    }

    /**
     * @param txId
     */
    public SelectWorkOrderTypeView(String txId) {
        this.txId = txId;
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
        wlp.y = R.dimen.px_500;
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
        if (dictDataModels != null && dictDataModels.size() != 0) {
            loadData();
        } else {
            viewModel.getByTypeKey().observe(this, dictDataModel -> {
                this.dictDataModels = dictDataModel;
                loadData();
            });
        }
//        viewModel.loadFromCacheWorkType().observe(this, models -> {
//            if (models != null) {
//                binding.periodSelectDefault.setVisibility(View.GONE);
//                selectOrgs.addAll(models);
//                loadTags();
//                blockId = selectOrgs.get(selectOrgs.size() - 1).getId();
//
//            }
//            if (dictDataModels != null && dictDataModels.size() != 0) {
//                loadData();
//            } else {
//                viewModel.getByTypeKey().observe(this, dictDataModel -> {
//                    this.dictDataModels = dictDataModel;
//                    loadData();
//                });
//            }
//        });
    }

    private void loadTags() {
        if (tagAdapter == null) {
            tagAdapter = new TagAdapter();
            binding.rvTags.setAdapter(tagAdapter);
            tagAdapter.setItemClickListener((ItemClickListener<DictDataModel>) (veiw, data)
                    -> switchOrgTag(data));
        }
        tagAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(View veiw, DictDataModel model) {
        if (selectOrgs.size() >= 1 && selectOrgs.get(selectOrgs.size() - 1).getName().equals(getContext().getResources().getString(R.string.text_choose_work_type))) {
            selectOrgs.remove(selectOrgs.size() - 1);
        }
        binding.periodSelectDefault.setVisibility(View.GONE);
        List<DictDataModel> dictDataModels = disposeData(model.getId());
        if (dictDataModels.size() == 0) {
//            viewModel.saveChacheWorkType(selectOrgs);
            Log.d("test", "zhixingitemcliected");
            selectOrgs.add(model);
            onWorkTypeSelectListener.onWorkTypeSelectListener(selectOrgs);
            this.dismiss();
        } else {
            selectOrgs.add(model);
            DictDataModel orgModel1 = new DictDataModel();
            orgModel1.setName(getContext().getResources().getString(R.string.text_choose_work_type));
            selectOrgs.add(orgModel1);
            loadTags();
            adapter.setDataList(dictDataModels);
        }
    }

    @Override
    public void onClick(View v) {
        this.dismiss();

    }

    public class TagAdapter extends RecyclerView.Adapter<TagViewHolder> {
        ItemClickListener<DictDataModel> itemClickListener;

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
            if (tag.equals(getContext().getResources().getString(R.string.text_choose_work_type))) {
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

    public void switchOrgTag(DictDataModel model) {
        selectOrgs.remove(selectOrgs.get(selectOrgs.size() - 1));
        DictDataModel lastOrg = selectOrgs.get(selectOrgs.size() - 1);
        try {
            if (!model.getId().equals(lastOrg.getId())) {
                int size = selectOrgs.size();
                for (int i = size - 1; i < 0; i--) {
                    if (selectOrgs.get(i).getId().equals(model.getId())) {
                        break;
                    } else {
                        selectOrgs.remove(i);
                    }
                }
                if (selectOrgs.size() == 1) {
                    selectOrgs = new ArrayList<>();
                }
                DictDataModel orgModel1 = new DictDataModel();
                orgModel1.setName(getContext().getResources().getString(R.string.text_choose_work_type));
                selectOrgs.add(orgModel1);
                if (selectOrgs.size() == 1) {
                    adapter.setDataList(disposeData(txId));
                } else {
                    adapter.setDataList(disposeData(model.getId()));
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 处理数据
     *
     * @param id
     * @return
     */
    private List<DictDataModel> disposeData(String id) {
        if (dictDataModels == null || dictDataModels.size() == 0) {
            return new ArrayList<>();
        }
        List<DictDataModel> data = new ArrayList<>();
        for (DictDataModel model : dictDataModels) {
            if (model.getParentId().equals(id)) {
                data.add(model);
            }
        }
        adapter.setDataList(data);
        return data;
    }

    private void loadData() {
        if (adapter == null) {
            adapter = new RVBindingAdapter<ItemWorkTypeChooseBinding, DictDataModel>(getActivity(), BR.dictDataModel) {
                @Override
                public void onBindItem(ItemWorkTypeChooseBinding binding, DictDataModel model, int pos) {

                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_work_type_choose;
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
        adapter.setDataList(disposeData(txId));
    }

    @Override
    public int show(@NonNull FragmentTransaction transaction, @Nullable String tag) {
        return super.show(transaction, tag);
    }

    /**
     * 设置分期选择监听
     */
    public interface OnWorkTypeSelectListener {
        void onWorkTypeSelectListener(List<DictDataModel> model);
    }

    public void setWorkTypeListener(OnWorkTypeSelectListener onWorkTypeSelectListener) {
        this.onWorkTypeSelectListener = onWorkTypeSelectListener;

    }
}
