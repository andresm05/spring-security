package com.udea.spring_security.service.customer;

import com.udea.spring_security.persistence.entity.CustomerEntity;

public interface CustomerService {

    public CustomerEntity create(CustomerEntity customerEntity);
    
}
