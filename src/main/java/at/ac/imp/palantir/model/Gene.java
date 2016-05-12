package at.ac.imp.palantir.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Gene
 *
 */
@Entity
@NamedQuery(name="Gene.findByName",query="SELECT g FROM Gene g WHERE g.geneSymbol = :name")
public class Gene implements Serializable, Comparable<Gene> {
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String chr;
	private int start;
	private int end;
	
	private String geneSymbol;
	private String strand;
	private String entrezId;
	
	private String anno;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Reference reference;
	
	// Review fetch type
//	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
//	protected Collection<Datapoint> datapoints;

//	public Collection<Datapoint> getDatapoints() {
//		return datapoints;
//	}
//
//	public void setDatapoints(Collection<Datapoint> datapoints) {
//		this.datapoints = datapoints;
//	}
//	
//	public void addDatapoint(Datapoint datapoint) {
//		this.datapoints.add(datapoint);
//	}

	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public String getChr() {
		return chr;
	}

	public void setChr(String chr) {
		this.chr = chr;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

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
	
	public String getStrand() {
		return strand;
	}

	public void setStrand(String strand) {
		this.strand = strand;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Gene() {
		super();
	}

	public Gene(String chr, int start, int end, String geneSymbol, String strand, String entrezId, String anno) {
		super();
		this.chr = chr;
		this.start = start;
		this.end = end;
		this.geneSymbol = geneSymbol;
		this.strand = strand;
		this.entrezId = entrezId;
		this.anno = anno;
	}

	@Override
	public String toString() {
		return "Gene [chr=" + chr + ", start=" + start + ", end=" + end + ", geneSymbol=" + geneSymbol + ", reverse="
				+ strand + ", entrezId=" + entrezId + ", reference=" + reference + ", anno=" + anno + "]";
	}

	@Override
	public int compareTo(Gene o) {
		String otherSymbol = o.getGeneSymbol();		
		return this.geneSymbol.compareTo(otherSymbol);
	}
}