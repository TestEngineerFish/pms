package com.einyun.app.base.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.einyun.app.base.BaseBindingViewHolder;
import com.einyun.app.base.event.ItemClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ProjectName: pms
 * @Package: com.einyun.app.base.adapter
 * @ClassName: RVPageListAdapter
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/11/19 0019 下午 19:15
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/19 0019 下午 19:15
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class RVPageListAdapter<D extends ViewDataBinding,M> extends PagedListAdapter<M,BaseBindingViewHolder> {

    protected ItemClickListener<M> itemClickListener;
    private LayoutInflater mInflater;
    private Context mContext;
    private int BR_id;//数据的实体类在BR文件中的id。在绑定到xml中后，在**！项目编译后 ！**会自动生成这个BR文件。
    protected RVPageListAdapter(Context context,int br_id,@NonNull DiffUtil.ItemCallback<M> diffCallback) {
        super(diffCallback);
        BR_id = br_id;
        this.mContext=context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public BaseBindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =mInflater.inflate(getLayoutId(), parent, false);
        return new BaseBindingViewHolder<D>(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseBindingViewHolder holder, int position) {

        holder.getBinding().setVariable(BR_id, getItem(position));
        //立即执行绑定
        holder.getBinding().executePendingBindings();
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener!=null){
                itemClickListener.onItemClicked(holder.itemView, getItem(position));
            }
        });

        onBindItem((D) holder.getBinding(),getItem(position));
    }

    public abstract void onBindItem(D binding,M model);

    //设置item布局文件id
    public abstract int getLayoutId();

    public void setOnItemClick(ItemClickListener<M> itemClick){
        this.itemClickListener =itemClick;
    }

}
