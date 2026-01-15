package ec.edu.ups.ppw.services;

import java.net.URI;
import java.util.List;

import ec.edu.ups.ppw.business.GestionAsesoria;
import ec.edu.ups.ppw.model.Asesoria;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("asesorias") 
public class AsesoriaService {

    @Inject
    private GestionAsesoria ga;

    // LEER TODAS
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListaAsesorias() {
        List<Asesoria> list = ga.getAsesorias();
        return Response.ok(list).build();
    }

    // LEER UNA
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAsesoria(@PathParam("id") int id) {
        try {
            Asesoria ase = ga.getAsesoriaPorId(id);
            
            if (ase != null) {
                return Response.ok(ase).build();
            } else {
                Error error = new Error(404, "No encontrado", "Asesoría con ID " + id + " no encontrada");
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
    public Response crearAsesoria(Asesoria asesoria, @Context UriInfo uriInfo) {
        try {
            ga.createAsesoria(asesoria);
            
            // Construye la URL del recurso: http://.../asesorias/5
            URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(asesoria.getId())).build();
            
            return Response.created(location).entity(asesoria).build();
            
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
    public Response actualizarAsesoria(@PathParam("id") int id, Asesoria asesoria) {
        try {
            // Seguridad: ID URL vs ID Body 
            if (id != asesoria.getId()) {
                Error error = new Error(400, "Bad Request", "El ID de la URL no coincide con el ID del cuerpo");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            ga.updateAsesoria(asesoria);
            return Response.ok(asesoria).build();
            
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
    public Response borrarAsesoria(@PathParam("id") int id) {
        try {
            // Validar existencia
            Asesoria existente = ga.getAsesoriaPorId(id);
            if (existente == null) {
                Error error = new Error(404, "No encontrado", "No se puede eliminar, la asesoría no existe");
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }
            
            ga.deleteAsesoria(id);
            return Response.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            Error error = new Error(500, "Error Interno", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
}