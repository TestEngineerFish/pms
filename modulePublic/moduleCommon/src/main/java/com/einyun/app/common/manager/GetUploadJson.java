package com.einyun.app.common.manager;

import com.einyun.app.common.model.PicBean;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.library.upload.model.PicUrl;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class GetUploadJson {
        private List<PicUrl> picUrls;
        private Gson gson;
        private List<PicUrlModel> picUrlModels;

        public GetUploadJson(List<PicUrl> picUrls) {
            this.picUrls = picUrls;
        }

        public Gson getGson() {
            return gson;
        }

        public List<PicUrlModel> getPicUrlModels() {
            return picUrlModels;
        }

        public GetUploadJson invoke() {
            gson = new Gson();
            picUrlModels = new ArrayList<>();
            for(PicUrl picUrl:picUrls){
                String url=picUrl.getUploaded();
                PicUrlModel picUrlModel=gson.fromJson(url, PicBean.class).toPicUrlModel();
                picUrlModels.add(picUrlModel);
            }
            return this;
        }
    }
