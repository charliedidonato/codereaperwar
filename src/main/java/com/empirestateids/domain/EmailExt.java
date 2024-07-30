/**
 * 
 */
package com.empirestateids.domain;

import java.io.Serializable;

/**
 * @author Syed
 *
 */
public class EmailExt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String subject;
	private String htmlMessage;
	private String textMessage;
	
	private String responseMessage;

	/**
	 * @return the htmlMessage
	 */
	public String getHtmlMessage() {
		return htmlMessage;
	}

	/**
	 * @param htmlMessage the htmlMessage to set
	 */
	public void setHtmlMessage(String htmlMessage) {
		this.htmlMessage = htmlMessage;
	}

	/**
	 * @return the textMessage
	 */
	public String getTextMessage() {
		return textMessage;
	}

	/**
	 * @param textMessage the textMessage to set
	 */
	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}

	/**
	 * @return the responseMessage
	 */
	public String getResponseMessage() {
		return responseMessage;
	}

	/**
	 * @param responseMessage the responseMessage to set
	 */
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
}
