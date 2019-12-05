package com.einyun.app.pms.patrol.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.db.entity.PatrolLocal;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.GetUploadJson;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.databinding.ActivityPatrolHandleBinding;
import com.einyun.app.pms.patrol.databinding.ItemPatrolWorkNodeBinding;
import com.einyun.app.pms.patrol.viewmodel.PatrolViewModel;
import com.einyun.app.pms.patrol.viewmodel.ViewModelFactory;
import com.google.gson.Gson;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_PATROL_HANDLE)
public class PatrolHandleActivity extends BaseHeadViewModelActivity<ActivityPatrolHandleBinding, PatrolViewModel> {
    RVBindingAdapter<ItemPatrolWorkNodeBinding, WorkNode> nodesAdapter;
    @Autowired
    IUserModuleService userModuleService;
    PhotoSelectAdapter photoSelectAdapter;
    PatrolLocal patrolLocal;
    private final int MAX_PHOTO_SIZE = 4;

    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Override
    protected PatrolViewModel initViewModel() {
        return new ViewModelProvider(this,new ViewModelFactory()).get(PatrolViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_patrol_handle;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.title_patrol);
        binding.setCallBack(this);
    }

    @Override
    protected void initData() {
        super.initData();
        //图片选择适配器
        photoSelectAdapter = new PhotoSelectAdapter(this);
        binding.pointCkImglist.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//设置横向
        binding.pointCkImglist.setAdapter(photoSelectAdapter);

        //工作节点适配
        if(nodesAdapter==null){
            nodesAdapter=new RVBindingAdapter<ItemPatrolWorkNodeBinding, WorkNode>(this, com.einyun.app.pms.patrol.BR.node) {
                @Override
                public void onBindItem(ItemPatrolWorkNodeBinding binding, WorkNode model, int position) {
                    //处理列表头
                    if(position==0){
                        tableHead(binding);
                    }else{
                        //处理节点
                        tableItem(binding, position);
                        //选中通过
                        agree(binding, model);
                        //选中不通过
                        reject(binding, model);

                        if(model.result.equals(WorkNode.RESULT_REJECT)){
                            onReject(binding);
                        }else if(model.result.equals(WorkNode.RESULT_PASS)){
                            onAgree(binding);
                        }
                    }
                }

                protected void onReject(ItemPatrolWorkNodeBinding binding){
                    binding.btnAgree.setBackgroundResource(R.drawable.shape_frame_corners_gray);
                    binding.btnAgree.setTextColor(binding.btnAgree.getResources().getColor(R.color.normal_main_text_icon_color));
                    binding.btnReject.setBackgroundResource(R.drawable.corners_red_large);
                    binding.btnReject.setTextColor(binding.btnAgree.getResources().getColor(R.color.white));
                }

                protected void onAgree(ItemPatrolWorkNodeBinding binding){
                    binding.btnAgree.setBackgroundResource(R.drawable.corners_green_large);
                    binding.btnAgree.setTextColor(binding.btnAgree.getResources().getColor(R.color.white));
                    binding.btnReject.setBackgroundResource(R.drawable.shape_frame_corners_gray);
                    binding.btnReject.setTextColor(binding.btnAgree.getResources().getColor(R.color.normal_main_text_icon_color));
                }

                //不通过
                protected void reject(ItemPatrolWorkNodeBinding binding, WorkNode model) {
                    binding.btnReject.setOnClickListener(v -> {
                       onReject(binding);
                        model.setResult(WorkNode.RESULT_REJECT);
                        saveLocalUserData();
                    });
                }

                //通过
                protected void agree(ItemPatrolWorkNodeBinding binding, WorkNode model) {
                    binding.btnAgree.setOnClickListener((View v) -> {
                        onAgree(binding);
                        model.setResult(WorkNode.RESULT_PASS);
                        saveLocalUserData();
                    });
                }

                //处理节点
                protected void tableItem(ItemPatrolWorkNodeBinding binding, int position) {
                    binding.tvNumber.setText(position+"");
                    binding.tvWorkNode.setVisibility(View.VISIBLE);
                    binding.tvResult.setVisibility(View.GONE);
                    binding.btnAgree.setVisibility(View.VISIBLE);
                    binding.btnReject.setVisibility(View.VISIBLE);
                    binding.tvWorkThings.setGravity(Gravity.LEFT);
                }

                //处理表头
                protected void tableHead(ItemPatrolWorkNodeBinding binding) {
                    binding.tvNumber.setText(R.string.text_no);
                    binding.tvResult.setVisibility(View.VISIBLE);
                    binding.btnAgree.setVisibility(View.GONE);
                    binding.btnReject.setVisibility(View.GONE);
                    binding.tvWorkNode.setVisibility(View.GONE);
                    binding.tvWorkThings.setGravity(Gravity.CENTER);
                    binding.tvWorkThings.setText(R.string.text_work_items);
                    binding.tvWorkThings.setTextSize(14);
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_patrol_work_node;
                }
            };
        }
        binding.rvNodes.setAdapter(nodesAdapter);

        //加载数据
        viewModel.loadDetial(taskId).observe(this, patrolInfo -> {
            updateUI(patrolInfo);
            viewModel.loadLocalUserData(taskId).observe(this, local -> {
                patrolLocal=local;
                updateLocalData(local);
            });
        });
    }

