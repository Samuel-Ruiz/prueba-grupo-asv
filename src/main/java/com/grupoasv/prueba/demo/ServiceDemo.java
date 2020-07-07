package com.grupoasv.prueba.demo;

import com.grupoasv.prueba.model.ambulance.Ambulance;
import com.grupoasv.prueba.model.ambulance.AmbulanceUtils;
import com.grupoasv.prueba.model.common.Status;
import com.grupoasv.prueba.model.patient.PatientGroup;
import com.grupoasv.prueba.services.AmbulanceService;
import com.grupoasv.prueba.services.PatientGroupService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ServiceDemo {

    private static final Integer FREE_SEATS_LIMIT = 1;

    private final AmbulanceService ambulanceService;
    private final PatientGroupService patientGroupService;

    public ServiceDemo(AmbulanceService ambulanceService, PatientGroupService patientGroupService){
        this.ambulanceService = ambulanceService;
        this.patientGroupService = patientGroupService;
    }

    @Transactional
    public void executePendingTransfers(){
        List<Ambulance> ambulanceList = this.ambulanceService.getAllByStatus(Status.WAITING.toString());
        List<PatientGroup> patientGroupList = this.patientGroupService.getAllByStatus(Status.WAITING.toString());

        ambulanceList.forEach(ambulance -> this.fulfillAmbulance(ambulance, patientGroupList));
    }

    public void executeAmbulanceEndTransfer(){
        List<Ambulance> ambulanceList = this.ambulanceService.getAllByStatus(Status.TRAVELING.toString());
        ambulanceList.forEach( ambulance -> {
            AmbulanceUtils ambulanceUtils = new AmbulanceUtils(ambulance);
            if(ambulanceUtils.isEmpty()){
                ambulance.setStatus(Status.END.toString());
                this.ambulanceService.update(ambulance);
            }
        });
    }

    private void fulfillAmbulance(final Ambulance ambulance, final List<PatientGroup> patientGroupList){
        AtomicBoolean isAnyGroupAvailable = new AtomicBoolean(true);
        AmbulanceUtils ambulanceUtils = new AmbulanceUtils(ambulance);
        while(!ambulanceUtils.isFull() && isAnyGroupAvailable.get()){
            patientGroupList.stream().filter(group -> (group.getStatus().equals(Status.WAITING.toString()) && ambulanceUtils.availableSeats() >= group.getPeople()))
                    .findFirst()
                    .ifPresentOrElse(group -> {
                        group.setStatus(Status.ONBOARD.toString());
                        group.setAmbulance(ambulance);
                        ambulanceUtils.addPatientGroup(group);
                        this.ambulanceService.update(ambulance);
                    }, () -> isAnyGroupAvailable.set(false));
        }
        if(ambulanceUtils.availableSeats() <= FREE_SEATS_LIMIT){
            ambulance.setStatus(Status.TRAVELING.toString());
            ambulance.getPatientGroupList().forEach( patientGroup -> patientGroup.setStatus(Status.TRAVELING.toString()));
            this.ambulanceService.update(ambulance);
        }
    }
}
