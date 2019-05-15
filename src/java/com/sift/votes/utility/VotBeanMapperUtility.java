/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.utility;



import com.sift.admin.interfaces.Definitions;
import com.sift.votes.bean.VotCompanyBean;
import com.sift.votes.model.VotCompany;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
import javax.naming.NamingException;

/**
 *
 * @author Nelson Akpos
 */
public class VotBeanMapperUtility {
     private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(VotBeanMapperUtility.class);
     private static String authenabled="false";
    //company field mapping
    public List<VotCompanyBean> prepareListofVotCompanyBean(List<VotCompany> votCompanys){
         List<VotCompanyBean> beans = null;
        
         if(votCompanys != null && !votCompanys.isEmpty()){
         	beans = new ArrayList<VotCompanyBean>();
         	VotCompanyBean bean = null;
 		    
         	for(VotCompany company : votCompanys){
 			    bean = new VotCompanyBean();
 			    bean.setCompanyid(company.getCompanyid());
 			    bean.setCompanyname(company.getCompanyname());
                            bean.setActive(company.getActive());
                            bean.setCompanyrefid(company.getCompanyrefid());
                            
 			    beans.add(bean);
 		   }
                System.out.println("size of bean "+ beans.size());
 	    }
          
         return beans;
  }
      public String getEasyCoopAgmURI() {
        String uri = "";

        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            uri = (String) ctx.lookup("java:comp/env/easycoopagmbaseurl");
            //BASE_URI = uri;            
        } catch (NamingException nx) {
            System.out.println("Error number exception" + nx.getMessage().toString());
        }

        return uri;
    }

      public static VotMailBean getMailConfig() {
        String host = "";
        String port = "";
        String sslenabled = "";
        String user = "";
        String pass = "";
        String from = "";
        String sslortls = "";
        //String authenabled = "false";

        VotMailBean MB = new VotMailBean();

        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();

            host = (String) ctx.lookup("java:comp/env/mail.host");
            port = (String) ctx.lookup("java:comp/env/mail.port");
            sslenabled = (String) ctx.lookup("java:comp/env/mail.ssl.enabled");
            user = (String) ctx.lookup("java:comp/env/mail.user");
            pass = (String) ctx.lookup("java:comp/env/mail.pass");
            from = (String) ctx.lookup("java:comp/env/mail.sender");
            sslortls = (String) ctx.lookup("java:comp/env/mail.ssl.sslortls");
            authenabled = (String) ctx.lookup("java:comp/env/mail.auth.enabled");

            MB.setFrom(from);
            MB.setMailsmtphost(host);
            MB.setMailsmtpport(port);
            MB.setUserid(user);
            MB.setPassword(pass);
            MB.setSslortls(sslortls);

        } catch (Exception nx) {
            MB = null;
            nx.printStackTrace();
        }

        return MB;
    }
        public static void sendMail(VotMailBean MB) {
        String userid = MB.getUserid();
        String mailsmtphost = MB.getMailsmtphost();
        String mailsmtpport = MB.getMailsmtpport();
        String sslortls = MB.getSslortls();
        String toemail = MB.getToemail();
        final String password = MB.getPassword();
        final String from = MB.getFrom();
        String subject = MB.getSubject();
        String mailBody = MB.getMailBody();
        String attachments[] = MB.getAttachments();
        boolean isloaded = attachments == null ? false : true;
       // String authenabled = "false";

        Properties props = new Properties();

        props.put("mail.smtp.host", mailsmtphost);
        props.put("mail.smtp.auth", authenabled);
        props.put("mail.smtp.port", mailsmtpport);

        if (sslortls.equalsIgnoreCase("SSL")) {
            props.put("mail.smtp.socketFactory.port", mailsmtpport);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        } else if (sslortls.equalsIgnoreCase("TLS")) {
            props.put("mail.smtp.starttls.enable", "true");
        } else {
            props.put("mail.smtp.starttls.enable", "false");
        }

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            //Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
           // message.setFrom(new InternetAddress(from));
           message.setFrom(new InternetAddress(Definitions.DEFAULT_MAIL_SENDER));
            Address[] toUser = InternetAddress.parse(toemail);
            message.setRecipients(Message.RecipientType.TO, toUser);

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
            if (isloaded == true) {
                for (String item : attachments) {
                    messageBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(item);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(new File(item).getName());
                    multipart.addBodyPart(messageBodyPart);

                }
            }

            //Send the complete message parts
            message.setContent(multipart);
            //message.setContent(mailBody, "text/html");

            // Send message
            Transport.send(message);
            //System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
