package com.grupoasv.prueba.model.interfaces;

import com.grupoasv.prueba.exceptions.ExceptionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AbstractValidatorTest{

    @Spy
    AbstractValidator validator = mock(AbstractValidator.class, Mockito.CALLS_REAL_METHODS);

    @Test
    public void validateShouldDoNothingWhenValueIsNotNull(){
        Integer id = 1;
        validator.validate(id);
    }

    @Test
    public void validateShouldReturnExceptionWhenValueIsNull(){
        assertThrows(ExceptionService.class, () -> validator.validate(null));
    }
}
