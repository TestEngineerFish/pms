package com.einyun.app.base.db.bean;

import java.io.Serializable;

public class ExtensionApplication implements Serializable {
   private String id;
   private String poId;
   private int applyType;
   private int extensionDays;
   private String applicationDescription;
   private int applicationState;
   private String creationDate;
   private int type;
   private String createdBy;
   private String approveId;
   private String approveName;
   private String createName;
   private String applyFiles;
   private String auditDate;
   private String createdName;

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getPoId() {
      return poId;
   }

   public void setPoId(String poId) {
      this.poId = poId;
   }

   public int getApplyType() {
      return applyType;
   }

   public void setApplyType(int applyType) {
      this.applyType = applyType;
   }

   public int getExtensionDays() {
      return extensionDays;
   }

   public void setExtensionDays(int extensionDays) {
      this.extensionDays = extensionDays;
   }

   public String getApplicationDescription() {
      return applicationDescription;
   }

   public void setApplicationDescription(String applicationDescription) {
      this.applicationDescription = applicationDescription;
   }

   public int getApplicationState() {
      return applicationState;
   }

   public void setApplicationState(int applicationState) {
      this.applicationState = applicationState;
   }

   public String getCreationDate() {
      return creationDate;
   }

   public void setCreationDate(String creationDate) {
      this.creationDate = creationDate;
   }

   public int getType() {
      return type;
   }

   public void setType(int type) {
      this.type = type;
   }

   public String getCreatedBy() {
      return createdBy;
   }

   public void setCreatedBy(String createdBy) {
      this.createdBy = createdBy;
   }

   public String getApproveId() {
      return approveId;
   }

   public void setApproveId(String approveId) {
      this.approveId = approveId;
   }

   public String getApproveName() {
      return approveName;
   }

   public void setApproveName(String approveName) {
      this.approveName = approveName;
   }

   public String getCreateName() {
      return createName;
   }

   public void setCreateName(String createName) {
      this.createName = createName;
   }

   public String getApplyFiles() {
      return applyFiles;
   }

   public void setApplyFiles(String applyFiles) {
      this.applyFiles = applyFiles;
   }

   public String getAuditDate() {
      return auditDate;
   }

   public void setAuditDate(String auditDate) {
      this.auditDate = auditDate;
   }

   public String getCreatedName() {
      return createdName;
   }

   public void setCreatedName(String createdName) {
      this.createdName = createdName;
   }

}
