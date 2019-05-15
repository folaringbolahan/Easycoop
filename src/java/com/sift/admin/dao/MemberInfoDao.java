package com.sift.admin.dao;

import java.util.List;

import com.sift.admin.model.MemberInfo;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface MemberInfoDao {
	 public void addMemberInfo(MemberInfo memberInfo);
	 public List<MemberInfo> listMemberInfo();
	 public MemberInfo getMemberInfo(int typeid);
	 public void deleteMemberInfo(MemberInfo memberInfo);
}