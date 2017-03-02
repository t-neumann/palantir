package at.ac.imp.palantir.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Gene
 *
 */
@Entity
public class GenericGene implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String entrezId;
	private String geneSymbol;
	private String organism;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ExternalRNASeqResource> resources = new ArrayList<ExternalRNASeqResource>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Collection<Gene> genes = new ArrayList<Gene>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Collection<ExternalRNASeqDatapoint> datapoints = new ArrayList<ExternalRNASeqDatapoint>();
	
	public String getGeneSymbol() {
		return geneSymbol;
	}

	public void setGeneSymbol(String geneSymbol) {
		this.geneSymbol = geneSymbol;
	}

	public String getEntrezId() {
		return entrezId;
	}

	public void setEntrezId(String entrezId) {
		this.entrezId = entrezId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrganism() {
		return organism;
	}

	public void setOrganism(String organism) {
		this.organism = organism;
	}

	public List<ExternalRNASeqResource> getResources() {
		return resources;
	}

	public void setResources(List<ExternalRNASeqResource> resources) {
		this.resources = resources;
	}
	
	public void addResource(ExternalRNASeqResource resource) {
		this.resources.add(resource);
	}

	public Collection<Gene> getGenes() {
		return genes;
	}

	public void setGenes(Collection<Gene> genes) {
		this.genes = genes;
	}

	public Collection<ExternalRNASeqDatapoint> getDatapoints() {
		return datapoints;
	}

	public void setDatapoints(Collection<ExternalRNASeqDatapoint> datapoints) {
		this.datapoints = datapoints;
	}

	public void addDatapoint(ExternalRNASeqDatapoint datapoint) {
		this.datapoints.add(datapoint);
	}
	
	public void addGene(Gene gene) {
		this.genes.add(gene);
	}

	public GenericGene() {
		super();
	}

	public GenericGene(String entrezId, String geneSymbol) {
		super();
		this.geneSymbol = geneSymbol;
		this.entrezId = entrezId;
	}

	@Override
	public String toString() {
		return "Gene [geneSymbol=" + geneSymbol + ", entrezId=" + entrezId + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericGene other = (GenericGene) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
