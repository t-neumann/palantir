package at.ac.imp.palantir.controller;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;


@Named("Navigator")
@ApplicationScoped
public class Navigator implements Serializable {
	
	private static final String SAMPLE_NAVIGATE = "samples";
	private static final String EXPERIMENT_NAVIGATE = "experiment";
	private static final String GENE_NAVIGATE = "reference";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String navigateSample () {
		return Navigator.SAMPLE_NAVIGATE;
	}
	
	public String navigateExperiment() {
		return Navigator.EXPERIMENT_NAVIGATE;
	}
	
	public String navigateGene() {
		return Navigator.GENE_NAVIGATE;
	}
}
