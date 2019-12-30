package com.einyun.app.common.ui.widget;

import android.text.TextUtils;
import com.einyun.app.common.R;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.library.mdm.model.BuildingUnit;
import com.einyun.app.library.mdm.model.DivideGrid;
import com.einyun.app.library.mdm.model.GridModel;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.resource.model.LineType;
import com.einyun.app.library.resource.workorder.model.PreviewSelectModel;
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean;
import com.einyun.app.library.resource.workorder.model.WorkOrderTypeModel;
import com.einyun.app.library.workorder.model.AreaModel;
import com.einyun.app.library.workorder.model.TypeAndLine;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.einyun.app.common.ui.widget.SelectPopUpView.PREVIEW_SELECT;
import static com.einyun.app.common.ui.widget.SelectPopUpView.PREVIEW_SELECT_TIAOXIAN;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_AREA;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_AREA_ALL;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_BUILDING;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_COMPLAIN_PROPERTYS;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_COMPLAIN_TYPES;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_DATE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_GRID;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_IS_OVERDUE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_LINE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_LINE_TYPES;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE1;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ROOT;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_TIME_CIRCLE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_UNIT;

public class ConditionBuilder {
    public static final String RESULT_YES = "1";
    public static final String RESULT_NO = "0";
    public static final String TIME_CIRCLE_ONE = "3";
    public static final String TIME_CIRCLE_TWO = "4";
    public static final String TIME_CIRLE_THREE = "5";


    public static final String RESULT_DATE_DAY = "day";
    public static final String RESULT_DATE_WEEK = "week";
    public static final String RESULT_DATE_MONTH = "month";
    public static final String RESULT_DATE_SEASON = "season";

    public static final String REPLACE_KEY = "_map";

    private Map<String, SelectModel> selectModelMap = new HashMap<>();
    private List<SelectModel> conditions = new ArrayList<>();
    private SelectModel lineRoot;

    public ConditionBuilder() {
        initMap();
    }

    private ConditionBuilder addItem(String key, List<SelectModel> selectModels) {
        if (selectModels == null) {
            return this;
        }
        addLineItem(key, selectModels);
        return this;
    }

    /**
     * 投诉类别数据
     * @param types
     * @return
     */
    public ConditionBuilder addComplainTypes(List<TypeAndLine> types){
        if (!selectModelMap.containsKey(SELECT_COMPLAIN_TYPES)) {
            SelectModel root = new SelectModel();
            root.setType(CommonApplication.getInstance().getString(R.string.text_comlain_types));
            root.setConditionType(SELECT_ROOT);
            if(types!=null){
                List<SelectModel> selectModels=new ArrayList<>();
                for(TypeAndLine type:types){
                    SelectModel child=new SelectModel();
                    child.setConditionType(SELECT_COMPLAIN_TYPES);
                    child.setId(type.getId());
                    child.setKey(type.getDataKey());
                    child.setContent(type.getDataName());
                    selectModels.add(child);
                }
                root.setSelectModelList(selectModels);
            }

            conditions.add(root);
            selectModelMap.put(SELECT_COMPLAIN_TYPES,root);
        }
        return this;
    }

    /**
     * 投诉性质
     * @param propertys
     * @return
     */
    public ConditionBuilder addComplainPropertys(List<DictDataModel> propertys){
        if (!selectModelMap.containsKey(SELECT_COMPLAIN_PROPERTYS)) {
            SelectModel root = new SelectModel();
            root.setType(CommonApplication.getInstance().getString(R.string.text_complain_propertys));
            root.setConditionType(SELECT_ROOT);
            if(propertys!=null){
                List<SelectModel> selectModels=new ArrayList<>();
                for(DictDataModel model:propertys){
                    SelectModel selectModel=new SelectModel();
                    selectModel.setId(model.getId());
                    selectModel.setKey(model.getKey());
                    selectModel.setConditionType(SELECT_COMPLAIN_PROPERTYS);
                    selectModel.setContent(model.getName());
                    selectModels.add(selectModel);
                }
                root.setSelectModelList(selectModels);
            }
            conditions.add(root);
            selectModelMap.put(SELECT_COMPLAIN_PROPERTYS,root);
        }
        return this;
    }


    /**
     * 添加条线数据
     * @param models
     */
    public ConditionBuilder addLines(List<WorkOrderTypeModel> models){
        List<SelectModel> listAll=createAllLineModels(models);
        addItem(SelectPopUpView.SELECT_LINE,listAll);
        return this;
    }

