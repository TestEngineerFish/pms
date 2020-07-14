package com.einyun.app.pms.mine.ui;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.library.mdm.model.FeedBackListModel;
import com.einyun.app.pms.mine.BR;
import com.einyun.app.pms.mine.R;
import com.einyun.app.pms.mine.constants.Constants;
import com.einyun.app.pms.mine.databinding.ActivityFeedBackdetailBinding;
import com.einyun.app.pms.mine.databinding.ItemFeedHandleBinding;
import com.einyun.app.pms.mine.model.FeedBackModel;
import com.einyun.app.pms.mine.viewmodule.SettingViewModel;
import com.einyun.app.pms.mine.viewmodule.SettingViewModelFactory;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_FEED_DETAIL)
public class FeedBackDetailActivity extends BaseHeadViewModelActivity<ActivityFeedBackdetailBinding, SettingViewModel> {
    PhotoListAdapter photoListInfoAdapter;
    RVBindingAdapter<ItemFeedHandleBinding, FeedBackModel> handleAdapter;
    List<FeedBackModel> feedBackModels = new ArrayList<>();
    @Autowired(name = RouteKey.KEY_FEED_ID)
    String feedId;
    private FeedBackListModel feedBackListModel;

    @Override
    protected SettingViewModel initViewModel() {
        return new ViewModelProvider(this, new SettingViewModelFactory()).get(SettingViewModel.class);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.txt_feed_back);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feed_backdetail;
    }

    @Override
    protected void initData() {
        super.initData();
        photoListInfoAdapter = new PhotoListAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.photoList.setLayoutManager(layoutManager);
        binding.photoList.addItemDecoration(new SpacesItemDecoration(20));
        binding.photoList.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.photoList.setAdapter(photoListInfoAdapter);
       /* handleAdapter = new RVBindingAdapter<ItemFeedHandleBinding, FeedBackModel>(this, com.einyun.app.pms.mine.BR.handleFeed) {
            @Override
            public void onBindItem(ItemFeedHandleBinding binding, FeedBackModel model, int position) {

            }

            @Override
            public int getLayoutId() {
                return R.layout.item_feed_handle;
            }
        };
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.handleList.setLayoutManager(layoutManager1);
        binding.handleList.setAdapter(handleAdapter);
        for (int i=0;i<4;i++){
            feedBackModels.add(new FeedBackModel());
        }
        handleAdapter.setDataList(feedBackModels);*/
        viewModel.getFeedBackDetail(feedId).observe(this, data -> {
            if (data == null) {
                return;
            }
            binding.setFeed(data);
            PicUrlModelConvert convert = new PicUrlModelConvert();
            if (data.getAttachment() == null) {
                binding.photoList.setVisibility(View.GONE);
            } else {
                List<PicUrlModel> modelList = convert.stringToSomeObjectList(data.getAttachment().toString());
                if (modelList == null || modelList.size() == 0) {
                    binding.photoList.setVisibility(View.GONE);
                } else {
                    binding.photoList.setVisibility(View.VISIBLE);
                    photoListInfoAdapter.updateList(modelList);
                }
                if (data.getUpdationDate() == null || data.getUpdationDate().equals("")) {
                    binding.replyCard.setVisibility(View.GONE);
                } else {
                    binding.replyCard.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
