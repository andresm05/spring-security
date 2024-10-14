package com.udea.spring_security.service.agenda;

import java.util.Optional;

import com.udea.spring_security.persistence.entity.AgendaEntity;

public interface AgendaFilterService {

    Optional<AgendaEntity> findByTechnician(String technician);
    
}
