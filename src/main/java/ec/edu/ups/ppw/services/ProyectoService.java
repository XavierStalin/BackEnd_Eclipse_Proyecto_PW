package ec.edu.ups.ppw.services;

import java.net.URI;
import java.util.List;

import ec.edu.ups.ppw.business.GestionProyecto;
import ec.edu.ups.ppw.model.Proyecto;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("proyectos") // He cambiado 'proyecto' a plural para seguir el estándar de 'usuarios'
public class ProyectoService {

    @Inject
    private GestionProyecto gp;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListaProyectos() {
        List<Proyecto> listado = gp.getProyectos();
        return Response.ok(listado).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProyecto(@PathParam("id") int id) {
        Proyecto p;
        try {
            // Asumo que existe este método en GestionProyecto
            p = gp.getProyectoPorId(id); 
        } catch (Exception e) {
            Error error = new Error(
                    500,
                    "Error interno del servidor",
                    "Se produjo un error al procesar la solicitud: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(error)
                    .build();
        }

        if (p == null) {
            Error error = new Error(
                    404,
                    "No encontrado",
                    "Proyecto con id " + id + " no encontrado");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(error)
                    .build();
        }

        return Response.ok(p).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProyecto(Proyecto proyecto, @Context UriInfo uriInfo) {
        try {
            gp.createProyecto(proyecto);
        } catch (Exception e) {
            Error error = new Error(
                    500,
                    "Error interno del servidor",
                    e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(error)
                    .build();
        }

        // Asumo que tu entidad Proyecto tiene un método getId()
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(proyecto.getId()))
                .build();

        return Response.created(location)
                .entity(proyecto)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProyecto(@PathParam("id") int id, Proyecto proyecto) {
        Proyecto existente = gp.getProyectoPorId(id);

        if (existente == null) {
            Error error = new Error(
                    404,
                    "No encontrado",
                    "Proyecto con id " + id + " no encontrado");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(error)
                    .build();
        }

        try {
            // --- AQUÍ ESTABA EL FALTANTE: Actualizar los campos ---
            
            existente.setNombre(proyecto.getNombre());
            existente.setDescripcion(proyecto.getDescripcion());
            existente.setCategoria(proyecto.getCategoria());
            existente.setTecnologiasUsadas(proyecto.getTecnologiasUsadas());
            
            existente.setUrlRepositorio(proyecto.getUrlRepositorio());
            existente.setUrlDespliegue(proyecto.getUrlDespliegue());
            existente.setUrlImagenPreview(proyecto.getUrlImagenPreview());
            
            
            if (proyecto.getActivo() != null) {
                existente.setActivo(proyecto.getActivo());
            }
            
            gp.updateProyecto(existente);
        } catch (Exception e) {
            Error error = new Error(
                    500,
                    "Error interno del servidor",
                    e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(error)
                    .build();
        }

        return Response.ok(existente).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProyecto(@PathParam("id") int id) {
        Proyecto existente = gp.getProyectoPorId(id);

        if (existente == null) {
            Error error = new Error(
                    404,
                    "No encontrado",
                    "Proyecto con id " + id + " no encontrado");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(error)
                    .build();
        }

        try {
            gp.deleteProyecto(id);
        } catch (Exception e) {
            Error error = new Error(
                    500,
                    "Error interno del servidor",
                    e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(error)
                    .build();
        }

        return Response.ok().build();
    }
}