package com.einyun.app.pms.sendorder.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.adapter.SendOrderAdapter;
import com.einyun.app.pms.sendorder.databinding.FragmentSendWorkOrderBinding;
import com.einyun.app.pms.sendorder.databinding.ItemWorkSendBinding;
import com.einyun.app.pms.sendorder.model.SendOrderModel;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;

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
public class SendWorkOrderFragment  extends BaseViewModelFragment<FragmentSendWorkOrderBinding, SendOrderViewModel> {
    private SendOrderAdapter adapter;//适配器
//    RVPageListAdapter<ItemWorkSendBinding, SendOrderModel> adapter;
    public static SendWorkOrderFragment newInstance(Bundle bundle) {
        SendWorkOrderFragment fragment = new SendWorkOrderFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_send_work_order;
    }

    @Override
    protected void init() {
        super.init();

        //初始化组件
        adapter=new SendOrderAdapter(mDiffCallback,getActivity());
        RecyclerView mRecyclerView = binding.workSendList;
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }


    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData() {
       /* if(adapter==null){
            adapter=new RVPageListAdapter<ItemWorkSendBinding, SendOrderModel>(getActivity(), com.einyun.app.pms.sendorder.BR.sendOrderModel,mDiffCallback){

                @Override
                public void onBindItem(ItemWorkSendBinding binding, SendOrderModel model) {

                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_work_send;
                }

                @Override
                public int getItemCount() {
                   return 4;
                }
            };
        }
        binding.workSendList.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false));
        binding.workSendList.setAdapter(adapter);*/

    }

    @Override
    protected SendOrderViewModel initViewModel() {
        return null;
    }

    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<DictDataModel> mDiffCallback = new DiffUtil.ItemCallback<DictDataModel>() {

        @Override
        public boolean areItemsTheSame(@NonNull DictDataModel oldItem, @NonNull DictDataModel newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull DictDataModel oldItem, @NonNull DictDataModel newItem) {
            return false;
        }
    };
}
