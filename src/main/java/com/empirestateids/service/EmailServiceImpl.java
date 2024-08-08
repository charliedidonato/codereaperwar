package com.empirestateids.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.empirestateids.common.IConstants;
import com.empirestateids.domain.AtlasGlobal;
import com.empirestateids.domain.EmailExt;
import com.empirestateids.domain.UserRegistration;
import com.empirestateids.domain.Users;
import com.empirestateids.utils.AtlasFreemarkerUtils;
import com.empirestateids.utls.UtilityMethods;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
@Service("EmailService")
public class EmailServiceImpl implements EmailService {
	static Logger logger = LogManager.getLogger(EmailServiceImpl.class);
	
	@Autowired
	private JavaMailSenderImpl mailSender;

	@Autowired
	private Configuration freemarkerConfig;
	
	/*
	 * Send email
	 */
	
	private void sendEmail(MimeMessage message) throws MessagingException, MailException{
		// send email if in production 
		// send email if in local and localemail is set to 'Y'
		// send email if stage is not set but localemail is set to 'Y' (default local server)
		if(AtlasGlobal.stage != null && (AtlasGlobal.stage.equals(IConstants.STAGE_PROD)
				|| (AtlasGlobal.stage.equals(IConstants.STAGE_LOCAL) && AtlasGlobal.localEmail)
				|| (AtlasGlobal.stage.equals(IConstants.STAGE_NOT_SPECIFIED) && AtlasGlobal.localEmail) ) ){
			//change subject line if its not in production
			if(!AtlasGlobal.stage.equals(IConstants.STAGE_PROD)){
				message.setSubject("PLEASE IGNORE THIS EMAIL. Testing email in Stage: " + AtlasGlobal.stage + ".  " + message.getSubject());
			}
			mailSender.setHost(IConstants.SMTP_MAILHOST);
		    mailSender.setPort(IConstants.SMTP_PORT);
		    mailSender.setUsername(IConstants.SMTP_USER);
		    mailSender.setPassword(IConstants.SMTP_PASS);
		    Properties props = mailSender.getJavaMailProperties();
		    props.put("mail.transport.protocol", "smtp");
		    props.put("mail.smtp.auth", "true");
		    props.put("mail.smtp.starttls.enable", "true");
		    props.put("mail.debug", "true");
			mailSender.send(message);
			
			logger.info("Mail sent : " + getMessageDetails(message));
		} else {
			logger.info("Mail NOT sent : " + getMessageDetails(message));
		}
	}
	
	/*
	 * Get message details as String
	 */
	private String getMessageDetails(MimeMessage message){
		StringBuffer strBuffer = new StringBuffer();
		Address[] recipients = null;
		
		strBuffer.append("Recipients: ");
		
		try{
			recipients = message.getRecipients(Message.RecipientType.TO);
			if(recipients != null){
				strBuffer.append("TO=" + InternetAddress.toString(recipients) + "; ");
			}
		} catch(MessagingException e){
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
		}

		try{
			recipients = message.getRecipients(Message.RecipientType.CC);
			if(recipients != null){
				strBuffer.append("CC=" + InternetAddress.toString(recipients) + "; ");
			}
		} catch(MessagingException e){
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
		}

		try{
			recipients = message.getRecipients(Message.RecipientType.BCC);
			if(recipients != null){
				strBuffer.append("BCC=" + InternetAddress.toString(recipients) + "; ");
			}
		} catch(MessagingException e){
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
		}
		
		try{
			strBuffer.append("Subject: " + message.getSubject() + "; ");
		} catch(MessagingException e){
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
		}
		
		return strBuffer.toString();
	}
	
	@Override
	public boolean sendToUsers(List<Users> users, EmailExt emailExt,ApplicationContext applicationContext) {


		return true;
	}



	@Override
	public boolean sendTemporaryPassword(UserRegistration userReg, String temporaryPassword) {
		try{
			Map model = new HashMap();
	        model.put("userReg", userReg);
	        model.put("temporaryPassword", temporaryPassword);
	        model.put("atlasProdUrl", IConstants.ATLAS_PROD_URL_STRING);
	        model.put("atlasProdContextUrl", IConstants.ATLAS_PROD_CONTEXT_URL);
	        
	        String html_content = AtlasFreemarkerUtils.processFreemarkerTemplate(freemarkerConfig, model,"auth/html_email_forgotPassword.ftl");
	        String text_content = AtlasFreemarkerUtils.processFreemarkerTemplate(freemarkerConfig, model,"auth/text_email_forgotPassword.ftl");
	        
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = null;
			
			// use the true flag to indicate you need a multipart message
			helper = new MimeMessageHelper(message, true);
			helper.setFrom(IConstants.DEFAULT_ATLAS_FROM_EMAIL);
			helper.setTo(userReg.getEmail().getEmail());
			helper.setSubject("AIHorsehandicapping.com temporary password for " + userReg.getUser().getIpowerliftId() + ". Name: " + userReg.getPerson().getLastName() + ", " + userReg.getPerson().getFirstName());
			
			helper.setText(text_content.toString(), html_content.toString());
			
			helper.setValidateAddresses(true);
			helper.setPriority(IConstants.EMAIL_PRIORITY_HIGH);
			
			this.sendEmail(message);
			
		} catch (IOException e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			return false;
		} catch (TemplateException e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			return false;
		} catch (MessagingException e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			return false;
		} catch(MailException e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			return false;
		} 
		
		return true;
	}

	@Override
	public boolean sendUsername(UserRegistration userReg) {
		try{
			Map model = new HashMap();
	        model.put("userReg", userReg);
	        model.put("atlasProdUrl", IConstants.ATLAS_PROD_URL_STRING);
	        model.put("atlasProdContextUrl", IConstants.ATLAS_PROD_CONTEXT_URL);
	        
	        String html_content = AtlasFreemarkerUtils.processFreemarkerTemplate(freemarkerConfig, model,"auth/html_email_forgotUsername.ftl");
	        String text_content = AtlasFreemarkerUtils.processFreemarkerTemplate(freemarkerConfig, model,"auth/text_email_forgotUsername.ftl");
	        
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = null;
			
			// use the true flag to indicate you need a multipart message
			helper = new MimeMessageHelper(message, true);
			helper.setFrom(IConstants.DEFAULT_ATLAS_FROM_EMAIL);
			helper.setTo(userReg.getEmail().getEmail());
			helper.setSubject("AIHorsehandicapping.com Username for " + userReg.getUser().getIpowerliftId() + ". Name: " + userReg.getPerson().getLastName() + ", " + userReg.getPerson().getFirstName());
			
			helper.setText(text_content.toString(), html_content.toString());
			
			helper.setValidateAddresses(true);
			helper.setPriority(IConstants.EMAIL_PRIORITY_HIGH);
			
			this.sendEmail(message);
			
		} catch (IOException e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			return false;
		} catch (TemplateException e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			return false;
		} catch (MessagingException e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			return false;
		} catch(MailException e) {
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			return false;
		} 
		
		return true;
	}
	
}
