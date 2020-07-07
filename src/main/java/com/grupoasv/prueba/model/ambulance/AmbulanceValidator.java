package com.grupoasv.prueba.model.ambulance;

import com.grupoasv.prueba.exceptions.ErrorCode;
import com.grupoasv.prueba.exceptions.ExceptionService;
import com.grupoasv.prueba.model.interfaces.AbstractValidator;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AmbulanceValidator extends AbstractValidator{

    public void validate(Ambulance ambulance){
        if(ambulance.getId() == null || ambulance.getTotalSeats() == null || ambulance.getStatus() == null){
            throw new ExceptionService(ErrorCode.BAD_REQUEST);
        }
    }

    public void validate(AmbulanceDto ambulanceDto){
        if(ambulanceDto.getId() == null || ambulanceDto.getTotalSeats() == null){
            throw new ExceptionService(ErrorCode.BAD_REQUEST);
        }
    }

    public void validate(AmbulanceDto[] dtos){
        Arrays.stream(dtos).forEach(this::validate);
    }
}
