package com.empirestateids.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.empirestateids.exception.GenericException;
import com.empirestateids.utls.UtilityMethods;

@Controller
@RequestMapping("/log")
public class LogController extends AtlasController{
	
	static Logger logger = LogManager.getLogger(LogController.class);
	
	@RequestMapping("/showLogFiles")
	public ModelAndView showLogFiles(){
		ModelAndView mav = new ModelAndView();
		File dir = new File("/codereaper");
		
		File[] fileArray = dir.listFiles();
		
		mav.addObject("fileList", Arrays.asList(fileArray));
		mav.setViewName("log/showLogFiles.jsp");
		
		return mav;
	}
	
	@RequestMapping("/getFile")
	public void getFile(@RequestParam String fileName, HttpServletResponse response){
		FileReader in = null;
		BufferedReader br = null;
		PrintWriter out = null;
		
		try {
			out = response.getWriter();

			out.println("<html><head><title>Read Text File</title></head>" +
					"<body>");
			
			File file = new File("/codereaper/"+fileName);
			
			in = new FileReader(file);
			br = new BufferedReader(in);
	        
	        String line = "";
	        out.println("<pre>");
	        while ((line = br.readLine()) != null){
	        	out.println(line);
	        }
	        out.println("</pre>");
	        
	        br.close();
	        in.close();
	        
	        out.println("</body></html>");
	        out.flush();
	        
		} catch (IOException e) {
			logger.error("Error getting log file", e);
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			throw new GenericException("Error getting log file",e);
		}finally{
			out.close();
		}
		
		
	}
	
}