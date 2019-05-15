/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.model;

import java.util.Date;

/**
 *
 * @author yomi-pc
 */
public class Account implements java.io.Serializable{
    
private Integer acId;
     private String accountNo;
     private String name;
     private String acType;
     private Integer acGroup;
     private String acStruct;
     private String currency;
     private String activeorclosed;
     private Boolean subAccount;
     private Boolean controlAccount;
     private String balanceType;
     private double ccyBalance;
     private double ccyClearedBalance;
     private double balance;
     private double clearedBalance;
     private boolean active;
     private boolean closed;
     private boolean blocked;
     private Date dateOpened;
     private String dateOpenedstr;
     private Integer branch;
     private Integer company;
     private String analysiscode1;
     private String analysiscode2;
     private String asegc;
     private String controlAccountno;
     

    public Account() {
    }

	
    public Account(String accountNo, String name, Integer acGroup, String acStruct, String currency, String balanceType, double ccyBalance, double ccyClearedBalance, double balance, double clearedBalance, boolean active, boolean closed, Integer branch,Integer company) {
        this.accountNo = accountNo;
        this.name = name;
        this.acGroup = acGroup;
        this.acStruct = acStruct;
        this.currency = currency;
        this.balanceType = balanceType;
        this.ccyBalance = ccyBalance;
        this.ccyClearedBalance = ccyClearedBalance;
        this.balance = balance;
        this.clearedBalance = clearedBalance;
        this.active = active;
        this.closed = closed;
        this.branch = branch;
        this.company = company;
    }
    public Account(String accountNo, String name, String acType, Integer acGroup, String acStruct, String currency, Boolean subAccount, Boolean controlAccount, String balanceType, double ccyBalance, double ccyClearedBalance, double balance, double clearedBalance, boolean active, boolean closed, Date dateOpened, Integer branch,Integer company) {
       this.accountNo = accountNo;
       this.name = name;
       this.acType = acType;
       this.acGroup = acGroup;
       this.acStruct = acStruct;
       this.currency = currency;
       this.subAccount = subAccount;
       this.controlAccount = controlAccount;
       this.balanceType = balanceType;
       this.ccyBalance = ccyBalance;
       this.ccyClearedBalance = ccyClearedBalance;
       this.balance = balance;
       this.clearedBalance = clearedBalance;
       this.active = active;
       this.closed = closed;
       this.dateOpened = dateOpened;
       this.branch = branch;
       this.company = company;
    }
   
    public Integer getAcId() {
        return this.acId;
    }
    
    public void setAcId(Integer acId) {
        this.acId = acId;
    }
    public String getAccountNo() {
        return this.accountNo;
    }
    
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }
    public Integer getAcGroup() {
        return this.acGroup;
    }
    
    public void setAcGroup(Integer acGroup) {
        this.acGroup = acGroup;
    }
    public String getAcStruct() {
        return this.acStruct;
    }
    
    public void setAcStruct(String acStruct) {
        this.acStruct = acStruct;
    }
    public String getCurrency() {
        return this.currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public Boolean getSubAccount() {
        return this.subAccount;
    }
    
    public void setSubAccount(Boolean subAccount) {
        this.subAccount = subAccount;
    }
    public Boolean getControlAccount() {
        return this.controlAccount;
    }
    
    public void setControlAccount(Boolean controlAccount) {
        this.controlAccount = controlAccount;
    }
    public String getBalanceType() {
        return this.balanceType;
    }
    
    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }
    public double getCcyBalance() {
        return this.ccyBalance;
    }
    
    public void setCcyBalance(double ccyBalance) {
        this.ccyBalance = ccyBalance;
    }
    public double getCcyClearedBalance() {
        return this.ccyClearedBalance;
    }
    
    public void setCcyClearedBalance(double ccyClearedBalance) {
        this.ccyClearedBalance = ccyClearedBalance;
    }
    public double getBalance() {
        return this.balance;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public double getClearedBalance() {
        return this.clearedBalance;
    }
    
    public void setClearedBalance(double clearedBalance) {
        this.clearedBalance = clearedBalance;
    }
    public boolean getActive() {
        return this.active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    public boolean getClosed() {
        return this.closed;
    }
    
    public void setClosed(boolean closed) {
        this.closed = closed;
    }
    public boolean getBlocked() {
        return this.blocked;
    }
    
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
    public Date getDateOpened() {
        return this.dateOpened;
    }
    
    public void setDateOpened(Date dateOpened) {
        this.dateOpened = dateOpened;
    }
    public Integer getBranch() {
        return this.branch;
    }
    
    public void setBranch(Integer branch) {
        this.branch = branch;
    }
    public Integer getCompany() {
        return this.company;
    }
    
    public void setCompany(Integer company) {
        this.company = company;
    }
    public String getDateOpenedstr() {
        return this.dateOpenedstr;
    }
    public void setDateOpenedstr(String dateOpenedstr) {
        this.dateOpenedstr = dateOpenedstr;
    }
    public String getActiveorclosed(){
        return this.activeorclosed;
    }
    public void setActiveorclosed(String activeorclosed){
        this.activeorclosed = activeorclosed;
    }
    public String getAnalysiscode1() {
        return this.analysiscode1;
    }
    public void setAnalysiscode1(String analysiscode1) {
        this.analysiscode1 = analysiscode1;
    }
    public String getAnalysiscode2() {
        return this.analysiscode2;
    }
    public void setAnalysiscode2(String analysiscode2) {
        this.analysiscode2 = analysiscode2;
    }
    public String getAsegc(){
        return asegc;
    }
    public void setAsegc(String value){
        this.asegc = value;
    }
    public String getControlAccountno(){
        return controlAccountno;
    }
    public void setControlAccountno(String controlAccountno){
        this.controlAccountno = controlAccountno;
    }
}
