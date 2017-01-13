package at.ac.imp.palantir.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;

import at.ac.imp.palantir.facades.ExperimentFacade;
import at.ac.imp.palantir.model.ExpressionValue;
import at.ac.imp.palantir.model.ExternalRNASeqDatapoint;
import at.ac.imp.palantir.model.ExternalRNASeqEntry;
import at.ac.imp.palantir.model.ExternalRNASeqResource;
import at.ac.imp.palantir.model.Gene;
import at.ac.imp.palantir.model.GenericGene;
import at.ac.imp.palantir.model.ScreenGene;
import at.ac.imp.palantir.util.LazyExpressionValueDataModel;
import at.ac.imp.palantir.util.LazyExternalRNASeqDataModel;

@Named("ExternalRNASeqController")
@ViewScoped
public class ExternalRNASeqController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9042595281818933860L;
	
	private ExternalRNASeqEntry entry;

	private Collection<ExternalRNASeqDatapoint> datapoints;
	
	private LazyExternalRNASeqDataModel lazyModel;

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
		int resourceId = (Integer) flash.get("resourceId");
		int entryId = (Integer) flash.get("entryId");
		
		//entry = em.find(ExternalRNASeqEntry.class, entryId);
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ExternalRNASeqEntry> query = cb.createQuery(ExternalRNASeqEntry.class);
		Root<ExternalRNASeqEntry> root = query.from(ExternalRNASeqEntry.class);
		root.fetch("datapoints", JoinType.INNER);
		query.select(root).distinct(true);
		query.where(cb.equal(root.get("id"), entryId));
		
		entry = em.createQuery(query).getSingleResult();
		
		datapoints = entry.getDatapoints();
		
		lazyModel = new LazyExternalRNASeqDataModel(em, entryId);
	}

	public Collection<ExternalRNASeqDatapoint> getDatapoints() {
		return datapoints;
	}

	public void setDatapoints(Collection<ExternalRNASeqDatapoint> datapoints) {
		this.datapoints = datapoints;
	}

	public ExternalRNASeqEntry getEntry() {
		return entry;
	}

	public void setEntry(ExternalRNASeqEntry entry) {
		this.entry = entry;
	}

	public LazyExternalRNASeqDataModel getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyExternalRNASeqDataModel lazyModel) {
		this.lazyModel = lazyModel;
	}
}
