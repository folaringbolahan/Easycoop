package com.sift.financial.utility;

import com.sift.financial.member.*;
import com.sift.financial.member.ws.client.*;
import com.sift.gl.NewSerialno;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Company;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import com.sift.financial.services.ManagementTempl;
import com.sift.financial.utility.*;
import java.text.DecimalFormatSymbols;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SiftFinancialUtil {
    
    private static final Log log = LogFactory.getLog(SiftFinancialUtil.class);
    

	public String getUserAddress(HttpServletRequest request)
	{
		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		   if (ipAddress == null) {  
			   ipAddress = request.getRemoteAddr();  
		   }
		
	  return ipAddress;
	}
	

	public java.util.Date getUserLocalTime()
	{
		return null;
	}
        
     
        public Entrys getTransaction(Map<String, Object> headerInfo, List<Map<String, Object>> bodyInfo, CurrentuserdisplayImpl user, String refType) throws Exception
        {
         Entrys entrys = null;
         Txnsheader txnHeader = new Txnsheader();
         List<Entry> entryLegs = null;
         
      
	 NewSerialno vSerial = new NewSerialno();
         String theSerial = vSerial.returnSerialnostr(refType, user.getCurruser().getBranch(), user.getCurruser().getCompanyid());
         Company compDet = user.getCompanydetails(user.getCurruser().getBranch(), user.getCurruser().getCompanyid());
         
         if(headerInfo!=null && !headerInfo.isEmpty())
         {
            try
            {
                entrys = new Entrys();

                txnHeader.setBranchId(user.getCurruser().getBranch());
                txnHeader.setCompanyId(user.getCurruser().getCompanyid());
                txnHeader.setDocRef((String)headerInfo.get(TransactionInterface.HEADERDOCREF));
                txnHeader.setEntrydate(compDet.getEntryDate());
                //txnHeader.setHeaderId(Integer.SIZE);
                txnHeader.setHeadernarrative((String)headerInfo.get(TransactionInterface.HEADERNARATIVE));
                txnHeader.setPeriod(compDet.getCurrentPeriod());
                txnHeader.setPostdate(compDet.getPostDate());
                
                txnHeader.setTxnDate(compDet.getPostDate());
                txnHeader.setTxnSerial(theSerial);
                txnHeader.setTxnType(refType);
                txnHeader.setUserId(user.getCurruser().getUserId());
                txnHeader.setYear(compDet.getCurrentYear());
                txnHeader.setTimezone(compDet.getTimezone());


                entrys.setTxnsheader(txnHeader);
            }
            catch(Exception ex)
            {
                  throw new Exception("Invalid transaction header......" + ex.getMessage());
            }
         }
         else
         {
              throw new Exception("Invalid transaction header......EMPTY");
         }
         
         if(bodyInfo!=null && !bodyInfo.isEmpty() /*&& entrys!=null*/)
         {
             System.out.println("debug stage 20");
             ///Entry ent1 = new Entry("WTDA1","TXCr","0003222r","2010-10-10","Ref0922r" ,"HRef0373r", "CASH WITHDRAWAL", "07774", -10893.08, -10764.54, 2.0, "User Waler", "02",2015,4,7);
             ///Entry ent2 = new Entry("WTDA2","TXCr","0003222b","2010-10-10","Ref0922r" ,"HRef0373r", "CASH WITHDRAWAL", "07774", 10893.08, 10764.54, 2.0, "User Waler", "02",2015,4,7);
             entryLegs = new ArrayList<Entry>();
              
             for(Map<String, Object> theMap : bodyInfo)
             {
                  System.out.println("debug stage 21");
                //Entry(String txnType,String txnCode,String accountNo,String valueDate,String docref ,String headerdocref, String narrative, String txnSerial, double ccyAmount, double amount, double rate, String userId, Integer branchId,Integer year,Integer period,Integer company)
                Double amount = (Double)theMap.get(TransactionInterface.AMOUNT);
                Double ccyAmount =(Double)theMap.get(TransactionInterface.CCYAMOUNT);
                String docref=(String)theMap.get(TransactionInterface.DOCREF);
                String headerdocref =(String)headerInfo.get(TransactionInterface.HEADERDOCREF);
                String narrative= (String)theMap.get(TransactionInterface.NARATIVE);
                Double rate = Double.parseDouble((String)theMap.get(TransactionInterface.RATE));
                
                 DateFormat dateFormat = new SimpleDateFormat(TransactionInterface.VALIUEDATE_FORMAT);
                 String valueDate = dateFormat.format((Date)theMap.get(TransactionInterface.VALUEDATE));
                 //String valueDate = "2015-10-18";
                 
                // System.out.println("user.getCurruser().getUserId() ::" + user.getCurruser().getUserId());
                 //System.out.println("theSerial :::: " + theSerial);
                // System.out.println("(String)theMap.get(TransactionInterface.TXNTYPE) ::" + (String)theMap.get(TransactionInterface.TXNTYPE));
                // System.out.println("(String)theMap.get(TransactionInterface.TXNCODE) ::" + (String)theMap.get(TransactionInterface.TXNCODE));
                
               //  System.out.println("(String)theMap.get(TransactionInterface.ACCOUNTNO) ::" + (String)theMap.get(TransactionInterface.ACCOUNTNO));
               //  System.out.println("valueDate ::" + valueDate);
                 
               //  System.out.println("docref ::" + docref);
               //  System.out.println("headerdocref ::" + headerdocref);
               //   System.out.println("narrative ::" + narrative);
               //  System.out.println("ccyAmount ::" + ccyAmount);
                 
               //  System.out.println("amount ::" + amount);
                // System.out.println("rate ::" + rate);
                // System.out.println("user.getCurruser().getUserId() ::" + user.getCurruser().getUserId());
                // System.out.println("user.getCurruser().getBranch() ::" + user.getCurruser().getBranch());
                 
               //  System.out.println("compDet.getCurrentYear() ::" + compDet.getCurrentYear());
               //  System.out.println("compDet.getCurrentPeriod() ::" + compDet.getCurrentPeriod());
                 
               //   System.out.println("user.getCurruser().getCompanyid() ::" + user.getCurruser().getCompanyid());
                
                 
                Entry  entry = new  Entry((String)theMap.get(TransactionInterface.TXNTYPE),(String)theMap.get(TransactionInterface.TXNCODE),(String)theMap.get(TransactionInterface.ACCOUNTNO),valueDate, docref , headerdocref,  narrative, theSerial, ccyAmount, amount, rate, user.getCurruser().getUserId(), user.getCurruser().getBranch(),compDet.getCurrentYear(),compDet.getCurrentPeriod(),user.getCurruser().getCompanyid());
                 
                entryLegs.add(entry);
             }
             
             entrys.setEntrys(entryLegs);
         }
         else
         {
            throw new Exception("Invalid transaction entries......EMPTY");
         }
	   
            return entrys;
	}
        
        
     
        public Entrys getTransaction(Map<String, Object> headerInfo, List<Map<String, Object>> bodyInfo, Map<String, Object> user, String refType) throws Exception
        {
         Entrys entrys = null;
         Txnsheader txnHeader = new Txnsheader();
         List<Entry> entryLegs = null;
         
         Map<String, String> Companydetails = (Map<String, String>)user.get("Companydetails");
         Map<String, String> Curruser = (Map<String, String>)user.get("Curruser");
         
          log.info(" user :::" + user);
          log.info(" Companydetails :::: " + Companydetails);
          log.info(" Curruser :::: " + Curruser);
          
	 NewSerialno vSerial = new NewSerialno();
         String theSerial = vSerial.returnSerialnostr(refType, Integer.parseInt((String)Curruser.get("branch_id")), Integer.parseInt((String)Curruser.get("company_id")));
         
         //Company compDet = user.getCompanydetails((String)user.get("branch"), (String)user.get("company"));
         log.info( "theSerial ===:: " + theSerial);
         
         if(headerInfo!=null && !headerInfo.isEmpty())
         {  
             //Set theKeys = Companydetails.keySet();
             if(Companydetails!=null)
             {
                for(Object key: Companydetails.keySet())
                {
                   String keyStr = (String)key;
                    log.info(keyStr + "===:: " + Companydetails.get(keyStr));
                }
             }
             else
             {
                log.info("Companydetails===:: i am very null");
             }
             
             if(Curruser!=null)
             {
                for(Object key: Curruser.keySet())
                {
                   String keyStr = (String)key;
                    log.info(keyStr + "===:: " + Curruser.get(keyStr));
                }
             }
             else
             {
                log.info("Curruser===:: i am very null");
             }
             
            
            try
            {
                entrys = new Entrys();
                
                log.info("branch_id ===:: " + Companydetails.get("branch_id"));
                txnHeader.setBranchId(Integer.parseInt(Companydetails.get("branch_id")));
                log.info("company_id ===:: " + Companydetails.get("company_id"));
                txnHeader.setCompanyId(Integer.parseInt(Companydetails.get("company_id")));
                
                txnHeader.setDocRef((String)headerInfo.get(TransactionInterface.HEADERDOCREF));
                log.info("entry_date===:: " + Companydetails.get("entry_date"));
                txnHeader.setEntrydate(customutil.getjavadate(Companydetails.get("entry_date"),TransactionInterface.VALIUEDATE_FORMAT));
                //txnHeader.setHeaderId(Integer.SIZE);
                txnHeader.setHeadernarrative((String)headerInfo.get(TransactionInterface.HEADERNARATIVE));
                 log.info("current_period===:: " + Companydetails.get("current_period"));
                txnHeader.setPeriod(Integer.parseInt(Companydetails.get("current_period")));
                 log.info("post_date===:: " + Companydetails.get("post_date"));
                txnHeader.setPostdate(customutil.getjavadate(Companydetails.get("post_date"),TransactionInterface.VALIUEDATE_FORMAT));
                log.info("post_date===:: " + Companydetails.get("post_date"));
                txnHeader.setTxnDate(customutil.getjavadate(Companydetails.get("post_date"),TransactionInterface.VALIUEDATE_FORMAT));
                txnHeader.setTxnSerial(theSerial);
                txnHeader.setTxnType(refType);
                 log.info("user_id===:: " + Curruser.get("userid"));
                txnHeader.setUserId(Curruser.get("userid"));
       
                log.info("current_year===:: " + Companydetails.get("current_year"));
                txnHeader.setYear(Integer.parseInt(Companydetails.get("current_year")));
                log.info("timez===:: " + Companydetails.get("timez"));
                txnHeader.setTimezone(Companydetails.get("timez"));
                
                entrys.setTxnsheader(txnHeader);
                
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                throw new Exception("Invalid transaction header......" + ex.getMessage());
            }
         }
         else
         {
              throw new Exception("Invalid transaction header......EMPTY");
         }
         
         if(bodyInfo!=null && !bodyInfo.isEmpty() /*&& entrys!=null*/)
         {
             ///Entry ent1 = new Entry("WTDA1","TXCr","0003222r","2010-10-10","Ref0922r" ,"HRef0373r", "CASH WITHDRAWAL", "07774", -10893.08, -10764.54, 2.0, "User Waler", "02",2015,4,7);
             ///Entry ent2 = new Entry("WTDA2","TXCr","0003222b","2010-10-10","Ref0922r" ,"HRef0373r", "CASH WITHDRAWAL", "07774", 10893.08, 10764.54, 2.0, "User Waler", "02",2015,4,7);
             entryLegs = new ArrayList<Entry>();
              
             for(Map<String, Object> theMap : bodyInfo)
             {
                //Entry(String txnType,String txnCode,String accountNo,String valueDate,String docref ,String headerdocref, String narrative, String txnSerial, double ccyAmount, double amount, double rate, String userId, Integer branchId,Integer year,Integer period,Integer company)
                Double amount = (Double)theMap.get(TransactionInterface.AMOUNT);
                Double ccyAmount =(Double)theMap.get(TransactionInterface.CCYAMOUNT);
                String docref=(String)theMap.get(TransactionInterface.DOCREF);
                String headerdocref =(String)headerInfo.get(TransactionInterface.HEADERDOCREF);
                String narrative= (String)theMap.get(TransactionInterface.NARATIVE);
                Double rate = Double.parseDouble((String)theMap.get(TransactionInterface.RATE));
                
                 DateFormat dateFormat = new SimpleDateFormat(TransactionInterface.VALIUEDATE_FORMAT);
                 String valueDate = dateFormat.format((Date)theMap.get(TransactionInterface.VALUEDATE));
                 //String valueDate = "2015-10-18";
                 
                // System.out.println("user.getCurruser().getUserId() ::" + user.getCurruser().getUserId());
                // System.out.println("theSerial :::: " + theSerial);
                // System.out.println("(String)theMap.get(TransactionInterface.TXNTYPE) ::" + (String)theMap.get(TransactionInterface.TXNTYPE));
                // System.out.println("(String)theMap.get(TransactionInterface.TXNCODE) ::" + (String)theMap.get(TransactionInterface.TXNCODE));
                
                // System.out.println("(String)theMap.get(TransactionInterface.ACCOUNTNO) ::" + (String)theMap.get(TransactionInterface.ACCOUNTNO));
                // System.out.println("valueDate ::" + valueDate);
                 
                // System.out.println("docref ::" + docref);
                // System.out.println("headerdocref ::" + headerdocref);
                //  System.out.println("narrative ::" + narrative);
               //  System.out.println("ccyAmount ::" + ccyAmount);
                 
               //  System.out.println("amount ::" + amount);
               //  System.out.println("rate ::" + rate);
               //  System.out.println("user.getCurruser().getUserId() ::" + user.getCurruser().getUserId());
              //   System.out.println("user.getCurruser().getBranch() ::" + user.getCurruser().getBranch());
                 
               //  System.out.println("compDet.getCurrentYear() ::" + compDet.getCurrentYear());
               //  System.out.println("compDet.getCurrentPeriod() ::" + compDet.getCurrentPeriod());
                 
                //  System.out.println("user.getCurruser().getCompanyid() ::" + user.getCurruser().getCompanyid());
                Entry  entry = new  Entry((String)theMap.get(TransactionInterface.TXNTYPE),(String)theMap.get(TransactionInterface.TXNCODE),(String)theMap.get(TransactionInterface.ACCOUNTNO),valueDate, docref , headerdocref,  narrative, theSerial, ccyAmount, amount, rate, Curruser.get("user_id"), Integer.parseInt(Companydetails.get("branch_id")),Integer.parseInt(Companydetails.get("current_year")),Integer.parseInt(Companydetails.get("current_period")),Integer.parseInt(Companydetails.get("company_id")));
                 
                entryLegs.add(entry);
             }
             
             entrys.setEntrys(entryLegs);
         }
         else
         {
            throw new Exception("Invalid transaction entries......EMPTY");
         }
	   
            return entrys;
	}
        
        
        //Stock
        public Map<String, Object> buildTransactionObjects(MemberHoldingsMovement moveObj, Event event, String txnCode,CurrentuserdisplayImpl user, Date valDate, String refType)
        {
            Date today = new Date();
            
            Map<String, Object> returnVal = new HashMap<String, Object>();
           
           //build header
            Map<String, Object> headObj = new HashMap<String, Object>();
            headObj.put(TransactionInterface.HEADERDOCREF, event.getEventShort());
            headObj .put(TransactionInterface.HEADERNARATIVE, event.getEventName()+"||==||" + moveObj.getCompStockType().getShortName());
            //System.out.println(" TransactionInterface.HEADERDOCREF " + headObj.get(TransactionInterface.HEADERDOCREF).toString());
            //System.out.println(" TransactionInterface.HEADERNARATIVE " + headObj.get(TransactionInterface.HEADERNARATIVE).toString());
           //set headerInfo
            
            returnVal.put(TransactionInterface.HEADERINFO, headObj);
            
            //buildBody
            List<Map<String, Object>>  mainBodyObj = new ArrayList<Map<String, Object>>( );
             
            moveObj.getCompStockType().getCompStockTypeDetails();
            
           Set theEvtProps  =  event.getEventProperties();
           
           //System.out.println(" event " + event.getEventName() + " " + event.getEventShort() + " " + event.getEventStockMove() + " " + event.getEventId() + " "  + event.getEventProperties().size());
           
           int n = 0;
           for(Object evtProp: theEvtProps)
           {
              Map<String, Object> bodyObj = new HashMap<String, Object>();
              n = n+1;
               EventProperty prop = (EventProperty)evtProp;
                bodyObj.put(TransactionInterface.TXNTYPE, refType);
                bodyObj.put(TransactionInterface.TXNCODE, txnCode);
                bodyObj.put(TransactionInterface.ACCOUNTNO, getAccount(moveObj.getCompStockType(),prop.getEventPptyShort()));
                bodyObj.put(TransactionInterface.DOCREF, event.getEventShort());
                bodyObj.put(TransactionInterface.NARATIVE, event.getEventName()+"||==||" + moveObj.getCompStockType().getShortName());
                bodyObj.put(TransactionInterface.RATE, "1");
                Double theAmt = getAmount(moveObj,prop.getEventPptyAmt(),user);
                
                 //todo 
               // bodyObj.put(TransactionInterface.VALUEDATE, valDate);
                bodyObj.put(TransactionInterface.VALUEDATE, user.getCurrusercompany().getPostDate());
             
                if (prop.getEventPptyValue().equals("D"))
                {
                    bodyObj.put(TransactionInterface.CCYAMOUNT,-theAmt);
                    bodyObj.put(TransactionInterface.AMOUNT,-theAmt);
                }
                
                 if (prop.getEventPptyValue().equals("C"))
                {
                    bodyObj.put(TransactionInterface.CCYAMOUNT,theAmt);
                    bodyObj.put(TransactionInterface.AMOUNT,theAmt);
                }
                 
                  if(theAmt > 0.00 || theAmt < 0.00 )
                  {
                    System.out.println("n==" + n);
                    mainBodyObj.add(bodyObj);
                  }
           }
            returnVal.put(TransactionInterface.BODYINFO, mainBodyObj);  
           
      
        return returnVal;
        }
        
        
        public Map<String, Object> buildTransactionObjects(MemberHoldingsMovement moveObj, Event event, String txnCode, Map<String,Object> user, Date valDate, String refType)
        {
            Date today = new Date();
            
            log.info("Heer inside buildTransactionObjects  statrting");
            
            Map<String, Object> returnVal = new HashMap<String, Object>();
            log.info("Heer inside buildTransactionObjects  getting CompanyDetails");
            Map<String, String> Companydetails = (Map<String, String>)user.get("Companydetails");
            
            if(Companydetails !=null)
            {
                for(String keyStr : Companydetails.keySet())
                {
                 log.info(keyStr + " buildTransactionObjects  statrting" + Companydetails.get(keyStr));
                }
            }
            else
            {
                log.info(" buildTransactionObjects  statrting Companydetails is null" );
            }
            
            log.info("Heer inside buildTransactionObjects  getting Curruser");
            Map<String, String> Curruser = (Map<String, String>)user.get("Curruser");
            
            
             if(Curruser !=null)
            {
                for(String keyStr : Curruser.keySet())
                {

                 log.info(keyStr + " buildTransactionObjects  statrting" + Curruser.get(keyStr));

                }
            }
            else
            {
                log.info(" buildTransactionObjects  statrting Curruser is null" );
            }
             
           //build header
            Map<String, Object> headObj = new HashMap<String, Object>();
            
             log.info("event buildTransactionObjects  statrting  " + event.getEventShort());
             
            headObj.put(TransactionInterface.HEADERDOCREF, event.getEventShort());
            headObj .put(TransactionInterface.HEADERNARATIVE, event.getEventName()+"||==||" + moveObj.getCompStockType().getShortName());
            
           //set headerInfo
            
            returnVal.put(TransactionInterface.HEADERINFO, headObj);
            
            //buildBody
            List<Map<String, Object>>  mainBodyObj = new ArrayList<Map<String, Object>>( );
             
            //moveObj.getCompStockType().getCompStockTypeDetails();
            
           Set theEvtProps  =  event.getEventProperties();
           int n = 0;
           for(Object evtProp: theEvtProps)
           {
              Map<String, Object> bodyObj = new HashMap<String, Object>();
              n = n+1;
               EventProperty prop = (EventProperty)evtProp;
             
                bodyObj.put(TransactionInterface.TXNTYPE, refType);
                bodyObj.put(TransactionInterface.TXNCODE, txnCode);
                bodyObj.put(TransactionInterface.ACCOUNTNO, getAccount(moveObj.getCompStockType(),prop.getEventPptyShort()));
                bodyObj.put(TransactionInterface.DOCREF, event.getEventShort());
                bodyObj.put(TransactionInterface.NARATIVE, event.getEventName()+"||==||" + moveObj.getCompStockType().getShortName());
                bodyObj.put(TransactionInterface.RATE, "1");
                Double theAmt = getAmount(moveObj,prop.getEventPptyAmt());
                
                 //todo 
               // bodyObj.put(TransactionInterface.VALUEDATE, valDate);
                bodyObj.put(TransactionInterface.VALUEDATE, customutil.getjavadate((String)Companydetails.get("post_date"),TransactionInterface.VALIUEDATE_FORMAT));
             
                if (prop.getEventPptyValue().equals("D"))
                {
                    bodyObj.put(TransactionInterface.CCYAMOUNT,-theAmt);
                    bodyObj.put(TransactionInterface.AMOUNT,-theAmt);
                }
                
                 if (prop.getEventPptyValue().equals("C"))
                {
                    bodyObj.put(TransactionInterface.CCYAMOUNT,theAmt);
                    bodyObj.put(TransactionInterface.AMOUNT,theAmt);
                }
                 
                  if(theAmt > 0.00 || theAmt < 0.00 )
                  {
                    System.out.println("n==" + n);
                    mainBodyObj.add(bodyObj);
                  }
           }
            
           returnVal.put(TransactionInterface.BODYINFO, mainBodyObj);  
           
      log.info("returnVal  :::; " + returnVal.size());
      
        return returnVal;
        }
        
        
       public Map<String, Object> buildTransactionObjects(Dividend moveObj, Event event, String txnCode,CurrentuserdisplayImpl user, Date valDate, String refType)
        {
            Date today = new Date();
            
            Map<String, Object> returnVal = new HashMap<String, Object>();
           
           //build header
            Map<String, Object> headObj = new HashMap<String, Object>();
            
            headObj.put(TransactionInterface.HEADERDOCREF, event.getEventShort());
            headObj .put(TransactionInterface.HEADERNARATIVE, event.getEventName()+"||==||" + moveObj.getDivYear() + "||==||" + moveObj.getDivPeriod());
            
           //set headerInfo
            
            returnVal.put(TransactionInterface.HEADERINFO, headObj);
            
            //buildBody
            List<Map<String, Object>>  mainBodyObj = new ArrayList<Map<String, Object>>( );
             
            //moveObj.getCompStockType().getCompStockTypeDetails();
            
           Set theEvtProps  =  event.getEventProperties();
           int n = 0;
           for(Object evtProp: theEvtProps)
           {
              Map<String, Object> bodyObj = new HashMap<String, Object>();
              n = n+1;
               EventProperty prop = (EventProperty)evtProp;
             
                bodyObj.put(TransactionInterface.TXNTYPE, refType);
                bodyObj.put(TransactionInterface.TXNCODE, txnCode);
                bodyObj.put(TransactionInterface.ACCOUNTNO, getAccount(moveObj,prop.getEventPptyShort()));
                bodyObj.put(TransactionInterface.DOCREF, event.getEventShort());
                bodyObj.put(TransactionInterface.NARATIVE, event.getEventName()+"||==||" + moveObj.getDivYear() + "||==||" + moveObj.getDivPeriod());
                bodyObj.put(TransactionInterface.RATE, "1");
                Double theAmt = getAmount(moveObj,prop.getEventPptyAmt(),user);
                
                 //todo 
               // bodyObj.put(TransactionInterface.VALUEDATE, valDate);
                bodyObj.put(TransactionInterface.VALUEDATE, user.getCurrusercompany().getPostDate());
             
                if (prop.getEventPptyValue().equals("D"))
                {
                    bodyObj.put(TransactionInterface.CCYAMOUNT,-theAmt);
                    bodyObj.put(TransactionInterface.AMOUNT,-theAmt);
                }
                
                 if (prop.getEventPptyValue().equals("C"))
                {
                    bodyObj.put(TransactionInterface.CCYAMOUNT,theAmt);
                    bodyObj.put(TransactionInterface.AMOUNT,theAmt);
                }
                 
                if(theAmt > 0.00 || theAmt < 0.00 )
                {
                  System.out.println("n==" + n);
                  mainBodyObj.add(bodyObj);
                }
           }
            
           returnVal.put(TransactionInterface.BODYINFO, mainBodyObj);  
           
      
        return returnVal;
        }
        
        //STOCK
        private String getAccount(CompStockType comp, String indicator)
        {
        
            String targetAccount = null;
            
            switch (indicator)
            {
            case TransactionInterface.CASH:
                targetAccount = comp.getStockCashAcct();
                break;
           case TransactionInterface.PAR: 
                targetAccount = comp.getStockParAcct();
                break;
           case TransactionInterface.EXCESS:
                targetAccount = comp.getStockExcessAcct();
                break;
            default:
                targetAccount = comp.getStockTreasuryAcct();
                break;
            }
            
        return targetAccount;
        }
        
        //Dividend
        private String getAccount(Dividend comp, String indicator)
        {
            String targetAccount = null;
            
            System.out.println("indicator ::: " + indicator);
            
            switch (indicator)
            {
            case TransactionInterface.CASH:
                targetAccount = comp.getDivCashAcct();
                break;
           case TransactionInterface.EARNING: 
                targetAccount = comp.getDivRetainedEarningsAcct();
                break;
           case TransactionInterface.TRANSIT:
                targetAccount = comp.getDivPayableAcct();
                break;
            default:
                targetAccount = "";
                break;
            }
             System.out.println("targetAccount ::: " + targetAccount);
        return targetAccount;
        }
        
       
        //Stock
        private Double getAmount(MemberHoldingsMovement moveObj, String indicator, CurrentuserdisplayImpl user)
        {
        
             Double amount = null;
             Double stockCost = 0.0;
             Double stockParValue = 0.0;
             
             Set details = moveObj.getCompStockType().getCompStockTypeDetails();
             
             for(Object det: details)
             {
                CompStockTypeDetail detail = (CompStockTypeDetail)det;
                
                if(detail.getCompStockProperty().getStockPptyName().equals("UNITCOST"))
                {
                   stockCost = Double.parseDouble(detail.getCompStockPptyVal());
                }
               
                if(detail.getCompStockProperty().getStockPptyName().equals("PARVALUE"))
                {
                   stockParValue = Double.parseDouble(detail.getCompStockPptyVal());
                }
           
             }
           
            switch (indicator)
            {
            case TransactionInterface.ALL:
                amount = stockCost * moveObj.getMovementHoldings();
                break;
           case TransactionInterface.PAR: 
                amount = stockParValue * moveObj.getMovementHoldings();
                break;
           case TransactionInterface.ALL_PARAMT:
                amount = (stockCost - stockParValue) * moveObj.getMovementHoldings();
                break;
           case TransactionInterface.NILL:
                amount = 0.00;
                break;
            default:
                amount = 0.00;
                break;
            }
            
           DecimalFormat decimalFormat = new DecimalFormat(TransactionInterface.COMMA_SEPERATED);
           
           //DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
           //df.setMaximumFractionDigits(340);
            
        return Double.parseDouble(decimalFormat.format(amount));
        }
        
        
        private Double getAmount(MemberHoldingsMovement moveObj, String indicator)
        {
        
             Double amount = null;
             Double stockCost = 0.0;
             Double stockParValue = 0.0;
             
             Set details = moveObj.getCompStockType().getCompStockTypeDetails();
             
             for(Object det: details)
             {
                CompStockTypeDetail detail = (CompStockTypeDetail)det;
                
                if(detail.getCompStockProperty().getStockPptyName().equals("UNITCOST"))
                {
                   stockCost = Double.parseDouble(detail.getCompStockPptyVal());
                }
               
                if(detail.getCompStockProperty().getStockPptyName().equals("PARVALUE"))
                {
                   stockParValue = Double.parseDouble(detail.getCompStockPptyVal());
                }
             }
           
            switch (indicator)
            {
            case TransactionInterface.ALL:
                amount = stockCost * moveObj.getMovementHoldings();
                break;
           case TransactionInterface.PAR: 
                amount = stockParValue * moveObj.getMovementHoldings();
                break;
           case TransactionInterface.ALL_PARAMT:
                amount = (stockCost - stockParValue) * moveObj.getMovementHoldings();
                break;
           case TransactionInterface.NILL:
                amount = 0.00;
                break;
            default:
                amount = 0.00;
                break;
            }
            
           DecimalFormat decimalFormat = new DecimalFormat(TransactionInterface.COMMA_SEPERATED);
           
            log.info("Formating Amount ::::: " + decimalFormat.format(amount));
            
        return Double.parseDouble(decimalFormat.format(amount));
        }
        
        //Dividend
        private Double getAmount(Dividend moveObj, String indicator, CurrentuserdisplayImpl user)
        {
        
            Double amount = null;

            switch (indicator)
            {
            case TransactionInterface.ALL:
                amount = moveObj.getDivPayable();
                break;
            case TransactionInterface.NILL:
                amount = 0.00;
                break;
            default:
                amount = 0.00;
                break;
            }
            
         DecimalFormat decimalFormat = new DecimalFormat(TransactionInterface.COMMA_SEPERATED);
            
        return Double.parseDouble(decimalFormat.format(amount));
        }
        
        //added to cater for Dividend Payment Final
        
        public Map<String, Object> buildTransactionObjects(Dividend moveObj, Event event, String txnCode, Map<String,Object> user, Date valDate, String refType)
        {
            Date today = new Date();
            
            Map<String, Object> returnVal = new HashMap<String, Object>();
            
             log.info("Heer inside buildTransactionObjects  getting CompanyDetails");
            Map<String, String> Companydetails = (Map<String, String>)user.get("Companydetails");
            
            if(Companydetails !=null)
            {
                for(String keyStr : Companydetails.keySet())
                {
                 log.info(keyStr + " buildTransactionObjects  statrting" + Companydetails.get(keyStr));
                }
            }
            else
            {
                log.info(" buildTransactionObjects  statrting Companydetails is null" );
            }
            
            log.info("Here inside buildTransactionObjects  getting Curruser");
            Map<String, String> Curruser = (Map<String, String>)user.get("Curruser");
            
            
             if(Curruser !=null)
            {
                for(String keyStr : Curruser.keySet())
                {
                 log.info(keyStr + " buildTransactionObjects for Div Payment  statrting" + Curruser.get(keyStr));
                }
            }
            else
            {
                log.info(" buildTransactionObjects for Div Payment stating Curruser is null" );
            }
            
          
           //build header
            Map<String, Object> headObj = new HashMap<String, Object>();
            
            headObj.put(TransactionInterface.HEADERDOCREF, event.getEventShort());
            headObj .put(TransactionInterface.HEADERNARATIVE, event.getEventName()+"||==||" + moveObj.getDivYear() + "||==||" + moveObj.getDivPeriod());
            
           //set headerInfo
            
            returnVal.put(TransactionInterface.HEADERINFO, headObj);
            
            //buildBody
            List<Map<String, Object>>  mainBodyObj = new ArrayList<Map<String, Object>>( );
             
            //moveObj.getCompStockType().getCompStockTypeDetails();
            
           Set theEvtProps  =  event.getEventProperties();
           int n = 0;
           for(Object evtProp: theEvtProps)
           {
              Map<String, Object> bodyObj = new HashMap<String, Object>();
              n = n+1;
               EventProperty prop = (EventProperty)evtProp;
             
                bodyObj.put(TransactionInterface.TXNTYPE, refType);
                bodyObj.put(TransactionInterface.TXNCODE, txnCode);
                bodyObj.put(TransactionInterface.ACCOUNTNO, getAccount(moveObj,prop.getEventPptyShort()));
                bodyObj.put(TransactionInterface.DOCREF, event.getEventShort());
                bodyObj.put(TransactionInterface.NARATIVE, event.getEventName()+"||==||" + moveObj.getDivYear() + "||==||" + moveObj.getDivPeriod());
                bodyObj.put(TransactionInterface.RATE, "1");
                Double theAmt = getAmount(moveObj,prop.getEventPptyAmt());
                
                 //todo 
               // bodyObj.put(TransactionInterface.VALUEDATE, valDate);
                bodyObj.put(TransactionInterface.VALUEDATE, customutil.getjavadate((String)Companydetails.get("post_date"),TransactionInterface.VALIUEDATE_FORMAT));
             
                if (prop.getEventPptyValue().equals("D"))
                {
                    bodyObj.put(TransactionInterface.CCYAMOUNT,-theAmt);
                    bodyObj.put(TransactionInterface.AMOUNT,-theAmt);
                }
                
                 if (prop.getEventPptyValue().equals("C"))
                {
                    bodyObj.put(TransactionInterface.CCYAMOUNT,theAmt);
                    bodyObj.put(TransactionInterface.AMOUNT,theAmt);
                }
                 
                if(theAmt > 0.00 || theAmt < 0.00 )
                {
                  System.out.println("n==" + n);
                  mainBodyObj.add(bodyObj);
                }
           }
            
           returnVal.put(TransactionInterface.BODYINFO, mainBodyObj);  
           
      
        return returnVal;
        }
        
        
        //Dividend
        private Double getAmount(Dividend moveObj, String indicator)
        {
        
            Double amount = null;

            switch (indicator)
            {
            case TransactionInterface.ALL:
                amount = moveObj.getDivPayable();
                break;
            case TransactionInterface.NILL:
                amount = 0.00;
                break;
            default:
                amount = 0.00;
                break;
            }
            
         DecimalFormat decimalFormat = new DecimalFormat(TransactionInterface.COMMA_SEPERATED);
            
        return Double.parseDouble(decimalFormat.format(amount));
        }
}
