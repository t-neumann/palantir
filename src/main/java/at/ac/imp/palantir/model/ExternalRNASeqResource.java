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
 * Entity implementation class for Entity: ExternalRNASeqResource
 *
 */
@Entity
public class ExternalRNASeqResource implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Collection<Reference> references = new ArrayList<Reference>();
	
	private Collection<ExternalRNASeqDatapoint> datapoints = new ArrayList<ExternalRNASeqDatapoint>();
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addDatapoint(ExternalRNASeqDatapoint datapoint) {
		datapoints.add(datapoint);
	}
	
	public Collection<Reference> getReferences() {
		return references;
	}

	public void setReference(Collection<Reference> references) {
		this.references = references;
	}
	
	public Collection<ExternalRNASeqDatapoint> getDatapoints() {
		return datapoints;
	}

	public void setDatapoints(Collection<ExternalRNASeqDatapoint> datapoints) {
		this.datapoints = datapoints;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public ExternalRNASeqResource() {
		super();
	}

	@Override
	public String toString() {
		return "ExternalRNASeqResource [id=" + id + ", name=" + name + "]";
	}
}
