package com.einyun.app.pms.toll.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.einyun.app.base.db.entity.CreateUnQualityRequest;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.widget.BottomPicker;
import com.einyun.app.common.utils.CheckUtil;
import com.einyun.app.common.utils.FormatUtil;
import com.einyun.app.common.utils.IsFastClick;
import com.einyun.app.pms.toll.R;
import com.einyun.app.pms.toll.databinding.ActivityAddHouserBinding;
import com.einyun.app.pms.toll.model.AddHouserModel;
import com.einyun.app.pms.toll.model.AddHouserRequset;
import com.einyun.app.pms.toll.model.DicRelationModel;
import com.einyun.app.pms.toll.model.GetNameRequset;
import com.einyun.app.pms.toll.viewmodel.TollViewModel;
import com.einyun.app.pms.toll.viewmodel.TollViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_ADD_HOUSER)
public class AddHouserActivity extends BaseHeadViewModelActivity<ActivityAddHouserBinding, TollViewModel> {
    @Autowired(name = RouteKey.HOUSE_ID)
    String houseId;
    @Autowired(name = RouteKey.KEY_DIVIDE_ID)
    String divideId;
    int txDefaultPosID = 0;
    int txDefaultPosType = 0;
    int txDefaultPosSexy = 0;
    private CreateUnQualityRequest mRequest;
    private String format = "";
    private String formatDate;
    private AddHouserRequset mAddHouserRequset;
    private List<DicRelationModel.DataBean> mRelationLists=new ArrayList<>();
    private List<DicRelationModel.DataBean> mTypeLists=new ArrayList<>();

