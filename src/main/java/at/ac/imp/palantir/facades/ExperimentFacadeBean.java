package at.ac.imp.palantir.facades;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Hibernate;

import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.model.Alignment;
import at.ac.imp.palantir.model.Result;

@Stateless
//@Named("ExperimentFacade")
//@ApplicationScoped
@Remote(ExperimentFacade.class)
public class ExperimentFacadeBean implements ExperimentFacade {
	
	@PersistenceContext(unitName = "palantir-db")
	private EntityManager em;

	@Override
	public List<Result> getResultsForAlignment(Alignment alignment) throws DatabaseException {
		TypedQuery<Result> query = em.createNamedQuery("Result.findByAlignmentId", Result.class);
		List<Result> results = null;
		
		try {
			results = query.setParameter("id", alignment.getId()).getResultList();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(),e.getCause());
		}
		return results;
	}

	@Override
	public Result getResultById(int id, boolean eager) throws DatabaseException {
		Result result = em.find(Result.class, id);
		if (eager) {
			Hibernate.initialize(result.getDatapoints());
		}
		return result;
	}
}