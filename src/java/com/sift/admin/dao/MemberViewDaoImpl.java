package com.sift.admin.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.admin.model.AppConfiguration;
import com.sift.admin.model.MemberView;

/**
 * @author Chris Faseun
 *
 */
@Repository("memberViewDao")
public class MemberViewDaoImpl implements MemberViewDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addMember(MemberView memberView) {
   sessionFactory.getCurrentSession().saveOrUpdate(memberView);
 }

 @SuppressWarnings("unchecked")
 public List<MemberView> listMember(){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MemberView.class);
	 criteria.add(Restrictions.eq("statusId","2"));
	 
	 return  criteria.list();  
 }
 
 @SuppressWarnings("unchecked")
 public List<MemberView> listAllMember(){
   return (List<MemberView>) sessionFactory.getCurrentSession().createCriteria(MemberView.class).list();  
 }
 
 @SuppressWarnings("unchecked")
 public List<MemberView> listInActiveMember(){
   return (List<MemberView>) sessionFactory.getCurrentSession().createCriteria(MemberView.class).list();  
 }
 
 @SuppressWarnings("unchecked")
 public List<MemberView> listMembersByCompanyId(String companyId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MemberView.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("statusId","2"));
	 
	 return  criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<MemberView> listMembersExclusiveByCompanyId(String companyId,String memberId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MemberView.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.ne("memberId",memberId));
	 criteria.add(Restrictions.eq("statusId","2"));
	 
	 return  criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<MemberView> listMembersIncByCompanyId(String companyId,String memberId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MemberView.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("memberId",memberId));
	 criteria.add(Restrictions.eq("statusId","2"));
	 
	 return  criteria.list();
 }
  
 @SuppressWarnings("unchecked")
 public List<MemberView> listMembersByBranchId(String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MemberView.class);
	 criteria.add(Restrictions.eq("branchId",branchId));
	 criteria.add(Restrictions.eq("statusId","2"));
	 
	 return  criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<MemberView> listMembersExclusiveByBranchId(String branchId,String memberId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MemberView.class);
	 criteria.add(Restrictions.eq("branchId",branchId));
	 criteria.add(Restrictions.ne("memberId",memberId));
	 criteria.add(Restrictions.eq("statusId","2"));
	 
	 return  criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<MemberView> listMembersIncByBranchId(String branchId,String memberId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MemberView.class);
	 criteria.add(Restrictions.eq("branchId",branchId));
	 criteria.add(Restrictions.eq("memberId",memberId));
	 criteria.add(Restrictions.eq("statusId","2"));
	 
	 return  criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<MemberView> listMembersIncByBranchIdNew(String branchId,String memberId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MemberView.class);
	 criteria.add(Restrictions.eq("branchId",branchId));
	 criteria.add(Restrictions.eq("memberId",memberId));
	 
	 return  criteria.list();
 }

 public MemberView getMember(String memberId){
     return (MemberView) sessionFactory.getCurrentSession().get(MemberView.class, memberId);
 }
 
 public MemberView getMember(String branchId,String memberNo){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MemberView.class);
	 criteria.add(Restrictions.eq("branchId",branchId));
	 criteria.add(Restrictions.eq("memberNo",memberNo));
	 
	 return  (MemberView)criteria.list().get(0);
 }

 public void deleteMember(MemberView memberView) {
     sessionFactory.getCurrentSession().createQuery("delete from Member where id = "+memberView.getMemberId()).executeUpdate();
 }
}