package at.ac.imp.palantir.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

/**
 * Entity implementation class for Entity: Datapoint
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DATAPOINT_TYPE")
public abstract class Datapoint implements Serializable, Comparable<Datapoint> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	protected Gene gene;
	
	@ManyToOne(cascade=CascadeType.ALL)
	protected Result result;

	public Datapoint() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Gene getGene() {
		return gene;
	}

	public void setGene(Gene gene) {
		this.gene = gene;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
	@Override
	public int compareTo(Datapoint o) {
		Gene otherGene = o.getGene();
		return this.gene.compareTo(otherGene);
	}
}
