package com.einyun.app.pms.repairs.net.request;

import com.einyun.app.base.paging.bean.PageBean;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.repairs.net.request
 * @ClassName: RepairsRequest
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/09/04 09:53
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/04 09:53
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class RepairsRequest {
    private PageBean pageBean;

    public PageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }
}
