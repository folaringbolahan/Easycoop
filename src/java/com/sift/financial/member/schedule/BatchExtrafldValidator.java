/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.schedule;

import com.sift.financial.GenericConfigDAO;
import com.sift.financial.member.ActivityInterface;
import com.sift.financial.member.BatchExtrafldEntries;
import com.sift.financial.member.BatchMember;
import com.sift.financial.member.BatchStock;
import com.sift.financial.member.MemberHoldings;
import com.sift.financial.utility.customutil;
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
public class BatchExtrafldValidator {
    
    private static final Log log = LogFactory.getLog(BatchExtrafldValidator.class);
    
    private Integer companyId;
    private Integer branchId;
    private Integer countryId;
    private String  createdBy;
    
    @Autowired
    private GenericConfigDAO genericConfigDAO;
    
    @Autowired
    @Value("${DEF-CON-PRODTYPE}")
    
    private String prodType;
    
       
    public List<BatchExtrafldEntries> doValidation(String[] validatedObj,List<Map<String,Object>> mapFlds)
    {
    
    log.info("validatedObj length:: " + validatedObj.length);
        
      List<BatchExtrafldEntries> theList = null;
            
      int theLength = validatedObj.length;
      
      StringBuffer errStr = new StringBuffer();
     
       String theMainId =  "";
       String memberId  = null;
       boolean batchError = false;
       String typeId = "N";
       
       //Integer memberId = null;
       
      
               if(validatedObj[0].equals("")  || validatedObj[0]==null)
               {
                     errStr.append("|Email: Is null");
                     theMainId= "";
               }
               else
               {
                        String query=" select email_add_1 as email_id, first_name as first_name, cast(member_id as CHAR) as member_id, member_no as member_no" +
                        " from member a, status b where a.status_id= b.status_id and company_id='" +companyId+ "' and email_add_1='" +validatedObj[0].toLowerCase()+ "' "
                        + "  and status_short ='APR-MEMBER'";

                        Map<String,String> theObject = getGenericConfigDAO().getObjectRead(query);

                        if(theObject == null || theObject.size()==0)
                        {
                             errStr.append("|Email: Does not exists"); 
                        }
                        else
                        {
                            //memberId = theObject.get("member_id") + ":::" + theObject.get("member_no");
                            memberId  = theObject.get("member_id");
                        }
                        
                        theMainId = validatedObj[0];
               }
               
               
             
        //run through the extra Fields
         theList = new ArrayList<> ();
         
         log.info("i::: starting loop");
         
         
         for (int i = 1; i < validatedObj.length; i++) {
      
            log.info("i::: " + i);
            
            BatchExtrafldEntries batchAdd = new BatchExtrafldEntries();
             
            batchAdd.setEmail1(theMainId);
            
             
            Map<String,Object> addFldName =  mapFlds.get(i-1);
            
            typeId = (String)addFldName.get("optional");
            batchAdd.setTypeId("N");
             if(((String)addFldName.get("optional")).equalsIgnoreCase("Y"))
             {
                  if (validatedObj[i].equals(""))
                  {
                        errStr.append("|" + (String)addFldName.get("code") + ": is required ");
                  }
                  else
                  {
                  // a step further is to make sure the numbers are actually within the ids for the cooprative
                     try
                     {
                        Double.parseDouble(validatedObj[i]);
                        batchAdd.setExtrFieldoptValue(validatedObj[i]);
                        batchAdd.setTypeId("G");
                     }
                     catch(Exception ex)
                     {
                       errStr.append("|" + (String)addFldName.get("code") + ": Value must be a number ");
                     }
                  } 
             }
             else
             {
                 if (validatedObj[i].equals(""))
                 {
                  errStr.append("|").append((String)addFldName.get("descr")).append(": is required ");
                 }
                 //batchAdd.setTypeId("N");
             }    
            
            //set Address Field Name
            batchAdd.setExtrFieldName((String)addFldName.get("descr"));
            // set Serial Position of Address Field name
            String serpos = (addFldName.get("code")).toString();
            batchAdd.setSerialPos(serpos);
            batchAdd.setExtrFieldId(Integer.parseInt(addFldName.get("code").toString()));
            //batchAdd.setSerialPos(((Integer)addFldName.get("code")).toString());
                        
            
            
          batchAdd.setExtrFieldValue(validatedObj[i].equals("")?"":validatedObj[i].toUpperCase());
              
          Map<String, String> existingAddEntry = null;
          
          if(memberId !=null)
          {
                batchAdd.setKeyId(memberId);
          }
          
         // if(typeId!=null)
         // {
         //       batchAdd.setTypeId(typeId);
         // }
             
             if(memberId !=null && !validatedObj[i].equals(""))
             {
                    try
                    {
                       //existingAddEntry = getGenericConfigDAO().getAddressEntry(typeId,memberId,validatedObj[i]);
                        //  validatedObj[i] == value not name field - get in touch with B to correct
                        String dcode = (String)addFldName.get("code");
                        if (dcode.contains("_OPT")==true)
                        {
                            dcode = dcode.substring(0,dcode.trim().length()-4);
                        }    
                        existingAddEntry = getGenericConfigDAO().getExtrafldEntry(memberId,dcode);
                    }
                    catch(Exception ex)
                    {
                        log.info("Exception for Extra Field : " + validatedObj[i] + " and Member: " +  theMainId + "  ::: "+ ex.getMessage());
                    }
             }
            
            if(existingAddEntry !=null)
            {
                batchAdd.setRecAction(ActivityInterface.RECORDEXIST);
            }
            else
            { 
                batchAdd.setRecAction(ActivityInterface.RECORDEMPTY);
            }

            
            if(errStr.length()>0)
            {
                batchAdd.setValError(errStr.toString());
                batchAdd.setHasErrors(true);
                batchError = true;
            }
            
          theList.add(batchAdd);
    }
    
    //Must fail all if one entry has issues. All must go in as a batch
    if(batchError)
    {
           for(int i =0; i<theList.size(); i++)
           {
                BatchExtrafldEntries newAdd =  theList.get(i);
                newAdd.setHasErrors(batchError);
                theList.set(i, newAdd);
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
    
    
}
