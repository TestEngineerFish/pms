package com.einyun.app.pms.notice.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.adapters.AdapterViewBindingAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.activity.X5WebViewActivity;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.utils.SpacesItemDecoration;
import com.einyun.app.library.mdm.model.NoticeModel;
import com.einyun.app.library.mdm.net.request.NoticeListPageRequest;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.notice.R;
import com.einyun.app.pms.notice.databinding.ActivityNoticeListBinding;
import com.einyun.app.pms.notice.databinding.ItemNoticeOutBinding;
import com.einyun.app.pms.notice.viewmodel.NoticeViewModel;
import com.einyun.app.pms.notice.viewmodel.ViewModelFactory;
import com.einyun.app.pms.notice.widget.RVPageListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Route(path = RouterUtils.ACTIVITY_NOTICE_LIST)
public class NoticeListActivity extends BaseHeadViewModelActivity<ActivityNoticeListBinding, NoticeViewModel> implements ItemClickListener<NoticeModel>, PeriodizationView.OnPeriodSelectListener {
    RVPageListAdapter<ItemNoticeOutBinding, NoticeModel> adapter;
    List<NoticeModel> noticeModels = new ArrayList<>();
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;

    @Override
    public int getLayoutId() {
        return R.layout.activity_notice_list;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.txt_notice);
        adapter = new RVPageListAdapter<ItemNoticeOutBinding, NoticeModel>(this, com.einyun.app.pms.notice.BR.notice, mDiffCallback) {
            @Override
            public void onBindItem(ItemNoticeOutBinding binding, NoticeModel model, NoticeModel modelLast) {
                if (modelLast == null) {
                    binding.outDate.setVisibility(View.VISIBLE);
                } else {
                    if (model.getReleaseTime().substring(0, 10).equals(modelLast.getReleaseTime().substring(0, 10))) {
                        binding.outDate.setVisibility(View.GONE);
                    } else {
                        binding.outDate.setVisibility(View.VISIBLE);
                    }
                }
                binding.tvTitle.setText(model.getTitle());
                binding.dateTxt.setText(model.getReleaseTime().substring(8, 10));
                if (getCurrentYear().equals(model.getReleaseTime().substring(0, 4))) {
                    binding.yearTxt.setVisibility(View.GONE);
                } else {
                    if (modelLast != null) {
                        if (model.getReleaseTime().substring(0, 4).equals(modelLast.getReleaseTime().substring(0, 4))) {
                            binding.yearTxt.setVisibility(View.GONE);
                        } else {
                            binding.yearTxt.setVisibility(View.VISIBLE);
                        }
                    } else {
                        binding.yearTxt.setVisibility(View.VISIBLE);
                    }
                }
                binding.yearTxt.setText(model.getReleaseTime().substring(0, 4) + "年");
                binding.monthTxt.setText(model.getReleaseTime().substring(5, 7) + "月");
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_notice_out;
            }

        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.noticeListOut.setLayoutManager(layoutManager);
        binding.noticeListOut.setAdapter(adapter);
        binding.noticeListOut.addItemDecoration(new SpacesItemDecoration(0, 0, 20, 0));
        adapter.setOnItemClick(this);

        binding.llOrderTabPeroidLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出园区view
                PeriodizationView periodizationView = new PeriodizationView();
                periodizationView.setPeriodListener(NoticeListActivity.this::onPeriodSelectListener);
                periodizationView.show(getSupportFragmentManager(), "");
            }
        });
        binding.llOrerTabSelectLn.setOnClickListener(v -> {
            selectTime();
        });
    }

    TimePickerView pvTime;

    public void selectTime() {
        if (pvTime == null) {
            //时间选择器
            pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    binding.setConditionSelected(true);
                    String year = String.valueOf(date.getYear() + 1900);
                    String month = String.valueOf(date.getMonth() + 1);
                    String time = year + "-" + month;
                    request.setRelease_time(time);
                    binding.tvSelectSelected.setText(time);
                    loadPagingData();
                }
            }).setType(new boolean[]{true, true, false, false, false, false})// 默认全部显示
                    .setRangDate(null, Calendar.getInstance())
                    .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                    .build();
        }

        pvTime.show();
    }

    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        request.setOrg_id(orgModel.getId());
        binding.tvPeriodSelected.setText(orgModel.getName());
        binding.setPeriodSelected(true);
        loadPagingData();
    }

    private String divides;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initData() {
        super.initData();

        for (int i = 0; i < 1; i++) {
            noticeModels.add(new NoticeModel());
        }
        List<String> divideCodes = userModuleService.getDivideCodes();
        StringBuilder builder = new StringBuilder();
        for (String divide : divideCodes) {
            builder.append(",").append(divide);
        }
        if (builder.length() > 1) {
            divides = builder.substring(1);
        }
        request.setOrg_id(divides);
//        adapter.setDataList(noticeModels);
        loadPagingData();
    }

    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }

    NoticeListPageRequest request = new NoticeListPageRequest();

    public void loadPagingData() {
        //初始化数据，LiveData自动感知，刷新页面
        viewModel.loadPagingData(request, binding.pageState).observe(this, dataBeans -> {
            adapter.submitList(dataBeans);
        });
    }

    @Override
    protected NoticeViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this, new ViewModelFactory()).get(NoticeViewModel.class);
    }

    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<NoticeModel> mDiffCallback = new DiffUtil.ItemCallback<NoticeModel>() {

        @Override
        public boolean areItemsTheSame(@NonNull NoticeModel oldItem, @NonNull NoticeModel newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull NoticeModel oldItem, @NonNull NoticeModel newItem) {
            return oldItem == newItem;
        }
    };

    @Override
    protected void initListener() {
        super.initListener();
        binding.swipeRefresh.setColorSchemeColors(getColorPrimaryAc());
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefresh.setRefreshing(false);
                loadPagingData();
            }
        });
    }

    /**
     * 获取主题颜色
     *
     * @return
     */
    public int getColorPrimaryAc() {
        TypedValue typedValue = new TypedValue();
        this.getTheme().resolveAttribute(com.einyun.app.base.R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }


    @Override
    public void onItemClicked(View veiw, NoticeModel data) {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_NOTICE_DETAIL)
                .withString(RouteKey.KEY_ID, data.getId())
                .withString(RouteKey.KEY_WEB_TITLE, "公告详情")
                .navigation();
    }
}
