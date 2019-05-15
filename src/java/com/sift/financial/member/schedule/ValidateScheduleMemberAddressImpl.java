/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.schedule;

import com.sift.financial.ReminderInterface;
import com.sift.financial.member.BatchAddressEntries;
import com.sift.financial.member.BatchStock;
import com.sift.financial.member.BatchUploadFile;
import com.sift.financial.member.Event;
import com.sift.financial.member.Status;
import com.sift.financial.member.StatusFlow;
import com.sift.financial.services.MailNotificationImpl;
import com.sift.financial.utility.SiftFinancialUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author baydel200
 */
public class ValidateScheduleMemberAddressImpl extends ValidateScheduleService implements ValidateScheduleInterface {
    
    private static final Log log = LogFactory.getLog(ValidateScheduleMemberStockImpl.class);
   
    @Autowired
    private SiftFinancialUtil siftUtil;
     
    @Autowired
    @Value("${addrProcessed}")
    private String addrProcessed;
    
    @Autowired
    @Value("${VALID-ADDBATCH}")
    private String valdationEvent;
    
    @Autowired
   private MailNotificationImpl emailDispatcher;

    @Override
    public List getRecords(BatchUploadFile object, boolean passOk) {
        
        log.info("Here inside get Records....................");
      return  getBatchAddressEntriesDAO().getBatchAddressByStatus(addrProcessed, object.getBatchUploadFileId(), object.getCompanyId(), object.getBranchId());
    }

    @Override
    public List buildRecords(List theRecords, Object fileInfo, boolean passOk) {
     
         log.info("Here inside buildRecords....................");
        
         BatchUploadFile batchUploadFile = (BatchUploadFile)fileInfo;
         List returnVal = new ArrayList<BatchStock>();
         Status status = null;
         int n= 0;
         log.info("Starting loop over records inside build Records ....................");
         
        for(Object obj: theRecords)
        {
         
         BatchAddressEntries batchStk = (BatchAddressEntries)obj;
         batchStk.setModifiedBy(batchUploadFile.getModifiedBy());
         batchStk.setModifiedDate(batchUploadFile.getModifiedDate());
         
         if(passOk)
         {
                    if(status==null)
                    {
                        Event curEvent = (Event)getEventDAO().findByEventShort(valdationEvent).get(0);
                        Status initStatus = batchStk.getStatus() ;
                        StatusFlow statusFlow = getStatusFlowDAO().getFlowFromEventStatus(curEvent, initStatus);

                        status = getStatusDAO().findById(statusFlow.getStatusSuccessId());
                    }
                    
            batchStk.setStatus(status);
                                    
          log.info("built Records...................." +  n);
          n= n+1;
          }
            
           returnVal.add(batchStk);
    }
        
        return returnVal;
    }

    @Override
    public List processObjects(List dataList, Object fileInfo, boolean passOk) {
        
        log.info("Here inside processObjects....................nothing much happening");
        return dataList;    }

