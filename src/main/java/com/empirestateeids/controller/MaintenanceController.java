package com.empirestateeids.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.ipowerlift.atlas.service.ShowThreadsService;
import net.ipowerlift.atlas.service.common.LogReaderService;

@Controller
@RequestMapping("/maint")
public class MaintenanceController extends AtlasController{
	
	@Autowired
	private ShowThreadsService showThreadsService;
	@Autowired
	private LogReaderService logReaderService;
	
		
	@RequestMapping("/showthreads")
	public ModelAndView showthreads() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("showthreads_text", showThreadsService.toString());
		mav.setViewName("showthreads.jsp");
		
		return mav;
	}
		
//	@RequestMapping("/logfilereader")
//	public ModelAndView logfilereader() 
//	    throws IllegalArgumentException, FileNotFoundException,Exception {
//		ModelAndView mav = new ModelAndView();
//		File logFile = new File("C:/atlasLogging/loging.log");
//		logReaderService.setFileName(logFile);
//		String data = logReaderService.getLogData();
//		mav.addObject("log_file_text", data);
//		mav.setViewName("logfilereader.jsp");
//		
//		return mav;
//	}
	
}