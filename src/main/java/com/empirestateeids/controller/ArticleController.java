package com.empirestateeids.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.empirestateids.domain.ArticleExt;
import com.empirestateids.exception.GenericException;
import com.empirestateids.security.UserInfo;
import com.empirestateids.service.ArticleService;
import com.empirestateids.utls.UtilityMethods;
import com.empirestateids.validators.ArticleExtValidator;

@Controller
@RequestMapping("/article")
public class ArticleController extends AtlasController {
	
	static Logger logger = LogManager.getLogger(ArticleController.class);
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleExtValidator articleExtValidator;
	
	@RequestMapping("/indexLatest")
	public ModelAndView indexLatest() {
		ModelAndView mav = new ModelAndView();

		List<ArticleExt> articleExt = null;

		try {
			articleExt = articleService.getLatestArticles();
		} catch (IOException e) {
			logger.error("Error getting latest Articles", e);
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			throw new GenericException("Error getting latest Articles",e);
		}
		
		mav.addObject("articleExt", articleExt);
		mav.setViewName("article/latestArticles.jsp");

		return mav;
	}
	
	/**
	 * Show all Article entities
	 * 
	 */
	@RequestMapping("/indexArticle")
	public ModelAndView listArticle() {
		ModelAndView mav = new ModelAndView();

		List<ArticleExt> articleExt = null;

		try {
			articleExt = articleService.getAllArticles();
		} catch (IOException e) {
			logger.error("Error getting all Articles", e);
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			throw new GenericException("Error getting all Articles",e);
		}
		
		mav.addObject("articleExt", articleExt);
		mav.setViewName("article/listArticles.jsp");

		return mav;
	}
	
	/**
	 * Edit an existing Article entity
	 * 
	 */
	@RequestMapping("/editArticle")
	public ModelAndView editArticle(@RequestParam Integer articleId) {
		ModelAndView mav = new ModelAndView();

		ArticleExt articleExt = null;
		
		try {
			articleExt = articleService.getArticle(articleId);
		} catch (IOException e) {
			logger.error("Error getting Article", e);
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			throw new GenericException("Error getting Article",e);
		}
		
		mav.addObject("articleExt", articleExt);
		mav.setViewName("article/editArticle.jsp");

		return mav;
	}
	
	/**
	 * Create a new Article entity
	 * 
	 */
	@RequestMapping("/newArticle")
	public ModelAndView newArticle() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("articleExt", new ArticleExt());
		mav.setViewName("article/newArticle.jsp");

		return mav;
	}
	
	/**
	 * View Article entity
	 * 
	 */
	@RequestMapping("/viewArticle")
	public ModelAndView viewArticle(@RequestParam Integer articleId) {
		ModelAndView mav = new ModelAndView();
		
		ArticleExt articleExt;
		try {
			articleExt = articleService.getArticle(articleId);
		} catch (IOException e) {
			logger.error("Error getting Article", e);
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			throw new GenericException("Error getting Article",e);
		}
		mav.addObject("articleExt", articleExt);
		mav.setViewName("article/viewArticle.jsp");

		return mav;
	}
	
	/**
	 * Add a new Article
	 * @ModelAttribute 
	 */
	@RequestMapping(value = "/addArticle", method = RequestMethod.POST)
	public ModelAndView addArticle( @ModelAttribute("articleExt") ArticleExt articleExt, BindingResult bindResult, HttpServletRequest request) {
		
		articleExtValidator.validate(articleExt, bindResult);
		
		ModelAndView mav = new ModelAndView();
		
		if(bindResult.hasErrors()) {
			mav.addObject("articleExt", articleExt);
			mav.setViewName("article/newArticle.jsp");
			return mav;
		}
		
		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
		
		try {
			articleService.addArticle(articleExt, userInfo);
		} catch (IOException e1) {
			logger.error("Exception:" + e1.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e1));
			logger.error("Error Adding Article", e1);
			throw new GenericException("Error adding Article",e1);
		} catch (Exception e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			throw new GenericException("Error updating Article",e);
		}
		
		return new ModelAndView("redirect:/article/viewArticle?articleId="+articleExt.getArticle().getArticleId());
	}
	
	/**
	 * Update an existing Article
	 * @ModelAttribute 
	 */
	@RequestMapping(value = "/updateArticle", method = RequestMethod.POST)
	public ModelAndView updateArticle( @ModelAttribute("articleExt") ArticleExt articleExt, BindingResult bindResult, HttpServletRequest request) {
		
		articleExtValidator.validate(articleExt, bindResult);
		
		ModelAndView mav = new ModelAndView();
		
		if(bindResult.hasErrors()) {
			mav.addObject("articleExt", articleExt);
			mav.setViewName("article/editArticle.jsp");
			return mav;
		}
		
		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
		
		try {
			articleService.updateArticle(articleExt, userInfo);
		} catch (IOException e1) {
			logger.error("Error Updating Article", e1);
			logger.error("Exception:" + e1.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e1));
			throw new GenericException("Error updating Article",e1);
		} catch (Exception e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			throw new GenericException("Error updating Article",e);
		}
		
		return new ModelAndView("redirect:/article/viewArticle?articleId="+articleExt.getArticle().getArticleId());
	}
		
}