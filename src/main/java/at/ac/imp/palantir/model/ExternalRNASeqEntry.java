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
import javax.persistence.OneToMany;

@Entity
public class ExternalRNASeqEntry implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1120421267091583263L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private ExternalRNASeqResource resource;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Collection<ExternalRNASeqDatapoint> datapoints = new ArrayList<ExternalRNASeqDatapoint>();
	
	private String name;
	private String context;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public ExternalRNASeqResource getResource() {
		return resource;
	}

	public void setResource(ExternalRNASeqResource resource) {
		this.resource = resource;
	}

	public void addDatapoint(ExternalRNASeqDatapoint datapoint) {
		datapoints.add(datapoint);
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
	
	public ExternalRNASeqEntry() {
		super();
	}

	@Override
	public String toString() {
		return "ExternalRNASeqEntry [id=" + id + ", name=" + name + ", context=" + context + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExternalRNASeqEntry other = (ExternalRNASeqEntry) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
