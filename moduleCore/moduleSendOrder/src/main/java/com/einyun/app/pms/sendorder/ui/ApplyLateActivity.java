package com.einyun.app.pms.sendorder.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.util.ActivityUtil;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.library.resource.workorder.model.ApplyType;
import com.einyun.app.library.resource.workorder.model.DisttributeDetialModel;
import com.einyun.app.library.resource.workorder.model.ExtensionApplication;
import com.einyun.app.library.resource.workorder.net.request.ExtenDetialRequest;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.ActivityApplyLateBinding;
import com.einyun.app.pms.sendorder.databinding.ActivityResendOrderBinding;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderDetialViewModel;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_LATE)
public class ApplyLateActivity extends BaseHeadViewModelActivity<ActivityApplyLateBinding, SendOrderDetialViewModel> {

    @Autowired(name = RouteKey.KEY_ORDER_DETAIL_EXTEN)
    public ArrayList<ExtensionApplication> extensionApplication;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    public String orderId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;
    @Autowired(name = RouteKey.KEY_LATER_ID)
    String keyId;
    PhotoSelectAdapter photoSelectAdapter;
    private final int MAX_PHOTO_SIZE = 4;

    private String getExtDays() {
        if (extensionApplication == null || extensionApplication.size() == 0) {
            return "0";
        }
        for (ExtensionApplication ext : extensionApplication) {
            if (ApplyType.POSTPONE.getState() == ext.getApplyType()) {
                return StringUtil.isNullStr(ext.getExtensionDays()) ? ext.getExtensionDays() : "0";
            }
        }
        return "0";
    }

    @Override
    protected SendOrderDetialViewModel initViewModel() {
        return new ViewModelProvider(this, new SendOdViewModelFactory()).get(SendOrderDetialViewModel.class);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_late;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_apply_postpone);
        binding.setCallBack(this);
        selectPng();
        binding.applyDate.setText(getExtDays() + "天");
    }

    /**
     * 初始化选择图片
     */
    private void selectPng() {
        photoSelectAdapter = new PhotoSelectAdapter(this);//图片选择适配器
        binding.sendOrderImgList.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//设置横向
        binding.sendOrderImgList.setAdapter(photoSelectAdapter);
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

    ExtenDetialRequest request = new ExtenDetialRequest();

    /**
     * 提交
     */
    public void submit() {
        if (!StringUtil.isNullStr(binding.delayDate.getText().toString())) {
            ToastUtil.show(this, "请输入延期天数");
            return;
        }
        if (!StringUtil.isNullStr(binding.delayInfo.getString())) {
            ToastUtil.show(this, "请输入延期原因");
            return;
        }
        request.setExtensionDays(binding.delayDate.getText().toString());
        request.setApplicationDescription(binding.delayInfo.getString());
        request.setId(orderId);
        request.setInstId(proInsId);
        uploadImages();
    }


    /**
     * 上传照片
     */
    private void uploadImages() {
        //开始上传照片
        viewModel.uploadImages(photoSelectAdapter.getSelectedPhotos()).observe(this, data -> {
            hideLoading();
            if (data != null) {
                if (StringUtil.isNullStr(keyId)) {
                    if (RouteKey.KEY_PLAN.equals(keyId)) {
                        viewModel.applyLatePlan(request, data).observe(this, o -> {
                            ToastUtil.show(getApplicationContext(), R.string.apply_late_success);
                            finish();
                        });
                    }
                } else {
                    viewModel.applyLate(request, data).observe(this, o -> {
                        ToastUtil.show(getApplicationContext(), R.string.apply_late_success);
                        finish();
                    });
                }
            } else {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_failed);
            }
        });
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
