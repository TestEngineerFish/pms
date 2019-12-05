package com.einyun.app.pms.approval.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.einyun.app.pms.approval.R;
import com.einyun.app.pms.approval.module.GetByTypeKeyForComBoModule;
import com.einyun.app.pms.approval.ui.widget.CustomPopWindow;

import java.util.List;


public class ApprovalChildTypeAdapter extends BaseAdapter {
    Context context;
    GetByTypeKeyForComBoModule getByTypeKeyForComBoModule;
    public ApprovalChildTypeAdapter(Context context, GetByTypeKeyForComBoModule getByTypeKeyForComBoModule){
        this.context=context;
        this.getByTypeKeyForComBoModule=getByTypeKeyForComBoModule;
    }
    @Override
    public int getCount() {
        return getByTypeKeyForComBoModule==null?0:getByTypeKeyForComBoModule.getChildren().size();
    }
    public void  setData(GetByTypeKeyForComBoModule getByTypeKeyForComBoModule){
        this.getByTypeKeyForComBoModule=getByTypeKeyForComBoModule;
        notifyDataSetChanged();
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
        if (position== CustomPopWindow.mApprovalChildTypePosition) {

            hodler.textview.setBackgroundResource(R.drawable.iv_pop_item_choise);
        }else {
            hodler.textview.setBackgroundResource(R.drawable.shape_line);
        }
            hodler.textview.setText(getByTypeKeyForComBoModule.getChildren().get(position).getName());
        return convertView;
    }
    static class ViewHodle {
        TextView textview;
    }
}
