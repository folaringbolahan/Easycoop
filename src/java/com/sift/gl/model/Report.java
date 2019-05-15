/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.model;

/**
 *
 * @author yomi-pc
 */
public class Report {
    // private static final long serialVersionUID = 1L;
    private Integer reportID;
    private String reportFileName;
    private String description;
    private String reportGroupCode;
    private String reportGroupdesc;
    private String rangeCriteria;
    private String sortCode;
    private String type;
    private String module;
    private String reportcode;

    public Report() {
    }

    public Report(Integer reportID) {
        this.reportID = reportID;
    }

    public Report(Integer reportID, String reportFileName, String description,String rangeCriteria) {
        this.reportID = reportID;
        this.reportFileName = reportFileName;
        this.description = description;
        this.rangeCriteria = rangeCriteria;
    }

    public Integer getReportID() {
        return reportID;
    }

    public void setReportID(Integer reportID) {
        this.reportID = reportID;
    }

    public String getReportFileName() {
        return reportFileName;
    }

    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReportGroupCode() {
        return reportGroupCode;
    }

    public void setReportGroupCode(String reportGroupCode) {
        this.reportGroupCode = reportGroupCode;
    }
    public String getReportGroupdesc() {
        return reportGroupdesc;
    }
    public void setReportGroupdesc(String reportGroupdesc) {
        this.reportGroupdesc = reportGroupdesc;
    }
    public String getReportcode() {
        return reportcode;
    }

    public void setReportcode(String reportcode) {
        this.reportcode = reportcode;
    }
    
    public String getRangeCriteria() {
        return rangeCriteria;
    }

    public void setRangeCriteria(String rangeCriteria) {
        this.rangeCriteria = rangeCriteria;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
