package com.empirestateids.exception;

public class BadFileFormatException extends RuntimeException{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String customMsg;
	
	public BadFileFormatException() {
		super();
	}
	
	public BadFileFormatException(String customMsg) {
		super(customMsg);
		this.customMsg = customMsg;
	}

	public BadFileFormatException(String customMsg, Throwable t) {
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