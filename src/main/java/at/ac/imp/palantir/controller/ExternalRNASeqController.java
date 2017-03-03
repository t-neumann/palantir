package at.ac.imp.palantir.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;

import at.ac.imp.palantir.facades.ExperimentFacade;
import at.ac.imp.palantir.model.EssentialomeDatapoint;
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

	private ExternalRNASeqResource resource;

	private List<GenericGene> genes;

	private List<Object[]> entryList = new ArrayList<Object[]>();

	private List<String> columnHeaders;

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
		
		resource = em.find(ExternalRNASeqResource.class, resourceId);
		
		String [] entryIds = flash.get("entryIds").toString().split(",");
	
		columnHeaders = new ArrayList<String>();
		
//		CriteriaBuilder gcb = em.getCriteriaBuilder();
//		CriteriaQuery<GenericGene> gquery = gcb.createQuery(GenericGene.class);
//		Root<GenericGene> groot = gquery.from(GenericGene.class);
//		gquery.select(groot);
//		
//		genes = em.createQuery(gquery).getResultList();
		
		genes = em.createQuery("SELECT g FROM GenericGene g JOIN g.resources gr WHERE gr.id = :id ORDER by g.id", GenericGene.class).setParameter("id", resourceId).getResultList();
		
		for (String id : entryIds) {
			
			TypedQuery<String> q = em.createQuery("SELECT e.name FROM ExternalRNASeqEntry e WHERE e.id = :id", String.class);
			q.setParameter("id", Integer.parseInt(id));
			
			columnHeaders.add(q.getResultList().get(0));
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<ExternalRNASeqDatapoint> query = cb.createQuery(ExternalRNASeqDatapoint.class);
			Root<ExternalRNASeqDatapoint> root = query.from(ExternalRNASeqDatapoint.class);
			query.where(cb.equal(root.get("entry").get("id"), Integer.parseInt(id)));
			query.orderBy(cb.asc(root.get("gene").get("id")));
			
			query.select(root);
			
			entryList.add(em.createQuery(query).getResultList().toArray());
		}
		
	}

	public ExternalRNASeqResource getResource() {
		return resource;
	}

	public void setResource(ExternalRNASeqResource resource) {
		this.resource = resource;
	}

	public List<GenericGene> getGenes() {
		return genes;
	}

	public void setGenes(List<GenericGene> genes) {
		this.genes = genes;
	}

	public List<Object[]> getEntryList() {
		return entryList;
	}

	public void setEntryList(List<Object[]> entryList) {
		this.entryList = entryList;
	}

	public List<String> getColumnHeaders() {
		return columnHeaders;
	}

	public void setColumnHeaders(List<String> columnHeaders) {
		this.columnHeaders = columnHeaders;
	}

}
