package com.sift.admin.service;

import java.util.List;
import com.sift.admin.model.ReportGroup;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface ReportGroupService { 
	public void addReportGroup(ReportGroup reportGroup);
	public List<ReportGroup> listReportGroup(); 
	public ReportGroup getReportGroup(String id); 
	public void deleteReportGroup(ReportGroup reportGroup);
}