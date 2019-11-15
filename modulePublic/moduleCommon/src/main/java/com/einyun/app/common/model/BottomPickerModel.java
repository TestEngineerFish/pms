package com.einyun.app.common.model;

import java.util.List;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.model
 * @ClassName: BottomPickerModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/11/7 0007 下午 15:48
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/7 0007 下午 15:48
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BottomPickerModel {
    private String data;
    private List<String> dataList;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<String> getDataList() {
        return dataList;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }
}
