package com.einyun.app.base.db.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.einyun.app.base.db.entity.Distribute;
import com.einyun.app.base.db.entity.Patrol;

import java.util.List;

@Dao
public interface DistributeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Distribute> list);

    @Query("delete from distributes where userId=:userId and orderType=:orderType")
    void deleteAll(String userId,int orderType);

    @Query("select * from distributes where userId=:userId and orderType=:orderType") //ORDER BY F_PROC_DATE,createTime DESC
    DataSource.Factory<Integer, Distribute> queryAll(String userId,int orderType);

    @Query("select * from distributes where userId=:userId and orderType=:orderType ORDER BY F_PROC_DATE,createTime DESC LIMIT :current,:pageSize ")
    List<Distribute> page(int current, int pageSize,String userId,int orderType);
}
