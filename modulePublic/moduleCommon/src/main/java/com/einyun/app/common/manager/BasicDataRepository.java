package com.einyun.app.common.manager;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.db.AppDatabase;
import com.einyun.app.base.db.entity.BasicDataDb;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.repository.CommonRepository;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * 数据处理类，网络、数据库、本地数据等
 */
public class BasicDataRepository extends CommonRepository {
    AppDatabase db;

    public BasicDataRepository() {
        db = AppDatabase.getInstance(CommonApplication.getInstance());
    }

    /**
     * 插入数据
     *
     * @return
     */
    public void insertData(@NonNull String basicDataTypeEnum, Object basicData) {
        Observable.just(1).subscribeOn(Schedulers.io()).subscribe(integer -> {
            db.basicDataDao().insert(new BasicDataDb(basicDataTypeEnum, basicData));
        });
    }

    /**
     * 删除数据库字段
     *
     * @return
     */
    public void queryData(@NonNull String basicDataTypeEnum, CallBack<BasicDataDb> callBack) {
        Observable.just(1).subscribeOn(Schedulers.io()).doOnError(throwable -> {
            Log.e("dataBase  error ===>", throwable.getMessage());
        }).subscribe(integer -> {
            BasicDataDb basicDataDb = db.basicDataDao().queryBasicData(basicDataTypeEnum);
            callBack.call(basicDataDb);
        });
    }
}
