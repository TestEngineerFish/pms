package com.einyun.app.base.db.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.einyun.app.base.db.entity.Plan;

import java.util.List;

@Dao
public interface PlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Plan> list);

    @Query("delete from plans where userId=:userId and listType=:listType")
    void deleteAll(String userId,int listType);

    @Query("select * from plans where userId=:userId and listType=:listType") //ORDER BY F_PROC_DATE,createTime DESC
    DataSource.Factory<Integer, Plan> queryAll(String userId, int listType);

}
