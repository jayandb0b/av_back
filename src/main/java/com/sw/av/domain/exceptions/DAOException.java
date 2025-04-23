package com.sw.av.domain.exceptions;

public class DAOException extends Exception {

    public DAOException() {
    }

    public DAOException(String m) {
        super(m);
    }

    public DAOException(Throwable t) {
        super(t);
    }

    public DAOException(String m, Throwable t) {
        super(m, t);
    }
    
}
