package com.sift.admin.dao;

import java.util.List;

import com.sift.admin.bean.AddressEntriesBean;
import com.sift.admin.bean.BranchBean;
import com.sift.admin.model.Branch;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface BranchDao{
	 public void addBranch(Branch branch);
	 public boolean addBranch(Branch branch,List<AddressEntriesBean> beanList);
	 public boolean addBranch(Branch branch,List<AddressEntriesBean> beanList,boolean isNew);
	 public List<Branch> listBranchs();
	 public List<Branch> listBranchsDistinct(String branchId);
	 public List<Branch> listBranchs(String filter);
	 public List<Branch> listBranchs(String atribName,String atribValue);
	 public List<Branch> listInActiveBranches(String companyId);
	 public List<BranchBean> listInActiveBranches();
	 public List<BranchBean> listBranchBeans(String companyId);
	 public Branch getBranch(int typeid);
	 public void deleteBranch(Branch branch);
	 public boolean updateActiveStatus(Branch branch);
}