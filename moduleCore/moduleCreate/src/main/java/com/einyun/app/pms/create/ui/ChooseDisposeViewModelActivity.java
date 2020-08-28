package com.einyun.app.pms.create.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.searchhistory.SingleSearchFragment;
import com.einyun.app.common.ui.component.searchhistory.SearchListener;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.create.BR;
import com.einyun.app.pms.create.R;
import com.einyun.app.pms.create.databinding.ActivityChooseDisposePersonBinding;
import com.einyun.app.pms.create.databinding.ItemChoosePersonBinding;
import com.einyun.app.pms.create.viewmodel.CreateViewModel;
import com.einyun.app.pms.create.viewmodel.CreateViewModelFactory;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_CHOOSE_DISPOSE_PERSON)
public class ChooseDisposeViewModelActivity extends BaseHeadViewModelActivity<ActivityChooseDisposePersonBinding, CreateViewModel> {

    @Autowired(name = RouteKey.KEY_ORG_ID)
    String orgId;
    @Autowired(name = RouteKey.KEY_DIM_CODE)
    String dimCode;
    @Autowired(name = RouteKey.KEY_IS_UNQUALITY)
    boolean isUnquality;
    RVBindingAdapter<ItemChoosePersonBinding, OrgModel> adapter;
    private List<OrgModel> orgModels = new ArrayList<>();

    @Override
    protected CreateViewModel initViewModel() {
        return new ViewModelProvider(this, new CreateViewModelFactory()).get(CreateViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_dispose_person;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.select_person);
        setRightOption(R.mipmap.icon_search);
        RecyclerView mRecyclerView = binding.rvChooseDisposePerson;
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (adapter == null) {
            adapter = new RVBindingAdapter<ItemChoosePersonBinding, OrgModel>(this, BR.org) {
                @Override
                public void onBindItem(ItemChoosePersonBinding binding, OrgModel model, int position) {
                    binding.llOrg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(DataConstants.KEY_CHOOSE_DISPOSE_PERSON_CONTENT, model);
                            intent.putExtra(DataConstants.KEY_CHOOSE_DISPOSE_PERSON_CONTENT, bundle);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_choose_person;
                }
            };
        }
        binding.rvChooseDisposePerson.setAdapter(adapter);
        if (isUnquality) {//创建品检工单选择人员
            viewModel.getCheckedPerson(orgId).observe(this, orgModels -> {
                this.orgModels = orgModels;
                adapter.setDataList(orgModels);
            });
        }else {
            viewModel.getDisposePerson(orgId, dimCode).observe(this, orgModels -> {
                this.orgModels = orgModels;
                adapter.setDataList(orgModels);
            });
        }
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
    }

    private SingleSearchFragment dialog;

    @Override
    public void onOptionClick(View view) {
        if (dialog == null) {
            dialog = new SingleSearchFragment<ItemChoosePersonBinding, OrgModel>(this, BR.org, new SearchListener<OrgModel>() {
                @Override
                public LiveData<List<OrgModel>> search(String search) {
                    MutableLiveData liveData = new MutableLiveData<List<OrgModel>>();
                    List<OrgModel> list = new ArrayList<>();
                    for (OrgModel model : orgModels) {
                        if (model.getName().contains(search)) {
                            list.add(model);
                        }
                    }
                    liveData.postValue(list);
                    return liveData;
                }

                @Override
                public void onItemClick(OrgModel model) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(DataConstants.KEY_CHOOSE_DISPOSE_PERSON_CONTENT, model);
                    intent.putExtra(DataConstants.KEY_CHOOSE_DISPOSE_PERSON_CONTENT, bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_choose_person;
                }
            });
            dialog.setHint("请搜索姓名");
            dialog.show(getSupportFragmentManager(),"");
        } else {
            dialog.show(getSupportFragmentManager(),"");
        }
    }

}
