package com.challenge.exception;

import org.springframework.http.HttpStatus;

import static com.challenge.exception.ServiceException.Code.DAOEXCEPTION;

/**
 * Created by tmaldonado on 29/06/2017.
 */
public class DAOException extends ServiceException {

    public DAOException(Throwable cause, String message , Object ... args) {
        super(DAOEXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR, cause, "exception.title.internal.error", message, args);
    }

    public DAOException(String message, Object ... args) {
        super(DAOEXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR, "exception.title.internal.error", message, args);
    }

}
