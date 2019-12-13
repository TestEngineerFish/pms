package com.einyun.app.base.db.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "users", indices = {@Index(value = {"user_name"}, unique = true)})
public class User implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "user_name")
    private String userName;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "update_time")
    private Date updateTime;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.updateTime = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
