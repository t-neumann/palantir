package at.ac.imp.palantir.exceptions;

public class DatabaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseException(String message) {
		super(message);
	}

	public DatabaseException(Throwable cause) {
		super(cause);
	}
	
	

}
