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
public interface MemberEmailChangeDao {
     public void addEmail(MemberEmailChange memberEmailChange);
     public List<MemberEmailChange> listEmailChanges();
     public MemberEmailChange getPendingEmailChangesbyId(int id);
}
