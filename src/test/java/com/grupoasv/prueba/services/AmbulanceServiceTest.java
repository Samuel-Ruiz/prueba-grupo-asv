package com.grupoasv.prueba.services;

import com.grupoasv.prueba.exceptions.ExceptionService;
import com.grupoasv.prueba.model.ambulance.Ambulance;
import com.grupoasv.prueba.model.ambulance.AmbulanceDto;
import com.grupoasv.prueba.model.ambulance.AmbulanceValidator;
import com.grupoasv.prueba.model.interfaces.Builder;
import com.grupoasv.prueba.repositories.AmbulanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
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

@ExtendWith(MockitoExtension.class)
class AmbulanceServiceTest{

    private AmbulanceRepository repository;
    private Builder<Ambulance, AmbulanceDto> builder;
    private AmbulanceValidator validator;

    private AmbulanceService service;

    @BeforeEach
    public void setUp(){
        repository = mock(AmbulanceRepository.class);
        builder = (Builder<Ambulance, AmbulanceDto>) mock(Builder.class);
        validator = mock(AmbulanceValidator.class);

        service = new AmbulanceService(repository, builder, validator);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateShouldCallValidateAndThenSave(){
        Ambulance ambulance = mock(Ambulance.class);
        doNothing().when(validator).validate(ambulance);
        when(repository.save(ambulance)).thenReturn(ambulance);

        service.update(ambulance);

        verify(validator, times(1)).validate(ambulance);
        verify(repository, times(1)).save(ambulance);
    }

    @Test
    public void updateFromDtoShouldCallValidateDeleteBuildDaoAndThenSaveAll(){
        List<Ambulance> ambulanceList = new ArrayList<>();
        AmbulanceDto[] ambulanceDto = new AmbulanceDto[0];
        doNothing().when(validator).validate(ambulanceDto);
        when(repository.saveAll(ambulanceList)).thenReturn(ambulanceList);
        when(builder.buildDao(Arrays.asList(ambulanceDto))).thenReturn(new ArrayList<>());

        service.updateFromDto(ambulanceDto);

        verify(validator, times(1)).validate(ambulanceDto);
        verify(repository, times(1)).saveAll(anyList());
        verify(repository, times(1)).deleteAll();
        verify(builder, times(1)).buildDao(anyList());
    }

    @Test
    public void createShouldCallValidateBuildDaoAndThenSave(){
        Ambulance ambulance = mock(Ambulance.class);
        AmbulanceDto ambulanceDto = mock(AmbulanceDto.class);
        doNothing().when(validator).validate(ambulanceDto);
        when(repository.save(ambulance)).thenReturn(ambulance);
        when(builder.buildDao(ambulanceDto)).thenReturn(ambulance);

        service.create(ambulanceDto);

        verify(validator, times(1)).validate(ambulanceDto);
        verify(repository, times(1)).save(ambulance);
        verify(builder, times(1)).buildDao(ambulanceDto);
    }

    @Test
    public void getByIdShouldReturnAnAmbulanceWhenElementAlreadyExists(){
        Integer id = 10;
        Ambulance ambulance = mock(Ambulance.class);
        when(repository.findById(anyInt())).thenReturn(Optional.of(ambulance));

        Ambulance response = service.getById(id);

        assertThat(response, is(ambulance));
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
    public void getAllByStatusShouldReturnAnAmbulanceWhenElementExists(){
        String status = "status";
        List<Ambulance> ambulanceList = new ArrayList<>();
        when(repository.findAllByStatus(anyString())).thenReturn(ambulanceList);

        List<Ambulance> response = service.getAllByStatus(status);

        assertThat(response, is(ambulanceList));
        verify(repository, times(1)).findAllByStatus(anyString());
    }

    @Test
    public void getAllShouldCallRepositoryToRetrieveDataAndBuildItIntoDto(){
        List<AmbulanceDto> expected = new ArrayList<>();
        List<Ambulance> ambulanceList = new ArrayList<>();

        when(repository.findAll()).thenReturn(ambulanceList);
        when(builder.buildDto(anyList())).thenReturn(expected);

        AmbulanceDto[] response = service.getAll();

        assertThat(response, is(expected.toArray(new AmbulanceDto[0])));
        verify(builder, times(1)).buildDto(anyList());
        verify(repository, times(1)).findAll();
    }
}
