package at.ac.imp.palantir.controller;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import at.ac.imp.palantir.entities.Datapoint;
import at.ac.imp.palantir.entities.Gene;
import at.ac.imp.palantir.exceptions.DatabaseException;

@Named("blabla")
@ViewScoped
//@EJB(name="blabla")
public class BasicView implements Serializable {
	
	private Collection<Datapoint> datapoints;
	
	//@Inject
	@EJB
	private GeneHandler handler;
	
	@PostConstruct
	public void init() {
		try {
			datapoints = handler.getGeneByName("BRCA1").getDatapoints();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Collection<Datapoint> getDatapoints() {
		return datapoints;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
