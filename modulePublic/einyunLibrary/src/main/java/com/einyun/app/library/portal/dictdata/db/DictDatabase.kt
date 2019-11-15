package com.einyun.app.library.portal.dictdata.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.einyun.app.library.portal.dictdata.model.DictDataModel

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.portal.dictdata.db
 * @ClassName:      DictDatabase
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/09/12 09:05
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/12 09:05
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
@Database(entities = [DictDataModel::class], version = 1)
abstract class DictDatabase : RoomDatabase() {
    companion object {
        private var instance: DictDatabase? = null
        private val LOCK = Any()
        fun instance(context: Context): DictDatabase {
            if (instance == null) {
                synchronized(LOCK) {
                    if (instance == null) {
                        instance = createDatabase(context)
                    }
                }
            }
            return instance!!
        }

        /**
         * create database
         */
        fun createDatabase(context: Context): DictDatabase {
            val databaseBuilder = Room.databaseBuilder(context, DictDatabase::class.java, "dict_data.db")
            return databaseBuilder
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }

    abstract fun dictDao(): DictDataDao
}