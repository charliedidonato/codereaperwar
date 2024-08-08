package com.empirestateids.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author Charlie D
 *
 */
@Component
@Service("LogReaderService")
public class LogReaderServiceImpl implements LogReaderService {

    public LogReaderServiceImpl() {
    
    }
	
	private File logFileName;
	private String eol = "\n";
    private StringBuffer data = new StringBuffer("");
    
	/**
	 * @param a File to read
	 * 
	 */
	@Override
	public void setFileName(File fileName)
		throws FileNotFoundException,IllegalArgumentException,Exception {
		if (fileName==null){
			throw new IllegalArgumentException("Filename argument:\""
		   +fileName+"\" is null.");
		}
		if (!fileName.exists()){
			throw new FileNotFoundException("Filename:\""
		    +fileName.getAbsolutePath()+"\" does not exist.");
		}
		if (!fileName.canRead()){
			throw new Exception("Filename:\""
		    +fileName.getAbsolutePath()+"\" no read access.");
		}
		logFileName = fileName;
	}
	
	/**
	 * @param Writer - a Writer for character output to a screen 
	 * or file for that matter.
	 * 
	 */
	public String getLogData()
		throws IOException {
        FileReader in = new FileReader(logFileName);
        BufferedReader br = new BufferedReader(in);
        String line = "";
        while ((line = br.readLine()) != null){
        	data.append(line+eol);
        }
        br.close();
        in.close();
        return(data.toString());
    }
	
	/**
	 * @param Writer - a Writer for character output to a screen 
	 * or file for that matter.
	 * 
	 */
	public String outputLogData(PrintWriter out)
		throws IOException {
        FileReader in = new FileReader(logFileName);
        BufferedReader br = new BufferedReader(in);
        String line = "";
        while ((line = br.readLine()) != null){
        	out.println(line+eol);
        }
        br.close();
        in.close();
        return(data.toString());
    }	
}
