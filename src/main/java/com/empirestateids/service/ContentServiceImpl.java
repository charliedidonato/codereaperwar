/**
 * 
 */
package com.empirestateids.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.empirestateids.dao.LookupMapper;
import com.empirestateids.domain.Content;
import com.empirestateids.domain.Lookup;
import com.empirestateids.domain.LookupCriteria;
import com.empirestateids.security.UserInfo;
import com.empirestateids.utils.CommonUtil;

/**
 * @author Syed
 *
 */
@Service("ContentService")
public class ContentServiceImpl implements ContentService {

	static Logger logger = LogManager.getLogger(ContentServiceImpl.class);
	
	@Autowired
	private LookupMapper lookupMapper;
		
	/* (non-Javadoc)
	 * @see net.ipowerlift.atlas.service.ContentService#updateAtlasContent(java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateAtlasContent(Content content, UserInfo userInfo) throws Exception {
		int status;
		
		for(Lookup lookup:content.getContentList()){
			
			CommonUtil.setAuditInfo(lookup, userInfo, false, true);
			
			status = lookupMapper.updateByPrimaryKeySelective(lookup);
			
			if(logger.isDebugEnabled()) {
				logger.debug("Email update status: " + status);
			}
		}
	}

	/* (non-Javadoc)
	 * @see net.ipowerlift.atlas.service.ContentService#getAtlasContent()
	 */
	@Override
	public Content getAtlasContent() {
		Content content = new Content();
		
		LookupCriteria lookupCriteria = new LookupCriteria();
		lookupCriteria.createCriteria().andLookupNameEqualTo("AtlasContent");
		lookupCriteria.setOrderByClause("sort_order");
		
		List<Lookup> contentList = lookupMapper.selectByExample(lookupCriteria);
		
		content.setContentList(contentList);
		
		return content;
	}

}
