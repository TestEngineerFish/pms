package com.example.shimaostaff.pointcheck.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shimaostaff.R;
import com.example.shimaostaff.activity.BaseActivity;
import com.example.shimaostaff.pointcheck.model.CheckPointModel;
import com.example.shimaostaff.pointcheck.model.State;
import com.example.shimaostaff.pointcheck.viewmodel.PointCheckListViewModel;
import com.example.shimaostaff.tools.PageHelper;
import com.example.shimaostaff.tools.PageHelperBuilder;
import com.example.shimaostaff.view.MyApp;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.twiceyuan.commonadapter.library.LayoutId;
import com.twiceyuan.commonadapter.library.ViewId;
import com.twiceyuan.commonadapter.library.adapter.CommonAdapter;
import com.twiceyuan.commonadapter.library.holder.CommonHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck.ui
 * @ClassName: PointCheckListActivity
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/09 11:41
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/09 11:41
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class PointCheckListActivity extends BaseActivity {
    PointCheckListViewModel viewModel;
    @Bind(R.id.iv_right_title)
    ImageView ivRightTitle;
    @Bind(R.id.tv_right_title)
    TextView tvRightTitle;
    @Bind(R.id.check_point_list)
    RecyclerView checkPointList;
    @Bind(R.id.srf_list)
    SmartRefreshLayout srfList;
    @Bind(R.id.ll_listwsj)
    LinearLayout llListwsj;

    private CommonAdapter adapter;
    private PageHelper pageHelper;

    @Override
    protected int getResourceId() {
        return R.layout.activity_check_point_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        pageHelper.refresh();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        super.initView();
        setTitle(R.string.title_point_check_list);
        ivRightTitle.setVisibility(View.VISIBLE);
        ivRightTitle.setImageResource(R.mipmap.add);
        viewModel = ViewModelProviders.of(this).get(PointCheckListViewModel.class);
        adapter = new CommonAdapter<>(this, CheckPointViewHolder.class);
        adapter.setOnItemClickListener((CommonAdapter.OnItemClickListener<CheckPointModel>) (position, model) -> {
//            ToastUtil.show(model.getCheckName()+"onClicked");
            Intent intent=new Intent(this,PointCheckDetialActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("CHECK_POINT_ID",model.getId());
            startActivity(intent);
        });
        pageHelper = new PageHelperBuilder<CheckPointModel>()
                .setDataSource((page, pageSize) -> {
                    viewModel.queryPage(page, pageSize).observe(this, checkPointPage -> {
                        if(checkPointPage!=null&&checkPointPage.getRows()!=null){
                            pageHelper.handleResult(checkPointPage.getPage(),checkPointPage.getRows());
                        }
                    });
                })
                .setRecyclerView(checkPointList)
                .setRefreshLayout(srfList)
                .setEmptyView(llListwsj)
                .setAdapter(adapter)
                .create();

        viewModel.state.observe(this, new Observer<State>() {
            @Override
            public void onChanged(@Nullable State state) {
                if(state==State.HIDELOADING){
                    pageHelper.finishRefresh();
                }
            }
        });
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        ivRightTitle.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreatePointCheckActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }

    @LayoutId(R.layout.item_check_point_brief)
    public static class CheckPointViewHolder extends CommonHolder<CheckPointModel> {
        @ViewId(R.id.iv_check_result)
        ImageView ivCheckResult;
        @ViewId(R.id.tv_check_result)
        TextView tvCheckResult;
        @ViewId(R.id.tv_check_no)
        TextView tvCheckNo;
        @ViewId(R.id.tv_check_block)
        TextView tvCheckBlock;
        @ViewId(R.id.tv_check_object)
        TextView tvCheckObject;
        @ViewId(R.id.tv_check_has_extra)
        TextView tvCheckHasExtra;
        @ViewId(R.id.tv_check_auther)
        TextView tvCheckAuther;
        @ViewId(R.id.tv_check_time)
        TextView tvCheckTime;
        @ViewId(R.id.tv_check_name)
        TextView tvCheckName;


        @Override
        public void bindData(CheckPointModel checkPointModel) {
            tvCheckName.setText(checkPointModel.getCheckName());
            tvCheckNo.setText(checkPointModel.getCheckRecordCode());
            tvCheckBlock.setText(checkPointModel.getMassifName());
            tvCheckObject.setText(checkPointModel.getCheckName());
            tvCheckAuther.setText(checkPointModel.getCreateName());
            tvCheckTime.setText(checkPointModel.getCreateTime());
            if(checkPointModel.getIsPic()>0){
                tvCheckHasExtra.setText(R.string.yes);
            }else{
                tvCheckHasExtra.setText(R.string.no);
            }
            if(checkPointModel.getIsUnusual()>0){
                tvCheckResult.setText(R.string.state_error);
                ivCheckResult.setImageResource(R.mipmap.error);
                tvCheckResult.setTextColor(MyApp.get().getResources().getColor(R.color.check_error));
            }else{
                tvCheckResult.setText(R.string.state_ok);
                ivCheckResult.setImageResource(R.mipmap.ok);
                tvCheckResult.setTextColor(MyApp.get().getResources().getColor(R.color.check_pass));
            }
        }
    }

}
