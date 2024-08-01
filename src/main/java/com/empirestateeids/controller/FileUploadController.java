package com.empirestateeids.controller;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.empirestateids.common.IConstants;
import com.empirestateids.domain.TestBean;

@Controller
@RequestMapping(value = "/test")
public class FileUploadController extends AtlasController {
	
	static Logger logger = LogManager.getLogger(FileUploadController.class);
	
	@RequestMapping(value = "/uploadForm", method = RequestMethod.GET)
	public ModelAndView uploadForm() {
		ModelAndView mav = new ModelAndView();

		TestBean testBean = new TestBean();
		
		mav.addObject("testBean", testBean);
		mav.setViewName("test/uploadForm.jsp");

		return mav;
	}
	
	@RequestMapping(value = "/saveForm", method = RequestMethod.POST)
	public String saveForm(TestBean uploadItem, BindingResult result) {
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				logger.error("Error: " + error.getCode() + " - "
						+ error.getDefaultMessage());
			}
			return "test/uploadForm";
		}

//		// Some type of file processing...
		System.err.println("-------------------------------------------");
		System.err.println("Upload file name: "
				+ uploadItem.getMultiPartFile().getOriginalFilename());
		System.err.println("-------------------------------------------");

		CommonsMultipartFile multiPartFile = uploadItem.getMultiPartFile();
		
		byte[] byteArray = multiPartFile.getBytes();
		// do whatever
		//
		
//		try {
//			InputStream is = multiPartFile.getInputStream();
//			// do whatever
//			//
//		} catch (IOException e) {
//			logger.error("Error uploading file:IOException", e);
//		}
		
		if(!new File(IConstants.FILE_UPLOAD_LOC).exists()){
			new File(IConstants.FILE_UPLOAD_LOC).mkdir();
		}
		
		File file = new File(IConstants.FILE_UPLOAD_LOC+multiPartFile.getOriginalFilename());
		System.err.println("File Path:"+file.getAbsolutePath());
		
		try {
			multiPartFile.transferTo(file);
		} catch (IllegalStateException e) {
			logger.error("Error uploading file:Illegal State", e);
		} catch (IOException e) {
			logger.error("Error uploading file: IOException", e);
		}
		
		return "redirect:/test/uploadForm";
	}
}
