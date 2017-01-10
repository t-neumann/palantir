package at.ac.imp.palantir.facades;

import java.util.List;

import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.model.Essentialome;
import at.ac.imp.palantir.model.ExternalRNASeqResource;
import at.ac.imp.palantir.model.GenericGene;

public interface PublicDataFacade {
	
	public List<ExternalRNASeqResource> getAvailableRNASeqResources() throws DatabaseException;
	
	public List<Essentialome> getAvailableEssentialomes() throws DatabaseException;

}