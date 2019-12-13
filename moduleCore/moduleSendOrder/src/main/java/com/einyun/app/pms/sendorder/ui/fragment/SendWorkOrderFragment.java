package com.einyun.app.pms.sendorder.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.db.entity.Distribute;
import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.widget.RecyclerViewNoBugLinearLayoutManager;
import com.einyun.app.common.utils.FormatUtil;
import com.einyun.app.common.utils.RecyclerViewAnimUtil;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrder;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.library.resource.workorder.net.request.PageRquest;
import com.einyun.app.pms.sendorder.BR;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.FragmentSendWorkOrderBinding;
import com.einyun.app.pms.sendorder.databinding.ItemWorkSendBinding;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_DONE;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_PENDING;

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
public class SendWorkOrderFragment extends BaseViewModelFragment<FragmentSendWorkOrderBinding, SendOrderViewModel> implements ItemClickListener<Distribute> {
    //    private SendOrderAdapter adapter;//适配器
    RVPageListAdapter<ItemWorkSendBinding, Distribute> adapter;
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    public static SendWorkOrderFragment newInstance(Bundle bundle) {
        SendWorkOrderFragment fragment = new SendWorkOrderFragment();
        fragment.setArguments(bundle);
        Logger.d("setBundle->"+bundle.getString(RouteKey.KEY_FRAGEMNT_TAG));
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_send_work_order;
    }


    @Override
    protected void init() {
        super.init();

    }

    protected String getFragmentTag(){
        return getArguments().getString(RouteKey.KEY_FRAGEMNT_TAG);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPagingData();
    }

    @Override
    protected void setUpView() {
        binding.sendOrderRef.setOnRefreshListener(() -> {
            binding.sendOrderRef.setRefreshing(false);
//            loadPagingData();
            viewModel.refresh();
        });
        binding.workSendList.addItemDecoration(new SpacesItemDecoration(30));

    }

    @Override
    protected void setUpData() {
        //停止刷新
        LiveEventBus.get(LiveDataBusKey.STOP_REFRESH,Boolean.class).observe(getActivity(), shown -> {
            if(!shown){
                binding.sendOrderRef.setRefreshing(false);
            }
        });

//        //切换筛选条件
//        viewModel.getLiveEvent().observe(getActivity(), status -> {
//            if(status.isRefresShown()){
//                viewModel.refresh();
//            }
//        });
        RecyclerView mRecyclerView = binding.workSendList;
        RecyclerViewNoBugLinearLayoutManager mLayoutManager = new RecyclerViewNoBugLinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (adapter == null) {
            adapter = new RVPageListAdapter<ItemWorkSendBinding, Distribute>(getActivity(), BR.workOrder, mDiffCallback) {

                @Override
                public void onBindItem(ItemWorkSendBinding binding, Distribute distribute) {
                    if(FRAGMENT_SEND_OWRKORDER_DONE.equals(getFragmentTag())){
                        binding.itemResendRe.setVisibility(View.GONE);
                    }
                    binding.itemResendRe.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ARouter.getInstance()
                                    .build(RouterUtils.ACTIVITY_RESEND_ORDER)
                                    .withString(RouteKey.KEY_TASK_ID,distribute.getTaskId())
                                    .withString(RouteKey.KEY_ORDER_ID,distribute.getID_())
                                    .withString(RouteKey.KEY_DIVIDE_ID,distribute.getF_DIVIDE_ID())
                                    .withString(RouteKey.KEY_PROJECT_ID,distribute.getF_PROJECT_ID())
                                    .navigation();
                        }
                    });
                    binding.selectOrderTime.setText(FormatUtil.formatDate(distribute.getCreateTime()));
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_work_send;
                }
            };
        }
        RecyclerViewAnimUtil.getInstance().closeDefaultAnimator(binding.workSendList);
        binding.workSendList.setAdapter(adapter);
        adapter.setOnItemClick(this);
    }

    private void loadPagingData() {
        String fragmentTag=getFragmentTag();
        if(viewModel.getOrgModel()!=null){
            viewModel.getRequest().setDivideId(viewModel.getOrgModel().getId());
        }
        viewModel.getRequest().setUserId(userModuleService.getUserId());
        if(fragmentTag.equals(FRAGMENT_SEND_OWRKORDER_PENDING)){
            viewModel.loadPadingData(viewModel.getRequest()).observe(this, dataBeans -> {
                adapter.submitList(dataBeans);
                adapter.notifyDataSetChanged();
            });
        }else{
            viewModel.loadDonePagingData(viewModel.getRequest()).observe(this, dataBeans -> {
                adapter.submitList(dataBeans);
                adapter.notifyDataSetChanged();
            });
        }
    }

    @Override
    protected SendOrderViewModel initViewModel() {
        return new ViewModelProvider(getActivity(), new SendOdViewModelFactory()).get(SendOrderViewModel.class);
    }

    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<Distribute> mDiffCallback = new DiffUtil.ItemCallback<Distribute>() {

        @Override
        public boolean areItemsTheSame(@NonNull Distribute oldItem, @NonNull Distribute newItem) {
            return oldItem.getID_().equals(newItem.getID_());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Distribute oldItem, @NonNull Distribute newItem) {
            return oldItem.getID_().equals(newItem.getID_());
        }

        @Nullable
        @Override
        public Object getChangePayload(@NonNull Distribute oldItem, @NonNull Distribute newItem) {
            return oldItem.getID_().equals(newItem.getID_());
        }
    };


    /**
     * 列表Item 点击，跳转进入详情
     * 代办详情进入(taskId)，已办详情(taskNodeTd,proInsId)
     * @param veiw
     * @param data
     */
    @Override
    public void onItemClicked(View veiw, Distribute data) {
            ARouter.getInstance().build(RouterUtils.ACTIVITY_SEND_ORDER_DETAIL)
                    .withString(RouteKey.KEY_ORDER_ID,data.getID_())
                    .withString(RouteKey.KEY_TASK_NODE_ID,data.getTaskNodeId())
                    .withString(RouteKey.KEY_TASK_ID,data.getTaskId())
                    .withString(RouteKey.KEY_PRO_INS_ID,data.getProInsId())
                    .withString(RouteKey.KEY_FRAGEMNT_TAG,getFragmentTag())
                    .withString(RouteKey.KEY_PRO_INS_ID,data.getProInsId())
                    .navigation();
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = space;
        }
    }

    /**
     * 超出10个字显示省略号
     * */
    public String LimitText(String txts){
        if (txts.length()>10){
            return txts.substring(0,10)+"...";
        }else {
            return txts;
        }

    }
}
