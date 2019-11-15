package com.einyun.app.common.ui.component.photo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.einyun.app.common.BuildConfig;
import com.einyun.app.common.R;
import com.einyun.app.common.model.PicUrlModel;
import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoViewHolder> {
    private List<PicUrlModel> picList;
    private Context mContext;
    private PhotoListItemListener listener;
    public PhotoListAdapter(Context context){
        this.mContext=context;
    }
    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LinearLayout.inflate(mContext, R.layout.item_photo_select,parent);
        PhotoViewHolder viewHolder=new PhotoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        holder.ivPhoto.setVisibility(View.VISIBLE);
        holder.add.setVisibility(View.GONE);
        PicUrlModel model = picList.get(position);
        Glide.with(mContext)
                .load(BuildConfig.BASE_URL+"media/"+model.getPath())
                .centerCrop()
                .into(holder.ivPhoto);
        if(listener!=null){
            holder.itemView.setOnClickListener(v -> listener.OnItemClick(holder.itemView,position));
        }
    }

    @Override
    public int getItemCount() {
        return picList==null?0:picList.size();
    }

    public void updateList(List<PicUrlModel> list){
        if(picList ==null){
            picList =list;
        }else{
            picList.clear();
            picList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void setOnItemListener(PhotoListItemListener listener){
        this.listener=listener;
    }
}