package com.sift.loan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PRODUCTS")
public class Product{
    @Id
    @Column(name="ID")
    @GeneratedValue
	private Integer   id;
    
    @Column(name="CODE")
	private String    code;
    
    @Column(name="COMPANY_ID")
    private String   companyId;
    
    @Column(name="BRANCH_ID")
    private String   branchId;
    
    @Column(name="NAME")   
	private String    name;
 
    @Column(name="INITIAL_AMOUNT_MIN")   
	private Float    initialAmountMin;
    
    @Column(name="INITIAL_AMOUNT_MAX")   
	private Float    initialAmountMax;
    
    @Column(name="INTEREST_RATE_MIN")   
	private Float    interestRateMin;
    
    @Column(name="INTEREST_RATE_MAX")   
	private Float    interestRateMax;
    
    @Column(name="INTEREST_RATE")   
	private Float    interestRate;
    
    @Column(name="PRODUCT_TYPE_ID")   
	private Integer   productTypeId;
    
    @Column(name="PRODUCT_TYPE_CODE")   
	private String   productTypeCode;
    
    @Column(name="CURRENCY_ID")   
	private Integer   currencyId;
	
    @Column(name="IS_DELETED")   
	private Integer   isDeleted;
    
    @Column(name="HAS_PENALTY") 
    private Integer  hasPenalty;
    
    @Column(name="PENALTY") 
    private Float  penalty;
    
    @Column(name="loan_type_code") 
    private String  loanTypeCode;
    
    @Column(name="loan_duration") 
    private Integer  loanDurationInMonth;
    
    @Column(name="is_active") 
    private Integer  isActive;
    
    @Column(name="penalty_formula")
    private String penaltyFormula;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id){
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
	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public Float getInitialAmountMin(){
		return initialAmountMin;
	}
	public void setInitialAmountMin(Float initialAmountMin){
		this.initialAmountMin = initialAmountMin;
	}
	public Float getInitialAmountMax(){
		return initialAmountMax;
	}
	public void setInitialAmountMax(Float initialAmountMax){
		this.initialAmountMax = initialAmountMax;
	}
	public Float getInterestRateMin(){
		return interestRateMin;
	}
	public void setInterestRateMin(Float interestRateMin){
		this.interestRateMin = interestRateMin;
	}
	public Float getInterestRateMax(){
		return interestRateMax;
	}
	public void setInterestRateMax(Float interestRateMax){
		this.interestRateMax = interestRateMax;
	}
	public Float getInterestRate(){
		return interestRate;
	}
	public void setInterestRate(Float interestRate){
		this.interestRate = interestRate;
	}
	public Integer getProductTypeId(){
		return productTypeId;
	}
	public void setProductTypeId(Integer productTypeId){
		this.productTypeId = productTypeId;
	}
	public Integer getCurrencyId(){
		return currencyId;
	}
	public void setCurrencyId(Integer currencyId){
		this.currencyId = currencyId;
	}
	public Integer getIsDeleted(){
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
	}
	public String getProductTypeCode() {
		return productTypeCode;
	}
	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
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
	public String getLoanTypeCode() {
		return loanTypeCode;
	}
	public void setLoanTypeCode(String loanTypeCode) {
		this.loanTypeCode = loanTypeCode;
	}
	public Integer getLoanDurationInMonth() {
		return loanDurationInMonth;
	}
	public void setLoanDurationInMonth(Integer loanDurationInMonth) {
		this.loanDurationInMonth = loanDurationInMonth;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	public String getPenaltyFormula() {
		return penaltyFormula;
	}
	public void setPenaltyFormula(String penaltyFormula) {
		this.penaltyFormula = penaltyFormula;
	}	
}