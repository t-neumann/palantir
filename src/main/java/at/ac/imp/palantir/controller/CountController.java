package at.ac.imp.palantir.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.facades.ExperimentFacade;
import at.ac.imp.palantir.facades.ExperimentFacadeBean;
import at.ac.imp.palantir.model.Datapoint;
import at.ac.imp.palantir.model.ExpressionValue;
import at.ac.imp.palantir.model.Result;

@Named("CountController")
@ViewScoped
public class CountController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ExperimentFacadeBean experimentFacade;
	
	private int resultId;
	
	private List<ExpressionValue> expressionValues;
	
	@PostConstruct
	public void init() {

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.resultId = (Integer) flash.get("resultId");

		try {
			Result result = experimentFacade.getResultById(this.resultId);
			Collection<Datapoint> datapoints = result.getDatapoints();
			
			expressionValues = new ArrayList<ExpressionValue>();
			for (Datapoint datapoint : datapoints) {
				expressionValues.add((ExpressionValue)datapoint);
			}
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getResultId() {
		return resultId;
	}

	public void setResultId(int resultId) {
		this.resultId = resultId;
	}

	public List<ExpressionValue> getExpressionValues() {
		return expressionValues;
	}

	public void setExpressionValues(List<ExpressionValue> expressionValues) {
		this.expressionValues = expressionValues;
	}
}
