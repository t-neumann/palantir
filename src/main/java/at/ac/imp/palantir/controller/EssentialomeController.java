package at.ac.imp.palantir.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;

import org.hibernate.Hibernate;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.model.Essentialome;
import at.ac.imp.palantir.model.EssentialomeDatapoint;
import at.ac.imp.palantir.model.EssentialomeEntry;
import at.ac.imp.palantir.model.ExpressionValue;
import at.ac.imp.palantir.model.ScreenGene;
import at.ac.imp.palantir.util.LazyExternalRNASeqDataModel;
import at.ac.imp.palantir.util.LazyScreenGeneDataModel;

@Named("EssentialomeController")
@ViewScoped
public class EssentialomeController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5578116705244021926L;

	private List<ScreenGene> genes;
	
	private List<String> columnHeaders;
	
	private LazyScreenGeneDataModel lazyModel;
		
	@PersistenceContext(unitName = "palantir-db")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		System.out.println(flash.size());
		for (String key : flash.keySet()) {
			System.out.println(key);
			System.out.println(flash.get(key));
		}
		int essentialomeId = (Integer) flash.get("essentialomeId");
				
		try {
						
			List<Integer> ids = Arrays.asList(new Integer[]{1485628, 1634134, 2555163});
			
			columnHeaders = new ArrayList<String>();
			
//			for(Integer i : ids) {
//				TypedQuery<EssentialomeEntry> q = em.createQuery("SELECT e FROM EssentialomeEntry e JOIN FETCH e.datapoints i WHERE e.id = :id",EssentialomeEntry.class);
//				q.setParameter("id", i);
//				//entries = q.getResultList();
//				EssentialomeEntry entry = q.getSingleResult();
//				columnHeaders.add(entry.getName());
//				entries.add(entry.getDatapoints().toArray());
//			}
			
			
			
			TypedQuery<String> q = em.createQuery("SELECT DISTINCT e.name FROM EssentialomeEntry e WHERE e.id IN :ids", String.class);
			q.setParameter("ids", ids);
			
			columnHeaders = q.getResultList();
			
			lazyModel = new LazyScreenGeneDataModel(em, essentialomeId);
			
			/*
			 *  uncomment for previous shit
			 */
			
//			CriteriaBuilder cb = em.getCriteriaBuilder();
//			CriteriaQuery<ScreenGene> query = cb.createQuery(ScreenGene.class);
//			Root<ScreenGene> root = query.from(ScreenGene.class);
//			root.fetch("datapoints", JoinType.INNER);
//			query.select(root).distinct(true);
//			
//			genes = em.createQuery(query).getResultList();
			
			//query.where(cb.equal(root.get("id"), essentialomeId));

			
			//TypedQuery<ScreenGene> query = em.createQuery("SELECT DISTINCT s FROM ScreenGene s JOIN FETCH s.datapoints", ScreenGene.class);
			//query.setParameter("id", essentialomeId);
			
			//genes = query.getResultList();
			
			
			
//			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//			CriteriaQuery<ScreenGene>criteriaQuery = criteriaBuilder.createQuery(ScreenGene.class);
//			Metamodel metamodel = em.getMetamodel();
//			EntityType<ScreenGene> screenGeneType = metamodel.entity(ScreenGene.class);
//			EntityType<Essentialome> essentialomeType = metamodel.entity(Essentialome.class);
//			
//			Root<ScreenGene> root = criteriaQuery.from(screenGeneType);
//			Join<ScreenGene, Essentialome> join = root.join(screenGeneType.getSingularAttribute("essentialome",Essentialome.class), JoinType.INNER);
//			
//			criteriaQuery.where(criteriaBuilder.equal(join.get(essentialomeType.getSingularAttribute("id", Integer.class)), essentialomeId));
//			criteriaQuery.select(root);
//
//			TypedQuery<ScreenGene> typedQuery = em.createQuery(criteriaQuery);
//			this.genes = typedQuery.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	public List<ScreenGene> getGenes() {
		return genes;
	}



	public void setGenes(List<ScreenGene> genes) {
		this.genes = genes;
	}



	public List<String> getColumnHeaders() {
		return columnHeaders;
	}

	public void setColumnHeaders(List<String> columnHeaders) {
		this.columnHeaders = columnHeaders;
	}



	public LazyScreenGeneDataModel getLazyModel() {
		return lazyModel;
	}



	public void setLazyModel(LazyScreenGeneDataModel lazyModel) {
		this.lazyModel = lazyModel;
	}
	
}
