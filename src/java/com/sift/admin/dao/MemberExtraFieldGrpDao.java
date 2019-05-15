/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.dao;

import com.sift.admin.model.MemberExtraFieldGrp;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface MemberExtraFieldGrpDao {
     public void addMemberExtraFieldGrp(MemberExtraFieldGrp addDetails);
     public List<MemberExtraFieldGrp> ListMemberExtraFieldGrps();
     public MemberExtraFieldGrp getMemberExtraFieldGrp(int id);
     public void deleteMemberExtraFieldGrp(MemberExtraFieldGrp addDetails);
}
