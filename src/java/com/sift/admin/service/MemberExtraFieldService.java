/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.service;

import com.sift.admin.model.MemberExtraField;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface MemberExtraFieldService {
    public void addMemberExtraField(MemberExtraField memberExtraField);
       public List<MemberExtraField> listAllMemberExtraField();
	public List<MemberExtraField> listMemberExtraField();
        public List<MemberExtraField> listMemberExtraFieldGrouped();
        public List<MemberExtraField> listAllMemberExtraFieldForApproval();
	public MemberExtraField getMemberExtraField(int id); 
	public void deleteMemberExtraField(MemberExtraField memberExtraField);
        public void approveMemberExtraField(MemberExtraField memberExtraField);
}
