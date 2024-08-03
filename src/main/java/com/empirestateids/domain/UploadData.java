package com.empirestateids.domain;

import org.springframework.web.multipart.MultipartFile;

public class UploadData {

	  private String fileType;
	  private MultipartFile   fileData;
	 
	  public String getFileType()
	  {
	    return fileType;
	  }
	 
	  public void setFileType(String fileType)
	  {
	    this.fileType = fileType;
	  }
	 
	  public MultipartFile   getFileData()
	  {
	    return fileData;
	  }
	 
	  public void setFileData(MultipartFile   fileData)
	  {
	    this.fileData = fileData;
	  }

}
