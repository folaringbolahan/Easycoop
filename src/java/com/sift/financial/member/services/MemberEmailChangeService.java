/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.services;

import com.sift.financial.member.MemberEmailChange;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface MemberEmailChangeService {
 public void addEmail(MemberEmailChange memberEmailChange);
  public List<MemberEmailChange> listEmailChanges();
  public MemberEmailChange getPendingEmailChangesbyId (int id);
    
}
