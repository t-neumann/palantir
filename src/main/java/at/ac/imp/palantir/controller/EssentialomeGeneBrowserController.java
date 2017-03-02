package at.ac.imp.palantir.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import at.ac.imp.palantir.model.EssentialomeEntry;
import at.ac.imp.palantir.model.ExpressionValue;
import at.ac.imp.palantir.model.ExternalRNASeqDatapoint;
import at.ac.imp.palantir.model.ExternalRNASeqEntry;
import at.ac.imp.palantir.model.ExternalRNASeqResource;
import at.ac.imp.palantir.model.Gene;
import at.ac.imp.palantir.model.GenericGene;
import at.ac.imp.palantir.model.ScreenGene;
import at.ac.imp.palantir.util.LazyExpressionValueDataModel;
import at.ac.imp.palantir.util.LazyExternalRNASeqDataModel;

@Named("EssentialomeGeneBrowserController")
@ViewScoped
public class EssentialomeGeneBrowserController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9042595281818933860L;

	private List<EssentialomeEntry> entries = new ArrayList<EssentialomeEntry>();

	private List<Object[]> entryList = new ArrayList<Object[]>();

	private List<String> columnHeaders = new ArrayList<String>();

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
		String [] stringIds = flash.get("geneIds").toString().split(",");
		List<Integer> geneIds = new ArrayList<Integer>();
		
		for (String id : stringIds) {
			geneIds.add(Integer.parseInt(id));
		}
		
		TypedQuery<ScreenGene> q = em.createQuery("SELECT DISTINCT s FROM ScreenGene s JOIN FETCH s.datapoints WHERE s.id IN :ids", ScreenGene.class);
		q.setParameter("ids", geneIds);
		
		List<ScreenGene> genes = q.getResultList();
		
		Map<String, Map<String, EssentialomeDatapoint> > datapointMap = new HashMap<String, Map<String, EssentialomeDatapoint> >();
		
		for (ScreenGene gene : genes) {
			columnHeaders.add(gene.getGeneSymbol());
			datapointMap.put(gene.getGeneSymbol(), new HashMap<String, EssentialomeDatapoint>());
			for (EssentialomeDatapoint datapoint : gene.getDatapoints().values()) {
				if (!entries.contains(datapoint.getEntry())) {
					entries.add(datapoint.getEntry());
				}
				datapointMap.get(gene.getGeneSymbol()).put(datapoint.getEntry().getName(), datapoint);
			}
		}
		
		for (ScreenGene gene : genes) {
			Object[] datapoints = new Object[entries.size()];
			int index = 0;
			for (EssentialomeEntry entry : entries) {
				if (datapointMap.get(gene.getGeneSymbol()).containsKey(entry.getName())) {
					datapoints[index] = datapointMap.get(gene.getGeneSymbol()).get(entry.getName());
				} else {
					datapoints[index] = null;
				}
				++index;
			}
			entryList.add(datapoints);
		}
		
		System.out.println("test");
		
	}

	public List<EssentialomeEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<EssentialomeEntry> entries) {
		this.entries = entries;
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
