package com.einyun.app.pms.customerinquiries.module;

import java.util.List;

public class InquiriesTypesBean {

    /**
     * dataKey : engineering_maintain
     * line_posi_key : gcfwdyxmfzr
     * majorLine : {"name":"工程","key":"engineering_classification"}
     * line_posi_name : 工程服务单元项目负责人
     * code_str : GC
     * dataName : 工程维修类
     * id : 247002
     * repair_area : {"name":["公区","户内"],"key":["outdoor","indoor"]}
     * flow_type : {"name":["客户投诉工单","客户问询工单"],"key":["customer_complain_flow","customer_enquiry_flow"]}
     */

    private String dataKey;
    private String line_posi_key;
    private MajorLineBean majorLine;
    private String line_posi_name;
    private String code_str;
    private String dataName;
    private String id;
    private RepairAreaBean repair_area;
    private FlowTypeBean flow_type;

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getLine_posi_key() {
        return line_posi_key;
    }

    public void setLine_posi_key(String line_posi_key) {
        this.line_posi_key = line_posi_key;
    }

    public MajorLineBean getMajorLine() {
        return majorLine;
    }

    public void setMajorLine(MajorLineBean majorLine) {
        this.majorLine = majorLine;
    }

    public String getLine_posi_name() {
        return line_posi_name;
    }

    public void setLine_posi_name(String line_posi_name) {
        this.line_posi_name = line_posi_name;
    }

    public String getCode_str() {
        return code_str;
    }

    public void setCode_str(String code_str) {
        this.code_str = code_str;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RepairAreaBean getRepair_area() {
        return repair_area;
    }

    public void setRepair_area(RepairAreaBean repair_area) {
        this.repair_area = repair_area;
    }

    public FlowTypeBean getFlow_type() {
        return flow_type;
    }

    public void setFlow_type(FlowTypeBean flow_type) {
        this.flow_type = flow_type;
    }

    public static class MajorLineBean {
        /**
         * name : 工程
         * key : engineering_classification
         */

        private String name;
        private String key;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    public static class RepairAreaBean {
        private List<String> name;
        private List<String> key;

        public List<String> getName() {
            return name;
        }

        public void setName(List<String> name) {
            this.name = name;
        }

        public List<String> getKey() {
            return key;
        }

        public void setKey(List<String> key) {
            this.key = key;
        }
    }

    public static class FlowTypeBean {
        private List<String> name;
        private List<String> key;

        public List<String> getName() {
            return name;
        }

        public void setName(List<String> name) {
            this.name = name;
        }

        public List<String> getKey() {
            return key;
        }

        public void setKey(List<String> key) {
            this.key = key;
        }
    }
}
