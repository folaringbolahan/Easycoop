package com.sift.admin.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="FISCAL_PERIOD_ITEMS")
public class FiscalPeriodItem{   
    @Id
    @Column(name="ID")
    @GeneratedValue	
	private Integer id;

    @Column(name="YEAR")
    private Integer year;
    
    @Column(name="PERIOD_ID")
    private Integer periodId;
    
    @Column(name="FISCAL_PERIOD_ID")
    private Integer fiscalPeriodId;
 
    @Column(name="FP_START")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date    periodStart;

    @Column(name="FP_END")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date    periodEnd;
	
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

	public Integer getFiscalPeriodId() {
		return fiscalPeriodId;
	}

	public void setFiscalPeriodId(Integer fiscalPeriodId) {
		this.fiscalPeriodId = fiscalPeriodId;
	}       
}
