package com.udea.spring_security.service.agenda;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udea.spring_security.dto.agenda.AgendaDtoRequest;
import com.udea.spring_security.persistence.entity.AgendaEntity;
import com.udea.spring_security.persistence.entity.CustomerEntity;
import com.udea.spring_security.persistence.entity.ServiceTypeEntity;
import com.udea.spring_security.persistence.repository.AgendaRepository;
import com.udea.spring_security.persistence.repository.CustomerRepository;
import com.udea.spring_security.persistence.repository.ServiceTypeRepository;

@Service
@Transactional
public class AgendaServiceImpl implements AgendaService {

        @Autowired
        private AgendaRepository agendaRepository;

        @Autowired
        private CustomerRepository customerRepository;

        @Autowired
        private ServiceTypeRepository serviceTypeRepository;

        @Override
        public AgendaEntity create(AgendaDtoRequest agendaDtoRequest) {

                // Verify that customer exists
                CustomerEntity customer = customerRepository.findById(agendaDtoRequest.getCustomerId())
                                .orElseThrow(() -> new RuntimeException("Customer not found"));

                // Verify that service type exists
                ServiceTypeEntity seriveType = serviceTypeRepository.findById(agendaDtoRequest.getServiceTypeId())
                                .orElseThrow(() -> new RuntimeException("Service type not found"));

                AgendaEntity agendaEntity = AgendaEntity.builder()
                                .serviceNumber(agendaDtoRequest.getServiceNumber())
                                .serviceName(agendaDtoRequest.getServiceName())
                                .serviceDate(agendaDtoRequest.getServiceDate())
                                .serviceHour(agendaDtoRequest.getServiceHour())
                                .technicianName(agendaDtoRequest.getTechnicianName())
                                .adviserName(agendaDtoRequest.getAdviserName())
                                .status(agendaDtoRequest.getStatus())
                                .realState(agendaDtoRequest.getRealState())
                                .observations(agendaDtoRequest.getObservations())
                                .price(agendaDtoRequest.getPrice())
                                .serviceType(seriveType)
                                .customer(customer)
                                .build();

                return agendaRepository.save(agendaEntity);
        }

        @Override
        public AgendaEntity update(AgendaDtoRequest agendaDtoRequest, Long id) {

                // verify that the agenda exists
                AgendaEntity agendaEntity = agendaRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Agenda not found"));

                // Verify that customer exists
                CustomerEntity customer = customerRepository.findById(agendaDtoRequest.getCustomerId())
                                .orElseThrow(() -> new RuntimeException("Customer not found"));

                // Verify that service type exists
                ServiceTypeEntity seriveType = serviceTypeRepository.findById(agendaDtoRequest.getServiceTypeId())
                                .orElseThrow(() -> new RuntimeException("Service type not found"));

                agendaEntity.setServiceNumber(agendaDtoRequest.getServiceNumber());
                agendaEntity.setServiceName(agendaDtoRequest.getServiceName());
                agendaEntity.setServiceDate(agendaDtoRequest.getServiceDate());
                agendaEntity.setServiceHour(agendaDtoRequest.getServiceHour());
                agendaEntity.setTechnicianName(agendaDtoRequest.getTechnicianName());
                agendaEntity.setAdviserName(agendaDtoRequest.getAdviserName());
                agendaEntity.setStatus(agendaDtoRequest.getStatus());
                agendaEntity.setRealState(agendaDtoRequest.getRealState());
                agendaEntity.setObservations(agendaDtoRequest.getObservations());
                agendaEntity.setPrice(agendaDtoRequest.getPrice());
                agendaEntity.setServiceType(seriveType);
                agendaEntity.setCustomer(customer);

                return agendaRepository.save(agendaEntity);
        }

        @Override
        public List<AgendaEntity> findAll() {

                return agendaRepository.findAll();
        }

        @Override
        public void delete(Long id) {

                // verify that the agenda exists
                AgendaEntity agendaEntity = agendaRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Agenda not found"));

                agendaRepository.delete(agendaEntity);
        }

}
