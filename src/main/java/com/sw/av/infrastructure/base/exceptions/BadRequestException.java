package com.sw.av.infrastructure.base.exceptions;

import com.sw.av.infrastructure.base.StatusRest;
import org.springframework.stereotype.Component;

@Component
public class BadRequestException extends RequestException {

    public BadRequestException() {
        this.setStatus(StatusRest.BAD_REQUEST);
    }

    public BadRequestException(String m) {
        super(m);
        this.setStatus(StatusRest.BAD_REQUEST);
    }

    public BadRequestException(Throwable t) {
        super(t);
        this.setStatus(StatusRest.BAD_REQUEST);
    }

    public BadRequestException(String m, Throwable t) {
        super(m, t);
        this.setStatus(StatusRest.BAD_REQUEST);
    }

}
