package com.einyun.app.pms.approval.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.einyun.app.pms.approval.R;

import com.einyun.app.pms.approval.constants.ApprovalDataKey;
import com.einyun.app.pms.approval.model.ApprovalBean;
import com.einyun.app.pms.approval.model.GetByTypeKeyForComBoModule;
import com.einyun.app.pms.approval.model.GetByTypeKeyInnerAuditStatusModule;
import com.einyun.app.pms.approval.ui.adapter.ApprovalChildTypeAdapter;
import com.einyun.app.pms.approval.ui.adapter.ApprovalStatusAdapter;
import com.einyun.app.pms.approval.ui.adapter.ApprovalTypeAdapter;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

public class CustomPopWindow extends PopupWindow {
    private static final String TAG = "CustomPopWindow";
    private final View view;
    private Activity context;
    private OnItemClickListener mListener;
    private NoScrollGridview gv_approval_type;
    private NoScrollGridview gv_approval_child_type;
    private NoScrollGridview gv_approval_status;
    public static int mApprovalTypePosition=-1;
    public static int mApprovalChildTypePosition=-1;
    public static int mApprovalStatusPosition=-1;
    private ApprovalTypeAdapter approvalTypeAdapter;
    private ApprovalChildTypeAdapter approvalChildTypeAdapter;
    private ApprovalStatusAdapter approvalStatusAdapter;
    int tabId;
    private String auditType = "";
    private String auditSubType = "";
    private String auditStatus = "";
    private ApprovalBean approvalBean;
    List<GetByTypeKeyForComBoModule> approvalAuditTypeModule;
    List<GetByTypeKeyInnerAuditStatusModule> approvalAuditStateModule;
    List<GetByTypeKeyInnerAuditStatusModule> approvalSelectedAuditStateModule;
    private GetByTypeKeyForComBoModule mGetByTypeKeyForComBoModule;
    public CustomPopWindow(Activity context, int tabId, List<GetByTypeKeyForComBoModule> approvalAuditTypeModule, List<GetByTypeKeyInnerAuditStatusModule> approvalAuditStateModule, int mApprovalTypePosition, int mApprovalChildTypePosition, int mApprovalStatusPosition, String auditType, String auditSubType, String auditStatus) {
        super(context);
        this.tabId=tabId;
        this.approvalAuditStateModule=approvalAuditStateModule;
        this.approvalAuditTypeModule=approvalAuditTypeModule;
        this.mApprovalTypePosition=mApprovalTypePosition;
        this.mApprovalChildTypePosition=mApprovalChildTypePosition;
        this.mApprovalStatusPosition=mApprovalStatusPosition;
        this.auditType =auditType;
        this.auditSubType = auditSubType;
        this.auditStatus = auditStatus;

        mGetByTypeKeyForComBoModule=approvalAuditTypeModule.get(mApprovalTypePosition==-1?0:mApprovalTypePosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_popwindow, null);//alt+ctrl+f
        this.context = context;
        initView();
        initPopWindow();
//        getData(1,10);
        this.setOnDismissListener(new PopupWindow.OnDismissListener(){

            @Override
            public void onDismiss() {
//                reSetdata();
            }
        });
}

    private void reSetdata() {
        auditStatus="";
        auditSubType="";
        auditType="";
        mApprovalStatusPosition=-1;
        mApprovalTypePosition=-1;
        mApprovalChildTypePosition=-1;
        approvalChildTypeAdapter.notifyDataSetChanged();
        approvalStatusAdapter.notifyDataSetChanged();
        approvalTypeAdapter.notifyDataSetChanged();
        gv_approval_child_type.setVisibility(View.GONE);
    }

    private ApprovalBean getData(int page, int pageSize) {
        JSONObject jsonObject = new JSONObject();
        JSONObject pageBean = new JSONObject();
        pageBean.put("page", page);
        pageBean.put("pageSize", pageSize);
        pageBean.put("showTotal", true);
        jsonObject.put("pageBean", pageBean);
        JSONArray jsonArray = new JSONArray();

//        if (!divideId.isEmpty()) {
//            JSONObject jsonObject1 = new JSONObject();
//            jsonObject1.put("property", "divide_id");
//            jsonObject1.put("operation", "EQUAL");
//            jsonObject1.put("value", divideId);
//            jsonObject1.put("relation", "AND");
//            jsonArray.add(jsonObject1);
//        }
//
//        if (!divideName.isEmpty()) {
//            JSONObject jsonObject1 = new JSONObject();
//            jsonObject1.put("property", "divide_name");
//            jsonObject1.put("operation", "EQUAL");
//            jsonObject1.put("value", divideName);
//            jsonObject1.put("relation", "AND");
//            jsonArray.add(jsonObject1);
//        }
//
        if (!auditType.isEmpty()) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("property", "audit_type");
            jsonObject1.put("operation", "EQUAL");
            jsonObject1.put("value", auditType);
            jsonObject1.put("relation", "AND");
            jsonArray.add(jsonObject1);
        }

        if (!auditSubType.isEmpty()) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("property", "audit_sub_type");
            jsonObject1.put("operation", "EQUAL");
            jsonObject1.put("value", auditSubType);
            jsonObject1.put("relation", "AND");
            jsonArray.add(jsonObject1);
        }


        if (!auditStatus.isEmpty()) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("property", "vo.status");
            jsonObject1.put("operation", "EQUAL");
            jsonObject1.put("value", auditStatus);
            jsonObject1.put("relation", "AND");
            jsonArray.add(jsonObject1);
        }

        jsonObject.put("querys", jsonArray);
