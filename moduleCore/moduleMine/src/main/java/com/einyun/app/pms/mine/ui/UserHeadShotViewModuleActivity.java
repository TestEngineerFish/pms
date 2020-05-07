package com.einyun.app.pms.mine.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.common.utils.HttpUrlUtil;
import com.einyun.app.pms.mine.R;
import com.einyun.app.pms.mine.constants.Constants;
import com.einyun.app.pms.mine.databinding.ActivityUserHeadShotViewModuleBinding;
import com.einyun.app.pms.mine.model.GetUserByccountBean;
import com.einyun.app.pms.mine.ui.widget.SelectPhotoPopWindow;
import com.einyun.app.pms.mine.viewmodule.SettingViewModelFactory;
import com.einyun.app.pms.mine.viewmodule.UserHeadShotViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;


//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_USER_HEAD_SHOT)
public class UserHeadShotViewModuleActivity extends BaseHeadViewModelActivity<ActivityUserHeadShotViewModuleBinding, UserHeadShotViewModel> implements SelectPhotoPopWindow.OnItemClickListener {
    @Autowired(name = Constants.KEY_USER_BEAN)
    GetUserByccountBean getUserByccountBean;
    @Override
    protected UserHeadShotViewModel initViewModel() {
        return new ViewModelProvider(this, new SettingViewModelFactory()).get(UserHeadShotViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_head_shot_view_module;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
//        setTitleBarColor(R.color.white);
//        setBackIcon(R.drawable.back);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(getString(R.string.tv_person_head));
        setRightOption(R.drawable.iv_head_choice);
        binding.setCallBack(this);
        String imageUrl= HttpUrlUtil.getImageServerUrl(getUserByccountBean.getPhoto());
        Glide.with(this)
                .load(imageUrl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .placeholder(R.drawable.iv_default_head_shot)
//                .centerCrop()
                .into(binding.ivHead);
    }

    @Override
    protected void initData() {
        super.initData();
    }
    /**
     * 上传头像按钮
     */
    public void onOptionClick(View view){
        SelectPhotoPopWindow selectPhotoPopWindow = new SelectPhotoPopWindow(this);
        selectPhotoPopWindow.setOnItemClickListener(this);
        selectPhotoPopWindow.showAtLocation(binding.ivHead, Gravity.BOTTOM,0,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK) {
            if (data == null) return;
            List<Uri> uris = Matisse.obtainResult(data);

            uploadImages(uris);
        }else if (requestCode==24){
            if (data == null) return;
            List<Uri> uris = Matisse.obtainResult(data);

            uploadImages(uris);
            Logger.d("拍照拍找拍照");
        }
    }
    /**
     * 上传照片
     */
    private void uploadImages(List<Uri> uris) {
        //开始上传照片
        viewModel.uploadImages(uris).observe(this, data -> {
            Glide.with(this).load(uris.get(0)).centerCrop().into(binding.ivHead);
            hideLoading();
            if (data!=null) {
                Log.e("uploadImages", "uploadImages: " );
                viewModel.create(getUserByccountBean,data).observe(this, flag -> {
                    if (!flag) {
                        ToastUtil.show(getApplicationContext(), R.string.alert_submit_error);
                    } else {
                        //通知刷新界面
                        LiveEventBus.get(LiveDataBusKey.MINE_FRESH, String.class).post("");
                        finish();
                    }
                });
            }else{
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_failed);
            }
        });
    }
    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }

    @Override
    public void takePicClick() {
                requestPerm(true);
//                startActivityForResult(new Intent(this,MatisseActivity.class),24);
////
//                MediaStoreCompat mediaStoreCompat = new MediaStoreCompat(UserHeadShotViewModuleActivity.this);
//                mediaStoreCompat.setCaptureStrategy(new CaptureStrategy(true, DataConstants.DATA_PROVIDER_NAME));
//                mediaStoreCompat.dispatchCaptureIntent(UserHeadShotViewModuleActivity.this, 24);
    }

    @Override
    public void photoAlbumClick() {
        requestPerm(false);
    }

    private void SelectPic(boolean isCapture) {
        Matisse.from(this) //加号添加图片
                .choose(MimeType.ofImage())
                .captureStrategy(new CaptureStrategy(true, DataConstants.DATA_PROVIDER_NAME))
                .capture(isCapture)
                .countable(true)
                .maxSelectable(1)
                //                .maxSelectable(4 - (photoSelectAdapter.getItemCount() - 1))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .forResult(RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK);
    }
    /**
     * 相机权限校验
     * */
    public void requestPerm(boolean isCapture) {
        AndPermission.with(this)
                .permission(
                        Permission.Group.CAMERA
                ).onGranted(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                SelectPic(isCapture);
            }
        }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                Toast.makeText(UserHeadShotViewModuleActivity.this, getString(R.string.tv_no_camera_permission), Toast.LENGTH_SHORT).show();

            }
        }).start();
    }
}
