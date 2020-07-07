package com.grupoasv.prueba.repositories;

import com.grupoasv.prueba.model.patient.PatientGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientGroupRepository extends JpaRepository<PatientGroup, Integer>{
    List<PatientGroup> findAllByStatus(String toString);
    boolean existsById(Integer id);
}
