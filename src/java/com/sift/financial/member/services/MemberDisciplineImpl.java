/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.services;

import com.sift.financial.member.ActivityInterface;
import com.sift.financial.member.ActivityLog;
import com.sift.financial.member.AddressEntries;
import com.sift.financial.member.AddressType;
import com.sift.financial.member.CompanyMemberIdentifier;
import com.sift.financial.member.Event;
import com.sift.financial.member.Member;
import com.sift.financial.member.MemberArchive;
import com.sift.financial.member.MemberHoldings;
import com.sift.financial.member.MemberHoldingsMovement;
import com.sift.financial.member.Status;
import com.sift.financial.member.StatusFlow;
import com.sift.financial.member.StockInterface;
import com.sift.financial.member.TransactionInterface;
import com.sift.financial.member.ws.client.Accnowbs;
import com.sift.financial.member.ws.client.Entrys;
import com.sift.financial.services.MailNotificationImpl;
import com.sift.financial.services.ReqManageService;
import com.sift.financial.stock.services.MemberHoldingsService;
import com.sift.financial.utility.SiftFinancialUtil;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author baydel200
 */
public class MemberDisciplineImpl extends ReqManageService {
    
    private static final Log log = LogFactory.getLog(MemberDisciplineImpl.class);
    
    @Autowired
    private SiftFinancialUtil siftUtil;
     
    private CurrentuserdisplayImpl user;
    
    private String msg;
    
    @Autowired
    @Value("${STOCKREPURCH}")
    private String tranCode; 

    @Autowired
    @Value("${regTranRef}")
    private String tranRef;
    
     @Autowired
    private  MemberHoldingsService memberHoldingsService;

