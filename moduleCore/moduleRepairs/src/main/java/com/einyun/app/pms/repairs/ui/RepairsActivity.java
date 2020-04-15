package com.einyun.app.pms.repairs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseActivity;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.library.workorder.model.AreaModel;
import com.einyun.app.pms.repairs.R;
import com.einyun.app.pms.repairs.databinding.RepairsActivityBinding;
import com.einyun.app.pms.repairs.ui.fragment.RepairsViewModelFragment;
import com.einyun.app.pms.repairs.viewmodel.RepairsViewModel;
import com.einyun.app.pms.repairs.viewmodel.ViewModelFactory;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_ALREDY_DONE;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_COPY_ME;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_GRAB;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_WAIT_FEED;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_WAIT_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_DONE;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_PENDING;

/**
 * demo of paging
 */
@Route(path = RouterUtils.ACTIVITY_REPAIRS_PAGING)
public class RepairsActivity extends BaseHeadViewModelActivity<RepairsActivityBinding, RepairsViewModel> implements View.OnClickListener {
    private String[] mTitles;//tab标题
    public static List<SelectModel> selectModelList = new ArrayList<>();
    private String taskId;
     ArrayList<RepairsViewModelFragment> fragments;
    public interface GrabListener{
        void onGrabed();
    }
    private GrabListener grabListener;
    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_work_repair);
        mTitles = new String[]{getResources().getString(R.string.text_grab_order), getResources().getString(R.string.text_wait_follow), getResources().getString(R.string.text_wait_feedback), getResources().getString(R.string.text_already_follow), getResources().getString(R.string.text_already_done), getResources().getString(R.string.text_copy_me)};
        fragments = new ArrayList<>();
        String fragmentTags[] = new String[]{FRAGMENT_REPAIR_GRAB, FRAGMENT_REPAIR_WAIT_FOLLOW, FRAGMENT_REPAIR_WAIT_FEED, FRAGMENT_REPAIR_ALREADY_FOLLOW, FRAGMENT_REPAIR_ALREDY_DONE, FRAGMENT_REPAIR_COPY_ME};
        for (int i = 0; i < mTitles.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString(RouteKey.KEY_FRAGEMNT_TAG, fragmentTags[i]);
            fragments.add(RepairsViewModelFragment.newInstance(bundle));
        }
        binding.vpRepair.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public RepairsViewModelFragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }

        });
        try {
            binding.tabRepairOrder.setupWithViewPager(binding.vpRepair);
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.tabRepairOrder.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                viewModel.currentFragmentTag=fragmentTags[tab.getPosition()];
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected RepairsViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(RepairsViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.repairs_activity;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Log.e("extras", "pushJump  is " + extras.getBoolean(RouteKey.KEY_PUSH_JUMP) + ",taskId = " + extras.getString(RouteKey.KEY_TASK_ID) + ",cateName = " + extras.getString(RouteKey.KEY_CATE_NAME));
        if (!StringUtil.isNullStr(getIntent().getType()) && extras.getBoolean(RouteKey.KEY_PUSH_JUMP)) {
            binding.grabFrame.getRoot().setVisibility(View.VISIBLE);
            binding.grabFrame.grabKind.setText(extras.getString(RouteKey.KEY_CATE_NAME));
            binding.grabFrame.grabClose.setOnClickListener(this);
            binding.grabFrame.grabBtn.setOnClickListener(this);
            taskId = extras.getString(RouteKey.KEY_TASK_ID);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.grab_close) {
            binding.grabFrame.getRoot().setVisibility(View.GONE);
            return;
        }
        if (v.getId() == R.id.grab_btn) {
            grab();
            return;
        }

    }

    /**
     * 抢单
     */
    private void grab() {
        if (taskId != null) {
            viewModel.grabRepair(taskId).observe(this, status -> {
                if (status.booleanValue()) {
//                    new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
//                            .setMsg(getResources().getString(R.string.text_grab_success)).
//                            setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    binding.grabFrame.getRoot().setVisibility(View.GONE);
//                                    fragments.get(0).loadPagingData();
//                                    fragments.get(1).loadPagingData();
//                                   /* if (grabListener!=null){
//                                        grabListener.onGrabed();
//                                    }*/
//                                    getIntent().setType("1");
//                                    binding.tabRepairOrder.getTabAt(1).select();
//                                }
//                            }).show();
                    ToastUtil.show(this,"抢单成功");
                    binding.grabFrame.getRoot().setVisibility(View.GONE);

                } else {
//                    new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
//                            .setMsg(getResources().getString(R.string.text_grab_faile)).
//                            setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    getIntent().setType("1");
//                                    binding.grabFrame.getRoot().setVisibility(View.GONE);
//                                }
//                            }).show();
                    ToastUtil.show(this,"该抢单任务已失效");
                    binding.grabFrame.getRoot().setVisibility(View.GONE);
                }
            });
        }
    }

    public void setLinstenr(GrabListener linstenr) {
        this.grabListener = linstenr;
    }
}
