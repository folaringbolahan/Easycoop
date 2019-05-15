package com.sift.financial.services;

import java.util.*;
import java.io.*;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.apache.commons.mail.Email;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import  org.springframework.core.io.*;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import com.sift.financial.member.*;
import com.sift.financial.*;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;


public class MailNotificationImpl {
	
	private final Log log = LogFactory.getLog(getClass());
	private boolean mailOk = false;
	private MailRecipientDAO mailRecipientDAO;
	private JavaMailSenderImpl mailSender;
	private String notifier;
	private String mailSubject;
	private String mailTo;
	private String filesToAttach[]=null;
	private String replyToAddress;
        
        //@Autowired
        private GenericConfigDAO genericConfigDAO;

        @Autowired
        private Mailtemplateutility mailtemplateutility;
            
       
        private List<MailRecipient> getRecipientsByMailEventAndAction(String eventShort, String action){
		
		return mailRecipientDAO.findByMailEventAndAction(eventShort,action);
	}
        

	private Map<String, String> groupMail;

	public Map<String, String> getGroupMail() {
		return groupMail;
	}

	public void setGroupMail(Map<String, String> groupMail) {
		this.groupMail = groupMail;
	}

	public MailRecipientDAO getMailRecipientDAO() {
		return mailRecipientDAO;
	}

	public void setMailRecipientDAO(MailRecipientDAO mailRecipientDAO) {
		this.mailRecipientDAO = mailRecipientDAO;
	}
	
        public List<MailRecipient> get(String mailId, String recipientType){
		return mailRecipientDAO.findByMailID(mailId);
	}
	
	
	public Map<String,Object> getMailRecipientsByEventAndAction(Object object, String event, String action) {
		
		Map<String,Object> notifMap = new HashMap<String, Object>();
		
		Set<String> toList= new HashSet<String>();
		Set<String> ccList= new HashSet<String>();
		Set<String> bccList= new HashSet<String>();
		
		              
                List<MailRecipient> thelist = getRecipientsByMailEventAndAction(event, action);
		
		buildRecepients(notifMap,  thelist, object,  toList, ccList, bccList);
                
		//buildRecepients(notifMap,  thelist,tradeReqFormm,  toList, ccList);
		
		//added this to cater for check if mail should be sent
		if (thelist!=null && thelist.size()>0)
		{
		 notifMap.put("DEL_FLG",thelist.get(0).getMailNotification().getDelFlg());
		}
		else
		{
		  notifMap.put("DEL_FLG","N");	
		}
	
		//log.info(" Mail template ==> "+template);
		log.info(" toList.toString[] ==> "+toList.toString());
		log.info(" ccList.toString[] ==> "+ccList.toString());
		log.info(" bccList.toString[] ==> " + bccList.toString());
		
		
		
		notifMap.put(ReminderInterface.TOLIST, toList);
		notifMap.put(ReminderInterface.CCLIST, ccList);
		notifMap.put(ReminderInterface.BCCLIST, bccList);
		
		
		
		return notifMap;
	
	}
	
	private void buildRecepients(Map<String,Object> notifMap, List<MailRecipient> thelist,Object object, Set<String> toList, Set<String> ccList){
		
		
		if(thelist!=null && thelist.size()>0){
			
			notifMap.put(ReminderInterface.SUBJECT,thelist.get(0).getMailNotification().getSubject());
			
			for(MailRecipient recipient : thelist ){
				
				if(recipient.getRecipientType().getRecipientType().trim().equalsIgnoreCase("TO")){
					//fillRecipientList(  object ,  toList,  recipient);
					
				}else if(recipient.getRecipientType().getRecipientType().trim().equalsIgnoreCase("CC")){
					//fillRecipientList(  object ,  ccList,  recipient);
				}
			}
		}
	}
	
