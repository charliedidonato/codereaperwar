package com.empirestateeids.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.empirestateids.domain.Content;
import com.empirestateids.exception.GenericException;
import com.empirestateids.security.UserInfo;
import com.empirestateids.service.ContentService;
import com.empirestateids.service.LookupService;
import com.empirestateids.utls.UtilityMethods;

@Controller
@RequestMapping("/content")
public class ContentController  extends AtlasController{
	
	static Logger logger = LogManager.getLogger(ContentController.class);
	
	@Autowired
	private ContentService contentService;
	@Autowired
	private LookupService lookupService;
	
	/**
	 * Edit Content
	 * 
	 */
	@RequestMapping("/editContent")
	public ModelAndView editContent() {
		ModelAndView mav = new ModelAndView();

		Content content = contentService.getAtlasContent();
		
		mav.addObject("content", content);
		mav.setViewName("content/editContent.jsp");

		return mav;
	}
	
	@ModelAttribute
	public void populateModel(Model model, HttpServletRequest req) {
	    model.addAttribute("LatestArticleList", lookupService.getLatestArticles());
	    model.addAttribute("LatestGalleryList", lookupService.getLatestGalleries());
	}
	
	
	/**
	 * Update Content
	 * @ModelAttribute 
	 */
	@RequestMapping(value = "/updateContent", method = RequestMethod.POST)
	public ModelAndView updateContent(@ModelAttribute("content") Content content, BindingResult bindResult, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		if(bindResult.hasErrors()) {
			mav.addObject("content", content);
			mav.setViewName("content/editContent.jsp");
			return mav;
		}
		
		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
		
		try {
			contentService.updateAtlasContent(content, userInfo);
		} catch (Exception e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			throw new GenericException("Error updating Content",e);
		}
		
		return new ModelAndView("redirect:/content/editContent");
	}
	
}