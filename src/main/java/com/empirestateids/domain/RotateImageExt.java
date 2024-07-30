package com.empirestateids.domain;

import java.io.Serializable;
import java.util.List;

public class RotateImageExt implements Serializable{


	private static final long serialVersionUID = 31L;


	private List<RotateImage> imageList;

	public List<RotateImage> getImageList() {
		return this.imageList;
	}

	public void setImageList(List<RotateImage> imageList) {
		this.imageList = imageList;
	}


	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("Hash Code:"+hashCode()+"\n");
		s.append("Rotate Image List:"+imageList+"\n");
		return (s.toString());

	}

	@Override
	public int hashCode() {
		final int prime = 29;
		int result = 7;
		result = prime * result + ((getImageList() == null) ? 0 : getImageList().hashCode());
		return result;
	}

}