  private void buildRecepients(Map<String,Object> notifMap, List<MailRecipient> thelist,Object object, Set<String> toList, Set<String> ccList, Set<String> bccList){
		
		if(thelist!=null && thelist.size()>0){
			
			///notifMap.put(ReminderInterface.SUBJECT,thelist.get(0).getMailNotification().getSubject());
			
			for(MailRecipient recipient : thelist ){
				
				if(recipient.getRecipientType().getRecipientType().trim().equalsIgnoreCase("TO")){
					fillRecipientList((Map<String, Object>)object ,  toList,  recipient);
					
				}else if(recipient.getRecipientType().getRecipientType().trim().equalsIgnoreCase("CC")){
					fillRecipientList(  (Map<String, Object>)object  ,  ccList,  recipient);
				}else if(recipient.getRecipientType().getRecipientType().trim().equalsIgnoreCase("BCC")){
					fillRecipientList(  (Map<String, Object>)object  ,  bccList,  recipient);
				}
			}
			
		}
	}
	

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public JavaMailSenderImpl getMailSender() {
		return mailSender;
	}
	
	private void fillRecipientList(Map<String, Object> object, Set<String> recpList, MailRecipient recipient){
            
            // initiator
            //  nextlevel
            //  ALL
          
            if(recipient.getRecipient().trim().equalsIgnoreCase(ReminderInterface.INITIATOR))
            {
			if(object!=null && object.size()>0)
                        {
                                recpList.add(((String)object.get(ReminderInterface.CREATOR)).toLowerCase());
			}
		
            }
            else if(recipient.getRecipient().trim().equalsIgnoreCase(ReminderInterface.ALL))
            { //
			if(object!=null && object.size()>0){
				
                            for(String a : (List<String>)object.get(ReminderInterface.COMPANYALL))
                            {
                                recpList.add(a.toLowerCase());
                            }
                           
			}
	    }
            else if(recipient.getRecipient().trim().equalsIgnoreCase(ReminderInterface.NEXTLEVEL))
            { 
                      if(object!=null && object.size()>0){
				
                            for(String a : (List<String>)object.get(ReminderInterface.NEXTLEVEL))
                            {
                                recpList.add(a.toLowerCase());
                            }
                           
			}
            }
	    else if( groupMail.containsKey(recipient.getRecipient().toUpperCase().trim()))
            {
			
			String[] commaMails = groupMail.get(recipient.getRecipient().toUpperCase().trim()).split(",");
			
			for(String themail :  commaMails){
				  
			      recpList.add(themail);
			}
			
	    }
            else
            {

			log.info(" Invalid mail receipient ==> "+recipient.getRecipient().toUpperCase().trim());
	    }
		
	}
	
