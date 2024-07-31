package com.empirestateids.utils;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.empirestateids.utls.UtilityMethods;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Component
public class AtlasFreemarkerUtils {
	static Logger logger = LogManager.getLogger(AtlasFreemarkerUtils.class);
	
	/*
	 * Run Freemarker engine and get transformed text for the given model and template.
	 */
	public static String processFreemarkerTemplate(Configuration freemarkerConfig, Map model, String templatePath) throws IOException, TemplateException{
		StringBuffer content = new StringBuffer();
		
		if(logger.isInfoEnabled()){
			logger.info("Processing - Model: " + model);
			logger.info("Processing - templatePath: " + templatePath);
		}
		
		try {
			content.append(FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfig.getTemplate(templatePath), model));
		} catch (IOException e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			throw e;
		} catch (TemplateException e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			throw e;
		}
		
		return content.toString();
	}
}
