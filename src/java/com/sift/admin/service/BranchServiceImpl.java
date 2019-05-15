package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.Branch;
import com.sift.admin.model.Company;
import com.sift.admin.bean.AddressEntriesBean;
import com.sift.admin.bean.BranchBean;
import com.sift.admin.dao.BranchDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("branchService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BranchServiceImpl implements BranchService{
	 @Autowired
	 private BranchDao branchDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addBranch(Branch branch){
		 branchDao.addBranch(branch);
	 }

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean addBranch(Branch branch,List<AddressEntriesBean> beanList){
		 return branchDao.addBranch(branch,beanList);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean addBranch(Branch branch,List<AddressEntriesBean> beanList,boolean isNew){
		 return branchDao.addBranch(branch,beanList,isNew);
	 }
	 
	 public List<Branch> listBranch(){
	     return branchDao.listBranchs();
	 }
	 
	 public List<Branch> listBranchsDistinct(String branchId){
		 return branchDao.listBranchsDistinct(branchId);
	 }

	 public List<Branch> listBranchByCompany(String companyId){
	     return branchDao.listBranchs(companyId);
	 }
	 
	 public List<Branch> listInActiveBranches(String companyId){
		 return branchDao.listInActiveBranches(companyId);
	 }

	 public List<BranchBean> listInActiveBranches(){
		 return branchDao.listInActiveBranches();
	 }
	 
	 public List<Branch> listBranchs(String atribName,String atribValue){
		 return branchDao.listBranchs(atribName,atribValue);
	 }
	 
	 public List<BranchBean> listBranchBeans(String companyId){
		 return branchDao.listBranchBeans(companyId);
	 }

	 public Branch getBranch(int id){
	  return branchDao.getBranch(id);
	 }

	 public void deleteBranch(Branch branch){
		 branchDao.deleteBranch(branch);
	 }
	 
	 public boolean updateActiveStatus(Branch branch){
		 return branchDao.updateActiveStatus(branch);
	 }
}