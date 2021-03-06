package com.einyun.app.common.ui.component.searchhistory;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;



import java.util.List;

public interface PageSearchListener<D extends ViewDataBinding,M> {
    LiveData<PagedList<M>> search(String search);

    //点击返回搜索
//    void searchClick(String search);

    void onItemClick(M model);
    void onItem(D binding, M model);

    int getLayoutId();
}
