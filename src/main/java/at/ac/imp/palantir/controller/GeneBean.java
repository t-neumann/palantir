package at.ac.imp.palantir.controller;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import at.ac.imp.palantir.entities.Reference;
import at.ac.imp.palantir.exceptions.DatabaseException;

@Stateless
@Remote(GeneHandler.class)
public class GeneBean implements GeneHandler {
	
	@PersistenceContext(unitName = "palantir-db")
	private EntityManager em;

	@Override
	public Reference getReferenceByName(String name) throws DatabaseException {
		TypedQuery<Reference> query = em.createNamedQuery("Reference.findByName", Reference.class);
		Reference result = null;
		
		try {
			result = query.setParameter("name", name).getSingleResult();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(),e.getCause());
		}
		return result;
	}

}
