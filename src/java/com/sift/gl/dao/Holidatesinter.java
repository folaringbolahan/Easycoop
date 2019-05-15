/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.HolidatesException;
import com.sift.gl.model.Holidatesdet;
import java.util.List;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Holidatesinter {

    public String updateHolidate(int did,String ddesc, String ddate,int drecurring,int branchid,int companyid) throws HolidatesException ;
    public void addHolidate(String ddesc, String ddate,int drecurring,int branchid,int companyid) throws HolidatesException ;
    public void removeHolidate(int did,int branchid,int companyid) throws HolidatesException ;
    public Holidatesdet retrieveHolidate(int did,int branchid,int companyid) throws HolidatesException;
    //public List<String> getContents();
    public List<Holidatesdet> retrieveHolidates(int branchid,int companyid) throws HolidatesException;
   // public void remove();

}
