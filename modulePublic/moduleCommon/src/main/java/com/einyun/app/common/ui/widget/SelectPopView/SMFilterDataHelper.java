package com.einyun.app.common.ui.widget.selectpopview;


/*import com.alibaba.fastjson.JSON;
import com.example.shimaostaff.bean.ComplainPropertyBean;
import com.example.shimaostaff.bean.GetByTypeKeyBean;
import com.example.shimaostaff.bean.GetByTypeKeyForComBoBean;
import com.example.shimaostaff.bean.GetByTypeKeyInnerAuditStatusBean;
import com.example.shimaostaff.bean.GetLineAndTypeBean;
import com.example.shimaostaff.bean.GetRepairTypeMapListBean;
import com.example.shimaostaff.bean.GridBasicInfoBean;
import com.example.shimaostaff.bean.GridRangeBean;
import com.example.shimaostaff.bean.PGdlxBean;
import com.example.shimaostaff.bean.RepairAreaBean;
import com.example.shimaostaff.bean.ResourceTypeBean;
import com.example.shimaostaff.bean.TSGetBaseListBean;
import com.example.shimaostaff.bean.WorkOrderStatusBean;
import com.example.shimaostaff.net.Constants;
import com.example.shimaostaff.net.RequestData;
import com.example.shimaostaff.net.okhttp.callback.ResponseCallBack;
import com.example.shimaostaff.view.MyApp;
import com.google.gson.JsonArray;*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SMFilterDataHelper {

    public interface SMFilterDataType{
        String FilterData_TS_XZ = "1";
        String FilterData_TS_LB = "2";
        String FilterData_WX_LB = "3";
        String FilterData_BX_AREA = "4";
        String FilterData_BX_DL = "5";
        String FilterData_BX_XL = "6";
        String FilterData_FINISH_DATE = "8";
        String FilterData_TIME_OUT = "9";

        String FilterData_TX = "10";
        String FilterData_GDLX = "11";
        String FilterData_WG = "12";
        String FilterData_LD = "13";
        String FilterData_UNIT = "14";
        String FilterData_SORT_LC = "15";

        String FilterData_TS_LIST_STATUS = "16";
        String FilterData_WX_LIST_STATUS = "17";
        String FilterData_BX_LIST_STATUS = "18";

        String FilterData_PDG_STATUS = "19";
        String FilterData_JHGD_STATUS = "20";
        String FilterData_JHGD_TX = "21";
        String FilterData_XCGD_TX = "22";
        String FilterData_XCGD_FL = "23";

        String FilterData_CreateBy = "25";
        String FilterData_TimeSpan = "26";

        String FilterData_SP_DL = "30";
        String FilterData_SP_XL = "31";
        String FilterData_SP_STAUTS = "32";

        String FilterData_WORK_MONTH = "35";

        String FilterData_Scan_TX = "40";
        String FilterData_Scan_GDLX = "41";

    }

    //顶部投诉的过滤条件列表
    public List<String>  arrayTypeListTS = new ArrayList<String>(){{add(SMFilterDataType.FilterData_TS_XZ);
    add(SMFilterDataType.FilterData_TS_LB);}};

    public List<String>  arrayTypeListWX = new ArrayList<String>(){{ add(SMFilterDataType.FilterData_WX_LB);}};

    public List<String>  arrayTypeListBX = new ArrayList<String>(){{ add(SMFilterDataType.FilterData_BX_AREA);}};

    public List<String>  arrayTypeListPGD = new ArrayList<String>(){{ add(SMFilterDataType.FilterData_TX);
        add(SMFilterDataType.FilterData_TIME_OUT);}};

    public List<String>  arrayTypeListJHGD = new ArrayList<String>(){{ add(SMFilterDataType.FilterData_FINISH_DATE);
        add(SMFilterDataType.FilterData_TIME_OUT);}};

    public List<String> arrayTypeListXCGD = new ArrayList<String>(){{ add(SMFilterDataType.FilterData_WG);
        add(SMFilterDataType.FilterData_SORT_LC); add(SMFilterDataType.FilterData_FINISH_DATE);
        add(SMFilterDataType.FilterData_TIME_OUT);}};

    public List<String>  arrayTypeBottomListTS = new ArrayList<String>(){{add(SMFilterDataType.FilterData_TS_XZ);
        add(SMFilterDataType.FilterData_TS_LB); add(SMFilterDataType.FilterData_TS_LIST_STATUS);
        add(SMFilterDataType.FilterData_CreateBy);}};

    public List<String>  arrayTypeBottomListBX = new ArrayList<String>(){{ add(SMFilterDataType.FilterData_BX_AREA);
        add(SMFilterDataType.FilterData_BX_LIST_STATUS);add(SMFilterDataType.FilterData_CreateBy);}};

    public List<String>  arrayTypeBottomListWX = new ArrayList<String>(){{ add(SMFilterDataType.FilterData_WX_LB);
        add(SMFilterDataType.FilterData_WX_LIST_STATUS);add(SMFilterDataType.FilterData_CreateBy);}};

    public List<String>  arrayTypeBottomListPGD = new ArrayList<String>(){{ add(SMFilterDataType.FilterData_TX);
        add(SMFilterDataType.FilterData_PDG_STATUS);add(SMFilterDataType.FilterData_TIME_OUT);
        add(SMFilterDataType.FilterData_CreateBy);}};

    public List<String>  arrayTypeBottomListJHGD = new ArrayList<String>(){{add(SMFilterDataType.FilterData_JHGD_TX);
        add(SMFilterDataType.FilterData_JHGD_STATUS); add(SMFilterDataType.FilterData_TIME_OUT);}};

    public List<String> arrayTypeBottomListXCGD = new ArrayList<String>(){{
        add(SMFilterDataType.FilterData_XCGD_TX);
        add(SMFilterDataType.FilterData_WG);
        add(SMFilterDataType.FilterData_SORT_LC);
        add(SMFilterDataType.FilterData_JHGD_STATUS);
        add(SMFilterDataType.FilterData_TIME_OUT);}};

    public List<String> arrayTypeListSP = new ArrayList<String>(){{
        add(SMFilterDataType.FilterData_SP_DL);
        add(SMFilterDataType.FilterData_SP_STAUTS); }};

    public List<String> arrayTypeListWORKPreview = new ArrayList<String>(){{
        add(SMFilterDataType.FilterData_WORK_MONTH); }};


    public List<String> arrayTypeListScanBarCode = new ArrayList<String>(){{
        add(SMFilterDataType.FilterData_Scan_TX);
        add(SMFilterDataType.FilterData_Scan_GDLX);
        add(SMFilterDataType.FilterData_PDG_STATUS);
        add(SMFilterDataType.FilterData_TIME_OUT); }};

    public List<String> arrayTypeListScanBarCodeHistory = new ArrayList<String>(){{
        add(SMFilterDataType.FilterData_Scan_TX);
        add(SMFilterDataType.FilterData_Scan_GDLX);
        add(SMFilterDataType.FilterData_PDG_STATUS);
        add(SMFilterDataType.FilterData_TIME_OUT); }};

    private HashMap<String, SMFilterItem>    m_datas = new HashMap<String, SMFilterItem>();

    //process
    public SMFilterDataInterface        m_interface;

    public List<SMFilterItem> getFilterDatas(int type, String exterValue){
        List<SMFilterItem> items = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        switch (type){
            case SMFilterView.FilterType.Type_List_TS:{
                keys = arrayTypeListTS;
            }break;
            case SMFilterView.FilterType.Type_List_WX:{
                keys = arrayTypeListWX;
            }break;
            case SMFilterView.FilterType.Type_List_BX:{
                keys = arrayTypeListBX;
            }break;
            case SMFilterView.FilterType.Type_List_PGD:{
                keys = arrayTypeListPGD;
            }break;
            case SMFilterView.FilterType.Type_List_JHGD:{
                keys = arrayTypeListJHGD;
            }break;
            case SMFilterView.FilterType.Type_List_XCGD:{
                if(exterValue == null || exterValue.length() == 0)
                    return items;
                keys = arrayTypeListXCGD;
            }break;
            case SMFilterView.FilterType.Type_Bottom_List_TS:{
                keys = arrayTypeBottomListTS;
            }break;
            case SMFilterView.FilterType.Type_Bottom_List_BX:{
                keys = arrayTypeBottomListBX;
            }break;
            case SMFilterView.FilterType.Type_Bottom_List_WX:{
                keys = arrayTypeBottomListWX;
            }break;
            case SMFilterView.FilterType.Type_Bottom_List_PGD:{
                keys = arrayTypeBottomListPGD;
            }break;
            case SMFilterView.FilterType.Type_Bottom_List_JHGD:{
                keys = arrayTypeBottomListJHGD;
            }break;
            case SMFilterView.FilterType.Type_Bottom_List_XCGD:{
                keys = arrayTypeBottomListXCGD;
            }break;
            case SMFilterView.FilterType.Type_List_SP:{
                keys = arrayTypeListSP;
            }break;
            case SMFilterView.FilterType.Type_List_Work_YL:{
                keys = arrayTypeListWORKPreview;
            }break;
            case SMFilterView.FilterType.Type_List_Scan_BarCode:{
                keys = arrayTypeListScanBarCode;
            }break;
            case SMFilterView.FilterType.Type_List_Scan_BarCode_History:{
                keys = arrayTypeListScanBarCodeHistory;
            }break;

        }

        for (String key : keys){
            SMFilterItem item = null;
            if(key.equalsIgnoreCase(SMFilterDataType.FilterData_WG))
                item = getFilterData(key, exterValue);
            else
                item = getFilterData(key, "");

            if(item != null){
                items.add(item);
                appendingGrandSonItem(item, items);
            }
        }

        return items;
    }

    public void appendingGrandSonItem(SMFilterItem item, List<SMFilterItem> items){
        SMFilterItem itemSelected = null;
        for (SMFilterItem itemSon : item.getM_items()){
            if(itemSon.isSelected()){
                itemSelected = itemSon;
                break;
            }
        }

        if(itemSelected != null && itemSelected.getM_grandSon() != null){
            //
            items.add(itemSelected.getM_grandSon());

            appendingGrandSonItem(itemSelected.getM_grandSon(), items);
        }
    }


    public SMFilterItem getFilterData(String dataType, String extenValue){
        //
        String filterKey = dataType;
        if(extenValue != null && extenValue.length() > 0){
            filterKey = dataType + extenValue;
        }
        if(m_datas.containsKey(filterKey)) {
            return m_datas.get(filterKey);
        }

        switch (dataType){
            /*case SMFilterDataType.FilterData_TS_XZ:{
                //
                requestTS_XZ();
            }break;
            case SMFilterDataType.FilterData_TS_LB:{
                //
                requestTS_LB(dataType,"投诉类别");
            }break;
            case SMFilterDataType.FilterData_WX_LB:{
                //
                requestTS_LB(dataType,"问询类别");
            }break;
            case SMFilterDataType.FilterData_BX_AREA:{
                //
                requestBX_Area();
            }break;
            case SMFilterDataType.FilterData_TX:{
                //
                requestTX(true, dataType);
            }break;
            case SMFilterDataType.FilterData_JHGD_TX:{
                //
                requestTX(false, dataType);
            }break;*/
            case SMFilterDataType.FilterData_FINISH_DATE:{
                //
                return requestFinishDate();
            }
            case SMFilterDataType.FilterData_TIME_OUT:{
                //
                return requestTimeout();
            }
            /*case SMFilterDataType.FilterData_CreateBy:{
                //
                return requestCreateBy();
            }case SMFilterDataType.FilterData_TimeSpan:{
                //
                return requestTimeSpan();
            }
            case SMFilterDataType.FilterData_WG:{
                requestWG(extenValue);
            }
            break;
            case SMFilterDataType.FilterData_SORT_LC:{
                return requestSortLC();
            }
            case SMFilterDataType.FilterData_TS_LIST_STATUS:{
                requestList_Status(dataType, "workorder_status_complain");
            }
            break;
            case SMFilterDataType.FilterData_WX_LIST_STATUS:{
                requestList_Status(dataType, "workorder_status_enquiry");
            }
            break;
            case SMFilterDataType.FilterData_BX_LIST_STATUS:{
                requestList_Status(dataType, "workorder_status_repair");
            }
            break;
            case SMFilterDataType.FilterData_PDG_STATUS:{
                return requestPGDStatus();
            }
            case SMFilterDataType.FilterData_JHGD_STATUS:{
                return requestJHGDStatus();
            }
            case SMFilterDataType.FilterData_XCGD_TX:{
                requestXCGD_TX();
            }
            break;
            case SMFilterDataType.FilterData_SP_DL:{
                requestSP_DL();
            }
            break;
            case SMFilterDataType.FilterData_SP_STAUTS:{
                requestSP_STAUTS();
            }break;
            case SMFilterDataType.FilterData_WORK_MONTH:{
                return requestWorkLookMonth();
            }
            case SMFilterDataType.FilterData_Scan_TX:{
                requestTX(false, dataType);
            }
            case SMFilterDataType.FilterData_Scan_GDLX:{
                requestScan_GDLX();
            }*/

        }

        return null;
    }

    /*//下载客户投诉的投诉性质
    public void requestTS_XZ(){
        //process
        RequestData.getRequest(Constants.UrlxcgdInquiryData + "complain_property", new ResponseCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response) {
                final List<ComplainPropertyBean> complainPropertyBeanList = JSON.parseArray(response, ComplainPropertyBean.class);

                List<SMFilterItem> children = new ArrayList<>();
                SMFilterItem item = new SMFilterItem(SMFilterDataType.FilterData_TS_XZ,
                        "投诉性质",  SMFilterDataType.FilterData_TS_XZ, 1, false, children, null);

                for (ComplainPropertyBean propertyBean : complainPropertyBeanList) {
                    SMFilterItem itemChild = new SMFilterItem(propertyBean.getKey(),
                            propertyBean.getName(), "", 2, true, null, item);

                    children.add(itemChild);
                }
                m_datas.put(SMFilterDataType.FilterData_TS_XZ, item);

                if(m_interface != null){
                    m_interface.onNetworkFilterDataResponds(SMFilterDataType.FilterData_TS_XZ, "", item);
                }

            }
        });
    }

    //下载投诉类别
    public void requestTS_LB(String dataType, String title){
        RequestData.getRequest(Constants.UrlxcgdTSGetBaseList, new ResponseCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response) {
                List<TSGetBaseListBean> tsGetBaseListBeanList = JSON.parseArray(response, TSGetBaseListBean.class);

                List<SMFilterItem> children = new ArrayList<>();
                SMFilterItem item = new SMFilterItem(dataType,
                        title, dataType, 1, false, children, null);

                for (TSGetBaseListBean tslbBean : tsGetBaseListBeanList) {
                    SMFilterItem itemChild = new SMFilterItem(tslbBean.getDataKey(),
                            tslbBean.getDataName(), "", 2, true, null, item);

                    children.add(itemChild);
                }
                m_datas.put(dataType, item);

                if(m_interface != null){
                    m_interface.onNetworkFilterDataResponds(dataType, "", item);
                }
            }
        });
    }

    //报修区域
    public void requestBX_Area(){
        RequestData.getRequest(Constants.UrlxcgdRepairArea, new ResponseCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response) {
                List<RepairAreaBean> repairAreaBeanList = JSON.parseArray(response, RepairAreaBean.class);

                RequestData.getRequest(Constants.UrlxcgdGetRepairTypeMapList, new ResponseCallBack() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response) {
                        GetRepairTypeMapListBean getRepairTypeMapListBean = JSON.parseObject(response, GetRepairTypeMapListBean.class);

                        List<SMFilterItem> children = new ArrayList<>();
                        SMFilterItem item = new SMFilterItem(SMFilterDataType.FilterData_BX_AREA,
                                "报修区域", SMFilterDataType.FilterData_BX_AREA, 1, false, children, null);

                        for (RepairAreaBean bxAreaBean : repairAreaBeanList) {
                            SMFilterItem itemChild = new SMFilterItem(bxAreaBean.getKey(),
                                    bxAreaBean.getName(), "", 2, true, null, item);
                            children.add(itemChild);


                            //
                            SMFilterItem grandSonItem = processBXArea(bxAreaBean, getRepairTypeMapListBean);
                            if(grandSonItem != null){
                                itemChild.setM_grandSon(grandSonItem);
                            }
                        }


                        m_datas.put(SMFilterDataType.FilterData_BX_AREA, item);

                        if(m_interface != null){
                            m_interface.onNetworkFilterDataResponds(SMFilterDataType.FilterData_BX_AREA, "", item);
                        }

                    }
                });


            }
        });
    }

    public static SMFilterItem processBXArea(RepairAreaBean bxAreaBean, GetRepairTypeMapListBean getRepairTypeMapListBean){
        List<SMFilterItem> grandSonChildren = new ArrayList<>();
        SMFilterItem itemGrandSon = new SMFilterItem(SMFilterDataType.FilterData_BX_AREA,
                "报修大类", SMFilterDataType.FilterData_BX_DL, 1, false, grandSonChildren, null);

        if(bxAreaBean.getKey().equalsIgnoreCase("indoor")){
            List<GetRepairTypeMapListBean.IndoorBean> indoors = getRepairTypeMapListBean.getIndoor();
            for (GetRepairTypeMapListBean.IndoorBean indoorBean : indoors){
                SMFilterItem itemChild = new SMFilterItem(indoorBean.getDataKey(),
                        indoorBean.getDataName(), "", 2, true, null, itemGrandSon);
                grandSonChildren.add(itemChild);

                //process
                List<GetRepairTypeMapListBean.IndoorBean.ChildrenBeanX> childrenBeanXES = indoorBean.getChildren();
                if(childrenBeanXES != null){

                    List<SMFilterItem> grandSonGrandSonChildren = new ArrayList<>();
                    SMFilterItem itemGrandSonGrandSon = new SMFilterItem(SMFilterDataType.FilterData_BX_AREA,
                            "报修小类", SMFilterDataType.FilterData_BX_XL,  1, false, grandSonGrandSonChildren, null);

                    itemChild.setM_grandSon(itemGrandSonGrandSon);

                    for (GetRepairTypeMapListBean.IndoorBean.ChildrenBeanX childrenBeanX : childrenBeanXES){
                        SMFilterItem itemGrandsonChild = new SMFilterItem(childrenBeanX.getDataKey(),
                                childrenBeanX.getDataName(),"",  2, true, null, itemGrandSonGrandSon);
                        grandSonGrandSonChildren.add(itemGrandsonChild);
                    }
                }
            }

            return itemGrandSon;
        }
        else if(bxAreaBean.getKey().equalsIgnoreCase("outdoor")){
            List<GetRepairTypeMapListBean.OutdoorBean> outdoors = getRepairTypeMapListBean.getOutdoor();
            for (GetRepairTypeMapListBean.OutdoorBean outdoorBean : outdoors){
                SMFilterItem itemChild = new SMFilterItem(outdoorBean.getDataKey(),
                        outdoorBean.getDataName(), "", 2, true, null, itemGrandSon);
                grandSonChildren.add(itemChild);

                List<GetRepairTypeMapListBean.OutdoorBean.ChildrenBean> childrenBeans = outdoorBean.getChildren();
                if(childrenBeans!=null){
                    List<SMFilterItem> grandSonGrandSonChildren = new ArrayList<>();
                    SMFilterItem itemGrandSonGrandSon = new SMFilterItem(SMFilterDataType.FilterData_BX_AREA,
                            "报修小类", SMFilterDataType.FilterData_BX_XL, 1, false, grandSonGrandSonChildren, null);

                    itemChild.setM_grandSon(itemGrandSonGrandSon);

                    for (GetRepairTypeMapListBean.OutdoorBean.ChildrenBean childrenBeanX : childrenBeans){
                        SMFilterItem itemGrandsonChild = new SMFilterItem(childrenBeanX.getDataKey(),
                                childrenBeanX.getDataName(),"",  2, true, null, itemGrandSonGrandSon);
                        grandSonGrandSonChildren.add(itemGrandsonChild);
                    }
                }
            }

            return itemGrandSon;
        }

        return null;
    }*/

    public SMFilterItem requestFinishDate(){
        List<SMFilterItem> children = new ArrayList<>();
        SMFilterItem item = new SMFilterItem(SMFilterDataType.FilterData_FINISH_DATE,
                "完成截止时间", SMFilterDataType.FilterData_FINISH_DATE,  1, false, children, null);

        SMFilterItem itemDay = new SMFilterItem("day",
                "当天","",  2, true, null, item);
        children.add(itemDay);

        SMFilterItem itemWeek= new SMFilterItem("week",
                "本周","",  2, true, null, item);
        children.add(itemWeek);

        SMFilterItem itemMonth = new SMFilterItem("month",
                "本月","",  2, true, null, item);
        children.add(itemMonth);

        SMFilterItem itemSeason = new SMFilterItem("season",
                "本季度", "", 2, true, null, item);
        children.add(itemSeason);

        m_datas.put(SMFilterDataType.FilterData_FINISH_DATE, item);

        return item;
    }

    public SMFilterItem requestTimeout(){
        List<SMFilterItem> children = new ArrayList<>();
        SMFilterItem item = new SMFilterItem(SMFilterDataType.FilterData_TIME_OUT,
                "是否超时", SMFilterDataType.FilterData_TIME_OUT, 1, false, children, null);

        SMFilterItem itemTure = new SMFilterItem("1",
                "是", "", 2, true, null, item);
        children.add(itemTure);

        SMFilterItem itemFalse = new SMFilterItem("0",
                "否", "", 2, true, null, item);
        children.add(itemFalse);

        m_datas.put(SMFilterDataType.FilterData_TIME_OUT, item);

        return item;
    }

   /* public SMFilterItem requestCreateBy(){
        List<SMFilterItem> children = new ArrayList<>();
        SMFilterItem item = new SMFilterItem(SMFilterDataType.FilterData_CreateBy,
                "是否我创建", SMFilterDataType.FilterData_CreateBy, 1, false, children, null);

        SMFilterItem itemTure = new SMFilterItem(MyApp.get().userId(),
                "我创建的", "", 2, true, null, item);
        itemTure.setSelected(true);
        children.add(itemTure);

        SMFilterItem itemFalse = new SMFilterItem("",
                "全部", "", 2, true, null, item);
        children.add(itemFalse);

        m_datas.put(SMFilterDataType.FilterData_CreateBy, item);

        return item;
    }

    public SMFilterItem requestTimeSpan(){
        List<SMFilterItem> children = new ArrayList<>();
        SMFilterItem item = new SMFilterItem(SMFilterDataType.FilterData_TimeSpan,
                "时间段", "", 1, false, children, null);

        SMFilterItem itemOneMonth = new SMFilterItem("1",
                "1个月", "", 2, true, null, item);
        children.add(itemOneMonth);

        SMFilterItem itemTwoMonth = new SMFilterItem("2",
                "2个月", "", 2, true, null, item);
        children.add(itemTwoMonth);

        SMFilterItem itemThreeMonth = new SMFilterItem("3",
                "3个月", "", 2, true, null, item);
        children.add(itemThreeMonth);

        m_datas.put(SMFilterDataType.FilterData_TimeSpan, item);

        return item;
    }

    public SMFilterItem requestSortLC(){
        List<SMFilterItem> children = new ArrayList<>();
        SMFilterItem item = new SMFilterItem(SMFilterDataType.FilterData_SORT_LC,
                "楼层", SMFilterDataType.FilterData_SORT_LC, 1, false, children, null);

        SMFilterItem asc = new SMFilterItem("asc",
                "从低到高", "", 2, true, null, item);
        children.add(asc);

        SMFilterItem desc = new SMFilterItem("desc",
                "从高到低", "", 2, true, null, item);
        children.add(desc);

        m_datas.put(SMFilterDataType.FilterData_SORT_LC, item);

        return item;
    }

    public SMFilterItem requestPGDStatus(){
        List<SMFilterItem> children = new ArrayList<>();
        SMFilterItem item = new SMFilterItem(SMFilterDataType.FilterData_PDG_STATUS,
                "工单状态", SMFilterDataType.FilterData_PDG_STATUS, 1, false, children, null);

        SMFilterItem newer = new SMFilterItem("1",
                "新生成", "", 2, true, null, item);
        children.add(newer);

        SMFilterItem processing = new SMFilterItem("2",
                "处理中", "", 2, true, null, item);
        children.add(processing);

        SMFilterItem checking = new SMFilterItem("3",
                "验收中","",  2, true, null, item);
        children.add(checking);

        SMFilterItem done = new SMFilterItem("4",
                "已完成","",  2, true, null, item);
        children.add(done);

        m_datas.put(SMFilterDataType.FilterData_PDG_STATUS, item);

        return item;
    }

    public SMFilterItem requestJHGDStatus(){
        List<SMFilterItem> children = new ArrayList<>();
        SMFilterItem item = new SMFilterItem(SMFilterDataType.FilterData_JHGD_STATUS,
                "工单状态", SMFilterDataType.FilterData_JHGD_STATUS,  1, false, children, null);

        SMFilterItem processing = new SMFilterItem("2",
                "处理中","",  2, true, null, item);
        children.add(processing);


        SMFilterItem done = new SMFilterItem("4",
                "已完成", "", 2, true, null, item);
        children.add(done);

        m_datas.put(SMFilterDataType.FilterData_JHGD_STATUS, item);

        return item;
    }

    public SMFilterItem requestWorkLookMonth(){
        List<SMFilterItem> children = new ArrayList<>();
        SMFilterItem item = new SMFilterItem(SMFilterDataType.FilterData_WORK_MONTH,
                "时间段", SMFilterDataType.FilterData_WORK_MONTH,  1, false, children, null);

        SMFilterItem oneMonth = new SMFilterItem("1",
                "一月","",  2, true, null, item);
        oneMonth.setSelected(true);
        children.add(oneMonth);

        SMFilterItem twoMonth = new SMFilterItem("2",
                "二月","",  2, true, null, item);
        children.add(twoMonth);

        SMFilterItem threeMonth = new SMFilterItem("3",
                "三月", "", 2, true, null, item);
        children.add(threeMonth);


        m_datas.put(SMFilterDataType.FilterData_WORK_MONTH, item);

        return item;
    }



    //获取条线数据
    public void requestTX(boolean expandChild, String dataType){

        RequestData.getRequest(Constants.UrlxcgdRESOURCETYPE, new ResponseCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response) {
                List<ResourceTypeBean> resourceTypeBeans = JSON.parseArray(response, ResourceTypeBean.class);

                //准备merge
                Map<String, ResourceTypeBean> typeBeanMap = new HashMap<>();
                for (ResourceTypeBean typeBean : resourceTypeBeans){
                    //
                    typeBeanMap.put(typeBean.getTypeKey(), typeBean);
                }

                RequestData.getRequest(Constants.UrlxcgdPGdlx, new ResponseCallBack() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response) {

                        List<PGdlxBean> pGdlxBeanList = JSON.parseArray(response, PGdlxBean.class);

                        //清空null数据
                        //先获取第一级别，并将其他级别按照parentid分组
                        List<PGdlxBean> txBeans = new ArrayList<>();
                        Map<String, List<PGdlxBean>> childOrSons = new HashMap<>();
                        for (PGdlxBean beanLoop : pGdlxBeanList) {
                            if (beanLoop.getParentId() != null &&
                                    beanLoop.getTypeId() != null){

                                if(beanLoop.getParentId().equals(beanLoop.getTypeId())) {
                                    //
                                    txBeans.add(beanLoop);
                                }
                                else{
                                    if(beanLoop.getParentId() == null)
                                        continue;

                                    if(childOrSons.get(beanLoop.getParentId()) == null){
                                        childOrSons.put(beanLoop.getParentId(), new ArrayList<>());
                                    }

                                    childOrSons.get(beanLoop.getParentId()).add(beanLoop);
                                }
                            }
                        }

                        //循环获取条线的儿子
                        List<SMFilterItem> children = new ArrayList<>();
                        SMFilterItem item = new SMFilterItem(dataType,
                                "条线", dataType, 1, false, children, null);

                        for (PGdlxBean txBean : txBeans){
                            //
                            SMFilterItem itemLoop = SMFilterDataHelper.convertToSMFilterItem(txBean, typeBeanMap);
                            if(itemLoop == null)
                                continue;

                            itemLoop.setM_parent(item);
                            children.add(itemLoop);

                            if(expandChild) {
                                SMFilterItem grandSon = SMFilterDataHelper.getGrandSonItem(1, txBean.getId(), "工单类型", childOrSons, typeBeanMap);
                                if (grandSon != null) {
                                    itemLoop.setM_grandSon(grandSon);
                                }
                            }
                        }

                        m_datas.put(dataType, item);

                        if(m_interface != null){
                            m_interface.onNetworkFilterDataResponds(dataType, "", item);
                        }
                    }

                });
            }
        });
    }

    public void requestXCGD_TX(){

        RequestData.getRequest(Constants.UrlxcgdGetLineAndType, new ResponseCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response) {
                List<GetLineAndTypeBean> getLineAndTypeBeanList = JSON.parseArray(response, GetLineAndTypeBean.class);

                List<SMFilterItem> children = new ArrayList<>();
                SMFilterItem item = new SMFilterItem(SMFilterDataType.FilterData_XCGD_TX,
                        "条线", SMFilterDataType.FilterData_XCGD_TX,  1, false, children, null);

                for (GetLineAndTypeBean lineAndTypeBean : getLineAndTypeBeanList) {
                    SMFilterItem itemChild = new SMFilterItem(lineAndTypeBean.getKey(),
                            lineAndTypeBean.getName(),"",  2, true, null, item);

                    children.add(itemChild);

                    //
                    List<GetLineAndTypeBean.ChildrenBean> childrenBeans = lineAndTypeBean.getChildren();
                    if(childrenBeans != null && childrenBeans.size() > 0){
                        List<SMFilterItem> grandSonChildren = new ArrayList<>();
                        SMFilterItem grandSonItem = new SMFilterItem(SMFilterDataType.FilterData_XCGD_FL,
                                "分类", SMFilterDataType.FilterData_XCGD_FL, 1, false, grandSonChildren, null);

                        itemChild.setM_grandSon(grandSonItem);

                        for (GetLineAndTypeBean.ChildrenBean childrenBean : childrenBeans){
                            SMFilterItem itemGrandSonChild = new SMFilterItem(childrenBean.getKey(),
                                    childrenBean.getName(),"",  2, true, null, grandSonItem);

                            grandSonChildren.add(itemGrandSonChild);
                        }
                    }

                }

                m_datas.put(SMFilterDataType.FilterData_XCGD_TX, item);

                if(m_interface != null){
                    m_interface.onNetworkFilterDataResponds(SMFilterDataType.FilterData_XCGD_TX, "", item);
                }
            }
        });
    }

    public void requestSP_DL(){

        JsonObject jsonObject = new JsonObject();
        RequestData.getRequest(jsonObject.toString(), Constants.UrlxcgdGetByTypeKeyForComBo, new ResponseCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response) {
                List<GetByTypeKeyForComBoBean> getByTypeKeyForComBoBeanList = JSON.parseArray(response, GetByTypeKeyForComBoBean.class);

                List<SMFilterItem> children = new ArrayList<>();
                SMFilterItem item = new SMFilterItem(SMFilterDataType.FilterData_SP_DL,
                        "审批大类", SMFilterDataType.FilterData_SP_DL, 1, false, children, null);


                for (GetByTypeKeyForComBoBean typeKeyForComBoBean : getByTypeKeyForComBoBeanList) {
                    SMFilterItem itemChild = new SMFilterItem(typeKeyForComBoBean.getKey(),
                            typeKeyForComBoBean.getName(), "", 2, true, null, item);

                    children.add(itemChild);

                    List<GetByTypeKeyForComBoBean.ChildrenBean> childrenBeans = typeKeyForComBoBean.getChildren();
                    if(childrenBeans != null && childrenBeans.size() > 0){
                        List<SMFilterItem> grandSonChildren = new ArrayList<>();
                        SMFilterItem grandSonItem = new SMFilterItem(SMFilterDataType.FilterData_SP_XL,
                                "审批小类", SMFilterDataType.FilterData_SP_XL,  1, false, grandSonChildren, null);

                        itemChild.setM_grandSon(grandSonItem);

                        for (GetByTypeKeyForComBoBean.ChildrenBean childrenBean : childrenBeans){
                            SMFilterItem itemGrandSonChild = new SMFilterItem(childrenBean.getKey(),
                                    childrenBean.getName(), "", 2, true, null, grandSonItem);

                            grandSonChildren.add(itemGrandSonChild);
                        }
                    }
                }

                m_datas.put(SMFilterDataType.FilterData_SP_DL, item);

                if(m_interface != null){
                    m_interface.onNetworkFilterDataResponds(SMFilterDataType.FilterData_SP_DL, "", item);
                }
            }
        });
    }

    public void requestSP_STAUTS(){

        RequestData.getRequest(Constants.UrlxcgdGetByTypeKeyInnerAuditStatus, new ResponseCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response) {
                List<GetByTypeKeyInnerAuditStatusBean> getByTypeKeyInnerAuditStatusBeanList = JSON.parseArray(response, GetByTypeKeyInnerAuditStatusBean.class);

                List<SMFilterItem> children = new ArrayList<>();
                SMFilterItem item = new SMFilterItem(SMFilterDataType.FilterData_SP_STAUTS,
                        "审批状态", SMFilterDataType.FilterData_SP_STAUTS, 1, false, children, null);

                for (GetByTypeKeyInnerAuditStatusBean lineAndTypeBean : getByTypeKeyInnerAuditStatusBeanList) {
                    SMFilterItem itemChild = new SMFilterItem(lineAndTypeBean.getKey(),
                            lineAndTypeBean.getName(),"",  2, true, null, item);

                    children.add(itemChild);

                }

                m_datas.put(SMFilterDataType.FilterData_SP_STAUTS, item);

                if(m_interface != null){
                    m_interface.onNetworkFilterDataResponds(SMFilterDataType.FilterData_SP_STAUTS, "", item);
                }
            }
        });
    }

    //将条线的网络数据转换为过滤对象
    public static SMFilterItem convertToSMFilterItem(PGdlxBean beanLoop,  Map<String, ResourceTypeBean> typeBeanMap){
        String fullKey = beanLoop.getKey();
        String partKey = fullKey;

        if(fullKey.length() > 4) {
            partKey = fullKey.substring(0, fullKey.length() - 4);
        }

        ResourceTypeBean typeBeanUsing = typeBeanMap.get(partKey);
        if(typeBeanUsing == null)
            return null;

        SMFilterItem item = new SMFilterItem(typeBeanUsing.getId(),
                beanLoop.getName(), "", 2, true, null, null);

        return item;
    }

    //获取条线的孙子过滤对象
    public static SMFilterItem getGrandSonItem(int level,
                                               String itemId,
                                               String grandSonName,
                                               Map<String, List<PGdlxBean>> childOrSons,
                                               Map<String, ResourceTypeBean> typeBeanMap){

        List<PGdlxBean> txChilds = childOrSons.get(itemId);
        if(txChilds == null || txChilds.size() == 0)
            return null;

        //
        List<SMFilterItem> itemChild = new ArrayList<>();
        SMFilterItem txChildItem = new SMFilterItem(SMFilterDataType.FilterData_GDLX,
                grandSonName, SMFilterDataType.FilterData_GDLX + level, 1, false, itemChild, null);

        for (PGdlxBean beanLoop : txChilds){

            SMFilterItem itemLoop = new SMFilterItem(beanLoop.getKey(),
                    beanLoop.getName(), "", 2, true, null, txChildItem);

            itemChild.add(itemLoop);

            SMFilterItem itemSon = getGrandSonItem(level + 1, beanLoop.getId(), "", childOrSons, typeBeanMap);
            if(itemSon != null){
                itemLoop.setM_grandSon(itemSon);
            }
        }

        return txChildItem;
    }

    public void requestWG(String divideId){
        JsonObject jsonObject = new JsonObject();
        JsonArray queries = new JsonArray();
        JsonObject query = new JsonObject();
        query.addProperty("property", "massifId");
        query.addProperty("value", divideId);
        query.addProperty("operation", "EQUAL");
        query.addProperty("relation", "AND");
        JsonObject query2 = new JsonObject();
        query2.addProperty("property", "enabledFlag");
        query2.addProperty("value", 1);
        query2.addProperty("operation", "EQUAL");
        query2.addProperty("relation", "AND");
        queries.add(query);
        queries.add(query2);
        jsonObject.add("querys", queries);
        RequestData.getRequest(jsonObject.toString(), Constants.UrlxcgdGridBasicInfo, new ResponseCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response) {
                GridBasicInfoBean gridBasicInfoBean = JSON.parseObject(response, GridBasicInfoBean.class);

                List<SMFilterItem> children = new ArrayList<>();
                SMFilterItem item = new SMFilterItem(SMFilterDataType.FilterData_WG,
                        "网格", SMFilterDataType.FilterData_WG, 1, false, children, null);

                for (GridBasicInfoBean.RowsBean rowBean :  gridBasicInfoBean.getRows()) {
                    if (rowBean.getGridType().equals("public_area_grid") ||
                            rowBean.getGridType().equals("service_center_grid")) {
                        continue;
                    }

                    SMFilterItem itemChild = new SMFilterItem(rowBean.getId(),
                            rowBean.getGridName(), "", 2, true, null, item);

                    makeGridChildren(itemChild, rowBean.getGridRange());

                    children.add(itemChild);
                }

                m_datas.put(SMFilterDataType.FilterData_WG + divideId, item);

                if(m_interface != null){
                    m_interface.onNetworkFilterDataResponds(SMFilterDataType.FilterData_WG, divideId, item);
                }
            }
        });
    }

    public void makeGridChildren(SMFilterItem item, String gridRange){

        List<SMFilterItem> children = new ArrayList<>();
        SMFilterItem itemBuilding = new SMFilterItem(SMFilterDataType.FilterData_LD,
                "楼栋", SMFilterDataType.FilterData_LD, 1, false, children, null);

        item.setM_grandSon(itemBuilding);

        Map<String, List<GridRangeBean>> floorInfos = new HashMap<>();
        if(gridRange != null && gridRange.length() > 0){
            List<GridRangeBean> gridRangeBeanList = JSON.parseArray(gridRange, GridRangeBean.class);

            for (GridRangeBean bean : gridRangeBeanList){
                if(bean.getLevel() == 1){
                    SMFilterItem itemBuildingChild = new SMFilterItem(bean.getCode(),
                            bean.getName(), bean.getId(), 2, true, null, itemBuilding);
                    children.add(itemBuildingChild);

                } else if(bean.getLevel() == 2) {
                    //
                    String parentId = bean.getParentId();
                    if(parentId != null && parentId.length() > 0){
                        if(floorInfos.get(parentId) == null){
                            floorInfos.put(parentId, new ArrayList<>());
                        }

                        floorInfos.get(parentId).add(bean);
                    }
                }
            }
        }

        for (SMFilterItem itemLoop : children){
            List<GridRangeBean> floorBeans = floorInfos.get(itemLoop.getKeyName());
            if(floorBeans == null){
                continue;
            }

            //
            List<SMFilterItem> floorChildren = new ArrayList<>();
            SMFilterItem itemFloor = new SMFilterItem(SMFilterDataType.FilterData_UNIT,
                    "单元", SMFilterDataType.FilterData_UNIT, 1, true, floorChildren, null);

            itemLoop.setM_grandSon(itemFloor);

            for (GridRangeBean floorBean : floorBeans){
                SMFilterItem itemBuildingChild = new SMFilterItem(floorBean.getCode(),
                        floorBean.getName(), "", 2, true, null, itemFloor);
                floorChildren.add(itemBuildingChild);
            }
        }
    }


    public void requestList_Status(String key, String type){
        RequestData.getRequest(Constants.UrlxcgdWorkOrderStatus + type, new ResponseCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response) {
                List<WorkOrderStatusBean> workOrderStatusBeanList = JSON.parseArray(response, WorkOrderStatusBean.class);

                List<SMFilterItem> children = new ArrayList<>();
                SMFilterItem item = new SMFilterItem(key,
                        "工单状态", key, 1, false, children, null);

                for (WorkOrderStatusBean statusBean : workOrderStatusBeanList) {
                    SMFilterItem itemChild = new SMFilterItem(statusBean.getKey(),
                            statusBean.getName(), "", 2, true, null, item);

                    children.add(itemChild);
                }
                m_datas.put(key, item);

                if(m_interface != null){
                    m_interface.onNetworkFilterDataResponds(key, "", item);
                }
            }
        });
    }

    public void requestScan_GDLX(){

        RequestData.getRequest(Constants.UrlxcgdGetByTypeKey, new ResponseCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response) {
                List<GetByTypeKeyBean> getByTypeKeyBeanList = JSON.parseArray(response, GetByTypeKeyBean.class);

                List<SMFilterItem> children = new ArrayList<>();
                SMFilterItem item = new SMFilterItem(SMFilterDataType.FilterData_Scan_GDLX,
                        "工单类型", SMFilterDataType.FilterData_Scan_GDLX, 1, false, children, null);

                for (GetByTypeKeyBean statusBean : getByTypeKeyBeanList) {
                    SMFilterItem itemChild = new SMFilterItem(statusBean.getKey(),
                            statusBean.getName(), "", 2, true, null, item);

                    children.add(itemChild);
                }
                m_datas.put(SMFilterDataType.FilterData_Scan_GDLX, item);

                if(m_interface != null){
                    m_interface.onNetworkFilterDataResponds(SMFilterDataType.FilterData_Scan_GDLX, "", item);
                }
            }
        });
    }*/


    public interface SMFilterDataInterface{
        void onNetworkFilterDataResponds(String dataType, String exterValue, SMFilterItem itemFilter);
    }
}
