package at.ac.imp.palantir.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.facades.SampleFacade;
import at.ac.imp.palantir.model.Datapoint;
import at.ac.imp.palantir.model.Gene;
import at.ac.imp.palantir.model.Sample;

@Named("SampleController")
@SessionScoped
public class SampleController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Collection<Sample> samples;
		
	//@Inject
	@EJB
	private SampleFacade sampleFacade;
	
	@PostConstruct
	public void init() {
		try {
			samples = sampleFacade.getAllSamples();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Sample selectedSample;
	
	public Sample getSelectedSample() {
		return selectedSample;
	}

	public void setSelectedSample(Sample selectedSample) {
		this.selectedSample = selectedSample;
	}

	public Collection<Sample> getSamples() {
		return samples;
	}
	
	public void onRowSelect(SelectEvent event) {
		
		FacesContext.getCurrentInstance().getExternalContext()
        .getSessionMap()
        .put("sampleId",this.selectedSample.getId());
		
		ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler)FacesContext.
                getCurrentInstance().getApplication().getNavigationHandler();
        
		//configurableNavigationHandler.performNavigation("alignment?faces-redirect=true");
		configurableNavigationHandler.performNavigation("alignment");
	}
	
	public String edit(){
		return "editSample";
	}
	
	public void getMetaInfo() {
		
	}
}
