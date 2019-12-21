package com.einyun.app.common.model;

public class IsClosedState {
    public IsClosedState(boolean isClosed, String type) {
        this.isClosed = isClosed;
        this.type = type;
    }

    private boolean isClosed;
    private String type;

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
