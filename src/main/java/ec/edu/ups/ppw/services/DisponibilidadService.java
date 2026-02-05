package ec.edu.ups.ppw.services;

import java.net.URI;
import java.util.List;
import ec.edu.ups.ppw.business.GestionDisponibilidad;
import ec.edu.ups.ppw.model.Disponibilidad;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@PermitAll
@Path("disponibilidades")
public class DisponibilidadService {

    @Inject
    private GestionDisponibilidad gd;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListaDisponibilidades() {
        return Response.ok(gd.getDisponibilidades()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisponibilidad(@PathParam("id") int id) {
        Disponibilidad d = gd.getDisponibilidadPorId(id);
        if (d == null) {
            Error error = new Error(404, "No encontrado", "Disponibilidad no encontrada");
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
        return Response.ok(d).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDisponibilidad(Disponibilidad disponibilidad, @Context UriInfo uriInfo) {
        try {
            // Nota: Asegúrate de que el objeto 'disponibilidad' venga con el 'programador' seteado
            // o maneja la lógica de asignación aquí si envías solo el ID.
            gd.createDisponibilidad(disponibilidad);
            URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(disponibilidad.getId())).build();
            return Response.created(location).entity(disponibilidad).build();
        } catch (Exception e) {
            Error error = new Error(500, "Error interno", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDisponibilidad(@PathParam("id") int id, Disponibilidad disponibilidad) {
        Disponibilidad existente = gd.getDisponibilidadPorId(id);
        
        if (existente == null) {
            Error error = new Error(404, "No encontrado", "Disponibilidad no encontrada");
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
        
        try {
            // --- CORRECCIÓN CRÍTICA: Asignar los nuevos valores ---
            existente.setDiaSemana(disponibilidad.getDiaSemana());
            existente.setHoraInicio(disponibilidad.getHoraInicio());
            existente.setHoraFin(disponibilidad.getHoraFin());
            existente.setModalidad(disponibilidad.getModalidad());
            // No tocamos ID ni Programador
            // -----------------------------------------------------
            
            gd.updateDisponibilidad(existente);
            return Response.ok(existente).build();
        } catch (Exception e) {
            Error error = new Error(500, "Error interno", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON) // Importante para devolver JSON si hay error
    public Response deleteDisponibilidad(@PathParam("id") int id) {
        try {
            gd.deleteDisponibilidad(id);
            return Response.ok().build();
        } catch (Exception e) {
            Error error = new Error(404, "Error al eliminar", "No se encontró la disponibilidad o hubo un error: " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }
}
