/**
 * 
 */
package com.empirestateids.utils;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;

import net.ipowerlift.atlas.web.UserRegController;

/**
 * @author Syed
 *
 */
public final class CustomValidationUtils {
	
	static Logger logger = Logger.getLogger(CustomValidationUtils.class);
	
	//validate emails
	public static boolean isValidEmail(String emailAddress){
		boolean isValidEmail = false;
		if(emailAddress!=null){
			isValidEmail = EmailValidator.getInstance().isValid(emailAddress);
		}
		return isValidEmail;
	}
	
	//validate username
	/*
	^                    # Start of the line
	  [a-z0-9_!]	     # Match characters and symbols in the list, a-z, 0-9, underscore
	             {6,20}  # Length at least 6 characters and maximum length of 20 
	$                    # End of the line
	*/
	public static boolean isValidUsername(String username){
		
		boolean isValidUsername = false;
		if(username!=null){
			isValidUsername = username.matches("^[a-zA-Z0-9_!]{6,20}$");
			logger.error("USERNAME VALIDATE:"+username);
		}else {
			logger.error("USERNAME NULL:'"+username+"'");
		}
		return isValidUsername;
	}
	
	//validate password
	/*
	(			# Start of group
	  (?=.*\d)		#   must contains one digit from 0-9
	  (?=.*[a-z])		#   must contains one lowercase characters
	  (?=.*[A-Z])		#   must contains one uppercase characters
	              .		#     match anything with previous condition checking
	                {6,20}	#        length at least 6 characters and maximum of 20	
	)			# End of group
	*/
	public static boolean isValidComplexPassword(String password){
		boolean isValidUsername = false;
		if(password!=null){
			isValidUsername = password.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})");
		}
		return isValidUsername;
	}
	
	//validate simple password
	/*
	^                    # Start of the line
	  [A-Za-z0-9]	     # Match characters and symbols in the list, a-z, 0-9
	             {6,20}  # Length at least 6 characters and maximum length of 20
	$                    # End of the line
	*/
	public static boolean isValidSimplePassword(String password){
		boolean isValidUsername = false;
		if(password!=null){
			isValidUsername = password.matches("^[A-Za-z0-9]{6,20}$");
		}
		return isValidUsername;
	}
	
	public static void main(String[] args){
		System.out.println("is 'charliedidonato' valid:" + CustomValidationUtils.isValidUsername("charliedidonato"));
	}
}
