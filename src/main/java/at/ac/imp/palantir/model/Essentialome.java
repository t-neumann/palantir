package at.ac.imp.palantir.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Essentialome
 *
 */
@Entity
@NamedQuery(name="Essentialome.findByName",query="SELECT e FROM Essentialome e WHERE e.name = :name")
public class Essentialome implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1970831542291382222L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<EssentialomeEntry> entries = new ArrayList<EssentialomeEntry>();
	
	@ManyToMany(cascade = CascadeType.ALL)
	private Collection<ScreenGene> genes = new ArrayList<ScreenGene>();
		
	private String name;
	
	private boolean valid = false;
	
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Collection<ScreenGene> getGenes() {
		return genes;
	}

	public void setReferences(Collection<ScreenGene> genes) {
		this.genes = genes;
	}
	
	public void addScreenGene(ScreenGene gene) {
		this.genes.add(gene);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addEntry(EssentialomeEntry entry) {
		entries.add(entry);
	}
	
	public Collection<EssentialomeEntry> getEntries() {
		return entries;
	}

	public void setEntries(Collection<EssentialomeEntry> entries) {
		this.entries = entries;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Essentialome() {
		super();
	}
	
	@Override
	public String toString() {
		return "Essentialome [id=" + id + ", name=" + name + ", valid=" + valid + "]";
	}
}
