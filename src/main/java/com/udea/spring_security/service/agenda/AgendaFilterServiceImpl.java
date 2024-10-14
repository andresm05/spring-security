package com.udea.spring_security.service.agenda;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udea.spring_security.persistence.entity.AgendaEntity;
import com.udea.spring_security.persistence.repository.AgendaRepository;

@Service
@Transactional
public class AgendaFilterServiceImpl implements AgendaFilterService {

    @Autowired
    private AgendaRepository agendaRepository;

    @Override
    public Optional<AgendaEntity> findByTechnician(String technician) {

        return agendaRepository.findByTechnicianName(technician);

    }

}