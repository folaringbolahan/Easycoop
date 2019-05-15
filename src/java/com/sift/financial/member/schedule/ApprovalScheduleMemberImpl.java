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
import com.sift.financial.member.BatchMember;
import com.sift.financial.member.BatchUploadFile;
import com.sift.financial.member.Branch;
import com.sift.financial.member.Company;
import com.sift.financial.member.Event;
import com.sift.financial.member.Member;
import com.sift.financial.member.MemberHoldings;
import com.sift.financial.member.MemberHoldingsMovement;
import com.sift.financial.member.Status;
import com.sift.financial.member.StatusFlow;
import com.sift.financial.member.StockInterface;
import com.sift.financial.member.TransactionInterface;
import com.sift.financial.member.ws.client.Accnowbs;
import com.sift.financial.member.ws.client.Entrys;
import com.sift.financial.stock.services.MemberHoldingsService;
import com.sift.financial.utility.SiftFinancialUtil;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.webservice.utility.WebServiceUtility;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.task.TaskExecutor;
import com.sift.financial.member.schedule.ConnectEasyCoopTask;
import com.sift.financial.services.MailNotificationImpl;

/**
 *
 * @author baydel200
 */
public class ApprovalScheduleMemberImpl extends ValidateScheduleService implements ValidateScheduleInterface {
    
    private static final Log log = LogFactory.getLog(ApprovalScheduleMemberImpl.class);
    
    @Autowired
    private SiftFinancialUtil siftUtil;
     
    @Autowired
    @Value("${memberValidated}")
    private String memberValidated;
    
    @Autowired
    @Value("${APPR-BATMEM}")
    private String approvalEvent;
    
    @Autowired
    @Value("${apprvdStockStat}")
    private String apprvdStockStat;
    
    @Autowired
    @Value("${REGISTER}")
    private String tranCode; 

    @Autowired
    @Value("${regTranRef}")
    private String tranRef;
    
    
    @Autowired
    @Value("${memberValidated}")
    private String valMemberStatus;

    @Autowired
    private MemberHoldingsService memberHoldingsService;
    
    @Autowired
    private MailNotificationImpl emailDispatcher;
    
    
    @Autowired
    private BranchService branchService;
   // WebServiceUtility webServiceUtility = new WebServiceUtility();
   // @Autowired
   // private CompanyService companyService;
   // @Autowired
   // private CountryService countryService;
   // EazyCoopUtility eazyCoopUTIL = new EazyCoopUtility();
    
    
    @Autowired
    private TaskExecutor taskExec;
               
    @Autowired
    private ConnectEasyCoopTask  coopTask;
    
    @Override
    public List getRecords(BatchUploadFile object, boolean passOk) {
        log.info("Here inside get Records....................");
        log.info("memberValidated " + memberValidated);
        log.info("object.getBatchUploadFileId() " + object.getBatchUploadFileId());
        log.info("object.getCompanyId() " + object.getCompanyId());
        log.info("object.getBranchId() " + object.getBranchId());
        List returnVal = getBatchMemberDAO().getBatchMemberByStatus(memberValidated, object.getBatchUploadFileId(), object.getCompanyId(), object.getBranchId());
        log.info("Here still inside get Records....................selected this no of records::" + returnVal.size() );
        return  returnVal;
    }

    @Override
    public List buildRecords(List theRecords, Object fileInfo, boolean passOk) {
        
          log.info("Here inside buildRecords....................not much happening....returning same");
          
        return theRecords;
    }

