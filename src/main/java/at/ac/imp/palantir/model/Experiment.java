package at.ac.imp.palantir.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * Entity implementation class for Entity: Experiment
 *
 */
@Entity

public class Experiment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	private Reference reference;
	
	@ManyToMany
	private Collection<Alignment> alignments = new ArrayList<Alignment>();
	
	@ManyToOne
	private Result result;

	public Experiment() {
		super();
	}

	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public Collection<Alignment> getAlignments() {
		return alignments;
	}

	public void setAlignments(Collection<Alignment> alignments) {
		this.alignments = alignments;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}   
}
