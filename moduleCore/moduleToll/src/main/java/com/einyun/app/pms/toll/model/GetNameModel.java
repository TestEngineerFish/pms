package com.einyun.app.pms.toll.model;

public class GetNameModel {

    /**
     * code : 0
     * msg : OK
     * data : {"updationDate":1582172694823,"rowVersion":0,"isDeleted":0,"enabledFlag":1,"id":"df034cc2-a5db-44bc-bafb-a744ca8307ad","sfGuid":null,"divideId":"7","wbsCode":null,"name":"zhengmin","gender":"1","clientType":1,"birthdate":null,"credentialsType":"1","credentialsNo":"320723199408090787","countryId":null,"cellphone":"15667676767","cellphone2":"15895937497","cellphone3":null,"commAddress":"南京","hometown":null,"nationality":null,"education":null,"updateChannel":"new_fee","maritalState":null,"hukouLocation":null,"qq":null,"wechat":null,"email":null,"batchId":null,"basicBatchId":null,"phone":null,"remark":null,"taxpayerIdentifyNumber":null,"companyName":null,"bankAccountName":null,"bankAccount":null,"bank":null,"sourceFrom":null,"mdmId":"69090357899427846","mdmVersion":"1"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * updationDate : 1582172694823
         * rowVersion : 0
         * isDeleted : 0
         * enabledFlag : 1
         * id : df034cc2-a5db-44bc-bafb-a744ca8307ad
         * sfGuid : null
         * divideId : 7
         * wbsCode : null
         * name : zhengmin
         * gender : 1
         * clientType : 1
         * birthdate : null
         * credentialsType : 1
         * credentialsNo : 320723199408090787
         * countryId : null
         * cellphone : 15667676767
         * cellphone2 : 15895937497
         * cellphone3 : null
         * commAddress : 南京
         * hometown : null
         * nationality : null
         * education : null
         * updateChannel : new_fee
         * maritalState : null
         * hukouLocation : null
         * qq : null
         * wechat : null
         * email : null
         * batchId : null
         * basicBatchId : null
         * phone : null
         * remark : null
         * taxpayerIdentifyNumber : null
         * companyName : null
         * bankAccountName : null
         * bankAccount : null
         * bank : null
         * sourceFrom : null
         * mdmId : 69090357899427846
         * mdmVersion : 1
         */

        private long updationDate;
        private int rowVersion;
        private int isDeleted;
        private int enabledFlag;
        private String id;
        private Object sfGuid;
        private String divideId;
        private Object wbsCode;
        private String name;
        private String gender;
        private int clientType;
        private Object birthdate;
        private String credentialsType;
        private String credentialsNo;
        private Object countryId;
        private String cellphone;
        private String cellphone2;
        private Object cellphone3;
        private String commAddress;
        private Object hometown;
        private Object nationality;
        private Object education;
        private String updateChannel;
        private Object maritalState;
        private Object hukouLocation;
        private Object qq;
        private Object wechat;
        private Object email;
        private Object batchId;
        private Object basicBatchId;
        private Object phone;
        private Object remark;
        private Object taxpayerIdentifyNumber;
        private Object companyName;
        private Object bankAccountName;
        private Object bankAccount;
        private Object bank;
        private Object sourceFrom;
        private String mdmId;
        private String mdmVersion;

        public long getUpdationDate() {
            return updationDate;
        }

        public void setUpdationDate(long updationDate) {
            this.updationDate = updationDate;
        }

        public int getRowVersion() {
            return rowVersion;
        }

        public void setRowVersion(int rowVersion) {
            this.rowVersion = rowVersion;
        }

        public int getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(int isDeleted) {
            this.isDeleted = isDeleted;
        }

        public int getEnabledFlag() {
            return enabledFlag;
        }

