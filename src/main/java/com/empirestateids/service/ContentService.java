/**
 * 
 */
package com.empirestateids.service;


import com.empirestateids.domain.Content;
import com.empirestateids.security.UserInfo;

/**
 * @author Syed
 *
 */
public interface ContentService {

	public void updateAtlasContent(Content content, UserInfo userInfo) throws Exception;
	
	public Content getAtlasContent();
	
}
