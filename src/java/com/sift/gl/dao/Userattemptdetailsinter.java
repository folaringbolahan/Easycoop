/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.AccountsegException;
import com.sift.gl.model.Accountsegmentdetails;
import com.sift.gl.model.Accountsegmentdetlist;
import com.sift.gl.model.Userattempts;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Userattemptdetailsinter {

    public void updateFailAttempts(String userid);
    public void resetFailAttempts(String userid);
    public Userattempts getUserAttempts(String userid);
}
