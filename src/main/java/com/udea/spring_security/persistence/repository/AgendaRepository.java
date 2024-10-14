package com.udea.spring_security.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udea.spring_security.persistence.entity.AgendaEntity;

@Repository
public interface AgendaRepository extends JpaRepository<AgendaEntity, Long> {

    //search and agenda by Techinician Name
    Optional<AgendaEntity> findByTechnicianName(String technicianName);

    
}
