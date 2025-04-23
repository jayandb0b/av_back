package com.sw.av.domain.exceptions;

public class PlSqlWarning extends Exception {

    private static final long serialVersionUID = 1L;

    private Integer retCode;
    private String errBuf;

    private PlSqlWarning() {
    }

    public PlSqlWarning(Object retCode, Object errBuf) {
        this.retCode = (Integer) retCode;
        this.errBuf = (String) errBuf;
    }

    public Integer getRetCode() {
        return retCode;
    }

    public String getErrBuf() {
        return errBuf;
    }

    @Override
    public String getMessage() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "(PL-SQL: " + retCode + ") - " + errBuf;
    }
}
