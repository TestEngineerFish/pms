package com.einyun.app.library.workorder.model;

import java.util.List;

public class TypeBigAndSmallModel {

    /**
     * id : 0
     * parentId : -
     * categoryId : 85131871771426820
     * dataName : 业务数据树
     * dataKey : root
     * enabledFlag : null
     * expand : null
     * sn : null
     * children : [{"id":"85132246507323396","parentId":"0","categoryId":"85131871771426820","dataName":"关怀活动","dataKey":"caring_activities","enabledFlag":null,"expand":{"majorLine":{"name":"客服","key":"customer_service_classification"},"code_str":"KF"},"sn":3,"children":[{"id":"85132610505801732","parentId":"85132246507323396","categoryId":"85131940490903556","dataName":"活动进展","dataKey":"inquiry_sub_category_progress","enabledFlag":null,"expand":{},"sn":2,"children":null},{"id":"85132570777354244","parentId":"85132246507323396","categoryId":"85131940490903556","dataName":"活动报名","dataKey":"inquiry_sub_category_registration","enabledFlag":null,"expand":{},"sn":1,"children":null}]},{"id":"85132269055901700","parentId":"0","categoryId":"85131871771426820","dataName":"其他","dataKey":"other","enabledFlag":null,"expand":{"majorLine":{"name":"客服","key":"customer_service_classification"},"code_str":"KF"},"sn":4,"children":[{"id":"85132657750441988","parentId":"85132269055901700","categoryId":"85131940490903556","dataName":"其他咨询","dataKey":"inquiry_sub_category_other","enabledFlag":null,"expand":{},"sn":1,"children":null}]},{"id":"85132121953271812","parentId":"0","categoryId":"85131871771426820","dataName":"物业服务","dataKey":"property_services","enabledFlag":null,"expand":{"majorLine":{"name":"客服","key":"customer_service_classification"},"code_str":"KF"},"sn":1,"children":[{"id":"85132347439054852","parentId":"85132121953271812","categoryId":"85131940490903556","dataName":"联系方式","dataKey":"inquiry_sub_category_contact_information","enabledFlag":null,"expand":{},"sn":2,"children":null},{"id":"85132304489381892","parentId":"85132121953271812","categoryId":"85131940490903556","dataName":"费用标准","dataKey":"inquiry_sub_category_cost_standard","enabledFlag":null,"expand":{},"sn":1,"children":null},{"id":"85132379651309572","parentId":"85132121953271812","categoryId":"85131940490903556","dataName":"服务内容","dataKey":"inquiry_sub_category_service_contentz","enabledFlag":null,"expand":{},"sn":3,"children":null}]},{"id":"85132219663777796","parentId":"0","categoryId":"85131871771426820","dataName":"销售服务","dataKey":"sales_service","enabledFlag":null,"expand":{"majorLine":{"name":"客服","key":"customer_service_classification"},"code_str":"KF"},"sn":2,"children":[{"id":"85132434412142596","parentId":"85132219663777796","categoryId":"85131940490903556","dataName":"合同办理","dataKey":"inquiry_sub_category_contract_handling","enabledFlag":null,"expand":{},"sn":2,"children":null},{"id":"85132408642338820","parentId":"85132219663777796","categoryId":"85131940490903556","dataName":"产权办理","dataKey":"inquiry_sub_category_property_management","enabledFlag":null,"expand":{},"sn":1,"children":null},{"id":"85132467698139140","parentId":"85132219663777796","categoryId":"85131940490903556","dataName":"在售楼盘","dataKey":"inquiry_sub_category_real_estate","enabledFlag":null,"expand":{},"sn":3,"children":null},{"id":"85132545007550468","parentId":"85132219663777796","categoryId":"85131940490903556","dataName":"推荐奖励","dataKey":"inquiry_sub_category_reward","enabledFlag":null,"expand":{},"sn":4,"children":null}]}]
     */

    private String id;
    private String parentId;
    private String categoryId;
    private String dataName;
    private String dataKey;
    private Object enabledFlag;
    private Object expand;
    private Object sn;
    private List<ChildrenBeanX> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public Object getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Object enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public Object getExpand() {
        return expand;
    }

