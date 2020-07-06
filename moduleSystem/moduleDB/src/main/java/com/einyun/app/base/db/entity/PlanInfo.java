package com.einyun.app.base.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "plan_info", primaryKeys = {"id", "userId"})
public class PlanInfo {
    @NonNull
    public String id;
    @NonNull
    public String userId;
    public String taskId;
}
