package com.einyun.app.common.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.R;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.databinding.ActivityApplyLateBinding;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.common.viewmodel.BaseUploadViewModel;
import com.einyun.app.library.resource.workorder.model.ApplyState;
import com.einyun.app.library.resource.workorder.model.ApplyType;
import com.einyun.app.library.resource.workorder.model.ExtensionApplication;
import com.einyun.app.library.upload.model.PicUrl;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseApplyPostPoneActivity<T extends BaseUploadViewModel> extends BaseHeadViewModelActivity<ActivityApplyLateBinding, T> {

    protected ArrayList<ExtensionApplication> extensionApplication;

    protected PhotoSelectAdapter photoSelectAdapter;
    protected final int MAX_PHOTO_SIZE = 4;

    public void setExtensionApplication(ArrayList<ExtensionApplication> extensionApplication) {
        this.extensionApplication = extensionApplication;
    }

    protected abstract T initViewModel();

    protected void getExtDays(ActivityApplyLateBinding binding) {
        int i = 0;
        int j = 0;
        if (extensionApplication == null || extensionApplication.size() == 0) {
            binding.applyDate.setText("0天");
            binding.applyNum.setText("0次");
            return;
        }
        List<ExtensionApplication> exts = new ArrayList<>();
        for (ExtensionApplication ext : extensionApplication) {
            if (ApplyType.POSTPONE.getState() == ext.getApplyType() && ext.getExtensionDays() != null && ext.getApplicationState() == ApplyState.APPLYING.getState()) {
                j++;
                i = i + Integer.valueOf(ext.getExtensionDays());
            }
        }
        binding.applyDate.setText(i + "天");
        binding.applyNum.setText(j + "次");
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
        getExtDays(binding);
    }

    /**
     * 初始化选择图片
     */
    protected void selectPng() {
        photoSelectAdapter = new PhotoSelectAdapter(this);//图片选择适配器
        binding.sendOrderImgList.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//设置横向
        binding.sendOrderImgList.setAdapter(photoSelectAdapter);
        binding.sendOrderImgList.addItemDecoration(new SpacesItemDecoration(18));
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
                    .maxSelectable(MAX_PHOTO_SIZE - photoSelectAdapter.getSelectedPhotos().size())
                    //                .maxSelectable(4 - (photoSelectAdapter.getItemCount() - 1))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new Glide4Engine())
                    .forResult(RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK);
        }, this);
    }

    /**
     * 上传照片
     */
    protected void uploadImages() {
        //开始上传照片
        viewModel.uploadImages(photoSelectAdapter.getSelectedPhotos()).observe(this, data -> {
            hideLoading();
            if (data != null) {
                submitForm(data);
            } else {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_failed);
            }
        });
    }

    protected boolean validateForm() {
        if (!StringUtil.isNullStr(binding.delayDate.getText().toString())) {
            ToastUtil.show(this, "请输入延期天数");
            return false;
        }
        if (!StringUtil.isNullStr(binding.delayInfo.getString())) {
            ToastUtil.show(this, "请输入延期原因");
            return false;
        }
        return true;
    }

    /**
     * 提交点击事件
     */
    public void submit() {
        if (validateForm()) {
            uploadImages();
        }
    }

    /**
     * 表单提交
     * @param data
     */
    public abstract void submitForm(List<PicUrl> data);

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

    protected void setResult(boolean flag){
        Intent intent = new Intent();
        intent.putExtra(DataConstants.KEY_OPTION_RESULT,flag);
        setResult(RESULT_OK, intent);
        finish();
    }
}
