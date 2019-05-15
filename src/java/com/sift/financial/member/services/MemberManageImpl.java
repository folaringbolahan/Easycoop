package com.sift.financial.member.services;

import java.text.ParseException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.sift.financial.member.*;
import com.sift.financial.services.MailNotificationImpl;
import com.sift.financial.services.ManagementTempl;
import com.sift.financial.services.ReqManageService;
import com.sift.financial.member.schedule.ConnectEasyCoopTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sift.financial.member.ws.client.*;
import com.sift.financial.stock.services.*;
import com.sift.financial.utility.SiftFinancialUtil;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;

//@Service
public class MemberManageImpl extends ReqManageService {
	
	private static final Log log = LogFactory.getLog(MemberManageImpl.class);
	
	private Map<String, String> memAddrType;
	
	private String apprvdStockStat;
	
	private String msg;
	
	private MemberHoldingsService memberHoldingsService;
	
	private StockDetailService stockDetailService;
	
        @Autowired
	private SiftFinancialUtil siftUtil;
	
        private Map<String,Object> reqInfo;
         
        private CurrentuserdisplayImpl user;
        
        @Autowired
        @Value("${REGISTER}")
        private String tranCode; 
      
        @Autowired
        @Value("${regTranRef}")
        private String tranRef;
        
        @Autowired
        @Value("${DEF-CON-PRODTYPE}")
        private String prodType;   
        
        @Autowired
        private TaskExecutor taskExec;
               
