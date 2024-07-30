package com.empirestateids.utls;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.logging.log4j.Logger;

import com.empirestateids.common.IConstants;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

/**
 * Sends e-mail via JavaMail 
 */
public class JavaEmail  {

	static Logger theLog = Logger.getLogger(JavaEmail.class);
    
    private String theTo = "";
    private String theCc = "";
    private String theBcc = "";
    private String theFrom = "";
    private String theReplyTo = "";
    private String theSubject = "";
    private String theText = "";
    private List theAttachment = null;

    public void setTo (String theToIn)  { theTo = theToIn; }
    public void setCc (String theCcIn)  { theCc = theCcIn; }
    public void setBcc (String theBccIn)  { theBcc = theBccIn; }
    public void setFrom (String theFromIn)  { theFrom = theFromIn;  }
    public void setReplyTo (String theReplyToIn)  { theReplyTo = theReplyToIn; }
    public void setSubject (String theSubjectIn )  { theSubject = theSubjectIn; }
    public void setText (String theTextIn)  { theText = theTextIn; }
    public void setAttachment (List theAttachmentIn)  { theAttachment = theAttachmentIn; }

    public String getTo ()  { return theTo; }
    public String getCc ()  { return theCc; }
    public String getBcc ()  { return theBcc; }
    public String getFrom ()  { return theFrom;  }
    public String getReplyTo ()  { return theReplyTo; }
    public String getSubject ()  { return theSubject; }
    public String getText ()  { return theText; }
    public List geAttachment ()  { return theAttachment; }
   
    public void JavaEmail() {
    	
    }

    public void send()  {
    	if (theTo.equals("")||theTo.trim().length() == 0)  {
            theLog.error("No recipient specified");
            return;
        }
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", IConstants.SMTP_MAILHOST);
		props.put("mail.smtp.port", IConstants.SMTP_PORT);
		
        Session session = Session.getInstance(props);
//		Session session = Session.getDefaultInstance(props,
//				new javax.mail.Authenticator() {
//					protected PasswordAuthentication getPasswordAuthentication() {
//						return new PasswordAuthentication(IConstants.SMTP_USER,
//								IConstants.SMTP_PASS);
//					}
//				});
        try  {
        	
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(theFrom));
			InternetAddress[] toAddress = parseAddressList(theTo);
			msg.setRecipients(Message.RecipientType.TO, toAddress);
//			InternetAddress[] ccAddress = parseAddressList(theCc);
//			msg.setRecipients(Message.RecipientType.CC, ccAddress);
//			InternetAddress[] bccAddress = parseAddressList(theBcc);
//			msg.setRecipients(Message.RecipientType.BCC, bccAddress);
			msg.setSubject(theSubject);
			msg.setSentDate(new java.util.Date());

			msg.setText(theText);
			Transport bus = session.getTransport("smtp");
			bus.connect(IConstants.SMTP_MAILHOST,
					IConstants.SMTP_USER,IConstants.SMTP_PASS);
			if (theAttachment != null) {
				setFileAsAttachment(msg, theAttachment);
			}
			msg.saveChanges();
			//System.out.println("To: " + theTo + "; From: "+ theFrom+"; Subject: " + theSubject);
			bus.sendMessage(msg, toAddress);
//			if(ccAddress != null){
//				bus.sendMessage(msg, ccAddress);
//			}
//			if(bccAddress != null){
//				bus.sendMessage(msg, bccAddress);
//			}
			bus.close();
			theLog.debug("To: " + theTo + ";Subject: " + theSubject);
			
        } catch (Exception e)  {
            theLog.error("Exception:"+e.getMessage()+
            	"Trace:"+UtilityMethods.getStackTrace(e));
        }
    }
    
    public void setFileAsAttachment(Message msg, List attachedFiles)
    throws MessagingException, FileNotFoundException {
    	MimeBodyPart p1 = new MimeBodyPart();
    	p1.setText(theText);
        Multipart mp = new MimeMultipart();
        mp.addBodyPart(p1);
        Iterator i = attachedFiles.iterator(); 
        while (i.hasNext()){
        	MimeBodyPart p2 = new MimeBodyPart();
    		FileDataSource fds = new FileDataSource((String)i.next());
            p2.setDataHandler(new DataHandler(fds));
            p2.setFileName(fds.getName());
            mp.addBodyPart(p2);
        }
    	msg.setContent(mp);
    }

	private InternetAddress[] parseAddressList(String theToList) 
	throws Exception {
		Vector v = new Vector();
        String token;
        for (StringTokenizer st = new StringTokenizer(theToList, ","); st.hasMoreTokens(); ) {
           token = st.nextToken().trim();
           v.addElement(new InternetAddress(token));
        }
        InternetAddress array[] = new InternetAddress[v.size()];
        v.copyInto(array);
        return array;
   }
}
