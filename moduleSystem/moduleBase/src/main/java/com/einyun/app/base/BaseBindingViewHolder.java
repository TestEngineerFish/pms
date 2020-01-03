package com.einyun.app.base;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.base
 * @ClassName: BaseBindingViewHolder
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/15 15:21
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/15 15:21
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class BaseBindingViewHolder <T extends ViewDataBinding> extends RecyclerView.ViewHolder{
    public  T getBinding() {
        return binding;
    }
    public Object data;
    private final T binding;

    public BaseBindingViewHolder(View itemView) {
        //构造方法，传入item布局文件生成的view
        super(itemView);
        //通过DataBindingUtil.bind()方法，使DataBinding绑定布局，并且返回ViewDataBinding的子类对象
        binding = DataBindingUtil.bind(itemView);
    }

}
