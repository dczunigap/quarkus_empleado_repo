package com.mx.entity;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "empleados")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    public int EmployeeId;
    public String Name;
    public String LastName;
    public String Department;
    public String Position;
    public LocalDate HireDate;
    public Double Salary;
}
