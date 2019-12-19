package com.einyun.app.pms.patrol.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.db.entity.PatrolLocal;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.model.SignCheckResult;
import com.einyun.app.pms.patrol.viewmodel.PatrolSignInViewModel;
import com.einyun.app.pms.patrol.viewmodel.ViewModelFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * 巡更扫码签到
 */
@Route(path = RouterUtils.ACTIVITY_PATROL_TIME_QR_SIGNIN_HANDLE)
public class PatrolQRSignInHandleActivity extends PatrolQRSignInDetialActivity {

    @Autowired(name = RouteKey.KEY_PARAMS)
    Bundle bundle;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    protected String orderId;

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
        binding.setCallBack(this);
        binding.btnSubmit.setVisibility(View.VISIBLE);


    }

    @Override
    protected void initData() {
        super.setBundle(bundle);
        super.setOrderId(orderId);
        super.initData();

        initScan();
    }

    /**
     * 如果未签到，显示右上角签到按钮
     */
    private void initScan() {
        if(SignCheckResult.SIGN_IN_SUCCESS!=workNode.getSign_result()){
            setRightOption(R.mipmap.img_qrcode_scan);
        }else{
            binding.headBar.ivRightOption.setVisibility(View.GONE);
        }
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
        binding.rvCaptureImages.setAdapter(photoSelectAdapter);
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

    @Override
    public void onOptionClick(View view) {
        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_QR_SCANNER)
                .withString(RouteKey.KEY_QR_ID, workNode.patrol_point_id)
                .navigation(this, RouterUtils.ACTIVITY_REQUEST_SCANNER);
    }

    /**
     * 提交
     */
    public void onSubmitClick(){
        cacheCaptures();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RouterUtils.ACTIVITY_REQUEST_SIGN_IN) {

        } else if (requestCode == RouterUtils.ACTIVITY_REQUEST_SCANNER && resultCode == RESULT_OK) {
            String pointId = data.getStringExtra(DataConstants.KEY_SCANNER_CONTENT);
            boolean sacnResult = data.getBooleanExtra(DataConstants.KEY_QR_SCAN_RESULT, false);
            if (sacnResult) {
                workNode.setSign_result(SignCheckResult.F_WK_RESULT_SUCCESS);
                binding.setNode(workNode);
                initScan();
                signInSuccess(pointId);
            }
        }
    }

    /**
     * 巡更点签到成功
     *
     * @param pointId
     */
    private void signInSuccess(String pointId) {
        viewModel.signInSuccess(orderId, pointId);
    }
}
