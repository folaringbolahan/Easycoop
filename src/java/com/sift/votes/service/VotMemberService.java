/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.gl.model.Users;
import com.sift.votes.model.VotMembers;
import java.util.List;
import org.orm.PersistentException;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 *
 * @author Nelson Akpos
 */
public interface VotMemberService {
 public String performBulkSave2(MultipartHttpServletRequest request, Users user) throws PersistentException;
 public void save(VotMembers votmembers);
 public List<VotMembers > listApprovedAgmMembers(int agmid);   
 public boolean getMemberValidity(String email,int agmid);
 public List<VotMembers > listAgmMemberDetailsByAgmid(int agmid);
 public  VotMembers getVotMemberDetails(int id);

}
