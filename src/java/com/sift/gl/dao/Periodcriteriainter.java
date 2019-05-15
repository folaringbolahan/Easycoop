/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.PeriodcriteriaException;
import java.util.List;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Periodcriteriainter {
    public List<Integer> retrieveyears(int companyid,int branchid) throws PeriodcriteriaException;
    public List<Integer> retrieveperiods(int companyid,int branchid) throws PeriodcriteriaException;
}
