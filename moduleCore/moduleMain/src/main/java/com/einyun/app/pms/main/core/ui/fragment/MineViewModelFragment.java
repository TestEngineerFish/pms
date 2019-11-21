package com.einyun.app.pms.main.core.ui.fragment;

import androidx.lifecycle.ViewModelProvider;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.core.viewmodel.MineViewModel;
import com.einyun.app.pms.main.core.viewmodel.ViewModelFactory;
import com.einyun.app.pms.main.databinding.FragmentMineBinding;

/**
 * 我的page
 */
public class MineViewModelFragment extends BaseViewModelFragment<FragmentMineBinding, MineViewModel> {


    public static MineViewModelFragment newInstance() {
        return new MineViewModelFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData() {

    }

    @Override
    protected MineViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(MineViewModel.class);
    }
}
