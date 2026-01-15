package ec.edu.ups.ppw.services;

import java.util.List;
import ec.edu.ups.ppw.business.GestionProyecto;
import ec.edu.ups.ppw.model.Proyecto;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("proyectos")
public class ProyectoService {

    @Inject
    private GestionProyecto gp;

    @GET
    @Produces("application/json")
    public List<Proyecto> getListaProyectos() {
        return gp.getProyecto();
    }
}
