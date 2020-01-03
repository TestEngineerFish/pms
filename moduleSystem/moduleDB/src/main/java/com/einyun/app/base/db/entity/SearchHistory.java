package com.einyun.app.base.db.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "search_history")
public class SearchHistory implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "search_content")
    private String searchContent;

    @ColumnInfo(name = "type")
    private Integer type;

    @ColumnInfo(name = "update_time")
    private Date updateTime;

    public SearchHistory(String searchContent, int type) {
        this.searchContent = searchContent;
        this.type = type;
        this.updateTime = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "SearchHistory{" +
                "id=" + id +
                ", searchContent='" + searchContent + '\'' +
                ", type=" + type +
                ", updateTime=" + updateTime +
                '}';
    }
}
