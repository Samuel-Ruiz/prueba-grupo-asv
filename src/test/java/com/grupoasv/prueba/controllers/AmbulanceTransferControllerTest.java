package com.grupoasv.prueba.controllers;

import com.grupoasv.prueba.exceptions.ErrorCode;
import com.grupoasv.prueba.exceptions.ExceptionService;
import com.grupoasv.prueba.model.ambulance.AmbulanceDto;
import com.grupoasv.prueba.model.patient.JourneyDto;
import com.grupoasv.prueba.services.AmbulanceTransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AmbulanceTransferControllerTest{

    @Mock
    private AmbulanceTransferService service;

    @InjectMocks
    private AmbulanceTransferController controller;

    @Test
    public void getStatusShouldResponseHttpStatusOk(){
        assertThat(controller.getStatus(), is(new ResponseEntity<>(HttpStatus.OK)));
    }

    @Test
    public void updateAmbulancesOnServiceShouldCallServiceAndResponseHttpStatusOkWhenIsSuccess(){
        doNothing().when(service).updateAmbulances(AdditionalMatchers.aryEq(new AmbulanceDto[0]));

        ResponseEntity<String> responseEntity = controller.updateAmbulancesOnService(new AmbulanceDto[0]);

        assertThat(responseEntity, is(new ResponseEntity<>(HttpStatus.OK)));
        verify(service, times(1)).updateAmbulances(AdditionalMatchers.aryEq(new AmbulanceDto[0]));
    }

    @Test()
    public void updateAmbulancesOnServiceShouldCallServiceAndResponseHttpStatusBadRequestWhenIsError(){
        doThrow(new ExceptionService(ErrorCode.BAD_REQUEST)).when(service)
                .updateAmbulances(AdditionalMatchers.aryEq(new AmbulanceDto[0]));

        ResponseEntity<String> responseEntity = controller.updateAmbulancesOnService(new AmbulanceDto[0]);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        verify(service, times(1)).updateAmbulances(AdditionalMatchers.aryEq(new AmbulanceDto[0]));
    }

    @Test
    public void journeyShouldCallServiceAndResponseHttpStatusAcceptedWhenIsSuccess(){
        doNothing().when(service).createJourney(any(JourneyDto.class));

        ResponseEntity<String> responseEntity = controller.journey(new JourneyDto());

        assertThat(responseEntity, is(new ResponseEntity<>(HttpStatus.ACCEPTED)));
        verify(service, times(1)).createJourney(any(JourneyDto.class));
    }

    @Test()
    public void journeyShouldCallServiceAndResponseHttpStatusBadRequestWhenIsError(){
        doThrow(new ExceptionService(ErrorCode.BAD_REQUEST)).when(service).createJourney(any(JourneyDto.class));

        ResponseEntity<String> responseEntity = controller.journey(new JourneyDto());

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        verify(service, times(1)).createJourney(any(JourneyDto.class));
    }

    @Test
    public void transferShouldCallServiceAndResponseHttpStatusOkWhenIsSuccess(){
        doNothing().when(service).transferPatientGroup(anyInt());

        ResponseEntity<String> responseEntity = controller.transfer(0);

        assertThat(responseEntity, is(new ResponseEntity<>(HttpStatus.OK)));
        verify(service, times(1)).transferPatientGroup(anyInt());
    }

    @Test()
    public void transferShouldCallServiceAndResponseHttpStatusBadRequestWhenIsError(){
        doThrow(new ExceptionService(ErrorCode.BAD_REQUEST)).when(service).transferPatientGroup(anyInt());

        ResponseEntity<String> responseEntity = controller.transfer(0);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        verify(service, times(1)).transferPatientGroup(anyInt());
    }

    @Test
    public void locateShouldCallServiceAndResponseHttpStatusOkWhenIsSuccess(){
        Integer expected = 1;
        when(service.locate(anyInt())).thenReturn(expected);

        ResponseEntity<String> responseEntity = controller.locate(0);

        assertThat(responseEntity, is(new ResponseEntity<>(expected.toString(), HttpStatus.OK)));
        verify(service, times(1)).locate(anyInt());
    }

    @Test()
    public void locateShouldCallServiceAndResponseHttpStatusBadRequestWhenIsError(){
        doThrow(new ExceptionService(ErrorCode.BAD_REQUEST)).when(service).locate(anyInt());

        ResponseEntity<String> responseEntity = controller.locate(0);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        verify(service, times(1)).locate(anyInt());
    }

    @Test
    public void getAmbulancesShouldReturnAnAmbulancesArray(){
        AmbulanceDto[] expected = new AmbulanceDto[0];
        when(service.getAmbulances()).thenReturn(new AmbulanceDto[0]);

        AmbulanceDto[] response = controller.getAmbulances();

        assertThat(response, is(expected));
        verify(service, times(1)).getAmbulances();
    }

    @Test
    public void getJourneyShouldReturnAJourneyArray(){
        JourneyDto[] expected = new JourneyDto[0];
        when(service.getJourney()).thenReturn(new JourneyDto[0]);

        JourneyDto[] response = controller.getJourney();

        assertThat(response, is(expected));
        verify(service, times(1)).getJourney();
    }

}
