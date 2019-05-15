/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.service;

import com.sift.admin.dao.MemberExtraFieldGrpDao;
import com.sift.admin.model.MemberExtraFieldGrp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("memberExtraFieldGrpService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MemberExtraFieldGrpServiceImpl implements MemberExtraFieldGrpService{
     @Autowired
	 private MemberExtraFieldGrpDao memberExtraFieldGrpDao;
    
      @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addMemberExtraFieldGrp(MemberExtraFieldGrp memberExtraFieldGrp) {
	   memberExtraFieldGrpDao.addMemberExtraFieldGrp(memberExtraFieldGrp);
	 }
      
     public List<MemberExtraFieldGrp> ListMemberExtraFieldGrps(){
	  return memberExtraFieldGrpDao.ListMemberExtraFieldGrps();
	 }
      public void deleteMemberExtraFieldGrp(MemberExtraFieldGrp memberExtraFieldGrp){
		 memberExtraFieldGrpDao.deleteMemberExtraFieldGrp(memberExtraFieldGrp);
	 }

    public MemberExtraFieldGrp getMemberExtraFieldGrp(int id){
	  return memberExtraFieldGrpDao.getMemberExtraFieldGrp(id);
	 }
}
