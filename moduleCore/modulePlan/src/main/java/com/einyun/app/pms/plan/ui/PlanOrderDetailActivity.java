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
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.adapters.TextViewBindingAdapter;
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
import com.einyun.app.base.db.entity.PlanLocal;
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
import com.einyun.app.common.manager.CustomEventTypeEnum;
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

import com.einyun.app.common.utils.NetWorkUtils;
import com.einyun.app.library.portal.dictdata.net.URLS;
import com.einyun.app.library.resource.workorder.model.ApplyType;

import com.einyun.app.library.resource.workorder.model.ExtensionApplication;
import com.einyun.app.library.resource.workorder.model.OrderState;
//import com.einyun.app.library.resource.workorder.model.PlanInfo;
import com.einyun.app.base.db.entity.PlanInfo;


import com.einyun.app.library.resource.workorder.model.WorkOrderTypeModel;
import com.einyun.app.library.resource.workorder.net.request.IsClosedRequest;
import com.einyun.app.library.resource.workorder.net.request.PatrolSubmitRequest;
import com.einyun.app.library.upload.model.PicUrl;
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse;
import com.einyun.app.pms.plan.BR;
import com.einyun.app.pms.plan.R;
import com.einyun.app.pms.plan.convert.ExtensionApplicationPlanDBToWokerConvert;
import com.einyun.app.pms.plan.databinding.ActivityPlanOrderDetailBinding;
import com.einyun.app.pms.plan.databinding.ItemPlanResouceBinding;
import com.einyun.app.pms.plan.databinding.ItemPlanWorkNodeBinding;
import com.einyun.app.pms.plan.databinding.ItemPlanWorkNodeNewBinding;
import com.einyun.app.pms.plan.model.NodeImgs;
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
import java.io.Serializable;
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
    RVBindingAdapter<ItemPlanWorkNodeNewBinding, WorkNode> nodesAdapter;
    RVBindingAdapter<ItemPlanResouceBinding, PlanInfo.Data.Zyjhgd.Sub_jhgdzyb> resourceAdapter;
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
    @Autowired(name = RouteKey.KEY_DIVIDE_ID)
    String divideId;
    @Autowired(name = RouteKey.KEY_PROJECT_ID)
    String projectId;
    @Autowired(name = RouteKey.KEY_IS_ORDER_LIST)
    boolean isOrderList;

    private PlanInfo planInfo;
    private File imageFile;

    private int mClickPosition;
    boolean isSubmit = true;
    private List<WorkOrderTypeModel> lines;
    private List<PlanOrderResLineModel> mPlanResLines = new ArrayList<>();
    private PlanLocal planLocal;
    private String f_status;
    private int addImgPosition = 0;//?????????????????????item???position

    @Override
    protected PlanOrderDetailViewModel initViewModel() {
        return new ViewModelProvider(this, new PlanOdViewModelFactory()).get(PlanOrderDetailViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_plan_order_detail;
    }

    /**
     * ???????????????
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
        if (!NetWorkUtils.isNetworkConnected(CommonApplication.getInstance())) {
            ToastUtil.show(CommonApplication.getInstance(), "?????????????????????????????????");
        }
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
        binding.rvNodes.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false));
        viewModel.isClosedLiveData.observe(this, isClosedState -> {
            if (isClosedState != null) {
                if (isClosedState.isClosed()) {
                    if (isClosedState.getType().equals(WorkOrder.POSTPONED_PLAN)) {

                        ExtensionApplicationPlanDBToWokerConvert ss = new ExtensionApplicationPlanDBToWokerConvert();
                        List<ExtensionApplication> extensionApplications = ss.stringToSomeObjectList(new Gson().toJson(planInfo.getExtensionApplication()));
                        //?????????????????????
                        ARouter.getInstance()
                                .build(RouterUtils.ACTIVITY_LATE)
                                .withString(RouteKey.KEY_ORDER_ID, id)
                                .withSerializable(RouteKey.KEY_ORDER_DETAIL_EXTEN, (Serializable) extensionApplications)
                                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                                .withString(RouteKey.KEY_LATER_ID, RouteKey.KEY_PLAN)
                                .navigation();
                    } else if (isClosedState.getType().equals(WorkOrder.FORCE_CLOSE_PLAN)) {
                        isCloseClose = isClosedState.isClosed();
                    }
                } else {
                    if (isClosedState.getType().equals(WorkOrder.FORCE_CLOSE_PLAN)) {//TODO ????????????????????? ????????? ?????? ??????
                        isCloseClose = isClosedState.isClosed();
                        binding.cvResultEdit.setVisibility(View.GONE);
                        binding.cvOperate.setVisibility(View.GONE);
                        binding.btnSubmit.setVisibility(View.GONE);
                        nodesAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.show(CommonApplication.getInstance(), R.string.text_applying_wait);
                    }

                }
                planInfo.setClosed(isCloseClose);
                viewModel.planRepository.insertPlanInfo(planInfo);
            } else {
                if (planInfo.getClosed()) {
//                    if (isClosedState.getType().equals(WorkOrder.POSTPONED_PLAN)) {
//                        //?????????????????????
//                        ARouter.getInstance()
//                                .build(RouterUtils.ACTIVITY_LATE)
//                                .withString(RouteKey.KEY_ORDER_ID, id)
//                                .withSerializable(RouteKey.KEY_ORDER_DETAIL_EXTEN, (Serializable) planInfo.getExtensionApplication())
//                                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
//                                .withString(RouteKey.KEY_LATER_ID, RouteKey.KEY_PLAN)
//                                .navigation();
//                    } else if (isClosedState.getType().equals(WorkOrder.FORCE_CLOSE_PLAN)) {
//                        isCloseClose = isClosedState.isClosed();
//                    }
                } else {
//                    if (isClosedState.getType().equals(WorkOrder.FORCE_CLOSE_PLAN)) {//TODO ????????????????????? ????????? ?????? ??????
                    isCloseClose = planInfo.getClosed();
                    binding.cvResultEdit.setVisibility(View.GONE);
                    binding.cvOperate.setVisibility(View.GONE);
                    binding.btnSubmit.setVisibility(View.GONE);
                    nodesAdapter.notifyDataSetChanged();
//                    } else {
//                        ToastUtil.show(CommonApplication.getInstance(), R.string.text_applying_wait);
//                    }

                }
            }


        });
        LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                finish();
            }
        });
        //????????????
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

    //false??????????????????  true???????????????
    private boolean isCloseClose = true;

    @Override
    protected void initData() {
        super.initData();
        //?????????????????????
//        photoSelectAdapter = new PhotoSelectAdapter(this);
//        binding.pointCkImglist.setLayoutManager(new LinearLayoutManager(
//                this,
//                LinearLayoutManager.HORIZONTAL,
//                false));//????????????
//        binding.pointCkImglist.addItemDecoration(new SpacesItemDecoration(18));
//        binding.pointCkImglist.setAdapter(photoSelectAdapter);
//????????????????????????
        if (nodesAdapter == null) {
            nodesAdapter = new RVBindingAdapter<ItemPlanWorkNodeNewBinding, WorkNode>(this, BR.node) {
                @Override
                public void onBindItem(ItemPlanWorkNodeNewBinding binding, WorkNode model, int position) {
                    if (position==nodesAdapter.getDataList().size()-1){
                        binding.bottomView.setVisibility(View.GONE);
                    }else {
                        binding.bottomView.setVisibility(View.VISIBLE);
                    }
                    int dex=position+1;
                    binding.nodePosition.setText("[??????"+dex+"]");
                    binding.imgList.addItemDecoration(new SpacesItemDecoration(18));
                    binding.emptyViewResult.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });
                    binding.emptyViewImg.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });
                    if (model.getResult() != null) {
                        if (model.getResult().equals(ResultState.RESULT_SUCCESS)) {
                            binding.nodeResultYes.setChecked(true);
                        } else if (model.getResult().equals(ResultState.RESULT_FAILD)) {
                            binding.nodeResultNo.setChecked(true);
                        }
                    }
                    //??????
                    binding.nodeResult.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            if (checkedId == R.id.node_result_yes) {
                                model.setResult(ResultState.RESULT_SUCCESS);
                            } else if (checkedId == R.id.node_result_no) {
                                model.setResult(ResultState.RESULT_FAILD);
                            }
                        }
                    });
                    //????????????
                    binding.checkResult.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            model.setF_CHECK_RESULT(s.toString());
                        }
                    });
                    String f_status = planInfo.getData().getZyjhgd().getF_STATUS();
                    if (FRAGMENT_PLAN_OWRKORDER_DONE.equals(fragmentTag) || !isCloseClose || "5".equals(f_status) || "6".equals(f_status)) {//??????????????????????????????????????????
                        binding.emptyViewResult.setVisibility(View.VISIBLE);
                        binding.emptyViewImg.setVisibility(View.VISIBLE);
                        binding.checkResult.setEnabled(false);
                    } else {
                        binding.emptyViewResult.setVisibility(View.GONE);
                        binding.emptyViewImg.setVisibility(View.GONE);
                        binding.checkResult.setEnabled(true);
                    }


                    PhotoSelectAdapter photoSelectAdapter = new PhotoSelectAdapter(PlanOrderDetailActivity.this);
                    List<Uri> uris = new ArrayList<>();
                    for (String path : model.getSelectImgs()) {
                        uris.add(Uri.parse(path));
                    }
                    photoSelectAdapter.setSelectedPhotos(uris);
                    photoSelectAdapter.setItemChangeListener(new PhotoSelectAdapter.ItemChangeListener() {
                        @Override
                        public void onChange(List<Uri> urs) {
                            List<String> pathList = new ArrayList<>();
                            for (Uri uri : urs) {
                                pathList.add(uri + "");
                            }
                            model.setSelectImgs(pathList);
                        }
                    });
                    photoSelectAdapter.setAddListener(selectedSize -> {
                        addImgPosition = position;//?????????????????????
                        if (photoSelectAdapter.getSelectedPhotos().size() >= MAX_PHOTO_SIZE) {
                            ToastUtil.show(getApplicationContext(), R.string.upload_pic_max);
                            return;
                        }
//            imageFile = CaptureUtils.startCapture(this);//????????????
                        Matisse.from(PlanOrderDetailActivity.this) //??????????????????  ???????????????????????????
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
                    }, PlanOrderDetailActivity.this);
                    binding.imgList.setLayoutManager(new LinearLayoutManager(
                            PlanOrderDetailActivity.this,
                            LinearLayoutManager.HORIZONTAL,
                            false));
                    binding.imgList.setAdapter(photoSelectAdapter);
                    if (model.getF_NODE_PICTURE()!=null&&model.getF_NODE_PICTURE().length()>8){
                        binding.emptyViewImg.setVisibility(View.GONE);
                        PhotoListAdapter adapter = new PhotoListAdapter(PlanOrderDetailActivity.this);
                    binding.imgList.setAdapter(adapter);
                    PicUrlModelConvert convert = new PicUrlModelConvert();
                    List<PicUrlModel> modelList = convert.stringToSomeObjectList(model.getF_NODE_PICTURE());
                    adapter.updateList(modelList);}
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_plan_work_node_new;
                }
            };
        }
        /*//??????????????????
        if (nodesAdapter == null) {
            nodesAdapter = new RVBindingAdapter<ItemPlanWorkNodeBinding, WorkNode>(this, BR.node) {
                @Override
                public void onBindItem(ItemPlanWorkNodeBinding binding, WorkNode model, int position) {
                    //???????????????
                    if (position == 0) {
                        tableHead(binding);
                    } else {
                        //????????????
                        tableItem(binding, position);
                        //????????????
                        agree(binding, model, position);
                        //???????????????
                        reject(binding, model, position);
                        String f_status = planInfo.getData().getZyjhgd().getF_STATUS();
                        if (FRAGMENT_PLAN_OWRKORDER_DONE.equals(fragmentTag) || !isCloseClose || "5".equals(f_status) || "6".equals(f_status)) {//??????????????????????????????????????????
                            if (!TextUtils.isEmpty(model.result)) {
                                //??????
                                if ("1".equals(nodes.get(position).getResult())) {
                                    //????????????
                                    onAgree(binding);
                                    binding.btnReject.setVisibility(View.GONE);
                                } else if ("0".equals(nodes.get(position).getResult())) {
                                    //???????????????
                                    onReject(binding);
                                    binding.btnAgree.setVisibility(View.GONE);
                                }
                            } else {
                                binding.btnReject.setVisibility(View.VISIBLE);
                                binding.btnAgree.setVisibility(View.VISIBLE);
                                binding.btnReject.setEnabled(false);
                                binding.btnAgree.setEnabled(false);
                                binding.btnAgree.setBackgroundResource(R.drawable.shape_button_corners_grey);
                                binding.btnReject.setBackgroundResource(R.drawable.shape_button_corners_grey);
                                binding.btnAgree.setTextColor(getResources().getColor(R.color.white));
                                binding.btnReject.setTextColor(getResources().getColor(R.color.white));
                            }
                        } else {//????????? ?????????????????? enable
                            if (!TextUtils.isEmpty(model.result)) {
                                if (ResultState.RESULT_FAILD.equals(model.result)) {
                                    onReject(binding);
                                } else if (ResultState.RESULT_SUCCESS.equals(model.result)) {
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

                //?????????
                protected void reject(ItemPlanWorkNodeBinding binding, WorkNode model, int position) {
                    binding.btnReject.setOnClickListener(v -> {
                        onReject(binding);
                        model.setResult(ResultState.RESULT_FAILD);
                        saveLocalData();
                    });
                }

                //??????
                protected void agree(ItemPlanWorkNodeBinding binding, WorkNode model, int position) {
                    binding.btnAgree.setOnClickListener((View v) -> {
                        onAgree(binding);
                        model.setResult(ResultState.RESULT_SUCCESS);
                        saveLocalData();
                    });
                }

                //????????????
                protected void tableItem(ItemPlanWorkNodeBinding binding, int position) {
                    binding.tvNumber.setText(position + "");
                    binding.tvWorkNode.setVisibility(View.VISIBLE);
                    binding.tvResult.setVisibility(View.GONE);
                    binding.btnAgree.setVisibility(View.VISIBLE);
                    binding.btnReject.setVisibility(View.VISIBLE);
                    binding.tvWorkThings.setGravity(Gravity.LEFT);
                }

                //????????????
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
        }*/
        binding.rvNodes.setAdapter(nodesAdapter);

        //????????????
        if (resourceAdapter == null) {
            resourceAdapter = new RVBindingAdapter<ItemPlanResouceBinding, PlanInfo.Data.Zyjhgd.Sub_jhgdzyb>(this, BR.resource) {
                @Override
                public void onBindItem(ItemPlanResouceBinding binding, PlanInfo.Data.Zyjhgd.Sub_jhgdzyb model, int position) {
                    String f_status1 = planInfo.getData().getZyjhgd().getF_STATUS();
                    if (f_status1.equals("5") || f_status1.equals("6")) {
                        binding.llForceScan.setVisibility(View.VISIBLE);
                        if (model.getIs_forced() == 0) {//  0 ?????????
                            binding.llScanReasult.setVisibility(View.GONE);
                        } else {//1 ????????????

                            binding.llScanReasult.setVisibility(View.VISIBLE);
                        }
                    } else {
                        switch (fragmentTag) {
                            case FRAGMENT_PLAN_OWRKORDER_PENDING://?????????
                                binding.llForceScan.setVisibility(View.VISIBLE);
                                if (model.getIs_forced() == 0) {//  0 ?????????
                                    binding.ivScan.setVisibility(View.GONE);
                                    binding.llScanReasult.setVisibility(View.GONE);
                                } else {//1 ????????????
                                    if (isCloseClose) {

                                        binding.ivScan.setVisibility(View.VISIBLE);
                                    }
                                    binding.llScanReasult.setVisibility(View.VISIBLE);
                                }
                                break;
                            case FRAGMENT_PLAN_OWRKORDER_DONE:
                                binding.llForceScan.setVisibility(View.VISIBLE);
                                if (model.getIs_forced() == 0) {//  0 ?????????
                                    binding.llScanReasult.setVisibility(View.GONE);
                                } else {//1 ????????????

                                    binding.llScanReasult.setVisibility(View.VISIBLE);
                                }
                                break;
                        }
                    }
                    PlanInfo.ExtensionApplication extForceClose = planInfo.getExt(ApplyType.FORCECLOSE.getState());
                    if (extForceClose == null) {
                        String f_status = planInfo.getData().getZyjhgd().getF_STATUS();
                        Log.e(TAG, "onBindItem: f_status==" + f_status);
                        if ("4".equals(f_status)) {

                            binding.tvScanReasult.setText("??????");
                            binding.tvScanReasult.setTextColor(getResources().getColor(R.color.blackTextColor));


                        } else {
                            if ("1".equals(model.getScan_result())) {
                                binding.tvScanReasult.setTextColor(getResources().getColor(R.color.blackTextColor));
                                binding.tvScanReasult.setText("??????");
                            } else if ("0".equals(model.getScan_result())) {
                                binding.tvScanReasult.setText("??????");
                                binding.tvScanReasult.setTextColor(getResources().getColor(R.color.redTextColor));
                            } else {
                                binding.tvScanReasult.setText("?????????");
                                binding.tvScanReasult.setTextColor(getResources().getColor(R.color.blackTextColor));
                            }
                        }
                    } else {
                        if ("1".equals(model.getScan_result())) {
                            binding.tvScanReasult.setTextColor(getResources().getColor(R.color.blackTextColor));
                            binding.tvScanReasult.setText("??????");
                        } else if ("0".equals(model.getScan_result())) {
                            binding.tvScanReasult.setText("??????");
                            binding.tvScanReasult.setTextColor(getResources().getColor(R.color.redTextColor));
                        } else {
                            binding.tvScanReasult.setText("?????????");
                            binding.tvScanReasult.setTextColor(getResources().getColor(R.color.blackTextColor));

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
                            Log.e(TAG, "onBindItem: ????????????" + line.getName());
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

    /**
     * ??????????????????
     */
    protected void saveLocalData() {
        if (planInfo == null) {

            return;

        }
//        if (planInfo.getExtensionApplication() != null) {
//
//            return;
//        }
//        List<Uri> uris = photoSelectAdapter.getSelectedPhotos();
//        List<String> images = new ArrayList<>();
//        for (Uri uri : uris) {
//            images.add(uri.toString());
//        }
        if (planLocal == null) {
            planLocal = new PlanLocal();
            planLocal.setOrderId(id);
        }
//        planLocal.setImages(images);
        planLocal.setNote(binding.limitInput.getString());
//        List<WorkNode> workNodes = viewModel.loadNodes(planInfo);
//        workNodes.add(0, new WorkNode());
        List<WorkNode> dataList = nodesAdapter.getDataList();
//        dataList.addAll(dataList);
        planLocal.setNodes(dataList);
        /*if (workNodes.size() == dataList.size()) {

            planLocal.setNodes(nodesAdapter.getDataList());
        } else {
            try {
                List<WorkNode> workNodes1 = workNodes.subList(dataList.size(), workNodes.size());
                dataList.addAll(workNodes1);
                planLocal.setNodes(dataList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        planLocal.setResources(resourceAdapter.getDataList());

        planLocal.setDesignatePerson(binding.sendOrder.repairSelectedPepple.getText().toString().trim());
        planLocal.setRemark(binding.sendOrder.repairSendReason.getString());
        viewModel.saveLocal(planLocal);
    }

    protected void updateLocalData(PlanLocal local) {
        if (local != null) {
//            if (local.getImages() != null && local.getImages().size() > 0) {
//                List<Uri> uris = new ArrayList<>();
//                for (String imgeUrl : local.getImages()) {
//                    Uri uri = Uri.parse(imgeUrl);
//                    uris.add(uri);
//                }
//                photoSelectAdapter.setSelectedPhotos(uris);
//            }
            if (local.getDesignatePerson() != null) {//????????????
                binding.sendOrder.repairSelectedPepple.setText(local.getDesignatePerson());
            }
            if (local.getRemark() != null) {//????????????
                binding.sendOrder.repairSendReason.setText(local.getRemark());
            }
            if (!TextUtils.isEmpty(local.getNote())) {
                binding.limitInput.setText(local.getNote());
            }
            if (local.getNodes() != null) {
                nodesAdapter.setDataList(local.getNodes());
                binding.rvNodes.setAdapter(nodesAdapter);
            }
            if (local.getResources() != null) {
                resourceAdapter.setDataList(local.getResources());
            }
        }
    }

    private void requestData() {

        //????????????
        viewModel.loadDetail(proInsId, taskId, taskNodeId, fragmentTag, id).observe(this, planInfo -> {
            if (planInfo == null || planInfo.getData() == null) {
                return;
            }
            projectId = planInfo.getData().zyjhgd.getF_PROJECT_ID();
            divideId = planInfo.getData().zyjhgd.getF_DIVIDE_ID();
            f_status = planInfo.getData().getZyjhgd().getF_STATUS();
            Log.e(TAG, "requestData: " + f_status);
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
            viewModel.loadLocalUserData(id).observe(this, local -> {
                planLocal = local;
                if (String.valueOf(OrderState.CLOSED.getState()).equals(f_status)) {

                } else {

                    updateLocalData(local);
                }
            });
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

    //??????????????????
    Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable, 1000);
            //????????????
            binding.tvHandleTime.setText(TimeUtil.getTimeExpend(createTime));
        }
    };

    @Override
    protected void initListener() {
        super.initListener();
//        photoSelectAdapter.setAddListener(selectedSize -> {
//            if (photoSelectAdapter.getSelectedPhotos().size() >= MAX_PHOTO_SIZE) {
//                ToastUtil.show(getApplicationContext(), R.string.upload_pic_max);
//                return;
//            }
////            imageFile = CaptureUtils.startCapture(this);//????????????
//            Matisse.from(this) //??????????????????  ???????????????????????????
//                    .choose(MimeType.ofImage())
//                    .captureStrategy(new CaptureStrategy(true, DataConstants.DATA_PROVIDER_NAME))
//                    .capture(true)
//                    .countable(true)
//                    .maxSelectable(MAX_PHOTO_SIZE - photoSelectAdapter.getSelectedPhotos().size())
//                    //                .maxSelectable(4 - (photoSelectAdapter.getItemCount() - 1))
//                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                    .thumbnailScale(0.85f)
//                    .imageEngine(new Glide4Engine())
//                    .forResult(RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK);
//        }, this);
    }

    List<WorkNode> nodes = new ArrayList<>();

    private void updateUI(com.einyun.app.base.db.entity.PlanInfo planInfo) {
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
//            nodes.add(0, new WorkNode());
            nodesAdapter.setDataList(nodes);
        }

        binding.setDetail(planInfo.getData().getZyjhgd());
    }

    protected void updatePageUIState(int state) {
        binding.pageState.setPageState(state);
    }

    /**
     * ??????????????????
     */
    private void showResult() {
        PlanInfo.Data.Zyjhgd zyjhgd = planInfo.getData().getZyjhgd();
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
        } else if (String.valueOf(OrderState.PENDING.getState()).equals(zyjhgd.getF_STATUS())) {//?????? f_status ?????????
            binding.btnSubmit.setText("??????");
            binding.cvResultEdit.setVisibility(View.GONE);
            binding.cvOperate.setVisibility(View.GONE);

        } else if (String.valueOf(OrderState.OVER_DUE.getState()).equals(zyjhgd.getF_STATUS())) {//?????? f_status ?????? ??????????????????
            binding.btnSubmit.setText("??????");
            binding.cvResultEdit.setVisibility(View.GONE);
            binding.cvOperate.setVisibility(View.GONE);
            if (!FRAGMENT_PLAN_OWRKORDER_DONE.equals(fragmentTag)) {
                binding.sendOrder.getRoot().setVisibility(View.VISIBLE);
            }
        } else {
            binding.btnSubmit.setText("??????");
        }
    }

    /**
     * ????????????????????????
     */
    private void showPostpone() {
        PlanInfo.ExtensionApplication extPostpone = planInfo.getExt(ApplyType.POSTPONE.getState());
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
     * ????????????????????????
     */
    private void showForceClose() {
        PlanInfo.ExtensionApplication extForceClose = planInfo.getExt(ApplyType.FORCECLOSE.getState());
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
     * ??????????????????
     */
    public void applyPostpone() {
        if (!NetWorkUtils.isNetworkConnected(CommonApplication.getInstance())) {
            ToastUtil.show(CommonApplication.getInstance(), "?????????????????????????????????");
            return;
        }
        IsClosedRequest request = new IsClosedRequest();
        request.setId(id);
        request.setType(WorkOrder.POSTPONED_PLAN);
        viewModel.isClosed(request, true);
    }

    /**
     *
     */
    public void closeOrder() {
        if (!NetWorkUtils.isNetworkConnected(CommonApplication.getInstance())) {

            ToastUtil.show(CommonApplication.getInstance(), "?????????????????????????????????");
            return;
        }
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
     * ??????
     */
    public void resendOrder() {
        if (!NetWorkUtils.isNetworkConnected(CommonApplication.getInstance())) {

            ToastUtil.show(CommonApplication.getInstance(), "?????????????????????????????????");
            return;
        }
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_RESEND_ORDER)
                .withString(RouteKey.KEY_TASK_ID, taskId)
                .withString(RouteKey.KEY_ORDER_ID, id)
                .withString(RouteKey.KEY_DIVIDE_ID, divideId)
                .withString(RouteKey.KEY_PROJECT_ID, projectId)
                .withString(RouteKey.KEY_CUSTOM_TYPE, CustomEventTypeEnum.COMPLAIN_TURN_ORDER.getTypeName())
                .withString(RouteKey.KEY_CUSTOMER_RESEND_ORDER, RouteKey.KEY_CUSTOMER_RESEND_ORDER)
                .navigation();
    }

    /**
     * ??????????????????
     *
     * @return
     */
    private boolean validateFormData() {
        List<WorkNode> data = getWorkNodes();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).result == null || TextUtils.isEmpty(data.get(i).result)) {
                ToastUtil.show(this, String.format(getResources().getString(R.string.text_alert_handle_node), data.get(i).number));
                return false;
            }
            if (data.get(i).getF_CHECK_RESULT() == null || TextUtils.isEmpty(data.get(i).getF_CHECK_RESULT())) {
                ToastUtil.show(this, String.format(getResources().getString(R.string.text_alert_check_node), data.get(i).number));
                return false;
            }
            if (data.get(i).getSelectImgs().size() <= 0) {
                ToastUtil.show(this, String.format(getResources().getString(R.string.text_alert_check_img), data.get(i).number));
                return false;
            }
        }
        if (TextUtils.isEmpty(binding.limitInput.getString())) {
            ToastUtil.show(this, R.string.text_alert_handle_content);
            binding.limitInput.requestFocus();
            return false;
        }

       /* if (photoSelectAdapter.getSelectedPhotos().size() <= 0) {
            ToastUtil.show(this, R.string.text_alert_photo_empty);
            return false;
        }*/
        return true;
    }

    /**
     * ????????????????????????
     *
     * @return
     */
    @NotNull
    protected List<WorkNode> getWorkNodes() {
        List<WorkNode> all = nodesAdapter.getDataList();
//        return all.subList(1, all.size());
        return all;
    }


    /**
     * ???????????????????????????
     *
     * @param planInfo
     */
    private void uploadImagesByList(PlanInfo planInfo) {
        List<NodeImgs> nodeImgsList=new ArrayList<>();
        int imgIndex = -1;
        if (planInfo == null) {
            return;
        }
        for (WorkNode workNode : nodesAdapter.getDataList()) {
            NodeImgs nodeImgs=new NodeImgs();
            for (String path : workNode.getSelectImgs()) {
                nodeImgs.getImgUris().add(Uri.parse(path));
            }
            nodeImgsList.add(nodeImgs);
        }
        if (!NetWorkUtils.isNetworkConnected(PlanOrderDetailActivity.this)) {
            ToastUtil.show(CommonApplication.getInstance(), "?????????????????????????????????");
            return;
        }
        uploadImagesList(planInfo,nodeImgsList,imgIndex);

    }

    private void uploadImagesList(PlanInfo planInfo,List<NodeImgs> nodeImgsList, int imgIndex) {
        imgIndex++;
        int i = imgIndex;
        viewModel.uploadImages(nodeImgsList.get(i).getImgUris()).observe(this, picUrls -> {
            if (picUrls==null||picUrls.size()==0){
                ToastUtil.show(this,"??????????????????");
                return;
            }
        wrapFormData(planInfo, picUrls,i);
            if (i < nodesAdapter.getDataList().size() - 1) {
                uploadImagesList(planInfo,nodeImgsList,i);
            }else {
                acceptForm(planInfo);
            }
        });
    }


    /**
     * ??????????????????
     *
     * @param patrol
     * @param picUrls
     * @return
     */
    private void wrapFormData(PlanInfo patrol, List<PicUrl> picUrls, int index) {
        GetUploadJson getUploadJsonStr = new GetUploadJson(picUrls).invoke();
        Gson gson = getUploadJsonStr.getGson();
        List<PicUrlModel> picUrlModels = getUploadJsonStr.getPicUrlModels();
        patrol.getData().getZyjhgd().setF_STATUS(String.valueOf(OrderState.CLOSED.getState()));
//        patrol.getData().getZyjhgd().setF_FILES(gson.toJson(picUrlModels));//????????????????????????
        patrol.getData().getZyjhgd().setF_CONTENT(binding.limitInput.getString());//????????????????????????
        nodesAdapter.getDataList().get(index).setF_NODE_PICTURE(gson.toJson(picUrlModels));
    }

    private boolean hasException() {
        int index = 0; //??????????????????
        for (PlanInfo.Data.Zyjhgd.Sub_jhgdgzjdb node : planInfo.getData().getZyjhgd().getSub_jhgdgzjdb()) {
            for (WorkNode workNode : getWorkNodes()) {
                if (node.getF_WK_ID().equals(workNode.number)) {
                    node.setF_WK_RESULT(workNode.getResult());
                    node.setF_CHECK_RESULT(workNode.getF_CHECK_RESULT());
                    node.setF_NODE_PICTURE(workNode.getF_NODE_PICTURE());
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
        Log.e("??????  patrol  ???", JsonUtil.toJson(patrol));
        String base64 = Base64Util.encodeBase64(new Gson().toJson(patrol.getData()));
        PatrolSubmitRequest request = new PatrolSubmitRequest(taskId, PatrolSubmitRequest.ACTION_AGREE, base64, patrol.getData().getZyjhgd().getId_());
        request.setRemark(binding.limitInput.getString());
        viewModel.submit(request).observe(this, aBoolean -> {
            if (aBoolean) {
                viewModel.finishTask(id);
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
//                    ToastUtil.show(PlanOrderDetailActivity.this,"??????????????????");
                } else {
                    ToastUtil.show(PlanOrderDetailActivity.this, "??????????????????");
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
     * ?????????????????????
     */
    private void createSendOrder() {
        if (alertDialog == null) {
            alertDialog = new CreateNewOrderDialog(this).builder()
                    .setCreateSendOrder(v -> {
                        goPaiGongDan(); //????????????????????????
                    }).setCreateUnOrder(v -> {
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PROPERTY_CREATE)
                                .withString(RouteKey.CODE, planInfo.getData().getZyjhgd().getF_ORDER_NO())
                                .withString(RouteKey.F_ORIGINAL_TYPE, "1")
                                .withString(RouteKey.KEY_ORDER_ID, id)
                                .withString(RouteKey.KEY_ORDER_NO, planInfo.getData().getZyjhgd().getF_ORDER_NO())
                                .withString(RouteKey.KEY_LINE, planInfo.getData().getZyjhgd().getF_TX_NAME())
                                .withString(RouteKey.KEY_RESOUSE, planInfo.getData().getZyjhgd().getF_RES_NAME())
//                .withString(RouteKey.KEY_ORDER_ID, data.getID_())

                                .withString(RouteKey.KEY_LINE_ID, planInfo.getData().getZyjhgd().getF_TIT_ID())
                                .withString(RouteKey.KEY_LINE_CODE, planInfo.getData().getZyjhgd().getF_TX_CODE())
                                .withString(RouteKey.KEY_PROJECT, planInfo.getData().getZyjhgd().getF_PROJECT_NAME())
                                .withString(RouteKey.KEY_DIVIDE_NAME, planInfo.getData().getZyjhgd().getF_DIVIDE_NAME())
                                .withString(RouteKey.KEY_DIVIDE_ID, planInfo.getData().getZyjhgd().getF_DIVIDE_ID())
                                .withString(RouteKey.KEY_RESOUSE_ID, planInfo.getData().getZyjhgd().getF_RES_ID())

                                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                                .withString(RouteKey.KEY_TASK_ID, taskId)
                                .withString(RouteKey.KEY_TASK_NODE_ID, taskNodeId)
                                .withString(RouteKey.KEY_FRAGEMNT_TAG, FRAGMENT_PLAN_OWRKORDER_DONE)
                                .navigation();
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

                .withString(RouteKey.KEY_LINE_ID, planInfo.getData().getZyjhgd().getF_TIT_ID())
                .withString(RouteKey.KEY_LINE_CODE, planInfo.getData().getZyjhgd().getF_TX_CODE())
                .withString(RouteKey.KEY_PROJECT, planInfo.getData().getZyjhgd().getF_PROJECT_NAME())
                .withString(RouteKey.KEY_DIVIDE_NAME, planInfo.getData().getZyjhgd().getF_DIVIDE_NAME())
                .withString(RouteKey.KEY_DIVIDE_ID, planInfo.getData().getZyjhgd().getF_DIVIDE_ID())
                .withString(RouteKey.KEY_RESOUSE_ID, planInfo.getData().getZyjhgd().getF_RES_ID())

                .withString(RouteKey.KEY_RESOUSE, planInfo.getData().getZyjhgd().getF_RES_NAME())
//                .withString(RouteKey.KEY_ORDER_ID, data.getID_())
                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                .withString(RouteKey.F_ORIGINAL_TYPE, "1")
                .withString(RouteKey.KEY_TASK_ID, taskId)
                .withString(RouteKey.KEY_TASK_NODE_ID, taskNodeId)
                .withString(RouteKey.KEY_FRAGEMNT_TAG, FRAGMENT_PLAN_OWRKORDER_DONE)
                .navigation();
        finish();
    }


    /**
     * ??????
     */
    public void onSubmitClick() {//?????? f_status ????????????????????????
        if (String.valueOf(OrderState.PENDING.getState()).equals(planInfo.getData().getZyjhgd().getF_STATUS())) {
//            planInfo.getData().getZyjhgd().setF_ACT_FINISH_TIME(getTime());
            planInfo.getData().getZyjhgd().setF_STATUS("2");
            planInfo.getData().getZyjhgd().setF_OWNER_NAME(viewModel.getUserName());
            planInfo.getData().getZyjhgd().setF_OWNER_ID(viewModel.getUserID());
            planInfo.getData().getZyjhgd().setF_PROCESS_NAME(viewModel.getUserName());
            planInfo.getData().getZyjhgd().setF_PROCESS_ID(viewModel.getUserID());
            Log.e("??????  patrol  ???", JsonUtil.toJson(planInfo));
            Log.e(TAG, "onSubmitClick: " + planInfo.getData().getZyjhgd().getSub_jhgdzyb().get(0).getScan_result());
            String base64 = Base64Util.encodeBase64(new Gson().toJson(planInfo.getData()));
            PatrolSubmitRequest request = new PatrolSubmitRequest(taskId, PatrolSubmitRequest.ACTION_AGREE, base64, planInfo.getData().getZyjhgd().getId_());
//            request.setRemark(binding.limitInput.getString());
            viewModel.receiceOrder(request).observe(this, model -> {

                if (model.isState()) {
                    viewModel.saveCache(planInfo, id);
                    initDialog("????????????");
                } else {
                    planInfo.getData().getZyjhgd().setF_STATUS("5");
                    ToastUtil.show(PlanOrderDetailActivity.this, model.getMsg());
                }
            });

        } else if (String.valueOf(OrderState.OVER_DUE.getState()).equals(planInfo.getData().getZyjhgd().getF_STATUS())) {
            String trim = binding.sendOrder.repairSelectedPepple.getText().toString().trim();
            if (!trim.isEmpty()) {
                planInfo.getData().getZyjhgd().setF_STATUS("2");
                planInfo.getData().getZyjhgd().setF_SEND_REMARK(binding.sendOrder.repairSendReason.getString());
                Log.e("??????  patrol  ???", JsonUtil.toJson(planInfo));
                String base64 = Base64Util.encodeBase64(new Gson().toJson(planInfo.getData()));
                String f_send_remark = binding.sendOrder.repairSendReason.getString();
                PatrolSubmitRequest request = new PatrolSubmitRequest(taskId, f_send_remark, PatrolSubmitRequest.ACTION_AGREE, base64, planInfo.getData().getZyjhgd().getId_());
                request.setRemark(binding.limitInput.getString());
                viewModel.assignOrder(request).observe(this, model -> {

                    if (model.isState()) {
                        viewModel.saveCache(planInfo, id);
                        initDialog("????????????");
                    } else {
                        planInfo.getData().getZyjhgd().setF_STATUS("6");
                        ToastUtil.show(PlanOrderDetailActivity.this, model.getMsg());
                    }
                });
            } else {
                ToastUtil.show(PlanOrderDetailActivity.this, "??????????????????");
            }
        } else {
            isSubmit = true;
            if (!validateFormData()) {
                return;
            }
            if (validateForceScan()) return;

//            uploadImages(planInfo);
            uploadImagesByList(planInfo);
        }
    }

    private boolean validateForceScan() {
        List<PlanInfo.Data.Zyjhgd.Sub_jhgdzyb> sub_jhgdzyb = resourceAdapter.getDataList();
        if (sub_jhgdzyb != null && sub_jhgdzyb.size() > 0) {

            for (PlanInfo.Data.Zyjhgd.Sub_jhgdzyb dzyb : sub_jhgdzyb) {
                if (dzyb.getIs_forced() == 1) {//??????????????? ??? ????????? ????????????
                    if (dzyb.getIs_suc() != 1) {//0 ??????
                        isSubmit = false;

                    }
                }
            }
        }
        if (!isSubmit) {
            new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
                    .setMsg("??????????????????")
                    .setPositiveButton("????????????", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();

            return true;
        }
        return false;
    }

    AlertDialog dialog;

    private void initDialog(String content) {
        if (dialog == null) {
            dialog = new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
                    .setMsg(content)
                    .setPositiveButton("????????????", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });
            dialog.show();
        } else {
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //?????????????????????
        if (requestCode == RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK) {
            if (data == null) return;
            List<String> paths = new ArrayList<>();
            List<Uri> uris = Matisse.obtainResult(data);
            for (Uri uri : uris) {
                paths.add(uri + "");
            }
            nodesAdapter.getDataList().get(addImgPosition).getSelectImgs().addAll(paths);
//            nodesAdapter.notifyDataSetChanged();
            binding.rvNodes.setAdapter(nodesAdapter);
//            if (uris != null && uris.size() > 0) {
//                photoSelectAdapter.addPhotos(uris);
////                cachePhoto(photoSelectAdapter.getSelectedPhotos());
//            }
        }
        //?????????????????????
//        if (requestCode == RouterUtils.ACTIVITY_REQUEST_CAMERA_OK && resultCode == RESULT_OK) {
//            Uri uri;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                uri = FileProvider.getUriForFile(this, DataConstants.DATA_PROVIDER_NAME, imageFile);
//            } else {
//                uri = Uri.fromFile(imageFile);
//            }
//            Observable.just(imageFile).subscribeOn(Schedulers.io())
//                    .subscribe(file -> {
//                        try {
//                            BitmapUtil.AddTimeWatermark(file);
//                        }catch (Exception e){
//                            ToastUtil.show(PlanOrderDetailActivity.this,"?????????????????????????????????");
//                        }
//                        runOnUiThread(() -> {
//                            if (uri != null) {
//                                photoSelectAdapter.addPhotos(Arrays.asList(uri));
//                            }
//                        });
//                    });
//        }
        if (resultCode == RESULT_OK) {
            if (requestCode == RouterUtils.ACTIVITY_REQUEST_SCANNER) {
                String stringExtra = data.getStringExtra(DataConstants.KEY_SCANNER_CONTENT);//??????code??????????????????
                List<PlanInfo.Data.Zyjhgd.Sub_jhgdzyb> sub_jhgdzyb = planInfo.getData().getZyjhgd().getSub_jhgdzyb();
                if (sub_jhgdzyb != null) {
                    if (stringExtra.equals(sub_jhgdzyb.get(mClickPosition).getF_RES_QRCODE())) {
                        planInfo.getData().getZyjhgd().getSub_jhgdzyb().get(mClickPosition).setIs_suc(1);
                        planInfo.getData().getZyjhgd().getSub_jhgdzyb().get(mClickPosition).setScan_result("1");
                        resourceAdapter.setDataList(planInfo.getData().getZyjhgd().getSub_jhgdzyb());
                        ToastUtil.show(CommonApplication.getInstance(), "????????????");
                    } else {
                        planInfo.getData().getZyjhgd().getSub_jhgdzyb().get(mClickPosition).setIs_suc(0);
                        planInfo.getData().getZyjhgd().getSub_jhgdzyb().get(mClickPosition).setScan_result("0");
                        resourceAdapter.setDataList(planInfo.getData().getZyjhgd().getSub_jhgdzyb());
                        ToastUtil.show(CommonApplication.getInstance(), "??????????????????");
                    }
                }
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
        saveLocalData();
    }
}
