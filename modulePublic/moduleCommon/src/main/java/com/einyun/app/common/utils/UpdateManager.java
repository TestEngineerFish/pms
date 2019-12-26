package com.einyun.app.common.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.FileProvider;

import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.common.R;
import com.einyun.app.common.ui.widget.NumberProgressBar;
import com.einyun.app.library.uc.user.model.UpdateAppModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

;

/**
 * @author coolszy
 * @date 2012-4-26
 * @blog http://blog.92coding.com
 */

public class UpdateManager {
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    /* 保存解析的XML信息 */
//    HashMap<String, String> mHashMap;
    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
//    private boolean cancelUpdate = false;
    Dialog dialog;
    private Activity mActivity;
    /* 更新进度条 */
    private NumberProgressBar mProgress;

    boolean isNotFirst;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位置
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    installApk();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    UpdateListener listener;

    public UpdateManager(Activity activity,UpdateListener listener) {
        this.mActivity = activity;
        this.listener = listener;
    }


    public void showVersionDialog(final UpdateAppModel versionBean) {
        if (!versionBean.getHasNewVersion()){
            listener.back();
            return;
        }
        if (!(Boolean) SPUtils.get(BasicApplication.getInstance(),versionBean.getVersion(),true)){
            listener.back();
            return;
        }
        View updateView = LayoutInflater.from(mActivity).inflate(R.layout.dialog_update, null);
        dialog = new Dialog(mActivity,R.style.AlertDialogStyle);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(updateView);
        TextView tv_submit = (TextView) updateView.findViewById(R.id.submit);
        TextView tv_cancel = (TextView) updateView.findViewById(R.id.cancel);
        TextView title = updateView.findViewById(R.id.title);
        TextView content = updateView.findViewById(R.id.content);
        title.setText(String.format(mActivity.getResources().getString(R.string.text_update_app_title), versionBean.getVersion()));
        content.setText(versionBean.getContent());
        View line =updateView.findViewById(R.id.line);
        //1为强制更新  去掉取消按钮
        if (versionBean.isForce()) {
            tv_cancel.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.put(BasicApplication.getInstance(),versionBean.getVersion(),false);
                listener.back();
                dialog.dismiss();
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateView.findViewById(R.id.bottom).setVisibility(View.GONE);
                updateView.findViewById(R.id.img_line).setVisibility(View.GONE);
                updateView.findViewById(R.id.progress).setVisibility(View.VISIBLE);
                // 显示下载对话框
                mProgress = (NumberProgressBar) updateView.findViewById(R.id.progress_bar);

                mProgress.setMax(100);
                // 下载文件
                downloadApk(versionBean);
            }
        });
        dialog.show();
    }


    /**
     * 下载apk文件
     */
    private void downloadApk(UpdateAppModel versionBean) {
        // 启动新线程下载软件
        new downloadApkThread(versionBean).start();
    }

    /**
     * 下载文件线程
     *
     * @author coolszy
     * @date 2012-4-26
     * @blog http://blog.92coding.com
     */
    private class downloadApkThread extends Thread {
        UpdateAppModel versionBean;

        public downloadApkThread(UpdateAppModel versionBean) {
            this.versionBean = versionBean;
        }

        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "com.einyun.app";

//                    URL url = new URL(versionBean.getVersionUrl());

                    URL url = new URL(versionBean.getUrl());
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Charset", "UTF-8");
                    conn.setRequestMethod("GET");
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, "一应未来.apk");
                    if (!apkFile.exists()) {
                        apkFile.delete();
                    }
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
//                    do {
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0) {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (true);
//                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 取消下载对话框显示
//            dialog.dismiss();
        }
    }

    ;

    /**
     * 安装APK文件
     */
    private void installApk() {

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 判断是否是7.0
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            // 适配android7.0 ，不能直接访问原路径
            // 需要对intent 授权
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uriForFile = FileProvider.getUriForFile(mActivity, "com.einyun.app.pms.fileprovider", new File(mSavePath, "一应未来.apk"));
            i.setDataAndType(uriForFile,mActivity.getContentResolver().getType(uriForFile));
        }
        else{
            i.setDataAndType(Uri.fromFile(new File(mSavePath, "一应未来.apk")), "application/vnd.android.package-archive");
        }
        mActivity.startActivity(i);
    }

    public interface UpdateListener {

        void back();
    }

}
