package ec.edu.ups.ppw.services;

import java.net.URI;
import java.util.List;

import ec.edu.ups.ppw.business.GestionProgramadorDetalles;
import ec.edu.ups.ppw.model.ProgramadorDetalles;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("programador-detalles")
public class ProgramadorDetallesService {

    @Inject
    private GestionProgramadorDetalles gpd;

    // LEER TODOS
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListaProgramadorDetalles() {
        List<ProgramadorDetalles> list = gpd.getProgramadorDetalles();
        return Response.ok(list).build();
    }

    // LEER UNO (Por Cédula)
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProgramadorDetalles(@PathParam("id") String id) {
        try {
            ProgramadorDetalles pd = gpd.getPorId(id);
            
            if (pd != null) {
                return Response.ok(pd).build();
            } else {
                Error error = new Error(404, "No encontrado", "Detalles para el ID " + id + " no encontrados");
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Error error = new Error(500, "Error Interno", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    // CREAR
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearProgramadorDetalles(ProgramadorDetalles pd, @Context UriInfo uriInfo) {
        try {
            gpd.createProgramadorDetalles(pd);
            
            // Construye la URL usando el ID (Cédula)
            // Asegúrate que pd.getId() devuelva la cédula (String)
            URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(pd.getId())).build();
            
            return Response.created(location).entity(pd).build();
            
        } catch (Exception e) {
            e.printStackTrace();
            Error error = new Error(500, "Error Interno", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    // ACTUALIZAR
    @PUT
    @Path("update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarProgramadorDetalles(@PathParam("id") String id, ProgramadorDetalles pd) {
        try {
            // Seguridad: Validar que el ID de la URL coincida con el ID del cuerpo
            // Asumiendo que pd.getId() devuelve String
            if (!id.equals(String.valueOf(pd.getId()))) {
                Error error = new Error(400, "Bad Request", "El ID de la URL no coincide con el ID del cuerpo");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            gpd.updateProgramadorDetalles(pd);
            return Response.ok(pd).build();
            
        } catch (Exception e) {
            e.printStackTrace();
            Error error = new Error(500, "Error Interno", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    // ELIMINAR
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response borrarProgramadorDetalles(@PathParam("id") String id) {
        try {
            // Validar existencia
            ProgramadorDetalles existente = gpd.getPorId(id);
            if (existente == null) {
                Error error = new Error(404, "No encontrado", "No se puede eliminar, el registro no existe");
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }
            
            gpd.deleteProgramadorDetalles(id);
            return Response.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            Error error = new Error(500, "Error Interno", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
}