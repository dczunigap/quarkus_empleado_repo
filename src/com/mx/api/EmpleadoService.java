package com.mx.api;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

import com.mx.business.EmpleadoBusiness;
import com.mx.entity.Empleado;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("/empleados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmpleadoService {

    @Inject
    EmpleadoBusiness empleadoBusiness;

    @GET
    public List<Empleado> getAll() {
        return empleadoBusiness.getAllEmpleados();
    }

    @GET
    @Path("/{id}")
    public Empleado getById(@PathParam("id") Long id) {
        return empleadoBusiness.getEmpleadoById(id);
    }

    @POST
    public Response create(Empleado empleado) {
        Empleado nuevoEmpleado = empleadoBusiness.createEmpleado(empleado);
        return Response.status(Response.Status.CREATED).entity(nuevoEmpleado).build();
    }

    @PATCH
    @Path("/{id}")
    public Empleado update(@PathParam("id") Long id, Empleado empleado) {
        return empleadoBusiness.actualizarEmpleado(id, empleado);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        empleadoBusiness.deleteEmpleado(id);
        return Response.noContent().build();
    }
}
