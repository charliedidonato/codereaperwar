/**
 * 
 */
package com.empirestateids.service;


import java.util.List;

import com.empirestateids.domain.Group;
import com.empirestateids.security.UserInfo;

/**
 * @author Syed
 *
 */
public interface GroupService {

	public Group getGroup(Integer groupId);
	
	public void updateGroup(Group group, UserInfo userInfo) throws Exception;

	public void addGroup(Group group, UserInfo userInfo) throws Exception;

	public List<Group> getAllGroups();
	
}
