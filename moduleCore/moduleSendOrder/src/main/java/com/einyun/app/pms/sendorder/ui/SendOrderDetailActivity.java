package com.einyun.app.pms.sendorder.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.ImageUploadManager;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.widget.TipDialog;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.library.resource.workorder.model.DisttributeDetialModel;
import com.einyun.app.library.resource.workorder.model.OrderState;
import com.einyun.app.library.resource.workorder.net.request.DistributeCheckRequest;
import com.einyun.app.library.resource.workorder.net.request.DistributeSubmitRequest;
import com.einyun.app.library.upload.model.PicUrl;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.ActivitySendOrderDetailBinding;
import com.einyun.app.pms.sendorder.databinding.LayoutCheckAndAcceptBinding;
import com.einyun.app.pms.sendorder.model.SendOrderModel;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderDetialViewModel;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_DONE;

@Route(path = RouterUtils.ACTIVITY_SEND_ORDER_DETAIL)
public class SendOrderDetailActivity extends BaseHeadViewModelActivity<ActivitySendOrderDetailBinding, SendOrderDetialViewModel> implements View.OnClickListener {
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_FRAGEMNT_TAG)
    String fragmentTag;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    String orderId;
    private TipDialog tipDialog;
    PhotoListAdapter photoListInfoAdapter;
    PhotoSelectAdapter photoListFormAdapter;
    private final int MAX_PHOTO_SIZE = 4;
    DisttributeDetialModel detialModel;
    private String checkResult;
    public static String RESULT_PASS = "1";
    public static String RESULT_REJECT = "0";

    @Override
    protected SendOrderDetialViewModel initViewModel() {
        return new ViewModelProvider(this, new SendOdViewModelFactory()).get(SendOrderDetialViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_send_order_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_send_order);
        tipDialog = new TipDialog(this, getString(R.string.text_take_order_success));
        setRightOption(R.drawable.histroy);
    }

    @Override
    protected void initData() {
        loadData();
        super.initData();
        photoListInfoAdapter = new PhotoListAdapter(this);
        binding.orderInfo.sendOrderDetailList.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.orderInfo.sendOrderDetailList.setAdapter(photoListInfoAdapter);
        initWorkForm();
        initCheckAccept();
    }

    /**
     * 加载详情数据
     * 已办详情或待办详情
     */
    private void loadData() {
        viewModel.detial(orderId).observe(this, disttributeDetialModel -> updateUI(disttributeDetialModel));
    }

    /**
     * 初始化提交表单项
     * 1.处理后图片
     * 2.处理结果
     */
    private void initWorkForm() {
        photoListFormAdapter = new PhotoSelectAdapter(this);
        binding.orderForm.sendOrderImgList.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//设置横向
        binding.orderForm.sendOrderImgList.setAdapter(photoListFormAdapter);
    }

    private void initCheckAccept() {
        binding.checkAndAccept.btnReject.setOnClickListener(v -> {
            onReject(binding.checkAndAccept);
            checkResult = RESULT_REJECT;
        });
        binding.checkAndAccept.btnAgree.setOnClickListener((View v) -> {
            onAgree(binding.checkAndAccept);
            checkResult = RESULT_PASS;
        });
    }

    protected void onReject(LayoutCheckAndAcceptBinding binding) {
        binding.btnAgree.setBackgroundResource(R.drawable.shape_frame_corners_gray);
        binding.btnAgree.setTextColor(binding.btnAgree.getResources().getColor(R.color.normal_main_text_icon_color));
        binding.btnReject.setBackgroundResource(R.drawable.corners_red_large);
        binding.btnReject.setTextColor(binding.btnAgree.getResources().getColor(R.color.white));
    }

    protected void onAgree(LayoutCheckAndAcceptBinding binding) {
        binding.btnAgree.setBackgroundResource(R.drawable.corners_green_large);
        binding.btnAgree.setTextColor(binding.btnAgree.getResources().getColor(R.color.white));
        binding.btnReject.setBackgroundResource(R.drawable.shape_frame_corners_gray);
        binding.btnReject.setTextColor(binding.btnAgree.getResources().getColor(R.color.normal_main_text_icon_color));
    }

    /**
     * 详情数据获取后进行UI展示
     *
     * @param distributeWorkOrder
     */
    private void updateUI(DisttributeDetialModel distributeWorkOrder) {
        detialModel = distributeWorkOrder;
        if (detialModel == null) {
            return;
        }
        binding.setWorkOrder(distributeWorkOrder);
        binding.orderInfo.setWorkOrder(distributeWorkOrder);
        binding.orderForm.setWorkOrder(distributeWorkOrder);
        updateImagesUI(distributeWorkOrder);
        switchState(distributeWorkOrder.getData().getFstatus());
    }


    private void updateImagesUI(DisttributeDetialModel distributeWorkOrder) {
        if (detialModel == null) {
            return;
        }
        PicUrlModelConvert convert = new PicUrlModelConvert();
        List<PicUrlModel> modelList = convert.stringToSomeObjectList(distributeWorkOrder.getData().getPgdAttachment());
        photoListInfoAdapter.updateList(modelList);
    }

    /**
     * 根据工单状态显示隐藏
     * 1.是否显示提交表单
     * 2.是否显示强制闭单
     * 3.是否显示申请延期
     *
     * @param stateStr
     */
    private void switchState(String stateStr) {
        if (detialModel == null) {
            return;
        }
        //如果是已办，全部显示详情
        if(fragmentTag.equals(FRAGMENT_SEND_OWRKORDER_DONE)){
            binding.sendOrderDetailSubmit.setVisibility(View.GONE);
            return;
        }
        int state = Integer.parseInt(stateStr);
        if (state == OrderState.NEW.getState()) {//接单-显示接单按钮
            binding.sendOrderDetailSubmit.setVisibility(View.VISIBLE);
        } else if ((state == OrderState.HANDING.getState())) {//处理-提交
            binding.sendOrderDetailSubmit.setVisibility(View.VISIBLE);
            binding.orderForm.getRoot().setVisibility(View.VISIBLE);//显示表单
            binding.applyForceCloseAndPostpone.getRoot().setVisibility(View.VISIBLE);//显示 申请延期和强制逼单
        } else if (state == OrderState.APPLY.getState()) {
            if (isNeedCheckAccept()) {//如果验收人是自己，显示验收
                binding.sendOrderDetailSubmit.setVisibility(View.VISIBLE);
                binding.checkAndAccept.getRoot().setVisibility(View.VISIBLE);//显示验收
                binding.applyPostpone.getRoot().setVisibility(View.VISIBLE);//显示申请延期
            } else {
                binding.sendOrderDetailSubmit.setVisibility(View.GONE);//如果是自己的单子待验收，显示详情
            }
        } else {
            binding.sendOrderDetailSubmit.setVisibility(View.GONE);//已关闭，显示详情
        }
    }

    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<SendOrderModel> mDiffCallback = new DiffUtil.ItemCallback<SendOrderModel>() {

        @Override
        public boolean areItemsTheSame(@NonNull SendOrderModel oldItem, @NonNull SendOrderModel newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull SendOrderModel oldItem, @NonNull SendOrderModel newItem) {
            return oldItem == newItem;
        }
    };

    @Override
    protected void initListener() {
        super.initListener();
        binding.sendOrderDetailSubmit.setOnClickListener(this);
        binding.applyForceCloseAndPostpone.sendOrderApplyLate.setOnClickListener(this);
        binding.applyForceCloseAndPostpone.applyPostpone.setOnClickListener(this);
        headBinding.ivRightOption.setOnClickListener(this);

        /**
         * 处理后图片
         */
        photoListFormAdapter.setAddListener(selectedSize -> {
            if (photoListFormAdapter.getSelectedPhotos().size() >= MAX_PHOTO_SIZE) {
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
        }, SendOrderDetailActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (tipDialog != null) {
            tipDialog.dismiss();
        }
    }

    /**
     * 上传图片
     */
    private void uploadImages() {
        if (photoListFormAdapter.getSelectedPhotos().size() >= 0) {
            viewModel.uploadImages(photoListFormAdapter.getSelectedPhotos()).observe(this, picUrls -> {
                if (validateForm()) {
                    postSubmit(picUrls);
                }
            });
        }
    }

    /**
     * 提交处理
     */
    private void postSubmit(List<PicUrl> uploads) {
        DistributeSubmitRequest request = new DistributeSubmitRequest();
        request.setAfterPic(new ImageUploadManager().toJosnString(uploads));
        request.setTaskId(taskId);
        request.setId(detialModel.getData().getId());
        request.setProcConeten(binding.orderForm.etLimitInput.getString());
        viewModel.submit(request).observe(this, aBoolean -> {
            if (aBoolean) {
                tipDialog.setTip(getString(R.string.text_handle_success));
                tipDialog.setTipDialogListener(dialog -> {
                    dialog.dismiss();
                    finish();
                });
                tipDialog.show();
            }
        });
    }

    /**
     * 接单
     */
    private void takeOrder() {
        viewModel.takeOrder(taskId).observe(this, aBoolean -> {
            if (aBoolean) {
                tipDialog.setTipDialogListener(dialog -> {
                    dialog.dismiss();
                    finish();
                });
                tipDialog.show();
            }
        });
    }

    /**
     * 处理表单
     */
    private void handle() {
        if (detialModel == null) {
            return;
        }
        int state = Integer.parseInt(detialModel.getData().getFstatus());
        if (state == OrderState.NEW.getState()) {
            takeOrder();//接单
        } else if (state == OrderState.HANDING.getState()) {
            submit();//处理-提交
        } else if (state == OrderState.APPLY.getState()) {
            checkAccept();//验收
        }
    }

    /**
     * 验收
     */
    private void checkAccept() {
        if (detialModel == null) {
            return;
        }
        if (validateApply()) {
            DistributeCheckRequest request = new DistributeCheckRequest();
            request.setId(orderId);
            request.setTaskId(taskId);
            request.setFCheckResult(checkResult);
            request.setFEvaluation(binding.checkAndAccept.ratingBar.getSelectedStarts()+"");
            request.setFCheckContent(binding.checkAndAccept.etLimitSuggestion.getString());
            request.setFCheckDate(TimeUtil.getAllTime(TimeUtil.currentTimeMillis()));
            viewModel.check(request).observe(this, aBoolean -> {
                if (aBoolean) {
                    tipDialog.setTip(getString(R.string.text_check_success));
                    tipDialog.setTipDialogListener(dialog -> {
                        dialog.dismiss();
                        finish();
                    });
                    tipDialog.show();
                }
            });
        }
    }

    /**
     * 处理-提交
     */
    private void submit() {
        if (detialModel == null) {
            return;
        }
        uploadImages();
    }

    /**
     * 验收校验
     *
     * @return
     */
    private boolean validateApply() {
        if(TextUtils.isEmpty(checkResult)){
            ToastUtil.show(CommonApplication.getInstance(), R.string.text_alert_check_result);
            return false;
        }
        if (TextUtils.isEmpty(binding.checkAndAccept.etLimitSuggestion.getString())) {
            ToastUtil.show(CommonApplication.getInstance(), R.string.text_alert_input_suggestion);
            return false;
        }
        return true;
    }

    /**
     * 表单校验
     *
     * @return
     */
    private boolean validateForm() {
        if (TextUtils.isEmpty(binding.orderForm.etLimitInput.getString())) {
            ToastUtil.show(CommonApplication.getInstance(), R.string.text_alert_handle_result);
            return false;
        }
        if (photoListFormAdapter.getSelectedPhotos().size() <= 0) {
            ToastUtil.show(CommonApplication.getInstance(), R.string.text_alert_handle_pic);
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_order_detail_submit) {
            handle();
        }
        if (v.getId() == R.id.send_order_apply_late) {
            ARouter.getInstance()
                    .build(RouterUtils.ACTIVITY_LATE)
                    .navigation();
        }
        if (v.getId() == R.id.apply_postpone) {
            ARouter.getInstance()
                    .build(RouterUtils.ACTIVITY_CLOSE)
                    .navigation();
        }
        if (v.getId() == R.id.iv_right_option) {
            ARouter.getInstance()
                    .build(RouterUtils.ACTIVITY_HISTORY)
                    .navigation();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //选择图片返回
        if (requestCode == RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK) {
            if (data == null) return;
            List<Uri> uris = Matisse.obtainResult(data);
            if (uris != null && uris.size() > 0) {
                photoListFormAdapter.addPhotos(uris);
            }
        }
    }

    @Autowired
    IUserModuleService userModuleService;

    /**
     * 判断自己是否是验收人
     *
     * @return
     */
    private boolean isNeedCheckAccept() {
        if (detialModel == null) {
            return false;
        }
        return userModuleService.getUserId().equals(detialModel.getData().getFcheckId());
    }
}
