package com.grupoasv.prueba.model.patient;

import com.grupoasv.prueba.exceptions.ErrorCode;
import com.grupoasv.prueba.exceptions.ExceptionService;
import com.grupoasv.prueba.model.interfaces.AbstractValidator;
import org.springframework.stereotype.Component;

@Component
public class PatientGroupValidator extends AbstractValidator{

    public void validate(JourneyDto journeyDto){
        if(journeyDto.getId() == null || journeyDto.getPeople() == null){
            throw new ExceptionService(ErrorCode.BAD_REQUEST);
        }
    }

    public void validate(PatientGroup patientGroup){
        if(patientGroup.getId() == null || patientGroup.getPeople() == null || patientGroup.getStatus() == null){
            throw new ExceptionService(ErrorCode.BAD_REQUEST);
        }
    }
}
