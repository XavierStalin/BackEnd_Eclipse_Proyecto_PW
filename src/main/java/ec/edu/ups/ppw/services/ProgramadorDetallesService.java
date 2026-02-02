package ec.edu.ups.ppw.services;

import java.net.URI;
import java.util.List;

import ec.edu.ups.ppw.business.GestionProgramadorDetalles;
import ec.edu.ups.ppw.model.ProgramadorDetalles;
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

@Path("programador-detalles")
public class ProgramadorDetallesService {

    @Inject
    private GestionProgramadorDetalles gpd;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListaProgramadorDetalles() {
        List<ProgramadorDetalles> listado = gpd.getProgramadorDetalles();
        return Response.ok(listado).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProgramadorDetalles(@PathParam("id") int id) {
        ProgramadorDetalles pd;
        try {
            pd = gpd.getProgramadorDetallesPorId(id);
        } catch (Exception e) {
            Error error = new Error(
                    500,
                    "Error interno del servidor",
                    "Se produjo un error al procesar la solicitud: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(error)
                    .build();
        }

        if (pd == null) {
            Error error = new Error(
                    404,
                    "No encontrado",
                    "Detalles de programador con id " + id + " no encontrados");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(error)
                    .build();
        }

        return Response.ok(pd).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProgramadorDetalles(ProgramadorDetalles programadorDetalles, @Context UriInfo uriInfo) {
        try {
            gpd.createProgramadorDetalles(programadorDetalles);
        } catch (Exception e) {
            Error error = new Error(
                    500,
                    "Error interno del servidor",
                    e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(error)
                    .build();
        }

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(programadorDetalles.getId()))
                .build();

        return Response.created(location)
                .entity(programadorDetalles)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProgramadorDetalles(@PathParam("id") int id, ProgramadorDetalles programadorDetalles) {
        ProgramadorDetalles existente = gpd.getProgramadorDetallesPorId(id);

        if (existente == null) {
            Error error = new Error(
                    404,
                    "No encontrado",
                    "Detalles de programador con id " + id + " no encontrados");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(error)
                    .build();
        }

        try {
            // --- AQUÍ ESTABA EL ERROR: FALTABA ASIGNAR LOS DATOS NUEVOS ---
            
            // 1. Actualizamos el nombre completo (Requisito de tu profesor)
            existente.setNombreCompleto(programadorDetalles.getNombreCompleto());

            // 2. Actualizamos el resto de campos
            existente.setEspecialidad(programadorDetalles.getEspecialidad());
            existente.setBiografia(programadorDetalles.getBiografia());
            existente.setTelefono(programadorDetalles.getTelefono());
            existente.setLinkedinLink(programadorDetalles.getLinkedinLink());
            existente.setGithubLink(programadorDetalles.getGithubLink());
            existente.setFotoPerfilUrl(programadorDetalles.getFotoPerfilUrl());
            
            // 3. Solo actualizamos activo si viene un valor (para evitar nulos accidentales)
            if (programadorDetalles.getActivo() != null) {
                existente.setActivo(programadorDetalles.getActivo());
            }

            // NOTA: Nunca actualizamos el ID ni el Usuario, esos son inmutables aquí.
            // -------------------------------------------------------------

            gpd.updateProgramadorDetalles(existente);
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
    public Response deleteProgramadorDetalles(@PathParam("id") int id) {
        ProgramadorDetalles existente = gpd.getProgramadorDetallesPorId(id);

        if (existente == null) {
            Error error = new Error(
                    404,
                    "No encontrado",
                    "Detalles de programador con id " + id + " no encontrados");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(error)
                    .build();
        }

        try {
            gpd.deleteProgramadorDetalles(id);
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