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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
public class ExternalRNASeqDatapoint implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private ExternalRNASeqEntry entry;
	
	@ManyToMany(cascade = CascadeType.ALL)
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
	
	private String entrezId;

	private float value;
	
	public String getEntrezId() {
		return entrezId;
	}
	
	public void setEntrezId(String entrezId) {
		this.entrezId = entrezId;
	}
	
	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
	
	public void addGene(Gene gene) {
		genes.add(gene);
	}
	
	public Collection<Gene> getGenes() {
		return genes;
	}

	public void setGenes(Collection<Gene> genes) {
		this.genes = genes;
	}

	public ExternalRNASeqEntry getEntry() {
		return entry;
	}

	public void setEntry(ExternalRNASeqEntry entry) {
		this.entry = entry;
	}

	public ExternalRNASeqDatapoint() {
		
	}

	public ExternalRNASeqDatapoint(String entrezId, float value) {
		super();
		this.entrezId = entrezId;
		this.value = value;
	}

	@Override
	public String toString() {
		return "ExternalRNASeqDatapoint [value=" + this.value + "entry=" + entry.getId()
				+ ", id=" + this.getId() + "]";
	}
}