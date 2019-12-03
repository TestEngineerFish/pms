package com.einyun.app.pms.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.einyun.app.pms.R;
import com.einyun.app.pms.ui.adapter.ApprovalChildTypeAdapter;
import com.einyun.app.pms.ui.adapter.ApprovalStatusAdapter;
import com.einyun.app.pms.ui.adapter.ApprovalTypeAdapter;

public class CustomPopWindow extends PopupWindow {
    private static final String TAG = "CustomPopWindow";
    private final View view;
    private Activity context;
    private OnItemClickListener mListener;
    private NoScrollGridview gv_approval_type;
    private NoScrollGridview gv_approval_child_type;
    private NoScrollGridview gv_approval_status;
    public static int mApprovalTypePosition = -1;
    public static int mApprovalChildTypePosition = -1;
    public static int mApprovalStatusPosition = -1;
    private ApprovalTypeAdapter approvalTypeAdapter;
    private ApprovalChildTypeAdapter approvalChildTypeAdapter;
    private ApprovalStatusAdapter approvalStatusAdapter;

    public CustomPopWindow(Activity context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_popwindow, null);//alt+ctrl+f
        this.context = context;
        initView();
        initPopWindow();
    }


    private void initView() {

        TextView cancel = view.findViewById(R.id.cancle);
        TextView ok = view.findViewById(R.id.ok);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        gv_approval_type = view.findViewById(R.id.gv_approval_type);
        gv_approval_child_type = view.findViewById(R.id.gv_approval_child_type);
        gv_approval_status = view.findViewById(R.id.gv_approval_status);
        iv_close.setOnClickListener(view1 -> {
            dismiss();
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.setOnItemClick(v, "我是接口参数");
            }
        });
        //一级列表
        approvalTypeAdapter = new ApprovalTypeAdapter(context);
        gv_approval_type.setAdapter(approvalTypeAdapter);
        //二级列表
        approvalChildTypeAdapter = new ApprovalChildTypeAdapter(context);
        approvalStatusAdapter = new ApprovalStatusAdapter(context);
        //三级列表
        gv_approval_child_type.setAdapter(approvalChildTypeAdapter);
        gv_approval_status.setAdapter(approvalStatusAdapter);
        gv_approval_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mApprovalTypePosition = position;//暂定这样 等有真实数据 直接修改数据源刷新适配器，取出对应参数放到请求参数中
                approvalTypeAdapter.notifyDataSetChanged();
            }
        });
        gv_approval_child_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mApprovalChildTypePosition = position;
                approvalChildTypeAdapter.notifyDataSetChanged();
            }
        });
        gv_approval_status.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mApprovalStatusPosition = position;
                approvalStatusAdapter.notifyDataSetChanged();

            }
        });
    }

    class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ///这里的convertView instanceof ViewGroup是对convertView来进行
            ViewHodle hodler;
            if (convertView != null && convertView instanceof ViewGroup) {
                hodler = (ViewHodle) convertView.getTag();
            } else {
                convertView = View.inflate(context, R.layout.custom_popwindow_item, null);
                hodler = new ViewHodle();
                hodler.textview = (TextView) convertView.findViewById(R.id.tv_content);
                convertView.setTag(hodler);
            }
            hodler.textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notifyDataSetChanged();
                    hodler.textview.setBackgroundResource(R.drawable.iv_pop_item_choise);
                }
            });

            return convertView;
        }

    }

    static class ViewHodle {
        TextView textview;
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
        setOnDismissListener(new PopupWindow.OnDismissListener() {
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
        void setOnItemClick(View v, String data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
