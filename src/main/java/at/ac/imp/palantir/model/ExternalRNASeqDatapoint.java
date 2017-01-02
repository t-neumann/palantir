package at.ac.imp.palantir.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
public class ExternalRNASeqDatapoint implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private ExternalRNASeqResource resource;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Collection<Gene> genes = new ArrayList<Gene>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	private int entrezId;

	private float value;
	
	public int getEntrezId() {
		return entrezId;
	}
	
	public void setEntrezId(int entrezId) {
		this.entrezId = entrezId;
	}
	
	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
	
	public ExternalRNASeqDatapoint() {
		
	}

	public ExternalRNASeqDatapoint(int entrezId, float value) {
		super();
		this.entrezId = entrezId;
		this.value = value;
	}

	@Override
	public String toString() {
		return "ExternalRNASeqDatapoint [value=" + this.value + "resource=" + resource.getId()
				+ ", id=" + this.getId() + "]";
	}
}