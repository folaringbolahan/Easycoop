/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.schedule;

import com.sift.financial.ReminderInterface;
import com.sift.financial.member.Banks;
import com.sift.financial.member.BatchMember;
import com.sift.financial.member.BatchUploadFile;
import com.sift.financial.member.Branch;
import com.sift.financial.member.CompanyMemberIdentifier;
import com.sift.financial.member.Member;
import com.sift.financial.member.StockInterface;
import com.sift.financial.utility.SiftFinancialUtil;
import com.sift.financial.member.Company;
import com.sift.financial.member.Event;
import com.sift.financial.member.IdentificationDoc;
import com.sift.financial.member.MemberContribution;
import com.sift.financial.member.MemberType;
import com.sift.financial.member.Religion;
import com.sift.financial.member.Status;
import com.sift.financial.member.StatusFlow;
import com.sift.financial.member.TaxGroups;
import com.sift.financial.services.MailNotificationImpl;
import com.sift.financial.utility.customutil;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
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
public class ValidateScheduleMemberImpl extends ValidateScheduleService implements ValidateScheduleInterface {
    
    private static final Log log = LogFactory.getLog(ValidateScheduleMemberImpl.class);
   
    @Autowired
    private SiftFinancialUtil siftUtil;
     
    @Autowired
    @Value("${memberProcessed}")
    private String memberProcessed;
    
    //@Autowired
    //Value("${memberValidated}")
   // private String memberValidated;
    
    @Autowired
    @Value("${VALID-BATCH}")
    private String valdationEvent;
    
    @Autowired
    @Value("${memberTargetStatus}")
    private String memberTargetStatus;
    
     @Autowired
     @Value("${DEF-CON-PRODTYPE}")
     private String prodType;
    
    private boolean memInd = false;
    
    @Autowired
    private MailNotificationImpl emailDispatcher;
    
    @Override
    public List getRecords(BatchUploadFile object, boolean recordPassed) {
      log.info("Here inside get Records....................");
      return  getBatchMemberDAO().getBatchMemberByStatus(memberProcessed, object.getBatchUploadFileId(), object.getCompanyId(), object.getBranchId());
    }

    @Override
    public List buildRecords(List theRecords, Object fileInfo,boolean recordPassed) {
        
        log.info("Here inside buildRecords....................");
        
       BatchUploadFile batchUploadFile = (BatchUploadFile)fileInfo;
        List returnVal = new ArrayList<BatchMember>();
        Company mainComp = null;
        Branch branch = null;
        Status status = null;
        Status memberStatus = null;
            int n= 0;
         log.info("Satring loop over records inside build Records ....................");
        for(Object obj: theRecords)
        {
         
         BatchMember batchMem = (BatchMember)obj;
         batchMem.setModifiedBy(batchUploadFile.getModifiedBy());
         batchMem.setModifiedDate(batchUploadFile.getModifiedDate());
         
         if(recordPassed)
         {
                Member member = null;
                Religion religion = null;
                MemberType memberType = null;
                IdentificationDoc identificationDoc = null;
                Banks banks = null;
          
                   //getCompany
                    if(mainComp ==null)
                    {
                       mainComp = getCompanyDAO().findById(batchMem.getCompanyId());
                    }
                    
                     //buildMember
                    member =  getMemberRecord(batchMem,mainComp);
                    
                    
                    member.setCompany(mainComp);
                    //branch
                    if(branch ==null)
                    {
                       branch = getBranchDAO().findById(batchMem.getBranchId()); 
                    }
                    member.setBranch(branch);

                    if(status==null)
                    {
                        Event curEvent = (Event)getEventDAO().findByEventShort(valdationEvent).get(0);
                        Status initStatus = batchMem.getStatus() ;
                        StatusFlow statusFlow = getStatusFlowDAO().getFlowFromEventStatus(curEvent, initStatus);

                        status = getStatusDAO().findById(statusFlow.getStatusSuccessId());
                    }
                    batchMem.setStatus(status);
                    
                    if(memberStatus == null)
                    {
                         memberStatus = (Status)getStatusDAO().findByProperty("statusShort", memberTargetStatus).get(0);
                    }
                     member.setStatus(memberStatus);

                     religion =getReligionDAO().getReligionByCountryIdNDesc(mainComp.getCountries().getId().toString(), batchMem.getReligionId());
                     member.setReligion(religion);

                     memberType = (MemberType)getMemberTypeDAO().findByMemberTypeDesc(batchMem.getMemberTypeId()).get(0);
                     member.setMemberType(memberType);

                     identificationDoc = getIdentificationDocDAO().getIdentificationDocByCountryIdNDesc(mainComp.getCountries().getId().toString(), batchMem.getIdentificationId());
                     member.setIdentificationDoc(identificationDoc);
                     member.setIdentificationCode(batchMem.getIdentificationCode());

                     if(batchMem.getBank()!=null)
                     {
                         banks = getBanksDAO().getBanksByCountryNDesc(mainComp.getCountries().getId().toString(), batchMem.getBank());
                         member.setBanks(banks);
                         member.setBankAccount(batchMem.getBankAcct());
                     }

                     member.setBatchMemberId(batchMem.getBatchMemberId());
                     
                     batchMem.setMember(member);
                     
          log.info("built Records...................." +  n);
          n= n+1;
          }
            
           returnVal.add(batchMem);
    }
        
       return returnVal;
  }

@Override
    public List processObjects(List dataList, Object fileInfo, boolean recordPassed) {
         log.info("Here inside processObjects....................nothing much happening");
    return dataList;
}

