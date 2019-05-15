package com.sift.admin.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.admin.model.MemberInfo;

/**
 * @author Chris Faseun
 *
 */
@Repository("memberInfoDao")
public class MemberInfoDaoImpl implements MemberInfoDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addMemberInfo(MemberInfo memberInfo) {
   sessionFactory.getCurrentSession().saveOrUpdate(memberInfo);
 }

 @SuppressWarnings("unchecked")
 public List<MemberInfo> listMemberInfo(){
  return (List<MemberInfo>) sessionFactory.getCurrentSession().createCriteria(MemberInfo.class).list();
 }

 public MemberInfo getMemberInfo(int id){
  return (MemberInfo) sessionFactory.getCurrentSession().get(MemberInfo.class, id);
 }

 public void deleteMemberInfo(MemberInfo memberInfo) {
  sessionFactory.getCurrentSession().createQuery("DELETE FROM MEMBER_INFO WHERE id = "+memberInfo.getId()).executeUpdate();
 }
}