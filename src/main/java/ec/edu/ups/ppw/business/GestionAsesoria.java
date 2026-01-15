package ec.edu.ups.ppw.business;

import java.util.List;
import ec.edu.ups.ppw.dao.AsesoriaDAO;
import ec.edu.ups.ppw.model.Asesoria;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionAsesoria {

    @Inject
    private AsesoriaDAO daoAsesoria;

    public List<Asesoria> getAsesorias() {
        return daoAsesoria.getAll();
    }

    public Asesoria getAsesoriaPorId(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("El ID debe ser mayor a cero.");
        }
        return daoAsesoria.read(id);
    }

    public void createAsesoria(Asesoria asesoria) throws Exception {
        
        if (asesoria.getCliente() == null || asesoria.getProgramador() == null) {
            throw new Exception("La asesoría debe tener un cliente y un programador asignados.");
        }
        daoAsesoria.insert(asesoria);
    }

    public void updateAsesoria(Asesoria asesoria) throws Exception {
       
        if (asesoria.getId() == 0) {
            throw new Exception("Se requiere un ID para actualizar.");
        }
        
      
        if (daoAsesoria.read(asesoria.getId()) == null) {
            throw new Exception("La asesoría con ID " + asesoria.getId() + " no existe.");
        }

        daoAsesoria.update(asesoria);
    }

    public void deleteAsesoria(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID inválido.");
        }
        daoAsesoria.delete(id);
    }
}