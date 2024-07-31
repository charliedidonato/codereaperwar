/**
 * 
 */
package com.empirestateids.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.empirestateids.domain.UserRegistration;
import com.empirestateids.utils.CustomValidationUtils;

/**
 * @author Syed
 *
 */
@Component("UserRegChangePwdValidator")
public class UserRegChangePwdValidator implements Validator {
	
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
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "user.password", "password.empty", "Password empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "confirmPassword", "confirmPassword.empty", "Confirm Password is empty");
		
		UserRegistration userReg = (UserRegistration) obj;
		
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
		
		if (! e.hasFieldErrors("user.password") && ! e.hasFieldErrors("confirmPassword")) {
	        if(!(userReg.getUser().getPassword()).equals(userReg.getConfirmPassword()) ){
	        	e.rejectValue("confirmPassword", "confirmPassword.invalid", "Password Does not match with Confirm Password");
	        }
		}
	}

}
