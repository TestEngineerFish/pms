package com.einyun.app.common.zxing;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.common.R;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.databinding.ActivityScannerBinding;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseSkinViewModelActivity;
import com.google.zxing.Result;
import com.orhanobut.logger.Logger;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

@Route(path = RouterUtils.ACTIVITY_SCANNER)
public class ScannerActivity extends BaseSkinViewModelActivity<ActivityScannerBinding, BaseViewModel> {
    protected boolean mFlash;

    protected ZXingScannerView.ResultHandler mResultHandler = new ZXingScannerView.ResultHandler() {
        @Override
        public void handleResult(Result result) {
            resumeCameraPreview();
            Logger.d("扫码内容->"+ result.getText());
            Logger.d("扫码格式"+ result.getBarcodeFormat().toString());
            onScanResult(result.getText());
        }
    };

    protected void resumeCameraPreview(){
        binding.scannerView.resumeCameraPreview(mResultHandler); //重新进入扫描二维码
    }

    protected void stopCameraPreview(){
        binding.scannerView.stopCameraPreview();
    }

   protected void onScanResult(String result){
        Intent intent = new Intent();
        intent.putExtra(DataConstants.KEY_SCANNER_CONTENT, result);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    protected void initData() {
        super.initData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1001);
            }
        } else {
        }
        binding.setCallBack(this);
        binding.scannerView.setResultHandler(mResultHandler);
    }

    @Override
    protected BaseViewModel initViewModel() {
        return new BaseViewModel();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_scanner;
    }


    @Override
    public void onResume() {
        super.onResume();
        binding.scannerView.setResultHandler(mResultHandler);
        binding.scannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.scannerView.stopCamera();
    }

    private void toggleFlash() {
        mFlash = !mFlash;
        binding.scannerView.setFlash(mFlash);
    }

    @Override
    protected boolean fullWindowFlag() {
        return true;
    }

    @Override
    protected int getColorPrimary() {
        return Color.TRANSPARENT;
    }
}