    /**
     * 分类，环境，秩序，工程，客服
     * @param data
     */
    public ConditionBuilder addLineTypesItem(List<LineType> data){
        if (!selectModelMap.containsKey(SELECT_LINE_TYPES)) {
            if(data!=null){
                SelectModel lineAndTypes=new SelectModel();
                lineAndTypes.setGrade(0);
                lineAndTypes.setType(CommonApplication.getInstance().getString(R.string.tv_tiao_line));
                lineAndTypes.setConditionType(SELECT_ROOT);
                setLineAndTypeChildren(lineAndTypes,data);
                conditions.add(lineAndTypes);
                selectModelMap.put(SELECT_LINE_TYPES,lineAndTypes);
            }
        }
        return this;
    }

    public void setLineAndTypeChildren(SelectModel selectModel,List<LineType> childs){
        if(childs!=null){
            List<SelectModel> models=new ArrayList<>();
            for(LineType type:childs){
                SelectModel model=new SelectModel();
                model.setGrade(selectModel.getGrade()+1);
                if(model.getGrade()==1){
                    model.setConditionType(SELECT_LINE);
                    model.setType(CommonApplication.getInstance().getResources().getString(R.string.text_type));
                }else if(model.getGrade()==2){
                    model.setConditionType(SELECT_LINE_TYPES);
                }
                model.setKey(type.getKey());
                model.setId(type.getKey());
                model.setContent(type.getName());
                models.add(model);
                setLineAndTypeChildren(model,type.getChildren());
            }
            selectModel.setSelectModelList(models);
        }
    }

    /**
     * 添加条线数据
     * @param key
     * @param selectModels
     */
    protected ConditionBuilder addLineItem(String key, List<SelectModel> selectModels) {
        if (key.equals(SELECT_LINE)) {//条线数据
            if (!selectModelMap.containsKey(SELECT_LINE)) {
                lineRoot = new SelectModel();
                lineRoot.setType(CommonApplication.getInstance().getResources().getString(R.string.text_line));
                lineRoot.setConditionType(SELECT_ROOT);
                List<SelectModel> lines = getLines(selectModels);
                lineRoot.setSelectModelList(lines);
                for (SelectModel model : lines) {//
                    setChildern(model, selectModels);//递归获取
                }
                selectModelMap.put(SELECT_LINE, lineRoot);
                conditions.add(lineRoot);
            }
        }
        return this;
    }

    /**
     * 添加网格数据 网格-楼栋-单元
     * @param divideGrid
     * @return
     */
    public ConditionBuilder addDivideGrid(DivideGrid divideGrid){
        if (!selectModelMap.containsKey(SELECT_GRID)) {
            SelectModel root=new SelectModel();
            root.setType(CommonApplication.getInstance().getResources().getString(R.string.text_grid));
            root.setConditionType(SELECT_ROOT);
            List<SelectModel> grids = buildGrids(divideGrid);
            root.setSelectModelList(grids);
            selectModelMap.put(SELECT_GRID, root);
            conditions.add(root);
        }
        return this;
    }

    /**
     * 添加区域数据
     * @return
     */
    public ConditionBuilder addRepairArea(AreaModel model){
        if (!selectModelMap.containsKey(SELECT_AREA)) {
            SelectModel root=buildReapirArea(model);
            selectModelMap.put(SELECT_AREA, root);
            conditions.add(root);
        }
        return this;
    }

    /**
     * 添加工单预览筛选
     * @return
     */
    public ConditionBuilder addPreviewSelect(List<PreviewSelectModel> modelList){
        if (!selectModelMap.containsKey(PREVIEW_SELECT)) {
            SelectModel root=buildPreviewSelect(modelList);
            selectModelMap.put(PREVIEW_SELECT, root);
            conditions.add(root);
        }
        return this;
    }



    /**
     * 添加工单预览筛选跳线数据
     * */


    /**
     * 构建网格数据
     * @param divideGrid
     * @return
     */
    @NotNull
    protected List<SelectModel> buildGrids(DivideGrid divideGrid) {
        List<SelectModel> grids=new ArrayList<>();
        for(GridModel grid:divideGrid.getGrids()){
            SelectModel gridModel=new SelectModel();
            gridModel.setType(CommonApplication.getInstance().getResources().getString(R.string.text_building));
            gridModel.setConditionType(SELECT_GRID);
            gridModel.setContent(grid.getGridName());
            gridModel.setKey(grid.getId());
            gridModel.setId(grid.getId());
            grids.add(gridModel);
            List<BuildingUnit> childs=grid.getChildren();
            buildUnits(gridModel,childs);
        }
        return grids;
    }

