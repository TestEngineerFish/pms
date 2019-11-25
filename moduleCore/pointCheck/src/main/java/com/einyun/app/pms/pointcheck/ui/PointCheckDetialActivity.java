package com.example.shimaostaff.pointcheck.ui;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shimaostaff.R;
import com.example.shimaostaff.activity.BaseActivity;
import com.example.shimaostaff.activity.PhotoShowActivity;
import com.example.shimaostaff.net.Constants;
import com.example.shimaostaff.pointcheck.listener.SQOnRecycleItemListener;
import com.example.shimaostaff.pointcheck.model.PicUrlModel;
import com.example.shimaostaff.pointcheck.model.PointCheckDetialModel;
import com.example.shimaostaff.pointcheck.model.ProjectContentItemModel;
import com.example.shimaostaff.pointcheck.model.State;
import com.example.shimaostaff.pointcheck.viewmodel.PointCheckDetialViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;;

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
public class PointCheckDetialActivity extends BaseActivity implements SQOnRecycleItemListener {
    private  final String TAG ="PointCheckDetialAct";
    PointCheckDetialViewModel viewModel;
    @Bind(R.id.tv_check_name)
    TextView tvCheckName;
    @Bind(R.id.iv_check_result)
    ImageView ivCheckResult;
    @Bind(R.id.tv_check_result)
    TextView tvCheckResult;
    @Bind(R.id.tv_check_no)
    TextView tvCheckNo;
    @Bind(R.id.tv_check_block)
    TextView tvCheckBlock;
    @Bind(R.id.tv_check_object)
    TextView tvCheckObject;
    @Bind(R.id.tv_object_address)
    TextView tvObjectAddress;
    @Bind(R.id.tv_check_has_extra)
    TextView tvCheckHasExtra;
    @Bind(R.id.tv_check_auther)
    TextView tvCheckAuther;
    @Bind(R.id.tv_check_time)
    TextView tvCheckTime;
    @Bind(R.id.rv_projects)
    RecyclerView rvProjects;
    @Bind(R.id.tv_check_content)
    TextView tvCheckContent;
    @Bind(R.id.rv_photo_selector)
    RecyclerView rvPhotoSelector;
    @Bind(R.id.ll_pic)
    LinearLayout llPic;
    private String checkId;
    ProjectContentAdapter contentAdapter;
    private SPPhotoAdapter spphotoAdapter;
    PointCheckDetialModel detialModel;


    @Override
    protected int getResourceId() {
        return R.layout.activity_check_point_detial;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        super.initView();
        viewModel = ViewModelProviders.of(this).get(PointCheckDetialViewModel.class);
        setTitle(R.string.title_point_check_detial);
        /**
         * Loading Dialog show/hide
         */
        viewModel.state.observe(this, state -> {
            if(state== State.HIDELOADING){
                hideLoading();
            }else if(state==State.SHOWLOADING){
                showLoading();
            }
        });
        if (getIntent() != null) {
            checkId = getIntent().getStringExtra("CHECK_POINT_ID");
        }
        if (!TextUtils.isEmpty(checkId)) {
            viewModel.queryDetial(checkId).observe(this, model -> {
                updateUI(model);
            });
        }
    }

    private void updateUI(PointCheckDetialModel model) {
        this.detialModel=model;
        tvCheckName.setText(model.getCheckName());
        if (model.getIsUnusual() > 0) {//异常
            tvCheckResult.setText(R.string.state_error);
            ivCheckResult.setImageResource(R.mipmap.error);
            tvCheckResult.setTextColor(getResources().getColor(R.color.check_error));
        } else {
            tvCheckResult.setText(R.string.state_ok);
            ivCheckResult.setImageResource(R.mipmap.ok);
            tvCheckResult.setTextColor(getResources().getColor(R.color.check_pass));
        }

        tvCheckBlock.setText(model.getMassifName());
        tvCheckNo.setText(model.getCheckRecordCode());
        tvCheckObject.setText(model.getCheckName());
        tvObjectAddress.setText(R.string.check_object_address);
        tvCheckHasExtra.setText(model.getSpecificLocation());
        tvCheckAuther.setText(model.getCreateName());
        tvCheckTime.setText(model.getCreateTime());
        tvCheckContent.setText(model.getRemark());
        loadProjectContents();
        if(model.getIsPic()>0||(model.getImages()!=null&&model.getImages().size()>0)){
            llPic.setVisibility(View.VISIBLE);
            loadPics();
        }else{
            llPic.setVisibility(View.GONE);
        }
    }

