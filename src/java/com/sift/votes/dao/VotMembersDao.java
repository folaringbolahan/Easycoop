/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotMembers;
import java.util.List;
import org.orm.PersistentException;

/**
 *
 * @author Nelson Akpos
 */
public interface VotMembersDao {
    //public boolean save(com.sift.votes.model.VotMembers votmembers) throws PersistentException;
    public void save(VotMembers votmembers);
    public List<VotMembers > listApprovedAgmMembers(int agmid);
    public boolean getMemberValidity(String email,int agmid);
    public List<VotMembers > listAgmMemberDetailsByAgmid(int agmid);
     public VotMembers getVotMemberDetails(int id);
}
