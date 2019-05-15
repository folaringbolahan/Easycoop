package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.MemberInfo;
import com.sift.admin.dao.MemberInfoDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("memberInfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MemberInfoServiceImpl implements MemberInfoService {
	 @Autowired
	 private MemberInfoDao memberInfoDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addMemberInfo(MemberInfo memberInfo) {
		 memberInfoDao.addMemberInfo(memberInfo);
	 }

	 public List<MemberInfo> listMemberInfos() {
	  return memberInfoDao.listMemberInfo();
	 }

	 public MemberInfo getMemberInfo(int id){
	  return memberInfoDao.getMemberInfo(id);
	 }

	 public void deleteMemberInfo(MemberInfo memberInfo) {
		 memberInfoDao.deleteMemberInfo(memberInfo);
	 }
}