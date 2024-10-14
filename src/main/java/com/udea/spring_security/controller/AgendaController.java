package com.udea.spring_security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udea.spring_security.dto.agenda.AgendaDtoRequest;
import com.udea.spring_security.persistence.entity.AgendaEntity;
import com.udea.spring_security.service.agenda.AgendaService;
import com.udea.spring_security.service.agenda.AgendaFilterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/agenda")
@PreAuthorize("denyAll()")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private AgendaFilterService agendaFilterService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AgendaEntity> save(@RequestBody @Valid AgendaDtoRequest agendaDtoRequest) {
        return new ResponseEntity<>(agendaService.create(agendaDtoRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AgendaEntity> update(@RequestBody @Valid AgendaDtoRequest agendaDtoRequest,
            @PathVariable Long id) {
        return new ResponseEntity<>(agendaService.update(agendaDtoRequest, id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AgendaEntity>> findAll() {
        return new ResponseEntity<>(agendaService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        agendaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<AgendaEntity> findByTechnician(@RequestParam String technician) {
        return new ResponseEntity<>(agendaFilterService.findByTechnician(technician).get(), HttpStatus.OK);
    }

}
