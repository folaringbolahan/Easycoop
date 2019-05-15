/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.service;

import com.sift.admin.dao.MemberExtraFieldDao;
import com.sift.admin.model.MemberExtraField;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("memberExtraFieldService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MemberExtraFieldServiceImpl implements MemberExtraFieldService {
     @Autowired
	 private MemberExtraFieldDao memberExtraFieldDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addMemberExtraField(MemberExtraField memberExtraField){
		memberExtraFieldDao.addMemberExtraField(memberExtraField);
	 }
         
         public List<MemberExtraField> listAllMemberExtraField(){
	  return memberExtraFieldDao.listAllMemberExtraField();
	 }
	 public List<MemberExtraField> listMemberExtraField(){
	  return memberExtraFieldDao.listMemberExtraField();
	 }
         public List<MemberExtraField> listMemberExtraFieldGrouped(){
          return memberExtraFieldDao.listMemberExtraFieldGrouped();
         }
       public List<MemberExtraField> listAllMemberExtraFieldForApproval(){
        return memberExtraFieldDao.listAllMemberExtraFieldForApproval();
       }

	 public MemberExtraField getMemberExtraField(int id){
	  return memberExtraFieldDao.getMemberExtraField(id);
	 }

	 public void deleteMemberExtraField(MemberExtraField memberExtraField){
		 memberExtraFieldDao.deleteMemberExtraField(memberExtraField);
	 }
          public void approveMemberExtraField(MemberExtraField memberExtraField){
		 memberExtraFieldDao.approveMemberExtraField(memberExtraField);
	 }
    
}
