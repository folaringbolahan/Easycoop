/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;
import com.sift.gl.AccounttxnsException;
import com.sift.gl.dao.Accounttxnsdao;
import com.sift.gl.model.Account;
import com.sift.gl.model.Accountgrprepdetails;
import com.sift.gl.model.Accountgrpclassdetails;
import com.sift.gl.model.Accounttxnsdetail;
import java.util.*;
//import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;
//import payhroll.ejb.*;
/**
 *
 * @author ABAYOMI AWOSUSI
 */

public class AccounttxnsImpl {
    Accounttxnsdao AccounttxnsInterface;
    Accountsenqdao AccountsInterface;
    Accountgrprepdao AccountgrouprepInterface;
    //Cadre CadreInterface;
    //Salarylevel SalarylevelInterface;
    //private static Logger logger = Logger.getLogger(AccountgroupImpl.class.getPackage().getName());
    /**
     *
     */
    public AccounttxnsImpl(){
        AccounttxnsInterface = new Accounttxnsdao(); //) initial.lookup("payrollsalarylevelstep");
         AccountsInterface = new Accountsenqdao();
       //  AccountgrouprepInterface = new Accountgrprepdao();
        
    }
  
    /**
     *
     * @param accountno
     * @param branchid
     * @param companyid
     * @param startdate
     * @param enddate
     * @return
     * @throws AccounttxnsException
     */
    public List<Accounttxnsdetail> retrieveAccounttxns(String accountno,int branchid,int companyid,Date startdate, Date enddate) throws AccounttxnsException{
            List allrecords = new ArrayList();
            try{
             allrecords = AccounttxnsInterface.retrieveAccounttxns(accountno,branchid,companyid,startdate,enddate);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allrecords;
    }
    
    /**
     *
     * @param branchid
     * @param companyid
     * @return
     * @throws AccounttxnsException
     */
    public List<Account> getAccounts(int branchid,int companyid) throws AccounttxnsException{
            List allrecords = new ArrayList();
            try{
             allrecords = AccountsInterface.retrieveAccountswthoutcntrl(branchid,companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allrecords;
    }
    /**
     *
     * @return
     * @throws AccounttxnsException
     */
    public double getDebits() throws AccounttxnsException{
            double allrecords = 0.0;
            try{
             allrecords = AccounttxnsInterface.retrieveTotaldebit();
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allrecords;
    }
    /**
     *
     * @return
     * @throws AccounttxnsException
     */
    public double getCredits() throws AccounttxnsException{
            double allrecords = 0.0;
            try{
             allrecords = AccounttxnsInterface.retrieveTotalcredit();
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allrecords;
    }
    /**
     *
     * @return
     * @throws AccounttxnsException
     */
    public double getNetmvmt() throws AccounttxnsException{
            double allrecords = 0.0;
            try{
             allrecords = AccounttxnsInterface.retrieveNetmvmt();
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allrecords;
    }
    /**
     *
     * @return
     * @throws AccounttxnsException
     */
    public double getBal() throws AccounttxnsException{
            double allrecords = 0.0;
            try{
             allrecords = AccounttxnsInterface.retrieveBal();
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allrecords;
    }
}
