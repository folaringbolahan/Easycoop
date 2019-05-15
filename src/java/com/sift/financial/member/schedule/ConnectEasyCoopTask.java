/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.schedule;

import com.sift.financial.member.*;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.webservice.utility.WebServiceUtility;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author baydel200
 */

@Component
@Scope("prototype")
public class ConnectEasyCoopTask implements Runnable {
    
    private static final Log log = LogFactory.getLog(ConnectEasyCoopTask.class);
    
    private Object targetObjs;
    
    private String typeInd;
    
    //private Object
    
    @Autowired
    private BranchService branchService;
    WebServiceUtility webServiceUtility = new WebServiceUtility();
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CountryService countryService;
    EazyCoopUtility eazyCoopUTIL = new EazyCoopUtility();
    
    @Override
    public void run() {
        
        
        if(typeInd!=null && !typeInd.equals(""))
        {
            
            if(targetObjs!=null)
            {
        
                    if(typeInd.equals(ActivityInterface.SINGLEMEMBER))
                    {
                         Member mem = null;
                            try
                            {
                             mem = (Member)targetObjs;
                             doEasyCoop (mem);
                             //this.finalize();
                            }
                            catch(Exception ex)
                            {
                            log.info("EasyCoop Fialing form member ::" + ex.getMessage());
                            }

                    }
                    else if(typeInd.equals(ActivityInterface.BATCHMEMBER))
                    {
                            List<Member> batch = null;
                            try
                            {
                             batch = (ArrayList<Member>)targetObjs;
                              
                                doEasyCoop(batch);
                            }
                            catch(Exception ex)
                            {
                            log.info("EasyCoop Failing for member ::" + ex.getMessage());
                            }
                    }
                    else if(typeInd.equals(ActivityInterface.ADRESSUPDATE))
                    {
                       ArrayList<String[]> batch = null;
                            try
                            {
                             batch = (ArrayList<String[]>)targetObjs;
                              
                                doEasyCoop(batch,true);
                            }
                            catch(Exception ex)
                            {
                            log.info("EasyCoop Failing for member ::" + ex.getMessage());
                            }
                       
                    //log.info("No handled yet");


                    }
                    else
                    {
                      log.info("Unknown type Specified :: " + typeInd);
                    }
            }
            else
            {
            
                log.info("Unknown Object becos is null");
            
            }

        }
        else
        {
        
          log.info("Type Specified is null or empty ");
        
        }
    }
    
    /**
     * For member Creation on EasyCoop
     * @param member 
     */

    private void doEasyCoop(Member member)
        {
                //call easycoop web service
                    int dbranch = member.getBranch().getId();
                    int dcompany = member.getCompany().getId();
                    if (branchService.getBranch(dbranch).getConnectToEazyCoop().equalsIgnoreCase("Y")
                            && branchService.getBranch(dbranch).getActive().equalsIgnoreCase("Y")) {
                        //System.out.println("Company connected to easycoop...");

                        String name = member.getSurname() + " " + member.getFirstName() + " " + member.getMiddleName();
                        String resource = "user";

                        com.sift.admin.model.Company coy = companyService.getCompany(dcompany);
                        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                        Date regDate = member.getCreatedDate();
                        String registrationDate = formatter1.format(regDate);

                        try {
                            webServiceUtility.webserviceClient(resource, webServiceUtility.createMember(dcompany,
                                    dbranch, member.getEmailAdd1(),
                                    registrationDate,
                                    member.getMemberNo(), member.getPhoneNo1(), name.trim(), member.getEmailAdd1(), member.getAddressStr()));
                        } catch (IOException ex) {                            
                            ex.printStackTrace();
                        }

                    } else {
                       log.info("Company not connected to easycoop...");
                        //System.out.println("Is it Connected to EasyCoop : " + branchService.getBranch(dbranch).getConnectToEazyCoop());
                    }
                    
        }
    
