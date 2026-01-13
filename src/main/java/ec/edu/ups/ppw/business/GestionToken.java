package ec.edu.ups.ppw.business;

import ec.edu.ups.ppw.dao.TokenDAO;
import ec.edu.ups.ppw.model.Token;
import ec.edu.ups.ppw.model.Usuario;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionToken {

    @Inject
    private TokenDAO daoToken;

    // VALIDAR
    public boolean esTokenValido(String tokenStr) {
        Token t = daoToken.getPorTokenString(tokenStr);
        if (t == null) return false;
        if (t.isRevoked() || t.isExpired()) return false;
        return true;
    }
    
    // GUARDAR
    public void crearToken(Usuario usuario, String tokenStr) {
        Token t = new Token();
        t.setUsuario(usuario);
        t.setToken(tokenStr);
        t.setRevoked(false);
        t.setExpired(false);
        //---------------
        daoToken.insert(t);
    }
    
    // REVOCAR
    public void revocarToken(String tokenStr) {
        Token t = daoToken.getPorTokenString(tokenStr);
        if(t != null) {
            t.setRevoked(true);
            daoToken.update(t);
        }
    }
}