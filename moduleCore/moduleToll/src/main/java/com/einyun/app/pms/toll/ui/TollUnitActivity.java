package com.einyun.app.pms.toll.ui;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.constants.SPKey;
import com.einyun.app.common.manager.BasicDataManager;
import com.einyun.app.common.manager.CustomEventTypeEnum;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.utils.ChineseSortUtilUnit;
import com.einyun.app.common.utils.HanziToPinyin;
import com.einyun.app.common.utils.LiveDataBusUtils;
import com.einyun.app.common.utils.UserUtil;
import com.einyun.app.library.mdm.model.DivideGrid;
import com.einyun.app.library.mdm.model.GridModel;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.toll.R;
import com.einyun.app.pms.toll.databinding.ActivityTollUnitBinding;
import com.einyun.app.pms.toll.databinding.ItemTollInListBinding;
import com.einyun.app.pms.toll.databinding.ItemTollOutListBinding;
import com.einyun.app.common.model.BuildModel;
import com.einyun.app.pms.toll.model.DivideNameModel;
import com.einyun.app.pms.toll.model.FeeModel;
import com.einyun.app.pms.toll.model.FeeRequset;
import com.einyun.app.pms.toll.viewmodel.TollViewModel;
import com.einyun.app.pms.toll.viewmodel.TollViewModelFactory;
import com.einyun.app.pms.toll.widget.UnitCheckPopWindow;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.umeng.analytics.MobclickAgent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


