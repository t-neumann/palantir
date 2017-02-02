package at.ac.imp.palantir.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.primefaces.model.DualListModel;

import at.ac.imp.palantir.model.ExternalRNASeqDatapoint;
import at.ac.imp.palantir.model.ExternalRNASeqEntry;
import at.ac.imp.palantir.model.ExternalRNASeqResource;
import at.ac.imp.palantir.util.LazyExternalRNASeqDataModel;

@Named("ExternalRNASeqContextController")
@ViewScoped
public class ExternalRNASeqContextController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4249948509325356167L;
	
	private ExternalRNASeqResource resource;

	private DualListModel<ExternalRNASeqEntry> entries;
	
	private String context;
	
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
		context = (String)flash.get("context");
		
		resource = em.find(ExternalRNASeqResource.class, resourceId);
		
		List<ExternalRNASeqEntry> entriesSource = new ArrayList<ExternalRNASeqEntry>();
				
		if (context.equals("")) {
			entriesSource.addAll(resource.getEntries());
		} else {
			for (ExternalRNASeqEntry entry : resource.getEntries()) {
				if (entry.getContext().equals(context)) {
					entriesSource.add(entry);
				}
			}
		}
		
		List<ExternalRNASeqEntry> entriesTarget = new ArrayList<ExternalRNASeqEntry>();
		
		entries = new DualListModel<ExternalRNASeqEntry>(entriesSource, entriesTarget);
		
	}

	public ExternalRNASeqResource getResource() {
		return resource;
	}

	public void setResource(ExternalRNASeqResource resource) {
		this.resource = resource;
	}

	public DualListModel<ExternalRNASeqEntry> getEntries() {
		return entries;
	}

	public void setEntries(DualListModel<ExternalRNASeqEntry> entries) {
		this.entries = entries;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
	public String sampleFetch() {
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		
		flash.put("resourceId", resource.getId());
		
		List<String> entryIds = new ArrayList<String>();
		
		for (Object o : entries.getTarget()) {
			entryIds.add(o.toString());
		}
		
		flash.put("entryIds", String.join(",", entryIds));
		
        return "publicRNASeqDataBrowser";
    }
}
