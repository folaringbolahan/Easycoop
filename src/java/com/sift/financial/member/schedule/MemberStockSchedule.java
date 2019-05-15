/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.schedule;

import com.sift.financial.ReminderInterface;
import com.sift.financial.member.BatchMember;
import com.sift.financial.member.BatchStock;
import com.sift.financial.member.BatchUploadFile;
import com.sift.financial.member.Event;
import com.sift.financial.member.Status;
import com.sift.financial.member.StatusFlow;
import com.sift.financial.services.MailNotificationImpl;
import com.sift.financial.utility.FileUtil;
import freemarker.template.Configuration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author baydel200
 */
public class MemberStockSchedule extends BatchScheduleInterface
{
    
   private static final Log log = LogFactory.getLog(MemberStockSchedule.class);
    
   @Autowired
   @Value("${MEMBER_FILE_COL}")
   private String memberFileCol;
    
   @Autowired
   @Value("${KEYCOLUMN}")
   private String keyColumn; 
   
   @Autowired
   private BatchStockValidator batchStockValidator;
   
   @Autowired
   @Value("${PROC-STKBATCH}")
   private String eventShort;
   
   @Autowired
   @Value("${initStckBatchStatus}")
   private String initStckBatchStatus;
    
   @Autowired
   private MailNotificationImpl emailDispatcher;

