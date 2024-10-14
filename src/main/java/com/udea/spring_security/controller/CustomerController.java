package com.udea.spring_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udea.spring_security.persistence.entity.CustomerEntity;
import com.udea.spring_security.service.customer.CustomerService;

@RestController
@RequestMapping("/customer")
@PreAuthorize("denyAll()")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<CustomerEntity> create( @RequestBody CustomerEntity customerEntity) {
        return new ResponseEntity<>(customerService.create(customerEntity), HttpStatus.CREATED);
    }

}
