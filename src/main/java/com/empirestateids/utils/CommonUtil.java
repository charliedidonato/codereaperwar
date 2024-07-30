/**
 * CommonUtils for Atlas
 */
package com.empirestateids.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.empirestateids.common.AtlasBeanComparator;
import com.empirestateids.common.IConstants;
import com.empirestateids.security.UserInfo;
import com.empirestateids.utls.UtilityMethods;

/**
 * @author Syed
 *
 */
public class CommonUtil {
	static Logger logger = LogManager.getLogger(CommonUtil.class);
	
	 
	 public static void setAuditInfo(Object obj,String userid, Integer ipowerliftId, Integer userId) throws Exception  {
		 setAuditInfo(obj, userId, true, true);
		 setAuditInfo(obj, ipowerliftId, true, true);
		 setAuditInfo(obj, userId, true, true);
	 }
	
	public static void setAuditInfo(Object obj, UserInfo userInfo, boolean setInserts, boolean setUpdates) throws Exception{
		Integer userId = userInfo.getIpowerliftId();
		setAuditInfo(obj, userId, setInserts, setUpdates);
	}
	
	public static void setAuditInfo(Object obj, UserInfo userInfo) throws Exception{
		Integer userId = userInfo.getIpowerliftId();
		setAuditInfo(obj, userId, true, true);
	}

	public static void setAuditInfo(Object obj, Integer userId) throws Exception{
		setAuditInfo(obj, userId, true, true);
	}
	
	
	public static void setAuditInfo(Object obj, Integer userId, boolean setInserts, boolean setUpdates) throws Exception{
		Date date = Calendar.getInstance().getTime();
		try {
			if(setInserts){
				PropertyUtils.setSimpleProperty(obj, "createdById", userId);
				PropertyUtils.setSimpleProperty(obj, "created", date);
			}
			
			if(setUpdates){
				PropertyUtils.setSimpleProperty(obj, "updatedById", userId);
				PropertyUtils.setSimpleProperty(obj, "updated", date);
			}
		} catch (IllegalAccessException e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
    		throw e;
		} catch (InvocationTargetException e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
    		throw e;
		} catch (NoSuchMethodException e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
    		throw e;
		}
	}
	
	public static <T> void sort(List<T> beanList, String fieldName, String sortOrder){
		if(IConstants.SORT_ASC.equalsIgnoreCase(sortOrder)){
			AtlasBeanComparator beanComparator = new AtlasBeanComparator(fieldName);
			Collections.sort(beanList, beanComparator);
		} else {
			Comparator comp = new ReverseComparator(new AtlasBeanComparator(fieldName));
			Collections.sort(beanList, comp);
		}
	}
	
}
