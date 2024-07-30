package com.empirestateeids.controller;

import java.io.File;

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
import com.empirestateids.domain.UploadData;
import com.empirestateids.domain.UploadStatus;
import com.empirestateids.security.UserInfo;
import com.empirestateids.service.CsvParserService;
import com.empirestateids.service.IcdService;

/**
 *
 * @author Charles DiDonato
 *
 */
@Controller
@RequestMapping("/parser")
public class CsvParserController {

	static Logger logger = LogManager.getLogger(CsvParserController.class);


	@Autowired
	private CsvParserService parserService;

	@Autowired
	private IcdService icdService;


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

		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");

        String validFileType = "L";
        String versionId = "1.0";
        String fileType = uploadData.getFileType();
        if (validFileType!=fileType) {
        	throw new Exception("Bad Filetype:"+fileType);
        }

        byte[] cvsByteArray = uploadData.getFileData().getBytes();
        UploadStatus status =  parserService.uploadLoincMasterFile(fileType, versionId, cvsByteArray, userInfo);
        mav.addObject("status", status);
		mav.setViewName("codes/uploadStatus.jsp");

		return mav;
	}


	/**
	 *
	 * @param uploadData
	 * @param bindResult
	 * @param request
	 * @return
	 */
	@RequestMapping("/uploadAndStoreICD10File")
	public ModelAndView icd10Loader(@ModelAttribute("uploadData") UploadData uploadData,
			BindingResult bindResult, HttpServletRequest request) throws Exception {
	   ModelAndView mav = new ModelAndView();
//	   RowsCreatedOrUpdated data = new RowsCreatedOrUpdated();
	   UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");

       String validFileType = "ICD10";
       String versionId = "1.0";
       String fileType = uploadData.getFileType();
//       if (validFileType!=fileType) {
//       	throw new Exception("Bad Filetype:"+fileType);
//       }
       if (fileType==null) {
    	   throw new Exception("Missing FILE TYPE parameter from File Upload.");
       }

       UploadStatus status = new UploadStatus();
       File inputFile = null;
       byte[] fileByteArray = uploadData.getFileData().getBytes();
       if(fileType.equalsIgnoreCase("ICD10_CM")) {
    	   inputFile = IConstants.ICD_10_CM_CODE_FILE;
    	   status = icdService.uploadStoreIcd10File(fileType,inputFile, versionId, fileByteArray, userInfo);
       }else if(fileType.equalsIgnoreCase("ICD10_CM_ADDENDUM")) {
    	   inputFile = IConstants.ICD_10_CM_CODE_ADDENDUM_FILE;
    	   status = icdService.uploadStoreAddendumIcd10File(fileType,inputFile, versionId, fileByteArray, userInfo);
       }else if(fileType.equalsIgnoreCase("ICD10_PCS")) {
    	   inputFile = IConstants.ICD_10_PCS_CODE_FILE;
    	   status = icdService.uploadStoreIcd10File(fileType,inputFile, versionId, fileByteArray, userInfo);
       }else if(fileType.equalsIgnoreCase("ICD10_PCS_ADDENDUM")) {
    	   inputFile = IConstants.ICD_10_PCS_CODE_ADDENDUM_FILE;
    	   status = icdService.uploadStoreAddendumIcd10File(fileType,inputFile, versionId, fileByteArray, userInfo);
       }else {
    	   throw new Exception("Unknown FILE TYPE parameter from File Upload:"+fileType);
       }

       mav.addObject("status", status);
       mav.setViewName("codes/uploadStatus.jsp");

       return mav;
	}

	/**
	 *
	 * @param uploadData
	 * @param bindResult
	 * @param request
	 * @return
	 */
//	@RequestMapping("/uploadAndStoreICD10Addendum")
//	public ModelAndView icd10LoaderAddendum(@ModelAttribute("uploadData") UploadData uploadData,
//			BindingResult bindResult, HttpServletRequest request) throws Exception {
//	   ModelAndView mav = new ModelAndView();
//	   RowsCreatedOrUpdated data = new RowsCreatedOrUpdated();
//	   UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
//
//      String validFileType = "ICD10";
//      String versionId = "1.0";
//      String fileType = uploadData.getFileType();
////      if (validFileType!=fileType) {
////      	throw new Exception("Bad Filetype:"+fileType);
////      }
//
//      File inputFile = IConstants.ICD_10_CODE_CM_FILE;
//      byte[] fileByteArray = uploadData.getFileData().getBytes();
//      UploadStatus status =  icdService.uploadStoreAddendumIcd10File(fileType,inputFile, versionId, fileByteArray, userInfo);
//      mav.addObject("status", status);
//      mav.setViewName("codes/uploadStatus.jsp");
//
//      return mav;
//	}

	/**
	 *
	 * @param uploadData
	 * @param bindResult
	 * @param request
	 * @return
	 */
//	@RequestMapping("/uploadAndStoreAddendumICD10")
//	public ModelAndView icd10AddendumLoader(@ModelAttribute("uploadData") UploadData uploadData,
//			BindingResult bindResult, HttpServletRequest request) throws Exception {
//	   ModelAndView mav = new ModelAndView();
//	   RowsCreatedOrUpdated data = new RowsCreatedOrUpdated();
//	   UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
//
//      String validFileType = "ICD10";
//      String versionId = "1.0";
//      String fileType = uploadData.getFileType();
//      if (validFileType!=fileType) {
//      	throw new Exception("Bad Filetype:"+fileType);
//      }

//      File inputFile = IConstants.ICD_10_CODE_CM_FILE;
//      byte[] fileByteArray = uploadData.getFileData().getBytes();
//      UploadStatus status =  icdService.uploadStoreIcd10File(fileType,inputFile,versionId, fileByteArray, userInfo);
//      mav.addObject("status", status);
//      mav.setViewName("codes/uploadStatus.jsp");
//
//      return mav;
//	}

	@RequestMapping("/uploadFile")
	public ModelAndView fileUploadForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		UploadData data = new UploadData();
		UserInfo userInfo = (UserInfo) request.getSession(true).getAttribute("userInfo");
		mav.addObject("uploadData", data);
		mav.setViewName("codes/uploadFile.jsp");

		return mav;
	}

}