//        String url = "";
//        if (tabId == 0) {
//            url = Constants.UrlxcgdGetTodoListApprove;
//        } else if (tabId == 1) {
//            url = Constants.UrlxcgdGetDoneCompelteApprove;
//        } else if (tabId == 2) {
//            url = Constants.UrlxcgdGetIInitiated;
//        }

//        Log.e("shmshmshm", "jsonObject.toString() = " + jsonObject.toString());
        approvalBean = new Gson().fromJson(jsonObject.toString(), ApprovalBean.class);
         return approvalBean;
    }
    private void initView() {

        TextView cancel = view.findViewById(R.id.cancle);
        TextView ok = view.findViewById(R.id.ok);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        LinearLayout llState = view.findViewById(R.id.ll_approval_state);
        LinearLayout llChirldType = view.findViewById(R.id.ll_chirld_type);
        gv_approval_type = view.findViewById(R.id.gv_approval_type);
        gv_approval_child_type = view.findViewById(R.id.gv_approval_child_type);
        gv_approval_status = view.findViewById(R.id.gv_approval_status);
        if (mApprovalChildTypePosition!=-1) {
            llChirldType.setVisibility(View.VISIBLE);
        }
        iv_close.setOnClickListener(view1 -> {dismiss();});
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reSetdata();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.setOnItemClick(v,getData(1,10));
                mListener.onData(auditType,auditSubType,auditStatus,mApprovalTypePosition,mApprovalChildTypePosition,mApprovalStatusPosition);
                dismiss();
            }
        });
//        switch (tabId) {
//            case 0:
//                mApprovalChildTypePosition=1;
//                break;
//            case 1:
//                mApprovalChildTypePosition=2;
//                break;
//
//            case 2:
//                mApprovalChildTypePosition=3;
//                break;
//        }

        //一级列表
        approvalTypeAdapter = new ApprovalTypeAdapter(context,approvalAuditTypeModule);
        gv_approval_type.setAdapter(approvalTypeAdapter);
        //二级列表
        if (mApprovalTypePosition==-1) {
            approvalChildTypeAdapter = new ApprovalChildTypeAdapter(context,approvalAuditTypeModule.get(0));
        }else {
            approvalChildTypeAdapter = new ApprovalChildTypeAdapter(context,approvalAuditTypeModule.get(mApprovalTypePosition));

        }
        gv_approval_child_type.setAdapter(approvalChildTypeAdapter);
        //三级列表  已审批 只显示 已审批  已驳回
        approvalSelectedAuditStateModule=new ArrayList<>();
        for (GetByTypeKeyInnerAuditStatusModule auditStatusModule : approvalAuditStateModule) {
            if (ApprovalDataKey.APPROVAL_STATE_HAD_PASS.equals(auditStatusModule.getKey())||ApprovalDataKey.APPROVAL_STATE_HAD_UNPASS.equals(auditStatusModule.getKey())) {
                approvalSelectedAuditStateModule.add(auditStatusModule);
            }
        }
        if (tabId==1) {
            approvalStatusAdapter = new ApprovalStatusAdapter(context,approvalAuditStateModule);
            gv_approval_status.setAdapter(approvalStatusAdapter);
        }else if (tabId==2){
            approvalStatusAdapter = new ApprovalStatusAdapter(context,approvalAuditStateModule);
            gv_approval_status.setAdapter(approvalStatusAdapter);
        }else if (tabId==0) {
//                llState.setVisibility(View.GONE);
            approvalStatusAdapter = new ApprovalStatusAdapter(context,approvalSelectedAuditStateModule);
            gv_approval_status.setAdapter(approvalStatusAdapter);
        }


        gv_approval_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                 gv_approval_child_type.setVisibility(View.VISIBLE);
                 mApprovalTypePosition=position;//暂定这样 等有真实数据 直接修改数据源刷新适配器，取出对应参数放到请求参数中
                 mGetByTypeKeyForComBoModule = approvalAuditTypeModule.get(position);
                 approvalChildTypeAdapter.setData(mGetByTypeKeyForComBoModule);//给child传数据
                 mApprovalChildTypePosition=-1;
                 approvalTypeAdapter.notifyDataSetChanged();
                 llChirldType.setVisibility(View.VISIBLE);
                 //bean 请求参数数据处理
                auditType= mGetByTypeKeyForComBoModule.getKey();

            }
        });
        gv_approval_child_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mApprovalChildTypePosition=position;
                approvalChildTypeAdapter.notifyDataSetChanged();
                if (mGetByTypeKeyForComBoModule!=null) {
                    auditSubType=mGetByTypeKeyForComBoModule.getChildren().get(position).getKey();
                }

            }
        });
        gv_approval_status.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mApprovalStatusPosition=position;
                approvalStatusAdapter.notifyDataSetChanged();
                if (tabId==1) {

                    auditStatus=approvalSelectedAuditStateModule.get(position).getKey();
                }else {
                    auditStatus=approvalAuditStateModule.get(position).getKey();

                }
            }
        });
    }

    private void initPopWindow() {
        this.setContentView(view);
        // 设置弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出窗体可点击()
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00FFFFFF);
        //设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        backgroundAlpha(context, 1f);//0.0-1.0
        setOnDismissListener(new PopupWindow.OnDismissListener(){
            @Override
            public void onDismiss() {
               backgroundAlpha(context, 1f);
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度(值越大,透明度越高)
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }


    public interface OnItemClickListener {
        void setOnItemClick(View v,ApprovalBean data);
        void onData(String auditType, String auditSubType, String auditStatus, int mApprovalTypePosition, int mApprovalChildTypePosition, int mApprovalStatusPosition);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
