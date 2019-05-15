/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.ReportlistException;
import com.sift.gl.GendataService;
import com.sift.gl.model.Report;
import java.sql.*;
//import java.sql.SQLException;
import java.util.*;
import javax.naming.*;
import javax.sql.*;

/**
 *
 * @author ABAYOMI AWOSUSI
 */

//@LocalBean
public class Reportlistdao implements Reportlistinter {
    //Connection con;
    //GendataService dbobj = new GendataService();
    public Reportlistdao () {
      //GendataService dbobj = new GendataService();
      //dbobj.inimkcon();
    }
      
   
    @Override 
    public List<Report> retrieveReportlist(int companyid,int branchid,String accesslevel) throws ReportlistException {
        List allreportDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select a.menu,b.Reportcode, b.ReportFileName, b.Description, b.Reportrole, b.RangeCriteria, b.SortCode, b.Reportgroup,c.description as repgrpdesc ,c.formatclass from usergrpmdl a inner join reports b on a.menu = b.reportcode inner join reportgroups c on b.reportgroup = c.code where a.usergroup = '" + accesslevel + "' and a.companyid = " + companyid + " and a.branchid = " + branchid + " order by c.sortorder asc ,b.sortcode asc");  
         int i =0;
         while (rs.next()) {
             Report vrepDetails = new Report();
             i = i + 1;
             vrepDetails.setReportID(i);
             vrepDetails.setDescription(rs.getString("Description")); 
             vrepDetails.setReportFileName(rs.getString("ReportFileName"));
             vrepDetails.setReportGroupCode(rs.getString("Reportgroup"));
             vrepDetails.setReportGroupdesc(rs.getString("repgrpdesc"));
             vrepDetails.setRangeCriteria(rs.getString("RangeCriteria"));
             vrepDetails.setType(rs.getString("formatclass"));
             allreportDetails.add(vrepDetails);
           }
         } catch (SQLException ex) {
          throw new ReportlistException(ex.getMessage());
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
        return allreportDetails;
    }

    @Override 
    public List<Report> retrieveregReportlist() throws ReportlistException {
        List allreportDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select b.Reportcode, b.ReportFileName, b.Description, b.Reportrole, b.RangeCriteria, b.SortCode, b.Reportgroup,c.description as repgrpdesc ,c.formatclass from regreports b inner join regreportgroups c on b.reportgroup = c.code order by c.sortorder asc ,b.sortcode asc");  
         int i =0;
         while (rs.next()) {
             Report vrepDetails = new Report();
             i = i + 1;
             vrepDetails.setReportID(i);
             vrepDetails.setDescription(rs.getString("Description")); 
             vrepDetails.setReportFileName(rs.getString("ReportFileName"));
             vrepDetails.setReportGroupCode(rs.getString("Reportgroup"));
             vrepDetails.setReportGroupdesc(rs.getString("repgrpdesc"));
             vrepDetails.setRangeCriteria(rs.getString("RangeCriteria"));
             vrepDetails.setType(rs.getString("formatclass"));
             allreportDetails.add(vrepDetails);
           }
         } catch (SQLException ex) {
          throw new ReportlistException(ex.getMessage());
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
        return allreportDetails;
    }
}
