/**
 * 
 */
package com.empirestateids.domain;

import java.util.Date;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author Syed
 * @author DiDonato
 *
 */
public class TestBean {
	private String eventName; 
	private Date eventDate;
	private MultipartFile  multiPartFile;
	
	/**
	 * @return the eventDate
	 */
	public Date getEventDate() {
		return eventDate;
	}
	/**
	 * @param eventDate the eventDate to set
	 */
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	/**
	 * @return the multiPartFile
	 */
	public MultipartFile  getMultiPartFile() {
		return multiPartFile;
	}
	/**
	 * @param multiPartFile the multiPartFile to set
	 */
	public void setMultiPartFile(MultipartFile  multiPartFile) {
		this.multiPartFile = multiPartFile;
	}
	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}
	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
}
