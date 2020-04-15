package com.einyun.app.library.resource.workorder.net.request;

import java.util.ArrayList;
import java.util.List;

public class GetOrgRequest {
    private List<String> orgIdList=new ArrayList<>();

    public List<String> getOrgIdList() {
        return orgIdList;
    }

    public void setOrgIdList(List<String> orgIdList) {
        this.orgIdList = orgIdList;
    }
}
