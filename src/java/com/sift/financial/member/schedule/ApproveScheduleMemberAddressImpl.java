/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.schedule;

import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.financial.ReminderInterface;
import com.sift.financial.member.ActivityInterface;
import com.sift.financial.member.AddressEntries;
import com.sift.financial.member.BatchAddressEntries;
import com.sift.financial.member.BatchUploadFile;
import com.sift.financial.member.Event;
import com.sift.financial.member.Member;
import com.sift.financial.member.Status;
import com.sift.financial.member.StatusFlow;
import com.sift.financial.services.MailNotificationImpl;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.webservice.utility.WebServiceUtility;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class ApproveScheduleMemberAddressImpl extends ValidateScheduleService implements ValidateScheduleInterface {
    
    private static final Log log = LogFactory.getLog(ApproveScheduleMemberAddressImpl.class);
    
    @Autowired
    @Value("${addrValidated}")
    private String addrValidated;
    
    @Autowired
    @Value("${APPR-ADDBATCH}")
    private String approvalEvent;
    
    
    @Autowired
    private BranchService branchService;
    WebServiceUtility webServiceUtility = new WebServiceUtility();
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CountryService countryService;
    EazyCoopUtility eazyCoopUTIL = new EazyCoopUtility();
    
     @Autowired
    private MailNotificationImpl emailDispatcher;
    
    @Override
    public List getRecords(BatchUploadFile object, boolean passOk) {

        log.info("Here inside get Records....................");
        List returnVal = getBatchAddressEntriesDAO().getBatchAddressByStatus(addrValidated, object.getBatchUploadFileId(), object.getCompanyId(), object.getBranchId());
        log.info("Here still inside get Records....................selected this no of records::" + returnVal.size() );
        return  returnVal;   
    }

    @Override
    public List buildRecords(List theRecords, Object fileInfo, boolean passOk) {

     log.info("Here inside buildRecords....................");
        
        List theUpdateList = new ArrayList<>();
        
        if(passOk)
        {
               if(theRecords!=null && fileInfo!=null)
                {
                       log.info("Here inside buildRecords....................first level If");
    
                         try
                         {
                          for(Object mem : theRecords)
                          {
                              BatchAddressEntries batchAddressRecord = (BatchAddressEntries)mem;
                              
                              log.info(" Here inside buildRecords....................building Address entries Objcets");
                            
                                ///get Member Record
                                AddressEntries  addObj = new AddressEntries();
                                
                                addObj.setActive("Y");
                                addObj.setAddrFieldName(batchAddressRecord.getAddrFieldName());
                                addObj.setAddrFieldValue(batchAddressRecord.getAddrFieldValue());
                                addObj.setCreatedBy(batchAddressRecord.getCreatedBy());
                                addObj.setCreationDate(batchAddressRecord.getCreationDate());
                                addObj.setDeleted("N");
                                String[] keyIds = batchAddressRecord.getKeyId().split(":::");
                                addObj.setKeyId(Long.parseLong(keyIds[0]));
                                addObj.setLastModificationDate(new java.sql.Timestamp(batchAddressRecord.getModifiedDate().getTime()));
                                addObj.setLastModifiedBy(batchAddressRecord.getModifiedBy());
                                addObj.setSerialPos(batchAddressRecord.getSerialPos());
                                addObj.setTypeId(Long.parseLong(batchAddressRecord.getTypeId()));
                              
                                batchAddressRecord.setAddEntries(addObj);
                                
                                if(batchAddressRecord.getRecAction().equals(ActivityInterface.RECORDEXIST))
                                {
                                   AddressEntries delObj = getAddEntriesDAO().getMemberAddressObjectByTypeField(batchAddressRecord.getKeyId(), batchAddressRecord.getTypeId(), batchAddressRecord.getAddrFieldName());
                                     
                                   if (delObj!=null)
                                   {
                                     batchAddressRecord.setExistingEntries(delObj);
                                   }
                                }
                               
                             theUpdateList.add(batchAddressRecord);
                          }

                           log.info("Here inside buildRecords....................record for BatchAddresEntries ");
                         }
                         catch(Exception ex)
                         {
                          theUpdateList = null;
                          log.info(ex.getMessage());
                          log.info("Here inside buildRecords....................record for BatchAddresEntries On Failure");
                         }
                         finally
                         {
                         
                         }
                }

            return theUpdateList;

        }
        else
        {
            return theRecords;
        }
    
    }

    @Override
    public List processObjects(List dataList, Object fileInfo, boolean passOk) {
        
       log.info("Here inside processObjects....................doing nothing !!!!!");
      
        return dataList;
    }

    @Override
    public boolean writeObjects(List dataList, Object fileInfo, boolean passOk) {

        log.info("Here inside writeObjects....................update Statuses and update database");
         
        boolean returnVal = false;
        Session sess = null;
        Transaction tx = null;
        boolean connected =false;
                
        if(dataList!=null && fileInfo!=null)
        {
              log.info("Here inside writeObjects....................first level If");
               
             if(fileInfo instanceof BatchUploadFile)
             {
                 log.info("Here inside writeObjects....................second level If");
                 
                BatchUploadFile batchUploadFile = (BatchUploadFile)fileInfo;
                                
                 //retrieves status of BatchFile
                 Event curEvent = (Event)getEventDAO().findByEventShort(approvalEvent).get(0);
                 Status initStatus = batchUploadFile.getStatus() ;
                 StatusFlow statusBatchFlow = getStatusFlowDAO().getFlowFromEventStatus(curEvent, initStatus);
                 Status successBatchStatus = getStatusDAO().findById(statusBatchFlow.getStatusSuccessId());
                 Status failBatchStatus = getStatusDAO().findById(statusBatchFlow.getStatusFailId());
                 
                 //retrieve status of BatchAddressEntries
                 Status addrInitStatus = (Status)getStatusDAO().findByStatusShort(addrValidated).get(0);
                 StatusFlow statusAddrFlow = getStatusFlowDAO().getFlowFromEventStatus(curEvent, addrInitStatus);
                 Status successAddrStatus = getStatusDAO().findById(statusAddrFlow.getStatusSuccessId());
                 Status failAddrStatus = getStatusDAO().findById(statusAddrFlow.getStatusFailId());
                
                 sess = getBatchUploadFileDAO().getSessionFactory().openSession();
                 
                  Map<String,Object> user = new HashMap <String,Object>();
                  
                  
                 if (branchService.getBranch(batchUploadFile.getBranchId()).getConnectToEazyCoop().equalsIgnoreCase("Y") && branchService.getBranch(batchUploadFile.getBranchId()).getActive().equalsIgnoreCase("Y"))
                 {
                   connected = true;
                 }
                  
                                 
                 if(passOk)
                 {
                     log.info("Here inside writeObjects....................inside pass OK ");
                    try
                    {
                            tx = sess.beginTransaction();

                            batchUploadFile.setStatus(successBatchStatus);
                            int n = 0;
                            StringBuffer addrValue = new StringBuffer();
                            
                            while(n < dataList.size())
                            {
                                 log.info("Here inside writeObjects....................looping on n= " + n);
                                //do for MemberBatch
                                Object addrBatch  = dataList.get(n);
                                
                                BatchAddressEntries addrBatchObj = (BatchAddressEntries)addrBatch;
                                
                                //if(addrBatchObj.getTypeId())
                               // {
                               //  addrValue.append(addrValue)        
                               // }
                                                             
                                addrBatchObj.setApprovedBy(batchUploadFile.getApprovedBy());
                                addrBatchObj.setApprovedDate(batchUploadFile.getApprovedDate());

                                addrBatchObj.setStatus(successAddrStatus);
                                
                                //Delete existing address if it exists
                                if(addrBatchObj.getRecAction().equals(ActivityInterface.RECORDEXIST) && addrBatchObj.getExistingEntries()!=null)
                                {
                                    //delete existing address info.
                                    getAddEntriesDAO().delete(addrBatchObj.getExistingEntries(), sess);
                                }
                                  
                                    //Write New address Info
                                   getAddEntriesDAO().save(addrBatchObj.getAddEntries(), sess);

                                  // update BatchAdddress Entries
                                   getBatchAddressEntriesDAO().merge(addrBatchObj, sess);
                                
                                   //getMemberDao().merge(memRecord, sess);
                                
                                 log.info("Here inside writeObjects....................ending loop on n= " + n);
                                n = n+1;
                            }
                            
                            getBatchUploadFileDAO().merge(batchUploadFile, sess);

                         tx.commit();
                         returnVal = true;
                 }
                 catch(Exception ex)
                   {tx.rollback();
                      returnVal = true;}
                 finally
                 {sess.close();}
                 
                }
                 else
                {

                    try
                    {
                            tx = sess.beginTransaction();

                            batchUploadFile.setStatus(failBatchStatus);
                            int n = 0;

                            while(n < dataList.size())
                            {
                                //do for MemberBatch
                                Object stkBatch  = dataList.get(n);
                                
                                BatchAddressEntries stkBatchObj = (BatchAddressEntries)stkBatch;
                                stkBatchObj.setApprovedBy(batchUploadFile.getApprovedBy());
                                stkBatchObj.setApprovedDate(batchUploadFile.getApprovedDate());

                                stkBatchObj.setStatus(failAddrStatus);

                                getBatchAddressEntriesDAO().merge(stkBatchObj, sess);

                                n = n+1;
                            }
                            
                            getBatchUploadFileDAO().merge(batchUploadFile, sess);

                         tx.commit();
                         returnVal = true;
                 }
                 catch(Exception ex)
                   {tx.rollback();
                      returnVal = true;}
                 finally
                 {sess.close();}
              
                 }                 
             }
        }
        
        return returnVal;
        }

    @Override
    public boolean sendNotification(Object fileBundle, Map<String, Object> processInfo, boolean mailToSend) {
       
        log.info(" Sending completion mail for Approval.............");
        
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
        mailInfo.put(ReminderInterface.PROC_USER, fileObject.getApprovedBy());
        mailInfo.put(ReminderInterface.PROC_FILE, fileObject.getBatchUploadType().getUploadTypeName());
        
        //Add Recipients 
        Map<String, Object> recipients = new HashMap<String, Object>();
        recipients.put(ReminderInterface.CREATOR, fileObject.getApprovedBy());
        //recipients.put(ReminderInterface.NEXTLEVEL, super.getNextValidUserEmail(fileObject.getBranchId(), fileObject.getCompanyId(), keyColumn));
        recipients.put(ReminderInterface.COMPANYALL, super.getAllValidUserEmail(fileObject.getBranchId(), fileObject.getCompanyId()));
        
        emailDispatcher.sendMail(recipients, mailInfo, approvalEvent, String.valueOf(mailToSend));
          	   
        return emailDispatcher.isMailOk();
    }

   
    public String getAddrValidated() {
        return addrValidated;
    }

    public void setAddrValidated(String addrValidated) {
        this.addrValidated = addrValidated;
    }

    public String getApprovalEvent() {
        return approvalEvent;
    }

    public void setApprovalEvent(String approvalEvent) {
        this.approvalEvent = approvalEvent;
    }
    
    
    private void doEasyCoop(String memNo,int dbranch, int dcompany, String addrStr, boolean connected)
        {
  
                    if (connected) 
                    {
                      
                        String resource = "useraddress";

                        try {
                            webServiceUtility.webserviceClient(resource, webServiceUtility.updateMemberAddress(dcompany,
                                    dbranch,memNo,addrStr));
                        } catch (IOException ex) {                            
                            ex.printStackTrace();
                        }

                    } else {
                       log.info("Company not connected to easycoop...");
                        //System.out.println("Is it Connected to EasyCoop : " + branchService.getBranch(dbranch).getConnectToEazyCoop());
                    }

        }

    
}