    @Override
    protected TollViewModel initViewModel() {
        return new ViewModelProvider(this, new TollViewModelFactory()).get(TollViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_houser;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(R.string.tv_add_houser);
        LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                Log.e("onChanged", "onChanged: " + aBoolean);
            }
        });
        viewModel.repository.getDicKey("house_client_rel_relation", new CallBack<DicRelationModel>() {
            @Override
            public void call(DicRelationModel data) {
                hideLoading();
                if (data!=null&&data.getData()!=null) {
                    mRelationLists = data.getData();
                }
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
//        viewModel.getDicKey("house_client_rel_relation").observe(AddHouserActivity.this,model->{
//            if (model!=null&&model.getData()!=null) {
//                mRelationLists = model.getData();
//            }
//        });
        viewModel.getDicKey("credentials_type").observe(AddHouserActivity.this,model->{
            if (model!=null&&model.getData()!=null) {
                mTypeLists = model.getData();
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        binding.setCallBack(this);
        formatDate=FormatUtil.getStringDate();
        mAddHouserRequset = new AddHouserRequset();
        binding.tvDate.setText(TimeUtil.getYMdTime(System.currentTimeMillis()).replace("-", "/"));
        binding.etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length()==11) {
                    String phone = charSequence.toString();
                    GetNameRequset getNameRequset = new GetNameRequset();
                    getNameRequset.setCellphone(phone);
                    getNameRequset.setDivideId(divideId);
                    viewModel.getNameFromPhone(getNameRequset).observe(AddHouserActivity.this,model->{
                        if (model!=null&&model.getData()!=null) {
                            binding.etName.setText(model.getData().getName().trim());
                            binding.etName.setFocusableInTouchMode(false);
                        }else {
                            binding.etName.setText("");
                            binding.etName.setFocusableInTouchMode(true);
                        }
                    });
                }else {
                    binding.etName.setText("");
                    binding.etName.setFocusableInTouchMode(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    private static final String TAG = "CreateDisqualifiedActiv";

    /**
     * ????????????
     */
    public void onChoiceIdClick() {
        selectID();
    }

    /**
     * ????????????
     */
    public void onChoiceSexyClick() {
        selectSexy();
    }

    /**
     * ??????????????????
     */
    public void onChoiceTypeClick() {

        selectType();
    }

    /**
     * ??????????????????
     */
    public void onChioceDateClick() {

        choosePayDate();
    }

    /**
     * ????????????
     */
    public void onPassClick() {
        if (IsFastClick.isFastDoubleClick()) {
            if (!CheckUtil.getInstance(this).isMatch(binding.etPhone.getText().toString().trim(), CheckUtil.REG_PHONE)) {
                ToastUtil.show(this, "???????????????????????????");
                return;
            }
            if (StringUtil.isEmpty(binding.etName.toString().trim())) {
                ToastUtil.show(this, "???????????????");
                return;
            }
            if (binding.tvType.getText().toString().equals("???????????????")) {
                String s = binding.etIdNum.getText().toString().trim();
                if (!StringUtil.isEmpty(s)) {
                    if (!CheckUtil.getInstance(this).isMatch(binding.etIdNum.getText().toString().trim(), CheckUtil.REG_IDNO)) {
                        ToastUtil.show(this, "?????????????????????????????????");
                        return;
                    }
                }
            }
            mAddHouserRequset.setCellphone(binding.etPhone.getText().toString());
            mAddHouserRequset.setName(binding.etName.getText().toString());
            mAddHouserRequset.setCredentialsNo(binding.etIdNum.getText().toString());
            mAddHouserRequset.setInDate(binding.tvDate.getText().toString().trim().replace("/","-"));
            mAddHouserRequset.setDivideId(divideId);
            mAddHouserRequset.setHouseId(houseId);
            mAddHouserRequset.setCredentialsType(mTypeLists.get(txDefaultPosType).getDicValue());
            mAddHouserRequset.setRelation(mRelationLists.get(txDefaultPosID).getDicValue());
            mAddHouserRequset.setGender(txDefaultPosSexy+"");
            viewModel.repository.AddHouser(mAddHouserRequset, new CallBack<AddHouserModel>() {
                @Override
                public void call(AddHouserModel data) {
                    hideLoading();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (data.getCode()==0) {
                                finish();
                            }else {
                                ToastUtil.show(AddHouserActivity.this,data.getMsg());
                            }
                        }
                    });
                }

                @Override
                public void onFaild(Throwable throwable) {
                    hideLoading();
                }
            });
//            viewModel.AddHouser(mAddHouserRequset).observe(AddHouserActivity.this,model->{
//
//                if (model.getCode()==0) {
//                    finish();
//                }else {
//                    ToastUtil.show(AddHouserActivity.this,model.getMsg());
//                }
//            });
        }
    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }

    private void selectType() {
        List<String> txStrLists = new ArrayList<>();
        List<String> txStrListKeys = new ArrayList<>();
        for (DicRelationModel.DataBean mTypeList : mTypeLists) {
            txStrLists.add(mTypeList.getDicText());
            txStrListKeys.add(mTypeList.getDicKey());
        }
//        txStrList.add("???????????????");
//        txStrList.add("?????????");
//        txStrList.add("?????????");
//        txStrList.add("?????????");
//        txStrList.add("?????????");
//        txStrList.add("?????????");
//        txStrList.add("??????");
//        txStrList.add("?????????");
//        txStrList.add("??????????????????");
//        txStrList.add("??????");
        BottomPicker.buildBottomPicker(this, txStrLists, txDefaultPosType, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                txDefaultPosType = position;
                binding.tvType.setText(txStrLists.get(position));

            }
        });
    }

    private void selectID() {
        List<String> txStrLists = new ArrayList<>();
        List<String> txStrListKeys = new ArrayList<>();
        for (DicRelationModel.DataBean mTypeList : mRelationLists) {
            txStrLists.add(mTypeList.getDicText());
            txStrListKeys.add(mTypeList.getDicKey());
        }
//        txStrList.add("??????");
//        txStrList.add("??????");
//        txStrList.add("????????????");
//        txStrList.add("?????????");
//        txStrList.add("?????????");
//        txStrList.add("??????");
        BottomPicker.buildBottomPicker(this, txStrLists, txDefaultPosID, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                txDefaultPosID = position;
                binding.tvIdentity.setText(txStrLists.get(position));

            }
        });
    }

    private void selectSexy() {
        List<String> txStrList = new ArrayList<>();
        txStrList.add("???");
        txStrList.add("???");
        BottomPicker.buildBottomPicker(this, txStrList, txDefaultPosSexy, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                txDefaultPosSexy = position;
                binding.tvSexy.setText(txStrList.get(position));

            }
        });
    }

    /**
     * ????????????
     */
    private void choosePayDate() {
        //???????????????
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat dft = new SimpleDateFormat("yyyy/MM/dd");
                binding.tvDate.setText(dft.format(date));
                formatDate = FormatUtil.dateToStrLong(date);

            }
        }).setType(new boolean[]{true, true, true, false, false, false})// ??????????????????
//                .setRangDate(startDate,endDate)
                .setLabel("???", "???", "???", "???", "???", "???")//?????????????????????????????????
                .build();
        pvTime.show();
    }
}
