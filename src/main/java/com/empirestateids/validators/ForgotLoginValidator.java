/**
 * 
 */
package com.empirestateids.validators;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.empirestateids.domain.UserRegistration;
import com.empirestateids.service.UserRegService;

/**
 * @author Syed
 *
 */
@Component("ForgotLoginValidator")
public class ForgotLoginValidator implements Validator {

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
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "email.email", "email.empty", "Email empty");
		
		UserRegistration userReg = (UserRegistration) obj;
		
		if ( ! e.hasFieldErrors("email.email")) {
	        if( ! EmailValidator.getInstance().isValid(userReg.getEmail().getEmail())){
	        	e.rejectValue("email.email", "email.invalid", "Invalid Email");
	        }
		}
		
		if ( ! e.hasFieldErrors("email.email")) {
	        if(!userRegService.emailExists(userReg.getEmail().getEmail())){
	        	e.rejectValue("email.email", "email.notExists", "Email not registered");
	        }
		}
	}

}
