package at.ac.imp.palantir.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import at.ac.imp.palantir.facades.SampleFacade;
import at.ac.imp.palantir.model.Alignment;
import at.ac.imp.palantir.model.Sample;

@Named("AlignmentController")
@RequestScoped
public class AlignmentController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Collection<Alignment> alignments;
	
	private Alignment selectedAlignment;
	
	private int sampleId;
		
	//@Inject
	@EJB
	private SampleFacade sampleFacade;
	
	@PostConstruct
	public void init() {
		
		this.sampleId = (Integer)FacesContext.getCurrentInstance().getExternalContext()
	               .getSessionMap().get("sampleId");
		Sample sample = sampleFacade.getSampleById(sampleId);
		if (sample != null) {
			this.alignments = sample.getAlignments();
		}
	}

	public Collection<Alignment> getAlignments() {
		return alignments;
	}

	public void setAlignments(Collection<Alignment> alignments) {
		this.alignments = alignments;
	}

	public Alignment getSelectedAlignment() {
		return selectedAlignment;
	}

	public void setSelectedAlignment(Alignment selectedAlignment) {
		this.selectedAlignment = selectedAlignment;
	}

	public int getSampleId() {
		return sampleId;
	}

	public void setSampleId(int sampleId) {
		this.sampleId = sampleId;
	}
}