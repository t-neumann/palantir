package at.ac.imp.palantir.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class EssentialomeDatapoint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6196680738779339005L;
	
	@ManyToOne
	private EssentialomeEntry entry;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ScreenGene gene;
	
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

	public EssentialomeEntry getEntry() {
		return entry;
	}

	public void setEntry(EssentialomeEntry entry) {
		this.entry = entry;
	}

	public ScreenGene getGene() {
		return gene;
	}

	public void setGene(ScreenGene gene) {
		this.gene = gene;
	}

	public EssentialomeDatapoint() {
		
	}

	public EssentialomeDatapoint(float value) {
		super();
		this.value = value;
	}

	@Override
	public String toString() {
		return "EssentialomeDatapoint [value=" + this.value + "entry=" + entry.getId()
				+ ", id=" + this.getId() + "]";
	}

}