    public void setExpand(Object expand) {
        this.expand = expand;
    }

    public Object getSn() {
        return sn;
    }

    public void setSn(Object sn) {
        this.sn = sn;
    }

    public List<ChildrenBeanX> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBeanX> children) {
        this.children = children;
    }

    public static class ChildrenBeanX {
        /**
         * id : 85132246507323396
         * parentId : 0
         * categoryId : 85131871771426820
         * dataName : 关怀活动
         * dataKey : caring_activities
         * enabledFlag : null
         * expand : {"majorLine":{"name":"客服","key":"customer_service_classification"},"code_str":"KF"}
         * sn : 3
         * children : [{"id":"85132610505801732","parentId":"85132246507323396","categoryId":"85131940490903556","dataName":"活动进展","dataKey":"inquiry_sub_category_progress","enabledFlag":null,"expand":{},"sn":2,"children":null},{"id":"85132570777354244","parentId":"85132246507323396","categoryId":"85131940490903556","dataName":"活动报名","dataKey":"inquiry_sub_category_registration","enabledFlag":null,"expand":{},"sn":1,"children":null}]
         */

        private String id;
        private String parentId;
        private String categoryId;
        private String dataName;
        private String dataKey;
        private Object enabledFlag;
        private ExpandBean expand;
        private int sn;
        private List<ChildrenBean> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getDataName() {
            return dataName;
        }

        public void setDataName(String dataName) {
            this.dataName = dataName;
        }

        public String getDataKey() {
            return dataKey;
        }

        public void setDataKey(String dataKey) {
            this.dataKey = dataKey;
        }

        public Object getEnabledFlag() {
            return enabledFlag;
        }

        public void setEnabledFlag(Object enabledFlag) {
            this.enabledFlag = enabledFlag;
        }

        public ExpandBean getExpand() {
            return expand;
        }

        public void setExpand(ExpandBean expand) {
            this.expand = expand;
        }

        public int getSn() {
            return sn;
        }

        public void setSn(int sn) {
            this.sn = sn;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ExpandBean {
            /**
             * majorLine : {"name":"客服","key":"customer_service_classification"}
             * code_str : KF
             */

            private MajorLineBean majorLine;
            private String code_str;

            public MajorLineBean getMajorLine() {
                return majorLine;
            }

            public void setMajorLine(MajorLineBean majorLine) {
                this.majorLine = majorLine;
            }

            public String getCode_str() {
                return code_str;
            }

            public void setCode_str(String code_str) {
                this.code_str = code_str;
            }

            public static class MajorLineBean {
                /**
                 * name : 客服
                 * key : customer_service_classification
                 */

                private String name;
                private String key;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getKey() {
                    return key;
                }

                public void setKey(String key) {
                    this.key = key;
                }
            }
        }

        public static class ChildrenBean {
            /**
             * id : 85132610505801732
             * parentId : 85132246507323396
             * categoryId : 85131940490903556
             * dataName : 活动进展
             * dataKey : inquiry_sub_category_progress
             * enabledFlag : null
             * expand : {}
             * sn : 2
             * children : null
             */

            private String id;
            private String parentId;
            private String categoryId;
            private String dataName;
            private String dataKey;
            private Object enabledFlag;
            private ExpandBeanX expand;
            private int sn;
            private Object children;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getDataName() {
                return dataName;
            }

            public void setDataName(String dataName) {
                this.dataName = dataName;
            }

            public String getDataKey() {
                return dataKey;
            }

            public void setDataKey(String dataKey) {
                this.dataKey = dataKey;
            }

            public Object getEnabledFlag() {
                return enabledFlag;
            }

            public void setEnabledFlag(Object enabledFlag) {
                this.enabledFlag = enabledFlag;
            }

            public ExpandBeanX getExpand() {
                return expand;
            }

            public void setExpand(ExpandBeanX expand) {
                this.expand = expand;
            }

            public int getSn() {
                return sn;
            }

            public void setSn(int sn) {
                this.sn = sn;
            }

            public Object getChildren() {
                return children;
            }

            public void setChildren(Object children) {
                this.children = children;
            }

            public static class ExpandBeanX {
            }
        }
    }
}
