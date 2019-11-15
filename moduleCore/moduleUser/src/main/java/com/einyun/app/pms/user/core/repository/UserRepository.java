package com.einyun.app.pms.user.core.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.db.entity.User;
import com.einyun.app.common.repository.CommonRepository;
import com.einyun.app.library.uc.user.model.UserModel;

import java.util.List;

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
    public LiveData<UserModel> localUser() {
        return new MutableLiveData<>(new UserModel("", "", "", "admin","A@1234cn"));
    }


    /**
     * 模拟数据库获取数据
     *
     * @return
     */
    public LiveData<List<User>> loadUsers() {
        MutableLiveData list = new MutableLiveData();
        BasicApplication.getInstance().getDatabase().userDao().loadAllUsers();
        list.postValue(list);
        return list;
    }
}
