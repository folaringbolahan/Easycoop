/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member;

/**
 *
 * @author baydel200
 */
public interface TransactionInterface {
    
    
     public final static String HEADERINFO = "HEADERINFO";
     public final static String BODYINFO = "BODYINFO";
   
    public final static String ACCOUNTNO = "ACCOUNTNO";
    public final static String TXNTYPE = "TXNTYPE";
    public final static String TXNCODE = "TXNCODE";
    public final static String HEADERNARATIVE = "HEADERNARATIVE";
    public final static String NARATIVE = "NARATIVE";
    public final static String VALUEDATE = "VALUEDATE";
    public final static String DOCREF = "DOCREF";
    public final static String HEADERDOCREF = "HEADERDOCREF";
    public final static String AMOUNT = "AMOUNT";
    public final static String CCYAMOUNT = "CCYAMOUNT";
    public final static String RATE = "RATE";
    
    //stock
    public final static String CASH = "CASH";
    public final static String PAR = "PAR";
    public final static String EXCESS = "EXCESS";
    public final static String TREAS = "TREAS";
  
    //dividend
    public final static String EARNING = "EARN";//EARNINGS
    public final static String TRANSIT = "TRAN"; //TRANSIT
    
    public final static String ALL = "ALL";
    public final static String PARAMT = "PARAMT";
    public final static String NILL = "NILL";
    public final static String ALL_PARAMT = "ALL_PARAMT";
    
    public static final String COMMA_SEPERATED = "#############.##";
    public static final String VALIUEDATE_FORMAT= "yyyy-MM-dd";
    
}
