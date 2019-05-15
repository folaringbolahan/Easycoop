/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.benefits.schedule;

import com.sift.financial.GenericConfigDAO;
import com.sift.financial.member.CompStockProperty;
import com.sift.financial.member.CompStockType;
import com.sift.financial.member.CompStockTypeDetail;
import com.sift.financial.member.Dividend;
import com.sift.financial.member.DividendSchedule;
import com.sift.financial.member.OperandInterface;
import com.sift.financial.services.ManagementTempl;
import com.sift.financial.utility.MemberContent;
import com.singularsys.jep.Jep;
import com.singularsys.jep.JepException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author baydel200
 */
public class BenefitScheduleService extends ManagementTempl {
    
    private static final Log log = LogFactory.getLog(BenefitScheduleService.class);
    
    private ApplicationContext ctx;
    
    @Autowired
    @Value("${apprvMemberStatusShort}")
    private String memberApprdStatus;
    
    
    @Autowired
    @Value("${apprvdStockStat}")
    private String stockApprdStatus;
     
    @Autowired
    @Value("${targetDivPpty}")
    private String targetStockPpt;
    
    @Autowired
    @Value("${defaultTaxPercent}")
    private Double defaultTaxPercent;
    
    @Autowired
    @Value("${stckPlaceHolder}")
    private String stckPlace;
    
    @Autowired
    @Value("${contPlaceHolder}")
    private String contrPlace;

    @Autowired
    @Value("${stockQuery}")
    private String stockQuery;
    
    @Autowired
    @Value("${contriQuery}")
    private String contriQuery;
    
    private String msg;
    private int stockidinuse = 0;
     
    //private GenericConfigDAO genericConfigDAO;
    
    /**
     * get all qualified Stocks for company
     * 
     * */
    public  List getQualifiedStocks(Dividend object)
    {
        List finalStockList = new ArrayList<CompStockType>();
        
        List allStocks = getCompStockTypeDAO().getAllValidStockListByCompany(object.getCompanyId().toString(), stockApprdStatus);
    
        for(Object compStk : allStocks)
        {
           Set theProps = ((CompStockType)compStk).getCompStockTypeDetails();
           
           for(Object compDet: theProps)
           {
              CompStockTypeDetail compDetObj = (CompStockTypeDetail)compDet;
              CompStockProperty ppty = compDetObj.getCompStockProperty();
              
              if(ppty.getStockPptyName().equals(targetStockPpt))
              {
                  if(compDetObj.getCompStockPptyVal().equals("Y"))
                  {
                    finalStockList.add(compStk);
                  }
                  break;
              }
           }
        }
       
     return finalStockList;
    }

   /**
     * get All Members based on record date
     * 
     * */
    public  List getMemberByRecordDate(Dividend object)
    {
         List tempMembers = null;
         try
         {
             tempMembers = getMemberDao().getMemberByRecordDate(object, memberApprdStatus);
             
             if(tempMembers!=null && tempMembers.size()==0)
             {
              setMsg(" No qualifying Member retrieved ");
             }
             else
             {
              setMsg(" No qualifying Member retrieved ");
             }
             
         }
         catch(Exception ex)
         {
         setMsg("Error encountered :: " + ex.getMessage());
         }
     return  tempMembers;
    }
    

