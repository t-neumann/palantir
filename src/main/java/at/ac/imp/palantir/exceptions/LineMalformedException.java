package at.ac.imp.palantir.exceptions;

public class LineMalformedException extends Exception {

	private static final long serialVersionUID = 1L;

	public LineMalformedException() {
		super();
	}

	public LineMalformedException(String message, Throwable cause) {
		super(message, cause);
	}

	public LineMalformedException(String message) {
		super(message);
	}

	public LineMalformedException(Throwable cause) {
		super(cause);
	}

}
