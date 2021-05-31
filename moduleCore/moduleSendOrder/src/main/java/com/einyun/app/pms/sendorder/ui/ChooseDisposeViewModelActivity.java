package com.einyun.app.pms.sendorder.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.util.ActivityUtil;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.searchhistory.SingleSearchFragment;
import com.einyun.app.common.ui.component.searchhistory.SearchListener;
import com.einyun.app.common.utils.LiveDataBusUtils;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse;
import com.einyun.app.pms.sendorder.BR;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.ActivityResendOrderChoosePersonBinding;
import com.einyun.app.pms.sendorder.databinding.ItemResendOrderChoosePersonBinding;
import com.einyun.app.pms.sendorder.viewmodel.SelectPeopleViewModel;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;


import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_CHOOSE_DISPOSE_PERSON_SEND_ORDER)
public class ChooseDisposeViewModelActivity extends BaseHeadViewModelActivity<ActivityResendOrderChoosePersonBinding, SelectPeopleViewModel> {

    @Autowired(name = RouteKey.KEY_ORG_ID_LIST)
    ArrayList<String> orgIdList;
    @Autowired(name = RouteKey.KEY_ROLE_ID_LIST)
    ArrayList<String> roleIdList;
    @Autowired(name = RouteKey.KEY_DIVIDE_NAME)
    String divideName;
    RVBindingAdapter<ItemResendOrderChoosePersonBinding, GetMappingByUserIdsResponse> adapter;
    public List<GetMappingByUserIdsResponse> users = new ArrayList<>();

    @Override
    protected SelectPeopleViewModel initViewModel() {
        return new ViewModelProvider(this, new SendOdViewModelFactory()).get(SelectPeopleViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_resend_order_choose_person;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        binding.tvDivideName.setText(divideName);
        setHeadTitle(R.string.select_person);
        setRightOption(R.mipmap.icon_search);
        RecyclerView mRecyclerView = binding.rvChooseDisposePerson;
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (adapter == null) {
            adapter = new RVBindingAdapter<ItemResendOrderChoosePersonBinding, GetMappingByUserIdsResponse>(this, BR.user) {
                @Override
                public void onBindItem(ItemResendOrderChoosePersonBinding binding, GetMappingByUserIdsResponse model, int position) {
                    binding.llOrg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LiveDataBusUtils.postResendOrderUser(model);
                            ActivityUtil.finishLastTwoActivity();
//                            ActivityUtil.finishToActivity(ResendOrderActivity.class);
                        }
                    });
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_resend_order_choose_person;
                }
            };
        }
        binding.rvChooseDisposePerson.setAdapter(adapter);

        viewModel.searchUserByCondition(roleIdList, orgIdList).observe(this, users -> {
            this.users = users;
            adapter.setDataList(users);
        });
        binding.rvChooseDisposePerson.addItemDecoration(new SpacesItemDecoration(0));
    }

    private SingleSearchFragment dialog;

    @Override
    public void onOptionClick(View view) {
        if (dialog == null) {
            dialog = new SingleSearchFragment<ItemResendOrderChoosePersonBinding, GetMappingByUserIdsResponse>(this, BR.user, new SearchListener<GetMappingByUserIdsResponse>() {
                @Override
                public LiveData<List<GetMappingByUserIdsResponse>> search(String search) {
                    MutableLiveData liveData = new MutableLiveData<List<OrgModel>>();
                    List<GetMappingByUserIdsResponse> list = new ArrayList<>();
                    for (GetMappingByUserIdsResponse user : users) {
                        if (StringUtil.isNullStr(user.getMobile())) {
                            if (user.getFullname().contains(search) || user.getMobile().contains(search)) {
                                list.add(user);
                            }
                        }else{
                            if (user.getFullname().contains(search)) {
                                list.add(user);
                            }
                        }
                    }
                    liveData.postValue(list);
                    return liveData;
                }

                @Override
                public void onItemClick(GetMappingByUserIdsResponse model) {
                    LiveDataBusUtils.postResendOrderUser(model);
                    ActivityUtil.finishLastTwoActivity();
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_resend_order_choose_person;
                }
            });
            dialog.setHint(getResources().getString(R.string.search_name_or_phone));
            dialog.show(getSupportFragmentManager(),"");
        } else {
            dialog.show(getSupportFragmentManager(),"");
        }
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildPosition(view) == 0)
                outRect.top = space;
        }
    }

}
