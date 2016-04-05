package at.ac.imp.palantir.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.ApplicationContext;
import org.primefaces.event.FlowEvent;

import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.facades.ExperimentFacadeBean;
import at.ac.imp.palantir.facades.SampleFacade;
import at.ac.imp.palantir.model.Alignment;
import at.ac.imp.palantir.model.Experiment;
import at.ac.imp.palantir.model.Reference;
import at.ac.imp.palantir.model.Result;
import at.ac.imp.palantir.model.Sample;

@Named("ExperimentWizard")
@ViewScoped
public class ExperimentWizard implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ExperimentFacadeBean experimentFacade;
			
	private Collection<Sample> samples;
	
	private Collection<Result> results;
	
	public Collection<Sample> getSamples() {
		return samples;
	}
		
	public void setSamples(Collection<Sample> samples) {
		
		this.samples = samples;
		Collections.sort((List<Sample>) samples);
	}
	
	public Collection<Result> getResults() {
		return results;
	}

	public void setResults(Collection<Result> results) {
		this.results = results;
		Collections.sort((List<Result>) results);
	}
	
	private List<Sample> selectedSamples;
	
	private List<Result> selectedResults;
	
	private String experimentName;
	
	private String experimentDesc;

	private Experiment experiment = new Experiment();
		
	@Inject
	private SampleFacade sampleFacade;
	
	@PostConstruct
	public void init() {
		System.out.println("Init");
		try {
			setSamples(sampleFacade.getAllSamples());
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PreDestroy
	public void destroy() {
		System.out.println("Destroyed");
	}
 
    public Experiment getExperiment() {
        return experiment;
    }
 
    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }
     
    public String onFlowProcess(FlowEvent event) {
        
    	if (event.getOldStep().equals("sample")) {
    		results = new ArrayList<Result>();
    		for (Sample sample : selectedSamples) {
    			for (Alignment alignment : sample.getAlignments()) {
    				try {
						List<Result> alignmentResults = experimentFacade.getResultsForAlignment(alignment);
						results.addAll(alignmentResults);
					} catch (DatabaseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    		}
    	}
    	if (event.getOldStep().equals("reference")) {
    		if (selectedResults == null) {
    			FacesMessage msg = new FacesMessage("Error", "Selected results null");
		        FacesContext.getCurrentInstance().addMessage(null, msg);
    		}
    		if (selectedResults.isEmpty()) {
    			FacesMessage msg = new FacesMessage("Error", "Selected results empty");
		        FacesContext.getCurrentInstance().addMessage(null, msg);
    		}
    		Reference ref = null;
    		for (Result result : selectedResults) {
    			if (ref == null) {
    				ref = result.getReference();
    			}
    			if (!result.getReference().equals(ref)) {
    				FacesMessage msg = new FacesMessage("Error", "Selected samples not analyzed on same reference");
    		        FacesContext.getCurrentInstance().addMessage(null, msg);
    				return event.getOldStep();
    			}
    		}
    		
    	}
        return event.getNewStep();
    }

	public List<Sample> getSelectedSamples() {
		return selectedSamples;
	}

	public void setSelectedSamples(List<Sample> selectedSamples) {
		System.out.println("SELECT start");
		for (Sample result : selectedSamples) {
			System.out.println(result.getId());
		}
		System.out.println("SELECT end");
		this.selectedSamples = selectedSamples;
	}

	public List<Result> getSelectedResults() {
		return selectedResults;
	}

	public void setSelectedResults(List<Result> selectedResults) {
		System.out.println("SELECTED samples");
		for (Sample result : selectedSamples) {
			System.out.println(result.getId());
		}
		System.out.println("SELECTED samples");
		System.out.println("SELECT start");
		for (Result result : selectedResults) {
			System.out.println(result.getId());
		}
		System.out.println("SELECT end");
		this.selectedResults = selectedResults;
	}

	public String getExperimentName() {
		return experimentName;
	}

	public void setExperimentName(String experimentName) {
		this.experimentName = experimentName;
	}

	public String getExperimentDesc() {
		return experimentDesc;
	}

	public void setExperimentDesc(String experimentDesc) {
		this.experimentDesc = experimentDesc;
	}
}
