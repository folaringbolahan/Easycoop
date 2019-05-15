/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.gl.GendataService;
import com.sift.gl.model.Userattempts;
import java.sql.*;
//import java.sql.SQLException;
import java.util.*;
import javax.sql.*;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Repository;
///import org.apache.log4j.Logger;
///import org.apache.log4j.BasicConfigurator;
//import biomet.ejb.PersonException;
//import javax.ejb.LocalBean;

/** dao class for data operations on user login attempts
 *
 * @author ABAYOMI AWOSUSI
 */

//@LocalBean
@Repository
public class VoterUserattemptdetailsdao implements VoterUserattemptdetailsinter {
    //Connection con;
    //GendataService dbobj = new GendataService();

    private static final String SQL_USERS_UPDATE_LOCKED = "UPDATE vot_creds SET accountNonLocked = ?,locked = '1' WHERE membermail = ? and locked = '0'";
    private static final String SQL_USER_ATTEMPTS_GET = "SELECT * FROM vot_creds WHERE membermail = '";
    //private static final String SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS = "UPDATE vot_creds SET LoginAttempts = LoginAttempts + 1, lastmodified = ? WHERE membermail = ?";
    //private static final String SQL_USER_ATTEMPTS_RESET_ATTEMPTS = "UPDATE vot_creds SET LoginAttempts = 0, lastmodified = ? WHERE membermail = ?";
    //private static final int MAX_ATTEMPTS = 3;

    private static final String SQL_USERS_UPDATE_ACCOUNTEXPIRED = "UPDATE vot_creds SET accountNonExpired = 0,locked = '1' WHERE membermail = ?  and locked = '0' and agmid = ?";
    //private static final String SQL_USERS_UPDATE_ACCOUNTEXPIREDRESET = "UPDATE vot_creds SET accountNonExpired = 1 WHERE membermail = ?";
    private static final String SQL_USERS_UPDATE_CREDENTIALEXPIRED = "UPDATE vot_creds SET credentialsNonExpired = 0 WHERE membermail = ? and locked = '0'";
    private static final String SQL_USERVALIDDAYS = "SELECT c.* FROM vot_creds c inner join vot_agm a on c.agmid = a.id WHERE locked = '0' and membermail = ";
    private static final String SQL_USERVALIDDAYSPRT2 = " and ( NOW() BETWEEN TIMESTAMP(a.startdate, a.starttime)  AND TIMESTAMP(a.enddate, a.endtime) AND (a.closed='N') AND (c.agmid = ";
    private boolean duserlocked = false;
    //private static final String SQL_USERS_UPDATE_CREDENTIALEXPIREDRESET = "UPDATE vot_creds SET credentialsNonExpired = 1 WHERE membermail = ?";
    //private static final int ACCOUNTINACTIVITYDAYS = 90;
    //private static final int PASSWORDEXPIRYDAYS = 90;
    //private static final String SQL_INACTIVITYDAYS_GET = "SELECT VALUE FROM SETTINGS_COOP WHERE SETTING = '";
    /**
     *
     */
    public VoterUserattemptdetailsdao() {
        //GendataService dbobj = new GendataService();
        //dbobj.inimkcon();
    }

