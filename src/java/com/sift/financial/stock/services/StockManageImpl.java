package com.sift.financial.stock.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sift.financial.member.ActivityInterface;
import com.sift.financial.member.ActivityLog;
import com.sift.financial.member.StatusFlow;
import com.sift.financial.services.MailNotificationImpl;
import com.sift.financial.services.ReqManageService;
import com.sift.gl.dao.CurrentuserdisplayImpl;

import org.hibernate.*;

import com.sift.financial.member.*;
import com.sift.financial.member.ws.client.*;
import com.sift.financial.utility.SiftFinancialUtil;
import java.util.Iterator;


public class StockManageImpl extends ReqManageService {

	private String defProdType;
        
        private String msg;
        
        ///private SiftFinancialUtil siftUtil;
        
        private Map<String,Object> reqInfo;
       
        private CurrentuserdisplayImpl user;
	

	@Override
	public Map<String, List> getReferenceData(String[] param) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sift.financial.services.ReqManageService#addDetail(java.lang.Object, com.sift.financial.member.StatusFlow, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public boolean addDetail(Object eventObject, StatusFlow flow,
			HttpServletRequest req) {
		// TODO Auto-generated method stub
		
		CompStockType  theStock = (CompStockType)eventObject;
		
		boolean success = false;
		Session sess =  null;
		 Date today = new Date();
                 
                ActivityLog actLog = null;
		Integer actionItem ;
                
		try
		{
			
			today = getCountryDate(today,theStock.getCompany().getCountries().getId().toString(), null);
			sess = getCompStockTypeDAO().getSessionFactory().openSession();
			sess.beginTransaction();
			Status nextStat = getStatusDAO().findById(flow.getStatusSuccessId());
			theStock.setStatus(nextStat);
			theStock.setCreatedDate(today);
			
			boolean acctFlg = true;
                        String theShortName = null;
                        
                        //create shortName for Stock
                        try
                        {
                             theShortName = getCompStockTypeDAO().getNewShortName(theStock.getCompany().getId().toString(), theStock.getCompStockName());
                             System.out.println("Printing Sirtname here : " + theShortName);
                        }
                        catch(Exception ex)
                        {
                          ex.printStackTrace();
                          acctFlg = false; 
                          theShortName = null;
                        }
                        
			if(acctFlg)
			{
				theStock.setShortName(theShortName);
                                
                                theStock.setCompStockName(theStock.getCompStockName().toUpperCase());
				//now save stock
				getCompStockTypeDAO().save(sess, theStock);
				
					List pptyList = getCompStockPropertyDAO().findByProperty("delFlg", "N");
					
					for(Object pptObj  : pptyList)
					{
						
						CompStockProperty stkPPty=  (CompStockProperty) pptObj;
						
						if(req.getParameter(stkPPty.getStockPptyName())!=null)
						{				
							//&& !req.getParameter(stkPPty.getStockPptyName()).equalsIgnoreCase("")
							
							CompStockTypeDetail detail = new CompStockTypeDetail();
							
							if(stkPPty.getStockPptyDesc().equals("TEXT"))
							{
								
								if(!req.getParameter(stkPPty.getStockPptyName()).equalsIgnoreCase(""))
								{
									detail.setCompStockPptyVal(req.getParameter(stkPPty.getStockPptyName()));
									
									System.out.println("stkPPty "   + stkPPty.getStockPptyDisplay());
									System.out.println("stkPPty "   +  req.getParameter(stkPPty.getStockPptyName()));
								}
								else
								{
									continue;
								}
							}
							else
							{
								//detail.setCompStockPptyVal("Y");
								detail.setCompStockPptyVal(req.getParameter(stkPPty.getStockPptyName()));
								System.out.println("stkPPty "   + stkPPty.getStockPptyDisplay());
								System.out.println("have been set to: "   + detail.getCompStockPptyVal());
								
							}
							
							detail.setCompStockType(theStock);
							System.out.println("Ok------::: "   +  req.getParameter(stkPPty.getStockPptyName()));
							detail.setCompStockProperty((CompStockProperty)getCompStockPropertyDAO().findByStockPptyName(stkPPty.getStockPptyName()).get(0));
							getCompStockTypeDetailDAO().save(sess, detail);
						}
					}
                                        actLog = getLogObject(theStock,flow,req);
                                        actLog.setActionItem(theStock.getCompStockTypeId().toString());
                                        actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
                                        actLog.setDescription(flow.getEvent().getEventName());
					sess.getTransaction().commit();
					success=true;
					 
				}
				else
				{
					actLog.setActionItem(ActivityInterface.UNSUCCESSFULLNEWITEMID);
                                        actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
                                        actLog.setDescription(flow.getEvent().getEventName() + " failed. shortName could not be retrieved");
					sess.getTransaction().rollback();;
					success=false;
					
				}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
                        //set
                        try
                        {
                        actLog.setActionItem(ActivityInterface.UNSUCCESSFULLNEWITEMID);
			actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
			actLog.setDescription(flow.getEvent().getEventName() + " failed due to :: " +  ex.getMessage());
			sess.getTransaction().rollback();
                        }
                        catch(Exception ignore)
                        {
                            ignore.printStackTrace();
                        }
			success=false;
		}
		finally
		{
                        getActivityLogDAO().save(actLog);
			try
                        {
                            sess.close();
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }
		}
		
		return success;
	}

	@Override
	public boolean editDetail(Object eventObject, StatusFlow flow,HttpServletRequest req) {
            
          CompStockType  theStock = (CompStockType)eventObject;
		
		boolean success = false;
		Session sess =  null;
		 Date today = new Date();
                 
                ActivityLog actLog = null;
		//Integer actionItem ;
                
		try
		{
			
			today = getCountryDate(today,theStock.getCompany().getCountries().getId().toString(), null);
			sess = getCompStockTypeDAO().getSessionFactory().openSession();
			sess.beginTransaction();
			Status nextStat = getStatusDAO().findById(flow.getStatusSuccessId());
			theStock.setStatus(nextStat);
			theStock.setModifiedDate(new java.sql.Timestamp(today.getTime()));
                        //theStock.setModifiedBy(defProdType);
			
			boolean acctFlg = true;
                                        
			if(acctFlg)
			{
			   
                            //delete details
                            for(Object oldDetail : theStock.getCompStockTypeDetails())
                            {
                                CompStockTypeDetail compDet = (CompStockTypeDetail)oldDetail;
                                getCompStockTypeDetailDAO().delete(compDet, sess);
                            }
                            
                            //sess.flush();
                            
                                //now merge stock
				getCompStockTypeDAO().merge(theStock, sess);
                                				
					List pptyList = getCompStockPropertyDAO().findByProperty("delFlg", "N");
					
					for(Object pptObj  : pptyList)
					{
						
						CompStockProperty stkPPty=  (CompStockProperty) pptObj;
						
						if(req.getParameter(stkPPty.getStockPptyName())!=null && !req.getParameter(stkPPty.getStockPptyName()).equals(""))
						{				
							//&& !req.getParameter(stkPPty.getStockPptyName()).equalsIgnoreCase("")
							
							CompStockTypeDetail detail = new CompStockTypeDetail();
							
							if(stkPPty.getStockPptyDesc().equals("TEXT"))
							{
								
								if(!req.getParameter(stkPPty.getStockPptyName()).equalsIgnoreCase(""))
								{
									detail.setCompStockPptyVal(req.getParameter(stkPPty.getStockPptyName()));
									
									//System.out.println("stkPPty "   + stkPPty.getStockPptyDisplay());
									//System.out.println("stkPPty "   +  req.getParameter(stkPPty.getStockPptyName()));
								}
								else
								{
									continue;
								}
							}
							else
							{
								//detail.setCompStockPptyVal("Y");
								detail.setCompStockPptyVal(req.getParameter(stkPPty.getStockPptyName()));
								//System.out.println("stkPPty "   + stkPPty.getStockPptyDisplay());
								//System.out.println("have been set to: "   + detail.getCompStockPptyVal());
							}
							
							detail.setCompStockType(theStock);
							//System.out.println("Ok------::: "   +  req.getParameter(stkPPty.getStockPptyName()));
							detail.setCompStockProperty((CompStockProperty)getCompStockPropertyDAO().findByStockPptyName(stkPPty.getStockPptyName()).get(0));
							getCompStockTypeDetailDAO().save(sess, detail);
						}
					}
                                        actLog = getLogObject(theStock,flow,req);
                                        actLog.setActionItem(theStock.getCompStockTypeId().toString());
                                        actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
                                        actLog.setDescription(flow.getEvent().getEventName());
					sess.getTransaction().commit();
					success=true;
					 
				}
				else
				{
					actLog.setActionItem(theStock.getCompStockTypeId().toString());
                                        actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
                                        actLog.setDescription(flow.getEvent().getEventName() + " failed. shortName could not be retrieved");
					sess.getTransaction().rollback();
					success=false;
					
				}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
                        //set
                        try
                        {
                        actLog.setActionItem(theStock.getCompStockTypeId().toString());
			actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
			actLog.setDescription(flow.getEvent().getEventName() + " failed due to :: " +  ex.getMessage());
			sess.getTransaction().rollback();
                        }
                        catch(Exception ignore)
                        {
                            //ignore.printStackTrace();
                        }
			success=false;
		}
		finally
		{
                        getActivityLogDAO().save(actLog);
			try
                        {
                            sess.close();
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }
		}
		
		return success;
	}

	@Override
	public boolean deleteDetail(Object eventObject, StatusFlow flow,
			HttpServletRequest req) {
		// TODO Auto-generated method stub
		 // log.debug("deleting CompStockTypeDetail instance");
               Session sess =  null;
               boolean success = false;
               ActivityLog actLog = null;
               
               CompStockType  theStock = (CompStockType)eventObject;
                
		try {
                    sess = getCompStockTypeDAO().getSessionFactory().openSession();
			sess.beginTransaction();
			//sess.delete(" from CompStockTypeDetail where compStockType.compStockTypeId =" + compStockTypeId);
                        System.out.println("theStock.getCompStockTypeId() :: " + theStock.getCompStockTypeId());
                        //getCompStockTypeDetailDAO().delete(theStock.getCompStockTypeId(), sess);
                        // getCompStockTypeDAO().delete(theStock);
                        //getCompStockTypeDAO().delete2(theStock.getCompStockTypeId(), sess);
                       // getCompStockTypeDAO().delete3(theStock, sess);
                        //sess.delete(" from CompStockTypeDetail where compStockType.compStockTypeId =" + theStock.getCompStockTypeId());
			
                       getCompStockTypeDAO().deleteByStockId(theStock.getCompStockTypeId(), sess);
                     //log.debug("delete successful");
                        System.out.println("Seems i have deleted this.");
                        
                        actLog = getLogObject(theStock,flow,req);
                        actLog.setActionItem(theStock.getCompStockTypeId().toString());
                        actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
                        actLog.setDescription(flow.getEvent().getEventName());
                        sess.getTransaction().commit();
                        success = true;
                        
		} catch (RuntimeException ex) {
			//log.error("delete failed", re);
			//throw re;
                        ex.printStackTrace();
                        try
                        {
                        actLog.setActionItem(theStock.getCompStockTypeId().toString());
			actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
			actLog.setDescription(flow.getEvent().getEventName() + " failed due to :: " +  ex.getMessage());
			//sess.getTransaction().rollback();
                        }
                        catch(Exception ignore)
                        {
                            ignore.printStackTrace();
                        }
			success=false;
		}finally
		{
                        getActivityLogDAO().save(actLog);
			try
                        {
                            sess.close();
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }
		}
		
                
		return success;
	}

	@Override
	public Object readDetail(Object eventObject) 
        {
           CompStockType targetObj = (CompStockType)eventObject;
	   return  getCompStockTypeDAO().findByIdAndCompany(targetObj.getCompStockTypeId(), (Integer)reqInfo.get(ActivityInterface.REQINFOCOMP)); 
	}
        

	@Override
	public List<Object> listDetail(Object eventObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> listDetail(Object eventObject, String status) {
		// TODO Auto-generated method stub
	  ///String companyId=user.getCurruser().getCompanyid().toString();
           // return getCompStockTypeDAO().getStockListByCompany((String)reqInfo.get(ActivityInterface.REQINFOCOMP), status);
            return null;
	}

	@Override
	public boolean apprDetail(Object eventObject, StatusFlow flow,	HttpServletRequest req) {
            		
		CompStockType  theStock = (CompStockType)eventObject;
		
		boolean success = false;
		Session sess =  null;
		 Date today = new Date();
                 
                ActivityLog actLog = null;
                
		System.out.println("Here inside "  +  theStock.getAction());		 
		try
		{
			System.out.println("Here inside 1 "  +  theStock.getAction());
			sess = getCompStockTypeDAO().getSessionFactory().openSession();
			sess.beginTransaction();
                        //theStock.setCreatedDate(today);
                         Status nextStat = null;
                         System.out.println("Here inside 2 "  +  theStock.getAction());
                         actLog = getLogObject(theStock,flow,req);
                         System.out.println("Here inside 3 "  +  theStock.getAction());
                         
                         theStock.setApprovedBy(actLog.getUsername());
                         theStock.setApprovedDate(actLog.getActionDate());
                            
                        if(theStock.getAction().equals("APPROVE"))
                        {
                            System.out.println("Here inside "  +  theStock.getAction());
                                    nextStat = getStatusDAO().findById(flow.getStatusSuccessId());
                                    theStock.setStatus(nextStat);
                                    
                                    boolean acctFlg = false;
                                    // create product for Stock -check if Trading product is NEW or and Existing Product.

                                    if(theStock.getStockAcctProd()!=null && theStock.getStockAcctProd().equals("NEW"))
                                    {
                                            //Build New Prodcut Object
                                            Products theAcct = new Products();
                                            int hasInt= 0;
                                            String intVal = "0";
                                            Double penalty = 0.0;
                                            
                                            Iterator ComIter = theStock.getCompStockTypeDetails().iterator();
                                            
                                            while(ComIter.hasNext())
                                            {
                                            
                                               CompStockTypeDetail compDet = (CompStockTypeDetail)ComIter.next();
                                                
                                               if(compDet.getCompStockProperty().getStockPptyName().equals("PRODHASINT") && compDet.getCompStockPptyVal().equals("Y"))
                                               {
                                                     hasInt = 1;
                                               }
                                               
                                               if(compDet.getCompStockProperty().getStockPptyName().equals("PRODINTVAL") && hasInt==1)
                                               {
                                                     intVal = compDet.getCompStockPptyVal();
                                               }
                                               
                                               if(compDet.getCompStockProperty().getStockPptyName().equals("PRODPENALTY") && (compDet.getCompStockPptyVal()!=null && !compDet.getCompStockPptyVal().equals("")))
                                               {
                                                     penalty = Double.parseDouble(compDet.getCompStockPptyVal());
                                               }
                                            
                                            }
                                            
                                            //Build Rest of the Fields
                                            theAcct.setProductTypeCode(defProdType);
                                            theAcct.setName(theStock.getCompStockName());
                                            theAcct.setCompanyId(theStock.getCompany().getId());
                                            theAcct.setBranchId((Integer)reqInfo.get(ActivityInterface.REQINFOBRAN));
                                            theAcct.setCurrencyId(Integer.parseInt(getGenericConfigDAO().getCurrencyIdByCode((String)reqInfo.get(ActivityInterface.REQINFOCURR))));
                                            theAcct.setControlAccount(theStock.getStockControlAcct());
                                            
                                            theAcct.setHasInterest(hasInt); //OK
                                            theAcct.setInterestRate(Float.parseFloat(intVal)); //OK
                                            theAcct.setPenalty(penalty); //OK
                                            
                                            //create product for Stock
                                            acctFlg= true;
                                            Products prodRet = null;
                                            try
                                            {
                                             
                                             System.out.println("Here inside "  +  theStock.getAction() +  " trying product");
                                             Object returnVal =   doRestService(theAcct, ActivityInterface.SERVTYPEPRODUCT);

                                            prodRet = (Products)returnVal;
                                            theStock.setStockAcctProd(prodRet.getCode());
                                                    

                                            } catch (Throwable e) {
                                                    // TODO Auto-generated catch block
                                                System.out.println("Here inside "  +  theStock.getAction() +  " failing product");
                                                 acctFlg= false;
                                                 e.printStackTrace();
                                                 setMsg("Product creation failed for Stock Class :" + theStock.getShortName());
                                                    //throw new Exception("Product creation failed for Stock Class :" + theStock.getShortName());
                                            }
                                    }
                                    else
                                    {
                                            //Products selected Exists
                                            acctFlg = true;
                                    }

                                    if(acctFlg)
                                    {
                                    //now merge stock
                                        System.out.println("Here inside "  +  theStock.getAction() +  " about merging");
                                        getCompStockTypeDAO().merge(theStock,sess);
                       
                                        actLog.setActionItem(theStock.getCompStockTypeId().toString());
                                        actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
                                        actLog.setDescription(flow.getEvent().getEventName());
                                        sess.getTransaction().commit();
                                        success=true; 
                                    }
                                    else
                                    {
                                        actLog.setActionItem(theStock.getCompStockTypeId().toString());
                                        actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
                                        //actLog.setDescription(flow.getEvent().getEventName() + " failed due to :: " +  ex.getMessage());
                                        sess.getTransaction().rollback();;
                                        success=false;

                                    }
                       }
                        else  if(theStock.getAction().equals("REJECT"))
                       {

                           nextStat = getStatusDAO().findById(flow.getStatusFailId());
                           theStock.setStatus(nextStat);
                           
                                        getCompStockTypeDAO().merge(theStock,sess);
                                        
                                        actLog.setActionItem(theStock.getCompStockTypeId().toString());
                                        actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
                                        actLog.setDescription(flow.getEvent().getEventName());
                                        sess.getTransaction().commit();
                                        success=true;
                       }
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
                        //set
                        actLog.setActionItem(theStock.getCompStockTypeId().toString());
			actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
			actLog.setDescription(flow.getEvent().getEventName() + " failed due to :: " +  ex.getMessage());
			sess.getTransaction().rollback();;
			success=false;
		}
		finally
		{
                        getActivityLogDAO().save(actLog);
			try
                        {
                            sess.close();
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }
		}
		
		return success;
	}

	@Override
	public boolean apprBulkDetail(Object eventObject, StatusFlow flow,
			HttpServletRequest req) {
		// TODO Auto-generated method stub
		return false;
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

	public String getDefProdType() {
		return defProdType;
	}

	public void setDefProdType(String defProdType) {
		this.defProdType = defProdType;
	}

        
	@Override
	public ActivityLog getLogObject(Object eventObject, StatusFlow flow,
			HttpServletRequest req) {
            
            CompStockType stk = (CompStockType)eventObject;
            SiftFinancialUtil siftUtil  =  new SiftFinancialUtil();
            ActivityLog log = new ActivityLog();
            
            System.out.println("here Inside Logging");
            Date today  = new Date();
           
            try
            {
                
                today = getCountryDate(today, reqInfo.get(ActivityInterface.REQINFOCOUN).toString(), null);
   		log.setAction(stk.getAction());
		log.setActionDate(new  java.sql.Timestamp(today.getTime()));
		log.setEvent(flow.getEvent());
                System.out.println("reqInfo.get(ActivityInterface.REQINFOBRAN) " + reqInfo.get(ActivityInterface.REQINFOBRAN));
		log.setBranchId(Integer.parseInt(reqInfo.get(ActivityInterface.REQINFOBRAN).toString()));
		log.setCompanyId(stk.getCompany().getId());
		log.setIpaddress(siftUtil.getUserAddress(req));
                log.setUsername(user.getCurruser().getUserId());
                
             }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
		
		return log;
	}
       
        public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
        
        
       public Map<String, Object> getReqInfo() {
        return reqInfo;
            }

        public void setReqInfo(Map<String, Object> reqInfo) {
            this.reqInfo = reqInfo;
        }

        public CurrentuserdisplayImpl getUser() {
            return user;
        }

        public void setUser(CurrentuserdisplayImpl user) {
            this.user = user;
        }

    
    
}
