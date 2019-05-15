/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.schedule;

import com.sift.financial.ReminderInterface;
import com.sift.financial.member.ActivityInterface;
import com.sift.financial.member.BatchStock;
import com.sift.financial.member.BatchUploadFile;
import com.sift.financial.member.CompStockType;
import com.sift.financial.member.Event;
import com.sift.financial.member.Member;
import com.sift.financial.member.MemberHoldings;
import com.sift.financial.member.MemberHoldingsMovement;
import com.sift.financial.member.Status;
import com.sift.financial.member.StatusFlow;
import com.sift.financial.member.StockInterface;
import com.sift.financial.member.TransactionInterface;
import com.sift.financial.member.ws.client.Entrys;
import com.sift.financial.services.MailNotificationImpl;
import com.sift.financial.stock.services.MemberHoldingsService;
import com.sift.financial.utility.SiftFinancialUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
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
public class ApproveScheduleMemberStockImpl extends ValidateScheduleService implements ValidateScheduleInterface {
    
    private static final Log log = LogFactory.getLog(ApproveScheduleMemberStockImpl.class);
    
    @Autowired
    private SiftFinancialUtil siftUtil;
     
    @Autowired
    @Value("${stockValidated}")
    private String stockValidated;
    
    @Autowired
    @Value("${APPR-STKBATCH}")
    private String approvalEvent;
    
    @Resource (name="stockEvents")
    private Map<String, String> stockEvents;
    
    @Autowired
    @Value("${apprvdStockStat}")
    private String apprvdStockStat;
    
    @Autowired
    private MemberHoldingsService memberHoldingsService;
    
    @Autowired
    @Value("${REGISTER}")
    private String tranCode; 

    @Autowired
    @Value("${regTranRef}")
    private String tranRef;
    
    @Autowired
    private MailNotificationImpl emailDispatcher;

    
    @Override
    public List getRecords(BatchUploadFile object, boolean passOk) {
        log.info("Here inside get Records....................");
        List returnVal = getBatchStockDAO().getBatchStockByStatus(stockValidated, object.getBatchUploadFileId(), object.getCompanyId(), object.getBranchId());
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
                              BatchStock stockRecord = (BatchStock)mem;
                              
                              log.info("Here inside buildRecords....................going for members Email & Stock Type  with  ::" + stockRecord.getEmail1() + " :::: "   + stockRecord.getStockShort());
                            
                                ///get Member Record
                                Member memInfo = getMemberDao().getMemberByEmailCompBranch(stockRecord.getCompanyId(), stockRecord.getBranchId(),stockRecord.getEmail1());
                              
                                CompStockType stockType = getCompStockTypeDAO().getStocksByCompIdShortName(stockRecord.getCompanyId(), stockRecord.getStockShort());
                             
                                stockRecord.setMember(memInfo);
                                stockRecord.setCompStockType(stockType);
                                
                                if(stockRecord.getStockValue() > 0)
                                {
                                    stockRecord.setBuySellIndicator(StockInterface.ADDSTOCK);
                                }
                                else
                                {
                                    stockRecord.setBuySellIndicator(StockInterface.LOOSESTOCK);
                                }
                                
                             theUpdateList.add(stockRecord);
                          }