    /**
     * 构建楼栋/单元
     * @param root
     */
    protected void buildUnits(SelectModel root,List<BuildingUnit> units){
        List<SelectModel> childs=new ArrayList<>();
        if(units!=null){
            for(BuildingUnit unit:units){
                SelectModel selectModel=new SelectModel();
                if(unit.getLevel()==DivideGrid.LEVEL_BUILDING){
                    selectModel.setConditionType(SELECT_BUILDING);
                    selectModel.setType(CommonApplication.getInstance().getResources().getString(R.string.text_unit));
                }else if((unit.getLevel()==DivideGrid.LEVEL_UNIT)){
                    selectModel.setConditionType(SELECT_UNIT);
                }
                selectModel.setContent(unit.getName());
                selectModel.setKey(unit.getId());
                selectModel.setId(unit.getId());
                childs.add(selectModel);
                List<BuildingUnit> items=unit.getChildren();
                buildUnits(selectModel,items);
            }
        }
        root.setSelectModelList(childs);
    }

    public ConditionBuilder mergeLineRes(List<ResourceTypeBean> allResources) {
        if (allResources == null) {
            return this;
        }
        if(!selectModelMap.containsKey(SELECT_LINE)){//只处理条线数据,前提已存入条线数据
            return this;
        }
        List<SelectModel> lines = selectModelMap.get(SELECT_LINE).getSelectModelList();
        Map<String, ResourceTypeBean> map = new HashMap<>();
        for (ResourceTypeBean typeBean : allResources) {
            map.put(typeBean.getTypeKey(), typeBean);
        }
        for (SelectModel model : lines) {
            ResourceTypeBean resource = map.get(model.getKey().replace(REPLACE_KEY, ""));
            if (resource != null) {
                model.setKey(resource.getId());
            }
        }
        return this;
    }

    public ConditionBuilder addItem(String key) {
        if (key.equals(SELECT_IS_OVERDUE)) {//是否超期数据
            //去重复叠加
            if (!selectModelMap.containsKey(SELECT_IS_OVERDUE)) {
                conditions.add(createIsOverDue());
            }
        }else if (key.equals(SELECT_TIME_CIRCLE)){
            if (!selectModelMap.containsKey(SELECT_TIME_CIRCLE)){
                conditions.add(createTimeCircle());
            }
        }
        if (key.equals(SELECT_DATE)) {//完成截止时间
            //去重复叠加
            if (!selectModelMap.containsKey(SELECT_DATE)) {
                conditions.add(createCheckDate());
            }
        }
        return this;
    }


    public List<SelectModel> build() {
        return conditions;
    }
    /**
     * 是否超期item
    **/

    public SelectModel createIsOverDue() {
        SelectModel selectModel = new SelectModel();
        selectModel.setType(CommonApplication.getInstance().getString(R.string.text_is_overdue));
        selectModel.setConditionType(SELECT_ROOT);
        SelectModel selectYes = new SelectModel();
        selectYes.setConditionType(SELECT_IS_OVERDUE);
        selectYes.setId(RESULT_YES);
        selectYes.setKey(RESULT_YES);
        selectYes.setContent(CommonApplication.getInstance().getString(R.string.text_result_yes));
        SelectModel selectNot = new SelectModel();
        selectNot.setContent(CommonApplication.getInstance().getString(R.string.text_result_no));
        selectNot.setId(RESULT_NO);
        selectNot.setKey(RESULT_NO);
        selectNot.setConditionType(SELECT_IS_OVERDUE);
        List<SelectModel> selectModels = new ArrayList<>();
        selectModels.add(selectYes);
        selectModels.add(selectNot);
        selectModel.setSelectModelList(selectModels);
        selectModelMap.put(SELECT_IS_OVERDUE, selectModel);
        return selectModel;
    }

