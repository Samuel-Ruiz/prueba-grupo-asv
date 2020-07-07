package com.grupoasv.prueba.services;

import com.grupoasv.prueba.exceptions.ErrorCode;
import com.grupoasv.prueba.exceptions.ExceptionService;
import com.grupoasv.prueba.model.ambulance.AmbulanceDto;
import com.grupoasv.prueba.model.interfaces.Builder;
import com.grupoasv.prueba.model.patient.JourneyDto;
import com.grupoasv.prueba.model.patient.PatientGroup;
import com.grupoasv.prueba.model.patient.PatientGroupValidator;
import com.grupoasv.prueba.repositories.PatientGroupRepository;
import com.grupoasv.prueba.services.interfaces.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientGroupService implements DataService<PatientGroup, JourneyDto>{
    private final PatientGroupRepository repository;
    private final Builder<PatientGroup, JourneyDto> builder;
    private final PatientGroupValidator validator;

    @Autowired
    public PatientGroupService(PatientGroupRepository repository, Builder<PatientGroup, JourneyDto> builder,
            PatientGroupValidator validator){
        this.repository = repository;
        this.builder = builder;
        this.validator = validator;
    }

    @Override
    public PatientGroup getById(final Integer id){
        this.validator.validate(id);
        return this.repository.findById(id).orElseThrow(() -> new ExceptionService(ErrorCode.PATIENT_NOT_FOUND));
    }

    @Override
    public JourneyDto[] getAll(){
        return this.builder.buildDto(this.repository.findAll()).toArray(new JourneyDto[0]);
    }

    @Override
    public void update(final PatientGroup dao){
        this.validator.validate(dao);
        this.repository.save(dao);
    }

    @Override
    public void updateFromDto(JourneyDto[] dtos){
        throw new UnsupportedOperationException();
    }

    @Override
    public void create(JourneyDto dto){
        this.validator.validate(dto);
        if(this.repository.existsById(dto.getId())){
            throw new ExceptionService(ErrorCode.PATIENT_GROUP_ALREADY_EXIST);
        } else {
            this.repository.save(this.builder.buildDao(dto));
        }
    }

    @Override
    public List<PatientGroup> getAllByStatus(final String status){
        return this.repository.findAllByStatus(status);
    }
}
