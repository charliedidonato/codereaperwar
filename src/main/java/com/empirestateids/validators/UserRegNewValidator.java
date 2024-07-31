/**
 * 
 */
package com.empirestateids.validators;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.empirestateids.domain.UserRegistration;
import com.empirestateids.service.UserRegService;
import com.empirestateids.utils.CustomValidationUtils;

/**
 * @author Syed
 *
 */
@Component("UserRegNewValidator")
public class UserRegNewValidator implements Validator {

	static Logger logger = LogManager.getLogger(UserRegNewValidator.class);
	
	@Autowired
	private UserRegService userRegService;
	
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return UserRegistration.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object obj, Errors e) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "person.lastName", "lastName.empty", "Last Name empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "person.firstName", "firstName.empty", "First Name empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "user.username", "username.empty", "Username empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "user.password", "password.empty", "Password empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "confirmPassword", "confirmPassword.empty", "Confirm Password is empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "email.email", "email.empty", "Email empty");
		//ValidationUtils.rejectIfEmptyOrWhitespace(e, "groupMember.groupId", "groupId.empty", "Permission Group empty");
		UserRegistration userReg = (UserRegistration) obj;
		
		if ( ! e.hasFieldErrors("user.username")) {
	        if(userRegService.userNameExists(userReg.getUser().getUsername())){
	        	e.rejectValue("user.username", "username.exists", "Username exists");
	        }
		}
		
		if ( ! e.hasFieldErrors("user.username")) {
	        if( ! CustomValidationUtils.isValidUsername(userReg.getUser().getUsername())){
	        	logger.error("Invalid USERNAME:"+userReg.getUser().getUsername());
	        	e.rejectValue("user.username", "username.invalid", "Invalid Username");
	        }
		}
			
		if (! e.hasFieldErrors("user.password") && ! e.hasFieldErrors("confirmPassword")) {
	        if(!(userReg.getUser().getPassword()).equals(userReg.getConfirmPassword()) ){
	        	e.rejectValue("confirmPassword", "confirmPassword.invalid", "Password Does not match with Confirm Password");
	        }
		}
		
		if ( ! e.hasFieldErrors("user.password")) {
	        if( ! CustomValidationUtils.isValidComplexPassword(userReg.getUser().getPassword())){
	        	e.rejectValue("user.password", "password.invalid", "Invalid Password");
	        }
		}
		
		if ( ! e.hasFieldErrors("confirmPassword")) {
	        if( ! CustomValidationUtils.isValidComplexPassword(userReg.getConfirmPassword())){
	        	e.rejectValue("confirmPassword", "confirmPassword.invalid", "Invalid Confirm Password");
	        }
		}
		
		
		if ( ! e.hasFieldErrors("email.email")) {
	        if( ! EmailValidator.getInstance().isValid(userReg.getEmail().getEmail())){
	        	e.rejectValue("email.email", "email.invalid", "Invalid Email");
	        }
		}
		
		if ( ! e.hasFieldErrors("email.email")) {
	        if(userRegService.emailExists(userReg.getEmail().getEmail())){
	        	e.rejectValue("email.email", "email.exists", "Email Id already registered");
	        }
		}
		
	}

}