    @Override
    public Map<String, List> getReferenceData(String[] param) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addDetail(Object eventObject, StatusFlow flow, HttpServletRequest req) {
    // TODO Auto-generated method stub
       
		Session sess = null;
		Member member = (Member)eventObject;
		Status successStatus = getStatusDAO().findById(flow.getStatusSuccessId());
		member.setStatus(successStatus);
                member.setModifiedBy(user.getCurruser().getUserId());
		
		ActivityLog actLog = null;
		Integer actionItem ;
		
		Date today = new Date();
		
		try {
			//today = getCountryDate(today, member.getCompany().getCountries().getId().toString(), "");
                        today = getCountryDate(today, user.getCurrusercompany().getCountryId(),"");
                        
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
                boolean success= false;
		
		try{
		    log.info("saving Member details...1");
			sess = getMemberDao().getSessionFactory().openSession();
			sess.beginTransaction();
                        
                        
                        actLog = getLogObject(member,flow,req);
			actLog.setActionItem(member.getMemberId().toString());
			actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
			actLog.setDescription(flow.getEvent().getEventName());
                        
                        //write member and retrieve ID
                        member.setModifiedDate(actLog.getActionDate());
			getMemberDao().merge(member, sess);
		
			sess.getTransaction().commit();
			success=true;
			 
                        setMsg("Action successfully initiated. Now awaiting approval.");
	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			try
                        {actLog.setActionItem(member.getMemberId().toString());
			actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
			actLog.setDescription(flow.getEvent().getEventName() + " failed due to :: " +  ex.getMessage());
                        }
                        catch(Exception excatch)
                        {
                            excatch.printStackTrace();
                        }
			sess.getTransaction().rollback();;
			success=false;
			setMsg("Action initiaition failed.Due to :: " + ex.getMessage());
                        
		}
		finally
		{
			getActivityLogDAO().save(actLog);
			sess.close();
		}
		
		return success;
    }

    @Override
    public boolean editDetail(Object eventObject, StatusFlow flow, HttpServletRequest req) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteDetail(Object eventObject, StatusFlow flow, HttpServletRequest req) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object readDetail(Object eventObject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object> listDetail(Object eventObject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object> listDetail(Object eventObject, String status) {

            List<Object> requests= getMemberDao().getMemberListByStatus(user.getCurruser().getCompanyid(), status);
            return requests;
            
    }

    @Override
    public boolean apprDetail(Object eventObject, StatusFlow flow, HttpServletRequest req) {
      
		
		boolean returnVal = false;
		Session sess = null;
		Member member = (Member)eventObject;
		//move status
		member.setModifiedBy(user.getCurruser().getUserId());
		ActivityLog actLog = null;
		//Integer actionItem ;
		actLog = getLogObject(member,flow,req);
		actLog.setActionItem(member.getMemberId().toString());
		
		Date today = new Date();
		try
		{
			log.info("saving Member details...1");
			sess = getMemberDao().getSessionFactory().openSession();
			sess.beginTransaction();
                        today = getCountryDate(today, user.getCurrusercompany().getCountryId(),"");
                        member.setModifiedDate(actLog.getActionDate());
                        
			if(member.getAction().startsWith(ActivityInterface.ACTIONAPPROVE))
			{

				log.info("Inside Action Approve ..............");
				
				Status successStatus = getStatusDAO().findById(flow.getStatusSuccessId());
				member.setStatus(successStatus);
										
                                if(member.getAction().equals("APPROVE EXIT"))  
                                {
                                    List<MemberHoldings> theHoldings = getMemberHoldingsDAO().getAvailableHoldings(member.getMemberId(), member.getCompany().getId());
                                    
                                    Set moveMents =  memberHoldingsService.buildHoldingsMovement(theHoldings, flow.getEvent(), member, today, user.getCurruser().getUserId());
                                    
                                    if(moveMents!=null && !moveMents.isEmpty())
                                    {
                                        // Zeroise member holding and pass entries
                                        //post Transaction based on stock assigned on default   and register stock to cash book if used 
                                        //Set theMove =  member.getMemberHoldingsMovements();
                                        
                                        for(Object move : moveMents)
                                        {
                                                boolean acctFlg = false;

                                                MemberHoldingsMovement moved = (MemberHoldingsMovement)move;
                                                
                                                
                                                //Only pay back if it is not register stock
                                                if(moved.getCompStockType().getRegisterStock().equals("N"))
                                                {

                                                    Map<String, Object> tranObj = siftUtil.buildTransactionObjects(moved, flow.getEvent(), tranCode, user, today, tranRef);
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
                                                                setMsg("Posting failed for Member Class :" + member.getSurname() + ":: " + moved.getCompStockType().getShortName() + " ::  value::" + moved.getMovementHoldings());
                                                                throw new Exception("Posting Failed for member :" + member.getSurname()  + " on stock " + moved.getCompStockType().getShortName());
                                                            }
                                                    }

                                                 }
                                                
                                                getMemberHoldingsMovementDAO().save(moved, sess);
                                                
                                            }
                                    }
                                    
                                   // MemberArchive archiveObj =  
                                    getMemberArchiveDAO().save(getArchiveObject(member), sess);
                                    member.setEmailAdd1("XXXXXXX");
                                    member.setEmailAdd2(null);
                                    member.setEmailAdd3(null);
                                    member.setDelFlg("Y");
                                    member.setDelDate(today);
                                }
                          
                                if(member.getAction().equals("APPROVE RESIGN"))  
                                {
                                    List<MemberHoldings> theHoldings = getMemberHoldingsDAO().getAvailableHoldings(member.getMemberId(), member.getCompany().getId());
                                    Set moveMents =  memberHoldingsService.buildHoldingsMovement(theHoldings, flow.getEvent(), member, today, user.getCurruser().getUserId());
                                    if(moveMents!=null && !moveMents.isEmpty())
                                    {
                                        // Zeroise member holding and pass entries
                                        //post Transaction based on stock assigned on default   and register stock to cash book if used 
                                        //Set theMove =  member.getMemberHoldingsMovements();
                                        for(Object move : moveMents)
                                        {
                                                boolean acctFlg = false;
                                                 MemberHoldingsMovement moved = (MemberHoldingsMovement)move;
                                                
                                                //Only pay back if it is not register stock
                                                if(moved.getCompStockType().getRegisterStock().equals("N"))
                                                {
                                                  
                                                    Map<String, Object> tranObj = siftUtil.buildTransactionObjects(moved, flow.getEvent(), tranCode, user, today, tranRef);
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
                                                                setMsg("Posting failed for Member Class :" + member.getSurname() + ":: " + moved.getCompStockType().getShortName() + " ::  value::" + moved.getMovementHoldings());
                                                                throw new Exception("Posting Failed for member :" + member.getSurname()  + " on stock " + moved.getCompStockType().getShortName());
                                                            }
                                                    }

                                                 }
                                                
                                                getMemberHoldingsMovementDAO().save(moved, sess);
                                                
                                            }
                                    }
                                    
                                   // MemberArchive archiveObj =  
                                    getMemberArchiveDAO().save(getArchiveObject(member), sess);
                                    member.setEmailAdd1("XXXXXXX");
                                    member.setEmailAdd2(null);
                                    member.setEmailAdd3(null);
                                    member.setDelFlg("Y");
                                    member.setDelDate(today);
                                }
                                
                                
                                actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
                                actLog.setDescription(flow.getEvent().getEventName());
                                getMemberDao().merge(member, sess);

                                sess.getTransaction().commit();
                                returnVal=true;
                                setMsg("Action successfully completed.....");

	
			}
			else if(member.getAction().startsWith(ActivityInterface.ACTIONREJECT))
			{
				log.info("Inside Action Reject ..............");
				
				Status failStatus = getStatusDAO().findById(flow.getStatusFailId());
				member.setStatus(failStatus);
				
				//clean up Objects
				actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
				actLog.setDescription(flow.getEvent().getEventName());
				
				//member
				getMemberDao().merge(member, sess);
				
				log.info("Aboout Commiting..............");
				  log.info("Reject action specified for the approval process........");
				sess.getTransaction().commit();
				returnVal=true;
                                setMsg("Action successfully completed.....");
	
			}
                        else if(member.getAction().equalsIgnoreCase("REINSTATE"))
			{
				log.info("Inside Action Reject ..............");
				
				Status successStatus = getStatusDAO().findById(flow.getStatusSuccessId());
				member.setStatus(successStatus);
				
				//clean up Objects
				actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
				actLog.setDescription(flow.getEvent().getEventName());
				
				//member
				getMemberDao().merge(member, sess);
				
				log.info("Aboout Commiting..............");
                                log.info("Reinstate action specified for the approval process........");
				//commit
				sess.getTransaction().commit();
				returnVal=true;
                                setMsg("Action successfully completed.....");
	
			}
			else
			{
				actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
				actLog.setDescription(flow.getEvent().getEventName() + " failed due to Could not get action");

				log.info("No action specified for the approval process........");
				throw new Exception("No action specified for the approval process........");
			}
			
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
			actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
			actLog.setDescription(flow.getEvent().getEventName() + " failed due to ::" + exp.getMessage());

			sess.beginTransaction().rollback();
			returnVal = false;
			setMsg(exp.getMessage());
		}
		finally
		{
			getActivityLogDAO().save(actLog);
			sess.close();
		}
	

