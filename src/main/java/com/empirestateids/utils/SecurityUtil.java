/**
 * 
 */
package com.empirestateids.utils;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import net.ipowerlift.atlas.security.UserInfo;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Syed
 * 
 */
public class SecurityUtil {

	public static String encryptPassword(String password, Integer salt) {
		ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder();
		String encodedPassword = passwordEncoder.encodePassword(password, salt);
		return encodedPassword;
	}

	/**
	 * Check if a role is present in the logged in user
	 * 
	 * @param role
	 *            required authority
	 * @return true if role is present in list of authorities assigned to
	 *         current user, false otherwise
	 */
	public static boolean hasRole(String role) {
		// get security context from thread local
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null)
			return false;

		Authentication authentication = context.getAuthentication();
		if (authentication == null)
			return false;

		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (role.equals(auth.getAuthority()))
				return true;
		}

		return false;
	}

	/**
	 * Get info about currently logged in user
	 * 
	 * @return UserDetails if found in the context, null otherwise
	 */
	public static UserDetails getUserDetails() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		return userDetails;
	}

	/**
	 * Get info about currently logged in user
	 * 
	 * @return UserInfo if found in the session, null otherwise
	 */
	public static UserInfo getUserInfo(HttpServletRequest request) {
		return (UserInfo) request.getSession(true).getAttribute("userInfo");
	}
	
	/**
	 * Check if a role is present in the authorities of current user
	 * 
	 * @param authorities
	 *            all authorities assigned to current user
	 * @param role
	 *            required authority
	 * @return true if role is present in list of authorities assigned to
	 *         current user, false otherwise
	 */
	public static boolean isRolePresent(Collection<GrantedAuthority> authorities, String role) {
		boolean isRolePresent = false;
		for (GrantedAuthority grantedAuthority : authorities) {
			isRolePresent = grantedAuthority.getAuthority().equals(role);
			if (isRolePresent)
				break;
		}
		return isRolePresent;
	}
	
	/*
	 * Is given role present in the userInfo object
	 */
	public static boolean isRolePresent(UserInfo userInfo, String role) {
		boolean isRolePresent = false;
		
		Collection<GrantedAuthority> authorities = userInfo.getAuthorities();
		
		for (GrantedAuthority grantedAuthority : authorities) {
			isRolePresent = grantedAuthority.getAuthority().equals(role);
			if (isRolePresent)
				break;
		}
		return isRolePresent;
	}
	
	/*
	 * Is any role from the string array present in the userInfo object
	 */
	public static boolean isAnyRolePresent(UserInfo userInfo, String[] roles) {
		boolean isRolePresent = false;
		
		Collection<GrantedAuthority> authorities = userInfo.getAuthorities();
		
		for (GrantedAuthority grantedAuthority : authorities) {
			for(String role: roles){
				isRolePresent = grantedAuthority.getAuthority().equals(role);
				if (isRolePresent)
					break;
			}
			if (isRolePresent)
				break;
		}
		return isRolePresent;
	}
	
	/*
	 * Are all roles from the string array present in the userInfo object
	 */
	public static boolean isAllRolesPresent(UserInfo userInfo, String[] roles) {
		boolean isRolePresent = false;
		for(String role: roles){
			isRolePresent = SecurityUtil.isRolePresent(userInfo, role);
			if (!isRolePresent)
				break;
		}
		return isRolePresent;
	}
	
	/*
	 * Generate random password of specified length
	 */
	public static String generateRandomPassword(int length){
		return RandomStringUtils.randomAlphanumeric(length);
	}
}
