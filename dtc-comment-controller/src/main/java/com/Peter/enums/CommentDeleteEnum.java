package com.Peter.enums;

public enum CommentDeleteEnum {
    NORMAL(0, "正常"),
    DELETE(1, "删除成功");
    int code;
    String message;
    //无参构造
    CommentDeleteEnum() {
    }
    //带参构造
    CommentDeleteEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
    //getter setter
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
