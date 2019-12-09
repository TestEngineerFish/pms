package com.einyun.app.library.resource.workorder.net.request;

import java.util.ArrayList;
import java.util.List;

public class GetJobRequest extends PageRquest {
    public params params;
    public class params{

        private List<String> orgIdList=new ArrayList<>();

        public params(List<String> orgIdList) {
            this.orgIdList = orgIdList;
        }
    }

    public GetJobRequest.params getParams() {
        return params;
    }

    public void setParams(GetJobRequest.params params) {
        this.params = params;
    }
}
