package com.einyun.app.pms.repairs.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.datasource.BaseDataSource;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.workorder.model.RepairGrabPage;
import com.einyun.app.library.workorder.model.RepairsPage;
import com.einyun.app.library.workorder.net.request.RepairsPageRequest;
import com.einyun.app.library.workorder.repository.WorkOrderRepository;
import com.einyun.app.base.paging.bean.PageBean;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_ALREDY_DONE;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_COPY_ME;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_GRAB;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_WAIT_FEED;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_WAIT_FOLLOW;

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
public class RepairsDataSource extends BaseDataSource<DictDataModel> {
    private RepairsPageRequest request;
    private String tag;
    public RepairsDataSource(RepairsPageRequest request,String tag) {
        this.request = request;
        this.tag=tag;
    }

    //根据页数获取数据
    public  <T> void loadData(PageBean pageBean,@NonNull T callback) {
        WorkOrderRepository repository = new WorkOrderRepository();
        request.setPageBean(pageBean);
        //抢单
        if (tag.equals(FRAGMENT_REPAIR_GRAB)) {
            repository.getRepairGrab(request, new CallBack<RepairsPage>() {
                @Override
                public void call(RepairsPage data) {
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
        }
        //待跟进
        if (tag.equals(FRAGMENT_REPAIR_WAIT_FOLLOW)) {
            repository.getRepairWaitFollow(request, new CallBack<RepairsPage>() {
                @Override
                public void call(RepairsPage data) {
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
        }
        //已跟进
        if (tag.equals(FRAGMENT_REPAIR_ALREADY_FOLLOW)) {
            repository.getRepaiAlreadyFollow(request, new CallBack<RepairsPage>() {
                @Override
                public void call(RepairsPage data) {
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
        }
        //已办结
        if (tag.equals(FRAGMENT_REPAIR_ALREDY_DONE)) {
            repository.getRepaiAlreadyDone(request, new CallBack<RepairsPage>() {
                @Override
                public void call(RepairsPage data) {
                    if (callback instanceof LoadInitialCallback) {
                        LoadInitialCallback loadInitialCallback = (LoadInitialCallback) callback;
                        Log.d("test", data.getRows().size() + "");
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
        }
        //待反馈
        if (tag.equals(FRAGMENT_REPAIR_WAIT_FEED)) {
            repository.getRepairWaitFeed(request, new CallBack<RepairsPage>() {
                @Override
                public void call(RepairsPage data) {
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
            //抄送我
            if (tag.equals(FRAGMENT_REPAIR_COPY_ME)) {
                repository.getRepairCopyMe(request, new CallBack<RepairsPage>() {
                    @Override
                    public void call(RepairsPage data) {
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
            }

        }
    }
    }

