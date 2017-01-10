package at.ac.imp.palantir.facades;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.model.Essentialome;
import at.ac.imp.palantir.model.ExternalRNASeqResource;
import at.ac.imp.palantir.model.GenericGene;
import at.ac.imp.palantir.model.Reference;

@Stateless
@Remote(PublicDataFacade.class)
public class PublicDataFacadeBean implements PublicDataFacade {
	
	@PersistenceContext(unitName = "palantir-db")
	private EntityManager em;

	@Override
	public List<ExternalRNASeqResource> getAvailableRNASeqResources() throws DatabaseException {
		List<ExternalRNASeqResource> resources = null;

		try {
			TypedQuery<ExternalRNASeqResource> query = em.createQuery("SELECT r FROM ExternalRNASeqResource r", ExternalRNASeqResource.class);
			resources = query.getResultList();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
		return resources;
	}

	@Override
	public List<Essentialome> getAvailableEssentialomes() throws DatabaseException {
		List<Essentialome> essentialomes = null;

		try {
			TypedQuery<Essentialome> query = em.createQuery("SELECT e FROM Essentialome e", Essentialome.class);
			essentialomes = query.getResultList();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
		return essentialomes;
	}

}