                           log.info("Here inside buildRecords....................record for BatchStock ");
                         }
                         catch(Exception ex)
                         {
                          theUpdateList = null;
                          log.info(ex.getMessage());
                          log.info("Here inside buildRecords....................record for Batch Stock On Failure");
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

        log.info("Here inside processObjects....................do nothing !!!!!");
        //build movemenet here 
       
        return dataList;
    
    }

    @Override
    public boolean writeObjects(List dataList, Object fileInfo, boolean passOk) {
    
        log.info("Here inside writeObjects....................alot about to happen");
         
        boolean returnVal = false;
        Session sess = null;
        Transaction tx = null;
                
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
                 
                 //retrieve status of BatchStock
                 Status stockInitStatus = (Status)getStatusDAO().findByStatusShort(stockValidated).get(0);
                 StatusFlow statusStockFlow = getStatusFlowDAO().getFlowFromEventStatus(curEvent, stockInitStatus);
                 Status successStkStatus = getStatusDAO().findById(statusStockFlow.getStatusSuccessId());
                 Status failStkStatus = getStatusDAO().findById(statusStockFlow.getStatusFailId());
                
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
                
                 if(passOk)
                 {
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
                                Object stkBatch  = dataList.get(n);
                                BatchStock stkBatchObj = (BatchStock)stkBatch;
                                
                                MemberHoldingsMovement memberHoldingsMovement =null ;
                                
                                stkBatchObj.setApprovedBy(batchUploadFile.getApprovedBy());
                                stkBatchObj.setApprovedDate(batchUploadFile.getApprovedDate());

                                stkBatchObj.setStatus(successStkStatus);
                                
                                Event postEvent = null;
                                           
                                postEvent = (Event)getEventDAO().findByEventShort(stockEvents.get(StockInterface.ADDSTOCK)).get(0);
                                
                                 memberHoldingsMovement =  buildMemberHoldings (stkBatchObj, postEvent, sess);

                                  boolean okHoldings = true;    
                                  String error= null;
         
                                if(batchUploadFile.getPostInfo()!=null && batchUploadFile.getPostInfo().equalsIgnoreCase("Y"))
                                {
                                      try
                                      {
                                          okHoldings = postMemberHoldings (memberHoldingsMovement,postEvent,user);
                                      }
                                      catch(Exception ex)
                                      {
                                        okHoldings = false;
                                        error = ex.getMessage();
                                      }
                                }
                                
                                if(!okHoldings)
                                {
                                  throw new Exception("Posting Failed ::: " + error);
                                }
                                
                                 getBatchStockDAO().merge(stkBatchObj, sess);
                                
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
                                //do for MemberBatch
                                Object stkBatch  = dataList.get(n);
                                BatchStock stkBatchObj = (BatchStock)stkBatch;
                                stkBatchObj.setApprovedBy(batchUploadFile.getApprovedBy());
                                stkBatchObj.setApprovedDate(batchUploadFile.getApprovedDate());

                                stkBatchObj.setStatus(failStkStatus);

                                getBatchStockDAO().merge(stkBatchObj, sess);

                                //do routine for Member
                                 /**      **/
                                //Member memRecord  = (Member)getValidatedMembers.get(n);

                                 //getMemberDao().delete(memRecord, sess);

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
    
    
    /**Build Holdings Value
     * 
     * @param stkRecord
     * @param curEvent
     * @param sess
     * @return
     * @throws Exception 
     */
     private MemberHoldingsMovement buildMemberHoldings (BatchStock stkRecord, Event curEvent, Session sess) throws Exception
    {
            //Member mem, Event event, Object typeKey, Double val, String buySellIndicator
            Set theMoves = getMemberHoldingsService().buildBatchHoldingsMovement(stkRecord.getMember(), curEvent, stkRecord.getCompStockType(), stkRecord.getStockValue(), stkRecord.getBuySellIndicator());
            
            MemberHoldingsMovement theMove = null;
            
			Iterator iter = theMoves.iterator();
			
			while(iter.hasNext())
                        {
				MemberHoldingsMovement holdMove= (MemberHoldingsMovement)iter.next();
				log.info("member.getCreatedDate() ::" + stkRecord.getCreatedDate());
				holdMove.setCreatedDate(new  java.sql.Timestamp(stkRecord.getCreatedDate().getTime()));
				holdMove.setCreatedBy(stkRecord.getCreatedBy());
                                
                                    if(holdMove.getCompStockType().getRegisterStock().equals("Y"))
                                    {
                                        holdMove.setEffectiveDate(getMemberHoldingsService().getRegisterStockDate(holdMove.getCreatedDate()));
                                    }
                                    else
                                    {
                                        holdMove.setEffectiveDate(getMemberHoldingsService().getEffectiveStockDate(holdMove.getCreatedDate()));
                                    }
                                    
                               log.info("Putting uploadId in builmemberHoldings here...............");
                               holdMove.setUploadId(stkRecord.getBatchUploadFile().getBatchUploadFileId());
                               log.info("Updated uploadId .............." +  holdMove.getUploadId());
                               
                               log.info("Putting BatchStockId in builmemberHoldings here...............");
                               holdMove.setBatchStockId(stkRecord.getBatchStockId());
                               log.info("Putting BatchStockId in builmemberHoldings here..............."+ +  holdMove.getBatchStockId());
                               
				///get aggregated stock entry....here
				Map<String, Object> holdings  = null;
   
                                        try
                                        {
                                            holdings = getMemberHoldingsService().aggregateHoldings(holdMove);
                                        }
                                        catch(Exception ex)
                                        {log.info(ex.getMessage());}
                                        theMove = holdMove ;
                                        getMemberHoldingsService().getMemberHoldingsMovementDAO().save(holdMove, sess);
				
					for(String key : holdings.keySet())
					{
						MemberHoldings curHoldings  = (MemberHoldings)holdings.get(key);
						
						curHoldings.setCreatedDate(new  java.sql.Timestamp(stkRecord.getCreatedDate().getTime()));
						curHoldings.setCreatedBy(stkRecord.getCreatedBy());
						
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
                        
      return theMove;
    }
    
   
    private boolean postMemberHoldings (MemberHoldingsMovement moved, Event currEvent, Map<String, Object> user)
    {
        log.info("Here inside WriteObjects....................posting for HoldingsMovement ....." + moved.getMember().getMemberNo());
        
        Date today = new Date();
                    //create other accounts for other stocks if specified
                   // if(member.getMemberHoldingsMovements()!=null && !member.getMemberHoldingsMovements().isEmpty())
                   // {

                    //post Transaction based on stock assigned on default   and register stock to cash book if used 

                    //List theMove = getMemberHoldingsService().getMemberHoldingsMovementDAO().findByEventAndMember(createEvt, member);
                    //Set theMove =  member.getMemberHoldingsMovements();

                    //for(Object move : theMove)
                    //{
                             boolean acctFlg = false;

                            //MemberHoldingsMovement moved = (MemberHoldingsMovement)move;

                            //double amtDouble = stockDetailService.evaluateStockBuyAmt(member, moved.getCompStockType(), moved.getMovementHoldings());
                            //do posting 
                            //String companyId, String branchId, String memCode, String prodCode,
                           //List acctList = getMemberDao().getMemberActiveAccounts(member.getCompany().getId().toString(), member.getBranch().getId().toString(), member.getMemberNo(), "", sess);
                            Map<String, Object> tranObj = siftUtil.buildTransactionObjects(moved, currEvent, tranCode, user, today, tranRef);
                            //TODO
                            //get memebr account for product and source fund acount
                    if(tranObj!=null)
                    {
                        log.info("I am not null tranObj  :: " + tranObj.values());
                        
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
                                //System.out.println("Here inside "  +  member.getAction() +  " failing product");
                                acctFlg= false;
                                e.printStackTrace();
                                //setMsg("Posting failed for Member Class :" + member.getSurname() + ":: " + moved.getCompStockType().getShortName() + " ::  value::" + moved.getMovementHoldings());
                                //throw new Exception("Posting Failed for member :" + member.getSurname()  + " on stock " + moved.getCompStockType().getShortName());
                            }
                    }

                    //}


                   // }
      return acctFlg;
    }

    public SiftFinancialUtil getSiftUtil() {
        return siftUtil;
    }

    public void setSiftUtil(SiftFinancialUtil siftUtil) {
        this.siftUtil = siftUtil;
    }

    public String getStockValidated() {
        return stockValidated;
    }

    public void setStockValidated(String stockValidated) {
        this.stockValidated = stockValidated;
    }

    public String getApprovalEvent() {
        return approvalEvent;
    }

    public void setApprovalEvent(String approvalEvent) {
        this.approvalEvent = approvalEvent;
    }

    public String getApprvdStockStat() {
        return apprvdStockStat;
    }

    public void setApprvdStockStat(String apprvdStockStat) {
        this.apprvdStockStat = apprvdStockStat;
    }

    public MemberHoldingsService getMemberHoldingsService() {
        return memberHoldingsService;
    }

    public void setMemberHoldingsService(MemberHoldingsService memberHoldingsService) {
        this.memberHoldingsService = memberHoldingsService;
    }
    
    
    
}
