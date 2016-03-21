package at.ac.imp.palantir.controller;

import at.ac.imp.palantir.entities.Reference;
import at.ac.imp.palantir.exceptions.DatabaseException;

public interface GeneHandler {
	
	public Reference getReferenceByName(String name) throws DatabaseException;
}
