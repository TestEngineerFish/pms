package com.einyun.app.pms.plan.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.db.entity.PatrolLocal;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.TimeUtil;
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

import com.einyun.app.library.resource.workorder.model.OrderState;
import com.einyun.app.library.resource.workorder.model.PlanInfo;
import com.einyun.app.library.resource.workorder.model.Sub_jhgdzyb;
import com.einyun.app.pms.plan.BR;
import com.einyun.app.pms.plan.R;
import com.einyun.app.pms.plan.databinding.ActivityPlanOrderDetailBinding;
import com.einyun.app.pms.plan.databinding.ItemPlanResouceBinding;
import com.einyun.app.pms.plan.databinding.ItemPlanWorkNodeBinding;
import com.einyun.app.pms.plan.viewmodel.PlanOdViewModelFactory;
import com.einyun.app.pms.plan.viewmodel.PlanOrderDetailViewModel;
import com.google.gson.Gson;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
public class PlanOrderDetailActivity extends BaseHeadViewModelActivity<ActivityPlanOrderDetailBinding, PlanOrderDetailViewModel> {
    RVBindingAdapter<ItemPlanWorkNodeBinding, WorkNode> nodesAdapter;
    RVBindingAdapter<ItemPlanResouceBinding, Sub_jhgdzyb> resourceAdapter;
    //    RVBindingAdapter<ItemPlanMaterialBinding, WorkNode> materialAdapter;
    @Autowired
    IUserModuleService userModuleService;
    PhotoSelectAdapter photoSelectAdapter;
    private final int MAX_PHOTO_SIZE = 4;
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    String id;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;
    @Autowired(name = RouteKey.KEY_FRAGEMNT_TAG)
    String fragmentTag;
    @Autowired(name = RouteKey.KEY_TASK_NODE_ID)
    String taskNodeId;

    @Override
    protected PlanOrderDetailViewModel initViewModel() {
        return new ViewModelProvider(this, new PlanOdViewModelFactory()).get(PlanOrderDetailViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_plan_order_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_plan_order);
        setRightOption(R.drawable.histroy);
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
        if (nodesAdapter == null) {
            nodesAdapter = new RVBindingAdapter<ItemPlanWorkNodeBinding, WorkNode>(this, BR.node) {
                @Override
                public void onBindItem(ItemPlanWorkNodeBinding binding, WorkNode model, int position) {
                    //处理列表头
                    if (position == 0) {
                        tableHead(binding);
                    } else {
                        //处理节点
                        tableItem(binding, position);
                        //选中通过
                        agree(binding, model);
                        //选中不通过
                        reject(binding, model);

                        if (!TextUtils.isEmpty(model.result)) {
                            if (model.result.equals(WorkNode.RESULT_REJECT)) {
                                onReject(binding);
                            } else if (model.result.equals(WorkNode.RESULT_PASS)) {
                                onAgree(binding);
                            }
                        }
                    }
                }

                protected void onReject(ItemPlanWorkNodeBinding binding) {
                    binding.btnAgree.setBackgroundResource(R.drawable.shape_frame_corners_gray);
                    binding.btnAgree.setTextColor(binding.btnAgree.getResources().getColor(R.color.normal_main_text_icon_color));
                    binding.btnReject.setBackgroundResource(R.drawable.corners_red_large);
                    binding.btnReject.setTextColor(binding.btnAgree.getResources().getColor(R.color.white));
                }

                protected void onAgree(ItemPlanWorkNodeBinding binding) {
                    binding.btnAgree.setBackgroundResource(R.drawable.corners_green_large);
                    binding.btnAgree.setTextColor(binding.btnAgree.getResources().getColor(R.color.white));
                    binding.btnReject.setBackgroundResource(R.drawable.shape_frame_corners_gray);
                    binding.btnReject.setTextColor(binding.btnAgree.getResources().getColor(R.color.normal_main_text_icon_color));
                }

                //不通过
                protected void reject(ItemPlanWorkNodeBinding binding, WorkNode model) {
                    binding.btnReject.setOnClickListener(v -> {
                        onReject(binding);
                        model.setResult(WorkNode.RESULT_REJECT);
                    });
                }

                //通过
                protected void agree(ItemPlanWorkNodeBinding binding, WorkNode model) {
                    binding.btnAgree.setOnClickListener((View v) -> {
                        onAgree(binding);
                        model.setResult(WorkNode.RESULT_PASS);
                    });
                }

                //处理节点
                protected void tableItem(ItemPlanWorkNodeBinding binding, int position) {
                    binding.tvNumber.setText(position + "");
                    binding.tvWorkNode.setVisibility(View.VISIBLE);
                    binding.tvResult.setVisibility(View.GONE);
                    binding.btnAgree.setVisibility(View.VISIBLE);
                    binding.btnReject.setVisibility(View.VISIBLE);
                    binding.tvWorkThings.setGravity(Gravity.LEFT);
                }

                //处理表头
                protected void tableHead(ItemPlanWorkNodeBinding binding) {
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
                    return R.layout.item_plan_work_node;
                }
            };
        }
        binding.rvNodes.setAdapter(nodesAdapter);

        //资源适配
        if (resourceAdapter == null) {
            resourceAdapter = new RVBindingAdapter<ItemPlanResouceBinding, Sub_jhgdzyb>(this, BR.resource) {
                @Override
                public void onBindItem(ItemPlanResouceBinding binding, Sub_jhgdzyb model, int position) {

                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_plan_resouce;
                }
            };
        }
        binding.rvResource.setAdapter(resourceAdapter);


        //加载数据
        viewModel.loadDetail(proInsId,taskId,taskNodeId,fragmentTag).observe(this, planInfo -> {
            updateUI(planInfo);
            if (StringUtil.isNullStr(planInfo.getData().getZyjhgd().getF_CREATE_TIME())) {
                createTime = planInfo.getData().getZyjhgd().getF_CREATE_TIME();
                if (planInfo.getData().getZyjhgd().getF_STATUS().equals(OrderState.CLOSED.getState())) {
                    if (StringUtil.isNullStr(planInfo.getData().getZyjhgd().getF_ACT_FINISH_TIME()))
                        binding.tvHandleTime.setText(TimeUtil.getTimeExpend(planInfo.getData().getZyjhgd().getF_CREATE_TIME(), planInfo.getData().getZyjhgd().getF_ACT_FINISH_TIME()));
                } else {
                    binding.tvHandleTime.setText(TimeUtil.getTimeExpend(planInfo.getData().getZyjhgd().getF_CREATE_TIME()));
                    runnable.run();
                }
            }
            if (planInfo.getData().getZyjhgd().getSub_jhgdzyb() != null && planInfo.getData().getZyjhgd().getSub_jhgdzyb().size() != 0) {
                resourceAdapter.setDataList(planInfo.getData().getZyjhgd().getSub_jhgdzyb());
            } else {
                binding.cdWorkResouce.setVisibility(View.GONE);
            }
        });
    }