    /**
     * 加载点检事项内容
     *
     */
    private void loadProjectContents() {
        if(detialModel==null){
            return;
        }
        List<ProjectContentItemModel> contentItemModels = detialModel.getContentList();
        if (contentAdapter == null) {
            contentAdapter = new ProjectContentAdapter(contentItemModels);
            rvProjects.setLayoutManager(new LinearLayoutManager(this));
            rvProjects.setNestedScrollingEnabled(false);
            rvProjects.setAdapter(contentAdapter);
        } else {
            contentAdapter.updateContent(contentItemModels);
        }
    }

    /**
     * load pics
     */
    private void loadPics(){
        if(detialModel==null){
            return;
        }
        if(spphotoAdapter==null){
            spphotoAdapter = new SPPhotoAdapter(this, detialModel.getImages());
            spphotoAdapter.addSQRecycleItemListener(this);
            rvPhotoSelector.setLayoutManager(new LinearLayoutManager(
                    this,
                    LinearLayoutManager.HORIZONTAL,
                    false));
            rvPhotoSelector.setAdapter(spphotoAdapter);
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
        Intent starter = new Intent(this, PhotoShowActivity.class);
        //传递当前点击的图片的位置、图片路径集合
        starter.putExtra("position", position);
        starter.putStringArrayListExtra("mImages", (ArrayList<String>) detialModel.getImagePaths());
        startActivity(starter);
    }


    /**
     * 点检事项内容适配器
     */
    class ProjectContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<ProjectContentItemModel> itemModels;

        public void updateContent(List<ProjectContentItemModel> contents) {
            if (itemModels == null) {
                itemModels = contents;
            } else {
                itemModels.clear();
                itemModels.addAll(contents);
            }
            notifyDataSetChanged();
        }

        public ProjectContentAdapter(List<ProjectContentItemModel> list) {
            this.itemModels = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = getLayoutInflater().inflate(R.layout.item_check_point_project, viewGroup, false);
            RecyclerView.ViewHolder holder =  new ProjectContentViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ProjectContentViewHolder holder = (ProjectContentViewHolder) viewHolder;
            ProjectContentItemModel model = itemModels.get(i);
            holder.tvCheckContent.setText(model.getCheckContent());
            if (model.getCheckType() == 2) {//判断型
                holder.tvCheckRange.setText(R.string.item_qualified);
                holder.tvCheckType.setText(R.string.check_type_judge);
                if (model.getQualified() > 0) { //合格
                    holder.tvCheckResult.setText(R.string.item_qualified);
                } else {
                    holder.tvCheckResult.setText(R.string.item_no_qualified);
                }
                holder.tvProjectName.setText(getResources().getString(R.string.name_project) + (i + 1));
            } else { //填空型
                holder.tvCheckType.setText(R.string.check_type_range);
                holder.tvCheckResult.setText(model.getCheckResult() + "");
                holder.tvCheckRange.setText(model.getMinValue()+"-"+model.getMaxVal());
            }
        }

        @Override
        public int getItemCount() {
            return itemModels == null ? 0 : itemModels.size();
        }
    }

    /**
     * 点检内容viewholder
     */
    class ProjectContentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_project_name)
        TextView tvProjectName;
        @Bind(R.id.tv_check_content)
        TextView tvCheckContent;
        @Bind(R.id.tv_check_type)
        TextView tvCheckType;
        @Bind(R.id.tv_check_range)
        TextView tvCheckRange;
        @Bind(R.id.tv_check_result)
        TextView tvCheckResult;
        @Bind(R.id.ll_range)
        LinearLayout llRange;
        @Bind(R.id.ll_qualified)
        LinearLayout llQualified;

        public ProjectContentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
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
            Glide.with(context)
                    .load(Constants.baseUrl+"media/"+model.getPath())
                    .centerCrop()
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
