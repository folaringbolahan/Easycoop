package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.Reports;
import com.sift.admin.dao.ReportsDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("reportsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ReportsServiceImpl implements ReportsService{
	 @Autowired
	 private ReportsDao reportsDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addReports(Reports reports){
		 reportsDao.addReports(reports);
	 }

	 public List<Reports> listReports(){
	  return reportsDao.listReports();
	 }

	 public Reports getReports(String id){
	  return reportsDao.getReports(id);
	 }

	 public void deleteReports(Reports reports){
		 reportsDao.deleteReports(reports);
	 }
}