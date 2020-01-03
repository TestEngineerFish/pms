package com.einyun.app.library.portal.dictdata.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.library.portal.dictdata.model
 * @ClassName: DictDataModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/09/11 15:48
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/11 15:48
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
@Entity(tableName = "dict_data")
open class DictDataModel {
    @Ignore
    var children: List<DictDataModel>? = null
    @ColumnInfo(name = "create_by")
    var createBy: String? = null
    @ColumnInfo(name = "create_org_id")
    var createOrgId: String? = null
    @ColumnInfo(name = "create_time")
    var createTime: String? = null
    @PrimaryKey
    @NonNull
    var id: String? = null
    @ColumnInfo(name = "is_parent")
    var isParent: Boolean? = null
    @ColumnInfo(name = "key")
    var key: String? = null
    @ColumnInfo(name = "name")
    var name: String? = null
    @ColumnInfo(name = "open")
    var open: Boolean? = null
    @ColumnInfo(name = "parent_id")
    var parentId: String? = null
    @ColumnInfo(name = "sn")
    var sn: Int = 0
    @ColumnInfo(name = "text")
    var text: String? = null
    @ColumnInfo(name = "type_id")
    var typeId: String? = null
    @ColumnInfo(name = "update_by")
    var updateBy: String? = null
    @ColumnInfo(name = "update_time")
    var updateTime: String? = null
    @ColumnInfo(name = "type_key")
    var typeKey: String? = null
}
