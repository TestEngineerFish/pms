package com.einyun.app.pms.sendorder.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.db.AppDatabase;
import com.einyun.app.base.db.dao.DistributeDao;
import com.einyun.app.base.db.entity.Distribute;
import com.einyun.app.base.db.entity.User;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.repository.CommonRepository;
import com.einyun.app.library.uc.user.model.UserModel;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class SendOrderRespository extends CommonRepository {
    AppDatabase db;
    DistributeDao dao;
    public SendOrderRespository() {
        db=AppDatabase.getInstance(CommonApplication.getInstance());
        dao=db.distributeDao();
    }

    /**
     *获取派工单数据源
     * @return
     */
    public DataSource.Factory<Integer, Distribute> queryAll(String userId,int orderType){
       return db.distributeDao().queryAll(userId,orderType);
    }

    /**
     * 删除全部派工单列表
     * @param userId
     * @param orderType
     */
    public void deleteAll(String userId,int orderType){
         dao.deleteAll(userId,orderType);
    }

}
