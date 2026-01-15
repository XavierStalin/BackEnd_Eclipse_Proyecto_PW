package ec.edu.ups.ppw.business;

import java.util.List;
import ec.edu.ups.ppw.dao.ProgramadorDetallesDAO;
import ec.edu.ups.ppw.model.ProgramadorDetalles;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionProgramadorDetalles {

    @Inject
    private ProgramadorDetallesDAO daoProgramadorDetalles;

    public List<ProgramadorDetalles> getProgramadorDetalles() {
        return daoProgramadorDetalles.getAll();
    }

    //  por ID
    public ProgramadorDetalles getPorId(String id) throws Exception {
        if (id == null || id.length() != 10) {
            throw new Exception("ID incorrecto (Se requiere cédula de 10 dígitos).");
        }
        return daoProgramadorDetalles.read(id); 
    }

    public void createProgramadorDetalles(ProgramadorDetalles pd) throws Exception {

        
        daoProgramadorDetalles.insert(pd);
    }

    public void updateProgramadorDetalles(ProgramadorDetalles pd) throws Exception {
  
        String idToCheck = String.valueOf(pd.getId()); 
        
        if (idToCheck == null || idToCheck.isEmpty()) {
            throw new Exception("Se requiere un ID para actualizar.");
        }
        
       
        if (daoProgramadorDetalles.read(idToCheck) == null) {
            throw new Exception("El detalle del programador con ID " + idToCheck + " no existe.");
        }

        daoProgramadorDetalles.update(pd);
    }

    public void deleteProgramadorDetalles(String id) throws Exception {
        if (id == null || id.length() != 10) {
            throw new Exception("ID inválido.");
        }
        daoProgramadorDetalles.delete(id);
    }
}