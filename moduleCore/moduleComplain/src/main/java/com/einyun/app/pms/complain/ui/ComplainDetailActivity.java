package com.einyun.app.pms.complain.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.library.workorder.model.ComplainModel;
import com.einyun.app.pms.complain.R;
import com.einyun.app.pms.complain.databinding.ActivityComplainDetailBinding;
import com.einyun.app.pms.complain.viewmodel.DetailViewModel;
import com.einyun.app.pms.complain.viewmodel.ViewModelFactory;

import java.util.List;

@Route(path = RouterUtils.ACTIVITY_CUSTOMER_COMPLAIN_DETAIL)
public class ComplainDetailActivity extends BaseHeadViewModelActivity<ActivityComplainDetailBinding, DetailViewModel> {
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;
    @Override
    public int getLayoutId() {
        return R.layout.activity_complain_detail;
    }

    @Override
    protected DetailViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(DetailViewModel.class);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_work_complain);
        setRightOption(R.drawable.histroy);
        binding.setCallBack(this);
        viewModel.getComplainDetail(proInsId);
    }

//     if (zyjhgd.getF_FILES() != null) {
//        PhotoListAdapter adapter = new PhotoListAdapter(this);
//        binding.itemAlreadyResult.imgList.setLayoutManager(new LinearLayoutManager(
//                this,
//                LinearLayoutManager.HORIZONTAL,
//                false));
//        binding.itemAlreadyResult.imgList.addItemDecoration(new SpacesItemDecoration(18));
//        binding.itemAlreadyResult.imgList.setAdapter(adapter);
//        PicUrlModelConvert convert = new PicUrlModelConvert();
//        List<PicUrlModel> modelList = convert.stringToSomeObjectList(zyjhgd.getF_FILES());
//        adapter.updateList(modelList);
//    }

    @Override
    public void onOptionClick(View view) {
        super.onOptionClick(view);
//        ARouter.getInstance()
//                .build(RouterUtils.ACTIVITY_HISTORY)
//                .withString(RouteKey.KEY_ORDER_ID, id)
//                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
//                .navigation();
    }
}
