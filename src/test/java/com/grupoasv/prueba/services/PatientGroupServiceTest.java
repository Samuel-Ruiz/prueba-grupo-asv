package com.grupoasv.prueba.services;

import com.grupoasv.prueba.exceptions.ExceptionService;
import com.grupoasv.prueba.model.ambulance.Ambulance;
import com.grupoasv.prueba.model.ambulance.AmbulanceDto;
import com.grupoasv.prueba.model.interfaces.Builder;
import com.grupoasv.prueba.model.patient.JourneyDto;
import com.grupoasv.prueba.model.patient.PatientGroup;
import com.grupoasv.prueba.model.patient.PatientGroupValidator;
import com.grupoasv.prueba.repositories.PatientGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientGroupServiceTest{
    private PatientGroupRepository repository;
    private Builder<PatientGroup, JourneyDto> builder;
    private PatientGroupValidator validator;

    private PatientGroupService service;

    @BeforeEach
    public void setUp(){
        repository = mock(PatientGroupRepository.class);
        builder = (Builder<PatientGroup, JourneyDto>) mock(Builder.class);
        validator = mock(PatientGroupValidator.class);

        service = new PatientGroupService(repository, builder, validator);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getByIdShouldReturnAPatientGroupWhenElementAlreadyExists(){
        Integer id = 10;
        PatientGroup patientGroup = mock(PatientGroup.class);
        when(repository.findById(anyInt())).thenReturn(Optional.of(patientGroup));

        PatientGroup response = service.getById(id);

        assertThat(response, is(patientGroup));
        verify(repository, times(1)).findById(anyInt());
    }

    @Test
    public void getByIdShouldThrowExceptionElementWasNotFound(){
        Integer id = 10;
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ExceptionService.class, () -> service.getById(id));

        verify(repository, times(1)).findById(anyInt());
    }

    @Test
    public void updateShouldCallValidateAndThenSave(){
        PatientGroup patientGroup = mock(PatientGroup.class);
        doNothing().when(validator).validate(patientGroup);
        when(repository.save(patientGroup)).thenReturn(patientGroup);

        service.update(patientGroup);

        verify(validator, times(1)).validate(patientGroup);
        verify(repository, times(1)).save(patientGroup);
    }

    @Test
    public void updateFromDtoThrowsUnsupportedOperationException(){
        assertThrows(UnsupportedOperationException.class, () -> service.updateFromDto(new JourneyDto[0]));
    }

    @Test
    public void createShouldCallValidateBuildDaoAndThenSaveWhenElementDoesNotExist(){
        PatientGroup patientGroup = mock(PatientGroup.class);
        JourneyDto journeyDto = mock(JourneyDto.class);
        doNothing().when(validator).validate(journeyDto);
        when(repository.existsById(anyInt())).thenReturn(false);
        when(repository.save(patientGroup)).thenReturn(patientGroup);
        when(builder.buildDao(journeyDto)).thenReturn(patientGroup);

        service.create(journeyDto);

        verify(validator, times(1)).validate(journeyDto);
        verify(repository, times(1)).existsById(anyInt());
        verify(repository, times(1)).save(patientGroup);
        verify(builder, times(1)).buildDao(journeyDto);
    }

    @Test
    public void createShouldThrowExceptionWhenElementDoesExist(){
        PatientGroup patientGroup = mock(PatientGroup.class);
        JourneyDto journeyDto = mock(JourneyDto.class);
        doNothing().when(validator).validate(journeyDto);
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.save(patientGroup)).thenReturn(patientGroup);
        when(builder.buildDao(journeyDto)).thenReturn(patientGroup);

        assertThrows(ExceptionService.class, () -> service.create(journeyDto));

        verify(validator, times(1)).validate(journeyDto);
        verify(repository, times(1)).existsById(anyInt());
        verify(repository, times(0)).save(patientGroup);
        verify(builder, times(0)).buildDao(journeyDto);
    }

    @Test
    public void getAllByStatusShouldReturnAndAmbulanceWhenElementExists(){
        String status = "status";
        List<PatientGroup> patientGroupList = new ArrayList<>();
        when(repository.findAllByStatus(anyString())).thenReturn(patientGroupList);

        List<PatientGroup> response = service.getAllByStatus(status);

        assertThat(response, is(patientGroupList));
        verify(repository, times(1)).findAllByStatus(anyString());
    }

    @Test
    public void getAllShouldCallRepositoryToRetrieveDataAndBuildItIntoDto(){
        List<JourneyDto> expected = new ArrayList<>();
        List<PatientGroup> patientGroupList = new ArrayList<>();

        when(repository.findAll()).thenReturn(patientGroupList);
        when(builder.buildDto(anyList())).thenReturn(expected);

        JourneyDto[] response = service.getAll();

        assertThat(response, is(expected.toArray(new JourneyDto[0])));
        verify(builder, times(1)).buildDto(anyList());
        verify(repository, times(1)).findAll();
    }

}
