package com.einyun.app.common.ui.component.photo;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.einyun.app.common.R;

public class PhotoViewHolder extends RecyclerView.ViewHolder {
    View add;
    ImageView ivPhoto;
    ImageView ivRemove;
    public PhotoViewHolder(@NonNull View itemView) {
        super(itemView);
        add=itemView.findViewById(R.id.layout_add);
        ivPhoto=itemView.findViewById(R.id.img_photo);
        ivRemove=itemView.findViewById(R.id.img_remove);
    }
}
