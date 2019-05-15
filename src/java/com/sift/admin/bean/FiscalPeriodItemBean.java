package com.sift.admin.bean;

import java.util.Date;

public class FiscalPeriodItemBean{
	private Integer id;
    private Integer year;
    private Integer periodId;
    private Integer fiscalPeriodId;
    private Date    periodStart;
    private Date    periodEnd;
	
    public Integer getYear(){
		return year;
	}
    
	public void setYear(Integer year){
		this.year = year;
	}
	public Integer getPeriodId(){
		return periodId;
	}
	public void setPeriodId(Integer periodId){
		this.periodId = periodId;
	}
	public Date getPeriodStart(){
		return periodStart;
	}
	public void setPeriodStart(Date periodStart){
		this.periodStart = periodStart;
	}
	public Date getPeriodEnd(){
		return periodEnd;
	}
	public void setPeriodEnd(Date periodEnd){
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