package com.einyun.app.pms.pointcheck.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoShowActivity;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.common.utils.HttpUrlUtil;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.pms.pointcheck.BR;
import com.einyun.app.pms.pointcheck.R;
import com.einyun.app.pms.pointcheck.databinding.ActivityPointCheckDetialBinding;
import com.einyun.app.pms.pointcheck.databinding.ItemPointCheckProjectBinding;
import com.einyun.app.pms.pointcheck.listener.SQOnRecycleItemListener;
import com.einyun.app.pms.pointcheck.model.PointCheckDetialModel;
import com.einyun.app.pms.pointcheck.model.ProjectContentItemModel;
import com.einyun.app.pms.pointcheck.viewmodel.PointCheckDetialViewModel;
import com.einyun.app.pms.pointcheck.viewmodel.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck.ui
 * @ClassName: PointCheckDetialActivity
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/09 11:42
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/09 11:42
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
@Route(path = RouterUtils.ACTIVITY_POINT_CHECK_DETIAL)
public class PointCheckDetialActivity extends BaseHeadViewModelActivity<ActivityPointCheckDetialBinding, PointCheckDetialViewModel> implements SQOnRecycleItemListener {
    private  final String TAG ="PointCheckDetialAct";
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String checkId;
//    ProjectContentAdapter contentAdapter;
    RVBindingAdapter<ItemPointCheckProjectBinding,ProjectContentItemModel> adapter;
    private SPPhotoAdapter spphotoAdapter;
    PointCheckDetialModel detialModel;


    @Override
    protected PointCheckDetialViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(PointCheckDetialViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_point_check_detial;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.title_point_check_detial);
        if (!TextUtils.isEmpty(checkId)) {
            viewModel.queryDetial(checkId).observe(this, model -> {
                updateUI(model);
            });
        }
    }


    private void updateUI(PointCheckDetialModel model) {
        if(model==null){
            return;
        }
        this.detialModel=model;
        binding.frameSpace.setVisibility(View.GONE);
        binding.layoutBrief.setVariable(BR.checkpoint,detialModel);
        binding.tvCheckContent.setText(model.getRemark());
        binding.layoutBrief.itemHasAttachment.setVisibility(View.GONE);
        loadProjectContents(model);
        if(model.getIsPic()>0||(model.getImages()!=null&&model.getImages().size()>0)){
            binding.llPic.setVisibility(View.VISIBLE);
            loadPics(model);
        }else{
            binding.llPic.setVisibility(View.GONE);
        }
    }

    /**
     * 加载点检事项内容
     *
     */
    private void loadProjectContents(PointCheckDetialModel detialModel) {
        if(detialModel==null){
            return;
        }
        List<ProjectContentItemModel> contentItemModels = detialModel.getContentList();
        if (adapter == null) {
            adapter = new RVBindingAdapter<ItemPointCheckProjectBinding, ProjectContentItemModel>(this,BR.content) {
                @Override
                public void onBindItem(ItemPointCheckProjectBinding binding, ProjectContentItemModel model,int position) {
                    binding.tvCheckContent.setText(model.getCheckContent());
                    binding.tvProjectName.setText(getResources().getString(R.string.name_project) + (position + 1));
                    if (model.getCheckType() == 2) {//判断型
                        binding.tvCheckRange.setText(R.string.item_qualified);
                        binding.tvCheckType.setText(R.string.check_type_judge);
                        if (model.getQualified() > 0) { //合格
                            binding.tvCheckResult.setText(R.string.item_qualified);
                        } else {
                            binding.tvCheckResult.setText(R.string.item_no_qualified);
                        }
                    } else { //填空型
                        binding.tvCheckType.setText(R.string.check_type_range);
                        binding.tvCheckResult.setText(model.getCheckResult() + "");
                        binding.tvCheckRange.setText(model.getMinValue()+"-"+model.getMaxVal());
                    }
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_point_check_project;
                }
            };
            binding.rvProjects.setLayoutManager(new LinearLayoutManager(this));
            binding.rvProjects.setNestedScrollingEnabled(false);
            binding.rvProjects.setAdapter(adapter);
        }
        adapter.addAll(contentItemModels);
    }

    /**
     * load pics
     */
    private void loadPics(PointCheckDetialModel detialModel){
        if(detialModel==null){
            return;
        }
        if(spphotoAdapter==null){
            spphotoAdapter = new SPPhotoAdapter(this, detialModel.getImages());
            spphotoAdapter.addSQRecycleItemListener(this);
            binding.rvPhotoSelector.setLayoutManager(new LinearLayoutManager(
                    this,
                    LinearLayoutManager.HORIZONTAL,
                    false));
            binding.rvPhotoSelector.addItemDecoration(new SpacesItemDecoration());
            binding.rvPhotoSelector.setAdapter(spphotoAdapter);
        }else{
            spphotoAdapter.updateList(detialModel.getImages());
        }
    }

    /**
     * item click for pics
     * @param v
     * @param position
     */
    @Override
    public void SQOnRecycleItemClick(View v, int position) {
        if(detialModel==null){
            return;
        }
        PhotoShowActivity.start(this,position,(ArrayList<String>) detialModel.getImagePaths());
    }




    /**
     * photo adapter
     */
    class SPPhotoAdapter extends RecyclerView.Adapter<PicViewHolder> {

        private final Context context;
        private LayoutInflater inflater;

        private List<PicUrlModel> selectedPhotos;
        private SQOnRecycleItemListener sqmOnItemClickListener;

        public void updateList(List<PicUrlModel> list){
            if(selectedPhotos==null){
                selectedPhotos=list;
            }else{
                selectedPhotos.clear();
                selectedPhotos.addAll(list);
            }
            notifyDataSetChanged();
        }

        public SPPhotoAdapter(Context context, List<PicUrlModel> selectedPhotos) {
            this.selectedPhotos = selectedPhotos;
            this.context = context;
        }


        @NonNull
        @Override
        public PicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            if (inflater == null) {
                inflater = LayoutInflater.from(viewGroup.getContext());
            }
            View rootView = inflater.inflate(R.layout.item_photo_select, viewGroup, false);
            return new PicViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(@NonNull PicViewHolder holder, int position) {
            holder.imgPhoto.setVisibility(View.VISIBLE);
            holder.layout_add.setVisibility(View.GONE);
            PicUrlModel model = selectedPhotos.get(position);
            Log.e(TAG, "photo = " + model.getPath());
            String imageUrl= HttpUrlUtil.getImageServerUrl(model.getPath());
            Glide.with(context)
                    .load(imageUrl)
                    .centerCrop()
                    .placeholder(com.einyun.app.common.R.mipmap.place_holder_img)
                    .error(com.einyun.app.common.R.mipmap.place_holder_img)
                    .into(holder.imgPhoto);

            if (sqmOnItemClickListener != null) {
                holder.itemView.setOnClickListener(v ->
                        sqmOnItemClickListener.SQOnRecycleItemClick(holder.itemView, position));
            }
        }

        @Override
        public int getItemCount() {
            return selectedPhotos==null?0:selectedPhotos.size();
        }

        public void addSQRecycleItemListener(SQOnRecycleItemListener listener) {
            this.sqmOnItemClickListener = listener;
        }
    }

    /**
     * 图片ViewHolder
     */
    class PicViewHolder extends RecyclerView.ViewHolder {

        private final View layout_add;
        ImageView imgPhoto;

        public PicViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            layout_add = itemView.findViewById(R.id.layout_add);
        }
    }

}
