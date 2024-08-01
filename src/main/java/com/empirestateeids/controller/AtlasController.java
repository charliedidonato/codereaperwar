/**
 * 
 */
package com.empirestateeids.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.empirestateids.common.IConstants;
import com.empirestateids.common.SecurityUtil;
import com.empirestateids.exception.GenericException;
import com.empirestateids.exception.PermissionDeniedException;
import com.empirestateids.security.UserInfo;
import com.empirestateids.service.LookupService;

/**
 * @author Syed
 *
 */
public class AtlasController {
	static Logger logger = LogManager.getLogger(AtlasController.class);
	
	@Autowired
	private LookupService lookupService;
	
	protected Map<String,String> getLookupMap(String lookupName, HttpServletRequest request){
		ServletContext servletContext = request.getServletContext();
		Map<String,String> lookupMap = null;
		
		lookupMap = (Map<String, String>) servletContext.getAttribute(lookupName);
		if(lookupMap == null){
			lookupMap = lookupService.getLookup(lookupName);
			servletContext.setAttribute(lookupName, lookupMap);
		}
		
		return lookupMap;
	}
	
	/**
	 * Register custom, context-specific property editors
	 * 
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register static property editors.
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	    df.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
		
		binder.registerCustomEditor(java.util.Calendar.class, new org.skyway.spring.util.databinding.CustomCalendarEditor());
		binder.registerCustomEditor(byte[].class, new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(false));
		binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
		binder.registerCustomEditor(java.math.BigDecimal.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
		binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
		//binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
		binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
		binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
		binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
		
		//binder.setValidator(new RecordValidator());
	}
	
	/*
	 * Edit check: if not admin user then check if the record belongs to the logged in user.
	 */
	public void ownCheckIpowerliftIdOrAdmin(UserInfo userInfo, Integer ipowerliftId) throws PermissionDeniedException{
		// if not admin user then check if its the record is logged in user's record.
		if(!SecurityUtil.hasRole(IConstants.ROLE_ADMIN)){
			if(userInfo == null || userInfo.getIpowerliftId()!=ipowerliftId){
				logger.error(ipowerliftId + ": not owned by: " + userInfo);
				throw new PermissionDeniedException("User not Administrator or doesn't own the record.");
			}
		}
	}
	
	/*
	 * Edit check: if not admin user.
	 */
	public boolean isAdmin(UserInfo userInfo, Integer ipowerliftId) throws PermissionDeniedException{
		// if not admin user 
		if(SecurityUtil.hasRole(IConstants.ROLE_ADMIN)){
			return true;
		}else {
			return false;
		}
	}
	
	public boolean ownCheckAdmin(UserInfo userInfo, Integer ipowerliftId) throws PermissionDeniedException{
		// if not admin user then check if its the record is logged in user's record.
		if(SecurityUtil.hasRole(IConstants.ROLE_ADMIN)){
			return true;
		}else {
			return false;
		}
	}
	
	public boolean ownCheckPreferred(UserInfo userInfo, Integer ipowerliftId) throws PermissionDeniedException{
		// if not admin user then check if its the record is logged in user's record.
		if(SecurityUtil.hasRole(IConstants.ROLE_PREFERRED_USER)){
			return true;
		}else {
			return false;
		}
	}
	
	/*
	 * View check: if profile is not approved and user is not profile user or admin then display error.
	 */
	public void checkValidProfile(UserInfo userInfo, Integer ipowerliftId) throws PermissionDeniedException{
		logger.error("User is trying unauthorised access: " + userInfo +"; Ipowerliftid: "+ipowerliftId );
		throw new GenericException("You are trying unauthorised access");
	}
	
	/*
	 * View check: if profile is not approved and user is not profile user or admin then display error.
	 */
	public void checkToViewApprovedProfile(UserInfo userInfo, boolean approved) throws PermissionDeniedException{
		// if not admin user then check if its the record is logged in user's record.
		if(!SecurityUtil.hasRole(IConstants.ROLE_ADMIN)){
			if(!approved) {
				logger.error("User is trying access to another user's unapproved profile " + userInfo );
				throw new GenericException("Profile is under review and is not ready for public view.");
			}
		}
	}
			
	/*
	 * Edit check: if not admin user then check if the record belongs to the logged in federation user.
	 */
	public void ownCheckFederationIdOrAdmin(UserInfo userInfo, Integer federationId) throws PermissionDeniedException{
		// if not admin user then check if its the record is logged in user's record.
		if(!SecurityUtil.hasRole(IConstants.ROLE_ADMIN)){
			if(userInfo.getIpowerliftId()!=federationId){
				logger.error(federationId + ": not owned by: " + userInfo);
				throw new PermissionDeniedException("User not Administrator or doesn't own the record.");
			}
		}
	}
}
