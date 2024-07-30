package com.empirestateids.domain;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadData {

	  private String fileType;
	  private CommonsMultipartFile fileData;
	 
	  public String getFileType()
	  {
	    return fileType;
	  }
	 
	  public void setFileType(String fileType)
	  {
	    this.fileType = fileType;
	  }
	 
	  public CommonsMultipartFile getFileData()
	  {
	    return fileData;
	  }
	 
	  public void setFileData(CommonsMultipartFile fileData)
	  {
	    this.fileData = fileData;
	  }

}
