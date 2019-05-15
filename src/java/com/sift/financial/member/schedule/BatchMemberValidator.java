/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.schedule;

import com.sift.financial.GenericConfigDAO;
import com.sift.financial.member.BatchMember;
import com.sift.financial.member.DividendDAO;
import com.sift.financial.member.DividendScheduleDAO;
import com.sift.financial.member.DividendTypeDAO;
import com.sift.financial.member.MemberHoldingsDAO;
import com.sift.financial.member.MemberHoldingsMovementDAO;
import java.util.Map;

import com.sift.financial.utility.customutil;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author baydel200
 */
public class BatchMemberValidator {
    
    private static final Log log = LogFactory.getLog(BatchMemberValidator.class);
    
    private Integer companyId;
    private Integer branchId;
    private Integer countryId;
    private String  createdBy;
    
    @Autowired
    private GenericConfigDAO genericConfigDAO;
    @Autowired
    @Value("${DEF-CON-PRODTYPE}")
    private String prodType;
    

    
       
    public BatchMember doValidation(String[] validatedObj)
    {
    
      //String[] validatedObj = new String[extractedObj.length+2] ;  
      
      BatchMember batchMember = new BatchMember();
      
      StringBuffer errStr = new StringBuffer();
       
      //Valdate corpid not empty & not prersent
      if(validatedObj[0].equals(""))
      {
            errStr.append("|MemberCoopId: Is null");
      }
      else
      {
        String query=" select member_comp_id as id, first_name as first_name " +
                    " from member where company_id='" +companyId+ "' and member_comp_id='" +validatedObj[0]+ "' "
                    + "  and status_id =2";
         
            Map<String,String> theObject = getGenericConfigDAO().getObjectRead(query);
        
        if(theObject!=null && theObject.size()>0)
        {
            errStr.append("|MemberCoopId: Exists");
        }
      
      }
      
      batchMember.setMemberCompId(validatedObj[0]);
      
     //Validate firstname
      if(validatedObj[1].equals(""))
      {
            errStr.append("|First Name: Is null");
      }
      else
      {
          try
          {
              Double.parseDouble(validatedObj[1]);
              errStr.append("|First Name: Cannot be numeric");
          }
          catch(Exception ex)
          {
                log.info(ex.getMessage());
          }
      }
      
      batchMember.setFirstName(validatedObj[1]);
      
      //Balidate middlename
      if(validatedObj[2].equals(""))
      {   }
      else
      {
          
           try
          {
              Double.parseDouble(validatedObj[2]);
             errStr.append("|Middle Name: Cannot be numeric");
          }
          catch(Exception ex)
          {
                log.info(ex.getMessage());
          }
      }
      
      batchMember.setMiddleName(validatedObj[2]);
      
      //Validate surname
      if(validatedObj[3].equals(""))
      {
            errStr.append("|Surname: Is null");
      }
      else
      {
          
          
           try
          {
              Double.parseDouble(validatedObj[3]);
            errStr.append("|Surname: Cannot be numeric");
          }
          catch(Exception ex)
          {
               log.info(ex.getMessage());
          }
      }
      
      batchMember.setSurname(validatedObj[3]);
      
      //validate dob
      java.util.Date dobDate =null;
      if(validatedObj[4].equals(""))
      {
            errStr.append("|DOB: Is null");
      }
      else
      {
           try
           {dobDate = customutil.getjavadate(validatedObj[4], "dd-MM-yyyy");
           }
           catch(Exception ex)
           {
               //ex.printStackTrace();
               log.info(ex.getMessage());
           }
          
            if(dobDate==null)
            {
                errStr.append("|DOB: Is Invalid");
            }
      }
      
      batchMember.setDob(dobDate);
      
      //religion
      String curReligion = null;
       if(validatedObj[5].equals(""))
      {
            errStr.append("|Religion: Is null");
      }
      else
      {
        String query=" select religion_id as id, religion_name as religion_name " +
                    " from religion where country_id=" +countryId+ " and religion_desc='" +validatedObj[5]+ "' ";
         
            Map<String,String> theObject = getGenericConfigDAO().getObjectRead(query);
        
        if(theObject==null || theObject.size()==0)
        {
            errStr.append("|Religion: Is Invalid");
        }
        else
        {
            curReligion = validatedObj[5].toUpperCase();
        }
      }
       
      batchMember.setReligionId(curReligion);
      
      
      //membertype
      
      String curMemberType= null;
       if(validatedObj[6].equals(""))
      {
            errStr.append("|Member Type: Is null");
      }
      else
      {
        String query=" select member_type_id as id, member_type_val as member_type_val " +
                    " from member_type where member_type_desc='" +validatedObj[6].toUpperCase()+ "' ";
         
            Map<String,String> theObject = getGenericConfigDAO().getObjectRead(query);
        
        if(theObject==null || theObject.size()==0)
        {
            errStr.append("|Member Type: Is Invalid");
        }
        else
        {
            curMemberType = validatedObj[6].toUpperCase();
        }
      }
       
      batchMember.setMemberTypeId(curMemberType);
      
      //Gender

      if(validatedObj[7].equals(""))
      {
            errStr.append("|Gender: Is null");
      }
      else
      {
        if(!validatedObj[7].toUpperCase().equals("M") && !validatedObj[7].toUpperCase().equals("F"))
        {
            errStr.append("|Gender: Is invalid");
        }
      }
       batchMember.setGender(validatedObj[7].toUpperCase());
       
       //Identity Doc id
       String curIDType= "";
       if(validatedObj[8].equals(""))
      {
            errStr.append("|Doc Type: Is null");
      }
      else
      {
        String query=" select identification_doc_id as id, Identification_doc_name as doc_name " +
                    " from identification_doc where Identification_doc_desc='" + validatedObj[8].toUpperCase()+ "' "
                + " and country_id=" + countryId + " and del_flg ='N'";
         
            Map<String,String> theObject = getGenericConfigDAO().getObjectRead(query);
        
        if(theObject==null || theObject.size()==0)
        {
            errStr.append("|Doc Type: Is Invalid");
        }
        else
        {
            curIDType = validatedObj[8].toUpperCase();
        }
      }
       
      batchMember.setIdentificationId(curIDType);
      
      //Identity Doc Code
       //String curCodeType= "";
       if(validatedObj[9].equals(""))
       {
            errStr.append("|Doc ID: Is null");
       }
       else
       {
           
        validatedObj[9]  = validatedObj[9].toUpperCase();
       }
       batchMember.setIdentificationCode(validatedObj[9]);
      
     //Phone1
      
      if(validatedObj[10].equals(""))
      {
            errStr.append("|Phone1: Is null");
      }
      else
      {
                
        if(!customutil.isValidPhone(validatedObj[10]))
        {
            errStr.append("|Phone1: Is Invalid");
        }
      }
       
      batchMember.setPhoneNo1(validatedObj[10]);
      
     //Phone2   
     if(!validatedObj[11].equals("") && !customutil.isValidPhone(validatedObj[11]))
     {
        errStr.append("|Phone2: Is Invalid");
     }
     
     batchMember.setPhoneNo2(validatedObj[11]);
      
     //phone3
     
      if(!validatedObj[12].equals("") && !customutil.isValidPhone(validatedObj[12]))
     {
        errStr.append("|Phone3: Is Invalid");
     }
     
     batchMember.setPhoneNo3(validatedObj[12]);
      
     //validate email1
     
     if(validatedObj[13].equals(""))
      {
            errStr.append("|Email1: Is null");
      }
      else
      {
                
        if(!customutil.isValidEmail(validatedObj[13]))
        {
            errStr.append("|Email1: Is Invalid");
        }
        else
        {
            String query=" select email_add_1 as email1, surname as surname " +
                    " from member where status_id=2 and email_add_1='" + validatedObj[13].toLowerCase() +"'";
         
            Map<String,String> theObject = getGenericConfigDAO().getObjectRead(query);
            
            if(theObject!=null && theObject.size()>0)
            {
                errStr.append("|Email1: Is in Use");
            }
        }
     
      }
       
      batchMember.setEmailAdd1(validatedObj[13]);
      
      //validate email2
      
                     
        if( !validatedObj[14].equals("") && !customutil.isValidEmail(validatedObj[14]))
        {
            errStr.append("|Email2: Is Invalid");
        }
  
         batchMember.setEmailAdd2(validatedObj[14]);
      
      //validate email3
      
        if(!validatedObj[15].equals("") && !customutil.isValidEmail(validatedObj[15]))
        {
            errStr.append("|Email3: Is Invalid");
        }
     
      batchMember.setEmailAdd3(validatedObj[15]);
     
      // contribution
      List<Map<String,Object>> contribList = getGenericConfigDAO().getDefaultProductsByType(companyId.toString(), branchId.toString(), prodType);
     
      float contrib = 0.0f;
      boolean fail =false;
      if(validatedObj[16].equals("") && contribList.size()>0)
      {
            errStr.append("|Contribution: Is null");
      }
      
     if(!validatedObj[16].equals("") && contribList.size()>0)
        {
            try
            {
             contrib = Float.parseFloat(validatedObj[16]);
            }
            catch(Exception ex)
            {
               // fail= true;
                contrib = 0.0f;
                errStr.append("|Contribution: Is Invalid");
            }
            //errStr.append("Email3: Is Invalid");
        }
      batchMember.setContribution(contrib);
     
     // Bank
      if(!validatedObj[17].equals(""))
      {
           String query=" select bank_id as bank_id, bank_name as bank_name " +
                    " from banks where bank_code='" + validatedObj[17].toUpperCase()+"'"
                   + " and country_id='" + countryId + "'";
      
         Map<String,String> bankObject = getGenericConfigDAO().getObjectRead(query);
         
         if(bankObject==null || bankObject.size()==0)
         {
            errStr.append("|Bank: Is Invalid");
         }
         else
         {
         validatedObj[17] = validatedObj[17].toUpperCase();
         }
      }
      
      batchMember.setBank(validatedObj[17]);
      
      // BankAcct
                        
      if(batchMember.getBank()!=null && !batchMember.getBank().equals(""))
      {
          if(validatedObj[18].equals(""))
          {
            errStr.append("|Bank Acct: Is null");
          }
          else
          {
                try
                {
                    Double.parseDouble(validatedObj[18]);
                }
                catch(Exception ex)
                {
                  errStr.append("|Bank Acct: must be numeric");
                }
          }
      }
         
    batchMember.setBankAcct(validatedObj[18]);
    
     //nok name
     if(validatedObj[19].equals(""))
      {
            errStr.append("|NOK First Name: Is null");
      }
      else
      {
          try
          {
              Double.parseDouble(validatedObj[19]);
              errStr.append("|NOK First Name: Cannot be numeric");
          }
          catch(Exception ex)
          {
                log.info(ex.getMessage());
          }
      }
      
      batchMember.setNokName(validatedObj[19]);
      
         //nok middlename
      
      if(validatedObj[20].equals(""))
      {   }
      else
      {
          
           try
          {
              Double.parseDouble(validatedObj[20]);
             errStr.append("|NOK Middle Name: Cannot be numeric");
          }
          catch(Exception ex)
          {
                log.info(ex.getMessage());
          }
      }
      
      batchMember.setNokMiddleName(validatedObj[20]);
      
       //nok surname
      if(validatedObj[21].equals(""))
      {
            errStr.append("|NOK Surname: Is null");
      }
      else
      {
          try
          {
              Double.parseDouble(validatedObj[21]);
            errStr.append("|NOK Surname: Cannot be numeric");
          }
          catch(Exception ex)
          {
               log.info(ex.getMessage());
          }
      }
      batchMember.setNokSurname(validatedObj[21]);

    //nok phone
      
      if(validatedObj[22].equals(""))
      {
            errStr.append("|NOK Phone: Is null");
      }
      else
      {
                
        if(!customutil.isValidPhone(validatedObj[22]))
        {
            errStr.append("|NOK Phone: Is Invalid");
        }
      }
       
    batchMember.setNokPhone(validatedObj[22]);
     
    batchMember.setCompanyId(companyId);
    batchMember.setBranchId(branchId);
    
    if(errStr.length()>0)
    {
        batchMember.setValError(errStr.toString());
        batchMember.setHasErrors(true);
    }
  
    return batchMember;
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
