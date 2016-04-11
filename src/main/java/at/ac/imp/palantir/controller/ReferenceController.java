package at.ac.imp.palantir.controller;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.SortOrder;

import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.facades.ExperimentFacade;
import at.ac.imp.palantir.model.Gene;
import at.ac.imp.palantir.model.Reference;
import at.ac.imp.palantir.util.LazyGeneDataModel;

@Named("ReferenceController")
@ViewScoped
public class ReferenceController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Collection<Reference> references;

	private Reference selectedReference;

	private Collection<Gene> genes;

	private Gene selectedGene;

	private LazyGeneDataModel lazyModel;

	@PersistenceContext(unitName = "palantir-db")
	private EntityManager em;

	@EJB
	private ExperimentFacade experimentFacade;

	// @Inject
	// private Conversation conversation;

	@PostConstruct
	public void init() {

		try {
			references = experimentFacade.getAllReferences();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onRowSelect(SelectEvent event) {
		
		lazyModel = new LazyGeneDataModel(em, selectedReference.getId());

		FacesMessage msg = new FacesMessage("Loading genes", "Reference: " + selectedReference.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
public void onGeneSelect(SelectEvent event) {

		 Flash flash =
		 FacesContext.getCurrentInstance().getExternalContext().getFlash();
		 flash.put("geneId", this.selectedGene.getId());
		
		 ConfigurableNavigationHandler configurableNavigationHandler =
		 (ConfigurableNavigationHandler) FacesContext
		 .getCurrentInstance().getApplication().getNavigationHandler();
		
		 configurableNavigationHandler.performNavigation("gene");
	}

	public Collection<Reference> getReferences() {
		return references;
	}

	public void setReferences(Collection<Reference> references) {
		this.references = references;
	}

	public Reference getSelectedReference() {
		return selectedReference;
	}

	public void setSelectedReference(Reference selectedReference) {
		this.selectedReference = selectedReference;
	}

	public Collection<Gene> getGenes() {
		return genes;
	}

	public void setGenes(Collection<Gene> genes) {
		this.genes = genes;
	}

	public Gene getSelectedGene() {
		return selectedGene;
	}

	public void setSelectedGene(Gene selectedGene) {
		this.selectedGene = selectedGene;
	}

	public LazyGeneDataModel getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyGeneDataModel lazyModel) {
		this.lazyModel = lazyModel;
	}
}