    private String createTime;

    //立即调用方法
    Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable, 1000);
            //计算时间
            binding.tvHandleTime.setText(TimeUtil.getTimeExpend(createTime));
        }
    };

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
        }, this);
    }

    private void updateUI(PlanInfo planInfo) {
        List<WorkNode> nodes = viewModel.loadNodes(planInfo);
        nodes.add(0, new WorkNode());
        nodesAdapter.addAll(nodes);
        binding.setDetail(planInfo.getData().getZyjhgd());
    }

    /**
     * 跳转申请延期
     */
    public void applyPostpone() {
        //还需要传入参数
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_LATE)
                .withString(RouteKey.KEY_ORDER_ID, id)
                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                .withString(RouteKey.KEY_LATER_ID, RouteKey.KEY_PLAN)
                .navigation();
    }

    /**
     *
     */
    public void closeOrder() {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_CLOSE)
                .withString(RouteKey.KEY_ORDER_ID, id)
                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                .withString(RouteKey.KEY_TASK_ID, taskId)
                .withString(RouteKey.KEY_CLOSE_ID, RouteKey.KEY_PLAN)
                .navigation();
    }

    /**
     * 校验必填参数
     *
     * @return
     */
    private boolean validateFormData() {
        List<WorkNode> data = getWorkNodes();
        for (int i = 0; i < data.size(); i++) {
            if (TextUtils.isEmpty(data.get(i).result)) {
                ToastUtil.show(this, String.format(getResources().getString(R.string.text_alert_handle_node), data.get(i).number));
                return false;
            }
        }
        if (TextUtils.isEmpty(binding.limitInput.getString())) {
            ToastUtil.show(this, R.string.text_alert_handle_content);
            binding.limitInput.requestFocus();
            return false;
        }

        if (photoSelectAdapter.getSelectedPhotos().size() <= 0) {
            ToastUtil.show(this, R.string.text_alert_photo_empty);
            return false;
        }

        return true;
    }

    /**
     * 获取节点处理信息
     *
     * @return
     */
    @NotNull
    protected List<WorkNode> getWorkNodes() {
        List<WorkNode> all = nodesAdapter.getDataList();
        return all.subList(1, all.size() - 1);
    }

    /**
     * 上传图片
     *
     * @param patrol
     */
    private void uploadImages(PatrolInfo patrol) {
        if (patrol == null) {
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

    @Override
    public void onOptionClick(View view) {
        super.onOptionClick(view);
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_HISTORY)
                .withString(RouteKey.KEY_ORDER_ID, id)
                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                .navigation();
    }
}
