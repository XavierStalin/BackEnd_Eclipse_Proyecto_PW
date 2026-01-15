package ec.edu.ups.ppw.dao;

import java.util.List;

import ec.edu.ups.ppw.model.Disponibilidad;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class DisponibilidadDAO {
	 @PersistenceContext
	    private EntityManager em;

	    public void insert(Disponibilidad  disponibilidad) {
	        em.persist(disponibilidad);
	    }

	    public void update(Disponibilidad disponibilidad) {
	        em.merge(disponibilidad);
	    }

	    public Disponibilidad read(int id) {
	        return em.find(Disponibilidad.class, id);
	    }

	    public void delete(int id) {
	    	Disponibilidad disponibilidad = em.find(Disponibilidad.class, id);
	        if (disponibilidad != null) {
	            em.remove(disponibilidad);
	        }
	    }

	    public List<Disponibilidad> getAll() {
	        String jpql = "SELECT a FROM Disponibilidad a";
	        Query q = em.createQuery(jpql, Disponibilidad.class);
	        return q.getResultList();
	    }
}
