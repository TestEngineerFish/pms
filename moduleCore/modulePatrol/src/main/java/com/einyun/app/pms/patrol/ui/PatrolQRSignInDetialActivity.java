package com.einyun.app.pms.patrol.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.einyun.app.common.utils.IsFastClick;
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
 * ??????????????????
 */
@Route(path = RouterUtils.ACTIVITY_PATROL_TIME_QR_SIGNIN_DETIAL)
public class PatrolQRSignInDetialActivity extends BaseHeadViewModelActivity<ActivityPatrolTimeSigninBinding, PatrolSignInViewModel> {
    protected RVBindingAdapter nodesAdapter;
    protected PhotoListAdapter photoListAdapter; //??????sample
    protected PhotoListAdapter photoListAdapterUpload; //??????????????????????????????(?????????)
    protected PhotoSelectAdapter photoSelectAdapter; //????????????-??????????????????(???????????????)
    protected PhotoLocalListAdapter photoLocalListAdapter;//????????????????????????????????????
    protected WorkNode workNode;
    @Autowired(name = RouteKey.KEY_PARAMS)
    Bundle bundle;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    protected String orderId;
    protected final int MAX_PHOTO_SIZE = 4;
    protected File imageFile;
    private Handler mHandler;

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
        initSamplePhotos();//??????????????????
        initCapturePhotos();//??????????????????
        initCheckNodes();//???????????????
        binding.btnSubmit.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        super.initData();
        updateUI();
        viewModel.loadCachedImageList(workNode, orderId).observe(this, strings -> {
            workNode.setCachedImages(strings);
            updateCapturePic();
        });
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                ToastUtil.show(PatrolQRSignInDetialActivity.this,msg.obj.toString());
            }
        };
    }

    //??????????????????
    protected void initSamplePhotos() {
        binding.rvSampleImages.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//????????????
        if (photoListAdapter == null) {
            photoListAdapter = new PhotoListAdapter(this);
        }
        binding.rvSampleImages.addItemDecoration(new SpacesItemDecoration());
        binding.rvSampleImages.setAdapter(photoListAdapter);
    }

    //??????????????????
    protected void initCapturePhotos() {
        binding.rvCaptureImages.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//????????????
        if (photoListAdapterUpload == null) {
            photoListAdapterUpload = new PhotoListAdapter(this);
        }
        binding.rvCaptureImages.addItemDecoration(new SpacesItemDecoration());
        binding.rvCaptureImages.setAdapter(photoListAdapterUpload);
    }

    //???????????????
    protected void initCheckNodes() {
        nodesAdapter = new RVBindingAdapter<ItemPatrolTimeCheckNodeBinding, PatrolCheckItem>(this, com.einyun.app.pms.patrol.BR.checkItem) {

            @Override
            public void onBindItem(ItemPatrolTimeCheckNodeBinding binding, PatrolCheckItem model, int position) {
                if (position == 0) {
                    tableHead(binding);
                } else {
                    //????????????
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
     * ??????UI
     */
    protected void updateUI() {
        workNode = (WorkNode) bundle.get(RouteKey.KEY_PATROL_TIME_WORKNODE);
        binding.frameSpace.setVisibility(View.GONE);
        binding.setNode(workNode);
        if (SignCheckResult.SIGN_IN_SUCCESS != workNode.getSign_result()) {
            binding.llPatrolSigninTime.setVisibility(View.GONE);
            binding.llPatrolSigninResult.setVisibility(View.GONE);
        } else {
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
            list.add(0, new PatrolCheckItem());//??????
            nodesAdapter.setDataList(list);
        }
    }

    protected void updateCapturePic() {
        if (!TextUtils.isEmpty(workNode.getPic_url())) {
            updateNetPhoto(workNode.getPic_url(), photoListAdapterUpload);
        } else {
            updateLocalPhoto();
        }
    }

    /**
     * ??????????????????
     */
    private void updateLocalPhoto() {
        binding.rvCaptureImages.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//????????????
        if (photoLocalListAdapter == null) {
            photoLocalListAdapter = new PhotoLocalListAdapter(this);
        }
        binding.rvCaptureImages.addItemDecoration(new SpacesItemDecoration());
        binding.rvCaptureImages.setAdapter(photoLocalListAdapter);
        List<String> iamgePaths = workNode.getCachedImages();
        if (iamgePaths != null && iamgePaths.size() > 0) {
            List<Uri> uris = new ArrayList<>();
            for (String imgeUrl : iamgePaths) {
                Uri uri = Uri.parse(imgeUrl);
                uris.add(uri);
            }
            photoLocalListAdapter.updateList(uris);
            photoLocalListAdapter.setOnItemListener((v, position) -> {
                PhotoShowActivity.start(this, position, (ArrayList<String>) photoLocalListAdapter.getImagePaths());
            });
        }
    }

    /**
     * ??????????????????
     *
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
        if (photoSelectAdapter != null) {
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
                        } catch (Exception e) {
                            ToastUtil.show(PatrolQRSignInDetialActivity.this, "?????????????????????????????????");
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

    protected void cacheCaptures() {
        viewModel.cachePhotos(workNode, orderId, photoSelectAdapter.getSelectedPhotos(), this);
    }

    /**
     * ??????
     */
    public void onSubmitClick() {
        if (IsFastClick.isFastDoubleClick()) {
            uploadImages();
        }
    }

    /**
     * ????????????
     */
    public void uploadImages() {
        List<Uri> uris = photoSelectAdapter.getSelectedPhotos();//???????????????????????????????????????
        if (uris==null||uris.size()==0){
            ToastUtil.show(getApplicationContext(),"???????????????");
            return;
        }
        ImageUploadManager manager = new ImageUploadManager();
        manager.upload(uris, new CallBack<List<PicUrl>>() {
            @Override
            public void call(List<PicUrl> data) {
                //??????????????????
                if (data.size() > 0) {
                    GetUploadJson getUploadJsonStr = new GetUploadJson(data).invoke();
                    List<PicUrlModel> picUrlModels = getUploadJsonStr.getPicUrlModels();
                    String picsJson = getUploadJsonStr.getGson().toJson(picUrlModels);
                    workNode.setPic_url(picsJson);//?????????????????????????????????????????????
                    Message message=new Message();
                    message.obj="??????????????????";
                    mHandler.sendMessage(message);
                    cacheCaptures();
                } else {
                    Message message=new Message();
                    message.obj="?????????????????????????????????";
                    mHandler.sendMessage(message);
                }
            }

            @Override
            public void onFaild(Throwable throwable) {
                Message message=new Message();
                message.obj="?????????????????????????????????";
                mHandler.sendMessage(message);
            }
        });
    }

}
