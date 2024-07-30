package com.empirestateids.exception;

public class GenericException extends RuntimeException{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String customMsg;
	
	public GenericException() {
		super();
	}
	
	public GenericException(String customMsg) {
		super(customMsg);
		this.customMsg = customMsg;
	}

	public GenericException(String customMsg, Throwable t) {
		super(customMsg, t);
		this.customMsg = customMsg;
	}

	public String getCustomMsg() {
		return customMsg;
	}

	public void setCustomMsg(String customMsg) {
		this.customMsg = customMsg;
	}
 
}