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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.R;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.databinding.FragmentOgselectfBinding;
import com.einyun.app.common.databinding.ItemBlockChooseBinding;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.component.blockchoose.viewmodel.BlockChooseVMFactory;
import com.einyun.app.common.ui.component.blockchoose.viewmodel.BlockChooseViewModel;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PeriodizationView extends DialogFragment implements ItemClickListener<OrgModel>, View.OnClickListener {
    FragmentOgselectfBinding binding;
    BlockChooseViewModel viewModel;
    private String userId;
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    List<OrgModel> selectOrgs = new CopyOnWriteArrayList<>();
    private OnPeriodSelectListener onPeriodSelectListener;
    //设置分期选择监听

    RVBindingAdapter<ItemBlockChooseBinding, OrgModel> adapter;
    String blockId = "";
    TagAdapter tagAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ogselectf, container, false);
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
        wlp.y = R.dimen.px_300;
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
        userId = userModuleService.getUserId();
        viewModel.loadFromCache().observe(this, models -> {
            if (models != null) {
                binding.periodSelectDefault.setVisibility(View.GONE);
                selectOrgs.addAll(models);
                loadTags();
                blockId = selectOrgs.get(selectOrgs.size() - 1).getId();

            }
            viewModel.queryOrgs(userId, blockId);
        });
//        viewModel.queryOrgs(userId, blockId);
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
        if (selectOrgs.size() >= 1 && selectOrgs.get(selectOrgs.size() - 1).getName().equals(getContext().getResources().getString(R.string.text_choose_org))) {
            selectOrgs.remove(selectOrgs.size() - 1);
        }
        binding.periodSelectDefault.setVisibility(View.GONE);
        if (orgModel.getGrade().equals(DataConstants.KEY_ORG_DIVIDE)) {
            viewModel.saveBlock2Local(orgModel.getId(), orgModel.getName(), orgModel.getCode());
            viewModel.saveChache2Local(selectOrgs);
            Log.d("test", "zhixingitemcliected");
            onPeriodSelectListener.onPeriodSelectListener(orgModel);
            this.dismiss();
        } else {
            selectOrgs.add(orgModel);
            OrgModel orgModel1 = new OrgModel();
            orgModel1.setName(getContext().getResources().getString(R.string.text_choose_org));
            selectOrgs.add(orgModel1);
            loadTags();
            viewModel.queryOrgs(userId, orgModel.getId());

        }
    }

    @Override
    public void onClick(View v) {
        this.dismiss();

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
            if (tag.equals(getContext().getResources().getString(R.string.text_choose_org))) {
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

    public void switchOrgTag(OrgModel model) {
        selectOrgs.remove(selectOrgs.get(selectOrgs.size() - 1));
        OrgModel lastOrg = selectOrgs.get(selectOrgs.size() - 1);
        try {
            if (!model.getId().equals(lastOrg)) {
                for (OrgModel orgModel : selectOrgs) {
                    if (orgModel.getLevel() > model.getLevel()) {
                        selectOrgs.remove(orgModel);
                    }
                }
                OrgModel orgModel1 = new OrgModel();
                orgModel1.setName(getContext().getResources().getString(R.string.text_choose_org));
                selectOrgs.add(orgModel1);
                viewModel.queryOrgs(userId, model.getId());
            }
        } catch (Exception e) {

        }
    }

    private void loadData(List<OrgModel> orgModels) {
        if (adapter == null) {
            adapter = new RVBindingAdapter<ItemBlockChooseBinding, OrgModel>(getActivity(), com.einyun.app.common.BR.org) {
                @Override
                public void onBindItem(ItemBlockChooseBinding binding, OrgModel model, int pos) {
                    if (model.getGrade().equals(DataConstants.KEY_ORG_DIVIDE)) {
                        binding.ivRightselect.setVisibility(View.VISIBLE);
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

    /**
     * 设置分期选择监听
     */
    public interface OnPeriodSelectListener {
        void onPeriodSelectListener(OrgModel orgModel);
    }

    public void setPeriodListener(OnPeriodSelectListener onPeriodSelectListener) {
        this.onPeriodSelectListener = onPeriodSelectListener;

    }
}
