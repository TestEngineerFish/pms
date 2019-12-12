package com.einyun.app.pms.mine.module;

import java.util.List;

/**
 * Create by dowedo on 2019/4/9
 */
public class UCUserDetailsBean {

    /**
     * msg : 处理成功
     * code : 200
     * data : {"id":null,"projectName":null,"devideName":null,"jobs":["片区","项目负责","工程","城市公司","服务中心","片区业务品质部负责人","楼栋网格负责","工程服务单元","环境服务","环境服务单元","秩序服务","公区网格","工单创建人","工程服务单元管理中心流动组负责人","工程服务单元项目","区域业务","城市公司业务品质部负责人","环境服务单元绿化","秩序服务单元","工程服务单元管理中心负责人","环境服务单元项目团队负责人","秩序服务单元项目地块负责人","工单创建人员","区域业务品质部负责人","秩序服务单元项目负责人","环境服务单元绿化现场负责人","工程服务单元项目负责人","公区网格负责人","环境服务单元保洁地块负责人","楼栋网格负责人","服务中心员工","项目负责人","网格管家"],"stars":2,"rank":90,"lists":null}
     * state : true
     * request_id : xxx
     */

    private String msg;
    private int code;
    private DataBean data;
    private boolean state;
    private String request_id;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public static class DataBean {
        /**
         * id : null
         * projectName : null
         * devideName : null
         * jobs : ["片区","项目负责","工程","城市公司","服务中心","片区业务品质部负责人","楼栋网格负责","工程服务单元","环境服务","环境服务单元","秩序服务","公区网格","工单创建人","工程服务单元管理中心流动组负责人","工程服务单元项目","区域业务","城市公司业务品质部负责人","环境服务单元绿化","秩序服务单元","工程服务单元管理中心负责人","环境服务单元项目团队负责人","秩序服务单元项目地块负责人","工单创建人员","区域业务品质部负责人","秩序服务单元项目负责人","环境服务单元绿化现场负责人","工程服务单元项目负责人","公区网格负责人","环境服务单元保洁地块负责人","楼栋网格负责人","服务中心员工","项目负责人","网格管家"]
         * stars : 2
         * rank : 90
         * lists : null
         */

        private Object id;
        private Object projectName;
        private Object devideName;
        private int stars;
        private int rank;
        private Object lists;
        private List<String> jobs;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getProjectName() {
            return projectName;
        }

        public void setProjectName(Object projectName) {
            this.projectName = projectName;
        }

        public Object getDevideName() {
            return devideName;
        }

        public void setDevideName(Object devideName) {
            this.devideName = devideName;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public Object getLists() {
            return lists;
        }

        public void setLists(Object lists) {
            this.lists = lists;
        }

        public List<String> getJobs() {
            return jobs;
        }

        public void setJobs(List<String> jobs) {
            this.jobs = jobs;
        }
    }
}
