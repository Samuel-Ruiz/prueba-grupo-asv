package com.grupoasv.prueba.model.ambulance;

import com.grupoasv.prueba.model.common.Status;
import com.grupoasv.prueba.model.interfaces.Builder;
import com.grupoasv.prueba.model.patient.PatientGroup;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component
public class AmbulanceBuilder extends Builder<Ambulance, AmbulanceDto>{

    private Integer id;
    private Integer totalSeats;
    private String status;
    private List<PatientGroup> patientGroupList;
    private int[] patientGroupIds;

    private AmbulanceBuilder with(Consumer<AmbulanceBuilder> builderFunction){
        builderFunction.accept(this);
        return this;
    }

    @Override
    public Ambulance buildDao(final AmbulanceDto dto){
        return new AmbulanceBuilder().with(builder -> {
            builder.id = dto.getId();
            builder.totalSeats = dto.getTotalSeats();
            builder.status = Status.WAITING.name();
            builder.patientGroupList = new ArrayList<>();
        }).createDao();
    }

    @Override
    public AmbulanceDto buildDto(Ambulance dao){
        return new AmbulanceBuilder().with(builder -> {
            builder.id = dao.getId();
            builder.totalSeats = dao.getTotalSeats();
            builder.status = dao.getStatus();
            builder.patientGroupIds = dao.getPatientGroupList().stream().mapToInt(PatientGroup::getId).toArray();
        }).createDto();
    }

    private Ambulance createDao(){
        return new Ambulance(id, totalSeats, status, patientGroupList);
    }

    private AmbulanceDto createDto(){
        return new AmbulanceDto(id, totalSeats, status, patientGroupIds);
    }
}
