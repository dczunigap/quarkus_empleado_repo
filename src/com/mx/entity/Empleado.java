package com.mx.entity;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    public int EmpleadoId;
    public String Nombre;
    public String Apellidos;
    public String Departamento;
    public String Puesto;
    public LocalDate FechaContratacion;
    public Double Salario;
}
