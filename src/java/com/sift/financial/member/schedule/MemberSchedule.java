/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.schedule;

import com.sift.admin.model.Company;
import com.sift.financial.ReminderInterface;
import com.sift.financial.member.BatchMember;
import com.sift.financial.member.BatchUploadFile;
import com.sift.financial.member.Event;
import com.sift.financial.member.Status;
import com.sift.financial.member.StatusFlow;
import com.sift.financial.services.MailNotificationImpl;
import com.sift.financial.utility.FileUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import java.util.HashMap;

/**
 *
 * @author baydel200
 */
public class MemberSchedule extends  BatchScheduleInterface  
{
   
   @Autowired
   @Value("${MEMBER_FILE_COL}")
   private String memberFileCol;
    
   @Autowired
   @Value("${KEYCOLUMN}")
   private String keyColumn; 
   
   @Autowired
   private BatchMemberValidator batchMemberValidator;
   
   @Autowired
   @Value("${procMemBatEvent}")
   private String eventShort;
   
   @Autowired
   @Value("${initMemBatchStatus}")
   private String initMemBatchStatus;
    
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
           
            try {
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
    public List processObjects(List dataList,Object fileInfo) {
        
         BatchUploadFile fileInfoObj = (BatchUploadFile)fileInfo;
         
        //do validation
        List objList = (List<String[]>)dataList;
        
        List finalObjects = new ArrayList<BatchMember>();
        
        //build status of record using the eventshort and statusflow
        Event curEvent = (Event)getEventDAO().findByEventShort(eventShort).get(0);

        Status initStatus = getStatusDAO().getStatusFromShort(initMemBatchStatus) ;

        StatusFlow statusFlow = getStatusFlowDAO().getFlowFromEventStatus(curEvent, initStatus);
        Status successStatus = getStatusDAO().findById(statusFlow.getStatusSuccessId());
        Status failStatus = getStatusDAO().findById(statusFlow.getStatusFailId());
        
        for(Object record: dataList )
        {
           String[] recordArray = (String[])record;
            Date today = new Date();
            batchMemberValidator.setBranchId(fileInfoObj.getBranchId());
            batchMemberValidator.setCompanyId(fileInfoObj.getCompanyId());
            com.sift.financial.member.Company comp = getCompanyDAO().findById(fileInfoObj.getCompanyId());
            batchMemberValidator.setCountryId(comp.getCountries().getId().intValue());
            
          BatchMember curBatch =  batchMemberValidator.doValidation(recordArray);
          
          curBatch.setCreatedBy(fileInfoObj.getCreatedBy());
          curBatch.setCreatedDate(today);
          curBatch.setDelFlg("N");
          curBatch.setBatchUploadRefId(fileInfoObj.getBatchUploadReference().getBatchUploadReferenceId());
          
          if(curBatch.hasErrors())
          {
             curBatch.setStatus(failStatus);
          }
          else
          {
           curBatch.setStatus(successStatus);
          }          
          finalObjects.add(curBatch);
        }
        
       return finalObjects;
     }

    @Override
    public boolean writeObjects(List dataList,Object fileInfo) 
    {
        
        boolean returnVal = false;
        //files
          BatchUploadFile fileInfoObj = (BatchUploadFile)fileInfo;
         
          Event curEvent = (Event)getEventDAO().findByEventShort(eventShort).get(0);

          //Status initStatus = getStatusDAO().getStatusFromShort(initMemBatchStatus) ;
           StatusFlow statusFlow = getStatusFlowDAO().getFlowFromEventStatus(curEvent, fileInfoObj.getStatus());
           
            Status successStatus = getStatusDAO().findById(statusFlow.getStatusSuccessId());
            Status failStatus = getStatusDAO().findById(statusFlow.getStatusSuccessId());
            fileInfoObj.setStatus(successStatus);
             Session sess = null;
            try
            {
               sess =getBatchUploadFileDAO().getSessionFactory().openSession();
               sess.beginTransaction();
               
              // getBatchUploadFileDAO().save(fileInfoObj, sess);
               getBatchUploadFileDAO().merge(fileInfoObj, sess);
               
               for(Object record : dataList)
               {
                 BatchMember batchMem = (BatchMember)record;
                 batchMem.setBatchUploadFile(fileInfoObj);
                 getBatchMemberDAO().save(batchMem, sess);
               }
              sess.getTransaction().commit();
              returnVal = true;
            }
            catch(Exception ex)
            {
                sess.getTransaction().rollback();
                ex.printStackTrace();
                setMsg(ex.getMessage());
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

    public BatchMemberValidator getBatchMemberValidator() {
        return batchMemberValidator;
    }

    public void setBatchMemberValidator(BatchMemberValidator batchMemberValidator) {
        this.batchMemberValidator = batchMemberValidator;
    }

    public String getMemberFileCol() {
        return memberFileCol;
    }

    public void setMemberFileCol(String memberFileCol) {
        this.memberFileCol = memberFileCol;
    }

    public String getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }

    public String getInitMemBatchStatus() {
        return initMemBatchStatus;
    }

    public void setInitMemBatchStatus(String initMemBatchStatus) {
        this.initMemBatchStatus = initMemBatchStatus;
    }

   
    
    
    
   
}
