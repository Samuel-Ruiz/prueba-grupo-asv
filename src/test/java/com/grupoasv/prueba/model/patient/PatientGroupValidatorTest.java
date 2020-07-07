package com.grupoasv.prueba.model.patient;

import com.grupoasv.prueba.exceptions.ExceptionService;
import com.grupoasv.prueba.model.common.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PatientGroupValidatorTest{

    PatientGroupValidator validator = new PatientGroupValidator();

    @Test
    public void validateDtoShouldDoNothingWhenValueIsNotNull(){
        JourneyDto journeyDto = new JourneyDto();
        Integer notNullInt = 1;
        journeyDto.setId(notNullInt);
        journeyDto.setPeople(notNullInt);

        validator.validate(journeyDto);
    }

    @Test
    public void validateDtoShouldReturnExceptionWhenValueIsNull(){
        assertThrows(ExceptionService.class, () -> validator.validate(new JourneyDto()));
    }

    @Test
    public void validateDaoShouldDoNothingWhenValueIsNotNull(){
        PatientGroup patientGroup = new PatientGroup();
        Integer notNullInt = 1;
        patientGroup.setId(notNullInt);
        patientGroup.setPeople(notNullInt);
        patientGroup.setStatus(Status.WAITING.toString());

        validator.validate(patientGroup);
    }

    @Test
    public void validateDaoShouldReturnExceptionWhenValueIsNull(){
        assertThrows(ExceptionService.class, () -> validator.validate(new PatientGroup()));
    }
}
