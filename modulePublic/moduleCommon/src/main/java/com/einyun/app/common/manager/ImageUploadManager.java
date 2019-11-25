package com.einyun.app.common.manager;

import android.net.Uri;
import android.telecom.Call;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.util.FileUtil;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.utils.FileProviderUtil;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UploadService;
import com.einyun.app.library.upload.model.ResourceModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName: pms
 * @Package: com.einyun.app.common.manager
 * @ClassName: UploadManager
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/11/22 0022 下午 20:08
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/22 0022 下午 20:08
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class UploadManager {
    private int uploadCount;
    private Lock lock=new ReentrantLock();
    private List<Uri> uris;
    private CallBack<List<String>> callBack;
    public UploadManager(List<Uri> uris,CallBack<List<String>> callBack){
        this.uris=uris;
        this.callBack=callBack;
        lock.lock();
        uploadCount=uris.size();
        lock.unlock();
    }

    private void cutDown(){
        lock.lock();
        uploadCount--;
        lock.unlock();
    }

    private boolean isEnd(){
        return uploadCount<=0;
    }

    public void compress(List<Uri> uris,CallBack<List<String>> callBack){
        List<String> fileList=new Vector<>();
        ExecutorService fixedThreadPool= Executors.newFixedThreadPool(5);
        CountDownLatch latch=new CountDownLatch(uris.size());
        for(Uri uri:uris){
            fixedThreadPool.execute(() -> {
                String filePath= FileProviderUtil.getUploadImagePath(uri);
                FileUtil.compress(filePath, new CallBack<File>() {
                    @Override
                    public void call(File data) {
                        fileList.add(data.getAbsolutePath());
                        latch.countDown();
//                        cutDown();
//                        if(isEnd()){
//                            callBack.call(fileList);
//                        }
                    }

                    @Override
                    public void onFaild(Throwable throwable) {
                        latch.countDown();
//                        cutDown();
//                        if(isEnd()){
//                            callBack.call(fileList);
//                        }
                    }
                });

            });
        }
        fixedThreadPool.execute(() -> {
            try {
                latch.await();
                callBack.call(fileList);
            } catch (InterruptedException e) {
                callBack.onFaild(e);
                e.printStackTrace();
            }
        });
        fixedThreadPool.shutdown();
    }



    public void upload()throws Exception{
        compress(uris, new CallBack<List<String>>() {
            @Override
            public void call(List<String> data) {
                UploadService uploadService= ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_UPLOAD);
                uploadService.uploadImageList(data, new CallBack<List<String>>() {
                    @Override
                    public void call(List<String> data) {
                        callBack.call(data);
                    }

                    @Override
                    public void onFaild(Throwable throwable) {
                        callBack.onFaild(throwable);
                    }
                });
            }

            @Override
            public void onFaild(Throwable throwable) {
                callBack.onFaild(throwable);
            }
        });
    }
}
