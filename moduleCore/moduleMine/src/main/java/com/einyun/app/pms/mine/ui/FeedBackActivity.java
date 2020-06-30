package com.einyun.app.pms.mine.ui;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.util.BitmapUtil;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.ImageUploadManager;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.common.utils.FileProviderUtil;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.library.mdm.net.request.FeedBackAddRequest;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.mine.R;
import com.einyun.app.pms.mine.databinding.ActivityFeedBackBinding;
import com.einyun.app.pms.mine.viewmodule.SettingViewModel;
import com.einyun.app.pms.mine.viewmodule.SettingViewModelFactory;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

@Route(path = RouterUtils.ACTIVITY_FEED)
public class FeedBackActivity extends BaseHeadViewModelActivity<ActivityFeedBackBinding, SettingViewModel> implements PeriodizationView.OnPeriodSelectListener {
    PhotoSelectAdapter photoSelectAdapter;
    private final int MAX_PHOTO_SIZE = 4;
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    FeedBackAddRequest request ;
    @Override
    public int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.txt_feed_back);
        setRightTxt(R.string.txt_feed_list);
        setRightTxtColor(R.color.colorPrimary);
        selectPng();
        binding.btSubmit.setOnClickListener(v -> {
            if (TextUtils.isEmpty(request.getOrgId())){
                ToastUtil.show(this,"请选择社区");
            }else {
                uploadImages();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        request = new FeedBackAddRequest();
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.selectDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出分期view
                PeriodizationView periodizationView = new PeriodizationView();
                periodizationView.setPeriodListener(FeedBackActivity.this::onPeriodSelectListener);
                periodizationView.show(getSupportFragmentManager(), "");
            }
        });
    }

    /**
     * 上传照片
     */
    private void uploadImages() {
        if (!StringUtil.isNullStr(binding.ltQuestionTitle.getString())) {
            ToastUtil.show(this, "请输入问题标题");
            return;
        }
        //开始上传照片
        viewModel.uploadImages(photoSelectAdapter.getSelectedPhotos()).observe(this, data -> {
            hideLoading();
            if (data != null) {
                ImageUploadManager uploadManager = new ImageUploadManager();
                String josnString = uploadManager.toJosnString(data);
                request.setFeedbackId(userModuleService.getUserId());
                request.setSource(1);
                request.setFeedbackPhone(userModuleService.getPhone());
                request.setFeedbackAccount(userModuleService.getAccount());
                request.setAttachment(josnString);
                request.setTitle(binding.ltQuestionTitle.getString());
                request.setContent(binding.ltQuestionDesc.getString());
                request.setFeedbackName(userModuleService.getUserName());
                viewModel.addFeedBack(request).observe(this, o -> {
                    ARouter.getInstance().build(RouterUtils.ACTIVITY_FEED_SUCCESS).navigation();
                });
            } else {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_failed);
            }
        });
    }

    @Override
    protected SettingViewModel initViewModel() {
        return new ViewModelProvider(this, new SettingViewModelFactory()).get(SettingViewModel.class);
    }

    /**
     * 初始化选择图片
     */
    private void selectPng() {
        photoSelectAdapter = new PhotoSelectAdapter(this);//图片选择适配器
        binding.selectImgsRec.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//设置横向
        binding.selectImgsRec.setAdapter(photoSelectAdapter);
        binding.selectImgsRec.addItemDecoration(new SpacesItemDecoration(18));
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK) {
            if (data == null) return;
            List<Uri> uris = Matisse.obtainResult(data);
//            photoSelectAdapter.addPhotos(uris);
            if (uris != null && uris.size() > 0) {
                for (Uri uri : uris) {
                    addWater(uri);
                }
            }
        }
    }


    private void addWater(Uri uri) {
        String file = FileProviderUtil.getUploadImagePath(uri);
        Observable.just(file).subscribeOn(Schedulers.io())
                .subscribe(path -> {
                    BitmapUtil.AddTimeWatermark(new File(path));
                    runOnUiThread(() -> {
                        if (uri != null) {
                            photoSelectAdapter.addPhotos(Arrays.asList(uri));
                        }
                    });
                });
    }


    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        binding.selectDivide.setText(orgModel.getName());
        request.setOrgId(orgModel.getId());
        request.setOrgName(orgModel.getName());
    }

    @Override
    public void onRightOptionClick(View view) {
        super.onRightOptionClick(view);
        ARouter.getInstance().build(RouterUtils.ACTIVITY_FEED_LIST).navigation();
    }
}
