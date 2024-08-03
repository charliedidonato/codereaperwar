/**
 * 
 */
package com.empirestateids.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.empirestateids.common.IConstants;
import com.empirestateids.common.SecurityUtil;
import com.empirestateids.dao.ContactMapper;
import com.empirestateids.dao.EmailMapper;
import com.empirestateids.dao.GroupMemberMapper;
import com.empirestateids.dao.IpowerliftIdMapper;
import com.empirestateids.dao.PersonMapper;
import com.empirestateids.dao.UsersMapper;
import com.empirestateids.domain.Contact;
import com.empirestateids.domain.Email;
import com.empirestateids.domain.EmailCriteria;
import com.empirestateids.domain.GroupMember;
import com.empirestateids.domain.GroupMemberCriteria;
import com.empirestateids.domain.IpowerliftId;
import com.empirestateids.domain.Person;
import com.empirestateids.domain.UserRegistration;
import com.empirestateids.domain.Users;
import com.empirestateids.domain.UsersCriteria;
import com.empirestateids.security.UserInfo;
import com.empirestateids.utils.CommonUtil;

/**
 * @author DiDonato
 *
 */
@Service("UserRegService")
public class UserRegServiceImpl implements UserRegService {
	
	static Logger logger = LogManager.getLogger(UserRegServiceImpl.class);
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private PersonMapper personMapper;
	
	@Autowired
	private ContactMapper contactMapper;
	
	@Autowired
	private EmailMapper emailMapper;
	
	@Autowired
	private GroupMemberMapper groupMemberMapper;
	
	//@Autowired
	//private UtilService utilService;

//	@Autowired
//	private RegistrationMapper registrationMapper;
	
