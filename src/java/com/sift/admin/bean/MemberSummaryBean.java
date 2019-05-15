package com.sift.admin.bean;

public class MemberSummaryBean {
    private String memberName;
    private String memberId;
    private String compMemberId;
    private Integer runningLoanCount;
    private double runningLoanSum;
    private double runningLoanBalance;
    private String numberOfLoanStandingIn;
    private double totalMemberContribution;
    
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCompMemberId() {
		return compMemberId;
	}
	public void setCompMemberId(String compMemberId) {
		this.compMemberId = compMemberId;
	}
	public int getRunningLoanCount() {
		return runningLoanCount;
	}
	public void setRunningLoanCount(int runningLoanCount) {
		this.runningLoanCount = runningLoanCount;
	}
	public double getRunningLoanSum() {
		return runningLoanSum;
	}
	public void setRunningLoanSum(double runningLoanSum) {
		this.runningLoanSum = runningLoanSum;
	}
	public String getNumberOfLoanStandingIn() {
		return numberOfLoanStandingIn;
	}
	public void setNumberOfLoanStandingIn(String numberOfLoanStandingIn) {
		this.numberOfLoanStandingIn = numberOfLoanStandingIn;
	}
	public double getRunningLoanBalance() {
		return runningLoanBalance;
	}
	public void setRunningLoanBalance(double runningLoanBalance) {
		this.runningLoanBalance = runningLoanBalance;
	}
	public double getTotalMemberContribution() {
		return totalMemberContribution;
	}
	public void setTotalMemberContribution(double totalMemberContribution) {
		this.totalMemberContribution = totalMemberContribution;
	}

   
}
