package com.sift.financial.stock.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sift.financial.member.*;
import com.sift.financial.member.ws.client.*;
import com.sift.financial.*;
import com.sift.financial.utility.*;
import com.sun.jersey.spi.inject.Inject;
import java.sql.Timestamp;
import java.util.Date;

import org.nfunk.jep.*;

@Service
public class MemberHoldingsService {
	
	private CompanyDAO companyDAO;
	private MemberDAO memberDAO;
	private AddressTypeDAO addressTypeDAO;
	private CountryAddressFilterDAO countryAddressFilterDAO;
	private CompStockTypeDAO compStockTypeDAO;
	private CompStockTypeDetailDAO compStockTypeDetailDAO;
	private CompStockPropertyDAO  compStockPropertyDAO;
	private MemberHoldingsDAO memberHoldingsDAO;
	private MemberHoldingsMovementDAO memberHoldingsMovementDAO;
	
	private GenericConfigDAO genericConfigDAO;
	
	 @Autowired
	 @Value("${apprvdStockStat}")
         private String apprvdStockStat;
	
	 @Autowired
	 @Value("${apprvMemberStatusShort}")
	 private String apprvMemberStatusShort;
         
         
         @Autowired
	 @Value("${effStockDateDiff}")
	 private String effStockDateDiff;
        
