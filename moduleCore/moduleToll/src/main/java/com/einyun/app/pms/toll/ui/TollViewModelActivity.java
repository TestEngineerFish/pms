package com.einyun.app.pms.toll.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.constants.SPKey;
import com.einyun.app.common.manager.BasicDataManager;
import com.einyun.app.common.manager.BasicDataTypeEnum;
import com.einyun.app.common.model.BasicData;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;

import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.ui.widget.BottomPicker;
import com.einyun.app.common.ui.widget.ConditionBuilder;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.SelectPopUpView;
import com.einyun.app.library.mdm.model.DivideGrid;
import com.einyun.app.library.mdm.model.GridModel;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.toll.BR;
import com.einyun.app.pms.toll.R;
import com.einyun.app.pms.toll.databinding.ActivityTollViewModelBinding;
import com.einyun.app.pms.toll.databinding.ItemTollInListBinding;
import com.einyun.app.pms.toll.databinding.ItemTollOutListBinding;
import com.einyun.app.pms.toll.model.BuildModel;
import com.einyun.app.pms.toll.model.DealBuildModel;
import com.einyun.app.pms.toll.model.DivideNameModel;
import com.einyun.app.pms.toll.model.FeeModel;
import com.einyun.app.pms.toll.model.FeeRequset;
import com.einyun.app.pms.toll.viewmodel.TollViewModel;
import com.einyun.app.pms.toll.viewmodel.TollViewModelFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_DATE;


