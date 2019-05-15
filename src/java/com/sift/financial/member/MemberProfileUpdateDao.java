/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member;

import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface MemberProfileUpdateDao {
   public List<MemberProfileUpdate> listMemberProfileUpdate(int branchid,int companyid); 
     public MemberProfileUpdate getPendingProfilebyId(int id);
}
