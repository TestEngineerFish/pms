package com.einyun.app.common.ui.widget.selectpopview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.einyun.app.common.R;

import java.util.ArrayList;
import java.util.List;


public class SMFilterAdapter extends RecyclerView.Adapter {

    public static final int LEVEL1 = 1;
    public static final int LEVEL2 = 2;

    private Context m_context;

    private List<SMFilterItem> m_items = new ArrayList<>();

    protected OnItemClickListener mOnItemClickListener;

    public SMFilterAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.m_context = context;
        mOnItemClickListener = onItemClickListener;
    }

    public void updateItems(List<SMFilterItem> items){
        m_items = items;

        notifyDataSetChanged();
    }

    public void reservedSelected(){
        if(m_items == null)
            return ;

        for (SMFilterItem item : m_items){
            List<SMFilterItem> children = item.getM_items();
            for (SMFilterItem child : children){
                child.setReservedSelected(child.isSelected());
            }
        }

    }

    public void makeReservedBack(){
        if(m_items == null)
            return ;

        for (SMFilterItem item : m_items){
            List<SMFilterItem> children = item.getM_items();
            for (SMFilterItem child : children){
                child.setSelected(child.isReservedSelected());
            }
        }

        notifyDataSetChanged();
    }

    public void resetSelected(){
        for (SMFilterItem item : m_items){
            item.setSelected(false);
            for (SMFilterItem child : item.getM_items()){
                child.setSelected(false);
            }
        }

        notifyDataSetChanged();
    }

    public List<SMFilterItem> getItems(){
        return m_items;
    }

    public SMFilterItem getItemAtPosition(int position){
        for (SMFilterItem item : m_items){
            if(position == 0)
                return item;

            if(position <= item.getM_items().size()){
                return item.getM_items().get(position - 1);
            }

            position -= 1 + item.getM_items().size();
        }

        return null;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == LEVEL1){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_filter_header, parent, false);
            return new SMFilterAdapter.HeaderHolder(itemView);
        }else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_filter_item, parent, false);
            return new SMFilterAdapter.ItemHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SMFilterItem cellItem = getItemAtPosition(position);
        switch (cellItem.getLevel()) {
            case LEVEL1:
                HeaderHolder headerHolder = (HeaderHolder)holder;
                headerHolder.m_tv_title.setText(cellItem.getName());
                headerHolder.m_tv_title.setVisibility(cellItem.getName().length() > 0?View.VISIBLE:View.GONE);
                break;
            case  LEVEL2:

                ItemHolder itemHolder = (ItemHolder)holder;
                itemHolder.setM_item(cellItem);
                itemHolder.m_tv_title.setText(cellItem.getName());
                itemHolder.m_iv_del.setVisibility(cellItem.isSelected()?View.VISIBLE:View.GONE);
                itemHolder.m_rlContainer.setBackgroundColor(ContextCompat.getColor(m_context, cellItem.isSelected()?R.color.color_FFD : R.color.color_FAF));

                break;
        }
    }

    @Override
    public int getItemViewType(int position) {

        SMFilterItem item = getItemAtPosition(position);

        return item.getLevel();
    }

    @Override
    public int getItemCount() {
        int count = 0;

        if(m_items != null){
            for (SMFilterItem item : m_items){
                count += 1 + item.getM_items().size();
            }
        }
        return count;
    }


    public class HeaderHolder extends RecyclerView.ViewHolder {


       private RelativeLayout  m_header_parent;
         private TextView m_tv_title;

        public HeaderHolder(@NonNull View itemView) {
            super(itemView);
         m_header_parent=(RelativeLayout)itemView.findViewById(R.id.header_parent);
         m_tv_title=(TextView)itemView.findViewById(R.id.tv_title);
        }
    }

    public class ItemHolder extends RecyclerView.ViewHolder{

       private RelativeLayout m_rlContainer;

       private TextView    m_tv_title;

       private ImageView   m_iv_del;

        private SMFilterItem m_item;

        public SMFilterItem getM_item() {
            return m_item;
        }

        public void setM_item(SMFilterItem m_item) {
            this.m_item = m_item;
        }

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
         m_rlContainer=(RelativeLayout)itemView.findViewById(R.id.rl_container);
         m_tv_title=(TextView)itemView.findViewById(R.id.tv_title);
         m_iv_del=(ImageView)itemView.findViewById(R.id.iv_del);
            m_rlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(m_item);
                    }
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(SMFilterItem floorItem);
    }
}
