package com.empirestateeids.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.ipowerlift.atlas.domain.RowsCreatedOrUpdated;
import net.ipowerlift.atlas.security.UserInfo;
import net.ipowerlift.atlas.service.IcdService;

/**
 *
 * @author Charles DiDonato
 *
 */
@Controller
@RequestMapping("/icdcode")
public class IcdCodeController extends AtlasController {

	static Logger logger = Logger.getLogger(AuthController.class);

	@Autowired
	IcdService icdService;

//	@RequestMapping("/getAndStoreIcd10CodeData")
//	public ModelAndView getIcd10CodeData(HttpServletRequest request) throws Exception {
//		ModelAndView mav = new ModelAndView();
//
//		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
//		RowsCreatedOrUpdated data = new RowsCreatedOrUpdated();
//		data = icdService.insertOrUpdateIcd10Codes(data,userInfo.getUsername());
//		mav.addObject("resultsModified", data);
//		mav.setViewName("codes/icd10CodeLoad.jsp");
//
//		return mav;
//	}

	@RequestMapping("/getAndStoreIcd11CodeData")
	public ModelAndView getIcd11CodeData(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();

		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
		RowsCreatedOrUpdated data = new RowsCreatedOrUpdated();
		//data = icdService.insertOrUpdateIcd11Codes(data,userInfo.getUsername());

		mav.addObject("resultsModified", data);
		mav.setViewName("codes/icd11CodeLoad.jsp");

		return mav;
	}

}
