package com.einyun.app.pms.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.einyun.app.pms.R;
import com.einyun.app.pms.ui.widget.CustomPopWindow;


public class ApprovalStatusAdapter extends BaseAdapter {
    Context context;
    public ApprovalStatusAdapter(Context context){
        this.context=context;
    }
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
        if (convertView !=null && convertView instanceof ViewGroup) {
            hodler = (ViewHodle) convertView.getTag();
        } else {
            convertView = View.inflate(context, R.layout.custom_popwindow_item, null);
            hodler = new ViewHodle();
            hodler.textview = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(hodler);
        }
        if (position== CustomPopWindow.mApprovalStatusPosition) {

            hodler.textview.setBackgroundResource(R.drawable.iv_pop_item_choise);
        }else {
            hodler.textview.setBackgroundResource(R.drawable.shape_line);
        }

        return convertView;
    }
    static class ViewHodle {
        TextView textview;
    }
}
