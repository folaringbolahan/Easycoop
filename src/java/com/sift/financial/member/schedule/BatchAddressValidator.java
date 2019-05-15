/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.schedule;

import com.sift.financial.GenericConfigDAO;
import com.sift.financial.member.ActivityInterface;
import com.sift.financial.member.BatchAddressEntries;
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
public class BatchAddressValidator {
    
    private static final Log log = LogFactory.getLog(BatchAddressValidator.class);
    
    private Integer companyId;
    private Integer branchId;
    private Integer countryId;
    private String  createdBy;
    
    @Autowired
    private GenericConfigDAO genericConfigDAO;
    
    @Autowired
    @Value("${DEF-CON-PRODTYPE}")
    
    private String prodType;
    
       
    public List<BatchAddressEntries> doValidation(String[] validatedObj,List<Map<String,Object>> mapAddressFlds)
    {
    
    log.info("validatedObj length:: " + validatedObj.length);
        
      List<BatchAddressEntries> theList = null;
            
      int theLength = validatedObj.length;
      
      StringBuffer errStr = new StringBuffer();
     
       String theMainId =  "";
       String memberId  = null;
       boolean batchError = false;
       String typeId = null;
       
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
               
               
               if(validatedObj[theLength-1].equals("") || validatedObj[theLength-1]==null)
               {
                     errStr.append("|AddressType: Is empty Check SheetName");
                     //theMainId= "";
               }
               else
               {
                        String query=" select cast(id as CHAR) as id, type_name as type_name  " +
                        " from address_type  where type_name='" +validatedObj[theLength-1].toUpperCase()+ "'";

                        Map<String,String> theObject = getGenericConfigDAO().getObjectRead(query);

                        if(theObject == null || theObject.size()==0)
                        {
                            errStr.append("|AddressType: Is Invalid. Check Excel SheetName"); 
                        }
                        else
                        {
                            typeId = theObject.get("id");
                        }
                        
                        //theMainId = validatedObj[0];
               }

        //run through the address Fields
         theList = new ArrayList<> ();
         
         log.info("i::: starting loop");
         
         
         for (int i = 1; i < validatedObj.length-1; i++) {
      
            log.info("i::: " + i);
            
            BatchAddressEntries batchAdd = new BatchAddressEntries();
             
            batchAdd.setEmail1(theMainId);
            
             
            Map<String,Object> addFldName =  mapAddressFlds.get(i-1);
            
            //set Address Field Name
            batchAdd.setAddrFieldName((String)addFldName.get("descr"));
            // set Serial Position of Address Field name
            batchAdd.setSerialPos((addFldName.get("code")).toString());
            //batchAdd.setSerialPos(((Integer)addFldName.get("code")).toString());
                        
            if (validatedObj[i].equals(""))
            {
                  errStr.append("|").append((String)addFldName.get("descr")).append(": is required ");
            }
            else
            {
               try
               {
                  Double.parseDouble(validatedObj[i]);

                  errStr.append("|").append((String)addFldName.get("descr")).append(": Value cannot be a number ");
               }
               catch(Exception ex)
               {
                log.info((String)addFldName.get("descr") + " is valid");
               }
            } 
            
          batchAdd.setAddrFieldValue(validatedObj[i].equals("")?"":validatedObj[i].toUpperCase());
              
          Map<String, String> existingAddEntry = null;
          
          if(memberId !=null)
          {
                batchAdd.setKeyId(memberId);
          }
          
          if(typeId!=null)
          {
                batchAdd.setTypeId(typeId);
          }
             
             if(memberId !=null && typeId!=null && !validatedObj[i].equals(""))
             {
                    try
                    {
                       //existingAddEntry = getGenericConfigDAO().getAddressEntry(typeId,memberId,validatedObj[i]);
                        //  validatedObj[i] == value not name field - get in touch with B to correct
                        existingAddEntry = getGenericConfigDAO().getAddressEntry(memberId,typeId,(String)addFldName.get("descr"));
                    }
                    catch(Exception ex)
                    {
                        log.info("Exception for AddressField : " + validatedObj[i] + " and Member: " +  theMainId + "  ::: "+ ex.getMessage());
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
                BatchAddressEntries newAdd =  theList.get(i);
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
