package com.empirestateids.domain;

import java.io.Serializable;

public class UploadStatus extends Atlas 
	implements Serializable {
	
    private static final long serialVersionUID = 47L;
	
	private String errors = "";
	private int rowCount = 0;
	
	public UploadStatus() {
		super();
	}
	
	public String getErrors() {
		return errors;
	}
	
	public void setErrors(String errors) {
		this.errors = errors;
	}
	
	public int getRowCount() {
		return rowCount;
	}
	
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

}
