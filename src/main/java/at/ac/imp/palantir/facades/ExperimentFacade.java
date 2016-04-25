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
import at.ac.imp.palantir.model.Reference;
import at.ac.imp.palantir.model.Result;

public interface ExperimentFacade {
	
	public List<Reference> getAllReferences()  throws DatabaseException;
	public List<Result> getResultsForAlignment(Alignment alignment) throws DatabaseException;
	public Result getResultById(int id,  boolean eager) throws DatabaseException;
	public List<ExpressionValue> findExpressionValuesPerGene(int geneId);
}
