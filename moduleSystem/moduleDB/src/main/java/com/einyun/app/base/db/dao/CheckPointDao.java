package com.einyun.app.base.db.dao;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.einyun.app.base.db.entity.CheckPoint;
import com.einyun.app.base.db.entity.Patrol;

import java.util.List;

@Dao
public interface CheckPointDao {
    @Query("select * from checkpoints where userId=:userId")
    DataSource.Factory<Integer, CheckPoint> queryAll(@NonNull String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<CheckPoint> list);

    @Query("delete from checkpoints where userId=:userId")
    void deleteAll(String userId);
}
