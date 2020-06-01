package com.einyun.app.pms.plan.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.db.bean.SubInspectionWorkOrderFlowNode;
import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.db.entity.PatrolLocal;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.util.Base64Util;
import com.einyun.app.base.util.BitmapUtil;
import com.einyun.app.base.util.JsonUtil;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.constants.WorkOrder;
import com.einyun.app.common.manager.BasicDataManager;
import com.einyun.app.common.manager.BasicDataTypeEnum;
import com.einyun.app.common.manager.GetUploadJson;
import com.einyun.app.common.model.BasicData;
import com.einyun.app.common.model.PageUIState;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.ResultState;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.ui.dialog.CreateNewOrderDialog;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.common.ui.widget.TipDialog;
import com.einyun.app.common.utils.CaptureUtils;
import com.einyun.app.common.utils.Glide4Engine;

import com.einyun.app.library.portal.dictdata.net.URLS;
import com.einyun.app.library.resource.workorder.model.ApplyType;
import com.einyun.app.library.resource.workorder.model.ExtensionApplication;
import com.einyun.app.library.resource.workorder.model.OrderState;
import com.einyun.app.library.resource.workorder.model.PlanInfo;
import com.einyun.app.library.resource.workorder.model.Sub_jhgdgzjdb;
import com.einyun.app.library.resource.workorder.model.Sub_jhgdzyb;
import com.einyun.app.library.resource.workorder.model.WorkOrderTypeModel;
import com.einyun.app.library.resource.workorder.model.Zyjhgd;
import com.einyun.app.library.resource.workorder.net.request.IsClosedRequest;
import com.einyun.app.library.resource.workorder.net.request.PatrolSubmitRequest;
import com.einyun.app.library.upload.model.PicUrl;
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse;
import com.einyun.app.pms.plan.BR;
import com.einyun.app.pms.plan.R;
import com.einyun.app.pms.plan.databinding.ActivityPlanOrderDetailBinding;
import com.einyun.app.pms.plan.databinding.ItemPlanResouceBinding;
import com.einyun.app.pms.plan.databinding.ItemPlanWorkNodeBinding;
import com.einyun.app.pms.plan.model.PlanOrderResLineModel;
import com.einyun.app.pms.plan.viewmodel.PlanOdViewModelFactory;
import com.einyun.app.pms.plan.viewmodel.PlanOrderDetailViewModel;
import com.google.gson.Gson;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PLAN_OWRKORDER_PENDING;
import static com.einyun.app.library.resource.workorder.net.URLs.URL_RESOURCE_WORKORDER_PLAN_QRCODE;

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
    @Autowired(name = RouteKey.KEY_IS_ORDER_LIST)
    boolean isOrderList;

    private PlanInfo planInfo;
    private File imageFile;

    private int mClickPosition;
    boolean isSubmit = true;
    private List<WorkOrderTypeModel> lines;
    private List<PlanOrderResLineModel> mPlanResLines = new ArrayList<>();

    @Override
    protected PlanOrderDetailViewModel initViewModel() {
        return new ViewModelProvider(this, new PlanOdViewModelFactory()).get(PlanOrderDetailViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_plan_order_detail;
    }

    /**
     * 选择指派人
     */
    private void selectPeple() {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_SELECT_PEOPLE)
                .withString(RouteKey.KEY_DIVIDE_ID, planInfo.getData().getZyjhgd().getF_DIVIDE_ID())
                .withString(RouteKey.KEY_PROJECT_ID, planInfo.getData().getZyjhgd().getF_PROJECT_ID())
                .navigation();
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_plan_order);
        setRightOption(R.drawable.histroy);
        setRightTxt(R.string.text_histroy);
        setRightTxtColor(R.color.blueTextColor);
        binding.setCallBack(this);

        binding.sendOrder.repairSelectPeople.setOnClickListener(view -> {
            selectPeple();
        });
        if (FRAGMENT_PLAN_OWRKORDER_DONE.equals(fragmentTag)) {
            binding.cvResultEdit.setVisibility(View.GONE);
            binding.cvOperate.setVisibility(View.GONE);
            binding.btnSubmit.setVisibility(View.GONE);
        }
        binding.itemApplyLateInfo.imgList.addItemDecoration(new SpacesItemDecoration(18));
        binding.itemAlreadyResult.imgList.addItemDecoration(new SpacesItemDecoration(18));
        binding.itemCloseOrderInfo.imgList.addItemDecoration(new SpacesItemDecoration(18));
        viewModel.isClosedLiveData.observe(this, isClosedState -> {
            if (isClosedState.isClosed()) {
                if (isClosedState.getType().equals(WorkOrder.POSTPONED_PLAN)) {
                    //还需要传入参数
                    ARouter.getInstance()
                            .build(RouterUtils.ACTIVITY_LATE)
                            .withString(RouteKey.KEY_ORDER_ID, id)
                            .withSerializable(RouteKey.KEY_ORDER_DETAIL_EXTEN, planInfo.getExtensionApplication())
                            .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                            .withString(RouteKey.KEY_LATER_ID, RouteKey.KEY_PLAN)
                            .navigation();
                } else if (isClosedState.getType().equals(WorkOrder.FORCE_CLOSE_PLAN)) {
                    isCloseClose = isClosedState.isClosed();
                }
            } else {
                if (isClosedState.getType().equals(WorkOrder.FORCE_CLOSE_PLAN)) {//TODO 强制闭单申请中 要隐藏 派单 模块
                    isCloseClose = isClosedState.isClosed();
                    binding.cvResultEdit.setVisibility(View.GONE);
                    binding.cvOperate.setVisibility(View.GONE);
                    binding.btnSubmit.setVisibility(View.GONE);
                    nodesAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.show(CommonApplication.getInstance(), R.string.text_applying_wait);
                }

            }
        });
        LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                finish();
            }
        });
        //选择人员
        LiveEventBus.get(LiveDataBusKey.POST_RESEND_ORDER_USER, GetMappingByUserIdsResponse.class).observe(this, model -> {
            binding.sendOrder.repairSelectedPepple.setText(model.getFullname());
            planInfo.getData().getZyjhgd().setF_PROCESS_NAME(model.getFullname());
            planInfo.getData().getZyjhgd().setF_PROCESS_ID(model.getId());
            planInfo.getData().getZyjhgd().setF_OWNER_NAME(model.getFullname());
            planInfo.getData().getZyjhgd().setF_OWNER_ID(model.getId());
        });
        viewModel.getLastWorthTime("ldzd").observe(this, model -> {

            mPlanResLines = model;
        });
    }

    //false为不可以闭单  true为可以闭单
    private boolean isCloseClose = true;

    @Override
    protected void initData() {
        super.initData();
        //图片选择适配器
        photoSelectAdapter = new PhotoSelectAdapter(this);
        binding.pointCkImglist.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//设置横向
        binding.pointCkImglist.addItemDecoration(new SpacesItemDecoration(18));
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
                        String f_status = planInfo.getData().getZyjhgd().getF_STATUS();
                        if (FRAGMENT_PLAN_OWRKORDER_DONE.equals(fragmentTag) || !isCloseClose || "5".equals(f_status) || "6".equals(f_status)) {//接单模式在并上一个接单的状态
                            if (!TextUtils.isEmpty(model.result)) {
                                //成功
                                if ("1".equals(nodes.get(position).getResult())) {
                                    //选中通过
                                    onAgree(binding);
                                    binding.btnReject.setVisibility(View.GONE);
                                } else if ("0".equals(nodes.get(position).getResult())) {
                                    //选中不通过
                                    onReject(binding);
                                    binding.btnAgree.setVisibility(View.GONE);
                                }
                            } else {
                                binding.btnReject.setVisibility(View.VISIBLE);
                                binding.btnAgree.setVisibility(View.VISIBLE);
                                binding.btnReject.setEnabled(false);
                                binding.btnAgree.setEnabled(false);
                            }
                        } else {//接单后 这里还要设置 enable
                            if (!TextUtils.isEmpty(model.result)) {
                                if (ResultState.RESULT_FAILD.equals(model.result)) {
                                    onReject(binding);
                                } else if (ResultState.RESULT_FAILD.equals(model.result)) {
                                    onAgree(binding);
                                }
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
                        model.setResult(ResultState.RESULT_FAILD);
                    });
                }

                //通过
                protected void agree(ItemPlanWorkNodeBinding binding, WorkNode model) {
                    binding.btnAgree.setOnClickListener((View v) -> {
                        onAgree(binding);
                        model.setResult(ResultState.RESULT_SUCCESS);
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
                    String f_status1 = planInfo.getData().getZyjhgd().getF_STATUS();
                    if (f_status1.equals("5") || f_status1.equals("6")) {
                        binding.llForceScan.setVisibility(View.VISIBLE);
                        if (model.is_forced() == 0) {//  0 不强制
                            binding.llScanReasult.setVisibility(View.GONE);
                        } else {//1 强制扫码

                            binding.llScanReasult.setVisibility(View.VISIBLE);
                        }
                    } else {
                        switch (fragmentTag) {
                            case FRAGMENT_PLAN_OWRKORDER_PENDING://待跟进
                                binding.llForceScan.setVisibility(View.VISIBLE);
                                if (model.is_forced() == 0) {//  0 不强制
                                    binding.ivScan.setVisibility(View.GONE);
                                    binding.llScanReasult.setVisibility(View.GONE);
                                } else {//1 强制扫码
                                    binding.ivScan.setVisibility(View.VISIBLE);
                                    binding.llScanReasult.setVisibility(View.VISIBLE);
                                }
                                break;
                            case FRAGMENT_PLAN_OWRKORDER_DONE:
                                binding.llForceScan.setVisibility(View.VISIBLE);
                                if (model.is_forced() == 0) {//  0 不强制
                                    binding.llScanReasult.setVisibility(View.GONE);
                                } else {//1 强制扫码

                                    binding.llScanReasult.setVisibility(View.VISIBLE);
                                }
                                break;
                        }
                    }
                    ExtensionApplication extForceClose = planInfo.getExt(ApplyType.FORCECLOSE.getState());
                    if (extForceClose == null) {
                        String f_status = planInfo.getData().getZyjhgd().getF_STATUS();
                        Log.e(TAG, "onBindItem: f_status==" + f_status);
                        if ("4".equals(f_status)) {

                            binding.tvScanReasult.setText("成功");

                        } else {
                            if (model.is_suc() == 1) {

                                binding.tvScanReasult.setText("成功");
                            } else if (model.is_suc() == 2) {
                                binding.tvScanReasult.setText("失败");
                            } else {
                                binding.tvScanReasult.setText("");

                            }
                        }
                    } else {
                        if (model.is_suc() == 1) {

                            binding.tvScanReasult.setText("成功");
                        } else if (model.is_suc() == 2) {
                            binding.tvScanReasult.setText("失败");
                        } else {
                            binding.tvScanReasult.setText("");

                        }
                    }
                    if (isOrderList) {
//                        binding.llScanReasult.setVisibility(View.GONE);
//                        binding.llForceScan.setVisibility(View.GONE);
                        binding.ivScan.setVisibility(View.GONE);
                    }
                    for (PlanOrderResLineModel line : mPlanResLines) {
                        String f_sp_type = model.getF_SP_TYPE();

                        String key = line.getKey();

                        if (key.equals(f_sp_type)) {

                            binding.tvAirType.setText(line.getName());
                            Log.e(TAG, "onBindItem: 空间分类" + line.getName());
                        }

                    }
                    binding.ivScan.setOnClickListener(view -> {
                        mClickPosition = position;
                        ARouter.getInstance()
                                .build(RouterUtils.ACTIVITY_SCANNER)
                                .navigation(PlanOrderDetailActivity.this, RouterUtils.ACTIVITY_REQUEST_SCANNER);
                    });
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_plan_resouce;
                }
            };
        }
        binding.rvResource.setAdapter(resourceAdapter);
        requestData();
    }

    private void requestData() {

        //加载数据
        viewModel.loadDetail(proInsId, taskId, taskNodeId, fragmentTag).observe(this, planInfo -> {
            if (planInfo == null || planInfo.getData() == null) {
                return;
            }
            updateUI(planInfo);
            updateElapsedTime(planInfo);
            if (planInfo != null && planInfo.getData() != null && planInfo.getData().getZyjhgd().getSub_jhgdzyb() != null && planInfo.getData().getZyjhgd().getSub_jhgdzyb().size() != 0) {
                binding.tvResource.setText(String.format(getResources().getString(R.string.text_already_resource), "" + planInfo.getData().getZyjhgd().getSub_jhgdzyb().size()));
                resourceAdapter.setDataList(planInfo.getData().getZyjhgd().getSub_jhgdzyb());
            } else {
                binding.cdWorkResouce.setVisibility(View.GONE);
            }
            Log.e(TAG, "requestData: id==" + id);
            if (id == null) {
//                id=planInfo.getData().getZyjhgd().getId_();
                id = planInfo.getData().getZyjhgd().getId_();
            }
            Log.e(TAG, "requestData: id==" + id);
            IsClosedRequest request = new IsClosedRequest();
            request.setId(id);
            request.setType(WorkOrder.FORCE_CLOSE_PLAN);
            viewModel.isClosed(request);
        });
    }

    private void updateElapsedTime(PlanInfo planInfo) {
        if (planInfo != null && planInfo.getData() != null && planInfo.getData().getZyjhgd() != null && StringUtil.isNullStr(planInfo.getData().getZyjhgd().getF_CREATE_TIME())) {
            createTime = planInfo.getData().getZyjhgd().getF_CREATE_TIME();
            if (planInfo.getData().getZyjhgd().getF_STATUS().equals(String.valueOf(OrderState.CLOSED.getState()))) {
                if (StringUtil.isNullStr(planInfo.getData().getZyjhgd().getF_ACT_FINISH_TIME()))
                    binding.tvHandleTime.setText(TimeUtil.getTimeExpend(planInfo.getData().getZyjhgd().getF_CREATE_TIME(), planInfo.getData().getZyjhgd().getF_ACT_FINISH_TIME()));
            } else {
                binding.tvHandleTime.setText(TimeUtil.getTimeExpend(planInfo.getData().getZyjhgd().getF_CREATE_TIME()));
                runnable.run();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        requestData();
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
//            imageFile = CaptureUtils.startCapture(this);//只能拍照
            Matisse.from(this) //加号添加图片  拍照本地都可以选择
                    .choose(MimeType.ofImage())
                    .captureStrategy(new CaptureStrategy(true, DataConstants.DATA_PROVIDER_NAME))
                    .capture(true)
                    .countable(true)
                    .maxSelectable(MAX_PHOTO_SIZE - photoSelectAdapter.getSelectedPhotos().size())
                    //                .maxSelectable(4 - (photoSelectAdapter.getItemCount() - 1))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new Glide4Engine())
                    .forResult(RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK);
        }, this);
    }

    List<WorkNode> nodes = new ArrayList<>();

    private void updateUI(PlanInfo planInfo) {
        if (planInfo == null) {
            updatePageUIState(PageUIState.LOAD_FAILED.getState());
            return;
        }
        this.planInfo = planInfo;
        updatePageUIState(PageUIState.FILLDATA.getState());
        showResult();
        showPostpone();
        showForceClose();
        if (nodes.size() < 1) {
            nodes = viewModel.loadNodes(planInfo);
            nodes.add(0, new WorkNode());
            nodesAdapter.setDataList(nodes);
        }

        binding.setDetail(planInfo.getData().getZyjhgd());
    }

    protected void updatePageUIState(int state) {
        binding.pageState.setPageState(state);
    }

    /**
     * 展示处理信息
     */
    private void showResult() {
        Zyjhgd zyjhgd = planInfo.getData().getZyjhgd();
        if (String.valueOf(OrderState.CLOSED.getState()).equals(planInfo.getData().getZyjhgd().getF_STATUS())) {
            binding.cvResultEdit.setVisibility(View.GONE);
            binding.cvOperate.setVisibility(View.GONE);
            binding.btnSubmit.setVisibility(View.GONE);
            binding.itemAlreadyResult.cv.setVisibility(View.VISIBLE);
            binding.itemAlreadyResult.setDetail(zyjhgd);
            if (zyjhgd.getF_FILES() != null) {
                PhotoListAdapter adapter = new PhotoListAdapter(this);
                binding.itemAlreadyResult.imgList.setLayoutManager(new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false));
                binding.itemAlreadyResult.imgList.setAdapter(adapter);
                PicUrlModelConvert convert = new PicUrlModelConvert();
                List<PicUrlModel> modelList = convert.stringToSomeObjectList(zyjhgd.getF_FILES());
                adapter.updateList(modelList);
            }
        } else if (String.valueOf(OrderState.PENDING.getState()).equals(zyjhgd.getF_STATUS())) {//添加 f_status 接单中
            binding.btnSubmit.setText("接单");
            binding.cvResultEdit.setVisibility(View.GONE);
            binding.cvOperate.setVisibility(View.GONE);

        } else if (String.valueOf(OrderState.OVER_DUE.getState()).equals(zyjhgd.getF_STATUS())) {//添加 f_status 派单 显示不同布局
            binding.cvResultEdit.setVisibility(View.GONE);
            binding.cvOperate.setVisibility(View.GONE);
            if (!FRAGMENT_PLAN_OWRKORDER_DONE.equals(fragmentTag)) {
                binding.sendOrder.getRoot().setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 显示申请延期信息
     */
    private void showPostpone() {
        ExtensionApplication extPostpone = planInfo.getExt(ApplyType.POSTPONE.getState());
        if (extPostpone != null) {
            binding.itemApplyLateInfo.cv.setVisibility(View.VISIBLE);
            binding.itemApplyLateInfo.setExt(extPostpone);
            if (extPostpone.getApplyFiles() != null) {
                PhotoListAdapter adapter = new PhotoListAdapter(this);
                binding.itemApplyLateInfo.imgList.setLayoutManager(new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false));
                binding.itemApplyLateInfo.imgList.setAdapter(adapter);
                PicUrlModelConvert convert = new PicUrlModelConvert();
                List<PicUrlModel> modelList = convert.stringToSomeObjectList(extPostpone.getApplyFiles());
                adapter.updateList(modelList);
            }
        } else {
            binding.itemApplyLateInfo.cv.setVisibility(View.GONE);
        }
    }

    /**
     * 显示强制闭单信息
     */
    private void showForceClose() {
        ExtensionApplication extForceClose = planInfo.getExt(ApplyType.FORCECLOSE.getState());
        if (extForceClose != null) {
            binding.itemCloseOrderInfo.cv.setVisibility(View.VISIBLE);
            binding.itemCloseOrderInfo.setExt(extForceClose);
            if (extForceClose.getApplyFiles() != null) {
                PhotoListAdapter adapter = new PhotoListAdapter(this);
                binding.itemCloseOrderInfo.imgList.setLayoutManager(new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false));

                binding.itemCloseOrderInfo.imgList.setAdapter(adapter);
                PicUrlModelConvert convert = new PicUrlModelConvert();
                List<PicUrlModel> modelList = convert.stringToSomeObjectList(extForceClose.getApplyFiles());
                adapter.updateList(modelList);
            }
        } else {
            binding.itemCloseOrderInfo.cv.setVisibility(View.GONE);
        }
    }


    /**
     * 跳转申请延期
     */
    public void applyPostpone() {
        IsClosedRequest request = new IsClosedRequest();
        request.setId(id);
        request.setType(WorkOrder.POSTPONED_PLAN);
        viewModel.isClosed(request, true);
    }

    /**
     *
     */
    public void closeOrder() {
        if (isCloseClose) {
            ARouter.getInstance()
                    .build(RouterUtils.ACTIVITY_CLOSE)
                    .withString(RouteKey.KEY_ORDER_ID, id)
                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                    .withString(RouteKey.KEY_TASK_ID, taskId)
                    .withString(RouteKey.KEY_CLOSE_ID, RouteKey.KEY_PLAN)
                    .navigation();
        } else {
            ToastUtil.show(CommonApplication.getInstance(), R.string.text_applying_wait);
        }
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
        return all.subList(1, all.size());
    }

    /**
     * 上传图片
     *
     * @param planInfo
     */
    private void uploadImages(PlanInfo planInfo) {
        if (planInfo == null) {
            return;
        }
        viewModel.uploadImages(photoSelectAdapter.getSelectedPhotos()).observe(this, picUrls -> {
            wrapFormData(planInfo, picUrls);
            acceptForm(planInfo);
        });

    }


    /**
     * 包装表单数据
     *
     * @param patrol
     * @param picUrls
     * @return
     */
    private void wrapFormData(PlanInfo patrol, List<PicUrl> picUrls) {
        GetUploadJson getUploadJsonStr = new GetUploadJson(picUrls).invoke();
        Gson gson = getUploadJsonStr.getGson();
        List<PicUrlModel> picUrlModels = getUploadJsonStr.getPicUrlModels();
        patrol.getData().getZyjhgd().setF_STATUS(String.valueOf(OrderState.CLOSED.getState()));
        patrol.getData().getZyjhgd().setF_FILES(gson.toJson(picUrlModels));//包装上传图片信息
        patrol.getData().getZyjhgd().setF_CONTENT(binding.limitInput.getString());//包装节点选择信息
    }

    private boolean hasException() {
        int index = 0; //异常节点选项
        for (Sub_jhgdgzjdb node : planInfo.getData().getZyjhgd().getSub_jhgdgzjdb()) {
            for (WorkNode workNode : getWorkNodes()) {
                if (node.getF_WK_ID().equals(workNode.number)) {
                    node.setF_WK_RESULT(workNode.getResult());
                    if (workNode.result.equals("0")) index++;
                }
            }
        }
        return index > 0;
    }

    TipDialog tipDialog;

    private void acceptForm(PlanInfo patrol) {
        hasException();
        patrol.getData().getZyjhgd().setF_ACT_FINISH_TIME(getTime());
        Log.e("传参  patrol  为", JsonUtil.toJson(patrol));
        String base64 = Base64Util.encodeBase64(new Gson().toJson(patrol.getData()));
        PatrolSubmitRequest request = new PatrolSubmitRequest(taskId, PatrolSubmitRequest.ACTION_AGREE, base64, patrol.getData().getZyjhgd().getId_());
        viewModel.submit(request).observe(this, aBoolean -> {
            if (aBoolean) {
//                tipDialog = new TipDialog(this, getString(R.string.text_handle_success));
//                tipDialog.setTipDialogListener(dialog -> {
//                    if (hasException()) {
//                        createSendOrder();
//                    } else {
//                        finish();
//                    }
//                });
//                tipDialog.show();
                if (hasException()) {
                    createSendOrder();
//                    ToastUtil.show(PlanOrderDetailActivity.this,"工单处理成功");
                } else {
                    ToastUtil.show(PlanOrderDetailActivity.this, "工单提交成功");
                    finish();
                }

            }
        });
    }

    private String getTime() {
        long time = System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date(time);
        String t1 = format.format(d1);
        return t1;
    }

    CreateNewOrderDialog alertDialog;

    /**
     * 是否创建派工单
     */
    private void createSendOrder() {
        if (alertDialog == null) {
            alertDialog = new CreateNewOrderDialog(this).builder()
                    .setCreateSendOrder(v -> {
                        goPaiGongDan(); //跳转至创建派工单
                    }).setCreateUnOrder(v -> {
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PROPERTY_CREATE).navigation();
                        finish();
                    }).setCancel(v -> {
                        finish();
                    });
            alertDialog.show();
        }
    }

    private void goPaiGongDan() {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_CREATE_SEND_ORDER)
                .withString(RouteKey.KEY_ORDER_ID, id)
                .withString(RouteKey.KEY_ORDER_NO, planInfo.getData().getZyjhgd().getF_ORDER_NO())
                .withString(RouteKey.KEY_LINE, planInfo.getData().getZyjhgd().getF_TX_NAME())
                .withString(RouteKey.KEY_RESOUSE, planInfo.getData().getZyjhgd().getF_RES_NAME())
