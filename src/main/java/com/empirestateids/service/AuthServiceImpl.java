/**
 * 
 */
package com.empirestateids.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.empirestateids.common.IConstants;
import com.empirestateids.common.SecurityUtil;
import com.empirestateids.dao.EmailMapper;
import com.empirestateids.dao.PersonMapper;
import com.empirestateids.dao.UsersMapper;
import com.empirestateids.domain.Email;
import com.empirestateids.domain.EmailCriteria;
import com.empirestateids.domain.Person;
import com.empirestateids.domain.PersonCriteria;
import com.empirestateids.domain.UserRegistration;
import com.empirestateids.domain.Users;
import com.empirestateids.exception.GenericException;



/**
 * @author Syed
 *
 */
@Component
@Service("AuthService")
public class AuthServiceImpl implements AuthService {
	static Logger logger = LogManager.getLogger(AuthServiceImpl.class);
	
	@Autowired
	private PersonMapper personMapper;
	@Autowired
	private EmailMapper emailMapper;
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private EmailService emailService;
	
	/* (non-Javadoc)
	 * @see net.ipowerlift.atlas.service.AuthService#sendTemporaryPassword(net.ipowerlift.atlas.domain.UserRegistration)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean sendTemporaryPassword(UserRegistration userReg) throws Exception {
		boolean forgotPasswordSuccess = false;
		Users user = null;
		Email email = null;
		Person person = null;
		
		String emailId = userReg.getEmail().getEmail();
		
		// update user account
		EmailCriteria emailCriteria = new EmailCriteria();
		emailCriteria.createCriteria().andEmailLikeInsensitive(emailId)
			.andEmailClassTypeEqualTo(IConstants.EMAIL_CLASS_TYPE_USER);
		List<Email> emailList = emailMapper.selectByExample(emailCriteria);
		
		// do not reset password if more than one account found with the same email id
		if(emailList.size() == 1){
			email = emailList.get(0);
			
			PersonCriteria personCriteria = new PersonCriteria();
			personCriteria.createCriteria().andContactIdEqualTo(email.getContactId());
			List<Person> personList = personMapper.selectByExample(personCriteria);

			if(personList != null && personList.size() ==1){
				person = personList.get(0);
				
				// generate password
				String temporaryPassword = SecurityUtil.generateRandomPassword(IConstants.TEMPORARY_PASSWORD_LENGTH);
				
				user = new Users();
				user.setIpowerliftId(person.getIpowerliftId());
				user.setPassword(SecurityUtil.encryptPassword(temporaryPassword));
				user.setPasswordReset((short) 1);
				
				int status = usersMapper.updateByPrimaryKeySelective(user);
				
				if(logger.isDebugEnabled()) {
					logger.debug("Users update status: " + status);
				}
				
				// email user
				userReg.setEmail(email);
				userReg.setPerson(person);
				userReg.setUser(user);
				
				boolean emailStatus = false;
				
				emailStatus = emailService.sendTemporaryPassword(userReg, temporaryPassword);
				
				if(emailStatus){
					forgotPasswordSuccess = true;
				} else {
					logger.error("Error sending reset password email for account with email id: " + emailId);
					throw new GenericException("Error sending Temporary Password email");
				}
				
			} else {
				forgotPasswordSuccess = false;
				logger.error("More than one account found with primary email id: " + emailId);
			}
			
		} else {
			forgotPasswordSuccess = false;
			logger.error("User forgot password and trying to reset it but found more than one account with primary email id: " + emailId);
		}
		
		// release object to gc
		userReg = null;
		
		return forgotPasswordSuccess;
	}
	
	/* (non-Javadoc)
	 * @see net.ipowerlift.atlas.service.AuthService#sendUsername(net.ipowerlift.atlas.domain.UserRegistration)
	 */
	@Override
	public boolean sendUsername(UserRegistration userReg) throws Exception {
		boolean forgotUsernameSuccess = false;
		Users user = null;
		Email email = null;
		Person person = null;
		
		String emailId = userReg.getEmail().getEmail();
		
		EmailCriteria emailCriteria = new EmailCriteria();
		emailCriteria.createCriteria().andEmailLikeInsensitive(emailId)
			.andEmailClassTypeEqualTo(IConstants.EMAIL_CLASS_TYPE_USER);
		List<Email> emailList = emailMapper.selectByExample(emailCriteria);
		
		// do not send email if more than one account found with the same email id
		if(emailList.size() == 1){
			email = emailList.get(0);
			
			PersonCriteria personCriteria = new PersonCriteria();
			personCriteria.createCriteria().andContactIdEqualTo(email.getContactId());
			List<Person> personList = personMapper.selectByExample(personCriteria);

			if(personList != null && personList.size() ==1){
				person = personList.get(0);
				
				user = usersMapper.selectByPrimaryKey(person.getIpowerliftId());
				
				// email user
				userReg.setEmail(email);
				userReg.setPerson(person);
				userReg.setUser(user);
				
				boolean emailStatus = false;
				
				emailStatus = emailService.sendUsername(userReg);
				
				if(emailStatus){
					forgotUsernameSuccess = true;
				} else {
					logger.error("Error sending Forgot Username email for account with email id: " + emailId);
					throw new GenericException("Error sending Forgot Username email");
				}
				
			} else {
				forgotUsernameSuccess = false;
				logger.error("More than one account found with primary email id: " + emailId);
			}
			
		} else {
			forgotUsernameSuccess = false;
			logger.error("User forgot username but found more than one account with primary email id: " + emailId);
		}
		
		// release object to gc
		userReg = null;
		
		return forgotUsernameSuccess;
	}

}
