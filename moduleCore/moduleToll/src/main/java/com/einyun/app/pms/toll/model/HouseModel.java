package com.einyun.app.pms.toll.model;

import java.util.List;

public class HouseModel {
    private List<DataBean> data;
    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * children : [{"children":[{"code":"1306","id":"ops-FF61CEAD-FDB9-4465-B302-0FBC72313806","level":3,"name":"19-02-01-1306"},{"code":"306","id":"ops-F99A258D-E70F-44CD-99E7-A67A4C1A0D26","level":3,"name":"19-02-01-306"},{"code":"1506","id":"ops-F7CA0317-F208-4E30-B18D-385CF2D26165","level":3,"name":"19-02-01-1506"},{"code":"904","id":"ops-F76E8290-6D36-41F3-B2B0-2E7CB417C8ED","level":3,"name":"19-02-01-904"},{"code":"1304","id":"ops-F149DAAF-2187-43D2-AE57-1D0EDB58D4FB","level":3,"name":"19-02-01-1304"},{"code":"1205","id":"ops-E3DAC949-4953-41B4-8E9D-13D2A296CD53","level":3,"name":"19-02-01-1205"},{"code":"1605","id":"ops-E36DA121-B3F0-4C7D-AC6E-A9534E888506","level":3,"name":"19-02-01-1605"},{"code":"1406","id":"ops-E13EAAE6-FDCA-4780-8B3C-0D4F03D8BF1A","level":3,"name":"19-02-01-1406"},{"code":"104","id":"ops-D67EF0AB-3CC3-446B-B041-4A72F452E597","level":3,"name":"19-02-01-104"},{"code":"1504","id":"ops-D0B32BCC-21F2-4A66-B2C5-F25AD390BE8C","level":3,"name":"19-02-01-1504"},{"code":"1004","id":"ops-C8F1C276-622A-4936-9229-E3A7863937C6","level":3,"name":"19-02-01-1004"},{"code":"605","id":"ops-C8124486-68D2-49FE-AFDC-EF24FA83DE1D","level":3,"name":"19-02-01-605"},{"code":"206","id":"ops-C283E498-E325-445C-85C5-5F6F8A7C85C5","level":3,"name":"19-02-01-206"},{"code":"504","id":"ops-BFEE4E2B-3528-4126-98F8-D6B3F73B6FC2","level":3,"name":"19-02-504"},{"code":"804","id":"ops-B6B8CDA3-5722-4B7B-94C4-28FFE26A67BC","level":3,"name":"19-02-01-804"},{"code":"1604","id":"ops-B1CC994D-8F7A-4E00-AC65-5009B1418367","level":3,"name":"19-02-01-1604"},{"code":"105","id":"ops-ADA20EE8-176F-458A-8301-E5166B8AE963","level":3,"name":"19-02-01-105"},{"code":"805","id":"ops-9CDD2C9A-5560-4C93-8EDA-6D5F68508022","level":3,"name":"19-02-01-805"},{"code":"1106","id":"ops-9AD795E8-63EB-48D5-938E-C003DAF77A79","level":3,"name":"19-02-01-1106"},{"code":"1405","id":"ops-9885E17E-2C5F-46B9-90C4-C927E2859280","level":3,"name":"19-02-01-1405"},{"code":"906","id":"ops-9823697B-D2E2-4E2A-B648-2AFCD16F1D81","level":3,"name":"19-02-01-906"},{"code":"1706","id":"ops-94149641-0426-4460-A0C1-EAF263D36458","level":3,"name":"19-02-01-1706"},{"code":"506","id":"ops-8A6B4A05-42A7-4572-BB09-5F1FFDB16BA8","level":3,"name":"19-02-01-506"},{"code":"606","id":"ops-865EB8F3-FB28-4A18-9829-18D287877FE8","level":3,"name":"19-02-01-606"},{"code":"505","id":"ops-85A55977-694C-4B44-B793-28561EC335CE","level":3,"name":"19-02-01-505"},{"code":"905","id":"ops-6D963292-D061-4F73-ABE5-8E70F71A9591","level":3,"name":"19-02-01-905"},{"code":"305","id":"ops-63EF07C1-C0FF-4DE0-861B-2C41253B1581","level":3,"name":"19-02-01-305"},{"code":"1206","id":"ops-6229AFCA-B82D-4F29-BB54-54F325B348C7","level":3,"name":"19-02-01-1206"},{"code":"106","id":"ops-5E63AA64-CC54-48A8-A1EF-E56565561C66","level":3,"name":"19-02-01-106"},{"code":"406","id":"ops-5CA6F3D8-8D43-4DF7-B933-717458698583","level":3,"name":"19-02-01-406"},{"code":"1606","id":"ops-5458FCF0-AE00-4899-8577-4A90E4BEE25B","level":3,"name":"19-02-01-1606"},{"code":"1305","id":"ops-529F4971-2B33-4D80-8836-DC00EE7BCACF","level":3,"name":"19-02-01-1305"},{"code":"806","id":"ops-500D602B-226C-4E99-B75A-5E33199AC179","level":3,"name":"19-02-01-806"},{"code":"404","id":"ops-4F909923-BABE-493E-BC42-F5B4EE3EB860","level":3,"name":"19-02-01-404"},{"code":"1005","id":"ops-468059D8-BE83-4DBB-9695-E08C7E765DB4","level":3,"name":"19-02-1005"},{"code":"1505","id":"ops-3CFAE579-C147-4614-97CD-3BF1ED2C15CB","level":3,"name":"19-02-1505"},{"code":"604","id":"ops-3876A554-55CC-478D-8E8E-031EDCC0D4BC","level":3,"name":"19-02-604"},{"code":"706","id":"ops-31635D42-B6A9-4C55-8ED9-45C40C0AD627","level":3,"name":"19-02-706"},{"code":"1705","id":"ops-2F9D1871-5F9A-4882-9747-DE821E48E4D1","level":3,"name":"19-02-1705"},{"code":"304","id":"ops-2C106A21-CC81-4172-BBA4-B0B972506F94","level":3,"name":"19-02-304"},{"code":"1704","id":"ops-29CA1AD5-4F76-4839-A192-2FA9F9203BB0","level":3,"name":"19-02-1704"},{"code":"1104","id":"ops-284F0E55-7FDC-4ED8-8814-9732A64ADF93","level":3,"name":"19-02-1104"},{"code":"405","id":"ops-27C98360-D012-4C19-8A1F-26CA4F0B6754","level":3,"name":"19-02-405"},{"code":"205","id":"ops-2055D87D-B27B-4AD7-9893-522541B0D1DA","level":3,"name":"19-02-205"},{"code":"1006","id":"ops-15E4C21D-D4AD-48D6-B74B-F95DE9A24D43","level":3,"name":"19-02-1006"},{"code":"204","id":"ops-10A3974C-F8DE-414B-BCC3-C1708B1D6F4C","level":3,"name":"19-02-204"},{"code":"1105","id":"ops-0B9C3551-34F9-48E7-B032-7BB5DAD48432","level":3,"name":"19-02-1105"},{"code":"1404","id":"ops-067FCBF9-642C-4B7B-8661-D5B363A5B249","level":3,"name":"19-02-1404"},{"code":"705","id":"ops-050D6C85-716D-4A58-9CA9-51C98ABB93D6","level":3,"name":"19-02-705"},{"code":"1204","id":"ops-04E22199-D84B-4EDA-AE26-1C9DE87814B8","level":3,"name":"19-02-1204"},{"code":"704","id":"ops-03C1B8FF-243B-4725-AB44-3D1F066F56C8","level":3,"name":"19-02-704"}],"code":"01","id":"ops-16c306c7-06b8-11ea-b427-005056a13fb9","level":2,"name":"1单元"}]
         * code : 19-02
         * id : ops-F7969376-FA94-4A12-BA9C-AA08A8593CB5
         * level : 1
         * name : 19幢2单元
         */

