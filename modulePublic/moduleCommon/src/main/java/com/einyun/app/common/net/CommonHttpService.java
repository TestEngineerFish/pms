package com.einyun.app.common.net;

import com.einyun.app.library.core.net.EinyunHttpService;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.net
 * @ClassName: CommonHttpService
 * @Description: 模块自定义网络
 * @Author: chumingjun
 * @CreateDate: 2019/09/10 17:25
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/10 17:25
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class CommonHttpService extends EinyunHttpService {
    private static CommonHttpService netWorkManager;
    private static Lock mLock = new ReentrantLock();


    private CommonHttpService() {
        super();
        init();
    }

    public static CommonHttpService getInstance() {
        if (netWorkManager == null) {
            mLock.lock();
            if (netWorkManager == null) {
                netWorkManager = new CommonHttpService();
            }
            mLock.unlock();
        }
        return netWorkManager;
    }


    public void init() {
        super.init();
        //添加自定义请求头

    }

}
