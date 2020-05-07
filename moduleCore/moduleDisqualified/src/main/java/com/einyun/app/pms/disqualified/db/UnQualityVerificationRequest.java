package com.einyun.app.pms.disqualified.db;

public class UnQualityVerificationRequest {
    private BizDataBean bizData;
    private DoNextParam doNextParam;
    public UnQualityVerificationRequest(){
        this.bizData=new BizDataBean();
        this.doNextParam=new DoNextParam();
    }
    public BizDataBean getBizData() {
        return bizData;
    }

    public void setBizData(BizDataBean bizData) {
        this.bizData = bizData;
    }

    public DoNextParam getDoNextParamt() {
        return doNextParam;
    }

    public void setDoNextParam(DoNextParam doNextParam) {
        this.doNextParam = doNextParam;
    }

    public static class BizDataBean {

        //工单信息
        private String order_info_state="";//工单状态
        private String order_info_code="";//工单编号
        private String order_info_create_time="";//创建时间
        private String order_info_divide="";//分期
        private String order_info_check_date="";//检查日期
        private String order_info_qus_desc="";//问题描述
        private String order_info_line="";
        private String order_info_serv="";//严重程度
        private String order_info_ver_date="";//纠正截至日期
        private String order_info_checked_person="";//被检查人
        private String order_info_enclosure="";//工单信息图片

        //反馈信息
        private String fed_info_date="";//反馈日期
        private String fed_info_reason="";//原因分析
        private String fed_info_cor_action="";//纠正预防措施
        private String fed_info_del_time="";//处理时间
        private String fed_info_enclosure="";//


        //验证操作
        private String verification_situation;
        private String verification_enclosure;
        private String verification_date;
        private int is_pass;
        public String getVerification_situation() {
            return verification_situation==null?"":verification_situation;
        }

        public void setVerification_situation(String verification_situation) {
            this.verification_situation = verification_situation;
        }

        public String getVerification_enclosure() {
            return verification_enclosure==null?"":verification_enclosure;
        }

        public void setVerification_enclosure(String verification_enclosure) {
            this.verification_enclosure = verification_enclosure;
        }

        public String getVerification_date() {
            return verification_date==null?"":verification_date;
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


        public String getOrder_info_state() {
            return order_info_state;
        }

        public void setOrder_info_state(String order_info_state) {
            this.order_info_state = order_info_state;
        }

        public String getOrder_info_code() {
            return order_info_code;
        }

        public void setOrder_info_code(String order_info_code) {
            this.order_info_code = order_info_code;
        }

        public String getOrder_info_create_time() {
            return order_info_create_time;
        }

        public void setOrder_info_create_time(String order_info_create_time) {
            this.order_info_create_time = order_info_create_time;
        }

        public String getOrder_info_divide() {
            return order_info_divide;
        }

        public void setOrder_info_divide(String order_info_divide) {
            this.order_info_divide = order_info_divide;
        }

        public String getOrder_info_check_date() {
            return order_info_check_date;
        }

        public void setOrder_info_check_date(String order_info_check_date) {
            this.order_info_check_date = order_info_check_date;
        }

        public String getOrder_info_qus_desc() {
            return order_info_qus_desc;
        }

        public void setOrder_info_qus_desc(String order_info_qus_desc) {
            this.order_info_qus_desc = order_info_qus_desc;
        }

        public String getOrder_info_line() {
            return order_info_line;
        }

        public void setOrder_info_line(String order_info_line) {
            this.order_info_line = order_info_line;
        }

        public String getOrder_info_serv() {
            return order_info_serv;
        }

        public void setOrder_info_serv(String order_info_serv) {
            this.order_info_serv = order_info_serv;
        }

        public String getOrder_info_ver_date() {
            return order_info_ver_date;
        }

        public void setOrder_info_ver_date(String order_info_ver_date) {
            this.order_info_ver_date = order_info_ver_date;
        }

        public String getOrder_info_checked_person() {
            return order_info_checked_person;
        }

        public void setOrder_info_checked_person(String order_info_checked_person) {
            this.order_info_checked_person = order_info_checked_person;
        }

        public String getOrder_info_enclosure() {
            return order_info_enclosure;
        }

        public void setOrder_info_enclosure(String order_info_enclosure) {
            this.order_info_enclosure = order_info_enclosure;
        }

        public String getFed_info_date() {
            return fed_info_date;
        }

        public void setFed_info_date(String fed_info_date) {
            this.fed_info_date = fed_info_date;
        }

        public String getFed_info_reason() {
            return fed_info_reason;
        }

        public void setFed_info_reason(String fed_info_reason) {
            this.fed_info_reason = fed_info_reason;
        }

        public String getFed_info_cor_action() {
            return fed_info_cor_action;
        }

        public void setFed_info_cor_action(String fed_info_cor_action) {
            this.fed_info_cor_action = fed_info_cor_action;
        }

        public String getFed_info_del_time() {
            return fed_info_del_time;
        }

        public void setFed_info_del_time(String fed_info_del_time) {
            this.fed_info_del_time = fed_info_del_time;
        }

        public String getFed_info_enclosure() {
            return fed_info_enclosure;
        }

        public void setFed_info_enclosure(String fed_info_enclosure) {
            this.fed_info_enclosure = fed_info_enclosure;
        }


    }

    public static class DoNextParam {
        private String taskId;

        public String getTaskId() {
            return taskId==null?"":taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }
    }
}
