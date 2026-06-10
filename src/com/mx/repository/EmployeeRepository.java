package com.mx.repository;

import com.mx.entity.EmployeeEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<EmployeeEntity> {

}
