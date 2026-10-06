package com.carrental.exception;

public class CarNotAvailableException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CarNotAvailableException(String message) {
        super(message);
    }
}