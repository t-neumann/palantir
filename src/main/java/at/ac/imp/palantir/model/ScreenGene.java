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
public class ScreenGene implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1754684594828869134L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String entrezId;
	private String geneSymbol;
	
	private String essential;
	private String pool;
	
	private String aliases;
	
	private String chrLocation;
	
	private String type;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Essentialome essentialome;

	@OneToMany(cascade = CascadeType.ALL)
	private Collection<Gene> genes = new ArrayList<Gene>();
	
	@OneToMany(cascade = CascadeType.ALL)
	private Collection<EssentialomeDatapoint> datapoints = new ArrayList<EssentialomeDatapoint>();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public String getEssential() {
		return essential;
	}

	public void setEssential(String essential) {
		this.essential = essential;
	}

	public String getPool() {
		return pool;
	}

	public void setPool(String pool) {
		this.pool = pool;
	}

	public String getAliases() {
		return aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}

	public String getChrLocation() {
		return chrLocation;
	}

	public void setChrLocation(String chrLocation) {
		this.chrLocation = chrLocation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Collection<Gene> getGenes() {
		return genes;
	}

	public void setGenes(Collection<Gene> genes) {
		this.genes = genes;
	}

	public Collection<EssentialomeDatapoint> getDatapoints() {
		return datapoints;
	}

	public void setDatapoints(Collection<EssentialomeDatapoint> datapoints) {
		this.datapoints = datapoints;
	}

	public void addDatapoint(EssentialomeDatapoint datapoint) {
		this.datapoints.add(datapoint);
	}
	
	public void addGene(Gene gene) {
		this.genes.add(gene);
	}

	public Essentialome getEssentialome() {
		return essentialome;
	}

	public void setEssentialome(Essentialome essentialome) {
		this.essentialome = essentialome;
	}

	public ScreenGene() {
		super();
	}

	public ScreenGene(String entrezId, String geneSymbol, String essential, String pool, String aliases,
			String chrLocation, String type) {
		super();
		this.entrezId = entrezId;
		this.geneSymbol = geneSymbol;
		this.essential = essential;
		this.pool = pool;
		this.aliases = aliases;
		this.chrLocation = chrLocation;
		this.type = type;
	}

	@Override
	public String toString() {
		return "ScreenGene [geneSymbol=" + geneSymbol + ", entrezId=" + entrezId + 
				", essential=" + essential + ", pool=" + pool +
				", aliases=" + aliases + ", chrLocation=" + chrLocation + 
				", type=" + type + "]";
	}

}
