package com.sift.financial.benefits.services;

import com.sift.financial.benefits.schedule.GenericDividendBuildTask;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.sql.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.sift.financial.member.*;
import com.sift.financial.member.services.MemberManageImpl;
import com.sift.financial.member.ws.client.Entrys;
import com.sift.financial.member.ws.client.Products;
import com.sift.financial.services.MailNotificationImpl;
import com.sift.financial.services.ReqManageService;
import com.sift.financial.utility.SiftFinancialUtil;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;


@Service
public class DividendManageImpl extends ReqManageService {
	
	private static final Log log = LogFactory.getLog(DividendManageImpl.class);
        
        @Autowired
	private BenefitService benefitService;
        
	@Autowired
	private SiftFinancialUtil siftUtil;

	private String msg;
        
        private CurrentuserdisplayImpl user;
        
        @Autowired
        @Value("${effRecordExDivDateDiff}")
        private int recordExDivDateDiff; 
        
        @Autowired
        private TaskExecutor taskExec;
   
        @Autowired
        private GenericDividendBuildTask buildTask;
               
        @Autowired
        @Value("${divPayableSchedule}")    
        private String theWork;
        
        @Autowired
        @Value("${divPostEntriesState}")    
        private String divPostEntriesState;
        
        @Autowired
        @Value("${DIVIDENDPYT}")
        private String tranCode; 
      
        @Autowired
        @Value("${divTranRef}")
        private String tranRef;
   

	@Override
	public Map<String, List> getReferenceData(String[] param) {
		// TODO Auto-generated method stub
		return null;
	}
        
