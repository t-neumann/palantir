package at.ac.imp.palantir.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

import at.ac.imp.palantir.model.EssentialomeDatapoint;
import at.ac.imp.palantir.model.ExternalRNASeqDatapoint;
import at.ac.imp.palantir.model.GenericGene;
import at.ac.imp.palantir.model.ScreenGene;

@Named("EssentialomeController")
@ViewScoped
public class EssentialomeController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5578116705244021926L;

	private List<ScreenGene> genes;
	
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
		int essentialomeId = (Integer) flash.get("essentialomeId");
				
		try {
			
			TypedQuery<Integer> qe = em.createQuery("SELECT e.id FROM EssentialomeEntry e WHERE e.essentialome.id = :id", Integer.class);
			qe.setParameter("id", essentialomeId);
			
			List<Integer> ids = qe.getResultList();
			
			columnHeaders = new ArrayList<String>();
			
//			CriteriaBuilder gcb = em.getCriteriaBuilder();
//			CriteriaQuery<ScreenGene> gquery = gcb.createQuery(ScreenGene.class);
//			Root<ScreenGene> groot = gquery.from(ScreenGene.class);
//			gquery.where(gcb.equal(groot.get("essentialome").get("id"), essentialomeId));
//			gquery.select(groot);
//			
//			genes = em.createQuery(gquery).getResultList();
			
			genes = em.createQuery("SELECT g FROM ScreenGene g JOIN g.essentialomes gr WHERE gr.id = :id ORDER by g.id", ScreenGene.class).setParameter("id", essentialomeId).getResultList();
			
			for (Integer id : ids) {
				
				TypedQuery<String> q = em.createQuery("SELECT e.name FROM EssentialomeEntry e WHERE e.id = :id", String.class);
				q.setParameter("id", id);
				
				columnHeaders.add(q.getResultList().get(0));
				
				CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<EssentialomeDatapoint> query = cb.createQuery(EssentialomeDatapoint.class);
				Root<EssentialomeDatapoint> root = query.from(EssentialomeDatapoint.class);
				query.where(cb.equal(root.get("entry").get("id"), id));
				query.orderBy(cb.asc(root.get("gene").get("id")));
				
				query.select(root);
				
				entryList.add(em.createQuery(query).getResultList().toArray());
			}
			
//			for (Integer id : ids) {
//				
//				TypedQuery<String> q = em.createQuery("SELECT e.name FROM EssentialomeEntry e WHERE e.id = :id", String.class);
//				q.setParameter("id", id);
//				
//				columnHeaders.add(q.getResultList().get(0));
//				
//				CriteriaBuilder cb = em.getCriteriaBuilder();
//				CriteriaQuery<EssentialomeDatapoint> query = cb.createQuery(EssentialomeDatapoint.class);
//				Root<EssentialomeDatapoint> root = query.from(EssentialomeDatapoint.class);
//				//root.fetch("EssentialomeEntry", JoinType.INNER);
//				query.where(cb.equal(root.get("entry").get("id"), id));
//				query.select(root);
//				
//				entryList.add(em.createQuery(query).getResultList().toArray());
//				//List<EssentialomeDatapoint> result = em.createQuery(query).getResultList();
//			
//			}
//		
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


	public List<Object[]> getEntryList() {
		return entryList;
	}

	public void setEntryList(List<Object[]> entryList) {
		this.entryList = entryList;
	}	
}