package com.einyun.app.pms.main.core.model;

import java.util.List;

public class LineListModel {

    /**
     * msg : SUCCESS
     * code : 0
     * data : [{"id":"498243","typeId":"483534","key":"ld","name":"楼栋","parentId":"483534","open":"true","children":[],"text":"楼栋"},{"id":"498250","typeId":"483534","key":"gq","name":"公区","parentId":"483534","open":"true","children":[],"text":"公区"},{"id":"498254","typeId":"483534","key":"ldgq","name":"楼栋公区","parentId":"498243","open":"true","children":[],"text":"楼栋公区"},{"id":"498258","typeId":"483534","key":"lddtj","name":"电梯间","parentId":"498254","open":"false","isParent":"false","children":[],"text":"电梯间"},{"id":"498262","typeId":"483534","key":"gjgq","name":"公建公区","parentId":"498250","open":"true","children":[],"text":"公建公区"},{"id":"498266","typeId":"483534","key":"swgq","name":"室外公区","parentId":"498250","open":"true","children":[],"text":"室外公区"},{"id":"498270","typeId":"483534","key":"dkgq","name":"地库公区","parentId":"498250","open":"true","children":[],"text":"地库公区"},{"id":"498274","typeId":"483534","key":"gjdtj","name":"电梯间","parentId":"498262","open":"false","isParent":"false","children":[],"text":"电梯间"},{"id":"498278","typeId":"483534","key":"gjggzd","name":"公共走道","parentId":"498262","open":"false","isParent":"false","children":[],"text":"公共走道"},{"id":"498282","typeId":"483534","key":"gc","name":"广场","parentId":"498266","open":"false","isParent":"false","children":[],"text":"广场"},{"id":"498286","typeId":"483534","key":"lh","name":"绿化","parentId":"498266","open":"false","isParent":"false","children":[],"text":"绿化"},{"id":"498290","typeId":"483534","key":"dxtcc","name":"地下停车场","parentId":"498270","open":"false","isParent":"false","children":[],"text":"地下停车场"},{"id":"498295","typeId":"483534","key":"ldggzd","name":"公共走道","parentId":"498254","open":"false","isParent":"false","children":[],"text":"公共走道"},{"id":"498299","typeId":"483534","key":"ldxftd","name":"消防通道","parentId":"498254","open":"false","isParent":"false","children":[],"text":"消防通道"},{"id":"498303","typeId":"483534","key":"lddt","name":"大堂","parentId":"498254","open":"false","isParent":"false","children":[],"text":"大堂"},{"id":"498307","typeId":"483534","key":"ldjkc","name":"架空层","parentId":"498254","open":"false","isParent":"false","children":[],"text":"架空层"},{"id":"498312","typeId":"483534","key":"wmtt","name":"屋面天台","parentId":"498254","open":"false","isParent":"false","children":[],"text":"屋面天台"},{"id":"498316","typeId":"483534","key":"lddtjd","name":"电梯井道","parentId":"498254","open":"false","isParent":"false","children":[],"text":"电梯井道"},{"id":"498325","typeId":"483534","key":"ldqdj","name":"强电井","parentId":"498254","open":"false","isParent":"false","children":[],"text":"强电井"},{"id":"498329","typeId":"483534","key":"ldrdj","name":"弱电井","parentId":"498254","open":"false","isParent":"false","children":[],"text":"弱电井"},{"id":"498333","typeId":"483534","key":"ldsngj","name":"水暖管井","parentId":"498254","open":"false","isParent":"false","children":[],"text":"水暖管井"},{"id":"498337","typeId":"483534","key":"ldxfsbf","name":"消防水泵房","parentId":"498254","open":"false","isParent":"false","children":[],"text":"消防水泵房"},{"id":"498341","typeId":"483534","key":"ldxfsjf","name":"消防风机房","parentId":"498254","open":"false","isParent":"false","children":[],"text":"消防风机房"},{"id":"498345","typeId":"483534","key":"ldqtmhs","name":"气体灭火室","parentId":"498254","open":"false","isParent":"false","children":[],"text":"气体灭火室"},{"id":"498349","typeId":"483534","key":"ldxfwybf","name":"消防稳压泵房","parentId":"498254","open":"false","isParent":"false","children":[],"text":"消防稳压泵房"},{"id":"498353","typeId":"483534","key":"ldxfjf","name":"新风机房","parentId":"498254","open":"false","isParent":"false","children":[],"text":"新风机房"},{"id":"498357","typeId":"483534","key":"ldshsbf","name":"生活水泵房","parentId":"498254","open":"false","isParent":"false","children":[],"text":"生活水泵房"},{"id":"498361","typeId":"483534","key":"ldzsjf","name":"中水机房","parentId":"498254","open":"false","isParent":"false","children":[],"text":"中水机房"},{"id":"498365","typeId":"483534","key":"gljf","name":"锅炉机房","parentId":"498254","open":"false","isParent":"false","children":[],"text":"锅炉机房"},{"id":"498369","typeId":"483534","key":"ktjf","name":"空调机房","parentId":"498254","open":"false","isParent":"false","children":[],"text":"空调机房"},{"id":"498373","typeId":"483534","key":"ldjf","name":"冷冻机房","parentId":"498254","open":"false","isParent":"false","children":[],"text":"冷冻机房"},{"id":"498377","typeId":"483534","key":"jsk","name":"集水坑","parentId":"498254","open":"false","isParent":"false","children":[],"text":"集水坑"},{"id":"498381","typeId":"483534","key":"hfc","name":"化粪池","parentId":"498254","open":"false","isParent":"false","children":[],"text":"化粪池"},{"id":"498385","typeId":"483534","key":"fdjf","name":"发电机房","parentId":"498254","open":"false","isParent":"false","children":[],"text":"发电机房"},{"id":"498389","typeId":"483534","key":"gypds","name":"高压配电室","parentId":"498254","open":"false","isParent":"false","children":[],"text":"高压配电室"},{"id":"498393","typeId":"483534","key":"dypds","name":"低压配电室","parentId":"498254","open":"false","isParent":"false","children":[],"text":"低压配电室"},{"id":"498397","typeId":"483534","key":"dtjf","name":"电梯机房","parentId":"498254","open":"false","isParent":"false","children":[],"text":"电梯机房"},{"id":"498401","typeId":"483534","key":"rdjf","name":"弱电机房","parentId":"498254","open":"false","isParent":"false","children":[],"text":"弱电机房"},{"id":"498405","typeId":"483534","key":"zbs","name":"值班室","parentId":"498254","open":"false","isParent":"false","children":[],"text":"值班室"},{"id":"498409","typeId":"483534","key":"jkzx","name":"监控中心","parentId":"498254","open":"false","isParent":"false","children":[],"text":"监控中心"},{"id":"498413","typeId":"483534","key":"gjj","name":"工具间","parentId":"498254","open":"false","isParent":"false","children":[],"text":"工具间"},{"id":"498417","typeId":"483534","key":"wsj","name":"卫生间","parentId":"498254","open":"false","isParent":"false","children":[],"text":"卫生间"},{"id":"498421","typeId":"483534","key":"xyf","name":"洗衣房","parentId":"498254","open":"false","isParent":"false","children":[],"text":"洗衣房"},{"id":"498426","typeId":"483534","key":"bgs","name":"办公室","parentId":"498254","open":"false","isParent":"false","children":[],"text":"办公室"},{"id":"498431","typeId":"483534","key":"csj","name":"茶水间","parentId":"498254","open":"false","isParent":"false","children":[],"text":"茶水间"},{"id":"498435","typeId":"483534","key":"bnc","name":"避难层","parentId":"498254","open":"false","isParent":"false","children":[],"text":"避难层"},{"id":"498439","typeId":"483534","key":"gyc","name":"隔油池","parentId":"498254","open":"false","isParent":"false","children":[],"text":"隔油池"},{"id":"498443","typeId":"483534","key":"pdj","name":"配电间","parentId":"498254","open":"false","isParent":"false","children":[],"text":"配电间"},{"id":"498447","typeId":"483534","key":"xftd","name":"消防通道","parentId":"498262","open":"false","isParent":"false","children":[],"text":"消防通道"},{"id":"498451","typeId":"483534","key":"dt","name":"大堂","parentId":"498262","open":"false","isParent":"false","children":[],"text":"大堂"},{"id":"498458","typeId":"483534","key":"xfsbf","name":"消防水泵房","parentId":"498270","open":"false","isParent":"false","children":[],"text":"消防水泵房"},{"id":"498462","typeId":"483534","key":"xffjf","name":"消防风机房","parentId":"498270","open":"false","isParent":"false","children":[],"text":"消防风机房"},{"id":"498466","typeId":"483534","key":"qtmhs","name":"气体灭火室","parentId":"498270","open":"false","isParent":"false","children":[],"text":"气体灭火室"},{"id":"498470","typeId":"483534","key":"xfwybf","name":"消防稳压泵房","parentId":"498270","open":"false","isParent":"false","children":[],"text":"消防稳压泵房"},{"id":"498474","typeId":"483534","key":"xfjf","name":"新风机房","parentId":"498270","open":"false","isParent":"false","children":[],"text":"新风机房"},{"id":"498478","typeId":"483534","key":"shsbf","name":"生活水泵房","parentId":"498270","open":"false","isParent":"false","children":[],"text":"生活水泵房"},{"id":"498482","typeId":"483534","key":"zsjf","name":"中水机房","parentId":"498270","open":"false","isParent":"false","children":[],"text":"中水机房"},{"id":"498487","typeId":"483534","key":"dkgljf","name":"锅炉机房","parentId":"498270","open":"false","isParent":"false","children":[],"text":"锅炉机房"},{"id":"498491","typeId":"483534","key":"dkktjf","name":"空调机房","parentId":"498270","open":"false","isParent":"false","children":[],"text":"空调机房"},{"id":"498496","typeId":"483534","key":"dkldjf","name":"冷冻机房","parentId":"498270","open":"false","isParent":"false","children":[],"text":"冷冻机房"},{"id":"498500","typeId":"483534","key":"dkjsk","name":"集水坑","parentId":"498270","open":"false","isParent":"false","children":[],"text":"集水坑"},{"id":"498504","typeId":"483534","key":"dkhfc","name":"化粪池","parentId":"498270","open":"false","isParent":"false","children":[],"text":"化粪池"},{"id":"498508","typeId":"483534","key":"dkfdjf","name":"发电机房","parentId":"498270","open":"false","isParent":"false","children":[],"text":"发电机房"},{"id":"498512","typeId":"483534","key":"dkgypds","name":"高压配电室","parentId":"498270","open":"false","isParent":"false","children":[],"text":"高压配电室"},{"id":"498516","typeId":"483534","key":"dkdypds","name":"低压配电室","parentId":"498270","open":"false","isParent":"false","children":[],"text":"低压配电室"},{"id":"498520","typeId":"483534","key":"dkdtjf","name":"电梯机房","parentId":"498270","open":"false","isParent":"false","children":[],"text":"电梯机房"},{"id":"498524","typeId":"483534","key":"dkrdjf","name":"弱电机房","parentId":"498270","open":"false","isParent":"false","children":[],"text":"弱电机房"},{"id":"498528","typeId":"483534","key":"dkzbs","name":"值班室","parentId":"498270","open":"false","isParent":"false","children":[],"text":"值班室"},{"id":"498532","typeId":"483534","key":"dkjkzx","name":"监控中心","parentId":"498270","open":"false","isParent":"false","children":[],"text":"监控中心"},{"id":"498536","typeId":"483534","key":"dkgjj","name":"工具间","parentId":"498270","open":"false","isParent":"false","children":[],"text":"工具间"},{"id":"498540","typeId":"483534","key":"dkwsj","name":"卫生间","parentId":"498270","open":"false","isParent":"false","children":[],"text":"卫生间"},{"id":"498544","typeId":"483534","key":"dkxyf","name":"洗衣房","parentId":"498270","open":"false","isParent":"false","children":[],"text":"洗衣房"},{"id":"498548","typeId":"483534","key":"dkbgs","name":"办公室","parentId":"498270","open":"false","isParent":"false","children":[],"text":"办公室"},{"id":"498552","typeId":"483534","key":"dkcsj","name":"茶水间","parentId":"498270","open":"false","isParent":"false","children":[],"text":"茶水间"},{"id":"498556","typeId":"483534","key":"dkpdj","name":"配电间","parentId":"498270","open":"false","isParent":"false","children":[],"text":"配电间"},{"id":"498560","typeId":"483534","key":"dkjqs","name":"集气室","parentId":"498270","open":"false","isParent":"false","children":[],"text":"集气室"},{"id":"498564","typeId":"483534","key":"dkxdj","name":"消毒间","parentId":"498270","open":"false","isParent":"false","children":[],"text":"消毒间"},{"id":"498568","typeId":"483534","key":"dkzbjf","name":"战备机房","parentId":"498270","open":"false","isParent":"false","children":[],"text":"战备机房"},{"id":"498572","typeId":"483534","key":"swsj","name":"水景","parentId":"498266","open":"false","isParent":"false","children":[],"text":"水景"},{"id":"498576","typeId":"483534","key":"swwq","name":"围墙","parentId":"498266","open":"false","isParent":"false","children":[],"text":"围墙"},{"id":"498580","typeId":"483534","key":"swdstcc","name":"地上停车场","parentId":"498266","open":"false","isParent":"false","children":[],"text":"地上停车场"},{"id":"498584","typeId":"483534","key":"swfjdcp","name":"非机动车棚","parentId":"498266","open":"false","isParent":"false","children":[],"text":"非机动车棚"},{"id":"498588","typeId":"483534","key":"swyqcrk","name":"园区出入口","parentId":"498266","open":"false","isParent":"false","children":[],"text":"园区出入口"},{"id":"498592","typeId":"483534","key":"swyqdl","name":"园区道路","parentId":"498266","open":"false","isParent":"false","children":[],"text":"园区道路"},{"id":"498596","typeId":"483534","key":"swyyc","name":"游泳池","parentId":"498266","open":"false","isParent":"false","children":[],"text":"游泳池"},{"id":"498600","typeId":"483534","key":"ylc","name":"游乐场","parentId":"498266","open":"false","isParent":"false","children":[],"text":"游乐场"},{"id":"498604","typeId":"483534","key":"gjjkc","name":"架空层","parentId":"498262","open":"false","isParent":"false","children":[],"text":"架空层"},{"id":"498623","typeId":"483534","key":"gjwmtt","name":"屋面天台","parentId":"498262","open":"false","isParent":"false","children":[],"text":"屋面天台"},{"id":"498628","typeId":"483534","key":"gjzbs","name":"值班室","parentId":"498262","open":"false","isParent":"false","children":[],"text":"值班室"},{"id":"498632","typeId":"483534","key":"gjjkzx","name":"监控中心","parentId":"498262","open":"false","isParent":"false","children":[],"text":"监控中心"},{"id":"498636","typeId":"483534","key":"gjgjj","name":"工具间","parentId":"498262","open":"false","isParent":"false","children":[],"text":"工具间"},{"id":"498640","typeId":"483534","key":"gjwsj","name":"卫生间","parentId":"498262","open":"false","isParent":"false","children":[],"text":"卫生间"},{"id":"498644","typeId":"483534","key":"gjxyf","name":"洗衣房","parentId":"498262","open":"false","isParent":"false","children":[],"text":"洗衣房"},{"id":"498648","typeId":"483534","key":"gjbgs","name":"办公室","parentId":"498262","open":"false","isParent":"false","children":[],"text":"办公室"},{"id":"498653","typeId":"483534","key":"gjcsj","name":"茶水间","parentId":"498262","open":"false","isParent":"false","children":[],"text":"茶水间"},{"id":"498666","typeId":"483534","key":"gjxfsbf","name":"消防水泵房","parentId":"498262","open":"false","isParent":"false","children":[],"text":"消防水泵房"},{"id":"498673","typeId":"483534","key":"gjxffjf","name":"消防风机房","parentId":"498262","open":"false","isParent":"false","children":[],"text":"消防风机房"},{"id":"498688","typeId":"483534","key":"gjqtmhs","name":"气体灭火室","parentId":"498262","open":"false","isParent":"false","children":[],"text":"气体灭火室"},{"id":"498699","typeId":"483534","key":"gjxfwybf","name":"消防稳压泵房","parentId":"498262","open":"false","isParent":"false","children":[],"text":"消防稳压泵房"},{"id":"498703","typeId":"483534","key":"gjxfjf","name":"新风机房","parentId":"498262","open":"false","isParent":"false","children":[],"text":"新风机房"},{"id":"498707","typeId":"483534","key":"gjshsbf","name":"生活水泵房","parentId":"498262","open":"false","isParent":"false","children":[],"text":"生活水泵房"},{"id":"498714","typeId":"483534","key":"gjzsjf","name":"中水机房","parentId":"498262","open":"false","isParent":"false","children":[],"text":"中水机房"},{"id":"498718","typeId":"483534","key":"gjgljf","name":"锅炉机房","parentId":"498262","open":"false","isParent":"false","children":[],"text":"锅炉机房"},{"id":"498722","typeId":"483534","key":"gjktjf","name":"空调机房","parentId":"498262","open":"false","isParent":"false","children":[],"text":"空调机房"},{"id":"498726","typeId":"483534","key":"gjldjf","name":"冷冻机房","parentId":"498262","open":"false","isParent":"false","children":[],"text":"冷冻机房"},{"id":"498730","typeId":"483534","key":"gjjhk","name":"集水坑","parentId":"498262","open":"false","isParent":"false","children":[],"text":"集水坑"},{"id":"498734","typeId":"483534","key":"gjhfc","name":"化粪池","parentId":"498262","open":"false","isParent":"false","children":[],"text":"化粪池"},{"id":"498738","typeId":"483534","key":"gjfdjf","name":"发电机房","parentId":"498262","open":"false","isParent":"false","children":[],"text":"发电机房"},{"id":"498742","typeId":"483534","key":"gjgypds","name":"高压配电室","parentId":"498262","open":"false","isParent":"false","children":[],"text":"高压配电室"},{"id":"498746","typeId":"483534","key":"gjdypds","name":"低压配电室","parentId":"498262","open":"false","isParent":"false","children":[],"text":"低压配电室"},{"id":"498756","typeId":"483534","key":"gjdtjf","name":"电梯机房","parentId":"498262","open":"false","isParent":"false","children":[],"text":"电梯机房"},{"id":"498764","typeId":"483534","key":"gjrdjf","name":"弱电机房","parentId":"498262","open":"false","isParent":"false","children":[],"text":"弱电机房"},{"id":"498768","typeId":"483534","key":"gjbnc","name":"避难层","parentId":"498262","open":"false","isParent":"false","children":[],"text":"避难层"},{"id":"498772","typeId":"483534","key":"gjgyc","name":"隔油池","parentId":"498262","open":"false","isParent":"false","children":[],"text":"隔油池"}]
     * state : true
     * request_id : xxx
     */

    private String msg;
    private int code;
    private boolean state;
    private String request_id;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 498243
         * typeId : 483534
         * key : ld
         * name : 楼栋
         * parentId : 483534
         * open : true
         * children : []
         * text : 楼栋
         * isParent : false
         */

        private String id;
        private String typeId;
        private String key;
        private String name;
        private String parentId;
        private String open;
        private String text;
        private String isParent;
        private List<?> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
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

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getIsParent() {
            return isParent;
        }

        public void setIsParent(String isParent) {
            this.isParent = isParent;
        }

        public List<?> getChildren() {
            return children;
        }

        public void setChildren(List<?> children) {
            this.children = children;
        }
    }
}
