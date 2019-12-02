package com.einyun.app.pms.sendorder.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.db.entity.User;
import com.einyun.app.common.repository.CommonRepository;
import com.einyun.app.library.uc.user.model.UserModel;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class SendOrderRespository extends CommonRepository {
    public SendOrderRespository() {
    }

    /**
     * 获取最后一个用户
     *
     * @return
     */
    public LiveData<UserModel> getLastUser() {
        MutableLiveData data = new MutableLiveData();
        Observable.just(1).subscribeOn(Schedulers.io()).doOnError(throwable -> {
        }).subscribe(integer -> {
            User user = BasicApplication.getInstance().getDatabase().userDao().selectUserLastUpdate();
            if (user != null) {
                Log.e("user  userName -> ", user.toString());
                data.postValue(new UserModel("", "", "", user.getUserName(), user.getPassword()));
            } else {
                data.postValue(new UserModel("", "", "", "", ""));
            }
        }, throwable -> {
            data.postValue(new UserModel("", "", "", "", ""));
        }, () -> {
        });
        return data;
    }
}
