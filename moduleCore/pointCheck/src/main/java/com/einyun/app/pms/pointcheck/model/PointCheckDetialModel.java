package com.example.shimaostaff.pointcheck.model;

import android.text.TextUtils;

import com.example.shimaostaff.net.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck.model
 * @ClassName: PointCheckDetialModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/09 18:40
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/09 18:40
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class PointCheckDetialModel extends CheckPointModel{
    private String picUrl;
    private List<PicUrlModel> images;
    private List<String> imagePaths;
    private List<ProjectContentItemModel> contentList;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public List<PicUrlModel> getImages() {
        try {
            if(!TextUtils.isEmpty(picUrl)){
                images=new Gson().fromJson(picUrl,new TypeToken<List<PicUrlModel>>(){}.getType());
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return images;
    }

    public void setImages(List<PicUrlModel> images) {
        this.images = images;
    }

    public List<ProjectContentItemModel> getContentList() {
        return contentList;
    }

    public void setContentList(List<ProjectContentItemModel> contentList) {
        this.contentList = contentList;
    }

    public List<String> getImagePaths() {
        if(imagePaths==null){
            imagePaths=new ArrayList<>();
        }
        imagePaths.clear();
        if(images==null){
           getImages();
        }
        for(PicUrlModel model:images){
            imagePaths.add(Constants.baseUrl+"media/"+model.getPath());
        }
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }
}
