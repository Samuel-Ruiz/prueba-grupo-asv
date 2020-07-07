package com.grupoasv.prueba.repositories;

import com.grupoasv.prueba.model.ambulance.Ambulance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmbulanceRepository extends JpaRepository<Ambulance, Integer>{

    List<Ambulance> findAllByStatus(final String status);
}