//                .withString(RouteKey.KEY_ORDER_ID, data.getID_())
                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                .withString(RouteKey.KEY_TASK_ID, taskId)
                .withString(RouteKey.KEY_TASK_NODE_ID, taskNodeId)
                .withString(RouteKey.KEY_FRAGEMNT_TAG, FRAGMENT_PLAN_OWRKORDER_DONE)
                .navigation();
        finish();
    }


    /**
     * 提交
     */
    public void onSubmitClick() {//根据 f_status 判断调用不同接口
        if (String.valueOf(OrderState.PENDING.getState()).equals(planInfo.getData().getZyjhgd().getF_STATUS())) {
//            planInfo.getData().getZyjhgd().setF_ACT_FINISH_TIME(getTime());
            planInfo.getData().getZyjhgd().setF_STATUS("2");
            planInfo.getData().getZyjhgd().setF_OWNER_NAME(viewModel.getUserName());
            planInfo.getData().getZyjhgd().setF_OWNER_ID(viewModel.getUserID());
            planInfo.getData().getZyjhgd().setF_PROCESS_NAME(viewModel.getUserName());
            planInfo.getData().getZyjhgd().setF_PROCESS_ID(viewModel.getUserID());
            Log.e("传参  patrol  为", JsonUtil.toJson(planInfo));
            String base64 = Base64Util.encodeBase64(new Gson().toJson(planInfo.getData()));
            PatrolSubmitRequest request = new PatrolSubmitRequest(taskId, PatrolSubmitRequest.ACTION_AGREE, base64, planInfo.getData().getZyjhgd().getId_());
            viewModel.receiceOrder(request).observe(this, model -> {

                if (model) {
                    finish();
                }
            });

        } else if (String.valueOf(OrderState.OVER_DUE.getState()).equals(planInfo.getData().getZyjhgd().getF_STATUS())) {
            planInfo.getData().getZyjhgd().setF_STATUS("2");
            planInfo.getData().getZyjhgd().setF_SEND_REMARK(binding.sendOrder.repairSendReason.getString());
            Log.e("传参  patrol  为", JsonUtil.toJson(planInfo));
            String base64 = Base64Util.encodeBase64(new Gson().toJson(planInfo.getData()));
            PatrolSubmitRequest request = new PatrolSubmitRequest(taskId, PatrolSubmitRequest.ACTION_AGREE, base64, planInfo.getData().getZyjhgd().getId_());
            viewModel.assignOrder(request).observe(this, model -> {

                if (model) {
                    finish();
                }
            });
        } else {
            isSubmit = true;
            if (!validateFormData()) {
                return;
            }
            if (validateForceScan()) return;
            uploadImages(planInfo);
        }
    }

    private boolean validateForceScan() {
        List<Sub_jhgdzyb> sub_jhgdzyb = planInfo.getData().getZyjhgd().getSub_jhgdzyb();
        if (sub_jhgdzyb != null && sub_jhgdzyb.size() > 0) {

            for (Sub_jhgdzyb dzyb : sub_jhgdzyb) {
                if (dzyb.is_forced() == 1) {//强制扫码下 有 失败的 不准提交
                    if (dzyb.is_suc() != 1) {//0 失败
                        isSubmit = false;

                    }
                }
            }
        }
        if (!isSubmit) {
            new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
                    .setMsg("请完成扫码！")
                    .setPositiveButton("我知道了", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();

            return true;
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //本地和拍照都有
        if (requestCode == RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK) {
            if (data == null) return;
            List<Uri> uris = Matisse.obtainResult(data);
            if (uris != null && uris.size() > 0) {
                photoSelectAdapter.addPhotos(uris);
//                cachePhoto(photoSelectAdapter.getSelectedPhotos());
            }
        }
        //原来只能拍照得
        if (requestCode == RouterUtils.ACTIVITY_REQUEST_CAMERA_OK && resultCode == RESULT_OK) {
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(this, DataConstants.DATA_PROVIDER_NAME, imageFile);
            } else {
                uri = Uri.fromFile(imageFile);
            }
            Observable.just(imageFile).subscribeOn(Schedulers.io())
                    .subscribe(file -> {
                        BitmapUtil.AddTimeWatermark(file);
                        runOnUiThread(() -> {
                            if (uri != null) {
                                photoSelectAdapter.addPhotos(Arrays.asList(uri));
                            }
                        });
                    });
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == RouterUtils.ACTIVITY_REQUEST_SCANNER) {
//                f_RES_CODE
                String stringExtra = data.getStringExtra(DataConstants.KEY_SCANNER_CONTENT);//校验code是不是正确的
                String subCode = stringExtra.substring(2, stringExtra.length());
                viewModel.checkQrCode(URL_RESOURCE_WORKORDER_PLAN_QRCODE + "/" + subCode).observe(this, aBoolean -> {

                    Log.e(TAG, "onActivityResult: " + aBoolean);
                    if (aBoolean.getResourceCode().equals(planInfo.getData().getZyjhgd().getSub_jhgdzyb().get(mClickPosition).getF_RES_CODE())) {
                        planInfo.getData().getZyjhgd().getSub_jhgdzyb().get(mClickPosition).setF_RES_CODE(aBoolean.getResourceCode());
                        planInfo.getData().getZyjhgd().getSub_jhgdzyb().get(mClickPosition).set_suc(1);
                        resourceAdapter.setDataList(planInfo.getData().getZyjhgd().getSub_jhgdzyb());
//                        resourceAdapter.notifyItemChanged(mClickPosition);
                    } else {
                        planInfo.getData().getZyjhgd().getSub_jhgdzyb().get(mClickPosition).set_suc(2);
                        resourceAdapter.setDataList(planInfo.getData().getZyjhgd().getSub_jhgdzyb());
//                        resourceAdapter.notifyItemChanged(mClickPosition);
                        ToastUtil.show(CommonApplication.getInstance(), "工单号不匹配");
                    }
                });
//                planInfo.getData().getZyjhgd().getSub_jhgdzyb().get(mClickPosition).setF_RES_CODE(data.getStringExtra(DataConstants.KEY_SCANNER_CONTENT));
//                planInfo.getData().getZyjhgd().getSub_jhgdzyb().get(mClickPosition).set_suc(1);
//                resourceAdapter.setDataList(planInfo.getData().getZyjhgd().getSub_jhgdzyb());
//                ToastUtil.show(PlanOrderDetailActivity.this, data.getStringExtra(DataConstants.KEY_SCANNER_CONTENT));
            }
        }
    }

    private static final String TAG = "PlanOrderDetailActivity";

    @Override
    public void onRightOptionClick(View view) {
        super.onRightOptionClick(view);
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_HISTORY)
                .withString(RouteKey.KEY_ORDER_ID, id)
                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                .navigation();
    }

    public void showOrHideOrderInfo() {
        if (View.VISIBLE == binding.llOrderContent.getVisibility()) {
            binding.llOrderContent.setVisibility(View.GONE);
            binding.ivOrderLine.setVisibility(View.GONE);
            binding.ivOrderArrow.setImageDrawable(getResources().getDrawable(R.mipmap.icon_arrow_right));
        } else {
            binding.llOrderContent.setVisibility(View.VISIBLE);
            binding.ivOrderLine.setVisibility(View.VISIBLE);
            binding.ivOrderArrow.setImageDrawable(getResources().getDrawable(R.mipmap.icon_arrow_down_grey));
        }
    }

    public void showOrHideResource() {
        if (View.VISIBLE == binding.rvResource.getVisibility()) {
            binding.rvResource.setVisibility(View.GONE);
            binding.ivResourceLine.setVisibility(View.GONE);
            binding.ivResourceArrow.setImageDrawable(getResources().getDrawable(R.mipmap.icon_arrow_right));
        } else {
            binding.rvResource.setVisibility(View.VISIBLE);
            binding.ivResourceLine.setVisibility(View.VISIBLE);
            binding.ivResourceArrow.setImageDrawable(getResources().getDrawable(R.mipmap.icon_arrow_down_grey));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
