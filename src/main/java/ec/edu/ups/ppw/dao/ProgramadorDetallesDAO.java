package ec.edu.ups.ppw.dao;

import ec.edu.ups.ppw.model.ProgramadorDetalles;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;

@Stateless
public class ProgramadorDetallesDAO {

    @PersistenceContext
    private EntityManager em;

    public void insert(ProgramadorDetalles detalles) {
        em.persist(detalles);
    }

    public void update(ProgramadorDetalles detalles) {
        em.merge(detalles);
    }

    public ProgramadorDetalles read(String id) {
        return em.find(ProgramadorDetalles.class, id);
    }

    public void delete(String id) {
        ProgramadorDetalles detalles = em.find(ProgramadorDetalles.class, id);
        if (detalles != null) {
            em.remove(detalles);
        }
    }
    
    public List<ProgramadorDetalles> getAll() {
        String jpql = "SELECT d FROM ProgramadorDetalles d";
        Query q = em.createQuery(jpql, ProgramadorDetalles.class);
        return q.getResultList();
    }
}