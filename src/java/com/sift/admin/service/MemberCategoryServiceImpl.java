/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.MemberCategory;
import com.sift.admin.dao.MemberCategoryDao;
/**
 *
 * @author Nelson Akpos
 */
@Service("memberCategoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MemberCategoryServiceImpl implements MemberCategoryService{

     @Autowired
	 private MemberCategoryDao memberCategoryDao;
     
     
      @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addMemberCategory(MemberCategory memberCategory) {
	   memberCategoryDao.addMemberCategory(memberCategory);
	 }

	 public List<MemberCategory> listMemberCategories() {
	  return memberCategoryDao.listMemberCategories();
	 }
          public void deleteMemberCategory(MemberCategory memberCategory){
		 memberCategoryDao.deleteMemberCategory(memberCategory);
	 }

   

   public MemberCategory getMemberCategory(int id){
	  return memberCategoryDao.getMemberCategory(id);
	 }  
     

    
}
