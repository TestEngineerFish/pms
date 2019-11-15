package com.einyun.app.base.event;

import com.einyun.app.base.http.BaseResponse;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.user.core
 * @ClassName: CallBack
 * @Description: 回调类
 * @Author: chumingjun
 * @CreateDate: 2019/08/30 14:30
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/08/30 14:30
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface CallBack<T> {
    void call(T data);
    void onFaild(Throwable throwable);
}
