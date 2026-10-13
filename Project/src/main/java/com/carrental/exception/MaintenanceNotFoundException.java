package com.carrental.exception;

public class MaintenanceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MaintenanceNotFoundException(String message) {
		super(message);
	}
}