    /**
     * For Address Update on EasyCoop
     * @param memNo
     * @param dbranch
     * @param dcompany
     * @param addrStr
     * @param connected 
     */
     private void doEasyCoop(List memNoAddrsList,int dbranch, int dcompany, String addrStr)
        {
  
                        String resource = "useraddress";

                        try {
                            webServiceUtility.webserviceClient(resource, webServiceUtility.updateMemberAddress(dcompany,
                                    dbranch,null,addrStr));
                        } catch (IOException ex) {                            
                            ex.printStackTrace();
                        }
        }
     
     
     /**
      * For List of Members 
      * @param members
      * @param connected 
      */
    
      private void doEasyCoop(List members)
        {
                //call easycoop web service
            for (Object mem : members)
            {
                Member member = (Member)mem;
                
                    int dbranch = member.getBranch().getId();
                    int dcompany = member.getCompany().getId();
                    // if (branchService.getBranch(dbranch).getConnectToEazyCoop().equalsIgnoreCase("Y") && branchService.getBranch(dbranch).getActive().equalsIgnoreCase("Y"))
                      
                  
                        //System.out.println("Company connected to easycoop...");

                        String name = member.getSurname() + " " + member.getFirstName() + " " + member.getMiddleName();
                        String resource = "user";

                        com.sift.admin.model.Company coy = companyService.getCompany(dcompany);
                        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                        Date regDate = member.getCreatedDate();
                        String registrationDate = formatter1.format(regDate);

                        try {
                            webServiceUtility.webserviceClient(resource, webServiceUtility.createMember(dcompany,
                                    dbranch, member.getEmailAdd1(),
                                    registrationDate,
                                    member.getMemberNo(), member.getPhoneNo1(), name.trim(), member.getEmailAdd1(), ""));
                        } catch (IOException ex) {                            
                            ex.printStackTrace();
                        }

            }

        }
      
      private void doEasyCoop(ArrayList<String[]> membersadd, boolean connected)
        {
                //call easycoop web service
            if (connected) 
              {
                      
              String resource = "useraddress";
            for (String[] mem : membersadd)
            {
                             
                    int dbranch = Integer.parseInt(mem[1]);
                    int dcompany = Integer.parseInt(mem[2]);
                    // if (branchService.getBranch(dbranch).getConnectToEazyCoop().equalsIgnoreCase("Y") && branchService.getBranch(dbranch).getActive().equalsIgnoreCase("Y"))
                      
                  String memNo=mem[0];
                  String addrStr=mem[3];
                        try {
                            webServiceUtility.webserviceClient(resource, webServiceUtility.updateMemberAddress(dcompany,
                                    dbranch,memNo,addrStr));
                        } catch (IOException ex) {                            
                            ex.printStackTrace();
                        }

                }
              } else {
                       log.info("Company not connected to easycoop...");
                        //System.out.println("Is it Connected to EasyCoop : " + branchService.getBranch(dbranch).getConnectToEazyCoop());
              }

        }
      
      public void doEasyCoop(String memNo,int dbranch, int dcompany, String addrStr, boolean connected)
        {
                    if (connected) 
                    {
                      
                        String resource = "useraddress";

                        try {
                            webServiceUtility.webserviceClient(resource, webServiceUtility.updateMemberAddress(dcompany,
                                    dbranch,memNo,addrStr));
                        } catch (IOException ex) {                            
                            ex.printStackTrace();
                        }

                    } else {
                       log.info("Company not connected to easycoop...");
                        //System.out.println("Is it Connected to EasyCoop : " + branchService.getBranch(dbranch).getConnectToEazyCoop());
                    }

        }

    public Object getTargetObjs() {
        return targetObjs;
    }

    public void setTargetObjs(Object targetObjs) {
        this.targetObjs = targetObjs;
    }

    public String getTypeInd() {
        return typeInd;
    }

    public void setTypeInd(String typeInd) {
        this.typeInd = typeInd;
    }
    
      
      
}
