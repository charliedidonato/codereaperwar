package com.empirestateids.domain;

import java.io.Serializable;

public class RotateImage implements Serializable{
	
	
	private static final long serialVersionUID = 31L;
	
	private Integer pictureId;
	private Integer rotateDegrees; 
	private String thumbnailPath;
	private String thumbnailURL;
	private String fullImagePath;
	private Boolean selectedPic;
	
	 public Integer getPictureId() {
		 return this.pictureId;
	 }
	
	 public void setPictureId(Integer pictureId) {
		 this.pictureId = pictureId;
	 }
	
	 public Integer getRotateDegrees() {
		 return this.rotateDegrees;
	 }
	
	 public void setRotateDegrees(Integer rotateDegrees) {
		 this.rotateDegrees = rotateDegrees;
	 }	 	
	 
	 public String getThumbnailPath() {
		 return thumbnailPath;
	 }

	 public void setThumbnailPath(String thumbnailPath) {
		 this.thumbnailPath = thumbnailPath;
	 }	

	 public String getThumbnailURL() {
		 return thumbnailURL;
	 }

	 public void setThumbnailURL(String thumbnailURL) {
		 this.thumbnailURL = thumbnailURL;
	 }	
	 
	 public void setFullImagePath(String fullImagePath) {
		 this.fullImagePath = fullImagePath;
	 }	
	 
	 public String getFullImagePath() {
		 return fullImagePath;
	 }
	 
	 public void setSelectedPic(Boolean selectedPic) {
		 this.selectedPic = selectedPic;
	 }
	 
	 public Boolean getSelectedPic() {
		 return this.selectedPic;
	 }
	 
	 public String toString() {
	     StringBuffer s = new StringBuffer();
	     s.append("Hash Code:"+hashCode()+"\n");
	     s.append("Picture ID:"+pictureId+"\n");
	     s.append("Rotate Degrees:"+rotateDegrees+"\n");
	     s.append("Thumbnail Path:"+thumbnailPath+"\n");
	     s.append("Full Image Path:"+fullImagePath+"\n");
	     s.append("Selected:"+selectedPic+"\n");
	     return (s.toString());
		 
	 }
	 
    @Override
    public int hashCode() {
        final int prime = 29;
        int result = 7;
        result = prime * result + ((getPictureId() == null) ? 0 : getPictureId().hashCode());
        result = prime * result + ((getRotateDegrees() == null) ? 0 : getRotateDegrees().hashCode());
        result = prime * result + ((getThumbnailPath() == null) ? 0 : getThumbnailPath().hashCode());
        result = prime * result + ((getFullImagePath() == null) ? 0 : getFullImagePath().hashCode());
        result = prime * result + ((getSelectedPic() == null) ? 0 : getSelectedPic().hashCode());
        return result;
    }

}
