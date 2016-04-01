package at.ac.imp.palantir.facades;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import at.ac.imp.palantir.model.Alignment;
import at.ac.imp.palantir.model.Result;

@Stateless
//@Named("GeneHandler")
//@ApplicationScoped
@Remote(ExperimentFacade.class)
public class ExperimentFacadeBean implements ExperimentFacade{

	@Override
	public List<Result> getResultsForAlignment(Alignment alignment) {
		// TODO Auto-generated method stub
		return null;
	}

}
