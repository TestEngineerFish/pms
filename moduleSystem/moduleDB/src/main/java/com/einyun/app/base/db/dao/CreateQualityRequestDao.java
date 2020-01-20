package com.einyun.app.base.db.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.einyun.app.base.db.entity.CreateUnQualityRequest;
import com.einyun.app.base.db.entity.QualityRequest;
import com.einyun.app.base.db.entity.User;

import java.util.List;

@Dao
public interface CreateQualityRequestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CreateUnQualityRequest request);

    @Query("delete from create_unquality_request where code=:code")
    void delete(String code);

    @Query("select * from create_unquality_request where code=:code")
    CreateUnQualityRequest queryRequest(String code);

    @Query("SELECT * from create_unquality_request")
    DataSource.Factory<Integer,CreateUnQualityRequest> loadAllRequest();
}
