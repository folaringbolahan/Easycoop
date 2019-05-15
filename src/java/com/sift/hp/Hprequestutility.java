/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.hp;

import com.sift.hp.model.Hprepymtschddetails;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author yomi
 */
public class Hprequestutility {
    
    private String accountno;
     private String refid;
     private Integer repaymentperiodinmonths;
     private String membername;
     private String product;
     private String interestcalcmtd;
     private String status;
     private String repaymentfrequency;
     private double hpprice;
     private double dhpprice;
     private double cashprice;
     private double interestamt;
     private double interestrate;
     private double downpaymentamount;	
     private double equalpaymentamount;
     private Date txndate;
     private Date finalmaturitydate;
     private String txndatestr;
     private Integer branchid;
     private Integer companyid;
     private Integer tenorinmonths;
     private Integer noofpayments;
     //
     private String invref;
     private String repaymentType;
     private Date dateofInitialPayment;
     private Integer noofIntPaymentsOnly;
    private Date dateof1stEqualRepayment;
    private Date dateofLastEqualRepayment;
    private Double loanBalance;
    private Double scheduledBalance;
    private Double intAccruedUnpaid;
    private Double intDueUnpaid;
    private Double intPaidTD;
    private Double intPaidYTD;
    private Double principalDueUnpaid;
    private Double principalPaidTD;
    private Double principalPaidYTD;
    private Integer daysPastDue;
    
    //convert txndate from string to date
     //calculate and display d following
     //1 equalrepayment amt - use hputility class
     //2. interest amt
     //3. hpprice
     //inputs - Interestcalcmtd,double InterestRate,java.util.Date TxnDate openeddate,double DownpaymentAmount,String RepaymentFrequency,int RepaymentPeriodinMonths,double EqualPaymentAmount
    public Hprequestutility(String interestcalcmtd,String repaymentfrequency, Integer repaymentperiodinmonths, double cashprice, double interestrate, double downpaymentamount,double interestamt,double dhpprice,Date txndate) {
        this.interestcalcmtd = interestcalcmtd;
        this.interestamt = interestamt;
        this.repaymentfrequency = repaymentfrequency;
        this.cashprice = cashprice;
        this.interestrate = interestrate;
        this.downpaymentamount = downpaymentamount;
        this.repaymentperiodinmonths = repaymentperiodinmonths;
        this.txndate = txndate;
        this.dhpprice = dhpprice;
    }
    
    public void docalculations(){
        calcnoofrepymts();
        calcintamt();
    }
    public List<List<String>> getSchcalculationsstr(){
        docalculations();
        List<Hprepymtschddetails> dval = generaterepymtschdl();
        //System.out.println("here now1 ");
        return generaterepymtschdlasstr(dval);
    }
    
    public List<Hprepymtschddetails> getSchcalculations(){
        docalculations();
        List<Hprepymtschddetails> dval = generaterepymtschdl();
        return dval;
    }
    
    private int calcnoofrepymts(){
        int amt = 0;
       // if (interestcalcmtd.equalsIgnoreCase("SL")==true) {
            
       // }
        if (repaymentfrequency.equalsIgnoreCase("D")==true) {
            //amt=repaymentperiodinmonths*30;
            amt=365*repaymentperiodinmonths/12;
          //  System.out.println("amt " + amt);
        }
         else if (repaymentfrequency.equalsIgnoreCase("W")==true) {
            //amt=repaymentperiodinmonths*4;
             amt=52*repaymentperiodinmonths/12;
        }
         else if (repaymentfrequency.equalsIgnoreCase("B")==true) {
            amt=repaymentperiodinmonths*2;
            // amt=26*repaymentperiodinmonths/12;
        }
         else if (repaymentfrequency.equalsIgnoreCase("M")==true) {
            amt=repaymentperiodinmonths;
        }
         else if (repaymentfrequency.equalsIgnoreCase("Q")==true) {
            amt=repaymentperiodinmonths/3;
        }
         else if (repaymentfrequency.equalsIgnoreCase("S")==true) {
            amt=repaymentperiodinmonths/6;
        }
        noofpayments=amt;
        return amt;
    }
    
