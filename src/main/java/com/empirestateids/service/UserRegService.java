/**
 * 
 */
package com.empirestateids.service;


import java.util.List;

import com.empirestateids.domain.UserRegistration;
import com.empirestateids.security.UserInfo;

/**
 * @author Syed
 *
 */
public interface UserRegService {

	public UserRegistration getUserReg(Integer playerId);
	
	public boolean userNameExists(String userName);

	public void updateUserReg(UserRegistration userReg, UserInfo userInfo) throws Exception;

	//public void addUserReg(UserRegistration userReg) throws Exception;
	
	public void addUserReg(UserRegistration userReg, UserInfo userInfo) throws Exception;

	public List<UserRegistration> getAllUserRegs();

	public void updatePasswordUserReg(UserRegistration userReg, UserInfo userInfo) throws Exception;

	public boolean emailExists(String email);
	
}
