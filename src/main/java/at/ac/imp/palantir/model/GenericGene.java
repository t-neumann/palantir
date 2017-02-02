package at.ac.imp.palantir.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

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
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ExternalRNASeqResource resource;

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
	
	public ExternalRNASeqResource getResource() {
		return resource;
	}

	public void setResource(ExternalRNASeqResource resource) {
		this.resource = resource;
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
}
