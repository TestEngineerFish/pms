package com.einyun.app.pms.repairs.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.repairs.R;
import com.einyun.app.pms.repairs.databinding.ActivityAddMaterialBinding;
import com.einyun.app.pms.repairs.databinding.ActivityRepairsDetailBinding;
import com.einyun.app.pms.repairs.viewmodel.RepairDetailViewModel;
import com.einyun.app.pms.repairs.viewmodel.ViewModelFactory;
@Route(path = RouterUtils.ACTIVITY_CUSTOMER_ADD_MATERIAL)
public class AddMaterialActivity extends BaseHeadViewModelActivity<ActivityAddMaterialBinding, RepairDetailViewModel> {


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
    public int getLayoutId() {
        return R.layout.activity_add_material;
    }
}
