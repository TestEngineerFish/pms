package com.einyun.app.common.ui.component.searchhistory.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.db.AppDatabase;
import com.einyun.app.base.db.dao.UserDao;
import com.einyun.app.base.db.entity.SearchHistory;
import com.einyun.app.base.db.entity.User;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.repository.CommonRepository;
import com.einyun.app.library.uc.user.model.UserModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * 数据处理类，网络、数据库、本地数据等
 */
public class SearchHistoryRepository extends CommonRepository {
    AppDatabase db;

    public SearchHistoryRepository() {
        db = AppDatabase.getInstance(CommonApplication.getInstance());
    }

    /**
     * 查询历史记录
     *
     * @return
     */
    public LiveData<List<String>> loadAllSearchHistory(int type) {
        MutableLiveData list = new MutableLiveData();
        Observable.just(1).subscribeOn(Schedulers.io()).subscribe(integer -> {
            list.postValue(db.searchHistoryDao().loadAllSearchHistory(type));
        });
        return list;
    }

    /**
     * 删除历史记录
     *
     * @return
     */
    public void deleteSearchHistory() {

    }

    /**
     * 保存历史记录
     */
    public void insertSearchHistory(SearchHistory searchHistory) {
        Observable.just(1).subscribeOn(Schedulers.io()).subscribe(integer -> {
            db.searchHistoryDao().insertSearchHistory(searchHistory);
        });
    }
}