    @Override
    public List processObjects(List dataList, Object fileInfo, boolean passOk) {
        
         log.info("Here inside processObjects....................something happening");
        //build movemenet here 
        if(passOk)
        {
                boolean returnVal = false;
                Session sess = null;
                Transaction tx = null;
                List getValidatedMembers = null;

                if(dataList!=null && fileInfo!=null)
                {
                       log.info("Here inside processObjects....................first level If");
                     if(fileInfo instanceof BatchUploadFile)
                     {
                         log.info("Here inside processObjects....................second level If");
                         
                          BatchUploadFile batchUploadFile = (BatchUploadFile)fileInfo;
                          getValidatedMembers = getMemberDao().getValidatedMemberByIdCompBranch(batchUploadFile.getCompanyId(), batchUploadFile.getBranchId(),valMemberStatus, batchUploadFile.getBatchUploadFileId());
                          Event curEvent = (Event)getEventDAO().findByEventShort(approvalEvent).get(0);
                          sess = getBatchUploadFileDAO().getSessionFactory().openSession();
                         try
                         {
                           tx = sess.beginTransaction();
                            log.info("Here inside processObjects....................starting loop for validated members holdings");
                            int n = 0;
                          for(Object mem : getValidatedMembers)
                          {
                              Member memRecord = (Member)mem;
                              
                              log.info("Here inside processObjects....................going for members holdings ::" + memRecord.getFirstName());
                            ///check if exist in case of failure
                              buildMemberHoldings (memRecord, curEvent, sess);
                              
                              n= n+1;
                          }

                          tx.commit();
                          returnVal = true;
                           log.info("Here inside processObjects....................record for members holdings successfully committed");
                         }
                         catch(Exception ex)
                         {
                            tx.rollback();
                            log.info(ex.getMessage());
                            returnVal = false;
                            log.info("Here inside processObjects....................record for members holdings successfully rolled back");
                         }
                         finally
                         {
                            sess.close();
                         }

                     }
                }

                if(returnVal)
                { return dataList;
                }
                else
                {return null;}
        }
        else
        {
            return dataList;
        }
        
    }

