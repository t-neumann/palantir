package at.ac.imp.palantir.controller;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.model.Gene;
import at.ac.imp.palantir.model.Reference;

@Stateless
//@Named("GeneHandler")
//@ApplicationScoped
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

	@Override
	public Gene getGeneByName(String name) throws DatabaseException {
		TypedQuery<Gene> query = em.createNamedQuery("Gene.findByName", Gene.class);
		Gene result = null;
		
		try {
			result = query.setParameter("name", name).getSingleResult();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(),e.getCause());
		}
		return result;
	}

}
