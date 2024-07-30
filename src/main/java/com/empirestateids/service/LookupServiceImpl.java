/**
 * 
 */
package com.empirestateids.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empirestateids.dao.LookupMapper;
import com.empirestateids.dao.MiscLookupMapper;
import com.empirestateids.domain.Lookup;
import com.empirestateids.domain.LookupCriteria;

/**
 * @author Syed
 *
 */
@Service("LookupService")
public class LookupServiceImpl implements LookupService {
	static Logger logger = LogManager.getLogger(LookupServiceImpl.class);
	
	@Autowired
	private LookupMapper lookupMapper;
	
	@Autowired
	private MiscLookupMapper miscLookupMapper;
	
	/* (non-Javadoc)
	 * @see net.ipowerlift.atlas.service.common.LookupService#getLookup(java.lang.String)
	 */
	@Override
	public Map<String, String> getLookup(String lookupName,String sortOrder) {
		Map<String,String> lookupMap = new LinkedHashMap<String,String>();
		if(logger.isDebugEnabled()) {
			logger.debug("getLookup for: " + lookupName);
		}
		
		LookupCriteria lookupCriteria = new LookupCriteria();
		lookupCriteria.createCriteria().andLookupNameEqualTo(lookupName);
		if (sortOrder==null){
			lookupCriteria.setOrderByClause("sort_order");	
		}else {
			lookupCriteria.setOrderByClause("value");
		}
		
		
		List<Lookup> lookupList = lookupMapper.selectByExample(lookupCriteria);
		
		for(Lookup lookup: lookupList){
			lookupMap.put(lookup.getCode(), lookup.getValue());
		}
		
		return lookupMap;
	}
	
	@Override
	public Map<String, String> getLookup(String lookupName) {
		return getLookup(lookupName,null); 
		
	}

	@Override
	public Map<String, String> getGroups() {
		Map<String,String> lookupMap = new LinkedHashMap<String,String>();
		List<Lookup> lookupList = miscLookupMapper.getGroups();
		
		for(Lookup lookup: lookupList){
			lookupMap.put(lookup.getCode(), lookup.getValue());
		}
		
		return lookupMap;
	}
	
	@Override
	public Map<String, String> getLatestArticles() {
		Map<String,String> lookupMap = new LinkedHashMap<String,String>();
		List<Lookup> lookupList = miscLookupMapper.getLatestArticles();
		
		for(Lookup lookup: lookupList){
			lookupMap.put(lookup.getCode(), lookup.getValue());
		}
		
		return lookupMap;
	}

	@Override
	public Map<String, String> getLatestGalleries() {
		Map<String,String> lookupMap = new LinkedHashMap<String,String>();
		List<Lookup> lookupList = miscLookupMapper.getLatestGalleries();
		
		for(Lookup lookup: lookupList){
			lookupMap.put(lookup.getCode(), lookup.getValue());
		}
		
		return lookupMap;
	}
	
	@Override
	public Map<String, String> getFederations() {
		Map<String,String> lookupMap = new LinkedHashMap<String,String>();
		List<Lookup> lookupList = miscLookupMapper.getFederations();
		
		for(Lookup lookup: lookupList){
			lookupMap.put(lookup.getCode(), lookup.getValue());
		}
		
		return lookupMap;
	}
}
