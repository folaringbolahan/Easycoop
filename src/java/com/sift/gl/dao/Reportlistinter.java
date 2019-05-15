/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.ReportlistException;
import com.sift.gl.model.Report;
import java.util.List;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Reportlistinter {
    public List<Report> retrieveReportlist(int companyid,int branchid,String accesslev) throws ReportlistException;
    public List<Report> retrieveregReportlist() throws ReportlistException;
}
