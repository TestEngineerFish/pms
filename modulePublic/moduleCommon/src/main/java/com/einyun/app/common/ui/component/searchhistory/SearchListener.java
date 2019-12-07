package com.einyun.app.common.ui.component.searchhistory;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface SearchListener<M> {
    LiveData<List<M>> search(String search);

    void onItemClick(M model);

    int getLayoutId();
}