        public void setEnabledFlag(int enabledFlag) {
            this.enabledFlag = enabledFlag;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getSfGuid() {
            return sfGuid;
        }

        public void setSfGuid(Object sfGuid) {
            this.sfGuid = sfGuid;
        }

        public String getDivideId() {
            return divideId;
        }

        public void setDivideId(String divideId) {
            this.divideId = divideId;
        }

        public Object getWbsCode() {
            return wbsCode;
        }

        public void setWbsCode(Object wbsCode) {
            this.wbsCode = wbsCode;
        }

        public String getName() {
            return name==null?"":name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getClientType() {
            return clientType;
        }

        public void setClientType(int clientType) {
            this.clientType = clientType;
        }

        public Object getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(Object birthdate) {
            this.birthdate = birthdate;
        }

        public String getCredentialsType() {
            return credentialsType;
        }

        public void setCredentialsType(String credentialsType) {
            this.credentialsType = credentialsType;
        }

        public String getCredentialsNo() {
            return credentialsNo;
        }

        public void setCredentialsNo(String credentialsNo) {
            this.credentialsNo = credentialsNo;
        }

        public Object getCountryId() {
            return countryId;
        }

        public void setCountryId(Object countryId) {
            this.countryId = countryId;
        }

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public String getCellphone2() {
            return cellphone2;
        }

        public void setCellphone2(String cellphone2) {
            this.cellphone2 = cellphone2;
        }

        public Object getCellphone3() {
            return cellphone3;
        }

        public void setCellphone3(Object cellphone3) {
            this.cellphone3 = cellphone3;
        }

        public String getCommAddress() {
            return commAddress;
        }

        public void setCommAddress(String commAddress) {
            this.commAddress = commAddress;
        }

        public Object getHometown() {
            return hometown;
        }

        public void setHometown(Object hometown) {
            this.hometown = hometown;
        }

        public Object getNationality() {
            return nationality;
        }

        public void setNationality(Object nationality) {
            this.nationality = nationality;
        }

        public Object getEducation() {
            return education;
        }

        public void setEducation(Object education) {
            this.education = education;
        }

        public String getUpdateChannel() {
            return updateChannel;
        }

        public void setUpdateChannel(String updateChannel) {
            this.updateChannel = updateChannel;
        }

        public Object getMaritalState() {
            return maritalState;
        }

        public void setMaritalState(Object maritalState) {
            this.maritalState = maritalState;
        }

        public Object getHukouLocation() {
            return hukouLocation;
        }

        public void setHukouLocation(Object hukouLocation) {
            this.hukouLocation = hukouLocation;
        }

        public Object getQq() {
            return qq;
        }

        public void setQq(Object qq) {
            this.qq = qq;
        }

        public Object getWechat() {
            return wechat;
        }

        public void setWechat(Object wechat) {
            this.wechat = wechat;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public Object getBatchId() {
            return batchId;
        }

        public void setBatchId(Object batchId) {
            this.batchId = batchId;
        }

        public Object getBasicBatchId() {
            return basicBatchId;
        }

        public void setBasicBatchId(Object basicBatchId) {
            this.basicBatchId = basicBatchId;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public Object getTaxpayerIdentifyNumber() {
            return taxpayerIdentifyNumber;
        }

        public void setTaxpayerIdentifyNumber(Object taxpayerIdentifyNumber) {
            this.taxpayerIdentifyNumber = taxpayerIdentifyNumber;
        }

        public Object getCompanyName() {
            return companyName;
        }

        public void setCompanyName(Object companyName) {
            this.companyName = companyName;
        }

        public Object getBankAccountName() {
            return bankAccountName;
        }

        public void setBankAccountName(Object bankAccountName) {
            this.bankAccountName = bankAccountName;
        }

        public Object getBankAccount() {
            return bankAccount;
        }

        public void setBankAccount(Object bankAccount) {
            this.bankAccount = bankAccount;
        }

        public Object getBank() {
            return bank;
        }

        public void setBank(Object bank) {
            this.bank = bank;
        }

        public Object getSourceFrom() {
            return sourceFrom;
        }

        public void setSourceFrom(Object sourceFrom) {
            this.sourceFrom = sourceFrom;
        }

        public String getMdmId() {
            return mdmId;
        }

        public void setMdmId(String mdmId) {
            this.mdmId = mdmId;
        }

        public String getMdmVersion() {
            return mdmVersion;
        }

        public void setMdmVersion(String mdmVersion) {
            this.mdmVersion = mdmVersion;
        }
    }
}
