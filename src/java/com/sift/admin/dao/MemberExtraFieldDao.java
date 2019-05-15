/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.dao;

import com.sift.admin.model.MemberExtraField;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface MemberExtraFieldDao {
     public void addMemberExtraField(MemberExtraField addDetails);
     public List<MemberExtraField> listAllMemberExtraField();
     public List<MemberExtraField> listMemberExtraField();
     public MemberExtraField getMemberExtraField(int id);
     public void deleteMemberExtraField(MemberExtraField addDetails);
     public void approveMemberExtraField(MemberExtraField addDetails);
     public List<MemberExtraField> listAllMemberExtraFieldForApproval();
     public List<MemberExtraField> listMemberExtraFieldGrouped();
}
