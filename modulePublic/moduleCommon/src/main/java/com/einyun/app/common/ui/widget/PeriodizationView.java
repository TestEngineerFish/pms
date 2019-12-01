package com.einyun.app.common.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.R;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.databinding.FragmentOgselectfBinding;
import com.einyun.app.common.databinding.ItemBlockChooseBinding;
import com.einyun.app.common.ui.component.blockchoose.viewmodel.BlockChooseVMFactory;
import com.einyun.app.common.ui.component.blockchoose.viewmodel.BlockChooseViewModel;
import com.einyun.app.library.uc.usercenter.model.OrgModel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PeriodizationView extends DialogFragment implements ItemClickListener<OrgModel> {
    FragmentOgselectfBinding binding;
    BlockChooseViewModel viewModel;
    private Activity activity;
    private String userId;
    List<OrgModel> selectOrgs = new CopyOnWriteArrayList<>();
    private PeriodizationView periodizationView = null;

    public  PeriodizationView getInstance() {
//        this.activity = activity;
        if (periodizationView == null) {
            return new PeriodizationView();
        }
        return periodizationView;
    }

    RVBindingAdapter<ItemBlockChooseBinding, OrgModel> adapter;
    String blockId = "";
    TagAdapter tagAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ogselectf, container, false);
        viewModel = new ViewModelProvider(this, new BlockChooseVMFactory()).get(BlockChooseViewModel.class);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        getDialog().setCanceledOnTouchOutside(true);

        initData();
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//        window.setWindowAnimations(R.style.dialogWindowAnim);
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setGravity(Gravity.TOP);
        wlp.y=R.dimen.px_500;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        window.setAttributes(wlp);

    }

    private void initData() {
        userId = "63879813097586693";
        viewModel.loadFromCache().observe(this, models -> {
            if (models != null) {
                selectOrgs.addAll(models);
                loadTags();
                blockId = selectOrgs.get(selectOrgs.size() - 1).getId();
            }
            viewModel.queryOrgs(userId, blockId);
        });
        viewModel.orgList.observe(this, orgModels -> {

            loadData(orgModels);
        });
    }

    private void loadTags() {
        if (tagAdapter == null) {
            tagAdapter = new TagAdapter();
            binding.rvTags.setAdapter(tagAdapter);
            tagAdapter.setItemClickListener((ItemClickListener<OrgModel>) (veiw, data)
                    -> switchOrgTag(data));
        }
        tagAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(View veiw, OrgModel orgModel) {
        if (orgModel.getGrade().equals(DataConstants.KEY_ORG_DIVIDE)) {
            viewModel.saveBlock2Local(orgModel.getId(), orgModel.getName(), orgModel.getCode());
            viewModel.saveChache2Local(selectOrgs);
        } else {
            selectOrgs.add(orgModel);
            loadTags();
            viewModel.queryOrgs(userId, orgModel.getId());
        }
    }

    public class TagAdapter extends RecyclerView.Adapter<TagViewHolder> {
        ItemClickListener<OrgModel> itemClickListener;

        public void setItemClickListener(ItemClickListener listener) {
            this.itemClickListener = listener;
        }

        @NonNull
        @Override
        public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(getActivity()).inflate(com.einyun.app.common.R.layout.item_block_choose_tag, parent, false);
            TagViewHolder viewHolder = new TagViewHolder(root);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
            String tag = selectOrgs.get(position).getName();
            holder.text1.setText(tag);
            holder.itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClicked(holder.itemView, selectOrgs.get(position));
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

        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
//            text1.setTextColor(getColorPrimary());
            text2 = itemView.findViewById(android.R.id.text2);
//            text2.setTextColor(getColorPrimary());
        }
    }

    public void switchOrgTag(OrgModel model) {
        OrgModel lastOrg = selectOrgs.get(selectOrgs.size() - 1);
        if (!model.getId().equals(lastOrg)) {
            for (OrgModel orgModel : selectOrgs) {
                if (orgModel.getLevel() > model.getLevel()) {
                    selectOrgs.remove(orgModel);
                }
            }
            viewModel.queryOrgs(userId, model.getId());
        }
    }

    private void loadData(List<OrgModel> orgModels) {
        if (adapter == null) {
            adapter = new RVBindingAdapter<ItemBlockChooseBinding, OrgModel>(getActivity(), com.einyun.app.common.BR.org) {
                @Override
                public void onBindItem(ItemBlockChooseBinding binding, OrgModel model, int pos) {
                    if (!model.getGrade().equals(DataConstants.KEY_ORG_DIVIDE)) {
                        binding.ivRightselect.setVisibility(View.GONE);
                        binding.ivRightSelected.setVisibility(View.GONE);
                    } else {
                        binding.ivRight.setVisibility(View.GONE);
                        binding.ivRightSelected.setVisibility(View.GONE);
                    }
                }

                @Override
                public int getLayoutId() {
                    return com.einyun.app.common.R.layout.item_block_choose;
                }
            };
        }
        adapter.setOnItemClick(this);
        adapter.setDataList(orgModels);
        binding.rvOrgList.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false));
        binding.rvOrgList.setAdapter(adapter);
//        LinearLayoutManager layoutManage = new GridLayoutManager(this, 3);
        LinearLayoutManager layoutManage = new LinearLayoutManager(getActivity());
        layoutManage.setOrientation(RecyclerView.HORIZONTAL);
        binding.rvTags.setLayoutManager(layoutManage);
    }

    @Override
    public int show(@NonNull FragmentTransaction transaction, @Nullable String tag) {
        return super.show(transaction, tag);
    }
}
