package ec.edu.ups.ppw.business;

import java.util.List;
import ec.edu.ups.ppw.dao.UsuarioDAO;
import ec.edu.ups.ppw.model.Usuario;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionUsuario {

    @Inject
    private UsuarioDAO daoUsuario;

    public List<Usuario> getUsuarios() {
        return daoUsuario.getAll();
    }

    public Usuario getUsuarioPorId(String cedula) throws Exception {
        if (cedula == null || cedula.length() != 10) {
            throw new Exception("Formato de cédula incorrecto (Debe tener 10 dígitos)");
        }
        return daoUsuario.read(cedula);
    }

    public void createUsuario(Usuario usuario) throws Exception {
        if (usuario.getCedula().length() != 10) {
            throw new Exception("Formato de cédula incorrecto");
        }
        daoUsuario.insert(usuario);
    }

    public void updateUsuario(Usuario usuario) throws Exception {
        // Validaciones igual que en tu referencia
        if (usuario.getCedula() == null || usuario.getCedula().length() != 10) {
            throw new Exception("Formato de cédula incorrecto para actualizar");
        }
        
        // Verificamos existencia
        if (daoUsuario.read(usuario.getCedula()) == null) {
            throw new Exception("El usuario con cédula " + usuario.getCedula() + " no existe");
        }

        daoUsuario.update(usuario);
    }
    
    public void deleteUsuario(String cedula) throws Exception {
        if (cedula == null || cedula.length() != 10) {
            throw new Exception("Formato de cédula incorrecto");
        }
        daoUsuario.delete(cedula);
    }
}