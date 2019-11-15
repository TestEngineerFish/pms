package com.einyun.app.library.resource.workorder.net

import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest
import com.einyun.app.library.resource.workorder.net.request.PageRquest
import com.einyun.app.library.resource.workorder.net.request.PatrolPageRequest
import com.einyun.app.library.resource.workorder.net.response.DistributeListResponse
import com.einyun.app.library.resource.workorder.net.response.PatrolListResponse
import com.einyun.app.library.resource.workorder.net.response.PlanListResponse
import com.einyun.app.library.resource.workorder.net.response.WaitCountResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.library.resource
 * @ClassName: ResourceWorkOrderServiceApi
 * @Description: 资源工单接口
 *              请参考《资源工单接口文档》
 * @Author: chumingjun
 * @CreateDate: 2019/10/18 09:45
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/18 09:45
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
interface ResourceWorkOrderServiceApi{
    /**
     *  2.6待办统计-计划、巡查、派工单 GET
     */
    @GET(URLs.URL_RESOURCE_WORKORDER_WIAIT_COUNT)
    fun getWaitCount():Flowable<WaitCountResponse>

    /**
     *   2.7计划工单-待办
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_WAIT_PROCESS_LIST)
    fun planWaitPage(request:PageRquest):Flowable<PlanListResponse>

    /**
     * 2.14计划工单-已办
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_PLAN_DONE)
    fun planDonePage(request:PageRquest):Flowable<PlanListResponse>

    /**
     * 2.54巡查工单-待办
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_PATROL_WAIT)
    fun patrolWaitPage(request:PatrolPageRequest):Flowable<PatrolListResponse>

    /**
     * 2.55巡查工单-已办
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_PATROL_DONE)
    fun patrolDonePage(request:PatrolPageRequest):Flowable<PatrolListResponse>

    /**
     * 2.31派工单-待办列表
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_DISTRIBUTE_WAIT)
    fun distributeWaitPage(request:DistributePageRequest):Flowable<DistributeListResponse>

    /**
     * 2.31派工单-已办列表
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_DISTRIBUTE_DONE)
    fun distributeDonePage(request:DistributePageRequest):Flowable<DistributeListResponse>

    /**
     * 2.30派工单-列表（根据地块id、条线编码）
     */

}