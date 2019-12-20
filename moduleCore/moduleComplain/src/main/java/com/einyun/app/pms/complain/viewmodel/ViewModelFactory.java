package com.einyun.app.pms.complain.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.einyun.app.base.BaseViewModelFactory;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.repairs.viewmodel
 * @ClassName: ViewModelFactory
 * @Description: ViewModelFactory
 * @Author: chumingjun
 * @CreateDate: 2019/09/04 09:16
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/04 09:16
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ViewModelFactory extends BaseViewModelFactory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ComplainViewModel.class)){
            return (T)new ComplainViewModel();
        }
        if(modelClass.isAssignableFrom(DetailViewModel.class)){
            return (T)new DetailViewModel();
        }
        return super.create(modelClass);
    }
}