    @Override
    public boolean writeObjects(List dataList, Object fileInfo, boolean passOk) {
    
        boolean returnVal = false;
        Session sess = null;
        Transaction tx = null;
        log.info("Here inside writeObjects....................alot about to happen");
        
        if(dataList!=null && fileInfo!=null)
        {
       log.info("Here inside writeObjects....................first level If");
       
             if(fileInfo instanceof BatchUploadFile)
             {
                 
                 log.info("Here inside writeObjects....................second level If");
                  
                 sess = getBatchUploadFileDAO().getSessionFactory().openSession();
                 
                 BatchUploadFile batchUploadFile = (BatchUploadFile)fileInfo;
                 Event curEvent = (Event)getEventDAO().findByEventShort(valdationEvent).get(0);
                 Status initStatus = batchUploadFile.getStatus() ;
                 StatusFlow statusFlow = getStatusFlowDAO().getFlowFromEventStatus(curEvent, initStatus);
                 Status successStatus = getStatusDAO().findById(statusFlow.getStatusSuccessId());
                 Status failStatus = getStatusDAO().findById(statusFlow.getStatusFailId());
               
                 if(passOk)
                 {
                        log.info("Here inside writeObjects....................if record passed");
                        batchUploadFile.setStatus(successStatus);
                     try
                        {
                         //now 
                        tx = sess.beginTransaction();
                        //merge batchfile
                   
                         // batchUploadFile
                            // mass merge Batch Member
                        int n = 0;
                         log.info("Here inside writeObjects....................about looping ");
                         
                            for(Object memBatch : dataList)
                            {
                                    BatchAddressEntries memBatchObj = (BatchAddressEntries)memBatch;

                                   getBatchAddressEntriesDAO().merge(memBatchObj, sess);
                            }
                        
                            getBatchUploadFileDAO().merge(batchUploadFile, sess);
                        
                            tx.commit();
                            returnVal= true;

                        }
                        catch(Exception ex)
                        {
                            tx.rollback();
                            returnVal= false;
                        }
                    finally
                    {sess.close();}

                 }
                 else
                 {
                     batchUploadFile.setStatus(failStatus);
                     try
                        {
                         //now 
                        tx = sess.beginTransaction();
                        //merge batchfile
                         // batchUploadFile
                            // mass merge Batch Stock
                            for(Object memBatch : dataList)
                            {
                                    BatchAddressEntries memBatchObj = (BatchAddressEntries)memBatch;
                                    memBatchObj.setStatus(failStatus);
                                    getBatchAddressEntriesDAO().merge(memBatchObj, sess);
                            }
                        
                            getBatchUploadFileDAO().merge(batchUploadFile, sess);
                        
                            tx.commit();
                            returnVal= true;

                        }
                        catch(Exception ex)
                        {
                            tx.rollback();
                            returnVal= false;
                        }
                    finally
                    {sess.close();}
                 }
             }
             else
             {
                returnVal = false;
             }
        }
        else
        {
            returnVal = false;
        }
           
    return returnVal;       
    }

    @Override
    public boolean sendNotification(Object fileBundle, Map<String, Object> processInfo, boolean mailToSend) 
    {
        log.info("Sending Completion mail");
        
        BatchUploadFile fileObject = (BatchUploadFile)fileBundle;
        
        Map<String, Object> mailInfo = new HashMap<String, Object>();
        
        if(processInfo !=null)
        {
            if(processInfo.get(ReminderInterface.PROC_MSG)!=null)
            {
                mailInfo.put(ReminderInterface.PROC_MSG, processInfo.get(ReminderInterface.PROC_MSG));
            }
        }
        
        mailInfo.put(ReminderInterface.PROC_REF, fileObject.getBatchUploadReference());
        mailInfo.put(ReminderInterface.PROC_USER, fileObject.getModifiedBy());
        mailInfo.put(ReminderInterface.PROC_FILE, fileObject.getBatchUploadType().getUploadTypeName());
        
        //Add Recipients 
        Map<String, Object> recipients = new HashMap<String, Object>();
        recipients.put(ReminderInterface.CREATOR, fileObject.getModifiedBy());
        //recipients.put(ReminderInterface.NEXTLEVEL, super.getNextValidUserEmail(fileObject.getBranchId(), fileObject.getCompanyId(), keyColumn));
        recipients.put(ReminderInterface.COMPANYALL, super.getAllValidUserEmail(fileObject.getBranchId(), fileObject.getCompanyId()));
        
        emailDispatcher.sendMail(recipients, mailInfo, valdationEvent, String.valueOf(mailToSend));
          	   
        return emailDispatcher.isMailOk();
    
    }

    public SiftFinancialUtil getSiftUtil() {
        return siftUtil;
    }

    public void setSiftUtil(SiftFinancialUtil siftUtil) {
        this.siftUtil = siftUtil;
    }

    public String getValdationEvent() {
        return valdationEvent;
    }

    public void setValdationEvent(String valdationEvent) {
        this.valdationEvent = valdationEvent;
    }

    public String getAddrProcessed() {
        return addrProcessed;
    }

    public void setAddrProcessed(String addrProcessed) {
        this.addrProcessed = addrProcessed;
    }

}
