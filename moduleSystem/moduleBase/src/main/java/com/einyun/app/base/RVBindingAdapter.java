package com.einyun.app.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.einyun.app.base.event.ItemClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.base
 * @ClassName: RVBindingAdapter
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/15 15:22
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/15 15:22
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public abstract class RVBindingAdapter<D extends ViewDataBinding,M> extends RecyclerView.Adapter<BaseBindingViewHolder> {

    protected ItemClickListener<M> itemClickListener;
    protected Context mContext;
    private LayoutInflater mInflater;
    protected List<M> mDataList = new ArrayList<>();//数据集合
    private int BR_id;//数据的实体类在BR文件中的id。在绑定到xml中后，在**！项目编译后 ！**会自动生成这个BR文件。

    public RVBindingAdapter(Context context, int br_id) {//构造方法传入上下文 和 BR_id
        mContext = context;
        BR_id = br_id;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public BaseBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(getLayoutId(), parent, false);
        return new BaseBindingViewHolder<D>(itemView);
    }

    @Override
    public void onBindViewHolder(BaseBindingViewHolder holder, final int position) {
        //把数据实体类的信息传递给xml文件，同时把item在recycleView中对应位置的数据传过去
        holder.getBinding().setVariable(BR_id,mDataList.get(position));
        //立即执行绑定
        holder.getBinding().executePendingBindings();
        holder.itemView.setOnClickListener(v -> itemClickListener.onItemClicked(holder.itemView,mDataList.get(position)));

        onBindItem((D) holder.getBinding(),mDataList.get(position));
    }


    public abstract void onBindItem(D binding,M model);

    //设置item布局文件id
    public abstract int getLayoutId();

    public void setOnItemClick(ItemClickListener<M> itemClick){
        this.itemClickListener =itemClick;
    }
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public List<M> getDataList() {
        return mDataList;
    }

    public void setDataList(Collection<M> list) {
        this.mDataList.clear();
        this.mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(Collection<M> list) {
        int lastIndex = this.mDataList.size();
        if (this.mDataList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public void remove(int position) {
        this.mDataList.remove(position);
        notifyItemRemoved(position);

        if(position != (getDataList().size())){ // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position,this.mDataList.size()-position);
        }
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

}
