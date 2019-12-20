package com.einyun.app.pms.create.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.Constants;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.widget.BottomPicker;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.SelectHouseView;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.uc.usercenter.model.HouseModel;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.library.workorder.model.ComplainModel;
import com.einyun.app.library.workorder.model.TypeAndLine;
import com.einyun.app.library.workorder.net.request.CreateClientComplainOrderRequest;
import com.einyun.app.pms.create.BR;
import com.einyun.app.pms.create.R;
import com.einyun.app.pms.create.SelectType;
import com.einyun.app.pms.create.databinding.ActivityAddComplainInfoBinding;
import com.einyun.app.pms.create.databinding.ActivityCreateClientComplainOrderBinding;
import com.einyun.app.pms.create.databinding.ItemComplainInfoBinding;
import com.einyun.app.pms.create.viewmodel.CreateViewModel;
import com.einyun.app.pms.create.viewmodel.CreateViewModelFactory;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建客户询问
 */
@Route(path = RouterUtils.ACTIVITY_ADD_COMPLAIN_INFO)
public class AddComplainInfoActivity extends BaseHeadViewModelActivity<ActivityAddComplainInfoBinding, CreateViewModel>  {
    @Autowired(name = RouteKey.KEY_PHONE)
    String phone;
    RVBindingAdapter<ItemComplainInfoBinding, ComplainModel> adapter;

    @Override
    protected CreateViewModel initViewModel() {
        return new ViewModelProvider(this, new CreateViewModelFactory()).get(CreateViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_complain_info;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.create_complain_order_title);
        if (adapter == null){
            adapter = new RVBindingAdapter<ItemComplainInfoBinding, ComplainModel>(this,BR.model) {
                @Override
                public void onBindItem(ItemComplainInfoBinding binding, ComplainModel model, int position) {

                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_complain_info;
                }
            };
        }
        viewModel.complainWorkListdPage(phone).observe(this, result -> {
            adapter.setDataList(result.getRows());
        });
        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

    }


    public void submit() {

    }
}
