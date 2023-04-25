package fr.uga.bib;

class TypeException extends RuntimeException {
	public TypeException() {

	}

	public TypeException(String message) {
		super(message);
	}

	public TypeException(Throwable cause) {
		super(cause);
	}

	public TypeException(String message, Throwable cause) {
		super(message, cause);
	}
}
