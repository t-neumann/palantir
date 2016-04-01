package at.ac.imp.palantir.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.facades.SampleFacade;
import at.ac.imp.palantir.model.Datapoint;
import at.ac.imp.palantir.model.Gene;
import at.ac.imp.palantir.model.Sample;

@Named("SampleController")
@ViewScoped
public class SampleController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Collection<Sample> samples;
	
	public Collection<Sample> getSamples() {
		return samples;
	}
		
	@SuppressWarnings("unchecked")
	public void setSamples(Collection<Sample> samples) {
		
		this.samples = samples;
		Collections.sort((List<Sample>) samples);
	}
	
	private List<String> organisms;

	public List<String> getOrganisms() {
		return organisms;
	}

	public void setOrganisms(List<String> organisms) {
		this.organisms = organisms;
	}

	//@Inject
	@EJB
	private SampleFacade sampleFacade;
	
	@PostConstruct
	public void init() {
		try {
			setSamples(sampleFacade.getAllSamples());
			setOrganisms(sampleFacade.getOrganisms());
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
	
	public void onRowSelect(SelectEvent event) {
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("sampleId", this.selectedSample.getId());
		
//		FacesContext.getCurrentInstance().getExternalContext()
//        .getSessionMap()
//        .put("sampleId",this.selectedSample.getId());
		
		ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler)FacesContext.
                getCurrentInstance().getApplication().getNavigationHandler();
        
		//configurableNavigationHandler.performNavigation("alignment?faces-redirect=true");
		configurableNavigationHandler.performNavigation("alignment?faces-redirect=true");
		
	}
	
	public String sampleAlignments() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("sampleId", this.selectedSample.getId());
		
		return "alignment";
	}
	
	public String edit(){
		
//		ConfigurableNavigationHandler configurableNavigationHandler =
//                (ConfigurableNavigationHandler)FacesContext.
//                getCurrentInstance().getApplication().getNavigationHandler();
//		
//		configurableNavigationHandler.performNavigation("editSample");
		
		return "editSample";
		
	}
	
	public void onCellEdit(CellEditEvent event) {
		
		Sample sample = (Sample)((DataTable)event.getComponent()).getRowData();
		sampleFacade.updateSample(sample);
    }
	
	public void getMetaInfo() {
		sampleFacade.addSampleMetaInfo(this.selectedSample);
		try {
			setSamples(sampleFacade.getAllSamples());
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