    private void updateLocalData(PatrolLocal local){
        if (local != null) {
            if (local.getImages() != null && local.getImages().size() > 0) {
                List<Uri> uris = new ArrayList<>();
                for (String imgeUrl : local.getImages()) {
                    Uri uri = Uri.parse(imgeUrl);
                    uris.add(uri);
                }
                photoSelectAdapter.setSelectedPhotos(uris);
            }
            if (!TextUtils.isEmpty(local.getNote())) {
                binding.limitInput.setText(local.getNote());
            }
            if(local.getNodes()!=null){
                nodesAdapter.setDataList(local.getNodes());
            }
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        photoSelectAdapter.setAddListener(selectedSize -> {
            if (photoSelectAdapter.getSelectedPhotos().size() >= MAX_PHOTO_SIZE) {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_max);
                return;
            }
            Matisse.from(this) //加号添加图片
                    .choose(MimeType.ofImage())
                    .captureStrategy(new CaptureStrategy(true, DataConstants.DATA_PROVIDER_NAME))
                    .capture(true)
                    .countable(true)
                    .maxSelectable(MAX_PHOTO_SIZE)
                    //                .maxSelectable(4 - (photoSelectAdapter.getItemCount() - 1))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new Glide4Engine())
                    .forResult(RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK);
        }, PatrolHandleActivity.this);
        binding.limitInput.addTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //saveLocalUserData();
            }
        });
    }

    private void updateUI(PatrolInfo patrol){
        List<WorkNode> nodes=viewModel.loadNodes(patrol);
        nodes.add(0,new WorkNode());
        nodesAdapter.addAll(nodes);
        binding.setPatrol(patrol);
    }

    /**
     * 校验必填参数
     * @return
     */
    private boolean validateFormData(){
        List<WorkNode> data = getWorkNodes();
        for (int i=0;i<data.size();i++) {
            if (TextUtils.isEmpty(data.get(i).result)) {
                ToastUtil.show(this,String.format(getResources().getString(R.string.text_alert_handle_node), data.get(i).number));
                return false;
            }
        }
        if (TextUtils.isEmpty(binding.limitInput.getString())) {
            ToastUtil.show(this, R.string.text_alert_handle_content);
            binding.limitInput.requestFocus();
            return false;
        }

        if(photoSelectAdapter.getSelectedPhotos().size()<=0){
            ToastUtil.show(this, R.string.text_alert_photo_empty);
            return false;
        }

        return true;
    }

    /**
     * 获取节点处理信息
     * @return
     */
    @NotNull
    protected List<WorkNode> getWorkNodes() {
        List<WorkNode> all=nodesAdapter.getDataList();
        return all.subList(1,all.size()-1);
    }

    /**
     * 上传图片
     * @param patrol
     */
    private void uploadImages(PatrolInfo patrol){
        if(patrol==null){
            return;
        }
        viewModel.uploadImages(photoSelectAdapter.getSelectedPhotos()).observe(this, picUrls -> {
            GetUploadJson getUploadJsonStr = new GetUploadJson(picUrls).invoke();
            Gson gson = getUploadJsonStr.getGson();
            List<PicUrlModel> picUrlModels = getUploadJsonStr.getPicUrlModels();
            patrol.getData().getZyxcgd().setF_files(gson.toJson(picUrlModels));
        });
    }


    /**
     * 保存本地数据
     */
    public void saveLocalUserData(){
        List<Uri> uris = photoSelectAdapter.getSelectedPhotos();
        List<String> images = new ArrayList<>();
        for (Uri uri : uris) {
            images.add(uri.toString());
        }
        if(patrolLocal==null){
            patrolLocal=new PatrolLocal();
            patrolLocal.setTaskId(taskId);
            patrolLocal.setUserId(userModuleService.getUserId());
        }
        patrolLocal.setImages(images);
        patrolLocal.setNote(binding.limitInput.getString());
        patrolLocal.setNodes(nodesAdapter.getDataList());
        viewModel.saveLocal(patrolLocal);
    }

    /**
     * 提交
     */
    public void onSubmitClick() {
        if (!validateFormData()) {
            return;
        }
//        uploadImages();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveLocalUserData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK) {
            if (data == null) return;
            List<Uri> uris = Matisse.obtainResult(data);
            if (uris != null && uris.size() > 0) {
                photoSelectAdapter.addPhotos(uris);
                saveLocalUserData();
            }
        }
    }


}
