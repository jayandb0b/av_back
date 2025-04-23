package com.sw.av.infrastructure.base.exceptions;

import com.sw.av.infrastructure.base.StatusRest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class PermisionResquestException extends RequestException {

    public PermisionResquestException() {
        this.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    public PermisionResquestException(String m) {
        super(m);
        this.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    public PermisionResquestException(Throwable t) {
        super(t);
        this.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    public PermisionResquestException(String m, Throwable t) {
        super(m, t);
        this.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

}
