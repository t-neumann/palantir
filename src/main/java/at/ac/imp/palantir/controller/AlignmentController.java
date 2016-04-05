package at.ac.imp.palantir.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.facades.ExperimentFacade;
import at.ac.imp.palantir.facades.ExperimentFacadeBean;
import at.ac.imp.palantir.facades.SampleFacade;
import at.ac.imp.palantir.facades.SampleFacadeBean;
import at.ac.imp.palantir.model.Alignment;
import at.ac.imp.palantir.model.Result;
import at.ac.imp.palantir.model.Sample;

@Named("AlignmentController")
@ViewScoped
public class AlignmentController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Collection<Result> results;

	private Result selectedResult;

	private int sampleId;

	@Inject
	private SampleFacadeBean sampleFacade;

	@Inject
	private ExperimentFacadeBean experimentFacade;

	@PostConstruct
	public void init() {

		//if (!FacesContext.getCurrentInstance().isPostback()) {
			//if (!FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {

				System.out.println("SCHAAS");
				Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
				for (String key : flash.keySet()) {
					System.out.println(key);
				}
				this.sampleId = (Integer) flash.get("sampleId");

				// this.sampleId =
				// (Integer)FacesContext.getCurrentInstance().getExternalContext()
				// .getSessionMap().get("sampleId");

				Sample sample = sampleFacade.getSampleById(sampleId);
				if (sample != null) {
					results = new ArrayList<Result>();

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
		//}
	//}

	public void onRowSelect(SelectEvent event) {

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("resultId", this.selectedResult.getId());

		ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext
				.getCurrentInstance().getApplication().getNavigationHandler();

		FacesMessage msg = new FacesMessage("Error", "error");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		// configurableNavigationHandler.performNavigation("alignment?faces-redirect=true");
		configurableNavigationHandler.performNavigation("count");

	}

	public Collection<Result> getResults() {
		return results;
	}

	public void setResults(Collection<Result> results) {
		this.results = results;
	}

	public Result getSelectedResult() {
		return selectedResult;
	}

	public void setSelectedResult(Result selectedResult) {
		this.selectedResult = selectedResult;
	}

	public int getSampleId() {
		return sampleId;
	}

	public void setSampleId(int sampleId) {
		this.sampleId = sampleId;
	}
}
