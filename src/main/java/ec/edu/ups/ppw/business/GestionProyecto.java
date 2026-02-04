package ec.edu.ups.ppw.business;

import java.util.List;
import ec.edu.ups.ppw.dao.ProyectoDAO;
import ec.edu.ups.ppw.model.Proyecto;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionProyecto {

    @Inject
    private ProyectoDAO daoProyecto;

    // CREATE
    public void createProyecto(Proyecto proyecto) {
        // Aquí puedes agregar validaciones de negocio antes de insertar
        daoProyecto.insert(proyecto);
    }

    // READ (Todos)
    // He renombrado este a getProyectos para ser consistente con Usuario, 
    // pero si prefieres getProyecto (en singular), solo cámbialo.
    public List<Proyecto> getProyectos() {
        return daoProyecto.getAll();
    }

    // READ (Uno)
    public Proyecto getProyectoPorId(int id) {
        return daoProyecto.read(id);
    }

    // UPDATE
    public void updateProyecto(Proyecto proyecto) {
        daoProyecto.update(proyecto);
    }

    // DELETE
    public void deleteProyecto(int id) {
        daoProyecto.delete(id);
    }
    public List<Proyecto> getProyectosPorProgramador(int programadorId) {
        return daoProyecto.findByProgramadorId(programadorId);
    }
}