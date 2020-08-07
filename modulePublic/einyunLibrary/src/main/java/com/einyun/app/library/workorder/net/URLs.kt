package com.einyun.app.library.workorder.net

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.workorder.net
 * @ClassName:      URLs
 * @Description:     《业主小程序接口文档1.9》
 * @Author:         chumingjun
 * @CreateDate:     2019/10/17 09:09
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/17 09:09
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class URLs {
    companion object{
        const val DOMAIN:String ="workOrder"
        //抢单数量
        const val URL_GRAP_ORDER_NUMS:String = "/$DOMAIN/workOrder/task/v1/getGrabCount"
        //待审批数量
        const val URL_APPROVAL_PENDING_NUMS:String= "/$DOMAIN/workOrder/task/v1/getUserAuditCount"
        //获取用户待办工单的数量(GET)
        const val URL_BACKLOG_NUMS:String= "/$DOMAIN/workOrder/task/v1/getMapByUcDivKeys?defKeys=customer_complain_flow,customer_repair_flow,customer_enquiry_flow,unqualified_key"

        // 客户投诉列表 待跟进
        const val URL_COMPLAIN_FLOW_PENDING:String="/$DOMAIN/workOrder/task/v1/getTodoListAliaForApp/customer_complain_flow/NORMAL,DELIVERTO"
        // 客户投诉列表 待反馈
        const val URL_COMPLAIN_FEEDBACK_PENDING:String="/$DOMAIN/workOrder/task/v1/getTodoListAliaForApp/customer_complain_flow/COMMU"
        // 客户投诉列表 已跟进
        const val URL_COMPLAIN_FLOWED:String="/$DOMAIN/workOrder/task/v1/getDoneListAliaForApp/customer_complain_flow"
        // 客户投诉列表 已办结
        const val URL_COMPLAIN_COMPLETE:String="/$DOMAIN/workOrder/task/v1/getCompletedAliaForApp/customer_complain_flow"
        // 客户投诉列表 抄送我的
        const val URL_COMPLAIN_COPY:String="/$DOMAIN/workOrder/task/v1/getReceiverCopyToForApp/customer_complain_flow"

        //获取投诉工单列表
        const val URL_COMPLAIN_LIST:String="/$DOMAIN/workOrder/task/v1/getWorkListBydefKeyForApp?defKey=customer_complain_flow"
        //根据参数（如：手机号）查询处理中的投诉列表
        const val URL_COMPLAIN_WORK_LIST:String="/$DOMAIN/workOrder/task/v1/getPersonWorkByKey/customer_complain_flow"
        //获取投诉、问询工单类别与条线
        const val URL_BIZDATA_BASE_LIST:String="/$DOMAIN/workOrder/bizData/v1/getBaseList?categoryKey=workorder_type_and_line"
        //获取创建问询大类小类
        const val URL_BIZDATA_ENQUI_LIST:String="/$DOMAIN/workOrder/bizData/v1/getBizDataTree?categoryKey=inquiry_categories"
        //提交追加投诉
        const val URL_COMPLAIN_SUBMIT:String="/$DOMAIN/workOrder/task/v1/appendWorkOrder"
        // 客户端启动投诉流程
        const val URL_TASK_RUN_START:String="/$DOMAIN/workOrder/taskRun/v1/start"
        //获取报修类别与条线
        const val URL_REPAIR_TYPE_MAP_LIST:String="/$DOMAIN/workOrder/customerRepair/v1/getRepairAreaAndType"
        //创建报修工单
        const val URL_CUSTOMER_REPAIR_SUBMIT:String="/$DOMAIN/workOrder/customerRepair/v1/start"
        //创建问询工单
        const val URL_CUSTOMER_ENQUIRY_SUBMIT:String="/$DOMAIN/workOrder/customerEnquiry/v1/start"
        //待审批
        const val URL_APPROVE_TODO_LIST:String="/$DOMAIN/workOrder/workOrderInnerAudit/v1/getTodoListApprove"
        //已审批
        const val URL_APPROVE_DONE_LIST:String="/$DOMAIN/workOrder/workOrderInnerAudit/v1/getDoneCompelteApprove"
        //我发起的
        const val URL_APPROVE_INITIATED_LIST:String="/$DOMAIN/workOrder/workOrderInnerAudit/v1/getIInitiated"

        //客户报修-待跟进
        const val URL_REPORT_COMPLAIN_WAIT_FOLLOW="workOrder/workOrder/task/v1/getTodoListAliaForApp/customer_complain_flow/NORMAL,DELIVERTO"
        //客户报修-已跟进
        const val URL_REPORT_COMPLAIN_ALREADY_FOLLOW="/workOrder/workOrder/task/v1/getDoneListAliaForApp/customer_complain_flow"
        //客户报修-已办结
        const val URL_REPORT_COMPLAIN_ALREADY_DONE="/workOrder/workOrder/task/v1/getCompletedAliaForApp/customer_complain_flow"
        //客户报修-抄送我的
        const val URL_REPORT_COMPLAIN_COPY_ME="/workOrder/workOrder/task/v1/getReceiverCopyToForApp/customer_complain_flow"

        //通过UserId批量查询待处理工单
        const val URL_GET_MAPPING_BY_USERIDS="/$DOMAIN/workOrder/userWorkorderCtn/v1/getMappingByUserIds"
        //客户报修-抢单列表
        const val URL_REPORT_REPAIRS_GRAB="/workOrder/workOrder/task/v1/getGrabListAlia"
        //客户报修-待跟进
        const val URL_REPORT_REPAIRS_WAIT_FOLLOW="workOrder/workOrder/task/v1/getTodoListAliaForApp/customer_repair_flow/NORMAL,DELIVERTO"
        //客户报修-已跟进
        const val URL_REPORT_REPAIRS_ALREADY_FOLLOW="/workOrder/workOrder/task/v1/getDoneListAliaForApp/customer_repair_flow"
        //客户报修-已办结
        const val URL_REPORT_REPAIRS_ALREADY_DONE="/workOrder/workOrder/task/v1/getCompletedAliaForApp/customer_repair_flow"
        //客户报修-待反馈
        const val URL_REPORT_REPAIRS_WAIT_FEED="/workOrder/workOrder/task/v1/getTodoListAliaForApp/customer_repair_flow/COMMU"
        //客户报修-抄送我的
        const val URL_REPORT_REPAIRS_COPY_ME="/workOrder/workOrder/task/v1/getReceiverCopyToForApp/customer_repair_flow"
        //客户报修-抢单动作
        const val URL_REPAIR_GRAB="/workOrder/workOrder/customerRepair/v1/orderGrab?taskId="
        //客户报修-查看详情
        const val URL_REPAIR_DETAIL="/workOrder/workOrder/task/v1/getOrderDetail?"
//        const val URL_REPAIR_DETAIL="/bpm-runtime/runtime/instance/v1/getInstBO?proInstId="
        //客户-查看详情
        const val URL_CLIENT_DETAIL="/workOrder/workOrder/task/v1/getOrderDetail"
        //客户报修-派单，响应
        const val URL_REPAIR_SEND="/workOrder/workOrder/customerRepair/v1/complete"
        //客户报修-处理
        const val URL_REPAIR_HANDLE_SAVE="/workOrder/workOrder/saveDraft/v1/saveHandle"
        //获取客户报修筛选数据
        const val URL_REPAIR_SELECT="/workOrder/workOrder/customerRepair/v1/getRepairAreaAndType"
        const val URL_INITIATE_COMMUNICATION="/bpm-runtime/runtime/task/v1/communicate"

        //详情处理响应评价
        const val URL_COMPLAIN_DETAIL_COMPLETE="/workOrder/workOrder/taskRun/v1/complete"
        //详情处理响应评价
        const val URL_COMPLAIN_DETAIL_SAVE="/workOrder/workOrder/saveDraft/v1/saveHandle"

        //根据房产ID来查询用户
        const val URL_USERINFO_BY_HOUSE_ID="/mdm/api/mdm/v1/houseClientRef/getListByHouseId"
    }
}