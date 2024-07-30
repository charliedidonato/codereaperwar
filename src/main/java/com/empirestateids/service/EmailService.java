package com.empirestateids.service;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.empirestateids.domain.EmailExt;
import com.empirestateids.domain.UserRegistration;
import com.empirestateids.domain.Users;

public interface EmailService {
	
	boolean sendToUsers(List <Users> users,EmailExt emailExt,ApplicationContext applicationContext);

	boolean sendTemporaryPassword(UserRegistration userReg, String temporaryPassword);

	boolean sendUsername(UserRegistration userReg);
	
}	
