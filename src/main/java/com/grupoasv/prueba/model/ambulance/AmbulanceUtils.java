package com.grupoasv.prueba.model.ambulance;

import com.grupoasv.prueba.model.patient.PatientGroup;

import java.util.ArrayList;
import java.util.List;

public class AmbulanceUtils {

    private final Ambulance ambulance;

    public AmbulanceUtils(Ambulance ambulance){
        this.ambulance = ambulance;
    }

    public void addPatientGroup(final PatientGroup patientGroup){
        if(this.ambulance.getPatientGroupList() == null){
            this.ambulance.setPatientGroupList(new ArrayList<>());
        }
        List<PatientGroup> groupList = this.ambulance.getPatientGroupList();
        groupList.add(patientGroup);
        this.ambulance.setPatientGroupList(groupList);
    }

    public boolean isFull(){
        return this.availableSeats() == 0;
    }

    public boolean isEmpty(){
        return this.availableSeats().equals(this.ambulance.getTotalSeats());
    }

    public Integer availableSeats(){
        Integer people = this.ambulance.getPatientGroupList().stream().mapToInt(PatientGroup::getPeople).sum();
        return this.ambulance.getTotalSeats() - people;
    }
}
