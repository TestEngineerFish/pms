package com.einyun.app.pms.disqualified.model;

public class DisqualifiedDetailModel {

    /**
     * data : {"unqualified_model":{"check_date":"2020-01-09","check_user_id":"63879813097586693","check_user_name":"李淑杰","checked_user_id":"63879813097586693","checked_user_name":"李淑杰","code":"HJ-BHG-2020019-0014","correction_date":"2020-01-10","created_time":"2020-01-09 20:34:23","divide_id":"63872495547056133","divide_name":"长城盛世家园一期","id_":"68544739212788740","initData":{},"line":"environmental_classification","problem_description":"Ddd","proc_inst_id_":"68544739212787716","ref_id_":"0","severity":"high_level","status":"createStep","tenant_id":"55614223698362369"}}
     * info : {"flowKey":"unqualified_key","formkey":"","nodeId":"FeedbackStep","parentFlowKey":""}
     * result : true
     */

    private DataBean data;
    private InfoBean info;
    private boolean result;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public static class DataBean {
        /**
         * unqualified_model : {"check_date":"2020-01-09","check_user_id":"63879813097586693","check_user_name":"李淑杰","checked_user_id":"63879813097586693","checked_user_name":"李淑杰","code":"HJ-BHG-2020019-0014","correction_date":"2020-01-10","created_time":"2020-01-09 20:34:23","divide_id":"63872495547056133","divide_name":"长城盛世家园一期","id_":"68544739212788740","initData":{},"line":"environmental_classification","problem_description":"Ddd","proc_inst_id_":"68544739212787716","ref_id_":"0","severity":"high_level","status":"createStep","tenant_id":"55614223698362369"}
         */

        private UnqualifiedModelBean unqualified_model;

        public UnqualifiedModelBean getUnqualified_model() {
            return unqualified_model;
        }

        public void setUnqualified_model(UnqualifiedModelBean unqualified_model) {
            this.unqualified_model = unqualified_model;
        }

        public static class UnqualifiedModelBean {
            /**
             * check_date : 2020-01-09
             * check_user_id : 63879813097586693
             * check_user_name : 李淑杰
             * checked_user_id : 63879813097586693
             * checked_user_name : 李淑杰
             * code : HJ-BHG-2020019-0014
             * correction_date : 2020-01-10
             * created_time : 2020-01-09 20:34:23
             * divide_id : 63872495547056133
             * divide_name : 长城盛世家园一期
             * id_ : 68544739212788740
             * initData : {}
             * line : environmental_classification
             * problem_description : Ddd
             * proc_inst_id_ : 68544739212787716
             * ref_id_ : 0
             * severity : high_level
             * status : createStep
             * tenant_id : 55614223698362369
             */

            private String check_date;
            private String check_user_id;
            private String check_user_name;
            private String checked_user_id;
            private String checked_user_name;
            private String code;
            private String parent_code;
            private String correction_date;
            private String created_time;
            private String divide_id;
            private String divide_name;
            private String id_;
            private InitDataBean initData;
            private String line;
            private String problem_description;
            private String proc_inst_id_;
            private String ref_id_;
            private String severity;
            private String status;
            private String tenant_id;
            private String create_enclosure;

            private String corrective_action;
            private String feedback_date;
            private String feedback_enclosure;
            private String feedback_time;
            private String reason;

            private String verifier_name;
            private String verification_date;
            private String verification_situation;
            private String verification_enclosure;
            private String completion_time;
            private String F_ORIGINAL_CODE;//原工单编号
            private String F_ORIGINAL_TYPE;//原工单类型
            private String F_ORIGINAL_PROLNSTLD;//原工单流程id
            public String getCompletion_time() {
                return completion_time==null?"":completion_time;
            }

            public void setCompletion_time(String completion_time) {
                this.completion_time = completion_time;
            }


            public String getVerifier_name() {
                return verifier_name==null?"":verifier_name;
            }

            public void setVerifier_name(String verifier_name) {
                this.verifier_name = verifier_name;
            }

            public String getVerification_date() {
                return verification_date==null?"":verification_date;
            }

            public void setVerification_date(String verification_date) {
                this.verification_date = verification_date;
            }

            public String getVerification_situation() {
                return verification_situation==null?"":verification_situation;
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




            public String getCorrective_action() {
                return corrective_action==null?"":corrective_action;
            }

            public void setCorrective_action(String corrective_action) {
                this.corrective_action = corrective_action;
            }

            public String getFeedback_date() {
                return feedback_date==null?"":feedback_date;
            }

            public void setFeedback_date(String feedback_date) {
                this.feedback_date = feedback_date;
            }

            public String getFeedback_enclosure() {
                return feedback_enclosure;
            }

            public void setFeedback_enclosure(String feedback_enclosure) {
                this.feedback_enclosure = feedback_enclosure;
            }

            public String getFeedback_time() {
                return feedback_time==null?"":feedback_time;
            }

            public void setFeedback_time(String feedback_time) {
                this.feedback_time = feedback_time;
            }

            public String getReason() {
                return reason==null?"":reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public String getCreate_enclosure() {
                return create_enclosure;
            }

            public void setCreate_enclosure(String create_enclosure) {
                this.create_enclosure = create_enclosure;
            }



            public String getCheck_date() {
                return check_date;
            }

            public void setCheck_date(String check_date) {
                this.check_date = check_date;
            }

            public String getCheck_user_id() {
                return check_user_id;
            }

            public void setCheck_user_id(String check_user_id) {
                this.check_user_id = check_user_id;
            }

            public String getCheck_user_name() {
                return check_user_name;
            }

            public void setCheck_user_name(String check_user_name) {
                this.check_user_name = check_user_name;
            }

            public String getChecked_user_id() {
                return checked_user_id;
            }

            public void setChecked_user_id(String checked_user_id) {
                this.checked_user_id = checked_user_id;
            }

            public String getChecked_user_name() {
                return checked_user_name;
            }

            public void setChecked_user_name(String checked_user_name) {
                this.checked_user_name = checked_user_name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCorrection_date() {
                return correction_date==null?"":correction_date;
            }

            public void setCorrection_date(String correction_date) {
                this.correction_date = correction_date;
            }

            public String getCreated_time() {
                return created_time;
            }

            public void setCreated_time(String created_time) {
                this.created_time = created_time;
            }

            public String getDivide_id() {
                return divide_id;
            }

            public void setDivide_id(String divide_id) {
                this.divide_id = divide_id;
            }

            public String getDivide_name() {
                return divide_name;
            }

            public void setDivide_name(String divide_name) {
                this.divide_name = divide_name;
            }

            public String getId_() {
                return id_;
            }

            public void setId_(String id_) {
                this.id_ = id_;
            }

            public InitDataBean getInitData() {
                return initData;
            }

            public void setInitData(InitDataBean initData) {
                this.initData = initData;
            }

            public String getLine() {
                return line;
            }

            public void setLine(String line) {
                this.line = line;
            }

            public String getProblem_description() {
                return problem_description;
            }

            public void setProblem_description(String problem_description) {
                this.problem_description = problem_description;
            }

            public String getProc_inst_id_() {
                return proc_inst_id_;
            }

            public void setProc_inst_id_(String proc_inst_id_) {
                this.proc_inst_id_ = proc_inst_id_;
            }

            public String getRef_id_() {
                return ref_id_;
            }

            public void setRef_id_(String ref_id_) {
                this.ref_id_ = ref_id_;
            }

            public String getSeverity() {
                return severity;
            }

            public void setSeverity(String severity) {
                this.severity = severity;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTenant_id() {
                return tenant_id;
            }

            public void setTenant_id(String tenant_id) {
                this.tenant_id = tenant_id;
            }

            public String getParent_code() {
                return parent_code==null?"":parent_code;
            }

            public void setParent_code(String parent_code) {
                this.parent_code = parent_code;
            }

            public String getF_ORIGINAL_CODE() {
                return F_ORIGINAL_CODE==null?"":F_ORIGINAL_CODE;
            }

            public void setF_ORIGINAL_CODE(String f_ORIGINAL_CODE) {
                F_ORIGINAL_CODE = f_ORIGINAL_CODE;
            }

            public String getF_ORIGINAL_TYPE() {
                return F_ORIGINAL_TYPE==null?"":F_ORIGINAL_TYPE;
            }

            public void setF_ORIGINAL_TYPE(String f_ORIGINAL_TYPE) {
                F_ORIGINAL_TYPE = f_ORIGINAL_TYPE;
            }

            public String getF_ORIGINAL_PROLNSTLD() {
                return F_ORIGINAL_PROLNSTLD==null?"":F_ORIGINAL_PROLNSTLD;
            }

            public void setF_ORIGINAL_PROLNSTLD(String f_ORIGINAL_PROLNSTLD) {
                F_ORIGINAL_PROLNSTLD = f_ORIGINAL_PROLNSTLD;
            }

            public static class InitDataBean {
            }
        }
    }

    public static class InfoBean {
        /**
         * flowKey : unqualified_key
         * formkey :
         * nodeId : FeedbackStep
         * parentFlowKey :
         */

        private String flowKey;
        private String formkey;
        private String nodeId;
        private String parentFlowKey;

        public String getFlowKey() {
            return flowKey;
        }

        public void setFlowKey(String flowKey) {
            this.flowKey = flowKey;
        }

        public String getFormkey() {
            return formkey;
        }

        public void setFormkey(String formkey) {
            this.formkey = formkey;
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getParentFlowKey() {
            return parentFlowKey;
        }

        public void setParentFlowKey(String parentFlowKey) {
            this.parentFlowKey = parentFlowKey;
        }
    }
}
