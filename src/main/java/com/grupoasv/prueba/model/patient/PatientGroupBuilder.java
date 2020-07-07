package com.grupoasv.prueba.model.patient;

import com.grupoasv.prueba.model.ambulance.Ambulance;
import com.grupoasv.prueba.model.ambulance.AmbulanceBuilder;
import com.grupoasv.prueba.model.ambulance.AmbulanceDto;
import com.grupoasv.prueba.model.common.Status;
import com.grupoasv.prueba.model.interfaces.Builder;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class PatientGroupBuilder extends Builder<PatientGroup, JourneyDto>{

    private Integer id;
    private Integer people;
    private String status;
    private Ambulance ambulance;
    private Integer ambulanceId;

    @Override
    public PatientGroup buildDao(final JourneyDto journeyDto){
        return new PatientGroupBuilder().with(builder -> {
            builder.id = journeyDto.getId();
            builder.people = journeyDto.getPeople();
            builder.status = Status.WAITING.toString();
            builder.ambulance = null;
        }).createDao();
    }

    @Override
    public JourneyDto buildDto(PatientGroup dao){
        return new PatientGroupBuilder().with(builder -> {
            builder.id = dao.getId();
            builder.people = dao.getPeople();
            builder.status = dao.getStatus();
            builder.ambulanceId = dao.getAmbulance() != null ? dao.getAmbulance().getId() : null;
        }).createDto();
    }

    private PatientGroup createDao(){
        return new PatientGroup(id, people, status, ambulance);
    }

    private JourneyDto createDto(){
        return new JourneyDto(id, people, status, ambulanceId);
    }


    private PatientGroupBuilder with(Consumer<PatientGroupBuilder> builderFunction){
        builderFunction.accept(this);
        return this;
    }
}
