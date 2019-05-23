package com.zhhl.qingbao.data;

/**
 * Created by miao on 2018/12/19.
 */
public class RespData {
    /**
     * obj : null
     * attributes : null
     * success : true
     * msg : 情报基本信息添加成功
     */

    private String obj;
    private Object attributes;
    private boolean success;
    private String msg;

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
