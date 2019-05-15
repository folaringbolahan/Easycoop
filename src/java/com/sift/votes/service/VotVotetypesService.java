/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.model.VotVotetypes;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface  VotVotetypesService {
    public List<VotVotetypes> listallvotetypes();
    public VotVotetypes getVoteTypeByid(int votetypeid);
}
