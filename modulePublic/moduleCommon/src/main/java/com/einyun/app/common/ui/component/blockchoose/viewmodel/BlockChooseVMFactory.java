package com.einyun.app.common.ui.component.blockchoose.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.einyun.app.base.BaseViewModelFactory;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.ui.component.blockchoose.viewmodel
 * @ClassName: BlockChooseVMFactory
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/15 11:06
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/15 11:06
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class BlockChooseVMFactory extends BaseViewModelFactory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BlockChooseViewModel.class)) {
            return (T) new BlockChooseViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
