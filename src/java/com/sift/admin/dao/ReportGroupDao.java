package com.sift.admin.dao;

import java.util.List;
import com.sift.admin.model.ReportGroup;
/**
 * @author XTOFFEL CONSULT
 *
 */
public interface ReportGroupDao{
	 public void addReportGroup(ReportGroup addDetails);
	 public List<ReportGroup> listReportGroup();
	 public ReportGroup getReportGroup(String id);
	 public void deleteReportGroup(ReportGroup addDetails);
}