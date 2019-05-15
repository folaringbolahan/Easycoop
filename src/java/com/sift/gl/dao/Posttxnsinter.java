/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.GenericsiftException;
import java.util.Date;

/** interface for hp postings data operations
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Posttxnsinter {
    /**
     *
     * @param refid
     * @param companyid
     * @param branchid
     * @param paraid
     * @param userid
     * @param dyr
     * @param dper
     * @param dtimezone
     * @param dpostdate
     * @param ipaddr
     * @throws GenericsiftException
     */
    public void post(String refid,int companyid,int branchid, int paraid,String userid,int dyr,int dper,String dtimezone,Date dpostdate,String ipaddr) throws GenericsiftException;
    /**
     *
     * @param refid
     * @param companyid
     * @param branchid
     * @param paraid
     * @param userid
     * @param dyr
     * @param dper
     * @param dtimezone
     * @param dpostdate
     * @param ipaddr
     * @throws GenericsiftException
     */
    public void posthp(String refid,int companyid,int branchid, int paraid,String userid,int dyr,int dper,String dtimezone,Date dpostdate,String ipaddr) throws GenericsiftException;
    public void updatefileuploadtbl(String ref,int branchid,int companyid,String veid,int isverified) throws GenericsiftException;
    public void updatefileuploadtblhp(String ref,int branchid,int companyid,String veid,int isverified) throws GenericsiftException;
    public boolean updatependingentrydtbl(String ref,int branchid,int companyid,String veid,int isverified,String txnserialref,String txntyperef,String timeZonestr) throws GenericsiftException;
    public String postapprvjrnl(String refid,int companyid,int branchid, int paraid,String userid,int dyr,int dper,String dtimezone,Date dpostdate,String ipaddr) throws GenericsiftException;
}
