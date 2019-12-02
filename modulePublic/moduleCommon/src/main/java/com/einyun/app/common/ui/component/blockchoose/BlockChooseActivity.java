package com.einyun.app.common.ui.component.blockchoose;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.R;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.databinding.ActivityChooseOrgBinding;
import com.einyun.app.common.databinding.ItemBlockChooseBinding;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.blockchoose.viewmodel.BlockChooseVMFactory;
import com.einyun.app.common.ui.component.blockchoose.viewmodel.BlockChooseViewModel;
import com.einyun.app.library.uc.usercenter.model.OrgModel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.ui.component
 * @ClassName: BlockChooseActivity
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/14 10:00
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/14 10:00
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
@Route(path = RouterUtils.ACTIVITY_BLOCK_CHOOSE)
public class BlockChooseActivity extends BaseHeadViewModelActivity<ActivityChooseOrgBinding, BlockChooseViewModel> implements ItemClickListener<OrgModel> {

    RVBindingAdapter<ItemBlockChooseBinding, OrgModel> adapter;
    TagAdapter tagAdapter;
    List<OrgModel> selectOrgs=new CopyOnWriteArrayList<>();
    @Autowired(name = RouteKey.KEY_USER_ID)
    String userId;
    String blockId="";

    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_org;
    }

    @Override
    protected BlockChooseViewModel initViewModel() {
        return new ViewModelProvider(this, new BlockChooseVMFactory()).get(BlockChooseViewModel.class);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.title_org_list);
        binding.tvBlock.setTextColor(getColorPrimary());
        binding.tvChooseWithArrow.setTextColor(getColorPrimary());
    }

    @Override
    protected void initData() {
        super.initData();
        userId="63879813097586693";
//        userId="55614223698362369";
        viewModel.loadFromCache().observe(this, models -> {
            if(models!=null){
                selectOrgs.addAll(models);
                loadTags();
                blockId=selectOrgs.get(selectOrgs.size()-1).getId();
            }
            viewModel.queryOrgs(userId, blockId);
        });
        viewModel.orgList.observe(this, orgModels -> {
            loadData(orgModels);
        });
//        viewModel.queryOrgs(userId, blockId);
    }

    private void loadData(List<OrgModel> orgModels) {
        if (adapter == null) {
            adapter = new RVBindingAdapter<ItemBlockChooseBinding, OrgModel>(this, com.einyun.app.common.BR.org) {
                @Override
                public void onBindItem(ItemBlockChooseBinding binding, OrgModel model,int pos) {
                    if(!model.getGrade().equals(DataConstants.KEY_ORG_DIVIDE)){
                       /* binding.ivRightselect.setVisibility(View.GONE);
                        binding.ivRightSelected.setVisibility(View.GONE);
                    }else{
                        binding.ivRight.setVisibility(View.GONE);
                        binding.ivRightSelected.setVisibility(View.GONE);*/
                    }
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_block_choose;
                }
            };
        }
        adapter.setOnItemClick(this);
        adapter.setDataList(orgModels);
        binding.rvOrgList.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false));
        binding.rvOrgList.setAdapter(adapter);
//        LinearLayoutManager layoutManage = new GridLayoutManager(this, 3);
        LinearLayoutManager layoutManage=new LinearLayoutManager(this);
        layoutManage.setOrientation(RecyclerView.HORIZONTAL);
        binding.rvTags.setLayoutManager(layoutManage);
    }

    private void loadTags(){
        if(tagAdapter==null){
            tagAdapter=new TagAdapter();
            binding.rvTags.setAdapter(tagAdapter);
            tagAdapter.setItemClickListener((ItemClickListener<OrgModel>) (veiw, data)
                    -> switchOrgTag(data));
        }
        tagAdapter.notifyDataSetChanged();
    }

    public void switchOrgTag(OrgModel model){
        OrgModel lastOrg=selectOrgs.get(selectOrgs.size()-1);
        if(!model.getId().equals(lastOrg)){
            for(OrgModel orgModel:selectOrgs){
                if(orgModel.getLevel()>model.getLevel()){
                    selectOrgs.remove(orgModel);
                }
            }
            viewModel.queryOrgs(userId, model.getId());
        }
    }

    @Override
    public void onItemClicked(View veiw, OrgModel orgModel) {
        if(orgModel.getGrade().equals(DataConstants.KEY_ORG_DIVIDE)){
            viewModel.saveBlock2Local(orgModel.getId(),orgModel.getName(),orgModel.getCode());
            viewModel.saveChache2Local(selectOrgs);
            setIntentResult(orgModel);
        }else{
            selectOrgs.add(orgModel);
            loadTags();
            viewModel.queryOrgs(userId, orgModel.getId());
        }
    }

    private void setIntentResult(OrgModel model){
        Intent intent=new Intent();
        intent.putExtra(DataConstants.KEY_BLOCK_ID,model.getId());
        intent.putExtra(DataConstants.KEY_BLOCK_CODE,model.getCode());
        intent.putExtra(DataConstants.KEY_BLOCK_NAME,model.getName());
        setResult(RESULT_OK,intent);
        finish();
    }

   public class TagAdapter extends RecyclerView.Adapter<TagViewHolder>{
        ItemClickListener<OrgModel> itemClickListener;

        public void setItemClickListener(ItemClickListener listener){
            this.itemClickListener=listener;
        }

        @NonNull
        @Override
        public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View root= LayoutInflater.from(BlockChooseActivity.this).inflate(R.layout.item_block_choose_tag,parent,false);
            TagViewHolder viewHolder=new TagViewHolder(root);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
            String tag=selectOrgs.get(position).getName();
            holder.text1.setText(tag);
            holder.itemView.setOnClickListener(v -> {
                if(itemClickListener!=null){
                    itemClickListener.onItemClicked(holder.itemView,selectOrgs.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return selectOrgs==null?0:selectOrgs.size();
        }
    }

    class TagViewHolder extends RecyclerView.ViewHolder{

        public TextView text1;
        public TextView text2;
        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text1.setTextColor(getColorPrimary());
            text2=itemView.findViewById(android.R.id.text2);
            text2.setTextColor(getColorPrimary());
        }
    }



}
