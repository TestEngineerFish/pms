package com.einyun.app.pms.approval.ui.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.pms.approval.R;
import com.einyun.app.pms.approval.model.ApprovalDetailInfoBean;

import java.util.List;


public class ApprovalInfoDetailAdapter extends BaseAdapter {
    Context context;
    List<ApprovalDetailInfoBean.RowsBean> rows;
    public ApprovalInfoDetailAdapter(Context context, List<ApprovalDetailInfoBean.RowsBean> rows){
        this.context=context;
        this.rows=rows;
    }
    @Override
    public int getCount() {
        return rows==null?0:rows.size();
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
        ViewHodle hodler;
        if (convertView !=null && convertView instanceof ViewGroup) {
            hodler = (ViewHodle) convertView.getTag();
        } else {
            convertView = View.inflate(context, R.layout.item_approval_info, null);
            hodler = new ViewHodle();
            hodler.textview = (TextView) convertView.findViewById(R.id.tv_content);
            hodler.tvTopLine = (TextView) convertView.findViewById(R.id.tv_line_top);
            hodler.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            hodler.tvApprovalContent = (TextView) convertView.findViewById(R.id.tv_approval_content);
            hodler.texBottomLine = (TextView) convertView.findViewById(R.id.tv_line_bottom);
            hodler.tvState = (TextView) convertView.findViewById(R.id.tv_state);
            hodler.ivState = (ImageView) convertView.findViewById(R.id.iv_state);
            convertView.setTag(hodler);
        }
        if (position==0) {
            hodler.tvTopLine.setVisibility(View.INVISIBLE);
        }else{
            hodler.tvTopLine.setVisibility(View.VISIBLE);

        }
        if (position==rows.size()-1) {
            hodler.texBottomLine.setVisibility(View.INVISIBLE);
        }else {
            hodler.texBottomLine.setVisibility(View.VISIBLE);
        }
        ApprovalDetailInfoBean.RowsBean rowsBean = rows.get(position);
        hodler.tvTime.setText( TimeUtil.getAllTime(rowsBean.getAudit_date(),true));
        if (rowsBean.getComment().isEmpty()) {

            hodler.tvApprovalContent.setVisibility(View.GONE);
        }else {
            hodler.tvApprovalContent.setText(rowsBean.getComment());

        }
        //富文本
        String content=rowsBean.getAuditor()+" ("+rowsBean.getApprovalRole()+ ")";
        SpannableString mSpannableString = new SpannableString(rowsBean.getAuditor()+" ("+rowsBean.getApprovalRole()+")");
        mSpannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.greyTextColor)),rowsBean.getAuditor().length(),content.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//        mSpannableString.setSpan(new ForegroundColorSpan(Color.BLUE),4,6, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        hodler.textview.setText(mSpannableString);
        if ("reject".equals(rowsBean.getStatus())) {
            hodler.tvState.setText(context.getString(R.string.tv_no_pass));
            hodler.tvState.setTextColor(context.getResources().getColor(R.color.redTextColor));
            hodler.ivState.setImageResource(R.drawable.iv_approval_unpass_state);
        }else if ("approve".equals(rowsBean.getStatus())){
            hodler.tvState.setText(context.getString(R.string.tv_had_pass));
            hodler.tvState.setTextColor(context.getResources().getColor(R.color.greenTextColor));
            hodler.ivState.setImageResource(R.drawable.iv_approval__pass_state);
        }
        if (position==rows.size()-1) {
            hodler.ivState.setVisibility(View.GONE);
            hodler.tvState.setVisibility(View.GONE);
        }else {
            hodler.ivState.setVisibility(View.VISIBLE);
            hodler.tvState.setVisibility(View.VISIBLE);
        }
        return convertView;


    }
    static class ViewHodle {
        TextView textview;
        TextView tvTopLine;
        TextView texBottomLine;
        TextView tvState;
        TextView tvTime;
        TextView tvApprovalContent;
        ImageView ivState;
    }
}
