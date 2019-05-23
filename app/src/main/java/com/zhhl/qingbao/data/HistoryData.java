package com.zhhl.qingbao.data;

import com.zhhl.qingbao.utils.DateUtil;

import java.util.List;

/**
 * Created by miao on 2019/1/7.
 */
public class HistoryData {
    /**
     * msg : 操作成功
     * obj : [{"mjssbm":"220020180508","zpCl":"http://10.107.1.143:8789/project-image/information/4/1546831736740.png","sjch":"e","cjsj":"2019年01月07日 11时29分44秒","mjdh":"13001111867","ljsy":"旅游","txmjxm":"txmj","zpXdwp":"http://10.107.1.143:8789/project-image/information/2/1546831721795.png","id":"297e09ea682628690168265be8960008","txmjjh":"00000000","zpTxrsfz":"","mjxm":"智慧互联","zpRy":"http://10.107.1.143:8789/project-image/information/1/1546831714432.png","txmjdh":"12345768797","csmc":"csmc","mjjh":"1111867","xb":"女","xxdz":"hjdz","txr":"","sjh":"12345678900","zpDemp1":"","sfzh":"000000011110000000","xm":"qq","zpXd":"http://10.107.1.143:8789/project-image/information/3/1546831728612.png","zpDemp2":"","zpDemp3":"","zpDemp4":"","zpDemp5":""},{"mjssbm":"220020180508","zpCl":"http://10.107.1.143:8789/project-image/information/4/1546831736740.png","sjch":"e","cjsj":"2019年01月07日 11时29分51秒","mjdh":"13001111867","ljsy":"旅游","txmjxm":"txmj","zpXdwp":"http://10.107.1.143:8789/project-image/information/2/1546831721795.png","id":"297e09ea682628690168265c03e3000a","txmjjh":"00000000","zpTxrsfz":"","mjxm":"智慧互联","zpRy":"http://10.107.1.143:8789/project-image/information/1/1546831714432.png","txmjdh":"12345768797","csmc":"csmc","mjjh":"1111867","xb":"女","xxdz":"hjdz","txr":"","sjh":"12345678900","zpDemp1":"","sfzh":"000000011110000000","xm":"qq","zpXd":"http://10.107.1.143:8789/project-image/information/3/1546831728612.png","zpDemp2":"","zpDemp3":"","zpDemp4":"","zpDemp5":""},{"mjssbm":"220020180508","zpCl":"http://10.107.1.143:8789/project-image/information/4/1546832693675.png","sjch":"还读书大笨笨","cjsj":"2019年01月07日 11时45分05秒","mjdh":"13001111867","ljsy":"办事","txmjxm":"txmj","zpXdwp":"http://10.107.1.143:8789/project-image/information/2/1546832681414.png","id":"297e09ea6826286901682669f702000c","txmjjh":"00000000","zpTxrsfz":"http://10.107.1.143:8789/project-image/information/5/1546832699322.png","mjxm":"智慧互联","zpRy":"http://10.107.1.143:8789/project-image/information/1/1546832675002.png","txmjdh":"12345768797","csmc":"吃什么菜","mjjh":"1111867","xb":"女","xxdz":"户籍地址","txr":"随行人","sjh":"12345468787","zpDemp1":"","sfzh":"222222222222222222","xm":"千秋月多喝水","zpXd":"http://10.107.1.143:8789/project-image/information/3/1546832688387.png","zpDemp2":"","zpDemp3":"","zpDemp4":"","zpDemp5":""}]
     * success : true
     * attributes : null
     */

