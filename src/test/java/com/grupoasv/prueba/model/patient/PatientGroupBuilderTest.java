package com.grupoasv.prueba.model.patient;

import com.grupoasv.prueba.model.ambulance.Ambulance;
import com.grupoasv.prueba.model.ambulance.AmbulanceDto;
import com.grupoasv.prueba.model.common.Status;
import com.grupoasv.prueba.model.interfaces.Builder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
class PatientGroupBuilderTest{

    Builder<PatientGroup, JourneyDto> builder = new PatientGroupBuilder();

    @Test
    public void buildDaoShouldConsumeAnyDtoAndReturnAnEquivalentDao(){
        PatientGroup expected = new PatientGroup();
        Integer id = 1;
        Integer people = 1;
        expected.setId(id);
        expected.setPeople(people);
        expected.setStatus(Status.WAITING.toString());

        JourneyDto dto = new JourneyDto();
        dto.setId(id);
        dto.setPeople(people);
        PatientGroup response = builder.buildDao(dto);

        assertThat(response, is(expected));
    }

    @Test
    public void buildDtoShouldConsumeAnyDaoAndReturnAnEquivalentDto(){
        JourneyDto expected = new JourneyDto();
        Integer id = 1;
        Integer ambulanceId = 1;
        Integer people = 1;
        expected.setId(id);
        expected.setPeople(people);
        expected.setAmbulanceId(ambulanceId);
        expected.setStatus(Status.WAITING.toString());

        Ambulance ambulance = new Ambulance();
        ambulance.setId(ambulanceId);

        PatientGroup dao = new PatientGroup();
        dao.setId(id);
        dao.setPeople(people);
        dao.setAmbulance(ambulance);
        dao.setStatus(Status.WAITING.toString());

        JourneyDto response = builder.buildDto(dao);

        assertThat(response, is(expected));
    }
}
