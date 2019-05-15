package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.ReportGroup;
import com.sift.admin.dao.ReportGroupDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("reportGroupService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ReportGroupServiceImpl implements ReportGroupService{
	 @Autowired
	 private ReportGroupDao reportGroupDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addReportGroup(ReportGroup reportGroup){
		 reportGroupDao.addReportGroup(reportGroup);
	 }

	 public List<ReportGroup> listReportGroup(){
	  return reportGroupDao.listReportGroup();
	 }

	 public ReportGroup getReportGroup(String id){
	  return reportGroupDao.getReportGroup(id);
	 }

	 public void deleteReportGroup(ReportGroup reportGroup){
		 reportGroupDao.deleteReportGroup(reportGroup);
	 }
}