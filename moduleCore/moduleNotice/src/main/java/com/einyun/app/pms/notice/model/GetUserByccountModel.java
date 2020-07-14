package com.einyun.app.pms.notice.model;

import java.io.Serializable;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

public final class GetUserByccountModel implements Serializable {
    @Nullable
    private String id;
    @Nullable
    private String account;
    @Nullable
    private String fullname;
    @Nullable
    private String email;
    @Nullable
    private String mobile= "";
    @Nullable
    private String address;
    @Nullable
    private String sex;
    @Nullable
    private String photo;
    private int status;
    @Nullable
    private String idCard;
    @Nullable
    private String phone = "";
    @Nullable
    private String birthday;
    @Nullable
    private String entryDate;
    @Nullable
    private String education;
    @Nullable
    private String updateTime;
    @Nullable
    private String isDelete;
    private int version;
    @Nullable
    private String from;

    @Nullable
    public final String getId() {
        return this.id;
    }

    public final void setId(@Nullable String var1) {
        this.id = var1;
    }

    @Nullable
    public final String getAccount() {
        return this.account;
    }

    public final void setAccount(@Nullable String var1) {
        this.account = var1;
    }

    @Nullable
    public final String getFullname() {
        return this.fullname;
    }

    public final void setFullname(@Nullable String var1) {
        this.fullname = var1;
    }

    @Nullable
    public final String getEmail() {
        return this.email;
    }

    public final void setEmail(@Nullable String var1) {
        this.email = var1;
    }

    @Nullable
    public final String getMobile() {
        return this.mobile;
    }

    public final void setMobile(@Nullable String var1) {
        this.mobile = var1;
    }

    @Nullable
    public final String getAddress() {
        return this.address;
    }

    public final void setAddress(@Nullable String var1) {
        this.address = var1;
    }

    @Nullable
    public final String getSex() {
        return this.sex;
    }

    public final void setSex(@Nullable String var1) {
        this.sex = var1;
    }

    @Nullable
    public final String getPhoto() {
        return this.photo;
    }

    public final void setPhoto(@Nullable String var1) {
        this.photo = var1;
    }

    public final int getStatus() {
        return this.status;
    }

    public final void setStatus(int var1) {
        this.status = var1;
    }

    @Nullable
    public final String getIdCard() {
        return this.idCard;
    }

    public final void setIdCard(@Nullable String var1) {
        this.idCard = var1;
    }

    @Nullable
    public final String getPhone() {
        return this.phone;
    }

    public final void setPhone(@Nullable String var1) {
        this.phone = var1;
    }

    @Nullable
    public final String getBirthday() {
        return this.birthday;
    }

    public final void setBirthday(@Nullable String var1) {
        this.birthday = var1;
    }

    @Nullable
    public final String getEntryDate() {
        return this.entryDate;
    }

    public final void setEntryDate(@Nullable String var1) {
        this.entryDate = var1;
    }

    @Nullable
    public final String getEducation() {
        return this.education;
    }

    public final void setEducation(@Nullable String var1) {
        this.education = var1;
    }

    @Nullable
    public final String getUpdateTime() {
        return this.updateTime;
    }

    public final void setUpdateTime(@Nullable String var1) {
        this.updateTime = var1;
    }

    @Nullable
    public final String isDelete() {
        return this.isDelete;
    }

    public final void setDelete(@Nullable String var1) {
        this.isDelete = var1;
    }

    public final int getVersion() {
        return this.version;
    }

    public final void setVersion(int var1) {
        this.version = var1;
    }

    @Nullable
    public final String getFrom() {
        return this.from;
    }

    public final void setFrom(@Nullable String var1) {
        this.from = var1;
    }
}
