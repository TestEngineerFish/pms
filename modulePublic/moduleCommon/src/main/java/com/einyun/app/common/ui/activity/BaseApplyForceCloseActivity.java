package com.einyun.app.common.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.R;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.databinding.ActivityApplyForceCloseBinding;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.common.viewmodel.BaseUploadViewModel;
import com.einyun.app.library.upload.model.PicUrl;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

public abstract class BaseApplyForceCloseActivity<T extends BaseUploadViewModel> extends BaseHeadViewModelActivity<ActivityApplyForceCloseBinding, T> {
    protected PhotoSelectAdapter photoSelectAdapter;
    protected static final int MAX_PHOTO_SIZE = 4;

    protected abstract T initViewModel();

    protected CallBack<Boolean> isClosedCallBack;

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
        initPhotosView();
    }

    /**
     * 初始化选择图片
     */
    protected void initPhotosView() {
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
                    .maxSelectable(MAX_PHOTO_SIZE - photoSelectAdapter.getSelectedPhotos().size())
                    //                .maxSelectable(4 - (photoSelectAdapter.getItemCount() - 1))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new Glide4Engine())
                    .forResult(RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK);
        }, this);
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.applyCloseBtn.setOnClickListener(v -> onSubmitClick());
    }

    /**
     * 点击提交事件
     */
    protected void onSubmitClick() {
        if (validateForm()) {
            uploadImages();
        }
    }

    /**
     * 验证表单
     * @return
     */
    protected boolean validateForm() {
        if (TextUtils.isEmpty(binding.applyCloseReason.getString())) {
            ToastUtil.show(CommonApplication.getInstance(), R.string.txt_plese_enter_reason);
            return false;
        }
        return true;
    }

    /**
     * 表单提交
     * @param data
     */
    public abstract void submitForm(List<PicUrl> data);

    /**
     * 上传照片
     */
    protected void uploadImages() {
        //开始上传照片
        viewModel.uploadImages(photoSelectAdapter.getSelectedPhotos()).observe(this, data -> {
            hideLoading();
            if (data != null) {
                submitForm(data);
            }
        });
    }

    protected void setResult(boolean flag){
        Intent intent = new Intent();
        intent.putExtra(DataConstants.KEY_OPTION_RESULT,flag);
        setResult(RESULT_OK, intent);
        finish();
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
