package com.sift.gl.model;

import java.util.Date;

public class AllItempara{   
    private Integer id;
    private Integer year;
    private Integer periodId;
    private Date periodStart;
    private Date periodEnd;
    private String paratype;
    private String reppath;
    private String loancaseidStr;
    private String loanStatusStr;
     private String memberlist;
    private String inactiveMemberList;
    
    private String startdatestr;
    private String enddatestr;
    private String accountno1;
    private String accountno2;
    private String strval1;
    private String strval2;
    private String strval3;
    private String strval4;
    private Integer intval1;
    private Integer intval2;
    private String purchasestatus;
    private Integer agmid;
	
    //Addition by Lolade to for Cash Deposit/Withdrawals
    private Integer productId;
      private Integer voteId;
    public Integer getYear(){
		return year;
	}
    
	public void setYear(Integer year){
		this.year = year;
	}
	public Integer getPeriodId() {
		return periodId;
	}
	public void setPeriodId(Integer periodId) {
		this.periodId = periodId;
	}
        public Integer getIntval1() {
		return intval1;
	}
	public void setIntval1(Integer intval1) {
		this.intval1 = intval1;
	}
        public Integer getIntval2() {
		return intval2;
	}
	public void setIntval2(Integer intval2) {
		this.intval2 = intval2;
	}
	public Date getPeriodStart() {
		return periodStart;
	}
	public void setPeriodStart(Date periodStart) {
		this.periodStart = periodStart;
	}
	public Date getPeriodEnd() {
		return periodEnd;
	}
	public void setPeriodEnd(Date periodEnd) {
		this.periodEnd = periodEnd;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
        public String getParatype() {
		return paratype;
	}

	public void setParatype(String paratype) {
		this.paratype = paratype;
	}
        public String getReppath() {
		return reppath;
	}

	public void setReppath(String reppath) {
		this.reppath = reppath;
	}  
        public String getStartdatestr() {
		return startdatestr;
	}
        public void setStartdatestr(String startdatestr) {
		this.startdatestr = startdatestr;
	}  
        public String getEnddatestr() {
		return enddatestr;
	}
        public void setEnddatestr(String enddatestr) {
		this.enddatestr = enddatestr;
	}  
        public String getAccountno1() {
		return accountno1;
	}
        public void setAccountno1(String accountno1) {
		this.accountno1 = accountno1;
	} 
        public String getAccountno2() {
		return accountno2;
	}
        public void setAccountno2(String accountno2) {
		this.accountno2 = accountno2;
	}  
        public String getStrval1() {
		return strval1;
	}
        public void setStrval1(String strval1) {
		this.strval1 = strval1;
	}  
        public String getStrval2() {
		return strval2;
	}
        public void setStrval2(String strval2) {
		this.strval2 = strval2;
	}
        public String getStrval3() {
		return strval3;
	}
        public void setStrval3(String strval3) {
		this.strval3 = strval3;
	}
        public String getStrval4() {
		return strval4;
	}
        public void setStrval4(String strval4) {
		this.strval4 = strval4;
	}
        //Addition by Lolade
        public Integer getProductId(){
            
            return productId;
        }
        public void setProductId(int value){
            this.productId = value;
        }
         public Integer getVoteId(){
            
            return voteId;
        }
        public void setVoteId(int value){
            this.voteId = value;
        }

    /**
     * @return the loancaseidStr
     */
    public String getLoancaseidStr() {
        return loancaseidStr;
    }

    /**
     * @param loancaseidStr the loancaseidStr to set
     */
    public void setLoancaseidStr(String loancaseidStr) {
        this.loancaseidStr = loancaseidStr;
    }

    /**
     * @return the loanStatusStr
     */
    public String getLoanStatusStr() {
        return loanStatusStr;
    }

    /**
     * @param loanStatusStr the loanStatusStr to set
     */
    public void setLoanStatusStr(String loanStatusStr) {
        this.loanStatusStr = loanStatusStr;
    }

    /**
     * @return the memberlist
     */
    public String getMemberlist() {
        return memberlist;
    }

    /**
     * @param memberlist the memberlist to set
     */
    public void setMemberlist(String memberlist) {
        this.memberlist = memberlist;
    }

    /**
     * @return the inactiveMemberList
     */
    public String getInactiveMemberList() {
        return inactiveMemberList;
    }

    /**
     * @param inactiveMemberList the inactiveMemberList to set
     */
    public void setInactiveMemberList(String inactiveMemberList) {
        this.inactiveMemberList = inactiveMemberList;
    }

    /**
     * @return the purchasestatus
     */
    public String getPurchasestatus() {
        return purchasestatus;
    }

    /**
     * @param purchasestatus the purchasestatus to set
     */
    public void setPurchasestatus(String purchasestatus) {
        this.purchasestatus = purchasestatus;
    }
    public Integer getAgmid() {
	return agmid;
    }
    public void setAgmid(Integer agmid) {
	this.agmid = agmid;
    }
}
