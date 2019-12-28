package com.einyun.app.common.ui.binding;

import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.einyun.app.common.R;
import com.einyun.app.common.databinding.LayoutListPageBinding;
import com.einyun.app.common.databinding.LayoutPageBinding;
import com.einyun.app.common.model.PageUIState;

public class PageUIStateAdapter {

    @BindingAdapter("page_state")
    public static void page_state(View view,int state){
        View page=view.findViewById(R.id.page);
        LayoutPageBinding binding=DataBindingUtil.getBinding(page);
        if(state== PageUIState.FILLDATA.getState()){
             view.setVisibility(View.GONE);
        }else if(state==PageUIState.EMPTY.getState()){
            binding.loading.getRoot().setVisibility(View.GONE);
            binding.loadFailed.getRoot().setVisibility(View.GONE);
            binding.empty.getRoot().setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
        }else if(state==PageUIState.LOADING.getState()){
            binding.loading.getRoot().setVisibility(View.VISIBLE);
            binding.loadFailed.getRoot().setVisibility(View.GONE);
            binding.empty.getRoot().setVisibility(View.GONE);
        }else if(state==PageUIState.LOAD_FAILED.getState()){
            binding.loadFailed.getRoot().setVisibility(View.VISIBLE);
            binding.empty.getRoot().setVisibility(View.GONE);
            binding.loading.getRoot().setVisibility(View.GONE);
        }
    }

    @BindingAdapter("page_list_state")
    public static void page_list_state(View view,int state){
        View page=view.findViewById(R.id.page);
        LayoutListPageBinding binding=DataBindingUtil.getBinding(page);
        if(state== PageUIState.FILLDATA.getState()){
            view.setVisibility(View.GONE);
        }else if(state==PageUIState.EMPTY.getState()){
            binding.loading.getRoot().setVisibility(View.GONE);
            binding.loadFailed.getRoot().setVisibility(View.GONE);
            binding.empty.getRoot().setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
        }else if(state==PageUIState.LOADING.getState()){
            binding.loading.getRoot().setVisibility(View.VISIBLE);
            binding.loadFailed.getRoot().setVisibility(View.GONE);
            binding.empty.getRoot().setVisibility(View.GONE);
        }else if(state==PageUIState.LOAD_FAILED.getState()){
            binding.loadFailed.getRoot().setVisibility(View.VISIBLE);
            binding.empty.getRoot().setVisibility(View.GONE);
            binding.loading.getRoot().setVisibility(View.GONE);
        }
    }
}
