package at.ac.imp.palantir.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: Result
 *
 */
@Entity
@NamedQuery(name="Result.findByAlignmentId",query="SELECT r FROM Result r WHERE r.alignment.id = :id")
public class Result implements Serializable, Comparable<Result> {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne(cascade = CascadeType.MERGE)
	private Alignment alignment;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private Reference reference;

	@OneToMany(cascade = CascadeType.ALL)
	private Collection<Datapoint> datapoints = new ArrayList<Datapoint>();
	
	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}
	
	public Alignment getAlignment() {
		return alignment;
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

	public Collection<Datapoint> getDatapoints() {
		return datapoints;
	}

	public void setDatapoints(Collection<Datapoint> datapoints) {
		this.datapoints = datapoints;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Result() {
		super();
	}
	
	@Override
	public int compareTo(Result o) {
		
		int otherId = o.getId();
		if (otherId > this.id) {
			return -1;
		} else if (otherId == this.id) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Result other = (Result) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
