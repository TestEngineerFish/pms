package com.einyun.app.pms.sendorder.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.constants.WorkOrder;
import com.einyun.app.common.manager.CustomEventTypeEnum;
import com.einyun.app.common.manager.ImageUploadManager;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.model.PageUIState;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.WorkOrderType;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.component.rating.RatingBar;
import com.einyun.app.common.ui.widget.TipDialog;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.library.resource.workorder.model.ApplyType;
import com.einyun.app.library.resource.workorder.model.DisttributeDetialModel;
import com.einyun.app.library.resource.workorder.model.DisttributeMainModel;
import com.einyun.app.library.resource.workorder.model.ExtensionApplication;
import com.einyun.app.library.resource.workorder.model.OrderState;
import com.einyun.app.library.resource.workorder.net.request.DistributeCheckRequest;
import com.einyun.app.library.resource.workorder.net.request.DistributeSubmitRequest;
import com.einyun.app.library.resource.workorder.net.request.IsClosedRequest;
import com.einyun.app.library.upload.model.PicUrl;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.ActivitySendOrderDetailBinding;
import com.einyun.app.pms.sendorder.databinding.LayoutCheckAndAcceptBinding;
import com.einyun.app.pms.sendorder.model.SendOrderModel;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderDetialViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_DONE;

