package com.mx.business;

import java.util.List;

import com.mx.entity.EmployeeEntity;
import com.mx.repository.EmployeeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;

@ApplicationScoped
public class EmployeeBusiness {

    @Inject
    private EmployeeRepository employeeRepository;

    /**
     * Get all employees.
     * @return List of employees.
     */
    public List<EmployeeEntity> getAllEmployees() {
        return employeeRepository.listAll();
    }

    /**
     * Get an employee by their ID.
     * @param id ID of the employee.
     * @return Employee found or null if not found.
     */
    public EmployeeEntity getEmployeeById(Long id) {
        if (id == null) {
            throw new BadRequestException("ID not be null.");
        }
        return employeeRepository.find("EmployeeId", id)
        .firstResult();
    }

    /**
     * Create a new employee.
     * @param employee Employee to create.
     * @return Created employee.
     */
    @Transactional
    public EmployeeEntity createEmployee(EmployeeEntity employee) {
        
        if (employeeRepository.find("EmployeeId", employee.EmployeeId).firstResult() != null) {
            throw new RuntimeException("An employee with ID " + employee.EmployeeId + " already exists.");
        }

        if (employee.Name == null || employee.LastName == null || employee.Department == null || 
            employee.Position == null || employee.HireDate == null || employee.Salary == null) {
            throw new RuntimeException("All fields are required to create an employee.");
        }

        employeeRepository.persist(employee);
        return employee;
    }

    /**
     * Update an existing employee.
     * @param id ID of the employee to update.
     * @param employee Employee with the new data.
     * @return Updated employee or null if not found.
     */
    @Transactional
    public EmployeeEntity actualizarEmpleado(Long id, EmployeeEntity employee) {
        EmployeeEntity existEmployee = employeeRepository.findById(id);

        if (existEmployee != null) {
            
            existEmployee.EmployeeId = employee.EmployeeId;
            existEmployee.Name = employee.Name;
            existEmployee.LastName = employee.LastName;
            existEmployee.Department = employee.Department;
            existEmployee.Position = employee.Position;
            existEmployee.HireDate = employee.HireDate;
            existEmployee.Salary = employee.Salary;
            return existEmployee;
        }

        return null;
    }

    /**
     * Delete an existing employee.
     * @param id ID of the employee to delete.
     */
    @Transactional
    public void deleteEmployee(Long id) {
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
