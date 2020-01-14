package com.einyun.app.base.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.einyun.app.base.db.entity.BasicDataDb;
import com.einyun.app.base.db.entity.QualityRequest;

@Dao
public interface QualityRequestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(QualityRequest request);

    @Query("delete from quality_request where id=:id")
    void delete(String id);

    @Query("select * from quality_request where id=:id")
    QualityRequest queryRequest(String id);

}
