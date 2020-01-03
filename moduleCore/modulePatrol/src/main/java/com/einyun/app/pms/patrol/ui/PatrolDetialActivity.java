package com.einyun.app.pms.patrol.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.db.entity.PatrolLocal;
import com.einyun.app.base.util.BitmapUtil;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.constants.WorkOrder;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.model.PageUIState;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.ResultState;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.component.photo.PhotoShowActivity;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.common.ui.widget.TipDialog;
import com.einyun.app.common.utils.CaptureUtils;
import com.einyun.app.library.resource.workorder.model.ApplyState;
import com.einyun.app.library.resource.workorder.model.OrderState;
import com.einyun.app.library.resource.workorder.net.request.IsClosedRequest;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.convert.ExtensionApplicationConvert;
import com.einyun.app.pms.patrol.databinding.ActivityPatrolDetialBinding;
import com.einyun.app.pms.patrol.databinding.ItemPatrolWorkNodeBinding;
import com.einyun.app.pms.patrol.viewmodel.PatrolViewModel;
import com.einyun.app.pms.patrol.viewmodel.ViewModelFactory;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * 巡查详情
 */
@Route(path = RouterUtils.ACTIVITY_PATROL_DETIAL)
public class PatrolDetialActivity extends BaseHeadViewModelActivity<ActivityPatrolDetialBinding, PatrolViewModel> {
    @Autowired(name = RouteKey.KEY_TASK_ID)
    protected String taskId;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    protected String orderId;
    @Autowired(name = RouteKey.KEY_TASK_NODE_ID)
    protected String taskNodeId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    protected String proInsId;

    @Autowired(name = RouteKey.KEY_LIST_TYPE)
    protected int listType = ListType.PENDING.getType();
    protected PhotoSelectAdapter photoSelectAdapter;
    protected PhotoListAdapter photoListAdapter;
    protected final int MAX_PHOTO_SIZE = 4;

    protected RVBindingAdapter nodesAdapter;
    protected PatrolLocal patrolLocal;
    protected PatrolInfo patrolInfo;
    protected AlertDialog alertDialog;
    protected TipDialog tipDialog;
    protected File imageFile;

    protected void setProInsId(String proInsId) {
        this.proInsId = proInsId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    protected PatrolViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(PatrolViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_patrol_detial;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.title_patrol);
        setRightTxt(R.string.text_histroy);
        setRightTxtColor(R.color.blueTextColor);
        setRightOption(R.drawable.histroy);
        switchStateUI();
    }

    @Override
    protected void initData() {
        super.initData();
        setUpPhotoList();
        //工作节点适配
        setUpWorkNodes();
        initRequest();
        loadData();
    }

    /**
     * 初始化request
     */
    protected void initRequest() {
        viewModel.request.setProInsId(proInsId);
        viewModel.request.setTaskNodeId(taskNodeId);
        viewModel.request.setTaskId(taskId);
    }

    /**
     * UI切换
     */
    protected void switchStateUI() {
        binding.panelHandleForm.setVisibility(View.GONE);
        binding.itemOrdered.setVisibility(View.GONE);
        binding.panelApplyForceCloseAndPostpone.setVisibility(View.GONE);
    }

    /**
     * 表单图片列表，添加上传图片
     */
    protected void setUpPhotoList() {
        photoSelectAdapter = new PhotoSelectAdapter(this);
        binding.pointCkImglist.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//设置横向
        binding.pointCkImglist.addItemDecoration(new SpacesItemDecoration());
        binding.pointCkImglist.setAdapter(photoSelectAdapter);

        photoListAdapter = new PhotoListAdapter(this);
    }