@Route(path = RouterUtils.ACTIVITY_TOLL_UNIT)
public class TollUnitActivity extends BaseHeadViewModelActivity<ActivityTollUnitBinding, TollViewModel> implements PeriodizationView.OnPeriodSelectListener,
        ItemClickListener<BuildModel.GridRangeBean>,UnitCheckPopWindow.OnItemClickListener {
    RVBindingAdapter<ItemTollInListBinding, BuildModel.GridRangeBean> unitAdapter;
    private String divideId = "";
    private String divideName = "";
    private String feeDivideId;
    boolean isSearch = false;
    private BigDecimal mUnitToallFee = new BigDecimal("0");
    private BigDecimal mHouseToallFee = new BigDecimal("0");
    private int mUnitToallUsers;
    private int mHouseToallUsers;
    private int mPreviousToallUsers;
    private int mCurrentToallUsers;
    private int mNoToallUsers;

    private BigDecimal mToallFee = new BigDecimal("0");
    private int mToallUsers;
    private AlertDialog alertDialog;
    int txDefaultPosSwitchSuqence = 0;
    private String blockName = "";
    private boolean flag1;
    private List<FeeModel.DataBean.FeeListBean> feeListNew = new ArrayList<>();
    private int isShowFee;
    private FeeRequset feeRequset;
    private List<GridModel> gridsSearchs;
    private String searchContent = "";
    private boolean isBuildSort;
    private boolean isFeeSort;
    private String sortFlag = "";
    public static final String BUIDL_DOWN = "BUIDL_DOWN";
    public static final String BUIDL_UP = "BUIDL_UP";
    public static final String FEE_DOWN = "FEE_DOWN";
    public static final String FEE_UP = "FEE_UP";
    @Autowired(name = RouteKey.KEY_BUILD_ID)
    String buildId;
    @Autowired(name = RouteKey.KEY_FEE_DIVIDE_ID)
    String mFeeDivideId;
    @Autowired(name = RouteKey.KEY_FEE_BUILD_NAME)
    String mBuildName;
    @Autowired(name = RouteKey.KEY_FEE_GRID_NAME)
    String mGridName;
    private List<BuildModel.GridRangeBean> mFeeBuildList = new ArrayList<>();
    private FeeRequset feeHouseRequset;
    private int feeType;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected TollViewModel initViewModel() {
        return new ViewModelProvider(this, new TollViewModelFactory()).get(TollViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_toll_unit;
    }
    @Override
    public void onBackOnClick(View view) {
        if (binding.llSearch.isShown()) {
            super.onBackOnClick(view);
        }else {
            finishAnim();
        }

    }

    @Override
    public void onBackPressed() {
        if (binding.llSearch.isShown()) {
//            super.onBackPressed();
        }else {
            finishAnim();
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sort(List<BuildModel.GridRangeBean> list) {

//        switch (sortFlag) {
//            case BUIDL_UP:
//
//                ChineseSortUtilUnit.transferListBuild(list);
//                break;
//            case BUIDL_DOWN:
//                ChineseSortUtilUnit.transferListBuildDown(list);
//                break;
////                                    return o2.getName().compareTo(o1.getName());//顺序
//        }
        if (sortFlag.equals("")) {
//            ChineseSortUtilUnit.transferListBuild(list);
            Collections.sort(list, new Comparator<BuildModel.GridRangeBean>() {
                @Override
                public int compare(BuildModel.GridRangeBean o1, BuildModel.GridRangeBean o2) {
                    if (o2.getFeeAmount().subtract(o1.getFeeAmount()).compareTo(BigDecimal.ZERO) > 0) {
                        return 1;
                    } else if (o2.getFeeAmount().subtract(o1.getFeeAmount()).compareTo(BigDecimal.ZERO) < 0) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }
    }
    /**
     * 搜索按钮
     */
    @Override
    public void onOptionClick(View view) {
        super.onOptionClick(view);
        isSearch = true;
        binding.etSearch.setText("");
        binding.llSearch.setVisibility(View.VISIBLE);
        binding.llUnit.setVisibility(View.GONE);
        binding.rlContainer.setVisibility(View.GONE);
        binding.headBar.getRoot().setVisibility(View.GONE);
        binding.llGrid.setVisibility(View.GONE);
        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getAction() == KeyEvent.KEYCODE_ENTER)) {
//                    getAllFeeBuilds(grids);
                    reFreshHouseData(feeHouseRequset);
//                    binding.llGrid.setVisibility(View.VISIBLE);
                    binding.llUnit.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
    }

    /**
     * 欠费排序
     */
    public void onFeeSort() {

        initSortView(true);
        if (isFeeSort) {
            isFeeSort = false;
            binding.ivFeeDown.setImageResource(R.drawable.iv_sort_blue_down);
            binding.ivFeeUp.setImageResource(R.drawable.iv_sort_grey_up);
            sortFlag = FEE_DOWN;
        } else {
            isFeeSort = true;
            binding.ivFeeDown.setImageResource(R.drawable.iv_sort_grey_down);
            binding.ivFeeUp.setImageResource(R.drawable.iv_sort_blue_up);
            sortFlag = FEE_UP;
        }
        binding.llFeeSort.setBackgroundResource(R.drawable.shape_rect_radius19_blue);
        binding.tvFee.setTextColor(getResources().getColor(R.color.blueTextColor));
//        unitAdapter.notifyDataSetChanged();
//        reFreshHouseData(feeHouseRequset);
        ArrayList<String> houseBuilds = new ArrayList<>();
        for (BuildModel.GridRangeBean gridRangeBean : mFeeCheckUnitList) {
            if (gridRangeBean.getChecked()==1) {

                houseBuilds.add(gridRangeBean.getId());
            }
        }
        if (houseBuilds.size()==0) {
            for (BuildModel.GridRangeBean gridRangeBean : this.mFeeUnitList) {
                houseBuilds.add(gridRangeBean.getId());
            }
        }
        FeeRequset unitCheckRequset = new FeeRequset();
        unitCheckRequset.setUntiId(houseBuilds);
        unitCheckRequset.setDivideId(mFeeDivideId);
        unitCheckRequset.setType(3);
        reFreshUnitCheckData(unitCheckRequset,feeType);
    }

    private void initSortView(boolean isSort) {
        sortFlag = "";
        binding.ivFeeDown.setImageResource(R.drawable.iv_sort_grey_down);
        binding.ivFeeUp.setImageResource(R.drawable.iv_sort_grey_up);
        binding.ivBuildDown.setImageResource(R.drawable.iv_sort_grey_down);
        binding.ivBuildUp.setImageResource(R.drawable.iv_sort_grey_up);
        binding.llSortBuild.setBackgroundResource(R.drawable.shape_white_big_radius_bg);
        binding.llFeeSort.setBackgroundResource(R.drawable.shape_white_big_radius_bg);
        binding.tvBuild.setTextColor(getResources().getColor(R.color.greyTextColor));
        binding.tvFee.setTextColor(getResources().getColor(R.color.greyTextColor));
        if (!isSort) {
            binding.tvAll.setText("全部");
        }

    }

    /**
     * 楼栋排序
     */
    public void onBuildSort() {
        initSortView(true);
        if (isBuildSort) {
            isBuildSort = false;
            binding.ivBuildDown.setImageResource(R.drawable.iv_sort_blue_down);
            binding.ivBuildUp.setImageResource(R.drawable.iv_sort_grey_up);
            sortFlag = BUIDL_DOWN;
        } else {
            isBuildSort = true;
            binding.ivBuildDown.setImageResource(R.drawable.iv_sort_grey_down);
            binding.ivBuildUp.setImageResource(R.drawable.iv_sort_blue_up);
            sortFlag = BUIDL_UP;
        }
        binding.llSortBuild.setBackgroundResource(R.drawable.shape_rect_radius19_blue);
        binding.tvBuild.setTextColor(getResources().getColor(R.color.blueTextColor));
//        unitAdapter.notifyDataSetChanged();
//        reFreshHouseData(feeHouseRequset);
        ArrayList<String> houseBuilds = new ArrayList<>();
        for (BuildModel.GridRangeBean gridRangeBean : mFeeCheckUnitList) {
            if (gridRangeBean.getChecked()==1) {

                houseBuilds.add(gridRangeBean.getId());
            }
        }
        if (houseBuilds.size()==0) {
            for (BuildModel.GridRangeBean gridRangeBean : this.mFeeUnitList) {
                houseBuilds.add(gridRangeBean.getId());
            }
        }
        FeeRequset unitCheckRequset = new FeeRequset();
        unitCheckRequset.setUntiId(houseBuilds);
        unitCheckRequset.setDivideId(mFeeDivideId);
        unitCheckRequset.setType(3);
        reFreshUnitCheckData(unitCheckRequset,feeType);
    }
    /**
     * 单元筛选弹框
     */
    public void onUnitSort() {

        binding.ivUnitDown.setImageResource(R.drawable.iv_sort_blue_down);
//        reFreshHouseData(feeHouseRequset);
        UnitCheckPopWindow  checkPopWindow = new UnitCheckPopWindow(this, mFeeUnitList);
        checkPopWindow.setOnItemClickListener(this);
        if (!checkPopWindow.isShowing()) {
            checkPopWindow.showAsDropDown(binding.rlUnit);
        }
    }
    /**
     * 单元筛选结果
     */
    @Override
    public void onData(List<BuildModel.GridRangeBean> mFeeUnitList) {
        Log.e(TAG, "onData: " );
//        this.mFeeUnitList=mFeeUnitList;
        StringBuffer name=new StringBuffer();
        mFeeCheckUnitList.clear();
        ArrayList<String> houseBuilds = new ArrayList<>();
        for (BuildModel.GridRangeBean gridRangeBean : mFeeUnitList) {
            if (gridRangeBean.getChecked()==1) {

                houseBuilds.add(gridRangeBean.getId());
                mFeeCheckUnitList.addAll(mFeeUnitList);

                name.append(","+gridRangeBean.getName());

            }

        }
        if (name.length()>0) {
            String substring = name.substring(1, name.length());
            binding.tvAll.setText(substring);
            binding.tvAll.setTextColor(getResources().getColor(R.color.blueTextColor));
            binding.rlUnit.setBackgroundResource(R.drawable.shape_rect_radius19_blue);
        }else {
            binding.rlUnit.setBackgroundResource(R.drawable.shape_white_big_radius_bg);
            binding.tvAll.setTextColor(getResources().getColor(R.color.greyTextColor));
            binding.tvAll.setText("全部");
        }

        if (houseBuilds.size()==0) {
            for (BuildModel.GridRangeBean gridRangeBean : this.mFeeUnitList) {
                houseBuilds.add(gridRangeBean.getId());
            }
            mFeeCheckUnitList.addAll(this.mFeeUnitList);
        }
        FeeRequset unitCheckRequset = new FeeRequset();
        unitCheckRequset.setUntiId(houseBuilds);
        unitCheckRequset.setDivideId(mFeeDivideId);
        unitCheckRequset.setType(3);
        reFreshUnitCheckData(unitCheckRequset,feeType);
    }
    /**
     * 搜索取消
     */

    public void onCancleClick() {
        binding.llSearch.setVisibility(View.GONE);
        binding.rlContainer.setVisibility(View.VISIBLE);
        binding.headBar.getRoot().setVisibility(View.VISIBLE);
        binding.llUnit.setVisibility(View.VISIBLE);
//        binding.llGrid.setVisibility(View.VISIBLE);
        binding.etSearch.setText("");
        isSearch = false;
//        getAllFeeBuilds(grids);
        ArrayList<String> houseBuilds = new ArrayList<>();
        for (BuildModel.GridRangeBean gridRangeBean : mFeeCheckUnitList) {
            if (gridRangeBean.getChecked()==1) {

                houseBuilds.add(gridRangeBean.getId());
            }
        }
        if (houseBuilds.size()==0) {
            for (BuildModel.GridRangeBean gridRangeBean : this.mFeeUnitList) {
                houseBuilds.add(gridRangeBean.getId());
            }
        }
        FeeRequset unitCheckRequset = new FeeRequset();
        unitCheckRequset.setUntiId(houseBuilds);
        unitCheckRequset.setDivideId(mFeeDivideId);
        unitCheckRequset.setType(3);
        reFreshHouseData(unitCheckRequset);
    }
    /**
     * 往年欠费
     */
    public void onFee1Click(){
        binding.llFeeSort.setVisibility(View.VISIBLE);
        feeType=1;
        initFeeView();
        binding.tvFee1.setTextColor(getResources().getColor(R.color.blueTextColor));
        binding.tvFeeUsers1.setTextColor(getResources().getColor(R.color.blueTextColor));
        binding.ivLine1.setBackgroundColor(getResources().getColor(R.color.blueTextColor));
        reFreshHouseData(feeHouseRequset);
//        unitAdapter.setDataList(mFeePreviousList);

    }

    private void initFeeView() {
        initSortView(false);
        binding.rlUnit.setBackgroundResource(R.drawable.shape_white_big_radius_bg);
        binding.tvAll.setTextColor(getResources().getColor(R.color.greyTextColor));
        mFeeCheckUnitList.clear();
        binding.tvFee1.setTextColor(getResources().getColor(R.color.blackTextColor));
        binding.ivLine1.setBackgroundColor(getResources().getColor(R.color.white));
        binding.tvFeeUsers1.setTextColor(getResources().getColor(R.color.blackTextColor));
        binding.tvFee2.setTextColor(getResources().getColor(R.color.blackTextColor));
        binding.ivLine2.setBackgroundColor(getResources().getColor(R.color.white));
        binding.tvFeeUsers2.setTextColor(getResources().getColor(R.color.blackTextColor));
        binding.tvFee3.setTextColor(getResources().getColor(R.color.blackTextColor));
        binding.ivLine3.setBackgroundColor(getResources().getColor(R.color.white));
        binding.tvFeeUsers3.setTextColor(getResources().getColor(R.color.blackTextColor));
        for (BuildModel.GridRangeBean gridRangeBean : mFeeUnitList) {
            gridRangeBean.setChecked(0);
        }
    }

    /**
     * 当年欠费
     */
    public void onFee2Click(){
        binding.llFeeSort.setVisibility(View.VISIBLE);
        feeType=2;
        initFeeView();
        binding.tvFee2.setTextColor(getResources().getColor(R.color.blueTextColor));
        binding.tvFeeUsers2.setTextColor(getResources().getColor(R.color.blueTextColor));
        binding.ivLine2.setBackgroundColor(getResources().getColor(R.color.blueTextColor));
//        unitAdapter.setDataList(mFeeCurrentList);
        reFreshHouseData(feeHouseRequset);
    }
    /**
     * 未欠费
     */
    public void onFee3Click(){
        binding.llFeeSort.setVisibility(View.GONE);
        feeType=3;
        initFeeView();
        binding.tvFee3.setTextColor(getResources().getColor(R.color.blueTextColor));
        binding.tvFeeUsers3.setTextColor(getResources().getColor(R.color.blueTextColor));
        binding.ivLine3.setBackgroundColor(getResources().getColor(R.color.blueTextColor));
//        unitAdapter.setDataList(mFeeNoList);
        reFreshHouseData(feeHouseRequset);
    }
    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(R.string.tv_toll_assant);
        setRightTxt(R.string.tv_change_trun);
        headBinding.tvRightTitle.setVisibility(View.GONE);
        setRightTxtColor(R.color.blueTextColor);
        binding.tvBuildName.setText(mBuildName+("(0户)"));
        setRightOption(R.mipmap.icon_search);
        binding.setCallBack(this);
        divideName = (String) SPUtils.get(CommonApplication.getInstance(), SPKey.KEY_BLOCK_NAME, "");
        divideId = (String) SPUtils.get(CommonApplication.getInstance(), SPKey.KEY_BLOCK_ID, "");
        if (!divideName.isEmpty()) {
            binding.tvDivide.setTextColor(getResources().getColor(R.color.blueTextColor));
//            binding.ivTriangleDivide.setImageResource(R.drawable.iv_approval_sel_type_blue);
            binding.tvDivide.setText(divideName);
        }
        /**
         * 付款成功刷新数据
         */
        LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                binding.llUnit.setVisibility(View.VISIBLE);
//                headBinding.tvRightTitle.setVisibility(View.VISIBLE);
                reFreshHouseData(feeHouseRequset);
            }
        });
        binding.unitList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int firstCompletelyVisibleItemPosition = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                    if (firstCompletelyVisibleItemPosition==0) {
                        binding.rlDivide.setVisibility(View.VISIBLE);
                    }else {
                        binding.rlDivide.setVisibility(View.GONE);
                    }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e(TAG, "onScrolled: "+dy );
                int firstCompletelyVisibleItemPosition = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (Math.abs(dy)>50) {
                    if (firstCompletelyVisibleItemPosition==0) {
                        binding.rlDivide.setVisibility(View.VISIBLE);
                    }else {
                        binding.rlDivide.setVisibility(View.GONE);
                    }
                }
//                Log.e(TAG, "onScrolled: "+dy );
//                if (dy>0) {
//                    binding.rlDivide.setVisibility(View.GONE);
//                }else {
//                    binding.rlDivide.setVisibility(View.VISIBLE);
//
//                }

            }
        });
    }
    /**
     * 单元筛选 刷新新数据 楼层排序 欠费排序公用
     *
     * @param feeHouseRequset
     */
    private void reFreshUnitCheckData(FeeRequset feeHouseRequset,int feeType) {
        showLoading();
        searchContent = binding.etSearch.getText().toString();
        viewModel.queryFeeInfo3(feeHouseRequset).observe(TollUnitActivity.this, model -> {

            mHouseToallUsers = 0;
            mHouseToallFee = new BigDecimal("0").setScale(0);
            mFeeHouseCheckList.clear();
            mFeePreviousList.clear();
            mFeeNoList.clear();
            mFeeCurrentList.clear();
            if (model.getData() == null || model.getData().getFeeList() == null) return;
            List<FeeModel.DataBean.FeeListBean> feeList = model.getData().getFeeList();
            for (FeeModel.DataBean.FeeListBean feeListBean : feeList) {
                BuildModel.GridRangeBean build = new BuildModel.GridRangeBean();
                build.setFeeAmount(feeListBean.getFeeAmount());
                build.setType(feeListBean.getType());
                for (BuildModel.GridRangeBean gridRangeBean : mFeeUnitList) {
                    if (gridRangeBean.getId().equals(feeListBean.getUnitId())) {

                        build.setName(gridRangeBean.getName()+"-"+feeListBean.getName());
                    }
                }
                build.setCode(feeListBean.getName());
                build.setId(feeListBean.getHouseId());
                build.setHouseTotal(feeListBean.getHouseTotal());
                build.setArrearsLevel(feeListBean.getArrearsLevel());
                if (!mFeeHouseCheckList.contains(build)) {
                    if (searchContent.equals("")) {
                        if (!mFeeHouseCheckList.contains(build)) {
                            mFeeHouseCheckList.add(build);
                            if (build.getArrearsLevel()==1) {
                                if (!mFeePreviousList.contains(build)) {
                                    mFeePreviousList.add(build);
                                }
                            }else if (build.getArrearsLevel()==2){
                                if (!mFeeCurrentList.contains(build)) {
                                    mFeeCurrentList.add(build);
                                }
                            }else if (build.getArrearsLevel()==3){
                                if (!mFeeNoList.contains(build)) {
                                    mFeeNoList.add(build);
                                }
                            }

                        }
                    } else {
                        if (build.getName().contains(searchContent)) {
                            if (!mFeeHouseCheckList.contains(build)) {
                                mFeeHouseCheckList.add(build);
                            }
                        }
                    }
                    if (feeListBean.getArrearsLevel() == 1 || feeListBean.getArrearsLevel() == 2) {

                        mHouseToallUsers += 1;
                    }
                    mHouseToallFee = mHouseToallFee.add(new BigDecimal("1"));
                }
            }

            binding.tvHouseToallUsers.setText("总  户  数：" + mHouseToallFee);
            binding.tvHouseToallFee.setText("欠费户数：" + mHouseToallUsers);
//            /**
//             * 默认排序
//             */
//            Collections.sort(mFeeHouseCheckList, new Comparator<BuildModel.GridRangeBean>() {
//                @Override
//                public int compare(BuildModel.GridRangeBean o1, BuildModel.GridRangeBean o2) {
//
//                            return HanziToPinyin.getStr(o2.getName()).compareTo(HanziToPinyin.getStr(o1.getName()));//顺序
//                }
//            });
            switch (feeType) {
                case 3:
                    sortAccondType(mFeeNoList);
                    break;
                case 1:
                    sortAccondType(mFeePreviousList);
//                    /**
//                     * 根据外面点击楼栋 欠费排序
//                     */
//                    Collections.sort(mFeePreviousList, new Comparator<BuildModel.GridRangeBean>() {
//                        @Override
//                        public int compare(BuildModel.GridRangeBean o1, BuildModel.GridRangeBean o2) {
//                            switch (sortFlag) {
//                                case BUIDL_UP:
//                                    return HanziToPinyin.getStr(o2.getName()).compareTo(HanziToPinyin.getStr(o1.getName()));//顺序
//                                case BUIDL_DOWN:
//                                    return HanziToPinyin.getStr(o1.getName()).compareTo(HanziToPinyin.getStr(o2.getName()));//顺序
//                                case FEE_DOWN:
////                                    BigDecimal subtract = o1.getFeeAmount().subtract(o2.getFeeAmount());
////                                    int i = o1.getFeeAmount().subtract(o2.getFeeAmount()).compareTo(BigDecimal.ZERO);
//                                    if (o1.getFeeAmount().subtract(o2.getFeeAmount()).compareTo(BigDecimal.ZERO) > 0) {
//                                        return 1;
//                                    } else if (o1.getFeeAmount().subtract(o2.getFeeAmount()).compareTo(BigDecimal.ZERO) < 0) {
//                                        return -1;
//                                    } else {
//                                        return 0;
//                                    }
////                                    return (int) (Double.parseDouble(String.valueOf(o1.getFeeAmount())) - Double.parseDouble(String.valueOf(o2.getFeeAmount())));
//                                case FEE_UP:
//                                    if (o2.getFeeAmount().subtract(o1.getFeeAmount()).compareTo(BigDecimal.ZERO) > 0) {
//                                        return 1;
//                                    } else if (o2.getFeeAmount().subtract(o1.getFeeAmount()).compareTo(BigDecimal.ZERO) < 0) {
//                                        return -1;
//                                    } else {
//                                        return 0;
//                                    }
////                                case FEE_DOWN:
////                                    return (int) (Double.parseDouble(String.valueOf(o1.getFeeAmount())) - Double.parseDouble(String.valueOf(o2.getFeeAmount())));
////                                case FEE_UP:
////                                    return (int) (Double.parseDouble(String.valueOf(o2.getFeeAmount())) - Double.parseDouble(String.valueOf(o1.getFeeAmount())));
//                            }
//                            return o2.getFeeAmount().compareTo(o1.getFeeAmount());//顺序
//                        }
//                    });
//                    unitAdapter.setDataList(mFeePreviousList);
                    break;
                case 2:
                    sortAccondType(mFeeCurrentList);
//                    /**
//                     * 根据外面点击楼栋 欠费排序
//                     */
//                    Collections.sort(mFeeCurrentList, new Comparator<BuildModel.GridRangeBean>() {
//                        @Override
//                        public int compare(BuildModel.GridRangeBean o1, BuildModel.GridRangeBean o2) {
//                            switch (sortFlag) {
//                                case BUIDL_UP:
//                                    return HanziToPinyin.getStr(o2.getName()).compareTo(HanziToPinyin.getStr(o1.getName()));//顺序
//                                case BUIDL_DOWN:
//                                    return HanziToPinyin.getStr(o1.getName()).compareTo(HanziToPinyin.getStr(o2.getName()));//顺序
//                                case FEE_DOWN:
////                                    BigDecimal subtract = o1.getFeeAmount().subtract(o2.getFeeAmount());
////                                    int i = o1.getFeeAmount().subtract(o2.getFeeAmount()).compareTo(BigDecimal.ZERO);
//                                    if (o1.getFeeAmount().subtract(o2.getFeeAmount()).compareTo(BigDecimal.ZERO) > 0) {
//                                        return 1;
//                                    } else if (o1.getFeeAmount().subtract(o2.getFeeAmount()).compareTo(BigDecimal.ZERO) < 0) {
//                                        return -1;
//                                    } else {
//                                        return 0;
//                                    }
////                                    return (int) (Double.parseDouble(String.valueOf(o1.getFeeAmount())) - Double.parseDouble(String.valueOf(o2.getFeeAmount())));
//                                case FEE_UP:
//                                    if (o2.getFeeAmount().subtract(o1.getFeeAmount()).compareTo(BigDecimal.ZERO) > 0) {
//                                        return 1;
//                                    } else if (o2.getFeeAmount().subtract(o1.getFeeAmount()).compareTo(BigDecimal.ZERO) < 0) {
//                                        return -1;
//                                    } else {
//                                        return 0;
//                                    }
////                                    if (Double.parseDouble(String.valueOf(o2.getFeeAmount())) - Double.parseDouble(String.valueOf(o1.getFeeAmount()))>0) {
////                                        return 1;
////                                    }else {
////                                        return  -1;
////                                    }
////                                case FEE_DOWN:
////                                    return (int) (Double.parseDouble(String.valueOf(o1.getFeeAmount())) - Double.parseDouble(String.valueOf(o2.getFeeAmount())));
////                                case FEE_UP:
////                                    return (int) (Double.parseDouble(String.valueOf(o2.getFeeAmount())) - Double.parseDouble(String.valueOf(o1.getFeeAmount())));
//                            }
//                            return o2.getFeeAmount().compareTo(o1.getFeeAmount());//顺序
//                        }
//                    });
//                    unitAdapter.setDataList(mFeeCurrentList);
                    break;
            }
            List<BuildModel.GridRangeBean> dataList = unitAdapter.getDataList();
            if (dataList!=null) {
                if (dataList.size()==0) {
                    binding.rlEmpty.setVisibility(View.VISIBLE);
                }else {
                    binding.rlEmpty.setVisibility(View.GONE);

                }
            }

        });
    }

    private void sortAccondType(List<BuildModel.GridRangeBean> list) {
        Collections.sort(list, new Comparator<BuildModel.GridRangeBean>() {
            @Override
            public int compare(BuildModel.GridRangeBean o1, BuildModel.GridRangeBean o2) {
                if (o2.getFeeAmount().subtract(o1.getFeeAmount()).compareTo(BigDecimal.ZERO) > 0) {
                    return 1;
                } else if (o2.getFeeAmount().subtract(o1.getFeeAmount()).compareTo(BigDecimal.ZERO) < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }


        });
        /**
         * 根据外面点击楼栋 欠费排序
         */
        Collections.sort(list, new Comparator<BuildModel.GridRangeBean>() {
            @Override
            public int compare(BuildModel.GridRangeBean o1, BuildModel.GridRangeBean o2) {
                switch (sortFlag) {
                    case BUIDL_UP:



                        return Integer.parseInt(viewModel.getNum(o1.getCode()))-Integer.parseInt(viewModel.getNum(o2.getCode()));//顺序
                    case BUIDL_DOWN:
                        return Integer.parseInt(viewModel.getNum(o2.getCode()))-Integer.parseInt(viewModel.getNum(o1.getCode()));//顺序
                    case FEE_DOWN:
                        if (o2.getFeeAmount().subtract(o1.getFeeAmount()).compareTo(BigDecimal.ZERO) > 0) {
                            return 1;
                        } else if (o2.getFeeAmount().subtract(o1.getFeeAmount()).compareTo(BigDecimal.ZERO) < 0) {
                            return -1;
                        } else {
                            return 0;
                        }
                    case FEE_UP:
                        if (o1.getFeeAmount().subtract(o2.getFeeAmount()).compareTo(BigDecimal.ZERO) > 0) {
                            return 1;
                        } else if (o1.getFeeAmount().subtract(o2.getFeeAmount()).compareTo(BigDecimal.ZERO) < 0) {
                            return -1;
                        } else {
                            return 0;
                        }
                }
                return HanziToPinyin.getStr(o1.getCode()).compareTo(HanziToPinyin.getStr(o2.getCode()));//顺序
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sort(list);
        }
        unitAdapter.setDataList(list);
    }

    /**
     * 刷新房间列表数据
     *
     * @param feeHouseRequset
     */
    private void reFreshHouseData(FeeRequset feeHouseRequset) {
        searchContent = binding.etSearch.getText().toString();
        showLoading();
        viewModel.repository.getFeeInfo(feeHouseRequset, new CallBack<FeeModel>() {
            @Override
            public void call(FeeModel data) {
             runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                     hideLoading();
                     mHouseToallUsers = 0;
                     mPreviousToallUsers = 0;
                     mCurrentToallUsers = 0;
                     mNoToallUsers = 0;
                     mHouseToallFee = new BigDecimal("0").setScale(0);
                     mFeeHouseList.clear();
                     mFeePreviousList.clear();
                     mFeeCurrentList.clear();
                     mFeeNoList.clear();
                     if (data.getData() == null || data.getData().getFeeList() == null) return;
                     List<FeeModel.DataBean.FeeListBean> feeList = data.getData().getFeeList();
                     for (FeeModel.DataBean.FeeListBean feeListBean : feeList) {
                         BuildModel.GridRangeBean build = new BuildModel.GridRangeBean();
                         build.setFeeAmount(feeListBean.getFeeAmount());
                         build.setType(feeListBean.getType());
                         for (BuildModel.GridRangeBean gridRangeBean : mFeeUnitList) {
                             if (gridRangeBean.getId().equals(feeListBean.getUnitId())) {

                                 build.setName(gridRangeBean.getName()+"-"+feeListBean.getName());
                             }
                         }
                         build.setCode(feeListBean.getName());
                         build.setId(feeListBean.getHouseId());
                         build.setHouseTotal(feeListBean.getHouseTotal());
                         build.setArrearsLevel(feeListBean.getArrearsLevel());
                         if (!mFeeHouseList.contains(build)) {
                             if (searchContent.equals("")) {
                                 if (!mFeeHouseList.contains(build)) {
                                     mFeeHouseList.add(build);
                                     if (build.getArrearsLevel()==1) {
                                         if (!mFeePreviousList.contains(build)) {
                                             mFeePreviousList.add(build);
                                         }
                                     }else if (build.getArrearsLevel()==2){
                                         if (!mFeeCurrentList.contains(build)) {
                                             mFeeCurrentList.add(build);
                                         }
                                     }else if (build.getArrearsLevel()==3){
                                         if (!mFeeNoList.contains(build)) {
                                             mFeeNoList.add(build);
                                         }
                                     }
                                 }
                             } else {
                                 if (build.getName().contains(searchContent)) {
                                     if (!mFeeHouseList.contains(build)) {
                                         mFeeHouseList.add(build);
                                     }
                                 }
                             }
                             if (feeListBean.getArrearsLevel() == 1 || feeListBean.getArrearsLevel() == 2) {
                                 mHouseToallUsers += 1;
                             }
                             switch (feeListBean.getArrearsLevel()) {
                                 case 1://往年欠费
                                     mPreviousToallUsers+=1;
                                     break;
                                 case 2://今年欠费
                                     mCurrentToallUsers+=1;

                                     break;
                                 case 3://暂无欠费
                                     mNoToallUsers+=1;

                                     break;
                             }
                             mHouseToallFee = mHouseToallFee.add(new BigDecimal("1"));
                         }
                     }

                     binding.tvHouseToallUsers.setText("总  户  数：" + mHouseToallFee);
                     binding.tvHouseToallFee.setText("欠费户数：" + mHouseToallUsers);
                     binding.tvFeeUsers1.setText(mPreviousToallUsers+"户");
                     binding.tvFeeUsers2.setText(mCurrentToallUsers+"户");
                     binding.tvFeeUsers3.setText(mNoToallUsers+"户");
                     binding.tvBuildName.setText(mBuildName+"("+mHouseToallFee+"户)");
//                     /**
//                      * 默认排序
//                      */
//
//                     Collections.sort(mFeeHouseList, new Comparator<BuildModel.GridRangeBean>() {
//                         @Override
//                         public int compare(BuildModel.GridRangeBean o1, BuildModel.GridRangeBean o2) {
//
//                                     return HanziToPinyin.getStr(o2.getName()).compareTo(HanziToPinyin.getStr(o1.getName()));//顺序
//
//                         }
//                     });
                     Collections.sort(mFeeHouseList, new Comparator<BuildModel.GridRangeBean>() {
                         @Override
                         public int compare(BuildModel.GridRangeBean o1, BuildModel.GridRangeBean o2) {
                                     if (o2.getFeeAmount().subtract(o1.getFeeAmount()).compareTo(BigDecimal.ZERO) > 0) {
                                         return 1;
                                     } else if (o2.getFeeAmount().subtract(o1.getFeeAmount()).compareTo(BigDecimal.ZERO) < 0) {
                                         return -1;
                                     } else {
                                         return 0;
                                     }
                             }


                     });
                     /**
                      * 根据外面点击楼栋 欠费排序
                      */

                     Collections.sort(mFeeHouseList, new Comparator<BuildModel.GridRangeBean>() {
                         @Override
                         public int compare(BuildModel.GridRangeBean o1, BuildModel.GridRangeBean o2) {
                             switch (sortFlag) {
                                 case BUIDL_UP:
                                     return Integer.parseInt(viewModel.getNum(o2.getCode()))-Integer.parseInt(viewModel.getNum(o1.getCode()));
//                                     return HanziToPinyin.getStr(o2.getCode()).compareTo(HanziToPinyin.getStr(o1.getCode()));//顺序
                                 case BUIDL_DOWN:
                                     return Integer.parseInt(viewModel.getNum(o1.getCode()))-Integer.parseInt(viewModel.getNum(o2.getCode()));
//                                     return HanziToPinyin.getStr(o1.getCode()).compareTo(HanziToPinyin.getStr(o2.getCode()));//顺序
                                 case FEE_DOWN:
                                     if (o1.getFeeAmount().subtract(o2.getFeeAmount()).compareTo(BigDecimal.ZERO) > 0) {
                                         return 1;
                                     } else if (o1.getFeeAmount().subtract(o2.getFeeAmount()).compareTo(BigDecimal.ZERO) < 0) {
                                         return -1;
                                     } else {
                                         return 0;
                                     }
                                 case FEE_UP:
                                     if (o2.getFeeAmount().subtract(o1.getFeeAmount()).compareTo(BigDecimal.ZERO) > 0) {
                                         return 1;
                                     } else if (o2.getFeeAmount().subtract(o1.getFeeAmount()).compareTo(BigDecimal.ZERO) < 0) {
                                         return -1;
                                     } else {
                                         return 0;
                                     }
//                                 case FEE_DOWN:
//                                     return (int) (Double.parseDouble(String.valueOf(o1.getFeeAmount())) - Double.parseDouble(String.valueOf(o2.getFeeAmount())));
//                                 case FEE_UP:
//                                     return (int) (Double.parseDouble(String.valueOf(o2.getFeeAmount())) - Double.parseDouble(String.valueOf(o1.getFeeAmount())));
                             }
                             return HanziToPinyin.getStr(o1.getCode()).compareTo(HanziToPinyin.getStr(o2.getCode()));//顺序
                         }
                     });
                     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                         sort(mFeeHouseList);
                     }
                     if (isSearch) {
                         unitAdapter.setDataList(mFeeHouseList);
                     }else {

                         if (feeType==1) {
                             sortAccondType(mFeePreviousList);
                             unitAdapter.setDataList(mFeePreviousList);
                         }else if (feeType==2){
                             sortAccondType(mFeeCurrentList);
                             unitAdapter.setDataList(mFeeCurrentList);
                         }else if (feeType==3){
                             sortAccondType(mFeeNoList);
                             unitAdapter.setDataList(mFeeNoList);
                         }else {
                             unitAdapter.setDataList(mFeeHouseList);
                         }
                     }
                     List<BuildModel.GridRangeBean> dataList = unitAdapter.getDataList();
                     if (dataList!=null) {
                         if (dataList.size()==0) {
                             binding.rlEmpty.setVisibility(View.VISIBLE);
                         }else {
                             binding.rlEmpty.setVisibility(View.GONE);

                         }
                     }
                 }
             });
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
//        viewModel.queryFeeInfo3(feeHouseRequset).observe(TollUnitActivity.this, model -> {
//            mHouseToallUsers = 0;
//            mHouseToallFee = new BigDecimal("0").setScale(0);
//            mFeeHouseList.clear();
//            mFeePreviousList.clear();
//            mFeeCurrentList.clear();
//            mFeeNoList.clear();
//            if (model.getData() == null || model.getData().getFeeList() == null) return;
//            List<FeeModel.DataBean.FeeListBean> feeList = model.getData().getFeeList();
//            for (FeeModel.DataBean.FeeListBean feeListBean : feeList) {
//                BuildModel.GridRangeBean build = new BuildModel.GridRangeBean();
//                build.setFeeAmount(feeListBean.getFeeAmount());
//                build.setType(feeListBean.getType());
//                build.setName(feeListBean.getName());
//                build.setId(feeListBean.getHouseId());
//                build.setHouseTotal(feeListBean.getHouseTotal());
//                build.setArrearsLevel(feeListBean.getArrearsLevel());
//                if (!mFeeHouseList.contains(build)) {
//                    if (searchContent.equals("")) {
//                        if (!mFeeHouseList.contains(build)) {
//                            mFeeHouseList.add(build);
//                            if (build.getArrearsLevel()==1) {
//                                if (!mFeePreviousList.contains(build)) {
//                                    mFeePreviousList.add(build);
//                                }
//                            }else if (build.getArrearsLevel()==2){
//                                if (!mFeeCurrentList.contains(build)) {
//                                    mFeeCurrentList.add(build);
//                                }
//                            }else if (build.getArrearsLevel()==0){
//                                if (!mFeeNoList.contains(build)) {
//                                    mFeeNoList.add(build);
//                                }
//                            }
//                        }
//                    } else {
//                        if (build.getName().contains(searchContent)) {
//                            if (!mFeeHouseList.contains(build)) {
//                                mFeeHouseList.add(build);
//                            }
//                        }
//                    }
//                    if (feeListBean.getArrearsLevel() == 1 || feeListBean.getArrearsLevel() == 2) {
//                        mHouseToallUsers += 1;
//                    }
//                    mHouseToallFee = mHouseToallFee.add(new BigDecimal("1"));
//                }
//            }
//
//            binding.tvHouseToallUsers.setText("总  户  数：" + mHouseToallFee);
//            binding.tvHouseToallFee.setText("欠费户数：" + mHouseToallUsers);
//            /**
//             * 根据外面点击楼栋 欠费排序
//             */
//
//            Collections.sort(mFeeHouseList, new Comparator<BuildModel.GridRangeBean>() {
//                @Override
//                public int compare(BuildModel.GridRangeBean o1, BuildModel.GridRangeBean o2) {
//                    switch (sortFlag) {
//                        case BUIDL_UP:
//                            return HanziToPinyin.getStr(o2.getName()).compareTo(HanziToPinyin.getStr(o1.getName()));//顺序
//                        case BUIDL_DOWN:
//                            return HanziToPinyin.getStr(o1.getName()).compareTo(HanziToPinyin.getStr(o2.getName()));//顺序
//                        case FEE_DOWN:
//                        return (int) (Double.parseDouble(String.valueOf(o1.getFeeAmount())) - Double.parseDouble(String.valueOf(o2.getFeeAmount())));
//                        case FEE_UP:
//                            return (int) (Double.parseDouble(String.valueOf(o2.getFeeAmount())) - Double.parseDouble(String.valueOf(o1.getFeeAmount())));
//                    }
//                    return o2.getFeeAmount().compareTo(o1.getFeeAmount());//顺序
//                }
//            });
//            unitAdapter.setDataList(mFeeHouseList);
//        });
    }

    /**
     * 切换到单元列表
     */
    public void onBuildClick() {
        binding.llUnit.setVisibility(View.GONE);
//        binding.llFeeYears.setVisibility(View.GONE);
        headBinding.tvRightTitle.setVisibility(View.GONE);
        binding.llBuild.setVisibility(View.VISIBLE);
    }
    /**
     * 切换到楼栋列表
     */
    public void onGridClick() {
//        binding.llBuild.setVisibility(View.GONE);
//        binding.llGrid.setVisibility(View.VISIBLE);
    }

    /**
     * 选择园区
     */
    public void onPlotClick() {
        //弹出园区view
//        PeriodizationView periodizationView = new PeriodizationView();
//        periodizationView.setPeriodListener(this::onPeriodSelectListener);
//        periodizationView.show(getSupportFragmentManager(), "");
        LiveEventBus.get(LiveDataBusKey.KEY_DIVIDE_CLOSE,Boolean.class).post(true);
        finishAnim();
    }

    private void finishAnim() {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_TOLL_BUILD)
                .withTransition(R.anim.fade_in, R.anim.fade_in)
                .navigation(this, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {

                    }

                    @Override
                    public void onLost(Postcard postcard) {

                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        finish();
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {

                    }
                });
    }

    //    List<BuildModel.GridRangeBean> mFeeBuildList = new ArrayList<>();
    List<BuildModel.GridRangeBean> mFeeBuildListSixData = new ArrayList<>();
    List<BuildModel.GridRangeBean> mFeeUnitList = new ArrayList<>();
    List<BuildModel.GridRangeBean> mFeeCheckUnitList = new ArrayList<>();
    List<BuildModel.GridRangeBean> mFeeHouseList = new ArrayList<>();
    List<BuildModel.GridRangeBean> mFeePreviousList = new ArrayList<>();
    List<BuildModel.GridRangeBean> mFeeCurrentList = new ArrayList<>();
    List<BuildModel.GridRangeBean> mFeeNoList = new ArrayList<>();
    List<BuildModel.GridRangeBean> mFeeHouseCheckList = new ArrayList<>();
    List<BuildModel.GridRangeBean> mUnitList = new ArrayList<>();
    List<BuildModel.GridRangeBean> mHouseList = new ArrayList<>();
    List<BuildModel.GridRangeBean> mHouseList2 = new ArrayList<>();

    boolean allFeeFlag = true;
    List<BuildModel.GridRangeBean> BuildList = new ArrayList<>();

    @Override
    protected void initData() {
        super.initData();
        feeType=1;
        initFeeView();
        binding.tvFee1.setTextColor(getResources().getColor(R.color.blueTextColor));
        binding.tvFeeUsers1.setTextColor(getResources().getColor(R.color.blueTextColor));
        binding.ivLine1.setBackgroundColor(getResources().getColor(R.color.blueTextColor));
        binding.tvGridName.setText(mGridName);
        Log.e(TAG, "initData: " + viewModel.isEnglish("1234"));
        Log.e(TAG, "initData: " + viewModel.isEnglish("d1a234"));
        Log.e(TAG, "initData: " + viewModel.isEnglish("d1a234"));
        Log.e(TAG, "initData: " + viewModel.isEnglish("d1a234"));
        gridLayoutManager = new GridLayoutManager(TollUnitActivity.this, 3, GridLayoutManager.VERTICAL, false);
        binding.unitList.setLayoutManager(gridLayoutManager);
        if (unitAdapter == null) {
            unitAdapter = new RVBindingAdapter<ItemTollInListBinding, BuildModel.GridRangeBean>(this, com.einyun.app.pms.toll.BR.gridinbean) {
                //                private static final String TAG = "ApprovalViewModelFragme";
                @Override
                public void onBindItem(ItemTollInListBinding bind, BuildModel.GridRangeBean inquiriesItemModule, int position) {
                    bind.tvOwe.setText("欠" + inquiriesItemModule.getFeeAmount().setScale(2, BigDecimal.ROUND_HALF_UP) + "元");
                    switch (inquiriesItemModule.getArrearsLevel()) {
                        case 1://往年欠费
                            bind.tvOwe.setTextColor(getResources().getColor(R.color.redTextColor));
                            bind.tvOwe.setBackgroundResource(R.color.toll_item_red_bg_color);
                            break;
                        case 2://今年欠费
                            bind.tvOwe.setTextColor(getResources().getColor(R.color.toll_yellow_text__color));
                            bind.tvOwe.setBackgroundResource(R.color.toll_item_yellow_bg_color);
                            break;
                        case 3://暂无欠费
                            bind.tvOwe.setTextColor(getResources().getColor(R.color.blueTextColor));
                            bind.tvOwe.setBackgroundResource(R.color.toll_item_grey_bg_color);
                            bind.tvOwe.setText("预存收费▶");
                            break;


                    }

                    bind.tvName.setText(inquiriesItemModule.getName());
                    bind.llItem.setOnClickListener(view -> {
                        switch (inquiriesItemModule.getArrearsLevel()) {
                            case 1://往年欠费
                            case 2://今年欠费
                                ARouter.getInstance()
                                        .build(RouterUtils.ACTIVITY_LACK_DETAIL)
                                        .withString(RouteKey.HOUSE_TITLE, mBuildName+inquiriesItemModule.getName())
                                        .withString(RouteKey.KEY_DIVIDE_ID, mFeeDivideId)
                                        .withString(RouteKey.KEY_DIVIDE_NAME, divideName)
                                        .withString(RouteKey.NAME, inquiriesItemModule.getName())
                                        .withString(RouteKey.HOUSE_ID, inquiriesItemModule.getId())
                                        .withTransition(R.anim.fade_in,R.anim.fade_in)
                                        .navigation(TollUnitActivity.this);
                                break;
                            case 3://暂无欠费
                                ARouter.getInstance()
                                        .build(RouterUtils.ACTIVITY_PAYMENT_ADVANCE)
                                        .withString(RouteKey.HOUSE_TITLE,mBuildName+inquiriesItemModule.getName())
                                        .withString(RouteKey.KEY_DIVIDE_ID, mFeeDivideId)
                                        .withString(RouteKey.KEY_DIVIDE_NAME, divideName)
                                        .withString(RouteKey.NAME, inquiriesItemModule.getName())
                                        .withString(RouteKey.HOUSE_ID, inquiriesItemModule.getId())
                                        .withString(RouteKey.HOUSE_FEE_ID, inquiriesItemModule.getId())
                                        .withTransition(R.anim.fade_in,R.anim.fade_in)
                                        .navigation(TollUnitActivity.this);
                                break;


                        }
                    });
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_toll_in_list;
                }
            };
        }
        binding.unitList.setAdapter(unitAdapter);
        binding.llBuild.setVisibility(View.GONE);
        binding.llGrid.setVisibility(View.GONE);
        binding.llUnit.setVisibility(View.VISIBLE);
        ArrayList<String> builds = new ArrayList<>();
        builds.add(buildId);
        FeeRequset feeRequset = new FeeRequset();
        feeRequset.setBuildingIds(builds);
        feeRequset.setDivideId(mFeeDivideId);
        feeRequset.setType(2);
        viewModel.repository.getFeeInfo(feeRequset, new CallBack<FeeModel>() {
            @Override
            public void call(FeeModel data) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (data.getData() == null || data.getData().getFeeList() == null)
                            return;

                        List<FeeModel.DataBean.FeeListBean> feeList = data.getData().getFeeList();

                        for (FeeModel.DataBean.FeeListBean feeListBean : feeList) {
                            BuildModel.GridRangeBean build = new BuildModel.GridRangeBean();
                            build.setFeeAmount(feeListBean.getFeeAmount());
                            build.setName(feeListBean.getName());
                            build.setType(feeListBean.getType());
                            build.setHouseTotal(feeListBean.getHouseTotal());
                            build.setFeeTotal(feeListBean.getFeeTotal());
                            build.setId(feeListBean.getUnitId());
                            build.setFeeHouseTotal(feeListBean.getFeeHouseTotal());
                            build.setArrearsLevel(feeListBean.getArrearsLevel());
                            if (!mFeeUnitList.contains(build)) {

                                mFeeUnitList.add(build);

                            }
                            mUnitToallUsers += feeListBean.getFeeHouseTotal();
                            mUnitToallFee = mUnitToallFee.add(new BigDecimal(feeListBean.getHouseTotal()));
//                                            }
                        }

//                                    }
                        binding.tvUnitToallUsers.setText("总  户  数：" + mUnitToallFee);
                        binding.tvUnitToallFee.setText("欠费户数：" + mUnitToallUsers);

                        ArrayList<String> houseBuilds = new ArrayList<>();
                        for (BuildModel.GridRangeBean gridRangeBean : mFeeUnitList) {
                            houseBuilds.add(gridRangeBean.getId());
                        }
                        feeHouseRequset = new FeeRequset();
                        feeHouseRequset.setUntiId(houseBuilds);
                        feeHouseRequset.setDivideId(mFeeDivideId);
                        feeHouseRequset.setType(3);
                        reFreshHouseData(feeHouseRequset);

                    }
                });
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
//        viewModel.queryFeeInfo2(feeRequset).observe(TollUnitActivity.this, model -> {
//
//
//            if (model.getData() == null || model.getData().getFeeList() == null)
//                return;
//
//            List<FeeModel.DataBean.FeeListBean> feeList = model.getData().getFeeList();
//
//            for (FeeModel.DataBean.FeeListBean feeListBean : feeList) {
//                BuildModel.GridRangeBean build = new BuildModel.GridRangeBean();
//                build.setFeeAmount(feeListBean.getFeeAmount());
//                build.setName(feeListBean.getName());
//                build.setType(feeListBean.getType());
//                build.setHouseTotal(feeListBean.getHouseTotal());
//                build.setFeeTotal(feeListBean.getFeeTotal());
//                build.setId(feeListBean.getUnitId());
//                build.setFeeHouseTotal(feeListBean.getFeeHouseTotal());
//                build.setArrearsLevel(feeListBean.getArrearsLevel());
//                if (!mFeeUnitList.contains(build)) {
//
//                    mFeeUnitList.add(build);
//
//                }
//                mUnitToallUsers += feeListBean.getFeeHouseTotal();
//                mUnitToallFee = mUnitToallFee.add(new BigDecimal(feeListBean.getHouseTotal()));
////                                            }
//            }
//
////                                    }
//            binding.tvUnitToallUsers.setText("总  户  数：" + mUnitToallFee);
//            binding.tvUnitToallFee.setText("欠费户数：" + mUnitToallUsers);
//
//            ArrayList<String> houseBuilds = new ArrayList<>();
//            for (BuildModel.GridRangeBean gridRangeBean : mFeeUnitList) {
//                houseBuilds.add(gridRangeBean.getId());
//            }
//            feeHouseRequset = new FeeRequset();
//            feeHouseRequset.setUntiId(houseBuilds);
//            feeHouseRequset.setDivideId(feeDivideId);
//            feeHouseRequset.setType(3);
//            reFreshHouseData(feeHouseRequset);
//        });

    }
    private static final String TAG = "TollViewModelActivity";

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }

    boolean flag = true;

    @Override
    protected void onResume() {
        super.onResume();
        reFreshHouseData(feeHouseRequset);
    }

    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        flag = true;
        mToallUsers = 0;
        mToallFee = new BigDecimal("0.00");
        divideId = orgModel.getId();
        divideName = orgModel.getName();
        binding.tvDivide.setText(divideName);
        binding.tvDivide.setTextColor(getResources().getColor(R.color.blueTextColor));
    }


    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<GridModel> mDiffCallback = new DiffUtil.ItemCallback<GridModel>() {


        @Override
        public boolean areItemsTheSame(@NonNull GridModel oldItem, @NonNull GridModel newItem) {
            return oldItem == newItem;
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull GridModel oldItem, @NonNull GridModel newItem) {
            return oldItem == newItem;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new GridModel().setLoadMore(true);

    }

    @Override
    public void onItemClicked(View view, BuildModel.GridRangeBean gridRangeBean) {

    }


}
