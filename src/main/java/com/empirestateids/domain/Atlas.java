/**
 * 
 */
package com.empirestateids.domain;

import java.io.Serializable;
import java.util.Map;

import net.ipowerlift.atlas.common.IConstants;

/**
 * @author Syed
 *
 */
public class Atlas implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getCodeDesc(String code, Map<String, String> map){
		if(code == null || code.trim().equals("")){
			return "Null";
		} else {
			return map.get(code);
		}
	}
	
    public static String getThumbnailURL(String path){
    	if(path==null || path.trim().equals("")){
    		return path;
    	}
    	
    	StringBuffer urlBufferPath = null;
    	String urlPath = path;
    	
    	try{
    		urlBufferPath = new StringBuffer(path);
    		urlBufferPath.insert(urlBufferPath.lastIndexOf("/")+1,IConstants.DIRECTORY_THUMBNAIL+"/");
    		urlPath = urlBufferPath.toString();
    	} catch(Exception e){
    		urlPath = path;
    	}
    	
    	return urlPath;
    }
    

    public static String getOriginalURL(String path){
    	if(path==null || path.trim().equals("")){
    		return path;
    	}
    	
    	StringBuffer urlBufferPath = null;
    	String urlPath = path;
    	
    	try{
    		urlBufferPath = new StringBuffer(path);
    		urlBufferPath.insert(urlBufferPath.lastIndexOf("/")+1,IConstants.DIRECTORY_ORIGINAL_IMAGE+"/");
    		urlPath = urlBufferPath.toString();
    	} catch(Exception e){
    		urlPath = path;
    	}
    	
    	return urlPath;
    }
}
