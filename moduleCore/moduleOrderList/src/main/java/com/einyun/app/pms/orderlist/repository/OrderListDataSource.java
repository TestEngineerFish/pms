package com.einyun.app.pms.orderlist.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.paging.datasource.BaseDataSource;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.utils.LiveDataBusUtils;
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
 * @Description: Paging?????????
 * @Author: chumingjun
 * @CreateDate: 2019/09/04 09:45
 * @UpdateUser: ????????????
 * @UpdateDate: 2019/09/04 09:45
 * @UpdateRemark: ???????????????
 * @Version: 1.0
 */
public class OrderListDataSource extends BaseDataSource<DictDataModel> {
    private OrderListPageRequest request;
    private String tag;

    public OrderListDataSource(OrderListPageRequest request, String tag) {
        this.request = request;
        this.tag = tag;
    }

    //????????????????????????
    public <T> void loadData(PageBean pageBean, @NonNull T callback) {
        ResourceWorkOrderRepo repository = new ResourceWorkOrderRepo();
        if (tag.equals(ORDER_LIST_DISTRIBUTE) || tag.equals(ORDER_LIST_PATRO) || tag.equals(ORDER_LIST_PLAN)) {
            request.setPageSize(PageBean.DEFAULT_PAGE_SIZE);
            request.setPage(pageBean.getPage());
        } else {
            request.setParams(request.getParams());
            request.setPageBean(pageBean);
        }
//        request.setPageBean(pageBean);
        //????????????-?????????
        if (tag.equals(ORDER_LIST_DISTRIBUTE)) {
            repository.orderListDistribute(request, new CallBack<OrderListPage>() {
                @Override
                public void call(OrderListPage data) {
                    if (callback instanceof LoadInitialCallback) {
                        LoadInitialCallback loadInitialCallback = (LoadInitialCallback) callback;
                        loadInitialCallback.onResult(data.getRows(), 0, (int) data.getTotal());
                        LiveDataBusUtils.postLiveBusData(LiveDataBusKey.POST_PLAN_SEARCH+tag,data.getTotal());
                    } else if (callback instanceof LoadRangeCallback) {
                        LoadRangeCallback loadInitialCallback = (LoadRangeCallback) callback;
                        loadInitialCallback.onResult(data.getRows());
                    }
                    LiveDataBusUtils.postLiveBusData(LiveDataBusKey.ORDER_LIST_EMPTY,data.getTotal());
                }

                @Override
                public void onFaild(Throwable throwable) {
                    ThrowableParser.onFailed(throwable);
                }
            });
            return;
        }
        //????????????-????????????
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
                    LiveDataBusUtils.postLiveBusData(LiveDataBusKey.ORDER_LIST_EMPTY,data.getTotal());
                }

                @Override
                public void onFaild(Throwable throwable) {
                    ThrowableParser.onFailed(throwable);
                }
            });
            return;
        }
        //????????????-????????????
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
                    LiveDataBusUtils.postLiveBusData(LiveDataBusKey.ORDER_LIST_EMPTY,data.getTotal());
                }

                @Override
                public void onFaild(Throwable throwable) {
                    ThrowableParser.onFailed(throwable);
                }
            });
            return;
        }
        //????????????-????????????
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
                    LiveDataBusUtils.postLiveBusData(LiveDataBusKey.ORDER_LIST_EMPTY,data.getTotal());
                }

                @Override
                public void onFaild(Throwable throwable) {
                    ThrowableParser.onFailed(throwable);
                }
            });
            return;
        }
        //????????????-????????????
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
                    LiveDataBusUtils.postLiveBusData(LiveDataBusKey.ORDER_LIST_EMPTY,data.getTotal());
                }

                @Override
                public void onFaild(Throwable throwable) {
                    ThrowableParser.onFailed(throwable);
                }
            });
            return;
        }
        //????????????-????????????
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
                    LiveDataBusUtils.postLiveBusData(LiveDataBusKey.ORDER_LIST_EMPTY,data.getTotal());
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

