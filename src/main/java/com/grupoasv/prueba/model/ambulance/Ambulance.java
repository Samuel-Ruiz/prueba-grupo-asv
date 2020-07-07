package com.grupoasv.prueba.model.ambulance;

import com.grupoasv.prueba.model.patient.PatientGroup;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@EqualsAndHashCode
public class Ambulance{

    @Id
    private Integer id;

    private Integer totalSeats;

    private String status;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PatientGroup> patientGroupList = new ArrayList<>();
}
