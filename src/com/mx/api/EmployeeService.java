package com.mx.api;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

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
        return employeeBusiness.getAllEmpleados();
    }

    @GET
    @Path("/{id}")
    public EmployeeEntity getById(@PathParam("id") Long id) {
        return employeeBusiness.getEmpleadoById(id);
    }

    @POST
    public Response create(EmployeeEntity employee) {
        EmployeeEntity nuevoEmpleado = employeeBusiness.createEmpleado(employee);
        return Response.status(Response.Status.CREATED).entity(nuevoEmpleado).build();
    }

    @PATCH
    @Path("/{id}")
    public EmployeeEntity update(@PathParam("id") Long id, EmployeeEntity employee) {
        return employeeBusiness.actualizarEmpleado(id, employee);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        employeeBusiness.deleteEmpleado(id);
        return Response.noContent().build();
    }
}
