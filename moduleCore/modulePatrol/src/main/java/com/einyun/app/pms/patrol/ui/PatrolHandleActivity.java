package com.einyun.app.pms.patrol.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.databinding.ActivityPatrolHandleBinding;
import com.einyun.app.pms.patrol.databinding.ItemPatrolWorkNodeBinding;
import com.einyun.app.pms.patrol.model.WorkNode;
import com.einyun.app.pms.patrol.viewmodel.PatrolViewModel;
import com.einyun.app.pms.patrol.viewmodel.ViewModelFactory;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_PATROL_HANDLE)
public class PatrolHandleActivity extends BaseHeadViewModelActivity<ActivityPatrolHandleBinding, PatrolViewModel> {
    RVBindingAdapter<ItemPatrolWorkNodeBinding, WorkNode> nodesAdapter;
    PhotoSelectAdapter photoSelectAdapter;
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
                    }
                }

                //不通过
                protected void reject(ItemPatrolWorkNodeBinding binding, WorkNode model) {
                    binding.btnReject.setOnClickListener(v -> {
                        binding.btnAgree.setBackgroundResource(R.drawable.shape_frame_corners_gray);
                        binding.btnAgree.setTextColor(v.getResources().getColor(R.color.normal_main_text_icon_color));
                        binding.btnReject.setBackgroundResource(R.drawable.corners_red_large);
                        binding.btnReject.setTextColor(v.getResources().getColor(R.color.white));
                        model.setResult(WorkNode.RESULT_REJECT);
                    });
                }

                //通过
                protected void agree(ItemPatrolWorkNodeBinding binding, WorkNode model) {
                    binding.btnAgree.setOnClickListener((View v) -> {
                        binding.btnAgree.setBackgroundResource(R.drawable.corners_green_large);
                        binding.btnAgree.setTextColor(v.getResources().getColor(R.color.white));
                        binding.btnReject.setBackgroundResource(R.drawable.shape_frame_corners_gray);
                        binding.btnReject.setTextColor(v.getResources().getColor(R.color.normal_main_text_icon_color));
                        model.setResult(WorkNode.RESULT_PASS);
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
        });
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
    }

    private void updateUI(PatrolInfo patrol){
        List<WorkNode> nodes=new ArrayList<>();
        WorkNode headWorkNode=new WorkNode();
        nodes.add(headWorkNode);
        WorkNode workNode1=new WorkNode("","电梯设备房巡查","1.机房温度，湿度，环境情况。" +
                "2.限速器，钢丝绳的运行状态。3.电动机温度，主机轴","");
        nodes.add(workNode1);
        WorkNode workNode2=new WorkNode("","电梯设备房巡查","1.机房温度，湿度，环境情况。" +
                "2.限速器，钢丝绳的运行状态。3.电动机温度，主机轴","");
        nodes.add(workNode2);
        nodesAdapter.addAll(nodes);
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

    private void uploadImages(){

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK) {
            if (data == null) return;
            List<Uri> uris = Matisse.obtainResult(data);
            if (uris != null && uris.size() > 0) {
                photoSelectAdapter.addPhotos(uris);
            }
        }
    }



}
