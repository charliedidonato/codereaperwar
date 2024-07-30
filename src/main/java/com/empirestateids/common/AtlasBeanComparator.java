package com.empirestateids.common;

import java.util.Comparator;
import java.util.Locale;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.validator.GenericValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.empirestateids.exception.GenericException;
import com.empirestateids.utls.UtilityMethods;

public class AtlasBeanComparator extends BeanComparator {
	static Logger logger = LogManager.getLogger(AtlasBeanComparator.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AtlasBeanComparator(String property) {
		super(property);
	}

	public AtlasBeanComparator(String property, Comparator comp) {
		super(property, comp);
	}

	public int compare(Object p0, Object p1) {
		String property0 = null;
		String property1 = null;
		try {
			property0 = BeanUtils.getProperty(p0, this.getProperty());
			property1 = BeanUtils.getProperty(p1, this.getProperty());
		} catch (Exception e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			return 0;
		}
		
		if (property0 == null || property0.equals(""))
			return -1;
		if (property1 == null || property1.equals(""))
			return 1;
		
		// check if comparing a String that is a Date, cast to Date for proper compare
		if (GenericValidator.isDate(property0, Locale.US)) {
			java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy");
			try {
				java.util.Date dateValue0 = df.parse(property0);
				java.util.Date dateValue1 = df.parse(property1);
				if (dateValue0.after(dateValue1)) {
					return 1;
				} else {
					if (dateValue0.equals(dateValue1)) {
						return 0;
					} else {
						return -1;
					}
				}
			} catch (java.text.ParseException e) {
				logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
				throw new GenericException("Error parsing dates to compare and sort. propert0="+property0+"; property1="+property1, e);
			}
		} else if (GenericValidator.isFloat(property0) || GenericValidator.isFloat(property1)){
			try {
				Float bdValue0 = new Float(property0);
				Float bdValue1 = new Float(property1);
				if (bdValue0.floatValue() > bdValue1.floatValue() ){
					return 1;
				} else {
					if (bdValue1.floatValue() == bdValue0.floatValue()){
						return 0;
					} else {
						return -1;
					}
				}
				
				
			}catch (NumberFormatException e){
				logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
				throw new GenericException("Error parsing BigDecimal to compare and sort. propert0="+property0+"; property1="+property1, e);
			}	
		} else if (GenericValidator.isInt(property0) && GenericValidator.isInt(property1)){
			logger.error("found Integer");
			try {
				Integer intValue0 = new Integer(property0);
				Integer intValue1 = new Integer(property1);
				if (intValue0 > intValue1){
					return 1;
				} else {
					if (intValue1 == intValue0){
						return 0;
					} else {
						return -1;
					}
				}
				
				
			}catch (NumberFormatException e){
				logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
				throw new GenericException("Error parsing Integers to compare and sort. propert0="+property0+"; property1="+property1, e);
			}
		} else {
		    // not a date, just use string comparison
		    return getComparator().compare(StringUtils.upperCase((String) property0), StringUtils.upperCase((String) property1));
		}
	}

}