   /**
     * do processing for Dividend with formula
     * 
     * */
    public List<DividendSchedule>  processObjects(Object dataList,Dividend fileInfo, Double taxInfo)
    {
        List<DividendSchedule> finalDivSchedule = new ArrayList<DividendSchedule> ();
        
        List<Object[]> theList = (List<Object[]>)dataList;
        
             
        for (Object[] obj : theList)
        {
            DividendSchedule newDiv = new DividendSchedule();
            
            Double defaultGrossVal  = null;
            Double defaultTaxVal = null;
            Double defaultNetVal = null;

           
            defaultGrossVal   = Double.parseDouble(fileInfo.getDivValue());
            
            defaultTaxVal = (defaultGrossVal * defaultTaxPercent)/100;
            defaultNetVal = defaultGrossVal-defaultTaxVal;
            
            newDiv.setBranchCode(fileInfo.getBranchId().toString());
            newDiv.setCompanyCode(fileInfo.getCompanyId().toString());
            newDiv.setDividendGrossValue(defaultGrossVal);
            newDiv.setDividendId(fileInfo.getDividendId());
            newDiv.setDividendNetValue(defaultNetVal);
            newDiv.setDividendTax(defaultTaxVal);
            newDiv.setDividendTaxValue(defaultTaxVal);
            newDiv.setDividendType(fileInfo.getDividendType().getDividendTypeName());
            newDiv.setEffectiveDate(null);
            newDiv.setMemberId((Integer)obj[0]);
            newDiv.setMemberNo((String)obj[3]);
            newDiv.setPaid("N");
            newDiv.setPaidDate(null);
            newDiv.setPayChannel(null);
            newDiv.setStockShort(null);
            
            if(fileInfo.getCreatedBy()!=null)
            {
                newDiv.setCreatedBy(fileInfo.getCreatedBy());
            }
            if(fileInfo.getCreatedDate()!=null)
            {
                newDiv.setCreatedDate(fileInfo.getCreatedDate());
            }
            
            if(fileInfo.getModifiedBy()!=null)
            {
                newDiv.setModifiedBy(fileInfo.getModifiedBy());
            }
            if(fileInfo.getModifiedDate()!=null)
            {
                newDiv.setModifiedDate(fileInfo.getModifiedDate());
            }
            
            if(fileInfo.getApprovedBy()!=null)
            {
                newDiv.setApprovedBy(fileInfo.getApprovedBy());
            }
            if(fileInfo.getApprovedDate()!=null)
            {
                newDiv.setApprovedDate(fileInfo.getApprovedDate());
            }
            
            
            finalDivSchedule.add(newDiv);
        }
        
        if(finalDivSchedule.size()==0)
        {
         setMsg("No Final Dividend retrieved");
        }

        return finalDivSchedule;
    }
    
    
    /**
     * do processing for Dividend with formula
     * 
     * */
    public List<DividendSchedule>  processObjects(Object dataList,Dividend fileInfo, Double taxInfo, boolean isFormulated)
    {
        List<DividendSchedule> finalDivSchedule = new ArrayList<DividendSchedule> ();
        
        List<Map<String,Object>>  operands = getGenericConfigDAO().getFormulaOperand("DIV");
        
        List stockList = null;
        stockidinuse = 0;       
        if(fileInfo.getDivValue().indexOf(stckPlace)> 0)
        {
                  stockList = getQualifiedStocks(fileInfo);   
        }
        
        List<Object[]> theList = (List<Object[]>)dataList;
        
             
        for (Object[] obj : theList)
        {
            /**
             * Works only when there are qualified stocks
             */
            if(stockList!=null && stockList.size()>0)
            {
                for(Object comp : stockList)
                {
                    DividendSchedule newDiv = null;
                    stockidinuse = ((CompStockType)comp).getCompStockTypeId();
                    newDiv =  biuldSchedule(fileInfo, (CompStockType)comp, obj, operands);
                    
                    if(newDiv != null && newDiv.getDividendGrossValue() > 0.0)
                    {
                        finalDivSchedule.add(newDiv);
                    }
                }
            }
            else
            {
             /**
             * Works only when there are no qualified stocks
             */
                DividendSchedule newDiv = null; 
                newDiv =  biuldSchedule(fileInfo, null, obj,operands);
                if(newDiv != null && newDiv.getDividendGrossValue() > 0.0)
                {
                     finalDivSchedule.add(newDiv);
                }
            }
        }

        if(finalDivSchedule.size()==0)
        {
          setMsg("No Final Dividend retrieved");
        }
        
        return finalDivSchedule;
    }
   
