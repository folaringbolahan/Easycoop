package com.sift.admin.bean;

public class ReportsBean {
    private String ReportID;	
    private String Reportcode;	
    private String ReportFileName;
    private String Description;
    private String Reportrole;
    private String RangeCriteria;
    private String SortCode;
    private String Type;
    private String Reportgroup;
    
	public String getReportID() {
		return ReportID;
	}
	public void setReportID(String reportID) {
		ReportID = reportID;
	}
	public String getReportcode() {
		return Reportcode;
	}
	public void setReportcode(String reportcode) {
		Reportcode = reportcode;
	}
	public String getReportFileName() {
		return ReportFileName;
	}
	public void setReportFileName(String reportFileName) {
		ReportFileName = reportFileName;
	}
	public String getDescription() {
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