        private String code;
        private String id;
        private int level;
        private String name;

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public int getChecked() {
            return checked;
        }

        public void setChecked(int checked) {
            this.checked = checked;
        }

        private String parentId;
        private int checked;
        private List<ChildrenBeanX> children;

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

        public List<ChildrenBeanX> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBeanX> children) {
            this.children = children;
        }

        public static class ChildrenBeanX {
            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public int getChecked() {
                return checked;
            }

            public void setChecked(int checked) {
                this.checked = checked;
            }

            /**
             * children : [{"code":"1306","id":"ops-FF61CEAD-FDB9-4465-B302-0FBC72313806","level":3,"name":"19-02-01-1306"},{"code":"306","id":"ops-F99A258D-E70F-44CD-99E7-A67A4C1A0D26","level":3,"name":"19-02-01-306"},{"code":"1506","id":"ops-F7CA0317-F208-4E30-B18D-385CF2D26165","level":3,"name":"19-02-01-1506"},{"code":"904","id":"ops-F76E8290-6D36-41F3-B2B0-2E7CB417C8ED","level":3,"name":"19-02-01-904"},{"code":"1304","id":"ops-F149DAAF-2187-43D2-AE57-1D0EDB58D4FB","level":3,"name":"19-02-01-1304"},{"code":"1205","id":"ops-E3DAC949-4953-41B4-8E9D-13D2A296CD53","level":3,"name":"19-02-01-1205"},{"code":"1605","id":"ops-E36DA121-B3F0-4C7D-AC6E-A9534E888506","level":3,"name":"19-02-01-1605"},{"code":"1406","id":"ops-E13EAAE6-FDCA-4780-8B3C-0D4F03D8BF1A","level":3,"name":"19-02-01-1406"},{"code":"104","id":"ops-D67EF0AB-3CC3-446B-B041-4A72F452E597","level":3,"name":"19-02-01-104"},{"code":"1504","id":"ops-D0B32BCC-21F2-4A66-B2C5-F25AD390BE8C","level":3,"name":"19-02-01-1504"},{"code":"1004","id":"ops-C8F1C276-622A-4936-9229-E3A7863937C6","level":3,"name":"19-02-01-1004"},{"code":"605","id":"ops-C8124486-68D2-49FE-AFDC-EF24FA83DE1D","level":3,"name":"19-02-01-605"},{"code":"206","id":"ops-C283E498-E325-445C-85C5-5F6F8A7C85C5","level":3,"name":"19-02-01-206"},{"code":"504","id":"ops-BFEE4E2B-3528-4126-98F8-D6B3F73B6FC2","level":3,"name":"19-02-504"},{"code":"804","id":"ops-B6B8CDA3-5722-4B7B-94C4-28FFE26A67BC","level":3,"name":"19-02-01-804"},{"code":"1604","id":"ops-B1CC994D-8F7A-4E00-AC65-5009B1418367","level":3,"name":"19-02-01-1604"},{"code":"105","id":"ops-ADA20EE8-176F-458A-8301-E5166B8AE963","level":3,"name":"19-02-01-105"},{"code":"805","id":"ops-9CDD2C9A-5560-4C93-8EDA-6D5F68508022","level":3,"name":"19-02-01-805"},{"code":"1106","id":"ops-9AD795E8-63EB-48D5-938E-C003DAF77A79","level":3,"name":"19-02-01-1106"},{"code":"1405","id":"ops-9885E17E-2C5F-46B9-90C4-C927E2859280","level":3,"name":"19-02-01-1405"},{"code":"906","id":"ops-9823697B-D2E2-4E2A-B648-2AFCD16F1D81","level":3,"name":"19-02-01-906"},{"code":"1706","id":"ops-94149641-0426-4460-A0C1-EAF263D36458","level":3,"name":"19-02-01-1706"},{"code":"506","id":"ops-8A6B4A05-42A7-4572-BB09-5F1FFDB16BA8","level":3,"name":"19-02-01-506"},{"code":"606","id":"ops-865EB8F3-FB28-4A18-9829-18D287877FE8","level":3,"name":"19-02-01-606"},{"code":"505","id":"ops-85A55977-694C-4B44-B793-28561EC335CE","level":3,"name":"19-02-01-505"},{"code":"905","id":"ops-6D963292-D061-4F73-ABE5-8E70F71A9591","level":3,"name":"19-02-01-905"},{"code":"305","id":"ops-63EF07C1-C0FF-4DE0-861B-2C41253B1581","level":3,"name":"19-02-01-305"},{"code":"1206","id":"ops-6229AFCA-B82D-4F29-BB54-54F325B348C7","level":3,"name":"19-02-01-1206"},{"code":"106","id":"ops-5E63AA64-CC54-48A8-A1EF-E56565561C66","level":3,"name":"19-02-01-106"},{"code":"406","id":"ops-5CA6F3D8-8D43-4DF7-B933-717458698583","level":3,"name":"19-02-01-406"},{"code":"1606","id":"ops-5458FCF0-AE00-4899-8577-4A90E4BEE25B","level":3,"name":"19-02-01-1606"},{"code":"1305","id":"ops-529F4971-2B33-4D80-8836-DC00EE7BCACF","level":3,"name":"19-02-01-1305"},{"code":"806","id":"ops-500D602B-226C-4E99-B75A-5E33199AC179","level":3,"name":"19-02-01-806"},{"code":"404","id":"ops-4F909923-BABE-493E-BC42-F5B4EE3EB860","level":3,"name":"19-02-01-404"},{"code":"1005","id":"ops-468059D8-BE83-4DBB-9695-E08C7E765DB4","level":3,"name":"19-02-1005"},{"code":"1505","id":"ops-3CFAE579-C147-4614-97CD-3BF1ED2C15CB","level":3,"name":"19-02-1505"},{"code":"604","id":"ops-3876A554-55CC-478D-8E8E-031EDCC0D4BC","level":3,"name":"19-02-604"},{"code":"706","id":"ops-31635D42-B6A9-4C55-8ED9-45C40C0AD627","level":3,"name":"19-02-706"},{"code":"1705","id":"ops-2F9D1871-5F9A-4882-9747-DE821E48E4D1","level":3,"name":"19-02-1705"},{"code":"304","id":"ops-2C106A21-CC81-4172-BBA4-B0B972506F94","level":3,"name":"19-02-304"},{"code":"1704","id":"ops-29CA1AD5-4F76-4839-A192-2FA9F9203BB0","level":3,"name":"19-02-1704"},{"code":"1104","id":"ops-284F0E55-7FDC-4ED8-8814-9732A64ADF93","level":3,"name":"19-02-1104"},{"code":"405","id":"ops-27C98360-D012-4C19-8A1F-26CA4F0B6754","level":3,"name":"19-02-405"},{"code":"205","id":"ops-2055D87D-B27B-4AD7-9893-522541B0D1DA","level":3,"name":"19-02-205"},{"code":"1006","id":"ops-15E4C21D-D4AD-48D6-B74B-F95DE9A24D43","level":3,"name":"19-02-1006"},{"code":"204","id":"ops-10A3974C-F8DE-414B-BCC3-C1708B1D6F4C","level":3,"name":"19-02-204"},{"code":"1105","id":"ops-0B9C3551-34F9-48E7-B032-7BB5DAD48432","level":3,"name":"19-02-1105"},{"code":"1404","id":"ops-067FCBF9-642C-4B7B-8661-D5B363A5B249","level":3,"name":"19-02-1404"},{"code":"705","id":"ops-050D6C85-716D-4A58-9CA9-51C98ABB93D6","level":3,"name":"19-02-705"},{"code":"1204","id":"ops-04E22199-D84B-4EDA-AE26-1C9DE87814B8","level":3,"name":"19-02-1204"},{"code":"704","id":"ops-03C1B8FF-243B-4725-AB44-3D1F066F56C8","level":3,"name":"19-02-704"}]
             * code : 01
             * id : ops-16c306c7-06b8-11ea-b427-005056a13fb9
             * level : 2
             * name : 1单元
             */
            private String parentId;
            private int checked;
            private String code;
            private String id;
            private int level;
            private String name;
            private List<ChildrenBean> children;

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

            public List<ChildrenBean> getChildren() {
                return children;
            }

            public void setChildren(List<ChildrenBean> children) {
                this.children = children;
            }

            public static class ChildrenBean {
                public String getParentId() {
                    return parentId;
                }

                public void setParentId(String parentId) {
                    this.parentId = parentId;
                }

                public int getChecked() {
                    return checked;
                }

                public void setChecked(int checked) {
                    this.checked = checked;
                }

                /**
                 * code : 1306
                 * id : ops-FF61CEAD-FDB9-4465-B302-0FBC72313806
                 * level : 3
                 * name : 19-02-01-1306
                 */
                private String parentId;
                private int checked;
                private String code;
                private String id;
                private int level;
                private String name;

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
            }
        }
    }
}