		return returnVal;
    }

    @Override
    public boolean apprBulkDetail(Object eventObject, StatusFlow flow, HttpServletRequest req) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean sendSuccessNotification(String comment, Object eventObject, String mailTempl, MailNotificationImpl mailNotificationService) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean sendFailureNotification(String comment, Object eventObject, String mailTempl, MailNotificationImpl mailNotificationService) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean sendSuccessAuthNotification(String comment, Object eventObject, String mailTempl, MailNotificationImpl mailNotificationService) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean sendFailureAuthNotification(String comment, Object eventObject, String mailTempl, MailNotificationImpl mailNotificationService) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    @Override
    public ActivityLog getLogObject(Object eventObject, StatusFlow flow, HttpServletRequest req) {
		
            
            Member stk = (Member)eventObject;
            SiftFinancialUtil siftUtil  =  new SiftFinancialUtil();
            ActivityLog log = new ActivityLog();
            
            System.out.println("here Inside Logging");
            Date today  = new Date();
           
            try
            {
                
                today = getCountryDate(today, user.getCurrusercompany().getCountryId(),"");
                System.out.println("here Inside Logging b4 date");
               // today = getCountryDate(today,stk.getCompany().getCountries().getId().toString(), null);
                 System.out.println("here Inside Logging after date");
		log.setAction(stk.getAction());
		log.setActionDate(new  java.sql.Timestamp(today.getTime()));
		log.setEvent(flow.getEvent());
		log.setBranchId(user.getCurruser().getBranch());
		log.setCompanyId(user.getCurruser().getCompanyid());
		log.setIpaddress(siftUtil.getUserAddress(req));
                log.setUsername(user.getCurruser().getUserId());
                
             }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
		
		return log;
    }

    public SiftFinancialUtil getSiftUtil() {
        return siftUtil;
    }

    public void setSiftUtil(SiftFinancialUtil siftUtil) {
        this.siftUtil = siftUtil;
    }

    public CurrentuserdisplayImpl getUser() {
        return user;
    }

    public void setUser(CurrentuserdisplayImpl user) {
        this.user = user;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
   
}
