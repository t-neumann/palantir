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
import at.ac.imp.palantir.model.Datapoint;
import at.ac.imp.palantir.model.ExpressionValue;
import at.ac.imp.palantir.model.Gene;
import at.ac.imp.palantir.model.Reference;
import at.ac.imp.palantir.util.LazyGeneDataModel;

@Named("GeneController")
@ViewScoped
public class GeneController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Gene selectedGene;

	private Collection<ExpressionValue> datapoints;

	@PersistenceContext(unitName = "palantir-db")
	private EntityManager em;

	@EJB
	private ExperimentFacade experimentFacade;

	// @Inject
	// private Conversation conversation;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		int geneId = (Integer) flash.get("geneId");
		
		System.out.println(geneId);
		selectedGene = em.find(Gene.class, geneId);
		System.out.println(selectedGene.getGeneSymbol());
		//datapoints = (Collection<ExpressionValue>)(Collection<?>) selectedGene.getDatapoints();
		datapoints = experimentFacade.findExpressionValuesPerGene(geneId);
	}

	public Collection<ExpressionValue> getDatapoints() {
		return datapoints;
	}

	public void setDatapoints(Collection<ExpressionValue> datapoints) {
		this.datapoints = datapoints;
	}

	public Gene getSelectedGene() {
		return selectedGene;
	}

	public void setSelectedGene(Gene selectedGene) {
		this.selectedGene = selectedGene;
	}
}
