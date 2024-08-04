package com.empirestateids.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PagesController extends AtlasController {
		
	/**
	 * About page
	 * 
	 */
	@RequestMapping("/about")
	public ModelAndView about() {
		ModelAndView mav = new ModelAndView("about.jsp");
		return mav;
	}
	
	/**
	 * Mission Statement page
	 * 
	 */
	@RequestMapping("/mission")
	public ModelAndView missionStatement() {
		ModelAndView mav = new ModelAndView("mission.jsp");
		return mav;
	}
	
	@RequestMapping("/accessDenied")
	public ModelAndView accessDenied() {
		ModelAndView mav = new ModelAndView("accessDenied.jsp");
		return mav;
	}
	
	@RequestMapping("/logoutSuccess")
	public ModelAndView logoutSuccess() {
		ModelAndView mav = new ModelAndView("logout-success.jsp");
		return mav;
	}
}