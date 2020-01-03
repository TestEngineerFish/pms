package com.einyun.app.pms.disqualified.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.utils.ClickProxy;
import com.einyun.app.library.uc.usercenter.model.OrgModel;

import com.einyun.app.pms.disqualified.BR;
import com.einyun.app.pms.disqualified.R;
import com.einyun.app.pms.disqualified.databinding.FragmentDisqualifiedViewModuleBinding;
import com.einyun.app.pms.disqualified.databinding.ItemDisqualifiedListBinding;
import com.einyun.app.pms.disqualified.model.DisqualifiedItemModel;
import com.einyun.app.pms.disqualified.ui.DisqualifiedViewModuleActivity;
import com.einyun.app.pms.disqualified.viewmodel.DisqualifiedFragmentViewModel;
import com.einyun.app.pms.disqualified.viewmodel.DisqualifiedViewModelFactory;
import com.einyun.app.pms.disqualified.widget.DisqualifiedTypeSelectPopWindow;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_COPY_ME;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_HAVE_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FEED_BACK;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TRANSFERRED_TO;


/**
 * @ProjectName: pms_old
 * @Package: com.einyun.app.pms.sendorder.ui
 * @ClassName: SendWorkOrderFragment
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2019/11/26 14:37
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/26 14:37
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class DisqualifiedViewModuleFragment extends BaseViewModelFragment<FragmentDisqualifiedViewModuleBinding, DisqualifiedFragmentViewModel>  implements ItemClickListener<DisqualifiedItemModel>,PeriodizationView.OnPeriodSelectListener, DisqualifiedTypeSelectPopWindow.OnItemClickListener {
    RVPageListAdapter<ItemDisqualifiedListBinding, DisqualifiedItemModel> adapter;
    private DisqualifiedViewModuleActivity activity;
    private String divideId="";
    private String divideName="";
    private int mPosition=-1;
    private String cate="";
    private String blockName;
    private DisqualifiedTypeSelectPopWindow inquiriesTypeSelectPopWindow;
    private PeriodizationView periodizationView;

    public static DisqualifiedViewModuleFragment newInstance(Bundle bundle) {
        DisqualifiedViewModuleFragment fragment = new DisqualifiedViewModuleFragment();
        fragment.setArguments(bundle);
        Logger.d("setBundle->"+bundle.getString(RouteKey.KEY_FRAGEMNT_TAG));
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_disqualified_view_module;
    }


    @Override
    protected void init() {
        super.init();

    }

    protected String getFragmentTag(){
        return getArguments().getString(RouteKey.KEY_FRAGEMNT_TAG);
    }



    @Override
    protected void setUpView() {
        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.swipeRefresh.setRefreshing(false);
           });
        binding.list.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false));
        binding.list.setAdapter(adapter);
        LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).observe(this, new Observer<Boolean>() {

                    @Override
                    public void onChanged(Boolean aBoolean) {
                        Log.e("onChanged", "onChanged: "+aBoolean);
                    }
                });
//        blockName = (String) SPUtils.get(CommonApplication.getInstance(), SPKey.KEY_BLOCK_NAME, "");
//        divideId = (String) SPUtils.get(CommonApplication.getInstance(), SPKey.KEY_BLOCK_ID, "");
//        if (!blockName.isEmpty()) {
//            binding.tvDivide.setTextColor(getResources().getColor(R.color.blueTextColor));
//            binding.ivTriangleDivide.setImageResource(R.drawable.iv_approval_sel_type_blue);
//            binding.tvDivide.setText(blockName);
//        }
    }

    @Override
    protected void setUpData() {
        binding.setCallBack(this);
        if(adapter==null){
            adapter=new RVPageListAdapter<ItemDisqualifiedListBinding, DisqualifiedItemModel>(getActivity(), com.einyun.app.pms.disqualified.BR.callBack,mDiffCallback){
                //                private static final String TAG = "ApprovalViewModelFragme";
                @Override
                public void onBindItem(ItemDisqualifiedListBinding binding, DisqualifiedItemModel inquiriesItemModule) {

                }
                @Override
                public int getLayoutId() {
                    return R.layout.item_disqualified_list;
                }
            };
        }
        binding.list.setAdapter(adapter);
        adapter.setOnItemClick(this);
        activity = (DisqualifiedViewModuleActivity) getActivity();
        switch (getFragmentTag()) {
            case FRAGMENT_TO_FOLLOW_UP://待跟进

                break;
        }

    }

//    private void loadPagingData(InquiriesRequestBean requestBean, String  tag){
////        初始化数据，LiveData自动感知，刷新页面
//        viewModel.loadPadingData(requestBean,tag).observe(this, dataBeans -> adapter.submitList(dataBeans));
//
//    }

    @Override
    protected DisqualifiedFragmentViewModel initViewModel() {
        return new ViewModelProvider(getActivity(), new DisqualifiedViewModelFactory()).get(DisqualifiedFragmentViewModel.class);
    }

    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<DisqualifiedItemModel> mDiffCallback = new DiffUtil.ItemCallback<DisqualifiedItemModel>() {


        @Override
        public boolean areItemsTheSame(@NonNull DisqualifiedItemModel oldItem, @NonNull DisqualifiedItemModel newItem) {
            return oldItem==newItem;
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull DisqualifiedItemModel oldItem, @NonNull DisqualifiedItemModel newItem) {
            return oldItem==newItem;
        }
    };
    /*
     * 搜索按钮点击
     * */
    public void onSearchClick(){
      ARouter.getInstance().build(RouterUtils.ACTIVITY_PROPERTY).navigation();
    }
    /*
     * 筛选按钮点击
     * */
    public void onInstallmentClick(){
//        DisqualifiedViewModuleActivity activity = (DisqualifiedViewModuleActivity) getActivity();
//        if (activity.mInquiriesTypesModule==null||activity.mInquiriesTypesModule.size()==0) {
//
//            return;
//        }
        //TODO 数据源
        inquiriesTypeSelectPopWindow = new DisqualifiedTypeSelectPopWindow(getActivity(), new ArrayList<>(),mPosition);
        inquiriesTypeSelectPopWindow.setOnItemClickListener(this);
        if (!inquiriesTypeSelectPopWindow.isShowing()) {
            inquiriesTypeSelectPopWindow.showAsDropDown(binding.llTableLine);
        }
    }
    /*
     * 分期按钮点击
     * */
    public void onPlotClick(){
        //弹出分期view
        if (periodizationView==null) {

            periodizationView = new PeriodizationView();
        }
        if (!periodizationView.isVisible()) {
            periodizationView.setPeriodListener(DisqualifiedViewModuleFragment.this::onPeriodSelectListener);
            periodizationView.show(getActivity().getSupportFragmentManager(),"");
        }
    }
    /**
     *分期返回
     */
    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        divideId = orgModel.getId();
        divideName = orgModel.getName();
        binding.tvDivide.setText(divideName);
        binding.tvDivide.setTextColor(getResources().getColor(R.color.blueTextColor));
//        binding.ivTriangleDivide.setImageResource(R.drawable.iv_approval_sel_type_blue);

    }
    /**
     * item点击
     */
    @Override
    public void onItemClicked(View veiw, DisqualifiedItemModel data) {

    }

    private static final String TAG = "CustomerInquiriesViewMo";
    /**
     * 筛选过后的position
     */
    @Override
    public void onData(String dataKey,int position) {
        Log.e("onData", "onData:dataKey=== "+dataKey );
        cate = dataKey;
        mPosition = position;
        if (mPosition==-1) {
            binding.tvSelect.setTextColor(getResources().getColor(R.color.greyTextColor));
            binding.ivTriangleSelect.setImageResource(R.drawable.iv_approval_sel_type);

        }else {
            binding.tvSelect.setTextColor(getResources().getColor(R.color.blueTextColor));
//            binding.ivTriangleSelect.setImageResource(R.drawable.iv_approval_sel_type_blue);

        }
//        loadPagingData(viewModel.getRequestBean(1,10,cate,"",divideId),getFragmentTag());
    }

}
