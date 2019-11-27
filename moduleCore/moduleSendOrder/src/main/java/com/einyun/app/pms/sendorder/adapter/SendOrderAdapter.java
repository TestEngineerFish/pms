package com.einyun.app.pms.sendorder.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;

public class SendOrderAdapter extends PagedListAdapter<DictDataModel, SendOrderAdapter.SendOrderViewHolder> {
    private Context context;
    public SendOrderAdapter(@NonNull DiffUtil.ItemCallback<DictDataModel> diffCallback, Context context) {
        super(diffCallback);
        this.context=context;
    }

    @NonNull
    @Override
    public SendOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_work_send,null);
        SendOrderViewHolder holder = new SendOrderViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SendOrderViewHolder holder, int position) {
        holder.resendBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance()
                        .build(RouterUtils.ACTIVITY_RESEND_ORDER)
                        .navigation();
            }
        });
        holder.orderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance()
                        .build(RouterUtils.ACTIVITY_SEND_ORDER_DETAIL)
                        .navigation();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    /**
     * ViewHolder
     */
    public class SendOrderViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout resendBt;
        public LinearLayout orderDetail;
        public SendOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            resendBt=(RelativeLayout)itemView.findViewById(R.id.item_resend_re);
            orderDetail=(LinearLayout)itemView.findViewById(R.id.item_order_ln);
           }
    }
}
