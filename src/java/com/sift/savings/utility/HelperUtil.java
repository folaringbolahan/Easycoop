/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.savings.utility;

import com.sift.admin.bean.UsergrpmdlBean;
import com.sift.admin.interfaces.Definitions;
import com.sift.admin.model.Usergrpmdl;
import com.sift.easycoopfin.models.DAOFactory;
import com.sift.easycoopfin.models.FileUpload;
import com.sift.easycoopfin.models.FileUploadCriteria;
import com.sift.easycoopfin.models.Member;
import com.sift.easycoopfin.models.MemberCriteria;
import com.sift.easycoopfin.models.Savings;
import com.sift.easycoopfin.models.SavingsError;
import com.sift.gl.AuditlogService;
import com.sift.gl.GendataService;
import com.sift.gl.model.Account;
import com.sift.gl.model.Activitylog;
import com.sift.gl.model.SMSBean;
import com.sift.gl.model.SmsLog;
import com.sift.gl.service.FileUploadItemsService;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.MailBean;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
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
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.criterion.Restrictions;
import org.orm.PersistentTransaction;

/**
 *
 * @author logzy
 */
public class HelperUtil {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(HelperUtil.class);
    EazyCoopUtility eazyCoopUTIL = new EazyCoopUtility();
    private String dpwd;
    private String dname;
    private static WebResource webResource;
    private static Client client;
    private static String BASE_URI = "http://localhost:7070/EasycoopfinTEST3/webserv";
    private static PersistentTransaction t = null;
    private static String authenabled = "false";
    
    public static Date getCurrentDateByTimezone(String timezone) {
        TimeZone timeZone = TimeZone.getTimeZone(timezone);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(timeZone);

        Calendar now = Calendar.getInstance(timeZone);

        java.util.Date currentDate;

        currentDate = now.getTime();
        return currentDate;
    }

    public static String getUserEmail(String userId) {
        PersistentSession session;
        String email = "";
        try {

            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            String sql = "select Email from users  where UserId='" + userId + "'";
            Query query = session.createSQLQuery(sql);

            if (query == null || query.list().isEmpty()) {
                System.out.println("User is null");

                email = "";
            } else {
                email = query.list().get(0).toString();
                System.out.println("User is exists: " + query.list().get(0).toString() + " User:" + userId);
            }



        } catch (PersistentException ex) {
            email = "";
            _logger.error("verify(String referenceNumber, String userId)", ex);
        }
        return email;
    }

