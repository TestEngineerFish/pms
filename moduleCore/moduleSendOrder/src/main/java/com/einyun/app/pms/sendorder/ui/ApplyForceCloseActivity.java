package com.einyun.app.pms.sendorder.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.library.resource.workorder.net.request.ApplyCloseRequest;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.ActivityApplyForceCloseBinding;
import com.einyun.app.pms.sendorder.databinding.ActivitySendOrderDetailBinding;
import com.einyun.app.pms.sendorder.viewmodel.ApplyCloseViewModel;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

@Route(path = RouterUtils.ACTIVITY_CLOSE)
public class ApplyForceCloseActivity extends BaseHeadViewModelActivity<ActivityApplyForceCloseBinding, ApplyCloseViewModel> {
    private PhotoSelectAdapter photoSelectAdapter;
    private static final int MAX_PHOTO_SIZE=4;
    private ApplyCloseRequest request;
    @Override
    protected ApplyCloseViewModel initViewModel() {
        return new ViewModelProvider(this, new SendOdViewModelFactory()).get(ApplyCloseViewModel.class);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_force_close;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_apply_close);
        request=new ApplyCloseRequest();
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
                    .maxSelectable(MAX_PHOTO_SIZE)
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
                request.setDesc(binding.applyCloseReason.getString());
                uploadImages();
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
                viewModel.applyClose(request, data).observe(this, model -> {
                    Log.d("test",model.getMsg());
                    if (!model.isState()) {
                        ToastUtil.show(getApplicationContext(), R.string.alert_submit_error);
                    } else {
                        ToastUtil.show(getApplicationContext(), R.string.alert_submit_work_order_success);
                        finish();
                    }
                });
            } else {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_failed);
            }
        });
    }

}
