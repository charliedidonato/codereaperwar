package com.empirestateeids.controller;

import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.empirestateids.common.IConstants;
import com.empirestateids.domain.CVXCatalog;
import com.empirestateids.domain.RowsCreatedOrUpdated;
import com.empirestateids.security.UserInfo;
import com.empirestateids.service.CDCCvxService;

/**
 *
 * @author Charles DiDonato
 *
 */
@Controller
@RequestMapping("/cvxvaccine")
public class CDCCvxVaccineController extends AtlasController {

	static Logger logger = LogManager.getLogger(AuthController.class);

	@Autowired
	CDCCvxService cdcCvxService;

	@RequestMapping("/getAndStoreCvxCodeData")
	public ModelAndView getRxNormCodeData(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();

		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");

		URL url = new URL(IConstants.CVX_CODE_CATALOG_URI);
		List<CVXCatalog>  catalogList = cdcCvxService.getCVXCatalogs(url);
		logger.error("Catalog list size:"+catalogList.size());
		RowsCreatedOrUpdated results = cdcCvxService.storeCVXCatalogs(catalogList,userInfo.getUsername());

		logger.error("Controller:"+results.toString());
		RowsCreatedOrUpdated singleVacResults =
				cdcCvxService.insertOrUpdateCvxCodes(catalogList, results, userInfo.getUsername());



		mav.addObject("resultsModified", results);
		mav.setViewName("codes/cvxCodeLoad.jsp");

		return mav;
	}

}
