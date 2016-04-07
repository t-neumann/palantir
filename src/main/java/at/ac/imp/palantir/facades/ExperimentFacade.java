package at.ac.imp.palantir.facades;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.SortOrder;

import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.model.Alignment;
import at.ac.imp.palantir.model.ExpressionValue;
import at.ac.imp.palantir.model.Result;

public interface ExperimentFacade {
	public List<Result> getResultsForAlignment(Alignment alignment) throws DatabaseException;
	public Result getResultById(int id,  boolean eager) throws DatabaseException;
	public int count(Map<String, Object> filters);
	public Predicate getFilterCondition (CriteriaBuilder cb, Root<ExpressionValue> myObj, Map<String, Object> filters);
	public List<ExpressionValue> getResultList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters);
}
