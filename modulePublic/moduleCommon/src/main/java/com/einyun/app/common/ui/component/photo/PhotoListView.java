package com.einyun.app.common.ui.component.photo;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.einyun.app.common.BuildConfig;
import com.einyun.app.common.R;
import com.einyun.app.common.databinding.LayoutLinearPhotoBinding;
import com.einyun.app.common.model.PicUrlModel;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片横向列表展示控件
 */
public class PhotoListView extends LinearLayout {

    private ArrayList<String> imagePaths;
    PhotoListAdapter listAdapter;
    List<PicUrlModel> pics;
    LayoutLinearPhotoBinding binding;
    public PhotoListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_linear_photo, this);
        binding= DataBindingUtil.getBinding(this);

        initView();
        loadData();
    }

    private void loadData() {

    }

    private void initView() {
        listAdapter=new PhotoListAdapter(getContext());
        binding.rvPhoto.setLayoutManager(new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.rvPhoto.setAdapter(listAdapter);
        listAdapter.setOnItemListener((v, position) -> {
            if(pics==null){
                return;
            }
            Intent starter = new Intent(getContext(), PhotoShowActivity.class);
            //传递当前点击的图片的位置、图片路径集合
            starter.putExtra("position", position);
            starter.putStringArrayListExtra("mImages", getImagePaths());
            getContext().startActivity(starter);
        });
    }

    public void updateList(List<PicUrlModel> pics){
        this.pics=pics;
        if(listAdapter!=null){
            listAdapter.updateList(pics);
        }
    }

    private ArrayList<String> getImagePaths() {
        if(pics!=null){
            if(imagePaths==null){
                imagePaths=new ArrayList<>();
            }
            imagePaths.clear();
            for(PicUrlModel model:pics){
                imagePaths.add(BuildConfig.BASE_URL+"media/"+model.getPath());
            }
        }
        return imagePaths;
    }

}
