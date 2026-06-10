package com.mx.business;

import java.util.List;

import com.mx.entity.EmployeeEntity;
import com.mx.repository.EmployeeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EmployeeBusiness {

    @Inject
    private EmployeeRepository employeeRepository;

    /**
     * Obtiene todos los empleados.
     * @return Lista de empleados.
     */
    public List<EmployeeEntity> getAllEmpleados() {
        return employeeRepository.listAll();
    }

    /**
     * Obtiene un empleado por su ID.
     * @param id ID del empleado.
     * @return Empleado encontrado o null si no se encuentra.
     */
    public EmployeeEntity getEmpleadoById(Long id) {
        return employeeRepository.find("EmployeeId", id).firstResult();
    }

    /**
     * Crea un nuevo empleado.
     * @param empleado Empleado a crear.
     * @return Empleado creado.
     */
    @Transactional
    public EmployeeEntity createEmpleado(EmployeeEntity empleado) {
        
        if (employeeRepository.find("EmployeeId", empleado.EmployeeId).firstResult() != null) {
            throw new RuntimeException("Ya existe un empleado con el ID: " + empleado.EmployeeId);
        }

        if (empleado.Name == null || empleado.LastName == null || empleado.Department == null || 
            empleado.Position == null || empleado.HireDate == null || empleado.Salary == null) {
            throw new RuntimeException("Todos los campos son obligatorios para crear un empleado.");
        }

        employeeRepository.persist(empleado);
        return empleado;
    }

    /**
     * Actualiza un empleado existente.
     * @param id ID del empleado a actualizar.
     * @param empleado Empleado con los nuevos datos.
     * @return Empleado actualizado o null si no se encuentra.
     */
    @Transactional
    public EmployeeEntity actualizarEmpleado(Long id, EmployeeEntity empleado) {
        EmployeeEntity existEmployee = employeeRepository.findById(id);

        if (existEmployee != null) {
            
            existEmployee.EmployeeId = empleado.EmployeeId;
            existEmployee.Name = empleado.Name;
            existEmployee.LastName = empleado.LastName;
            existEmployee.Department = empleado.Department;
            existEmployee.Position = empleado.Position;
            existEmployee.HireDate = empleado.HireDate;
            existEmployee.Salary = empleado.Salary;
            return existEmployee;
        }

        return null;
    }

    /**
     * Elimina un empleado existente.
     * @param id ID del empleado a eliminar.
     */
    @Transactional
    public void deleteEmpleado(Long id) {
        EmployeeEntity exists = employeeRepository.findById(id);
        try {
            if (exists != null) {
                employeeRepository.delete(exists);
            }
        } catch (Exception e) {
            // Manejar la excepción según sea necesario
            e.printStackTrace();
        }
    }
}
