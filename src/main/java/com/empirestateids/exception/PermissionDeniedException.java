package com.empirestateids.exception;

public class PermissionDeniedException extends RuntimeException{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String customMsg;
	
	public PermissionDeniedException() {
		super();
	}
	
	public PermissionDeniedException(String customMsg) {
		super(customMsg);
		this.customMsg = customMsg;
	}

	public PermissionDeniedException(String customMsg, Throwable t) {
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