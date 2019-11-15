package com.einyun.app.library.uc.user.net.request;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.user.core.net.request
 * @ClassName: 登陆网络请求实体类
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/08/30 14:45
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/08/30 14:45
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class LoginRequest {
    private String username;
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
