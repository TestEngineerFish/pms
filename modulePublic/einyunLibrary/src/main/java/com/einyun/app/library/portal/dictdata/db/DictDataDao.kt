package com.einyun.app.library.portal.dictdata.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.einyun.app.library.portal.dictdata.model.DictDataModel

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.portal.dictdata.db
 * @ClassName:      DictDataDao
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/09/12 09:10
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/12 09:10
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
@Dao
interface DictDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts : List<DictDataModel>)

    @Query("SELECT * from dict_data")
    fun listAll(): LiveData<List<DictDataModel>>
}