package com.empirestateids.controller;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.empirestateids.common.IConstants;
import com.empirestateids.domain.RowsCreatedOrUpdated;
import com.empirestateids.domain.UploadData;
import com.empirestateids.security.UserInfo;
import com.empirestateids.service.RxNormService;

/**
 *
 * @author Charles DiDonato
 *
 */
@Controller
@RequestMapping("/rxnorm")
public class RxNormController extends AtlasController {

	static Logger logger = LogManager.getLogger(RxNormController.class);


//	@Autowired
//	private CsvParserService parserService;

	@Autowired
	private RxNormService rsnormService;

	/**
	 *
	 * @param uploadData
	 * @param bindResult
	 * @param request
	 * @return
	 */
	@RequestMapping("/uploadAndStoreLoincMaster")
	public ModelAndView showAds(@ModelAttribute("uploadData") UploadData uploadData,
			BindingResult bindResult, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();

//		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
//
//        String validFileType = "L";
//        String versionId = "1.0";
//        String fileType = uploadData.getFileType();
//        if (validFileType!=fileType) {
//        	throw new Exception("Bad Filetype:"+fileType);
//        }
//
//        byte[] cvsByteArray = uploadData.getFileData().getBytes();
//        UploadStatus status =  parserService.uploadLoincMasterFile(fileType, versionId, cvsByteArray, userInfo);
//        mav.addObject("status", status);
//		mav.setViewName("codes/uploadStatus.jsp");

		return mav;
	}

	@RequestMapping("/uploadForm")
	public ModelAndView loincUploadForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
//
//		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
//		UploadData uploadData = new UploadData();
//		uploadData.setFileType("L");
//
//        mav.addObject("uploadData",uploadData);
//        mav.addObject("validFileType", uploadData.getFileType());
//		mav.setViewName("codes/uploadLoinc.jsp");
//
		return mav;
	}


	@RequestMapping("/getRxNormCodeData")
	public ModelAndView getRxNormCodeData(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();

		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
		//active - obsolete - remapped - quantified - notcurrent

		URL url = new URL(IConstants.NLM_DOMAIN_REST+IConstants.NLM_DOMAIN_REST_ALL_NDC_EXT);
		RowsCreatedOrUpdated results = rsnormService.insertOrUpdateRxNormCodes(userInfo.getUsername());

		logger.error("Controller:"+results.toString());
		mav.addObject("resultsModified", results);
		mav.setViewName("codes/rxNormLoad.jsp");

		return mav;
	}

}
