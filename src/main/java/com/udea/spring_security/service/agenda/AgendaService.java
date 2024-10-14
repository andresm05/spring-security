package com.udea.spring_security.service.agenda;

import java.util.List;

import com.udea.spring_security.dto.agenda.AgendaDtoRequest;
import com.udea.spring_security.persistence.entity.AgendaEntity;

public interface AgendaService {

    AgendaEntity create(AgendaDtoRequest agendaDtoRequest);

    AgendaEntity update(AgendaDtoRequest agendaDtoRequest, Long id);

    List<AgendaEntity> findAll();

    void delete(Long id);
    
}
