package com.sift.admin.service;

import java.util.List;

import com.sift.admin.model.MemberInfo;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface MemberInfoService { 
	public void addMemberInfo(MemberInfo memberInfo);
	public List<MemberInfo> listMemberInfos(); 
	public MemberInfo getMemberInfo(int id); 
	public void deleteMemberInfo(MemberInfo memberInfo);
}