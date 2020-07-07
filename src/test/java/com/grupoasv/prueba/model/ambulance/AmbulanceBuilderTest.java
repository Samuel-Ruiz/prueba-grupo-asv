package com.grupoasv.prueba.model.ambulance;

import com.grupoasv.prueba.model.common.Status;
import com.grupoasv.prueba.model.interfaces.Builder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
class AmbulanceBuilderTest{

    Builder<Ambulance, AmbulanceDto> builder = new AmbulanceBuilder();

    @Test
    public void buildDaoShouldConsumeAnyDtoAndReturnAnEquivalentDao(){
        Ambulance expected = new Ambulance();
        Integer id = 1;
        Integer seats = 1;
        expected.setId(id);
        expected.setTotalSeats(seats);
        expected.setStatus(Status.WAITING.toString());

        AmbulanceDto dto = new AmbulanceDto();
        dto.setId(id);
        dto.setTotalSeats(seats);
        Ambulance response = builder.buildDao(dto);

        assertThat(response, is(expected));
    }

    @Test
    public void buildDtoShouldConsumeAnyDaoAndReturnAnEquivalentDto(){
        AmbulanceDto expected = new AmbulanceDto();
        Integer id = 1;
        Integer seats = 1;
        expected.setId(id);
        expected.setTotalSeats(seats);
        expected.setStatus(Status.WAITING.toString());

        Ambulance dao = new Ambulance();
        dao.setId(id);
        dao.setTotalSeats(seats);
        dao.setStatus(Status.WAITING.toString());

        AmbulanceDto response = builder.buildDto(dao);

        assertEquals(response.getId(), expected.getId());
        assertEquals(response.getTotalSeats(), expected.getTotalSeats());
        assertEquals(response.getStatus(), expected.getStatus());
        assertThat(response.getPatientGroupIds(), is(new int[0]));
    }

}