	@Autowired
	private IpowerliftIdMapper ipowerliftIdMapper;
	
	
	/* (non-Javadoc)
	 * @see net.ipowerlift.atlas.service.UserRegService#saveUserReg(net.ipowerlift.atlas.domain.UserRegistration)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addUserReg(UserRegistration userReg, UserInfo userInfo) throws Exception {
		Users user = userReg.getUser();
		Person person = userReg.getPerson();
		Contact contact = userReg.getContact();
		Email email = userReg.getEmail();
		GroupMember groupMember = userReg.getGroupMember();
		IpowerliftId id = new IpowerliftId();
		Integer ipowerliftId = null;
		
		int status;

		Date createdDate = new Date();
		ipowerliftId = 0;
		id.setIpowerliftId(ipowerliftId);
		id.setCreatedDate(createdDate);
		id.setCreatedId(1000000);
		
		ipowerliftIdMapper.insert(id);
		if(logger.isDebugEnabled()) {
			logger.debug("ipowerliftId: " + ipowerliftId);
		}
		ipowerliftId = ipowerliftIdMapper.getLastInsertId();
		if(logger.isDebugEnabled()) {
			logger.debug("Ipowerlift LAST INSERT ID: " + ipowerliftId);	
		}
		
		userReg.setIpowerliftId(ipowerliftId);
	
		
		Integer contactId = new Integer(0);
		contact.setContactId(contactId);
		if(logger.isDebugEnabled()) {
			logger.debug("contactid: " + contactId);
		}
		
		CommonUtil.setAuditInfo(user, ipowerliftId);
		CommonUtil.setAuditInfo(person, ipowerliftId);
		CommonUtil.setAuditInfo(contact, ipowerliftId);
		CommonUtil.setAuditInfo(email, ipowerliftId);
		CommonUtil.setAuditInfo(groupMember, ipowerliftId);
		
		status = contactMapper.insertSelective(contact);
		
		if(logger.isDebugEnabled()) {
			logger.debug("Contact insert status: " + status);
		}
		
		contactId = contactMapper.getLastInsertId();
		if(logger.isDebugEnabled()) {
			logger.debug("Contact LAST INSERT ID: " + contactId);
		}
		
		//person
		person.setIpowerliftId(ipowerliftId);
		person.setContactId(contactId);
		
		status = personMapper.insertSelective(person);
		
		if(logger.isDebugEnabled()) {
			logger.debug("Person insert status: " + status);
		}
		
		//users
		user.setIpowerliftId(ipowerliftId);
		
		user.setPassword(SecurityUtil.encryptPassword(user.getPassword()));
		
		user.setAccountNonExpired(IConstants.TRUE);
		user.setAccountNonLocked(IConstants.TRUE);
		user.setCredentialsNonExpired(IConstants.TRUE);
		user.setEnable(IConstants.TRUE);
		
		status = usersMapper.insertSelective(user);
		
		if(logger.isDebugEnabled()) {
			logger.debug("Users insert status: " + status);
		}
		
		//email
		email.setContactId(contactId);
		email.setEmailClassType(IConstants.EMAIL_CLASS_TYPE_USER);
		
		status = emailMapper.insertSelective(email);
		
		if(logger.isDebugEnabled()) {
			logger.debug("Email insert status: " + status);
		}
		
		//groupMember
		groupMember.setUsername(user.getUsername());
		
		status = groupMemberMapper.insertSelective(groupMember);
		
		if(logger.isDebugEnabled()) {
			logger.debug("GroupMember insert status: " + status);
		}
	}
	
	/* (non-Javadoc)
	 * @see net.ipowerlift.atlas.service.UserRegService#saveUserReg(net.ipowerlift.atlas.domain.UserRegistration)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUserReg(UserRegistration userReg, UserInfo userInfo) throws Exception {
//		Users user = userReg.getUser();
		Person person = userReg.getPerson();
		Contact contact = userReg.getContact();
		Email email = userReg.getEmail();
		GroupMember groupMember = userReg.getGroupMember();
		
		int status;
		
//		user.setUpdatedById(userId);
//		user.setUpdated(date);
		
		CommonUtil.setAuditInfo(person, userInfo, false, true);
		CommonUtil.setAuditInfo(contact, userInfo, false, true);
		CommonUtil.setAuditInfo(email, userInfo, false, true);
		CommonUtil.setAuditInfo(groupMember, userInfo, false, true);
		
		//contact
		status = contactMapper.updateByPrimaryKeySelective(contact);
		
		if(logger.isDebugEnabled()) {
			logger.debug("Contact update status: " + status);
		}
		
		//person
		status = personMapper.updateByPrimaryKeySelective(person);
		
		if(logger.isDebugEnabled()) {
			logger.debug("Person udpate status: " + status);
		}
		
		//users
		//user.setPassword(SecurityUtil.encryptPassword(user.getPassword(), ipowerliftId));
		
//		status = usersMapper.updateByPrimaryKeySelective(user);
//		
//		if(logger.isDebugEnabled()) {
//			logger.debug("Users update status: " + status);
//		}
		
		//email
		email.setEmailClassType(IConstants.EMAIL_CLASS_TYPE_USER);
		status = emailMapper.updateByPrimaryKeySelective(email);
		
		if(logger.isDebugEnabled()) {
			logger.debug("Email update status: " + status);
		}
		
		//groupMember
		//groupMember.setUsername(user.getUsername());
		
		status = groupMemberMapper.updateByPrimaryKeySelective(groupMember);
		
		if(logger.isDebugEnabled()) {
			logger.debug("GroupMember update status: " + status);
		}
	}

//	/* (non-Javadoc)
//	 * @see net.ipowerlift.atlas.service.UserRegService#getUserReg(java.lang.Integer)
//	 */
//	@Override
//	public UserRegistration getUserRegMembershipExpireDate(Integer ipowerliftId) {
//		UserRegistration userReg = new UserRegistration();
//		
//		Users user = usersMapper.selectByPrimaryKey(ipowerliftId);
//		//user.setPassword(null);
//		
//		Person person = personMapper.selectByPrimaryKey(ipowerliftId);
//		Contact contact = contactMapper.selectByPrimaryKey(person.getContactId());
//		
//		EmailCriteria criteria = new EmailCriteria();
//		criteria.createCriteria().andContactIdEqualTo(person.getContactId());
//		Email email = emailMapper.selectByExample(criteria).get(0);
//		
//		GroupMemberCriteria criteriaGM = new GroupMemberCriteria();
//		criteriaGM.createCriteria().andUsernameEqualTo(user.getUsername());
//		GroupMember groupMember = groupMemberMapper.selectByExample(criteriaGM).get(0);
//		
//		userReg.setIpowerliftId(ipowerliftId);
//		userReg.setUser(user);
//		userReg.setPerson(person);
//		userReg.setContact(contact);
//		userReg.setEmail(email);
//		userReg.setGroupMember(groupMember);
//		
//		userReg.setCurrentEmail(email.getEmail());
//		
//		return userReg;
//	}
//	

