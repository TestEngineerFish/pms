package com.einyun.app.pms.sendorder.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.library.resource.workorder.model.JobModel;
import com.einyun.app.library.resource.workorder.net.request.GetJobRequest;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.adapter.SelectPeopleAdapter;
import com.einyun.app.pms.sendorder.databinding.ActivitySelectPeopleBinding;
import com.einyun.app.pms.sendorder.databinding.ActivitySendOrderBinding;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

@Route(path = RouterUtils.ACTIVITY_SELECT_PEOPLE)
public class SelectPeopleActivity extends BaseHeadViewModelActivity<ActivitySelectPeopleBinding, SendOrderViewModel> {
     private SelectPeopleAdapter selectPeopleAdapter;
    @Autowired(name = RouteKey.KEY_DIVIDE_ID)
    String divideID;
    @Autowired(name = RouteKey.KEY_PROJECT_ID)
    String projectID;
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
        setHeadTitle(R.string.text_add_people);
    }

    @Override
    protected void initData() {
        super.initData();

        GetJobRequest request=new GetJobRequest();
        List<String> idList=new ArrayList<>();
        idList.add(divideID);
        idList.add(projectID);
        request.params=request.new params(idList);
        viewModel.getJob(request);
        viewModel.getOrgnization(divideID).observe(this,model ->{
                selectPeopleAdapter = new SelectPeopleAdapter(SelectPeopleActivity.this, viewModel.jobModels, viewModel.orgnizationModelLiveData);
                binding.selectPeopleList.setAdapter(selectPeopleAdapter);
        });
        viewModel.jobModels.observe(this,jobModels -> {
                selectPeopleAdapter = new SelectPeopleAdapter(SelectPeopleActivity.this, viewModel.jobModels, viewModel.orgnizationModelLiveData);
                binding.selectPeopleList.setAdapter(selectPeopleAdapter);
        });
    }
}
