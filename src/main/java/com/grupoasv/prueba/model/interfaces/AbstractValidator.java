package com.grupoasv.prueba.model.interfaces;

import com.grupoasv.prueba.exceptions.ErrorCode;
import com.grupoasv.prueba.exceptions.ExceptionService;

public abstract class AbstractValidator {

    public void validate(Integer id){
        if(id == null){
            throw new ExceptionService(ErrorCode.BAD_REQUEST);
        }
    }
}
