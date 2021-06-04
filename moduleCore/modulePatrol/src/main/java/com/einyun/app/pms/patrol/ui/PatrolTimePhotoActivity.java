package com.einyun.app.pms.patrol.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.base.util.BitmapUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.component.photo.PhotoLocalListAdapter;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.component.photo.PhotoShowActivity;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.common.utils.CaptureUtils;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.databinding.ActivityPatrolPhotoBinding;
import com.einyun.app.pms.patrol.viewmodel.PatrolSignInViewModel;
import com.einyun.app.pms.patrol.viewmodel.ViewModelFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
/**
 * 巡更现场图片
 */
@Route(path = RouterUtils.ACTIVITY_PATROL_PHOTO)
public class PatrolTimePhotoActivity  extends BaseHeadViewModelActivity<ActivityPatrolPhotoBinding, PatrolSignInViewModel> {
    protected PhotoListAdapter photoListAdapter; //网络sample
    protected PhotoListAdapter photoListAdapterUpload; //网络用户上传对比照片(详情用)
    protected PhotoSelectAdapter photoSelectAdapter; //签到处理-用户上传图片(处理页面用)
    protected PhotoLocalListAdapter photoLocalListAdapter;//本地上传展示（详情使用）
    protected WorkNode workNode;
    @Autowired(name = RouteKey.KEY_PARAMS)
    Bundle bundle;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    protected String orderId;
    protected final int MAX_PHOTO_SIZE = 4;
    protected File imageFile;

    @Override
    protected PatrolSignInViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(PatrolSignInViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_patrol_photo;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_patrol_photo);
        binding.setCallBack(this);
        initSamplePhotos();//参考标准图片
        initCapturePhotos();//现场拍照对比
    }

    @Override
    protected void initData() {
        super.initData();
        updateUI();
        viewModel.loadCachedImageList(workNode,orderId).observe(this, strings -> {
            workNode.setCachedImages(strings);
            updateCapturePic();
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        if(photoSelectAdapter!=null){
            photoSelectAdapter.setAddListener(selectedSize -> {
                if (photoSelectAdapter.getSelectedPhotos().size() >= MAX_PHOTO_SIZE) {
                    ToastUtil.show(getApplicationContext(), R.string.upload_pic_max);
                    return;
                }
                imageFile = CaptureUtils.startCapture(this);
            }, this);
        }
    }

    /**
     * 更新UI
     */
    protected void updateUI() {
        workNode = (WorkNode) bundle.get(RouteKey.KEY_PATROL_TIME_WORKNODE);
        updateSamplePic();
        updateCapturePic();
    }

    //参考标准图片
    protected void initSamplePhotos() {
        binding.rvSampleImages.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//设置横向
        if (photoListAdapter == null) {
            photoListAdapter = new PhotoListAdapter(this);
        }
        binding.rvSampleImages.addItemDecoration(new SpacesItemDecoration());
        binding.rvSampleImages.setAdapter(photoListAdapter);
    }

    //现场拍照对比
    protected void initCapturePhotos() {
        binding.rvCaptureImages.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//设置横向
        if (photoSelectAdapter == null) {
            photoSelectAdapter = new PhotoSelectAdapter(this);
        }
        binding.rvCaptureImages.addItemDecoration(new SpacesItemDecoration());
        binding.rvCaptureImages.setAdapter(photoSelectAdapter);
    }

    protected void updateSamplePic() {
        if (!TextUtils.isEmpty(workNode.pic_example_url)) {
            updateNetPhoto(workNode.pic_example_url, photoListAdapter);
        }
    }

    protected void updateCapturePic(){
        List<String> iamgePaths=workNode.getCachedImages();
        if (iamgePaths!= null && iamgePaths.size() > 0) {
            List<Uri> uris = new ArrayList<>();
            for (String imgeUrl :iamgePaths) {
                Uri uri = Uri.parse(imgeUrl);
                uris.add(uri);
            }
            photoSelectAdapter.setSelectedPhotos(uris);
        }
    }

    /**
     * 显示网络图片
     * @param pic_url
     * @param photoListAdapterUpload
     */
    private void updateNetPhoto(String pic_url, PhotoListAdapter photoListAdapterUpload) {
        PicUrlModelConvert convert = new PicUrlModelConvert();
        List<PicUrlModel> modelList = convert.stringToSomeObjectList(pic_url);
        photoListAdapterUpload.updateList(modelList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RouterUtils.ACTIVITY_REQUEST_CAMERA_OK && resultCode == RESULT_OK) {
            if (data==null||imageFile==null){
                return;
            }

            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(this, DataConstants.DATA_PROVIDER_NAME, imageFile);
            } else {
                uri = Uri.fromFile(imageFile);
            }
            showLoading();
            Observable.just(imageFile).subscribeOn(Schedulers.io())
                    .subscribe(file -> {
                        BitmapUtil.AddTimeWatermark(file);
                        runOnUiThread(() -> {
                            if (uri != null) {
                                photoSelectAdapter.addPhotos(Arrays.asList(uri));
                                cacheCaptures();
                            }
                            hideLoading();
                        });
                    });
        }
    }

    protected void cacheCaptures(){
        viewModel.cachePhotos(workNode,orderId,photoSelectAdapter.getSelectedPhotos());
    }

    /**
     * 提交
     */
    public void onSubmitClick(){
        cacheCaptures();
        finish();
    }
}
