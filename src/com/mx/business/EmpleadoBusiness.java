package com.mx.business;

import java.util.List;

import com.mx.entity.Empleado;
import com.mx.repository.EmpleadoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EmpleadoBusiness {

    @Inject
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.listAll();
    }
    
    public Empleado getEmpleadoById(Long id) {
        return empleadoRepository.find("EmpleadoId", id).firstResult();
    }

    @Transactional
    public Empleado createEmpleado(Empleado empleado) {
        empleadoRepository.persist(empleado);
        return empleado;
    }

    @Transactional
    public Empleado actualizarEmpleado(Long id, Empleado empleado) {
        Empleado empleadoExistente = empleadoRepository.findById(id);

        if (empleadoExistente != null) {
            empleadoExistente.EmpleadoId = empleado.EmpleadoId;
            empleadoExistente.Nombre = empleado.Nombre;
            empleadoExistente.Apellidos = empleado.Apellidos;
            empleadoExistente.Departamento = empleado.Departamento;
            empleadoExistente.Puesto = empleado.Puesto;
            empleadoExistente.FechaContratacion = empleado.FechaContratacion;
            empleadoExistente.Salario = empleado.Salario;
            return empleadoExistente;
        }

        return null;
    }

    @Transactional
    public void deleteEmpleado(Long id) {
        Empleado empleadoExistente = empleadoRepository.findById(id);
        empleadoRepository.delete(empleadoExistente);
    }
}
