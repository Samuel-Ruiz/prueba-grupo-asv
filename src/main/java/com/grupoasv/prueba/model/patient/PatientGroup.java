package com.grupoasv.prueba.model.patient;

import com.grupoasv.prueba.model.ambulance.Ambulance;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "ambulance")
@Entity
@EqualsAndHashCode
public class PatientGroup{

    @Id
    private Integer id;

    private Integer people;

    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    private Ambulance ambulance;
}
