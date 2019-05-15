/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.service;

import com.sift.admin.model.MemberExtraFieldGrp;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface MemberExtraFieldGrpService {
     public void addMemberExtraFieldGrp(MemberExtraFieldGrp memberExtraFieldGrp);
    public List<MemberExtraFieldGrp> ListMemberExtraFieldGrps(); 
    public MemberExtraFieldGrp getMemberExtraFieldGrp(int id);
    public void deleteMemberExtraFieldGrp(MemberExtraFieldGrp memberExtraFieldGrp);
}
