package at.ac.imp.palantir.facades;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.primefaces.model.SortOrder;

import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.model.Alignment;
import at.ac.imp.palantir.model.ExpressionValue;
import at.ac.imp.palantir.model.Reference;
import at.ac.imp.palantir.model.Result;
import at.ac.imp.palantir.model.Sample;

@Stateless
// @Named("ExperimentFacade")
// @ApplicationScoped
@Remote(ExperimentFacade.class)
public class ExperimentFacadeBean implements ExperimentFacade {

	@PersistenceContext(unitName = "palantir-db")
	private EntityManager em;

	@EJB
	private ExperimentFacade experimentFacade;

	@Override
	public List<Result> getResultsForAlignment(Alignment alignment) throws DatabaseException {
		TypedQuery<Result> query = em.createNamedQuery("Result.findByAlignmentId", Result.class);
		List<Result> results = null;

		try {
			results = query.setParameter("id", alignment.getId()).getResultList();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
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

	@Override
	public List<Reference> getAllReferences() throws DatabaseException {
		List<Reference> references = null;

		try {
			TypedQuery<Reference> query = em.createQuery("SELECT r FROM Reference r", Reference.class);
			references = query.getResultList();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
		return references;
	}
	
	public List<ExpressionValue> findExpressionValuesPerGene(int geneId) {
		List<ExpressionValue> result = null;
		TypedQuery<ExpressionValue> query = em.createNamedQuery("ExpressionValue.findByGene", ExpressionValue.class);
		result = query.setParameter("id", geneId).getResultList();
		return result;
	}
}