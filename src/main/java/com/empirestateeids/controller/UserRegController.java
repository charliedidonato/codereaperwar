package com.empirestateeids.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.empirestateids.common.IConstants;
import com.empirestateids.domain.GroupMember;
import com.empirestateids.domain.UserRegistration;
import com.empirestateids.exception.GenericException;
import com.empirestateids.exception.PermissionDeniedException;
import com.empirestateids.security.UserInfo;
import com.empirestateids.service.UserRegService;
import com.empirestateids.utls.UtilityMethods;
import com.empirestateids.validators.UserRegChangePwdValidator;
import com.empirestateids.validators.UserRegNewValidator;

@Controller
@RequestMapping("/userReg")
public class UserRegController extends AtlasController{
	
	static Logger logger = LogManager.getLogger(UserRegController.class);
	
	@Autowired
	private UserRegService userRegService;
	@Autowired
	private UserRegNewValidator userRegNewValidator;
	@Autowired
	private UserRegChangePwdValidator userRegChangePwdValidator;
	
	/**
	 * Show all User Registrations
	 * 
	 */
	@RequestMapping("/indexUserReg")
	public ModelAndView listUserRegs(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Device currentDevice = DeviceUtils.getCurrentDevice(request);
		
		mav.addObject("userRegs", userRegService.getAllUserRegs());

		mav.setViewName("userReg/listUserRegs.jsp");

		return mav;
	}
	
	/**
	 * Edit an existing User Registration
	 * 
	 */
	@RequestMapping({"/editUserReg","/m/editUserReg"})
	public ModelAndView editUserReg(@RequestParam Integer userId, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Device currentDevice = DeviceUtils.getCurrentDevice(request);
		
		UserRegistration userReg = null;
		
		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
		
		/* if not admin user then check if its the record is logged in user's record. */
		super.ownCheckIpowerliftIdOrAdmin(userInfo, userId);
		
		userReg = userRegService.getUserReg(userId);
		
		mav.addObject("userReg", userReg);
		if (currentDevice.isMobile()){
			mav.setViewName("userReg/m/editUserRegs.jsp");
		}else {
			mav.setViewName("userReg/editUserRegs.jsp");
		}
		return mav;
	}
	
	/**
	 * Create a new User Registration
	 * 
	 */
	@RequestMapping({"/newUserReg","/m/newUserReg"})
	public ModelAndView newUserReg(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		UserRegistration userReg = new UserRegistration();
	    GroupMember groupMember = new GroupMember();
	    
	    Device currentDevice = DeviceUtils.getCurrentDevice(request);
	    
	    mav.addObject("groupMember",groupMember);
		mav.addObject("userReg", userReg);
		mav.addObject("newFlag", true);
		if (currentDevice.isMobile()){
			mav.setViewName("userReg/m/newUserReg.jsp");
		}else {
		    mav.setViewName("userReg/newUserReg.jsp");
		}
		return mav;
	}
	
	/**
	 * View User Registration
	 * 
	 */
	@RequestMapping({"/viewUserReg","/m/viewUserReg"})
	public ModelAndView viewUserReg(@RequestParam Integer userId,HttpServletRequest request) 
	  {
		ModelAndView mav = new ModelAndView();
		Device currentDevice = DeviceUtils.getCurrentDevice(request);

		UserRegistration userReg;
		//userReg won't be null below as the service will create a new empty 
		//UserRegistration and set the internals WHICH MAY BE NULL...You have been warned
		userReg = userRegService.getUserReg(userId);
		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
		
		/* if not admin user then check if its the record is logged in user's record. */
		super.ownCheckIpowerliftIdOrAdmin(userInfo, userId);
		mav.addObject("userReg", userReg);
		
		if (currentDevice.isMobile()){
			mav.setViewName("userReg/m/viewUserReg.jsp");
		}else {
		    mav.setViewName("userReg/viewUserReg.jsp");
		}
		
		return mav;
	}
	
	/**
	 * Change Password on existing User Registration
	 * 
	 */
	@RequestMapping({"/changePwdUserReg","/m/changePwdUserReg"})
	public ModelAndView changePwdUserReg(@RequestParam Integer userId, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		UserRegistration userReg = null;
		
		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
		Device currentDevice = DeviceUtils.getCurrentDevice(request);
			
		/* if not admin user then check if its the record is logged in user's record. */
		super.ownCheckIpowerliftIdOrAdmin(userInfo, userId);
		
		userReg = userRegService.getUserReg(userId);
		
		mav.addObject("userReg", userReg);
//		if (currentDevice.isMobile()){
//			mav.setViewName("changepassword");
//		}else {
		    mav.setViewName("userReg/changePwdUserReg.jsp");
//		}    
		return mav;
	}
	
	@ModelAttribute
	public void populateModel(Model model, HttpServletRequest request) {
	    model.addAttribute("GroupList", getLookupMap(IConstants.LKUP_REF_GROUP_LIST,request));
	    model.addAttribute("GenderList", getLookupMap(IConstants.LKUP_GENDER,request));
	}
	
