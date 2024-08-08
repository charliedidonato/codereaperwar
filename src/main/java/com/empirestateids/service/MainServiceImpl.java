/**
 * 
 */
package com.empirestateids.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.empirestateids.dao.LookupMapper;
import com.empirestateids.domain.Lookup;
import com.empirestateids.domain.LookupCriteria;
import com.empirestateids.domain.MainContent;

/**
 * @author DiDonato
 *
 */
@Component
@Service("MainService")
public class MainServiceImpl implements MainService {

	static Logger logger = LogManager.getLogger(MainServiceImpl.class);
	
	@Autowired
	private LookupMapper lookupMapper;

	@Override
	public MainContent getMainContent(MainContent mainContent) {
		
		LookupCriteria lookupCriteria = new LookupCriteria();
		lookupCriteria.createCriteria().andLookupNameEqualTo("AtlasContent");
		lookupCriteria.setOrderByClause("sort_order");		
		List<Lookup> contentList = lookupMapper.selectByExample(lookupCriteria);
		return mainContent;
	}

}
