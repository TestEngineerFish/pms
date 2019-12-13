package com.einyun.app.common.ui.widget;

import android.text.TextUtils;

import com.einyun.app.common.R;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_DATE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_IS_OVERDUE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_LINE;

public class ConditionBuilder {
    public static final String RESULT_YES = "1";
    public static final String RESULT_NO = "2";

    public static final String RESULT_DATE_DAY = "day";
    public static final String RESULT_DATE_WEEK = "week";
    public static final String RESULT_DATE_MONTH = "month";
    public static final String RESULT_DATE_SEASON = "season";

    public static final String REPLACE_KEY = "_map";

    private Map<String, SelectModel> selectModelMap = new HashMap<>();
    private List<SelectModel> conditions = new ArrayList<>();

    public ConditionBuilder() {
        initMap();
    }

    public ConditionBuilder addItem(String key, List<SelectModel> selectModels) {
        if (selectModels == null) {
            return this;
        }
        if (key.equals(SELECT_LINE)) {//条线数据
            if (!selectModelMap.containsKey(SELECT_LINE)) {
                SelectModel line = new SelectModel();
                line.setType(CommonApplication.getInstance().getResources().getString(R.string.text_line));
                line.setConditionType(SELECT_LINE);
                List<SelectModel> lines = getLines(selectModels);
                line.setSelectModelList(lines);
                for (SelectModel model : lines) {
                    setChildern(model, selectModels);
                }
                selectModelMap.put(SELECT_LINE, line);
                conditions.add(line);
            }
        }
        return this;
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
        }
        if (key.equals(SELECT_DATE)) {//是否超期数据
            //去重复叠加
            if (!selectModelMap.containsKey(SELECT_DATE)) {
                conditions.add(createCheckDate());
            }
        }
        return this;
    }

    public ConditionBuilder addItem(String key, SelectModel selectModel) {
        if (selectModelMap.containsKey(key)) {
            conditions.add(selectModel);
        }
        return this;
    }

    public List<SelectModel> build() {
        return conditions;
    }

    public SelectModel createIsOverDue() {
        SelectModel selectModel = new SelectModel();
        selectModel.setType(CommonApplication.getInstance().getString(R.string.text_is_overdue));
//        selectModel.setConditionType(SELECT_IS_OVERDUE);
        SelectModel selectYes = new SelectModel();
        selectYes.setType(SELECT_IS_OVERDUE);
        selectYes.setId(RESULT_YES);
        selectYes.setContent(CommonApplication.getInstance().getString(R.string.text_result_yes));
        SelectModel selectNot = new SelectModel();
        selectNot.setType(SELECT_IS_OVERDUE);
        selectNot.setContent(CommonApplication.getInstance().getString(R.string.text_result_no));
        selectNot.setId(RESULT_NO);
        List<SelectModel> selectModels = new ArrayList<>();
        selectModels.add(selectYes);
        selectModels.add(selectNot);
        selectModel.setSelectModelList(selectModels);
        selectModelMap.put(SELECT_IS_OVERDUE, selectModel);
        return selectModel;
    }

    public SelectModel createCheckDate() {
        SelectModel selectModel = new SelectModel();
        selectModel.setType(CommonApplication.getInstance().getString(R.string.text_complete_time));
//        selectModel.setConditionType(SELECT_IS_OVERDUE);
        SelectModel selectDay = new SelectModel();
        selectDay.setType(SELECT_DATE);
        selectDay.setId(RESULT_DATE_DAY);
        selectDay.setContent(CommonApplication.getInstance().getString(R.string.text_date_day));

        SelectModel selectWeek = new SelectModel();
        selectWeek.setType(SELECT_DATE);
        selectWeek.setContent(CommonApplication.getInstance().getString(R.string.text_date_week));
        selectWeek.setId(RESULT_DATE_WEEK);

        SelectModel selectMonth = new SelectModel();
        selectMonth.setType(SELECT_DATE);
        selectMonth.setContent(CommonApplication.getInstance().getString(R.string.pickerview_month));
        selectMonth.setId(RESULT_DATE_MONTH);

        SelectModel selectSeason = new SelectModel();
        selectSeason.setType(SELECT_DATE);
        selectSeason.setContent(CommonApplication.getInstance().getString(R.string.text_date_season));
        selectSeason.setId(RESULT_DATE_SEASON);
        List<SelectModel> selectModels = new ArrayList<>();
        selectModels.add(selectDay);
        selectModels.add(selectWeek);
        selectModels.add(selectMonth);
        selectModels.add(selectSeason);
        selectModel.setSelectModelList(selectModels);
        selectModelMap.put(SELECT_DATE, selectModel);
        return selectModel;
    }

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
                model.setKey(model.getId());
                lines.add(model);
            }
        }
        return lines;
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
}
