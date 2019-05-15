/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;
import com.sift.gl.ReportlistException;
import com.sift.gl.dao.Reportlistdao;
import com.sift.gl.model.Account;

import com.sift.gl.model.Report;
import java.util.*;
//import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;
//import payhroll.ejb.*;
/**
 *
 * @author ABAYOMI AWOSUSI
 */

public class ReportlistImpl {
    Reportlistdao ReportlistInterface;
     public ReportlistImpl(){
        ReportlistInterface = new Reportlistdao(); //) initial.lookup("payrollsalarylevelstep");
     }
  
    public List<Report> retrieveReportlist(int companyid,int branchid,String accesslev){
            List allrecords = new ArrayList();
            try{
             allrecords = ReportlistInterface.retrieveReportlist(companyid,branchid,accesslev);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allrecords;
    }
    
   public List<Report> retrieveregReportlist(){
            List allrecords = new ArrayList();
            try{
             allrecords = ReportlistInterface.retrieveregReportlist();
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allrecords;
    }
}
