package at.ac.imp.palantir.controller;

import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.model.Gene;
import at.ac.imp.palantir.model.Reference;

public interface GeneHandler {
	
	public Reference getReferenceByName(String name) throws DatabaseException;
	
	public Gene getGeneByName(String name) throws DatabaseException;
}
