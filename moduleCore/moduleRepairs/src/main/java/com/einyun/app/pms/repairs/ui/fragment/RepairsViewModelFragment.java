package com.einyun.app.pms.repairs.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.pms.repairs.R;
import com.einyun.app.pms.repairs.databinding.RepairsFragmentBinding;
import com.einyun.app.pms.repairs.viewmodel.RepairsViewModel;
import com.einyun.app.pms.repairs.viewmodel.ViewModelFactory;

/**
 * 报修已完结列表
 * Paging Component
 */
public class RepairsViewModelFragment extends BaseViewModelFragment<RepairsFragmentBinding,RepairsViewModel> {

    public static RepairsViewModelFragment newInstance() {
        return new RepairsViewModelFragment();
    }
    RepairsPageListAdapter adapter;//Paging PageListAdapter,auto paging
    @Override
    public int getLayoutId() {
        return R.layout.repairs_fragment;
    }


    @Override
    protected void init() {
        super.init();
        //初始化组件
        adapter=new RepairsPageListAdapter();
        RecyclerView mRecyclerView = binding.repairsList;
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void setUpView() {
        binding.swipeRefresh.setColorSchemeColors(Color.parseColor("#D1B26A"));
        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.swipeRefresh.setRefreshing(false);
            viewModel.refresh();
        });
    }

    private void loadPagingData(){
        //初始化数据，LiveData自动感知，刷新页面
        viewModel.loadPadingData().observe(this, dataBeans -> adapter.submitList(dataBeans));
    }

    @Override
    protected void setUpData() {
        loadPagingData();
    }



    @Override
    protected RepairsViewModel initViewModel() {
       return new ViewModelProvider(this, new ViewModelFactory()).get(RepairsViewModel.class);
    }

    //Paging PageListAdapter,auto paging
    class RepairsPageListAdapter extends PagedListAdapter<DictDataModel,RepairsViewHolder>{

        protected RepairsPageListAdapter() {
            super(mDiffCallback);
        }

        @NonNull
        @Override
        public RepairsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2,null);
            RepairsViewHolder holder = new RepairsViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RepairsViewHolder holder, int position) {
            DictDataModel model=getItem(position);
            holder.text1.setText(String.valueOf(model.getId()));
            holder.text2.setText(String.valueOf(model.getName()));
        }
    }

    /**
     * ViewHolder
     */
    private class RepairsViewHolder extends RecyclerView.ViewHolder {
        public TextView text1;
        public TextView text2;
        public RepairsViewHolder(@NonNull View itemView) {
            super(itemView);
            //viewholder view setup
            text1 = itemView.findViewById(android.R.id.text1);
            text1.setTextColor(Color.RED);

            text2 = itemView.findViewById(android.R.id.text2);
            text2.setTextColor(Color.BLUE);
        }
    }

    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<DictDataModel> mDiffCallback = new DiffUtil.ItemCallback<DictDataModel>() {

        @Override
        public boolean areItemsTheSame(@NonNull DictDataModel oldItem, @NonNull DictDataModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull DictDataModel oldItem, @NonNull DictDataModel newItem) {
            return oldItem==newItem;
        }
    };
}
