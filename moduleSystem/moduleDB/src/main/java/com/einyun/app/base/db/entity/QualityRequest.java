package com.einyun.app.base.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

@Entity(tableName = "quality_request", indices = {@Index(value = {"id"}, unique = true)})
public class QualityRequest {
    @NonNull
    @PrimaryKey
    private String id;
    @NonNull
    private String taskId;
    private String verification_situation;
    private String verification_enclosure;
    private String verification_date;
    private int is_pass;
    private String feedback_enclosure;
    private String reason;
    private String corrective_action;
    private String feedback_date;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(@NonNull String taskId) {
        this.taskId = taskId;
    }

    public String getVerification_situation() {
        return verification_situation;
    }

    public void setVerification_situation(String verification_situation) {
        this.verification_situation = verification_situation;
    }

    public String getVerification_enclosure() {
        return verification_enclosure;
    }

    public void setVerification_enclosure(String verification_enclosure) {
        this.verification_enclosure = verification_enclosure;
    }

    public String getVerification_date() {
        return verification_date;
    }

    public void setVerification_date(String verification_date) {
        this.verification_date = verification_date;
    }

    public int getIs_pass() {
        return is_pass;
    }

    public void setIs_pass(int is_pass) {
        this.is_pass = is_pass;
    }

    public String getFeedback_enclosure() {
        return feedback_enclosure;
    }

    public void setFeedback_enclosure(String feedback_enclosure) {
        this.feedback_enclosure = feedback_enclosure;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCorrective_action() {
        return corrective_action;
    }

    public void setCorrective_action(String corrective_action) {
        this.corrective_action = corrective_action;
    }

    public String getFeedback_date() {
        return feedback_date;
    }

    public void setFeedback_date(String feedback_date) {
        this.feedback_date = feedback_date;
    }
}

