package com.einyun.app.base.paging.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.paging.bean.PageBean;

/**
 * @ProjectName: pms
 * @Package: com.einyun.app.base.paging.viewmodel
 * @ClassName: BasePageListViewModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/11/20 0020 下午 14:21
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/20 0020 下午 14:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BasePageListViewModel<T> extends BaseViewModel {
    public LiveData<PagedList<T>> pageList;

    public void refresh(){
        if(pageList !=null){
            pageList.getValue().getDataSource().invalidate();
        }
    }

    protected PagedList.Config config = new PagedList.Config.Builder()
            .setPageSize(PageBean.DEFAULT_PAGE_SIZE)                         //配置分页加载的数量
            .setEnablePlaceholders(false)     //配置是否启动PlaceHolders
            .setInitialLoadSizeHint(PageBean.DEFAULT_PAGE_SIZE)              //初始化加载的数量
            .build();

}
