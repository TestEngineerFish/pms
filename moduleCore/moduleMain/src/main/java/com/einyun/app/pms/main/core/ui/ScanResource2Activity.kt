package com.einyun.app.pms.main.core.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider

import com.alibaba.android.arouter.facade.annotation.Route
import com.einyun.app.common.constants.RouteKey
import com.einyun.app.common.service.RouterUtils
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity
import com.einyun.app.pms.main.R
import com.einyun.app.pms.main.core.ui.fragment.ScanBasicInfoFragment
import com.einyun.app.pms.main.core.ui.fragment.ScanResourceFragment
import com.einyun.app.pms.main.core.viewmodel.MineViewModel
import com.einyun.app.pms.main.core.viewmodel.ViewModelFactory
import com.einyun.app.pms.main.databinding.ActivityScanResBinding
import com.google.android.material.tabs.TabLayout

import java.util.ArrayList

import com.einyun.app.common.constants.RouteKey.FRAGMENT_HAVE_TO_FOLLOW_UP
import com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FEED_BACK
import com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP
//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_SCAN_RES)
class ScanResource2Activity : BaseHeadViewModelActivity<ActivityScanResBinding, MineViewModel>() {

    private var mTitles: Array<String>? = null
    override fun initViewModel(): MineViewModel {
        return ViewModelProvider(this, ViewModelFactory()).get(MineViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_scan_res
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        //        setTitleBarColor(R.color.white);
        //        setBackIcon(R.drawable.back);
        setTxtColor(resources.getColor(R.color.blackTextColor))
        setHeadTitle(R.string.resource_info)
        mTitles = resources.getStringArray(R.array.scan_res_list)
        val fragments = ArrayList<Fragment>()
        val fragmentTags =
            arrayOf(FRAGMENT_TO_FOLLOW_UP, FRAGMENT_TO_FEED_BACK, FRAGMENT_HAVE_TO_FOLLOW_UP)
        for (i in mTitles!!.indices) {
            val bundle = Bundle()
            bundle.putString(RouteKey.KEY_FRAGEMNT_TAG, fragmentTags[i])
            if (i == 2) {

                fragments.add(ScanBasicInfoFragment.newInstance(bundle))
            } else {

                fragments.add(ScanResourceFragment.newInstance(bundle))
            }
        }
        binding.vpCustomerInquiries.offscreenPageLimit = 3
        binding.vpCustomerInquiries.adapter =
            object : FragmentStatePagerAdapter(supportFragmentManager) {
                override fun getItem(i: Int): Fragment {
                    return fragments[i]
                }

                override fun getCount(): Int {
                    return mTitles!!.size
                }

                override fun getPageTitle(position: Int): CharSequence? {
                    return mTitles!![position]
                }

            }
        binding.tabSendOrder.setupWithViewPager(binding.vpCustomerInquiries)
        binding.tabSendOrder.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                //                viewModel.currentFragmentTag=fragmentTags[tab.getPosition()];
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }

    override fun initData() {
        super.initData()
    }

    override fun getColorPrimary(): Int {
        return resources.getColor(R.color.white)
    }
}
