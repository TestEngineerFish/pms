package com.einyun.app.base;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.base
 * @ClassName: ApplicationCrashHandler
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/15 14:20
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/15 14:20
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ApplicationCrashHandler implements Thread.UncaughtExceptionHandler{
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        BasicApplication.getInstance().exit();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
