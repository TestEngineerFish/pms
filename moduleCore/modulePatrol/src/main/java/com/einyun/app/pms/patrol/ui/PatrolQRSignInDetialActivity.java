package com.einyun.app.pms.patrol.ui;

import android.content.Intent;
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
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.util.BitmapUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.GetUploadJson;
import com.einyun.app.common.manager.ImageUploadManager;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.component.photo.PhotoListItemListener;
import com.einyun.app.common.ui.component.photo.PhotoLocalListAdapter;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.component.photo.PhotoShowActivity;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.common.utils.CaptureUtils;
import com.einyun.app.library.upload.model.PicUrl;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.databinding.ActivityPatrolTimeSigninBinding;
import com.einyun.app.pms.patrol.databinding.ItemPatrolTimeCheckNodeBinding;
import com.einyun.app.pms.patrol.model.PatrolCheckItem;
import com.einyun.app.pms.patrol.model.SignCheckResult;
import com.einyun.app.pms.patrol.viewmodel.PatrolSignInViewModel;
import com.einyun.app.pms.patrol.viewmodel.ViewModelFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * 巡更扫码签到
 */
@Route(path = RouterUtils.ACTIVITY_PATROL_TIME_QR_SIGNIN_DETIAL)
public class PatrolQRSignInDetialActivity extends BaseHeadViewModelActivity<ActivityPatrolTimeSigninBinding, PatrolSignInViewModel> {
    protected RVBindingAdapter nodesAdapter;
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


    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    protected PatrolSignInViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(PatrolSignInViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_patrol_time_signin;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_patrol_time_signin);
        initSamplePhotos();//参考标准图片
        initCapturePhotos();//现场拍照对比
        initCheckNodes();//巡更检查项
        binding.btnSubmit.setVisibility(View.GONE);
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
        if (photoListAdapterUpload == null) {
            photoListAdapterUpload = new PhotoListAdapter(this);
        }
        binding.rvCaptureImages.addItemDecoration(new SpacesItemDecoration());
        binding.rvCaptureImages.setAdapter(photoListAdapterUpload);
    }

    //巡更检查项
    protected void initCheckNodes() {
        nodesAdapter = new RVBindingAdapter<ItemPatrolTimeCheckNodeBinding, PatrolCheckItem>(this, com.einyun.app.pms.patrol.BR.checkItem) {

            @Override
            public void onBindItem(ItemPatrolTimeCheckNodeBinding binding, PatrolCheckItem model, int position) {
                if (position == 0) {
                    tableHead(binding);
                } else {
                    //处理节点
                    tableItem(binding, position);
                }
            }

            private void tableItem(ItemPatrolTimeCheckNodeBinding binding, int position) {
                binding.tvNumber.setText(position + "");
                binding.tvWorkNode.setVisibility(View.VISIBLE);
                binding.tvWorkThings.setGravity(Gravity.LEFT);
            }

            private void tableHead(ItemPatrolTimeCheckNodeBinding binding) {
                binding.tvNumber.setText(R.string.text_no);
                binding.tvWorkNode.setVisibility(View.GONE);
                binding.tvWorkThings.setGravity(Gravity.CENTER);
                binding.tvWorkThings.setText(R.string.text_check_items);
                binding.tvWorkThings.setTextSize(14);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_patrol_time_check_node;
            }
        };
        binding.rvNodes.setAdapter(nodesAdapter);
    }

    /**
     * 更新UI
     */
    protected void updateUI() {
        workNode = (WorkNode) bundle.get(RouteKey.KEY_PATROL_TIME_WORKNODE);
        binding.frameSpace.setVisibility(View.GONE);
        binding.setNode(workNode);
        if(SignCheckResult.SIGN_IN_SUCCESS!=workNode.getSign_result()){
            binding.llPatrolSigninTime.setVisibility(View.GONE);
            binding.llPatrolSigninResult.setVisibility(View.GONE);
        }else{
            binding.llPatrolSigninTime.setVisibility(View.VISIBLE);
        }
        updateSamplePic();
        updateCapturePic();
        updateCheckNodes();
    }

    protected void updateSamplePic() {
        if (!TextUtils.isEmpty(workNode.pic_example_url)) {
            updateNetPhoto(workNode.pic_example_url, photoListAdapter);
        }
    }

    protected void updateCheckNodes() {
        if (!TextUtils.isEmpty(workNode.patrol_items)) {
            List<PatrolCheckItem> list = new Gson().fromJson(workNode.patrol_items, new TypeToken<List<PatrolCheckItem>>() {
            }.getType());
            list.add(0, new PatrolCheckItem());//表头
            nodesAdapter.setDataList(list);
        }
    }

    protected void updateCapturePic(){
        if(!TextUtils.isEmpty(workNode.getPic_url())){
            updateNetPhoto(workNode.getPic_url(), photoListAdapterUpload);
        }else {
            updateLocalPhoto();
        }
    }

    /**
     * 显示本地图片
     */
    private void updateLocalPhoto() {
        binding.rvCaptureImages.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//设置横向
        if(photoLocalListAdapter==null){
            photoLocalListAdapter=new PhotoLocalListAdapter(this);
        }
        binding.rvCaptureImages.addItemDecoration(new SpacesItemDecoration());
        binding.rvCaptureImages.setAdapter(photoLocalListAdapter);
        List<String> iamgePaths=workNode.getCachedImages();
        if (iamgePaths!= null && iamgePaths.size() > 0) {
            List<Uri> uris = new ArrayList<>();
            for (String imgeUrl :iamgePaths) {
                Uri uri = Uri.parse(imgeUrl);
                uris.add(uri);
            }
            photoLocalListAdapter.updateList(uris);
            photoLocalListAdapter.setOnItemListener((v, position) -> {
                PhotoShowActivity.start(this,position, (ArrayList<String>) photoLocalListAdapter.getImagePaths());
            });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RouterUtils.ACTIVITY_REQUEST_CAMERA_OK && resultCode == RESULT_OK) {
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(this, DataConstants.DATA_PROVIDER_NAME, imageFile);
            } else {
                uri = Uri.fromFile(imageFile);
            }
            Observable.just(imageFile).subscribeOn(Schedulers.io())
                    .subscribe(file -> {
                        try {
                            BitmapUtil.AddTimeWatermark(file);
                        }catch (Exception e){
                            ToastUtil.show(PatrolQRSignInDetialActivity.this,"内存不足，水印添加失败");
                        }
                        runOnUiThread(() -> {
                            if (uri != null) {
                                photoSelectAdapter.addPhotos(Arrays.asList(uri));
//                                cacheCaptures();
                            }
                        });
                    });
        }
    }

    protected void cacheCaptures(){
        viewModel.cachePhotos(workNode,orderId,photoSelectAdapter.getSelectedPhotos(),this);
    }

    /**
     * 提交
     */
    public void onSubmitClick(){
        uploadImages();
    }

    /**
     *上传图片
     */
    public void uploadImages() {
        List<Uri> uris = photoSelectAdapter.getSelectedPhotos();//取出本地缓存图片，开始上传
        ImageUploadManager manager = new ImageUploadManager();
        manager.upload(uris, new CallBack<List<PicUrl>>() {
            @Override
            public void call(List<PicUrl> data) {
                //图片上传成功
                GetUploadJson getUploadJsonStr = new GetUploadJson(data).invoke();
                List<PicUrlModel> picUrlModels = getUploadJsonStr.getPicUrlModels();
                String picsJson = getUploadJsonStr.getGson().toJson(picUrlModels);
                workNode.setPic_url(picsJson);//回填服务器返回上传图片结果信息
                ToastUtil.show(getApplicationContext(),"图片上传成功");
                cacheCaptures();
            }

            @Override
            public void onFaild(Throwable throwable) {
                ToastUtil.show(getApplicationContext(),"图片上传失败");
            }
        });
    }

}
