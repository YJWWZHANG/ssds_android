package com.dashi1314.common.bean;

public class SmsCodeErrorMessage {
    /**
     * detail : 当前手机号码每天最多可发送短信10条，包括客户端发送和WebApi发送
     * description : 当前手机号发送短信的数量超过限额
     * httpStatus : 400
     * error : The app or phone send SMS overrun .
     * status : 477
     */

    private String detail;
    private String description;
    private int httpStatus;
    private String error;
    private int status;

    @Override
    public String toString() {
        return "SmsCodeErrorMessage{" +
                "detail='" + detail + '\'' +
                ", description='" + description + '\'' +
                ", httpStatus=" + httpStatus +
                ", error='" + error + '\'' +
                ", status=" + status +
                '}';
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
