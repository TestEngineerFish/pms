package com.einyun.app.pms.toll.model;

import java.math.BigDecimal;
import java.util.List;

public class FeeSucInfoModel {


    /**
     * code : 0
     * msg : null
     * data : {"pointAccount":null,"payAmount":0.09,"taxId":"122029290000083878","deatil":[{"chargeDesc":"物业费","list":[{"id":"1d473d6b-295c-4b55-83fb-be1131e0b3ce","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"7a73e925-281b-4f49-91a1-128fe0b24f9b","chargeParkingId":null,"chargeCostDate":"202008","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"47befb76-e7a2-4167-bb40-066d57639ded","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"263706d7-b77d-45d3-af2e-f2070c276c45","chargeParkingId":null,"chargeCostDate":"202012","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"6d807b6d-1281-4d05-8553-c15cc044b300","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"a78434f7-75b8-412b-9b21-29c52401ab97","chargeParkingId":null,"chargeCostDate":"202006","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"a02b86a9-ae24-49a2-b1d1-76ba90a4d7bd","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"dc87fd26-210c-440e-97b5-dd4ed1696b4b","chargeParkingId":null,"chargeCostDate":"202004","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"aef763bc-b7f0-4e98-b86d-f8acc64706c6","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"429d1d09-7703-46b8-978c-3568e71c5070","chargeParkingId":null,"chargeCostDate":"202009","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"b065f5b0-8eb8-4b0d-b40a-c2b804f1909e","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"a19464a6-8614-4b04-ba52-e0de976ecfb8","chargeParkingId":null,"chargeCostDate":"202010","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"ca379677-7284-42d0-a001-7543f7e72e77","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"430e3ddf-80f1-4ecf-a670-62e8eebda083","chargeParkingId":null,"chargeCostDate":"202007","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"d05fba6d-c632-4b21-87cf-69221e9c0841","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"befc0655-2ffa-43e2-85ba-3b89b4767bea","chargeParkingId":null,"chargeCostDate":"202011","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"f1694bc3-7943-40a1-8696-0bbb27bbec47","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"6e819795-57fb-49e4-b693-27cc03c447fb","chargeParkingId":null,"chargeCostDate":"202005","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"}]}]}
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
         * pointAccount : null
         * payAmount : 0.09
         * taxId : 122029290000083878
         * deatil : [{"chargeDesc":"物业费","list":[{"id":"1d473d6b-295c-4b55-83fb-be1131e0b3ce","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"7a73e925-281b-4f49-91a1-128fe0b24f9b","chargeParkingId":null,"chargeCostDate":"202008","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"47befb76-e7a2-4167-bb40-066d57639ded","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"263706d7-b77d-45d3-af2e-f2070c276c45","chargeParkingId":null,"chargeCostDate":"202012","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"6d807b6d-1281-4d05-8553-c15cc044b300","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"a78434f7-75b8-412b-9b21-29c52401ab97","chargeParkingId":null,"chargeCostDate":"202006","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"a02b86a9-ae24-49a2-b1d1-76ba90a4d7bd","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"dc87fd26-210c-440e-97b5-dd4ed1696b4b","chargeParkingId":null,"chargeCostDate":"202004","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"aef763bc-b7f0-4e98-b86d-f8acc64706c6","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"429d1d09-7703-46b8-978c-3568e71c5070","chargeParkingId":null,"chargeCostDate":"202009","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"b065f5b0-8eb8-4b0d-b40a-c2b804f1909e","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"a19464a6-8614-4b04-ba52-e0de976ecfb8","chargeParkingId":null,"chargeCostDate":"202010","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"ca379677-7284-42d0-a001-7543f7e72e77","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"430e3ddf-80f1-4ecf-a670-62e8eebda083","chargeParkingId":null,"chargeCostDate":"202007","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"d05fba6d-c632-4b21-87cf-69221e9c0841","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"befc0655-2ffa-43e2-85ba-3b89b4767bea","chargeParkingId":null,"chargeCostDate":"202011","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"f1694bc3-7943-40a1-8696-0bbb27bbec47","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"6e819795-57fb-49e4-b693-27cc03c447fb","chargeParkingId":null,"chargeCostDate":"202005","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"}]}]
         */

        private Object pointAccount;
        private double payAmount;
        private String taxId;
        private List<DeatilBean> deatil;

        public Object getPointAccount() {
            return pointAccount;
        }

        public void setPointAccount(Object pointAccount) {
            this.pointAccount = pointAccount;
        }

        public double getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(double payAmount) {
            this.payAmount = payAmount;
        }

        public String getTaxId() {
            return taxId;
        }

        public void setTaxId(String taxId) {
            this.taxId = taxId;
        }

        public List<DeatilBean> getDeatil() {
            return deatil;
        }

        public void setDeatil(List<DeatilBean> deatil) {
            this.deatil = deatil;
        }

