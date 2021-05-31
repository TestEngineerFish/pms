package com.einyun.app.pms.plan.model;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class NodeImgs {
   public List<Uri> imgUris=new ArrayList<>();

    public List<Uri> getImgUris() {
        return imgUris;
    }

    public void setImgUris(List<Uri> imgUris) {
        this.imgUris = imgUris;
    }
}
