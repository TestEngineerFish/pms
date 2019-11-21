package com.einyun.app.common.service;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.service
 * @ClassName: RouterUtils
 * @Description: 共享服务路由
 * @Author: chumingjun
 * @CreateDate: 2019/08/29 18:44
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/08/29 18:44
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class RouterUtils {
    /**********************Service API******************************/
    public static final String SERVICE_USER="/user/service";
    public static final String SERVICE_MAIN="/main/service";
    public static final String SERVICE_EXTRA="/extra/service";

    /**********************Activity*********************************/
    public static final String ACTIVITY_USER_SPLASH="/user/splashActivity";
    public static final String ACTIVITY_MAIN_HOME="/main/home";
    public static final String ACTIVITY_USER_LOGIN="/user/loginActivity";
    public static final String ACTIVITY_REPAIRS_PAGING="/repairs/RepairsActivity";
    public static final String ACTIVITY_HEALTH_WORK="/health/HealthActivity";
    public static final String ACTIVITY_BLOCK_CHOOSE="/choose/BlockChooseActivity";
    public static final String ACTIVITY_X5_WEBVIEW="/webview/X5WebViewActivity";

    /***********************ACTIVITY REQUEST_CODE********************/
    public static final int ACTIVITY_REQUEST_BLOCK_CHOOSE =101;
    public static final int ACTIVITY_REQUEST_CAMERA_OK =102;
    public static final int ACTIVITY_REQUEST_REQUEST_PIC_PICK = 103;


    /**********************Fragment*********************************/
    public static final String FRAGMENT_REPAIRS_PAGING="/repairs/RepairsFragment";
}



