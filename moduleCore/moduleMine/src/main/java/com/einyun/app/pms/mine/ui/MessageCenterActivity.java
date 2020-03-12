package com.einyun.app.pms.mine.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.util.LogTime;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.mine.BR;
import com.einyun.app.pms.mine.R;
import com.einyun.app.pms.mine.databinding.ActivityMessageCenterBinding;
import com.einyun.app.pms.mine.databinding.ItemMessageCenterBinding;
import com.einyun.app.pms.mine.model.MessageCenterModel;
import com.einyun.app.pms.mine.model.MsgExtendVars;
import com.einyun.app.pms.mine.model.MsgModel;
import com.einyun.app.pms.mine.model.RequestPageBean;
import com.einyun.app.pms.mine.viewmodule.SettingViewModelFactory;
import com.einyun.app.pms.mine.viewmodule.SignSetViewModel;
import com.google.gson.Gson;

@Route(path = RouterUtils.ACTIVITY_MESSAGE_CENTER)
public class MessageCenterActivity extends BaseHeadViewModelActivity<ActivityMessageCenterBinding, SignSetViewModel> implements ItemClickListener<MsgModel> {
    RVPageListAdapter<ItemMessageCenterBinding, MsgModel> adapter;
    @Override
    protected SignSetViewModel initViewModel() {
        return new ViewModelProvider(this, new SettingViewModelFactory()).get(SignSetViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(getString(R.string.tv_message_center));
        binding.setCallBack(this);
        setRightTxt(R.string.tv_sign_read);
        headBinding.tvRightTitle.setTextColor(getResources().getColor(R.color.blueTextColor));
    }

    @Override
    protected void initData() {
        super.initData();
        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.swipeRefresh.setRefreshing(false);

        });
        loadPagingData(new RequestPageBean(),"");
        initAdapter();
    }

    private void loadPagingData(RequestPageBean requestBean, String  tag){
//        初始化数据，LiveData自动感知，刷新页面
        viewModel.loadPadingData(requestBean,tag).observe(this, dataBeans ->
                adapter.submitList(dataBeans)
        );

    }
    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
    /**
     * 标记已读
     */
    public void onRightOptionClick(View view){
        viewModel.allRead("", TimeUtil.getAllTime(System.currentTimeMillis())).observe(this, model->{
            loadPagingData(new RequestPageBean(),"");
        });
    }
    private void initAdapter() {
        if(adapter==null){
            adapter=new RVPageListAdapter<ItemMessageCenterBinding, MsgModel>(this, com.einyun.app.pms.mine.BR.messageCenter,mDiffCallback){
                @Override
                public void onBindItem(ItemMessageCenterBinding itemBinding, MsgModel itemModel) {
                    if (itemModel.isHasRead()) {
                        itemBinding.tvContent.setTextColor(getResources().getColor(R.color.greyTextColor));
                    }else {
                        itemBinding.tvContent.setTextColor(getResources().getColor(R.color.txt_black_order));
                    }
                    MsgExtendVars msgExtendVars = new Gson().fromJson(itemModel.getExtendVars(), MsgExtendVars.class);
                    if (msgExtendVars!=null) {
                        switch (msgExtendVars.getType()) {
                            case "grab"://抢单
                                itemBinding.ivMsgType.setImageResource(R.drawable.iv_grab);
                                break;
                            case "reminder"://新待处理工单提醒
                                itemBinding.ivMsgType.setImageResource(R.drawable.iv_reminder);
                                break;
                        }
                    }


                }
                @Override
                public int getLayoutId() {
                    return R.layout.item_message_center;
                }
            };
        }
        binding.list.setLayoutManager(new LinearLayoutManager(MessageCenterActivity.this, LinearLayoutManager.VERTICAL, false));
        binding.list.setFocusable(false);
        binding.list.setAdapter(adapter);
        adapter.setOnItemClick(this);
    }
    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<MsgModel> mDiffCallback = new DiffUtil.ItemCallback<MsgModel>() {


        @Override
        public boolean areItemsTheSame(@NonNull MsgModel oldItem, @NonNull MsgModel newItem) {
            return oldItem==newItem;
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull MsgModel oldItem, @NonNull MsgModel newItem) {
            return oldItem==newItem;
        }
    };

    private static final String TAG = "MessageCenterActivity";
    @Override
    public void onItemClicked(View view, MsgModel msgModel) {


//        viewModel.singleRead(msgModel.getId()).observe(this,model->{
//            loadPagingData(new RequestPageBean(),"");
//        });
        MsgExtendVars msgExtendVars = new Gson().fromJson(msgModel.getExtendVars(), MsgExtendVars.class);

        if (msgExtendVars==null) {
            return;
        }
        switch (msgExtendVars.getType()) {
            case "grab"://抢单

                switch (msgExtendVars.getSubType()) {
                    case "repair"://报修

                        break;
                }

                break;
            case "reminder"://新待处理工单提醒
                switch (msgExtendVars.getSubType()) {
                    case "audit"://审批消息

                        break;
                    case "diapatch"://派工单消息

                        break;
                    case "plan"://计划工单消息

                        break;
                    case "inspection"://巡查工单消息

                        break;
                    case "complain"://投诉工单消息

                        break;
                    case "enquiry"://问询消息

                        break;
                    case "repair"://报修消息

                        break;
                }

                break;
        }

    }
}
