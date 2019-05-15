/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.PeriodcriteriaException;
import com.sift.gl.GendataService;
import com.sift.gl.model.Accountgroupdetail;
import java.sql.*;
//import java.sql.SQLException;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.naming.*;
import javax.sql.*;
///import org.apache.log4j.Logger;
///import org.apache.log4j.BasicConfigurator;
//import biomet.ejb.PersonException;
//import javax.ejb.LocalBean;

/**
 *
 * @author ABAYOMI AWOSUSI
 */

//@LocalBean
public class Periodcriteriadao implements Periodcriteriainter {
    //Connection con;
    //GendataService dbobj = new GendataService();
    
    public Periodcriteriadao () {
      //GendataService dbobj = new GendataService();
      //dbobj.inimkcon();
    }
    
    
    @Override 
    public List<Integer> retrieveyears(int companyid,int branchid) throws PeriodcriteriaException {
        List allvintDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select distinct a.year FROM fiscal_period a where a.company_id  = " + companyid + " and a.branch_id = " + branchid);  
         while (rs.next()) {
             allvintDetails.add(rs.getInt("year"));
         } 
        } catch (SQLException ex) {
          throw new PeriodcriteriaException(ex.getMessage());
        }
        finally {
             if (rs != null) {
              try {
                rs.close();
              } catch (Exception ignore) { }
             }
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
        }
        return allvintDetails;
    }
    @Override 
    public List<Integer> retrieveperiods(int companyid,int branchid) throws PeriodcriteriaException {
        List allvintDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select distinct a.period_id FROM fiscal_period_items a inner join fiscal_period b on a.fiscal_period_id = b.id where b.company_id  = " + companyid + " and b.branch_id = " + branchid);  
         while (rs.next()) {
             allvintDetails.add(rs.getInt("period_id"));
         }  
        } catch (SQLException ex) {
          throw new PeriodcriteriaException(ex.getMessage());
       } 
       finally {
             if (rs != null) {
              try {
                rs.close();
              } catch (Exception ignore) { }
             }
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
        }
        return allvintDetails;
    }

 
}
