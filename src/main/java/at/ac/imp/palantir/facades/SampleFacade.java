package at.ac.imp.palantir.facades;

import java.util.Collection;
import java.util.List;

import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.model.Sample;

public interface SampleFacade {
	
	public Collection<Sample> getAllSamples() throws DatabaseException;
	
	public Sample getSampleById(int id);
	
	public void addSampleMetaInfo(Sample sample);
	
	public void updateSample(Sample sample);
	
	public List<String> getOrganisms();
}
