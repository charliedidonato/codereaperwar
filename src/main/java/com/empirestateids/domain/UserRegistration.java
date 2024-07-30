/**
 * 
 */
package com.empirestateids.domain;

/**
 * @author Syed
 *
 */
public class UserRegistration extends Atlas{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer ipowerliftId;
	
	private Person person = new Person();
	
	private Users user = new Users();
	
	private Contact contact = new Contact();
	
	private Email email = new Email();
	
	private GroupMember groupMember = new GroupMember();
	
	private String confirmPassword;
	private String currentEmail;
	
	/**
	 * @return the ipowerliftId
	 */
	public Integer getIpowerliftId() {
		return ipowerliftId;
	}
	/**
	 * @param ipowerliftId the ipowerliftId to set
	 */
	public void setIpowerliftId(Integer ipowerliftId) {
		this.ipowerliftId = ipowerliftId;
	}
	/**
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}
	/**
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}
	/**
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(Users user) {
		this.user = user;
	}
	/**
	 * @return the contact
	 */
	public Contact getContact() {
		return contact;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	/**
	 * @return the email
	 */
	public Email getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(Email email) {
		this.email = email;
	}
	/**
	 * @return the groupMember
	 */
	public GroupMember getGroupMember() {
		return groupMember;
	}
	/**
	 * @param groupMember the groupMember to set
	 */
	public void setGroupMember(GroupMember groupMember) {
		this.groupMember = groupMember;
	}
	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}
	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	/**
	 * @return the currentEmail
	 */
	public String getCurrentEmail() {
		return currentEmail;
	}
	/**
	 * @param currentEmail the currentEmail to set
	 */
	public void setCurrentEmail(String currentEmail) {
		this.currentEmail = currentEmail;
	}
	
}
