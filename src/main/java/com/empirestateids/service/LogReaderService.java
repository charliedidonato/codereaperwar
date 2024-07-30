package com.empirestateids.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface LogReaderService {
	
	public void setFileName(File name) 
		throws FileNotFoundException,IllegalArgumentException,Exception;

	public String getLogData() 
		throws IOException;
	
}