	/* (non-Javadoc)
	 * @see net.ipowerlift.atlas.service.UserRegService#getUserReg(java.lang.Integer)
	 */
	@Override
	public UserRegistration getUserReg(Integer ipowerliftId) {
		UserRegistration userReg = new UserRegistration();
		
		Users user = usersMapper.selectByPrimaryKey(ipowerliftId);
		user.setPassword(null);
		
		Person person = personMapper.selectByPrimaryKey(ipowerliftId);
		Contact contact = contactMapper.selectByPrimaryKey(person.getContactId());
		
		EmailCriteria criteria = new EmailCriteria();
		criteria.createCriteria().andContactIdEqualTo(person.getContactId());
		Email email = emailMapper.selectByExample(criteria).get(0);
		
		GroupMemberCriteria criteriaGM = new GroupMemberCriteria();
		criteriaGM.createCriteria().andUsernameEqualTo(user.getUsername());
		GroupMember groupMember = groupMemberMapper.selectByExample(criteriaGM).get(0);
		
//		RegistrationCriteria regCriteria = new RegistrationCriteria();
//		regCriteria.createCriteria().andIpowerliftIdEqualTo(ipowerliftId);
//		List<Registration> registrationList = registrationMapper.selectByExample(regCriteria);
//		Registration registration = null;
//		if(registrationList != null){
//			for(Registration reg: registrationList){
//				registration = reg;
//			}
//			
//			if(registration != null){
//	            Calendar finalExpDate = Calendar.getInstance();
//	            finalExpDate.setTime(registration.getRegistrationDate());
//	            finalExpDate.add(Calendar.MONTH, registration.getPeriodMonths());
//	            
//	            userReg.setMembershipExpDate(finalExpDate.getTime());
//			} else {
//				userReg.setMembershipExpDate(null);
//			}
//		}
		
		userReg.setIpowerliftId(ipowerliftId);
		userReg.setUser(user);
		userReg.setPerson(person);
		userReg.setContact(contact);
		userReg.setEmail(email);
		userReg.setGroupMember(groupMember);		
		userReg.setCurrentEmail(email.getEmail());
		
		return userReg;
	}

	/* (non-Javadoc)
	 * @see net.ipowerlift.atlas.service.UserRegService#userNameExists(java.lang.String)
	 */
	@Override
	public boolean userNameExists(String userName){
		int count;
		UsersCriteria userCriteria = new UsersCriteria();
		userCriteria.createCriteria().andUsernameLikeInsensitive(userName);
		
		count = usersMapper.countByExample(userCriteria);
		
		if(logger.isDebugEnabled()) {
			logger.debug(userName + " found " + count + " times.");
		}
		
		if(count>0){
			//username exists
			return true;
		} else { 
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see net.ipowerlift.atlas.service.UserRegService#emailExists(java.lang.String)
	 */
	@Override
	public boolean emailExists(String email){
		int count;
		EmailCriteria emailCriteria = new EmailCriteria();
		emailCriteria.createCriteria().andEmailLikeInsensitive(email)
			.andEmailClassTypeEqualTo(IConstants.EMAIL_CLASS_TYPE_USER);
		
		count = emailMapper.countByExample(emailCriteria);
		
		if(logger.isDebugEnabled()) {
			logger.debug(email + " found " + count + " times.");
		}
		
		if(count>0){
			//email exists
			return true;
		} else { 
			return false;
		}
	}
	
	@Override
	public List<UserRegistration> getAllUserRegs() {
		List<Users> usersList = usersMapper.selectByExample(null);
		
		List<UserRegistration> userRegList = new ArrayList<UserRegistration>();
		
		for(Users user:usersList){
			userRegList.add(this.getUserReg(user.getIpowerliftId()));
		}
		
		return userRegList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePasswordUserReg(UserRegistration userReg, UserInfo userInfo) throws Exception {
		Users user = userReg.getUser();
		
		Integer ipowerliftId = userReg.getIpowerliftId();
		
		int status;
		
		CommonUtil.setAuditInfo(user, userInfo, false, true);
		
		//users
		user.setPassword(SecurityUtil.encryptPassword(user.getPassword()));
		
		user.setPasswordReset((short) 0);
		
		status = usersMapper.updateByPrimaryKeySelective(user);
		
		if(logger.isDebugEnabled()) {
			logger.debug("Users update status: " + status);
		}
	}

}
