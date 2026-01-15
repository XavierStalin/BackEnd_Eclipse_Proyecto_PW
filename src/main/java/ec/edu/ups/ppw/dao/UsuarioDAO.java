package ec.edu.ups.ppw.dao;

import ec.edu.ups.ppw.model.Usuario;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;

@Stateless
public class UsuarioDAO {

    @PersistenceContext
    private EntityManager em;

    public void insert(Usuario usuario) {
        em.persist(usuario);
    }

    public void update(Usuario usuario) {
        em.merge(usuario);
    }

    public Usuario read(String id) {
        return em.find(Usuario.class, id);
    }

    public void delete(String id) {
        Usuario usuario = em.find(Usuario.class, id);
        if (usuario != null) {
            em.remove(usuario);
        }
    }

    public List<Usuario> getAll() {
        String jpql = "SELECT u FROM Usuario u";
        Query q = em.createQuery(jpql, Usuario.class);
        return q.getResultList();
    }

    // Método extra: Necesario para el Login
    public Usuario getUsuarioPorEmail(String email) {
        String jpql = "SELECT u FROM Usuario u WHERE u.email = :email";
        Query q = em.createQuery(jpql, Usuario.class);
        q.setParameter("email", email);
        List<Usuario> result = q.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}