@Route(path = RouterUtils.ACTIVITY_SEND_ORDER_DETAIL)
public class SendOrderDetailActivity extends BaseHeadViewModelActivity<ActivitySendOrderDetailBinding, SendOrderDetialViewModel> implements View.OnClickListener {
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_TASK_NODE_ID)
    String taskNodeId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;
    @Autowired(name = RouteKey.KEY_LIST_TYPE)
    int listType;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    String orderId;
    @Autowired(name = RouteKey.KEY_DIVIDE_ID)
    String divideId;
    @Autowired(name = RouteKey.KEY_PROJECT_ID)
    String projectId;
    private TipDialog tipDialog;
    PhotoListAdapter photoListInfoAdapter;
    PhotoListAdapter photoHandleListAdapter;
    PhotoSelectAdapter photoListFormAdapter;
    private final int MAX_PHOTO_SIZE = 4;
    DisttributeDetialModel detialModel;
    private String checkResult;
    public static String RESULT_PASS = "1";
    public static String RESULT_REJECT = "0";
    IsClosedRequest isClosedRequest;

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
        setRightTxt(R.string.text_histroy);
        setRightTxtColor(R.color.blueTextColor);
        tipDialog = new TipDialog(this, getString(R.string.text_take_order_success));
        setRightOption(R.drawable.histroy);
    }

    @Override
    public void onRightOptionClick(View view) {
        super.onRightOptionClick(view);
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_HISTORY)
                .withString(RouteKey.KEY_ORDER_ID, detialModel.getData().getInfo().getID())
                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                .navigation();
    }

    @Override
    protected void initData() {
        loadData();
        super.initData();
        photoListInfoAdapter = new PhotoListAdapter(this);
        photoHandleListAdapter = new PhotoListAdapter(this);
        binding.orderHandle.sendOrderHandlePicList.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.orderHandle.sendOrderHandlePicList.addItemDecoration(new SpacesItemDecoration(18));
        binding.orderHandle.sendOrderHandlePicList.setAdapter(photoHandleListAdapter);
        binding.orderInfo.sendOrderDetailList.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.orderInfo.sendOrderDetailList.addItemDecoration(new SpacesItemDecoration(18));
        binding.orderInfo.sendOrderDetailList.setAdapter(photoListInfoAdapter);
        initWorkForm();
        initCheckAccept();
        binding.applyPostpone.panelPostpone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(RouterUtils.ACTIVITY_LATE)
                        .withSerializable(RouteKey.KEY_ORDER_DETAIL_EXTEN, detialModel.getExtensionApplication())
                        .withString(RouteKey.KEY_ORDER_ID, detialModel.getData().getInfo().getID())
                        .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                        .navigation();
            }
        });

        LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                finish();
            }
        });
    }

    /**
     * 加载详情数据
     * 已办详情或待办详情
     */
    private void loadData() {
        if (listType == ListType.DONE.getType()) {
            viewModel.doneDetial(taskNodeId, proInsId).observe(this, model -> updateUI(model));
        } else {
            viewModel.pendingDetial(taskId).observe(this, model -> updateUI(model));
        }
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
        binding.orderForm.sendOrderImgList.addItemDecoration(new SpacesItemDecoration(18));
        binding.orderForm.sendOrderImgList.setAdapter(photoListFormAdapter);

    }

    private void initCheckAccept() {
        onAgree(binding.checkAndAccept);
        checkResult = RESULT_PASS;
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

        if (distributeWorkOrder == null) {
            updatePageUIState(PageUIState.LOAD_FAILED.getState());
            return;
        }
        detialModel = distributeWorkOrder;
        if (detialModel.getData().getInfo().getOriginalCode()==null||detialModel.getData().getInfo().getOriginalCode().isEmpty()) {
            binding.orderInfo.llOriginalCode.setVisibility(View.GONE);
        }
        orderId=detialModel.getData().getInfo().getID();
        binding.setWorkOrder(distributeWorkOrder);
        updatePageUIState(PageUIState.FILLDATA.getState());
        binding.orderInfo.setWorkOrder(distributeWorkOrder);
        binding.orderForm.setWorkOrder(distributeWorkOrder);
        binding.orderHandle.setWorkOrder(distributeWorkOrder);
        binding.tvHandleTime.setText(TimeUtil.getTimeExpend(detialModel.getData().getInfo().getCreateTime()));
        updateElapsedTime();
        updateImagesUI(distributeWorkOrder);
        switchState(distributeWorkOrder.getData().getInfo().getStatus());
        StringBuffer type=new StringBuffer();
        DisttributeMainModel info = distributeWorkOrder.getData().getInfo();
//        distributeWorkOrder.getData().info.typeName+@string/text_padding+workOrder.data.info.envirmentType2Name+@string/text_padding+workOrder.data.info.envirmentType3Name
                type.append(info.getTypeName());

        if (info.getEnvirmentType2Name()!=null) {
            type.append("-"+info.getEnvirmentType2Name());
        }
        if (info.getEnvirmentType3Name()!=null) {
            type.append("-"+info.getEnvirmentType3Name());
        }
        binding.orderInfo.orderType.setText(type);
        isClosedRequest = new IsClosedRequest(orderId, WorkOrder.FORCE_CLOSE_ALLOCATE);
        //判断是否有闭单申请，有只显示详情
        viewModel.isClosed(isClosedRequest).observe(this, model -> {
            Log.d("Test", model.isClosed() + "");
            if (model.isClosed() == false) {
                showIfHasClosed();
            }
//            if (binding.forceCloseInfo.getRoot().isShown()) {
//                binding.orderForm.getRoot().setVisibility(View.GONE);
//                binding.applyForceCloseAndPostpone.getRoot().setVisibility(View.GONE);
//                binding.sendOrderDetailSubmit.setVisibility(View.GONE);
//            }
        });
    }

    protected void updatePageUIState(int state) {
        binding.pageState.setPageState(state);
    }

    private static final String TAG = "SendOrderDetailActivity";
    private void updateElapsedTime() {
        if (StringUtil.isNullStr(detialModel.getData().getInfo().getCreateTime())) {
            createTime = detialModel.getData().getInfo().getCreateTime();
            if (detialModel.getData().getInfo().getStatus().equals(String.valueOf(OrderState.CLOSED.getState()))) {
                if (StringUtil.isNullStr(detialModel.getData().getInfo().getActFinishTime()))
                    Log.e(TAG, "updateElapsedTime: "+createTime );
                    Log.e(TAG, "getActFinishTim e: "+detialModel.getData().getInfo().getActFinishTime() );
                    binding.tvHandleTime.setText(TimeUtil.getTimeExpend(createTime, detialModel.getData().getInfo().getActFinishTime()));
            } else {
                binding.tvHandleTime.setText(TimeUtil.getTimeExpend(createTime));
                runnable.run();
            }
        }
    }

    /**
     * 有闭单申请的情况
     */
    private void showIfHasClosed() {
        binding.sendOrderDetailSubmit.setVisibility(View.GONE);
        binding.orderHandle.getRoot().setVisibility(View.GONE);
        binding.applyForceCloseAndPostpone.getRoot().setVisibility(View.GONE);
        binding.applyPostpone.getRoot().setVisibility(View.GONE);
        binding.checkAndAccept.getRoot().setVisibility(View.GONE);
        binding.orderForm.getRoot().setVisibility(View.GONE);
    }

    private void updateImagesUI(DisttributeDetialModel distributeWorkOrder) {
        if (detialModel == null) {
            return;
        }
        PicUrlModelConvert convert = new PicUrlModelConvert();
        if (distributeWorkOrder.getData().getInfo().getPgdAttachment()!=null){
            List<PicUrlModel> modelList = convert.stringToSomeObjectList(distributeWorkOrder.getData().getInfo().getPgdAttachment());
            photoListInfoAdapter.updateList(modelList);
        }
        if (distributeWorkOrder.getData().getInfo().getAftPic() != null) {
            List<PicUrlModel> modelListHandle = convert.stringToSomeObjectList(distributeWorkOrder.getData().getInfo().getAftPic());
            photoHandleListAdapter.updateList(modelListHandle);
        }
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
        showForceClose();
        showPostpone();
        //如果是已办全部显示详情
        if (listType == ListType.DONE.getType()) {
            onlyShowDetial();
            return;
        }
        int state = Integer.parseInt(stateStr);
        if (detialModel.isReply() > 0) {//批复-显示批复按钮
            showReply();
            return;
        } else {
            if (state == OrderState.NEW.getState()) {//接单-显示接单按钮
                showTakeOrder();
            } else if ((state == OrderState.HANDING.getState())) {//处理-提交
                if (binding.forceCloseInfo.getRoot().isShown()) {
                    showSubmit();
                }else {

                    showSubmit();
                }
            } else if (state == OrderState.APPLY.getState()) {//验收
                showApply();
            } else {
                onlyShowDetial();
            }
        }
    }

    /**
     * 显示接单
     */
    private void showTakeOrder() {
        binding.sendOrderDetailSubmit.setVisibility(View.VISIBLE);
        binding.sendOrderDetailSubmit.setText(com.einyun.app.common.R.string.text_take_order);
    }

    /**
     * 显示验收
     */
    private void showApply() {
//        if (isNeedCheckAccept()) {//如果验收人是自己，显示验收
            binding.orderHandle.getRoot().setVisibility(View.VISIBLE);
            binding.sendOrderDetailSubmit.setVisibility(View.VISIBLE);
            binding.checkAndAccept.getRoot().setVisibility(View.VISIBLE);//显示验收
            binding.checkAndAccept.ratingBar.setStar(5f);
            binding.orderHandle.getRoot().setVisibility(View.VISIBLE);//显示处理信息
            binding.sendOrderDetailSubmit.setText(com.einyun.app.common.R.string.text_work_order_apply);
//        } else {
//            binding.sendOrderDetailSubmit.setVisibility(View.GONE);//如果是自己的单子待验收，显示详情
//        }
    }

    /**
     * 显示处理提交
     */
    private void showSubmit() {
        binding.sendOrderDetailSubmit.setVisibility(View.VISIBLE);
        binding.orderForm.getRoot().setVisibility(View.VISIBLE);//显示表单
        binding.sendOrderDetailSubmit.setText(com.einyun.app.common.R.string.text_commit);
        binding.applyForceCloseAndPostpone.getRoot().setVisibility(View.VISIBLE);//显示 申请延期和强制逼单
    }

    /**
     * 显示批复
     */
    private void showReply() {
        binding.sendOrderDetailSubmit.setVisibility(View.VISIBLE);
        binding.sendOrderDetailSubmit.setText(getString(R.string.text_work_order_reply));
    }

    /**
     * 只显示详情
     */
    private void onlyShowDetial() {
        binding.sendOrderDetailSubmit.setVisibility(View.GONE);
        binding.orderHandle.getRoot().setVisibility(View.VISIBLE);
        binding.orderHandle.setWorkOrder(detialModel);

        if(!TextUtils.isEmpty(detialModel.getData().getInfo().getCheckResult())) {
            binding.checkAndAcceptInfo.getRoot().setVisibility(View.VISIBLE);
            binding.checkAndAcceptInfo.setWorkOrder(detialModel);
            if (detialModel.getData().getInfo().getCheckResult().equals("1")) {
                binding.checkAndAcceptInfo.btnAgree.setVisibility(View.VISIBLE);
            } else {
                binding.checkAndAcceptInfo.btnReject.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(detialModel.getData().getInfo().getCheckContent())) {
                binding.checkAndAcceptInfo.checkContent.setText(detialModel.getData().getInfo().getCheckContent());
            }
            if (!TextUtils.isEmpty(detialModel.getData().getInfo().getScore())) {
                binding.checkAndAcceptInfo.ratingBar.setStar(Float.valueOf(detialModel.getData().getInfo().getScore()));
            }else {
                binding.checkAndAcceptInfo.ratingBar.setStar(Float.valueOf(0));
            }
        }

    }

    /**
     * 显示申请延期信息
     */
    private void showPostpone() {
        ExtensionApplication extPostpone = detialModel.getExtApplication(ApplyType.POSTPONE.getState());
        if (extPostpone != null) {
            binding.postponeInfo.getRoot().setVisibility(View.VISIBLE);
            binding.postponeInfo.setExt(extPostpone);
            if (extPostpone.getApplyFiles() != null) {
                PhotoListAdapter adapter = new PhotoListAdapter(this);
                binding.postponeInfo.sendOrderPostponePicList.setLayoutManager(new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false));
                binding.postponeInfo.sendOrderPostponePicList.addItemDecoration(new SpacesItemDecoration(18));
                binding.postponeInfo.sendOrderPostponePicList.setAdapter(adapter);
                PicUrlModelConvert convert = new PicUrlModelConvert();
                List<PicUrlModel> modelList = convert.stringToSomeObjectList(extPostpone.getApplyFiles());
                adapter.updateList(modelList);
            }
        }
    }

    /**
     * 显示强制闭单信息
     */
    private void showForceClose() {
        ExtensionApplication extForceClose = detialModel.getExtApplication(ApplyType.FORCECLOSE.getState());
        if (extForceClose != null) {
            binding.forceCloseInfo.getRoot().setVisibility(View.VISIBLE);
            binding.orderForm.getRoot().setVisibility(View.GONE);
            binding.applyForceCloseAndPostpone.getRoot().setVisibility(View.GONE);
            binding.sendOrderDetailSubmit.setVisibility(View.GONE);
            binding.forceCloseInfo.setExt(extForceClose);
            if (extForceClose.getApplyFiles() != null) {
                PhotoListAdapter adapter = new PhotoListAdapter(this);
                binding.forceCloseInfo.sendOrderClosePicList.setLayoutManager(new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false));
                binding.forceCloseInfo.sendOrderClosePicList.addItemDecoration(new SpacesItemDecoration(18));
                binding.forceCloseInfo.sendOrderClosePicList.setAdapter(adapter);
                PicUrlModelConvert convert = new PicUrlModelConvert();
                List<PicUrlModel> modelList = convert.stringToSomeObjectList(extForceClose.getApplyFiles());
                adapter.updateList(modelList);
            }
        }
    }


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
                    .maxSelectable(MAX_PHOTO_SIZE - photoListFormAdapter.getSelectedPhotos().size())
                    //                .maxSelectable(4 - (photoSelectAdapter.getItemCount() - 1))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new Glide4Engine())
                    .forResult(RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK);
        }, SendOrderDetailActivity.this);
        //申请闭单后关闭详情页面
        LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                SendOrderDetailActivity.this.finish();
            }
        });
        binding.applyForceCloseAndPostpone.resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(RouterUtils.ACTIVITY_RESEND_ORDER)
                        .withString(RouteKey.KEY_CUSTOM_TYPE, CustomEventTypeEnum.SEND_ORDER_TURN_ORDER.getTypeName())
                        .withString(RouteKey.KEY_TASK_ID, taskId)
                        .withString(RouteKey.KEY_ORDER_ID, orderId)
                        .withString(RouteKey.KEY_DIVIDE_ID, divideId)
                        .withString(RouteKey.KEY_PROJECT_ID, projectId)
                        .navigation();
            }
        });
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
        request.setId(detialModel.getData().getInfo().getID());
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
        int state = Integer.parseInt(detialModel.getData().getInfo().getStatus());
        if (detialModel.isReply() > 0) {
            reply();
            return;
        }
        if (state == OrderState.NEW.getState()) {
            takeOrder();//接单
        } else if (state == OrderState.HANDING.getState()) {
            submit();//处理-提交
        } else if (state == OrderState.APPLY.getState()) {
            checkAccept();//验收
        }
    }

    /**
     * 批复
     */
    private void reply() {
        viewModel.reply(taskId).observe(this, aBoolean -> {
            tipDialog.setTip(getString(R.string.text_reply_success));
            tipDialog.setTipDialogListener(dialog -> {
                dialog.dismiss();
                finish();
            });
            tipDialog.show();
        });
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
            request.setFScore(binding.checkAndAccept.ratingBar.getSelectedStarts()+"");
            request.setFCheckResult(checkResult);
            request.setFEvaluation(binding.checkAndAccept.ratingBar.getSelectedStarts() + "");
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
        if (TextUtils.isEmpty(checkResult)) {
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
                    .withSerializable(RouteKey.KEY_ORDER_DETAIL_EXTEN, detialModel.getExtensionApplication())
                    .withString(RouteKey.KEY_ORDER_ID, detialModel.getData().getInfo().getID())
                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                    .navigation();
        }
        if (v.getId() == R.id.apply_postpone) {
            ARouter.getInstance()
                    .build(RouterUtils.ACTIVITY_CLOSE)
                    .withString(RouteKey.KEY_ORDER_ID, detialModel.getData().getInfo().getID())
                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                    .withString(RouteKey.KEY_TASK_ID, taskId)
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
        try {
            return userModuleService.getUserId().equals(detialModel.getData().getInfo().getCheckID());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置图片间隔
     */
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
        }
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
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
