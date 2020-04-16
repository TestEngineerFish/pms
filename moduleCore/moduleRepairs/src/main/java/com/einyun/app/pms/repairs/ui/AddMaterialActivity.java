package com.einyun.app.pms.repairs.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.library.workorder.model.RepairsDetailModel;
import com.einyun.app.pms.repairs.R;
import com.einyun.app.pms.repairs.databinding.ActivityAddMaterialBinding;
import com.einyun.app.pms.repairs.databinding.ActivityRepairsDetailBinding;
import com.einyun.app.pms.repairs.model.MaterialModel;
import com.einyun.app.pms.repairs.viewmodel.RepairDetailViewModel;
import com.einyun.app.pms.repairs.viewmodel.ViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;

@Route(path = RouterUtils.ACTIVITY_CUSTOMER_ADD_MATERIAL)
public class AddMaterialActivity extends BaseHeadViewModelActivity<ActivityAddMaterialBinding, RepairDetailViewModel> {
   RepairsDetailModel.DataBean.CustomerRepairModelBean.InitDataBean.RepairMaterialsBean materialModel;

    @Override
    protected RepairDetailViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(RepairDetailViewModel.class);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_select_material);
    }

    @Override
    protected void initData() {
        super.initData();
        materialModel=new RepairsDetailModel.DataBean.CustomerRepairModelBean.InitDataBean.RepairMaterialsBean();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_material;
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.addMaterialAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(binding.addMaterialAmount.getText())||TextUtils.isEmpty(binding.addMaterialPrice.getText().toString())){
                    binding.addMaterialTotalPrice.setText("");
                }else {
                    binding.addMaterialTotalPrice.setText(Float.parseFloat(binding.addMaterialAmount.getText().toString())*Float.parseFloat(binding.addMaterialPrice.getText().toString())+"");
                }

            }
        });
        binding.addMaterialPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(binding.addMaterialAmount.getText())||TextUtils.isEmpty(binding.addMaterialPrice.getText().toString())){
                    binding.addMaterialTotalPrice.setText("");
                }else {
                    binding.addMaterialTotalPrice.setText(Float.parseFloat(binding.addMaterialAmount.getText().toString())*Float.parseFloat(binding.addMaterialPrice.getText().toString())+"");
                }

            }
        });
        binding.addMaterialConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.addMaterialAmount.getText())){
                    ToastUtil.show(AddMaterialActivity.this,"请输入材料名称");
                    return;
                }
                if (TextUtils.isEmpty(binding.addMaterialName.getText())){
                    ToastUtil.show(AddMaterialActivity.this,"请输入单价");
                    return;
                }
                if (TextUtils.isEmpty(binding.addMaterialPrice.getText())){
                    ToastUtil.show(AddMaterialActivity.this,"请输入数量");
                    return;
                }
                materialModel.setQuantity(binding.addMaterialAmount.getText().toString());
                materialModel.setPrice(binding.addMaterialPrice.getText().toString());
                materialModel.setName(binding.addMaterialName.getText().toString());
                materialModel.setTotal_price(binding.addMaterialTotalPrice.getText().toString());
                LiveEventBus.get(LiveDataBusKey.POST_REPAIR_ADD_MATERIAL).post(materialModel);
                finish();
            }
        });
    }
}