	private final Log logger = LogFactory.getLog(getClass());
	/**
         * othetInfo - object to hold next steps
         * object - Mail body Info
         * event  - event in question
         * action - event action in question
         * 
         */
        public void sendMail(Object otherInfo, Map<String, Object> object, String event, String action){
		
		    boolean confMailSend= false;
	            setMailOk(false);
                    Map<String,Object> mailRecipInfo = null;
                    MimeMessageHelper helper = null;
		
			try {
				
				try {
					 helper = new MimeMessageHelper(mailSender.createMimeMessage(), true , "UTF-8");
					
					//mailMap= getMailRecipientsByMailTemplate(object, mailTemplate);
                                        
                                        mailRecipInfo =  getMailRecipientsByEventAndAction(otherInfo, event,action);

					helper.setFrom(notifier);
                                        
                                        Map<String,String> mailData =  buildMailBody(object, event,action);
                                        
					helper.setText(mailData.get(ReminderInterface.MAILBODY), true);
                                        helper.setSubject(mailData.get(ReminderInterface.SUBJECT));
                                                                                
				
					Set<String> toList=(Set<String>)mailRecipInfo.get(ReminderInterface.TOLIST);
					Set<String> ccList=(Set<String>)mailRecipInfo.get(ReminderInterface.CCLIST);
					Set<String> bccList=(Set<String>)mailRecipInfo.get(ReminderInterface.BCCLIST);
					
					helper.setTo((String[])toList.toArray(new String[toList.size()]));
					
					logger.info("BCc :: "+ bccList.toString().replace("[", "").replace("]", ""));
					logger.info("Cc :: "+ ccList.toString().replace("[", "").replace("]", ""));
					logger.info("To :: "+ toList.toString().replace("[", "").replace("]", ""));
					
					//populate CC
					if(ccList.size()>0){
						//helper.setCc(ccList.toString().replace("[", "").replace("]", ""));
						helper.setCc((String[])ccList.toArray(new String[ccList.size()]));
					}
					
					//populate BCC
					if(bccList.size()>0){
						//helper.setCc(ccList.toString().replace("[", "").replace("]", ""));
						helper.setBcc((String[])bccList.toArray(new String[bccList.size()]));
					}
					
					//Add reply to add
					helper.setReplyTo(replyToAddress);
										
					logger.info("Mail content ...." + helper.getMimeMessage().toString());
					logger.info("Mail Host ...." +  mailSender.getHost());
					
					if(mailData.get(ReminderInterface.MAILDELFLG).equals("N"))
					{
						mailSender.send(helper.getMimeMessage());
                                                confMailSend= true;
						logger.info("Mail successfully sent to recipients....");
					}
					else
					{
						logger.info("Configured not to send Mail to recipients....");
						confMailSend= false;
					}
					
					setMailOk(confMailSend);
					
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					logger.fatal("Notification Failure!!!",e);
					setMailOk(false);
				}
				
			} catch (MailException e) {
				
				logger.fatal("Notification Failure!!!",e);
				setMailOk(false);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				logger.fatal("Notification Failure!!!",e);
				setMailOk(false);
			}
			finally
			{
				if(confMailSend)
				{
					logger.info("Mail successfully sent to recipients.. about to start logging");
					//sendMailLog(object,mailBody,mailTemplate, mailMap,isMailOk());
					logger.info("Mail successfully sent to recipients....after logging");
				}
				helper=null;
			}
	}
        
        
     private Map<String, String> buildMailBody (Map<String, Object> obj, String eventShort, String action)
     {
    
          Map<String, String> mailData = new HashMap<String, String>();
          
          String sql = "select cast(a.email_id as CHAR) as mail_id, a.subject as subject, a.template as template, a.del_flg as del_flg from mail_notification a, event b where a.event_id=b.event_id and b.event_short='" + eventShort + "' and a.action='" +action+ "'";
         
          Map<String, String> mailResult =  getGenericConfigDAO().getObjectRead(sql);
         
          String htmlOutput =  mailtemplateutility.outputhtmlcfg(obj, mailResult.get("template"));
        
          log.info("Mail Meassege :: " +  htmlOutput);
          mailData.put(ReminderInterface.MAILBODY, htmlOutput);
          mailData.put(ReminderInterface.SUBJECT, mailResult.get("subject"));
          mailData.put(ReminderInterface.MAILDELFLG, mailResult.get("del_flg"));
    
       return mailData;
     }
	
	
     
     
  public void sendMail(Object otherInfo, Map<String, Object> object, String event, String action, String[] theFiles ){
		
		    boolean confMailSend= false;
			setMailOk(false);
			Map<String,Object> mailRecipInfo = null;
			MimeMessageHelper helper = null;
		
			try {
				
				try {
					 helper = new MimeMessageHelper(mailSender.createMimeMessage(), true , "UTF-8");
					
					//mailMap= getMailRecipientsByMailTemplate(object, mailTemplate);
                                        
                                        mailRecipInfo =  getMailRecipientsByEventAndAction(otherInfo,event,action);

					helper.setFrom(notifier);
                                        
                                        Map<String,String> mailData =  buildMailBody(object,event,action);
                                        
					helper.setText(mailData.get(ReminderInterface.MAILBODY), true);
                                        helper.setSubject(mailData.get(ReminderInterface.SUBJECT));
                                                                                
				
					Set<String> toList=(Set<String>)mailRecipInfo.get(ReminderInterface.TOLIST);
					Set<String> ccList=(Set<String>)mailRecipInfo.get(ReminderInterface.CCLIST);
					Set<String> bccList=(Set<String>)mailRecipInfo.get(ReminderInterface.BCCLIST);
					
					helper.setTo((String[])toList.toArray(new String[toList.size()]));
					
					logger.info("BCc :: "+ bccList.toString().replace("[", "").replace("]", ""));
					logger.info("Cc :: "+ ccList.toString().replace("[", "").replace("]", ""));
					logger.info("To :: "+ toList.toString().replace("[", "").replace("]", ""));
					
					//populate CC
					if(ccList.size()>0){
						//helper.setCc(ccList.toString().replace("[", "").replace("]", ""));
						helper.setCc((String[])ccList.toArray(new String[ccList.size()]));
					}
					
					//populate BCC
					if(bccList.size()>0){
						//helper.setCc(ccList.toString().replace("[", "").replace("]", ""));
						helper.setBcc((String[])bccList.toArray(new String[bccList.size()]));
					}
					
					//Add reply to add
					helper.setReplyTo(replyToAddress);
					
					//Add attachment
					if (theFiles != null  && theFiles.length>0)
					  {
						  for (String attachmentFile: theFiles)
						  {
							  FileDataSource fds=new FileDataSource(attachmentFile);
							  helper.addAttachment(fds.getName(), fds);
						  }
					  }
					
					
					logger.info("Mail content ...." + helper.getMimeMessage().toString());
					logger.info("Mail Host ...." +  mailSender.getHost());
					
					if(mailData.get(ReminderInterface.MAILDELFLG).equals("N"))
					{
						mailSender.send(helper.getMimeMessage());
                                                confMailSend=true;
						logger.info("Mail successfully sent to recipients....");
					}
					else
					{
						logger.info("Configured not to send Mail to recipients....");
						confMailSend= false;
					}
					
					setMailOk(confMailSend);
					
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					logger.fatal("Notification Failure!!!",e);
					setMailOk(false);
				}
				
			} catch (MailException e) {
				
				logger.fatal("Notification Failure!!!",e);
				setMailOk(false);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				logger.fatal("Notification Failure!!!",e);
				setMailOk(false);
			}
			finally
			{
				if(confMailSend)
				{
					logger.info("Mail successfully sent to recipients.. about to start logging");
					//sendMailLog(object,mailBody,mailTemplate, mailMap,isMailOk());
					logger.info("Mail successfully sent to recipients....after logging");
				}
				
				helper=null;
			}
	}

