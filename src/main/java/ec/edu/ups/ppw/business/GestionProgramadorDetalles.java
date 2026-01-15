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

    // Buscamos por ID (que es la Cédula del programador)
    public ProgramadorDetalles getPorId(String id) throws Exception {
        if (id == null || id.length() != 10) {
            throw new Exception("ID incorrecto (Se requiere cédula de 10 dígitos).");
        }
        return daoProgramadorDetalles.read(id); // Asegúrate que tu DAO reciba String
    }

    public void createProgramadorDetalles(ProgramadorDetalles pd) throws Exception {
        // Validación: Debe tener un ID asociado (la cédula del usuario)
        // En relaciones @MapsId, el ID suele venir seteaado o se toma del usuario
        /* Nota: Dependiendo de tu modelo, a veces el ID viene dentro del objeto 'usuario' asociado. 
           Asumiremos aquí que el objeto pd ya trae su ID o su Usuario. */
        
        daoProgramadorDetalles.insert(pd);
    }

    public void updateProgramadorDetalles(ProgramadorDetalles pd) throws Exception {
        // Validamos ID
        /* Nota: Si tu clase ProgramadorDetalles usa 'String id' o 'Usuario usuario' como ID, 
           ajusta este getter: pd.getId() o pd.getUsuario().getCedula() */
        String idToCheck = String.valueOf(pd.getId()); // Asumiendo que getId devuelve el String cédula
        
        if (idToCheck == null || idToCheck.isEmpty()) {
            throw new Exception("Se requiere un ID para actualizar.");
        }
        
        // Verificar existencia
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