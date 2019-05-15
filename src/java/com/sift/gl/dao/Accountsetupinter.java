/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.AccountsetupException;
import com.sift.gl.model.Account;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Accountsetupinter {

    /**
     *
     * @param acno
     * @param name
     * @param acgrp
     * @param acstruct
     * @param currency
     * @param dateopened
     * @param baltype
     * @param cntrlac
     * @param cntrlacno
     * @param activeorclosed
     * @param blocked
     * @param branchid
     * @param companyid
     * @param ipaddr
     * @param username
     * @param timezone
     * @throws AccountsetupException
     */
    public String addAccount(String acno,String name,Integer acgrp, String acstruct,String currency, Date dateopened, String baltype,boolean cntrlac,String cntrlacno,String activeorclosed,boolean blocked, int branchid,int companyid,String ipaddr, String username,String timezone) throws AccountsetupException;

    /**
     *
     * @param acno
     * @param branchid
     * @param companyid
     * @param ipaddr
     * @param username
     * @param timezone
     * @throws AccountsetupException
     */
    public String deleteAccount(String acno,int branchid,int companyid,String ipaddr, String username,String timezone) throws AccountsetupException;

    /**
     *
     * @param acno
     * @param name
     * @param acgrp
     * @param acstruct
     * @param currency
     * @param dateopened
     * @param baltype
     * @param cntrlac
     * @param cntrlacno
     * @param activeorclosed
     * @param blocked
     * @param branchid
     * @param companyid
     * @param ipaddr
     * @param username
     * @param timezone
     * @return
     * @throws AccountsetupException
     */
    public String updateAccount(String acno,String name,Integer acgrp, String acstruct,String currency, Date dateopened, String baltype,boolean cntrlac,String cntrlacno,String activeorclosed,boolean blocked, int branchid,int companyid,String ipaddr, String username,String timezone) throws AccountsetupException ;

    /**
     *
     * @param acno
     * @param branchid
     * @param companyid
     * @return
     * @throws AccountsetupException
     */
    public Account retrieveAccount(String acno,int branchid,int companyid) throws AccountsetupException;
    //public List<String> getContents();
    /**
     *
     * @param branchid
     * @param companyid
     * @return
     * @throws AccountsetupException
     */
    public List<Account> retrieveAccounts(int branchid,int companyid) throws AccountsetupException;
    /**
     *
     * @param branchid
     * @param companyid
     * @return
     * @throws AccountsetupException
     */
    public List<Account> retrieveallAccounts(int branchid,int companyid) throws AccountsetupException;
    /**
     *
     * @param branchid
     * @param companyid
     * @return
     * @throws AccountsetupException
     */
    public List<Account> retrieveControlAccounts(int branchid,int companyid) throws AccountsetupException;
    
   // public void remove();

}