    @Override
    public Object getFiles(String dataType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List buildObjects(Object fileInfo, String path) {
        List<String[]> readRecords = null;
        
        if(fileInfo instanceof BatchUploadFile)
        {
           BatchUploadFile fileInfoObj = (BatchUploadFile)fileInfo;
           
           FileUtil fileUtil = new FileUtil();
            fileUtil.setGenericConfigDAO(getGenericConfigDAO());
            fileUtil.setMemberFileCol(memberFileCol);
            fileUtil.setKeyColumn(keyColumn);
            fileUtil.setUploadPath(path);
           
            try 
            {
                readRecords= fileUtil.readFile(fileInfoObj);
                
            } catch (Exception ex) {
                Logger.getLogger(MemberSchedule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
        
        
        
        }
    return readRecords;
    }

    @Override
    public List processObjects(List dataList, Object fileInfo) {

        
         BatchUploadFile fileInfoObj = (BatchUploadFile)fileInfo;
         
        //do validation
        List objList = (List<String[]>)dataList;
        
        List finalObjects = new ArrayList<BatchStock>();
        
        //build status of record using the eventshort and statusflow
        Event curEvent = (Event)getEventDAO().findByEventShort(eventShort).get(0);

        Status initStatus = getStatusDAO().getStatusFromShort(initStckBatchStatus) ;

        StatusFlow statusFlow = getStatusFlowDAO().getFlowFromEventStatus(curEvent, initStatus);
        Status successStatus = getStatusDAO().findById(statusFlow.getStatusSuccessId());
        Status failStatus = getStatusDAO().findById(statusFlow.getStatusFailId());
        
        
         // generate stock file if company does stock
        List<Map<String,Object>> stocksList = getGenericConfigDAO().getStockColumns(fileInfoObj.getCompanyId().toString());
        
        for(Object record: dataList )
        {
           String[] recordArray = (String[])record;
           log.info("recordArray size ::" + recordArray.length);
           
           for(int r=0;r < recordArray.length; r++)
           {
                log.info("recordArray @ ::" + r + " equals:::: " + recordArray[r]);
           }
           
            Date today = new Date();
            batchStockValidator.setBranchId(fileInfoObj.getBranchId());
            batchStockValidator.setCompanyId(fileInfoObj.getCompanyId());
            com.sift.financial.member.Company comp = getCompanyDAO().findById(fileInfoObj.getCompanyId());
            batchStockValidator.setCountryId(comp.getCountries().getId().intValue());
            
            batchStockValidator.setGenericConfigDAO(getGenericConfigDAO());
            batchStockValidator.setMemberHoldingsDAO(getMemberHoldingsDAO());
            batchStockValidator.setMemberHoldingsMovementDAO(getMemberHoldingsMovementDAO());
            
          List<BatchStock> curBatches =  batchStockValidator.doValidation(recordArray, stocksList);
          
           log.info("curBatches size ::" + curBatches.size());
          
            for(BatchStock curBatch : curBatches)
            {
              curBatch.setCreatedBy(fileInfoObj.getCreatedBy());
              curBatch.setCreatedDate(new java.sql.Timestamp(today.getTime()));
              curBatch.setBatchUploadRefId(fileInfoObj.getBatchUploadReference().getBatchUploadReferenceId());
              curBatch.setBatchUploadFile(fileInfoObj);
              curBatch.setCompanyId(fileInfoObj.getCompanyId());
              curBatch.setRunCount(1);
              curBatch.setBranchId(fileInfoObj.getBranchId());
              
              

              if(curBatch.hasErrors())
              {
                   log.info("Updating  fail status stock  ::" + failStatus.getStatusShort());
                 curBatch.setStatus(failStatus);
              }
              else
              {
                  log.info("Updating  pass status for Stock ::" + successStatus.getStatusShort());
               curBatch.setStatus(successStatus);
              }    

            finalObjects.add(curBatch);
             }
        }
       log.info("finalObjects size ::" + finalObjects.size());
       
       return finalObjects;   
        }

    @Override
    public boolean writeObjects(List dataList, Object fileInfo) {
    
       log.info("entry dataList size ::" + dataList.size());
           
        boolean returnVal = false;
        //files
          BatchUploadFile fileInfoObj = (BatchUploadFile)fileInfo;
         
          Event curEvent = (Event)getEventDAO().findByEventShort(eventShort).get(0);

          //Status initStatus = getStatusDAO().getStatusFromShort(initMemBatchStatus) ;
          log.info("curEvent  ::: " +  curEvent.getEventShort());
          
          log.info(" fileInfoObj.getStatus() ::: " + fileInfoObj.getStatus().getStatusShort());
          
           StatusFlow statusFlow = getStatusFlowDAO().getFlowFromEventStatus(curEvent, fileInfoObj.getStatus());
           
           if(statusFlow==null)
           {
                log.info(" statusFlow ::: Is null here oooooo!!!!" );
           }
           
            Status successStatus = getStatusDAO().findById(statusFlow.getStatusSuccessId());
            
            Status failStatus = getStatusDAO().findById(statusFlow.getStatusSuccessId());
            
            fileInfoObj.setStatus(successStatus);
            Session sess = null;
            int i =0;
            try
            {
               sess =getBatchUploadFileDAO().getSessionFactory().openSession();
               sess.beginTransaction();
               
              // getBatchUploadFileDAO().save(fileInfoObj, sess);
               getBatchUploadFileDAO().merge(fileInfoObj, sess);
              
               for(Object record : dataList)
               {
                 log.info("attending to record at position " + i);
                  BatchStock batchMem = (BatchStock)record;
                 if(batchMem==null)
                 {
                   log.info("record at position " + i + " is Nullll!!!!!");
                 }
                
                 batchMem.setBatchUploadFile(fileInfoObj);
                 batchMem.setCompanyId(fileInfoObj.getCompanyId());
                 batchMem.setBranchId(fileInfoObj.getBranchId());
                 if(sess==null)
                 {
                   log.info("sessION at position " + i + " is Nullll!!!!!");
                 }
                 
                 getBatchStockDAO().save(batchMem, sess);
                 i=i+1;
               }
              sess.getTransaction().commit();
              returnVal = true;
            }
            catch(Exception ex)
            {
                sess.getTransaction().rollback();
                log.info("........"  + ex.getMessage());
                log.info("attending and failing on record at position " + i);
                ex.printStackTrace();
                returnVal = false;
            }
            finally
            {
                sess.close();
            }
           
    return returnVal;
    
    }

    @Override
    public boolean sendNotification(Object fileBundle, Map<String, String> processInfo, boolean processState) {
        
     log.info("sending notification........");
       
      BatchUploadFile fileObject = (BatchUploadFile)fileBundle;
      String tempMesg ="Kindly logon to portal to check uploaded records state";
        
        Map<String, Object> mailInfo = new HashMap<String, Object>();
        
        if(processInfo !=null)
        {
            if(processInfo.get(ReminderInterface.PROC_MSG)!=null)
            {
                mailInfo.put(ReminderInterface.PROC_MSG, processInfo.get(ReminderInterface.PROC_MSG));
            }
            else
            {
                 mailInfo.put(ReminderInterface.PROC_MSG, tempMesg);
            }
        }
        else
        { 
            mailInfo.put(ReminderInterface.PROC_MSG, tempMesg);
        }
        
        mailInfo.put(ReminderInterface.PROC_REF, fileObject.getBatchUploadReference());
        mailInfo.put(ReminderInterface.PROC_USER, fileObject.getCreatedBy());
        mailInfo.put(ReminderInterface.PROC_FILE, fileObject.getBatchUploadType().getUploadTypeName());
        
        //Add Recipients 
        Map<String, Object> recipients = new HashMap<String, Object>();
        recipients.put(ReminderInterface.CREATOR, fileObject.getCreatedBy());
        //recipients.put(ReminderInterface.NEXTLEVEL, super.getNextValidUserEmail(fileObject.getBranchId(), fileObject.getCompanyId(), keyColumn));
        recipients.put(ReminderInterface.COMPANYALL, super.getAllValidUserEmail(fileObject.getBranchId(), fileObject.getCompanyId()));
        
        emailDispatcher.sendMail(recipients, mailInfo, eventShort, String.valueOf(processState));
          	   
        return emailDispatcher.isMailOk();
    }
    
}
