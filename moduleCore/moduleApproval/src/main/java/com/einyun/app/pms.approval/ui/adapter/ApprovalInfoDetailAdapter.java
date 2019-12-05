package com.einyun.app.pms.approval.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.einyun.app.pms.approval.R;


public class ApprovalInfoDetailAdapter extends BaseAdapter {
    Context context;
    public ApprovalInfoDetailAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return 6;
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
            hodler.texBottomLine = (TextView) convertView.findViewById(R.id.tv_line_bottom);
            convertView.setTag(hodler);
        }
        if (position==0) {
            hodler.tvTopLine.setVisibility(View.INVISIBLE);
        }else{
            hodler.tvTopLine.setVisibility(View.VISIBLE);

        }
        if (position==5) {
            hodler.texBottomLine.setVisibility(View.INVISIBLE);
        }else {
            hodler.texBottomLine.setVisibility(View.VISIBLE);

        }
        return convertView;
    }
    static class ViewHodle {
        TextView textview;
        TextView tvTopLine;
        TextView texBottomLine;
    }
}
