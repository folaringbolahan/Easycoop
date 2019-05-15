/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.hp.dao;

import com.sift.gl.NotificationService;
import com.sift.hp.dao.*;
import com.sift.hp.HpgenericException;
import com.sift.hp.model.Hpintcalcmtd;
import com.sift.hp.model.Hpoperands;
import com.sift.hp.model.Hpproduct;
import com.sift.hp.model.Hprepaymtfreq;
import com.sift.hp.model.Hprepymtschddetails;
import com.sift.hp.model.Hprequestdetails;
import com.sift.hp.model.Member;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Hprequestinter {

    public String addRequest(String memberno,String prod,double HPprice,double Cashprice,double Interestamt,String invoiceref,String Interestcalcmtd,double InterestRate,java.util.Date TxnDate,double DownpaymentAmount,String RepaymentFrequency,int RepaymentPeriodinMonths, int NoofPayments, int branchid,int companyid,String ipaddr, String username,String timezone, String mailsubject,NotificationService notificationService) throws HpgenericException;
    public List<Member> retrieveMembers(int branchid,int companyid) throws HpgenericException;
    public List<Hpproduct> retrieveHPproducts(int branchid,int companyid) throws HpgenericException;
    public List<Hpintcalcmtd> retrieveHpintcalcmtd() throws HpgenericException;
    public List<Hpoperands> retrieveHpoperands() throws HpgenericException;
    public List<Hprepaymtfreq> retrieveHPrepymtfreqs() throws HpgenericException;
    
    public List<Hprequestdetails> getHpreqs4apprv(int companyid,int branchid,String userid) throws HpgenericException;
    public Hprequestdetails getHpreq4apprv(String ref,int companyid,int branchid,String userid) throws HpgenericException;
   
    public String saveHpdetails(Hprequestdetails hpdet,List<Hprepymtschddetails> hpschdet,int companyid,int branchid,String ipaddr, String username,String timezone, String mailsubject,NotificationService notificationService) throws HpgenericException ;
    public String cancelHpdetails(String ref,int companyid,int branchid,String ipaddr, String username,String timezone) throws HpgenericException ;
    
    public List<Hprequestdetails> getHpreqs4sale(int companyid,int branchid,String userid) throws HpgenericException;
    public Hprequestdetails getHpreq4sale(String ref,int companyid,int branchid,String userid) throws HpgenericException;
    public String postHpsales(Hprequestdetails hpdet,int companyid,int branchid,String ipaddr, String username,String timezone,int dyr,int dper,java.util.Date dpostdate) throws HpgenericException ;
    public List<Hprequestdetails> getHpreqsactive(int companyid,int branchid,String userid) throws HpgenericException;
    public String addRule(String code,String desc,String prodcode,String valtype,String formula,String resultcond, int branchid,int companyid,String ipaddr, String username,String timezone) throws HpgenericException;
    public void removesetting(int code,int branchid,int companyid) throws HpgenericException;
    
    public List<Hprequestdetails> getHpreqs4singpymt(int companyid,int branchid,String userid) throws HpgenericException;
    public List<Hprepymtschddetails> getHpscdhdet(String refid,int companyid,int branchid,String userid) throws HpgenericException;
    public Hprepymtschddetails getHpreq4paysing(String refid,int did,int companyid,int branchid,String userid) throws HpgenericException;
    public String postsingHpsales(Hprepymtschddetails hpdet,int companyid,int branchid,String ipaddr, String username,String timezone,int dyr,int dper,java.util.Date dpostdate) throws HpgenericException ;
    
    /*
    public void deleteAccount(String acno,int branchid,int companyid,String ipaddr, String username,String timezone) throws AccountsetupException;

    public String updateAccount(String acno,String name,Integer acgrp, String acstruct,String currency, Date dateopened, String baltype,boolean cntrlac,String cntrlacno,String activeorclosed,boolean blocked, int branchid,int companyid,String ipaddr, String username,String timezone) throws AccountsetupException ;

    
    public Account retrieveAccount(String acno,int branchid,int companyid) throws AccountsetupException;
    public List<Member> retrieveAccounts(int branchid,int companyid) throws AccountsetupException;
    public List<Account> retrieveallAccounts(int branchid,int companyid) throws AccountsetupException;
    public List<Account> retrieveControlAccounts(int branchid,int companyid) throws AccountsetupException;
    */
   // public void remove();

}
