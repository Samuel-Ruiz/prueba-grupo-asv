package com.grupoasv.prueba.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum  ErrorCode{

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Invalid data format"),
    AMBULANCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Ambulance not found"),
    PATIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Patient group not found"),
    CHANGE_PRODUCES_NO_CHANGES(HttpStatus.NO_CONTENT, "Everything is already up to date"),
    PATIENT_GROUP_ALREADY_EXIST(HttpStatus.CONFLICT, "Patient group was already created"),
    PATIENT_GROUP_HAS_NO_AMBULANCE(HttpStatus.NO_CONTENT, "Patient group does not have any associated ambulance");

    private final HttpStatus status;
    private final String reason;

    ErrorCode(HttpStatus status, String reason){
        this.status = status;
        this.reason = reason;
    }

    public HttpStatus getStatus(){
        return status;
    }

    public String getReason(){
        return reason;
    }
}
