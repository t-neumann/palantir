package at.ac.imp.palantir.model;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.*;

import at.ac.imp.palantir.model.Datapoint;

/**
 * Entity implementation class for Entity: ExpressionValue
 *
 */
@Entity
@DiscriminatorValue(value = "ExpressionValue")
public class ExpressionValue extends Datapoint implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int count;
	
	private double rpkm;
	
	private double tpm;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getRpkm() {
		return rpkm;
	}

	public void setRpkm(double rpkm) {
		this.rpkm = rpkm;
	}

	public double getTpm() {
		return tpm;
	}

	public void setTpm(double tpm) {
		this.tpm = tpm;
	}

	public ExpressionValue() {
		super();
	}

	public ExpressionValue(int count, double rpkm, double tpm) {
		super();
		this.count = count;
		this.rpkm = rpkm;
		this.tpm = tpm;
	}

	@Override
	public String toString() {
		return "ExpressionValue [count=" + count + ", rpkm=" + rpkm + ", tpm=" + tpm + ", result=" + result.getId()
				+ ", id=" + getId() + "]";
	}
}
