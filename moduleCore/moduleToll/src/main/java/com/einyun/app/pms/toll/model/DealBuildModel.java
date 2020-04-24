package com.einyun.app.pms.toll.model;

import java.util.List;

public class DealBuildModel {

    private List<BuildModel.GridRangeBean> gridRange;

    public List<BuildModel.GridRangeBean> getGridRange() {
        return gridRange;
    }

    public void setGridRange(List<BuildModel.GridRangeBean> gridRange) {
        this.gridRange = gridRange;
    }

    public static class GridRangeBean {
        /**
         * checked : 0
         * code : 08-01
         * id : ops-DD6A9E9A-AE85-487E-8C75-CF143902D169
         * level : 1
         * name : 8幢1单元
         * parentId : 0
         */

        private int checked;
        private String code;
        private String id;
        private int level;
        private String name;
        private String parentId;
        private List<BuildModel.GridRangeBean> buildRange;

        public List<BuildModel.GridRangeBean> getBuildRange() {
            return buildRange;
        }

        public void setBuildRange(List<BuildModel.GridRangeBean> gridRange) {
            this.buildRange = gridRange;
        }
        public int getChecked() {
            return checked;
        }

        public void setChecked(int checked) {
            this.checked = checked;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }
    }


}
