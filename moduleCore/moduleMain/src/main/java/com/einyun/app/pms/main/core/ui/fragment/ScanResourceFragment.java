package com.einyun.app.pms.main.core.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.CustomEventTypeEnum;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.service.LoginNavigationCallbackImpl;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.fragment.BaseViewModelFragment;
import com.einyun.app.common.ui.widget.BottomPicker;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.utils.ClickProxy;
import com.einyun.app.common.utils.LiveDataBusUtils;
import com.einyun.app.common.utils.UserUtil;
import com.einyun.app.pms.main.BR;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.core.model.ScanRequest;
import com.einyun.app.pms.main.core.model.ScanResItemModel;
import com.einyun.app.pms.main.core.model.ScanResModel;
import com.einyun.app.pms.main.core.ui.ScanResourceActivity;
import com.einyun.app.pms.main.core.viewmodel.MineViewModel;
import com.einyun.app.pms.main.core.viewmodel.ViewModelFactory;
import com.einyun.app.pms.main.databinding.FragmentScanResBinding;
import com.einyun.app.pms.main.databinding.ItemResListBinding;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_COPY_ME;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_HAVE_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SCAN_HISTORY;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SCAN_WAIT_DEAL;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FEED_BACK;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TRANSFERRED_TO;


/**
 * @ProjectName: pms_old
 * @Package: com.einyun.app.pms.sendorder.ui
 * @ClassName: SendWorkOrderFragment
 * @Description: java类作用描述
 * @Author: zhulufeng
 * @CreateDate: 2019/11/26 14:37
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/26 14:37
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ScanResourceFragment extends BaseViewModelFragment<FragmentScanResBinding, MineViewModel> implements ItemClickListener<ScanResItemModel> {
    RVPageListAdapter<ItemResListBinding, ScanResItemModel> adapter;
    private ScanResourceActivity activity;


    public static ScanResourceFragment newInstance(Bundle bundle) {
        ScanResourceFragment fragment = new ScanResourceFragment();
        fragment.setArguments(bundle);
        Logger.d("setBundle->" + bundle.getString(RouteKey.KEY_FRAGEMNT_TAG));
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_scan_res;
    }


    @Override
    protected void init() {
        super.init();
    }

    protected String getFragmentTag() {
        return getArguments().getString(RouteKey.KEY_FRAGEMNT_TAG);
    }

    private void loadPagingData(ScanRequest requestBean, String tag, String type) {
//        初始化数据，LiveData自动感知，刷新页面
        viewModel.loadPadingData(requestBean, tag, type).observe(this, dataBeans ->
                adapter.submitList(dataBeans)
        );

    }

    /**
     * 是否超期选择
     */
    public void onTimeClick() {
        isOverTime();
    }

    /**
     * 严重程度选择
     */
    int txDefaultPos;

    private void isOverTime() {
        List<String> txStrList = new ArrayList<>();
        txStrList.add("超期");
        txStrList.add("未超期");
        txStrList.add("重置");
        BottomPicker.buildBottomPicker(activity, txStrList, txDefaultPos, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                txDefaultPos = position;
                if (position == 0) {
                    mOverTime = "1";
                    initOverTimeShow("超期",false);
                } else if (position == 1) {
                    mOverTime = "0";
                    initOverTimeShow("未超期",false);
                } else if (position == 2) {
                    mOverTime = null;

                    initOverTimeShow("是否超期",true);
                }
                initRequest();
            }
        });
    }

    private void initOverTimeShow(String value,boolean isAll) {
        if (isAll) {
            binding.tvOverTime.setTextColor(getResources().getColor(R.color.greyTextColor));
            binding.ivOverTime.setImageResource(R.drawable.iv_sort_grey_down);

        }else {
            binding.tvOverTime.setTextColor(getResources().getColor(R.color.blueTextColor));
            binding.ivOverTime.setImageResource(R.drawable.iv_sort_blue_down);
        }
        binding.tvOverTime.setText(value);
    }


    @Override
    protected void setUpView() {
        binding.setCallBack(this);
        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.swipeRefresh.setRefreshing(false);

            initRequest();
//            loadPagingData(viewModel.getRequestBean(1,10,cate,"",divideId),getFragmentTag()); });
        });
        binding.inquiriesList.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false));
        binding.inquiriesList.setAdapter(adapter);
    }

    @Override
    protected void setUpData() {
        if (adapter == null) {
            adapter = new RVPageListAdapter<ItemResListBinding, ScanResItemModel>(getActivity(), BR.scanResItemModel, mDiffCallback) {
                //                private static final String TAG = "ApprovalViewModelFragme";
                @Override
                public void onBindItem(ItemResListBinding binding, ScanResItemModel model) {

                    binding.tvCreateTime.setText(TimeUtil.getAllTime(model.getCreateDate()));
                    binding.tvOrderNo.setText(model.getOrderNo());
                    binding.tvTitle.setText(model.getPlanName());
                    if (model.getOrderOverTime()==1) {
                        binding.itemSendWorkLfImg.setVisibility(View.VISIBLE);
                    }else {
                        binding.itemSendWorkLfImg.setVisibility(View.GONE);

                    }
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_res_list;
                }
            };
        }
        binding.inquiriesList.setAdapter(adapter);
        adapter.setOnItemClick(this);
        activity = (ScanResourceActivity) getActivity();
        LiveDataBusUtils.getLiveBusData(binding.empty.getRoot(), LiveDataBusKey.SCAN_EMPTY + getFragmentTag(), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initRequest();
    }

    String mOverTime = null;

    private void initRequest() {
        ScanRequest scanRequest = new ScanRequest();
        scanRequest.setResId(activity.resId);
        scanRequest.setOrderOverTime(mOverTime);
        scanRequest.setId(activity.patrolId);
        scanRequest.setUserId(UserUtil.getUserId());
        loadPagingData(scanRequest, getFragmentTag(), activity.type);
    }

    @Override
    protected MineViewModel initViewModel() {
        return new ViewModelProvider(getActivity(), new ViewModelFactory()).get(MineViewModel.class);
    }

    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<ScanResItemModel> mDiffCallback = new DiffUtil.ItemCallback<ScanResItemModel>() {


        @Override
        public boolean areItemsTheSame(@NonNull ScanResItemModel oldItem, @NonNull ScanResItemModel newItem) {
            return oldItem == newItem;
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull ScanResItemModel oldItem, @NonNull ScanResItemModel newItem) {
            return oldItem == newItem;
        }
    };

    /**
     * item点击
     */
    @Override
    public void onItemClicked(View veiw, ScanResItemModel data) {

        switch (getFragmentTag()) {
            case FRAGMENT_SCAN_WAIT_DEAL:
                switch (data.getWorkOrderType()) {
                    case "plan_order":
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
                                .withString(RouteKey.KEY_ORDER_ID, data.getId())
                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                .withString(RouteKey.KEY_TASK_ID, data.getTaskId())
                                .withString(RouteKey.KEY_PRO_INS_ID, data.getPROC_INST_ID_())
//                    .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE)
                                .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_PLAN_OWRKORDER_PENDING)
                                .navigation();
                        break;
                    case "dispatch_order":
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_SEND_ORDER_DETAIL)
                                .withString(RouteKey.KEY_ORDER_ID, "")
                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                .withString(RouteKey.KEY_TASK_ID, data.getTaskId())
                                .withString(RouteKey.KEY_PRO_INS_ID, data.getPROC_INST_ID_())
                                .withInt(RouteKey.KEY_LIST_TYPE, ListType.PENDING.getType())
                                .navigation();
                        break;

                    default:
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_HANDLE)
                                .withString(RouteKey.KEY_TASK_ID, data.getTaskId())
                                .withString(RouteKey.KEY_ORDER_ID, "")
                                .withInt(RouteKey.KEY_LIST_TYPE, ListType.PENDING.getType())
                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                .withString(RouteKey.KEY_PRO_INS_ID, data.getPROC_INST_ID_())
                                .navigation();
                        break;

                }
                break;
            case FRAGMENT_SCAN_HISTORY:
                switch (data.getWorkOrderType()) {
                    case "plan_order":
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
                                .withString(RouteKey.KEY_ORDER_ID, data.getId())
                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                .withString(RouteKey.KEY_TASK_ID, data.getTaskId())
                                .withString(RouteKey.KEY_PRO_INS_ID, data.getPROC_INST_ID_())
                                .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE)
                                .navigation();
                        break;
                    case "dispatch_order":
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_SEND_ORDER_DETAIL)
                                .withString(RouteKey.KEY_ORDER_ID, "")
                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                .withString(RouteKey.KEY_TASK_ID, "")
                                .withString(RouteKey.KEY_PRO_INS_ID, data.getPROC_INST_ID_())
                                .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
                                .navigation();
                        break;

                    default:
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_DETIAL)
                                .withString(RouteKey.KEY_TASK_ID, "")
                                .withString(RouteKey.KEY_ORDER_ID, "")
                                .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                .withString(RouteKey.KEY_PRO_INS_ID, data.getPROC_INST_ID_())
                                .navigation();
                        break;

                }
                break;
        }

        Log.e(TAG, "onItemClicked: " + "taskId  ");
    }

    private static final String TAG = "CustomerInquiriesViewMo";


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
