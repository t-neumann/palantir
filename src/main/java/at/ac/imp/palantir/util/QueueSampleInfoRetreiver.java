package at.ac.imp.palantir.util;

import at.ac.imp.palantir.model.QueueSampleMetaInfo;

public interface QueueSampleInfoRetreiver {
	
	public QueueSampleMetaInfo getInfoForSample(int sampleId);
}
