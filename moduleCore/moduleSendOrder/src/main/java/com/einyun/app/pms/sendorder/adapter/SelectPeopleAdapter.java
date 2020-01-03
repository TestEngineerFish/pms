package com.einyun.app.pms.sendorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.library.resource.workorder.model.JobModel;
import com.einyun.app.library.resource.workorder.model.OrgnizationModel;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.ItemSelectPeopleChildBinding;
import com.einyun.app.pms.sendorder.databinding.ItemSelectPeopleGroupBinding;
import com.einyun.app.pms.sendorder.ui.SendOrderDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class SelectPeopleAdapter extends BaseExpandableListAdapter {
    private Context mcontext;
    ItemSelectPeopleGroupBinding groupBinding;
    ItemSelectPeopleChildBinding childBinding;
    private MutableLiveData<List<JobModel>>jobList=new MutableLiveData<>();
    private MutableLiveData<OrgnizationModel> orgnizationModel;
    public SelectPeopleAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    public SelectPeopleAdapter(Context mcontext, MutableLiveData<List<JobModel>> list, MutableLiveData<OrgnizationModel> orgnizationModel) {
        this.mcontext = mcontext;
        this.jobList = list;
        this.orgnizationModel = orgnizationModel;
    }

    public String[] groupString = {"按组织架构选择", "按审批角色选择"};

    @Override
    public int getGroupCount() {
        return groupString.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition==0)
        {
            return 1;
        }else {
            return jobList.getValue().size();
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupString[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (groupPosition==0){
            return orgnizationModel;
        }else {
            return jobList.getValue().get(childPosition);
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view=LayoutInflater.from(mcontext).inflate(R.layout.item_select_people_group,null);
        groupBinding=DataBindingUtil.bind(view);
        groupBinding.itemSelectPeopleHead.setText(groupString[groupPosition]);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view=LayoutInflater.from(mcontext).inflate(R.layout.item_select_people_child,null);
        childBinding=DataBindingUtil.bind(view);
        if (groupPosition==0){
            childBinding.itemSelectChildTxt.setText(orgnizationModel.getValue().getName());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        ArrayList<String> list=new ArrayList<>();
                        list.add(orgnizationModel.getValue().getId());
                        list.add(orgnizationModel.getValue().getParentId());
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_CHOOSE_DISPOSE_PERSON_SEND_ORDER).withStringArrayList(RouteKey.KEY_ORG_ID_LIST,list)
                                .navigation();
                }
            });
        }else {
            childBinding.itemSelectChildTxt.setText(jobList.getValue().get(childPosition).getName());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> list1=new ArrayList<>();
                    list1.add(orgnizationModel.getValue().getId());
                    list1.add(orgnizationModel.getValue().getParentId());
                    ArrayList<String> list2=new ArrayList<>();
                    list2.add(jobList.getValue().get(childPosition).getId());
                    ARouter.getInstance().build(RouterUtils.ACTIVITY_CHOOSE_DISPOSE_PERSON_SEND_ORDER).withStringArrayList(RouteKey.KEY_ORG_ID_LIST,list1)
                            .withStringArrayList(RouteKey.KEY_JOB_ID_LIST,list2).navigation();
                }
            });
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
