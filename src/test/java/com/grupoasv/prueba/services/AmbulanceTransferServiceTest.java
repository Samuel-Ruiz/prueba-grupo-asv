package com.grupoasv.prueba.services;

import com.grupoasv.prueba.exceptions.ExceptionService;
import com.grupoasv.prueba.model.ambulance.Ambulance;
import com.grupoasv.prueba.model.ambulance.AmbulanceDto;
import com.grupoasv.prueba.model.common.Status;
import com.grupoasv.prueba.model.patient.JourneyDto;
import com.grupoasv.prueba.model.patient.PatientGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalMatchers;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AmbulanceTransferServiceTest{

    private AmbulanceService ambulanceService;

    private PatientGroupService patientGroupService;

    private AmbulanceTransferService service;

    @BeforeEach
    public void setUp(){
        ambulanceService = mock(AmbulanceService.class);
        patientGroupService = mock(PatientGroupService.class);

        service = new AmbulanceTransferService(ambulanceService, patientGroupService);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateAmbulancesShouldCallAmbulanceService(){
        doNothing().when(ambulanceService).updateFromDto(AdditionalMatchers.aryEq(new AmbulanceDto[0]));

        service.updateAmbulances(new AmbulanceDto[0]);

        verify(ambulanceService, times(1)).updateFromDto(AdditionalMatchers.aryEq(new AmbulanceDto[0]));
    }

    @Test
    public void createJourneyShouldCallPatientGroupService(){
        doNothing().when(patientGroupService).create(any(JourneyDto.class));

        service.createJourney(new JourneyDto());

        verify(patientGroupService, times(1)).create(any(JourneyDto.class));
    }

    @Test
    public void transferPatientGroupShouldUpdateGroupStatusAndRemoveGroupFromAmbulanceWhenGroupIsTravelling(){
        Integer id = 1;
        List<PatientGroup> patientGroupList = new ArrayList<>();
        Ambulance ambulance = mockAmbulance(null, patientGroupList);
        PatientGroup patientGroup = mockPatientGroup(id, Status.TRAVELING.toString(), ambulance);
        patientGroupList.add(patientGroup);
        when(patientGroupService.getById(anyInt())).thenReturn(patientGroup);
        when(ambulanceService.getById(anyInt())).thenReturn(ambulance);

        service.transferPatientGroup(id);

        verify(patientGroupService, times(1)).getById(anyInt());
        verify(ambulanceService, times(1)).getById(anyInt());
        verify(ambulanceService, times(1)).update(any(Ambulance.class));
    }

    @Test
    public void transferPatientGroupShouldThrowExceptionWhenGroupHasEndedTravel(){
        Integer id = 1;
        PatientGroup patientGroup = mockPatientGroup(null, Status.END.toString(), null);
        when(patientGroupService.getById(anyInt())).thenReturn(patientGroup);
        assertThrows(ExceptionService.class, () -> service.transferPatientGroup(id));

        verify(patientGroupService, times(1)).getById(anyInt());
        verify(ambulanceService, times(0)).getById(anyInt());
        verify(ambulanceService, times(0)).update(any(Ambulance.class));
    }

    @Test
    public void locateShouldReturnAmbulanceIdWhenGroupHasAmbulance(){
        Integer id = 1;
        Integer expected = 5;
        Ambulance ambulance = mockAmbulance(expected,null);
        PatientGroup patientGroup = mockPatientGroup(null, null, ambulance);
        when(patientGroupService.getById(anyInt())).thenReturn(patientGroup);

        Integer response =  service.locate(id);

        assertThat(response, is(expected));
        verify(patientGroupService, times(1)).getById(anyInt());
    }

    @Test
    public void locateShouldThrowExceptionWhenGroupHasNoAmbulance(){
        Integer id = 1;
        PatientGroup patientGroup = mockPatientGroup(null, null, null);
        when(patientGroupService.getById(anyInt())).thenReturn(patientGroup);

        assertThrows(ExceptionService.class, () -> service.locate(id));

        verify(patientGroupService, times(1)).getById(anyInt());
    }

    @Test
    public void getAmbulancesShouldCallAmbulancesService(){
        AmbulanceDto[] expected = new AmbulanceDto[0];
        when(ambulanceService.getAll()).thenReturn(new AmbulanceDto[0]);

        AmbulanceDto[] response = service.getAmbulances();

        assertThat(response, is(expected));
        verify(ambulanceService, times(1)).getAll();
    }

    @Test
    public void getJourneyShouldCallPatientGroupService(){
        JourneyDto[] expected = new JourneyDto[0];
        when(patientGroupService.getAll()).thenReturn(new JourneyDto[0]);

        JourneyDto[] response = service.getJourney();

        assertThat(response, is(expected));
        verify(patientGroupService, times(1)).getAll();
    }

    private PatientGroup mockPatientGroup(final Integer id, final String status, final Ambulance ambulance){
        PatientGroup patientGroup = mock(PatientGroup.class);

        if(id != null){
            when(patientGroup.getId()).thenReturn(id);
        }
        if(status != null){
            when(patientGroup.getStatus()).thenReturn(status);
        }

        if(ambulance != null){
            when(patientGroup.getAmbulance()).thenReturn(ambulance);
        }

        return patientGroup;
    }

    private Ambulance mockAmbulance(final Integer id, final List<PatientGroup> patientGroupList){
        Ambulance ambulance = mock(Ambulance.class);
        if(id != null){
            when(ambulance.getId()).thenReturn(id);
        }
        if(patientGroupList != null){
            when(ambulance.getPatientGroupList()).thenReturn(patientGroupList);
            doNothing().when(ambulance).setPatientGroupList(anyList());
        }

        return ambulance;
    }

}
