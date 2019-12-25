package com.einyun.app.pms.mine.model;

import java.util.List;

/**
 * Create by dowedo on 2019/4/9
 */
public class UCUserDetailsBean {
    /**
     * code : 200
     * state : true
     * message : 处理成功
     * value : {"projectName":"北京房山维拉","devideName":"北京房山维拉","jobs":["客服管家","工程人员"],"stars":2,"rank":90}
     */


        /**
         * projectName : 北京房山维拉
         * devideName : 北京房山维拉
         * jobs : ["客服管家","工程人员"]
         * stars : 2
         * rank : 90
         */

        private String projectName;
        private String devideName;
        private int stars;
        private int rank;
        private List<String> jobs;

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getDevideName() {
            return devideName;
        }

        public void setDevideName(String devideName) {
            this.devideName = devideName;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public List<String> getJobs() {
            return jobs;
        }

        public void setJobs(List<String> jobs) {
            this.jobs = jobs;
        }

}
