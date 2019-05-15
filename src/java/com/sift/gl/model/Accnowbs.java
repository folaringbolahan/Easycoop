/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yomi-pc
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Accnowbs implements java.io.Serializable{
    @XmlElement(name = "productcode")
     private String productcode;
    @XmlElement(name = "customerno")
     private String customerno;
    @XmlElement(name = "subno")
     private String subno;
    @XmlElement(name = "branchcode")
     private String branchcode;
    @XmlElement(name = "companycode")
     private String companycode;
    @XmlElement(name = "branchId")
     private Integer branchId;
    @XmlElement(name = "company")
     private Integer company;
    @XmlElement(name = "timezone")
     private String timezone;

    public Accnowbs() {
    }
/*
    public Entry(String txnType,String txnCode,String accountNo,String valueDate,String docref ,String headerdocref, String narrative, String txnSerial, double ccyAmount, double amount, double rate, String userId, String branchId) {
       this.txnType = txnType;
       this.txnCode= txnCode;
       this.docref = docref;
       this.headerdocref = headerdocref;
       this.narrative = narrative;
       this.txnSerial = txnSerial;
       this.accountNo = accountNo;
       if (ccyAmount > 0)
        {
           this.ccyCredit = ccyAmount;
        }
       else
        {
           this.ccyDebit = -1 * ccyAmount ;
        }
       //this.ccyCredit = 0;
       this.ccyAmount = ccyAmount;
        if (amount > 0)
        {
          this.credit = amount;
        }
        else
        {
           this.debit = -1 * amount;
        }
       this.amount = amount;
       this.rate = rate;
       this.userId = userId;
       this.branchId = branchId;
     }
     * 
     */
     
    public Accnowbs(String productcode,String customerno,String subno,String branchcode,String companycode ,String headerdocref, String narrative, String txnSerial, double ccyAmount, double amount, double rate, String userId, Integer branchId,Integer year,Integer period,Integer company,String timezone) {
       this.productcode = productcode;
       this.customerno = customerno;
       this.companycode = companycode;
       this.subno = subno;
       this.branchcode = branchcode;
       this.branchcode = branchcode;
       this.branchId = branchId;
       this.company = company;
       this.timezone = timezone;
     }

    public String getProductcode() {
        return this.productcode;
    }
    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }
    public String getCustomerno() {
        return this.customerno;
    }
    public void setCustomerno(String customerno) {
        this.customerno = customerno;
    }
    public String getSubno() {
        return this.subno;
    }

    public void setSubno(String subno) {
        this.subno = subno;
    }
    public String getBranchcode() {
        return this.branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }
    public String getCompanycode() {
        return this.companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }
    public Integer getBranchId() {
        return this.branchId;
    }
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }
    public Integer getCompany() {
        return this.company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }
     public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
