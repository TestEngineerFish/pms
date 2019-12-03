package com.einyun.app.base.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.einyun.app.base.db.bean.DelayExtensionApplication;
import com.einyun.app.base.db.bean.ExtensionApplication;
import com.einyun.app.base.db.bean.PatrolButton;
import com.einyun.app.base.db.bean.PatrolMain;
import com.einyun.app.base.db.converter.ButtonTypeConvert;
import com.einyun.app.base.db.converter.DataBeanTypeConvert;
import com.einyun.app.base.db.converter.DelayExtensionApplicationBeanConvert;
import com.einyun.app.base.db.converter.ExtensionApplicationBeanConvert;

import java.util.List;

@Entity(tableName = "patrols_info")
public class PatrolInfo {
    @PrimaryKey
    @NonNull
    private String taskId;
    @TypeConverters(DataBeanTypeConvert.class)
    private PatrolMain data;
    @TypeConverters(ExtensionApplicationBeanConvert.class)
    private ExtensionApplication extensionApplication;
    @TypeConverters(ButtonTypeConvert.class)
    private List<PatrolButton> buttons;
    @TypeConverters(DelayExtensionApplicationBeanConvert.class)
    private DelayExtensionApplication delayExtensionApplication;

    @NonNull
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(@NonNull String taskId) {
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

    public DelayExtensionApplication getDelayExtensionApplication() {
        return delayExtensionApplication;
    }

    public void setDelayExtensionApplication(DelayExtensionApplication delayExtensionApplication) {
        this.delayExtensionApplication = delayExtensionApplication;
    }
}

