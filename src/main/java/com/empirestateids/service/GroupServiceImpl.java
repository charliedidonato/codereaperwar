/**
 * 
 */
package com.empirestateids.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.empirestateids.dao.GroupAuthorityMapper;
import com.empirestateids.dao.GroupsMapper;
import com.empirestateids.domain.Group;
import com.empirestateids.domain.GroupAuthority;
import com.empirestateids.domain.GroupAuthorityCriteria;
import com.empirestateids.domain.Groups;
import com.empirestateids.security.UserInfo;
import com.empirestateids.utils.CommonUtil;

/**
 * @author Syed
 *
 */
@Component
@Service("GroupService")
public class GroupServiceImpl implements GroupService {

	static Logger logger = LogManager.getLogger(GroupServiceImpl.class);
	
	@Autowired
	private GroupsMapper groupsMapper;
	
	@Autowired
	private GroupAuthorityMapper groupAuthorityMapper;
	
	/* (non-Javadoc)
	 * @see net.ipowerlift.atlas.service.GroupService#getGroup(java.lang.Integer)
	 */
	@Override
	public Group getGroup(Integer groupId) {
		Group group = new Group();		
		
		Groups groups = groupsMapper.selectByPrimaryKey(groupId);
		
		group.setGroupId(groupId);
		group.setGroupName(groups.getGroupName());
		
		GroupAuthorityCriteria criteria = new GroupAuthorityCriteria();
		criteria.createCriteria().andGroupIdEqualTo(groupId);
		List<GroupAuthority> groupAuthorityList = groupAuthorityMapper.selectByExample(criteria);
		
		String[] authorities = new String[groupAuthorityList.size()];
		int index=0;
		for(GroupAuthority groupAuthority: groupAuthorityList){
			authorities[index] = groupAuthority.getAuthority();
			index++;
		}
		
		group.setAuthorities(authorities);
		
		return group;
	}
	
	/* (non-Javadoc)
	 * @see net.ipowerlift.atlas.service.GroupService#updateGroup(net.ipowerlift.atlas.domain.Group)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateGroup(Group group, UserInfo userInfo) throws Exception {
		Groups groups = new Groups();
		GroupAuthority groupAuthority = new GroupAuthority();
		
		Integer groupId = group.getGroupId();
		
		int status;
		
		CommonUtil.setAuditInfo(groups, userInfo, false, true);
		
		CommonUtil.setAuditInfo(groupAuthority, userInfo);
		
		//groups
		groups.setGroupId(groupId);
		groups.setGroupName(group.getGroupName());
		
		status = groupsMapper.updateByPrimaryKeySelective(groups);
		
		if(logger.isDebugEnabled()) {
			logger.debug("Groups update status: " + status);
		}
		
		//groupAuthority
		groupAuthority.setGroupId(groupId);
		
		//clean up existing authorities
		GroupAuthorityCriteria criteria = new GroupAuthorityCriteria();
		criteria.createCriteria().andGroupIdEqualTo(groupId);
		status = groupAuthorityMapper.deleteByExample(criteria);
		
		if(logger.isDebugEnabled()) {
			logger.debug("GroupAuthority delete status: " + status);
		}
		
		//insert newly selected authorities
		for(String authority: group.getAuthorities()){
			groupAuthority.setAuthority(authority);
			
			status = groupAuthorityMapper.insertSelective(groupAuthority);
			
			if(logger.isDebugEnabled()) {
				logger.debug("GroupAuthority insert status: " + status);
			}
		}

	}

	/* (non-Javadoc)
	 * @see net.ipowerlift.atlas.service.GroupService#addGroup(net.ipowerlift.atlas.domain.Group)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addGroup(Group group, UserInfo userInfo) throws Exception {
		Groups groups = new Groups();
		GroupAuthority groupAuthority = new GroupAuthority();
		
		Integer groupId = null;
		
		int status;
		
		CommonUtil.setAuditInfo(groups, userInfo);
		CommonUtil.setAuditInfo(groupAuthority, userInfo);
		//set to ZERO for AUTO INCREMENT
		groupId = 0;
		if(logger.isDebugEnabled()) {
			logger.debug("groupId: " + groupId);
		}

		group.setGroupId(groupId);
		
		//groups
		groups.setGroupId(groupId);
		groups.setGroupName(group.getGroupName());
		
		status = groupsMapper.insertSelective(groups);
		
		if(logger.isDebugEnabled()) {
			logger.debug("Groups insert status: " + status);
		}
		//get the last insert id
		groupId = groupsMapper.getLastInsertId();
		groups.setGroupId(groupId);
		group.setGroupId(groupId);
		
		//groupAuthority
		groupAuthority.setGroupId(groupId);
		
		for(String authority: group.getAuthorities()){
			groupAuthority.setAuthority(authority);
			
			status = groupAuthorityMapper.insertSelective(groupAuthority);
			
			if(logger.isDebugEnabled()) {
				logger.debug("GroupAuthority insert status: " + status);
			}
		}
			
	}

	@Override
	public List<Group> getAllGroups() {
		List<Groups> groupsList = groupsMapper.selectByExample(null);
		
		List<Group> groupList = new ArrayList<Group>();
		
		for(Groups groups:groupsList){
			groupList.add(this.getGroup(groups.getGroupId()));
		}
		
		return groupList;
	}

}