    @Override
    public boolean writeObjects(List dataList, Object fileInfo, boolean recordPassed) {
        
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
               
                 if(recordPassed)
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
                                    BatchMember memBatchObj = (BatchMember)memBatch;

                                    Member curMember = (Member)memBatchObj.getMember();

                                    curMember = getMemberDao().saveReturn(curMember, sess);
                                    
                                   if (curMember.getMemberContributions()!=null)
                                    {
                                        for(Object memCon: curMember.getMemberContributions())
                                        {
                                            log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");
                                            log.info(curMember.getMemberId());
                                            log.info(curMember.getMemberNo());
                                            MemberContribution memConObj = (MemberContribution)memCon;
                                            memConObj.setMember(curMember);
                                            
                                            getMemberContributionDAO().save(memConObj, sess);
                                        }
                                    }
                               
                                    getBatchMemberDAO().merge(memBatchObj, sess);

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
                            // mass merge Batch Member
                            for(Object memBatch : dataList)
                            {
                                    BatchMember memBatchObj = (BatchMember)memBatch;
                                    memBatchObj.setStatus(failStatus);

                                    getBatchMemberDAO().merge(memBatchObj, sess);
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
    public boolean sendNotification(Object fileBundle, Map<String, Object> processInfo, boolean Ok) {
        
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
        
        emailDispatcher.sendMail(recipients, mailInfo, valdationEvent, String.valueOf(Ok));
          	   
        return emailDispatcher.isMailOk();
    }
    
    
  public Member getMemberRecord(BatchMember batchMember, Company comp)
  {
     log.info("Here inside getMemberRecord....................building memeber: " + batchMember.getFirstName());
      Member member = new Member();
      
      member.setBankAccount(valdationEvent);
      //member.setB
      member.setCreatedBy(batchMember.getCreatedBy());
      member.setCreatedDate(batchMember.getCreatedDate());
      member.setDelFlg("N");
      member.setDob(batchMember.getDob());
      member.setEmailAdd1(batchMember.getEmailAdd1());
      member.setEmailAdd2(batchMember.getEmailAdd2()!=null?batchMember.getEmailAdd2().toLowerCase():"");
      member.setEmailAdd3(batchMember.getEmailAdd3()!=null?batchMember.getEmailAdd3().toLowerCase():"");
      member.setFirstName(batchMember.getFirstName());
      member.setGender(batchMember.getGender());
      member.setMemberCompId(batchMember.getMemberCompId());
      Map<String, String> theMap = getMemberCode(batchMember.getCompanyId());
      
        if(theMap!=null && theMap.size()>0)
        {
            log.info("theMap.get(\"MEMCODE\")" +  theMap.get("MEMCODE"));
            member.setMemberNo(theMap.get("MEMCODE"));
        }
        
      member.setMiddleName(batchMember.getMiddleName()!=null?batchMember.getMiddleName():"");
      member.setModifiedBy(batchMember.getModifiedBy());
      member.setModifiedDate(new Timestamp(batchMember.getModifiedDate().getTime()));
      member.setPhoneNo1(batchMember.getPhoneNo1());
      member.setPhoneNo2(batchMember.getPhoneNo2()!=null?batchMember.getPhoneNo2():"");
      member.setPhoneNo3(batchMember.getPhoneNo3()!=null?batchMember.getPhoneNo3():"");
      member.setSurname(batchMember.getSurname());
      member.setUploadId(batchMember.getBatchUploadFile().getBatchUploadFileId());
      member.setBatchMemberId(batchMember.getBatchMemberId());
      Set contribs = getMemberContributionEntries(batchMember, comp);
      if(contribs!=null)
      {
        member.setMemberContributions(contribs);
      }
      member.setNokMiddleName(batchMember.getNokMiddleName()!=null?batchMember.getNokMiddleName():"");
      member.setNokName(batchMember.getNokName());
      member.setNokPhone(batchMember.getNokPhone());
      member.setNokSurname(batchMember.getNokSurname());
      
   return member;
  
  }
  
  
  private  Map<String,String> getMemberCode(Integer compId)
  {
 
             Date today = new Date();
             memInd = false;
             Session sess = getCompanyMemberIdentifierDAO().getSessionFactory().openSession();
             Map<String,String> memCodeMap = null;
             Transaction trx = null;
             
             try
             {
               trx = sess.beginTransaction();
               
                memCodeMap = getCompanyMemberIdentifierDAO().getNextMemberIdentity(compId.toString(), sess);
							
                CompanyMemberIdentifier compNew =  new CompanyMemberIdentifier();
                compNew.setCompanyId(compId);

                        if(memCodeMap.get("MEMCODE").equals(StockInterface.STARTIDENTITIER))
                        {
                                //do insert
                                compNew.setLastMemberCode(Integer.valueOf(StockInterface.STARTIDENTITIER));
                                compNew.setLastDate(today);
                                //save object
                                getCompanyMemberIdentifierDAO().save(compNew, sess);
                        }
                        else
                        {
                                //do update
                                compNew.setLastMemberCode(Integer.valueOf(memCodeMap.get("MEMCODE")));
                                compNew.setLastDate(today);
                                compNew.setCompanyMemberId(Integer.valueOf(memCodeMap.get("ID")));
                                //Update Record
                                getCompanyMemberIdentifierDAO().update(compNew, sess);
                        }
                        
               trx.commit();
               memInd = true;
             }
             catch(Exception ex)
             {
                memCodeMap = null;
                 trx.rollback();
                memInd = false;
             }
             finally
             {
             sess.close();
             }
             log.info("Here inside getMemberCode ...................retrieved Code  ::: " + memCodeMap);
             return memCodeMap;
  
  }
  
  
  
  public Set getMemberContributionEntries (BatchMember batchMember, Company comp)
	{
		Set contribList = new HashSet(0);
                
       log.info("Here inside getMemberContributionEntries ...................batchMember  ::: " + batchMember.getFirstName());
       
                List<Map<String,Object>> theProds = getGenericConfigDAO().getDefaultProductsByType(batchMember.getCompanyId().toString(), batchMember.getBranchId().toString(), prodType);
         
		if(theProds!=null && theProds.size()> 0)
                {
                    for(Map<String,Object> Key: theProds)
                    {
                            MemberContribution contrb = new MemberContribution();
                            
                            //System.out.println("(String)Key.get(\"code\") ::" +  (String)Key.get("code"));
                             
                            String theCode = (String)Key.get("code");
                            //System.out.println("theCode " +  theCode);
                              
                            //String valKey = (String)Key.get("code")+ "_val";
                            //System.out.println("valKey :: " +  valKey);
                            double theValue = batchMember.getContribution();
                            
                            //System.out.println("theValue ::" +  theValue);
                            
                            contrb.setBranchId(batchMember.getBranchId());
                            log.info(batchMember.getBranchId());
                            contrb.setCompanyId(batchMember.getCompanyId());
                             log.info(batchMember.getCompanyId());
                            contrb.setContribProd(theCode);
                             log.info(theCode);
                            contrb.setCountryId(comp.getCountries().getId().intValue());
                             log.info(comp.getCountries().getId().intValue());
                            contrb.setCreatedDate(new  java.sql.Timestamp(batchMember.getCreatedDate().getTime()));
                             log.info(new  java.sql.Timestamp(batchMember.getCreatedDate().getTime()));
                            contrb.setCreatedBy(batchMember.getCreatedBy());
                             log.info(batchMember.getCreatedBy());
                            contrb.setMemberContribValue(theValue);
                             log.info(theValue);
                            contrb.setModifiedBy(batchMember.getModifiedBy());
                             log.info(batchMember.getModifiedBy());
                            contrb.setModifiedDate(new java.sql.Timestamp(batchMember.getModifiedDate().getTime()));
                             log.info(new  java.sql.Timestamp(batchMember.getModifiedDate().getTime()));
                            //contrb.setApprovedBy(null);
                            //contrb.setApprovedDate(null);
                            contribList.add(contrb);
    log.info("Here inside getMemberContributionEntries ...................ading prod for batchMember  ::: " + batchMember.getFirstName() + " and prod " + contrb.getContribProd());
                    }
                }
		    log.info("leaving  getMemberContributionEntries ................... for batchMember  ::: " + batchMember.getFirstName() + " and prod size " + contribList.size());

		 return contribList;
	}
  
  
  
  private boolean doMemberContribution()
    {
        return false;
    }
    
}
