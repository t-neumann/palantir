package at.ac.imp.palantir.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.ac.imp.palantir.facades.ExperimentFacade;
import at.ac.imp.palantir.model.ExpressionValue;
import at.ac.imp.palantir.util.LazyExpressionValueDataModel;

@Named("CountController")
@ViewScoped
public class CountController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LazyExpressionValueDataModel lazyModel;
	
	@EJB
	private ExperimentFacade experimentFacade;
	
	@PersistenceContext(unitName = "palantir-db")
	private EntityManager em;
	
	private int resultId;
	
	private List<ExpressionValue> expressionValues;
	
	@PostConstruct
	public void init() {
		
		System.out.println("Count init");

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.resultId = (Integer) flash.get("resultId");

		lazyModel = new LazyExpressionValueDataModel(em, resultId);

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

	public LazyExpressionValueDataModel getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyExpressionValueDataModel lazyModel) {
		this.lazyModel = lazyModel;
	}
}
