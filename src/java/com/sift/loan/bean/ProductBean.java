package com.sift.loan.bean;

public class ProductBean {
	private Integer   id;
	private String    code;
	private String   companyId;
	private String    name;
	private Float    initialAmountMin;
	private Float    initialAmountMax;
	private Float    interestRateMin;
	private Float    interestRateMax;
	private Float    interestRate;
	private Integer   productTypeId;
	private String   productTypeCode;
	private Integer   currencyId;
	private Integer   isDeleted;
    private String   branchId;
    private Integer  hasPenalty;
    private Float    penalty;
    private String   loanTypeCode;
    private Integer  loanDurationInMonth;
    private Integer  isActive;
    private String   penaltyFormula;
	
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	public Integer getLoanDurationInMonth() {
		return loanDurationInMonth;
	}
	public void setLoanDurationInMonth(Integer loanDurationInMonth) {
		this.loanDurationInMonth = loanDurationInMonth;
	}
	public String getLoanTypeCode() {
		return loanTypeCode;
	}
	public void setLoanTypeCode(String loanTypeCode) {
		this.loanTypeCode = loanTypeCode;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getInitialAmountMin() {
		return initialAmountMin;
	}
	public void setInitialAmountMin(Float initialAmountMin) {
		this.initialAmountMin = initialAmountMin;
	}
	public Float getInitialAmountMax() {
		return initialAmountMax;
	}
	public void setInitialAmountMax(Float initialAmountMax) {
		this.initialAmountMax = initialAmountMax;
	}
	public Float getInterestRateMin() {
		return interestRateMin;
	}
	public void setInterestRateMin(Float interestRateMin) {
		this.interestRateMin = interestRateMin;
	}
	public Float getInterestRateMax() {
		return interestRateMax;
	}
	public void setInterestRateMax(Float interestRateMax) {
		this.interestRateMax = interestRateMax;
	}
	public Float getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(Float interestRate) {
		this.interestRate = interestRate;
	}
	public Integer getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	public Integer getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getProductTypeCode() {
		return productTypeCode;
	}
	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}
	public Integer getHasPenalty() {
		return hasPenalty;
	}
	public void setHasPenalty(Integer hasPenalty) {
		this.hasPenalty = hasPenalty;
	}
	public Float getPenalty() {
		return penalty;
	}
	public void setPenalty(Float penalty) {
		this.penalty = penalty;
	}
	public String getPenaltyFormula() {
		return penaltyFormula;
	}
	public void setPenaltyFormula(String penaltyFormula) {
		this.penaltyFormula = penaltyFormula;
	}	
}
