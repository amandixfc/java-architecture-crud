package com.mila.exceptions;

public class StudentNotFoundExcepction extends Exception {

	private static final long serialVersionUID = 7718828512143293558L;

	public StudentNotFoundExcepction() {
		super();
	}
	
	public StudentNotFoundExcepction(String message, Throwable cause) {
		super(message, cause);

	}

	public StudentNotFoundExcepction(String message) {
		super(message);
	}

	public StudentNotFoundExcepction(Throwable cause) {
		super(cause);
	}
	
	
}
