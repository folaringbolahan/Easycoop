/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;


import com.sift.gl.CurrentuserdisplayException;
import com.sift.gl.GendataService;
import com.sift.gl.GetSetting;
import com.sift.votes.model.VotAgm;
import com.sift.votes.model.VotMembers;
import java.sql.*;
import java.text.SimpleDateFormat;
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
public class Currentvoterdisplaydao implements Currentvoterdisplayinter {
    //Connection con;
    //GendataService dbobj = new GendataService();
    
    public Currentvoterdisplaydao () {
      //GendataService dbobj = new GendataService();
      //dbobj.inimkcon();
    }
   
   
    @Override 
    public VotMembers retrieveUserdet(Integer userid) throws CurrentuserdisplayException {
        VotMembers allDetails = new VotMembers();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select a.* FROM vot_members a where a.memberid = " + userid + "");  
         while (rs.next()) {
             allDetails.setMemberid(userid);
             allDetails.setAgmid(rs.getInt("agmid"));
             allDetails.setCompanyid(rs.getInt("companyid"));
             allDetails.setEmail(rs.getString("email"));
             allDetails.setMemberrefid(rs.getInt("memberrefid"));
             allDetails.setFirstname(rs.getString("firstname"));
             allDetails.setMiddlename(rs.getString("middlename"));
             allDetails.setSurname(rs.getString("surname"));
             allDetails.setUserid(rs.getString("userid"));
             allDetails.setVoteunits(rs.getInt("voteunits"));
          }  
        } catch (SQLException ex) {
          throw new CurrentuserdisplayException(ex.getMessage());
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
        return allDetails;
    }
    
    @Override 
    public VotAgm retrieveAgmdet(Integer id) throws CurrentuserdisplayException {
        VotAgm vDetails = new VotAgm();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        
        try {
         rs =  dbobj.retrieveRecset("select a.* from vot_agm a where a.id = " + id);  
         while (rs.next()) {
             vDetails.setId(id);
             vDetails.setDescription(rs.getString("description")); 
             vDetails.setCompanyid(rs.getInt("companyid")); 
             vDetails.setAgmyear(rs.getInt("agmyear"));
             vDetails.setBallotid(rs.getInt("ballotid"));
             vDetails.setStartdate(rs.getDate("startdate"));
             vDetails.setStarttime(rs.getString("starttime"));
             vDetails.setEnddate(rs.getDate("enddate"));
             vDetails.setEndtime(rs.getString("endtime"));
            
             
         }
        } catch (SQLException ex) {
          throw new CurrentuserdisplayException(ex.getMessage());
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
        return vDetails;
    }
}
