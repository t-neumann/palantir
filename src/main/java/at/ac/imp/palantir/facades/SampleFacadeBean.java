package at.ac.imp.palantir.facades;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.model.QueueSampleMetaInfo;
import at.ac.imp.palantir.model.Sample;
import at.ac.imp.palantir.util.QueueSampleInfoRetreiver;

@Stateless
//@Named("GeneHandler")
//@ApplicationScoped
@Remote(SampleFacade.class)
public class SampleFacadeBean implements SampleFacade {
	
	@PersistenceContext(unitName = "palantir-db")
	private EntityManager em;
	
	@EJB
	private QueueSampleInfoRetreiver infoRetreiver;

	@Override
	public Sample getSampleById(int id) {
		Sample sample = em.find(Sample.class, id);
		return sample;
	}

	@Override
	public void addSampleMetaInfo(Sample sample) {
		QueueSampleMetaInfo info = infoRetreiver.getInfoForSample(sample.getId());

	}

	@Override
	public Collection<Sample> getAllSamples() throws DatabaseException {
		
		Collection<Sample> samples = null;
		
		try {
			TypedQuery<Sample> query = em.createQuery("SELECT s FROM Sample s", Sample.class);
			samples = query.getResultList();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(),e.getCause());
		}
		return samples;
	}

}
