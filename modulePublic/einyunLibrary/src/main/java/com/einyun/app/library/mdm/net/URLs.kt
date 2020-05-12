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

        //公告列表
        const val URL_NOTICE_LIST = "/noticeAndActivite/api/Announcement/v1/communityAnnouncement/listForCApp"
        //公告详情
        const val URL_NOTICE_DETAIL = "/noticeAndActivite/api/Announcement/v1/communityAnnouncement/get/"
        //点赞
        const val URL_NOTICE_UPDATE_LIKE_BAD = "/noticeAndActivite/api/Announcement/v1/communityAnnouncement/updateByMemberId"
        //新增阅读量
        const val URL_NOTICE_ADD_READING = "/noticeAndActivite/api/Announcement/v1/communityAnnouncement/addReading"
        //查询点赞差评状态
        const val URL_NOTICE_QUERY_UP_DOWN = "/noticeAndActivite/api/Announcement/v1/communityAnnouncement/queryThumbUpDownByCondition"

    }
}
