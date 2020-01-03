package com.einyun.app.common.ui.component.photo;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.einyun.app.common.R;
import com.einyun.app.common.model.PicUrlModel;

import java.util.ArrayList;
import java.util.List;

public class PhotoLocalListAdapter extends RecyclerView.Adapter<PhotoViewHolder> {
    private List<Uri> picList;
    private Context mContext;
    private PhotoListItemListener listener;
    public PhotoLocalListAdapter(Context context){
        this.mContext=context;
    }
    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LinearLayout.inflate(mContext, R.layout.item_photo_select,null);
        PhotoViewHolder viewHolder=new PhotoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        holder.ivPhoto.setVisibility(View.VISIBLE);
        holder.add.setVisibility(View.GONE);
        Uri uri = picList.get(position);
        if(uri!=null){
            Glide.with(mContext)
                    .load(uri)
                    .centerCrop()
                    .placeholder(R.mipmap.place_holder_img)
                    .error(R.mipmap.place_holder_img)
                    .into(holder.ivPhoto);
        }
        if(listener!=null){
            holder.itemView.setOnClickListener(v -> listener.OnItemClick(holder.itemView,position));
        }
    }

    @Override
    public int getItemCount() {
        return picList==null?0:picList.size();
    }

    public void updateList(List<Uri> list){
        if(picList ==null){
            picList =list;
        }else{
            picList.clear();
            picList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public List<String> getImagePaths(){
        List<String> paths=new ArrayList<>();
        if(picList!=null){
            for(Uri uri:picList){
                paths.add(uri.toString());
            }
        }
        return paths;
    }

    public void setOnItemListener(PhotoListItemListener listener){
        this.listener=listener;
    }
}
