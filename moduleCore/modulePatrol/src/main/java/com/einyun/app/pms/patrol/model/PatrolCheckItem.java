package com.einyun.app.pms.patrol.model;

public class PatrolCheckItem {
    private int index;
    private String id;
    private String patrolItemName;
    private String patrolItemContent;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatrolItemName() {
        return patrolItemName;
    }

    public void setPatrolItemName(String patrolItemName) {
        this.patrolItemName = patrolItemName;
    }

    public String getPatrolItemContent() {
        return patrolItemContent;
    }

    public void setPatrolItemContent(String patrolItemContent) {
        this.patrolItemContent = patrolItemContent;
    }
}