    public  void sendMailByUserGroup(String menuCode, int branchId, int companyId, String userId, String subject, String messageBody) {
        PersistentSession session;
        ArrayList<UsergrpmdlBean> list = null;
        UsergrpmdlBean userGrpMdlBean = null;

        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            SQLQuery query = session.createSQLQuery("SELECT * FROM usergrpmdl u  where u.menu='" + menuCode + "' and u.branchid = " + branchId + " and u.companyid=" + companyId);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List data = query.list();

            for (Object object : data) {
                Map row = (Map) object;
                System.out.println("First Name: " + row.get("usergroup").toString());
                SQLQuery userQuery = session.createSQLQuery("SELECT * FROM users u where u.AccessLevel='" + row.get("usergroup").toString() + "' and u.branch=" + branchId + " and u.Companyid=" + companyId);
                userQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                List userData = userQuery.list();
                if (userData.isEmpty() == false) {
                    for (Object userObject : userData) {
                        Map rowObject = (Map) userObject;

                        MailBean MB = HelperUtil.createMailBean();
                        MB.setSubject(subject);
                        MB.setToemail(rowObject.get("Email").toString());
                        //MB.
                        //MB.setAttachments(null);
                        HelperUtil.sendMail(MB);
                    }
                }else{
                    System.out.println("Unable to send mail");
                }
            }

        } catch (PersistentException ex) {
            _logger.error("sendMailByUserGroup(String menuCode)", ex);
        } catch (Exception ex) {
            _logger.error("sendMailByUserGroup(String menuCode)", ex);
        }
    }

    public static MailBean createMailBean() {
        String host = "";
        String port = "";
        String sslenabled = "";
        String user = "";
        String pass = "";
        String from = "";
        String sslortls = "";

        MailBean MB = new MailBean();

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

        } catch (Exception ex) {
            MB = null;
            _logger.error("MailBean createMailBean()", ex);
        }

        return MB;
    }
    
    public static MailBean getMailConfig() {
        String host = "";
        String port = "";
        String sslenabled = "";
        String user = "";
        String pass = "";
        String from = "";
        String sslortls = "";
        //String authenabled = "false";

        MailBean MB = new MailBean();

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
    
    // new from Chris
    public static void sendMail(MailBean MB) {
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
        //String authenabled = "false";

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
            message.setFrom(new InternetAddress(from));
           // message.setFrom(new InternetAddress(Definitions.DEFAULT_MAIL_SENDER));
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

    
    public static void sendMailOLD(MailBean MB) {
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

        Properties props = new Properties();

        props.put("mail.smtp.host", mailsmtphost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", mailsmtpport);

        if (sslortls.equalsIgnoreCase("SSL")) {
            props.put("mail.smtp.socketFactory.port", mailsmtpport);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        } else {
            props.put("mail.smtp.starttls.enable", "true");
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
            message.setFrom(new InternetAddress(from));
            Address[] toUser = InternetAddress.parse(toemail);
            message.setRecipients(Message.RecipientType.BCC, toUser);

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

    public static void logEvent(int eventid, String eventactioncode, String ipaddr, String username, String timezone, String actionitem, String result, int branch, int company) {
        Activitylog ent;
        ent = new Activitylog();

        ent.setBranchid(branch);
        //ent.setEvent(1);
        //ent.setAction("GLGAC");
        ent.setEvent(eventid);
        ent.setAction(eventactioncode);
        ent.setCompanyid(company);
        ent.setUsername(username);
        ent.setTimezone(timezone);
        ent.setDescription("");
        ent.setIpaddress(ipaddr);
        ent.setActionItem(actionitem);
        ent.setActionResult(result);
        AuditlogService cliserv = new AuditlogService();
        String respo = cliserv.create(ent);
        System.out.println(respo);
        //theerrmess= cliserv.gettheerrmess();
        //System.out.println(theerrmess);
    }

    public boolean sendSMS(SMSBean obj) {
        boolean success = false;

        try {
            BASE_URI = getURI();
            com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
            client = Client.create(config);
            getRoleparameters();
            client.addFilter(new HTTPBasicAuthFilter(dname, dpwd));
            webResource = client.resource(BASE_URI).path("glwssms");

            ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class, obj);
            System.out.println("Server response : \n" + response.getStatus());

            if (response.getStatus() != 201) {
                //throw new RuntimeException("Failed : HTTP error code : "
                //        + response.getStatus() + ". Operation failed");
                success = false;
            } else {
                success = true;
            }

            String output = response.getEntity(String.class);
            System.out.println("Server response : \n");
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    public void getRoleparameters() {
        String name = "";
        String password = "";
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        String mySQLString;
        mySQLString = " select * FROM tblwebserv a where a.app = 'internal'";
        ResultSet agRecSet;

        try {
            agRecSet = dbobj.retrieveRecset(mySQLString);

            while (agRecSet.next()) {
                name = agRecSet.getString("user");
                password = agRecSet.getString("pwd");
            }

            dname = name;
            dpwd = password;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (dbobj != null) {
                dbobj.closecon();
            }
        }
    }

    public String getURI() {
        String uri = "";

        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            uri = (String) ctx.lookup("java:comp/env/webservicebaseurl");
            //BASE_URI = uri;            
        } catch (NamingException nx) {
            System.out.println("Error number exception" + nx.getMessage().toString());
        }

        return uri;
    }
   
}
