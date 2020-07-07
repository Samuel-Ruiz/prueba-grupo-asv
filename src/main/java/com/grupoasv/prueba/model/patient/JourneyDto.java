package com.grupoasv.prueba.model.patient;

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
public class JourneyDto{
    private Integer id;
    private Integer people;
    private String status;
    private Integer ambulanceId;
}
