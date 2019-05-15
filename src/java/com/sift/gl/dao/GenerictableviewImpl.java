/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;
import com.sift.gl.GenericsiftException;
import com.sift.gl.model.Generictableviewbean;
import java.util.*;
import java.util.concurrent.Future;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
//import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;
//import payhroll.ejb.*;
/**
 *
 * @author ABAYOMI AWOSUSI
 */

public class GenerictableviewImpl {
    Generictableviewdao GenerictableviewInterface;
    public GenerictableviewImpl(){
        GenerictableviewInterface = new Generictableviewdao(); //) initial.lookup("payrollsalarylevelstep");
    }
    public Generictableviewbean buildtablebody(String vSqlStmt,String pagetitle,List<String> colhdrs,List<String> colflds) {
        List<List<String>> newlist = null;
        Generictableviewbean gb = new Generictableviewbean();
            try{
              GenerictableviewInterface.addTitle(pagetitle);
              GenerictableviewInterface.setHeader(colhdrs);
              GenerictableviewInterface.setTableflds(colflds);
              newlist  = GenerictableviewInterface.buildtablebody(vSqlStmt);
              gb.setPagetitle(pagetitle);
              gb.setHeader(colhdrs);
              gb.setBody(newlist);
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-GenerictableviewImpl;buildtablebody:" + ";" ;
             System.out.println(errmess);
             
            }
            return gb;
    }
     
    public void writeListToExcel(Generictableviewbean dlist, HttpServletResponse response,String filename) {
          try{
              GenerictableviewInterface.writeListToExcel(dlist, response, filename);
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-GenerictableviewImpl;writeListToExcel:" + ";" ;
             System.out.println(errmess);
             
            }
            
    } 
     
}