    @Override
    public boolean writeObjects(List dataList, Object fileInfo, boolean passOk) {
        
        //Account creation
        //contribution creation
        //Posting of register stock type
         log.info("Here inside writeObjects....................alot about to happen");
         
        boolean returnVal = false;
        Session sess = null;
        Transaction tx = null;
        List getValidatedMembers = null;
        List<Map<String,Object>>  theKeysProd =null;
        Company comp = null;
        Branch branch =  null;
        boolean connected = false;
        
         List<Member> members =null;
        
        if(dataList!=null && fileInfo!=null)
        {
               log.info("Here inside writeObjects....................first level If");
               
             if(fileInfo instanceof BatchUploadFile)
             {
                 log.info("Here inside writeObjects....................second level If");
                 
                BatchUploadFile batchUploadFile = (BatchUploadFile)fileInfo;
                //getValidatedMembers = getMemberDao().getValidatedMemberByIdCompBranch(batchUploadFile.getCompanyId(), batchUploadFile.getBranchId(), "");
                theKeysProd = getGenericConfigDAO().getDefProducts(batchUploadFile.getCompanyId().toString(), batchUploadFile.getBranchId().toString());
                
                 //retries status od BatchFile
                 Event curEvent = (Event)getEventDAO().findByEventShort(approvalEvent).get(0);
                 Status initStatus = batchUploadFile.getStatus() ;
                 StatusFlow statusBatchFlow = getStatusFlowDAO().getFlowFromEventStatus(curEvent, initStatus);
                 Status successBatchStatus = getStatusDAO().findById(statusBatchFlow.getStatusSuccessId());
                 Status failBatchStatus = getStatusDAO().findById(statusBatchFlow.getStatusFailId());
                 
                 Status memInitStatus = (Status)getStatusDAO().findByStatusShort(memberValidated).get(0);
                 StatusFlow statusMemFlow = getStatusFlowDAO().getFlowFromEventStatus(curEvent, memInitStatus);
                 Status successMemStatus = getStatusDAO().findById(statusMemFlow.getStatusSuccessId());
                 Status failMemStatus = getStatusDAO().findById(statusMemFlow.getStatusFailId());
                
                 sess = getBatchUploadFileDAO().getSessionFactory().openSession();
                 
                  Map<String,Object> user = new HashMap <String,Object>();
                  
                 //String query ="select a.name as name ,a.short_name  as short_name,a.code as code,a.Country_id as country_id,b.id as id ,b.currentyear as current_year,b.currentperiod as current_period,to_char(b.postdate,'YYYY-MM-DD') as post_date,b.currentsystemdate as current_sysdate,b.entrydate as entry_date,c.currency_code as currency_code as basecurrency as base_currency,c.timez as timez from company a , branch b,countries c where a.id = b.company_id and b.country_id = c.id and a.id  =" + batchUploadFile.getCompanyId()  + " and b.id = " + batchUploadFile.getBranchId() ;
                 Map<String, String> Companydetails = getGenericConfigDAO().getCompanyInfo(batchUploadFile.getCompanyId(), batchUploadFile.getBranchId());
                 user.put("Companydetails", Companydetails);
                 
                 log.info("Here inside writeObjects....................fetched company details");
                 
                 //String userQuery ="select userid as userid, username as username, branch as branch_id, companyid as company_id, accesslevel as access_level  FROM users where userid = '" + batchUploadFile.getCreatedBy() + "'";
                 Map<String, String> Curruser = getGenericConfigDAO().getUserInfo(batchUploadFile.getModifiedBy());
                 user.put("Curruser", Curruser);
                 
                 log.info("Here inside writeObjects....................fetched Curruser");
                
                 com.sift.admin.model.Branch bran = null;
                
                    try
                    {
                       bran= branchService.getBranch(batchUploadFile.getBranchId());
                    }
                    catch(Exception branchError)
                    {
                    log.info("branchError::::" + branchError.getMessage());
                    }

                     if (bran!=null)
                     {
                        if (bran.getConnectToEazyCoop()!=null && bran.getActive()!=null && bran.getConnectToEazyCoop().equalsIgnoreCase("Y") && bran.getActive().equalsIgnoreCase("Y"))
                        {
                          connected = true;
                        }
                     }
                 
                 if(passOk)
                 {
                     
                   members = new ArrayList<Member>();
                   
                     log.info("Here inside writeObjects....................inside pass OK ");
                    try
                    {
                            tx = sess.beginTransaction();

                            batchUploadFile.setStatus(successBatchStatus);
                            int n = 0;

                            while(n < dataList.size())
                            {
                                 log.info("Here inside writeObjects....................looping on n= " + n);
                                //do for MemberBatch
                                Object memBatch  = dataList.get(n);
                                BatchMember memBatchObj = (BatchMember)memBatch;
                                memBatchObj.setApprovedBy(batchUploadFile.getApprovedBy());
                                memBatchObj.setApprovedDate(batchUploadFile.getApprovedDate());

                                memBatchObj.setStatus(successMemStatus);

                                getBatchMemberDAO().merge(memBatchObj, sess);

                                //do routine for Member
                                /**      **/
                                Member memRecord = getMemberDao().getValidatedMemberByIdCompBranch(batchUploadFile.getCompanyId(), batchUploadFile.getBranchId(), memBatchObj.getBatchMemberId());
                                
                                    memRecord.setApprovedBy(batchUploadFile.getApprovedBy());
                                    memRecord.setApprovedDate(batchUploadFile.getApprovedDate());
                                    memRecord.setStatus(successMemStatus);
                                
                                //do products creation for member
                                boolean okProducts = doMemberProducts(memRecord, theKeysProd);

                                //stock posting via service
                                //user.getCurruser().getP
                                if(batchUploadFile.getPostInfo()!=null && batchUploadFile.getPostInfo().equalsIgnoreCase("Y"))
                                {
                                      boolean okHoldings = postMemberHoldings (memRecord,curEvent,user);
                                }
                                
                                getMemberDao().merge(memRecord, sess);
                                
                                members.add(memRecord);
                                
                                //do EasyCoo
                                //doEasyCoop(memRecord, connected);
                               
                                log.info("Here inside writeObjects....................ending loop on n= " + n);
                                n = n+1;
                            }
                            
                            getBatchUploadFileDAO().merge(batchUploadFile, sess);

                         tx.commit();
                         returnVal = true;
                         
                         //send to EasyCopp if Coop is Commected
                         if(connected)
                         {
                                            
                            coopTask.setTargetObjs(members);
                            coopTask.setTypeInd(ActivityInterface.BATCHMEMBER);

                            log.info("Here inside apprDetail.................spurning task with info to EasyCoop");
                            taskExec.execute(coopTask);
                            log.info("Here inside apprDetail.................spurned task with info to EasyCoop");
                         }
                 }
                 catch(Exception ex)
                   {tx.rollback();
                      returnVal = true;}
                 finally
                 {sess.close();}
                 
                 }
                 else
                 {
                    log.info("Here inside apprDetail.deleting ................and spurned task with info to EasyCoop");
                    //mark batchfile and batchMember as rejected
                    //
                    ///do deletion of members 
                    try
                    {
                            tx = sess.beginTransaction();

                            batchUploadFile.setStatus(failBatchStatus);
                            int n = 0;

                            while(n < dataList.size())
                            {
                                log.info("Here inside apprDetail.................spurned task start count n==" +n + "====" + dataList.size());
                                //do for MemberBatch
                                Object memBatch  = dataList.get(n);
                                BatchMember memBatchObj = (BatchMember)memBatch;
                                memBatchObj.setApprovedBy(batchUploadFile.getApprovedBy());
                                memBatchObj.setApprovedDate(batchUploadFile.getApprovedDate());

                                memBatchObj.setStatus(failMemStatus);

                                getBatchMemberDAO().merge(memBatchObj, sess);

                                //do routine for Member
                                /**      **/
                                //try
                                //{
                                    //Member memRecord  = (Member)getValidatedMembers.get(n);
                                    
                                    Member memRecord = getMemberDao().getValidatedMemberByIdCompBranch(batchUploadFile.getCompanyId(), batchUploadFile.getBranchId(), memBatchObj.getBatchMemberId());

                                    getMemberContributionDAO().delete(memRecord, sess);
                                    
                                    getMemberDao().delete(memRecord, sess);
                                //}
                               // catch(Exception ne)
                               // {
                                //    ne.printStackTrace();
                                //    log.info("Here inside apprDetail................deleting ...spurned task ending ne==" +ne.getMessage());
                               // }

                                n = n+1;
                                 log.info("Here inside apprDetail................deleting ...spurned task ending count n==" +n);
                            }
                            
                             log.info("Here inside apprDetail.................merging batchFile :" );
                            getBatchUploadFileDAO().merge(batchUploadFile, sess);
                             log.info("Here inside apprDetail.................ending...merging batchFile :" );

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
    public boolean sendNotification(Object fileBundle, Map<String, Object> processInfo, boolean Ok) {
        
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
        
        emailDispatcher.sendMail(recipients, mailInfo, approvalEvent, String.valueOf(Ok));
          	   
        return emailDispatcher.isMailOk();
        
    }

    public MemberHoldingsService getMemberHoldingsService() {
        return memberHoldingsService;
    }

    public void setMemberHoldingsService(MemberHoldingsService memberHoldingsService) {
        this.memberHoldingsService = memberHoldingsService;
    }
    
    
    private boolean buildMemberHoldings (Member memRecord, Event curEvent, Session sess) throws Exception
    {
        
            Set theMoves = getMemberHoldingsService().buildDefaultRegisterHoldingsMovement(memRecord, memRecord.getCompany(), curEvent, apprvdStockStat);
			
			Iterator iter = theMoves.iterator();
			
			while(iter.hasNext())
                        {
				MemberHoldingsMovement holdMove= (MemberHoldingsMovement)iter.next();
				log.info("member.getCreatedDate() ::" + memRecord.getCreatedDate());
				holdMove.setCreatedDate(new  java.sql.Timestamp(memRecord.getCreatedDate().getTime()));
				holdMove.setCreatedBy(memRecord.getCreatedBy());
                                
                                    if(holdMove.getCompStockType().getRegisterStock().equals("Y"))
                                    {
                                        holdMove.setEffectiveDate(getMemberHoldingsService().getRegisterStockDate(holdMove.getCreatedDate()));
                                    }
                                    else
                                    {
                                        holdMove.setEffectiveDate(getMemberHoldingsService().getEffectiveStockDate(holdMove.getCreatedDate()));
                                    }

				///get aggregated stock entry....here
				Map<String, Object> holdings  = null;
   
                                        try
                                        {
                                            holdings = getMemberHoldingsService().aggregateHoldings(holdMove);
                                        }
                                        catch(Exception ex)
                                        {log.info(ex.getMessage());}
				
                                getMemberHoldingsService().getMemberHoldingsMovementDAO().save(holdMove, sess);
				
					for(String key : holdings.keySet())
					{
						MemberHoldings curHoldings  = (MemberHoldings)holdings.get(key);
						
						curHoldings.setCreatedDate(new  java.sql.Timestamp(memRecord.getCreatedDate().getTime()));
						curHoldings.setCreatedBy(memRecord.getCreatedBy());
						
						if(key.equals(StockInterface.DOUPDATE))
						{
							getMemberHoldingsService().getMemberHoldingsDAO().merge(curHoldings, sess);
						}
						else if(key.equals(StockInterface.DOINSERT))
						{
							getMemberHoldingsService().getMemberHoldingsDAO().save(curHoldings, sess);
						}
						
					}
			}
                        
      return true;
    }
    
   
    private boolean postMemberHoldings (Member member, Event currEvent, Map<String, Object> user)
    {
        log.info("Here inside WriteObjects....................posting for member....." + member.getMemberNo());
        
        Date today = new Date();
                    //create other accounts for other stocks if specified
                    if(member.getMemberHoldingsMovements()!=null && !member.getMemberHoldingsMovements().isEmpty())
                    {

                    //post Transaction based on stock assigned on default   and register stock to cash book if used 

                    //List theMove = getMemberHoldingsService().getMemberHoldingsMovementDAO().findByEventAndMember(createEvt, member);
                    Set theMove =  member.getMemberHoldingsMovements();

                    for(Object move : theMove)
                    {
                             boolean acctFlg = false;

                            MemberHoldingsMovement moved = (MemberHoldingsMovement)move;

                            //double amtDouble = stockDetailService.evaluateStockBuyAmt(member, moved.getCompStockType(), moved.getMovementHoldings());
                            //do posting 
                            //String companyId, String branchId, String memCode, String prodCode,
                           //List acctList = getMemberDao().getMemberActiveAccounts(member.getCompany().getId().toString(), member.getBranch().getId().toString(), member.getMemberNo(), "", sess);
                            Map<String, Object> tranObj = siftUtil.buildTransactionObjects(moved, currEvent, tranCode, user, today, tranRef);
                            //TODO
                            //get memebr account for product and source fund acount
                    if(tranObj!=null)
                    {

                        Entrys theEntrys = null; 

                            try
                            {

                             theEntrys=siftUtil.getTransaction((Map<String, Object>)tranObj.get(TransactionInterface.HEADERINFO), (List<Map<String, Object>>)tranObj.get(TransactionInterface.BODYINFO), user, tranRef);
                             Object result =   doRestService(theEntrys, ActivityInterface.SERVTYPEPOSTING);

                                if(result instanceof Entrys)
                                {
                                  theEntrys = (Entrys)result;
                                }
                                else if(result instanceof String)
                                {
                                 System.out.println("Posting result here :"   + result);
                                }

                                acctFlg= true;
                                System.out.println("Posting Succesful");

                            } catch (Throwable e) 
                            {
                            // TODO Auto-generated catch block
                                System.out.println("Here inside "  +  member.getAction() +  " failing product");
                                acctFlg= false;
                                e.printStackTrace();
                                //setMsg("Posting failed for Member Class :" + member.getSurname() + ":: " + moved.getCompStockType().getShortName() + " ::  value::" + moved.getMovementHoldings());
                                //throw new Exception("Posting Failed for member :" + member.getSurname()  + " on stock " + moved.getCompStockType().getShortName());
                            }
                    }

                    }


                    }
      return true;
    }

    public ConnectEasyCoopTask getCoopTask() {
        return coopTask;
    }

    public void setCoopTask(ConnectEasyCoopTask coopTask) {
        this.coopTask = coopTask;
    }
   /**
    private void doEasyCoop(Member member, boolean connected)
        {
                //call easycoop web service
                    int dbranch = member.getBranch().getId();
                    int dcompany = member.getCompany().getId();
                    // if (branchService.getBranch(dbranch).getConnectToEazyCoop().equalsIgnoreCase("Y") && branchService.getBranch(dbranch).getActive().equalsIgnoreCase("Y"))
                      
                    if (connected) {
                        //System.out.println("Company connected to easycoop...");

                        String name = member.getSurname() + " " + member.getFirstName() + " " + member.getMiddleName();
                        String resource = "user";

                        com.sift.admin.model.Company coy = companyService.getCompany(dcompany);
                        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                        Date regDate = member.getCreatedDate();
                        String registrationDate = formatter1.format(regDate);

                        try {
                            webServiceUtility.webserviceClient(resource, webServiceUtility.createMember(dcompany,
                                    dbranch, member.getEmailAdd1(),
                                    registrationDate,
                                    member.getMemberNo(), member.getPhoneNo1(), name.trim(), member.getEmailAdd1(), ""));
                        } catch (IOException ex) {                            
                            ex.printStackTrace();
                        }

                    } else {
                       log.info("Company not connected to easycoop...");
                        //System.out.println("Is it Connected to EasyCoop : " + branchService.getBranch(dbranch).getConnectToEazyCoop());
                    }

        }
    
   **/
    
    
    
}
