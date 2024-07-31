package com.empirestateids.domain;

import org.springframework.web.multipart.support.StandardServletMultipartResolver;

public class UploadData {

	  private String fileType;
	  private StandardServletMultipartResolver fileData;
	 
	  public String getFileType()
	  {
	    return fileType;
	  }
	 
	  public void setFileType(String fileType)
	  {
	    this.fileType = fileType;
	  }
	 
	  public StandardServletMultipartResolver getFileData()
	  {
	    return fileData;
	  }
	 
	  public void setFileData(StandardServletMultipartResolver fileData)
	  {
	    this.fileData = fileData;
	  }

}
