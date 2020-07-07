package com.grupoasv.prueba.model.ambulance;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AmbulanceDto{
    private Integer id;
    private Integer totalSeats;
    private String status;
    private int[] patientGroupIds;
}