    public SelectModel createCheckDate() {
        SelectModel selectModel = new SelectModel();
        selectModel.setConditionType(SELECT_ROOT);
        selectModel.setType(CommonApplication.getInstance().getString(R.string.text_complete_time));
        SelectModel selectDay = new SelectModel();
        selectDay.setType(SELECT_DATE);
        selectDay.setConditionType(SELECT_DATE);
        selectDay.setId(RESULT_DATE_DAY);
        selectDay.setKey(RESULT_DATE_DAY);
        selectDay.setContent(CommonApplication.getInstance().getString(R.string.text_date_day));

        SelectModel selectWeek = new SelectModel();
        selectWeek.setType(SELECT_DATE);
        selectWeek.setConditionType(SELECT_DATE);
        selectWeek.setContent(CommonApplication.getInstance().getString(R.string.text_date_week));
        selectWeek.setId(RESULT_DATE_WEEK);
        selectWeek.setKey(RESULT_DATE_WEEK);

        SelectModel selectMonth = new SelectModel();
        selectMonth.setType(SELECT_DATE);
        selectMonth.setConditionType(SELECT_DATE);
        selectMonth.setContent(CommonApplication.getInstance().getString(R.string.pickerview_month));
        selectMonth.setId(RESULT_DATE_MONTH);
        selectMonth.setKey(RESULT_DATE_MONTH);

        SelectModel selectSeason = new SelectModel();
        selectSeason.setType(SELECT_DATE);
        selectSeason.setConditionType(SELECT_DATE);
        selectSeason.setContent(CommonApplication.getInstance().getString(R.string.text_date_season));
        selectSeason.setId(RESULT_DATE_SEASON);
        selectSeason.setKey(RESULT_DATE_SEASON);

        List<SelectModel> selectModels = new ArrayList<>();
        selectModels.add(selectDay);
        selectModels.add(selectWeek);
        selectModels.add(selectMonth);
        selectModels.add(selectSeason);
        selectModel.setSelectModelList(selectModels);
        selectModelMap.put(SELECT_DATE, selectModel);
        return selectModel;
    }
    public SelectModel createTimeCircle() {
        SelectModel selectModel = new SelectModel();
        selectModel.setType(CommonApplication.getInstance().getString(R.string.txt_time_circle));
        selectModel.setConditionType(SELECT_ROOT);
        SelectModel selectOne = new SelectModel();
        selectOne.setType(SELECT_TIME_CIRCLE);
        selectOne.setConditionType(SELECT_TIME_CIRCLE);
        selectOne.setId(TIME_CIRCLE_ONE);
        selectOne.setContent(CommonApplication.getInstance().getString(R.string.txt_one_month));
        SelectModel selecTwo = new SelectModel();
        selecTwo.setType(SELECT_TIME_CIRCLE);
        selecTwo.setConditionType(SELECT_TIME_CIRCLE);
        selecTwo.setContent(CommonApplication.getInstance().getString(R.string.txt_two_month));
        selecTwo.setId(TIME_CIRCLE_TWO);
        SelectModel selectThree = new SelectModel();
        selectThree.setType(SELECT_TIME_CIRCLE);
        selectThree.setConditionType(SELECT_TIME_CIRCLE);
        selectThree.setContent(CommonApplication.getInstance().getString(R.string.txt_three_month));
        selectThree.setId(TIME_CIRLE_THREE);
        List<SelectModel> selectModels = new ArrayList<>();
        selectModels.add(selectOne);
        selectModels.add(selecTwo);
        selectModels.add(selectThree);
        selectModel.setSelectModelList(selectModels);
        selectModelMap.put(SELECT_TIME_CIRCLE, selectModel);
        return selectModel;
    }
    /**
     * 周期item
     * */



    private void initMap() {

    }

    /**
     * 获取条线
     *
     * @param all
     * @return
     */
    private List<SelectModel> getLines(List<SelectModel> all) {
        List<SelectModel> lines = new ArrayList<>();
        for (SelectModel model : all) {
            if (!TextUtils.isEmpty(model.getTypeId()) && !TextUtils.isEmpty(model.getParentId())
                    && model.getParentId().equals(model.getTypeId())) {
                model.setConditionType(SELECT_LINE);
                model.setGrade(lineRoot.getGrade()+1);
                model.setKey(model.getKey());
                lines.add(model);
            }
        }
        return lines;
    }

