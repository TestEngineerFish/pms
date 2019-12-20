package com.einyun.app.pms.patrol.ui;

import android.content.Intent;
import android.os.Handler;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.zxing.ScannerActivity;
import com.einyun.app.pms.patrol.R;

/**
 * 二维码扫码签到，通过比对巡更点code，判断是否签到成功
 */
@Route(path = RouterUtils.ACTIVITY_PATROL_TIME_QR_SCANNER)
public class PatrolSignInScannerActivity extends ScannerActivity {
    protected AlertDialog alertDialog;
    Handler handler=new Handler();
    @Autowired(name = RouteKey.KEY_QR_ID)
    String qrId;
    boolean scanResult;
    private Runnable runnable;

    /**
     * 扫码识别结果处理
     * @param result
     */
    @Override
    protected void onScanResult(String result) {
        //比对二维码是否一致，判断是否签到成功
        if(qrId.equals(result)){
            scanResult=true;
            showSuccess();
        }else{
            scanResult=false;
            showFaild();
        }
        /**
         * 3秒自动退出扫码
         */
        if(runnable==null){
            runnable= () -> setScanResult();
        }else{
            handler.removeCallbacks(runnable);
        }
        handler.postDelayed(runnable,3*1000);
    }

    /**
     * 设置扫码结果
     */
    private void setScanResult() {
        if(scanResult){
            setSuccessResult();
        }else{
            setFailedResult();
        }
    }

    private void setSuccessResult(){
        setResult(true);
    }

    private void setResult(boolean flag){
        Intent intent = new Intent();
        intent.putExtra(DataConstants.KEY_SCANNER_CONTENT, qrId);
        intent.putExtra(DataConstants.KEY_QR_SCAN_RESULT,flag);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setFailedResult(){
       setResult(false);
    }

    /**
     * 显示扫码成功
     */
    private void showSuccess(){
        if(alertDialog==null){
            alertDialog=new AlertDialog(this).builder()
                    .setTitle(getString(R.string.text_signin_success))
                    .setMsg(getString(R.string.text_auto_return))
                    .setNegativeButton(getString(R.string.text_know), v -> {
                        setScanResult();
                        finish();
                    });
        }
        alertDialog.show();
    }

    /**
     * 显示扫码失败dialog
     */
    private void showFaild(){
        if(alertDialog==null){
            alertDialog=new AlertDialog(this).builder()
                    .setTitle(getString(R.string.text_signin_failed))
                    .setMsg(getString(R.string.text_auto_return))
                    .setNegativeButton(getString(R.string.text_know), v -> {
                        setScanResult();
                        finish();
                    });
        }
        alertDialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
        setScanResult();
        if(handler!=null){
            if(runnable!=null){
                handler.removeCallbacks(runnable);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
