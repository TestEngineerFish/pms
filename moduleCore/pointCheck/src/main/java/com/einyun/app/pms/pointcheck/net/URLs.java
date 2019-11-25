package com.einyun.app.pms.pointcheck.net;

/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck.net
 * @ClassName: URLs
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/08 15:12
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/08 15:12
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class URLs {
    //新增时获取该地块下的所有点检设置
    public static final String URL_POINT_CHECK_PROJECTS="resource/api/checkcenter/v1/checkRecord/getProject";

    //根据点检设置获取该点检设置下面的点检内容
    public static final String URL_POINT_CHECK_PROJECT_CONTENT="resource/api/checkcenter/v1/checkRecord/getContent/";

    //create new point check
    public static final String URL_POINT_CHECK_CREATE="resource/api/checkcenter/v1/checkRecord/add";

    //list of brief checkpoints
    public static final String URL_POINT_CHECK_LIST="resource/api/checkcenter/v1/checkRecord/list";

    public static final String URL_POINT_CHECK_DETIAL="resource/api/checkcenter/v1/checkRecord/get/";
}
