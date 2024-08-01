package com.empirestateeids.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DevicePlatform;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.empirestateids.domain.UserRegistration;
import com.empirestateids.exception.GenericException;
import com.empirestateids.security.UserInfo;
import com.empirestateids.service.AuthService;
import com.empirestateids.service.UserRegService;
import com.empirestateids.utls.UtilityMethods;
import com.empirestateids.validators.ForgotLoginValidator;
import com.empirestateids.validators.UserRegChangePwdValidator;

@Controller
public class AuthController  extends AtlasController{

	static Logger logger = LogManager.getLogger(AuthController.class);
	
	@Autowired
	private UserRegService userRegService;
	@Autowired
	private AuthService authService;
	@Autowired
	private UserRegChangePwdValidator userRegChangePwdValidator;
	@Autowired
	private ForgotLoginValidator forgotLoginValidator;
	
	/**
	 * Login page
	 * 
	 */
	@RequestMapping({"/auth/login","/auth/m/login"})
	@ExceptionHandler(GenericException.class)
	public ModelAndView loginView(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		try {
		Device currentDevice = DeviceUtils.getCurrentDevice(request);
		DevicePlatform platform  = currentDevice.getDevicePlatform();
		
		mav.addObject("platform", platform.toString());
		if (currentDevice.isMobile()){
			logger.error("going to /auth/m/login.jsp:"+platform.toString());
			mav.setViewName("auth/m/login.jsp");
		}else {
			mav.setViewName("login.jsp");
		}
		}catch(Exception e) {
			mav.setViewName("login.jsp");
			logger.error("Message:"+e.getMessage()+
					" trace:"+UtilityMethods.getStackTrace(e));
		}
		return mav;
	}
	
	/**
	 * Login error 
	 * 
	 */
	@RequestMapping("/auth/loginError")
	public ModelAndView loginErrorView(HttpServletRequest request) {
		Device currentDevice = DeviceUtils.getCurrentDevice(request);
		DevicePlatform platform  = currentDevice.getDevicePlatform();
		
		ModelAndView mav = new ModelAndView();
		if (currentDevice.isMobile()){
			mav.addObject("login_error", true);
			mav.addObject("platform", platform.toString());			
			mav.setViewName("auth/m/login.jsp");
		}else {
			mav.addObject("login_error", true);
			mav.setViewName("login.jsp");
		}
		return mav;
	}
	
	@RequestMapping({"/auth/changePassword","/auth/m/changePassword"})
	public ModelAndView changePasswordView(HttpServletRequest request) {
		Device currentDevice = DeviceUtils.getCurrentDevice(request);
		DevicePlatform platform  = currentDevice.getDevicePlatform();
		
		ModelAndView mav = new ModelAndView();

		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
		
		UserRegistration userReg = null;
		
		userReg = userRegService.getUserReg(userInfo.getIpowerliftId());
		
		mav.addObject("userReg", userReg);
		if (currentDevice.isMobile()){
			mav.setViewName("auth/m/changePassword.jsp");
		}else {
			mav.setViewName("auth/changePassword.jsp");
		}
		return mav;
	}
	
	/**
	 * Change Password on reset password
	 * @ModelAttribute 
	 */
	@RequestMapping(value = {"/auth/saveChangePassword","/auth/m/saveChangePassword"}, method = RequestMethod.POST)
	public ModelAndView saveChangePwdUserReg(@ModelAttribute("userReg") UserRegistration userReg, BindingResult bindResult, HttpServletRequest request) {
		Device currentDevice = DeviceUtils.getCurrentDevice(request);
		DevicePlatform platform  = currentDevice.getDevicePlatform();
		ModelAndView mav = new ModelAndView();
		
		userRegChangePwdValidator.validate(userReg, bindResult);
		
		if(bindResult.hasErrors()) {
			mav.addObject("userReg", userReg);
			if (currentDevice.isMobile()){
				mav.setViewName("changepassword");
			}else {
			    mav.setViewName("auth/changePassword.jsp");
			}
			return mav;
//			return "userReg/editUserRegs.jsp";
		}
		
		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
		
		Integer ipowerliftId = userReg.getIpowerliftId();
		/* if not admin user then check if its the record is logged in user's record. */
		super.ownCheckIpowerliftIdOrAdmin(userInfo, ipowerliftId);
		
		try {
			userRegService.updatePasswordUserReg(userReg, userInfo);
		} catch (Exception e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			throw new GenericException("Error saving change password auth",e);
		}
		
		//clear password
		userReg.getUser().setPassword(null);
		
//		mav.addObject("userReg", userReg);
//		mav.setViewName("userReg/editUserRegs.jsp");
//		return mav;
		//return viewUserReg(userReg.getIpowerliftId());
		return new ModelAndView("redirect:/logout");
//		return "forward:/userReg/viewUserReg?ipowerliftId="+userReg.getIpowerliftId();
	}
	
