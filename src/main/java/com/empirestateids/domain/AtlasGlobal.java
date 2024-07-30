/**
 * 
 */
package com.empirestateids.domain;

import java.io.Serializable;

import javax.servlet.ServletContext;

/**
 * @author mxs34
 *
 */
public class AtlasGlobal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static ServletContext servletContext = null;
	
	public static boolean localEmail = true;
	
	public static String catalinaServername;
	
	public static String stage="prod";
}
