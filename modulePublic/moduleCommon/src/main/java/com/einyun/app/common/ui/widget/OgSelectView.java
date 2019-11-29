package com.einyun.app.common.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.common.R;
import com.einyun.app.common.databinding.ItemBlockChooseBinding;
import com.einyun.app.common.databinding.OgSelectBinding;
import com.einyun.app.library.uc.usercenter.model.OrgModel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public   class OgSelectView extends RelativeLayout {
    private OgSelectBinding binding;//ogselectview   的binding
    private ItemBlockChooseBinding itemBlockChooseBinding;//item  的binding
    RVBindingAdapter<ItemBlockChooseBinding, OrgModel> adapter;
    List<OrgModel> selectOrgs=new CopyOnWriteArrayList<>();
    public OgSelectView(Context context) {
        super(context);
    }

    public OgSelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.og_select,this);
        initView(context);
    }

    /*public OgSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.og_select,null);
    }*/

    private void initView(Context context){
      binding= DataBindingUtil.setContentView((Activity)context, R.layout.og_select);

    }

}