	@RequestMapping({"/auth/forgotPassword","/auth/m/forgotPassword"})
	public ModelAndView forgotPassword(HttpServletRequest request) {
		Device currentDevice = DeviceUtils.getCurrentDevice(request);
		DevicePlatform platform  = currentDevice.getDevicePlatform();
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("userReg", new UserRegistration());
		if (currentDevice.isMobile()){
			mav.setViewName("auth/m/forgotPassword.jsp");
		}else {
			mav.setViewName("auth/forgotPassword.jsp");
		}
		return mav;
	}
	
	/**
	 * Send temporary password
	 * @ModelAttribute 
	 */
	@RequestMapping(value = {"/auth/submitForgotPassword","/auth/m/submitForgotPassword"}, method = RequestMethod.POST)
	public ModelAndView submitForgotPassword(@ModelAttribute("userReg") UserRegistration userReg, BindingResult bindResult, HttpServletRequest request) {
		Device currentDevice = DeviceUtils.getCurrentDevice(request);
		DevicePlatform platform  = currentDevice.getDevicePlatform();
		ModelAndView mav = new ModelAndView();
		
		forgotLoginValidator.validate(userReg, bindResult);
		
		if(bindResult.hasErrors()) {
			mav.addObject("userReg", userReg);
			if (currentDevice.isMobile()){
				mav.addObject("platform", platform.toString());
				mav.setViewName("auth/m/forgotPassword.jsp");
			}else {
				mav.setViewName("auth/forgotPassword.jsp");
			}
			return mav;
		}
		
		boolean forgotPasswordSuccess = false;
		try {
			forgotPasswordSuccess = authService.sendTemporaryPassword(userReg);
		} catch (GenericException e) {
			// error sending email
			forgotPasswordSuccess = false;
		} catch (Exception e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			throw new GenericException("Error resetting password: ",e);
		}
		
		mav.addObject("forgotPasswordSuccess", forgotPasswordSuccess);
		if (currentDevice.isMobile()){
			mav.setViewName("auth/m/forgotPasswordStatus.jsp");
		}else {
			mav.setViewName("auth/forgotPasswordStatus.jsp");
		}
		return mav;
	}
	
	@RequestMapping({"/auth/forgotUsername","/auth/m/forgotUsername"})
	public ModelAndView forgotUsername(HttpServletRequest request) {
		Device currentDevice = DeviceUtils.getCurrentDevice(request);
		DevicePlatform platform  = currentDevice.getDevicePlatform();
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("userReg", new UserRegistration());
		if (currentDevice.isMobile()){
			mav.setViewName("auth/m/forgotUsername.jsp");
		}else {
			mav.setViewName("auth/forgotUsername.jsp");
		}
		return mav;
	}
	
	/**
	 * Send username
	 * @ModelAttribute 
	 */
	@RequestMapping(value = {"/auth/submitForgotUsername","/auth/m/submitForgotUsername"}, method = RequestMethod.POST)
	public ModelAndView submitForgotUsername(@ModelAttribute("userReg") UserRegistration userReg, BindingResult bindResult, HttpServletRequest request) {
		Device currentDevice = DeviceUtils.getCurrentDevice(request);
		DevicePlatform platform  = currentDevice.getDevicePlatform();
		
		ModelAndView mav = new ModelAndView();
		
		forgotLoginValidator.validate(userReg, bindResult);
		
		if(bindResult.hasErrors()) {
			mav.addObject("userReg", userReg);
			if (currentDevice.isMobile()){
				mav.setViewName("auth/m/forgotUsername.jsp");
			}else {
				mav.setViewName("auth/forgotUsername.jsp");
			}
			return mav;
		}
		
		boolean forgotUsernameSuccess = false;
		try {
			forgotUsernameSuccess = authService.sendUsername(userReg);
		} catch (GenericException e) {
			// error sending email
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));	
			forgotUsernameSuccess = false;
		} catch (Exception e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			throw new GenericException("Error sending Username: ",e);
		}
		
		mav.addObject("forgotUsernameSuccess", forgotUsernameSuccess);
		if (currentDevice.isMobile()){
			mav.setViewName("auth/m/forgotUsernameStatus.jsp");
		}else {
			mav.setViewName("auth/forgotUsernameStatus.jsp");
		}
		return mav;
	}
}