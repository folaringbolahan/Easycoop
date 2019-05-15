/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.hp.model;

import com.sift.gl.model.*;
import java.util.Date;

/**
 *
 * @author yomi-pc
 */
public class Hprequestdetails implements java.io.Serializable{
    
     private Integer id;
     private String accountno;
     private String refid;
     private Integer repaymentperiodinmonths;
     private String membername;
     private String product;
     private String productname;
     private String interestcalcmtd;
     private String status;
     private String repaymentfrequency;
     private double hpprice=0.0;
     private double cashprice=0.0;
     private double interestamt=0.0;
     private double interestrate;
     private double downpaymentamount=0.0;	
     private double equalpaymentamount;
     private String memberno;
     private boolean closed;
     private Date txndate;
     private Date finalmaturitydate;
     private String txndatestr;
     private Integer branchid;
     private Integer companyid;
     private Integer tenorinmonths;
     private int downpaymentpaid;
     private Integer noofpayments=0;
     private String analysiscode1;
     private String analysiscode2;
     
     //
     private String invref;
     private String repaymentType;
     private Date dateofInitialPayment;
     private Integer noofIntPaymentsOnly;
    private Date dateof1stEqualRepayment;
    private Date dateofLastEqualRepayment;
    private Integer noofPaymentsMatured;
    private Integer noofPaymentsOutstanding;
    private Integer noofPaymentsPaid;
    private Integer nextInstalmentNo;
    private Date nextRepaymentDate;
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
    
    private String createdby;
    private String approvedby;
    private String salesby;
    private Date createddate;
    private Date approveddate;
    private Date salesdate;
    private String rejectcomment;
    
    public Hprequestdetails() {
    }

	
    public Hprequestdetails(String refid,String accountno, String memberno, String membername, double hpprice, double cashprice, double interestrate, double downpaymentamount, double equalpaymentamount, String status,Date txndate, Integer branch,Integer company) {
        this.accountno = accountno;
        this.membername = membername;
        this.refid = refid;
        this.cashprice = cashprice;
        this.hpprice = hpprice;
        this.interestrate = interestrate;
        this.downpaymentamount = downpaymentamount;
        this.status = status;
        this.equalpaymentamount = equalpaymentamount;
        this.txndate = txndate;
        //this.active = active;
        this.branchid = branch;
        this.companyid = company;
    }
    
     public String getAnalysiscode1() {
        return analysiscode1;
    }

    public void setAnalysiscode1(String analysiscode1) {
        this.analysiscode1 = analysiscode1;
    }
     public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRefid() {
        return refid;
    }

    public void setRefid(String refid) {
        this.refid = refid;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }
    public String getMemberno() {
        return memberno;
    }

    public void setMemberno(String memberno) {
        this.memberno = memberno;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public Double getHpprice() {
        return hpprice;
    }

    public void setHpprice(Double hpprice) {
        this.hpprice = hpprice;
    }

    public Double getCashprice() {
        return cashprice;
    }

    public void setCashprice(Double cashprice) {
        this.cashprice = cashprice;
    }

    public Double getInterestamt() {
        return interestamt;
    }

    public void setInterestamt(Double interestamt) {
        this.interestamt = interestamt;
    }

   
    public String getInterestcalcmtd() {
        return interestcalcmtd;
    }

    public void setInterestcalcmtd(String interestcalcmtd) {
        this.interestcalcmtd = interestcalcmtd;
    }

    public Double getInterestrate() {
        return interestrate;
    }

    public void setInterestrate(Double interestrate) {
        this.interestrate = interestrate;
    }

    public Date getTxndate() {
        return txndate;
    }

    public void setTxndate(Date txndate) {
        this.txndate = txndate;
    }
    public String getTxndatestr() {
        return txndatestr;
    }

    public void setTxndatestr(String txndatestr) {
        this.txndatestr = txndatestr;
    }
    public Integer getTenorinmonths() {
        return tenorinmonths;
    }

    public void setTenorinmonths(Integer tenorinmonths) {
        this.tenorinmonths = tenorinmonths;
    }

    public Date getFinalmaturitydate() {
        return finalmaturitydate;
    }

    public void setFinalmaturitydate(Date finalmaturitydate) {
        this.finalmaturitydate = finalmaturitydate;
    }

    public Double getDownpaymentamount() {
        return downpaymentamount;
    }

    public void setDownpaymentamount(Double downpaymentamount) {
        this.downpaymentamount = downpaymentamount;
    }

    public int getDownpaymentpaid() {
        return downpaymentpaid;
    }

    public void setDownpaymentPaid(int downpaymentpaid) {
        this.downpaymentpaid = downpaymentpaid;
    }

    public Date getDateofInitialPayment() {
        return dateofInitialPayment;
    }

    public void setDateofInitialPayment(Date dateofInitialPayment) {
        this.dateofInitialPayment = dateofInitialPayment;
    }

    public String getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType;
    }

    public String getRepaymentfrequency() {
        return repaymentfrequency;
    }

    public void setRepaymentfrequency(String repaymentfrequency) {
        this.repaymentfrequency = repaymentfrequency;
    }

    public Integer getRepaymentperiodinmonths() {
        return repaymentperiodinmonths;
    }

    public void setRepaymentperiodinmonths(Integer repaymentperiodinmonths) {
        this.repaymentperiodinmonths = repaymentperiodinmonths;
    }

    public Double getEqualpaymentamount() {
        return equalpaymentamount;
    }

