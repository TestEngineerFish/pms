package com.einyun.app.pms.customerinquiries.model;

import java.util.List;

public class FeedBackModule {

    /**
     * commuReceivers : [{"commuId":"1302599","id":"1302602","receiver":"ceshi001","receiverId":"732001","status":"no"}]
     * taskCommu : {"createtime":"2019-02-24 23:32:46","id":"1302599","instanceId":"1302438","nodeId":"UserTask8","nodeName":"回访不满意后处理（强制闭单或创建不满意工单）","opinion":"测试001","sender":"ceshi004","senderId":"732004","taskId":"1302585"}
     */

    private TaskCommuBean taskCommu;
    private List<CommuReceiversBean> commuReceivers;

    public TaskCommuBean getTaskCommu() {
        return taskCommu;
    }

    public void setTaskCommu(TaskCommuBean taskCommu) {
        this.taskCommu = taskCommu;
    }

    public List<CommuReceiversBean> getCommuReceivers() {
        return commuReceivers;
    }

    public void setCommuReceivers(List<CommuReceiversBean> commuReceivers) {
        this.commuReceivers = commuReceivers;
    }

    public static class TaskCommuBean {
        /**
         * createtime : 2019-02-24 23:32:46
         * id : 1302599
         * instanceId : 1302438
         * nodeId : UserTask8
         * nodeName : 回访不满意后处理（强制闭单或创建不满意工单）
         * opinion : 测试001
         * sender : ceshi004
         * senderId : 732004
         * taskId : 1302585
         */

        private String createtime;
        private String id;
        private String instanceId;
        private String nodeId;
        private String nodeName;
        private String opinion;
        private String sender;
        private String senderId;
        private String taskId;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInstanceId() {
            return instanceId;
        }

        public void setInstanceId(String instanceId) {
            this.instanceId = instanceId;
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public String getOpinion() {
            return opinion;
        }

        public void setOpinion(String opinion) {
            this.opinion = opinion;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getSenderId() {
            return senderId;
        }

        public void setSenderId(String senderId) {
            this.senderId = senderId;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }
    }

    public static class CommuReceiversBean {
        /**
         * commuId : 1302599
         * id : 1302602
         * receiver : ceshi001
         * receiverId : 732001
         * status : no
         */

        private String commuId;
        private String id;
        private String receiver;
        private String receiverId;
        private String status;

        public long getExpectTime() {
            return expectTime;
        }

        public void setExpectTime(long expectTime) {
            this.expectTime = expectTime;
        }

        private long expectTime;

        public String getCommuId() {
            return commuId;
        }

        public void setCommuId(String commuId) {
            this.commuId = commuId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(String receiverId) {
            this.receiverId = receiverId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
