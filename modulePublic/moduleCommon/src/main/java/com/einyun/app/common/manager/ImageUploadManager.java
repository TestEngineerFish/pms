package com.einyun.app.common.manager;

import android.net.Uri;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.util.FileUtil;
import com.einyun.app.common.utils.FileProviderUtil;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UploadService;
import com.einyun.app.library.upload.model.PicUrl;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ProjectName: pms
 * @Package: com.einyun.app.common.manager
 * @ClassName: ImageUploadManager
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/11/22 0022 下午 20:08
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/22 0022 下午 20:08
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ImageUploadManager {
    private final String KEY_SERVER_FILE_ID="fileid";
    private final String KEY_SERVER_FILE_ID_FIX="id";

    private final String KEY_SERVER_FILE_NAME="fileName";
    private final String KEY_SERVER_FILE_NAME_FIX="name";

    private final String KEY_SERVER_FILE_PATH="filePath";
    private final String KEY_SERVER_FILE_PATH_FIX="path";


    public ImageUploadManager(){
    }


    public void compress(List<Uri> uris,CallBack<List<PicUrl>> callBack){
        List<PicUrl> fileList=new Vector<>();
        ExecutorService fixedThreadPool= Executors.newFixedThreadPool(5);
        CountDownLatch latch=new CountDownLatch(uris.size());
        for(Uri uri:uris){
            fixedThreadPool.execute(() -> {
                String filePath= FileProviderUtil.getUploadImagePath(uri);
                FileUtil.compress(filePath, new CallBack<File>() {
                    @Override
                    public void call(File data) {
                        long fileSize=data.length();
                        PicUrl picUrl=new PicUrl();
                        picUrl.setOriginUrl(uri.toString());
                        picUrl.setCompressed(filePath);
                        fileList.add(picUrl);
                        latch.countDown();
                    }

                    @Override
                    public void onFaild(Throwable throwable) {
                        latch.countDown();
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



    public void upload(List<Uri> uris, CallBack<List<PicUrl>> callBack){
        compress(uris, new CallBack<List<PicUrl>>() {
            @Override
            public void call(List<PicUrl> compressList) {
                List<String> uploadPaths = getCompressedPaths(compressList);
                UploadService uploadService= ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_UPLOAD);
                uploadService.uploadImageList(uploadPaths, new CallBack<List<PicUrl>>() {
                    @Override
                    public void call(List<PicUrl> data) {
                        for(PicUrl picUrl:data){
                            String uri=getOriginPath(compressList,picUrl.getCompressed());
                            picUrl.setOriginUrl(uri);
                        }
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

    private String getOriginPath(List<PicUrl> uris,String key){
        for(PicUrl uri:uris){
            if(uri.getCompressed().equals(key)){
                return uri.getOriginUrl();
            }
        }
        return null;
    }


    @NotNull
    private List<String> getUploadPaths(List<PicUrl> data) {
        List<String> uploadPaths=new ArrayList<>();
        for(PicUrl picUrl:data){
            uploadPaths.add(picUrl.getUploaded());
        }
        return uploadPaths;
    }

    @NotNull
    private List<String> getCompressedPaths(List<PicUrl> data) {
        List<String> uploadPaths=new ArrayList<>();
        for(PicUrl picUrl:data){
            uploadPaths.add(picUrl.getCompressed());
        }
        return uploadPaths;
    }

    public String toJosnString(List<PicUrl> data){
        List<String> uploadPaths = getUploadPaths(data);
        JSONArray jsonArray = new JSONArray();
        try {
            for (String value : uploadPaths) {
                String jsonStr =value.replace(KEY_SERVER_FILE_ID, KEY_SERVER_FILE_ID_FIX).replace(KEY_SERVER_FILE_NAME, KEY_SERVER_FILE_NAME_FIX).replace(KEY_SERVER_FILE_PATH, KEY_SERVER_FILE_PATH_FIX);
                org.json.JSONObject json = new org.json.JSONObject(jsonStr);
                jsonArray.put(json);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray.toString();
    }
}
