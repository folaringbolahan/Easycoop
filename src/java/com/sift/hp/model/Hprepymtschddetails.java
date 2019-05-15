/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.hp.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author yomi
 */
public class Hprepymtschddetails implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String refid;
    private String accountNo;
    private int instalNo;
    private Date rpymtdate;
    private Double accruedInt;
    private Double instalment;
    private Double principal;
    private Double principalToDate;
    private Double interest;
    private Double interestToDate;
    private Double unPaidBalance;
    private Double scheduleBalance;
    private Double penalty=0.0;
    private short processed;
    private Date processdate;
    private short payed;
    private Date paymentdate;
    private Integer branchid;
    private Integer companyid;
    private String rpymtdatestr;
    private String paymentdatestr;

    public Hprepymtschddetails() {
    }

    public Hprepymtschddetails(Integer id) {
        this.id = id;
    }

    public Hprepymtschddetails(Integer id, String refid, int instalNo, short processed) {
        this.id = id;
        this.refid = refid;
        this.instalNo = instalNo;
        this.processed = processed;
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

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public int getInstalNo() {
        return instalNo;
    }

    public void setInstalNo(int instalNo) {
        this.instalNo = instalNo;
    }

    public Date getRpymtdate() {
        return rpymtdate;
    }

    public void setRpymtdate(Date rpymtdate) {
        this.rpymtdate = rpymtdate;
    }

    public String getRpymtdatestr() {
        return rpymtdatestr;
    }

    public void setRpymtdatestr(String rpymtdatestr) {
        this.rpymtdatestr = rpymtdatestr;
    }
    
    public Double getAccruedInt() {
        return accruedInt;
    }

    public void setAccruedInt(Double accruedInt) {
        this.accruedInt = accruedInt;
    }

    public Double getInstalment() {
        return instalment;
    }

    public void setInstalment(Double instalment) {
        this.instalment = instalment;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Double getPrincipalToDate() {
        return principalToDate;
    }

    public void setPrincipalToDate(Double principalToDate) {
        this.principalToDate = principalToDate;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getInterestToDate() {
        return interestToDate;
    }

    public void setInterestToDate(Double interestToDate) {
        this.interestToDate = interestToDate;
    }

    public Double getUnPaidBalance() {
        return unPaidBalance;
    }

    public void setUnPaidBalance(Double unPaidBalance) {
        this.unPaidBalance = unPaidBalance;
    }

    public Double getScheduleBalance() {
        return scheduleBalance;
    }

    public void setScheduleBalance(Double scheduleBalance) {
        this.scheduleBalance = scheduleBalance;
    }

    public short getProcessed() {
        return processed;
    }

    public void setProcessed(short processed) {
        this.processed = processed;
    }

    public Date getProcessdate() {
        return processdate;
    }

    public void setProcessdate(Date processdate) {
        this.processdate = processdate;
    }

    public String getPaymentdatestr() {
        return paymentdatestr;
    }

    public void setPaymentdatestr(String paymentdatestr) {
        this.paymentdatestr = paymentdatestr;
    }
    
     public short getPayed() {
        return payed;
    }

    public void setPayed(short payed) {
        this.payed = payed;
    }

    public Date getPaymentdate() {
        return paymentdate;
    }
    public void setPaymentdate(Date paymentdate) {
        this.paymentdate = paymentdate;
    }
    public Double getPenalty() {
        return penalty;
    }

    public void setPenalty(Double penalty) {
        this.penalty = penalty;
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


}
