package com.einyun.app.pms.approval.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.einyun.app.pms.approval.R;
import com.einyun.app.pms.approval.model.GetByTypeKeyForComBoModule;
import com.einyun.app.pms.approval.ui.widget.CustomPopWindow;

import java.util.List;


public class ApprovalTypeAdapter extends BaseAdapter {
    Context context;
    List<GetByTypeKeyForComBoModule> approvalAuditTypeModule;
    public ApprovalTypeAdapter(Context context, List<GetByTypeKeyForComBoModule> approvalAuditTypeModule){
        this.context=context;
        this.approvalAuditTypeModule=approvalAuditTypeModule;
    }
    @Override
    public int getCount() {
        return approvalAuditTypeModule==null?0:approvalAuditTypeModule.size();
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
        if (convertView !=null && convertView instanceof ViewGroup) {
            hodler = (ViewHodle) convertView.getTag();
        } else {
            convertView = View.inflate(context, R.layout.custom_popwindow_item, null);
            hodler = new ViewHodle();
            hodler.textview = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(hodler);
        }
        if (position== CustomPopWindow.mApprovalTypePosition) {

            hodler.textview.setBackgroundResource(R.drawable.iv_pop_item_choise);
        }else {
            hodler.textview.setBackgroundResource(R.drawable.shape_line);
        }
            hodler.textview.setText(approvalAuditTypeModule.get(position).getName());
        return convertView;
    }
    static class ViewHodle {
        TextView textview;
    }
}
