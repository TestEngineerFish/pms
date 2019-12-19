package com.einyun.app.pms.patrol.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
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
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.model.ResultState;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.ui.widget.TipDialog;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.convert.ExtensionApplicationConvert;
import com.einyun.app.pms.patrol.databinding.ActivityPatrolDetialBinding;
import com.einyun.app.pms.patrol.databinding.ItemPatrolWorkNodeBinding;
import com.einyun.app.pms.patrol.viewmodel.PatrolViewModel;
import com.einyun.app.pms.patrol.viewmodel.ViewModelFactory;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.jetbrains.annotations.NotNull;

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
    protected int listType= ListType.PENDING.getType();
    protected PhotoSelectAdapter photoSelectAdapter;
    protected final int MAX_PHOTO_SIZE = 4;

    protected RVBindingAdapter nodesAdapter;
    protected PatrolLocal patrolLocal;
    protected PatrolInfo patrolInfo;
    protected AlertDialog alertDialog;
    protected TipDialog tipDialog;

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
    }

    @Override
    protected void initData() {
        super.initData();
        setUpPhotoList();
        //工作节点适配
        setUpWorkNodes();
        initRequest();
        switchStateUI();
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
        binding.pointCkImglist.setAdapter(photoSelectAdapter);
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
                        if (ResultState.RESULT_SUCCESS.equals(model.getResult())) {
                            onAgree(binding);
                        } else {
                            onReject(binding);
                        }
                    }
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
        if(listType== ListType.PENDING.getType()){
            viewModel.loadPendingDetial(orderId).observe(this, patrolInfo -> {
                updateUI(patrolInfo);
                viewModel.loadLocalUserData(orderId).observe(this, local -> {
                    patrolLocal=local;
                    updateLocalData(local);
                });
            });
        }else if(listType==ListType.DONE.getType()){
            //加载数据
            viewModel.loadDoneDetial(orderId).observe(this, patrolInfo -> {
                updateUI(patrolInfo);
            });
        }
    }

    protected void setListType(int listType){
        this.listType=listType;
    }

    protected void setOrderId(String orderId){
        this.orderId=orderId;
    }

    protected void updateUI(PatrolInfo patrol) {
        if (patrol == null) {
            return;
        }
        this.patrolInfo = patrol;
        binding.setPatrol(patrol);
        updateWorkNodesUI(patrol);//更新节点信息
        updateHandleResultUI(patrol);//更新处理结果信息

        ExtensionApplicationConvert convert = new ExtensionApplicationConvert();
        updateForceCloseUI(patrol,convert);//更新强制关闭信息
        uploadPostponeUI(patrol, convert);//更新申请超时信息
    }

    protected void updateLocalData(PatrolLocal local){
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


    /**
     * 更新申请超时信息
     * @param patrol
     * @param convert
     */
    protected void uploadPostponeUI(PatrolInfo patrol, ExtensionApplicationConvert convert) {
        if (patrol.getDelayExtensionApplication() != null) {
            binding.panelPostponeInfo.getRoot().setVisibility(View.VISIBLE);
            binding.panelPostponeInfo.setExt(convert.stringToSomeObject(convert.getGson().toJson(patrol.getDelayExtensionApplication())));
        }
    }

    /**
     * 更新强制关闭信息
     * @param patrol
     * @param convert
     * @return
     */
    @NotNull
    protected ExtensionApplicationConvert updateForceCloseUI(PatrolInfo patrol,ExtensionApplicationConvert convert) {
        if (patrol.getExtensionApplication() != null) {
            binding.panelCloseInfo.getRoot().setVisibility(View.VISIBLE);
            binding.panelCloseInfo.setExt(convert.stringToSomeObject(convert.getGson().toJson(patrol.getExtensionApplication())));
        }
        return convert;
    }

    /**
     * 更新处理结果信息
     * @param patrol
     */
    protected void updateHandleResultUI(PatrolInfo patrol) {
        binding.panelHandleInfo.setPatrol(patrol.getData().getZyxcgd());
    }

    /**
     * 更新处理巡查节点信息
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
            Matisse.from(this) //加号添加图片
                    .choose(MimeType.ofImage())
                    .captureStrategy(new CaptureStrategy(true, DataConstants.DATA_PROVIDER_NAME))
                    .capture(true)
                    .countable(true)
                    .maxSelectable(MAX_PHOTO_SIZE-photoSelectAdapter.getSelectedPhotos().size())
                    //                .maxSelectable(4 - (photoSelectAdapter.getItemCount() - 1))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new Glide4Engine())
                    .forResult(RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK);
        }, PatrolDetialActivity.this);
    }

    /**
     * 保存本地数据
     */
    protected void saveLocalUserData(){
        List<Uri> uris = photoSelectAdapter.getSelectedPhotos();
        List<String> images = new ArrayList<>();
        for (Uri uri : uris) {
            images.add(uri.toString());
        }
        if(patrolLocal==null){
            patrolLocal=new PatrolLocal();
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
        if(tipDialog!=null){
            tipDialog.dismiss();
        }
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
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
