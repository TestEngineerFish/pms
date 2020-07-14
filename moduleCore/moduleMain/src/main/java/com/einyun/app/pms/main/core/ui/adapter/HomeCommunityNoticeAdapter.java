package com.einyun.app.pms.main.core.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.einyun.app.base.util.StringUtil;
import com.einyun.app.library.mdm.model.NoticeModel;
import com.einyun.app.pms.main.R;
import com.stx.xmarqueeview.XMarqueeView;
import com.stx.xmarqueeview.XMarqueeViewAdapter;

import java.util.ArrayList;

//MarqueeFactory<T extends View, E>
//泛型T:指定ItemView的类型
//泛型E:指定ItemView填充的数据类型
public class HomeCommunityNoticeAdapter extends XMarqueeViewAdapter<NoticeModel> {

    private LayoutInflater inflater;
    //私有属性
    private OnItemClickListener onItemClickListener = null;

    public HomeCommunityNoticeAdapter(Context mContext) {
        super(new ArrayList<>());
        inflater = LayoutInflater.from(mContext);
    }

    TextView title;
    TextView content;
    ImageView img;

    @Override
    public View onCreateView(XMarqueeView parent) {
        //跑马灯单个显示条目布局，自定义
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_community_notice, null);
    }

    @Override
    public void onBindView(View parent, View mView, int position) {
        content = mView.findViewById(R.id.tv_notice_content);
        /*if (mDatas.get(position).getWenxi()){
            title.setVisibility(View.VISIBLE);
        }else{
            title.setVisibility(View.GONE);
        }
        if (mDatas.get(position).getImg()){
            img.setVisibility(View.VISIBLE);
        }else{
            img.setVisibility(View.GONE);
        }*/
        content.setText(mDatas.get(position).getTitle());
//        if (!StringUtil.isNullStr(mDatas.get(position).getTitle()) || "暂无公告".equals(mDatas.get(position).getTitle())) {
//            img.setVisibility(View.GONE);
//        } else {
//            img.setVisibility(View.VISIBLE);
//        }
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, position);
            }
        });
    }

    //setter方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //回调接口
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

}