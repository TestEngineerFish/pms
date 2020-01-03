package com.einyun.app.library.mdm.net

/**
 * 业主小区 房产接口
 *
 * 《业主小程序接口文档》
 */
class URLs {
    companion object{
        const val DOMAIN="mdm"

        //获取楼栋单元房产的树
        const val URL_MDM_DIVIDE_TREE_LIST= "/$DOMAIN/api/mdm/v1/divide/getTreeList?divideId="

        //小区下的楼栋列表查询接口（绑定房产用）
        const val URL_MDM_BUILD_LIST="/$DOMAIN/api/mdm/v1/building/list"

        //楼栋下的单元列表查询接口（绑定房产用）
        const val URL_MDM_BUILD_="/$DOMAIN/api/mdm/v1/unit/list"

        //单元下的房产列表查询接口（绑定房产用）
        const val URL_MDM_HOSE_LIST="/$DOMAIN/api/mdm/v1/house/list"

        //获取divideId
        const val URL_MDM_DIVIDE_BY_PLATCODE="/$DOMAIN/api/mdm/v1/divide/getDivideIdByPlatCode?code="

        //获取网格信息
        const val URL_MDM_GRID_INFO="/$DOMAIN/api/mdm/v1/newGridBasicInfo/list"
    }
}