   /**
     * Write out dividend schedule
     * 
     * */
    public boolean writeObjects(List dataList, Dividend divInfo) 
    {
        
        boolean returnVal = false;
        double totalDivPayable = 0.0;
        Session  sess = null;
        Transaction tx = null;
        if (dataList !=null)
        {
            try
            {
            sess = getDividendDAO().getSessionFactory().openSession();
            
             tx = sess.beginTransaction();
            
            for(Object rec : dataList)
            {
               DividendSchedule currSch = (DividendSchedule)rec;
               
               totalDivPayable = totalDivPayable + currSch.getDividendGrossValue();
               
               getDividendScheduleDAO().save(currSch, sess);
            }
            
                divInfo.setActualDivPayable(totalDivPayable);
                divInfo.setDivPayable(totalDivPayable);
            
                getDividendDAO().merge(divInfo, sess);
                returnVal = true;
                tx.commit();
            }
            catch(Exception ex)
            {
               returnVal = false;
                setMsg("Error Writing Objects : " + ex.getMessage());
               ex.printStackTrace();
               tx.rollback();
            }
            finally
            {
                sess.close();
            }
        }
       
        return returnVal;
    }
    
    
    public Object evaluateDivFormula (Object memberRecord, Dividend divObj, List<Map<String,Object>>  operands)
    {
        
        Object returnval = 0.0;
    
        if (divObj.getFormula().equals("Y"))
        {   
            Jep jep = new Jep();
                    try 
                    {
                        for(Map<String,Object> curr : operands)
                        {
                           MemberContent memberContent = new MemberContent();
                           
                           memberContent.setMemberContributionDAO(getMemberContributionDAO());
                           memberContent.setMemberHoldingsDAO(getMemberHoldingsDAO());
                           memberContent.setContrPlace(contrPlace);
                           memberContent.setStckPlace(stckPlace);
                           memberContent.setContribQueryHolder(contriQuery);
                           memberContent.setStockQueryHolder(stockQuery);
                          
                           Object[] theArray = new Object[5];
                           theArray[0] = (String)curr.get("operand_val");
                           /*
                           theArray[1] = ((Object[])memberRecord)[0];
                           theArray[2] = ((Object[])memberRecord)[1];
                           theArray[3] = ((Object[])memberRecord)[2];                   
                           */
                           
                           /////try and see if it works - confirm from ba
                           Map<String,Object> otherdivinfo = new HashMap<String,Object>(); 
                           //pls correct next line
                           otherdivinfo.put(OperandInterface.PRODINFOCUSID, stockidinuse);
                           otherdivinfo.put(OperandInterface.EFFECTIVEDATE, divObj.getDivDateRecord());
                           ///////
                           
                           theArray[1] = ((Object[])memberRecord)[0];
                           theArray[2] = ((Object[])memberRecord)[3];
                           ///theArray[3] = ((Object[])memberRecord)[5]; 
                           theArray[3] = otherdivinfo;
                           
                           memberContent.setBranchId((Integer)((Object[])memberRecord)[10]);
                           memberContent.setCompanyId((Integer)((Object[])memberRecord)[11]);
                           
                           memberContent.setTheVariables(theArray);
                           
             /*
                            String type = (String)theArray[0];
                              System.out.println("type = " + type);
                            Integer memberId = (Integer)theArray[1];
                              System.out.println("memberId = " + memberId);
                            String memberNo = (String)theArray[2];
                              System.out.println("memberNo = " + memberNo);
                            Object others =  theArray[3];
                           */
                           
                           String type = (String)theArray[0];
                              System.out.println("type = " + type);
                            Integer memberId = (Integer)theArray[1];
                              System.out.println("memberId = " + memberId);
                            String memberNo = (String)theArray[2];
                              System.out.println("memberNo = " + memberNo);
                            Object others =  theArray[3];
                           
                           Object returnVal = null;
                           
                           try
                           {
                                    if(type.equals(stckPlace))
                                    {
                                          System.out.println("divObj.getDivValue() Type = " + stckPlace + "  "+  type.equals(stckPlace));
                                           returnVal =  memberContent.getMemberHoldingsValue(memberId, memberNo, others);
                                        ///  System.out.println("divObj.getDivValue() = " + returnVal);
                                    }
                                    else if(type.equals(contrPlace))
                                    {
                                         System.out.println("divObj.getDivValue() Type = " + contrPlace + "  "+ type.equals(contrPlace));
                                           returnVal = memberContent.getMemberContribValue(memberId, memberNo, others);
                                        ///   System.out.println("divObj.getDivValue() = " + returnVal);
                                    }
                                    else
                                    {
                                        returnVal = 0.0;
                                    }
                           }
                           catch(Exception ex)
                           {
                                 System.out.println("failing here ============.getDivValue() = ");
                           returnVal = 0.0;
                           }
                            
                            //jep.addFunction((String)curr.get("operand_val"), memberContent);
                         ///   System.out.println("adding variable  = " + ((String)curr.get("operand_val")).replaceAll("\\[|\\]", ""));
                             jep.addVariable(((String)curr.get("operand_val")).replaceAll("\\[|\\]", ""), Double.parseDouble(returnVal.toString()));
                         ////   System.out.println("adding variable value   =  = " + returnVal);
                            
                            memberContent = null;
                           
                            
                            try {
                                jep.parse(divObj.getDivValue().replaceAll("\\[|\\]", ""));
                                returnval = jep.evaluate();
                            } catch (JepException e) {
                                System.out.println("An error occurred1: " + divObj.getDivValue().replaceAll("\\[|\\]", "") + " " + e.getMessage());
                            }
                        }
                        /*
                        jep.parse(divObj.getDivValue());
                    
                        returnval = jep.evaluate();
                       */
                  // System.out.println("divObj.getDivValuelast() = " + returnval);
                   
                   } catch (JepException e) 
                   {
                     System.out.println("An error occurred: " + e.getMessage());
                   }
           
        }
        else
        {
        
        returnval = 0.0;
        }
        
        
  
     return returnval;
    }
  
