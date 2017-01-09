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
public class EssentialomeEntry implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6436535808068371340L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Essentialome essentialome;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<EssentialomeDatapoint> datapoints = new ArrayList<EssentialomeDatapoint>();
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Essentialome getEssentialome() {
		return essentialome;
	}

	public void setEssentialome(Essentialome essentialome) {
		this.essentialome = essentialome;
	}

	public void addDatapoint(EssentialomeDatapoint datapoint) {
		datapoints.add(datapoint);
	}
	
	public Collection<EssentialomeDatapoint> getDatapoints() {
		return datapoints;
	}

	public void setDatapoints(Collection<EssentialomeDatapoint> datapoints) {
		this.datapoints = datapoints;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public EssentialomeEntry() {
		super();
	}

	@Override
	public String toString() {
		return "EssentialomeEntry [id=" + id + ", name=" + name + "]";
	}


}
