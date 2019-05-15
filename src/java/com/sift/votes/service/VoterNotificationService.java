/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.gl.*;
import com.sift.gl.model.Activitylog;
import com.sift.votes.bean.MailvtBean;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
//import javax.jms.Session;
import javax.naming.NamingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

/**
 *
 * @author yomi-pc
 */
public class VoterNotificationService {
    @Autowired
    private Configuration freemarkerConfiguration;
    String msgstr = "";
    String authenabled ="false";
    MailvtBean MB;
    @Autowired
    private TaskExecutor taskExecutor;
    public VoterNotificationService() {
       MB = new MailvtBean();
       MB = getMailConfig();
    }

    public void createflowemailnotice(String currentrole,String mailsubject,String mailtemplate, int branchid,int companyid,Map model) {
        String emailaddrss = "";
        if (emailaddrss.isEmpty()==false) {
            preparemailandsend(emailaddrss,mailsubject,mailtemplate, model);
        }
    }
    
    
    public boolean preparemailandsend(String emailaddresses,String mailsubject,String mailtemplate,Map model){
        boolean mailSent = false;
        //send email notifying the uploader
        //System.out.println("inside voting email sende2..." + emailaddresses);
        try {
            MB.setToemail(emailaddresses);
            MB.setSubject(mailsubject);
            MB.setAttachments(null);
            MB.setMailBody("");

            String mailBody = "";
            String template = mailtemplate;
            //System.out.println("inside voting notification reminder 3...");
           try {
                mailBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(template, "UTF-8"), model);
            } catch (TemplateNotFoundException tpex) {
                System.out.println("Error - missing email template :" + tpex.getMessage());
                tpex.printStackTrace();
            } catch (MalformedTemplateNameException tpex) {
                System.out.println("Error - email template name :" + tpex.getMessage());
                tpex.printStackTrace();
            } catch (TemplateException tpex) {
                System.out.println("Error - email template :" + tpex.getMessage());
                tpex.printStackTrace();
            } catch (IOException tpex) {
                System.out.println("Error - email template IO :" + tpex.getMessage());
                tpex.printStackTrace();
            }
            MB.setMailBody(mailBody);
            //System.out.println("inside voting notification reminder 4...");
            this.sendMailasync(MB);
            mailSent = true;
            return mailSent;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return mailSent;
        }
    }
    
    
    
    public MailvtBean getMailConfig(){
    	String host ="";
    	String port ="";
    	String sslenabled ="";
    	String user ="";
    	String pass ="";
    	String from ="";
    	String sslortls="";
	
    	MailvtBean MBg=new MailvtBean();
    	
        try{
            javax.naming.Context ctx = new javax.naming.InitialContext();
            host = (String) ctx.lookup("java:comp/env/mail.host");
            port = (String) ctx.lookup("java:comp/env/mail.port");
            sslenabled = (String) ctx.lookup("java:comp/env/mail.ssl.enabled");
            user = (String) ctx.lookup("java:comp/env/mail.user");
            pass = (String) ctx.lookup("java:comp/env/mail.pass");
            from = (String) ctx.lookup("java:comp/env/mail.sender");
            sslortls= (String) ctx.lookup("java:comp/env/mail.ssl.sslortls");
	    authenabled = (String) ctx.lookup("java:comp/env/mail.auth.enabled");
            
            MBg.setFrom(from);
            MBg.setMailsmtphost(host);
            MBg.setMailsmtpport(port);
            MBg.setUserid(user);
            MBg.setPassword(pass);
            MBg.setSslortls(sslortls);
            
        } catch (Exception nx) {
        	MBg=null;
            nx.printStackTrace();
        }
        return MBg;
    }
    
    
    public void sendMail(MailvtBean MBg){
		 String userid = MBg.getUserid();
		 String mailsmtphost = MBg.getMailsmtphost();
		 String mailsmtpport = MBg.getMailsmtpport();
		 String sslortls = MBg.getSslortls();
		 String toemail = MBg.getToemail();
		 final String password =MBg.getPassword();
		 final String from = MBg.getFrom();
		 String subject=MBg.getSubject();
		 String mailBody=MBg.getMailBody();
		 String attachments[]=MBg.getAttachments();		 
		 boolean isloaded=attachments==null?false:true;
             //System.out.println("inside voting notification reminder 5...");
	     Properties props = new Properties();

	         props.put("mail.smtp.host", mailsmtphost);
		 props.put("mail.smtp.auth", authenabled);
		 props.put("mail.smtp.port", mailsmtpport);
	         if(sslortls.equalsIgnoreCase("SSL")) {
                    props.put("mail.smtp.socketFactory.port", mailsmtpport);
		    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		 }
		 else if(sslortls.equalsIgnoreCase("TLS")) {
                    props.put("mail.smtp.starttls.enable", "true");
		 }
		 else{
                    props.put("mail.smtp.starttls.enable", "false");
         }
			
		 Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, password);
				}
		  });

	      try{
		         //Create a default MimeMessage object.
		         MimeMessage message = new MimeMessage(session);
	
		         // Set From: header field of the header.
		         message.setFrom(new InternetAddress(from));
		         Address[] toUser = InternetAddress.parse(toemail);
		         message.setRecipients(Message.RecipientType.BCC,toUser);
		       
		         message.setSubject(subject);
		         
		         // Create the message part 
		         //BodyPart messageBodyPart = new MimeBodyPart();
		         
		         // creates message part
				 MimeBodyPart messageBodyPart = new MimeBodyPart();
				 messageBodyPart.setContent(mailBody, "text/html");
		         
		         //Fill the message
		         //messageBodyPart.setText(mailBody);
		         
		         //Create a multipar message
		         Multipart multipart = new MimeMultipart();
	
		         //Set text message part
		         multipart.addBodyPart(messageBodyPart);
	
		         //Part two is attachment
		         if(isloaded == true) {          
			          for(String item: attachments){
			        	  messageBodyPart = new MimeBodyPart();
				          DataSource source = new FileDataSource(item);
				          messageBodyPart.setDataHandler(new DataHandler(source));
				          messageBodyPart.setFileName(new File(item).getName());
				          multipart.addBodyPart(messageBodyPart);
				   }
		         } 	         
		         
		         //Send the complete message parts
		         message.setContent(multipart );          
		         //message.setContent(mailBody, "text/html");

		         // Send message
		         Transport.send(message);
		         //System.out.println("Sent message successfully....");
	       }catch (MessagingException mex) {
	            mex.printStackTrace();
	       } 
	}  
    
    
    public class sendMailTask implements Runnable {
        private MailvtBean tMB;
        public sendMailTask(MailvtBean tMB) {
            this.tMB = tMB;
        }
        public void run() {
            sendMail(tMB);
        }
    }
    
    public void sendMailasync(MailvtBean ttMB) {
        taskExecutor.execute(new sendMailTask(ttMB));
    }
    
    
    // for the task executor 
    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    /**
     * @return the taskExec
     */
    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }
}
