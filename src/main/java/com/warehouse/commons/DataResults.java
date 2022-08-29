package com.warehouse.commons;

public class DataResults<T> {

    private int code;
    private String msg;
    private T data;

    public DataResults() {
        //无参数的构造
    }

    private DataResults(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static DataResults success(ResultCode resultCode){
        DataResults resultCommon = new DataResults(resultCode.getCode(), resultCode.getMsg());
        return resultCommon;
    }

    public static<T> DataResults success(ResultCode resultCode, T data){
        DataResults success = success(resultCode);
        success.setData(data);
        return success;
    }

    public static DataResults fail(ResultCode resultCode){
        return success(resultCode);
    }

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
