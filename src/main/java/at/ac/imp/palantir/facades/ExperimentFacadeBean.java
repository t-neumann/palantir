package at.ac.imp.palantir.facades;

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
import at.ac.imp.palantir.model.Result;

@Stateless
//@Named("ExperimentFacade")
//@ApplicationScoped
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

	@Override
	public int count(Map<String, Object> filters) {
		 CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
	        Root<ExpressionValue> myObj = cq.from(ExpressionValue.class);
	        cq.where(experimentFacade.getFilterCondition(cb, myObj, filters));
	        cq.select(cb.count(myObj));
	        return em.createQuery(cq).getSingleResult().intValue();
	}

	
	@Override
	public Predicate getFilterCondition(CriteriaBuilder cb, Root<ExpressionValue> myObj, Map<String, Object> filters) {
		Predicate filterCondition = cb.conjunction();
        String wildCard = "%";
        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            String value = wildCard + filter.getValue() + wildCard;
            if (!filter.getValue().equals("")) {
                javax.persistence.criteria.Path<String> path = myObj.get(filter.getKey());
                filterCondition = cb.and(filterCondition, cb.like(path, value));
            }
        }
        return filterCondition;
	}

	@Override
	public List<ExpressionValue> getResultList(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ExpressionValue> cq = cb.createQuery(ExpressionValue.class);
        Root<ExpressionValue> myObj = cq.from(ExpressionValue.class);
        cq.where(experimentFacade.getFilterCondition(cb, myObj, filters));

        if (sortField != null) {
            if (sortOrder == SortOrder.ASCENDING) {
                cq.orderBy(cb.asc(myObj.get(sortField)));
            } else if (sortOrder == SortOrder.DESCENDING) {
                cq.orderBy(cb.desc(myObj.get(sortField)));
            }
        }
        return em.createQuery(cq).setFirstResult(first).setMaxResults(pageSize).getResultList();

	}
}