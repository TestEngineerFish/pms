package com.einyun.app.common.application;

import com.einyun.app.base.http.BaseResponse;
import com.einyun.app.library.core.net.EinyunHttpException;

import org.jetbrains.annotations.NotNull;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.application
 * @ClassName: ModuleWorkException
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/14 17:16
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/14 17:16
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ModuleWorkException extends EinyunHttpException {
    public ModuleWorkException(@NotNull BaseResponse<Object> response) {
        super(response);
    }
}
