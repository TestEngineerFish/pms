package com.einyun.app.pms.sendorder.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.databinding.ActivityApplyForceCloseBinding;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.library.resource.workorder.net.request.ApplyCloseRequest;
import com.einyun.app.library.resource.workorder.net.request.ApplyCusCloseRequest;
import com.einyun.app.pms.sendorder.R;
//import com.einyun.app.pms.sendorder.databinding.ActivityApplyForceCloseBinding;
import com.einyun.app.pms.sendorder.viewmodel.ApplyCloseViewModel;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

@Route(path = RouterUtils.ACTIVITY_CLOSE)
public class ApplyForceCloseActivity extends BaseHeadViewModelActivity<ActivityApplyForceCloseBinding, ApplyCloseViewModel> {
    private PhotoSelectAdapter photoSelectAdapter;
    private static final int MAX_PHOTO_SIZE = 4;
    private ApplyCloseRequest request;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    public String id;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_CLOSE_ID)
    String keyId;
    @Autowired(name = RouteKey.KEY_MID_URL)
    String midUrl;
    @Override
    protected ApplyCloseViewModel initViewModel() {
        return new ViewModelProvider(this, new SendOdViewModelFactory()).get(ApplyCloseViewModel.class);

    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_force_close;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_apply_close);
        request = new ApplyCloseRequest();
        if (StringUtil.isNullStr(keyId) && RouteKey.KEY_PLAN.equals(keyId)){
            request.setID(id);
            request.setTaskId(taskId);
            request.setInstId(proInsId);
            request.setMessageType("1");
        }else{
            request.setId(id);
            request.setApplyTaskId(taskId);
            request.setInstId(proInsId);
        }

        selectPng();
    }

    /**
     * 初始化选择图片
     */
    private void selectPng() {
        photoSelectAdapter = new PhotoSelectAdapter(this);//图片选择适配器
        binding.sendOrderCloseList.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//设置横向
        binding.sendOrderCloseList.setAdapter(photoSelectAdapter);
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
        }, this);
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
    protected void initListener() {
        super.initListener();
        binding.applyCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.setApplicationDescription(binding.applyCloseReason.getString());
                request.setEndReason(binding.applyCloseReason.getString());
                if (TextUtils.isEmpty(binding.applyCloseReason.getString())) {
                    ToastUtil.show(ApplyForceCloseActivity.this, R.string.txt_plese_enter_reason);
                } else if (photoSelectAdapter.getSelectedPhotos().size() == 0) {
                    ToastUtil.show(ApplyForceCloseActivity.this, R.string.txt_plese_select_img);
                } else {
                    uploadImages();
                }
            }
        });
    }

    /**
     * 上传照片
     */
    private void uploadImages() {
        //开始上传照片
        viewModel.uploadImages(photoSelectAdapter.getSelectedPhotos()).observe(this, data -> {
            hideLoading();
            if (data != null) {
                if (!StringUtil.isNullStr(midUrl)) {
                    if (StringUtil.isNullStr(keyId)) {
                        if (RouteKey.KEY_PLAN.equals(keyId)) {
                            viewModel.applyClosePlan(request, data).observe(this, model -> {
                                if (model.getCode().equals("0")) {
                                    ToastUtil.show(this, R.string.apply_close_success);
                                    this.finish();
                                } else {
                                    ToastUtil.show(this, model.getMsg());
                                }
                            });
                        }
                    } else {
                        viewModel.applyClose(request, data).observe(this, model -> {
                            if (model.getCode().equals("0")) {
                                ToastUtil.show(this, R.string.apply_close_success);
                                this.finish();
                            } else {
                                ToastUtil.show(this, model.getMsg());
                            }
                        });
                    }
                }else {
                    ApplyCusCloseRequest applyCusCloseRequest = new ApplyCusCloseRequest(new ApplyCusCloseRequest.BizDataBean(),new ApplyCusCloseRequest.DoNextParamBean());
                    applyCusCloseRequest.getDoNextParam().setTaskId(taskId);
                    applyCusCloseRequest.getBizData().setFclose_apply_reason(binding.applyCloseReason.getString());
                    viewModel.applyCustomerClose(applyCusCloseRequest,midUrl, data).observe(this, model -> {
                        if (model.getCode().equals("0")) {
                            ToastUtil.show(this, R.string.apply_close_success);
                            this.finish();
                        } else {
                            ToastUtil.show(this, model.getMsg());
                        }
                    });
                }
            }
        });
    }
}
