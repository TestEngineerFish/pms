package com.einyun.app.common.manager;


public enum CustomEventTypeEnum {
	POINT_CHECK("POINT_CHECK"),//点击点检按钮时触发
	KEY_SCHOOL("key_school"),//点击“一键催缴”按钮确认后触发
	FEE_DETAIL_LIST("fee_detail_list"),//欠费详单页面“提交”按钮确认后触发
	FEE_DETAIL("fee_detail"),//欠费页面下方直接点击“收费”按钮时出发，
	SINGLE_WORTH("single_worth"),//详情单个催缴
	ORDER_LIST("order_list"),//点击“工单列表”按钮后触发
	PREVIEW_WORK("preview_work"),//点击“工作预览”按钮后触发
	SWEEP_CODE("sweep_code"),//点击“扫码处理”按钮后触发
	ORDER_HANDLE("order_handle"),//点击工单处理情况总览-“更多”按钮后触发
	OPERATE_CAPTURE_RATE("operate_capture_rate"),//点击运营收缴率-“更多”按钮后触发

	UNQUALIFIED_SEARCH("unqualified_search"),//点击“搜索”按钮进行搜索后触发
	SEND_ORDER_TURN_ORDER("send_order_turn_order"),//点击“转单”按钮提交后触发
	PLAN_ORDER_SEARCH("plan_order_search"),//点击“搜索”按钮进行搜索后触发
	PATOL_ORDER_SEARCH("patrol_order_search"),//点击“搜索”按钮进行搜索后触发

	COMPLAIN_COMMUN("complain_commun"),//点击“沟通”按钮提交后触发
	COMPLAIN_TURN_ORDER("complain_turn_order"),//点击“转单”按钮提交后触发
	INQUIRIES_COMMUN("inquiries_commun"),//点击“沟通”按钮后触发
	INQUIRIES_TURN_ORDER("inquiries_turn_order"),//点击“转单”按钮后触发
	REPAIR_COMMUN("repair_commun"),//点击“沟通”按钮后触发
	REPAIR_TURN_ORDER("repair_turn_order"),//点击“转单”按钮后触发
	REPAIR_GRAB("repair_grab"),//点击“抢单”按钮抢单后触发

	FEEDBACK("feedback"),//点击“意见反馈”按钮提交后触发
	APPROVAL_SUBMIT("approval_submit"),//点击通过/不通过按钮提交后触发
	MSG_SWITCH("msg_switch");//点击消息通知打开/关闭按钮触发


	String typeName;

	CustomEventTypeEnum(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}