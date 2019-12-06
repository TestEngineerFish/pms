package com.einyun.app.pms.sendorder.ui;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.event.Status;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.SelectPopUpView;
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.ActivitySendOrderBinding;
import com.einyun.app.pms.sendorder.ui.fragment.SendWorkOrderFragment;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: pms_old
 * @Package: com.einyun.app.pms.sendorder.ui
 * @ClassName: SendOrderActivity
 * @Description: java类作用描述
 * @Author: zhulufeng
 * @CreateDate: 2019/11/25 16:37
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/25 16:37
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
@Route(path = RouterUtils.ACTIVITY_SEND_ORDER)
public class SendOrderActivity extends BaseHeadViewModelActivity<ActivitySendOrderBinding, SendOrderViewModel> implements PeriodizationView.OnPeriodSelectListener {
    private String[] mTitles;//tab标题
    private SelectModel selectIfNot;
    private List<SelectModel> selectModelList=new ArrayList<>();//筛选展示的数据
    private String listJsonString;
    private List<SelectModel> selectModelListOrig = new ArrayList<>();//复制原始数据
    public static String divideId="";
    public static String tiaoxianId="";
    public static String typeFir="";
    public static String typeSec="";
    public static String typeThir="";
    public static String ifLate="";



    @Override
    protected SendOrderViewModel initViewModel() {
        return new ViewModelProvider(this, new SendOdViewModelFactory()).get(SendOrderViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_send_order;
    }
    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        mTitles=new String[]{getResources().getString(R.string.text_wait_handle),getResources().getString(R.string.text_already_handle)};
        setHeadTitle(R.string.text_send_order);
        setTxtColor(R.color.blackTextColor);
        setRightOption(R.drawable.scan);
        setBackIcon(R.drawable.back);
        final ArrayList<SendWorkOrderFragment> fragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString("tabId", i+"");
            fragments.add(SendWorkOrderFragment.newInstance(bundle));
        }

