package com.einyun.app.pms.patrol.ui.fragment;

import android.view.View;

import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.databinding.ItemPatrolListBinding;

public class PatrolClosedListFragment extends PatrolPendingFragment{
    protected void initAdapter() {
        if(adapter==null){
            adapter=new RVPageListAdapter<ItemPatrolListBinding, Patrol>(getContext(), com.einyun.app.pms.patrol.BR.patrol,mDiffCallback) {

                @Override
                public void onBindItem(ItemPatrolListBinding binding, Patrol model) {
                    binding.itemCache.setVisibility(View.GONE);
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_patrol_list;
                }
            };
        }
    }
}
