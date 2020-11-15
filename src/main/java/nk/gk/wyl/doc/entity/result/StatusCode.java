package nk.gk.wyl.doc.entity.result;

public enum  StatusCode {
    // 成功
   SUCCESS_CODE(200),
    // 错误码
    ERROR_CODE(400),
    // 插入成功
    INSERT_CODE(201),
    // 更新成功
    UPDATE_CODE(202),
    // 删除成功
    DEL_CODE(301);
    // code码
    private int code;

    StatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
