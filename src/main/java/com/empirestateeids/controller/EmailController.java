package com.empirestateeids.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.empirestateids.domain.EmailExt;
import com.empirestateids.service.EmailService;
import com.empirestateids.service.UserRegService;

@Controller
@RequestMapping("/email")
public class EmailController extends AtlasController {
	
	static Logger logger = LogManager.getLogger(EmailController.class);
	
	@Autowired
	private EmailService emailService;
	//@Autowired
	//private ProfileRegService profileRegService;
	@Autowired
	private UserRegService userRegService;

	/**
	 * Bulk email to lifter profiles
	 * 
	 */
	@RequestMapping("/composeBulkProfileEmails")
	public ModelAndView composeBulkProfileEmails() {
		ModelAndView mav = new ModelAndView();
		
		EmailExt emailExt = new EmailExt();
		
		mav.addObject("emailExt", emailExt);
		mav.setViewName("email/composeBulkProfileEmails.jsp");
		
		return mav;
	}
	
//	@RequestMapping("/sendBulkUserEmails")
//	public ModelAndView sendBulkProfileEmails(@ModelAttribute("emailExt") EmailExt emailExt, BindingResult bindResult, HttpServletRequest request) {
//		ModelAndView mav = new ModelAndView();
//		
//        List <Email> emailList = new ArrayList<Email>();
//        try {			
//            List<UserRegistration> userRegList = new ArrayList<UserRegistration>();
//            
//			try {
//				userRegList = userRegService.getAllUserRegs();
//				emailService.sendToUsers(userRegList, emailExt);
//			} catch (Exception e) {
//				logger.error("Exception:" + e.getMessage() + " Trace:" + UtilityMethods.getStackTrace(e));
//			}
//		} catch (Exception e) {
//			logger.error("Error sending bulk email", e);
//			logger.error("Exception:" + e.getMessage() + " Trace:" + UtilityMethods.getStackTrace(e));
//			throw new GenericException("Error sending bulk emails", e);
//		}
//		
//		mav.setViewName("email/responseBulkProfileEmails.jsp");
//
//		return mav;
//	}
	
		
}