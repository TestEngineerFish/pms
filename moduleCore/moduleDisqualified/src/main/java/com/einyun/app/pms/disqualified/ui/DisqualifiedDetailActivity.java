package com.einyun.app.pms.disqualified.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.pms.disqualified.R;
import com.einyun.app.pms.disqualified.databinding.ActivityDisqualifiedDetailBinding;
import com.einyun.app.pms.disqualified.databinding.ActivityDisqualifiedViewModuleBinding;
import com.einyun.app.pms.disqualified.ui.fragment.DisqualifiedViewModuleFragment;
import com.einyun.app.pms.disqualified.viewmodel.DisqualifiedViewModel;
import com.einyun.app.pms.disqualified.viewmodel.DisqualifiedViewModelFactory;
import com.google.android.material.tabs.TabLayout;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_HAD_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_WAIT_FOLLOW;

//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_DISQUALIFIED_DETAIL)
public class DisqualifiedDetailActivity extends BaseHeadViewModelActivity<ActivityDisqualifiedDetailBinding, DisqualifiedViewModel> {

    private PhotoSelectAdapter photoSelectAdapter;
    private PhotoSelectAdapter photoValidationSelectAdapter;
    private final int MAX_PHOTO_SIZE = 4;
    @Override
    protected DisqualifiedViewModel initViewModel() {
        return new ViewModelProvider(this, new DisqualifiedViewModelFactory()).get(DisqualifiedViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_disqualified_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(R.string.tv_disqualified_order);
        selectPng();
        selectValidationPng();
        binding.setCallBack(this);
//        PhotoListAdapter photoOrderInfoAdapter = new PhotoListAdapter(this);
//        PhotoListAdapter photoFeedBackInfoAdapter = new PhotoListAdapter(this);
//        PhotoListAdapter photoValidationInfoAdapter = new PhotoListAdapter(this);
//        forseClosephotoListInfoAdapter = new PhotoListAdapter(this);
//        binding.listPic.setLayoutManager(new LinearLayoutManager(
//                this,
//                LinearLayoutManager.HORIZONTAL,
//                false));
//        binding.listPic.addItemDecoration(new SpacesItemDecoration(18));
//        binding.listPic.setAdapter(photoListInfoAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
    }
    /**
     * 反馈信息缓存按钮
     */
    public void onFeedBackCacheClick(){}
    /**
     * 反馈信息提交
     */
    public void onFeedBackPassClick(){}
    //创建新工单
    public void onCreateOrderClick(){}
    //验证信息缓存
    public void onValidationCacheClick(){}
    //验证信息提交
    public void onValidationPassClick(){}
    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
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
        }else if (requestCode == RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK_SECOND){
            if (data == null) return;
            List<Uri> uris = Matisse.obtainResult(data);
            if (uris != null && uris.size() > 0) {
                photoValidationSelectAdapter.addPhotos(uris);
            }
        }
    }
    /**
     * 初始化选择图片
     */
    private void selectPng() {
        //图片选择适配器
        photoSelectAdapter = new PhotoSelectAdapter(this);
        binding.rvFeedbackList.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//设置横向
        binding.rvFeedbackList.setAdapter(photoSelectAdapter);
        binding.rvFeedbackList.addItemDecoration(new SpacesItemDecoration(18));
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
     * 初始化选择图片
     */
    private void selectValidationPng() {
        //图片选择适配器
        photoValidationSelectAdapter = new PhotoSelectAdapter(this);
        binding.rvValidationList.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//设置横向
        binding.rvValidationList.setAdapter(photoValidationSelectAdapter);
        binding.rvValidationList.addItemDecoration(new SpacesItemDecoration(18));
        photoValidationSelectAdapter.setAddListener(selectedSize -> {
            if (photoValidationSelectAdapter.getSelectedPhotos().size() >= MAX_PHOTO_SIZE) {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_max);
                return;
            }
            Matisse.from(this) //加号添加图片
                    .choose(MimeType.ofImage())
                    .captureStrategy(new CaptureStrategy(true, DataConstants.DATA_PROVIDER_NAME))
                    .capture(true)
                    .countable(true)
                    .maxSelectable(MAX_PHOTO_SIZE - photoValidationSelectAdapter.getSelectedPhotos().size())
                    //                .maxSelectable(4 - (photoSelectAdapter.getItemCount() - 1))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new Glide4Engine())
                    .forResult(RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK_SECOND);
        }, this);
    }
}