    private Date calcnextrepymtdate(Date currdate,String rpymtfrq){
        Date nextdate;
        Calendar dateinst = Calendar.getInstance();
        dateinst.setTime(currdate);
         nextdate = dateinst.getTime();
       if (rpymtfrq.equalsIgnoreCase("D")==true) {
            dateinst.add(Calendar.DATE  , 1);
            nextdate = dateinst.getTime();
        }
         else if (rpymtfrq.equalsIgnoreCase("W")==true) {
            dateinst.add(Calendar.WEEK_OF_YEAR  , 1);
            nextdate = dateinst.getTime();
        }
         else if (rpymtfrq.equalsIgnoreCase("B")==true) {
            dateinst.add(Calendar.WEEK_OF_YEAR  , 2);
            nextdate = dateinst.getTime();
        }
         else if (rpymtfrq.equalsIgnoreCase("M")==true) {
            dateinst.add(Calendar.MONTH  , 1);
            nextdate = dateinst.getTime();
        }
         else if (rpymtfrq.equalsIgnoreCase("Q")==true) {
            dateinst.add(Calendar.MONTH  , 3);
            nextdate = dateinst.getTime();
        }
         else if (rpymtfrq.equalsIgnoreCase("S")==true) {
            dateinst.add(Calendar.MONTH  , 6);
            nextdate = dateinst.getTime();
        }
        return nextdate;
    }
    
    
    private Double calcintamt(){
        Double intamt = 0.0;
        Double totintamt = 0.0;
        Double resprice = 0.0;
        BigDecimal bgdnewintvalue = new BigDecimal(intamt);
        bgdnewintvalue = bgdnewintvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
        intamt = bgdnewintvalue.doubleValue();
        if (interestcalcmtd.equalsIgnoreCase("SLN")) {
            resprice = cashprice - downpaymentamount;
            totintamt = resprice*((double)repaymentperiodinmonths/12)*((double)interestrate/100);
            //System.out.println("totintamt2 " + ((double)repaymentperiodinmonths/12) );
            //System.out.println("totintamt3 " + ((double)interestrate/100) );
            //System.out.println("totintamt4 " + (resprice) );
            //System.out.println("totintamt " + totintamt );
            intamt = (double)totintamt/noofpayments;
            bgdnewintvalue = new BigDecimal(intamt);
            bgdnewintvalue = bgdnewintvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
            intamt = bgdnewintvalue.doubleValue();
        }
        if (interestcalcmtd.equalsIgnoreCase("SUD")) {
            resprice = cashprice - downpaymentamount;
            totintamt = resprice*((double)repaymentperiodinmonths/12)*((double)interestrate/100);
        }
        if (interestcalcmtd.equalsIgnoreCase("ACE")) {
            
            Double tothpprice = dhpprice;
            Double intamttodate = 0.0;
            Double scheamt = 0.0;
            Double scheamttodate = 0.0;
            Double dschdbal = 0.0;
            Double dinstmt = 0.0;
            BigDecimal bgdnewvalue = new BigDecimal(intamt);
            bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
            intamt = bgdnewvalue.doubleValue();
            
             resprice = tothpprice - downpaymentamount;
            double inddhpprice = (double)resprice/noofpayments;
            totintamt = 0.0;
            
            intamttodate = 0.0;
            scheamttodate = 0.0;
            double outstandprin = cashprice - downpaymentamount;
            double outstandbal = resprice;
            int totint = calcnoofrepymts();
            dschdbal = 0.0;
            dinstmt = 0.0;
            
            
            for (int i = 1; i<=totint; i++) {
                //intamt = outstandbal*interestrate/100;;
                if (i==1) {
                 intamt = ((outstandprin)+intamt-dinstmt)*((double)interestrate/100);
                }
                else {
                 intamt = ((outstandprin+scheamt)+intamt-dinstmt)*((double)interestrate/100);   
                }
                //intamt = (outstandprin+intamt-dinstmt)*interestrate/100;
                
                scheamt = inddhpprice - intamt;
                
                totintamt = totintamt + intamt;
                
                intamttodate = intamttodate + intamt;
                scheamttodate = scheamttodate + scheamt;
                
                if ((i==totint)&&(scheamttodate>(cashprice - downpaymentamount))) {
                   scheamt =  scheamt - (scheamttodate-(cashprice - downpaymentamount));
                   scheamttodate = scheamttodate - (scheamttodate-(cashprice - downpaymentamount)); 
                   totintamt = totintamt - intamt;
                   intamt =  inddhpprice - scheamt;
                   totintamt = totintamt + intamt;
                   intamttodate = intamttodate + (intamt); 
                }
                if ((i==totint)&&(intamt<0.0)) {
                   intamt =  0.0;
                   intamttodate = intamttodate - (intamt); 
                }
                
               bgdnewvalue = new BigDecimal(intamt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                intamt = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(scheamt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                scheamt = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(intamttodate);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                intamttodate = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(scheamttodate);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                scheamttodate = bgdnewvalue.doubleValue();
                dschdbal = cashprice - downpaymentamount-scheamttodate;
                bgdnewvalue = new BigDecimal(dschdbal);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                dschdbal = bgdnewvalue.doubleValue();
                dinstmt = inddhpprice;
                bgdnewvalue = new BigDecimal(dinstmt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                dinstmt = bgdnewvalue.doubleValue();
                
                //outstandbal = (outstandprin+intamt) - (intamt+scheamt);
                outstandprin = outstandprin -scheamt;
              
            }
            
            
            /*
            Double tothpprice = dhpprice;
            double dhpprice = tothpprice/noofpayments;
            Double intamttodate = 0.0;
            Double scheamt = 0.0;
            Double scheamttodate = 0.0;
            Double dschdbal = 0.0;
            Double dinstmt = 0.0;
            BigDecimal bgdnewvalue = new BigDecimal(intamt);
            bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
            intamt = bgdnewvalue.doubleValue();
            
            resprice = cashprice - downpaymentamount;
            
            totintamt = 0.0;
            scheamt = resprice/noofpayments;
            intamttodate = 0.0;
            scheamttodate = 0.0;
            double outstandprin = resprice;
            double outstandbal = resprice;
            int totint = calcnoofrepymts();
            dschdbal = 0.0;
            dinstmt = 0.0;
            for (int i = 1; i<=totint; i++) {
                intamt = (outstandprin+intamt-dinstmt)*interestrate/100;
                totintamt = totintamt + intamt;
                intamttodate = intamttodate + intamt;
                scheamttodate = scheamttodate + scheamt;
                
                if ((i==totint)&&(scheamttodate>resprice)) {
                   scheamt =  scheamt - (scheamttodate-resprice);
                   scheamttodate = scheamttodate - (scheamttodate-resprice); 
                }
                if ((i==totint)&&(intamt<0.0)) {
                   intamt =  0.0;
                   intamttodate = intamttodate - (intamt); 
                }
                
               bgdnewvalue = new BigDecimal(intamt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                intamt = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(scheamt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                scheamt = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(intamttodate);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                intamttodate = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(scheamttodate);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                scheamttodate = bgdnewvalue.doubleValue();
                dschdbal = resprice-scheamttodate;
                bgdnewvalue = new BigDecimal(dschdbal);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                dschdbal = bgdnewvalue.doubleValue();
                dinstmt = dhpprice;
                bgdnewvalue = new BigDecimal(dinstmt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                dinstmt = bgdnewvalue.doubleValue();
                
                //outstandbal = (outstandprin+intamt) - (intamt+scheamt);
                outstandprin = outstandprin -scheamt;
            }  */ 
        }
        if (interestcalcmtd.equalsIgnoreCase("ACU")) {
            Double intamttodate = 0.0;
            Double scheamt = 0.0;
            Double scheamttodate = 0.0;
            Double dschdbal = 0.0;
            Double dinstmt = 0.0;
            BigDecimal bgdnewvalue = new BigDecimal(intamt);
            bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
            intamt = bgdnewvalue.doubleValue();
            
            resprice = cashprice - downpaymentamount;
            
            totintamt = 0.0;
            scheamt = (double)resprice/noofpayments;
            intamttodate = 0.0;
            scheamttodate = 0.0;
            double outstandprin = resprice;
            double outstandbal = resprice;
            int totint = calcnoofrepymts();
            dschdbal = 0.0;
            dinstmt = 0.0;
            for (int i = 1; i<=totint; i++) {
                if (i==1) {
                 intamt = ((outstandprin)+intamt-dinstmt)*((double)interestrate/100);
                }
                else {
                 intamt = ((outstandprin+scheamt)+intamt-dinstmt)*((double)interestrate/100);   
                }
                totintamt = totintamt + intamt;
                intamttodate = intamttodate + intamt;
                scheamttodate = scheamttodate + scheamt;
                
                if ((i==totint)&&(scheamttodate>resprice)) {
                   scheamt =  scheamt - (scheamttodate-resprice);
                   scheamttodate = scheamttodate - (scheamttodate-resprice); 
                }
                if ((i==totint)&&(intamt<0.0)) {
                   intamt =  0.0;
                   intamttodate = intamttodate - (intamt); 
                }
                
               bgdnewvalue = new BigDecimal(intamt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                intamt = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(scheamt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                scheamt = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(intamttodate);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                intamttodate = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(scheamttodate);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                scheamttodate = bgdnewvalue.doubleValue();
                dschdbal = resprice-scheamttodate;
                bgdnewvalue = new BigDecimal(dschdbal);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                dschdbal = bgdnewvalue.doubleValue();
                dinstmt = intamt+scheamt;
                bgdnewvalue = new BigDecimal(dinstmt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                dinstmt = bgdnewvalue.doubleValue();
                
                //outstandbal = (outstandprin+intamt) - (intamt+scheamt);
                outstandprin = outstandprin -scheamt;
            } 
        }
        interestamt=totintamt;
        //intamt;
        return totintamt;
    }
    
    private Double calcequalrepaymentamt(){
        Double amt = 0.0;
        
        
        return amt;
    }
    private Double calchpprice(){
        Double amt = 0.0;
        amt = interestamt+cashprice;
        return amt;
    }
    
    
    private List<Hprepymtschddetails> generaterepymtschdl(){
        //at approval use module and update schedule, number of instalmts. ,finalmaturitydate, accountno, status to A(N-new,A-approved,R-rejected,S-Sold,C-cancelled,F-Finished payment)
        //use decimal util class to round up
        Double intamt = 0.0;
        Double intamttodate = 0.0;
        Double scheamt = 0.0;
        Double scheamttodate = 0.0;
        List allvobjDetails = new ArrayList();
        Hprepymtschddetails vobjDetails = new Hprepymtschddetails();
        Date dnextdate = txndate;
        Double totintamt = 0.0;
        Double resprice = 0.0;
        Double dschdbal = 0.0;
        Double dinstmt = 0.0;
        Double tothpprice = 0.0;
        tothpprice = dhpprice;
        BigDecimal bgdnewvalue = new BigDecimal(intamt);
        bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
        intamt = bgdnewvalue.doubleValue();
       // System.out.println("here now23 " + interestcalcmtd);
        if (interestcalcmtd.equalsIgnoreCase("SLN")) {
            resprice = cashprice - downpaymentamount;
            totintamt = resprice*((double)repaymentperiodinmonths/12)*((double)interestrate)/(100);
            intamt = (double)totintamt/noofpayments;
            scheamt = (double)resprice/noofpayments;
            intamttodate = 0.0;
            scheamttodate = 0.0;
            dschdbal = 0.0;
            dinstmt = 0.0;
            int dnoofrepmts = calcnoofrepymts();
           // System.out.println("here now2 " + calcnoofrepymts());
            for (int i = 1; i<=dnoofrepmts; i++) {
                dnextdate = calcnextrepymtdate(dnextdate,repaymentfrequency);
                vobjDetails = new Hprepymtschddetails();
                intamttodate = intamttodate + intamt;
                scheamttodate = scheamttodate + scheamt;
                
                if ((i==dnoofrepmts)&&(scheamttodate>resprice)) {
                   scheamt =  scheamt - (scheamttodate-resprice);
                   scheamttodate = scheamttodate - (scheamttodate-resprice); 
                }
                if ((i==dnoofrepmts)&&(intamttodate>totintamt)) {
                   intamt =  intamt - (intamttodate-totintamt);
                   intamttodate = intamttodate - (intamttodate-totintamt); 
                }
                if ((i==dnoofrepmts)&&(scheamttodate<resprice)) {
                   scheamt =  scheamt + (resprice-scheamttodate);
                   scheamttodate = scheamttodate + (resprice-scheamttodate); 
                }
                if ((i==dnoofrepmts)&&(intamttodate<totintamt)) {
                   intamt =  intamt + (intamttodate-totintamt);
                   intamttodate = intamttodate + (totintamt-intamttodate); 
                }
                
                bgdnewvalue = new BigDecimal(intamt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                intamt = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(scheamt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                scheamt = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(intamttodate);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                intamttodate = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(scheamttodate);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                scheamttodate = bgdnewvalue.doubleValue();
                dschdbal = resprice-scheamttodate;
                bgdnewvalue = new BigDecimal(dschdbal);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                dschdbal = bgdnewvalue.doubleValue();
                dinstmt = intamt+scheamt;
                bgdnewvalue = new BigDecimal(dinstmt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                dinstmt = bgdnewvalue.doubleValue();
                
                vobjDetails.setInstalNo(i);
                vobjDetails.setInstalment(dinstmt);
                vobjDetails.setInterest(intamt);
                vobjDetails.setPrincipal(scheamt);
                
                vobjDetails.setInterestToDate(intamttodate);
                vobjDetails.setPrincipalToDate(scheamttodate);
                vobjDetails.setScheduleBalance(dschdbal);
              //  System.out.println("here now5 " + scheamttodate);
                vobjDetails.setRpymtdate(dnextdate);
                allvobjDetails.add(vobjDetails);
            }
            
        }
        
        if (interestcalcmtd.equalsIgnoreCase("SUD")) {
            resprice = cashprice - downpaymentamount;
             totintamt = resprice*((double)repaymentperiodinmonths/12)*((double)interestrate/(100));
            scheamt = (double)resprice/noofpayments;
            intamttodate = 0.0;
            scheamttodate = 0.0;
            int totint = calcnoofrepymts();
            int sumdin = totint;
            int sumofdig = totint*(totint+1)/2;
            dschdbal = 0.0;
            dinstmt = 0.0;
            for (int i = 1; i<=totint; i++) {
                dnextdate = calcnextrepymtdate(dnextdate,repaymentfrequency);
                intamt = (double)totintamt*sumdin/sumofdig;
                sumdin = sumdin -1;
                vobjDetails = new Hprepymtschddetails();
                intamttodate = intamttodate + intamt;
                scheamttodate = scheamttodate + scheamt;
                
                if ((i==totint)&&(scheamttodate>resprice)) {
                   scheamt =  scheamt - (scheamttodate-resprice);
                   scheamttodate = scheamttodate - (scheamttodate-resprice); 
                }
                if ((i==totint)&&(intamttodate>totintamt)) {
                   intamt =  intamt - (intamttodate-totintamt);
                   intamttodate = intamttodate - (intamttodate-totintamt); 
                }
                if ((i==totint)&&(scheamttodate<resprice)) {
                   scheamt =  scheamt + (resprice-scheamttodate);
                   scheamttodate = scheamttodate + (resprice-scheamttodate); 
                }
                if ((i==totint)&&(intamttodate<totintamt)) {
                   intamt =  intamt + (intamttodate-totintamt);
                   intamttodate = intamttodate + (totintamt-intamttodate); 
                }
                
                bgdnewvalue = new BigDecimal(intamt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                intamt = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(scheamt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                scheamt = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(intamttodate);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                intamttodate = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(scheamttodate);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                scheamttodate = bgdnewvalue.doubleValue();
                dschdbal = resprice-scheamttodate;
                bgdnewvalue = new BigDecimal(dschdbal);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                dschdbal = bgdnewvalue.doubleValue();
                dinstmt = intamt+scheamt;
                bgdnewvalue = new BigDecimal(dinstmt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                dinstmt = bgdnewvalue.doubleValue();
                
                vobjDetails.setInstalNo(i);
                vobjDetails.setInstalment(dinstmt);
                vobjDetails.setInterest(intamt);
                vobjDetails.setPrincipal(scheamt);
                
                vobjDetails.setInterestToDate(intamttodate);
                vobjDetails.setPrincipalToDate(scheamttodate);
                vobjDetails.setScheduleBalance(dschdbal);
                vobjDetails.setRpymtdate(dnextdate);
                allvobjDetails.add(vobjDetails);
            }
            
        }
        
        if (interestcalcmtd.equalsIgnoreCase("ACU")) {
            resprice = cashprice - downpaymentamount;
            
            totintamt = 0.0;
            scheamt = (double)resprice/noofpayments;
            intamttodate = 0.0;
            scheamttodate = 0.0;
            double outstandprin = resprice;
            double outstandbal = resprice;
            int totint = calcnoofrepymts();
            dschdbal = 0.0;
            dinstmt = 0.0;
            for (int i = 1; i<=totint; i++) {
                dnextdate = calcnextrepymtdate(dnextdate,repaymentfrequency);
                //intamt = outstandbal*interestrate/100;;
                if (i==1) {
                 intamt = ((outstandprin)+intamt-dinstmt)*(double)interestrate/100;
                }
                else {
                 intamt = ((outstandprin+scheamt)+intamt-dinstmt)*(double)interestrate/100;   
                }
                totintamt = totintamt + intamt;
                vobjDetails = new Hprepymtschddetails();
                
                intamttodate = intamttodate + intamt;
                scheamttodate = scheamttodate + scheamt;
                
                if ((i==totint)&&(scheamttodate>resprice)) {
                   scheamt =  scheamt - (scheamttodate-resprice);
                   scheamttodate = scheamttodate - (scheamttodate-resprice); 
                }
                if ((i==totint)&&(intamt<0.0)) {
                   intamt =  0.0;
                   intamttodate = intamttodate - (intamt); 
                }
                
               bgdnewvalue = new BigDecimal(intamt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                intamt = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(scheamt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                scheamt = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(intamttodate);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                intamttodate = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(scheamttodate);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                scheamttodate = bgdnewvalue.doubleValue();
                dschdbal = resprice-scheamttodate;
                bgdnewvalue = new BigDecimal(dschdbal);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                dschdbal = bgdnewvalue.doubleValue();
                dinstmt = intamt+scheamt;
                bgdnewvalue = new BigDecimal(dinstmt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                dinstmt = bgdnewvalue.doubleValue();
                
                vobjDetails.setInstalNo(i);
                vobjDetails.setInstalment(dinstmt);
                vobjDetails.setInterest(intamt);
                vobjDetails.setPrincipal(scheamt);
                
                vobjDetails.setInterestToDate(intamttodate);
                vobjDetails.setPrincipalToDate(scheamttodate);
                vobjDetails.setScheduleBalance(dschdbal);
                vobjDetails.setRpymtdate(dnextdate);
                //outstandbal = (outstandprin+intamt) - (intamt+scheamt);
                outstandprin = outstandprin -scheamt;
                allvobjDetails.add(vobjDetails);
            }
            
        }
        
        if (interestcalcmtd.equalsIgnoreCase("ACE")) {
            //resprice = cashprice - downpaymentamount;
            resprice = tothpprice - downpaymentamount;
            double inddhpprice = (double)resprice/noofpayments;
            totintamt = 0.0;
            
            intamttodate = 0.0;
            scheamttodate = 0.0;
            double outstandprin = cashprice - downpaymentamount;
            double outstandbal = resprice;
            int totint = calcnoofrepymts();
            dschdbal = 0.0;
            dinstmt = 0.0;
            for (int i = 1; i<=totint; i++) {
                dnextdate = calcnextrepymtdate(dnextdate,repaymentfrequency);
                //intamt = outstandbal*interestrate/100;;
                if (i==1) {
                 intamt = ((outstandprin)+intamt-dinstmt)*(double)interestrate/100;
                }
                else {
                 intamt = ((outstandprin+scheamt)+intamt-dinstmt)*(double)interestrate/100;   
                }
                //intamt = (outstandprin+intamt-dinstmt)*interestrate/100;
                
                scheamt = inddhpprice - intamt;
                
                totintamt = totintamt + intamt;
                vobjDetails = new Hprepymtschddetails();
                
                intamttodate = intamttodate + intamt;
                scheamttodate = scheamttodate + scheamt;
                
                if ((i==totint)&&(scheamttodate>(cashprice - downpaymentamount))) {
                   scheamt =  scheamt - (scheamttodate-(cashprice - downpaymentamount));
                   scheamttodate = scheamttodate - (scheamttodate-(cashprice - downpaymentamount)); 
                   intamt =  inddhpprice - scheamt;
                   intamttodate = intamttodate + (intamt); 
                }
                if ((i==totint)&&(intamt<0.0)) {
                   intamt =  0.0;
                   intamttodate = intamttodate - (intamt); 
                }
                
               bgdnewvalue = new BigDecimal(intamt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                intamt = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(scheamt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                scheamt = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(intamttodate);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                intamttodate = bgdnewvalue.doubleValue();
                bgdnewvalue = new BigDecimal(scheamttodate);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                scheamttodate = bgdnewvalue.doubleValue();
                dschdbal = cashprice - downpaymentamount-scheamttodate;
                bgdnewvalue = new BigDecimal(dschdbal);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                dschdbal = bgdnewvalue.doubleValue();
                dinstmt = inddhpprice;
                bgdnewvalue = new BigDecimal(dinstmt);
                bgdnewvalue = bgdnewvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                dinstmt = bgdnewvalue.doubleValue();
                
                vobjDetails.setInstalNo(i);
                vobjDetails.setInstalment(dinstmt);
                vobjDetails.setInterest(intamt);
                vobjDetails.setPrincipal(scheamt);
                
                vobjDetails.setInterestToDate(intamttodate);
                vobjDetails.setPrincipalToDate(scheamttodate);
                vobjDetails.setScheduleBalance(dschdbal);
                vobjDetails.setRpymtdate(dnextdate);
                //outstandbal = (outstandprin+intamt) - (intamt+scheamt);
                outstandprin = outstandprin -scheamt;
                allvobjDetails.add(vobjDetails);
            }
            
        }
        //System.out.println("here now3 " + allvobjDetails.size());
        return allvobjDetails;
    }
    
    
    public List<List<String>> generaterepymtschdlasstr(List<Hprepymtschddetails> listofobj){
         List allvobjDetails = new ArrayList();
         SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
         String dateopenedx = null;
         Iterator<Hprepymtschddetails> bditerator = listofobj.iterator();
        int i = 0;
        
        while (bditerator.hasNext()) {
            
            Hprepymtschddetails objinn = bditerator.next();
            List allrowrecDetails = new ArrayList<String>();
              
            allrowrecDetails.add(Integer.toString(objinn.getInstalNo()));
            allrowrecDetails.add(objinn.getInstalment().toString());
            allrowrecDetails.add(objinn.getPrincipal().toString());
            allrowrecDetails.add(objinn.getInterest().toString());
            allrowrecDetails.add(objinn.getInterestToDate().toString());
            allrowrecDetails.add(objinn.getPrincipalToDate().toString());
            allrowrecDetails.add(objinn.getScheduleBalance().toString());
           // allrowrecDetails.add(String.valueOf(objinn.getPayed()));
            try {
            dateopenedx = formatter1.format(objinn.getRpymtdate());
             } catch( NullPointerException nullEx ) {   }
            allrowrecDetails.add(dateopenedx);
            allvobjDetails.add(i,allrowrecDetails);
            i = i + 1;
            //System.out.println("line no" + i +" " + objinn.getScheduleBalance().toString());
         }
         
        return allvobjDetails;
    }
    
    public Double getInterestamt() {
        return interestamt;
    }
    public Integer getNoofpayments() {
        return noofpayments;
    }
    public Double getHpprice() {
        return interestamt+cashprice;
    }
}
