/**
 * 
 */
package com.empirestateids.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Syed
 *
 */
public class Content implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<Lookup> contentList = new ArrayList<Lookup>();

	/**
	 * @return the contentList
	 */
	public List<Lookup> getContentList() {
		return contentList;
	}

	/**
	 * @param contentList the contentList to set
	 */
	public void setContentList(List<Lookup> contentList) {
		this.contentList = contentList;
	}
	
	
}
