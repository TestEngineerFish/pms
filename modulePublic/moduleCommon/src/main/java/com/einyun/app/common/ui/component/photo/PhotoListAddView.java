package com.einyun.app.common.ui.component.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.einyun.app.common.R;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.databinding.LayoutLinearPhotoBinding;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.utils.Glide4Engine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.ui.component.photo
 * @ClassName: PhotoListAddView
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/28 0028 下午 17:18
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/10/28 0028 下午 17:18
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PhotoListAddView extends LinearLayout {
    private List<Uri> picList;
    PhotoListAdapter listAdapter;
    LayoutLinearPhotoBinding binding;
    private int MAX_PHOTO_SIZE = 999;

    public PhotoListAddView(AppCompatActivity context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_linear_photo, this);
        binding = DataBindingUtil.getBinding(this);

        initView();
        loadData();
    }

    public void setMaxSize(int maxSize) {
        this.MAX_PHOTO_SIZE = maxSize;
    }

    private void loadData() {
    }

    private void initView() {
        listAdapter = new PhotoListAdapter(getContext());
        binding.rvPhoto.setLayoutManager(new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.rvPhoto.setAdapter(listAdapter);
        listAdapter.setAddPhotoClickListener(new AddPhotoClickListener() {
            @Override
            public void onAddClick(int selectedSize) {
                Matisse.from((Activity) getContext())
                        .choose(MimeType.ofImage())
                        .capture(true)
                        .captureStrategy(new CaptureStrategy(true, DataConstants.DATA_PROVIDER_NAME))
                        .countable(true)
                        .maxSelectable(MAX_PHOTO_SIZE)
//                .maxSelectable(4 - (photoSelectAdapter.getItemCount() - 1))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new Glide4Engine())
                        .forResult(RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK);
            }
        });
    }

    public interface AddPhotoClickListener {
        void onAddClick(int selectedSize);
    }

    public void updateList(List<Uri> pics) {
        this.picList = pics;
        if (listAdapter != null) {
            listAdapter.updateList(picList);
        }
    }

    public List<Uri> getPicList() {
        return listAdapter.getPicList();
    }

    class PhotoListAdapter extends RecyclerView.Adapter<PhotoViewHolder> {
        private List<Uri> picList;
        private Context mContext;
        private AddPhotoClickListener listener;
        private int MAX_SIZE = 9999;

        public PhotoListAdapter(Context context) {
            this.mContext = context;
        }

        @NonNull
        @Override
        public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LinearLayout.inflate(mContext, R.layout.item_photo_select, parent);
            PhotoViewHolder viewHolder = new PhotoViewHolder(view);
            return viewHolder;
        }

        public void setMaxSize(int maxSize) {
            MAX_SIZE = maxSize;
        }

        public List<Uri> getPicList() {
            return picList;
        }

        @Override
        public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
            if (position == 0) {
                holder.add.setVisibility(View.VISIBLE);
                holder.ivPhoto.setVisibility(View.INVISIBLE);
                holder.ivRemove.setVisibility(View.INVISIBLE);

                holder.add.setOnClickListener(v -> {
                    if (listener != null) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                //先判断有没有权限 ，没有就在这里进行权限的申请
                                ActivityCompat.requestPermissions((Activity) mContext, new String[]{android.Manifest.permission.CAMERA}, RouterUtils.ACTIVITY_REQUEST_CAMERA_OK);
                                //跳转到应用详情手动打开权限
//                            Intent localIntent = new Intent();
//                            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
//                            localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
//                            activity.startActivity(localIntent);
                            } else {//说明已经获取到摄像头权限了 想干嘛干嘛
                                listener.onAddClick(picList.size());
                            }
                        } else {//这个说明系统版本在6.0之下，不需要动态获取权限。
                            listener.onAddClick(picList.size());
                        }
//                    listener.onAddClick(selectedPhotos.size());
                    }
                });

                if (getItemCount() - 1 >= MAX_SIZE) {
                    holder.add.setVisibility(View.GONE);
                } else {
                    holder.add.setVisibility(View.VISIBLE);
                }

            } else {
                holder.ivPhoto.setVisibility(View.INVISIBLE);
                holder.add.setVisibility(View.VISIBLE);
                holder.ivRemove.setVisibility(View.VISIBLE);
                Uri photo = picList.get(position - 1);

                holder.ivRemove.setOnClickListener(v -> {
                    picList.remove(photo);
                    notifyDataSetChanged();
                });
                Glide.with(getContext())
                        .load(photo).centerCrop().placeholder(R.mipmap.place_holder_img)
                        .error(R.mipmap.place_holder_img)
                        .into(holder.ivPhoto);
            }
        }

        @Override
        public int getItemCount() {
            return picList == null ? 0 : picList.size() + 1;
        }

        public void updateList(List<Uri> list) {
            if (picList == null) {
                picList = list;
            } else {
                picList.clear();
                picList.addAll(list);
            }
            notifyDataSetChanged();
        }

        public void setAddPhotoClickListener(AddPhotoClickListener listener) {
            this.listener = listener;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK) {
            if (data == null) return;
            List<Uri> uris = Matisse.obtainResult(data);
            if (uris != null && uris.size() > 0) {
                listAdapter.updateList(uris);
            }
        }
    }
}
