package com.butuh.uang.bu.tuhu.bean;

import java.util.List;

public class AdditionalBean {

    /**
     * designation : 额外信息 大标题
     * particulars : [{"judul":"标题","ico":"图标","details":"描述"}]
     */

    private String designation;
    private List<ParticularsBean> particulars;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<ParticularsBean> getParticulars() {
        return particulars;
    }

    public void setParticulars(List<ParticularsBean> particulars) {
        this.particulars = particulars;
    }

    public static class ParticularsBean {
        /**
         * judul : 标题
         * ico : 图标
         * details : 描述
         */

        private String judul;
        private String ico;
        private String details;

        public String getJudul() {
            return judul;
        }

        public void setJudul(String judul) {
            this.judul = judul;
        }

        public String getIco() {
            return ico;
        }

        public void setIco(String ico) {
            this.ico = ico;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }
}
