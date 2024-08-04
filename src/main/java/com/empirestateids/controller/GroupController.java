package com.empirestateids.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.empirestateids.common.IConstants;
import com.empirestateids.domain.Group;
import com.empirestateids.exception.GenericException;
import com.empirestateids.security.UserInfo;
import com.empirestateids.service.GroupService;
import com.empirestateids.utls.UtilityMethods;

@Controller
@RequestMapping("/group")
public class GroupController extends AtlasController{
	
	static Logger logger = LogManager.getLogger(GroupController.class);
	
	@Autowired
	private GroupService groupService;
	
	/**
	 * Show all Group entities
	 * 
	 */
	@RequestMapping("/indexGroup")
	public ModelAndView listGroups() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("groups", groupService.getAllGroups());

		mav.setViewName("group/listGroups.jsp");

		return mav;
	}
	
	/**
	 * Edit an existing Group entity
	 * 
	 */
	@RequestMapping("/editGroup")
	public ModelAndView editGroup(@RequestParam Integer groupId) {
		ModelAndView mav = new ModelAndView();

		Group group = null;
		
		group = groupService.getGroup(groupId);
		
		mav.addObject("group", group);
		mav.setViewName("group/editGroup.jsp");

		return mav;
	}
	
	/**
	 * Create a new Group
	 * 
	 */
	@RequestMapping("/newGroup")
	public ModelAndView newGroup() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("group", new Group());
		mav.setViewName("group/newGroup.jsp");

		return mav;
	}
	
	/**
	 * View Group
	 * 
	 */
	@RequestMapping("/viewGroup")
	public ModelAndView viewGroup(@RequestParam Integer groupId) {
		ModelAndView mav = new ModelAndView();
		
		Group group;
		
		group = groupService.getGroup(groupId);
		
		mav.addObject("group", group);
		mav.setViewName("group/viewGroup.jsp");

		return mav;
	}
	
	@ModelAttribute
	public void populateModel(Model model, HttpServletRequest request) {
	    model.addAttribute("AuthorityList", getLookupMap(IConstants.LKUP_AUTHORITY,request));
	}
	
	/**
	 * Add a new Group
	 * @ModelAttribute 
	 */
	@RequestMapping(value = "/addGroup", method = RequestMethod.POST)
	public ModelAndView addGroup( @ModelAttribute("group") Group group, BindingResult bindResult, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(bindResult.hasErrors()) {
			mav.addObject("group", group);
			mav.setViewName("group/newGroup.jsp");
			return mav;
		}
		
		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
		
		try {
			groupService.addGroup(group, userInfo);
		} catch (Exception e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			throw new GenericException("Error adding Group",e);
		}
		
		return new ModelAndView("redirect:/group/viewGroup?groupId="+group.getGroupId());
	}
	
	/**
	 * Update an existing Group
	 * @ModelAttribute 
	 */
	@RequestMapping(value = "/updateGroup", method = RequestMethod.POST)
	public ModelAndView updateGroup( @ModelAttribute("group") Group group, BindingResult bindResult, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		if(bindResult.hasErrors()) {
			mav.addObject("group", group);
			mav.setViewName("group/editGroup.jsp");
			return mav;
		}
		
		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
		
		try {
			groupService.updateGroup(group, userInfo);
		} catch (Exception e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			throw new GenericException("Error updating Group",e);
		}
		
		return new ModelAndView("redirect:/group/viewGroup?groupId="+group.getGroupId());
	}
		
	/**
	 * Register custom, context-specific property editors
	 * 
	 */
//	@InitBinder
//	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register static property editors.
//		
//		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//	    df.setLenient(false);
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
//		
//		binder.registerCustomEditor(java.util.Calendar.class, new org.skyway.spring.util.databinding.CustomCalendarEditor());
//		binder.registerCustomEditor(byte[].class, new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
//		binder.registerCustomEditor(boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(false));
//		binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
//		binder.registerCustomEditor(java.math.BigDecimal.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
//		binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
//		//binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
//		binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
//		binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
//		binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
//		
//		binder.setValidator(new GroupValidator());
//	}
	
}