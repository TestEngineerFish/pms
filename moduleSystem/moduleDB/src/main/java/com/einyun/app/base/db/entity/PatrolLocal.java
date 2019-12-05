package com.einyun.app.base.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.base.db.converter.StringTypeConvert;
import com.einyun.app.base.db.converter.WorkNoteTypeConvert;

import java.util.List;

@Entity(tableName = "patrol_local")
public class PatrolLocal {
    @PrimaryKey
    @NonNull
    private String taskId;
    private String note;
    @NonNull
    private String userId;

    @TypeConverters(StringTypeConvert.class)
    private List<String> images;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @TypeConverters(WorkNoteTypeConvert.class)
    private List<WorkNode> nodes;

    @NonNull
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(@NonNull String taskId) {
        this.taskId = taskId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId( @NonNull String userId) {
        this.userId = userId;
    }

    public List<WorkNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<WorkNode> nodes) {
        this.nodes = nodes;
    }
}