    /**
     *
     * @param userid
     * @return
     */
    @Override
    public Userattempts getUserAttempts(String userid) {
        Userattempts usrattmdet = new Userattempts();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs = null;
        try {
            rs = dbobj.retrieveRecset(SQL_USER_ATTEMPTS_GET + userid + "'");
            while (rs.next()) {
                usrattmdet.setUserid(rs.getString("userid"));
                usrattmdet.setAttempts(rs.getInt("loginattempts"));
                usrattmdet.setLastModified(rs.getDate("lastModified"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ignore) {
                }
            }
            if (dbobj != null) {
                try {
                    dbobj.closecon();
                } catch (Exception ignore) {
                }
            }
            dbobj = null;
        }
        return usrattmdet;
    }

    /**
     *
     * @param userid
     */
    /**
    @Override
    public void updateFailAttempts(String userid) {
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        PreparedStatement psmt = null;
        PreparedStatement psmt2 = null; 
        try {
            psmt = dbobj.con.prepareStatement(SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS);
            Calendar rightNow = Calendar.getInstance();
            java.util.Date logindate = rightNow.getTime();
            java.sql.Timestamp sqllogindate = new java.sql.Timestamp(logindate.getTime());
            psmt.setTimestamp(1, sqllogindate);
            psmt.setString(2, userid);
            psmt.executeUpdate();
            ///////////
            Userattempts user = getUserAttempts(userid);
            if (user.getAttempts() >= MAX_ATTEMPTS) {
		// locked user
                
		psmt2 = dbobj.con.prepareStatement(SQL_USERS_UPDATE_LOCKED);
                psmt2.setInt(1, 0);
                psmt2.setString(2, userid);
                psmt2.executeUpdate();// throw exception
		throw new LockedException("User Account is locked!");
	    }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (psmt != null) {
                try {
                    psmt.close();
                } catch (Exception ignore) {
                }
            }
            if (psmt2 != null) {
                try {
                    psmt2.close();
                } catch (Exception ignore) {
                }
            }
            if (dbobj != null) {
                try {
                    dbobj.closecon();
                } catch (Exception ignore) {
                }
            }
            dbobj = null;
        }
    }

    /**
     *
     * @param userid
     
    @Override
    public void resetFailAttempts(String userid) {
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        PreparedStatement psmt = null;
        try {
            psmt = dbobj.con.prepareStatement(SQL_USER_ATTEMPTS_RESET_ATTEMPTS);
            Calendar rightNow = Calendar.getInstance();
            java.util.Date logindate = rightNow.getTime();
            java.sql.Timestamp sqllogindate = new java.sql.Timestamp(logindate.getTime());
            psmt.setTimestamp(1, sqllogindate);
            psmt.setString(2, userid);
            psmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (psmt != null) {
                try {
                    psmt.close();
                } catch (Exception ignore) {
                }
            }
            if (dbobj != null) {
                dbobj.closecon();
            }
            dbobj = null;
        }
    }  
    
    public int getSettingvalue(String setting) {
        int stval = 0;
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs = null;
        if (setting.equalsIgnoreCase("ACCOUNTINACTIVITYDAYS")) {
                stval=ACCOUNTINACTIVITYDAYS;
        }
        if (setting.equalsIgnoreCase("PASSWORDEXPIRYDAYS")) {
                stval=PASSWORDEXPIRYDAYS;
        }
        try {
            rs = dbobj.retrieveRecset(SQL_INACTIVITYDAYS_GET + setting + "'");
            while (rs.next()) {
                String stvalstr = rs.getString("VALUE");
                stval = Integer.parseInt(stvalstr);
            }
        } catch (Exception ex) {
            if (setting.equalsIgnoreCase("ACCOUNTINACTIVITYDAYS")) {
                stval=ACCOUNTINACTIVITYDAYS;
            }
            if (setting.equalsIgnoreCase("PASSWORDEXPIRYDAYS")) {
                stval=PASSWORDEXPIRYDAYS;
            }
            System.out.println(ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ignore) {
                }
            }
            if (dbobj != null) {
                try {
                    dbobj.closecon();
                } catch (Exception ignore) {
                }
            }
            dbobj = null;
        }
        return stval;
    }
    **/
    public void loadsettingsandsetuser(String userid,int domainref) {
        duserlocked = false;
        int validdaysandtime = getValiddaysandtime(userid,domainref);
        //System.out.println("Expecting call 2 " + validdaysandtime );
        if (validdaysandtime==0)
        {
          updateAccountExpired(userid,domainref);
          duserlocked = true;
        }
        
    }
    
    public void updateAccountExpired(String userid,int domainref) {
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        PreparedStatement psmt = null;
        try {
            psmt = dbobj.con.prepareStatement(SQL_USERS_UPDATE_ACCOUNTEXPIRED);
            Calendar rightNow = Calendar.getInstance();
            java.util.Date logindate = rightNow.getTime();
            java.sql.Timestamp sqllogindate = new java.sql.Timestamp(logindate.getTime());
            psmt.setString(1, userid);
            psmt.setInt(2, domainref);
            //System.out.println("Expecting call 3 " + SQL_USERS_UPDATE_ACCOUNTEXPIRED + " - " + userid + " - " + domainref);
            //psmt.setTimestamp(2,sqllogindate);
            psmt.executeUpdate();
            //System.out.println("Expecting call 3 " + SQL_USERS_UPDATE_ACCOUNTEXPIRED + " - " + userid);
                        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (psmt != null) {
                try {
                    psmt.close();
                } catch (Exception ignore) {
                }
            }
            if (dbobj != null) {
                try {
                    dbobj.closecon();
                } catch (Exception ignore) {
                }
            }
            dbobj = null;
        }
    }
    public void updatePwdExpired(String userid,int noofdays) {
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        PreparedStatement psmt = null;
        try {
            psmt = dbobj.con.prepareStatement(SQL_USERS_UPDATE_CREDENTIALEXPIRED);
            Calendar rightNow = Calendar.getInstance();
            java.util.Date logindate = rightNow.getTime();
            java.sql.Timestamp sqllogindate = new java.sql.Timestamp(logindate.getTime());
            psmt.setString(1, userid);
            //psmt.setTimestamp(2,sqllogindate);
            //psmt.setInt(3,noofdays);
            psmt.executeUpdate();
                        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (psmt != null) {
                try {
                    psmt.close();
                } catch (Exception ignore) {
                }
            }
            if (dbobj != null) {
                try {
                    dbobj.closecon();
                } catch (Exception ignore) {
                }
            }
            dbobj = null;
        }
    }
    
    public int getValiddaysandtime(String userid,int domainref) {
        int stval = 0;
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs = null;
        
         Calendar rightNow = Calendar.getInstance();
            java.util.Date logindate = rightNow.getTime();
            java.sql.Timestamp sqllogindate = new java.sql.Timestamp(logindate.getTime());
            
        
        try {
            rs = dbobj.retrieveRecset(SQL_USERVALIDDAYS + "'" + userid + "'" + SQL_USERVALIDDAYSPRT2 + domainref + "))");
            while (rs.next()) {
                String stvalstr = rs.getString("membermail");
                stval = 1;
            }
        } catch (Exception ex) {
            
            System.out.println(ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ignore) {
                }
            }
            if (dbobj != null) {
                try {
                    dbobj.closecon();
                } catch (Exception ignore) {
                }
            }
            dbobj = null;
        }
        //System.out.println("Expecting call 1 " + stval );
        return stval;
    }
    
    public boolean getDuserlocked() {
        return duserlocked;
    }
}
