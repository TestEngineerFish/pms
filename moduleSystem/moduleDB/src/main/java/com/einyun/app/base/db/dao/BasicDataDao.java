package com.einyun.app.base.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.einyun.app.base.db.entity.BasicDataDb;

@Dao
public interface BasicDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BasicDataDb basicData);

    @Query("delete from basic_data_table")
    void deleteAll();

    @Query("select * from basic_data_table where basicDataTypeEnum=:basicDataTypeEnum")
    BasicDataDb queryBasicData(String basicDataTypeEnum);

}
