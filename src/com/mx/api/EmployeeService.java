package com.mx.api;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import com.mx.business.EmployeeBusiness;
import com.mx.entity.EmployeeEntity;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("/api/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeService {

    @Inject
    EmployeeBusiness employeeBusiness;

    @GET
    public List<EmployeeEntity> getAll() {
        return employeeBusiness.getAllEmployees();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get employee by EmployeeID", description = "Find employee using Employee ID.")
    @APIResponse(responseCode = "200", description = "Employee found successfully")
    @APIResponse(responseCode = "404", description = "The employee with the requested ID does not exist")
    public EmployeeEntity getById(@PathParam("id") Long id) {
        return employeeBusiness.getEmployeeById(id);
    }

    @POST
    public Response create(EmployeeEntity employee) {
        EmployeeEntity newEmployee = employeeBusiness.createEmployee(employee);
        return Response.status(Response.Status.CREATED).entity(newEmployee).build();
    }

    @PATCH
    @Path("/{id}")
    @Operation(summary = "Update employee", description = "Updates an existing employee's information.")
    @APIResponse(responseCode = "200", description = "Employee updated successfully")
    @APIResponse(responseCode = "404", description = "The employee with the requested ID does not exist")
    public EmployeeEntity update(@PathParam("id") Long id, EmployeeEntity employee) {
        return employeeBusiness.actualizarEmpleado(id, employee);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete employee", description = "Deletes an existing employee.")
    @APIResponse(responseCode = "204", description = "Employee deleted successfully")
    @APIResponse(responseCode = "404", description = "The employee with the requested ID does not exist")
    public Response delete(@PathParam("id") Long id) {
        employeeBusiness.deleteEmployee(id);
        return Response.noContent().build();
    }
}
