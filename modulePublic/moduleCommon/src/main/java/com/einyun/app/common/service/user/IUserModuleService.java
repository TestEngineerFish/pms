package com.einyun.app.common.service.user;

import com.einyun.app.common.service.IBaseModuleService;

import java.util.List;

/**
 * Description:IUserModuleService
 *  用户模块对外暴露接口
 * @Author: chumingjun
 * @Email: 15951837502@163.com
 * Time: 2019/08/27
 * */
public interface IUserModuleService extends IBaseModuleService {
    public String getUserId();
    public String getUserName();
    public void saveDivideCodes(List<String> divideCodes);
    public List<String> getDivideCodes();
}
