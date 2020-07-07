package com.grupoasv.prueba.services;

import com.grupoasv.prueba.exceptions.ErrorCode;
import com.grupoasv.prueba.exceptions.ExceptionService;
import com.grupoasv.prueba.model.ambulance.Ambulance;
import com.grupoasv.prueba.model.ambulance.AmbulanceDto;
import com.grupoasv.prueba.model.common.Status;
import com.grupoasv.prueba.model.patient.JourneyDto;
import com.grupoasv.prueba.model.patient.PatientGroup;
import com.grupoasv.prueba.services.interfaces.DataService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmbulanceTransferService{

    private final DataService<Ambulance, AmbulanceDto> ambulanceService;
    private final DataService<PatientGroup, JourneyDto> patientGroupService;

    public AmbulanceTransferService(DataService<Ambulance, AmbulanceDto> ambulanceService,
            DataService<PatientGroup, JourneyDto> patientGroupService){
        this.ambulanceService = ambulanceService;
        this.patientGroupService = patientGroupService;
    }

    public void updateAmbulances(final AmbulanceDto[] ambulances){
        this.ambulanceService.updateFromDto(ambulances);
    }

    public AmbulanceDto[] getAmbulances(){
        return this.ambulanceService.getAll();
    }

    public void createJourney(final JourneyDto dto){
        this.patientGroupService.create(dto);
    }

    public JourneyDto[] getJourney(){
        return this.patientGroupService.getAll();
    }


    @Transactional
    public void transferPatientGroup(final Integer id){
        PatientGroup patientGroup = this.patientGroupService.getById(id);

        checkChangeProducesChange(patientGroup);

        Ambulance ambulance = getPatientGroupAmbulanceWithoutPatientGroup(patientGroup);

        updateTransferredPatientGroup(patientGroup);

        this.ambulanceService.update(ambulance);
    }

    private void checkChangeProducesChange(PatientGroup patientGroup){
        if(patientGroup.getStatus().equals(Status.END.toString())){
            throw new ExceptionService(ErrorCode.CHANGE_PRODUCES_NO_CHANGES);
        }
    }

    private Ambulance getPatientGroupAmbulanceWithoutPatientGroup(PatientGroup patientGroup){
        Ambulance ambulance = this.ambulanceService.getById(patientGroup.getAmbulance().getId());

        List<PatientGroup> updatedPatientList = ambulance.getPatientGroupList().stream()
                .filter(group -> !group.getId().equals(patientGroup.getId())).collect(Collectors.toList());
        ambulance.setPatientGroupList(updatedPatientList);

        return ambulance;
    }

    private void updateTransferredPatientGroup(PatientGroup patientGroup){
        patientGroup.setStatus(Status.END.toString());
        patientGroup.setAmbulance(null);
    }

    public Integer locate(final Integer id){
        PatientGroup patientGroup = this.patientGroupService.getById(id);

        checkPatientGroupHasAmbulance(patientGroup);

        return patientGroup.getAmbulance().getId();
    }

    private void checkPatientGroupHasAmbulance(PatientGroup patientGroup){
        if(patientGroup.getAmbulance() == null){
            throw new ExceptionService(ErrorCode.PATIENT_GROUP_HAS_NO_AMBULANCE);
        }
    }
}
