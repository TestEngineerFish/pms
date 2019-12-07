package com.einyun.app.pms.sendorder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.adapter.SelectPeopleAdapter;
import com.einyun.app.pms.sendorder.databinding.ActivitySelectPeopleBinding;
import com.einyun.app.pms.sendorder.databinding.ActivitySendOrderBinding;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;
@Route(path = RouterUtils.ACTIVITY_SELECT_PEOPLE)
public class SelectPeopleActivity extends BaseHeadViewModelActivity<ActivitySelectPeopleBinding, SendOrderViewModel> {
     private SelectPeopleAdapter selectPeopleAdapter;
    @Override
    protected SendOrderViewModel initViewModel() {
        return new ViewModelProvider(this, new SendOdViewModelFactory()).get(SendOrderViewModel.class);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_select_people;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        selectPeopleAdapter=new SelectPeopleAdapter(SelectPeopleActivity.this);
        binding.selectPeopleList.setAdapter(selectPeopleAdapter);
    }
}