@Route(path = RouterUtils.ACTIVITY_TOLL)
public class TollViewModelActivity extends BaseHeadViewModelActivity<ActivityTollViewModelBinding, TollViewModel> implements PeriodizationView.OnPeriodSelectListener,
        ItemClickListener<BuildModel.GridRangeBean> {
    RVBindingAdapter<ItemTollOutListBinding, GridModel> adapter;
    RVBindingAdapter<ItemTollInListBinding, BuildModel.GridRangeBean> inAdapter;
    RVBindingAdapter<ItemTollInListBinding, BuildModel.GridRangeBean> buildAdapter;
    RVBindingAdapter<ItemTollInListBinding, BuildModel.GridRangeBean> unitAdapter;
    private String divideId="";
    private String divideName="";
    private String feeDivideId;
    private BigDecimal mUnitToallFee=new BigDecimal("0");
    private BigDecimal mHouseToallFee=new BigDecimal("0");
    private int mUnitToallUsers;
    private int mHouseToallUsers;

    private BigDecimal mToallFee=new BigDecimal("0");
    private int mToallUsers;
    private AlertDialog alertDialog;
    int txDefaultPosSwitchSuqence = 0;
    private String blockName="";
    private boolean flag1;
    private List<FeeModel.DataBean.FeeListBean> feeListNew=new ArrayList<>();
    private int isShowFee;
    private FeeRequset feeRequset;

    @Override
    protected TollViewModel initViewModel() {
        return new ViewModelProvider(this, new TollViewModelFactory()).get(TollViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_toll_view_model;
    }

    private void hiddenAllView(){
        binding.llFeeYears.setVisibility(View.GONE);
        binding.llGrid.setVisibility(View.VISIBLE);
        binding.llBuild.setVisibility(View.GONE);
        binding.llUnit.setVisibility(View.GONE);
        headBinding.tvRightTitle.setVisibility(View.GONE);
    }

    @Override
    public void onBackOnClick(View view) {
        if (binding.llGrid.isShown()) {
            super.onBackOnClick(view);
        }else if (binding.llBuild.isShown()){
            hiddenAllView();
            queryData(false);
        }else if (binding.llUnit.isShown()){
            hiddenAllView();
            queryData(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.llGrid.isShown()) {
            super.onBackPressed();
        }else if (binding.llBuild.isShown()){
            hiddenAllView();
            queryData(false);
        }else if (binding.llUnit.isShown()){
            hiddenAllView();
            queryData(false);
        }
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(R.string.tv_toll_assant);
        setRightTxt(R.string.tv_change_trun);
        headBinding.tvRightTitle.setVisibility(View.GONE);
        setRightTxtColor(R.color.blueTextColor);

        binding.setCallBack(this);

        binding.rlTime.setVisibility(View.GONE);
        divideName = (String) SPUtils.get(CommonApplication.getInstance(), SPKey.KEY_BLOCK_NAME, "");
        divideId = (String) SPUtils.get(CommonApplication.getInstance(), SPKey.KEY_BLOCK_ID, "");
        if (!divideName.isEmpty()) {
            binding.tvDivide.setTextColor(getResources().getColor(R.color.blueTextColor));
            binding.ivTriangleDivide.setImageResource(R.drawable.iv_approval_sel_type_blue);
            binding.tvDivide.setText(divideName);
        }
        if (divideId.equals("")){
            viewModel.getDefauftDivideId(viewModel.getUserId()).observe(this,model->{
                Log.e(TAG, "initViews: model=="+model );
                divideId=model;
                viewModel.getDefauftDivideName(viewModel.getUserId()).observe(TollViewModelActivity.this,nameModel->{

                    if (nameModel!=null) {
                        for (DivideNameModel divideNameModel : nameModel) {
                            if (divideNameModel.getId().equals(divideId)) {
                                divideName=divideNameModel.getName();
                                Log.e(TAG, "initViews:divideId== "+divideId +"divideName==="+divideName );
                                binding.tvDivide.setTextColor(getResources().getColor(R.color.blueTextColor));
                                binding.ivTriangleDivide.setImageResource(R.drawable.iv_approval_sel_type_blue);
                                binding.tvDivide.setText(divideName);
                                queryData(false);
                            }
                        }
                    }
                });

            });
        }else{
            queryData(true);
        }
        /**
         * 付款成功刷新数据
         */
        LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {

//                hiddenAllView();
                binding.llUnit.setVisibility(View.VISIBLE);
                binding.llFeeYears.setVisibility(View.VISIBLE);
                headBinding.tvRightTitle.setVisibility(View.VISIBLE);
//                queryData(true);
                reFreshHouseData();
            }
        });
    }

    /**
     * 刷新房间列表数据
     */
    private void reFreshHouseData() {
        viewModel.queryFeeInfo3(feeRequset).observe(TollViewModelActivity.this, model -> {
//                                    Log.e(TAG, "onBindItem: "+"model size===  " );
            mHouseToallUsers = 0;
            mHouseToallFee = new BigDecimal("0").setScale(0);
            mFeeHouseList.clear();
            if (model.getData() == null || model.getData().getFeeList() == null) return;
//
//                                    Log.e(TAG, "onBindItem: "+"model size===  " +model.getData().getFeeList().size());
            List<FeeModel.DataBean.FeeListBean> feeList = model.getData().getFeeList();
            for (BuildModel.GridRangeBean build : mHouseList2) {
//                                Log.e(TAG, "onBindItem: "+build.getId() );
                for (FeeModel.DataBean.FeeListBean feeListBean : feeList) {
                    if (feeListBean.getHouseId().equals(build.getId())) {//同一房产
                        build.setFeeAmount(feeListBean.getFeeAmount());
                        build.setType(feeListBean.getType());
                        build.setHouseTotal(feeListBean.getHouseTotal());
                        build.setArrearsLevel(feeListBean.getArrearsLevel());
                        if (!mFeeHouseList.contains(build)) {
                            mFeeHouseList.add(build);
                            if (feeListBean.getArrearsLevel() == 1 || feeListBean.getArrearsLevel() == 2) {

                                mHouseToallUsers += 1;
//                                                mHouseToallFee = mHouseToallFee.add(feeListBean.getFeeAmount());
                            }
                            mHouseToallFee = mHouseToallFee.add(new BigDecimal("1"));
                        }
                    }
                }

            }
            binding.tvHouseToallUsers.setText("总  户  数：" + mHouseToallFee);
            binding.tvHouseToallFee.setText("欠费户数：" + mHouseToallUsers);

//                            for (BuildModel.GridRangeBean house : mFeeHouseList) {
////                                BigDecimal bigDecimal = house.getFeeAmoun
////                                t() == null ? new BigDecimal("0.00") : house.getFeeAmount();
//                                if (house.getFeeAmount()!=null) {
//                                    mHouseToallFee.add(house.getFeeAmount());
//                                }
//                            }
//                            binding.tvHouseToallFee.setText("欠费："+mHouseToallFee);
            Collections.sort(mFeeHouseList, new Comparator<BuildModel.GridRangeBean>() {
                @Override
                public int compare(BuildModel.GridRangeBean o1, BuildModel.GridRangeBean o2) {
                    return o2.getFeeAmount().compareTo(o1.getFeeAmount());//顺序

                }
            });
            unitAdapter.setDataList(mFeeHouseList);
        });
    }

    @Override
    public void onRightOptionClick(View view) {
        super.onRightOptionClick(view);
        switchSuqence();
    }
    /**
     * 切换顺序
     */
    private void switchSuqence() {

        List<String> txStrList = new ArrayList<>();

        txStrList.add("欠费置顶");
        txStrList.add("楼层（高～低）");
        txStrList.add("楼层（低～高）");
        BottomPicker.buildBottomPicker(this, txStrList, txDefaultPosSwitchSuqence, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                txDefaultPosSwitchSuqence = position;
                switch (position) {
                    case 0://欠费置顶
                        Collections.sort(mFeeHouseList, new Comparator<BuildModel.GridRangeBean>() {
                            @Override
                            public int compare(BuildModel.GridRangeBean o1, BuildModel.GridRangeBean o2) {
                                return o2.getFeeAmount().compareTo(o1.getFeeAmount());//顺序

                            }
                        });
                        unitAdapter.setDataList(mFeeHouseList);
                        break;
                    case 1://楼层（高～低）
                        Collections.sort(mFeeHouseList, new Comparator<BuildModel.GridRangeBean>() {
                            @Override
                            public int compare(BuildModel.GridRangeBean o1, BuildModel.GridRangeBean o2) {
                                return o2.getCode().compareTo(o1.getCode());//高～低

                            }
                        });
                        unitAdapter.setDataList(mFeeHouseList);
                        break;
                    case 2://楼层（低～高）
                        Collections.sort(mFeeHouseList, new Comparator<BuildModel.GridRangeBean>() {
                            @Override
                            public int compare(BuildModel.GridRangeBean o1, BuildModel.GridRangeBean o2) {
                                return o1.getCode().compareTo(o2.getCode());//顺序

                            }
                        });
                        unitAdapter.setDataList(mFeeHouseList);
                        break;
                }
            }
        });
    }
    /**
     * 切换到单元列表
     */
    public  void  onBuildClick(){
        binding.llUnit.setVisibility(View.GONE);
        binding.llFeeYears.setVisibility(View.GONE);
        headBinding.tvRightTitle.setVisibility(View.GONE);
        binding.llBuild.setVisibility(View.VISIBLE);
    }
    /**
     * 一键催缴
     */
    public  void  onWorthClick(){
        if (!feeDivideId.isEmpty()) {
            FeeRequset feeRequset = new FeeRequset();
            feeRequset.setDivideId(feeDivideId);
            viewModel.allWorth(feeRequset).observe(this,model->{

                if (model.getCode()==0) {
                    if (alertDialog == null) {
                        alertDialog = new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
                                .setMsg("催缴消息已发送成功！")
                                .setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                });
                        alertDialog.show();
                    } else {
                        if (!alertDialog.isShowing()) {
                            alertDialog.show();
                        }
                    }
                }

            });
        }
    }


    /**
     * 切换到楼栋列表
     */
    public  void onGridClick(){
        binding.llBuild.setVisibility(View.GONE);
        binding.llGrid.setVisibility(View.VISIBLE);
    }
    /**
     * 选择分期
     */
    public void onPlotClick(){
        //弹出分期view
        PeriodizationView periodizationView=new PeriodizationView();
        periodizationView.setPeriodListener(this::onPeriodSelectListener);
        periodizationView.show(getSupportFragmentManager(),"");
    }

    List<BuildModel.GridRangeBean> mFeeBuildList=new ArrayList<>();
    List<BuildModel.GridRangeBean> mFeeBuildListSixData=new ArrayList<>();
    List<BuildModel.GridRangeBean> mFeeUnitList=new ArrayList<>();
    List<BuildModel.GridRangeBean> mFeeHouseList=new ArrayList<>();
    List<BuildModel.GridRangeBean> mUnitList=new ArrayList<>();
    List<BuildModel.GridRangeBean> mHouseList=new ArrayList<>();
    List<BuildModel.GridRangeBean> mHouseList2=new ArrayList<>();

    boolean allFeeFlag=true;
    List<BuildModel.GridRangeBean> BuildList=new ArrayList<>();
    private void getAllFeeBuilds(List<GridModel> grids){
        isShowFee=0;
        BuildList.clear();
        for (GridModel grid : grids) {
            grid.setLoadMore(true);
        List<BuildModel.GridRangeBean> list=new Gson().fromJson(grid.getGridRange(),new TypeToken<List<BuildModel.GridRangeBean>>(){}.getType());
        if (list!=null&&list.size()>1) {
            for (BuildModel.GridRangeBean gridRangeBean : list) {
                if (gridRangeBean.getLevel()==1) {
                    if (!BuildList.contains(gridRangeBean)) {
                        BuildList.add(gridRangeBean);
                    }
                }
            }
        }
        }
        ArrayList<String> builds = new ArrayList<>();
        for (BuildModel.GridRangeBean build : BuildList) {
            builds.add(build.getId());
        }
//                    builds.add(gridModel.getId());
        FeeRequset feeRequset = new FeeRequset();
        feeRequset.setBuildingIds(builds);
        feeRequset.setDivideId(feeDivideId);
        feeRequset.setType(1);
//        if (allFeeFlag) {

            viewModel.queryFeeInfo(feeRequset).observe(TollViewModelActivity.this,model->{
//                allFeeFlag=false;
                if (model.getData()==null) {
                    return;
                }
                if (model.getData().getFeeList()==null) {
                    return;
                }
                feeListNew = model.getData().getFeeList();
                if (feeListNew.size()!=0) {
                    adapter.setDataList(grids);
//                    if (isShowFee==0) {
//                        binding.rlTime.setVisibility(View.GONE);
//                    }
                }
            });
//        }

    }
    @Override
    protected void initData() {
        super.initData();
        Log.e(TAG, "initData: "+viewModel.isEnglish("1234") );
        Log.e(TAG, "initData: "+viewModel.isEnglish("d1a234") );
        Log.e(TAG, "initData: "+viewModel.isEnglish("d1a234") );
        Log.e(TAG, "initData: "+viewModel.isEnglish("d1a234") );
        binding.outList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.buildList.setLayoutManager(new GridLayoutManager(TollViewModelActivity.this, 3, GridLayoutManager.VERTICAL, false));
        binding.unitList.setLayoutManager(new GridLayoutManager(TollViewModelActivity.this, 3, GridLayoutManager.VERTICAL, false));
        if(adapter==null){
            adapter=new RVBindingAdapter<ItemTollOutListBinding, GridModel>(this, com.einyun.app.pms.toll.BR.gridmodel){

                //                private static final String TAG = "ApprovalViewModelFragme";
                @Override
                public void onBindItem(ItemTollOutListBinding outListBindingbinding, GridModel gridModel,int position) {
                    outListBindingbinding.tvGrid.setText(gridModel.getGridName());
                    List<BuildModel.GridRangeBean> mBuildList=new ArrayList<>();
//                    new Gson().fromJson(inquiriesItemModule.getGridRange(), BuildModel.GridRangeBean.class)
                    mBuildList.clear();
                    mUnitList.clear();
                    mHouseList.clear();
                    mFeeBuildList.clear();
                    mToallUsers=0;
                    mToallFee=new BigDecimal("0.00");
                    mToallFee.setScale(0);
                    List<BuildModel.GridRangeBean> list=new Gson().fromJson(gridModel.getGridRange(),new TypeToken<List<BuildModel.GridRangeBean>>(){}.getType());

//                    adapter.notifyItemChanged(position);
                    if (list!=null&&list.size()>1) {
                        for (BuildModel.GridRangeBean gridRangeBean : list) {
                            if (gridRangeBean.getLevel()==1) {
                                mBuildList.add(gridRangeBean);
                            }else if (gridRangeBean.getLevel()==2){
                                mUnitList.add(gridRangeBean);
                            }else if (gridRangeBean.getLevel()==3){
                                mHouseList.add(gridRangeBean);
                            }
                        }
                    }
                    ArrayList<String> builds = new ArrayList<>();
                    for (BuildModel.GridRangeBean build : mBuildList) {
                        builds.add(build.getId());
                    }
//                    builds.add(gridModel.getId());
                    FeeRequset feeRequset = new FeeRequset();
                    feeRequset.setBuildingIds(builds);
                    feeRequset.setDivideId(feeDivideId);
                    feeRequset.setType(1);
//                    feeRequset.setGrid("1");
//                    feeRequset.setIsArrears(1);
                    List<BuildModel.GridRangeBean> mFeeBuildList2=new ArrayList<>();
//                    viewModel.queryFeeInfo(feeRequset).observe(TollViewModelActivity.this,model->{

                        mToallUsers=0;
                        mToallFee=new BigDecimal("0").setScale(0);
                        mFeeBuildList.clear();
                        mFeeBuildListSixData.clear();

//                        Log.e(TAG, "onBindItem: "+"sucsize===  " +model.getData().getFeeList().size());
//                        List<FeeModel.DataBean.FeeListBean> feeList = model.getData().getFeeList();
                        for (BuildModel.GridRangeBean build : mBuildList) {
                            for (FeeModel.DataBean.FeeListBean feeListBean : feeListNew) {
                                if (feeListBean.getBuildingId().equals(build.getId())) {//同一楼栋
                                    build.setFeeAmount(feeListBean.getFeeAmount());
                                    build.setType(feeListBean.getType());
                                    build.setHouseTotal(feeListBean.getHouseTotal());
                                    build.setFeeHouseTotal(feeListBean.getFeeHouseTotal());
                                    build.setArrearsLevel(feeListBean.getArrearsLevel());
                                    build.setFeeTotal(feeListBean.getFeeTotal());
                                    mFeeBuildList2.add(build);
                                    mToallUsers+=feeListBean.getFeeHouseTotal();
//                                    mToallFee=mToallFee.add(feeListBean.getFeeAmount());
                                    mToallFee=mToallFee.add(new BigDecimal(feeListBean.getHouseTotal()));
                                }
                            }
                        }
//                        outListBindingbinding.tvToallUsers.setText("总户数："+mToallUsers);
//                        outListBindingbinding.tvToallFee.setText("欠费："+mToallFee);
                        outListBindingbinding.tvToallUsers.setText("总  户  数："+mToallFee);
                        outListBindingbinding.tvToallFee.setText("欠费户数："+mToallUsers);
                        isShowFee = isShowFee +mToallUsers;
                    if (isShowFee==0) {
                        binding.rlTime.setVisibility(View.GONE);
                    }else {
                        binding.rlTime.setVisibility(View.VISIBLE);

                    }

//                    });
                    inAdapter=new RVBindingAdapter<ItemTollInListBinding, BuildModel.GridRangeBean>(TollViewModelActivity.this, com.einyun.app.pms.toll.BR.gridinbean){
                        //                private static final String TAG = "ApprovalViewModelFragme";
                        @Override
                        public void onBindItem(ItemTollInListBinding bind, BuildModel.GridRangeBean inquiriesItemModule, int position) {
                            mUnitToallUsers=0;
                            mUnitToallFee=new BigDecimal("0");
                            bind.tvName.setText(inquiriesItemModule.getName());

                            switch (inquiriesItemModule.getArrearsLevel()) {
                                case 1://往年欠费
                                    bind.tvOwe.setTextColor(getResources().getColor(R.color.redTextColor));
                                    bind.tvOwe.setBackgroundResource(R.color.toll_item_red_bg_color);
                                    bind.tvOwe.setText("欠"+new BigDecimal(inquiriesItemModule.getFeeTotal()).setScale(2)+"元");
                                    break;
                                case 2://今年欠费
                                    bind.tvOwe.setTextColor(getResources().getColor(R.color.toll_yellow_text__color));
                                    bind.tvOwe.setBackgroundResource(R.color.toll_item_yellow_bg_color);
                                    bind.tvOwe.setText("欠"+new BigDecimal(inquiriesItemModule.getFeeTotal()).setScale(2)+"元");
                                    break;
                                case 3://暂无欠费
                                    bind.tvOwe.setTextColor(getResources().getColor(R.color.greyTextColor));
                                    bind.tvOwe.setBackgroundResource(R.color.toll_item_grey_bg_color);
                                    bind.tvOwe.setText("暂无欠费");
                                    break;
                            }
//                            bind.tvOwe.setText("欠"+inquiriesItemModule.getFeeAmount());
//                            bind.tvOwe.setTextColor(getResources().getColor(R.color.redTextColor));
//                            bind.tvOwe.setBackgroundResource(R.color.toll_item_red_bg_color);
                            bind.llItem.setOnClickListener(view -> {
                                mBuildList.clear();
                                mUnitList.clear();
                                mHouseList.clear();
                                mFeeUnitList.clear();
                                mUnitToallUsers=0;
                                mUnitToallFee=new BigDecimal("0");
//                                for (GridModel grid : grids) {
                                    List<BuildModel.GridRangeBean> list=new Gson().fromJson(gridModel.getGridRange(),new TypeToken<List<BuildModel.GridRangeBean>>(){}.getType());

//                                    if (gridModel.getId()==grid.getId()) {
                                        if (list!=null&&list.size()>1) {
                                            for (BuildModel.GridRangeBean rangeBean : list) {
                                                if (inquiriesItemModule.getLevel()==1) {
                                                    if (inquiriesItemModule.getId().equals(rangeBean.getId())) {//当前点击的楼栋id等于网格里的楼栋id 接可以拿到但钱网格的所有数据 即 list

                                                        for (BuildModel.GridRangeBean Bean : list) {
                                                            if (Bean.getLevel()==1) {
                                                                binding.tvBuild1.setText(inquiriesItemModule.getName());
                                                                binding.tvGrid1.setText(gridModel.getGridName()+">>");

                                                                binding.tvBuild2.setText(inquiriesItemModule.getName());
                                                                mBuildList.add(Bean);
                                                            }else if (Bean.getLevel()==2){
                                                                binding.tvUnit2.setText(Bean.getName());

                                                                binding.tvGrid2.setText(gridModel.getGridName()+">>");
                                                                if (inquiriesItemModule.getId().equals(Bean.getParentId())) {
                                                                    mUnitList.add(Bean);//根据 id=pid 取到 对应单元得房产 展示数据
                                                                }
                                                            }else if (Bean.getLevel()==3){
                                                                mHouseList.add(Bean);
                                                            }
                                                        }

                                                    }
                                                }

                                            }

                                        }
//                                    }

//                                }
                                binding.llGrid.setVisibility(View.GONE);
                                binding.llUnit.setVisibility(View.GONE);
                                binding.llBuild.setVisibility(View.VISIBLE);
                                ArrayList<String> builds = new ArrayList<>();
                                for (BuildModel.GridRangeBean build : mUnitList) {
                                    builds.add(build.getId());
                                }
                                FeeRequset feeRequset = new FeeRequset();
                                feeRequset.setUntiId(builds);
                                feeRequset.setDivideId(feeDivideId);
                                feeRequset.setType(2);
                                viewModel.queryFeeInfo2(feeRequset).observe(TollViewModelActivity.this,model->{
//                                    Log.e(TAG, "onBindItem: "+"model size===  " );
                                    mUnitToallUsers=0;
                                    mUnitToallFee=new BigDecimal("0");
                                        if (model.getData()==null||model.getData().getFeeList()==null) return;
//
//                                    Log.e(TAG, "onBindItem: "+"model size===  " +model.getData().getFeeList().size());
                                    List<FeeModel.DataBean.FeeListBean> feeList = model.getData().getFeeList();
                                    for (BuildModel.GridRangeBean build : mUnitList) {
                                        for (FeeModel.DataBean.FeeListBean feeListBean : feeList) {
                                            if (build.getId().equals(feeListBean.getUnitId())) {//同一单元
                                                build.setFeeAmount(feeListBean.getFeeAmount());
                                                build.setType(feeListBean.getType());
                                                build.setHouseTotal(feeListBean.getHouseTotal());
                                                build.setFeeTotal(feeListBean.getFeeTotal());
                                                build.setFeeHouseTotal(feeListBean.getFeeHouseTotal());
                                                build.setArrearsLevel(feeListBean.getArrearsLevel());
                                                if (!mFeeUnitList.contains(build)) {

                                                    mFeeUnitList.add(build);

                                                }
                                                mUnitToallUsers += feeListBean.getFeeHouseTotal();
                                                mUnitToallFee = mUnitToallFee.add(new BigDecimal(feeListBean.getHouseTotal()));
                                            }
                                        }

                                    }
                                    binding.tvUnitToallUsers.setText("总  户  数："+mUnitToallFee);
                                    binding.tvUnitToallFee.setText("欠费户数："+mUnitToallUsers);
                                    buildAdapter.setDataList(mFeeUnitList);
                                });
//                                buildAdapter.setDataList(mUnitList);
                            });
                        }
                        @Override
                        public int getLayoutId() {
                            return R.layout.item_toll_in_list;
                        }
                    };
                    outListBindingbinding.inList.setLayoutManager(new GridLayoutManager(TollViewModelActivity.this, 3, GridLayoutManager.VERTICAL, false));
                    outListBindingbinding.inList.setAdapter(inAdapter);
                    if (mFeeBuildList2.size()>6) {//只展示6条
                        if (gridModel.isLoadMore()) {
                            outListBindingbinding.llMore.setVisibility(View.VISIBLE);
                            for (int i = 0; i < 6; i++) {
                                mFeeBuildListSixData.add(mFeeBuildList2.get(i));
                            }
                            inAdapter.setDataList(mFeeBuildListSixData);
                        }else {
                            outListBindingbinding.llMore.setVisibility(View.GONE);
                            inAdapter.setDataList(mFeeBuildList2);
                        }
                    }else {
                        outListBindingbinding.llMore.setVisibility(View.GONE);
                        inAdapter.setDataList(mFeeBuildList2);
                    }
                    outListBindingbinding.llMore.setOnClickListener(view -> {
//                        inAdapter.setDataList(mFeeBuildList2);
                        gridModel.setLoadMore(false);
                        adapter.notifyDataSetChanged();
                        outListBindingbinding.llMore.setVisibility(View.GONE);
                    });

                }
                @Override
                public int getLayoutId() {
                    return R.layout.item_toll_out_list;
                }
            };
        }

        if(buildAdapter==null){
            buildAdapter=new RVBindingAdapter<ItemTollInListBinding, BuildModel.GridRangeBean>(this, com.einyun.app.pms.toll.BR.gridinbean){
                //                private static final String TAG = "ApprovalViewModelFragme";
                @Override
                public void onBindItem(ItemTollInListBinding buildbinding, BuildModel.GridRangeBean inquiriesItemModule, int position) {
                    mHouseToallUsers=0;
                    buildbinding.tvName.setText(inquiriesItemModule.getName());

                    switch (inquiriesItemModule.getArrearsLevel()) {
                        case 1://往年欠费
                            buildbinding.tvOwe.setTextColor(getResources().getColor(R.color.redTextColor));
                            buildbinding.tvOwe.setBackgroundResource(R.color.toll_item_red_bg_color);
                            buildbinding.tvOwe.setText("欠"+new BigDecimal(inquiriesItemModule.getFeeTotal()).setScale(2)+"元");
                            break;
                        case 2://今年欠费
                            buildbinding.tvOwe.setTextColor(getResources().getColor(R.color.toll_yellow_text__color));
                            buildbinding.tvOwe.setBackgroundResource(R.color.toll_item_yellow_bg_color);
                            buildbinding.tvOwe.setText("欠"+new BigDecimal(inquiriesItemModule.getFeeTotal()).setScale(2)+"元");
                            break;
                        case 3://暂无欠费
                            buildbinding.tvOwe.setTextColor(getResources().getColor(R.color.greyTextColor));
                            buildbinding.tvOwe.setBackgroundResource(R.color.toll_item_grey_bg_color);
                            buildbinding.tvOwe.setText("暂无欠费");
                            break;
                    }
                    mHouseList2.clear();
                    mFeeHouseList.clear();
                    buildbinding.llItem.setOnClickListener(view -> {
                        binding.tvUnit2.setText(inquiriesItemModule.getName());
                        mHouseList2.clear();
                        mFeeHouseList.clear();
                        mHouseToallUsers=0;
                        mHouseToallFee=new BigDecimal("0").setScale(0);
                        binding.llBuild.setVisibility(View.GONE);
                        binding.llUnit.setVisibility(View.VISIBLE);
                        binding.llFeeYears.setVisibility(View.VISIBLE);
                        headBinding.tvRightTitle.setVisibility(View.VISIBLE);
                        for (BuildModel.GridRangeBean gridRangeBean : mHouseList) {
                            if (gridRangeBean.getParentId().equals(inquiriesItemModule.getId())) {
                                mHouseList2.add(gridRangeBean);
                            }
                        }
                        ArrayList<String> builds = new ArrayList<>();
                        for (BuildModel.GridRangeBean build : mHouseList2) {
                            builds.add(build.getId());
                        }
                        feeRequset = new FeeRequset();
                        feeRequset.setHouseIdS(builds);
                        feeRequset.setDivideId(feeDivideId);
                        feeRequset.setType(3);
                        reFreshHouseData();
//                        viewModel.queryFeeInfo3(feeRequset).observe(TollViewModelActivity.this, model->{
////                                    Log.e(TAG, "onBindItem: "+"model size===  " );
//                            mHouseToallUsers=0;
//                            mHouseToallFee=new BigDecimal("0").setScale(0);
//                            mFeeHouseList.clear();
//                            if (model.getData()==null||model.getData().getFeeList()==null) return;
////
////                                    Log.e(TAG, "onBindItem: "+"model size===  " +model.getData().getFeeList().size());
//                            List<FeeModel.DataBean.FeeListBean> feeList = model.getData().getFeeList();
//                            for (BuildModel.GridRangeBean build : mHouseList2) {
////                                Log.e(TAG, "onBindItem: "+build.getId() );
//                                for (FeeModel.DataBean.FeeListBean feeListBean : feeList) {
//                                    if (feeListBean.getHouseId().equals(build.getId())) {//同一房产
//                                        build.setFeeAmount(feeListBean.getFeeAmount());
//                                        build.setType(feeListBean.getType());
//                                        build.setHouseTotal(feeListBean.getHouseTotal());
//                                        build.setArrearsLevel(feeListBean.getArrearsLevel());
//                                        if (!mFeeHouseList.contains(build)) {
//                                            mFeeHouseList.add(build);
//                                            if (feeListBean.getArrearsLevel()==1||feeListBean.getArrearsLevel()==2) {
//
//                                                mHouseToallUsers +=1;
////                                                mHouseToallFee = mHouseToallFee.add(feeListBean.getFeeAmount());
//                                            }
//                                            mHouseToallFee = mHouseToallFee.add(new BigDecimal("1"));
//                                        }
//                                    }
//                                }
//
//                            }
//                            binding.tvHouseToallUsers.setText("总  户  数："+mHouseToallFee);
//                            binding.tvHouseToallFee.setText("欠费户数："+mHouseToallUsers);
//
////                            for (BuildModel.GridRangeBean house : mFeeHouseList) {
//////                                BigDecimal bigDecimal = house.getFeeAmoun
//////                                t() == null ? new BigDecimal("0.00") : house.getFeeAmount();
////                                if (house.getFeeAmount()!=null) {
////                                    mHouseToallFee.add(house.getFeeAmount());
////                                }
////                            }
////                            binding.tvHouseToallFee.setText("欠费："+mHouseToallFee);
//                            unitAdapter.setDataList(mFeeHouseList);
//                        });
//                        unitAdapter.setDataList(mHouseList2);
                    });

                }
                @Override
                public int getLayoutId() {
                    return R.layout.item_toll_in_list;
                }
            };
        }
        if(unitAdapter==null){
            unitAdapter=new RVBindingAdapter<ItemTollInListBinding, BuildModel.GridRangeBean>(this, com.einyun.app.pms.toll.BR.gridinbean){
                //                private static final String TAG = "ApprovalViewModelFragme";
                @Override
                public void onBindItem(ItemTollInListBinding bind, BuildModel.GridRangeBean inquiriesItemModule, int position) {
                    bind.tvOwe.setText("欠"+inquiriesItemModule.getFeeAmount().setScale(2)+"元");
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
                                        .withString(RouteKey.HOUSE_TITLE,binding.tvBuild2.getText().toString()+binding.tvUnit2.getText().toString()+inquiriesItemModule.getName())
                                        .withString(RouteKey.KEY_DIVIDE_ID,feeDivideId)
                                        .withString(RouteKey.KEY_DIVIDE_NAME,divideName)
                                        .withString(RouteKey.NAME,inquiriesItemModule.getName())
                                        .withString(RouteKey.HOUSE_ID,inquiriesItemModule.getId())
                                        .navigation();
                                break;
                            case 3://暂无欠费
                                flag1 = true;


                                viewModel.getFeeHouseId(inquiriesItemModule.getId(),"3/").observe(TollViewModelActivity.this,model2->{
                                    Log.e(TAG, "onBindItem: houseId"+model2 );
                                    if (flag1) {
                                        flag1 = false;
                                        ARouter.getInstance()
                                                .build(RouterUtils.ACTIVITY_PAYMENT_ADVANCE)
                                                .withString(RouteKey.HOUSE_TITLE, binding.tvBuild2.getText().toString() + binding.tvUnit2.getText().toString() + inquiriesItemModule.getName())
                                                .withString(RouteKey.KEY_DIVIDE_ID, feeDivideId)
                                                .withString(RouteKey.KEY_DIVIDE_NAME, divideName)
                                                .withString(RouteKey.NAME, inquiriesItemModule.getName())
                                                .withString(RouteKey.HOUSE_ID,inquiriesItemModule.getId())
                                                .withString(RouteKey.HOUSE_FEE_ID, model2)
                                                .navigation();
                                    }
                                });

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
        binding.buildList.setAdapter(buildAdapter);
        binding.unitList.setAdapter(unitAdapter);
        binding.outList.setAdapter(adapter);
//        inAdapter.setOnItemClick(this);
    }
    private List<GridModel> grids=new ArrayList<>();
    private static final String TAG = "TollViewModelActivity";
    private void queryGrid(String divideId) {
        BasicDataManager.getInstance().loadDivideGrid(divideId, new CallBack<DivideGrid>() {



            @Override
            public void call(DivideGrid divideGrid) {
                if (divideGrid==null) {
                    return;
                }
                if (divideGrid.getGrids()==null) {
                    return;
                }
                    grids = divideGrid.getGrids();

                if (grids.size()==0) {
                    binding.rlTime.setVisibility(View.GONE);
                }else {
                    binding.rlTime.setVisibility(View.VISIBLE);

                }
                getAllFeeBuilds(grids);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
    }


    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
     boolean flag=true;
    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {

        hiddenAllView();
        binding.rlTime.setVisibility(View.GONE);
        flag=true;
        mToallUsers=0;
        mToallFee=new BigDecimal("0.00");
        divideId = orgModel.getId();
        divideName = orgModel.getName();
        binding.tvDivide.setText(divideName);
        binding.tvDivide.setTextColor(getResources().getColor(R.color.blueTextColor));
        queryData(false);

    }

    private void queryData(boolean isFresh) {
        viewModel.getFeeDivideId(divideId,"0/").observe(this,model->{

            feeDivideId = model;
//            if (flag) {
                flag=false;
                queryGrid(divideId);
                viewModel.getLastWorthTime(feeDivideId).observe(this,model2->{

                    if (model2.getData()==null) {
                        binding.rlTime.setVisibility(View.GONE);
                    }else {
                        if (grids.size()!=0) {

                            binding.rlTime.setVisibility(View.VISIBLE);
                        }
                        String urgeDate = model2.getData().getUrgeDate();
                        String substring = urgeDate.substring(0, 10);
                        String split = substring.replace("-","/");
                        binding.tvTime.setText("上次催缴时间："+ split);
                    }
                });
//            }
        });
    }

    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<GridModel> mDiffCallback = new DiffUtil.ItemCallback<GridModel>() {


        @Override
        public boolean areItemsTheSame(@NonNull GridModel oldItem, @NonNull GridModel newItem) {
            return oldItem==newItem;
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull GridModel oldItem, @NonNull GridModel newItem) {
            return oldItem==newItem;
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
