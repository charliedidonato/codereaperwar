package com.empirestateids.service;

import com.empirestateids.domain.UserRegistration;

public interface AuthService {
	
	public boolean sendTemporaryPassword(UserRegistration userReg) throws Exception;

	public boolean sendUsername(UserRegistration userReg) throws Exception;
	
}
