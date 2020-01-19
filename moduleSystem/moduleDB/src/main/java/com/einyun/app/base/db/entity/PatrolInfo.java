package com.einyun.app.base.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.einyun.app.base.db.bean.ExtensionApplication;
import com.einyun.app.base.db.bean.PatrolButton;
import com.einyun.app.base.db.bean.PatrolMain;
import com.einyun.app.base.db.converter.ButtonTypeConvert;
import com.einyun.app.base.db.converter.DataBeanTypeConvert;
import com.einyun.app.base.db.converter.ExtensionApplicationBeanConvert;

import java.util.List;

@Entity(tableName = "patrols_info", primaryKeys = {"id", "userId"})
public class PatrolInfo {
    private String taskId;
    @NonNull
    private String id;
    @TypeConverters(DataBeanTypeConvert.class)
    private PatrolMain data;
    @TypeConverters(ExtensionApplicationBeanConvert.class)
    private ExtensionApplication extensionApplication;
    @TypeConverters(ButtonTypeConvert.class)
    private List<PatrolButton> buttons;
    @TypeConverters(ExtensionApplicationBeanConvert.class)
    private ExtensionApplication delayExtensionApplication;
    @NonNull
    private String userId;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public PatrolMain getData() {
        return data;
    }

    public void setData(PatrolMain data) {
        this.data = data;
    }

    public ExtensionApplication getExtensionApplication() {
        return extensionApplication;
    }

    public void setExtensionApplication(ExtensionApplication extensionApplication) {
        this.extensionApplication = extensionApplication;
    }

    public List<PatrolButton> getButtons() {
        return buttons;
    }

    public void setButtons(List<PatrolButton> buttons) {
        this.buttons = buttons;
    }

    public ExtensionApplication getDelayExtensionApplication() {
        return delayExtensionApplication;
    }

    public ExtensionApplication getDelayExtensionApplicationPost(int applyType) {
        if (delayExtensionApplication == null) {
            return null;
        }
        if (applyType == delayExtensionApplication.getApplicationState()) {
            return delayExtensionApplication;
        }
        return null;
    }

    public void setDelayExtensionApplication(ExtensionApplication delayExtensionApplication) {
        this.delayExtensionApplication = delayExtensionApplication;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }
}

