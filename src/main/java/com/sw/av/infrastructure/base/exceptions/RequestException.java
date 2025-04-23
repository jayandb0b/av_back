package com.sw.av.infrastructure.base.exceptions;

import org.springframework.stereotype.Component;


@Component
public abstract class RequestException extends Exception {
    
    private int status;

    public RequestException() {
    }

    public RequestException(String m) {
        super(m);
    }

    public RequestException(Throwable t) {
        super(t);
    }

    public RequestException(String m, Throwable t) {
        super(m, t);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
