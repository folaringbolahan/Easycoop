/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.schedule;

import com.sift.financial.GenericConfigDAO;
import com.sift.financial.member.ActivityInterface;
import com.sift.financial.member.BatchMember;
import com.sift.financial.member.BatchStock;
import com.sift.financial.member.MemberHoldings;
import com.sift.financial.member.MemberHoldingsDAO;
import com.sift.financial.member.MemberHoldingsMovementDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author baydel200
 */
public class BatchStockValidator {
    
    
    private static final Log log = LogFactory.getLog(BatchStockValidator.class);
    
    private Integer companyId;
    private Integer branchId;
    private Integer countryId;
    private String  createdBy;
    
    @Autowired
    private GenericConfigDAO genericConfigDAO;
    
    @Autowired
    @Value("${DEF-CON-PRODTYPE}")
    private String prodType;
    
    private MemberHoldingsMovementDAO memberHoldingsMovementDAO;
    private MemberHoldingsDAO memberHoldingsDAO;
    
 
    public List<BatchStock> doValidation(String[] validatedObj, List<Map<String,Object>> mapColumns)
    {
      
      log.info("validatedObj length:: " + validatedObj.length);
        
      List<BatchStock> theList = null;
            
      int theLength = validatedObj.length;
      
      StringBuffer errStr1 = new StringBuffer();
     
       String theMainId =  "";
       
       Integer memberId = null;
       
      
               if(validatedObj[0].equals("")  || validatedObj[0]==null)
               {
                     errStr1.append("|Email: Is null");
                     theMainId= "";
               }
               else
               {
                             String query=" select email_add_1 as email_id, first_name as first_name, cast(member_id as CHAR) as member_id  " +
                             " from member a, status b where a.status_id= b.status_id and company_id='" +companyId+ "' and email_add_1='" +validatedObj[0]+ "' "
                             + "  and status_short ='APR-MEMBER'";

                        Map<String,String> theObject = getGenericConfigDAO().getObjectRead(query);

                        if(theObject == null || theObject.size()==0)
                        {
                             errStr1.append("|Email: Does not exists"); 
                             
                        }
                        else
                        {
                             log.info("Updating member Id::: here");
                             memberId  = Integer.parseInt(theObject.get("member_id"));
                             log.info("Updated member Id::: here" + memberId);
                            
                        }
                        
                        theMainId = validatedObj[0];
               }
               
        //run through the stocks
         theList = new ArrayList<BatchStock> ();
         
         log.info("i::: starting loop");
         
         for (int i = 1; i < validatedObj.length; i++) {
        {
            log.info("i::: " + i);
            String localError = "";
             StringBuffer errStr = new StringBuffer();
            
             BatchStock batchStock = new BatchStock();
             
             batchStock.setEmail1(theMainId);
             
            Map<String,Object> curStockMap =  mapColumns.get(i-1);
            
             if(((String)curStockMap.get("defStock")).equalsIgnoreCase("Y"))
             {
                  if (validatedObj[i].equals(""))
                  {
                        errStr.append("|" + (String)curStockMap.get("code") + ": is required ");
                  }
                  else
                  {
                  
                     try
                     {
                        Double.parseDouble(validatedObj[i]);
                     
                     }
                     catch(Exception ex)
                     {
                       errStr.append("|" + (String)curStockMap.get("code") + ": Value must be a number ");
                     }
                  } 
             }
             
             //get stock property
             Map<String,String> props = genericConfigDAO.getStockProperty(((Integer)curStockMap.get("stock_id")).toString());
             
             log.info("props keysets " +  props.keySet());
             
             for(String key : props.keySet())
             {
                log.info("props key " +  key);
                log.info("props value " + props.get(key));
             }
             
             batchStock.setStockShort((String)curStockMap.get("code"));
             
             
             
             //find if stock already exists
            //TODO
            //Include validation to check that when stock exists and it's added value is negative then the
            //available can cater for the added available value
            
             MemberHoldings existingHolding = null;
             
             if(memberId !=null)
             {
                    try
                    {
                    existingHolding = getMemberHoldingsDAO().getAvailableHoldings(memberId, companyId, (Integer)curStockMap.get("stock_id"));
                    }
                    catch(Exception ex)
                    {
                        log.info("Exception for Stock : " + (String)curStockMap.get("code") + " and Member: " +  theMainId + "  ::: "+ ex.getMessage());
                    }
             }
            
            if(existingHolding !=null)
            {
                batchStock.setRecAction(ActivityInterface.RECORDEXIST);
            }
            else
            { batchStock.setRecAction(ActivityInterface.RECORDEMPTY);}
            
             
             boolean doubleValueOk = false;
             Double  theValue = 0d;
             
             try
             {
                 theValue = Double.parseDouble(validatedObj[i]);
                 doubleValueOk = true;
             } 
             catch(Exception ex)
             {
                 log.info("Exception while evaluating double " + ex.getMessage());
                 
                 errStr.append("|" + (String)curStockMap.get("code") + ": Value must be a number ");
                 
                doubleValueOk= false;
             }
             //check Max value
            if (!validatedObj[i].equals("") && doubleValueOk)
            {
                  
                //Double theValue = Double.parseDouble(validatedObj[i]);
                
                Double stockMin = Double.parseDouble(props.get("MINVALUE"));
                
                Double stockMax = Double.parseDouble(props.get("MAXVALUE"));
                
              if(existingHolding ==null)
              {      
                    if (theValue.compareTo(stockMin) < 0)
                    {
                     errStr.append("|").append((String)curStockMap.get("code")).append(": less than minimum allowed ");
                    }
              }
                
                 if (theValue.compareTo(stockMax)> 0)
                {
                  errStr.append("|").append((String)curStockMap.get("code")).append(": More than maximum allowed ");
                }
                 else
                 {
                        if(existingHolding !=null)
                        { 
                            Double newValue = theValue + existingHolding.getHoldings();
                            
                             if (newValue.compareTo(stockMax)> 0)
                             {
                                errStr.append("|").append((String)curStockMap.get("code")).append(":Aggregate is more than maximum allowed ");
                             }
                        }
                 
                 }

            }
            
            batchStock.setStockValue(theValue);

            if(errStr1.length()>0 && errStr.length()>0)
            {
             localError = errStr1.toString() +  errStr.toString();
            }
            else if(errStr1.length()== 0 && errStr.length()>0)
            {
                localError =  errStr.toString();
            }
            else if(errStr1.length()> 0 && errStr.length()==0)
            {
                localError =  errStr1.toString();
            }
            
            if(!localError.equals(""))
            {
                batchStock.setValError(localError);
                batchStock.setHasErrors(true);
            }
            
            theList.add(batchStock);
    }
        
    }
        
        return theList;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public GenericConfigDAO getGenericConfigDAO() {
        return genericConfigDAO;
    }

    public void setGenericConfigDAO(GenericConfigDAO genericConfigDAO) {
        this.genericConfigDAO = genericConfigDAO;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public MemberHoldingsMovementDAO getMemberHoldingsMovementDAO() {
        return memberHoldingsMovementDAO;
    }

    public void setMemberHoldingsMovementDAO(MemberHoldingsMovementDAO memberHoldingsMovementDAO) {
        this.memberHoldingsMovementDAO = memberHoldingsMovementDAO;
    }

    public MemberHoldingsDAO getMemberHoldingsDAO() {
        return memberHoldingsDAO;
    }

    public void setMemberHoldingsDAO(MemberHoldingsDAO memberHoldingsDAO) {
        this.memberHoldingsDAO = memberHoldingsDAO;
    }
    

}
