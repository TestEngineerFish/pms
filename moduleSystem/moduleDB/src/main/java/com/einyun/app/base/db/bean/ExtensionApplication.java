package com.einyun.app.base.db.bean;

public class  ExtensionApplication{
   private String id;
   private String poId;
   private int applyType;
   private String applicationDescription;
   private int applicationState;
   private String creationDate;
   private int type;
   private String createdBy;
   private String createdName;
   private String applyFiles;

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

   public String getCreatedName() {
      return createdName;
   }

   public void setCreatedName(String createdName) {
      this.createdName = createdName;
   }

   public String getApplyFiles() {
      return applyFiles;
   }

   public void setApplyFiles(String applyFiles) {
      this.applyFiles = applyFiles;
   }
}
