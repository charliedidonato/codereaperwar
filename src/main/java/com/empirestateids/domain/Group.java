/**
 * 
 */
package com.empirestateids.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Syed
 *
 */
public class Group implements Serializable {
	
	private Integer groupId;
	
	@NotEmpty
    @Size(max=50)
	private String groupName;
	
	@Size(min=1, message="Select atleast one Authority")
	private String[] authorities;
	
	/**
	 * @return the groupId
	 */
	public Integer getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the authorities
	 */
	public String[] getAuthorities() {
		return authorities;
	}
	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(String[] authorities) {
		this.authorities = authorities;
	}
	
}