    private String msg;
    private boolean success;
    private Object attributes;
    private List<ObjBean> obj;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean implements Comparable<ObjBean> {
        /**
         * mjssbm : 220020180508
         * zpCl : http://10.107.1.143:8789/project-image/information/4/1546831736740.png
         * sjch : e
         * cjsj : 2019年01月07日 11时29分44秒
         * mjdh : 13001111867
         * ljsy : 旅游
         * txmjxm : txmj
         * zpXdwp : http://10.107.1.143:8789/project-image/information/2/1546831721795.png
         * id : 297e09ea682628690168265be8960008
         * txmjjh : 00000000
         * zpTxrsfz :
         * mjxm : 智慧互联
         * zpRy : http://10.107.1.143:8789/project-image/information/1/1546831714432.png
         * txmjdh : 12345768797
         * csmc : csmc
         * mjjh : 1111867
         * xb : 女
         * xxdz : hjdz
         * txr :
         * sjh : 12345678900
         * zpDemp1 :
         * sfzh : 000000011110000000
         * xm : qq
         * zpXd : http://10.107.1.143:8789/project-image/information/3/1546831728612.png
         * zpDemp2 :
         * zpDemp3 :
         * zpDemp4 :
         * zpDemp5 :
         */

        private String mjssbm;
        private String zpCl;
        private String sjch;
        private String cjsj;
        private String mjdh;
        private String ljsy;
        private String txmjxm;
        private String zpXdwp;
        private String id;
        private String txmjjh;
        private String zpTxrsfz;
        private String mjxm;
        private String zpRy;
        private String txmjdh;
        private String csmc;
        private String mjjh;
        private String xb;
        private String xxdz;
        private String txr;
        private String sjh;
        private String zpDemp1;
        private String sfzh;
        private String xm;
        private String zpXd;
        private String zpDemp2;
        private String zpDemp3;
        private String zpDemp4;
        private String zpDemp5;

        public String getMjssbm() {
            return mjssbm;
        }

        public void setMjssbm(String mjssbm) {
            this.mjssbm = mjssbm;
        }

        public String getZpCl() {
            return zpCl;
        }

        public void setZpCl(String zpCl) {
            this.zpCl = zpCl;
        }

        public String getSjch() {
            return sjch;
        }

        public void setSjch(String sjch) {
            this.sjch = sjch;
        }

        public String getCjsj() {
            return cjsj;
        }

        public void setCjsj(String cjsj) {
            this.cjsj = cjsj;
        }

        public String getMjdh() {
            return mjdh;
        }

        public void setMjdh(String mjdh) {
            this.mjdh = mjdh;
        }

        public String getLjsy() {
            return ljsy;
        }

        public void setLjsy(String ljsy) {
            this.ljsy = ljsy;
        }

        public String getTxmjxm() {
            return txmjxm;
        }

        public void setTxmjxm(String txmjxm) {
            this.txmjxm = txmjxm;
        }

        public String getZpXdwp() {
            return zpXdwp;
        }

        public void setZpXdwp(String zpXdwp) {
            this.zpXdwp = zpXdwp;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTxmjjh() {
            return txmjjh;
        }

        public void setTxmjjh(String txmjjh) {
            this.txmjjh = txmjjh;
        }

        public String getZpTxrsfz() {
            return zpTxrsfz;
        }

        public void setZpTxrsfz(String zpTxrsfz) {
            this.zpTxrsfz = zpTxrsfz;
        }

        public String getMjxm() {
            return mjxm;
        }

        public void setMjxm(String mjxm) {
            this.mjxm = mjxm;
        }

        public String getZpRy() {
            return zpRy;
        }

        public void setZpRy(String zpRy) {
            this.zpRy = zpRy;
        }

        public String getTxmjdh() {
            return txmjdh;
        }

        public void setTxmjdh(String txmjdh) {
            this.txmjdh = txmjdh;
        }

        public String getCsmc() {
            return csmc;
        }

        public void setCsmc(String csmc) {
            this.csmc = csmc;
        }

        public String getMjjh() {
            return mjjh;
        }

        public void setMjjh(String mjjh) {
            this.mjjh = mjjh;
        }

        public String getXb() {
            return xb;
        }

        public void setXb(String xb) {
            this.xb = xb;
        }

        public String getXxdz() {
            return xxdz;
        }

        public void setXxdz(String xxdz) {
            this.xxdz = xxdz;
        }

        public String getTxr() {
            return txr;
        }

        public void setTxr(String txr) {
            this.txr = txr;
        }

        public String getSjh() {
            return sjh;
        }

        public void setSjh(String sjh) {
            this.sjh = sjh;
        }

        public String getZpDemp1() {
            return zpDemp1;
        }

        public void setZpDemp1(String zpDemp1) {
            this.zpDemp1 = zpDemp1;
        }

        public String getSfzh() {
            return sfzh;
        }

        public void setSfzh(String sfzh) {
            this.sfzh = sfzh;
        }

        public String getXm() {
            return xm;
        }

        public void setXm(String xm) {
            this.xm = xm;
        }

        public String getZpXd() {
            return zpXd;
        }

        public void setZpXd(String zpXd) {
            this.zpXd = zpXd;
        }

        public String getZpDemp2() {
            return zpDemp2;
        }

        public void setZpDemp2(String zpDemp2) {
            this.zpDemp2 = zpDemp2;
        }

        public String getZpDemp3() {
            return zpDemp3;
        }

        public void setZpDemp3(String zpDemp3) {
            this.zpDemp3 = zpDemp3;
        }

        public String getZpDemp4() {
            return zpDemp4;
        }

        public void setZpDemp4(String zpDemp4) {
            this.zpDemp4 = zpDemp4;
        }

        public String getZpDemp5() {
            return zpDemp5;
        }

        public void setZpDemp5(String zpDemp5) {
            this.zpDemp5 = zpDemp5;
        }

        @Override
        public int compareTo(ObjBean o) {
            return Long.compare(DateUtil.parseDate("yyyy年MM月dd日 HH时mm分ss秒", o.cjsj), DateUtil.parseDate("yyyy年MM月dd日 HH时mm分ss秒", cjsj));
        }
    }
}
