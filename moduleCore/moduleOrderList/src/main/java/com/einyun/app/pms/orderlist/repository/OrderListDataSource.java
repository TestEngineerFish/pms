package com.einyun.app.pms.orderlist.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.paging.datasource.BaseDataSource;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrderPage;
import com.einyun.app.library.resource.workorder.model.OrderListPage;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.library.resource.workorder.net.request.OrderListPageRequest;
import com.einyun.app.library.resource.workorder.repository.ResourceWorkOrderRepo;
import com.einyun.app.library.workorder.model.RepairsPage;
import com.einyun.app.library.workorder.net.request.RepairsPageRequest;
import com.einyun.app.library.workorder.repository.WorkOrderRepository;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_ALREDY_DONE;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_COPY_ME;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_GRAB;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_WAIT_FEED;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_WAIT_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.ORDER_LIST_ASK;
import static com.einyun.app.common.constants.RouteKey.ORDER_LIST_COMPLAIN;
import static com.einyun.app.common.constants.RouteKey.ORDER_LIST_DISTRIBUTE;
import static com.einyun.app.common.constants.RouteKey.ORDER_LIST_PATRO;
import static com.einyun.app.common.constants.RouteKey.ORDER_LIST_PLAN;
import static com.einyun.app.common.constants.RouteKey.ORDER_LIST_REPAIR;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.repairs.repository
 * @ClassName: RepairsDataSource
 * @Description: Paging数据源
 * @Author: chumingjun
 * @CreateDate: 2019/09/04 09:45
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/04 09:45
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class OrderListDataSource extends BaseDataSource<DictDataModel> {
    private OrderListPageRequest request;
    private String tag;

    public OrderListDataSource(OrderListPageRequest request, String tag) {
        this.request = request;
        this.tag = tag;
    }

    //根据页数获取数据
    public <T> void loadData(PageBean pageBean, @NonNull T callback) {
        ResourceWorkOrderRepo repository = new ResourceWorkOrderRepo();
        request.setPageSize(PageBean.DEFAULT_PAGE_SIZE);
//        request.setPage(PageBean.DEFAULT_PAGE);
//        request.setPageBean(pageBean);
        //工单列表-派工单
        if (tag.equals(ORDER_LIST_DISTRIBUTE)) {
            repository.orderListDistribute(request, new CallBack<OrderListPage>() {
                @Override
                public void call(OrderListPage data) {
                    if (callback instanceof LoadInitialCallback) {
                        LoadInitialCallback loadInitialCallback = (LoadInitialCallback) callback;
                        loadInitialCallback.onResult(data.getRows(), 0, (int) data.getTotal());
                    } else if (callback instanceof LoadRangeCallback) {
                        LoadRangeCallback loadInitialCallback = (LoadRangeCallback) callback;
                        loadInitialCallback.onResult(data.getRows());
                    }
                }

                @Override
                public void onFaild(Throwable throwable) {
                    ThrowableParser.onFailed(throwable);
                }
            });
            return;
        }
        //工单列表-计划工单
        if (tag.equals(ORDER_LIST_PLAN)) {
            repository.orderListPlan(request, new CallBack<OrderListPage>() {
                @Override
                public void call(OrderListPage data) {
                    if (callback instanceof LoadInitialCallback) {
                        LoadInitialCallback loadInitialCallback = (LoadInitialCallback) callback;
                        loadInitialCallback.onResult(data.getRows(), 0, (int) data.getTotal());
                    } else if (callback instanceof LoadRangeCallback) {
                        LoadRangeCallback loadInitialCallback = (LoadRangeCallback) callback;
                        loadInitialCallback.onResult(data.getRows());
                    }
                }

                @Override
                public void onFaild(Throwable throwable) {
                    ThrowableParser.onFailed(throwable);
                }
            });
            return;
        }
        //工单列表-巡查工单
        if (tag.equals(ORDER_LIST_PATRO)) {
            repository.orderListPatro(request, new CallBack<OrderListPage>() {
                @Override
                public void call(OrderListPage data) {
                    if (callback instanceof LoadInitialCallback) {
                        LoadInitialCallback loadInitialCallback = (LoadInitialCallback) callback;
                        loadInitialCallback.onResult(data.getRows(), 0, (int) data.getTotal());
                    } else if (callback instanceof LoadRangeCallback) {
                        LoadRangeCallback loadInitialCallback = (LoadRangeCallback) callback;
                        loadInitialCallback.onResult(data.getRows());
                    }
                }

                @Override
                public void onFaild(Throwable throwable) {
                    ThrowableParser.onFailed(throwable);
                }
            });
            return;
        }
        //工单列表-客户报修
        if (tag.equals(ORDER_LIST_REPAIR)) {
            repository.orderListRepair(request, new CallBack<OrderListPage>() {
                @Override
                public void call(OrderListPage data) {
                    if (callback instanceof LoadInitialCallback) {
                        LoadInitialCallback loadInitialCallback = (LoadInitialCallback) callback;
                        loadInitialCallback.onResult(data.getRows(), 0, (int) data.getTotal());
                    } else if (callback instanceof LoadRangeCallback) {
                        LoadRangeCallback loadInitialCallback = (LoadRangeCallback) callback;
                        loadInitialCallback.onResult(data.getRows());
                    }
                }

                @Override
                public void onFaild(Throwable throwable) {
                    ThrowableParser.onFailed(throwable);
                }
            });
            return;
        }
       //工单列表-客户问询
        if (tag.equals(ORDER_LIST_ASK)) {
            repository.orderListAsk(request, new CallBack<OrderListPage>() {
                @Override
                public void call(OrderListPage data) {
                    if (callback instanceof LoadInitialCallback) {
                        LoadInitialCallback loadInitialCallback = (LoadInitialCallback) callback;
                        loadInitialCallback.onResult(data.getRows(), 0, (int) data.getTotal());
                    } else if (callback instanceof LoadRangeCallback) {
                        LoadRangeCallback loadInitialCallback = (LoadRangeCallback) callback;
                        loadInitialCallback.onResult(data.getRows());
                    }
                }

                @Override
                public void onFaild(Throwable throwable) {
                    ThrowableParser.onFailed(throwable);
                }
            });
            return;
        }
        //工单列表-客户投诉
        if (tag.equals(ORDER_LIST_COMPLAIN)) {
            repository.orderListComplain(request, new CallBack<OrderListPage>() {
                @Override
                public void call(OrderListPage data) {
                    if (callback instanceof LoadInitialCallback) {
                        LoadInitialCallback loadInitialCallback = (LoadInitialCallback) callback;
                        loadInitialCallback.onResult(data.getRows(), 0, (int) data.getTotal());
                    } else if (callback instanceof LoadRangeCallback) {
                        LoadRangeCallback loadInitialCallback = (LoadRangeCallback) callback;
                        loadInitialCallback.onResult(data.getRows());
                    }
                }

                @Override
                public void onFaild(Throwable throwable) {
                    ThrowableParser.onFailed(throwable);
                }
            });
            return;
        }


    }
}