	/**
	 * Save an existing User Registration
	 * @ModelAttribute 
	 */
	@RequestMapping(value = {"/addUserReg","/m/addUserReg"}, method = RequestMethod.POST)
	public ModelAndView addUserReg(@ModelAttribute("userReg") UserRegistration userReg, BindingResult bindResult, HttpServletRequest request) {
		//set dummy value
		Integer ipowerliftId = new Integer(0);
		userRegNewValidator.validate(userReg, bindResult);
		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
//		if (userInfo!=null) {
//			ipowerliftId = userInfo.getIpowerliftId();
//		}else {
//			
//		}
		
		//default all add users to 103 registered user code
		GroupMember g = userReg.getGroupMember();
		Integer groupId = g.getGroupId();
		if (groupId == null){
			groupId = new Integer(IConstants.GROUP_NAME_REGISTERED_USER);
			g.setGroupId(groupId); 
		}else {
			if (!super.isAdmin(userInfo,ipowerliftId) 
					&& groupId!=null 
					&& g.getGroupId()!=IConstants.GROUP_NAME_REGISTERED_USER){
				throw new PermissionDeniedException("User not Administrator and can't set GROUP to elevated  privileges.");
			}
		}
		
		ModelAndView mav = new ModelAndView();
		if(bindResult.hasErrors()) {
			mav.addObject("userReg", userReg);
			mav.setViewName("userReg/newUserReg.jsp");
			return mav;
		}
		Device currentDevice = DeviceUtils.getCurrentDevice(request);

		try {
			userRegService.addUserReg(userReg,userInfo);
		} catch (Exception e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			throw new GenericException("Error adding UserReg",e);
		}
		
		String message = "Added User:"+ userReg.getUser().getUsername();
		//clear password
		userReg.getUser().setPassword(null);
		userReg.getEmail().setEmail(null);
		userReg.getPerson().setLastName(null);
		userReg.getPerson().setFirstName(null);
		mav.addObject("message", message);
		mav.addObject("userReg", userReg);
		mav.setViewName("userReg/newUserReg.jsp");
		return mav;
	}

	
	/**
	 * Update an existing User Registration
	 * @ModelAttribute 
	 */
	@RequestMapping(value = {"/updateUserReg","/m/updateUserReg"}, method = RequestMethod.POST)
	public ModelAndView updateUserReg(@ModelAttribute("userReg") UserRegistration userReg, BindingResult bindResult, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Device currentDevice = DeviceUtils.getCurrentDevice(request);
		userRegNewValidator.validate(userReg, bindResult);
		
		if(bindResult.hasErrors()) {
			mav.addObject("userReg", userReg);
			mav.setViewName("userReg/editUserRegs.jsp");
			return mav;
		}
		
		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
		
		Integer ipowerliftId = userReg.getIpowerliftId();
		/* if not admin user then check if its the record is logged in user's record. */
		super.ownCheckIpowerliftIdOrAdmin(userInfo, ipowerliftId);
		
		try {
			userRegService.updateUserReg(userReg, userInfo);
		} catch (Exception e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			throw new GenericException("Error updating UserReg",e);
		}
		
		//clear password
		userReg.getUser().setPassword(null);
//		if (currentDevice.isMobile()){
//			return new ModelAndView("redirect:/userReg/m/viewUserReg?playerId="+userReg.getIpowerliftId());
//		}else {
		    return new ModelAndView("redirect:/userReg/viewUserReg?userId="+userReg.getIpowerliftId());
//		}
	}
	
	/**
	 * Change Password on existing User Registration
	 * @ModelAttribute 
	 */
	@RequestMapping(value = {"/saveChangePwdUserReg","/m/saveChangePwdUserReg"}, method = RequestMethod.POST)
	public ModelAndView saveChangePwdUserReg(@ModelAttribute("userReg") UserRegistration userReg, BindingResult bindResult, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Device currentDevice = DeviceUtils.getCurrentDevice(request);
		
		userRegChangePwdValidator.validate(userReg, bindResult);
		
		if(bindResult.hasErrors()) {
			List <ObjectError>errors = bindResult.getAllErrors();
			for(int i=0; i < errors.size();i++) {
				ObjectError oneError = errors.get(i);
				logger.error(oneError);
			}
			
			mav.addObject("userReg", userReg);
//			if (currentDevice.isMobile()){
//				mav.setViewName("changepwduserreg");
//			}else {
			    mav.setViewName("userReg/changePwdUserReg.jsp");
//			}    
			return mav;
		}
		
		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
		
		Integer ipowerliftId = userReg.getIpowerliftId();
		/* if not admin user then check if its the record is logged in user's record. */
		super.ownCheckIpowerliftIdOrAdmin(userInfo, ipowerliftId);
		
		try {
			userRegService.updatePasswordUserReg(userReg, userInfo);
		} catch (Exception e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			throw new GenericException("Error saving change password user reg",e);
		}
		
		//clear password
		userReg.getUser().setPassword(null);
//		if (currentDevice.isMobile()){
//			return new ModelAndView("redirect:/userReg/m/viewUserReg?playerId="+userReg.getIpowerliftId());
//		}else {
			return new ModelAndView("redirect:/userReg/viewUserReg?userId="+userReg.getIpowerliftId());
//		}
	}
	
	/**
     * Check if userName is taken
     */
	@RequestMapping(value = "/ajax/userNameExists", method = RequestMethod.POST)
	public @ResponseBody String userNameExists(
			@RequestParam(value = "userName", required = true) String userName,
			Model model) {
		if(logger.isDebugEnabled()) {
			logger.debug("Received userName: " + userName);
		}
		// Delegate to service to do the actual adding
		boolean exists = userRegService.userNameExists(userName);

		// @ResponseBody will automatically convert the returned value into JSON
		// format
		// You must have Jackson in your classpath
		return new String(Boolean.toString(exists));
	}
	
	/**
     * Check if emailId is taken
     */
	@RequestMapping(value = "/ajax/emailExists", method = RequestMethod.POST)
	public @ResponseBody String emailExists(
			@RequestParam(value = "email", required = true) String email,
			Model model) {
		if(logger.isDebugEnabled()) {
			logger.debug("Received email: " + email);
		}
		// Delegate to service to do the actual adding
		boolean exists = userRegService.emailExists(email);

		// @ResponseBody will automatically convert the returned value into JSON
		// format
		// You must have Jackson in your classpath
		return new String(Boolean.toString(exists));
	}
}