        public static class DeatilBean {
            /**
             * chargeDesc : 物业费
             * list : [{"id":"1d473d6b-295c-4b55-83fb-be1131e0b3ce","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"7a73e925-281b-4f49-91a1-128fe0b24f9b","chargeParkingId":null,"chargeCostDate":"202008","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"47befb76-e7a2-4167-bb40-066d57639ded","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"263706d7-b77d-45d3-af2e-f2070c276c45","chargeParkingId":null,"chargeCostDate":"202012","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"6d807b6d-1281-4d05-8553-c15cc044b300","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"a78434f7-75b8-412b-9b21-29c52401ab97","chargeParkingId":null,"chargeCostDate":"202006","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"a02b86a9-ae24-49a2-b1d1-76ba90a4d7bd","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"dc87fd26-210c-440e-97b5-dd4ed1696b4b","chargeParkingId":null,"chargeCostDate":"202004","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"aef763bc-b7f0-4e98-b86d-f8acc64706c6","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"429d1d09-7703-46b8-978c-3568e71c5070","chargeParkingId":null,"chargeCostDate":"202009","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"b065f5b0-8eb8-4b0d-b40a-c2b804f1909e","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"a19464a6-8614-4b04-ba52-e0de976ecfb8","chargeParkingId":null,"chargeCostDate":"202010","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"ca379677-7284-42d0-a001-7543f7e72e77","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"430e3ddf-80f1-4ecf-a670-62e8eebda083","chargeParkingId":null,"chargeCostDate":"202007","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"d05fba6d-c632-4b21-87cf-69221e9c0841","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"befc0655-2ffa-43e2-85ba-3b89b4767bea","chargeParkingId":null,"chargeCostDate":"202011","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"},{"id":"f1694bc3-7943-40a1-8696-0bbb27bbec47","paymentId":"1255ce6d-65a1-4a2d-9620-363d41b53844","chargeTypeCode":null,"chargeTypeName":null,"chargeAmount":0.01,"chargeDesc":"物业费","chargeReceivableId":"6e819795-57fb-49e4-b693-27cc03c447fb","chargeParkingId":null,"chargeCostDate":"202005","costTypeCode":"001","costTypeName":"物业费","feeItemId":"39b5c285-f53e-4c67-a15d-7bf7ba865c4b"}]
             */

            private String chargeDesc;
            private List<ListBean> list;

            public String getChargeDesc() {
                return chargeDesc;
            }

            public void setChargeDesc(String chargeDesc) {
                this.chargeDesc = chargeDesc;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * id : 1d473d6b-295c-4b55-83fb-be1131e0b3ce
                 * paymentId : 1255ce6d-65a1-4a2d-9620-363d41b53844
                 * chargeTypeCode : null
                 * chargeTypeName : null
                 * chargeAmount : 0.01
                 * chargeDesc : 物业费
                 * chargeReceivableId : 7a73e925-281b-4f49-91a1-128fe0b24f9b
                 * chargeParkingId : null
                 * chargeCostDate : 202008
                 * costTypeCode : 001
                 * costTypeName : 物业费
                 * feeItemId : 39b5c285-f53e-4c67-a15d-7bf7ba865c4b
                 */

                private String id;
                private String paymentId;
                private Object chargeTypeCode;
                private Object chargeTypeName;
                private BigDecimal chargeAmount;
                private String chargeDesc;
                private String chargeReceivableId;
                private Object chargeParkingId;
                private String chargeCostDate;
                private String costTypeCode;
                private String costTypeName;
                private String feeItemId;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getPaymentId() {
                    return paymentId;
                }

                public void setPaymentId(String paymentId) {
                    this.paymentId = paymentId;
                }

                public Object getChargeTypeCode() {
                    return chargeTypeCode;
                }

                public void setChargeTypeCode(Object chargeTypeCode) {
                    this.chargeTypeCode = chargeTypeCode;
                }

                public Object getChargeTypeName() {
                    return chargeTypeName;
                }

                public void setChargeTypeName(Object chargeTypeName) {
                    this.chargeTypeName = chargeTypeName;
                }

                public BigDecimal getChargeAmount() {
                    return chargeAmount;
                }

                public void setChargeAmount(BigDecimal chargeAmount) {
                    this.chargeAmount = chargeAmount;
                }

                public String getChargeDesc() {
                    return chargeDesc;
                }

                public void setChargeDesc(String chargeDesc) {
                    this.chargeDesc = chargeDesc;
                }

                public String getChargeReceivableId() {
                    return chargeReceivableId;
                }

                public void setChargeReceivableId(String chargeReceivableId) {
                    this.chargeReceivableId = chargeReceivableId;
                }

                public Object getChargeParkingId() {
                    return chargeParkingId;
                }

                public void setChargeParkingId(Object chargeParkingId) {
                    this.chargeParkingId = chargeParkingId;
                }

                public String getChargeCostDate() {
                    return chargeCostDate;
                }

                public void setChargeCostDate(String chargeCostDate) {
                    this.chargeCostDate = chargeCostDate;
                }

                public String getCostTypeCode() {
                    return costTypeCode;
                }

                public void setCostTypeCode(String costTypeCode) {
                    this.costTypeCode = costTypeCode;
                }

                public String getCostTypeName() {
                    return costTypeName;
                }

                public void setCostTypeName(String costTypeName) {
                    this.costTypeName = costTypeName;
                }

                public String getFeeItemId() {
                    return feeItemId;
                }

                public void setFeeItemId(String feeItemId) {
                    this.feeItemId = feeItemId;
                }
            }
        }
    }
}
