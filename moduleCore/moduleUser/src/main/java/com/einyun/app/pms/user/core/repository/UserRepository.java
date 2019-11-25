package com.einyun.app.pms.user.core.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import androidx.work.impl.utils.LiveDataUtils;

import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.db.dao.UserDao;
import com.einyun.app.base.db.entity.User;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.common.repository.CommonRepository;
import com.einyun.app.library.uc.user.model.UserModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * 数据处理类，网络、数据库、本地数据等
 */
public class UserRepository extends CommonRepository {

    public UserRepository() {

    }

    /**
     * 模拟从本地获取缓存数据
     *
     * @return
     */
    public LiveData<UserModel> getLastUser() {
        MutableLiveData data = new MutableLiveData();
        Observable.just(1).subscribeOn(Schedulers.io()).subscribe(integer -> {
            User user = BasicApplication.getInstance().getDatabase().userDao().selectUserLastUpdate();
            if (user != null) {
                data.postValue(new UserModel("", "", "", user.getUserName(), user.getPassword()));
            }
        });
        return data;
    }


    /**
     * 模拟数据库获取数据
     *
     * @return
     */
    public LiveData<List<UserModel>> loadUsers() {
        MutableLiveData list = new MutableLiveData();
        Observable.just(1).subscribeOn(Schedulers.io()).subscribe(integer -> {
            list.postValue(BasicApplication.getInstance().getDatabase().userDao().loadAllUsers());
        });

        return list;
    }

    /**
     * 查询所有的用户名
     *
     * @return
     */
    public LiveData<List<String>> loadAllUserName() {
        MutableLiveData list = new MutableLiveData();
        Observable.just(1).subscribeOn(Schedulers.io()).subscribe(integer -> {
            list.postValue(BasicApplication.getInstance().getDatabase().userDao().loadAllUserName());
        });
        return list;
    }

    /**
     * 删除数据库字段
     *
     * @return
     */
    public void deleteUser(String userName) {
        Observable.just(1).subscribeOn(Schedulers.io()).subscribe(integer -> {
            User user = BasicApplication.getInstance().getDatabase().userDao().selectUserByName(userName);
            BasicApplication.getInstance().getDatabase().userDao().deleteUser(user);
        });
    }

    /**
     * 保存用户数据并更新数据库顺序
     */
    public void saveOrUpdateUser(UserModel userModel) {
        Observable.just(1).subscribeOn(Schedulers.io()).subscribe(integer -> {
            UserDao userDao = BasicApplication.getInstance().getDatabase().userDao();
            User user = new User(userModel.getUsername(), userModel.getPassword());
            if (userDao.selectUserByName(userModel.getUsername()) == null) {
                userDao.insertUsers(user);
            } else {
                userDao.updateUser(user);
            }
        });
    }
}
