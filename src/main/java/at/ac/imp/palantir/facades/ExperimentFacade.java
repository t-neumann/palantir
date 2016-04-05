package at.ac.imp.palantir.facades;

import java.util.List;

import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.model.Alignment;
import at.ac.imp.palantir.model.Result;

public interface ExperimentFacade {
	public List<Result> getResultsForAlignment(Alignment alignment) throws DatabaseException;
	public Result getResultById(int id) throws DatabaseException;
}
