package com.sift.admin.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.ModuleMenu;
import com.sift.admin.model.Reports;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("reportsDao")
@Transactional
public class ReportsDaoImpl implements ReportsDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addReports(Reports reports){
   sessionFactory.getCurrentSession().saveOrUpdate(reports);
 }

 @SuppressWarnings("unchecked")
 public List<Reports> listReports(){
     //return (List<Reports>) sessionFactory.getCurrentSession().createCriteria(Reports.class).list();  
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Reports.class);
	 
	 criteria.addOrder(Order.asc("Reportcode"));	 
	 return  criteria.list();
 }

 public Reports getReports(String id){
  return (Reports) sessionFactory.getCurrentSession().get(Reports.class, id);
 }

 public void deleteReports(Reports reports) {
	 sessionFactory.getCurrentSession().createQuery("delete from Reports where id= "+reports.getReportID()).executeUpdate();
 }
}