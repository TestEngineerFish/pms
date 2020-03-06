package com.einyun.app.pms.toll.model;

import java.math.BigDecimal;
import java.util.List;

public class TollModel {

    /**
     * code : 0
     * msg : OK
     * data : {"houseId":"ops-2227ED3D-AA42-4C18-93F8-204BB35AFD38","totalPage":24,"paymentList":[{"feeTotal":1644.48,"feeItemCode":"001","feeItemName":"物业费","chargeTypeCode":"4","chargeTypeName":"物业服务费","list":[{"receivableId":"b3d10359-c5f0-4a97-973a-ee01c1e37f69","receivableAmount":1.36,"costDateCourse":"202001","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年1月","yearMonth":"202001","cycleStartDate":1577808000000,"cycleEndDate":1580400000000},{"receivableId":"d81ed17e-8d05-47ec-81b6-d67a4b351a2e","receivableAmount":135.68,"costDateCourse":"202001","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年1月","yearMonth":"202001","cycleStartDate":1577808000000,"cycleEndDate":1580400000000},{"receivableId":"4399e493-e60c-41db-8dc4-4c6a4958fbff","receivableAmount":1.36,"costDateCourse":"202002","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年2月","yearMonth":"202002","cycleStartDate":1580486400000,"cycleEndDate":1582905600000},{"receivableId":"7600871a-0b0c-42d8-923c-2cce03b21ca4","receivableAmount":135.68,"costDateCourse":"202002","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年2月","yearMonth":"202002","cycleStartDate":1580486400000,"cycleEndDate":1582905600000},{"receivableId":"14018769-22e6-4a0e-bc58-07ace992b862","receivableAmount":1.36,"costDateCourse":"202003","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年3月","yearMonth":"202003","cycleStartDate":1582992000000,"cycleEndDate":1585584000000},{"receivableId":"9a477f3a-45a5-4fa9-9643-2586f2e03926","receivableAmount":135.68,"costDateCourse":"202003","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年3月","yearMonth":"202003","cycleStartDate":1582992000000,"cycleEndDate":1585584000000},{"receivableId":"2679e467-b2ab-4054-aea6-d241d7c7ac14","receivableAmount":1.36,"costDateCourse":"202004","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年4月","yearMonth":"202004","cycleStartDate":1585670400000,"cycleEndDate":1588176000000},{"receivableId":"90b24ca0-5fc2-49ff-a584-2761b81cdbcf","receivableAmount":135.68,"costDateCourse":"202004","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年4月","yearMonth":"202004","cycleStartDate":1585670400000,"cycleEndDate":1588176000000},{"receivableId":"7520931d-513c-4c39-a616-790e11dfc21d","receivableAmount":1.36,"costDateCourse":"202005","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年5月","yearMonth":"202005","cycleStartDate":1588262400000,"cycleEndDate":1590854400000},{"receivableId":"75614d75-1736-4bfc-871e-4a7ec30073d9","receivableAmount":135.68,"costDateCourse":"202005","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年5月","yearMonth":"202005","cycleStartDate":1588262400000,"cycleEndDate":1590854400000},{"receivableId":"0bb2e6f1-f88a-42c1-8578-f1c02842d194","receivableAmount":135.68,"costDateCourse":"202006","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年6月","yearMonth":"202006","cycleStartDate":1590940800000,"cycleEndDate":1593446400000},{"receivableId":"24b16b2f-139b-4cbc-9aa9-2d815b5aa7c6","receivableAmount":1.36,"costDateCourse":"202006","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年6月","yearMonth":"202006","cycleStartDate":1590940800000,"cycleEndDate":1593446400000},{"receivableId":"7c5c2785-6021-4a87-88a6-9b5cf2a02197","receivableAmount":135.68,"costDateCourse":"202007","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年7月","yearMonth":"202007","cycleStartDate":1593532800000,"cycleEndDate":1596124800000},{"receivableId":"f8b6c2a9-580b-4ab8-a619-260e65d9d717","receivableAmount":1.36,"costDateCourse":"202007","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年7月","yearMonth":"202007","cycleStartDate":1593532800000,"cycleEndDate":1596124800000},{"receivableId":"2f1f1310-6698-4f2a-a533-5ea66639edee","receivableAmount":1.36,"costDateCourse":"202008","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年8月","yearMonth":"202008","cycleStartDate":1596211200000,"cycleEndDate":1598803200000},{"receivableId":"42208bdb-350c-425c-b6d3-af649cb94cbd","receivableAmount":135.68,"costDateCourse":"202008","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年8月","yearMonth":"202008","cycleStartDate":1596211200000,"cycleEndDate":1598803200000},{"receivableId":"81e3f5fc-0e7e-44ae-99cb-b2b2a133bfeb","receivableAmount":135.68,"costDateCourse":"202009","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年9月","yearMonth":"202009","cycleStartDate":1598889600000,"cycleEndDate":1601395200000},{"receivableId":"951a8c28-9e81-4357-9504-fdc8f09808af","receivableAmount":1.36,"costDateCourse":"202009","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年9月","yearMonth":"202009","cycleStartDate":1598889600000,"cycleEndDate":1601395200000},{"receivableId":"10f3a880-cd8f-440f-824b-2dd1803bf5f3","receivableAmount":1.36,"costDateCourse":"202010","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年10月","yearMonth":"202010","cycleStartDate":1601481600000,"cycleEndDate":1604073600000},{"receivableId":"861d69ef-992d-472e-81d3-b81bbbcf2c4d","receivableAmount":135.68,"costDateCourse":"202010","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年10月","yearMonth":"202010","cycleStartDate":1601481600000,"cycleEndDate":1604073600000},{"receivableId":"49cf0b77-7c79-48a8-830c-91462b52e67a","receivableAmount":1.36,"costDateCourse":"202011","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年11月","yearMonth":"202011","cycleStartDate":1604160000000,"cycleEndDate":1606665600000},{"receivableId":"687f0eab-f7ba-4f64-8a92-21dce753f012","receivableAmount":135.68,"costDateCourse":"202011","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年11月","yearMonth":"202011","cycleStartDate":1604160000000,"cycleEndDate":1606665600000},{"receivableId":"96fc88af-8cb8-4a60-9e89-a60ae10bc018","receivableAmount":135.68,"costDateCourse":"202012","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年12月","yearMonth":"202012","cycleStartDate":1606752000000,"cycleEndDate":1609344000000},{"receivableId":"ff4435c9-fb14-4c80-b195-58a6ee38d189","receivableAmount":1.36,"costDateCourse":"202012","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年12月","yearMonth":"202012","cycleStartDate":1606752000000,"cycleEndDate":1609344000000}]}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * houseId : ops-2227ED3D-AA42-4C18-93F8-204BB35AFD38
         * totalPage : 24
         * paymentList : [{"feeTotal":1644.48,"feeItemCode":"001","feeItemName":"物业费","chargeTypeCode":"4","chargeTypeName":"物业服务费","list":[{"receivableId":"b3d10359-c5f0-4a97-973a-ee01c1e37f69","receivableAmount":1.36,"costDateCourse":"202001","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年1月","yearMonth":"202001","cycleStartDate":1577808000000,"cycleEndDate":1580400000000},{"receivableId":"d81ed17e-8d05-47ec-81b6-d67a4b351a2e","receivableAmount":135.68,"costDateCourse":"202001","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年1月","yearMonth":"202001","cycleStartDate":1577808000000,"cycleEndDate":1580400000000},{"receivableId":"4399e493-e60c-41db-8dc4-4c6a4958fbff","receivableAmount":1.36,"costDateCourse":"202002","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年2月","yearMonth":"202002","cycleStartDate":1580486400000,"cycleEndDate":1582905600000},{"receivableId":"7600871a-0b0c-42d8-923c-2cce03b21ca4","receivableAmount":135.68,"costDateCourse":"202002","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年2月","yearMonth":"202002","cycleStartDate":1580486400000,"cycleEndDate":1582905600000},{"receivableId":"14018769-22e6-4a0e-bc58-07ace992b862","receivableAmount":1.36,"costDateCourse":"202003","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年3月","yearMonth":"202003","cycleStartDate":1582992000000,"cycleEndDate":1585584000000},{"receivableId":"9a477f3a-45a5-4fa9-9643-2586f2e03926","receivableAmount":135.68,"costDateCourse":"202003","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年3月","yearMonth":"202003","cycleStartDate":1582992000000,"cycleEndDate":1585584000000},{"receivableId":"2679e467-b2ab-4054-aea6-d241d7c7ac14","receivableAmount":1.36,"costDateCourse":"202004","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年4月","yearMonth":"202004","cycleStartDate":1585670400000,"cycleEndDate":1588176000000},{"receivableId":"90b24ca0-5fc2-49ff-a584-2761b81cdbcf","receivableAmount":135.68,"costDateCourse":"202004","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年4月","yearMonth":"202004","cycleStartDate":1585670400000,"cycleEndDate":1588176000000},{"receivableId":"7520931d-513c-4c39-a616-790e11dfc21d","receivableAmount":1.36,"costDateCourse":"202005","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年5月","yearMonth":"202005","cycleStartDate":1588262400000,"cycleEndDate":1590854400000},{"receivableId":"75614d75-1736-4bfc-871e-4a7ec30073d9","receivableAmount":135.68,"costDateCourse":"202005","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年5月","yearMonth":"202005","cycleStartDate":1588262400000,"cycleEndDate":1590854400000},{"receivableId":"0bb2e6f1-f88a-42c1-8578-f1c02842d194","receivableAmount":135.68,"costDateCourse":"202006","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年6月","yearMonth":"202006","cycleStartDate":1590940800000,"cycleEndDate":1593446400000},{"receivableId":"24b16b2f-139b-4cbc-9aa9-2d815b5aa7c6","receivableAmount":1.36,"costDateCourse":"202006","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年6月","yearMonth":"202006","cycleStartDate":1590940800000,"cycleEndDate":1593446400000},{"receivableId":"7c5c2785-6021-4a87-88a6-9b5cf2a02197","receivableAmount":135.68,"costDateCourse":"202007","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年7月","yearMonth":"202007","cycleStartDate":1593532800000,"cycleEndDate":1596124800000},{"receivableId":"f8b6c2a9-580b-4ab8-a619-260e65d9d717","receivableAmount":1.36,"costDateCourse":"202007","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年7月","yearMonth":"202007","cycleStartDate":1593532800000,"cycleEndDate":1596124800000},{"receivableId":"2f1f1310-6698-4f2a-a533-5ea66639edee","receivableAmount":1.36,"costDateCourse":"202008","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年8月","yearMonth":"202008","cycleStartDate":1596211200000,"cycleEndDate":1598803200000},{"receivableId":"42208bdb-350c-425c-b6d3-af649cb94cbd","receivableAmount":135.68,"costDateCourse":"202008","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年8月","yearMonth":"202008","cycleStartDate":1596211200000,"cycleEndDate":1598803200000},{"receivableId":"81e3f5fc-0e7e-44ae-99cb-b2b2a133bfeb","receivableAmount":135.68,"costDateCourse":"202009","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年9月","yearMonth":"202009","cycleStartDate":1598889600000,"cycleEndDate":1601395200000},{"receivableId":"951a8c28-9e81-4357-9504-fdc8f09808af","receivableAmount":1.36,"costDateCourse":"202009","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年9月","yearMonth":"202009","cycleStartDate":1598889600000,"cycleEndDate":1601395200000},{"receivableId":"10f3a880-cd8f-440f-824b-2dd1803bf5f3","receivableAmount":1.36,"costDateCourse":"202010","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年10月","yearMonth":"202010","cycleStartDate":1601481600000,"cycleEndDate":1604073600000},{"receivableId":"861d69ef-992d-472e-81d3-b81bbbcf2c4d","receivableAmount":135.68,"costDateCourse":"202010","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年10月","yearMonth":"202010","cycleStartDate":1601481600000,"cycleEndDate":1604073600000},{"receivableId":"49cf0b77-7c79-48a8-830c-91462b52e67a","receivableAmount":1.36,"costDateCourse":"202011","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年11月","yearMonth":"202011","cycleStartDate":1604160000000,"cycleEndDate":1606665600000},{"receivableId":"687f0eab-f7ba-4f64-8a92-21dce753f012","receivableAmount":135.68,"costDateCourse":"202011","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年11月","yearMonth":"202011","cycleStartDate":1604160000000,"cycleEndDate":1606665600000},{"receivableId":"96fc88af-8cb8-4a60-9e89-a60ae10bc018","receivableAmount":135.68,"costDateCourse":"202012","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年12月","yearMonth":"202012","cycleStartDate":1606752000000,"cycleEndDate":1609344000000},{"receivableId":"ff4435c9-fb14-4c80-b195-58a6ee38d189","receivableAmount":1.36,"costDateCourse":"202012","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年12月","yearMonth":"202012","cycleStartDate":1606752000000,"cycleEndDate":1609344000000}]}]
         */

