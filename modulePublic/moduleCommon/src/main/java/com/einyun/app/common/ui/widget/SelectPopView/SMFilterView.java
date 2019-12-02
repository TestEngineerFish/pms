package com.einyun.app.common.ui.widget.selectpopview;

import android.content.Context;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.einyun.app.common.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SMFilterView extends RelativeLayout {
    private View view;
    public interface FilterType{

        //头上的投诉列表筛选
        int Type_List_TS = 1;

        //头上的报修列表筛选
        int Type_List_BX = 2;

        //头上的问询列表筛选
        int Type_List_WX = 3;

        //头上的派工单列表筛选
        int Type_List_PGD = 4;

        //头上的计划工单列表筛选
        int Type_List_JHGD = 5;

        //头上的巡查工单列表筛选
        int Type_List_XCGD = 6;

        //底部的投诉列表筛选
        int Type_Bottom_List_TS = 7;

        //底部的报修列表筛选
        int Type_Bottom_List_BX = 8;

        //底部的问询列表筛选
        int Type_Bottom_List_WX = 9;

        //底部的派工单列表筛选
        int Type_Bottom_List_PGD = 10;

        //底部的计划工单列表筛选
        int Type_Bottom_List_JHGD = 11;

        //底部的巡查工单列表筛选
        int Type_Bottom_List_XCGD = 12;

        //审批列表筛选
        int Type_List_SP = 13;

        //工作预览筛选
        int Type_List_Work_YL = 14;

        //扫描二维码筛选
        int Type_List_Scan_BarCode = 15;

        //扫描历史列表筛选
        int Type_List_Scan_BarCode_History = 16;
    }

   private RelativeLayout  m_rlRightParent;

   private RelativeLayout  m_rlLeftParent;

   private RelativeLayout  m_rlAlphaBackground;

  private   RecyclerView m_rv_filter_content;

  private TextView m_btn_cancel;

  private   TextView    m_btn_confirm;

    private Context m_context ;

    private SMFilterAdapter m_adapter;

    private SMFilterViewInterface        m_interface;

    private SMFilterDataHelper m_dataHelper;
    private int                          m_type;
    private String                       m_extreValue;

    public void getItemSelected(Map<String, Object> container, SMFilterItem item){

        List<SMFilterItem> selectedItems = new ArrayList<>();
        for (SMFilterItem child : item.getM_items()){
            if(child.isSelected()) {
                selectedItems.add(child);
            }
        }

        if(selectedItems.size() == 1){
            SMFilterItem itemSelected = selectedItems.get(0);
            container.put(item.getKeyName(), itemSelected.getId());

            SMFilterItem grandson = itemSelected.getM_grandSon();
            if(grandson != null){
                getItemSelected(container, grandson);
            }
        }
        else if(selectedItems.size() > 1){
            List<String> selectedValues = new ArrayList<>();
            for (SMFilterItem selectedItem : selectedItems){
                selectedValues.add(selectedItem.getId());

                SMFilterItem grandson = selectedItem.getM_grandSon();
                if(grandson != null){
                    getItemSelected(container, grandson);
                }
            }

            container.put(item.getKeyName(), selectedValues);
        }


    }

    public  Map<String, Object> getItemSelected(){
        List<SMFilterItem> items = m_adapter.getItems();
        Map<String, Object> selectedKeyvalue = new HashMap<>();

        for (SMFilterItem item : items){

            getItemSelected(selectedKeyvalue, item);
        }

        return selectedKeyvalue;
    }

    public void setCallback(SMFilterViewInterface callBack){
        m_interface = callBack;
    }

    public void updateType(int type, String exterValue){
        m_type = type;
        m_extreValue = exterValue;

        List<SMFilterItem> items = m_dataHelper.getFilterDatas(m_type, exterValue);
        m_adapter.updateItems(items);
    }

    public void resetSelected(){
        m_adapter.resetSelected();

        List<SMFilterItem> items = m_dataHelper.getFilterDatas(m_type, m_extreValue);
        m_adapter.updateItems(items);
    }

    public SMFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.m_context = context;
        view=LayoutInflater.from(context).inflate(R.layout.item_filter_view, this, true);
        m_rlRightParent=(RelativeLayout)view.findViewById(R.id.rlRightParent);
        m_rlAlphaBackground=(RelativeLayout)view.findViewById(R.id.rlAlphaBackground);
        m_rv_filter_content=(RecyclerView)view.findViewById(R.id.rv_filter_content);
        m_btn_cancel=(TextView)view.findViewById(R.id.btn_cancel);
        m_btn_confirm=(TextView)view.findViewById(R.id.btn_confirm);
        m_rlRightParent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        m_btn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                m_adapter.resetSelected();
            }
        });

        m_btn_confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if(m_interface != null)
                    m_interface.onFilter(getItemSelected());

                animationHide(false);
            }
        });

        m_adapter = new SMFilterAdapter(getContext(), new SMFilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SMFilterItem floorItem) {
                //process

                floorItem.setSelected(!floorItem.isSelected());

                if(floorItem.isSelected()) {
                    for (SMFilterItem brother : floorItem.getM_parent().getM_items()) {
                        if(brother == floorItem)
                            continue;

                        if (floorItem.isDiscardBrothers() || brother.isDiscardBrothers()) {
                            brother.setSelected(false);
                        }
                    }
                }

                if(m_interface != null){
                    m_interface.onItemClicked(floorItem);
                }

                m_adapter.notifyDataSetChanged();
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(m_rv_filter_content.getContext(),
                3,
                GridLayoutManager.VERTICAL,
                false);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int i) {
                int level =  m_adapter.getItemViewType(i);
                switch (level){
                    case 1:
                        return 3;
                    default:
                        return 1;
                }
            }
        });
        m_rv_filter_content.setLayoutManager(gridLayoutManager);
        m_rv_filter_content.setAdapter(m_adapter);

        m_adapter.notifyDataSetChanged();

        m_dataHelper = new SMFilterDataHelper();
        m_dataHelper.m_interface = new SMFilterDataHelper.SMFilterDataInterface() {
            @Override
            public void onNetworkFilterDataResponds(String dataType, String exterValue, SMFilterItem itemFilter) {
                //
                List<SMFilterItem> items = m_dataHelper.getFilterDatas(m_type, exterValue);

                m_adapter.updateItems(items);
            }
        };
    }

    public void animationShowout(){

        m_adapter.reservedSelected();

        setVisibility(View.VISIBLE);

    }

    public void animationHide(boolean reservedBack){
        //

        TranslateAnimation hideAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        hideAnim.setDuration(300);
        m_rlRightParent.startAnimation(hideAnim);

        hideAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(View.GONE);

                if(reservedBack) {
                    m_adapter.makeReservedBack();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        AlphaAnimation alphaAnim = new AlphaAnimation(1, 0);
        alphaAnim.setDuration(300);
        m_rlAlphaBackground.startAnimation(alphaAnim);

    }

    public interface SMFilterViewInterface{
        void onFilter(Map<String, Object> items);
        void onItemClicked(SMFilterItem item);
    }
}