    public String getReplyToAddress() {
        return replyToAddress;
    }

    public void setReplyToAddress(String replyToAddress) {
        this.replyToAddress = replyToAddress;
    }
	
	
	

	public void setNotifier(String notifier) {
		this.notifier = notifier;
	}

	public String getNotifier() {
		return notifier;
	}

	/**
	 * @return the mailSubject
	 */
	public String getMailSubject() {
		return mailSubject;
	}

	/**
	 * @param mailSubject the mailSubject to set
	 */
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	/**
	 * @return the mailResult
	 */
	public boolean isMailOk() {
		return mailOk;
	}

	/**
	 * @param mailResult the mailResult to set
	 */
	public void setMailOk(boolean mailOk) {
		this.mailOk = mailOk;
	}

	/**
	 * @return the filesToAttach
	 */
	public String[] getFilesToAttach() {
		return filesToAttach;
	}

	/**
	 * @param filesToAttach the filesToAttach to set
	 */
	public void setFilesToAttach(String[] filesToAttach) {
		this.filesToAttach = filesToAttach;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

        public GenericConfigDAO getGenericConfigDAO() {
            return genericConfigDAO;
        }
         @Autowired
        public void setGenericConfigDAO(GenericConfigDAO genericConfigDAO) {
            this.genericConfigDAO = genericConfigDAO;
        }

	

}
