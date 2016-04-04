package at.ac.imp.palantir.facades;

import java.util.Collection;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.model.Alignment;
import at.ac.imp.palantir.model.Reference;
import at.ac.imp.palantir.model.Result;
import at.ac.imp.palantir.model.Sample;

@Stateless
//@Named("GeneHandler")
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
}