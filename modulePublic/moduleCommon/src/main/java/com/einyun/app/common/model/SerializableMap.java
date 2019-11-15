package com.einyun.app.common.model;

import java.util.Map;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.model
 * @ClassName: SerializableMap
 * @Description: 序列化map供Bundle传递map使用
 * @Author: chumingjun
 * @CreateDate: 2019/11/14 0014 下午 15:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/14 0014 下午 15:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SerializableMap {
    private Map<String,String> map;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
