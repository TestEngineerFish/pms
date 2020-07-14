package com.einyun.app.pms.main.core.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.constants.RouteKey;

import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.fragment.BaseViewModelFragment;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.core.model.LineListModel;
import com.einyun.app.pms.main.core.model.ScanResModel;
import com.einyun.app.pms.main.core.ui.ScanResourceActivity;
import com.einyun.app.pms.main.core.viewmodel.MineViewModel;
import com.einyun.app.pms.main.core.viewmodel.ViewModelFactory;
import com.einyun.app.pms.main.databinding.FragmentScanBasicInfoBinding;
import com.orhanobut.logger.Logger;

import java.util.List;


/**
 * @ProjectName: pms_old
 * @Package: com.einyun.app.pms.sendorder.ui
 * @ClassName: SendWorkOrderFragment
 * @Description: java类作用描述
 * @Author: zhulufeng
 * @CreateDate: 2019/11/26 14:37
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/26 14:37
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ScanBasicInfoFragment extends BaseViewModelFragment<FragmentScanBasicInfoBinding, MineViewModel> {

    private ScanResourceActivity activity;
    private PhotoListAdapter photoOrderInfoAdapter;

    public static ScanBasicInfoFragment newInstance(Bundle bundle) {
        ScanBasicInfoFragment fragment = new ScanBasicInfoFragment();
        fragment.setArguments(bundle);
        Logger.d("setBundle->" + bundle.getString(RouteKey.KEY_FRAGEMNT_TAG));
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_scan_basic_info;
    }


    @Override
    protected void init() {
        super.init();

    }

    private void getResBasic() {
        binding.cvPatrol.setVisibility(View.GONE);
        /**
         * 获取资源基本信息
         * */
        viewModel.getRes(activity.resId).observe(this, model -> {

            switch (model.getResourceType()) {
                case "engineering_resources"://工程类
                    binding.llProject.setVisibility(View.VISIBLE);

                    binding.rlProChildType.setVisibility(View.GONE);
                    break;
                case "order_resources"://秩序类
                    binding.llProject.setVisibility(View.VISIBLE);

                    binding.rlProBuild.setVisibility(View.GONE);
                    binding.rlProFloor.setVisibility(View.GONE);
                    binding.rlProUnit.setVisibility(View.GONE);
                    binding.rlProImportant.setVisibility(View.GONE);
                    binding.rlProMaior.setVisibility(View.GONE);
                    binding.rlProSpace.setVisibility(View.GONE);
                    binding.rlProSystem.setVisibility(View.GONE);
                    binding.rlProSystemType.setVisibility(View.GONE);
                    binding.rlProChildSystem.setVisibility(View.GONE);
                    break;
                case "environmental_resources"://环境类

                    binding.llEnvironment.setVisibility(View.VISIBLE);
                    switch (model.getResourceClassificationPathValue()) {
                        case "绿化"://绿化
                            binding.llGreen.setVisibility(View.VISIBLE);
                            if (model.getLatin().isEmpty()) {
                                binding.rlLatin.setVisibility(View.GONE);
                            }
                            if (model.getBranch().isEmpty()) {
                                binding.rlBranch.setVisibility(View.GONE);
                            }
                            if (model.getGenus().isEmpty()) {
                                binding.rlGenus.setVisibility(View.GONE);
                            }
                            if (model.getPlaceOfOrigin().isEmpty()) {
                                binding.rlPlaceOfOrigin.setVisibility(View.GONE);
                            }
                            if (model.getHabit().isEmpty()) {
                                binding.rlHabit.setVisibility(View.GONE);
                            }

                            break;
                        case "保洁"://保洁
                            binding.llClean.setVisibility(View.VISIBLE);
                            break;

                            default:
//                                if (model.getLatin().isEmpty()) {
//                                    binding.rlLatin.setVisibility(View.GONE);
//                                }
//                                if (model.getBranch().isEmpty()) {
//                                    binding.rlBranch.setVisibility(View.GONE);
//                                }
//                                if (model.getGenus().isEmpty()) {
//                                    binding.rlGenus.setVisibility(View.GONE);
//                                }
//                                if (model.getPlaceOfOrigin().isEmpty()) {
//                                    binding.rlPlaceOfOrigin.setVisibility(View.GONE);
//                                }
//                                if (model.getHabit().isEmpty()) {
//                                    binding.rlHabit.setVisibility(View.GONE);
//                                }
//                                binding.llGreen.setVisibility(View.VISIBLE);

                                break;
                    }

                    break;
            }
            binding.setScanResModel(model);
            Log.e(TAG, "getResBasic: ");
        });
    }

    private void getPatrolBasic() {
        binding.llProject.setVisibility(View.GONE);
        binding.cvPatrol.setVisibility(View.VISIBLE);

        /**
         * 获取巡更点基本信息
         * */
        viewModel.getPatrol(activity.patrolId).observe(this, model -> {

            binding.setScanPatrolModel(model);
            //获取数据展示图片暂时注释
            PicUrlModelConvert convert = new PicUrlModelConvert();
            List<PicUrlModel> modelList = convert.stringToSomeObjectList(model.getPicExampleUrl());
            photoOrderInfoAdapter.updateList(modelList);
            Log.e(TAG, "getResBasic: ");
        });
    }

    protected String getFragmentTag() {
        return getArguments().getString(RouteKey.KEY_FRAGEMNT_TAG);
    }

    @Override
    protected void setUpView() {
    }

    @Override
    protected void setUpData() {

        activity = (ScanResourceActivity) getActivity();
        /**
         * 巡查图片
         */
        photoOrderInfoAdapter = new PhotoListAdapter(activity);
        binding.listPicOrderInfo.setLayoutManager(new LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.listPicOrderInfo.addItemDecoration(new SpacesItemDecoration(18));
        binding.listPicOrderInfo.setAdapter(photoOrderInfoAdapter);
        //获取数据展示图片暂时注释
//        PicUrlModelConvert convert = new PicUrlModelConvert();
//        List<PicUrlModel> modelList = convert.stringToSomeObjectList("");
//        photoOrderInfoAdapter.updateList(modelList);
        switch (activity.type) {
            case "30":

                getResBasic();
                break;
            case "31":
                getPatrolBasic();
                break;
        }
    }


    @Override
    protected MineViewModel initViewModel() {
        return new ViewModelProvider(getActivity(), new ViewModelFactory()).get(MineViewModel.class);
    }


    private static final String TAG = "CustomerInquiriesViewMo";


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
