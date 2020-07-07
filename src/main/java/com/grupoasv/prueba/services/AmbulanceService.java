package com.grupoasv.prueba.services;

import com.grupoasv.prueba.exceptions.ErrorCode;
import com.grupoasv.prueba.exceptions.ExceptionService;
import com.grupoasv.prueba.model.ambulance.Ambulance;
import com.grupoasv.prueba.model.ambulance.AmbulanceDto;
import com.grupoasv.prueba.model.ambulance.AmbulanceValidator;
import com.grupoasv.prueba.model.interfaces.Builder;
import com.grupoasv.prueba.repositories.AmbulanceRepository;
import com.grupoasv.prueba.services.interfaces.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AmbulanceService implements DataService<Ambulance, AmbulanceDto>{

    private final AmbulanceRepository repository;
    private final Builder<Ambulance, AmbulanceDto> builder;
    private final AmbulanceValidator validator;

    @Autowired
    public AmbulanceService(final AmbulanceRepository repository, final Builder<Ambulance, AmbulanceDto> builder,
            final AmbulanceValidator validator){
        this.repository = repository;
        this.builder = builder;
        this.validator = validator;
    }

    @Override
    public Ambulance getById(final Integer id){
        return this.repository.findById(id).orElseThrow(() -> new ExceptionService(ErrorCode.AMBULANCE_NOT_FOUND));
    }

    @Override
    public void update(final Ambulance dao){
        this.validator.validate(dao);
        this.repository.save(dao);
    }

    @Override
    public void updateFromDto(final AmbulanceDto[] dtos){
        this.validator.validate(dtos);
        this.repository.deleteAll();
        this.repository.saveAll(this.builder.buildDao(Arrays.asList(dtos)));
    }

    @Override
    public void create(AmbulanceDto dto){
        this.validator.validate(dto);
        this.repository.save(this.builder.buildDao(dto));
    }

    @Override
    public AmbulanceDto[] getAll(){
        return this.builder.buildDto(this.repository.findAll()).toArray(new AmbulanceDto[0]);
    }

    @Override
    public List<Ambulance> getAllByStatus(final String status){
        return this.repository.findAllByStatus(status);
    }
}
