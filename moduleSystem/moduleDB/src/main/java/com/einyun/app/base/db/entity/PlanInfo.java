package com.einyun.app.base.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.einyun.app.base.db.bean.Buttons;
import com.einyun.app.base.db.converter.ButtonTypeConvert;
import com.einyun.app.base.db.converter.ButtonTypePlanConvert;
import com.einyun.app.base.db.converter.ExtensionApplicationPlanBeanConvert;
import com.einyun.app.base.db.converter.PlanDataSubJhgdgzjdbConvert;
import com.einyun.app.base.db.converter.PlanDataSubJhgdzybConvert;
import com.einyun.app.base.db.converter.PlanDataTypeConvert;
import com.einyun.app.base.db.converter.PlanInitDataConvert;
import com.einyun.app.base.db.converter.PlanInitDataJhgdgzjdbConvert;
import com.einyun.app.base.db.converter.PlanInitDataJhgdwlbConvert;
import com.einyun.app.base.db.converter.PlanInitDatajhgdzybConvert;
import com.einyun.app.base.db.converter.PlanZyjhgdConvert;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "plan_info", primaryKeys = {"id", "userId"})
public class PlanInfo {

    /**
     * buttons : null
     * data : {"zyjhgd":{"tenant_id":"55614223698362369","F_OWNER_NAME":"张溧阳","F_DEADLINE_TIME":"2020-06-15 00:02:03","F_WP_ID":"bbd02d290dfc4b1dadae29b706618b30","F_OT_STATUS":1,"F_PROJECT_ID":"ops-6cb7da69-3325-486c-af6d-43371b7f4090","proc_inst_id_":"80305289529660422","F_WG_ID":"49f4ec24-3d3d-430e-8dec-4070ab48a2e9","F_PROJECT_NAME":"ops-京杭明珠","F_ROLE_NAME":null,"F_FILES":"","F_OT_HOURS":744,"F_OPER_CONTENT":"caozuoyaoling","F_TX_CODE":"environmental_classification","F_CREATE_TIME":"2020-05-15 00:02:03","F_DIVIDE_ID":"ops-CF497009-97B0-43E7-AC35-668130E63A11","F_TIT_NAME":"环境服务单元项目负责人","F_ACT_FINISH_TIME":null,"F_WP_NAME":"扫码处理","F_PROCESS_MODE":null,"F_PROCESS_NAME":"张溧阳","F_LOCATION":"小区","F_PROCESS_ID":"66929494354558982","F_OWNER_ID":"66929494354558982","F_TX_ID":"482887","id_":"80305289529661446","F_STATUS":"2","F_ROLE_ID":null,"F_CONTENT":"","F_RES_NAME":"外围","F_WG_NAME":"扫码","F_PRINCIPAL_TYPE":null,"F_JIEDAN_KDQSJ":20,"F_RES_ID":"5fa54c3faa1c43dd9e099bae8c900088","F_HANG_STATUS":"0","F_DIVIDE_NAME":"ops-京杭明珠","F_TIT_ID":"66654654028578820","F_TX_NAME":"环境","F_SEND_REMARK":null,"F_ORDER_NO":"community_JHMZ-HJ-JH-202005150066","F_ROLE_CODE":null,"F_EXT_STATUS":0,"sub_jhgdgzjdb":[{"tenant_id":"55614223698362369","ref_id_":"80305289529661446","F_WK_CONTENT":"工作事项01","F_WK_ID":"1","id_":"80305289529692166","F_WK_NODE":"工作节点01","F_WK_RESULT":null,"proc_inst_id_":"80305289529660422"},{"tenant_id":"55614223698362369","ref_id_":"80305289529661446","F_WK_CONTENT":"工作事项02","F_WK_ID":"2","id_":"80305289529693190","F_WK_NODE":"工作节点02","F_WK_RESULT":null,"proc_inst_id_":"80305289529660422"},{"tenant_id":"55614223698362369","ref_id_":"80305289529661446","F_WK_CONTENT":"工作事项03","F_WK_ID":"3","id_":"80305289529694214","F_WK_NODE":"工作节点03","F_WK_RESULT":null,"proc_inst_id_":"80305289529660422"}],"sub_jhgdwlb":[],"sub_jhgdzyb":[{"F_RES_CODE":"community_JHMZ-BJ-WW-WWZQBJ-0001","tenant_id":"55614223698362369","id_":"80305289529695238","F_REMARK":"欧克","is_forced":1,"F_WG_NAME":"扫码","F_RES_NAME":"外围周期保洁","F_RES_COUNT":null,"proc_inst_id_":"80305289529660422","scan_result":null,"ref_id_":"80305289529661446","F_SP_TYPE":null,"F_PG_ID":"49f4ec24-3d3d-430e-8dec-4070ab48a2e9","F_RES_LOCATION":"小区","F_RES_TYPENAME":"外围","F_RES_TYPE":"WW"}],"initData":{"jhgdgzjdb":{"F_WK_CONTENT":"","F_WK_ID":"","F_WK_NODE":"","F_WK_RESULT":""},"jhgdwlb":{"F_MAT_REMARK":"","F_MAT_NAME":"","F_MAT_UNIT_PRICE":"","F_MAT_COUNT":"","F_MAT_UNIT":""},"jhgdzyb":{"F_RES_CODE":"","F_SP_TYPE":"","F_PG_ID":"","F_REMARK":"","is_forced":"","F_RES_LOCATION":"","F_WG_NAME":"","F_RES_NAME":"","F_RES_COUNT":"","F_RES_TYPENAME":"","F_RES_TYPE":"","scan_result":""}}}}
     * extensionApplication : [{"id":"85132517090264070","poId":"80305289529661446","applyType":2,"extensionDays":null,"applicationDescription":"331米","applicationState":3,"creationDate":"2020-07-06 15:50:36","type":1,"createdBy":"66929494354558982","approveId":"66929494354558982","approveName":"张溧阳","createdName":"张溧阳","applyFiles":"[{\"success\":true,\"fileId\":\"85132516016522246\",\"name\":\"1594021838206\",\"path\":\"55614223698362369\\/zly\\/2020\\/7\\/85132516016522246.\",\"size\":3091232}]","auditDate":"2020-07-06 15:51:01"},{"id":"85132476288073734","poId":"80305289529661446","applyType":1,"extensionDays":1,"applicationDescription":"你周五","applicationState":1,"creationDate":"2020-07-06 15:49:58","type":1,"createdBy":"66929494354558982","approveId":null,"approveName":null,"createdName":"张溧阳","applyFiles":"[{\"success\":true,\"fileId\":\"85132475214331910\",\"name\":\"1594021800781\",\"path\":\"55614223698362369\\/zly\\/2020\\/7\\/85132475214331910.\",\"size\":361753}]","auditDate":null}]
     */
    @NonNull
    public   String id;
    @NonNull
   public String userId;
  public    String taskId;
    @TypeConverters(ButtonTypePlanConvert.class)
    public List<Buttons> buttons;
    @TypeConverters(PlanDataTypeConvert.class)
    public Data data;