        @Autowired
        private ConnectEasyCoopTask  coopTask;
        
        
        
	
	@Override
	public Map<String, List> getReferenceData(String[] param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addDetail(Object eventObject, StatusFlow flow,HttpServletRequest req) {
		// TODO Auto-generated method stub
		
		Session sess = null;
		Member member = (Member)eventObject;
		Status successStatus = getStatusDAO().findById(flow.getStatusSuccessId());
		member.setStatus(successStatus);
		
		ActivityLog actLog = null;
		Integer actionItem ;
		
		Date today = new Date();
		
		try {
			//today = getCountryDate(today, member.getCompany().getCountries().getId().toString(), "");
                        today = getCountryDate(today, (String)reqInfo.get(ActivityInterface.REQINFOCOUN),"");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		member.setCreatedDate(today);
		member.setDelFlg("N");
		
		//System.out.println("Tax Doc:::: " + member.getTaxGroups().getId());
		
        boolean success= false;
		
		try{
		    log.info("saving Member details...1");
			sess = getMemberDao().getSessionFactory().openSession();
			sess.beginTransaction();
                        
                        //create new member-code
			String memCode = null;
						
			Map<String,String> memCodeMap = getCompanyMemberIdentifierDAO().getNextMemberIdentity(member.getCompany().getId().toString(), sess);
							
                        CompanyMemberIdentifier compNew =  new CompanyMemberIdentifier();
                        compNew.setCompanyId(member.getCompany().getId());

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
                  
                        member.setMemberNo(memCodeMap.get("MEMCODE"));
			//write member and retrieve ID
			Integer memberId = getMemberDao().save(member, sess);
		
			//Map<String, List<AddressEntries>> theAdd =member.getAddressEntriesList();
			
			actLog = getLogObject(member,flow,req);
                        
			actLog.setActionItem(memberId.toString());
			actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
			actLog.setDescription(flow.getEvent().getEventName());
			
			
			Map<String, List<AddressEntries>> theAdd =reBuildAddress(req, memberId);
			
			 //System.out.println("Final theAdd:::" +  theAdd.size());
			
			Set theKeys = theAdd.keySet();
					
			 Iterator keyIter = theKeys.iterator();
					
			while(keyIter.hasNext())
			{
				String theAddType = (String)keyIter.next();
				
				 //System.out.println("theAddType:::" +  theAddType);
				
				AddressType addType =getAddressTypeByName(theAddType);
				
				if(addType==null)
				{
					//System.out.println("Cant retrieve Address Type:::");
				}
				else
				{
					//System.out.println("Retrieved Address Type:::" +  addType.getTypeName());
				}
				
				//get address litera
				
				List<AddressEntries> theAddEntries= theAdd.get(theAddType);
				
				   for(int n=0; n < theAddEntries.size(); n++)
				   {
					   
					   AddressEntries thisAdd =   theAddEntries.get(n);
					   		   
					   thisAdd.setCreatedBy(member.getCreatedBy());
					   thisAdd.setCreationDate(new  java.sql.Timestamp(member.getCreatedDate().getTime()));
					   thisAdd.setTypeId(addType.getId());
					   thisAdd.setKeyId(Long.valueOf(memberId.toString()));
					   
					    /*  System.out.println("Fieldname: " + thisAdd.getAddrFieldName());
					   System.out.println("Addess Value: " + thisAdd.getAddrFieldValue());
					   
					   System.out.println("SerialPos : " + thisAdd.getSerialPos());
					   
					   System.out.println("Created By :: " + thisAdd.getCreatedBy());
					   System.out.println("Type Id :: "  + thisAdd.getTypeId());
					   
					System.out.println(thisAdd.getAddrFieldName());
					   System.out.println(thisAdd.getAddrFieldName());
					   
					   System.out.println("key Id :: " + thisAdd.getKeyId());*/
					   
					   getAddEntriesDAO().save(thisAdd, sess);
					  
				   }
			}
			// do all for events
                        //save contribution value if there
                        
                        Set contribSet =  getMemberContributionEntries (req, memberId.toString(), today);
                        
                        if (contribSet!=null && contribSet.size()>0)
                        {
                           Iterator iter = contribSet.iterator();
			
                            while(iter.hasNext())
                            {
                                MemberContribution memContrib = (MemberContribution)iter.next();
                                memContrib.setMember(member);
                                memContrib.setCreatedDate(new  java.sql.Timestamp(member.getCreatedDate().getTime()));
                                getMemberContributionDAO().save(memContrib, sess);
                            }
                   
                        }
                        
			// Save Stock too
			//create stock entry based on company for movement & create aggregated stock entry
			Set theMoves=	getMemberHoldingsService().buildDefaultHoldingsMovement(req, member, member.getCompany(), flow.getEvent(), apprvdStockStat);
			
			Iterator iter = theMoves.iterator();
			
			while(iter.hasNext())
			{
		
				MemberHoldingsMovement holdMove= (MemberHoldingsMovement)iter.next();
				System.out.println("member.getCreatedDate() ::" + member.getCreatedDate());
				holdMove.setCreatedDate(new  java.sql.Timestamp(member.getCreatedDate().getTime()));
				holdMove.setCreatedBy(member.getCreatedBy());
                                
                                    if(holdMove.getCompStockType().getRegisterStock().equals("Y"))
                                    {
                                        holdMove.setEffectiveDate(getMemberHoldingsService().getRegisterStockDate(holdMove.getCreatedDate()));
                                    }
                                    else
                                    {
                                        holdMove.setEffectiveDate(getMemberHoldingsService().getEffectiveStockDate(holdMove.getCreatedDate()));
                                    }
			
				///get aggregated stock entry....here
				Map<String, Object> holdings = getMemberHoldingsService().aggregateHoldings(holdMove);
				
				getMemberHoldingsService().getMemberHoldingsMovementDAO().save(holdMove, sess);
				
					for(String key : holdings.keySet())
					{
						MemberHoldings curHoldings  = (MemberHoldings)holdings.get(key);
						
						curHoldings.setCreatedDate(new  java.sql.Timestamp(member.getCreatedDate().getTime()));
						curHoldings.setCreatedBy(member.getCreatedBy());
						
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
			
                        // add extra fields
                       /* 
                        Map<String, List<MembersExtrafldEntries>> theExtfld =reBuildExtraflds(req, memberId);
			Set theextrafldKeys = theExtfld.keySet();
			Iterator keyIterext = theextrafldKeys.iterator();
			while(keyIterext.hasNext())
			{
				String theExtrafldType = (String)keyIterext.next();
				AddressType addType =getAddressTypeByName(theAddType);
				
				if(addType==null)
				{
					//System.out.println("Cant retrieve Address Type:::");
				}
				else
				{
					//System.out.println("Retrieved Address Type:::" +  addType.getTypeName());
				}
				
				//get address litera
				*/
				List<MembersExtrafldEntries> theEntries= reBuildExtraflds(req, memberId, member.getBranch().getId());//    theAdd.get(theAddType);
				
				   for(int n=0; n < theEntries.size(); n++)
				   {
					   
					   MembersExtrafldEntries thisItem =   theEntries.get(n);
					   		   
					   thisItem.setCreatedBy(member.getCreatedBy());
					   thisItem.setCreationDate(new  java.sql.Timestamp(member.getCreatedDate().getTime()));
					   //thisItem.setTypeId("G");
					   thisItem.setKeyId(Long.valueOf(memberId.toString()));
					   
					    /*  System.out.println("Fieldname: " + thisAdd.getAddrFieldName());
					   System.out.println("Addess Value: " + thisAdd.getAddrFieldValue());
					   
					   System.out.println("SerialPos : " + thisAdd.getSerialPos());
					   
					   System.out.println("Created By :: " + thisAdd.getCreatedBy());
					   System.out.println("Type Id :: "  + thisAdd.getTypeId());
					   
					System.out.println(thisAdd.getAddrFieldName());
					   System.out.println(thisAdd.getAddrFieldName());
					   
					   System.out.println("key Id :: " + thisAdd.getKeyId());*/
					   getMembersExtrafldEntriesDAO().save(thisItem, sess);
					  
				   }
			//}
                        
			 sess.getTransaction().commit();
			 success=true;
			 
			 setMsg("Member successfully created");
	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			try
                        {actLog.setActionItem(ActivityInterface.UNSUCCESSFULLNEWITEMID);
			actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
			actLog.setDescription(flow.getEvent().getEventName() + " failed due to :: " +  ex.getMessage());
                        }
                        catch(Exception excatch)
                        {
                            excatch.printStackTrace();
                        }
			sess.getTransaction().rollback();;
			success=false;
			setMsg("Member creation failed due to :: " + ex.getMessage());
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
		// TODO Auto-generated method stub
		
		Session sess = null;
		Member member = (Member)eventObject;
		Status successStatus = getStatusDAO().findById(flow.getStatusSuccessId());
		member.setStatus(successStatus);
                
               // Member  memberOld = getMemberDao().findById(member.getMemberId());
		
		ActivityLog actLog = null;
		Integer actionItem ;
		
		Date today = new Date();
		
		try {
			//today = getCountryDate(today, member.getCompany().getCountries().getId().toString(), "");
                        today = getCountryDate(today, (String)reqInfo.get(ActivityInterface.REQINFOCOUN),"");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		member.setModifiedDate(new Timestamp(today.getTime()));
		member.setDelFlg("N");
                //member.setCreatedBy(memberOld.getCreatedBy());
                //memberOld.setCreatedDate(memberOld.getCreatedDate());
		
		//System.out.println("Tax Doc:::: " + member.getTaxGroups().getId());
		
        boolean success= false;
		
		try{
		    log.info("saving Member details...1");
			sess = getMemberDao().getSessionFactory().openSession();
			sess.beginTransaction();
                        
                        //create new member-code
			//String memCode = null;
						
			//Map<String,String> memCodeMap = getCompanyMemberIdentifierDAO().getNextMemberIdentity(member.getCompany().getId().toString(), sess);
							
                        //CompanyMemberIdentifier compNew =  new CompanyMemberIdentifier();
                        //compNew.setCompanyId(member.getCompany().getId());

                        //if(memCodeMap.get("MEMCODE").equals(StockInterface.STARTIDENTITIER))
                       // {
                                //do insert
                         //       compNew.setLastMemberCode(Integer.valueOf(StockInterface.STARTIDENTITIER));
                        //        compNew.setLastDate(today);
                                //save object
                        //        getCompanyMemberIdentifierDAO().save(compNew, sess);
                        //}
                        //else
                        //{
                                //do update
                          //      compNew.setLastMemberCode(Integer.valueOf(memCodeMap.get("MEMCODE")));
                         //       compNew.setLastDate(today);
                         //       compNew.setCompanyMemberId(Integer.valueOf(memCodeMap.get("ID")));
                                //Update Record
                         //       getCompanyMemberIdentifierDAO().update(compNew, sess);
                        //}
                  
                        //member.setMemberNo(memCodeMap.get("MEMCODE"));
			//write member and retrieve ID
			getMemberDao().merge(member, sess);
		
			//Map<String, List<AddressEntries>> theAdd =member.getAddressEntriesList();
			
			actLog = getLogObject(member,flow,req);
                        
			actLog.setActionItem(member.getMemberId().toString());
			actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
			actLog.setDescription(flow.getEvent().getEventName());
			
			
			Map<String, List<AddressEntries>> theAdd =reBuildAddress(req, member.getMemberId());
			
			 //System.out.println("Final theAdd:::" +  theAdd.size());
			
			Set theKeys = theAdd.keySet();
					
			 Iterator keyIter = theKeys.iterator();
					
			while(keyIter.hasNext())
			{
				String theAddType = (String)keyIter.next();
				
				 //System.out.println("theAddType:::" +  theAddType);
				
				AddressType addType =getAddressTypeByName(theAddType);
				
				if(addType==null)
				{
					//System.out.println("Cant retrieve Address Type:::");
				}
				else
				{
					//System.out.println("Retrieved Address Type:::" +  addType.getTypeName());
				}
				
				//get address litera
				
				List<AddressEntries> theAddEntries= theAdd.get(theAddType);
				
				   for(int n=0; n < theAddEntries.size(); n++)
				   {
					   
					   AddressEntries thisAdd =   theAddEntries.get(n);
					   		   
					   thisAdd.setCreatedBy(member.getCreatedBy());
					   thisAdd.setCreationDate(new  java.sql.Timestamp(member.getCreatedDate().getTime()));
					   thisAdd.setTypeId(addType.getId());
					   thisAdd.setKeyId(Long.valueOf(member.getMemberId().toString()));
					   
					    /*  System.out.println("Fieldname: " + thisAdd.getAddrFieldName());
					   System.out.println("Addess Value: " + thisAdd.getAddrFieldValue());
					   
					   System.out.println("SerialPos : " + thisAdd.getSerialPos());
					   
					   System.out.println("Created By :: " + thisAdd.getCreatedBy());
					   System.out.println("Type Id :: "  + thisAdd.getTypeId());
					   
					  System.out.println(thisAdd.getAddrFieldName());
					   System.out.println(thisAdd.getAddrFieldName());
					   
					   System.out.println("key Id :: " + thisAdd.getKeyId());*/
                                          // memberId, String typeId, String addField
                                          AddressEntries markForDelete = getAddEntriesDAO().getMemberAddressObjectByTypeField(member.getMemberId().toString(), addType.getId().toString(), thisAdd.getAddrFieldName());
                                          
                                          if(markForDelete !=null)
                                          {
                                            getAddEntriesDAO().delete(markForDelete, sess);
                                          }
					   
					  getAddEntriesDAO().save(thisAdd, sess);
					  
				   }
			}
			// do all for events
                        //save contribution value if there
                        /* error prone*/
                        Set contribSet =  getMemberContributionEntries (req, member.getMemberId().toString(), today);
                        
                        if (contribSet!=null && contribSet.size()>0)
                        {
                            
                               List forDels = getMemberContributionDAO().getMemberContributions(member.getMemberId());
                               
                               if(forDels!=null && forDels.size()>0)
                               {
                                   for (Object obj: forDels)
                                   {
                                       MemberContribution forDel = (MemberContribution)obj;
                                       MemberContributionArchive conArchive= new MemberContributionArchive();

                                        conArchive.setBranchId(forDel.getBranchId());
                                        conArchive.setCompanyId(forDel.getCompanyId());
                                        conArchive.setContribProd(forDel.getContribProd());
                                        conArchive.setCountryId(forDel.getCountryId());
                                        conArchive.setCreatedBy(user.getCurruser().getUserId());
                                        conArchive.setCreatedDate(new Timestamp (today.getTime()));
                                        conArchive.setMemberContribValue(forDel.getMemberContribValue());
                                        conArchive.setMemberId(member.getMemberId());

                                        getMemberContributionArchiveDAO().save(conArchive, sess);

                                        getMemberContributionDAO().delete(forDel, sess);
                                    
                                    sess.flush();
                                    System.out.println("############OK DOB#################");
                                   }
                               }
                           
                           
                           Iterator iter = contribSet.iterator();
			
                            while(iter.hasNext())
                            {
                            
                                MemberContribution memContrib = (MemberContribution)iter.next();
                                memContrib.setMember(member);
                                memContrib.setCreatedDate(new  java.sql.Timestamp(member.getCreatedDate().getTime()));
                                
                               getMemberContributionDAO().save(memContrib, sess);
                            }

                        }
                        
                        ////
                        
                        List<MembersExtrafldEntries> theEntries= reBuildExtraflds(req, member.getMemberId(), member.getBranch().getId());//    theAdd.get(theAddType);
			 for(int n=0; n < theEntries.size(); n++)
			  {
					   
					   MembersExtrafldEntries thisItem =   theEntries.get(n);
					   thisItem.setCreatedBy(member.getCreatedBy());
					   thisItem.setCreationDate(new  java.sql.Timestamp(member.getCreatedDate().getTime()));
					   thisItem.setKeyId(Long.valueOf(member.getMemberId().toString()));
					   
					   MembersExtrafldEntries markextraForDelete = getMembersExtrafldEntriesDAO().getMemberExtrafldObjectByTypeField(member.getMemberId().toString(), thisItem.getTypeId().toString(), thisItem.getExtraFieldId());
                                          
                                          if(markextraForDelete !=null)
                                          {
                                            getMembersExtrafldEntriesDAO().delete(markextraForDelete, sess);
                                          }
					   
					   getMembersExtrafldEntriesDAO().save(thisItem, sess);
					  
				   }
                        
                        
                        
			// Save Stock too
			//create stock entry based on company for movement & create aggregated stock entry
			/**Set theMoves=	getMemberHoldingsService().buildDefaultHoldingsMovement(req, member, member.getCompany(), flow.getEvent(), apprvdStockStat);
			
			Iterator iter = theMoves.iterator();
			
			while(iter.hasNext())
			{
		
				MemberHoldingsMovement holdMove= (MemberHoldingsMovement)iter.next();
				System.out.println("member.getCreatedDate() ::" + member.getCreatedDate());
				holdMove.setCreatedDate(new  java.sql.Timestamp(member.getCreatedDate().getTime()));
				holdMove.setCreatedBy(member.getCreatedBy());
                                
                                    if(holdMove.getCompStockType().getRegisterStock().equals("Y"))
                                    {
                                        holdMove.setEffectiveDate(getMemberHoldingsService().getRegisterStockDate(holdMove.getCreatedDate()));
                                    }
                                    else
                                    {
                                        holdMove.setEffectiveDate(getMemberHoldingsService().getEffectiveStockDate(holdMove.getCreatedDate()));
                                    }
			
				///get aggregated stock entry....here
				Map<String, Object> holdings = getMemberHoldingsService().aggregateHoldings(holdMove);
				
				getMemberHoldingsService().getMemberHoldingsMovementDAO().save(holdMove, sess);
				
					for(String key : holdings.keySet())
					{
						MemberHoldings curHoldings  = (MemberHoldings)holdings.get(key);
						
						curHoldings.setCreatedDate(new  java.sql.Timestamp(member.getCreatedDate().getTime()));
						curHoldings.setCreatedBy(member.getCreatedBy());
						
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
			**/
			 sess.getTransaction().commit();
			 success=true;
			 
			 setMsg("Member successfully updated");
	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			try
                        {actLog.setActionItem(ActivityInterface.UNSUCCESSFULLNEWITEMID);
			actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
			actLog.setDescription(flow.getEvent().getEventName() + " failed due to :: " +  ex.getMessage());
                        }
                        catch(Exception excatch)
                        {
                            excatch.printStackTrace();
                        }
			sess.getTransaction().rollback();;
			success=false;
			setMsg("Member update failed due to :: " + ex.getMessage());
		}
		finally
		{
			getActivityLogDAO().save(actLog);
			sess.close();
		}
		
		return success;
	}

	@Override
	public boolean deleteDetail(Object eventObject, StatusFlow flow, HttpServletRequest req) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object readDetail(Object eventObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> listDetail(Object eventObject) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Object> listDetail(Object eventObject, String status) {
	
		return null;
	}
	
	public List<Object[]> listDetailSpecific(Object eventObject, String status) 
        {
		
		return getMemberDao().getMemberListByStatus(((Member)eventObject).getCompany().getId().toString(), status, ((Member)eventObject).getBranch().getId().toString());
	}
        
        
        public  Map<String, List<Map<String,Object>>> doEnquiry(String objId, Map<String, Map<String, Object>> pageData, boolean IsAdmin, Event event, HttpServletRequest req) 
        {
             Map<String, List<Map<String,Object>>> model = null;
             
            //Do activity log Too
            ActivityLog actLog = new ActivityLog();
	    Integer actionItem ;
            boolean isCompanyMember = false;
            
	    // need to check sql injection
            Integer theMember = 0;
            try 
            {
              theMember = Integer.parseInt(objId);
              
            }catch (Exception e)
            {
                // TODO Auto-generated catch block
                theMember = 0;
                e.printStackTrace();
	     }
             
         
            Date today = new Date();
		
		try {
			//today = getCountryDate(today, member.getCompany().getCountries().getId().toString(), "");
                        today = getCountryDate(today, user.getCurrusercompany().getCountryId(),"");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                // build other parameter: companyid, brach_id
                actLog.setDescription(event.getEventName());
                actLog.setActionDate(new Timestamp(today.getTime()));
                actLog.setBranchId(user.getCurruser().getBranch());
                actLog.setCompanyId(user.getCurruser().getCompanyid());
                actLog.setEvent(event);
                actLog.setIpaddress(siftUtil.getUserAddress(req));
                actLog.setUsername(user.getCurruser().getUserId());
                
		actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
			
                if(theMember!=0)
                {
                    actLog.setActionItem(theMember.toString());
                 
                   if(!IsAdmin)
                   {
                       List valMember = getMemberDao().getMemberByIdCompBranch(user.getCurruser().getCompanyid(),user.getCurruser().getBranch(),theMember);
                       
                       if(valMember!=null)
                       {
                            isCompanyMember = true;
                       }
                   }
                    
                   if(IsAdmin || isCompanyMember)
                   {
                        model = getGenericConfigDAO().getMemberDetail(theMember.toString(), pageData, null);
                   }
                }
                else
                {
                    actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
                    model = null;
                    setMsg("Member info cannot be retrieved");
                }
                
                
	    return model;
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
	public boolean sendSuccessAuthNotification(String comment, Object eventObject,
			String mailTempl, MailNotificationImpl mailNotificationService) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendFailureAuthNotification(String comment, Object eventObject,
			String mailTempl, MailNotificationImpl mailNotificationService) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.sift.financial.services.ReqManageService#apprDetail(java.lang.Object, com.sift.financial.member.StatusFlow, javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("unused")
	@Override
	public boolean apprDetail(Object eventObject, StatusFlow flow, HttpServletRequest req)
	{
		
		boolean returnVal = false;
		Session sess = null;
		Member member = (Member)eventObject;
		//move status
		
		ActivityLog actLog = null;
		Integer actionItem ;
		actLog = getLogObject(member,flow,req);
		actLog.setActionItem(member.getMemberId().toString());
		
		Date today = new Date();
		try
		{
                    
                    log.info("saving Member details...1");
                    sess = getMemberDao().getSessionFactory().openSession();
		    sess.beginTransaction();
                                        
                    Map<String, String> resultReq = canApproveRequest(member.getCreatedBy(),member.getModifiedBy(),user.getCurruser().getUserId());
        
                    if(!Boolean.parseBoolean(resultReq.get("STATE")))
                    {
                        throw new Exception(resultReq.get("MSG"));
                    }
                    
                        today = getCountryDate(today, user.getCurrusercompany().getCountryId(),"");
			
			if(member.getAction().equals(ActivityInterface.ACTIONAPPROVE))
			{
			
				log.info("Inside Action Reject ..............");
				
				Status successStatus = getStatusDAO().findById(flow.getStatusSuccessId());
				member.setStatus(successStatus);
				
						
                                                    //create default Accounts contributions and savings
							/**Map<String,String> defAccounts = getDefaultProducts();
							Set theKeys = defAccounts.keySet();*/
                                
                                                 // List<Map<String,Object>>  theKeys = getGenericConfigDAO().getProductsByType(member.getCompany().getId().toString(), member.getBranch().getId().toString(), prodType);
					            List<Map<String,Object>>  theKeys = getGenericConfigDAO().getDefProducts(member.getCompany().getId().toString(), member.getBranch().getId().toString());
							for(Object key : theKeys)
							{
								Map<String,Object> keyStr = (Map<String,Object>)key;
								Object serVStr = null;
								Accnowbs theAcct = new Accnowbs();
								
								theAcct.setBranchcode(member.getBranch().getBranchCode());
								theAcct.setBranchId(member.getBranch().getId().toString());
								theAcct.setCompany(member.getCompany().getId().toString());
								theAcct.setCompanycode(member.getCompany().getCode());
								theAcct.setCustomerno(member.getMemberNo());
								//theAcct.setProductcode(defAccounts.get(keyStr));
                                                                theAcct.setProductcode((String)keyStr.get("code"));								
                                                                theAcct.setSubno(getDefProdSubCode());
                                                                theAcct.setTimezone(user.getCurrusercompany().getTimezone());
								
								try
								{
                                                                    
								serVStr = doRestService(theAcct, ActivityInterface.SERVTYPEACCOUNT);
									
                                                                    if(serVStr instanceof Accnowbs)
                                                                    {
                                                                      theAcct = (Accnowbs)serVStr;
                                                                      //theAcct.get
                                                                    }
                                                                    else if(serVStr instanceof String)
                                                                    {

                                                                     System.out.println("serVStr :" + serVStr);

                                                                    }
								} catch (Throwable e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									throw new Exception("Account creation failed for Product Code :" + (String)keyStr.get("code") + " with name: " + (String)keyStr.get("name"));
								}
							
							}
                                                        
                                                        //create other accounts for other stocks if specified
                                                        if(member.getMemberHoldingsMovements()!=null && !member.getMemberHoldingsMovements().isEmpty())
                                                        {
                                                            
                                                          /**  for(Object holdings : member.getMemberHoldingsMovements())
                                                            {
                                                                 MemberHoldingsMovement objHold= (MemberHoldingsMovement)holdings;
                                                            
                                                                   if(objHold.getCompStockType().getRegisterStock().equals("Y"))
                                                                   {
                                                                         continue;
                                                                   }
                                                                   else
                                                                   {
                                                                   
                                                                                 Object serVStr = null;
                                                                                 Accnowbs theAcct = new Accnowbs();

                                                                                 theAcct.setBranchcode(member.getBranch().getBranchCode());
                                                                                 theAcct.setBranchId(member.getBranch().getId().toString());
                                                                                 theAcct.setCompany(member.getCompany().getId().toString());
                                                                                 theAcct.setCompanycode(member.getCompany().getCode());
                                                                                 theAcct.setCustomerno(member.getMemberNo());
                                                                                 theAcct.setProductcode(objHold.getCompStockType().getStockAcctProd());
                                                                                 theAcct.setSubno(getDefProdSubCode());
                                                                                 theAcct.setTimezone(user.getCurrusercompany().getTimezone());
                                                                                 try
                                                                                 {

                                                                                 serVStr = doRestService(theAcct, ActivityInterface.SERVTYPEACCOUNT);

                                                                                     if(serVStr instanceof Accnowbs)
                                                                                     {
                                                                                       theAcct = (Accnowbs)serVStr;
                                                                                       //theAcct.get
                                                                                     }
                                                                                     else if(serVStr instanceof String)
                                                                                     {
                                                                                      System.out.println("serVStr :" + serVStr);
                                                                                     }
                                                                                 } catch (Throwable e) {
                                                                                         // TODO Auto-generated catch block
                                                                                         e.printStackTrace();
                                                                                         throw new Exception("Account creation failed for Product Code :" + objHold.getCompStockType().getStockAcctProd());
                                                                                 }
                             
                                                                   }

                                                            }*/
                                                            
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
                                                              
                                                       //moved.getCompStockType().getStockAcctProd();
                                                                
						        }
                                                        
							
                                                        }
							actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
							//actLog.setDescription(flow.getEvent().getEventName());
							
							//save Member Info
							//member.setMemberNo(memCode);
							getMemberDao().merge(member, sess);
							
							sess.getTransaction().commit();
							returnVal=true;
                                                        
                                                        //send to EasyCopp if Coop is Commected
                                                        coopTask.setTargetObjs(member);
                                                        coopTask.setTypeInd(ActivityInterface.SINGLEMEMBER);
                                                        
                                                        log.info("Here inside apprDetail.................spurning task with info to EasyCoop");
                                                        taskExec.execute(coopTask);
                                                        log.info("Here inside apprDetail.................spurned task with info to EasyCoop");
                                                        
						/**}
						else
						{
							actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
							actLog.setDescription(flow.getEvent().getEventName() + " failed due to Could not get memcode");
							
							System.out.println("Could not get memcode due to error above");
							sess.beginTransaction().rollback();
							returnVal = false;
							setMsg("Could not get memcode due to error above");
						}
						*/
	
			}
			else if(member.getAction().equals(ActivityInterface.ACTIONREJECT))
			{
				log.info("Inside Action Reject ..............");
				
				Status failStatus = getStatusDAO().findById(flow.getStatusFailId());
				member.setStatus(failStatus);
				
				//clean up Objects
				actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
				//actLog.setDescription(flow.getEvent().getEventName());
				
				//member
				getMemberDao().merge(member, sess);
				
				//address
				Map<String, String> theTypes = getMemAddrType();
				
				for(String theType : theTypes.keySet())
				{
					AddressType theAddr = (AddressType)getAddressTypeDAO().findByTypeName(theType).get(0);
					
					boolean delFlg = getAddEntriesDAO().delete(member.getMemberId().toString(), theAddr.getId().toString(), sess);
					
					if(!delFlg)
					{
						//throw new Exception("No Address to delete........");
					}
				}
				
				//stocks movementHolings
				Event createEvt = (Event)getEventDAO().findByEventShort("eventShort").get(0);
				
				boolean delFlg = getMemberHoldingsService().getMemberHoldingsMovementDAO().deleteByEventAndMember(createEvt, member, sess);

				if(!delFlg)
				{
					//throw new Exception("There are no stocks created at Setup........");
				}
				
                                boolean delFlg2 = getMembersExtrafldEntriesDAO().delete(member.getMemberId().toString(), sess);
				if(!delFlg2)
				{
					//throw new Exception("There are no stocks created at Setup........");
				}	
                                
				log.info("Aboout Commiting..............");
				//commit
				sess.getTransaction().commit();
				returnVal=true;
	
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
	public boolean apprBulkDetail(Object eventObject, StatusFlow flow, HttpServletRequest req)
	{
		
		
		
		
		
		return false;
	}
	
	
	@SuppressWarnings("unused")
	private Map<String, List<AddressEntries>> reBuildAddress(HttpServletRequest req, Integer memberId)
	{
		 Map<String, List<AddressEntries>>  retrunVal = new  HashMap<String, List<AddressEntries>> ();
		 
		 List <String> parameterKeyList = new ArrayList<String>();
		
		 Map<String, String[]> parameterMap = req.getParameterMap();
		 
		 System.out.println("parameterMap Size:::" +  parameterMap.size());
		 
		
		 Map<String, String> theTypes = getMemAddrType();
		 List<AddressEntries> theAddressList = null;
		 
		 for(String typeKey: theTypes.keySet())
		 {
				
			 theAddressList = new  ArrayList<AddressEntries>();
			 parameterKeyList = new ArrayList<String>();
			 
			 for (String key: parameterMap.keySet()) 
			 {
				 System.out.println("key:::" +  key);
				 
				 System.out.println("typeKey:::" +  typeKey);
				 
				    if (key.startsWith(theTypes.get(typeKey))) 
				    {
				    	System.out.println("adding key ============= " +  key);
				    	System.out.println("for typeKey ============= " +  theTypes.get(typeKey));
				        
				    	parameterKeyList.add(key); 
				    }
			 }
				
			System.out.println("Printing For ::::===============" + theTypes.get(typeKey) + "  ===================");
			
			for (String parameter : parameterKeyList)
			{
			    String value = req.getParameter(parameter); 
			    
			    AddressEntries  addEntry = new AddressEntries();
			    
			    String[] theArray = parameter.split(":::");
			    
			   System.out.println("theArray[1]:::" +  theArray[1]);
			   System.out.println("value:::" +  value);
			    
			    addEntry.setAddrFieldName(theArray[1]);
			    addEntry.setAddrFieldValue(value);
			    
			    addEntry.setKeyId(Long.parseLong(memberId.toString()));
			    addEntry.setSerialPos(theArray[2]);
			  
			    theAddressList.add(addEntry);
			    
			    theArray = null;
			    
			}
				
				retrunVal.put(theTypes.get(typeKey), theAddressList);
				parameterKeyList= null;
				theAddressList = null;
		 }
		
		
	 return retrunVal;
	}
	
	/**
	 * @param memberId
	 * @return
	 */
	public Map<String, List<AddressEntries>> getMemberAddressEntries (String memberId)
	{
		
		Map<String, List<AddressEntries>> theAddressMap = new HashMap<String, List<AddressEntries>>();
			
		
		 for(String Key: getMemAddrType().keySet())
		 {
			 
			 AddressType curAddType =  (AddressType)(getAddressTypeDAO().findByTypeName(getMemAddrType().get(Key)).get(0));
			 
			  List addsType = getAddEntriesDAO().getMemberAddressObjectByType(memberId, curAddType.getId().toString());
			 			 
			  theAddressMap.put(getMemAddrType().get(Key), addsType);
			 
		 }
		
		 return theAddressMap;
	}
        
        public List<MembersExtrafldEntries> getMembersExtrafldEntries (String memberId)
	{
		List<MembersExtrafldEntries> theMap =  new  ArrayList<MembersExtrafldEntries>();
		theMap = getMembersExtrafldEntriesDAO().getMemberExtrafldObjectByType(memberId);
		return theMap;
	}
        /**
         * @param req
	 * @param memberId
         * @param today
	 * @return
	 */
	public Set getMemberContributionEntries (HttpServletRequest req, String memberId, Date today)
	{
		Set contribList = new HashSet(0);
                	                
                 log.info("user.getCurruser().getCompanyid().toString() : :: " +  user.getCurruser().getCompanyid().toString());
                 log.info("user.getCurruser().getBranch().toString() : :: " +  user.getCurruser().getBranch().toString());
                 log.info("prodType : :: " +  prodType);
                
                List<Map<String,Object>> theProds = getGenericConfigDAO().getDefaultProductsByType(user.getCurruser().getCompanyid().toString(), user.getCurruser().getBranch().toString(), prodType);
                
                log.info("theProds V : :: " +  theProds.size());
                
		if(theProds!=null && theProds.size()> 0)
                {
                    for(Map<String,Object> Key: theProds)
                    {
                            MemberContribution contrb = new MemberContribution();
                            
                            log.info("(String)Key.get(\"code\") ::" +  (String)Key.get("code"));
                             
                            String theCode = req.getParameter((String)Key.get("code"));
                            log.info("theCode " +  theCode);
                              
                            String valKey = (String)Key.get("code")+ "_val";
                            log.info("valKey :: " +  valKey);
                            String theValue = req.getParameter(valKey);
                            
                            log.info("theValue ::" +  theValue);
                            
                            contrb.setBranchId(user.getCurruser().getBranch());
                            contrb.setCompanyId(user.getCurruser().getCompanyid());
                            contrb.setContribProd(theCode);
                            contrb.setCountryId(Integer.parseInt(user.getCurrusercompany().getCountryId()));
                            contrb.setCreatedDate(new  java.sql.Timestamp(today.getTime()));
                            contrb.setCreatedBy(user.getCurruser().getUserId());
                            contrb.setMemberContribValue(Double.parseDouble(theValue));
                            //contrb.setModifiedBy(user.getCurruser().getUserId());
                            //contrb.setModifiedDate(new  java.sql.Timestamp(today.getTime()));
                           // contrb.setApprovedBy(user.getCurruser().getUserId());
                            //contrb.setApprovedDate(new  java.sql.Timestamp(today.getTime()));
                            contribList.add(contrb);

                    }
                }
		
		 return contribList;
	}
	
        
        @SuppressWarnings("unused")
	private  List<MembersExtrafldEntries> reBuildExtraflds(HttpServletRequest req, Integer memberId, Integer branchId)
	{
		  List<MembersExtrafldEntries>  retrunVal = null;
		  List <String> parameterKeyList = new ArrayList<String>();
		
		 Map<String, String[]> parameterMap = req.getParameterMap();
		 
		 System.out.println("parameterMap Size:::" +  parameterMap.size());
		  List<MembersExtrafldEntries> theList = new  ArrayList<MembersExtrafldEntries>();
		 List<MemberExtrafld> theextraTypes = getMemExtraType(branchId);
		
			 for (MemberExtrafld keytheextraTypes: theextraTypes) 
			 {
                            String parameter = keytheextraTypes.getDescription()+":::"+keytheextraTypes.getId();
			    String value = req.getParameter(parameter); 
			    
			    MembersExtrafldEntries  addEntry = new MembersExtrafldEntries();
			    
			   // String[] theArray = parameter.split(":::");
			    
			   //System.out.println("theArray[1]:::" +  theArray[1]);
			   //System.out.println("value:::" +  value);
			    
			    addEntry.setExtraFieldName(keytheextraTypes.getDescription());
			    addEntry.setExtraFieldValue(value);
                            addEntry.setExtraFieldId(keytheextraTypes.getId());
                            addEntry.setTypeId("N");
                            if (keytheextraTypes.getGrouped().equals("Y")) {
                              //addEntry.setExtraFieldoptionId(value);
                              addEntry.setExtraFieldoptionValue(value);
                              addEntry.setTypeId("G"); 
                            }
			    
			    addEntry.setKeyId(Long.parseLong(memberId.toString()));
			    addEntry.setSerialPos(keytheextraTypes.getId().toString());
			  
			    theList.add(addEntry);
			 }
				
			
				retrunVal=theList;
				parameterKeyList= null;
				theList = null;
		
		
		
	 return retrunVal;
	}
        
        public List getMemberContributions(Integer memberId)
	{
         
          return  getMemberContributionDAO().getMemberContributions(memberId);

        }
	
	
	
	public List getMemExtraType(Integer branchId) {
		return getMemberExtrafldDAO().findByBranch(branchId);
	}
	

	public Map<String, String> getMemAddrType() {
		return memAddrType;
	}

	public void setMemAddrType(Map<String, String> memAddrType) {
		this.memAddrType = memAddrType;
	}

	public String getApprvdStockStat() {
		return apprvdStockStat;
	}

	public void setApprvdStockStat(String apprvdStockStat) {
		this.apprvdStockStat = apprvdStockStat;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public MemberHoldingsService getMemberHoldingsService() {
		return memberHoldingsService;
	}
	@Autowired
	public void setMemberHoldingsService(MemberHoldingsService memberHoldingsService) {
		this.memberHoldingsService = memberHoldingsService;
	}

	public StockDetailService getStockDetailService() {
		return stockDetailService;
	}

	public void setStockDetailService(StockDetailService stockDetailService) {
		this.stockDetailService = stockDetailService;
	}

	@Override
	public ActivityLog getLogObject(Object eventObject, StatusFlow flow,HttpServletRequest req) {
		
            
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

    public ConnectEasyCoopTask getCoopTask() {
        return coopTask;
    }

    public void setCoopTask(ConnectEasyCoopTask coopTask) {
        this.coopTask = coopTask;
    }

  
}
