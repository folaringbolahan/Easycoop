/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.benefits.schedule;

import com.sift.financial.ReminderInterface;
import com.sift.financial.member.ActivityInterface;
import com.sift.financial.member.Dividend;
import com.sift.financial.member.Event;
import com.sift.financial.member.Status;
import com.sift.financial.member.StatusFlow;
import com.sift.financial.member.TransactionInterface;
import com.sift.financial.member.ws.client.Entrys;
import static com.sift.financial.utility.customutil.*;

import com.sift.financial.services.MailNotificationImpl;
import com.sift.financial.utility.SiftFinancialUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author baydel200
 */

public class PayDividendScheduleImpl extends PayDividendInterface {
    
    
   private static final Log log = LogFactory.getLog(PayDividendScheduleImpl.class);
    
    @Autowired
    private MailNotificationImpl emailDispatcher;
    
    @Autowired
    @Value("${APPR-DIV}")
    private String approvedDivStatus;
    
    @Autowired
    @Value("${APPLY-DIV}")
    private String divPayEvent;
    
    @Autowired
    private SiftFinancialUtil siftUtil;
    
    @Autowired
    @Value("${DIVIDENDPYT}")
    private String tranCode; 

    @Autowired
    @Value("${divTranRef}")
    private String tranRef;
    
    
   
    

    @Override
    public List getQualifiedDiv(String date) {
        
        Date today = truncateDate(new java.util.Date());
        
        return  getQualifiedDivDefault(today, approvedDivStatus);

    }

    @Override
    public Map<String, Object> makePayment(Object obj, Object fileInfo, boolean passOk) {

        Event event = (Event)getEventDAO().findByEventShort(divPayEvent).get(0);
         
        Map<String, Object> returnVal = new HashMap<String, Object>();
        setPayOk(false);
        Dividend objDiv = (Dividend)obj;
        Map<String, Object> tranObj = null;
        Status nextStat = null;
        StatusFlow statusFlow = null;
        Session sess = null;
        
        Date today = new java.util.Date();
        
        boolean  postedFlg = false;
   
       //check if postEntries is Yes
        if(objDiv.getPostEntries().equals("Y"))
        {
            //make payment
            Map<String,Object> user = new HashMap <String,Object>();
                  
            //String query ="select a.name as name ,a.short_name  as short_name,a.code as code,a.Country_id as country_id,b.id as id ,b.currentyear as current_year,b.currentperiod as current_period,to_char(b.postdate,'YYYY-MM-DD') as post_date,b.currentsystemdate as current_sysdate,b.entrydate as entry_date,c.currency_code as currency_code as basecurrency as base_currency,c.timez as timez from company a , branch b,countries c where a.id = b.company_id and b.country_id = c.id and a.id  =" + batchUploadFile.getCompanyId()  + " and b.id = " + batchUploadFile.getBranchId() ;
            Map<String, String> Companydetails = getGenericConfigDAO().getCompanyInfo(objDiv.getCompanyId(), objDiv.getBranchId());
            user.put("Companydetails", Companydetails);
                 
            log.info("Here inside writeObjects....................fetched company details");
                 
            //String userQuery ="select userid as userid, username as username, branch as branch_id, companyid as company_id, accesslevel as access_level  FROM users where userid = '" + batchUploadFile.getCreatedBy() + "'";
            Map<String, String> Curruser = getGenericConfigDAO().getUserInfo(objDiv.getApprovedBy());
            user.put("Curruser", Curruser);
            
            tranObj = siftUtil.buildTransactionObjects(objDiv, event, tranCode, user, null, tranRef);
            
             if(tranObj!=null)
                {
                    Entrys theEntrys = null; 
                        try
                        {
                         theEntrys=siftUtil.getTransaction((Map<String, Object>)tranObj.get(TransactionInterface.HEADERINFO), (List<Map<String, Object>>)tranObj.get(TransactionInterface.BODYINFO), user, tranRef);

                         Object restResult =   doRestService(theEntrys, ActivityInterface.SERVTYPEPOSTING);

                            if(restResult instanceof Entrys)
                            {
                              theEntrys = (Entrys)restResult;
                            }
                            else if(restResult instanceof String)
                            {
                             System.out.println("Posting result here :"   + restResult);
                            }

                            postedFlg= true;
                            System.out.println("Posting Succesful");

                        } catch (Throwable e) 
                        {
                           // TODO Auto-generated catch block
                            postedFlg = false;
                            e.printStackTrace();
                            setMsg("Posting Failed for Pay Dividend entries");
                        }

                }
        }
        else
        {

            postedFlg= true;
        }
        
      //update dividend and didvidend schedule
        
        if(postedFlg)
        {
            try
            {
                sess = getDividendDAO().getSessionFactory().openSession();
                
                sess.beginTransaction();
                
                statusFlow = getStatusFlowDAO().getFlowFromEventStatus(event, objDiv.getStatus());
                nextStat = getStatusDAO().findById(statusFlow.getStatusSuccessId());
                objDiv.setStatus(nextStat);
                
                getDividendScheduleDAO().updateByDividendId(objDiv, sess, today, approvedDivStatus);
                
                getDividendDAO().merge(objDiv, sess);
                
                 sess.getTransaction().commit();
                 setPayOk(true);
            }
            catch(Exception ex)
            {
             setMsg("Dividend Update failed though posting was successful");
            }
            finally
            {
            sess.close();
            
                if(getMsg()!=null)
                {
                  returnVal.put(ReminderInterface.PROC_MSG, getMsg());
                }
            }
        }
        else
        {
            
         returnVal.put(ReminderInterface.PROC_MSG, getMsg());
        }
       
    return returnVal;
    }

    @Override
    public boolean sendNotification(Map<String, Object> processInfo, boolean mailToSend, Object obj) {
    
        Dividend fileObject = (Dividend)obj;
        
        Map<String, Object> mailInfo = new HashMap<String, Object>();
        
        if(processInfo !=null)
        {
            if(processInfo.get(ReminderInterface.PROC_MSG)!=null)
            {
                mailInfo.put(ReminderInterface.PROC_MSG, processInfo.get(ReminderInterface.PROC_MSG));
            }
        }
        
        mailInfo.put(ReminderInterface.PROC_REF, fileObject.getDivNumber());
        mailInfo.put(ReminderInterface.PROC_USER, fileObject.getApprovedBy());
        
        //Add Recipients 
        Map<String, Object> recipients = new HashMap<String, Object>();
        recipients.put(ReminderInterface.CREATOR, fileObject.getApprovedBy());
        //recipients.put(ReminderInterface.NEXTLEVEL, super.getNextValidUserEmail(fileObject.getBranchId(), fileObject.getCompanyId(), keyColumn));
        recipients.put(ReminderInterface.COMPANYALL, super.getAllValidUserEmail(fileObject.getBranchId(), fileObject.getCompanyId()));
        
        emailDispatcher.sendMail(recipients, mailInfo, super.getEvenShort(), String.valueOf(mailToSend));
          	   
        return emailDispatcher.isMailOk();    }
    
}
