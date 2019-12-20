package com.einyun.app.pms.repairs.net.response;

import com.einyun.app.library.workorder.model.RepairsModel;

import java.util.List;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.repairs.net.response
 * @ClassName: RepairsResponse
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/09/04 09:51
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/04 09:51
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class RepairsResponse {
    private List<RepairsModel> rows;

    public List<RepairsModel> getRows() {
        return rows;
    }

    public void setRows(List<RepairsModel> rows) {
        this.rows = rows;
    }
}