    /**
     * 递归转成SelectModel
     * */
    public SelectModel buildReapirArea(AreaModel model){
        SelectModel selectModel=new SelectModel();
        selectModel.setId(model.getId());
        selectModel.setName(model.getDataName());
        if(model.getParentId().equals("-")){
            model.setGrade(0);
            selectModel.setType(CommonApplication.getInstance().getString(R.string.text_repair_area));
            selectModel.setConditionType(SELECT_ROOT);
        }
        
        selectModel.setContent(model.getDataName());
        selectModel.setConditionType(model.getDataName());

        if (model.getChildren()!=null){
            List<SelectModel> selectModelList=new ArrayList<>();
            for (AreaModel model1:model.getChildren()){
                model1.setGrade(model.getGrade()+1);
                SelectModel child= buildReapirArea(model1);
                if(model1.getGrade()==1){
                    child.setType(CommonApplication.getInstance().getString(R.string.text_repair_type_first));
                    child.setConditionType(SelectPopUpView.SELECT_AREA);
                }else if(model1.getGrade()==2){
                    child.setType(CommonApplication.getInstance().getString(R.string.text_repair_type_second));
                    child.setConditionType(SelectPopUpView.SELECT_AREA_FIR);
                }else if(model1.getGrade()==3){
                    child.setType("");
                    child.setConditionType(SelectPopUpView.SELECT_AREA_SEC);
                }else if (model1.getGrade()==4){
                    child.setType("");
                    child.setConditionType(SelectPopUpView.SELECT_AREA_THIR);
                }
                selectModelList.add(child);
            }
            selectModel.setSelectModelList(selectModelList);
        }

        return selectModel;

    }
    /**
     * 预览转selectmodel
     * */
    public SelectModel buildPreviewSelect(List<PreviewSelectModel> list){
        SelectModel selectModel1=new SelectModel();
        selectModel1.setConditionType(SELECT_ROOT);
        selectModel1.setType(CommonApplication.getInstance().getResources().getString(R.string.tv_tiao_line));
        List<SelectModel> selectModelList=new ArrayList<>();
        for (PreviewSelectModel model:list){
            SelectModel selectModel=new SelectModel();
            selectModel.setId(model.getId());
            selectModel.setType("");
            selectModel.setContent(model.getName());
            selectModel.setConditionType(PREVIEW_SELECT_TIAOXIAN);
            selectModelList.add(selectModel);
        }
        selectModel1.setSelectModelList(selectModelList);
        return selectModel1;

    }
    /**
     * 包装条线数据
     * @param models
     * @return
     */
    protected List<SelectModel> createAllLineModels(List<WorkOrderTypeModel> models) {
        List<SelectModel> listAll=new ArrayList<>();
        for (WorkOrderTypeModel beanLoop : models) {
            SelectModel selectModel = new SelectModel();
            selectModel.setId(beanLoop.getId());
            selectModel.setIsCheck(false);
            selectModel.setContent(beanLoop.getText());
            selectModel.setType("");
            selectModel.setTypeId(beanLoop.getTypeId());
            selectModel.setKey(beanLoop.getKey());
            selectModel.setName(beanLoop.getName());
            selectModel.setParentId(beanLoop.getParentId());
            selectModel.setOpen(beanLoop.getOpen());
            selectModel.setText(beanLoop.getText());
            selectModel.setKey(beanLoop.getKey());
            listAll.add(selectModel);
        }
        return listAll;
    }

    /**
     * 递归插入子模块
     *
     * @param model
     * @param all
     */
    private void setChildern(SelectModel model, List<SelectModel> all) {
        List<SelectModel> childeren = getChildren(model.getId(), all);
        if (childeren.size() > 0) {
            model.setSelectModelList(childeren);
            for (SelectModel selectModel : childeren) {
                selectModel.setGrade(model.getGrade()+1);
                SelectModel parent=getSelectModel(selectModel.getParentId(),lineRoot);
                if(parent!=null&&SELECT_LINE.equals(parent.getConditionType())){
                    selectModel.setConditionType(SELECT_ORDER_TYPE);
                }else{
                    String conditionType=SELECT_ORDER_TYPE+(selectModel.getGrade()-1);
                    selectModel.setConditionType(conditionType.replace(SELECT_ORDER_TYPE1,SELECT_ORDER_TYPE));
                }
                setChildern(selectModel, all);
            }
        }
    }

    /**
     * 查询子节点
     *
     * @param parentId
     * @param all
     * @return
     */
    private List<SelectModel> getChildren(String parentId, List<SelectModel> all) {
        List<SelectModel> childeren = new ArrayList<>();
        for (SelectModel model : all) {
            if (TextUtils.isEmpty(model.getParentId())) {
                continue;
            }
            if (model.getParentId().equals(parentId)) {
                childeren.add(model);
            }
        }
        return childeren;
    }

    private SelectModel getSelectModel(String id,SelectModel root){
        if(root.getId().equals(id)){
            return root;
        }else{
            List<SelectModel> children=root.getSelectModelList();
            if(children!=null&&children.size()>0){
                for(SelectModel model:children){
                    return getSelectModel(id,model);
                }
            }
        }
        return null;
    }
}
