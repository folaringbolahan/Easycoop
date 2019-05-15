package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.sift.admin.model.MemberView;
import com.sift.admin.dao.MemberViewDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("memberViewService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MemberViewServiceImpl implements MemberViewService {
	 @Autowired
	 private MemberViewDao memberViewDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addMemberView(MemberView memberView) {
		 memberViewDao.addMember(memberView);
	 }

	 public List<MemberView> listMembers() {
	  return memberViewDao.listMember();
	 }
	 
	 public List<MemberView> listMembersByCompanyId(String companyId){
		 return memberViewDao.listMembersByCompanyId(companyId);
	 }
	 
	 public List<MemberView> listMembersExclusiveByCompanyId(String companyId,String memberId){
		 return memberViewDao.listMembersExclusiveByCompanyId(companyId,memberId);
	 }

	 public List<MemberView> listMembersIncByCompanyId(String companyId,String memberId){
		 return memberViewDao.listMembersIncByCompanyId(companyId,memberId);
	 }
	 
	 public List<MemberView> listMembersByBranchId(String branchId){
		 return memberViewDao.listMembersByBranchId(branchId);
	 }
	 
	 public List<MemberView> listMembersExclusiveByBranchId(String branchId,String memberId){
		 return memberViewDao.listMembersExclusiveByBranchId(branchId,memberId);
	 }

	 public List<MemberView> listMembersIncByBranchId(String branchId,String memberId){
		 return memberViewDao.listMembersIncByBranchId(branchId,memberId);
	 }

	 public List<MemberView> listMembersIncByBranchIdNew(String branchId,String memberId){
		 return memberViewDao.listMembersIncByBranchIdNew(branchId,memberId);
	 }
	 
	 public MemberView getMember(String memberId){
	  return memberViewDao.getMember(memberId);
	 }
	 
	 public MemberView getMember(String branchId,String memberNo){
		 return memberViewDao.getMember(branchId,memberNo);
	 }

	 public void deleteMember(MemberView memberView) {
		 memberViewDao.deleteMember(memberView);
	 }
}