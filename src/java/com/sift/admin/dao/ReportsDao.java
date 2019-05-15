package com.sift.admin.dao;

import java.util.List;
import com.sift.admin.model.Reports;
/**
 * @author XTOFFEL CONSULT
 *
 */
public interface ReportsDao{
	 public void addReports(Reports addDetails);
	 public List<Reports> listReports();
	 public Reports getReports(String id);
	 public void deleteReports(Reports addDetails);
}