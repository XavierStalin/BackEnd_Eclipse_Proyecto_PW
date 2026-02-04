package ec.edu.ups.ppw.dao;

import ec.edu.ups.ppw.model.Proyecto;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;

@Stateless
public class ProyectoDAO {

    @PersistenceContext
    private EntityManager em;

    public void insert(Proyecto proyecto) {
        em.persist(proyecto);
    }

    public void update(Proyecto proyecto) {
        em.merge(proyecto);
    }

    public Proyecto read(int id) {
        return em.find(Proyecto.class, id);
    }

    public void delete(int id) {
        Proyecto proyecto = em.find(Proyecto.class, id);
        if (proyecto != null) {
            em.remove(proyecto);
        }
    }

    public List<Proyecto> getAll() {
        String jpql = "SELECT p FROM Proyecto p";
        Query q = em.createQuery(jpql, Proyecto.class);
        return q.getResultList();
    }
    
    // Método extra: Ver el portafolio de un usuario específico
    public List<Proyecto> getProyectosPorUsuario(int usuarioId) {
        String jpql = "SELECT p FROM Proyecto p WHERE p.programador.id = :usuarioId";
        Query q = em.createQuery(jpql, Proyecto.class);
        q.setParameter("usuarioId", usuarioId);
        return q.getResultList();
    }
    public List<Proyecto> findByProgramadorId(int programadorId) {
        String jpql = "SELECT p FROM Proyecto p WHERE p.programadorId = :programadorId";
        Query q = em.createQuery(jpql, Proyecto.class);
        q.setParameter("programadorId", programadorId);
        return q.getResultList();
    }
}