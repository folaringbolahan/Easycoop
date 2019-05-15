package com.sift.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Reports")
public class Reports {
	@Id
	@Column(name="ReportID")
    @GeneratedValue
    private String ReportID;
	
	@Column(name="Reportcode")
    private String Reportcode;
	
	@Column(name="ReportFileName")
    private String ReportFileName;
	
	@Column(name="Description")
    private String Description;
	
	@Column(name="Reportrole")
    private String Reportrole;
		
	@Column(name="RangeCriteria")
    private String RangeCriteria;
	
	@Column(name="SortCode")
    private String SortCode;
		
	@Column(name="Type")
    private String Type;
	
	@Column(name="Reportgroup")
    private String Reportgroup;
    
	public String getReportID(){
		return ReportID;
	}
	public void setReportID(String reportID){
		ReportID = reportID;
	}
	public String getReportcode(){
		return Reportcode;
	}
	public void setReportcode(String reportcode){
		Reportcode = reportcode;
	}
	public String getReportFileName() {
		return ReportFileName;
	}
	public void setReportFileName(String reportFileName) {
		ReportFileName = reportFileName;
	}
	public String getDescription(){
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getReportrole() {
		return Reportrole;
	}
	public void setReportrole(String reportrole) {
		Reportrole = reportrole;
	}
	public String getRangeCriteria() {
		return RangeCriteria;
	}
	public void setRangeCriteria(String rangeCriteria) {
		RangeCriteria = rangeCriteria;
	}
	public String getSortCode() {
		return SortCode;
	}
	public void setSortCode(String sortCode) {
		SortCode = sortCode;
	}
	public String getType() {
		return Type;
	}
	
	public void setType(String type) {
		Type = type;
	}
	
	public String getReportgroup() {
		return Reportgroup;
	}
	
	public void setReportgroup(String reportgroup) {
		Reportgroup = reportgroup;
	}
}