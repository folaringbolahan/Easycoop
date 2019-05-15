/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.validator;

import com.sift.financial.member.CompStockProperty;
import java.util.*;

import com.sift.financial.member.CompStockPropertyDAO;
import com.sift.financial.member.CompStockType;
import com.sift.financial.member.CompStockTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author baydel200
 */
public class StockValidator implements Validator {
    
    private CompStockPropertyDAO comStockDao;
    private CompStockTypeDAO  compStockTypeDAO;
    
    @Autowired
    @Value("${apprvdStockStat}")
    private String appStatus; 

    @Override
    public boolean supports(Class<?> type) {
         return type.equals(CompStockType.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       
        CompStockType comp = (CompStockType)o;
        
        if(comp.getAction().equals("ADD") || comp.getAction().equals("EDIT"))
        {
                   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "compStockName", "error.compStock.empty.compStockName","Kindly enter a valid name for Stock");
                   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "defaultStock", "error.compStock.empty.defaultStock","Please select if Stock is required for member registration");
                   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stockCashAcct", "error.compStock.empty.stockCashAcct","Kindly select the Cash account for the Stock ");
                   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stockExcessAcct", "error.compStock.empty.stockExcessAcct","Kindly select the Excess account for the Stock ");
                   
                   List thePpty = getComStockDao().findByProperty("delFlg", "N");

                  if (thePpty!=null && !thePpty.isEmpty())
                  {
                           for(Object theProp : thePpty)
                           {
                                 CompStockProperty thePropObj = (CompStockProperty)theProp;

                                 if(thePropObj.getStockPptyName().equals("VOTEFORMULA"))
                                 {
                                           if(comp.getVOTERIGHTPROP().equals("Y"))
                                           {
                                              ValidationUtils.rejectIfEmptyOrWhitespace(errors, thePropObj.getStockPptyName(), "error.compStock.empty.ppty ", thePropObj.getStockPptyEmptyMsg());
                                           }
                                 }
                                 else if(thePropObj.getStockPptyName().equals("TOTALAVAILABLE"))
                                 {
                                           if(comp.getTOTALLIMITED().equals("Y"))
                                           {
                                              ValidationUtils.rejectIfEmptyOrWhitespace(errors, thePropObj.getStockPptyName(), "error.compStock.empty.ppty ", thePropObj.getStockPptyEmptyMsg());
                                           }
                                 }
                                 else if(thePropObj.getStockPptyName().equals("PARVALUE"))
                                 {
                                    if(comp.getPARVALUE().equals("Y"))
                                       {
                                          ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stockParAcct", "error.compStock.empty.stockParAcct ", "Kindly select the ParValue account for the Stock ");
                                       }
                                 }
                                 else if(thePropObj.getStockPptyName().equals("REPURCHASABLE"))
                                 {
                                     if(comp.getREPURCHASABLE().equals("Y"))
                                        {
                                           ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stockTreasuryAcct", "error.compStock.empty.stockTreasuryAcct ", "Kindly select the Treasury account for the Stock ");
                                        }
                                 }
                                 else if(thePropObj.getStockPptyName().equals("VOTERIGHTPROP"))
                                 {
                                     if(comp.getCANVOTE().equals("Y"))
                                        {
                                           ValidationUtils.rejectIfEmptyOrWhitespace(errors, thePropObj.getStockPptyName(), "error.compStock.empty.ppty ", thePropObj.getStockPptyEmptyMsg());
                                        }
                                 }
                                 
                               /**  else if(thePropObj.getStockPptyName().equals("PRODHASINT"))
                                 {
                                     if(comp.getStockAcctProd().equals("NEW"))
                                           {
                                              ValidationUtils.rejectIfEmptyOrWhitespace(errors, thePropObj.getStockPptyName(), "error.compStock.empty.ppty ", thePropObj.getStockPptyEmptyMsg());
                                           }
                                 }
                                 else if(thePropObj.getStockPptyName().equals("PRODINTVAL"))
                                 {
                                      if(comp.getPRODHASINT().equals("YES"))
                                        {
                                           ValidationUtils.rejectIfEmptyOrWhitespace(errors, thePropObj.getStockPptyName(), "error.compStock.empty.ppty ", thePropObj.getStockPptyEmptyMsg());
                                        }                                    
                                 }*/
                                 else
                                 {
                                      ValidationUtils.rejectIfEmptyOrWhitespace(errors, thePropObj.getStockPptyName(), "error.compStock.empty.ppty ", thePropObj.getStockPptyEmptyMsg());
                                 }
                             
                           }
                          
                           if(comp.getStockCashAcct()!=null && !comp.getStockCashAcct().equals("") && comp.getStockExcessAcct()!=null && !comp.getStockExcessAcct().equals(""))
                           {
                                if(comp.getStockCashAcct().equals(comp.getStockExcessAcct()))
                                {
                                        errors.rejectValue("stockCashAcct", "error.member.invalid.stockCashAcct","You have provided same account number for Cash and Excess Account...kindly correct");
                                }
                           }
                           
                           if(comp.getStockParAcct()!=null && !comp.getStockParAcct().equals(""))
                           {
                                if(comp.getStockParAcct().equals(comp.getStockExcessAcct()))
                                {
                                        errors.rejectValue("stockParAcct", "error.member.invalid.stockCashAcct","You have provided same account number for Par and Excess Account...kindly correct");
                                }
                           }
                           
                           if(comp.getStockTreasuryAcct()!=null && !comp.getStockTreasuryAcct().equals(""))
                           {
                                if(comp.getStockTreasuryAcct().equals(comp.getStockExcessAcct()))
                                {
                                        errors.rejectValue("stockTreasuryAcct", "error.member.invalid.stockTreasuryAcct","You have provided same account number for Treasury and Excess Account...kindly correct");
                                }
                                
                                if(comp.getStockTreasuryAcct().equals(comp.getStockCashAcct()))
                                {
                                        errors.rejectValue("stockTreasuryAcct", "error.member.invalid.stockTreasuryAcct","You have provided same account number for Treasury and Cash Account...kindly correct");
                                }
                           }
                  }

                  
                  if(!errors.hasErrors())
                  {
                   List existNames = null;
                                       if(comp.getAction().equals("ADD"))
                                       {
                                           System.out.println("Company Id : " + comp.getCompany().getId().toString());
                                            System.out.println("appStatus : " + appStatus);
                                            System.out.println("appStatus : " + comp.getCompStockName().toUpperCase());
                                            existNames  = compStockTypeDAO.getStocksByNameCompStatus(comp.getCompany().getId().toString(),appStatus, comp.getCompStockName().toUpperCase());
                                       }
                                       else if(comp.getAction().equals("EDIT"))
                                       {
                                            existNames  = compStockTypeDAO.getStocksByNameCompStatus(comp.getCompany().getId().toString(),appStatus, comp.getCompStockName().toUpperCase(), comp.getCompStockTypeId().toString());
                                       }
                                       else if(comp.getAction().equals("DELETE"))
                                       {
                                            //existNames  = compStockTypeDAO.getStocksByNameCompStatus(comp.getCompany().getId().toString(),appStatus, comp.getCompStockName().toUpperCase(), comp.getCompStockTypeId().toString());
                                       }

                                       if(existNames!=null && !existNames.isEmpty())
                                       {
                                           errors.rejectValue("compStockName", "error.compStock.exist.compStockName ", "The supplied Stock Name is already in use. Kindly change");
                                       }
                                       
                                       //todo
                                       //compare unitcost & Parvalue
                                       //
                  }
        }
        else if(comp.getAction().equals("APPROVE") || comp.getAction().equals("REJECT"))
        {
        
            
            
            
            
        
        }
       
    }
    
     public CompStockPropertyDAO getComStockDao() {
        return comStockDao;
    }
     
    @Autowired
    public void setComStockDao(CompStockPropertyDAO comStockDao) {
        this.comStockDao = comStockDao;
    }

    public CompStockTypeDAO getCompStockTypeDAO() {
        return compStockTypeDAO;
    }
    
    @Autowired
    public void setCompStockTypeDAO(CompStockTypeDAO compStockTypeDAO) {
        this.compStockTypeDAO = compStockTypeDAO;
    }
    
    
    
}
