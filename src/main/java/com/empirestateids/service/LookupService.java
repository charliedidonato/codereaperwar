/**
 * 
 */
package com.empirestateids.service;

import java.util.Map;

/**
 * @author Syed
 *
 */
public interface LookupService {
	public Map<String,String> getLookup(String lookupName);
	public Map<String,String> getLookup(String lookupName,String sortOrder);
	public Map<String,String> getGroups();
	public Map<String, String> getLatestArticles();
	public Map<String, String> getLatestGalleries();
	public Map<String, String> getFederations();
}
