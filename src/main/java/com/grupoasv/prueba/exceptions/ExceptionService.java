package com.grupoasv.prueba.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExceptionService extends ResponseStatusException{

    public ExceptionService(HttpStatus status){
        super(status);
    }

    public ExceptionService(HttpStatus status, String reason){
        super(status, reason);
    }

    public ExceptionService(HttpStatus status, String reason, Throwable cause){
        super(status, reason, cause);
    }

    public ExceptionService(ErrorCode errorCode){
        super(errorCode.getStatus(), errorCode.getReason(), new ExceptionInInitializerError());
    }

    public ExceptionService(ErrorCode errorCode, String message){
        super(errorCode.getStatus(), errorCode.getReason() + ": " + message, new ExceptionInInitializerError());
    }

    public ExceptionService(ErrorCode errorCode, Throwable cause){
        super(errorCode.getStatus(), errorCode.getReason(), cause);
    }

    public ExceptionService(ErrorCode errorCode, String message, Throwable cause){
        super(errorCode.getStatus(), errorCode.getReason() + ": " + message, cause);
    }
}