    public void setEqualpaymentamount(Double equalpaymentamount) {
        this.equalpaymentamount = equalpaymentamount;
    }

    public Integer getNoofpayments() {
        return noofpayments;
    }

    public void setNoofpayments(Integer noofpayments) {
        this.noofpayments = noofpayments;
    }

    public Integer getNoofIntPaymentsOnly() {
        return noofIntPaymentsOnly;
    }

    public void setNoofIntPaymentsOnly(Integer noofIntPaymentsOnly) {
        this.noofIntPaymentsOnly = noofIntPaymentsOnly;
    }

    public Date getDateof1stEqualRepayment() {
        return dateof1stEqualRepayment;
    }

    public void setDateof1stEqualRepayment(Date dateof1stEqualRepayment) {
        this.dateof1stEqualRepayment = dateof1stEqualRepayment;
    }

    public Date getDateofLastEqualRepayment() {
        return dateofLastEqualRepayment;
    }

    public void setDateofLastEqualRepayment(Date dateofLastEqualRepayment) {
        this.dateofLastEqualRepayment = dateofLastEqualRepayment;
    }

    public Integer getNoofPaymentsMatured() {
        return noofPaymentsMatured;
    }

    public void setNoofPaymentsMatured(Integer noofPaymentsMatured) {
        this.noofPaymentsMatured = noofPaymentsMatured;
    }

    public Integer getNoofPaymentsOutstanding() {
        return noofPaymentsOutstanding;
    }

    public void setNoofPaymentsOutstanding(Integer noofPaymentsOutstanding) {
        this.noofPaymentsOutstanding = noofPaymentsOutstanding;
    }

    public Integer getNoofPaymentsPaid() {
        return noofPaymentsPaid;
    }

    public void setNoofPaymentsPaid(Integer noofPaymentsPaid) {
        this.noofPaymentsPaid = noofPaymentsPaid;
    }

    public Integer getNextInstalmentNo() {
        return nextInstalmentNo;
    }

    public void setNextInstalmentNo(Integer nextInstalmentNo) {
        this.nextInstalmentNo = nextInstalmentNo;
    }

    public Date getNextRepaymentDate() {
        return nextRepaymentDate;
    }

    public void setNextRepaymentDate(Date nextRepaymentDate) {
        this.nextRepaymentDate = nextRepaymentDate;
    }

    public Double getLoanBalance() {
        return loanBalance;
    }

    public void setLoanBalance(Double loanBalance) {
        this.loanBalance = loanBalance;
    }

    public Double getScheduledBalance() {
        return scheduledBalance;
    }

    public void setScheduledBalance(Double scheduledBalance) {
        this.scheduledBalance = scheduledBalance;
    }

    public Double getIntAccruedUnpaid() {
        return intAccruedUnpaid;
    }

    public void setIntAccruedUnpaid(Double intAccruedUnpaid) {
        this.intAccruedUnpaid = intAccruedUnpaid;
    }

    public Double getIntDueUnpaid() {
        return intDueUnpaid;
    }

    public void setIntDueUnpaid(Double intDueUnpaid) {
        this.intDueUnpaid = intDueUnpaid;
    }

    public Double getIntPaidTD() {
        return intPaidTD;
    }

    public void setIntPaidTD(Double intPaidTD) {
        this.intPaidTD = intPaidTD;
    }

    public Double getIntPaidYTD() {
        return intPaidYTD;
    }

    public void setIntPaidYTD(Double intPaidYTD) {
        this.intPaidYTD = intPaidYTD;
    }

    public Double getPrincipalDueUnpaid() {
        return principalDueUnpaid;
    }

    public void setPrincipalDueUnpaid(Double principalDueUnpaid) {
        this.principalDueUnpaid = principalDueUnpaid;
    }

    public Double getPrincipalPaidTD() {
        return principalPaidTD;
    }

    public void setPrincipalPaidTD(Double principalPaidTD) {
        this.principalPaidTD = principalPaidTD;
    }

    public Double getPrincipalPaidYTD() {
        return principalPaidYTD;
    }

    public void setPrincipalPaidYTD(Double principalPaidYTD) {
        this.principalPaidYTD = principalPaidYTD;
    }

    public Integer getDaysPastDue() {
        return daysPastDue;
    }

    public void setDaysPastDue(Integer daysPastDue) {
        this.daysPastDue = daysPastDue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getInvref() {
        return invref;
    }

    public void setInvref(String invref) {
        this.invref = invref;
    }
    public Integer getBranchid() {
        return branchid;
    }

    public void setBranchid(Integer branchid) {
        this.branchid = branchid;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }
    public String getCreatedby() {
        return createdby;
    }
    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }
    public String getApprovedby() {
        return approvedby;
    }
    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }
    public String getSalesby() {
        return salesby;
    }
    public void setSalesby(String salesby) {
        this.salesby = salesby;
    }
    public Date getCreateddate() {
        return createddate;
    }
    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }
    public Date getApproveddate() {
        return approveddate;
    }
    public void setApproveddate(Date approveddate) {
        this.approveddate = approveddate;
    }
    public Date getSalesdate() {
        return salesdate;
    }
    public void setSalesdate(Date salesdate) {
        this.salesdate = salesdate;
    }
    public String getRejectcomment() {
        return rejectcomment;
    }
    public void setRejectcomment(String rejectcomment) {
        this.rejectcomment = rejectcomment;
    }
}
