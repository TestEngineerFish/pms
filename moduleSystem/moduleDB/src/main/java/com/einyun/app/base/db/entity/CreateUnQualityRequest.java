package com.einyun.app.base.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.einyun.app.base.db.converter.BizDataBeanTypeConvert;
import com.einyun.app.base.db.converter.StartFlowParamBeanTypeConvert;

import java.io.Serializable;

@Entity(tableName = "create_unquality_request", indices = {@Index(value = {"code"}, unique = true)})
public class CreateUnQualityRequest implements Serializable {
    @PrimaryKey
    @NonNull
    private String code;
    private String original_prolnstld;

    public String getOriginal_prolnstld() {
        return original_prolnstld;
    }

    public void setOriginal_prolnstld(String original_prolnstld) {
        this.original_prolnstld = original_prolnstld;
    }

    public String getOriginal_code() {
        return original_code;
    }

    public void setOriginal_code(String original_code) {
        this.original_code = original_code;
    }

    public String getOriginal_id() {
        return original_id;
    }

    public void setOriginal_id(String original_id) {
        this.original_id = original_id;
    }

    public String getOriginal_type() {
        return original_type;
    }

    public void setOriginal_type(String original_type) {
        this.original_type = original_type;
    }

    private String original_code;
    private String original_id;
    private String original_type;
    /**
     * bizData : {"divide_id":"63872495547056133","divide_name":"长城盛世家园一期","code":"devCode_09","line":"engineering_classification","severity":"middle_level","problem_description":"哦拉拉拉拉.....","parent_id":null,"parent_code":null,"check_user_id":"1","check_user_name":"超级管理员","checked_user_id":"1","checked_user_name":"超级管理员","check_date":"2020-01-08","correction_date":"2020-01-12","create_enclosure":"{.....}"}
     * startFlowParamObject : {"flowKey":"unqualified_key"}
     */
    @TypeConverters(BizDataBeanTypeConvert.class)
    private BizDataBean bizData;
    @TypeConverters(StartFlowParamBeanTypeConvert.class)
    private StartFlowParamObjectBean startFlowParamObject;
    public CreateUnQualityRequest(){
        this.bizData=new BizDataBean();
        this.startFlowParamObject=new StartFlowParamObjectBean();
    }
    public BizDataBean getBizData() {
        return bizData;
    }

    public void setBizData(BizDataBean bizData) {
        this.bizData = bizData;
    }

    public StartFlowParamObjectBean getStartFlowParamObject() {
        return startFlowParamObject;
    }

    public void setStartFlowParamObject(StartFlowParamObjectBean startFlowParamObject) {
        this.startFlowParamObject = startFlowParamObject;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class BizDataBean implements Serializable{
        /**
         * divide_id : 63872495547056133
         * divide_name : 长城盛世家园一期
         * code : devCode_09
         * line : engineering_classification
         * severity : middle_level
         * problem_description : 哦拉拉拉拉.....
         * parent_id : null
         * parent_code : null
         * check_user_id : 1
         * check_user_name : 超级管理员
         * checked_user_id : 1
         * checked_user_name : 超级管理员
         * check_date : 2020-01-08
         * correction_date : 2020-01-12
         * create_enclosure : {.....}
         */

        private String divide_id;
        private String divide_name;
        private String code;
        private String line;
        private String severity;
        private String problem_description;
        private String parent_id;
        private String parent_code;
        private String check_user_id;
        private String check_user_name;
        private String checked_user_id;
        private String checked_user_name;
        private String check_date;
        private String correction_date;
        private String create_enclosure;
        private String original_prolnstld;
        private String original_code;
        private String original_id;
        private String original_type;

        public String getDivide_id() {
            return divide_id==null?"":divide_id;
        }

        public void setDivide_id(String divide_id) {
            this.divide_id = divide_id;
        }

        public String getDivide_name() {
            return divide_name==null?"":divide_name;
        }

        public void setDivide_name(String divide_name) {
            this.divide_name = divide_name;
        }

        public String getCode() {
            return code==null?"":code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLine() {
            return line==null?"":line;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public String getSeverity() {
            return severity==null?"":severity;
        }

        public void setSeverity(String severity) {
            this.severity = severity;
        }

        public String getProblem_description() {
            return problem_description==null?"":problem_description;
        }

        public void setProblem_description(String problem_description) {
            this.problem_description = problem_description;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getParent_code() {
            return parent_code;
        }

        public void setParent_code(String parent_code) {
            this.parent_code = parent_code;
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
            return checked_user_id==null?"":checked_user_id;
        }

        public void setChecked_user_id(String checked_user_id) {
            this.checked_user_id = checked_user_id;
        }

        public String getChecked_user_name() {
            return checked_user_name==null?"":checked_user_name;
        }

        public void setChecked_user_name(String checked_user_name) {
            this.checked_user_name = checked_user_name;
        }

        public String getCheck_date() {
            return check_date==null?"":check_date;
        }

        public void setCheck_date(String check_date) {
            this.check_date = check_date;
        }

        public String getCorrection_date() {
            return correction_date==null?"":correction_date;
        }

        public void setCorrection_date(String correction_date) {
            this.correction_date = correction_date;
        }

        public String getCreate_enclosure() {
            return create_enclosure==null?"":create_enclosure;
        }

        public void setCreate_enclosure(String create_enclosure) {
            this.create_enclosure = create_enclosure;
        }

        public String getOriginal_prolnstld() {
            return original_prolnstld;
        }

        public void setOriginal_prolnstld(String original_prolnstld) {
            this.original_prolnstld = original_prolnstld;
        }

        public String getOriginal_code() {
            return original_code;
        }

        public void setOriginal_code(String original_code) {
            this.original_code = original_code;
        }

        public String getOriginal_id() {
            return original_id;
        }

        public void setOriginal_id(String original_id) {
            this.original_id = original_id;
        }

        public String getOriginal_type() {
            return original_type;
        }

        public void setOriginal_type(String original_type) {
            this.original_type = original_type;
        }
    }

    public static class StartFlowParamObjectBean implements Serializable{
        /**
         * flowKey : unqualified_key
         */

        private String flowKey;

        public String getFlowKey() {
            return flowKey;
        }

        public void setFlowKey(String flowKey) {
            this.flowKey = flowKey;
        }
    }
}
