/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;
import com.sift.gl.dao.Accountgroupdao;
import com.sift.gl.model.Accountgrprepdetails;
import com.sift.gl.model.Accountgrpclassdetails;
import com.sift.gl.model.Accountgroupdetail;
import java.util.*;
import javax.naming.InitialContext;
//import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;
//import payhroll.ejb.*;
/**
 *
 * @author ABAYOMI AWOSUSI
 */

public class PeriodcriteriaImpl {
    Periodcriteriadao PeriodcriteriaInterface;
    public PeriodcriteriaImpl(){
        PeriodcriteriaInterface = new Periodcriteriadao(); //) initial.lookup("payrollsalarylevelstep");
    }
    
    public List<Integer> getYears(Integer companyid, Integer branchid){
            List allyrs = new ArrayList();
            try{
             allyrs = PeriodcriteriaInterface.retrieveyears(companyid, branchid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allyrs;
    }
    public List<Integer> getPeriods(Integer companyid, Integer branchid){
            List allprds = new ArrayList();
            try{
             allprds = PeriodcriteriaInterface.retrieveperiods(companyid, branchid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allprds;
    }   
}
