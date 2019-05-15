package com.sift.admin.service;

import java.util.List;

import com.sift.admin.model.MemberView;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface MemberViewService { 
	public void addMemberView(MemberView memberView);
	public List<MemberView> listMembers(); 
	public List<MemberView> listMembersByCompanyId(String companyId);
	public List<MemberView> listMembersExclusiveByCompanyId(String companyId,String memberId);
	public List<MemberView> listMembersIncByCompanyId(String companyId,String memberId);

	
	public List<MemberView> listMembersByBranchId(String branchId);
	public List<MemberView> listMembersExclusiveByBranchId(String branchId,String memberId);
	public List<MemberView> listMembersIncByBranchId(String branchId,String memberId);
	public List<MemberView> listMembersIncByBranchIdNew(String branchId,String memberId);
	public MemberView getMember(String memberId); 
	public MemberView getMember(String branchId,String memberNo); 
	public void deleteMember(MemberView memberView);
}