    private DividendSchedule biuldSchedule(Dividend fileInfo, CompStockType compStock,Object[] obj, List<Map<String,Object>>  operands)
    {
    
         DividendSchedule newDiv = new DividendSchedule();

                    Double defaultGrossVal  = 0.0;
                    Double defaultTaxVal = null;
                    Double defaultNetVal = null;

                    if(fileInfo.getFormula().equals("N"))
                    {
                        defaultGrossVal   = Double.parseDouble(fileInfo.getDivValue());
                    }
                    else
                    {
                        //evaluate expression
                        //defaultGrossVal = (Double)evaluateDivFormula(obj, fileInfo,operands);
                         // defaultGrossVal = (Double)evaluateDivFormula(obj, fileInfo,operands);
                        
                  /****  Vector elements = (java.util.Vector)evaluateDivFormula(obj, fileInfo,operands);
                    Enumeration e=elements.elements();
                    
                    if (e.hasMoreElements())
                    {         
                         System.out.println("Number = " + e.nextElement());
                         defaultGrossVal = (Double)e.nextElement();
                    } 
                    */
                     try {   
                       defaultGrossVal = Double.parseDouble((evaluateDivFormula(obj, fileInfo,operands)).toString());
                     }
                     catch(Exception ex) 
                     {
                       defaultGrossVal =0.0; 
                     }
                        
                    }
                    defaultTaxVal = (defaultGrossVal * defaultTaxPercent)/100;
                    defaultNetVal = defaultGrossVal-defaultTaxVal;

                    newDiv.setBranchCode(fileInfo.getBranchId().toString());
                    newDiv.setCompanyCode(fileInfo.getCompanyId().toString());
                    newDiv.setDividendGrossValue(defaultGrossVal);
                    newDiv.setDividendId(fileInfo.getDividendId());
                    newDiv.setDividendNetValue(defaultNetVal);
                    newDiv.setDividendTax(defaultTaxVal);
                    newDiv.setDividendTaxValue(defaultTaxVal);
                    newDiv.setDividendType(fileInfo.getDividendType().getDividendTypeName());
                    newDiv.setEffectiveDate(null);
                    newDiv.setMemberId((Integer)obj[0]);
                    newDiv.setMemberNo((String)obj[3]);
                    newDiv.setPaid("N");
                    newDiv.setPaidDate(null);
                    newDiv.setPayChannel(null);
                    newDiv.setStockShort(null);
                    
                    if(compStock!=null)
                    {
                        newDiv.setStockShort(compStock.getShortName());
                    }
                    
                     if(fileInfo.getCreatedBy()!=null)
            {
                newDiv.setCreatedBy(fileInfo.getCreatedBy());
            }
            if(fileInfo.getCreatedDate()!=null)
            {
                newDiv.setCreatedDate(fileInfo.getCreatedDate());
            }
            
            if(fileInfo.getModifiedBy()!=null)
            {
                newDiv.setModifiedBy(fileInfo.getModifiedBy());
            }
            if(fileInfo.getModifiedDate()!=null)
            {
                newDiv.setModifiedDate(fileInfo.getModifiedDate());
            }
            
            if(fileInfo.getApprovedBy()!=null)
            {
                newDiv.setApprovedBy(fileInfo.getApprovedBy());
            }
            if(fileInfo.getApprovedDate()!=null)
            {
                newDiv.setApprovedDate(fileInfo.getApprovedDate());
            }
    
    
    return newDiv;
    
    }
    
    
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    // This section handles Dividend payment
    
     public List getQualifiedDivDefault(Date date, String status) {
         
        List returnVal = null;
      
          try
            {
               returnVal =   getDividendDAO().getDividendByPayDate(date, status);
            }
            catch(Exception ex)
            {
               returnVal = null;
               setMsg("Error retriving Objects Lsit : " + ex.getMessage());
               ex.printStackTrace();
            }
            finally
            {
              
            }
            
         return returnVal;
     }
    
    
}
