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
@Component("UserRegEditProfileValidator")
public class UserRegEditProfileValidator implements Validator {

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
		//ValidationUtils.rejectIfEmptyOrWhitespace(e, "user.username", "username.empty", "Username empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "email.email", "email.empty", "Email empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "groupMember.groupId", "groupId.empty", "Permission Group empty");
		
		//userRegService = new UserRegServiceImpl();
        
		UserRegistration userReg = (UserRegistration) obj;
		
//		if ( ! e.hasFieldErrors("user.username")) {
//	        if(userRegService.userNameExists(userReg.getUser().getUsername())){
//	        	//e.reject("user.username", "Username exists");
//	        	e.rejectValue("user.username", "username.exists", "Username exists");
//	        }
//		}
//		
//		if ( ! e.hasFieldErrors("user.username")) {
//	        if( ! CustomValidationUtils.isValidUsername(userReg.getUser().getUsername())){
//	        	e.rejectValue("user.username", "username.invalid", "Invalid Username");
//	        }
//		}
		
		if ( ! e.hasFieldErrors("email.email")) {
	        if( ! EmailValidator.getInstance().isValid(userReg.getEmail().getEmail())){
	        	e.rejectValue("email.email", "email.invalid", "Invalid Email");
	        }
		}
		
		if ( ! e.hasFieldErrors("email.email")) {
			if(!userReg.getCurrentEmail().equalsIgnoreCase(userReg.getEmail().getEmail())){
		        if(userRegService.emailExists(userReg.getEmail().getEmail())){
		        	e.rejectValue("email.email", "email.exists", "Email Id already registered");
		        }
			}
		}
	}

}
