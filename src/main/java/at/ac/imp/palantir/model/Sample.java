package at.ac.imp.palantir.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Entity implementation class for Entity: Sample
 *
 */
@Entity

public class Sample implements Serializable, Comparable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	
	@OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private QueueSampleMetaInfo metaInfo;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<Alignment> alignments = new ArrayList<Alignment>();

	public Sample() {
		super();
	}
	
	public Sample(int id) {
		super();
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void addAlignment(final Alignment alignment) {
		alignments.add(alignment);
	}

	public Collection<Alignment> getAlignments() {
		return alignments;
	}

	public void setAlignments(Collection<Alignment> alignments) {
		this.alignments = alignments;
	}
	
	public QueueSampleMetaInfo getMetaInfo() {
		return metaInfo;
	}

	public void setMetaInfo(QueueSampleMetaInfo metaInfo) {
		this.metaInfo = metaInfo;
	}

	@Override
	public int compareTo(Object o) {
		if (!(o instanceof Sample))
		      throw new ClassCastException("A Sample object expected.");
		int otherId = ((Sample)o).getId();
		if (otherId > this.id) {
			return -1;
		} else if (otherId == this.id) {
			return 0;
		} else {
			return 1;
		}
	}   
}