        binding.vpSendWork.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public SendWorkOrderFragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });
        binding.tabSendOrder.setupWithViewPager(binding.vpSendWork);
        binding.sendWorkOrerTabPeroidLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出分期view
                PeriodizationView periodizationView=new PeriodizationView();
                periodizationView.setPeriodListener(SendOrderActivity.this::onPeriodSelectListener);
                periodizationView.show(getSupportFragmentManager(),"");
            }
        });
        binding.sendWorkOrerTabSelectLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出筛选view
                handleData(viewModel.resourceTypeBeans,viewModel.listAll);

                for (ResourceTypeBean resourceTypeBean:viewModel.resourceTypeBeans){
                    for (SelectModel selectModel:viewModel.listAll){
                        if (resourceTypeBean.getText().equals("环境")||resourceTypeBean.getText().equals("工程")||resourceTypeBean.getText().equals("秩序")||resourceTypeBean.getText().equals("环境")||resourceTypeBean.getText().equals("客服")){
                            selectModel.setKey(resourceTypeBean.getId());
                        }
                    }
                }
                new SelectPopUpView(SendOrderActivity.this,selectModelListOrig).setOnSelectedListener(new SelectPopUpView.OnSelectedListener() {
                    @Override
                    public void onSelected(List<SelectModel> selectModelList) {
                        handleSelect(selectModelList);
                    }
                }).showAsDropDown(binding.sendWorkTabLn);

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        viewModel.getTiaoXian();
        viewModel.getOrderType();
        selectIfNot = new SelectModel();
        selectIfNot.setType("是否超期");
        SelectModel selectYes = new SelectModel();
        selectYes.setType("");
        selectYes.setId("1");
        selectYes.setContent("是");
        SelectModel selectNot = new SelectModel();
        selectNot.setType("");
        selectNot.setContent("否");
        selectNot.setId("2");
        List<SelectModel> selectModels = new ArrayList<>();
        selectModels.add(selectYes);
        selectModels.add(selectNot);
        selectIfNot.setSelectModelList(selectModels);
    }

    @Override
    protected int getColorPrimary() {
        return Color.WHITE;
    }

    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        binding.periodSelected.setText(orgModel.getName());
        viewModel.setOrgModel(orgModel);
        viewModel.refreshUI();
    }
   /**
    * 处理筛选展示数据
    * */
    private void handleData(List<ResourceTypeBean> resourceTypeBeans,List<SelectModel> listAll){
        selectModelList.removeAll(selectModelList);
        SelectModel selectModelTx=new SelectModel();
        selectModelTx.setType("条线");
        selectModelList.add(selectModelTx);
        //条线
        List<SelectModel> txBeans = new ArrayList<>();
        for (SelectModel beanLoop : listAll) {
            if (beanLoop.getParentId() != null &&
                    beanLoop.getTypeId() != null) {

                if (beanLoop.getParentId().equals(beanLoop.getTypeId())) {
                    beanLoop.setType("工单类型");
                    txBeans.add(beanLoop);
                }
            }
        }
        selectModelTx.setSelectModelList(txBeans);
        //工单类型一级
        List<SelectModel> listGdFir=new ArrayList<>();
        for (SelectModel gdFir:selectModelTx.getSelectModelList()){
            List<SelectModel> selectModels=new ArrayList<>();
            for (SelectModel beanLoop : listAll){
                if (beanLoop.getParentId()!=null&&beanLoop.getParentId().equals(gdFir.getId())){
                    selectModels.add(beanLoop);
                    listGdFir.add(beanLoop);
                }
            }
            gdFir.setSelectModelList(selectModels);
        }

        List<SelectModel> listGdSec=new ArrayList<>();
        //工单类型二级
        for (SelectModel gdSec:listGdFir){
            List<SelectModel> selectModels=new ArrayList<>();
            for (SelectModel beanLoop : listAll){
                if (beanLoop.getParentId()!=null&&beanLoop.getParentId().equals(gdSec.getId())){
                    selectModels.add(beanLoop);
                    listGdSec.add(beanLoop);
                }
            }
            gdSec.setSelectModelList(selectModels);
        }
        //工单类型三级
        List<SelectModel> listGdThir=new ArrayList<>();
        for (SelectModel gdThir:listGdSec){
            List<SelectModel> selectModels=new ArrayList<>();
            for (SelectModel beanLoop : listAll){
                if (beanLoop.getParentId()!=null&&beanLoop.getParentId().equals(gdThir.getId())){
                    selectModels.add(beanLoop);
                    listGdThir.add(beanLoop);
                }
            }
            gdThir.setSelectModelList(selectModels);
        }
        selectModelList.add(selectIfNot);
        listJsonString = new Gson().toJson(selectModelList);
        selectModelListOrig = new Gson().fromJson(listJsonString, new TypeToken<List<SelectModel>>() {
        }.getType());
    }

    /**
     * 处理筛选返回数据
     * */
    private void handleSelect(List<SelectModel> selectModels){
        List<String> selectedId=new ArrayList<>();
        for (SelectModel selectModel:selectModels){
            if (selectModel.getIsCheck()){
                selectedId.add(selectModel.getKey());
            }
            for (SelectModel selectModel1:selectModel.getSelectModelList()){
                if (selectModel1.getIsCheck()){
                    selectedId.add(selectModel1.getKey());
                }
                for (SelectModel selectModel2:selectModel1.getSelectModelList()) {
                    if (selectModel2.getIsCheck()) {
                        selectedId.add(selectModel2.getKey());
                    }
                    for (SelectModel selectModel3:selectModel2.getSelectModelList()) {
                        if (selectModel3.getIsCheck()) {
                            selectedId.add(selectModel3.getKey());
                        }
                        for (SelectModel selectModel4:selectModel3.getSelectModelList()) {
                            if (selectModel4.getIsCheck()) {
                                selectedId.add(selectModel4.getKey());
                            }
                        }
                    }
                }
            }
        }
    }
}
