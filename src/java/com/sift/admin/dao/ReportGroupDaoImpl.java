package com.sift.admin.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.sift.admin.model.ReportGroup;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("reportGroupDao")
@Transactional
public class ReportGroupDaoImpl implements ReportGroupDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addReportGroup(ReportGroup reportGroup){
   sessionFactory.getCurrentSession().saveOrUpdate(reportGroup);
 }

 @SuppressWarnings("unchecked")
 public List<ReportGroup> listReportGroup(){
  return (List<ReportGroup>) sessionFactory.getCurrentSession().createCriteria(ReportGroup.class).list();
 }

 public ReportGroup getReportGroup(String id){
  return (ReportGroup) sessionFactory.getCurrentSession().get(ReportGroup.class, id);
 }

 public void deleteReportGroup(ReportGroup reportGroup) {
	 sessionFactory.getCurrentSession().createQuery("delete from ReportGroup where id= "+reportGroup.getId()).executeUpdate();
 }
}