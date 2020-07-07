package com.grupoasv.prueba.model.ambulance;

import com.grupoasv.prueba.exceptions.ExceptionService;
import com.grupoasv.prueba.model.common.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AmbulanceValidatorTest{

    AmbulanceValidator validator = new AmbulanceValidator();

    @Test
    public void validateDtoShouldDoNothingWhenValueIsNotNull(){
        AmbulanceDto ambulanceDto = new AmbulanceDto();
        Integer notNullInt = 1;
        ambulanceDto.setId(notNullInt);
        ambulanceDto.setTotalSeats(notNullInt);

        validator.validate(ambulanceDto);
    }

    @Test
    public void validateDtoShouldReturnExceptionWhenValueIsNull(){
        assertThrows(ExceptionService.class, () -> validator.validate(new AmbulanceDto()));
    }

    @Test
    public void validateDaoShouldDoNothingWhenValueIsNotNull(){
        Ambulance ambulance = new Ambulance();
        Integer notNullInt = 1;
        ambulance.setId(notNullInt);
        ambulance.setTotalSeats(notNullInt);
        ambulance.setStatus(Status.WAITING.toString());

        validator.validate(ambulance);
    }

    @Test
    public void validateDaoShouldReturnExceptionWhenValueIsNull(){
        assertThrows(ExceptionService.class, () -> validator.validate(new Ambulance()));
    }
}