        private String houseId;
        private int totalPage;
        private List<PaymentList> paymentList;

        public String getHouseId() {
            return houseId;
        }

        public void setHouseId(String houseId) {
            this.houseId = houseId;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<PaymentList> getPaymentList() {
            return paymentList;
        }

        public void setPaymentList(List<PaymentList> paymentList) {
            this.paymentList = paymentList;
        }

        public static class PaymentList {
            /**
             * feeTotal : 1644.48
             * feeItemCode : 001
             * feeItemName : 物业费
             * chargeTypeCode : 4
             * chargeTypeName : 物业服务费
             * list : [{"receivableId":"b3d10359-c5f0-4a97-973a-ee01c1e37f69","receivableAmount":1.36,"costDateCourse":"202001","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年1月","yearMonth":"202001","cycleStartDate":1577808000000,"cycleEndDate":1580400000000},{"receivableId":"d81ed17e-8d05-47ec-81b6-d67a4b351a2e","receivableAmount":135.68,"costDateCourse":"202001","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年1月","yearMonth":"202001","cycleStartDate":1577808000000,"cycleEndDate":1580400000000},{"receivableId":"4399e493-e60c-41db-8dc4-4c6a4958fbff","receivableAmount":1.36,"costDateCourse":"202002","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年2月","yearMonth":"202002","cycleStartDate":1580486400000,"cycleEndDate":1582905600000},{"receivableId":"7600871a-0b0c-42d8-923c-2cce03b21ca4","receivableAmount":135.68,"costDateCourse":"202002","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年2月","yearMonth":"202002","cycleStartDate":1580486400000,"cycleEndDate":1582905600000},{"receivableId":"14018769-22e6-4a0e-bc58-07ace992b862","receivableAmount":1.36,"costDateCourse":"202003","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年3月","yearMonth":"202003","cycleStartDate":1582992000000,"cycleEndDate":1585584000000},{"receivableId":"9a477f3a-45a5-4fa9-9643-2586f2e03926","receivableAmount":135.68,"costDateCourse":"202003","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年3月","yearMonth":"202003","cycleStartDate":1582992000000,"cycleEndDate":1585584000000},{"receivableId":"2679e467-b2ab-4054-aea6-d241d7c7ac14","receivableAmount":1.36,"costDateCourse":"202004","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年4月","yearMonth":"202004","cycleStartDate":1585670400000,"cycleEndDate":1588176000000},{"receivableId":"90b24ca0-5fc2-49ff-a584-2761b81cdbcf","receivableAmount":135.68,"costDateCourse":"202004","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年4月","yearMonth":"202004","cycleStartDate":1585670400000,"cycleEndDate":1588176000000},{"receivableId":"7520931d-513c-4c39-a616-790e11dfc21d","receivableAmount":1.36,"costDateCourse":"202005","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年5月","yearMonth":"202005","cycleStartDate":1588262400000,"cycleEndDate":1590854400000},{"receivableId":"75614d75-1736-4bfc-871e-4a7ec30073d9","receivableAmount":135.68,"costDateCourse":"202005","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年5月","yearMonth":"202005","cycleStartDate":1588262400000,"cycleEndDate":1590854400000},{"receivableId":"0bb2e6f1-f88a-42c1-8578-f1c02842d194","receivableAmount":135.68,"costDateCourse":"202006","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年6月","yearMonth":"202006","cycleStartDate":1590940800000,"cycleEndDate":1593446400000},{"receivableId":"24b16b2f-139b-4cbc-9aa9-2d815b5aa7c6","receivableAmount":1.36,"costDateCourse":"202006","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年6月","yearMonth":"202006","cycleStartDate":1590940800000,"cycleEndDate":1593446400000},{"receivableId":"7c5c2785-6021-4a87-88a6-9b5cf2a02197","receivableAmount":135.68,"costDateCourse":"202007","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年7月","yearMonth":"202007","cycleStartDate":1593532800000,"cycleEndDate":1596124800000},{"receivableId":"f8b6c2a9-580b-4ab8-a619-260e65d9d717","receivableAmount":1.36,"costDateCourse":"202007","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年7月","yearMonth":"202007","cycleStartDate":1593532800000,"cycleEndDate":1596124800000},{"receivableId":"2f1f1310-6698-4f2a-a533-5ea66639edee","receivableAmount":1.36,"costDateCourse":"202008","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年8月","yearMonth":"202008","cycleStartDate":1596211200000,"cycleEndDate":1598803200000},{"receivableId":"42208bdb-350c-425c-b6d3-af649cb94cbd","receivableAmount":135.68,"costDateCourse":"202008","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年8月","yearMonth":"202008","cycleStartDate":1596211200000,"cycleEndDate":1598803200000},{"receivableId":"81e3f5fc-0e7e-44ae-99cb-b2b2a133bfeb","receivableAmount":135.68,"costDateCourse":"202009","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年9月","yearMonth":"202009","cycleStartDate":1598889600000,"cycleEndDate":1601395200000},{"receivableId":"951a8c28-9e81-4357-9504-fdc8f09808af","receivableAmount":1.36,"costDateCourse":"202009","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年9月","yearMonth":"202009","cycleStartDate":1598889600000,"cycleEndDate":1601395200000},{"receivableId":"10f3a880-cd8f-440f-824b-2dd1803bf5f3","receivableAmount":1.36,"costDateCourse":"202010","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年10月","yearMonth":"202010","cycleStartDate":1601481600000,"cycleEndDate":1604073600000},{"receivableId":"861d69ef-992d-472e-81d3-b81bbbcf2c4d","receivableAmount":135.68,"costDateCourse":"202010","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年10月","yearMonth":"202010","cycleStartDate":1601481600000,"cycleEndDate":1604073600000},{"receivableId":"49cf0b77-7c79-48a8-830c-91462b52e67a","receivableAmount":1.36,"costDateCourse":"202011","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年11月","yearMonth":"202011","cycleStartDate":1604160000000,"cycleEndDate":1606665600000},{"receivableId":"687f0eab-f7ba-4f64-8a92-21dce753f012","receivableAmount":135.68,"costDateCourse":"202011","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年11月","yearMonth":"202011","cycleStartDate":1604160000000,"cycleEndDate":1606665600000},{"receivableId":"96fc88af-8cb8-4a60-9e89-a60ae10bc018","receivableAmount":135.68,"costDateCourse":"202012","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":135.68,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年12月","yearMonth":"202012","cycleStartDate":1606752000000,"cycleEndDate":1609344000000},{"receivableId":"ff4435c9-fb14-4c80-b195-58a6ee38d189","receivableAmount":1.36,"costDateCourse":"202012","breakMoney":null,"adjustMoney":"0.00","paidMoney":"0.00","totalReceivableAmount":1.36,"status":null,"billDate":null,"isInvoice":null,"nationalCode":null,"productCode":null,"taxRate":null,"isRecepit":"1","remark":null,"month":"2020年12月","yearMonth":"202012","cycleStartDate":1606752000000,"cycleEndDate":1609344000000}]
             */

            public boolean isCheckParent=true;
            public boolean isLoreMore=true;
            private double feeTotal;
            private String feeItemCode;
            private String feeItemName;
            private String chargeTypeCode;
            private String chargeTypeName;
            private List<ListBean> list;

            public boolean isCheckParent() {
                return isCheckParent;
            }

            public void setCheckParent(boolean checkParent) {
                isCheckParent = checkParent;
            }
            public double getFeeTotal() {
                return feeTotal;
            }

            public void setFeeTotal(double feeTotal) {
                this.feeTotal = feeTotal;
            }

            public String getFeeItemCode() {
                return feeItemCode;
            }

            public void setFeeItemCode(String feeItemCode) {
                this.feeItemCode = feeItemCode;
            }

            public String getFeeItemName() {
                return feeItemName;
            }

            public void setFeeItemName(String feeItemName) {
                this.feeItemName = feeItemName;
            }

            public String getChargeTypeCode() {
                return chargeTypeCode;
            }

            public void setChargeTypeCode(String chargeTypeCode) {
                this.chargeTypeCode = chargeTypeCode;
            }

            public String getChargeTypeName() {
                return chargeTypeName;
            }

            public void setChargeTypeName(String chargeTypeName) {
                this.chargeTypeName = chargeTypeName;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public boolean isSetCheckParent() {
                return isCheckParent;
            }

            public void setSetCheckParent(boolean setCheckParent) {
                this.isCheckParent = setCheckParent;
            }

            public boolean isLoreMore() {
                return isLoreMore;
            }

            public void setLoreMore(boolean loreMore) {
                isLoreMore = loreMore;
            }

            public static class ListBean {
                /**
                 * receivableId : b3d10359-c5f0-4a97-973a-ee01c1e37f69
                 * receivableAmount : 1.36
                 * costDateCourse : 202001
                 * breakMoney : null
                 * adjustMoney : 0.00
                 * paidMoney : 0.00
                 * totalReceivableAmount : 1.36
                 * status : null
                 * billDate : null
                 * isInvoice : null
                 * nationalCode : null
                 * productCode : null
                 * taxRate : null
                 * isRecepit : 1
                 * remark : null
                 * month : 2020年1月
                 * yearMonth : 202001
                 * cycleStartDate : 1577808000000
                 * cycleEndDate : 1580400000000
                 */

                private boolean isCheckChirld=true;
                private String receivableId;
                private BigDecimal receivableAmount;
                private String costDateCourse;
                private Object breakMoney;
                private String adjustMoney;
                private String paidMoney;
                private BigDecimal totalReceivableAmount;
                private Object status;
                private Object billDate;
                private Object isInvoice;
                private Object nationalCode;
                private Object productCode;
                private Object taxRate;
                private String isRecepit;
                private Object remark;
                private String month;
                private String yearMonth;
                private long cycleStartDate;
                private long cycleEndDate;
                public boolean isCheckChirld() {
                    return isCheckChirld;
                }

                public void setCheckChirld(boolean checkChirld) {
                    isCheckChirld = checkChirld;
                }
                public String getReceivableId() {
                    return receivableId;
                }

                public void setReceivableId(String receivableId) {
                    this.receivableId = receivableId;
                }

                public BigDecimal getReceivableAmount() {
                    return receivableAmount;
                }

                public void setReceivableAmount(BigDecimal receivableAmount) {
                    this.receivableAmount = receivableAmount;
                }

                public String getCostDateCourse() {
                    return costDateCourse;
                }

                public void setCostDateCourse(String costDateCourse) {
                    this.costDateCourse = costDateCourse;
                }

                public Object getBreakMoney() {
                    return breakMoney;
                }

                public void setBreakMoney(Object breakMoney) {
                    this.breakMoney = breakMoney;
                }

                public String getAdjustMoney() {
                    return adjustMoney;
                }

                public void setAdjustMoney(String adjustMoney) {
                    this.adjustMoney = adjustMoney;
                }

                public String getPaidMoney() {
                    return paidMoney;
                }

                public void setPaidMoney(String paidMoney) {
                    this.paidMoney = paidMoney;
                }

                public BigDecimal getTotalReceivableAmount() {
                    return totalReceivableAmount;
                }

                public void setTotalReceivableAmount(BigDecimal totalReceivableAmount) {
                    this.totalReceivableAmount = totalReceivableAmount;
                }

                public Object getStatus() {
                    return status;
                }

                public void setStatus(Object status) {
                    this.status = status;
                }

                public Object getBillDate() {
                    return billDate;
                }

                public void setBillDate(Object billDate) {
                    this.billDate = billDate;
                }

                public Object getIsInvoice() {
                    return isInvoice;
                }

                public void setIsInvoice(Object isInvoice) {
                    this.isInvoice = isInvoice;
                }

                public Object getNationalCode() {
                    return nationalCode;
                }

                public void setNationalCode(Object nationalCode) {
                    this.nationalCode = nationalCode;
                }

                public Object getProductCode() {
                    return productCode;
                }

                public void setProductCode(Object productCode) {
                    this.productCode = productCode;
                }

                public Object getTaxRate() {
                    return taxRate;
                }

                public void setTaxRate(Object taxRate) {
                    this.taxRate = taxRate;
                }

                public String getIsRecepit() {
                    return isRecepit;
                }

                public void setIsRecepit(String isRecepit) {
                    this.isRecepit = isRecepit;
                }

                public Object getRemark() {
                    return remark;
                }

                public void setRemark(Object remark) {
                    this.remark = remark;
                }

                public String getMonth() {
                    return month;
                }

                public void setMonth(String month) {
                    this.month = month;
                }

                public String getYearMonth() {
                    return yearMonth;
                }

                public void setYearMonth(String yearMonth) {
                    this.yearMonth = yearMonth;
                }

                public long getCycleStartDate() {
                    return cycleStartDate;
                }

                public void setCycleStartDate(long cycleStartDate) {
                    this.cycleStartDate = cycleStartDate;
                }

                public long getCycleEndDate() {
                    return cycleEndDate;
                }

                public void setCycleEndDate(long cycleEndDate) {
                    this.cycleEndDate = cycleEndDate;
                }

            }
        }
    }
}



