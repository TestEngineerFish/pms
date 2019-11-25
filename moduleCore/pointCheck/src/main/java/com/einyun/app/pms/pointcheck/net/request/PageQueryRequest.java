package com.example.shimaostaff.pointcheck.net.request;

import com.example.shimaostaff.pointcheck.model.PageModel;

/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck.net.request
 * @ClassName: PageQueryRequest
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/09 13:41
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/09 13:41
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class PageQueryRequest {
    private PageModel pageBean;

    public PageModel getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageModel pageBean) {
        this.pageBean = pageBean;
    }
}
