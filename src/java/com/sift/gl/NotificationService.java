/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl;

import com.sift.gl.model.Activitylog;
import com.sift.gl.model.MailglBean;
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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

/**
 *
 * @author yomi-pc
 */
////@Component ("notificationService")
////@Scope(value="session",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class NotificationService {
    @Autowired
    private Configuration freemarkerConfiguration;
    String msgstr = "";
    String authenabled ="false";
    MailglBean MB;
    public NotificationService() {
        MB = new MailglBean();
       MB = getMailConfig();
    }

  //  public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
  //    this.freemarkerConfiguration = freemarkerConfiguration;
  //   }
    public void createflowemailnotice(String currentrole,String mailsubject,String mailtemplate, int branchid,int companyid,Map model) {
        String emailaddrss = "";
        String nextrole = retrievenextrole(currentrole);
        if ((nextrole!=null) && (nextrole.isEmpty()==false)) {
            emailaddrss = retrieveusersforrole(nextrole, branchid,companyid);
        }
        if (emailaddrss.isEmpty()==false) {
            preparemailandsend(emailaddrss,mailsubject,mailtemplate, model);
        }
        
    }
    
    public String retrievenextrole(String currrole){
        String nextrole = "";
        GendataService dbobj = new GendataService();
        ResultSet rs=null; 
        dbobj.inimkcon();
        String mySQLString = "select * from glemailworkflow where currentmodule = '" + currrole + "'";
        try {
            rs = dbobj.retrieveRecset(mySQLString);
            if (rs.first() == true) {
                nextrole = rs.getString("nextmodule");
            }
        } catch (SQLException ex) {
            System.out.println("Record not retrieved : Error - " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            dbobj.closecon();
        }
       
       return nextrole;
    } 
    
    public String retrieveusersforrole(String role, int branchid,int companyid){
       String nextusers = "";
       String nextrole = "";
        GendataService dbobj = new GendataService();
        ResultSet rs=null; 
        dbobj.inimkcon();
        String mySQLString = "select a.email from users a inner join usergrpmdl b on a.branch = b.branchid and a.companyid = b.companyid and a.accesslevel = b.usergroup where b.menu = '" + role + "' and b.branchid = " + branchid + " and b.companyid = " + companyid + " and a.enabled = 1";
        //System.out.println("mysql" + mySQLString);
        try {
            rs = dbobj.retrieveRecset(mySQLString);
            while (rs.next())
            {   
                try {
                 if (rs.getString("email").isEmpty()==false) {
                 nextusers = nextusers + rs.getString("email") + ",";
                 }
                } 
                catch (NullPointerException nx){
                    //do noting
                } 
            }
        } catch (SQLException ex) {
            System.out.println("Record not retrieved : Error - " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            dbobj.closecon();
        }
       if (nextusers.isEmpty()==false) {
           nextusers = nextusers.trim();
           int len = nextusers.length();
                 nextusers = nextusers.substring(0, len-1) ;
       }
       return nextusers;
    } 
    
    public void preparemailandsend(String emailaddresses,String mailsubject,String mailtemplate,Map model){
        boolean mailSent = false;

        //send email notifying the uploader
        try {
            //mail bean setup
            
            MB.setToemail(emailaddresses);
            MB.setSubject(mailsubject);

            MB.setAttachments(null);
            MB.setMailBody("");

            

            String mailBody = "";
            String template = mailtemplate;//"glacctxnsuptoapprvmail.ftl";
            //Map model = new HashMap();
            //model.put("getUploadedBy", item.getUploadedBy());
            //model.put("getUserUploadcount", item.getUserUploadcount().toString());
            //model.put("getUserUploadSum", item.getUserUploadSum().toString());
           // System.out.println("template " + template);
           // System.out.println("model " + model.get("referenceid"));
           // System.out.println("freemarkerConfiguration " + freemarkerConfiguration.toString());
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

            //send email
            this.sendMail(MB);
            mailSent = true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public MailglBean getMailConfig(){
    	String host ="";
    	String port ="";
    	String sslenabled ="";
    	String user ="";
    	String pass ="";
    	String from ="";
    	String sslortls="";
		
    	
    	MailglBean MBg=new MailglBean();
    	
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
    
    
    public void sendMail(MailglBean MBg){
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
}
