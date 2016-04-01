package at.ac.imp.palantir.controller;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.FlowEvent;

import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.facades.SampleFacade;
import at.ac.imp.palantir.model.Experiment;
import at.ac.imp.palantir.model.Result;
import at.ac.imp.palantir.model.Sample;

@Named("ExperimentWizard")
@ViewScoped
public class ExperimentWizard implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
			
	private Collection<Sample> samples;
	
	private Collection<Result> results;
	
	public Collection<Sample> getSamples() {
		return samples;
	}
		
	@SuppressWarnings("unchecked")
	public void setSamples(Collection<Sample> samples) {
		
		this.samples = samples;
		Collections.sort((List<Sample>) samples);
	}
	
	private List<Sample> selectedSamples;

	private Experiment experiment = new Experiment();
	
	private boolean skip = false;
	
	@EJB
	private SampleFacade sampleFacade;
	
	@PostConstruct
	public void init() {
		try {
			setSamples(sampleFacade.getAllSamples());
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
    public Experiment getExperiment() {
        return experiment;
    }
 
    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }
     
    public void save() {        
        FacesMessage msg = new FacesMessage("Successful", "Welcome :" + experiment.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public String onFlowProcess(FlowEvent event) {
    	if (event.getOldStep().equals("sample")) {
    	}
        if(skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        }
        else {
            return event.getNewStep();
        }
    }

	public List<Sample> getSelectedSamples() {
		return selectedSamples;
	}

	public void setSelectedSamples(List<Sample> selectedSamples) {
		this.selectedSamples = selectedSamples;
	}

	public Collection<Result> getResults() {
		return results;
	}

	public void setResults(Collection<Result> results) {
		this.results = results;
	}
   
}