         @Autowired
	 @Value("${effRegisterStockDateDiff}")
	 private String effRegisterStockDateDiff;
	
	
	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}
	 @Autowired
	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}
	public MemberDAO getMemberDAO() {
		return memberDAO;
	}
	 @Autowired
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	public AddressTypeDAO getAddressTypeDAO() {
		return addressTypeDAO;
	}
	 @Autowired
	public void setAddressTypeDAO(AddressTypeDAO addressTypeDAO) {
		this.addressTypeDAO = addressTypeDAO;
	}
	public CountryAddressFilterDAO getCountryAddressFilterDAO() {
		return countryAddressFilterDAO;
	}
	 @Autowired
	public void setCountryAddressFilterDAO(
			CountryAddressFilterDAO countryAddressFilterDAO) {
		this.countryAddressFilterDAO = countryAddressFilterDAO;
	}
	
	public CompStockTypeDAO getCompStockTypeDAO() {
		return compStockTypeDAO;
	}
	 @Autowired
	public void setCompStockTypeDAO(CompStockTypeDAO compStockTypeDAO) {
		this.compStockTypeDAO = compStockTypeDAO;
	}
	public CompStockTypeDetailDAO getCompStockTypeDetailDAO() {
		return compStockTypeDetailDAO;
	}
	 @Autowired
	public void setCompStockTypeDetailDAO(
			CompStockTypeDetailDAO compStockTypeDetailDAO) {
		this.compStockTypeDetailDAO = compStockTypeDetailDAO;
	}
	public CompStockPropertyDAO getCompStockPropertyDAO() {
		return compStockPropertyDAO;
	}
	 @Autowired
	public void setCompStockPropertyDAO(CompStockPropertyDAO compStockPropertyDAO) {
		this.compStockPropertyDAO = compStockPropertyDAO;
	}
	public MemberHoldingsDAO getMemberHoldingsDAO() {
		return memberHoldingsDAO;
	}
	 @Autowired
	public void setMemberHoldingsDAO(MemberHoldingsDAO memberHoldingsDAO) {
		this.memberHoldingsDAO = memberHoldingsDAO;
	}
	public MemberHoldingsMovementDAO getMemberHoldingsMovementDAO() {
		return memberHoldingsMovementDAO;
	}
	 @Autowired
	public void setMemberHoldingsMovementDAO(
			MemberHoldingsMovementDAO memberHoldingsMovementDAO) {
		this.memberHoldingsMovementDAO = memberHoldingsMovementDAO;
	}
	
	
	
	//build memberHoldingsMovement
	
		/**
		 * @param req
		 * @param memberId
		 * @param event
		 * @return
		 */
		@SuppressWarnings({ "unused", "unchecked" })
		
	public Set buildHoldingsMovement(HttpServletRequest req, Member mem, Company comp, Event event, String statShort)
		{
		
			Set retrunVal = new HashSet (0);
			 
			 List theTypes = getCompStockTypeDAO().getAllValidStockListByCompany(comp.getId().toString(),statShort);
			 
			 MemberHoldingsMovement memberHoldingMovement = null;
			 
	
			 for(Object typeKey: theTypes)
			 {
					
				 memberHoldingMovement = new  MemberHoldingsMovement();
				 CompStockType finObj = (CompStockType)typeKey;
				 
                                 System.out.println("req.getParameter(finObj.getCompStockName()) " + req.getParameter(finObj.getCompStockName()));
				 String val = req.getParameter(finObj.getCompStockName());
				 
				 memberHoldingMovement.setCompany(comp);
				 memberHoldingMovement.setDelFlg(ActivityInterface.UNDELFLAG);
				 memberHoldingMovement.setEvent(event);
				 memberHoldingMovement.setMovementHoldings(Double.valueOf(val));
				 memberHoldingMovement.setMovementType(StockInterface.ADDSTOCK);
				 memberHoldingMovement.setMember(mem);
				 memberHoldingMovement.setCompStockType(finObj);
			
				 retrunVal.add(memberHoldingMovement);
				 
				 memberHoldingMovement = null;
                         
			 }
			
			
		 return retrunVal;
		}
        
        
               //// Todo not Finsished
                public Set buildHoldingsMovement(List memberHoldings, Event event, Member mem, Date date, String userid)
		{
		      Set retrunVal = new HashSet (0);
			//List theTypes = getCompStockTypeDAO().getAllValidStockListByCompany(comp.getId().toString(),statShort);
                         System.out.println("memberHoldings.size() :: " + memberHoldings.size()); 
                        
			 MemberHoldingsMovement memberHoldingMovement = null;
                         
                         MemberHoldings memberHolding = null;
			 
			 for(Object typeKey: memberHoldings)
			 {
			     System.out.println("memberHoldings.size() 1111 :: " + memberHoldings.size()); 
                             memberHolding = (MemberHoldings)typeKey;
                             
			    if(memberHolding.getHoldings() > 0.0)
                            {
                                System.out.println("memberHoldings.size() 2222 :: " + memberHoldings.size()); 
                                memberHoldingMovement =  new MemberHoldingsMovement();
                                
                                CompStockType StckType = compStockTypeDAO.findById(memberHolding.getStockId());
				 
                                // System.out.println("req.getParameter(finObj.getCompStockName()) " + req.getParameter(finObj.getCompStockName()));
				 //String val = req.getParameter(finObj.getCompStockName());
				 
				 memberHoldingMovement.setCompany(StckType.getCompany());
				 memberHoldingMovement.setDelFlg(ActivityInterface.UNDELFLAG);
				 memberHoldingMovement.setEvent(event);
				 memberHoldingMovement.setMovementHoldings(memberHolding.getHoldings());
                                 System.out.println("memberHoldings.size() 33332 :: " + event.getEventName() + " " + memberHolding.getHoldings()); 
                                 System.out.println("memberHoldings.size() 3333 :: " + event.getEventStockMove()); 
				 memberHoldingMovement.setMovementType(event.getEventStockMove());
                                 memberHoldingMovement.setMember(mem);
				 memberHoldingMovement.setCompStockType(StckType);
                                 memberHoldingMovement.setCreatedDate(new Timestamp(date.getTime()));
                                 memberHoldingMovement.setEffectiveDate(date);
                                 memberHoldingMovement.setCreatedBy(userid);
                                		
                                 retrunVal.add(memberHoldingMovement);
				 memberHoldingMovement = null;
                            }
			 }
		
		 return retrunVal;
		}
	
                /**
                 * Build Stock Values for Registration through the member form interface
                 * @param req
                 * @param mem
                 * @param comp
                 * @param event
                 * @param statShort
                 * @return 
                 */
		
		public Set buildDefaultHoldingsMovement(HttpServletRequest req, Member mem, Company comp, Event event, String statShort)
		{
		
			Set retrunVal = new HashSet (0);
			 
			 List theTypes = getCompStockTypeDAO().getDefaultStockListByCompany(comp.getId().toString(),statShort);
			 
			 MemberHoldingsMovement memberHoldingMovement = null;
			 
			 for(Object typeKey: theTypes)
			 {
				 memberHoldingMovement = new  MemberHoldingsMovement();
				 CompStockType finObj = (CompStockType)typeKey;
				 
				 String val = "0";
                                 
                                 if(finObj.getRegisterStock().equalsIgnoreCase("Y"))
                                 {
                                    val = StockInterface.REGISTERSTOCKVALUE;
                                 }
                                 else
                                 {                                       
                                   val =req.getParameter(finObj.getCompStockName());
                                 }
				 
				 memberHoldingMovement.setCompany(comp);
				 memberHoldingMovement.setDelFlg(ActivityInterface.UNDELFLAG);
				 memberHoldingMovement.setEvent(event);
				 memberHoldingMovement.setMovementHoldings(Double.valueOf(val));
				 memberHoldingMovement.setMovementType(StockInterface.ADDSTOCK);
				 memberHoldingMovement.setMember(mem);
				 memberHoldingMovement.setCompStockType(finObj);
			
				 retrunVal.add(memberHoldingMovement);
				 
				 memberHoldingMovement = null;
			 }
			
			
		 return retrunVal;
		}
                
                /**Necessary for Batch process creation of member for Register stocks
                 * 
                 * @param mem
                 * @param comp
                 * @param event
                 * @param statShort
                 * @return 
                 */
                public Set buildDefaultRegisterHoldingsMovement(Member mem, Company comp, Event event, String statShort)
		{
		
			Set retrunVal = new HashSet (0);
			 
			 List theTypes = getCompStockTypeDAO().getDefaultRegisterStockListByCompany(comp.getId().toString(),statShort);
			 
			 MemberHoldingsMovement memberHoldingMovement = null;
			 
			 for(Object typeKey: theTypes)
			 {
					
				 memberHoldingMovement = new  MemberHoldingsMovement();
				 CompStockType finObj = (CompStockType)typeKey;
				 
				 String val = StockInterface.REGISTERSTOCKVALUE;
				 
				 memberHoldingMovement.setCompany(comp);
				 memberHoldingMovement.setDelFlg(ActivityInterface.UNDELFLAG);
				 memberHoldingMovement.setEvent(event);
				 memberHoldingMovement.setMovementHoldings(Double.valueOf(val));
				 memberHoldingMovement.setMovementType(StockInterface.ADDSTOCK);
				 memberHoldingMovement.setMember(mem);
				 memberHoldingMovement.setCompStockType(finObj);
			
				 retrunVal.add(memberHoldingMovement);
				 
				 memberHoldingMovement = null;
			 }
			
			
		 return retrunVal;
		}
                
                 /**For Batch process upload of stock Information
                  * 
                  * @param mem
                  * @param event
                  * @param typeKey
                  * @param val
                  * @param buySellIndicator
                  * @return 
                  */
               
                public Set buildBatchHoldingsMovement(Member mem, Event event, Object typeKey, Double val, String buySellIndicator)
		{
		
			Set retrunVal = new HashSet (0);
			 
			 //List theTypes = getCompStockTypeDAO().getDefaultStockListByCompany(comp.getId().toString(),statShort);
			 
			 MemberHoldingsMovement memberHoldingMovement = null;
			 
			 //for(Object typeKey: theTypes)
			 //{
				 memberHoldingMovement = new  MemberHoldingsMovement();
				 CompStockType finObj = (CompStockType)typeKey;
				 
				 //String val = req.getParameter(finObj.getCompStockName());
				 
				 memberHoldingMovement.setCompany(finObj.getCompany());
				 memberHoldingMovement.setDelFlg(ActivityInterface.UNDELFLAG);
				 memberHoldingMovement.setEvent(event);
				 memberHoldingMovement.setMovementHoldings(Math.abs(val));
				 memberHoldingMovement.setMovementType(buySellIndicator);
				 memberHoldingMovement.setMember(mem);
				 memberHoldingMovement.setCompStockType(finObj);
			
				 retrunVal.add(memberHoldingMovement);
				 
				 //memberHoldingMovement = null;
			 //}
			
			
		 return retrunVal;
		}
		
		
	//get memberHoldings
		
	/**
	 * @param movementReq
	 * @return
	 * @throws Exception
	 */
          public  Map<String, Object> aggregateHoldings(MemberHoldingsMovement movementReq) throws Exception
		{
		
		 Map<String, Object>  retrunVal = new HashMap<String, Object>();
		 
		 java.util.Date today = new java.util.Date();
		
		 double theMove = 0;
		 
		 MemberHoldings holdings = null;
			 
		 holdings= getMemberHoldingsDAO().getAvailableHoldings(movementReq.getMember().getMemberId(), movementReq.getCompany().getId(), movementReq.getCompStockType().getCompStockTypeId());
		 
		 if(holdings !=null)
		 {
			 
			if(!movementReq.getCompStockType().getRegisterStock().equalsIgnoreCase("Y"))
                        {
                            if(movementReq.getMovementType().equals(StockInterface.ADDSTOCK))
                            {
                                    theMove =  holdings.getHoldings() + movementReq.getMovementHoldings();
                            }
                            else 
                            {
                                    theMove =  holdings.getHoldings() - movementReq.getMovementHoldings();
                            }
                        }
                        else
                        {
                          theMove = Double.parseDouble(StockInterface.REGISTERSTOCKVALUE);
                        }
			 
			 holdings.setHoldings(theMove);
			 
			 retrunVal.put(StockInterface.DOUPDATE, holdings);
		 }
		 else
		 {
			 
			 holdings = new MemberHoldings();
			 
			 holdings.setCompanyId(movementReq.getCompany().getId());	
			 holdings.setCreatedBy("");
			// holdings.setCreatedDate(today.);
			
			 holdings.setMemberId(movementReq.getMember().getMemberId());
			 holdings.setStockId(movementReq.getCompStockType().getCompStockTypeId());
			 
                        if(!movementReq.getCompStockType().getRegisterStock().equalsIgnoreCase("Y"))
                        {
                            if(movementReq.getMovementType().equals(StockInterface.ADDSTOCK))
                            {
                                    holdings.setHoldings(movementReq.getMovementHoldings());
                            }
                            else 
                            {
                                    holdings.setHoldings(0-movementReq.getMovementHoldings());
                            }
                        }
                        else
                        {
                             holdings.setHoldings(Double.parseDouble(StockInterface.REGISTERSTOCKVALUE));
                        }
		
			 retrunVal.put(StockInterface.DOINSERT, holdings);
		 }
		 return retrunVal;
		}
	
	
	/**
	 * @param companyId
	 * @param memberCode
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	
	private MemberVote getMemberVote(String companyId, String memberCode) throws NumberFormatException, Exception
	{
		double memVotes = 0;
		
		MemberVote memberVote = new MemberVote();
		
		//find member
		System.out.println("memberCode :: " +  memberCode);
		Member member = (Member)(memberDAO.findByMemberCode(memberCode).get(0));
		
		//get Company Stocks 
		List<Object[]> compStocks = compStockTypeDAO.getStockListByCompany(companyId, apprvdStockStat);
		
		
		for(Object[] obj : compStocks)
		{
			
			//get stocks definition
			Map<String,String> stockPpty = genericConfigDAO.getStockPropertyListingConfig(companyId, ((Integer)obj[0]).toString());
			
			//get memberStocks
			MemberHoldings holding = memberHoldingsDAO.getAvailableHoldings(member.getMemberId(), Integer.parseInt(companyId), (Integer)obj[0]);
		    
			//do evaluation
				 if(stockPpty.get("CANVOTE").equals("Y"))
				 {
					 
					 if(stockPpty.get("VOTEFORMULA").equals("Y"))
					 {
						 
						 String formExpression = stockPpty.get("VOTERIGHTPROP");
						 
						 JEP myParser = new JEP();
						 
						 myParser.addStandardFunctions();
						 myParser.addStandardConstants();
						 
						 List<Map<String,Object>> operands = genericConfigDAO.getFormulaOperand("STOCK");
						 
						 for(Map<String,Object> map : operands)
						 {
							 
							 if(formExpression.indexOf((String)map.get("operand_val"))!=-1)
							 {
								 //Class  objClass = Class.forName((String)map.get("operand_obj"));
								 
								 myParser.addVariable((String)map.get("operand_val"), holding.getHoldings());
								 
							 }
						 }
						 
						 myParser.parseExpression(formExpression);
						 
						 memVotes = memVotes +  myParser.getValue();
					 }
					 else
					 {
						 memVotes = memVotes + Double.parseDouble(stockPpty.get("VOTERIGHTPROP"));
						 
					 }
				 }
		}
		
		// returns the closest floating-point value to a that is equal to a mathematical integer.
		
		double voteInt =Math.rint(memVotes);
		
		memberVote.setCompanyCode(member.getCompany().getCode());
		memberVote.setMemberCode(member.getMemberNo());
		memberVote.setMemberName(member.getFirstName() + " " + member.getMiddleName() + " " + member.getSurname());
		memberVote.setVote( Double.toString(voteInt));
		memberVote.setMemberType(member.getMemberType().getMemberTypeVal());
		
		
	 return memberVote;
	}
	
	
	
	/**
	 * @param compCode
	 * @return
	 */
	public MemberVotes getCompanyMemberVote(String compCode)
	{
		
		System.out.println("Here inside servkce");
		MemberVotes retrunList = null;
		
	    try
	    {
			retrunList = new MemberVotes();
			
			
			List<MemberVote> theVotes = new ArrayList<MemberVote>();
			
			if(getCompanyDAO()==null)
			{
				
				System.out.println("DAO is Null");
			}
			
			Company comp = (Company)getCompanyDAO().findByProperty("code", compCode).get(0);

			//getMembers
			List<Object[]> memList = memberDAO.getMemberListByStatus(comp.getId().toString(), apprvMemberStatusShort, null);
			
			
			for(Object[] mem : memList)
			{
				MemberVote	memberVote = getMemberVote(comp.getId().toString(), (String)mem[3]);
				theVotes.add(memberVote);
			}
			
			retrunList.setCompanyCode(compCode);
			retrunList.setMemberVote(theVotes);
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	
	 return retrunList;
	}
	
	
	/**
	 * @param compCode
	 * @param memCode
	 * @return
	 */
	public MemberVote findMemberVote(String compCode, String memCode)
	{
		
		MemberVote retrunList = null;
		
	    try
	    {
			retrunList = new MemberVote();
			
			Company comp = (Company)companyDAO.findByProperty("code", compCode).get(0);

			retrunList = getMemberVote(comp.getId().toString(), memCode);
	
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			retrunList = new MemberVote();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			retrunList = new MemberVote();
		}
			
	 return retrunList;
	}
        
        
        public java.util.Date getEffectiveStockDate(java.util.Date dateOfInitiation)
        {
         return customutil.getNewDate(dateOfInitiation, Integer.parseInt(effStockDateDiff));    
        }
        
        
        public java.util.Date getRegisterStockDate(java.util.Date dateOfInitiation)
        {
         return customutil.getNewDate(dateOfInitiation, Integer.parseInt(effRegisterStockDateDiff));    
        }
        
	
	
	public String getApprvdStockStat() {
		return apprvdStockStat;
	}
	public void setApprvdStockStat(String apprvdStockStat) {
		this.apprvdStockStat = apprvdStockStat;
	}
	public GenericConfigDAO getGenericConfigDAO() {
		return genericConfigDAO;
	}
	public void setGenericConfigDAO(GenericConfigDAO genericConfigDAO) {
		this.genericConfigDAO = genericConfigDAO;
	}
	public String getApprvMemberStatusShort() {
		return apprvMemberStatusShort;
	}
	public void setApprvMemberStatusShort(String apprvMemberStatusShort) {
		this.apprvMemberStatusShort = apprvMemberStatusShort;
	}

}
