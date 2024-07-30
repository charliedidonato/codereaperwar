package com.empirestateids.domain;

public class RowsCreatedOrUpdated {

	int rowsCreated = -1;

	int rowsUpdated = -1;
	
	public RowsCreatedOrUpdated() {
		rowsCreated = 0;
		rowsUpdated = 0;
	}
	
	public RowsCreatedOrUpdated addCreated(RowsCreatedOrUpdated obj) {
		obj.rowsCreated = obj.rowsCreated + 1;
	    return obj;
	}
	
	public RowsCreatedOrUpdated addUpdated(RowsCreatedOrUpdated obj) {
		obj.rowsUpdated = obj.rowsUpdated + 1;
	    return obj;
	}
	
	public int getRowsCreated() {
		return rowsCreated;
	}

	public void setRowsCreated(int rowsCreated) {
		this.rowsCreated = rowsCreated;
	}

	public int getRowsUpdated() {
		return rowsUpdated;
	}

	public void setRowsUpdated(int rowsUpdated) {
		this.rowsUpdated = rowsUpdated;
	}
	
	@Override
	public String toString() {
		return "RowsCreatedOrUpdated [rowsCreated=" + rowsCreated + ", rowsUpdated=" + rowsUpdated + "]";
	}
	
}