    public List<Buttons> getButtons() {
        return buttons;
    }

    public void setButtons(List<Buttons> buttons) {
        this.buttons = buttons;
    }

    @TypeConverters(ExtensionApplicationPlanBeanConvert.class)
    public List<ExtensionApplication> extensionApplication;

    public ExtensionApplication getExt(int applyType) {
        if (extensionApplication == null || extensionApplication.size() == 0) {
            return null;
        }

        for (ExtensionApplication application : extensionApplication) {
            if (applyType == application.applyType) {
                return application;
            }
        }
        return null;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<ExtensionApplication> getExtensionApplication() {
        return extensionApplication;
    }

    public void setExtensionApplication(List<ExtensionApplication> extensionApplication) {
        this.extensionApplication = extensionApplication;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public static class Data {
        /**
         * zyjhgd : {"tenant_id":"55614223698362369","F_OWNER_NAME":"张溧阳","F_DEADLINE_TIME":"2020-06-15 00:02:03","F_WP_ID":"bbd02d290dfc4b1dadae29b706618b30","F_OT_STATUS":1,"F_PROJECT_ID":"ops-6cb7da69-3325-486c-af6d-43371b7f4090","proc_inst_id_":"80305289529660422","F_WG_ID":"49f4ec24-3d3d-430e-8dec-4070ab48a2e9","F_PROJECT_NAME":"ops-京杭明珠","F_ROLE_NAME":null,"F_FILES":"","F_OT_HOURS":744,"F_OPER_CONTENT":"caozuoyaoling","F_TX_CODE":"environmental_classification","F_CREATE_TIME":"2020-05-15 00:02:03","F_DIVIDE_ID":"ops-CF497009-97B0-43E7-AC35-668130E63A11","F_TIT_NAME":"环境服务单元项目负责人","F_ACT_FINISH_TIME":null,"F_WP_NAME":"扫码处理","F_PROCESS_MODE":null,"F_PROCESS_NAME":"张溧阳","F_LOCATION":"小区","F_PROCESS_ID":"66929494354558982","F_OWNER_ID":"66929494354558982","F_TX_ID":"482887","id_":"80305289529661446","F_STATUS":"2","F_ROLE_ID":null,"F_CONTENT":"","F_RES_NAME":"外围","F_WG_NAME":"扫码","F_PRINCIPAL_TYPE":null,"F_JIEDAN_KDQSJ":20,"F_RES_ID":"5fa54c3faa1c43dd9e099bae8c900088","F_HANG_STATUS":"0","F_DIVIDE_NAME":"ops-京杭明珠","F_TIT_ID":"66654654028578820","F_TX_NAME":"环境","F_SEND_REMARK":null,"F_ORDER_NO":"community_JHMZ-HJ-JH-202005150066","F_ROLE_CODE":null,"F_EXT_STATUS":0,"sub_jhgdgzjdb":[{"tenant_id":"55614223698362369","ref_id_":"80305289529661446","F_WK_CONTENT":"工作事项01","F_WK_ID":"1","id_":"80305289529692166","F_WK_NODE":"工作节点01","F_WK_RESULT":null,"proc_inst_id_":"80305289529660422"},{"tenant_id":"55614223698362369","ref_id_":"80305289529661446","F_WK_CONTENT":"工作事项02","F_WK_ID":"2","id_":"80305289529693190","F_WK_NODE":"工作节点02","F_WK_RESULT":null,"proc_inst_id_":"80305289529660422"},{"tenant_id":"55614223698362369","ref_id_":"80305289529661446","F_WK_CONTENT":"工作事项03","F_WK_ID":"3","id_":"80305289529694214","F_WK_NODE":"工作节点03","F_WK_RESULT":null,"proc_inst_id_":"80305289529660422"}],"sub_jhgdwlb":[],"sub_jhgdzyb":[{"F_RES_CODE":"community_JHMZ-BJ-WW-WWZQBJ-0001","tenant_id":"55614223698362369","id_":"80305289529695238","F_REMARK":"欧克","is_forced":1,"F_WG_NAME":"扫码","F_RES_NAME":"外围周期保洁","F_RES_COUNT":null,"proc_inst_id_":"80305289529660422","scan_result":null,"ref_id_":"80305289529661446","F_SP_TYPE":null,"F_PG_ID":"49f4ec24-3d3d-430e-8dec-4070ab48a2e9","F_RES_LOCATION":"小区","F_RES_TYPENAME":"外围","F_RES_TYPE":"WW"}],"initData":{"jhgdgzjdb":{"F_WK_CONTENT":"","F_WK_ID":"","F_WK_NODE":"","F_WK_RESULT":""},"jhgdwlb":{"F_MAT_REMARK":"","F_MAT_NAME":"","F_MAT_UNIT_PRICE":"","F_MAT_COUNT":"","F_MAT_UNIT":""},"jhgdzyb":{"F_RES_CODE":"","F_SP_TYPE":"","F_PG_ID":"","F_REMARK":"","is_forced":"","F_RES_LOCATION":"","F_WG_NAME":"","F_RES_NAME":"","F_RES_COUNT":"","F_RES_TYPENAME":"","F_RES_TYPE":"","scan_result":""}}}
         */
        @TypeConverters(PlanZyjhgdConvert.class)
        public Zyjhgd zyjhgd;

        public Zyjhgd getZyjhgd() {
            return zyjhgd;
        }

        public void setZyjhgd(Zyjhgd zyjhgd) {
            this.zyjhgd = zyjhgd;
        }

        public static class Zyjhgd {
            /**
             * tenant_id : 55614223698362369
             * F_OWNER_NAME : 张溧阳
             * F_DEADLINE_TIME : 2020-06-15 00:02:03
             * F_WP_ID : bbd02d290dfc4b1dadae29b706618b30
             * F_OT_STATUS : 1
             * F_PROJECT_ID : ops-6cb7da69-3325-486c-af6d-43371b7f4090
             * proc_inst_id_ : 80305289529660422
             * F_WG_ID : 49f4ec24-3d3d-430e-8dec-4070ab48a2e9
             * F_PROJECT_NAME : ops-京杭明珠
             * F_ROLE_NAME : null
             * F_FILES :
             * F_OT_HOURS : 744
             * F_OPER_CONTENT : caozuoyaoling
             * F_TX_CODE : environmental_classification
             * F_CREATE_TIME : 2020-05-15 00:02:03
             * F_DIVIDE_ID : ops-CF497009-97B0-43E7-AC35-668130E63A11
             * F_TIT_NAME : 环境服务单元项目负责人
             * F_ACT_FINISH_TIME : null
             * F_WP_NAME : 扫码处理
             * F_PROCESS_MODE : null
             * F_PROCESS_NAME : 张溧阳
             * F_LOCATION : 小区
             * F_PROCESS_ID : 66929494354558982
             * F_OWNER_ID : 66929494354558982
             * F_TX_ID : 482887
             * id_ : 80305289529661446
             * F_STATUS : 2
             * F_ROLE_ID : null
             * F_CONTENT :
             * F_RES_NAME : 外围
             * F_WG_NAME : 扫码
             * F_PRINCIPAL_TYPE : null
             * F_JIEDAN_KDQSJ : 20
             * F_RES_ID : 5fa54c3faa1c43dd9e099bae8c900088
             * F_HANG_STATUS : 0
             * F_DIVIDE_NAME : ops-京杭明珠
             * F_TIT_ID : 66654654028578820
             * F_TX_NAME : 环境
             * F_SEND_REMARK : null
             * F_ORDER_NO : community_JHMZ-HJ-JH-202005150066
             * F_ROLE_CODE : null
             * F_EXT_STATUS : 0
             * sub_jhgdgzjdb : [{"tenant_id":"55614223698362369","ref_id_":"80305289529661446","F_WK_CONTENT":"工作事项01","F_WK_ID":"1","id_":"80305289529692166","F_WK_NODE":"工作节点01","F_WK_RESULT":null,"proc_inst_id_":"80305289529660422"},{"tenant_id":"55614223698362369","ref_id_":"80305289529661446","F_WK_CONTENT":"工作事项02","F_WK_ID":"2","id_":"80305289529693190","F_WK_NODE":"工作节点02","F_WK_RESULT":null,"proc_inst_id_":"80305289529660422"},{"tenant_id":"55614223698362369","ref_id_":"80305289529661446","F_WK_CONTENT":"工作事项03","F_WK_ID":"3","id_":"80305289529694214","F_WK_NODE":"工作节点03","F_WK_RESULT":null,"proc_inst_id_":"80305289529660422"}]
             * sub_jhgdwlb : []
             * sub_jhgdzyb : [{"F_RES_CODE":"community_JHMZ-BJ-WW-WWZQBJ-0001","tenant_id":"55614223698362369","id_":"80305289529695238","F_REMARK":"欧克","is_forced":1,"F_WG_NAME":"扫码","F_RES_NAME":"外围周期保洁","F_RES_COUNT":null,"proc_inst_id_":"80305289529660422","scan_result":null,"ref_id_":"80305289529661446","F_SP_TYPE":null,"F_PG_ID":"49f4ec24-3d3d-430e-8dec-4070ab48a2e9","F_RES_LOCATION":"小区","F_RES_TYPENAME":"外围","F_RES_TYPE":"WW"}]
             * initData : {"jhgdgzjdb":{"F_WK_CONTENT":"","F_WK_ID":"","F_WK_NODE":"","F_WK_RESULT":""},"jhgdwlb":{"F_MAT_REMARK":"","F_MAT_NAME":"","F_MAT_UNIT_PRICE":"","F_MAT_COUNT":"","F_MAT_UNIT":""},"jhgdzyb":{"F_RES_CODE":"","F_SP_TYPE":"","F_PG_ID":"","F_REMARK":"","is_forced":"","F_RES_LOCATION":"","F_WG_NAME":"","F_RES_NAME":"","F_RES_COUNT":"","F_RES_TYPENAME":"","F_RES_TYPE":"","scan_result":""}}
             */

            private String tenant_id;
            private String F_OWNER_NAME;
            private String F_DEADLINE_TIME;
            private String F_WP_ID;
            private int F_OT_STATUS;
            private String F_PROJECT_ID;
            private String proc_inst_id_;
            private String F_WG_ID;
            private String F_PROJECT_NAME;
            private Object F_ROLE_NAME;
            private String F_FILES;
            private int F_OT_HOURS;
            private String F_OPER_CONTENT;
            private String F_TX_CODE;
            private String F_CREATE_TIME;
            private String F_DIVIDE_ID;
            private String F_TIT_NAME;
            private String F_ACT_FINISH_TIME;
            private String F_WP_NAME;
            private Object F_PROCESS_MODE;
            private String F_PROCESS_NAME;
            private String F_LOCATION;
            private String F_PROCESS_ID;
            private String F_OWNER_ID;
            private String F_TX_ID;
            private String id_;
            private String F_STATUS;
            private Object F_ROLE_ID;
            private String F_CONTENT;
            private String F_RES_NAME;
            private String F_WG_NAME;
            private Object F_PRINCIPAL_TYPE;
            private int F_JIEDAN_KDQSJ;
            private String F_RES_ID;
            private String F_HANG_STATUS;
            private String F_DIVIDE_NAME;
            private String F_TIT_ID;
            private String F_TX_NAME;
            private Object F_SEND_REMARK;
            private String F_ORDER_NO;
            private Object F_ROLE_CODE;
            private int F_EXT_STATUS;
            @TypeConverters(PlanInitDataConvert.class)
            private InitData initData;
            @TypeConverters(PlanDataSubJhgdgzjdbConvert.class)
            private List<Sub_jhgdgzjdb> sub_jhgdgzjdb;
            private List<?> sub_jhgdwlb;
            @TypeConverters(PlanDataSubJhgdzybConvert.class)
            private List<Sub_jhgdzyb> sub_jhgdzyb;

            public String getTenant_id() {
                return tenant_id;
            }

            public void setTenant_id(String tenant_id) {
                this.tenant_id = tenant_id;
            }

            public String getF_OWNER_NAME() {
                return F_OWNER_NAME;
            }

            public void setF_OWNER_NAME(String F_OWNER_NAME) {
                this.F_OWNER_NAME = F_OWNER_NAME;
            }

            public String getF_DEADLINE_TIME() {
                return F_DEADLINE_TIME;
            }

            public void setF_DEADLINE_TIME(String F_DEADLINE_TIME) {
                this.F_DEADLINE_TIME = F_DEADLINE_TIME;
            }

            public String getF_WP_ID() {
                return F_WP_ID;
            }

            public void setF_WP_ID(String F_WP_ID) {
                this.F_WP_ID = F_WP_ID;
            }

            public int getF_OT_STATUS() {
                return F_OT_STATUS;
            }

            public void setF_OT_STATUS(int F_OT_STATUS) {
                this.F_OT_STATUS = F_OT_STATUS;
            }

            public String getF_PROJECT_ID() {
                return F_PROJECT_ID;
            }

            public void setF_PROJECT_ID(String F_PROJECT_ID) {
                this.F_PROJECT_ID = F_PROJECT_ID;
            }

            public String getProc_inst_id_() {
                return proc_inst_id_;
            }

            public void setProc_inst_id_(String proc_inst_id_) {
                this.proc_inst_id_ = proc_inst_id_;
            }

            public String getF_WG_ID() {
                return F_WG_ID;
            }

            public void setF_WG_ID(String F_WG_ID) {
                this.F_WG_ID = F_WG_ID;
            }

            public String getF_PROJECT_NAME() {
                return F_PROJECT_NAME;
            }

            public void setF_PROJECT_NAME(String F_PROJECT_NAME) {
                this.F_PROJECT_NAME = F_PROJECT_NAME;
            }

            public Object getF_ROLE_NAME() {
                return F_ROLE_NAME;
            }

            public void setF_ROLE_NAME(Object F_ROLE_NAME) {
                this.F_ROLE_NAME = F_ROLE_NAME;
            }

            public String getF_FILES() {
                return F_FILES;
            }

            public void setF_FILES(String F_FILES) {
                this.F_FILES = F_FILES;
            }

            public int getF_OT_HOURS() {
                return F_OT_HOURS;
            }

            public void setF_OT_HOURS(int F_OT_HOURS) {
                this.F_OT_HOURS = F_OT_HOURS;
            }

            public String getF_OPER_CONTENT() {
                return F_OPER_CONTENT;
            }

            public void setF_OPER_CONTENT(String F_OPER_CONTENT) {
                this.F_OPER_CONTENT = F_OPER_CONTENT;
            }

            public String getF_TX_CODE() {
                return F_TX_CODE;
            }

            public void setF_TX_CODE(String F_TX_CODE) {
                this.F_TX_CODE = F_TX_CODE;
            }

            public String getF_CREATE_TIME() {
                return F_CREATE_TIME;
            }

            public void setF_CREATE_TIME(String F_CREATE_TIME) {
                this.F_CREATE_TIME = F_CREATE_TIME;
            }

            public String getF_DIVIDE_ID() {
                return F_DIVIDE_ID;
            }

            public void setF_DIVIDE_ID(String F_DIVIDE_ID) {
                this.F_DIVIDE_ID = F_DIVIDE_ID;
            }

            public String getF_TIT_NAME() {
                return F_TIT_NAME;
            }

            public void setF_TIT_NAME(String F_TIT_NAME) {
                this.F_TIT_NAME = F_TIT_NAME;
            }

            public String getF_ACT_FINISH_TIME() {
                return F_ACT_FINISH_TIME;
            }

            public void setF_ACT_FINISH_TIME(String F_ACT_FINISH_TIME) {
                this.F_ACT_FINISH_TIME = F_ACT_FINISH_TIME;
            }

            public String getF_WP_NAME() {
                return F_WP_NAME;
            }

            public void setF_WP_NAME(String F_WP_NAME) {
                this.F_WP_NAME = F_WP_NAME;
            }

            public Object getF_PROCESS_MODE() {
                return F_PROCESS_MODE;
            }

            public void setF_PROCESS_MODE(Object F_PROCESS_MODE) {
                this.F_PROCESS_MODE = F_PROCESS_MODE;
            }

            public String getF_PROCESS_NAME() {
                return F_PROCESS_NAME;
            }

            public void setF_PROCESS_NAME(String F_PROCESS_NAME) {
                this.F_PROCESS_NAME = F_PROCESS_NAME;
            }

            public String getF_LOCATION() {
                return F_LOCATION;
            }

            public void setF_LOCATION(String F_LOCATION) {
                this.F_LOCATION = F_LOCATION;
            }

            public String getF_PROCESS_ID() {
                return F_PROCESS_ID;
            }

            public void setF_PROCESS_ID(String F_PROCESS_ID) {
                this.F_PROCESS_ID = F_PROCESS_ID;
            }

            public String getF_OWNER_ID() {
                return F_OWNER_ID;
            }

            public void setF_OWNER_ID(String F_OWNER_ID) {
                this.F_OWNER_ID = F_OWNER_ID;
            }

            public String getF_TX_ID() {
                return F_TX_ID;
            }

            public void setF_TX_ID(String F_TX_ID) {
                this.F_TX_ID = F_TX_ID;
            }

            public String getId_() {
                return id_;
            }

            public void setId_(String id_) {
                this.id_ = id_;
            }

            public String getF_STATUS() {
                return F_STATUS;
            }

            public void setF_STATUS(String F_STATUS) {
                this.F_STATUS = F_STATUS;
            }

            public Object getF_ROLE_ID() {
                return F_ROLE_ID;
            }

            public void setF_ROLE_ID(Object F_ROLE_ID) {
                this.F_ROLE_ID = F_ROLE_ID;
            }

            public String getF_CONTENT() {
                return F_CONTENT;
            }

            public void setF_CONTENT(String F_CONTENT) {
                this.F_CONTENT = F_CONTENT;
            }

            public String getF_RES_NAME() {
                return F_RES_NAME;
            }

            public void setF_RES_NAME(String F_RES_NAME) {
                this.F_RES_NAME = F_RES_NAME;
            }

            public String getF_WG_NAME() {
                return F_WG_NAME;
            }

            public void setF_WG_NAME(String F_WG_NAME) {
                this.F_WG_NAME = F_WG_NAME;
            }

            public Object getF_PRINCIPAL_TYPE() {
                return F_PRINCIPAL_TYPE;
            }

            public void setF_PRINCIPAL_TYPE(Object F_PRINCIPAL_TYPE) {
                this.F_PRINCIPAL_TYPE = F_PRINCIPAL_TYPE;
            }

            public int getF_JIEDAN_KDQSJ() {
                return F_JIEDAN_KDQSJ;
            }

            public void setF_JIEDAN_KDQSJ(int F_JIEDAN_KDQSJ) {
                this.F_JIEDAN_KDQSJ = F_JIEDAN_KDQSJ;
            }

            public String getF_RES_ID() {
                return F_RES_ID;
            }

            public void setF_RES_ID(String F_RES_ID) {
                this.F_RES_ID = F_RES_ID;
            }

            public String getF_HANG_STATUS() {
                return F_HANG_STATUS;
            }

            public void setF_HANG_STATUS(String F_HANG_STATUS) {
                this.F_HANG_STATUS = F_HANG_STATUS;
            }

            public String getF_DIVIDE_NAME() {
                return F_DIVIDE_NAME;
            }

            public void setF_DIVIDE_NAME(String F_DIVIDE_NAME) {
                this.F_DIVIDE_NAME = F_DIVIDE_NAME;
            }

            public String getF_TIT_ID() {
                return F_TIT_ID;
            }

            public void setF_TIT_ID(String F_TIT_ID) {
                this.F_TIT_ID = F_TIT_ID;
            }

            public String getF_TX_NAME() {
                return F_TX_NAME;
            }

            public void setF_TX_NAME(String F_TX_NAME) {
                this.F_TX_NAME = F_TX_NAME;
            }

            public Object getF_SEND_REMARK() {
                return F_SEND_REMARK;
            }

            public void setF_SEND_REMARK(Object F_SEND_REMARK) {
                this.F_SEND_REMARK = F_SEND_REMARK;
            }

            public String getF_ORDER_NO() {
                return F_ORDER_NO;
            }

            public void setF_ORDER_NO(String F_ORDER_NO) {
                this.F_ORDER_NO = F_ORDER_NO;
            }

            public Object getF_ROLE_CODE() {
                return F_ROLE_CODE;
            }

            public void setF_ROLE_CODE(Object F_ROLE_CODE) {
                this.F_ROLE_CODE = F_ROLE_CODE;
            }

            public int getF_EXT_STATUS() {
                return F_EXT_STATUS;
            }

            public void setF_EXT_STATUS(int F_EXT_STATUS) {
                this.F_EXT_STATUS = F_EXT_STATUS;
            }

            public InitData getInitData() {
                return initData;
            }

            public void setInitData(InitData initData) {
                this.initData = initData;
            }

            public List<Sub_jhgdgzjdb> getSub_jhgdgzjdb() {
                return sub_jhgdgzjdb;
            }

            public void setSub_jhgdgzjdb(List<Sub_jhgdgzjdb> sub_jhgdgzjdb) {
                this.sub_jhgdgzjdb = sub_jhgdgzjdb;
            }

            public List<?> getSub_jhgdwlb() {
                return sub_jhgdwlb;
            }

            public void setSub_jhgdwlb(List<?> sub_jhgdwlb) {
                this.sub_jhgdwlb = sub_jhgdwlb;
            }

            public List<Sub_jhgdzyb> getSub_jhgdzyb() {
                return sub_jhgdzyb;
            }

            public void setSub_jhgdzyb(List<Sub_jhgdzyb> sub_jhgdzyb) {
                this.sub_jhgdzyb = sub_jhgdzyb;
            }

            public static class InitData {
                /**
                 * jhgdgzjdb : {"F_WK_CONTENT":"","F_WK_ID":"","F_WK_NODE":"","F_WK_RESULT":""}
                 * jhgdwlb : {"F_MAT_REMARK":"","F_MAT_NAME":"","F_MAT_UNIT_PRICE":"","F_MAT_COUNT":"","F_MAT_UNIT":""}
                 * jhgdzyb : {"F_RES_CODE":"","F_SP_TYPE":"","F_PG_ID":"","F_REMARK":"","is_forced":"","F_RES_LOCATION":"","F_WG_NAME":"","F_RES_NAME":"","F_RES_COUNT":"","F_RES_TYPENAME":"","F_RES_TYPE":"","scan_result":""}
                 */
                @TypeConverters(PlanInitDataJhgdgzjdbConvert.class)
                private Jhgdgzjdb jhgdgzjdb;
                @TypeConverters(PlanInitDataJhgdwlbConvert.class)
                private Jhgdwlb jhgdwlb;
                @TypeConverters(PlanInitDatajhgdzybConvert.class)
                private Jhgdzyb jhgdzyb;

                public Jhgdgzjdb getJhgdgzjdb() {
                    return jhgdgzjdb;
                }

                public void setJhgdgzjdb(Jhgdgzjdb jhgdgzjdb) {
                    this.jhgdgzjdb = jhgdgzjdb;
                }

                public Jhgdwlb getJhgdwlb() {
                    return jhgdwlb;
                }

                public void setJhgdwlb(Jhgdwlb jhgdwlb) {
                    this.jhgdwlb = jhgdwlb;
                }

                public Jhgdzyb getJhgdzyb() {
                    return jhgdzyb;
                }

                public void setJhgdzyb(Jhgdzyb jhgdzyb) {
                    this.jhgdzyb = jhgdzyb;
                }

                public static class Jhgdgzjdb {
                    /**
                     * F_WK_CONTENT :
                     * F_WK_ID :
                     * F_WK_NODE :
                     * F_WK_RESULT :
                     */

                    private String F_WK_CONTENT;
                    private String F_WK_ID;
                    private String F_WK_NODE;
                    private String F_WK_RESULT;

                    public String getF_WK_CONTENT() {
                        return F_WK_CONTENT;
                    }

                    public void setF_WK_CONTENT(String F_WK_CONTENT) {
                        this.F_WK_CONTENT = F_WK_CONTENT;
                    }

                    public String getF_WK_ID() {
                        return F_WK_ID;
                    }

                    public void setF_WK_ID(String F_WK_ID) {
                        this.F_WK_ID = F_WK_ID;
                    }

                    public String getF_WK_NODE() {
                        return F_WK_NODE;
                    }

                    public void setF_WK_NODE(String F_WK_NODE) {
                        this.F_WK_NODE = F_WK_NODE;
                    }

                    public String getF_WK_RESULT() {
                        return F_WK_RESULT;
                    }

                    public void setF_WK_RESULT(String F_WK_RESULT) {
                        this.F_WK_RESULT = F_WK_RESULT;
                    }
                }

                public static class Jhgdwlb {
                    /**
                     * F_MAT_REMARK :
                     * F_MAT_NAME :
                     * F_MAT_UNIT_PRICE :
                     * F_MAT_COUNT :
                     * F_MAT_UNIT :
                     */

                    private String F_MAT_REMARK;
                    private String F_MAT_NAME;
                    private String F_MAT_UNIT_PRICE;
                    private String F_MAT_COUNT;
                    private String F_MAT_UNIT;

                    public String getF_MAT_REMARK() {
                        return F_MAT_REMARK;
                    }

                    public void setF_MAT_REMARK(String F_MAT_REMARK) {
                        this.F_MAT_REMARK = F_MAT_REMARK;
                    }

                    public String getF_MAT_NAME() {
                        return F_MAT_NAME;
                    }

                    public void setF_MAT_NAME(String F_MAT_NAME) {
                        this.F_MAT_NAME = F_MAT_NAME;
                    }

                    public String getF_MAT_UNIT_PRICE() {
                        return F_MAT_UNIT_PRICE;
                    }

                    public void setF_MAT_UNIT_PRICE(String F_MAT_UNIT_PRICE) {
                        this.F_MAT_UNIT_PRICE = F_MAT_UNIT_PRICE;
                    }

                    public String getF_MAT_COUNT() {
                        return F_MAT_COUNT;
                    }

                    public void setF_MAT_COUNT(String F_MAT_COUNT) {
                        this.F_MAT_COUNT = F_MAT_COUNT;
                    }

                    public String getF_MAT_UNIT() {
                        return F_MAT_UNIT;
                    }

                    public void setF_MAT_UNIT(String F_MAT_UNIT) {
                        this.F_MAT_UNIT = F_MAT_UNIT;
                    }
                }

                public static class Jhgdzyb {
                    /**
                     * F_RES_CODE :
                     * F_SP_TYPE :
                     * F_PG_ID :
                     * F_REMARK :
                     * is_forced :
                     * F_RES_LOCATION :
                     * F_WG_NAME :
                     * F_RES_NAME :
                     * F_RES_COUNT :
                     * F_RES_TYPENAME :
                     * F_RES_TYPE :
                     * scan_result :
                     */

                    private String F_RES_CODE;
                    private String F_SP_TYPE;
                    private String F_PG_ID;
                    private String F_REMARK;
                    private String is_forced;
                    private String F_RES_LOCATION;
                    private String F_WG_NAME;
                    private String F_RES_NAME;
                    private String F_RES_COUNT;
                    private String F_RES_TYPENAME;
                    private String F_RES_TYPE;
                    private String scan_result;

                    public String getF_RES_CODE() {
                        return F_RES_CODE;
                    }

                    public void setF_RES_CODE(String F_RES_CODE) {
                        this.F_RES_CODE = F_RES_CODE;
                    }

                    public String getF_SP_TYPE() {
                        return F_SP_TYPE;
                    }

                    public void setF_SP_TYPE(String F_SP_TYPE) {
                        this.F_SP_TYPE = F_SP_TYPE;
                    }

                    public String getF_PG_ID() {
                        return F_PG_ID;
                    }

                    public void setF_PG_ID(String F_PG_ID) {
                        this.F_PG_ID = F_PG_ID;
                    }

                    public String getF_REMARK() {
                        return F_REMARK;
                    }

                    public void setF_REMARK(String F_REMARK) {
                        this.F_REMARK = F_REMARK;
                    }

                    public String getIs_forced() {
                        return is_forced;
                    }

                    public void setIs_forced(String is_forced) {
                        this.is_forced = is_forced;
                    }

                    public String getF_RES_LOCATION() {
                        return F_RES_LOCATION;
                    }

                    public void setF_RES_LOCATION(String F_RES_LOCATION) {
                        this.F_RES_LOCATION = F_RES_LOCATION;
                    }

                    public String getF_WG_NAME() {
                        return F_WG_NAME;
                    }

                    public void setF_WG_NAME(String F_WG_NAME) {
                        this.F_WG_NAME = F_WG_NAME;
                    }

                    public String getF_RES_NAME() {
                        return F_RES_NAME;
                    }

                    public void setF_RES_NAME(String F_RES_NAME) {
                        this.F_RES_NAME = F_RES_NAME;
                    }

                    public String getF_RES_COUNT() {
                        return F_RES_COUNT;
                    }

                    public void setF_RES_COUNT(String F_RES_COUNT) {
                        this.F_RES_COUNT = F_RES_COUNT;
                    }

                    public String getF_RES_TYPENAME() {
                        return F_RES_TYPENAME;
                    }

                    public void setF_RES_TYPENAME(String F_RES_TYPENAME) {
                        this.F_RES_TYPENAME = F_RES_TYPENAME;
                    }

                    public String getF_RES_TYPE() {
                        return F_RES_TYPE;
                    }

                    public void setF_RES_TYPE(String F_RES_TYPE) {
                        this.F_RES_TYPE = F_RES_TYPE;
                    }

                    public String getScan_result() {
                        return scan_result;
                    }

                    public void setScan_result(String scan_result) {
                        this.scan_result = scan_result;
                    }
                }
            }

            public static class Sub_jhgdgzjdb {
                /**
                 * tenant_id : 55614223698362369
                 * ref_id_ : 80305289529661446
                 * F_WK_CONTENT : 工作事项01
                 * F_WK_ID : 1
                 * id_ : 80305289529692166
                 * F_WK_NODE : 工作节点01
                 * F_WK_RESULT : null
                 * proc_inst_id_ : 80305289529660422
                 */

                private String tenant_id;
                private String ref_id_;
                private String F_WK_CONTENT;
                private String F_WK_ID;
                private String id_;
                private String F_WK_NODE;
                private String F_WK_RESULT;
                private String proc_inst_id_;

                public String getTenant_id() {
                    return tenant_id;
                }

                public void setTenant_id(String tenant_id) {
                    this.tenant_id = tenant_id;
                }

                public String getRef_id_() {
                    return ref_id_;
                }

                public void setRef_id_(String ref_id_) {
                    this.ref_id_ = ref_id_;
                }

                public String getF_WK_CONTENT() {
                    return F_WK_CONTENT;
                }

                public void setF_WK_CONTENT(String F_WK_CONTENT) {
                    this.F_WK_CONTENT = F_WK_CONTENT;
                }

                public String getF_WK_ID() {
                    return F_WK_ID;
                }

                public void setF_WK_ID(String F_WK_ID) {
                    this.F_WK_ID = F_WK_ID;
                }

                public String getId_() {
                    return id_;
                }

                public void setId_(String id_) {
                    this.id_ = id_;
                }

                public String getF_WK_NODE() {
                    return F_WK_NODE;
                }

                public void setF_WK_NODE(String F_WK_NODE) {
                    this.F_WK_NODE = F_WK_NODE;
                }

                public String getF_WK_RESULT() {
                    return F_WK_RESULT;
                }

                public void setF_WK_RESULT(String F_WK_RESULT) {
                    this.F_WK_RESULT = F_WK_RESULT;
                }

                public String getProc_inst_id_() {
                    return proc_inst_id_;
                }

                public void setProc_inst_id_(String proc_inst_id_) {
                    this.proc_inst_id_ = proc_inst_id_;
                }
            }

            public static class Sub_jhgdzyb {
                /**
                 * F_RES_CODE : community_JHMZ-BJ-WW-WWZQBJ-0001
                 * tenant_id : 55614223698362369
                 * id_ : 80305289529695238
                 * F_REMARK : 欧克
                 * is_forced : 1
                 * F_WG_NAME : 扫码
                 * F_RES_NAME : 外围周期保洁
                 * F_RES_COUNT : null
                 * proc_inst_id_ : 80305289529660422
                 * scan_result : null
                 * ref_id_ : 80305289529661446
                 * F_SP_TYPE : null
                 * F_PG_ID : 49f4ec24-3d3d-430e-8dec-4070ab48a2e9
                 * F_RES_LOCATION : 小区
                 * F_RES_TYPENAME : 外围
                 * F_RES_TYPE : WW
                 */

                private String F_RES_CODE;
                private String tenant_id;
                private String id_;
                private String F_REMARK;
                private int is_forced;
                private int is_suc;
                private String F_WG_NAME;
                private String F_RES_NAME;
                private String F_RES_COUNT;
                private String proc_inst_id_;
                private String scan_result;
                private String ref_id_;
                private String F_SP_TYPE;
                private String F_PG_ID;
                private String F_RES_LOCATION;
                private String F_RES_TYPENAME;
                private String F_RES_TYPE;

                public String getF_RES_CODE() {
                    return F_RES_CODE;
                }

                public void setF_RES_CODE(String F_RES_CODE) {
                    this.F_RES_CODE = F_RES_CODE;
                }

                public String getTenant_id() {
                    return tenant_id;
                }

                public void setTenant_id(String tenant_id) {
                    this.tenant_id = tenant_id;
                }

                public String getId_() {
                    return id_;
                }

                public void setId_(String id_) {
                    this.id_ = id_;
                }

                public String getF_REMARK() {
                    return F_REMARK;
                }

                public void setF_REMARK(String F_REMARK) {
                    this.F_REMARK = F_REMARK;
                }

                public int getIs_forced() {
                    return is_forced;
                }

                public void setIs_forced(int is_forced) {
                    this.is_forced = is_forced;
                }

                public String getF_WG_NAME() {
                    return F_WG_NAME;
                }

                public void setF_WG_NAME(String F_WG_NAME) {
                    this.F_WG_NAME = F_WG_NAME;
                }

                public String getF_RES_NAME() {
                    return F_RES_NAME;
                }

                public void setF_RES_NAME(String F_RES_NAME) {
                    this.F_RES_NAME = F_RES_NAME;
                }

                public String getF_RES_COUNT() {
                    return F_RES_COUNT;
                }

                public void setF_RES_COUNT(String F_RES_COUNT) {
                    this.F_RES_COUNT = F_RES_COUNT;
                }

                public String getProc_inst_id_() {
                    return proc_inst_id_;
                }

                public void setProc_inst_id_(String proc_inst_id_) {
                    this.proc_inst_id_ = proc_inst_id_;
                }

                public String getScan_result() {
                    return scan_result;
                }

                public void setScan_result(String scan_result) {
                    this.scan_result = scan_result;
                }

                public String getRef_id_() {
                    return ref_id_;
                }

                public void setRef_id_(String ref_id_) {
                    this.ref_id_ = ref_id_;
                }

                public String getF_SP_TYPE() {
                    return F_SP_TYPE;
                }

                public void setF_SP_TYPE(String F_SP_TYPE) {
                    this.F_SP_TYPE = F_SP_TYPE;
                }

                public String getF_PG_ID() {
                    return F_PG_ID;
                }

                public void setF_PG_ID(String F_PG_ID) {
                    this.F_PG_ID = F_PG_ID;
                }

                public String getF_RES_LOCATION() {
                    return F_RES_LOCATION;
                }

                public void setF_RES_LOCATION(String F_RES_LOCATION) {
                    this.F_RES_LOCATION = F_RES_LOCATION;
                }

                public String getF_RES_TYPENAME() {
                    return F_RES_TYPENAME;
                }

                public void setF_RES_TYPENAME(String F_RES_TYPENAME) {
                    this.F_RES_TYPENAME = F_RES_TYPENAME;
                }

                public String getF_RES_TYPE() {
                    return F_RES_TYPE;
                }

                public void setF_RES_TYPE(String F_RES_TYPE) {
                    this.F_RES_TYPE = F_RES_TYPE;
                }

                public int getIs_suc() {
                    return is_suc;
                }

                public void setIs_suc(int is_suc) {
                    this.is_suc = is_suc;
                }
            }
        }
    }

    public static class ExtensionApplication implements Serializable {
        /**
         * id : 85132517090264070
         * poId : 80305289529661446
         * applyType : 2
         * extensionDays : null
         * applicationDescription : 331米
         * applicationState : 3
         * creationDate : 2020-07-06 15:50:36
         * type : 1
         * createdBy : 66929494354558982
         * approveId : 66929494354558982
         * approveName : 张溧阳
         * createdName : 张溧阳
         * applyFiles : [{"success":true,"fileId":"85132516016522246","name":"1594021838206","path":"55614223698362369\/zly\/2020\/7\/85132516016522246.","size":3091232}]
         * auditDate : 2020-07-06 15:51:01
         */

        private String id;
        private String poId;
        private int applyType;
        private String extensionDays;
        private String applicationDescription;
        private int applicationState;
        private String creationDate;
        private int type;
        private String createdBy;
        private String approveId;
        private String approveName;
        private String createdName;
        private String applyFiles;
        private String auditDate;

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

        public String getExtensionDays() {
            return extensionDays;
        }

        public void setExtensionDays(String extensionDays) {
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

        public String getAuditDate() {
            return auditDate;
        }

        public void setAuditDate(String auditDate) {
            this.auditDate = auditDate;
        }
    }
}
