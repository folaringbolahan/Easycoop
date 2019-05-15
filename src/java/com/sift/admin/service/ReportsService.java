package com.sift.admin.service;

import java.util.List;
import com.sift.admin.model.Reports;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface ReportsService { 
	public void addReports(Reports reports);
	public List<Reports> listReports(); 
	public Reports getReports(String id); 
	public void deleteReports(Reports reports);
}