package com.sw.av.infrastructure.base.exceptions;

import com.sw.av.infrastructure.base.StatusRest;
import com.sw.av.infrastructure.base.exceptions.RequestException;
import org.springframework.stereotype.Component;

@Component
public class LoginResquestException extends RequestException {

    public LoginResquestException() {
        this.setStatus(StatusRest.FORBIDDEN);
    }

    public LoginResquestException(String m) {
        super(m);
        this.setStatus(StatusRest.FORBIDDEN);
    }

    public LoginResquestException(Throwable t) {
        super(t);
        this.setStatus(StatusRest.FORBIDDEN);
    }

    public LoginResquestException(String m, Throwable t) {
        super(m, t);
        this.setStatus(StatusRest.FORBIDDEN);
    }

}
