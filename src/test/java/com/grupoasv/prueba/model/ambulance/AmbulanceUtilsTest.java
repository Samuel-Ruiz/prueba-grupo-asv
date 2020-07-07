package com.grupoasv.prueba.model.ambulance;

import com.grupoasv.prueba.model.patient.PatientGroup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AmbulanceUtilsTest{

    @Test
    public void addPatientGroupShouldAddPatientGroup(){
        Ambulance ambulance = new Ambulance();
        AmbulanceUtils utils = new AmbulanceUtils(ambulance);

        PatientGroup patientGroup = mock(PatientGroup.class);
        utils.addPatientGroup(patientGroup);

        assertThat(ambulance.getPatientGroupList().size(), is(1));

        utils.addPatientGroup(patientGroup);

        assertThat(ambulance.getPatientGroupList().size(), is(2));
    }

    @Test
    public void addPatientGroupShouldAddPatientGroupEvenWhenListInNull(){
        Ambulance ambulance = new Ambulance();
        ambulance.setPatientGroupList(null);
        AmbulanceUtils utils = new AmbulanceUtils(ambulance);

        PatientGroup patientGroup = mock(PatientGroup.class);
        utils.addPatientGroup(patientGroup);

        assertThat(ambulance.getPatientGroupList().size(), is(1));

        utils.addPatientGroup(patientGroup);

        assertThat(ambulance.getPatientGroupList().size(), is(2));
    }

    @Test
    public void isFullShouldReturnTrueWhenAllAvailableSeatsAreOccupied(){
        AmbulanceUtils utils = new AmbulanceUtils(buildAmbulance(2, 2));
        assertTrue(utils.isFull());
    }

    @Test
    public void isFullShouldReturnFalseWhenAnySeatIsFree(){
        AmbulanceUtils utils = new AmbulanceUtils(buildAmbulance(5, 2));
        assertFalse(utils.isFull());
    }

    @Test
    public void isEmptyShouldReturnTrueWhenAllAvailableSeatsAreFree(){
        AmbulanceUtils utils = new AmbulanceUtils(buildAmbulance(2, 0));
        assertTrue(utils.isEmpty());
    }

    @Test
    public void isEmptyShouldReturnFalseWhenAnySeatIsOccupied(){
        AmbulanceUtils utils = new AmbulanceUtils(buildAmbulance(5, 2));
        assertFalse(utils.isEmpty());
    }

    @Test
    public void availableSeatsShouldReturnNumberOfFreeSeats(){
        AmbulanceUtils utils = new AmbulanceUtils(buildAmbulance(2, 0));
        assertThat(utils.availableSeats(), is(2));

        utils = new AmbulanceUtils(buildAmbulance(5, 2));
        assertThat(utils.availableSeats(), is(3));
    }


    private Ambulance buildAmbulance(final Integer totalSeats, final Integer people){
        Ambulance ambulance = new Ambulance();
        ambulance.setTotalSeats(totalSeats);

        if(people > 0){
            PatientGroup patientGroup = mock(PatientGroup.class);
            when(patientGroup.getPeople()).thenReturn(people);

            List<PatientGroup> patientGroupList = new ArrayList<>();
            patientGroupList.add(patientGroup);
            ambulance.setPatientGroupList(patientGroupList);
        }

        return ambulance;
    }

}
