package ec.edu.ups.ppw.services;

import java.net.URI;
import java.util.List;

import ec.edu.ups.ppw.business.GestionUsuario;
import ec.edu.ups.ppw.model.Usuario;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("usuarios")
public class UsuarioService {

    @Inject
    private GestionUsuario gu;

    // LEER TODOS
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListaUsuarios() {
        List<Usuario> list = gu.getUsuarios();
        return Response.ok(list).build();
    }

    // LEER UNO
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuario(@PathParam("id") String cedula) {
        try {
            Usuario usu = gu.getUsuarioPorId(cedula);
            
            if (usu != null) {
                return Response.ok(usu).build();
            } else {
                Error error = new Error(404, "No encontrado", "Usuario con ID " + cedula + " no encontrado");
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Error error = new Error(500, "Error Interno", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    // CREAR (Con Location Header)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearUsuario(Usuario usuario, @Context UriInfo uriInfo) {
        try {
            gu.createUsuario(usuario);
            
            
            URI location = uriInfo.getAbsolutePathBuilder().path(usuario.getCedula()).build();
            
            return Response.created(location).entity(usuario).build();
            
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
    public Response actualizarUsuario(@PathParam("id") String id, Usuario usuario) {
        try {
            // Seguridad: ID URL vs ID Body
            if (!id.equals(usuario.getCedula())) {
                Error error = new Error(400, "Bad Request", "El ID de la URL no coincide con la cédula del cuerpo");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            gu.updateUsuario(usuario);
            return Response.ok(usuario).build();
            
        } catch (Exception e) {
            e.printStackTrace();
            Error error = new Error(500, "Error Interno", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response borrarUsuario(@PathParam("id") String id) {
        try {
            // Validar primero para dar un buen 404 si no existe
            Usuario existente = gu.getUsuarioPorId(id);
            if (existente == null) {
                Error error = new Error(404, "No encontrado", "No se puede eliminar, el usuario no existe");
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }
            
            gu.deleteUsuario(id);
            return Response.noContent().build(); // 204 No Content  para delete exitoso
        } catch (Exception e) {
            e.printStackTrace();
            Error error = new Error(500, "Error Interno", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
}