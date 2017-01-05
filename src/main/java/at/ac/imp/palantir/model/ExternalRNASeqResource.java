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
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<ExternalRNASeqEntry> entries = new ArrayList<ExternalRNASeqEntry>();
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<GenericGene> genes = new ArrayList<GenericGene>();
		
	private String name;
	
	public Collection<GenericGene> getGenes() {
		return genes;
	}

	public void setReferences(Collection<GenericGene> genes) {
		this.genes = genes;
	}
	
	public void addGenericGene(GenericGene gene) {
		this.genes.add(gene);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addEntry(ExternalRNASeqEntry entry) {
		entries.add(entry);
	}
	
	public Collection<ExternalRNASeqEntry> getEntries() {
		return entries;
	}

	public void setEntries(Collection<ExternalRNASeqEntry> entries) {
		this.entries = entries;
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
