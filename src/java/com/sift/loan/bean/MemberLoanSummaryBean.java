package com.sift.loan.bean;

public class MemberLoanSummaryBean{
	private String memberName;
	private String memberNo;
	private String registrationDate;
	private String totalSavings;
	private String concurrentLoans;
	private String totalCurrentLoans;
	private String outBalanceCurrentLoans;
	private String memberCurrentlyGuranteeing;
	private String sumGuranteed;
	
	public String getMemberName(){
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	public String getTotalSavings() {
		return totalSavings;
	}
	public void setTotalSavings(String totalSavings) {
		this.totalSavings = totalSavings;
	}
	public String getConcurrentLoans() {
		return concurrentLoans;
	}
	public void setConcurrentLoans(String concurrentLoans) {
		this.concurrentLoans = concurrentLoans;
	}
	public String getTotalCurrentLoans() {
		return totalCurrentLoans;
	}
	
	public void setTotalCurrentLoans(String totalCurrentLoans) {
		this.totalCurrentLoans = totalCurrentLoans;
	}
	
	public String getOutBalanceCurrentLoans() {
		return outBalanceCurrentLoans;
	}
	
	public void setOutBalanceCurrentLoans(String outBalanceCurrentLoans) {
		this.outBalanceCurrentLoans = outBalanceCurrentLoans;
	}
	
	public String getMemberCurrentlyGuranteeing() {
		return memberCurrentlyGuranteeing;
	}
	
	public void setMemberCurrentlyGuranteeing(String memberCurrentlyGuranteeing) {
		this.memberCurrentlyGuranteeing = memberCurrentlyGuranteeing;
	}
	
	public String getSumGuranteed() {
		return sumGuranteed;
	}
	
	public void setSumGuranteed(String sumGuranteed) {
		this.sumGuranteed = sumGuranteed;
	}
}