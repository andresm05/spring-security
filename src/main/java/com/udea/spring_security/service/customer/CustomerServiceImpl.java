package com.udea.spring_security.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udea.spring_security.persistence.entity.CustomerEntity;
import com.udea.spring_security.persistence.repository.CustomerRepository;

@Service
@Transactional
public class CustomerServiceImpl  implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerEntity create(CustomerEntity customerEntity) {

        return customerRepository.save(customerEntity); 
    }
    
}
