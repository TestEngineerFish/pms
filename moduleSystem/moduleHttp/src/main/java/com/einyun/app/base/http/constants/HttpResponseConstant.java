package com.einyun.app.base.http.constants;

/**
 * Description: HTTP请求返回常量
 * @Author: chumingjun
 * @Email: 15951837502@163.com
 * Time: 2019/08/27
 * */
public interface HttpResponseConstant {

    /**
     * 服务器返回成功
     */
    public static final String RESPONSE_OK = "10000";

    /**
     * 重新登录
     */
    public static final int RE_LOGIN = 1000;

    /**
     * 解析异常
     */
    public static final int PARSE_ERROR = 1001;

    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1002;

    /**
     * 无网络
     */
    public static final int NONET = 1003;

    /**
     * token异常
     */
    public static final String TOKEN_EXCEPTION = "insufficient-user-permissions";

    /**
     * 密码异常
     */
    public static final String PASSWORD_EXCEPTION = " wen_wen.wc1002";


}
