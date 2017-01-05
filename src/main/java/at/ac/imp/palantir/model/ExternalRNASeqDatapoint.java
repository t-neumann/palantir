package at.ac.imp.palantir.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
public class ExternalRNASeqDatapoint implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private ExternalRNASeqEntry entry;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private GenericGene gene;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	private float value;

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public ExternalRNASeqEntry getEntry() {
		return entry;
	}

	public void setEntry(ExternalRNASeqEntry entry) {
		this.entry = entry;
	}

	public GenericGene getGene() {
		return gene;
	}

	public void setGene(GenericGene gene) {
		this.gene = gene;
	}

	public ExternalRNASeqDatapoint() {
		
	}

	public ExternalRNASeqDatapoint(float value) {
		super();
		this.value = value;
	}

	@Override
	public String toString() {
		return "ExternalRNASeqDatapoint [value=" + this.value + "entry=" + entry.getId()
				+ ", id=" + this.getId() + "]";
	}
}