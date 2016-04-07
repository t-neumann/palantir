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

//		try {
//			Result result = experimentFacade.getResultById(this.resultId, true);
//			System.out.println("Result loaded");
//			System.out.println("Result initialized");
//			Collection<Datapoint> datapoints = result.getDatapoints();
//			
//			expressionValues = new ArrayList<ExpressionValue>();
//			for (Datapoint datapoint : datapoints) {
//				expressionValues.add((ExpressionValue)datapoint);
//			}
//		} catch (DatabaseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		lazyModel = new LazyExpressionValueDataModel(em, resultId);
		//lazyModel.setRowCount(experimentFacade.count(new HashMap<String,Object>()));
		
//		CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
//        Metamodel m = em.getMetamodel();
//        ManagedType<ExpressionValue> expressionValueMetaModel = m.managedType(ExpressionValue.class);
//        Root<ExpressionValue> expressionValue = cq.from(ExpressionValue.class);
//        Join<ExpressionValue, Gene> gene = expressionValue.join(expressionValueMetaModel.getSingularAttribute("gene", Gene.class));
//        cq.select(cb.count(expressionValue)).where(cb.equal(gene.get("geneSymbol"), "BRCA1"));
//        int res = em.createQuery(cq).getSingleResult().intValue();
//        
//        System.out.println("Got" + res);

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
