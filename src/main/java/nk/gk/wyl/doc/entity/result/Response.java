package nk.gk.wyl.doc.entity.result;

import java.io.Serializable;

public class Response implements Serializable {

    private int code;
    private Object data;
    private String msg;

    public Response success(Object data){
        this.code = StatusCode.SUCCESS_CODE.getCode();
        this.data = data;
        this.msg="";
        return this;
    }

    public Response error(String msg){
        this.code = StatusCode.ERROR_CODE.getCode();
        this.msg=msg;
        return this;
    }


    public Response(){
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
