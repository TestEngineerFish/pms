package com.einyun.app.library.resource.workorder.net.request;

public class GetOrgnizationRequest {
    private String id="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GetOrgnizationRequest(String id) {
        this.id = id;
    }
}
