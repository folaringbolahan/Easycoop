/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.AccounttxnsException;
import com.sift.gl.model.Accounttxnsdetail;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Accounttxnsinter {
    public List<Accounttxnsdetail> retrieveAccounttxns(String accountno,int branchid,int companyid,Date startdate, Date enddate) throws AccounttxnsException;
    public double retrieveTotalcredit() throws AccounttxnsException;
    public double retrieveTotaldebit() throws AccounttxnsException;
    public double retrieveNetmvmt() throws AccounttxnsException;
    public double retrieveBal() throws AccounttxnsException;
}
