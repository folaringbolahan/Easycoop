/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.PeriodcriteriaException;
import com.sift.gl.GendataService;
import com.sift.gl.model.Accountgroupdetail;
import com.sift.gl.model.Userattempts;
import java.sql.*;
//import java.sql.SQLException;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.naming.*;
import javax.sql.*;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
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
public class Userattemptdetailsdao implements Userattemptdetailsinter {
    //Connection con;
    //GendataService dbobj = new GendataService();

    private static final String SQL_USERS_UPDATE_LOCKED = "UPDATE USERS SET accountNonLocked = ? WHERE userid = ?";
    private static final String SQL_USER_ATTEMPTS_GET = "SELECT * FROM USERS WHERE userid = '";
    private static final String SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS = "UPDATE USERS SET LoginAttempts = LoginAttempts + 1, lastmodified = ? WHERE userid = ?";
    private static final String SQL_USER_ATTEMPTS_RESET_ATTEMPTS = "UPDATE USERS SET LoginAttempts = 0, lastmodified = ? WHERE userid = ?";
    private static final int MAX_ATTEMPTS = 3;

    private static final String SQL_USERS_UPDATE_ACCOUNTEXPIRED = "UPDATE USERS SET accountNonExpired = 0 WHERE userid = ? and (DATEDIFF(? ,lastmodified) > ?)";
    private static final String SQL_USERS_UPDATE_ACCOUNTEXPIREDRESET = "UPDATE USERS SET accountNonExpired = 1 WHERE userid = ?";
    private static final String SQL_USERS_UPDATE_CREDENTIALEXPIRED = "UPDATE USERS SET credentialsNonExpired = 0 WHERE userid = ? and (DATEDIFF(? ,lastpassworddate) > ?)";
    private static final String SQL_USERS_UPDATE_CREDENTIALEXPIREDRESET = "UPDATE USERS SET credentialsNonExpired = 1 WHERE userid = ?";
    private static final int ACCOUNTINACTIVITYDAYS = 90;
    private static final int PASSWORDEXPIRYDAYS = 90;
    private static final String SQL_INACTIVITYDAYS_GET = "SELECT VALUE FROM SETTINGS_COOP WHERE SETTING = '";
    /**
     *
     */
    public Userattemptdetailsdao() {
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
     */
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
    
    public void loadsettingsandsetuser(String userid) {
        int actinactivedays = getSettingvalue("ACCOUNTINACTIVITYDAYS");
        int passexpdays = getSettingvalue("PASSWORDEXPIRYDAYS");
        ///if set to zero then you have disabled enforcement of policies
        if (actinactivedays!=0)
        {
          updateAccountExpired(userid,actinactivedays);
        }
        if (passexpdays!=0)
        {
        updatePwdExpired(userid,passexpdays);
        }
    }
    
    public void updateAccountExpired(String userid,int noofdays) {
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        PreparedStatement psmt = null;
        try {
            psmt = dbobj.con.prepareStatement(SQL_USERS_UPDATE_ACCOUNTEXPIRED);
            Calendar rightNow = Calendar.getInstance();
            java.util.Date logindate = rightNow.getTime();
            java.sql.Timestamp sqllogindate = new java.sql.Timestamp(logindate.getTime());
            psmt.setString(1, userid);
            psmt.setTimestamp(2,sqllogindate);
            psmt.setInt(3,noofdays);
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
            psmt.setTimestamp(2,sqllogindate);
            psmt.setInt(3,noofdays);
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
}
