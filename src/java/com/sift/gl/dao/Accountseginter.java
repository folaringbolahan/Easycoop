/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.AccountsegException;
import com.sift.gl.model.Accountsegmentdetails;
import com.sift.gl.model.Accountsegmentdetlist;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Accountseginter {

    public void addData(Accountsegmentdetlist acseglist,Integer company) throws AccountsegException;

    public void deleteData(Integer company) throws AccountsegException;

    public Accountsegmentdetails retrieveData(Integer company) throws AccountsegException;
    //public List<String> getContents();
    public List<Accountsegmentdetails> retrieveDatalist(Integer company) throws AccountsegException;
   // public void remove();

}
