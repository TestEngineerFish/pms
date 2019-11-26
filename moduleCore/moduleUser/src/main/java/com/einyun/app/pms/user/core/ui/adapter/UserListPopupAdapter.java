package com.einyun.app.pms.user.core.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.einyun.app.pms.user.R;

import java.util.List;

public class UserListPopupAdapter extends BaseAdapter {
    private List userList;
    private Context context;
    private DrawableDeleteClickListener mDeleteListener;

    public UserListPopupAdapter(Context context, List<String> userList, DrawableDeleteClickListener drawableDeleteClickListener) {
        this.context = context;
        this.userList = userList;
        this.mDeleteListener = drawableDeleteClickListener;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_block_text_delete, viewGroup, false);
        TextView userName = view.findViewById(R.id.tv_user_name);
        ImageView delete = view.findViewById(R.id.iv_delete_icon);
        userName.setText(userList.get(i).toString());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDeleteListener.click(i, userList.get(i));
                userList.remove(i);
                notifyDataSetChanged();
            }
        });
        return view;
    }


    /**
     * 点击事件
     */
    public interface DrawableDeleteClickListener {
        void click(Integer position, Object user);
    }
}
