package com.empirestateids.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DevicePlatform;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.empirestateids.domain.MainContent;
import com.empirestateids.service.MainService;

@Controller
public class MainController extends AtlasController{
	
	static Logger logger = LogManager.getLogger(MainController.class);
	
	@Autowired
	MainService mainService;
	
	/**
	 * Home page
	 * 
	 */
	@RequestMapping("/home")
	public ModelAndView home(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		MainContent mainContent = new MainContent();
		Device currentDevice = DeviceUtils.getCurrentDevice(request);
		DevicePlatform platform  = currentDevice.getDevicePlatform();

		String username; 
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
	
		mav.addObject("userName", username);
		mav.addObject("devicePlatform", platform.toString());
//		if (currentDevice.isMobile()) {
//			logger.error("going to mobile mhome.jsp");
//			mav.setViewName("mhome.jsp"); 
//		} else {
			logger.error("going to normal home.jsp");
			mav.setViewName("home.jsp");
//		}
		return mav;
	}
	
	@RequestMapping("/adminhome")
	public ModelAndView oldhome(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		MainContent mainContent = new MainContent();
		Device currentDevice = DeviceUtils.getCurrentDevice(request);
		DevicePlatform platform  = currentDevice.getDevicePlatform();
		
		String username; 
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		mav.setViewName("adminhome.jsp");
		mav.addObject("userName", username);
		return mav;
	}
}