	@Override
	public boolean addDetail(Object eventObject, StatusFlow flow,HttpServletRequest req) {
		// TODO Auto-generated method stub
		
		Session sess = null;
		Dividend divid = (Dividend)eventObject;
		
		ActivityLog actLog = null;
		Integer actionItem ;
		
		Status successStatus = getStatusDAO().findById(flow.getStatusSuccessId());
		divid.setStatus(successStatus);
		
		Date today = new Date();
                
		try {
			today = getCountryDate(today, divid.getCountryId().toString(), null);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		divid.setCreatedDate(new Timestamp(today.getTime()));
                divid.setPostEntries(divPostEntriesState);
                
		
		//divid.setDelFlg("N");
		
                boolean success= false;
		
		try{
		    log.info("saving Dividend details...1");
                    
			sess = getDividendDAO().getSessionFactory().openSession();
                        
			sess.beginTransaction();
                        
                        System.out.println("divid.getDivDateRecord() ::::" + divid.getDivDateRecord());
			System.out.println("divid.getCountryId() ::::" + divid.getCountryId());
                        System.out.println("recordExDivDateDiff ::::" + recordExDivDateDiff);
                        System.out.println("dividendType ::::" + divid.getDividendType().getDividendTypeName());
                        
			Date exDivdate  = benefitService.getExDivDate(divid.getDivDateRecord(), user.getCurrusercompany().getTimezone(),recordExDivDateDiff, divid.getCountryId().toString());
		
			divid.setExDividendDate(exDivdate);
			divid.setCreatedBy(user.getCurruser().getUserId());
                        
                        actLog = getLogObject(divid,flow,req);
			
			actionItem= getDividendDAO().saveWithId(divid, sess);
			
			actLog.setActionItem(actionItem.toString());
			actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
			actLog.setDescription(flow.getEvent().getEventName());
                        
                        sess.getTransaction().commit();
			success=true;
                        
                        buildTask.setDivInfo(divid);
                        buildTask.setTaskToRun(theWork);
                        buildTask.setEventShort(flow.getEvent().getEventShort());
                        log.info("Here inside apprDetail.................spurning build task with info " + divid.getDividendId() );
                        taskExec.execute(buildTask);
        
                        log.info("Here inside addDetail.................spurning task successful");
                        setMsg("Dividend record successfully created and dividend payable value in generation");

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			actLog.setActionItem(ActivityInterface.UNSUCCESSFULLNEWITEMID);
			actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
			actLog.setDescription(flow.getEvent().getEventName() + " failed due to :: " +  ex.getMessage());
			sess.getTransaction().rollback();;
			success=false;
			setMsg("Dividend record creation failed due to :: " + ex.getMessage());
		}
		finally
		{
			getActivityLogDAO().save(actLog);
			sess.close();
		}
		
		return success;
	}

	@Override
	public boolean editDetail(Object eventObject, StatusFlow flow,	HttpServletRequest req) 
	{
		// TODO Auto-generated method stub
		Session sess = null;
		Dividend divid = (Dividend)eventObject;
		
		Status successStatus = getStatusDAO().findById(flow.getStatusSuccessId());
		divid.setStatus(successStatus);
                
                ActivityLog actLog = null;
		
		Date today = new Date();
                
                divid.setModifiedBy(user.getCurruser().getUserId());
                divid.setModifiedDate(new java.sql.Timestamp(today.getTime()));
		
		try {
			today = getCountryDate(today, divid.getCountryId().toString(), "");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
                boolean success= false;
		
		try{
		    log.info("saving Dividend details...1");
			sess = getDividendDAO().getSessionFactory().openSession();
			sess.beginTransaction();
			
			Date exDivdate  = benefitService.getExDivDate(divid.getDivDateRecord(),user.getCurrusercompany().getTimezone(),recordExDivDateDiff,divid.getCountryId().toString());
		
			divid.setExDividendDate(exDivdate);
                        actLog = getLogObject(divid, flow, req);
                         System.out.println("dividendType ::::" + divid.getDividendType().getDividendTypeName());
                        //merge update on dividend
			getDividendDAO().merge(divid, sess);
                        
                        List theSchdule = getDividendScheduleDAO().findByDividendId(divid.getDividendId());
                        
                         //delete 
                        if(theSchdule !=null && theSchdule.size()>0)
                        {
                            getDividendScheduleDAO().deleteByDividendId(divid.getDividendId(), sess);
                        }
                 
			actLog.setActionItem(divid.getDividendId().toString());
			actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
			actLog.setDescription(flow.getEvent().getEventName());
			
			 sess.getTransaction().commit();
			 success=true;
                         
                        buildTask.setDivInfo(divid);
                        buildTask.setTaskToRun(theWork);
                        buildTask.setEventShort(flow.getEvent().getEventShort());
                        log.info("Here inside apprDetail.................spurning build task with info " + divid.getDividendId() );
                        taskExec.execute(buildTask);
        
                        log.info("Here inside apprDetail.................spurning task successful");
                        setMsg("Dividend record successfully updated and dividend payable value in generation");
			 
			
	
		}
		catch(Exception ex)
		{
			 ex.printStackTrace();
			 actLog.setActionItem(ActivityInterface.UNSUCCESSFULLNEWITEMID);
			 actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
			 actLog.setDescription(flow.getEvent().getEventName() + " failed due to :: " +  ex.getMessage());
			 sess.getTransaction().rollback();;
			 success=false;
			 setMsg("Dividend record update failed due to :: " + ex.getMessage());
		}
		finally
		{
			try
                        {getActivityLogDAO().save(actLog);
                        }
                        catch(Exception ex)
                        {}
                        
			sess.close();
		}
		
		return success;
	}

	@Override
	public boolean deleteDetail(Object eventObject, StatusFlow flow,
			HttpServletRequest req) {// TODO Auto-generated method stub
		Session sess = null;
		Dividend divid = (Dividend)eventObject;
		
		Status successStatus = getStatusDAO().findById(flow.getStatusSuccessId());
		divid.setStatus(successStatus);
                
                ActivityLog actLog = null;
		
		Date today = new Date();
                
                divid.setModifiedBy(user.getCurruser().getUserId());
                divid.setModifiedDate(new java.sql.Timestamp(today.getTime()));
		
		try {
			today = getCountryDate(today, divid.getCountryId().toString(), "");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
                boolean success= false;
		
		try{
		    log.info("saving Dividend details...1");
			sess = getDividendDAO().getSessionFactory().openSession();
			sess.beginTransaction();
                        
                        //update status on dividend
			getDividendDAO().merge(divid, sess);
                        
                        //delete from dividend schedule
                        getDividendScheduleDAO().deleteByDividendId(divid.getDividendId(), sess);
                        
			actLog.setActionItem(divid.getDividendId().toString());
			actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
			actLog.setDescription(flow.getEvent().getEventName());
			
			actLog = getLogObject(divid, flow, req);
			
			 sess.getTransaction().commit();
			 success=true;
			 
			 setMsg("Dividend record successfully cancelled");
	
		}
		catch(Exception ex)
		{
			 ex.printStackTrace();
			 actLog.setActionItem(ActivityInterface.UNSUCCESSFULLNEWITEMID);
			 actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
			 actLog.setDescription(flow.getEvent().getEventName() + " failed due to :: " +  ex.getMessage());
			 sess.getTransaction().rollback();;
			 success=false;
			 setMsg("Dividend record cancellation failed due to :: " + ex.getMessage());
		}
		finally
		{
			getActivityLogDAO().save(actLog);
			sess.close();
		}
		
		return success;
	}

	@Override
	public Object readDetail(Object eventObject) {
            
            Dividend targetObj = (Dividend)eventObject;
	   return  getDividendDAO().findByIdAndCompany(targetObj.getDividendId(), user.getCurruser().getCompanyid()); 

	}

	@Override
	public List<Object> listDetail(Object eventObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> listDetail(Object eventObject, String status) {
            
          return  getDividendDAO().getDividendListByStatus(((Integer)eventObject).toString(), status, status);
	}

	@Override
	public boolean apprDetail(Object eventObject, StatusFlow flow,
			HttpServletRequest req) 
        {
	
            Status nextStat = null;
            log.info("Here inside apprDetail....................Dividend");
            boolean returnVal = false;
            ActivityLog actLog = null;
            Dividend dividend = null;
            Date today = new Date();
            Session sess =  null;

               try {
                       today = getCountryDate(today, user.getCurrusercompany().getCountryId(),"");

               } catch (ParseException e) {
                       // TODO Auto-generated catch block
                       e.printStackTrace();
               }
         
                 
        try
        {
             sess = getDividendDAO().getSessionFactory().openSession();
             
               dividend = (Dividend)eventObject;
                
                Map<String, String> result = canApproveRequest(dividend.getCreatedBy(),dividend.getModifiedBy(),user.getCurruser().getUserId());
        
                actLog = getLogObject(dividend,flow,req);
                actLog.setActionItem(dividend.getDividendId().toString());
                actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
                actLog.setDescription(flow.getEvent().getEventName());
         
                if(!Boolean.parseBoolean(result.get("STATE")))
                {
                    //throw new Exception(result.get("MSG"));
                }
                
              
	        sess.beginTransaction();
       
                    if(dividend.getAction().equals("APPROVE"))
                    {
                            System.out.println("Here inside "  +  dividend.getAction());
                            nextStat = getStatusDAO().findById(flow.getStatusSuccessId());
                            dividend.setStatus(nextStat);
                            
   
                            actLog.setActionItem(dividend.getDividendId().toString());
                            actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
                            actLog.setDescription(flow.getEvent().getEventName());
                            
                            //post entries if required
                        boolean  postedFlg = false;
                            //########################################################
                            if(dividend.getPostEntries().equalsIgnoreCase("Y"))
                            {
                                System.out.println("Dividend Requires Posting");
                                Map<String, Object> tranObj = siftUtil.buildTransactionObjects(dividend, flow.getEvent(), tranCode, user, today, tranRef);
								
                                if(tranObj!=null)
                                {

                                    Entrys theEntrys = null; 
                                        try
                                        {
                                         theEntrys=siftUtil.getTransaction((Map<String, Object>)tranObj.get(TransactionInterface.HEADERINFO), (List<Map<String, Object>>)tranObj.get(TransactionInterface.BODYINFO), user, tranRef);
                                         //System.out.println("theEntrys :: " + theEntrys);
                                         Object restResult =   doRestService(theEntrys, ActivityInterface.SERVTYPEPOSTING);
                                         System.out.println("restResult :: " + restResult);
                                            if(restResult instanceof Entrys)
                                            {
                                              //System.out.println("restResult instanceof Entrys :: " + restResult);
                                              theEntrys = (Entrys)restResult;
                                            }
                                            else if(restResult instanceof String)
                                            {
                                             System.out.println("Posting result here :"   + result);
                                            }

                                            postedFlg= true;
                                            System.out.println("Posting Succesful");

                                        } catch (Throwable e) 
                                        {
                                        // TODO Auto-generated catch block
                                            postedFlg = false;
                                            e.printStackTrace();
                                            setMsg("Posting Failed for Dividend entries");
                                            throw new Exception("Posting Failed for Dividend entries ");
                                        }

                                }
                            }
                            else
                            {
                            
                                postedFlg= true;
                            }
                            

                    }
                    else  if(dividend.getAction().equals("REJECT"))
                    {

                       nextStat = getStatusDAO().findById(flow.getStatusFailId());
                       dividend.setStatus(nextStat);
                       
                        actLog.setActionItem(dividend.getDividendId().toString());
                        actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
                        actLog.setDescription(flow.getEvent().getEventName());
                    }
                    
                    dividend.setApprovedBy(user.getCurruser().getUserId());
                    dividend.setApprovedDate(new Timestamp(today.getTime()));
                    getDividendDAO().merge(dividend,sess);
                    sess.getTransaction().commit();
                    returnVal=true; 

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            try
            {
             sess.getTransaction().rollback();
             actLog.setActionItem(dividend.getDividendId().toString());
             actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
             actLog.setDescription(flow.getEvent().getEventName() + " failed due to :: " +  ex.getMessage());
            }
            catch(Exception excatch)
            {
                excatch.printStackTrace();
            }
            finally
            {
                getActivityLogDAO().save(actLog);
            }
             setMsg("Approval Failed due to :: " +  ex.getMessage());
             returnVal = false;
        }
        finally
        {
            getActivityLogDAO().save(actLog);
            sess.close();
        }
  
        return returnVal;
	}

	@Override
	public boolean apprBulkDetail(Object eventObject, StatusFlow flow,
			HttpServletRequest req) {
		// TODO Auto-generated method stub
		return false;
	}
        

	public boolean reBuildDiv(Object eventObject, StatusFlow flow,HttpServletRequest req) {
            
		Session sess = null;
		Dividend divid = (Dividend)eventObject;
		
		ActivityLog actLog = null;
		Integer actionItem ;
		
		Status successStatus = getStatusDAO().findById(flow.getStatusSuccessId());
		divid.setStatus(successStatus);
		
		Date today = new Date();
                
		try {
			today = getCountryDate(today, user.getCurrusercompany().getCountryId(), null);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
                boolean success= false;
		
		try{
		    log.info("rebuilding Dividend details...1");
                    
			sess = getDividendDAO().getSessionFactory().openSession();
                        
			sess.beginTransaction();
                        
                        actLog = getLogObject(divid,flow,req);
                        
                        List theSchdule = getDividendScheduleDAO().findByDividendId(divid.getDividendId());
                        
                         //delete 
                        if(theSchdule !=null && theSchdule.size()>0)
                        {
                            getDividendScheduleDAO().deleteByDividendId(divid.getDividendId(), sess);
                        }

			getDividendDAO().merge(divid, sess);
			
			actLog.setActionItem(divid.getDividendId().toString());
			actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
			actLog.setDescription(flow.getEvent().getEventName());
                        
                        sess.getTransaction().commit();
			success=true;
                        
                        buildTask.setDivInfo(divid);
                        buildTask.setTaskToRun(theWork);
                        buildTask.setEventShort(flow.getEvent().getEventShort());
                        log.info("Here inside reBuild.................spurning build task with info " + divid.getDividendId() );
                        taskExec.execute(buildTask);
                        log.info("Here inside reBuild.................spurning task successful");
                        
                        setMsg("Dividend Payable regeneration succesfully initiated");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			actLog.setActionItem(ActivityInterface.UNSUCCESSFULLNEWITEMID);
			actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
			actLog.setDescription(flow.getEvent().getEventName() + " failed due to :: " +  ex.getMessage());
			sess.getTransaction().rollback();;
			success=false;
			setMsg("Dividend Payable regeneration failed due to :: " + ex.getMessage());
		}
		finally
		{
			getActivityLogDAO().save(actLog);
			sess.close();
		}
		
		return success;
        }

	@Override
	public boolean sendSuccessNotification(String comment, Object eventObject,
			String mailTempl, MailNotificationImpl mailNotificationService) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendFailureNotification(String comment, Object eventObject,
			String mailTempl, MailNotificationImpl mailNotificationService) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendSuccessAuthNotification(String comment,
			Object eventObject, String mailTempl,
			MailNotificationImpl mailNotificationService) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendFailureAuthNotification(String comment,
			Object eventObject, String mailTempl,
			MailNotificationImpl mailNotificationService) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public BenefitService getBenefitService() {
		return benefitService;
	}

	public void setBenefitService(BenefitService benefitService) {
		this.benefitService = benefitService;
	}


	@Override
	public ActivityLog getLogObject(Object eventObject, StatusFlow flow,
			HttpServletRequest req) {
		// TODO Auto-generated method stub
		
		Dividend div = (Dividend)eventObject;
                
		System.out.println("flow.getEvent()::: Dividend " + flow.getEvent());
                
		ActivityLog log = new ActivityLog();
                 Date today  = new Date();
		
		log.setAction(div.getAction());
		log.setActionDate(new java.sql.Timestamp(today.getTime()));
		log.setEvent(flow.getEvent());
		log.setBranchId(div.getBranchId());
		log.setCompanyId(div.getCompanyId());
		log.setIpaddress(siftUtil.getUserAddress(req));
                log.setUsername(user.getCurruser().getUserId());
		
		return log;
	}


	public SiftFinancialUtil getSiftUtil() {
		return siftUtil;
	}


	public void setSiftUtil(SiftFinancialUtil siftUtil) {
		this.siftUtil = siftUtil;
	}

    public int getRecordExDivDateDiff() {
        return recordExDivDateDiff;
    }

    public void setRecordExDivDateDiff(int recordExDivDateDiff) {
        this.recordExDivDateDiff = recordExDivDateDiff;
    }

    public CurrentuserdisplayImpl getUser() {
        return user;
    }

    public void setUser(CurrentuserdisplayImpl user) {
        this.user = user;
    }
        
        

}
