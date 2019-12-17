package com.einyun.app.common.ui.component.photo;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.einyun.app.common.R;
import com.einyun.app.common.ui.dialog.AlertDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: pms
 * @Package: com.einyun.app.common.ui.component.photo
 * @ClassName: PhotoSelectAdapter
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/11/20 0020 下午 19:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/20 0020 下午 19:05
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PhotoSelectAdapter extends RecyclerView.Adapter<PhotoSelectAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Activity activity;
    public static int maxSize=4;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setItemChangeListener(ItemChangeListener itemChangeListener) {
        this.itemChangeListener = itemChangeListener;
    }

    private ItemChangeListener itemChangeListener;

    private List<Uri> selectedPhotos = new ArrayList<>();

    private AddPhotoClickListener listener;
    private int CAMERA_OK = 0;

    public List<Uri> getSelectPhotos() {
        return selectedPhotos;
    }

    public void setSelectedPhotos(List<Uri> selectedPhotos) {
        this.selectedPhotos.clear();
        this.selectedPhotos.addAll(selectedPhotos);
        notifyDataSetChanged();
    }

    public PhotoSelectAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (inflater == null) {
            inflater = LayoutInflater.from(viewGroup.getContext());
        }
        View rootView = inflater.inflate(R.layout.item_photo_select, viewGroup, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == selectedPhotos.size()) {
            holder.layoutAdd.setVisibility(View.VISIBLE);
            holder.imgPhoto.setVisibility(View.INVISIBLE);
            holder.imgRemove.setVisibility(View.INVISIBLE);

            holder.layoutAdd.setOnClickListener(v -> {
                if (listener != null) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            //先判断有没有权限 ，没有就在这里进行权限的申请
                            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.CAMERA}, CAMERA_OK);
                            //跳转到应用详情手动打开权限
//                            Intent localIntent = new Intent();
//                            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
//                            localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
//                            activity.startActivity(localIntent);
                        } else {//说明已经获取到摄像头权限了 想干嘛干嘛
                            listener.onAddClick(selectedPhotos.size());
                        }
                    } else {//这个说明系统版本在6.0之下，不需要动态获取权限。
                        listener.onAddClick(selectedPhotos.size());
                    }
//                    listener.onAddClick(selectedPhotos.size());
                }
            });
        } else {
            holder.layoutAdd.setVisibility(View.INVISIBLE);
            holder.imgPhoto.setVisibility(View.VISIBLE);
            holder.imgRemove.setVisibility(View.VISIBLE);
            Uri photo = selectedPhotos.get(position);

            holder.imgRemove.setOnClickListener(v -> {
                new AlertDialog(activity).builder()
                        .setTitle("提示")
                        .setMsg("是否确认删除?")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                selectedPhotos.remove(photo);
                                if (itemChangeListener != null) {
                                    itemChangeListener.onChange(selectedPhotos);
                                }
                                notifyDataSetChanged();
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();

            });
            Glide.with(inflater.getContext()).load(photo).into(holder.imgPhoto);
        }
    }

    public interface ItemChangeListener {
        void onChange(List<Uri> urs);
    }

    public interface AddPhotoClickListener {
        void onAddClick(int selectedSize);
    }

    public void setAddListener(AddPhotoClickListener listener, Activity activity) {
        this.listener = listener;
        this.activity = activity;
    }

    public void addPhotos(List<Uri> photoUri) {
        selectedPhotos.addAll(photoUri);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
//        return selectedPhotos.size() < maxSize ? selectedPhotos.size() + 1 : maxSize;
        int count = selectedPhotos.size() + 1;
        return count;
    }

    public List<Uri> getSelectedPhotos() {
        return selectedPhotos;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhoto;
        ImageView imgRemove;

        FrameLayout layoutAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            layoutAdd = itemView.findViewById(R.id.layout_add);
            imgRemove = itemView.findViewById(R.id.img_remove);
        }
    }
}