    /**
     * 工作节点
     */
    protected void setUpWorkNodes() {
        if (nodesAdapter == null) {
            nodesAdapter = new RVBindingAdapter<ItemPatrolWorkNodeBinding, WorkNode>(this, com.einyun.app.pms.patrol.BR.node) {
                @Override
                public void onBindItem(ItemPatrolWorkNodeBinding binding, WorkNode model, int position) {
                    //处理列表头
                    if (position == 0) {
                        tableHead(binding);
                    } else {
                        //处理节点
                        tableItem(binding, position);
                        if (TextUtils.isEmpty(model.getResult())) {
                            onNoneHandle(binding);
                        } else {
                            if (ResultState.RESULT_SUCCESS.equals(model.getResult())) {
                                onAgree(binding);
                            } else {
                                onReject(binding);
                            }
                        }

                    }
                }

                protected void onNoneHandle(ItemPatrolWorkNodeBinding binding) {
                    binding.btnReject.setVisibility(View.GONE);
                    binding.btnAgree.setVisibility(View.GONE);
                    binding.tvResult.setVisibility(View.VISIBLE);
                    binding.tvResult.setText(R.string.text_un_need_handle);
                    binding.tvResult.setTypeface(null, Typeface.NORMAL);
                    binding.tvResult.setTextSize(12);
                    binding.tvResult.setTextColor(CommonApplication.getInstance().getResources().getColor(R.color.normal_main_text_icon_color));
                }

                protected void onReject(ItemPatrolWorkNodeBinding binding) {
                    binding.btnReject.setBackgroundResource(R.drawable.corners_red_large);
                    binding.btnReject.setTextColor(binding.btnAgree.getResources().getColor(R.color.white));
                    binding.btnReject.setVisibility(View.VISIBLE);
                    binding.btnAgree.setVisibility(View.GONE);
                }

                protected void onAgree(ItemPatrolWorkNodeBinding binding) {
                    binding.btnAgree.setBackgroundResource(R.drawable.corners_green_large);
                    binding.btnAgree.setTextColor(binding.btnAgree.getResources().getColor(R.color.white));
                    binding.btnReject.setVisibility(View.GONE);
                    binding.btnAgree.setVisibility(View.VISIBLE);

                }

                //处理节点
                protected void tableItem(ItemPatrolWorkNodeBinding binding, int position) {
                    binding.tvNumber.setText(position + "");
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
    }

    protected void loadData() {
        if (listType == ListType.PENDING.getType()) {
            viewModel.loadPendingDetial(orderId).observe(this, patrolInfo -> {
                updateUI(patrolInfo);
                viewModel.loadLocalUserData(orderId).observe(this, local -> {
                    patrolLocal = local;
                    updateLocalData(local);
                });
            });
        } else if (listType == ListType.DONE.getType()) {
            //加载数据
            viewModel.loadDoneDetial(orderId).observe(this, patrolInfo -> {
                updateUI(patrolInfo);
            });
        }

        viewModel.isClosedLiveData.observe(this, isClosedState -> {
            if (isClosedState.isClosed()) {
                if (isClosedState.getType().equals(WorkOrder.FORCE_CLOSE_PATROL)) {
                    navigatApply(RouterUtils.ACTIVITY_PATROL_FORCE_CLOSE);//强制关闭
                } else if (isClosedState.getType().equals(WorkOrder.POSTPONED_PATROL)) {
                    navigatApply(RouterUtils.ACTIVITY_PATROL_POSTPONE);//申请延期
                }
            } else {
                ToastUtil.show(CommonApplication.getInstance(), R.string.text_applying_wait);
            }
        });
    }

    protected void setListType(int listType) {
        this.listType = listType;
    }

    protected void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    protected void updateUI(PatrolInfo patrol) {
        if (patrol == null) {
            updatePageUIState(PageUIState.LOAD_FAILED.getState());
            return;
        }
        this.patrolInfo = patrol;
        updateElapsedTime(patrol);
        binding.setPatrol(patrol);
        updatePageUIState(PageUIState.FILLDATA.getState());
        updateWorkNodesUI(patrol);//更新节点信息
        updateHandleResultUI(patrol);//更新处理结果信息

        ExtensionApplicationConvert convert = new ExtensionApplicationConvert();
        updateForceCloseUI(patrol, convert);//更新强制关闭信息
        uploadPostponeUI(patrol, convert);//更新申请超时信息
    }

    protected void updatePageUIState(int state) {
        binding.pageState.setPageState(state);
    }

    private void updateElapsedTime(PatrolInfo patrol) {
        createTime = patrol.getData().getZyxcgd().getF_creation_date();
        binding.tvHandleTime.setText(TimeUtil.getTimeExpend(createTime));
        if (patrol.getData().getZyxcgd().getF_plan_work_order_state() == OrderState.CLOSED.getState()) {
            if (StringUtil.isNullStr(patrol.getData().getZyxcgd().getF_actual_completion_time())) {
                binding.tvHandleTime.setText(TimeUtil.getTimeExpend(createTime, patrol.getData().getZyxcgd().getF_actual_completion_time()));
            }
        } else {
            runnable.run();
        }
    }

    protected void updateLocalData(PatrolLocal local) {
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
            if (local.getNodes() != null) {
                nodesAdapter.setDataList(local.getNodes());
            }
        }
    }


    /**
     * 更新申请超时信息
     *
     * @param patrol
     * @param convert
     */
    protected void uploadPostponeUI(PatrolInfo patrol, ExtensionApplicationConvert convert) {
        if (patrol.getDelayExtensionApplication() != null) {
            binding.panelPostponeInfo.getRoot().setVisibility(View.VISIBLE);
            binding.panelPostponeInfo.setExt(convert.stringToSomeObject(convert.getGson().toJson(patrol.getDelayExtensionApplication())));
            String images = patrol.getDelayExtensionApplication().getApplyFiles();
            PhotoListAdapter adapter = new PhotoListAdapter(this);
            binding.panelPostponeInfo.sendOrderPostponePicList.setLayoutManager(new LinearLayoutManager(
                    this,
                    LinearLayoutManager.HORIZONTAL,
                    false));//设置横向
            binding.panelPostponeInfo.sendOrderPostponePicList.setAdapter(adapter);
            binding.panelPostponeInfo.sendOrderPostponePicList.addItemDecoration(new SpacesItemDecoration());
            if (!TextUtils.isEmpty(images)) {
                PicUrlModelConvert convertPic = new PicUrlModelConvert();
                List<PicUrlModel> modelList = convertPic.stringToSomeObjectList(images);
                adapter.updateList(modelList);
            }
        }
    }

    /**
     * 更新强制关闭信息
     *
     * @param patrol
     * @param convert
     * @return
     */
    @NotNull
    protected ExtensionApplicationConvert updateForceCloseUI(PatrolInfo patrol, ExtensionApplicationConvert convert) {
        if (patrol.getExtensionApplication() != null) {
            binding.panelCloseInfo.getRoot().setVisibility(View.VISIBLE);
            binding.panelCloseInfo.setExt(convert.stringToSomeObject(convert.getGson().toJson(patrol.getExtensionApplication())));
            PhotoListAdapter adapter = new PhotoListAdapter(this);
            int state = patrol.getExtensionApplication().getApplicationState();
            onApplyForceClose(state);
            binding.panelCloseInfo.sendOrderClosePicList.setLayoutManager(new LinearLayoutManager(
                    this,
                    LinearLayoutManager.HORIZONTAL,
                    false));//设置横向
            binding.panelCloseInfo.sendOrderClosePicList.setAdapter(adapter);
            binding.panelCloseInfo.sendOrderClosePicList.addItemDecoration(new SpacesItemDecoration());
            String images = patrol.getExtensionApplication().getApplyFiles();
            if (!TextUtils.isEmpty(images)) {
                PicUrlModelConvert convertPic = new PicUrlModelConvert();
                List<PicUrlModel> modelList = convertPic.stringToSomeObjectList(images);
                adapter.updateList(modelList);
            }

        }
        return convert;
    }

    /**
     * 强制闭单审批中只展示基本信息
     *
     * @param state
     */
    protected void onApplyForceClose(int state) {
        if (state == ApplyState.APPLYING.getState()) {
            if (listType == ListType.PENDING.getType()) {
                binding.cdWorkNodes.setVisibility(View.GONE);
                binding.panelHandleForm.setVisibility(View.GONE);
                binding.panelHandleInfo.getRoot().setVisibility(View.GONE);
                binding.panelApplyForceCloseAndPostpone.setVisibility(View.GONE);
                binding.btnSubmit.setVisibility(View.GONE);
            } else {//已办显示全部信息
                binding.panelHandleInfo.getRoot().setVisibility(View.VISIBLE);
                binding.cdWorkNodes.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 更新处理结果信息
     *
     * @param patrol
     */
    protected void updateHandleResultUI(PatrolInfo patrol) {
        binding.panelHandleInfo.setPatrol(patrol.getData().getZyxcgd());
        String images = patrol.getData().getZyxcgd().getF_files();
        if (!TextUtils.isEmpty(images)) {
            PicUrlModelConvert convert = new PicUrlModelConvert();
            List<PicUrlModel> modelList = convert.stringToSomeObjectList(images);
            photoListAdapter.updateList(modelList);
        }
    }

    /**
     * 更新处理巡查节点信息
     *
     * @param patrol
     */
    protected void updateWorkNodesUI(PatrolInfo patrol) {
        List<WorkNode> nodes = viewModel.loadNodes(patrol);
        nodes.add(0, new WorkNode());
        nodesAdapter.addAll(nodes);
    }

    @Override
    protected void initListener() {
        super.initListener();
        photoSelectAdapter.setAddListener(selectedSize -> {
            if (photoSelectAdapter.getSelectedPhotos().size() >= MAX_PHOTO_SIZE) {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_max);
                return;
            }
            imageFile = CaptureUtils.startCapture(this);
        }, PatrolDetialActivity.this);
    }

    /**
     * 保存本地数据
     */
    protected void saveLocalUserData() {
        List<Uri> uris = photoSelectAdapter.getSelectedPhotos();
        List<String> images = new ArrayList<>();
        for (Uri uri : uris) {
            images.add(uri.toString());
        }
        if (patrolLocal == null) {
            patrolLocal = new PatrolLocal();
            patrolLocal.setOrderId(orderId);
        }
        patrolLocal.setImages(images);
        patrolLocal.setNote(binding.limitInput.getString());
        patrolLocal.setNodes(nodesAdapter.getDataList());
        viewModel.saveLocal(patrolLocal);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveLocalUserData();
        if (tipDialog != null) {
            tipDialog.dismiss();
        }
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    @Override
    public void onOptionClick(View view) {
        super.onOptionClick(view);
        if (!TextUtils.isEmpty(proInsId)) {
            ARouter.getInstance()
                    .build(RouterUtils.ACTIVITY_HISTORY)
                    .withString(RouteKey.KEY_ORDER_ID, orderId)
                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                    .navigation();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                                saveLocalUserData();
                            }
                        });
                    });
        } else if (requestCode == RouterUtils.ACTIVITY_REQUEST_OPTION) {
            if (data == null) {
                return;
            }
            boolean flag = data.getBooleanExtra(DataConstants.KEY_OPTION_RESULT, false);
            if (flag) {
                viewModel.loadPendingDetial(orderId);
                finish();
            }
        }
    }

    /**
     * 强制闭单
     */
    public void onForceClose() {
        viewModel.isClosed(new IsClosedRequest(orderId, WorkOrder.FORCE_CLOSE_PATROL));
    }

    /**
     * 申请延期
     */
    public void onPostpone() {
        viewModel.isClosed(new IsClosedRequest(orderId, WorkOrder.POSTPONED_PATROL));
    }

    private void navigatApply(String activityPatrolForceClose) {
        ARouter.getInstance()
                .build(activityPatrolForceClose)
                .withString(RouteKey.KEY_ORDER_ID, orderId)
                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                .withString(RouteKey.KEY_TASK_ID, taskId)
                .withString(RouteKey.KEY_CLOSE_ID, RouteKey.KEY_PLAN)
                .withInt(RouteKey.KEY_PARAMS, patrolInfo.getDelayExtensionApplication() == null ? 0 : patrolInfo.getDelayExtensionApplication().getExtensionDays())
                .withInt(RouteKey.KEY_PARENT_ID, patrolInfo.getDelayExtensionApplication() == null ? 0 : 1)
                .navigation(this, RouterUtils.ACTIVITY_REQUEST_OPTION);
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